package cbs.common;

import cbs.common.utils.PropertyManager;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actorg;
import cbs.repository.code.model.ActorgExample;
import cbs.repository.code.model.Actsct;
import cbs.repository.code.model.ActsctExample;
import cbs.repository.platform.dao.PtdeptMapper;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.dao.PtenumainMapper;
import cbs.repository.platform.model.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-16
 * Time: 20:58:24
 * To change this template use File | Settings | File Templates.
 */
/*
 * updated by zhangxiaobo
 * Date: 2011-03-10
 * 新增变量 sdfdate10
  * 新增方法 getBizDate10
  * 新增金额格式化函数
 */
//@ManagedBean(eager = true)
public class SystemService {
    private static final Logger logger = LoggerFactory.getLogger(SystemService.class);

    static private SqlSessionFactory sessionFactory = IbatisFactory.ORACLE.getInstance();
    static private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMdd");
    static private SimpleDateFormat sdfdate10 = new SimpleDateFormat("yyyy-MM-dd");
    static private SimpleDateFormat sdfdateYM = new SimpleDateFormat("yyyy年MM月");
    static private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");
    static private SimpleDateFormat sdftime8 = new SimpleDateFormat("HH:mm:ss");

    public static String getSysidtAC() {
        return "8";
    }

    public static String getBatchResultFilePath() {
        return PropertyManager.getProperty("BATCH_RESULT_FILE_PATH");
    }

