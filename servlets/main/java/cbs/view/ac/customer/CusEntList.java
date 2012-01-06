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

    private List<SelectItem> ctrList;           // 国家码表
    private List<SelectItem> pasTypList;        // 证件类型数据
    private List<SelectItem> cusKidList;        // 客户类型
    private List<SelectItem> busCdeList;        // 行业代码表
    private List<SelectItem> entTypList;        // 企业性质
    private List<SelectItem> ccyList;             // 外币表

    // 生成国家码表
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

    // 生成证件类型(枚举)
    public List<SelectItem> getPasTypList() {
        this.pasTypList = generateEnuList("ENTPASTYPE");
        return pasTypList;
    }

    // 生成客户类型(枚举)
    public List<SelectItem> getCusKidList() {
        this.cusKidList = generateEnuList("CUSTKID");
        return cusKidList;
    }

 // 生成企业性质(枚举)
    public List<SelectItem> getEntTypList() {
        this.entTypList = generateEnuList("ENTTYPE");
        return entTypList;
    }
    
    // 生成行业代码
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
     // 获取外币列表(人民币除外)
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
    根据枚举类型名获取相应的枚举值
     */
    private List<SelectItem> generateEnuList(String enutypeVal) {

        List<SelectItem> enudetailList=new ArrayList<SelectItem>();
        // 企业性质枚举项可不填，填入空字符串
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
