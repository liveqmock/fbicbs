package cbs.batch.report.rptobf;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.model.ActactForOblRpt;
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
 * Time: 10:28:04
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RPTOBFHandler extends AbstractACBatchJobLogic {
    private static final Log logger = LogFactory.getLog(RPTOBFHandler.class);
    private String nowDate = "";
    private String reportdate = "";
    @Inject
    private BatchSystemService systemService;
    @Inject
    private ActorgMapper orgmap;
    @Inject
    private ActactMapper actmap;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            nowDate = systemService.getBizDate();
            reportdate = systemService.getBizDate10();
            ExcelObf2();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }
    /*
    * 余额表2报表数据生成*/

    public void ExcelObf2() throws IOException {
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
        String orgidt = "";
        String orgnam = "";
        int pageRowCnt = 50;   //50行数据(+分行标志行+表头+页脚 总共60行/页)
        int pageLine = 10;     //每隔10行加横线
        String title0 = "                                       \u001BW1余额表\u001BW0";
        String titleCont0 = "       账号       ";
        String titleCont1 = "              名称              ";
        String titleCont2 = "      余额     ";
        String titleCont3 = "  最后交易日期";
        String titleCont4 = "   借方发生额  ";
        String titleCont5 = "   贷方发生额  ";
        String plan = "-----------------------------------------------------------------------------------------------------------";
        int title01Len = 50;   //表头内容（机构）字节长度
        int title02Len = 27;   //表头内容（日期）字节长度
        List<Actorg> orgLst = orgmap.selectForActact();
        Iterator it = orgLst.iterator();
        while (it.hasNext()) {
            int pageCount = 1;   //页数
            Actorg org = (Actorg) it.next();
            orgidt = org.getOrgidt();
            orgnam = org.getOrgnam();
            strPath = strPath + orgidt + "\\";
            //创建路径
            File fileDirec = new File(strPath);
            if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                if (!fileDirec.mkdirs()) {
                    logger.error("路径创建失败！");
                }
            }
            File file = new File(strPath + "余额表.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(title0);
            bw.newLine();
            bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
            bw.newLine();
            bw.write(plan);
            bw.newLine();
            bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
            bw.newLine();
            bw.write(plan);
            bw.newLine();
            int j = 0; //行数计算
            //获得币种分组
            List<ActactForOblRpt> CurLst = actmap.selectGrpCurcde(orgidt);
            Iterator itCur = CurLst.iterator();
            while (itCur.hasNext()) {
                ActactForOblRpt grpCur = (ActactForOblRpt) itCur.next();
                String curcde = grpCur.getCurcde();
                String curnmc = grpCur.getCurnmc();
                long bokbalCur = grpCur.getBokbal();
                bw.write(ReportHelper.getLeftSpaceStr("币别：" + curcde, 18) + curnmc);
                bw.newLine();
                j++;
                //加横线
                if (j !=0 && (j % pageLine == 0)) {
                    bw.write(plan);
                    bw.newLine();
                }
                //换页
                if ((j != 0) && (j % pageRowCnt == 0)) {
                    //换页
                    pageCount++;
                    bw.write("\f");
                    //建立表头
                    bw.write(title0);
                    bw.newLine();
                    bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();

                }
                //获得科目分组
                List<ActactForOblRpt> glLst = actmap.selectGrpGlcode(orgidt, curcde);
                Iterator itGl = glLst.iterator();
                while (itGl.hasNext()) {
                    ActactForOblRpt grpGl = (ActactForOblRpt) itGl.next();
                    String glcode = grpGl.getGlcode();
                    String glcnam = grpGl.getGlcnam();
                    double bokbalGl = (double) grpGl.getBokbal() / 100;
                    String bokbalGlStr = ReportHelper.getRightSpaceStr(df0.format(bokbalGl), 15);
                    bw.write(ReportHelper.getLeftSpaceStr("总账码：" + glcode, 18) + ReportHelper.getLeftSpaceStr(glcnam, 32) + bokbalGlStr);
                    bw.newLine();
                    j++;
                    //加横线
                    if (j !=0 && (j % pageLine == 0)) {
                        bw.write(plan);
                        bw.newLine();
                    }
                    //换页
                    if ((j != 0) && (j % pageRowCnt == 0)) {
                        //换页
                        pageCount++;
                        bw.write("\f");
                        //建立表头
                        bw.write(title0);
                        bw.newLine();
                        bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();
                        bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();
                    }
                    //获得核算码分组
                    List<ActactForOblRpt> apLst = actmap.selectGrpApcode(orgidt, curcde, glcode);
                    Iterator itAp = apLst.iterator();
                    while (itAp.hasNext()) {
                        ActactForOblRpt grpAp = (ActactForOblRpt) itAp.next();
                        String apcode = grpAp.getApcode();
                        String apcnam = grpAp.getApcnam();
                        double bokbalAp = (double) grpAp.getBokbal() / 100;
                        String bokbalApStr = ReportHelper.getRightSpaceStr(df0.format(bokbalAp), 15);
                        bw.write(ReportHelper.getLeftSpaceStr("核算码：" + apcode, 18) + ReportHelper.getLeftSpaceStr(apcnam, 32) + bokbalApStr);
                        bw.newLine();
                        j++;
                        //加横线
                        if (j !=0 && (j % pageLine == 0)) {
                            bw.write(plan);
                            bw.newLine();
                        }
                        //换页
                        if ((j != 0) && (j % pageRowCnt == 0)) {
                            //换页
                            pageCount++;
                            bw.write("\f");
                            //建立表头
                            bw.write(title0);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                        }
                        //获得账号信息
                        List<ActactForOblRpt> actLst = actmap.selectForObfRpt(orgidt, curcde, apcode);
                        Iterator itAct = actLst.iterator();
                        while (itAct.hasNext()) {
                            ActactForOblRpt actRpt = (ActactForOblRpt) itAct.next();
                            String oldacn = actRpt.getOldacn();
                            String actnam = actRpt.getActnam();
                            double bokbal = (double) actRpt.getBokbal() / 100;
                            String lentdt = df.format(actRpt.getLentdt());
                            double ddramt = (double) (actRpt.getDdramt() *-1) / 100;
                            double dcramt = (double) actRpt.getDcramt() / 100;
                            oldacn = ReportHelper.getLeftSpaceStr(oldacn, 18);
                            actnam = ReportHelper.getLeftSpaceStr(actnam, 32);
                            String bokbalStr = ReportHelper.getRightSpaceStr(df0.format(bokbal), 15);
                            String ddramtStr = ReportHelper.getRightSpaceStr(df0.format(ddramt), 15);
                            String dcramtStr = ReportHelper.getRightSpaceStr(df0.format(dcramt), 15);
                            bw.write(oldacn + actnam + bokbalStr + "  " + lentdt + ddramtStr + dcramtStr);
                            bw.newLine();
                            j++;
                            //加横线
                            if (j !=0 && (j % pageLine == 0)) {
                                bw.write(plan);
                                bw.newLine();
                            }
                            //换页
                            if ((j != 0) && (j % pageRowCnt == 0)) {
                                //换页
                                pageCount++;
                                bw.write("\f");
                                //建立表头
                                bw.write(title0);
                                bw.newLine();
                                bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
                                bw.newLine();
                                bw.write(plan);
                                bw.newLine();
                                bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                                bw.newLine();
                                bw.write(plan);
                                bw.newLine();
                            }
                        }
                    }
                }
            }
            bw.flush();
            bw.close();
        }
        System.out.print("余额表数据生成成功。");
        logger.error("余额表数据生成成功。");
    }
}