    //是否在套平前进行借贷平衡平衡检查（默认为 需检查）
    public static boolean isChkAllVchIsDCBalanced() {
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            PtenumainMapper mainMapper = session.getMapper(PtenumainMapper.class);
            PtenumainExample mainExample = new PtenumainExample();
            Ptenumain main = mainMapper.selectByPrimaryKey("CHKALLVCHISBAL");
            if (main == null) {
                return true;  //默认为 需检查
            }

            PtenudetailMapper detailMapper = session.getMapper(PtenudetailMapper.class);
            PtenudetailExample detailExample = new PtenudetailExample();
            detailExample.createCriteria().andEnutypeEqualTo("CHKALLVCHISBAL");
            List<Ptenudetail> detailList = detailMapper.selectByExample(detailExample);
            if (!detailList.isEmpty()) {
                Ptenudetail detail = detailList.get(0);
                if ("1".equals(detail.getEnuitemvalue())) {
                    return false;
                }
            }
            return true;  //默认为 需检查
        } catch (Exception e) {
            logger.error("获取套平前借贷平衡检查标志（CHKALLVCHISBAL）错误。", e);
            throw new RuntimeException("获取套平前借贷平衡检查标志（CHKALLVCHISBAL）错误。", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //获取chkflag（新老传票余额透支检查标志）  0=老检查标准，套平时检查透支 ； 1=新检查标准, 每次输入都要检查是否透支
    public static String getChkOverDraftFlag() {
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            PtenudetailMapper enudetailmap = session.getMapper(PtenudetailMapper.class);
            PtenudetailExample enudetailexm = new PtenudetailExample();
            enudetailexm.clear();
            enudetailexm.createCriteria().andEnutypeEqualTo("CHKALLVCH");
            List<Ptenudetail> enudetailList = enudetailmap.selectByExample(enudetailexm);
            if (enudetailList.isEmpty()) {
                return "1"; //默认值
            }
            Ptenudetail enudetail = enudetailList.get(0);
            return enudetail.getEnuitemvalue();
        } catch (Exception e) {
            logger.error("获取chkflag出现错误", e);
            throw new RuntimeException("获取chkflag出现错误", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

/*
    //获取chkflag（新老传票余额透支检查标志）   haiyu
    public static String getChkflag() {
        SqlSession session = sessionFactory.openSession();
        PtenudetailMapper enudetailmap = session.getMapper(PtenudetailMapper.class);
        PtenudetailExample enudetailexm = new PtenudetailExample();
        enudetailexm.clear();
        enudetailexm.createCriteria().andEnutypeEqualTo("SYSTEMPARAM").andEnuitemvalueEqualTo("CHKALLVCH");
        List<Ptenudetail> enudetailList = enudetailmap.selectByExample(enudetailexm);
        Ptenudetail enudetail = enudetailList.get(0);
        return enudetail.getEnuitemexpand();
    }
*/

    // 获取机构名
    public static String getDepnamByDeptid(String deptId) {
        String deptNam = null;
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            PtdeptMapper mapper = session.getMapper(PtdeptMapper.class);
            Ptdept dept = mapper.selectByPrimaryKey(deptId);
            if (dept != null) {
                deptNam = dept.getDeptname();
            }
            return deptNam;
        } catch (Exception e) {
            logger.error("获取机构名出现错误", e);
            throw new RuntimeException("获取机构名出现错误", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //20120731 zhanrui  获取本机构的联行行号， 主要用于主界面区分各部署版本  暂时不用，UI基础部分不使用Mybatis
    @Deprecated
    public static String getOrgInterBankCode() {
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActorgMapper mapper = session.getMapper(ActorgMapper.class);
            ActorgExample example = new ActorgExample();
            example.createCriteria().andOrglvlEqualTo("1");
            List<Actorg> records = mapper.selectByExample(example);
            if (records.isEmpty()) {
                logger.error("获取机构联行行号出现错误");
                return "";
            }
            String ibkcde = records.get(0).getIbkcde();
            if (ibkcde == null || "".equals(ibkcde)) {
                return "";
            }
            return ibkcde;
        } catch (Exception e) {
            logger.error("获取机构联行行号出现错误", e);
            throw new RuntimeException("获取机构联行行号出现错误", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /*
   * 获取满页最大行数*/
    public static int getLineLimit() {
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            PtenudetailMapper enudetailmap = session.getMapper(PtenudetailMapper.class);
            PtenudetailExample enudetailexm = new PtenudetailExample();
            enudetailexm.clear();
            enudetailexm.createCriteria().andEnutypeEqualTo("LINELIMIT");
            List<Ptenudetail> enudetailList = enudetailmap.selectByExample(enudetailexm);
            Ptenudetail enudetail = enudetailList.get(0);
            return Integer.parseInt(enudetail.getEnuitemvalue());
        } catch (NumberFormatException e) {
            logger.error("获取满页最大行数出现错误", e);
            throw new RuntimeException("获取满页最大行数出现错误", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    //TODO 系统中文字体
    public static String getPdfFont() {
        return "c:\\windows\\fonts\\simsun.ttc,1";
    }

    public static String getBizDate() {
        //TODO
        String result = "";
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            result = sdfdate.format(sct.getCrndat());
        } catch (Exception e) {
            logger.error("获取系统业务时间错误", e);
            throw new RuntimeException("获取系统业务时间错误！");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public static String getBizDate10() {
        String result = "";
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            result = sdfdate10.format(sct.getCrndat());
        } catch (Exception e) {
            logger.error("获取系统业务时间错误", e);
            throw new RuntimeException("获取系统业务时间错误！");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    public static String getBizDateYM() {
        String result = "";
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            result = sdfdateYM.format(sct.getCrndat());
        } catch (Exception e) {
            logger.error("获取系统业务时间错误", e);
            throw new RuntimeException("获取系统业务时间错误！");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * Create by wanglichao
     * 2014-11-19
     * 在对账单打印中使用默认系统日期前一天
     */
    public static Date sysDate11() {
        Date result;
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            result = sct.getCrndat();
        } catch (Exception e) {
            logger.error("获取系统业务时间错误", e);
            throw new RuntimeException("获取系统业务时间错误！");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return result;
    }
    public static Date getIpydat() {
        Date ipydat = null;
        SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            ipydat = sct.getIpydat();
        } catch (Exception e) {
            logger.error("获取系统业务时间错误", e);
            throw new RuntimeException("获取系统业务时间错误！");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return ipydat;
    }

    /**
     * 例："9999.88" ->  "9,999.88"
     *
     * @param str
     * @return
     */
    public static String formatStrAmt(String str) {
        NumberFormat n = NumberFormat.getNumberInstance();
        double d = 0.00;
        String outStr = null;
        try {
            d = Double.parseDouble(str);
            outStr = n.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outStr;
    }

    public static short getBizYear2() {
        String bizdate = getBizDate();
        return Short.parseShort(bizdate.substring(2, 4));
    }

    public static String getBizTime6() {
        return sdftime6.format(new Date());
    }

    public static String getBizTime8() {
        return sdftime8.format(new Date());
    }

}
