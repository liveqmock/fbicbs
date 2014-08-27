package cbs.view.ac.customer;


import cbs.common.IbatisManager;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.code.dao.ActctrMapper;
import cbs.repository.code.model.Actapc;
import cbs.repository.code.model.Actccy;
import cbs.repository.code.model.Actctr;
import cbs.repository.code.model.ActctrExample;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.model.Ptenudetail;
import cbs.repository.platform.model.PtenudetailExample;
import cbs.repository.platform.model.PtenudetailKey;
import cbs.view.ac.accountinfo.ActInfo;
import oracle.jdbc.OracleTypes;
import org.apache.ibatis.session.SqlSession;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
//import report.test.UserJdbcWithoutTransManagerService;

import javax.faces.bean.*;
import javax.faces.model.SelectItem;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-10-26
 * Time: 10:04:41
 * To change this template use File | Settings | File Templates.
 */
//@ManagedBean(name = "listbond", eager=true)
@ManagedBean(name = "listbond")
@RequestScoped
public class ListBond implements java.io.Serializable {
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private ArrayList<SelectItem> alapc = null;         //������
    private ArrayList<SelectItem> alccy = null;         //������
    private ArrayList<SelectItem> alacttype = null;    //�˻�����
    private ArrayList<SelectItem> alintflg = null;     //��Ϣ��־
    private ArrayList<SelectItem> alinfcyc = null;      //��Ϣ������
    private ArrayList<SelectItem> alcqeflg = null;       //֧Ʊ���۱�־
    private ArrayList<SelectItem> allegfmt = null;       // �ֻ�����ҳ��ʽ
    private ArrayList<SelectItem> alstmfmt = null;       //���˵���ҳ��ʽ
    private ArrayList<SelectItem> alstmcyc = null;      //���ʵ���������
    private ArrayList ctr = null;                       //������
    private ArrayList passtype = null;                 //���֤�����
    private ArrayList customerkid = null;              //�ͻ����
    private ArrayList alcussex = null;                  //�Ա�
    private ArrayList alprofsn = null;                  //ְҵ
    private ArrayList alposton = null;                  //ְ��
    private ArrayList almarsts = null;     //����״��
    private ArrayList alrecsts = null;     //��¼״̬
    private String cusidt = null;           //�ͻ���

    public ArrayList<SelectItem> getAlstmcyc() {
        return this.getList("STMCYC");
    }

    public void setAlstmcyc(ArrayList<SelectItem> alstmcyc) {
        this.alstmcyc = alstmcyc;
    }

    public ArrayList<SelectItem> getAllegfmt() {
        return this.getList("LEGFMT");
    }

    public void setAllegfmt(ArrayList<SelectItem> allegfmt) {
        this.allegfmt = allegfmt;
    }

    public ArrayList<SelectItem> getAlstmfmt() {
        return this.getList("STMFMT");
    }

    public void setAlstmfmt(ArrayList<SelectItem> alstmfmt) {
        this.alstmfmt = alstmfmt;
    }

    public ArrayList<SelectItem> getAlcqeflg() {
        return this.getList("CQEFLG");
    }

    public void setAlcqeflg(ArrayList<SelectItem> alcqeflg) {
        this.alcqeflg = alcqeflg;
    }

    public ArrayList<SelectItem> getAlinfcyc() {
        return this.getList("INFCYC");
    }

    public void setAlinfcyc(ArrayList<SelectItem> alinfcyc) {
        this.alinfcyc = alinfcyc;
    }

    public ArrayList getCtr() {
        ActctrExample pe = new ActctrExample();
        pe.setOrderByClause("ctrcde");
        ArrayList aList=new ArrayList();
        /*SelectItem siSpace = new SelectItem("","");
        aList.add(siSpace);*/
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActctrMapper mapper = session.getMapper(ActctrMapper.class);
            List<Actctr> ls = mapper.selectByExample(pe);
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                Actctr m = (Actctr)it.next();
                int i=0;
                String val = "";
                String lbl = "";
                val = m.getCtrcde();
                lbl = m.getCtrnmc();
                SelectItem si = new SelectItem(val,lbl);
                aList.add(si);
            }
        } finally {
            session.close();
        }
        return aList;
    }

    public ArrayList<SelectItem> getAlintflg() {
        return getList("INTFLG");
    }

    public void setAlintflg(ArrayList<SelectItem> alintflg) {
        this.alintflg = alintflg;
    }

    public ArrayList<SelectItem> getAlacttype() {
        return getList("ACTTYP");
    }

    public void setAlacttype(ArrayList<SelectItem> alacttype) {
        this.alacttype = alacttype;
    }

    public ArrayList<SelectItem> getAlccy() {
        Actccy ccy = new Actccy();
        return ccy.getAllData();
    }

    public void setAlccy(ArrayList<SelectItem> alccy) {
        this.alccy = alccy;
    }

    public ArrayList<SelectItem> getAlapc() {
        Actapc apc = new Actapc();
        return apc.getAllData();
    }

    public void setAlapc(ArrayList alapc) {
        this.alapc = alapc;
    }

    public ArrayList getPasstype() {
        return getList("PASTYPE");
    }

    public ArrayList getCustomerkid() {
        return getList("PERCUSTKID");
    }

    public ArrayList getAlcussex() {
        return getList("SEX");
    }

    public ArrayList getAlprofsn() {
        return getList("PROFSN");
    }

    public ArrayList getAlposton() {
        return getList("POSTON");
    }

    public ArrayList getAlmarsts() {
        return getList("MARSTS");
    }

    public ArrayList getAlrecsts() {
        return getList("RECSTS");
    }
    /*
    * selectȡֵ
    * ��һ��value �ڶ��� label*/
    private ArrayList getList(String enutypeVal) {
        PtenudetailExample pe = new PtenudetailExample();
        pe.setOrderByClause("dispno");
        pe.createCriteria().andEnutypeEqualTo(enutypeVal);
        ArrayList aList=new ArrayList();
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            PtenudetailMapper mapper = session.getMapper(PtenudetailMapper.class);
            List<Ptenudetail> ls = mapper.selectByExample(pe);
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                Ptenudetail m = (Ptenudetail)it.next();
                int i=0;
                String val = "";
                String lbl = "";
                val = m.getEnuitemvalue();
                lbl = m.getEnuitemlabel();
                SelectItem si = new SelectItem(val,lbl);
                aList.add(si);
            }
        } finally {
            session.close();
        }

        return aList;
    }

    /*
    * ��ȡ�ͻ���+1����
    *  �� �糬�����ֵ���ͷ��Ѱ��ֵ��Сֵ*/

    /*public String getCusidt() {
        Connection con = null;
        CallableStatement cstmt = null;
        String cidt = null;
        SqlSession session1 = ibatisManager.getSessionFactory().openSession();
        try {
            con = session1.getConnection();
            cstmt = con.prepareCall("{call get_custidt(?)}");
            cstmt.registerOutParameter(1,OracleTypes.VARCHAR);
            cstmt.execute();
            cidt = (String)cstmt.getString(1);
            session1.commit();
            return cidt;
        } catch (Exception ex) {
            session1.close();
            ex.printStackTrace();
            return null;
        } finally {
            session1.close();
        }

    }*/

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }
}