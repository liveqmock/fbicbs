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
    private int topSpaceLines = 9;  //ҳͷ��������
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
     * ����2
     */
    public void ExcelActglf2() throws IOException {
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        SimpleDateFormat dfgldate = new SimpleDateFormat("d");  //ȡ������day
        String yearmonth = SystemService.getBizDateYM();
        String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
        String orgidt = "";
        String orgnam = "";
        String title0 = "                             \u001BW1����\u001BW0";
        String plantop = "-------------------------------------------------------------------------------" +
                "--------";
        String plan1 = "|----|------------|---------------------------------|----------------------------" +
                "-----|";
        String plan = "|----|-----|------|----------------|----------------|----------------|------------" +
                "----|";
        int pageLine = 10;   //ÿ��10��+����
        int title01Len = 30;
        int title02Len = 20;
        int titleCont1Len = 5;  //ƾ֤����
        int titleCont2Len = 16;
        String titleCont1 = "|    |  ƾ֤����  |              ������             |            ���                 |";
        String titleCont2 = "|����| �跽|  ����|       �跽     |      ����      |     �跽       |    ����        |";
        String footerCont = "          ��ƣ�         ���ϣ�         �Ʊ�         ��ӡ���ڣ�" + reportdate;
        List<Actorg> orgLst = orgmap.selectForActglf();
        Iterator it = orgLst.iterator();
        while (it.hasNext()) {
            Actorg org = (Actorg) it.next();
            orgidt = org.getOrgidt();
            orgnam = org.getOrgnam();
            strPath += orgidt + "\\";
            //����·��
            File fileDirec = new File(strPath);
            if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                if (!fileDirec.mkdirs()) {
                    logger.error("·������ʧ�ܣ�");
                }
            }
            File file = new File(strPath + "����.txt");
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
                //������ͷ
                bw.write("P"+pageCount);
                bw.newLine();
                for(int seqi=1;seqi<topSpaceLines;seqi++) {
                    bw.write("   ");
                    bw.newLine();
                }
                bw.write(title0);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("���ڣ�" + yearmonth, title02Len) + "ҳ����" + pageCount);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("�ұ�" + curnmc, title01Len) + ReportHelper.getLeftSpaceStr("��Ŀ�ţ�" + glcode, title02Len) + "��Ŀ���ƣ�" + glcnam);
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
                //���ۼ�
                double dramntTotal = 0;
                double cramntTotal = 0;
                double drbala = 0;
                double crbala = 0;
                int j = 0;
                Iterator itGlf = glfLst.iterator();
                while (itGlf.hasNext()) {
                    Actglf glf = (Actglf) itGlf.next();
                    String glDay = dfgldate.format(glf.getGldate());
                    glDay = "| " + ReportHelper.getLeftSpaceStr(glDay, 3);  //����
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
                    //�Ӻ���
                    if (j !=0 && (j % pageLine == 0) && j < glfLst.size()) {
                        bw.write(plan);
                        bw.newLine();
                    }
                    dramntTotal += dramnt;
                    cramntTotal += cramnt;
                }
                //���ۼ�
                String dramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(dramntTotal), titleCont2Len);
                String cramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(cramntTotal), titleCont2Len);
                String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), titleCont2Len);
                String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), titleCont2Len);
                bw.write(plantop);
                bw.newLine();
                bw.write("  ���ۼ�           " + dramntTotalStr + cramntTotalStr + drbalaStr + crbalaStr);
                bw.newLine();
                bw.write(footerCont);
                //��ҳ;
                if (pageCount < glcnumLst.size()) {
                    bw.write("\f");
                }
                pageCount++;
            }
            bw.flush();
            bw.close();
        }
        System.out.print("���˱������ɳɹ���");
    }
}
