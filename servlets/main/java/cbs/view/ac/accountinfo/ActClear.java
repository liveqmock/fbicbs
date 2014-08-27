package cbs.view.ac.accountinfo;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.DataFormater;
import cbs.common.utils.MessageUtil;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.dao.ActblhMapper;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.dao.ActobfMapper;
import cbs.repository.account.maininfo.model.*;
import cbs.repository.code.dao.ActaniMapper;
import cbs.repository.code.dao.ActapcMapper;
import cbs.repository.code.dao.ActccyMapper;
import cbs.repository.code.dao.ActirtMapper;
import cbs.repository.code.model.*;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import pub.platform.security.OperatorManager;
import pub.print.pdf.PdfReportFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-10
 * Time: 10:28:03
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "actclear")
@RequestScoped
public class ActClear {
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private static final Log logger = LogFactory.getLog(ActClear.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private Actani actani = new Actani();   //�˺���Ϣ��(�¾��˺Ŷ��ձ�)
    private Actact act;
    private Actoac oac;
    private final String aniOrgidt = "010";
    private String bizStrDate;
    private Date bizDateDate;
    private BigDecimal wkCratsf;
    private long wkCraccm;
    private String dinIrtval;   //�跽����
    private String cinIrtval;   //��������
    private short ccyDecpos;   //����λ��
    private String incActnum;   //��Ϣ�����˺�
    private String expActnum;    //��Ϣ֧���˺�


    public String onBtnClearClick() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActaniMapper animap = session.getMapper(ActaniMapper.class);
        try {
            this.actani = animap.selectByOldacn(actani.getOldacn(), this.aniOrgidt);
            if (actani == null) {
                session.commit();
                MessageUtil.addInfo("M104");
                return null;
            }
            ActobfMapper obfmap = session.getMapper(ActobfMapper.class);
            Actobf obf = obfmap.selectByPKForlock(ACEnum.SYSIDT_AC.getStatus(), actani.getOrgidt(), actani.getCusidt(),
                    actani.getApcode(), actani.getCurcde());
            if (obf == null) {
                session.commit();
                MessageUtil.addInfo("M104");
                return null;
            }
            if (!obf.getRecsts().trim().equals("")) {
                session.commit();
                MessageUtil.addInfo("M405");
                return null;
            }
            if (obf.getFrzsts().equals(ACEnum.FRZSTS_NODRAW.getStatus())) {
                session.commit();
                MessageUtil.addInfo("M409");
                return null;
            }
            //��ȡactoac��
            ActoacMapper oacmap = session.getMapper(ActoacMapper.class);
            ActoacExample oacexamp = new ActoacExample();
            oacexamp.createCriteria().andOrgidtEqualTo(actani.getOrgidt()).andCusidtEqualTo(actani.getCusidt())
                    .andApcodeEqualTo(actani.getApcode()).andCurcdeEqualTo(actani.getCurcde());
            List<Actoac> oacList = oacmap.selectByExample(oacexamp);
            oac = oacList.get(0);
            if (oac == null) {
                session.commit();
                MessageUtil.addInfo("M103");
                return null;
            }
            getBisDate();
            if (oac.getOpndat().equals(this.bizDateDate)) {
                session.commit();
                MessageUtil.addInfo("M212");
                return null;
            }
            if (oac.getOacflg().equals(ACEnum.OACFLG_OPEN.getStatus()) && oac.getRecsts().equals(ACEnum.RECSTS_OAC_VALID.getStatus())) {
                session.commit();
                MessageUtil.addInfo("M212");
                return null;
            }
            //��ȡactact��
            ActactMapper actmap = session.getMapper(ActactMapper.class);
            ActactExample actexamp = new ActactExample();
            actexamp.createCriteria().andSysidtEqualTo(ACEnum.SYSIDT_AC.getStatus()).andOrgidtEqualTo(actani.getOrgidt())
                    .andCusidtEqualTo(actani.getCusidt()).andApcodeEqualTo(actani.getApcode()).andCurcdeEqualTo(actani.getCurcde());

            List<Actact> actList = actmap.selectByExample(actexamp);
            act = actList.get(0);
            if (act == null) {
                session.commit();
                MessageUtil.addInfo("M103");
                return null;
            }
            if (act.getCinrat().equals("999")) {
                double cratsf = Double.parseDouble(act.getCratsf().toString());
                if (act.getCurcde().equals("001")) {
                    this.wkCratsf = act.getCratsf().divide(new BigDecimal(1000)).divide(new BigDecimal(30));
                } else {
                    this.wkCratsf = act.getCratsf().divide(new BigDecimal(100)).divide(new BigDecimal(360));
                }
            } else {
                String irtkd1 = act.getCinrat().substring(0, 1);
                String irtkd2 = act.getCinrat().substring(1, 3);
                ActirtMapper irtmap = session.getMapper(ActirtMapper.class);
                ActirtExample irtexamp = new ActirtExample();
                irtexamp.createCriteria().andCurcdeEqualTo(act.getCurcde()).andIrtkd1EqualTo(irtkd1).andIrtkd2EqualTo(irtkd2)
                        .andCurflgEqualTo("1");
                List<Actirt> irtList = irtmap.selectByExample(irtexamp);
                Actirt irt = irtList.get(0);
                if (irt == null) {
                    logger.error("SELECT FROM ACTIRT ERROR!!!");
                    logger.error("CURCDE =  " + act.getCurcde());
                    logger.error("IRTRAT = " + act.getCinrat());
                    session.commit();
                    MessageUtil.addInfo("M107");
                    return null;
                } else {
                    String trmunt = irt.getTrmunt();
                    double irtval = Double.parseDouble(irt.getIrtval().toString());
                    short irttrm = irt.getIrttrm();
                    if (trmunt.equals("Y")) {
                        this.wkCratsf = irt.getIrtval().divide(new BigDecimal(irttrm).multiply(new BigDecimal(100)), 15, BigDecimal.ROUND_HALF_UP);
                        //this.wkCratsf = irtval / (irttrm * 100);
                    } else if (trmunt.equals("M")) {
                        this.wkCratsf = irt.getIrtval().divide(new BigDecimal(irttrm).multiply(new BigDecimal(1000)), 6, BigDecimal.ROUND_HALF_UP);
                        //this.wkCratsf = irtval / (irttrm * 1000);
                    } else if (trmunt.equals("D")) {
                        this.wkCratsf = irt.getIrtval().divide(new BigDecimal(irttrm).multiply(new BigDecimal(1000)), 6, BigDecimal.ROUND_HALF_UP);
                        //this.wkCratsf = irtval / (irttrm * 1000);
                    }
                    act.setCratsf(irt.getIrtval());
                }
            }
            if (act.getApcode().equals("8272") && act.getCurcde().equals("001")) {
                this.wkCraccm = 0;
                ActblhMapper blhmap = session.getMapper(ActblhMapper.class);
                this.wkCraccm = blhmap.selectCraccm(act.getOrgidt(), act.getCusidt(), act.getApcode(), act.getCurcde(), act.getLintdt());
            }
            //todo 5000
            nomalPrecess(session);
            return "accountClearPrnt";
        } catch (Exception e) {
            session.rollback();
            MessageUtil.addInfo("�����ѯʧ��");
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    /*5000-NORMAL-PROCESS-RTN�Ժ�*/

    private void nomalPrecess(SqlSession session) throws Exception {
        ActccyMapper ccymap = session.getMapper(ActccyMapper.class);
        Actccy ccy = ccymap.selectByPrimaryKey(act.getCurcde());
        this.ccyDecpos = ccy.getDecpos();
        ccy.getDecpos();
        if (ccy != null) {
            if (!act.getCurcde().equals("001")) {
                ActblhMapper blhmap = session.getMapper(ActblhMapper.class);
                List<Actblh> blhList = blhmap.selectCompareErydat(act.getOrgidt(), act.getCusidt(), act.getApcode(), act.getCurcde(), act.getLintdt());
            }
            if (act.getCurcde().equals("001")) {
                if (!act.getApcode().equals("8272")) {
                    this.dinIrtval = getIrtval(act.getDinrat(), session);      //��ȡ�跽����ֵ
                    this.cinIrtval = getIrtval(act.getCinrat(), session);      //��ȡ�跽����ֵ
                    getincexpActnum(session);                                    //��ȡ��Ϣ����֧���˺�
                    //���������Ϣ
                    writeClearFilePDF();
                }
            }
        } else {
            session.commit();
            MessageUtil.addInfo("M105");
        }
    }

    private void writeClearFilePDF() throws Exception {
        String title = "���ڴ����Ϣ��";
        float[] widths = {140f, 140f, 140f, 140f};// ���ñ������Լ��п�
        String[] columns = {"","","",""};
        String[] fields = null;
        int[] aligns = new int[]{Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT, Element.ALIGN_LEFT};
        String bizDate = "���ڣ�" + bizStrDate;
        String[] afterTitleMsgs = new String[]{bizDate};
        int[] msgAligns = new int[]{Element.ALIGN_CENTER};
        String[] lastMsgs = null;
        Rectangle pageSize = new Rectangle(241 * 72 / 25.4f, 250 * 72 / 25.4f);
        List<ArrayList> dataList=getDataList();
        PdfReportFile pdfReport = new PdfReportFile(title, afterTitleMsgs, msgAligns, columns, fields, aligns, pageSize, widths, dataList);
        //save
        pdfReport.savePDFfile();

    }

    private ArrayList<ArrayList> getDataList() {
        ArrayList<ArrayList> dataAryList = new ArrayList<ArrayList>();
        ArrayList<String> dataList1 = new ArrayList<String>();
        dataList1.add(0,"��  �ţ�");
        dataList1.add(1,actani.getOldacn());
        dataList1.add(2,"��  ����");
        dataList1.add(3,act.getActnam());
        dataAryList.add(0,dataList1);

        ArrayList<String> dataList2 = new ArrayList<String>();
        dataList2.add(0,"��  �");
        dataList2.add(1, DataFormater.formatNum(this.ccyDecpos, act.getBokbal()));
        dataList2.add(2,"");
        dataList2.add(3,"");
        dataAryList.add(1,dataList2);

        ArrayList<String> dataList3 = new ArrayList<String>();
        dataList3.add(0,"��Ϣ�գ�");
        dataList3.add(1,sdf.format(act.getLintdt()));
        dataList3.add(2,"ֹϢ�գ�");
        dataList3.add(3,this.bizStrDate);
        dataAryList.add(2,dataList3);

        ArrayList<String> dataList4 = new ArrayList<String>();
        dataList4.add(0,"�跽���ʣ�");
        dataList4.add(1,this.dinIrtval+"%");
        dataList4.add(2,"�������ʣ�");
        dataList4.add(3,this.cinIrtval+"%");
        dataAryList.add(3,dataList4);

        String draccm = DataFormater.formatNum(this.ccyDecpos, act.getDraccm());
        String craccm = DataFormater.formatNum(this.ccyDecpos, act.getCraccm());
        ArrayList<String> dataList5 = new ArrayList<String>();
        dataList5.add(0,"�跽������");
        dataList5.add(1,draccm);
        dataList5.add(2,"����������");
        dataList5.add(3,craccm);
        dataAryList.add(4,dataList5);

        ArrayList<String> dataList6 = new ArrayList<String>();
        //��Ϣ���� ���� * ����
        //double dbdacint = Double.parseDouble(StringUtils.isEmpty(this.dinIrtval.trim()) ? "0" : this.dinIrtval) * act.getDraccm();
        //double dbcacint = Double.parseDouble(StringUtils.isEmpty(this.cinIrtval.trim()) ? "0" : this.cinIrtval) * act.getCraccm();

        //20120716 zhanrui
        BigDecimal dbdacint = this.wkCratsf.multiply(new BigDecimal(act.getDraccm()));
        BigDecimal dbcacint = this.wkCratsf.multiply(new BigDecimal(act.getCraccm()));

        String dacint = DataFormater.formatNum(this.ccyDecpos, dbdacint.doubleValue());
        String cacint = DataFormater.formatNum(this.ccyDecpos, dbcacint.doubleValue());
        dataList6.add(0,"�跽��Ϣ��");
        dataList6.add(1,dacint);
        dataList6.add(2,"������Ϣ��");
        dataList6.add(3,cacint);
        dataAryList.add(5,dataList6);

        ArrayList<String> dataList7 = new ArrayList<String>();
        String totalacint = DataFormater.formatNum(this.ccyDecpos, dbcacint.subtract(dbdacint).doubleValue());   //��Ϣ�ϼ�
        String amount = DataFormater.formatNum(this.ccyDecpos, new BigDecimal(act.getBokbal()).add(dbcacint).subtract(dbdacint).doubleValue());  //��Ϣ�ϼ�
        dataList7.add(0,"��Ϣ�ϼƣ�");
        dataList7.add(1,totalacint);
        dataList7.add(2,"��Ϣ�ϼƣ�");
        dataList7.add(3,amount);
        dataAryList.add(6,dataList7);

        ArrayList<String> dataList8 = new ArrayList<String>();
        dataList8.add(0, "�����˺ţ�");
        dataList8.add(1,"");
        dataList8.add(2, "");
        dataList8.add(3,"");
        dataAryList.add(7,dataList8);

        ArrayList<String> dataList9 = new ArrayList<String>();
        dataList9.add(0,"��Ϣ�����˺ţ�");
        dataList9.add(1,incActnum);
        dataList9.add(2,"��Ϣ֧���˺ţ�");
        dataList9.add(3,expActnum);
        dataAryList.add(8,dataList9);

        ArrayList<String> dataList10 = new ArrayList<String>();
        dataList10.add(0,"��ע��");
        dataList10.add(1, "");
        dataList10.add(2,"");
        dataList10.add(3,"");
        dataAryList.add(9,dataList10);

        return dataAryList;
    }

    //����ļ�

    private void writeClearFile() throws IOException {
        OperatorManager opm = OnlineService.getOperatorManager();
        //��ӡƾ֤
        String fileName = opm.getOperatorId() + ".txt";
        String strPath = PropertyManager.getProperty("REPEAT_PRNTPATH");

        //�����ļ�
        createFile(strPath);
        String plan = "-------------------------------------|-------------------------------------";
        int contLen1 = 18;
        String controw1 = "   ��    �ţ�" + ReportHelper.getLeftSpaceStr(actani.getOldacn(), contLen1) + "|��    ����" + act.getActnam();
        String controw2 = "   ��    �" + DataFormater.formatNum(this.ccyDecpos, act.getBokbal()) + "|";
        String controw3 = "   �� Ϣ �գ�" + ReportHelper.getLeftSpaceStr(sdf.format(act.getLintdt()), contLen1) + "|ֹ Ϣ �գ�" + this.bizStrDate;
        String controw4 = "   �跽���ʣ�" + ReportHelper.getLeftSpaceStr(this.dinIrtval, contLen1) + "|�������ʣ�" + this.cinIrtval;
        String draccm = DataFormater.formatNum(this.ccyDecpos, act.getDraccm());
        String craccm = DataFormater.formatNum(this.ccyDecpos, act.getCraccm());
        String controw5 = "   �跽������" + ReportHelper.getLeftSpaceStr(draccm, contLen1) + "|����������" + craccm;
        //��Ϣ���� ���� * ����
        double dbdacint = Double.parseDouble(StringUtils.isEmpty(this.dinIrtval.trim()) ? "0" : this.dinIrtval) * act.getDraccm();
        double dbcacint = Double.parseDouble(StringUtils.isEmpty(this.cinIrtval.trim()) ? "0" : this.cinIrtval) * act.getCraccm();
        String dacint = DataFormater.formatNum(this.ccyDecpos, dbdacint);
        String cacint = DataFormater.formatNum(this.ccyDecpos, dbcacint);
        String controw6 = "   �跽��Ϣ��" + ReportHelper.getLeftSpaceStr(dacint, contLen1) + "|������Ϣ��" + cacint;
        String totalacint = DataFormater.formatNum(this.ccyDecpos, dbcacint - dbdacint);   //��Ϣ�ϼ�
        String amount = DataFormater.formatNum(this.ccyDecpos, act.getBokbal() + dbcacint - dbdacint);  //��Ϣ�ϼ�
        String controw7 = "   ��Ϣ�ϼƣ�" + ReportHelper.getLeftSpaceStr(totalacint, contLen1) + "|��Ϣ�ϼƣ�" + amount;
        String controw8 = "   �����˺ţ�" + "|";
        String controw9 = "   ��Ϣ�����˺ţ�" + incActnum + "|";
        String controw10 = "   ��Ϣ֧���˺ţ�" + expActnum + "|";
        File file = new File(strPath + fileName);
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("                          |���ڴ����Ϣ��");
        bw.newLine();
        bw.write("                           |���ڣ�" + bizStrDate);
        bw.newLine();
        bw.write(controw1);
        bw.newLine();
        bw.write(controw2);
        bw.newLine();
        bw.write(controw3);
        bw.newLine();
        bw.write(controw4);
        bw.newLine();
        bw.write(controw5);
        bw.newLine();
        bw.write(controw6);
        bw.newLine();
        bw.write(controw7);
        bw.newLine();
        bw.write(controw8);
        bw.newLine();
        bw.write(controw9);
        bw.newLine();
        bw.write(controw10);
        bw.newLine();
        bw.write("   ��ע��|");
        bw.newLine();
//        bw.write(plan);
        bw.flush();
        bw.close();

    }

    /*
    * ��������ֵ*/

    private String getIrtval(String rat, SqlSession session) {
        if (rat.trim().length() != 3) {
            return "";
        }
        String curcde = act.getCurcde();
        String irtkd1 = rat.substring(0, 1);
        String irtkd2 = rat.substring(1, 3);
        ActccyMapper ccymap = session.getMapper(ActccyMapper.class);
        double irtval = ccymap.selectByParam(curcde, irtkd1, irtkd2);
        return (String.valueOf(irtval).equals("")?"0":String.valueOf(irtval));
    }

    //����ƾ֤�����ļ� ��Ա��.txt

    private void createFile(String strPath) throws IOException {

        //����·��
        File fileDirec = new File(strPath);
        if (!fileDirec.isDirectory() || !fileDirec.exists()) {
            if (!fileDirec.mkdirs()) {
                MessageUtil.addInfo("·������ʧ�ܣ�");
                logger.error("·������ʧ�ܣ�");
            }
        }
    }

    /*��ȡ��Ϣ����֧���˻�*/

    private void getincexpActnum(SqlSession session) {
        ActapcMapper apcmap = session.getMapper(ActapcMapper.class);
        ActapcExample apcexamp = new ActapcExample();
        apcexamp.createCriteria().andApcodeEqualTo(act.getApcode());
        List<Actapc> apcList = apcmap.selectByExample(apcexamp);
        Actapc apc = apcList.get(0);
        ActaniMapper animap = session.getMapper(ActaniMapper.class);
        this.incActnum = animap.selectByActnoCode(aniOrgidt, "9010600", apc.getIntinc(), act.getCurcde());
        this.incActnum = (this.incActnum == null ? "" : this.incActnum);
        this.expActnum = animap.selectByActnoCode(aniOrgidt, "9010600", apc.getIntexp(), act.getCurcde());
        this.expActnum = (this.expActnum == null ? "" : this.expActnum);
    }

    private void getBisDate() throws ParseException {
        this.bizStrDate = SystemService.getBizDate();
        SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMdd");
        this.bizDateDate = sdf0.parse(this.bizStrDate);
        this.bizStrDate = sdf.format(this.bizDateDate);
    }

    public Actani getActani() {
        return actani;
    }

    public void setActani(Actani actani) {
        this.actani = actani;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }
}
