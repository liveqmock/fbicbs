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
    private ActlgcMapper stmmap;       //�滻Ϊactlgc��

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
     * �ֻ���3
     * �ı���ӡ
     * remark:
     * ��10��    ժҪ10     �跽������15   ����������15       ���15     ����10
     * ����������м�+�����ո�
     * actstm�滻Ϊ actlgc
     */
    public void ExcelActlgc() throws IOException {
        int topSpaceLines = 9;  //ҳͷ��������
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
        String orgidt = "";
        String orgnam = "";
        String title0 = "                                  \u001BW1�ֻ���\u001BW0";
       String plantop = "--------------------------------------------------------------------------------" +
                "-------------------------------";
        String plan = "|----------|------|----------|----------------|----------------|----------------|--" +
                "---|-----------------------|";
        String contTitle = "|   ����   |ƾ֤��|   ժҪ   |     �跽������ |     ���������� |         ���   | ����|  " +
                "           ����      |";
        String contCQY = "|          |      |��ǰҳ    |                |                |";
        String contCont7="|     |                       |";
        //ҳ��
        String curPageBal = "          ��ҳ��";
        String curDate = "�������ڣ�";
        String strPgcd = "                                   P";
        int title0Cnt1 = 45; //��ͷ���ݵ�һ������ֽ���
        //����������ȣ��ֽ���)
        int contCnt1 = 6;
        int contCnt2 = 10;
        int contCnt3 = 16;
        int contCnt4 = 10;
        int contCnt5 = 4;     //������
        int contCnt6 = 23;    //������
        int footerCnt1 = 15;
        int footerCnt2 = 20;
        List<Actorg> orgLst = orgmap.selectForActlgc();
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
            File file = new File(strPath + "�ֻ���.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            int acncount = 1;      //�˻���
            int pageLine = 10;     //ÿ��10�мӺ���
            int seq = 1; //�˻����
            List<ActstmForActnum> stmnumLst = stmmap.selectForActnum(orgidt);   //ȡ���˻�������Ϣ--�˻���
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
                    irtval="0 ��";
                } else {
                    irtval = dformt.format(stmnum.getIrtval().doubleValue()/12 * 10) + " ��";
                }
                for(int seqi=0;seqi<topSpaceLines;seqi++) {
                    bw.write("   ");
                    bw.newLine();
                }
                bw.write(title0);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("���ң�" + curnmc, title0Cnt1) + "��Ŀ��" + glcnam);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title0Cnt1) + "���ţ�" + depnam);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("������" + actnam, title0Cnt1) + "�˺ţ�" + actacn);
                bw.newLine();
                bw.write("���ʣ��� " + irtval);
                bw.newLine();
                bw.write(plantop);
                bw.newLine();
                bw.write(contTitle);
                bw.newLine();

                //�ֻ�������
                List<ActstmForStmCotent> stmcont = stmmap.selectForStmContent(sysidt, orgidt, cusidt, apcode, curcde);
                Iterator it2 = stmcont.iterator();
                int j = 1;       //ÿҳ����
                int totalCnt = 1; //������
                int beforeDaynum = 0;
                int beforestmpny = 0;  //ǰ���
                int beforenstmpg = 0;  //ǰҳ��
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
                    String furinf2 = ((strFurinf.length > 1)?strFurinf[1] : "");//ƾ֤��
                    String furinf = ReportHelper.getRightSpaceStr(furinf2, contCnt1);  //ƾ֤��
                    String thrref = ReportHelper.getLeftSpaceStr(stmCont2.getThrref(), contCnt2);//ժҪ
                    double dtxnamt = (double) (stmCont2.getDtxnamt() * -1) / 100;
                    double ctxnamt = (double) stmCont2.getCtxnamt() / 100;
                    double actbal = (double) stmCont2.getActbal() / 100;
                    String spaDtxnamt = ReportHelper.getRightSpaceStr(df0.format(dtxnamt), contCnt3);
                    String spaCtxnamt = ReportHelper.getRightSpaceStr(df0.format(ctxnamt), contCnt3);
                    String spaActbal = ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3);
                    Date datedat = stmCont2.getValdat();
                    SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");
                    String valdat = ReportHelper.getLeftSpaceStr(dateformt.format(datedat), contCnt4);
                    //��ҳ
                    if (totalCnt != 1 && (beforestmpny != stmCont2.getLegpny() || beforenstmpg != stmCont2.getNlegpg())) {
                        double lasbal = (double) stmCont2.getLasbal() / 100;
                        String spaLasbal =  ReportHelper.getLeftSpaceStr(df0.format(lasbal), footerCnt1);
                        //���ҳ��
                        bw.write(plantop);
                        bw.newLine();
                        bw.write(curPageBal + spaLasbal + curDate +
                                ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "��" + beforenstmpg + "ҳ" + strPgcd + seq);
                        bw.newLine();
                        //��ҳ��
                        bw.write("\f");
                        seq++;
                        //������ͷ
                        for(int seqi=0;seqi<topSpaceLines;seqi++) {
                            bw.write("   ");
                            bw.newLine();
                        }
                        bw.write(title0);
                        bw.newLine();
                        bw.write(ReportHelper.getLeftSpaceStr("���ң�" + curnmc, title0Cnt1) + "��Ŀ��" + glcnam);
                        bw.newLine();
                        bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title0Cnt1) + "���ţ�" + depnam);
                        bw.newLine();
                        bw.write(ReportHelper.getLeftSpaceStr("������" + actnam, title0Cnt1) + "�˺ţ�" + actacn);
                        bw.newLine();
        //                bw.write("��ţ�" + seq);
                        bw.write("���ʣ��� " + irtval);
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
                        j = 1;   //�кŹ�1
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
                    beforestmpny = stmCont2.getLegpny();   //��
                    beforenstmpg = stmCont2.getNlegpg();   //ҳ
                    //���һ��
                    if (totalCnt == stmcont.size()) {
                        //���ҳ��
                        bw.write(plantop);
                        bw.newLine();
                        bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "��" + stmCont2.getNlegpg() + "ҳ" + strPgcd + seq);
                        bw.newLine();
                    }
                    j++;
                    totalCnt++;
                }
                //�˻���+1
                if (acncount < stmnumLst.size()) {
                    //��ҳ��
                    bw.write("\f");
                }
                acncount++;
            }
            bw.flush();
            bw.close();
        }
        System.out.print("�ֻ��˱������ɳɹ���");

    }
}
