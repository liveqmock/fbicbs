package cbs.view.batch;


import cbs.common.IbatisManager;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActvhhMapper;
import cbs.repository.account.maininfo.dao.ActvthMapper;
import cbs.repository.account.maininfo.model.ActvchForGlDRpt;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.model.Actorg;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.model.Ptenudetail;
import cbs.repository.platform.model.PtenudetailExample;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * ��Ŀ�սᵥ.
 * User: Administrator ��hanghaiyu��
 * Date: 2011-3-17
 * Time: 15:02:33
 * �汾�޸ģ�2012-11-09   zhanrui  ���������ʽ��������
 */
@ManagedBean
@ViewScoped
public class OutputfilePdf implements Serializable {
    private static final Log logger = LogFactory.getLog(OutputfilePdf.class);
    private String nowDate = "";
    private String reportdate = "2014-11-14";

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;

    //ÿҳ���������
    private static int TABLE_NUM_PER_PAGE = 3;
    //ÿ����񶥶˱߾�����
    private static int TABLE_MARGIN_TOP = 2;
    //ÿ�����ײ��߾�����
    private static int TABLE_MARGIN_BOTTOM = 0;

    private ActorgMapper orgmap;
    private ActvhhMapper vhhmap;


    public String ExcelActvch() throws IOException {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        orgmap = session.getMapper(ActorgMapper.class);
        vhhmap = session.getMapper(ActvhhMapper.class);
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
        String title3 = "��   ��Ŀ   �� �跽���� ��     �跽���         �� �������� ��     �������         ����";
        String title4 = "���ֽ�      ��";
        String title5 = "��ת��      ��";
        String title6 = "������      ��";
        String title7 = "���ϼ�      ��";
        String nodtTitle = "         0��                  0.00��         0��                  0.00��";
        String footer = title2Cell1 + "���ˣ�              ���ˣ�                    �Ʊ�                         ";
        String topplan = "�������������ש����������������������������������ש�������������������������������������";
        String middleplan = "�������������贈���������贈���������������������贈���������贈����������������������";
        String bottomplan = "�������������ߩ����������ߩ����������������������ߩ����������ߩ�������������������������";

        List<Actorg> orgLst = orgmap.selectForActvhh();
        if (orgLst.size() == 0) {
            System.out.print("��Ŀ�սᵥ�������������ɡ�");
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
                    System.out.print("·������ʧ�ܣ�");
                    logger.error("·������ʧ�ܣ�");
                }
            }
            File file = new File("d:/" + "�սᵥ.pdf");
            System.out.println("======>"+strPath);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(fw);
            //��ȡ����
            int rowCnt = 0;
            List<ActvchForGlDRpt> glcurLst = vhhmap.selectForGlCur(orgidt,reportdate);
            int glCurCnt = 0;
            Iterator it1 = glcurLst.iterator();
            while (it1.hasNext()) {
                ActvchForGlDRpt glcur = (ActvchForGlDRpt) it1.next();
                String glcode = glcur.getGlcode();
                String curcde = glcur.getCurcde();
                String glcnam = glcur.getGlcnam();
                String curnmc = glcur.getCurnmc();
                titleOrg = ReportHelper.getLeftSpaceStr("������" + orgnam, 48);
                titleCurDate = ReportHelper.getLeftSpaceStr("���ڣ�" + reportdate, 20);
                titleGlc = ReportHelper.getLeftSpaceStr("��Ŀ��" + glcode + " " + glcnam, 48);
                titleCur = ReportHelper.getLeftSpaceStr("�ұ�" + curnmc, 20);


                for (int i = 0; i < TABLE_MARGIN_TOP; i++) {
                    bw.newLine();
                }

                bw.write(title1);
                bw.newLine();
                bw.write(titleOrg + titleCurDate);
                bw.newLine();
                bw.write(titleGlc + titleCur);
                bw.newLine();
                bw.write(topplan);
                bw.newLine();
                bw.write(title3);
                bw.newLine();
                rowCnt += 8;
                //��ȡ����
                List<ActvchForGlDRpt> gldrptLst = vhhmap.selectForGlDRpt(orgidt, curcde, glcode,reportdate);
                short totalvchatt = 0;
                for (ActvchForGlDRpt gld : gldrptLst) {
                    totalvchatt += gld.getVchatt();  //������
                }
                Iterator it2 = gldrptLst.iterator();
                int totalDcunt = 0;
                double totalDamt = 0;
                int totalCcunt = 0;
                double totalCamt = 0;
                int tempi = 0;
                while (it2.hasNext()) {
                    ActvchForGlDRpt gldrpt = (ActvchForGlDRpt) it2.next();
                    String rvslbl = gldrpt.getRvslbl();
                    int intDcun = Integer.parseInt(gldrpt.getDcunt());
                    double doubleDamt = Double.parseDouble(gldrpt.getDamt()) * -1 / 100;
                    int intCcunt = Integer.parseInt(gldrpt.getCcunt());
                    double doubleCamt = Double.parseDouble(gldrpt.getCamt()) / 100;
                    String dcunt = ReportHelper.getRightSpaceStr(df01.format(intDcun), 10) + "��";   //�跽����
                    String damt = ReportHelper.getRightSpaceStr(df0.format(doubleDamt), 22) + "��";     //�跽���
                    String ccunt = ReportHelper.getRightSpaceStr(df01.format(intCcunt), 10) + "��";   //��������
                    String camt = ReportHelper.getRightSpaceStr(df0.format(doubleCamt), 22) + "��";     //�������

                    if (tempi == 0 && rvslbl.equals("1")) {
                        bw.write(middleplan + totalvchatt);
                        bw.newLine();
                        bw.write(title4 + dcunt + damt + ccunt + camt + "��");
                    } else if (tempi == 0 && rvslbl.equals("2")) {
                        bw.write(middleplan + totalvchatt);
                        bw.newLine();
                        bw.write(title4 + nodtTitle + "��");
                        bw.newLine();
                        bw.write(middleplan);
                        bw.newLine();
                        bw.write(title5 + dcunt + damt + ccunt + camt + "ӡ");
                    } else
                        bw.write(title5 + dcunt + damt + ccunt + camt + "ӡ");
                    bw.newLine();
                    bw.write(middleplan);
                    bw.newLine();
                    totalDcunt += intDcun;
                    totalDamt += doubleDamt;
                    totalCcunt += intCcunt;
                    totalCamt += doubleCamt;
                    tempi++;
                }
                bw.write(title6 + nodtTitle + "��");
                bw.newLine();
                bw.write(middleplan);
                bw.newLine();
                String strTotalDcunt = ReportHelper.getRightSpaceStr(df01.format(totalDcunt), 10) + "��";
                String strTotalDamt = ReportHelper.getRightSpaceStr(df0.format(totalDamt), 22) + "��";
                String strTotalCcunt = ReportHelper.getRightSpaceStr(df01.format(totalCcunt), 10) + "��";
                String strTotalCamt = ReportHelper.getRightSpaceStr(df0.format(totalCamt), 22) + "��";
                bw.write(title7 + strTotalDcunt + strTotalDamt + strTotalCcunt + strTotalCamt);
                bw.newLine();
                bw.write(bottomplan);
                bw.newLine();
                bw.write(footer);
                bw.newLine();

                //��ҳ�����
                glCurCnt++;
                if (glCurCnt % TABLE_NUM_PER_PAGE == 0) {
                    bw.write("\f");
                }else{
                    for (int i = 0; i < TABLE_MARGIN_BOTTOM; i++) {
                        bw.newLine();
                    }
                }
            }
            bw.flush();
            bw.close();

        }
        System.out.print("��Ŀ�սᵥ�������ɳɹ���");
        logger.info("��Ŀ�սᵥ�������ɳɹ���");
        return null;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public ActvhhMapper getVhhmap() {
        return vhhmap;
    }

    public void setVhhmap(ActvhhMapper vhhmap) {
        this.vhhmap = vhhmap;
    }

    public ActorgMapper getOrgmap() {
        return orgmap;
    }

    public void setOrgmap(ActorgMapper orgmap) {
        this.orgmap = orgmap;
    }
}
