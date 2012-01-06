package cbs.view.ac.customer;

import cbs.common.IbatisManager;
import cbs.repository.code.dao.ActccyMapper;
import cbs.repository.code.dao.ActctrMapper;
import cbs.repository.code.dao.ActtbcMapper;
import cbs.repository.code.model.Actccy;
import cbs.repository.code.model.Actctr;
import cbs.repository.code.model.Acttbc;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.model.Ptenudetail;
import cbs.repository.platform.model.PtenudetailExample;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "cusEntlist")
@SessionScoped
public class CusEntList {

     @ManagedProperty("#{ibatisManager}")
     IbatisManager ibatisManager;
     private static final Logger logger = Logger.getLogger(CusEntList.class);

    private List<SelectItem> ctrList;           // �������
    private List<SelectItem> pasTypList;        // ֤����������
    private List<SelectItem> cusKidList;        // �ͻ�����
    private List<SelectItem> busCdeList;        // ��ҵ�����
    private List<SelectItem> entTypList;        // ��ҵ����
    private List<SelectItem> ccyList;             // ��ұ�

    // ���ɹ������
    public List<SelectItem> getCtrList() {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActctrMapper mapper = session.getMapper(ActctrMapper.class);
            List<Actctr> actctrList = mapper.selectByExample(null);
            if(actctrList.size() > 0){
                  ctrList = new ArrayList<SelectItem>();
                  for(Actctr ctr : actctrList){
                       SelectItem si = new SelectItem(ctr.getCtrcde(),ctr.getCtrnmc());
                       ctrList.add(si);
                  }
            }
        } finally {
            session.close();
        }
        return ctrList;
    }

    // ����֤������(ö��)
    public List<SelectItem> getPasTypList() {
        this.pasTypList = generateEnuList("ENTPASTYPE");
        return pasTypList;
    }

    // ���ɿͻ�����(ö��)
    public List<SelectItem> getCusKidList() {
        this.cusKidList = generateEnuList("CUSTKID");
        return cusKidList;
    }

 // ������ҵ����(ö��)
    public List<SelectItem> getEntTypList() {
        this.entTypList = generateEnuList("ENTTYPE");
        return entTypList;
    }
    
    // ������ҵ����
    public List<SelectItem> getBusCdeList() {

        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActtbcMapper mapper = session.getMapper(ActtbcMapper.class);
            List<Acttbc> acttbcList = mapper.selectByExample(null);
            if(acttbcList.size() > 0){
                  busCdeList = new ArrayList<SelectItem>();
                  for(Acttbc tbc : acttbcList){
                       SelectItem si = new SelectItem(tbc.getTdbsrt(),tbc.getTdbnam());
                       busCdeList.add(si);
                  }
            }
        } finally {
            session.close();
        }
        return busCdeList;
    }
     // ��ȡ����б�(����ҳ���)
    public List<SelectItem> getCcyList() {
         SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActccyMapper mapper = session.getMapper(ActccyMapper.class);
            List<Actccy> actccyList = mapper.selectByExample(null);
            if(actccyList.size() > 0){
                  ccyList = new ArrayList<SelectItem>();
                  ccyList.add(new SelectItem("",""));
                  for(Actccy ccy : actccyList){
                      if(!"001".equalsIgnoreCase(ccy.getCurcde().trim())){
                       SelectItem si = new SelectItem(ccy.getCurcde(),ccy.getCurnmc());
                       ccyList.add(si);
                      }                       
                  }
            }
        } finally {
            session.close();
        }
        return ccyList;
    }
    
    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    /*
    ����ö����������ȡ��Ӧ��ö��ֵ
     */
    private List<SelectItem> generateEnuList(String enutypeVal) {

        List<SelectItem> enudetailList=new ArrayList<SelectItem>();
        // ��ҵ����ö����ɲ��������ַ���
        if("ENTTYPE".equals(enutypeVal)){
        	enudetailList.add(new SelectItem("",""));
        }
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            PtenudetailMapper mapper = session.getMapper(PtenudetailMapper.class);
            PtenudetailExample pe = new PtenudetailExample();
            pe.createCriteria().andEnutypeEqualTo(enutypeVal);
            pe.setOrderByClause("DISPNO");
            List<Ptenudetail> enuList = mapper.selectByExample(pe);
            for(Ptenudetail p : enuList){
                SelectItem si = new SelectItem(p.getEnuitemvalue(),p.getEnuitemlabel());
                enudetailList.add(si);
            }
        } finally {
            session.close();
        }
        return enudetailList;
    }
}
