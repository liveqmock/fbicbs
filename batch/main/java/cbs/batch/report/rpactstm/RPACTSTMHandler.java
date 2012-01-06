package cbs.batch.report.rpactstm;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.billinfo.dao.ActlgcMapper;
import cbs.repository.account.maininfo.model.ActstmForActnum;
import cbs.repository.account.maininfo.model.ActstmForStmCotent;
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-17
 * Time: 15:10:43
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RPACTSTMHandler extends AbstractACBatchJobLogic {
    private static final Log logger = LogFactory.getLog(RPACTSTMHandler.class);
    private String nowDate = "";
    private String reportdate = "";
     @Inject
    private BatchSystemService systemService;
    @Inject
    private ActorgMapper orgmap;

    @Inject
    private ActlgcMapper stmmap;       //替换为actlgc表

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            nowDate = systemService.getBizDate();
            reportdate = systemService.getBizDate10();
            ExcelActlgc();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    /**
     * 分户账3
     * 文本打印
     * remark:
     * 传10号    摘要10     借方发生额15   贷方发生额15       余额15     日期10
     * 余额与日期中间+两个空格
     * actstm替换为 actlgc
     */
    public void ExcelActlgc() throws IOException {
        int topSpaceLines = 9;  //页头留白行数
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
        String orgidt = "";
        String orgnam = "";
        String title0 = "                                  \u001BW1分户账\u001BW0";
       String plantop = "--------------------------------------------------------------------------------" +
                "-------------------------------";
        String plan = "|----------|------|----------|----------------|----------------|----------------|--" +
                "---|-----------------------|";
        String contTitle = "|   日期   |凭证号|   摘要   |     借方发生额 |     贷方发生额 |         余额   | 日数|  " +
                "           积数      |";
        String contCQY = "|          |      |承前页    |                |                |";
        String contCont7="|     |                       |";
        //页脚
        String curPageBal = "          本页余额：";
        String curDate = "出帐日期：";
        String strPgcd = "                                   P";
        int title0Cnt1 = 45; //表头内容第一列最大字节数
        //内容列最大宽度（字节数)
        int contCnt1 = 6;
        int contCnt2 = 10;
        int contCnt3 = 16;
        int contCnt4 = 10;
        int contCnt5 = 4;     //日数列
        int contCnt6 = 23;    //积数列
        int footerCnt1 = 15;
        int footerCnt2 = 20;
        List<Actorg> orgLst = orgmap.selectForActlgc();
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
            File file = new File(strPath + "分户账.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            int acncount = 1;      //账户数
            int pageLine = 10;     //每隔10行加横线
            int seq = 1; //账户序号
            List<ActstmForActnum> stmnumLst = stmmap.selectForActnum(orgidt);   //取得账户基本信息--账户别
            Iterator it1 = stmnumLst.iterator();
            while (it1.hasNext()) {
                ActstmForActnum stmnum = (ActstmForActnum) it1.next();
                String curnmc = stmnum.getCurnmc();
                String actnam = stmnum.getActnam();
                String glcnam = stmnum.getGlcnam();
                String depnam = stmnum.getDepnam();
                String actacn = stmnum.getOldacn();
                String sysidt = stmnum.getSysidt();
                String cusidt = stmnum.getCusidt();
                String apcode = stmnum.getApcode();
                String curcde = stmnum.getCurcde();
                String irtval = "";
                DecimalFormat dformt = new DecimalFormat("0.0000");
                if (stmnum.getIrtval()==null) {
                    irtval="0 ‰";
                } else {
                    irtval = dformt.format(stmnum.getIrtval().doubleValue()/12 * 10) + " ‰";
                }
                for(int seqi=0;seqi<topSpaceLines;seqi++) {
                    bw.write("   ");
                    bw.newLine();
                }
                bw.write(title0);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("货币：" + curnmc, title0Cnt1) + "科目：" + glcnam);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title0Cnt1) + "部门：" + depnam);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("户名：" + actnam, title0Cnt1) + "账号：" + actacn);
                bw.newLine();
                bw.write("利率：月 " + irtval);
                bw.newLine();
                bw.write(plantop);
                bw.newLine();
                bw.write(contTitle);
                bw.newLine();

                //分户账内容
                List<ActstmForStmCotent> stmcont = stmmap.selectForStmContent(sysidt, orgidt, cusidt, apcode, curcde);
                Iterator it2 = stmcont.iterator();
                int j = 1;       //每页条数
                int totalCnt = 1; //总条数
                int beforeDaynum = 0;
                int beforestmpny = 0;  //前年份
                int beforenstmpg = 0;  //前页号
                while (it2.hasNext()) {
                    ActstmForStmCotent stmCont2 = (ActstmForStmCotent) it2.next();
                    if (totalCnt == 1) {
                        double lasbal = (double) stmCont2.getLasbal() / 100;
                        bw.write(plan);
                        bw.newLine();
                        bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(lasbal), contCnt3) + contCont7);
                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();
                    }
                    String[] strFurinf = stmCont2.getFurinf().split(" ");
                    String furinf2 = ((strFurinf.length > 1)?strFurinf[1] : "");//凭证号
                    String furinf = ReportHelper.getRightSpaceStr(furinf2, contCnt1);  //凭证号
                    String thrref = ReportHelper.getLeftSpaceStr(stmCont2.getThrref(), contCnt2);//摘要
                    double dtxnamt = (double) (stmCont2.getDtxnamt() * -1) / 100;
                    double ctxnamt = (double) stmCont2.getCtxnamt() / 100;
                    double actbal = (double) stmCont2.getActbal() / 100;
                    String spaDtxnamt = ReportHelper.getRightSpaceStr(df0.format(dtxnamt), contCnt3);
                    String spaCtxnamt = ReportHelper.getRightSpaceStr(df0.format(ctxnamt), contCnt3);
                    String spaActbal = ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3);
                    Date datedat = stmCont2.getValdat();
                    SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");
                    String valdat = ReportHelper.getLeftSpaceStr(dateformt.format(datedat), contCnt4);
                    //换页
                    if (totalCnt != 1 && (beforestmpny != stmCont2.getLegpny() || beforenstmpg != stmCont2.getNlegpg())) {
                        double lasbal = (double) stmCont2.getLasbal() / 100;
                        String spaLasbal =  ReportHelper.getLeftSpaceStr(df0.format(lasbal), footerCnt1);
                        //添加页脚
                        bw.write(plantop);
                        bw.newLine();
                        bw.write(curPageBal + spaLasbal + curDate +
                                ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "第" + beforenstmpg + "页" + strPgcd + seq);
                        bw.newLine();
                        //换页符
                        bw.write("\f");
                        seq++;
                        //建立表头
                        for(int seqi=0;seqi<topSpaceLines;seqi++) {
                            bw.write("   ");
                            bw.newLine();
                        }
                        bw.write(title0);
                        bw.newLine();
                        bw.write(ReportHelper.getLeftSpaceStr("货币：" + curnmc, title0Cnt1) + "科目：" + glcnam);
                        bw.newLine();
                        bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title0Cnt1) + "部门：" + depnam);
                        bw.newLine();
                        bw.write(ReportHelper.getLeftSpaceStr("户名：" + actnam, title0Cnt1) + "账号：" + actacn);
                        bw.newLine();
        //                bw.write("序号：" + seq);
                        bw.write("利率：月 " + irtval);
                        bw.newLine();
                        bw.write(plantop);
                        bw.newLine();
                        bw.write(contTitle);
                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();
                        bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(lasbal), contCnt3) + contCont7);
                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();
                        j = 1;   //行号归1
                    } else if (j != 1 && ((j-1) % pageLine == 0)) {
                        bw.write(plan);
                        bw.newLine();
                    }
                    if (j == 1) {
                        String daynum = ReportHelper.getRightSpaceStr(String.valueOf(stmCont2.getDaynum()), contCnt5);
                        double dbcraccm = (double) stmCont2.getCraccm() / 100;
                        String craccm = ReportHelper.getRightSpaceStr(df0.format(dbcraccm), contCnt6);
                        bw.write("|" + valdat +"|" + furinf +"|" + thrref +"|" + spaDtxnamt +"|" + spaCtxnamt +"|" + spaActbal + "|" + daynum +" |" + craccm + "|");
                    } else if (j != 1 && beforeDaynum != stmCont2.getDaynum()) {
                        String daynum = ReportHelper.getRightSpaceStr(String.valueOf(stmCont2.getDaynum()), contCnt5);
                        double dbcraccm = (double) stmCont2.getCraccm() / 100;
                        String craccm = ReportHelper.getRightSpaceStr(df0.format(dbcraccm), contCnt6);
                        bw.write("|" + valdat +"|" + furinf +"|" + thrref +"|" + spaDtxnamt +"|" + spaCtxnamt +"|" + spaActbal + "|" + daynum +" |" + craccm + "|");
                    } else
                        bw.write("|" + valdat +"|" + furinf +"|" + thrref +"|" + spaDtxnamt +"|" + spaCtxnamt +"|" + spaActbal+contCont7);
                    bw.newLine();
                    beforeDaynum = stmCont2.getDaynum();
                    beforestmpny = stmCont2.getLegpny();   //年
                    beforenstmpg = stmCont2.getNlegpg();   //页
                    //最后一行
                    if (totalCnt == stmcont.size()) {
                        //添加页脚
                        bw.write(plantop);
                        bw.newLine();
                        bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "第" + stmCont2.getNlegpg() + "页" + strPgcd + seq);
                        bw.newLine();
                    }
                    j++;
                    totalCnt++;
                }
                //账户数+1
                if (acncount < stmnumLst.size()) {
                    //换页符
                    bw.write("\f");
                }
                acncount++;
            }
            bw.flush();
            bw.close();
        }
        System.out.print("分户账报表生成成功！");

    }
}
