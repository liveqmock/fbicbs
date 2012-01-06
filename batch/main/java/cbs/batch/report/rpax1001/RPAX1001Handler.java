package cbs.batch.report.rpax1001;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.model.ActactForPlcRpt;
import cbs.repository.code.dao.ActglcMapper;
import cbs.repository.code.model.Actglc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-3-17
 * Time: 13:57:17
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RPAX1001Handler extends AbstractACBatchJobLogic {
    private static final Log logger = LogFactory.getLog(RPAX1001Handler.class);
    private String nowDate = "";
    private String reportdate = "";
    @Inject
    private BatchSystemService systemService;
    @Inject
    private ActglcMapper glcmap;

    @Inject
    private ActactMapper actmap;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            nowDate = systemService.getBizDate();
            reportdate = systemService.getBizDate10();
            ExcelPlc();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    /**
     * 损益表报表生成
     */
    public void ExcelPlc() throws IOException {
        Date date = new Date();
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate +"\\010\\";
        String plitStr = "|";
        String title0 = "                                                             \u001BW1损益表\u001BW0";
//        String title0 = "                                                                     损益表        ";
        String title1 = "货币:人民币                                      " + reportdate + "            " +
                "                                      单位：元";
        String titleCont0 = "                         支出项目                              " + plitStr +
                "                            收入项目 ";
        String titleCont1 = "    代号                 名称                      金额        " + plitStr +
                "    代号                 名称                      金额";
        int contLen0 = 6;
        int contLen1 = 5;
        int contLen2 = 32;
        int contLen3 = 20;
        String leftSpace = ReportHelper.getRightSpaceStr(plitStr, contLen0 + contLen1 + contLen2 + contLen3 + plitStr.length());
        String plan = "------------------------------------------------------------------------------" +
                "-------------------------------------------------";
        //创建路径
        File fileDirec = new File(strPath);
        if (!fileDirec.isDirectory() || !fileDirec.exists()) {
            if (!fileDirec.mkdirs()) {
                System.out.print("路径创建失败！");
                logger.error("路径创建失败！");
            }
        }
        //创建报表文件
        File file = new File(strPath + "损益表.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(title0);
        bw.newLine();
        bw.write(title1);
        bw.newLine();
        bw.write(plan);
        bw.newLine();
        bw.write(titleCont0);
        bw.newLine();
        bw.write(plan);
        bw.newLine();
        bw.write(titleCont1);
        bw.newLine();
        bw.write(plan);
        bw.newLine();
        List<String> strLst = new ArrayList();
        List<Actglc> glcLst = glcmap.selectGrpActForPlc(ACEnum.CURCDE_001.getStatus(), ACEnum.PLCTYP_OUT.getStatus());//支出项目
        Iterator it0 = glcLst.iterator();
        while (it0.hasNext()) {
            Actglc glc = (Actglc) it0.next();
            String glcode = glc.getGlcode();
            String glcnam = glc.getGlcnam();
            String strT = ReportHelper.getLeftSpaceStr(glcode, contLen0 + contLen1) + ReportHelper.getLeftSpaceStr(glcnam, contLen2 + contLen3)
                    + plitStr;
            strLst.add(strT);
            List<ActactForPlcRpt> plcLst = actmap.selectForPlc(ACEnum.CURCDE_001.getStatus(), glcode);
            Iterator it1 = plcLst.iterator();
            while (it1.hasNext()) {
                ActactForPlcRpt plc = (ActactForPlcRpt) it1.next();
                String plcode = ReportHelper.getRightSpaceStr(ReportHelper.getLeftSpaceStr(plc.getActplc(), contLen1), contLen0 + contLen1);
                String plcnam = ReportHelper.getLeftSpaceStr(plc.getPlcnam(), contLen2);
                double dbTotalBokbal = (double) plc.getTotalBokbal() / 100;
                String totalBokbal = ReportHelper.getRightSpaceStr(df0.format(dbTotalBokbal), contLen3);
                strLst.add(plcode + plcnam + totalBokbal + plitStr);
            }
        }
        List<Actglc> glc1Lst = glcmap.selectGrpActForPlc(ACEnum.CURCDE_001.getStatus(), ACEnum.PLCTYP_IN.getStatus());//收入项目
        Iterator it2 = glc1Lst.iterator();
        int lstCnt = strLst.size();
        int i = 1;
        while (it2.hasNext()) {
            Actglc glc = (Actglc) it2.next();
            String glcode = glc.getGlcode();
            String glcnam = glc.getGlcnam();
            String strT = ReportHelper.getLeftSpaceStr(glcode, contLen0 + contLen1) + ReportHelper.getLeftSpaceStr(glcnam, contLen2 + contLen3);
            if (lstCnt >= i) {
                String tmpStr = strLst.get(i - 1);
                strLst.remove(i - 1);
                strLst.add(i - 1, tmpStr + strT);
            } else {
                strLst.add(leftSpace + strT);
            }
            i++;
            List<ActactForPlcRpt> plcLst = actmap.selectForPlc(ACEnum.CURCDE_001.getStatus(), glcode);
            Iterator it1 = plcLst.iterator();
            while (it1.hasNext()) {
                ActactForPlcRpt plc = (ActactForPlcRpt) it1.next();
                String plcode = ReportHelper.getRightSpaceStr(ReportHelper.getLeftSpaceStr(plc.getActplc(), contLen1), contLen0 + contLen1);
                String plcnam = ReportHelper.getLeftSpaceStr(plc.getPlcnam(), contLen2);
                double dbTotalBokbal = (double) plc.getTotalBokbal() / 100;
                String totalBokbal = ReportHelper.getRightSpaceStr(df0.format(dbTotalBokbal), contLen3);
                String strT0 = plcode + plcnam + totalBokbal;
                if (lstCnt >= i) {
                    String tmpStr = strLst.get(i - 1);
                    strLst.remove(i - 1);
                    strLst.add(i - 1, tmpStr + strT0);
                } else {
                    strLst.add(leftSpace + strT0);
                }
                i++;
            }
        }
        lstCnt = strLst.size();
        for (i = 0; i < lstCnt; i++) {
            bw.write(strLst.get(i));
            bw.newLine();
            bw.write(plan);
            bw.newLine();
        }
        bw.flush();
        bw.close();
        System.out.print("损益表数据生成成功。");

    }
}
