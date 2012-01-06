package cbs.batch.report.rpactglf;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.SystemService;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActglfMapper;
import cbs.repository.account.maininfo.model.Actglf;
import cbs.repository.account.maininfo.model.ActglfForGlcnum;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.model.Actorg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-17
 * Time: 15:18:32
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RPACTGLFHandler extends AbstractACBatchJobLogic {
    private static final Log logger = LogFactory.getLog(RPACTGLFHandler.class);
    private String nowDate = "";
    private String reportdate = "";
    private int topSpaceLines = 9;  //页头留白行数
    @Inject
    private BatchSystemService systemService;
    
    @Inject
    private ActorgMapper orgmap;

    @Inject
    private ActglfMapper glfmap;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            nowDate = systemService.getBizDate();
            reportdate = systemService.getBizDate10();
            ExcelActglf2();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    /**
     * 总账2
     */
    public void ExcelActglf2() throws IOException {
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        SimpleDateFormat dfgldate = new SimpleDateFormat("d");  //取得日期day
        String yearmonth = SystemService.getBizDateYM();
        String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
        String orgidt = "";
        String orgnam = "";
        String title0 = "                             \u001BW1总账\u001BW0";
        String plantop = "-------------------------------------------------------------------------------" +
                "--------";
        String plan1 = "|----|------------|---------------------------------|----------------------------" +
                "-----|";
        String plan = "|----|-----|------|----------------|----------------|----------------|------------" +
                "----|";
        int pageLine = 10;   //每隔10行+横线
        int title01Len = 30;
        int title02Len = 20;
        int titleCont1Len = 5;  //凭证号列
        int titleCont2Len = 16;
        String titleCont1 = "|    |  凭证张数  |              发生额             |            余额                 |";
        String titleCont2 = "|日期| 借方|  贷方|       借方     |      贷方      |     借方       |    贷方        |";
        String footerCont = "          会计：         复合：         制表：         打印日期：" + reportdate;
        List<Actorg> orgLst = orgmap.selectForActglf();
        Iterator it = orgLst.iterator();
        while (it.hasNext()) {
            Actorg org = (Actorg) it.next();
            orgidt = org.getOrgidt();
            orgnam = org.getOrgnam();
            strPath += orgidt + "\\";
            //创建路径
            File fileDirec = new File(strPath);
            if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                if (!fileDirec.mkdirs()) {
                    logger.error("路径创建失败！");
                }
            }
            File file = new File(strPath + "总账.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            int pageCount = 1;
            List<ActglfForGlcnum> glcnumLst = glfmap.selectForGlcnum(orgidt);
            Iterator it1 = glcnumLst.iterator();
            while (it1.hasNext()) {
                ActglfForGlcnum glcnum = (ActglfForGlcnum) it1.next();
                String curcde = glcnum.getCurcde();
                String curnmc = glcnum.getCurnmc();
                String glcode = glcnum.getGlcode();
                String glcnam = glcnum.getGlcnam();
                //创建表头
                bw.write("P"+pageCount);
                bw.newLine();
                for(int seqi=1;seqi<topSpaceLines;seqi++) {
                    bw.write("   ");
                    bw.newLine();
                }
                bw.write(title0);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + yearmonth, title02Len) + "页数：" + pageCount);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("币别：" + curnmc, title01Len) + ReportHelper.getLeftSpaceStr("科目号：" + glcode, title02Len) + "科目名称：" + glcnam);
                bw.newLine();
                bw.write(plantop);
                bw.newLine();
                bw.write(titleCont1);
                bw.newLine();
                bw.write(plan1);
                bw.newLine();
                bw.write(titleCont2);
                bw.newLine();
                bw.write(plan);
                bw.newLine();
                List<Actglf> glfLst = glfmap.selectForGlcCont(orgidt, glcode, curcde);
                //月累计
                double dramntTotal = 0;
                double cramntTotal = 0;
                double drbala = 0;
                double crbala = 0;
                int j = 0;
                Iterator itGlf = glfLst.iterator();
                while (itGlf.hasNext()) {
                    Actglf glf = (Actglf) itGlf.next();
                    String glDay = dfgldate.format(glf.getGldate());
                    glDay = "| " + ReportHelper.getLeftSpaceStr(glDay, 3);  //日期
                    String drcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(glf.getDrcunt()),titleCont1Len);
                    String crcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(glf.getCrcunt()),titleCont1Len+1);
                    double dramnt = (double) (glf.getDramnt() * -1) / 100;
                    double cramnt = (double) glf.getCramnt() / 100;
                    drbala = (double) (glf.getDrbala() * -1) / 100;
                    crbala = (double) glf.getCrbala() / 100;
                    String dramntStr = ReportHelper.getRightSpaceStr(df0.format(dramnt), titleCont2Len);
                    String cramntStr = ReportHelper.getRightSpaceStr(df0.format(cramnt), titleCont2Len);
                    String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), titleCont2Len);
                    String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), titleCont2Len);
                    bw.write(glDay + "|" + drcuntStr + "|" + crcuntStr + "|" + dramntStr + "|" + cramntStr + "|" + drbalaStr + "|" + crbalaStr + "|");
                    bw.newLine();
                    j++;
                    //加横线
                    if (j !=0 && (j % pageLine == 0) && j < glfLst.size()) {
                        bw.write(plan);
                        bw.newLine();
                    }
                    dramntTotal += dramnt;
                    cramntTotal += cramnt;
                }
                //月累计
                String dramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(dramntTotal), titleCont2Len);
                String cramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(cramntTotal), titleCont2Len);
                String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), titleCont2Len);
                String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), titleCont2Len);
                bw.write(plantop);
                bw.newLine();
                bw.write("  月累计           " + dramntTotalStr + cramntTotalStr + drbalaStr + crbalaStr);
                bw.newLine();
                bw.write(footerCont);
                //换页;
                if (pageCount < glcnumLst.size()) {
                    bw.write("\f");
                }
                pageCount++;
            }
            bw.flush();
            bw.close();
        }
        System.out.print("总账报表生成成功！");
    }
}
