package cbs.batch.common;

/**
 * 批处理主控.
 * User: zhanrui
 * Date: 2010-12-15
 * Time: 17:09:38
 * To change this template use File | Settings | File Templates.
 */

import cbs.common.exception.BusinessErrorException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import java.text.SimpleDateFormat;
import java.util.*;

public class CommonBatch {

    public static final int EXITSTATUS_NORMAL = 0;
    public static final int EXITSTATUS_ERROR = 1;

    private static Log log = LogFactory.getLog(CommonBatch.class);
    private static Log observationLog = LogFactory.getLog("observationlog");
    private static final String LOG4J_FILENAME_TAIL = "log4j.properties";

    private static final int EXITSTATUS_BATCHMAIN_STARTERROR = 120;
    private static final int EXITSTATUS_BUSINESS_EXITERROR = 121;

    private static final String PKG_BUSINESSDATELOGIC = "";
    private static final String PKG_JOBINFDBACCESSLOGIC = "";

    private static final String PKG_DIR = "cbs/batch/";

    private static final String SYS_KEY_JOB_ID = "jobId";
    private static final String SYS_KEY_JOB_RUN_ID = "jobRunId";
    private static final String SYS_KEY_USER_ID = "userId";
    private static final String SYS_KEY_MODULE_ID = "moduleId";

    private static final String SYS_KEY_APPLICATION_CONTEXT =
            "applicationContext";

    private static final String SYS_KEY_EXECUTE_LOGIC_CLASS =
            "executeLogicClass";

    private static final String SYS_KEY_NOTUSE_DB = "notuseDb";

    private static final int LENGTH_BUSINESSDATE = 8;

    private static final String BUSINESSDATE_FORMAT = "yyyyMMdd";

    //TODO
    private static final String ENV_NAME_BUSINESSDATE = " ";

    private static final String JOB_STATUS_RUN = "1";
    private static final String JOB_STATUS_EXIT = "0";

    private static final String REGEX_JOB_ID = "^J[ACSVB]{2,5}[0-9]{4,9}$";

    private static final int HEAD_MODULEID_IN_JOBID = 2;
    private static final int TAIL_MODULEID_IN_JOBID = 7;

    private static final int AMOUNT_DAY = 7;

    private int addNormalEndDay = AMOUNT_DAY;

    private int addEndDay = AMOUNT_DAY;

    private AbstractBatchJobLogic abstractBatchJobLogic;

    private void setAbstractBatchJobLogic(AbstractBatchJobLogic abstractBatchJobLogic) {
        this.abstractBatchJobLogic = abstractBatchJobLogic;
    }

    public void setAddNormalEndDay(int addNormalEndDay) {
        this.addNormalEndDay = addNormalEndDay;
    }

    public void setAddEndDay(int addEndDay) {
        this.addEndDay = addEndDay;
    }

    /**
     * 启动
     *
     * @param args
     */
    public static void batchMain(String[] args) {
        new CommonBatch().batch(args);
    }

    /**
     * 主控
     *
     * @param args
     */
    private void batch(String[] args) {

        long startTime = System.currentTimeMillis();

        String jobId = getJobId();
        String jobRunId = System.getProperty(SYS_KEY_JOB_RUN_ID, null);
        String userId = System.getProperty(SYS_KEY_USER_ID, null);
        String moduleId = System.getProperty(SYS_KEY_MODULE_ID, null);

        String dirPrefix = PKG_DIR + moduleId + "/" + jobId.toLowerCase();
        String mybatisConfigFile = dirPrefix +  "/prop/mybatis-batch.xml";
        String executeClassName = (dirPrefix + "/" + jobId + "Handler").replaceAll("/", ".");

        int exitStatus = EXITSTATUS_BATCHMAIN_STARTERROR;
        BatchParameterData batchParameterData = null;

        try {
            initLog4J(dirPrefix, jobId);

            if (log.isDebugEnabled()) {
                log.debug(SYS_KEY_EXECUTE_LOGIC_CLASS + ":" + executeClassName);
            }

            //setBusinessDate(factory);

            jobRunId = updateJobStatus(jobRunId, jobId, JOB_STATUS_RUN,
                    null, userId, moduleId, "false");

            batchParameterData = new BatchParameterData();
            batchParameterData.setCommandLine(args);
            batchParameterData.setJobId(jobId);
            batchParameterData.setJobProcessId(jobRunId);
            batchParameterData.setExitStatus(EXITSTATUS_ERROR);
            batchParameterData.setUserId(userId);
            batchParameterData.setModuleId(moduleId);
            if (log.isDebugEnabled()) {
                log.debug("batchParameterData:" + batchParameterData.toString());
            }

            BatchBeanFactory factory;
            factory = new GuiceXmlBatchBeanFactory();

            setAbstractBatchJobLogic(factory.getAbstractBatchJobLogic(mybatisConfigFile, executeClassName));

            exitStatus = EXITSTATUS_BUSINESS_EXITERROR;

            //RUN
            try {
                this.abstractBatchJobLogic.run(batchParameterData);
            } catch (BusinessErrorException bee) {
                //继续进行JOB数据库状态更新 TODO
            } catch (Exception e) {
                exitStatus = batchParameterData.getExitStatus();
                //跳过数据库状态更新
                throw e;
            }

            jobRunId = updateJobStatus(jobRunId, jobId, JOB_STATUS_EXIT,
                    Integer.valueOf(batchParameterData.getExitStatus()), userId, moduleId, "false");

            if (log.isDebugEnabled())
                log.debug("任务结束状态=" + batchParameterData.getExitStatus());

            long endTime = System.currentTimeMillis();
            System.out.println("程序:" + jobId + " 运行时间： " + (endTime - startTime) + "ms.");

            System.exit(batchParameterData.getExitStatus());
        } catch (Throwable th) {
            log.info(batchParameterData);
            log.info(ExceptionUtils.getFullStackTrace(th));
            if (log.isDebugEnabled()) {
                log.debug("任务结束状态=" + exitStatus);
            }

            long endTime = System.currentTimeMillis();
            System.out.println("程序运行时间： " + (endTime - startTime) + "ms.");
            System.exit(exitStatus);
        }
    }

