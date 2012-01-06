package cbs.view.ac.report;

import cbs.common.IbatisManager;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.billinfo.dao.ActlgcMapper;
import cbs.repository.account.billinfo.dao.ActnsmMapper;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.dao.ActcglMapper;
import cbs.repository.account.maininfo.dao.ActglfMapper;
import cbs.repository.account.maininfo.dao.ActvthMapper;
import cbs.repository.account.maininfo.model.*;
import cbs.repository.code.dao.ActccyMapper;
import cbs.repository.code.dao.ActglcMapper;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.model.Actccy;
import cbs.repository.code.model.Actglc;
import cbs.repository.code.model.Actorg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-12-8
 * Time: 13:29:04
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "report")
@RequestScoped
public class Report {
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private static final Log logger = LogFactory.getLog(Report.class);
    private final String nowDate = SystemService.getBizDate();
    private final String reportdate = SystemService.getBizDate10();
    /**
     * �����������*/
    public void ExcelPlc() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\010\\";
            String plitStr = "|";
            String title0 = "                                                             �����";
            String title1 = "����:�����                                      " + reportdate + "            " +
                    "                                      ��λ��Ԫ";
            String titleCont0 = "                         ֧����Ŀ                              " + plitStr +
                    "                            ������Ŀ ";
            String titleCont1 = "    ����                 ����                      ���        " + plitStr +
                    "    ����                 ����                      ���";
            int contLen0 = 6;
            int contLen1 = 5;
            int contLen2 = 32;
            int contLen3 = 20;
            String leftSpace = ReportHelper.getRightSpaceStr(plitStr, contLen0 + contLen1 + contLen2 + contLen3 + plitStr.length());
            String plan = "------------------------------------------------------------------------------" +
                    "-------------------------------------------------";
            //����·��
            File fileDirec = new File(strPath);
            if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                if (!fileDirec.mkdirs()) {
                    MessageUtil.addInfo("·������ʧ�ܣ�");
                    logger.error("·������ʧ�ܣ�");
                }
            }
            //���������ļ�
            File file = new File(strPath+"�����.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw=new BufferedWriter(fw);
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
            ActglcMapper glcmap = session.getMapper(ActglcMapper.class);
            List<Actglc> glcLst = glcmap.selectGrpActForPlc(ACEnum.CURCDE_001.getStatus(),ACEnum.PLCTYP_OUT.getStatus());//֧����Ŀ
            Iterator it0 = glcLst.iterator();
            while (it0.hasNext()) {
                Actglc glc = (Actglc)it0.next();
                String glcode = glc.getGlcode();
                String glcnam = glc.getGlcnam();
                String strT = ReportHelper.getLeftSpaceStr(glcode, contLen0 + contLen1) + ReportHelper.getLeftSpaceStr(glcnam, contLen2 + contLen3)
                        + plitStr;
                strLst.add(strT);
                ActactMapper actmap = session.getMapper(ActactMapper.class);
                List<ActactForPlcRpt> plcLst = actmap.selectForPlc(ACEnum.CURCDE_001.getStatus(),glcode);
                Iterator it1 = plcLst.iterator();
                while (it1.hasNext()) {
                    ActactForPlcRpt plc = (ActactForPlcRpt)it1.next();
                    String plcode = ReportHelper.getRightSpaceStr(ReportHelper.getLeftSpaceStr(plc.getActplc(), contLen1), contLen0 + contLen1);
                    String plcnam = ReportHelper.getLeftSpaceStr(plc.getPlcnam(), contLen2);
                    double dbTotalBokbal = (double)plc.getTotalBokbal()/100;
                    String totalBokbal = ReportHelper.getRightSpaceStr(df0.format(dbTotalBokbal), contLen3);
                    strLst.add(plcode + plcnam + totalBokbal + plitStr);
                }
            }
            List<Actglc> glc1Lst = glcmap.selectGrpActForPlc(ACEnum.CURCDE_001.getStatus(),ACEnum.PLCTYP_IN.getStatus());//������Ŀ
            Iterator it2 = glc1Lst.iterator();
            int lstCnt = strLst.size();
            int i = 1;
            while (it2.hasNext()) {
                Actglc glc = (Actglc)it2.next();
                String glcode = glc.getGlcode();
                String glcnam = glc.getGlcnam();
                String strT = ReportHelper.getLeftSpaceStr(glcode, contLen0 + contLen1) + ReportHelper.getLeftSpaceStr(glcnam, contLen2 + contLen3);
                if (lstCnt >= i) {
                    String tmpStr = strLst.get(i-1);
                    strLst.remove(i-1);
                    strLst.add(i-1, tmpStr + strT);
                }
                else {
                    strLst.add(leftSpace + strT);
                }
                i++;
                ActactMapper actmap = session.getMapper(ActactMapper.class);
                List<ActactForPlcRpt> plcLst = actmap.selectForPlc(ACEnum.CURCDE_001.getStatus(),glcode);
                Iterator it1 = plcLst.iterator();
                while (it1.hasNext()) {
                    ActactForPlcRpt plc = (ActactForPlcRpt)it1.next();
                    String plcode = ReportHelper.getRightSpaceStr(ReportHelper.getLeftSpaceStr(plc.getActplc(), contLen1), contLen0 + contLen1);
                    String plcnam = ReportHelper.getLeftSpaceStr(plc.getPlcnam(), contLen2);
                    double dbTotalBokbal = (double)plc.getTotalBokbal()/100;
                    String totalBokbal = ReportHelper.getRightSpaceStr(df0.format(dbTotalBokbal), contLen3);
                    String strT0 = plcode + plcnam + totalBokbal;
                    if (lstCnt >= i) {
                        String tmpStr = strLst.get(i-1);
                        strLst.remove(i-1);
                        strLst.add(i-1,tmpStr + strT0);
                    } else {
                        strLst.add(leftSpace + strT0);
                    }
                    i++;
                }
            }
            lstCnt = strLst.size();
            for(i=0;i<lstCnt;i++) {
                bw.write(strLst.get(i));
                bw.newLine();
            }
            bw.flush();
            bw.close();
            MessageUtil.addInfoWithClientID(null, "������������ɳɹ���");
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
            logger.error("������������ݴ���");
            MessageUtil.addInfoWithClientID(null, "��������ݳ�����ݴ���");
        } finally {
            session.close();
        }
    }
    /*
    * ����2������������*/
    public String ExcelObf2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            int pageRowCnt = 50;   //25������(+���б�־��+��ͷ+ҳ�� �ܹ�60��/ҳ)
            int pageLine = 10;     //ÿ��10�мӺ���
            String title0 = "                                       ����";
            String titleCont0 = "       �˺�       ";
            String titleCont1 = "              ����              ";
            String titleCont2 = "      ���     ";
            String titleCont3 = "  ���������";
            String titleCont4 = "   �跽������  ";
            String titleCont5 = "   ����������  ";
            String plan = "-----------------------------------------------------------------------------------------------------------";
            int title01Len = 50;   //��ͷ���ݣ��������ֽڳ���
            int title02Len = 27;   //��ͷ���ݣ����ڣ��ֽڳ���
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActact();
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                int pageCount = 1;   //ҳ��
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                strPath = strPath + orgidt + "\\";
                //����·��
                File fileDirec = new File(strPath);
                if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                    if (!fileDirec.mkdirs()) {
                        MessageUtil.addInfo("·������ʧ�ܣ�");
                        logger.error("·������ʧ�ܣ�");
                    }
                }
                File file = new File(strPath+"����.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                bw.write(title0);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("���ڣ�" + reportdate, title02Len) + "ҳ�룺" + pageCount);
                bw.newLine();
                bw.write(plan);
                bw.newLine();
                bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                bw.newLine();
                bw.write(plan);
                bw.newLine();
                int j = 0; //��������
                //��ñ��ַ���
                ActactMapper actmap = session.getMapper(ActactMapper.class);
                List<ActactForOblRpt> CurLst = actmap.selectGrpCurcde(orgidt);
                Iterator itCur = CurLst.iterator();
                while (itCur.hasNext()) {
                    ActactForOblRpt grpCur = (ActactForOblRpt) itCur.next();
                    String curcde = grpCur.getCurcde();
                    String curnmc = grpCur.getCurnmc();
                    bw.write(ReportHelper.getLeftSpaceStr("�ұ�" + curcde, 18) + curnmc);
                    bw.newLine();
                    j++;
                    //�Ӻ���
                    if (j !=0 && (j % pageLine == 0)) {
                        bw.write(plan);
                        bw.newLine();
                    }
                    //��ҳ
                    if ((j!=0) && (j%pageRowCnt==0)) {
                        //��ҳ
                        pageCount++;
                        bw.write("\f");
                        //������ͷ
                        bw.write(title0);
                        bw.newLine();
                        bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();
                        bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("���ڣ�" + reportdate, title02Len) + "ҳ�룺" + pageCount);
                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();

                    }
                    //��ÿ�Ŀ����
                    List<ActactForOblRpt> glLst = actmap.selectGrpGlcode(orgidt,curcde);
                    Iterator itGl = glLst.iterator();
                    while (itGl.hasNext()) {
                        ActactForOblRpt grpGl = (ActactForOblRpt) itGl.next();
                        String glcode = grpGl.getGlcode();
                        String glcnam = grpGl.getGlcnam();
                        double bokbalGl = (double)grpGl.getBokbal()/100;
                        String bokbalGlStr = ReportHelper.getRightSpaceStr(df0.format(bokbalGl), 15);
                        bw.write(ReportHelper.getLeftSpaceStr("�����룺" + glcode, 18) + ReportHelper.getLeftSpaceStr(glcnam, 32) + bokbalGlStr);
                        bw.newLine();
                        j++;
                        //�Ӻ���
                        if (j !=0 && (j % pageLine == 0)) {
                            bw.write(plan);
                            bw.newLine();
                        }
                        //��ҳ
                        if ((j!=0) && (j%pageRowCnt==0)) {
                            //��ҳ
                            pageCount++;
                            bw.write("\f");
                            //������ͷ
                            bw.write(title0);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("���ڣ�" + reportdate, title02Len) + "ҳ�룺" + pageCount);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                        }
                        //��ú��������
                        List<ActactForOblRpt> apLst = actmap.selectGrpApcode(orgidt,curcde,glcode);
                        Iterator itAp = apLst.iterator();
                        while (itAp.hasNext()) {
                            ActactForOblRpt grpAp = (ActactForOblRpt)itAp.next();
                            String apcode = grpAp.getApcode();
                            String apcnam = grpAp.getApcnam();
                            double bokbalAp = (double)grpAp.getBokbal()/100;
                            String bokbalApStr = ReportHelper.getRightSpaceStr(df0.format(bokbalAp), 15);
                            bw.write(ReportHelper.getLeftSpaceStr("�����룺" + apcode, 18) + ReportHelper.getLeftSpaceStr(apcnam, 32) + bokbalApStr);
                            bw.newLine();
                            j++;
                            //�Ӻ���
                            if (j !=0 && (j % pageLine == 0)) {
                                bw.write(plan);
                                bw.newLine();
                            }
                            //��ҳ
                            if ((j!=0) && (j%pageRowCnt==0)) {
                                //��ҳ
                                pageCount++;
                                bw.write("\f");
                                //������ͷ
                                bw.write(title0);
                                bw.newLine();
                                bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("���ڣ�" + reportdate, title02Len) + "ҳ�룺" + pageCount);
                                bw.newLine();
                                bw.write(plan);
                                bw.newLine();
                                bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                                bw.newLine();
                                bw.write(plan);
                                bw.newLine();
                            }
                             //����˺���Ϣ
                            List<ActactForOblRpt> actLst = actmap.selectForObfRpt(orgidt,curcde,apcode);
                            Iterator itAct = actLst.iterator();
                            while (itAct.hasNext()) {
                                ActactForOblRpt actRpt = (ActactForOblRpt) itAct.next();
                                String oldacn = actRpt.getOldacn();
                                String actnam = actRpt.getActnam();
                                double bokbal = (double)actRpt.getBokbal()/100;
                                String lentdt = df.format(actRpt.getLentdt());
                                double ddramt = (double)(actRpt.getDdramt() * -1)/100;
                                double dcramt = (double)actRpt.getDcramt()/100;
                                oldacn = ReportHelper.getLeftSpaceStr(oldacn, 18);
                                actnam = ReportHelper.getLeftSpaceStr(actnam, 32);
                                String bokbalStr = ReportHelper.getRightSpaceStr(df0.format(bokbal), 15);
                                String ddramtStr = ReportHelper.getRightSpaceStr(df0.format(ddramt), 15);
                                String dcramtStr = ReportHelper.getRightSpaceStr(df0.format(dcramt), 15);
                                bw.write(oldacn + actnam + bokbalStr + "  " + lentdt + ddramtStr + dcramtStr);
                                bw.newLine();
                                j++;
                                //�Ӻ���
                                if (j !=0 && (j % pageLine == 0)) {
                                    bw.write(plan);
                                    bw.newLine();
                                }
                                //��ҳ
                                if ((j!=0) && (j%pageRowCnt==0)) {
                                    //��ҳ
                                    pageCount++;
                                    bw.write("\f");
                                    //������ͷ
                                    bw.write(title0);
                                    bw.newLine();
                                    bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("���ڣ�" + reportdate, title02Len) + "ҳ�룺" + pageCount);
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
             MessageUtil.addInfoWithClientID(null, "�����������ɳɹ���");
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
            logger.error("������ݴ���");
            MessageUtil.addInfoWithClientID(null, "�������ݳ�����ݴ���");
        } finally {
            session.close();
            return null;
        }
    }

    /**
     * �սᵥ2*/
    public String ExcelActvch2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            DecimalFormat df01 = new DecimalFormat("###,###");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title1 = "                                 ��Ŀ�սᵥ";
            String titleOrg = "";
            String titleCurDate = "";
            String titleGlc = "";
            String titleCur = "";
            String title2Cell1 = "          ";
            String title2Cell2 = "                  �跽                  ";
            String title2Cell3 = "                  ����                  ";
            String title3Cell2 = "       ����       ";
            String title3Cell3 = "         ���         ";
            String title4 = "�ֽ�      ";
            String title5 = "ת��      ";
            String title6 = "����      ";
            String title7 = "�ϼ�      ";
            String footer = title2Cell1 + "���ϣ�                                  �Ʊ�                                  ";
            String plan = "-------------------------------------------------------------------------------------------";
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActvch();
            if (orgLst.size() == 0) {
                MessageUtil.addInfo("��Ŀ�սᵥ�������������ɡ�");
                logger.info("��Ŀ�սᵥ�������������ɡ�");
                return null;
            }
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
                        MessageUtil.addInfo("·������ʧ�ܣ�");
                        logger.error("·������ʧ�ܣ�");
                    }
                }
                File file = new File(strPath+"�սᵥ.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(fw);
                //��ȡ����
                int rowCnt = 0;
                ActvthMapper vchmap = session.getMapper(ActvthMapper.class);
                List<ActvchForGlDRpt> glcurLst = vchmap.selectForGlCur(orgidt);
                int glCurCnt = 0;
                Iterator it1 = glcurLst.iterator();
                while (it1.hasNext()) {
                    ActvchForGlDRpt glcur = (ActvchForGlDRpt)it1.next();
                    String glcode = glcur.getGlcode();
                    String curcde = glcur.getCurcde();
                    String glcnam = glcur.getGlcnam();
                    String curnmc = glcur.getCurnmc();
                    titleOrg = ReportHelper.getLeftSpaceStr("������" + orgnam, 48);
                    titleCurDate = ReportHelper.getLeftSpaceStr("���ڣ�" + reportdate, 20);
                    titleGlc = ReportHelper.getLeftSpaceStr("��Ŀ��" + glcode + " " + glcnam, 48);
                    titleCur = ReportHelper.getLeftSpaceStr("�ұ�" + curnmc, 20);
                    bw.newLine();
                    bw.newLine();
                    bw.write(title1);
                    bw.newLine();
                    bw.write(titleOrg+titleCurDate);
                    bw.newLine();
                    bw.write(titleGlc+titleCur);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(title2Cell1 + title2Cell2 + title2Cell3);
                    bw.newLine();
                    bw.write(title2Cell1 + title3Cell2 + title3Cell3 + title3Cell2 + title3Cell3);
                    bw.newLine();
                    rowCnt += 8;
                    //��ȡ����
                    List<ActvchForGlDRpt> gldrptLst = vchmap.selectForGlDRpt(orgidt,curcde,glcode);
                    Iterator it2 = gldrptLst.iterator();
                    int totalDcunt = 0;
                    double totalDamt = 0;
                    int totalCcunt = 0;
                    double totalCamt = 0;
                    int tempi = 0;
                    while (it2.hasNext()) {
                        ActvchForGlDRpt gldrpt = (ActvchForGlDRpt)it2.next();
                        String rvslbl = gldrpt.getRvslbl();
                        int intDcun = Integer.parseInt(gldrpt.getDcunt());
                        double doubleDamt = Double.parseDouble(gldrpt.getDamt()) * -1/100;
                        int intCcunt = Integer.parseInt(gldrpt.getCcunt());
                        double doubleCamt = Double.parseDouble(gldrpt.getCamt())/100;
                        String dcunt = ReportHelper.getRightSpaceStr(df01.format(intDcun), 18);   //�跽����
                        String damt = ReportHelper.getRightSpaceStr(df0.format(doubleDamt), 22);     //�跽���
                        String ccunt = ReportHelper.getRightSpaceStr(df01.format(intCcunt), 18);   //��������
                        String camt = ReportHelper.getRightSpaceStr(df0.format(doubleCamt), 22);     //�������
                        if (tempi == 0 && rvslbl.equals("1")) {
                            bw.write(plan);
                            bw.newLine();
                            bw.write(title4 + dcunt + damt + ccunt + camt);
                        } else if (tempi == 0 && rvslbl.equals("2")) {
                            bw.write(plan);
                            bw.newLine();
                            bw.write(title4);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(title5 + dcunt + damt + ccunt + camt);
                        } else
                            bw.write(title5 + dcunt + damt + ccunt + camt);

                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();
                        totalDcunt += intDcun;
                        totalDamt += doubleDamt;
                        totalCcunt += intCcunt;
                        totalCamt += doubleCamt;
                        tempi++;
                    }
                    bw.write(title6);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    String strTotalDcunt = ReportHelper.getRightSpaceStr(String.valueOf(totalDcunt), 18);
                    String strTotalDamt = ReportHelper.getRightSpaceStr(String.valueOf(totalDamt), 22);
                    String strTotalCcunt = ReportHelper.getRightSpaceStr(String.valueOf(totalCcunt), 18);
                    String strTotalCamt = ReportHelper.getRightSpaceStr(String.valueOf(totalCamt), 22);
                    bw.write(title7 + strTotalDcunt + strTotalDamt + strTotalCcunt + strTotalCamt);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(footer);
                    bw.newLine();
                    //��ҳ�����
                    glCurCnt++;
                    if (glCurCnt % 3 == 0) {
                        bw.write("\f");
                    }
                }
                bw.flush();
                bw.close();

            }
             MessageUtil.addInfo("��Ŀ�սᵥ�������ɳɹ���");
        } catch (Exception ex) {
            MessageUtil.addError("��Ŀ�սᵥ�������ɴ���");
            ex.getMessage();
            ex.printStackTrace();
            logger.error("��Ŀ�սᵥ������ݴ���");
        } finally {
            session.close();
            return null;
        }


    }

    /**
     * �ֻ���3
     * �ı���ӡ
     * remark:
     *   ��10��    ժҪ10     �跽������15   ����������15       ���15     ����10
     *                                                                ����������м�+�����ո�*/
    public String ExcelActstm3() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                  �ֻ���";
            String plan = "-----------------------------------------------------------------------------";
            String contTitle = "  ��Ʊ��    ժҪ       �跽������     ����������         ���       ����     ";
            String contCQY =   "          ��ǰҳ                                  ";
            //ҳ��
            String curPageBal = "          ��ҳ��";
            String curDate = "�������ڣ�";
            int title0Cnt1 = 45; //��ͷ���ݵ�һ������ֽ���
            //����������ȣ��ֽ���)
            int contCnt1 = 10;
            int contCnt2 = 10;
            int contCnt3 = 15;
            int contCnt4 = 10;
            int footerCnt1 = 15;
            int footerCnt2 = 20;

            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
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
                        MessageUtil.addInfo("·������ʧ�ܣ�");
                        logger.error("·������ʧ�ܣ�");
                    }
                }
                File file = new File(strPath+"�ֻ���.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                int pageCount = 0;
                int pageRowCnt = 50;   //25������(+���б�־��+��ͷ+ҳ�� �ܹ�61��/ҳ)
                int pageLine = 10;     //ÿ��10�мӺ���
                int seq = 1; //�˻����
                ActlgcMapper stmmap = session.getMapper(ActlgcMapper.class);      //20110324 �滻Ϊactlgc��
                List<ActstmForActnum> stmnumLst = stmmap.selectForActnum(orgidt);   //ȡ���˻�������Ϣ--�˻���
                Iterator it1 = stmnumLst.iterator();
                while (it1.hasNext()) {
                    ActstmForActnum stmnum = (ActstmForActnum)it1.next();
                    String curnmc = stmnum.getCurnmc();
                    String actnam = stmnum.getActnam();
                    String glcnam = stmnum.getGlcnam();
                    String depnam = stmnum.getDepnam();
                    String actacn = stmnum.getOldacn();
                    String sysidt = stmnum.getSysidt();
                    String cusidt = stmnum.getCusidt();
                    String apcode = stmnum.getApcode();
                    String curcde = stmnum.getCurcde();
                    double legbal = (double)stmnum.getLegbal()/100;
                    bw.write(title0);
                    bw.newLine();
                    bw.write("��ţ�" + seq);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("���ң�" + curnmc, title0Cnt1)+"��Ŀ��" + glcnam);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title0Cnt1) + "���ţ�" + depnam);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("������" + actnam, title0Cnt1) + "�˺ţ�" + actacn);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(contTitle);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(legbal), contCnt3));
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //�ֻ�������
                    List<ActstmForStmCotent> stmcont = stmmap.selectForStmContent(sysidt,orgidt,cusidt,apcode,curcde);
                    Iterator it2 = stmcont.iterator();
                    int j = 0;       //�ֻ�������
                    while (it2.hasNext()) {
                        ActstmForStmCotent stmCont2 = (ActstmForStmCotent) it2.next();
                        String cpcode = ReportHelper.getLeftSpaceStr(stmCont2.getCpcode(), contCnt1);
                        String furinf = ReportHelper.getLeftSpaceStr(stmCont2.getFurinf(), contCnt2);
                        double dtxnamt = (double) (stmCont2.getDtxnamt() * -1)/100;
                        double ctxnamt = (double)stmCont2.getCtxnamt()/100;
                        double actbal = (double)stmCont2.getActbal()/100;
                        String spaDtxnamt = ReportHelper.getRightSpaceStr(df0.format(dtxnamt), contCnt3);
                        String spaCtxnamt = ReportHelper.getRightSpaceStr(df0.format(ctxnamt), contCnt3);
                        String spaActbal = ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3);
                        Date datedat = stmCont2.getValdat();
                        SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");
                        String valdat = ReportHelper.getLeftSpaceStr(dateformt.format(datedat), contCnt4);
                        bw.write(cpcode + furinf + spaDtxnamt + spaCtxnamt + spaActbal + " " + valdat);
                        bw.newLine();
                        if ((j+1) == stmcont.size()) {
                            //���ҳ��
                            bw.write(plan);
                            bw.newLine();
                            bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                    ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "��" + (pageCount+1) + "ҳ");
                            bw.newLine();
                        } else if ((j!=0) && ((j+1)%pageRowCnt==0)) {
                            //���ҳ��
                            bw.write(plan);
                            bw.newLine();
                            bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                    ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "��" + (pageCount+1) + "ҳ");
                            bw.newLine();
                            pageCount++; //ҳ��
                            //��ҳ��
                            bw.write("\f");
                            //������ͷ
                            bw.write(title0);
                            bw.newLine();
                            bw.write("��ţ�" + seq);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("���ң�" + curnmc, title0Cnt1)+"��Ŀ��" + glcnam);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title0Cnt1) + "���ţ�" + depnam);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("������" + actnam, title0Cnt1) + "�˺ţ�" + actacn);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(contTitle);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3));
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                        } else if (j !=0 && ((j+1) % pageLine == 0)) {
                            bw.write(plan);
                            bw.newLine();
                        }
                        j++;
                    }

                    //���+1
                    seq++;
                    pageCount++; //ҳ��
                    //��ҳ��
                    bw.write("\f");
                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addInfo("�ֻ��˱������ɳɹ���");
        } catch (Exception ex) {
            MessageUtil.addError("�ֻ��˱������ɴ���");
            ex.getMessage();
            ex.printStackTrace();
            logger.error("�ֻ��˳�����ݴ���");
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * ���˵�*/
    public void ExcelActnsm() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                  ���˵�";
            String plan = "-----------------------------------------------------------------------------";
            String contTitle = "  ��Ʊ��    ժҪ       �跽������     ����������         ���       ����     ";
            String contCQY = "          ��ǰҳ                                  ";
            //ҳ��
            String curPageBal = "          ��ҳ��";
            String curDate = "�������ڣ�";
            int title0Cnt1 = 45; //��ͷ���ݵ�һ������ֽ���
            //����������ȣ��ֽ���)
            int contCnt1 = 10;
            int contCnt2 = 10;
            int contCnt3 = 15;
            int contCnt4 = 10;
            int footerCnt1 = 15;
            int footerCnt2 = 20;
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
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
                        MessageUtil.addError("·������ʧ�ܣ�");
                        logger.error("·������ʧ�ܣ�");
                    }
                }
                File file = new File(strPath + "���˵�.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                int pageCount = 0;
                int pageRowCnt = 50;   //25������(+���б�־��+��ͷ+ҳ�� �ܹ�61��/ҳ)
                int pageLine = 10;     //ÿ��10�мӺ���
                int seq = 1; //�˻����
                ActnsmMapper nsmmap = session.getMapper(ActnsmMapper.class);
                List<ActstmForActnum> stmnumLst = nsmmap.selectForActnum(orgidt);   //ȡ���˻�������Ϣ--�˻���
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
                    double legbal = (double) stmnum.getLegbal() / 100;
                    bw.write(title0);
                    bw.newLine();
                    bw.write("��ţ�" + seq);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("���ң�" + curnmc, title0Cnt1) + "��Ŀ��" + glcnam);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title0Cnt1) + "���ţ�" + depnam);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("������" + actnam, title0Cnt1) + "�˺ţ�" + actacn);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(contTitle);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(legbal), contCnt3));
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //�ֻ�������
                    List<ActstmForStmCotent> stmcont = nsmmap.selectForStmContent(sysidt, orgidt, cusidt, apcode, curcde);
                    Iterator it2 = stmcont.iterator();
                    int j = 0;       //�ֻ�������
                    while (it2.hasNext()) {
                        ActstmForStmCotent stmCont2 = (ActstmForStmCotent) it2.next();
                        String cpcode = ReportHelper.getLeftSpaceStr(stmCont2.getCpcode(), contCnt1);
                        String furinf = ReportHelper.getLeftSpaceStr(stmCont2.getFurinf(), contCnt2);
                        double dtxnamt = (double) (stmCont2.getDtxnamt() * -1) / 100;
                        double ctxnamt = (double) stmCont2.getCtxnamt() / 100;
                        double actbal = (double) stmCont2.getActbal() / 100;
                        String spaDtxnamt = ReportHelper.getRightSpaceStr(df0.format(dtxnamt), contCnt3);
                        String spaCtxnamt = ReportHelper.getRightSpaceStr(df0.format(ctxnamt), contCnt3);
                        String spaActbal = ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3);
                        Date datedat = stmCont2.getValdat();
                        SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");
                        String valdat = ReportHelper.getLeftSpaceStr(dateformt.format(datedat), contCnt4);
                        bw.write(cpcode + furinf + spaDtxnamt + spaCtxnamt + spaActbal + " " + valdat);
                        bw.newLine();

                        if ((j + 1) == stmcont.size()) {
                            //���ҳ��
                            bw.write(plan);
                            bw.newLine();
                            bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                    ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "��" + (pageCount + 1) + "ҳ");
                            bw.newLine();
                        } else if ((j != 0) && ((j + 1) % pageRowCnt == 0)) {
                            //���ҳ��
                            bw.write(plan);
                            bw.newLine();
                            bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                    ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "��" + (pageCount + 1) + "ҳ");
                            bw.newLine();
                            pageCount++; //ҳ��
                            //��ҳ��
                            bw.write("\f");
                            //������ͷ
                            bw.write(title0);
                            bw.newLine();
                            bw.write("��ţ�" + seq);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("���ң�" + curnmc, title0Cnt1) + "��Ŀ��" + glcnam);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title0Cnt1) + "���ţ�" + depnam);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("������" + actnam, title0Cnt1) + "�˺ţ�" + actacn);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(contTitle);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3));
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                        } else if (j !=0 && ((j+1) % pageLine == 0)) {
                            bw.write(plan);
                            bw.newLine();
                        }
                        j++;
                    }

                    //���+1
                    seq++;
                    pageCount++; //ҳ��
                    //��ҳ��
                    bw.write("\f");
                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addError("���˵��������ɳɹ���");
        } catch (Exception ex) {
            MessageUtil.addError("���˵��������ɴ���");
            ex.getMessage();
            ex.printStackTrace();
            logger.error("���˵�������ݴ���");
        } finally {
            session.close();
        }
    }

    /**
     * ����2*/
    public String ExcelActglf2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            SimpleDateFormat dfgldate = new SimpleDateFormat("d");  //ȡ������day
            String yearmonth = SystemService.getBizDateYM();
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                 ����";
            String plan = "--------------------------------------------------------------------------------";
            int pageLine = 10;   //ÿ��10��+����
            int title01Len = 25;
            int title02Len = 20;
            int titleCont2Len = 15;
            String titleCont0 = "  ����    "; //10
            String titleCont1 = "                      ������            ";
            String titleCont2 = "             ���             ";
            String titleCont11 = "      �跽     ";
            String titleCont12 = "      ����     ";
            String ContSpace = "                              ";
            String footerCont = "          ��ƣ�         ���ϣ�         �Ʊ�         ��ӡ���ڣ�"+reportdate;
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
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
                        MessageUtil.addInfo("·������ʧ�ܣ�");
                        logger.error("·������ʧ�ܣ�");
                    }
                }
                File file = new File(strPath+"����.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                int pageCount = 1;
               ActglfMapper glfmap = session.getMapper(ActglfMapper.class);
                List<ActglfForGlcnum> glcnumLst = glfmap.selectForGlcnum(orgidt);
                Iterator it1 =glcnumLst.iterator();
                while (it1.hasNext()) {
                    ActglfForGlcnum glcnum = (ActglfForGlcnum)it1.next();
                    String curcde = glcnum.getCurcde();
                    String curnmc = glcnum.getCurnmc();
                    String glcode = glcnum.getGlcode();
                    String glcnam = glcnum.getGlcnam();
                    //������ͷ
                    bw.write(title0);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("������" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("���ڣ�" + yearmonth, title02Len) + "ҳ����"+pageCount);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("�ұ�" + curnmc, title01Len) + ReportHelper.getLeftSpaceStr("��Ŀ�ţ�" + glcode, title02Len) + "��Ŀ���ƣ�"+glcnam);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(titleCont1 + titleCont2);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(titleCont0 + titleCont11 + titleCont12 + titleCont11 + titleCont12);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    List<Actglf> glfLst = glfmap.selectForGlcCont(orgidt,glcode,curcde);
                    //���ۼ�
                    double dramntTotal = 0;
                    double cramntTotal = 0;
                    double drbalaTotal = 0;
                    double crbalaTotal = 0;
                    int glfLstsize = 0;
                    double drbala = 0;
                    double crbala = 0;
                    int j = 0;
                    Iterator itGlf = glfLst.iterator();
                    while(itGlf.hasNext()) {
                        Actglf glf = (Actglf) itGlf.next();
                        String glDay = dfgldate.format(glf.getGldate());
                        double dramnt = (double) (glf.getDramnt() * -1)/100;
                        double cramnt = (double)glf.getCramnt()/100;
                        drbala = (double) (glf.getDrbala() * -1)/100;
                        crbala = (double)glf.getCrbala()/100;
                        String dramntStr = ReportHelper.getRightSpaceStr(df0.format(dramnt), titleCont2Len);
                        String cramntStr = ReportHelper.getRightSpaceStr(df0.format(cramnt), titleCont2Len);
                        String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), titleCont2Len);
                        String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), titleCont2Len);
                        bw.write("    "+ ReportHelper.getLeftSpaceStr(glDay, 6) + dramntStr + cramntStr + drbalaStr + crbalaStr);
                        bw.newLine();
                        j++;
                        //�Ӻ���
                        if (j !=0 && (j % pageLine == 0)) {
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
                    bw.write(plan);
                    bw.newLine();
                    bw.write("  ���ۼ�  " + dramntTotalStr + cramntTotalStr + drbalaStr + crbalaStr);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(footerCont);
                    //��ҳ
                    pageCount++;
                    bw.write("\f");
                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addInfo("���˱������ɳɹ���");
        } catch (Exception ex) {
            MessageUtil.addError("���˱������ɴ���");
            ex.getMessage();
            ex.printStackTrace();
            logger.error("���˳�����ݴ���");
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * �ռƱ�2*/
    public String ExcelActcgl2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                                                             " +
                    "           �ռƱ�";
            String plan = "-------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------" +
                    "-------------";
            int title0Len = 40;
            int title1Len = 68;
            int cont0Len = 6;
            int cont1Len = 34;
            int cont2Len = 20;
            int cont3Len = 8;
            String titleCont0 = " ��Ŀ ";
            String titleCont1 = "        ��Ŀ����                  ";
            String titleCont2 = "    ���ս跽���    ";
            String titleCont3 = "      ���մ������  ";
            String titleCont4 = "�跽����";
            String titleCont5 = "         �跽������ ";
            String titleCont6 = "��������";
            String titleCont7 = "         ���������� ";
            String titleCont8 = "     ���ս跽���   ";
            String titleCont9 = "    ���մ������    ";
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActcgl();
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
                        MessageUtil.addInfo("·������ʧ�ܣ�");
                        logger.error("·������ʧ�ܣ�");
                    }
                }
                File file = new File(strPath+"�ռƱ�.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                //��ñұ�����
                ActccyMapper ccymap = session.getMapper(ActccyMapper.class);
                List<Actccy> ccyLst = ccymap.selectForActcgl(orgidt,ACEnum.RECTYP_D.getStatus());
                Iterator itCcy = ccyLst.iterator();
                int ccyCnt = 0;
                while (itCcy.hasNext()) {
                    Actccy ccy = (Actccy)itCcy.next();
                    String curcde = ccy.getCurcde();
                    String curnmc = ccy.getCurnmc();
                    //��ҳ
                    if (ccyCnt !=0 && (ccyCnt%2==0)) {
                        bw.write("\f");
                    } else if (ccyCnt !=0) {
                        bw.newLine();
                        bw.newLine();
                    }
                    bw.write(title0);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("�ұ�" + curnmc, title0Len) + ReportHelper.getLeftSpaceStr("���ڣ�" + reportdate, title1Len) +
                    "��λ��Ԫ");
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //��ͷ
                    bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5 + titleCont6 +
                    titleCont7 + titleCont8 + titleCont9);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //�������
                    ActcglMapper cglmap = session.getMapper(ActcglMapper.class);
                    List<ActcglForglc> cgLst = cglmap.selectForglcnam(ACEnum.RECTYP_D.getStatus(),orgidt,curcde);
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
                        ActcglForglc cfg = (ActcglForglc)itCgl.next();
                        String glcode = ReportHelper.getLeftSpaceStr(cfg.getGlcode(), cont0Len);
                        String glcnam = ReportHelper.getLeftSpaceStr(cfg.getGlcnam(), cont1Len);
                        double dlsbal = (double) (cfg.getDlsbal() * -1)/100;
                        double clsbal = (double) cfg.getClsbal()/100;
                        int drcunt = cfg.getDrcunt();
                        double dramnt = (double) (cfg.getDramnt() * -1)/100;
                        int crcunt = cfg.getCrcunt();
                        double cramnt = (double) cfg.getCramnt()/100;
                        double drbala = (double) (cfg.getDrbala() * -1)/100;
                        double crbala = (double) cfg.getCrbala()/100;
                        String dlsbalStr = ReportHelper.getRightSpaceStr(df0.format(dlsbal), cont2Len);
                        String clsbalStr = ReportHelper.getRightSpaceStr(df0.format(clsbal), cont2Len);
                        String drcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(drcunt), cont3Len);
                        String dramntStr = ReportHelper.getRightSpaceStr(df0.format(dramnt), cont2Len);
                        String crcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(crcunt), cont3Len);
                        String cramntStr = ReportHelper.getRightSpaceStr(df0.format(cramnt), cont2Len);
                        String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), cont2Len);
                        String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), cont2Len);
                        //��������
                        bw.write(glcode + glcnam + dlsbalStr + clsbalStr + drcuntStr + dramntStr + crcuntStr + cramntStr +
                                drbalaStr + crbalaStr);
                        bw.newLine();
                        //�ϼ�
                        dlsbalTotal += dlsbal;
                        clsbalTotal += clsbal;
                        drcuntTotal += drcunt;
                        dramntTotal += dramnt;
                        crcuntTotal += crcunt;
                        cramntTotal += cramnt;
                        drbalaTotal += drbala;
                        crbalaTotal += crbala;
                    }
                    //�ϼ�
                    String totalStr = "      " + ReportHelper.getLeftSpaceStr("�ϼ�", cont1Len);
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
                    //�ұ����
                    ccyCnt++;

                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addInfo("�ռƱ������ɳɹ���");
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
            logger.error("�ռƱ������ݴ���");
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * �¼Ʊ�2*/
    public String ExcelMonth2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                                                             " +
                    "           �¼Ʊ�";
            String plan = "-------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------" +
                    "-------------";
            int title0Len = 40;
            int title1Len = 68;
            int cont0Len = 6;
            int cont1Len = 34;
            int cont2Len = 20;
            int cont3Len = 8;
            String titleCont0 = " ��Ŀ ";
            String titleCont1 = "        ��Ŀ����                  ";
            String titleCont2 = "    ���½跽���    ";
            String titleCont3 = "      ���´������  ";
            String titleCont4 = "�跽����";
            String titleCont5 = "         �跽������ ";
            String titleCont6 = "��������";
            String titleCont7 = "         ���������� ";
            String titleCont8 = "     ���½跽���   ";
            String titleCont9 = "    ���´������    ";
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActcgl();
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
                        MessageUtil.addInfo("·������ʧ�ܣ�");
                        logger.error("·������ʧ�ܣ�");
                    }
                }
                File file = new File(strPath+"�¼Ʊ�.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                //��ñұ�����
                ActccyMapper ccymap = session.getMapper(ActccyMapper.class);
                List<Actccy> ccyLst = ccymap.selectForActcgl(orgidt,ACEnum.RECTYP_M.getStatus());
                Iterator itCcy = ccyLst.iterator();
                int ccyCnt = 0;
                while (itCcy.hasNext()) {
                    Actccy ccy = (Actccy)itCcy.next();
                    String curcde = ccy.getCurcde();
                    String curnmc = ccy.getCurnmc();
                    //��ҳ
                    if (ccyCnt !=0 && (ccyCnt%2==0)) {
                        bw.write("\f");
                    } else if (ccyCnt !=0) {
                        bw.newLine();
                        bw.newLine();
                    }
                    bw.write(title0);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("�ұ�" + curnmc, title0Len) + ReportHelper.getLeftSpaceStr("���ڣ�" + reportdate, title1Len) +
                    "��λ��Ԫ");
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //��ͷ
                    bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5 + titleCont6 +
                    titleCont7 + titleCont8 + titleCont9);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //�������
                    ActcglMapper cglmap = session.getMapper(ActcglMapper.class);
                    List<ActcglForglc> cgLst = cglmap.selectForglcnam(ACEnum.RECTYP_M.getStatus(),orgidt,curcde);
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
                        ActcglForglc cfg = (ActcglForglc)itCgl.next();
                        String glcode = ReportHelper.getLeftSpaceStr(cfg.getGlcode(), cont0Len);
                        String glcnam = ReportHelper.getLeftSpaceStr(cfg.getGlcnam(), cont1Len);
                        double dlsbal = (double) (cfg.getDlsbal() * -1)/100;
                        double clsbal = (double)cfg.getClsbal()/100;
                        int drcunt = cfg.getDrcunt();
                        double dramnt = (double) (cfg.getDramnt() * -1)/100;
                        int crcunt = cfg.getCrcunt();
                        double cramnt = (double)cfg.getCramnt()/100;
                        double drbala = (double) (cfg.getDrbala() * -1)/100;
                        double crbala = (double)cfg.getCrbala()/100;
                        String dlsbalStr = ReportHelper.getRightSpaceStr(df0.format(dlsbal), cont2Len);
                        String clsbalStr = ReportHelper.getRightSpaceStr(df0.format(clsbal), cont2Len);
                        String drcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(drcunt), cont3Len);
                        String dramntStr = ReportHelper.getRightSpaceStr(df0.format(dramnt), cont2Len);
                        String crcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(crcunt), cont3Len);
                        String cramntStr = ReportHelper.getRightSpaceStr(df0.format(cramnt), cont2Len);
                        String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), cont2Len);
                        String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), cont2Len);
                        //��������
                        bw.write(glcode + glcnam + dlsbalStr + clsbalStr + drcuntStr + dramntStr + crcuntStr + cramntStr +
                                drbalaStr + crbalaStr);
                        bw.newLine();
                        //�ϼ�
                        dlsbalTotal += dlsbal;
                        clsbalTotal += clsbal;
                        drcuntTotal += drcunt;
                        dramntTotal += dramnt;
                        crcuntTotal += crcunt;
                        cramntTotal += cramnt;
                        drbalaTotal += drbala;
                        crbalaTotal += crbala;
                    }
                    //�ϼ�
                    String totalStr = "      " + ReportHelper.getLeftSpaceStr("�ϼ�", cont1Len);
                    String dlsbalTotalStr = ReportHelper.getRightSpaceStr(df0.format(dlsbalTotal), cont2Len);
                    String clsbalTotalStr = ReportHelper.getRightSpaceStr(df0.format(clsbalTotal), cont2Len);
                    String drcuntTotalStr = ReportHelper.getRightSpaceStr(String.valueOf(drcuntTotal), cont2Len);
                    String dramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(dramntTotal), cont2Len);
                    String crcuntTotalStr = ReportHelper.getRightSpaceStr(String.valueOf(crcuntTotal), cont2Len);
                    String cramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(cramntTotal), cont2Len);
                    String drbalaTotalStr = ReportHelper.getRightSpaceStr(df0.format(drbalaTotal), cont2Len);
                    String crbalaTotalStr = ReportHelper.getRightSpaceStr(df0.format(crbalaTotal), cont2Len);
                    bw.write(plan);
                    bw.newLine();
                    bw.write(totalStr + dlsbalTotalStr + clsbalTotalStr + drcuntTotalStr + dramntTotalStr + crcuntTotalStr
                            + cramntTotalStr + drbalaTotalStr + crbalaTotalStr);
                    bw.newLine();
                    //�ұ����
                    ccyCnt++;

                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addInfo("�¼Ʊ������ɳɹ���");
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
            logger.error("�¼Ʊ������ݴ���");
        } finally {
            session.close();
        }
        return null;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }
}