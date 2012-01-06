package cbs.batch.report.rpxx1001d;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActcglMapper;
import cbs.repository.account.maininfo.model.ActcglForglc;
import cbs.repository.code.dao.ActccyMapper;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.model.Actccy;
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
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-17
 * Time: 14:13:44
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RPXX1001DHandler extends AbstractACBatchJobLogic {
    private static final Log logger = LogFactory.getLog(RPXX1001DHandler.class);
    private String nowDate = "";
    private String reportdate = "";
    @Inject
    private BatchSystemService systemService;
    @Inject
    private ActorgMapper orgmap;

    @Inject
    private ActccyMapper ccymap;

    @Inject
    private ActcglMapper cglmap;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            nowDate = systemService.getBizDate();
            reportdate = systemService.getBizDate10();
            ExcelActcgl2();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    /**
     * 日计表2
     */
    public void ExcelActcgl2() throws IOException {

        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
        String orgidt = "";
        String title0 = "                                                                             " +
                "           \u001BW1日计表\u001BW0";
        String plan = "-------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------" +
                "-------------";
        int title0Len = 40;
        int title1Len = 68;
        int cont0Len = 6;
        int cont1Len = 34;
        int cont2Len = 20;
        int cont3Len = 8;
        String titleCont0 = " 科目 ";
        String titleCont1 = "        科目名称                  ";
        String titleCont2 = "    上日借方余额    ";
        String titleCont3 = "      上日贷方余额  ";
        String titleCont4 = "借方笔数";
        String titleCont5 = "         借方发生额 ";
        String titleCont6 = "贷方笔数";
        String titleCont7 = "         贷方发生额 ";
        String titleCont8 = "     本日借方余额   ";
        String titleCont9 = "    本日贷方余额    ";
        List<Actorg> orgLst = orgmap.selectForActcgl();
        Iterator it = orgLst.iterator();
        while (it.hasNext()) {
            Actorg org = (Actorg) it.next();
            orgidt = org.getOrgidt();
            strPath += orgidt + "\\";
            //创建路径
            File fileDirec = new File(strPath);
            if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                if (!fileDirec.mkdirs()) {
                    System.out.print("路径创建失败！");
                    logger.error("路径创建失败！");
                }
            }
            File file = new File(strPath + "日计表.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            //获得币别种类
            List<Actccy> ccyLst = ccymap.selectForActcgl(orgidt, ACEnum.RECTYP_D.getStatus());
            Iterator itCcy = ccyLst.iterator();
            int ccyCnt = 0;
            while (itCcy.hasNext()) {
                Actccy ccy = (Actccy) itCcy.next();
                String curcde = ccy.getCurcde();
                String curnmc = ccy.getCurnmc();
                //换页
                if (ccyCnt != 0 && (ccyCnt % 2 == 0)) {
                    bw.write("\f");
                } else if (ccyCnt != 0) {
                    bw.newLine();
                    bw.newLine();
                }
                bw.write(title0);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("币别：" + curnmc, title0Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title1Len) +
                        "单位：元");
                bw.newLine();
                bw.write(plan);
                bw.newLine();
                //表头
                bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5 + titleCont6 +
                        titleCont7 + titleCont8 + titleCont9);
                bw.newLine();
                bw.write(plan);
                bw.newLine();
                //获得数据

                List<ActcglForglc> cgLst = cglmap.selectForglcnam(ACEnum.RECTYP_D.getStatus(), orgidt, curcde);
                Iterator itCgl = cgLst.iterator();
                double dlsbalTotal = 0;
                double clsbalTotal = 0;
                int drcuntTotal = 0;
                double dramntTotal = 0;
                int crcuntTotal = 0;
                double cramntTotal = 0;
                double drbalaTotal = 0;
                double crbalaTotal = 0;
                while (itCgl.hasNext()) {
                    ActcglForglc cfg = (ActcglForglc) itCgl.next();
                    String glcode = ReportHelper.getLeftSpaceStr(cfg.getGlcode(), cont0Len);
                    String glcnam = ReportHelper.getLeftSpaceStr(cfg.getGlcnam(), cont1Len);
                    double dlsbal = (double) (cfg.getDlsbal() * -1) / 100;
                    double clsbal = (double) cfg.getClsbal() / 100;
                    int drcunt = cfg.getDrcunt();
                    double dramnt = (double) (cfg.getDramnt() * -1) / 100;
                    int crcunt = cfg.getCrcunt();
                    double cramnt = (double) cfg.getCramnt() / 100;
                    double drbala = (double) (cfg.getDrbala() * -1) / 100;
                    double crbala = (double) cfg.getCrbala() / 100;
                    String dlsbalStr = ReportHelper.getRightSpaceStr(df0.format(dlsbal), cont2Len);
                    String clsbalStr = ReportHelper.getRightSpaceStr(df0.format(clsbal), cont2Len);
                    String drcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(drcunt), cont3Len);
                    String dramntStr = ReportHelper.getRightSpaceStr(df0.format(dramnt), cont2Len);
                    String crcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(crcunt), cont3Len);
                    String cramntStr = ReportHelper.getRightSpaceStr(df0.format(cramnt), cont2Len);
                    String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), cont2Len);
                    String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), cont2Len);
                    //插入内容
                    bw.write(glcode + glcnam + dlsbalStr + clsbalStr + drcuntStr + dramntStr + crcuntStr + cramntStr +
                            drbalaStr + crbalaStr);
                    bw.newLine();

                    //合计
                    dlsbalTotal += dlsbal;
                    clsbalTotal += clsbal;
                    drcuntTotal += drcunt;
                    dramntTotal += dramnt;
                    crcuntTotal += crcunt;
                    cramntTotal += cramnt;
                    drbalaTotal += drbala;
                    crbalaTotal += crbala;
                }
                //合计
                String totalStr = "      " + ReportHelper.getLeftSpaceStr("合计", cont1Len);
                String dlsbalTotalStr = ReportHelper.getRightSpaceStr(df0.format(dlsbalTotal), cont2Len);
                String clsbalTotalStr = ReportHelper.getRightSpaceStr(df0.format(clsbalTotal), cont2Len);
                String drcuntTotalStr = ReportHelper.getRightSpaceStr(String.valueOf(drcuntTotal), cont3Len);
                String dramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(dramntTotal), cont2Len);
                String crcuntTotalStr = ReportHelper.getRightSpaceStr(String.valueOf(crcuntTotal), cont3Len);
                String cramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(cramntTotal), cont2Len);
                String drbalaTotalStr = ReportHelper.getRightSpaceStr(df0.format(drbalaTotal), cont2Len);
                String crbalaTotalStr = ReportHelper.getRightSpaceStr(df0.format(crbalaTotal), cont2Len);
                bw.write(plan);
                bw.newLine();
                bw.write(totalStr + dlsbalTotalStr + clsbalTotalStr + drcuntTotalStr + dramntTotalStr + crcuntTotalStr
                        + cramntTotalStr + drbalaTotalStr + crbalaTotalStr);
                bw.newLine();
                //币别计数
                ccyCnt++;
            }
            bw.flush();
            bw.close();
        }
        System.out.print("日计表报表生成成功！");

    }
}