    private String getJobId() {
        String jobId = (System.getProperty(SYS_KEY_JOB_ID));

        if (StringUtils.isEmpty(jobId)) {
            throw new RuntimeException("SystemJobID:" + SYS_KEY_JOB_ID + "未定义！");
        }

/*
        Pattern ptn = Pattern.compile(REGEX_JOB_ID);
        Matcher mc = ptn.matcher(jobId);
        if (!mc.matches()) {
            throw new RuntimeException(
                    "SystemJobID:" + SYS_KEY_JOB_ID + "不匹配。"
                    + "(" + SYS_KEY_JOB_ID + "=" + jobId + ")");
        }
*/
        return jobId;
    }

    private void initLog4J(String dirPrefix, String jobId) {
        //String log4jFilename = dirPrefix + "/prop/" + "J" + jobId + "-" + LOG4J_FILENAME_TAIL;
        String log4jFilename = LOG4J_FILENAME_TAIL;
        String path = this.getClass().getClassLoader().getResource(log4jFilename).getFile();

        System.setProperty("jobId", jobId);
        //TODO    !!
        System.setProperty("prjPath", "d:/cbs/log");
        PropertyConfigurator.configure(path);

        log.debug("Log4J配置文件读入成功。");
        if (log.isDebugEnabled()) {
            //log.debug("java.library.path:" + System.getProperty("java.library.path"));
        }
        if (log.isDebugEnabled()) {
            //log.debug("java.class.path:" + System.getProperty("java.class.path"));
        }
    }

    private String[] getStringArray(Properties prop,
                                    String propertyKey) {

        List<String> props = getProperties(prop, propertyKey);
        String[] stringArray = new String[props.size()];
        for (int i = 0; i < props.size(); i++) {
            stringArray[i] = props.get(i);
        }
        return stringArray;
    }

    private List<String> getProperties(Properties prop, String propertyKey) {

        List<String> props = new ArrayList<String>();
        for (int i = 1; ; i++) {
            String key = propertyKey + i;
            String value = prop.getProperty(key, null);
            if (value == null) {
                if (log.isDebugEnabled()) {
                    log.debug(key + "定义完成.");
                }
                break;
            }
            props.add(value);
        }
        return props;
    }


    /**
     * 更新数据库中JOB状态
     *
     * @param jobRunId
     * @param jobId
     * @param jobStatus
     * @param exitStatus
     * @param userId
     * @param moduleId   启动元模块
     * @param notuseDb
     * @return job运行ID
     */
    private String updateJobStatus(String jobRunId, String jobId,
                                   String jobStatus, Integer exitStatus, String userId,
                                   String moduleId, String notuseDb) {

/*
        if (!"true".equals(notuseDb)) {

        */
        return jobRunId;
    }

    private String getModuleIdFromJobId(String jobId) {

        String moduleId = "M" + jobId.substring(HEAD_MODULEID_IN_JOBID, TAIL_MODULEID_IN_JOBID);
        return moduleId;
    }

    private Date getBusinessDate(int field, int amount) {

        SimpleDateFormat sdf = new SimpleDateFormat();

        sdf.applyPattern(BUSINESSDATE_FORMAT);
        //Date businessDate = sdf.parse(businessDateLogic.getBusinessDate(), new ParsePosition(0));
        Date businessDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(businessDate);
        cal.add(field, amount);
        return cal.getTime();
    }

}
