package cbs.view.common;

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

@ManagedBean(name = "beanList")
@SessionScoped
public class BeanList {

     @ManagedProperty("#{ibatisManager}")
     IbatisManager ibatisManager;
     private static final Logger logger = Logger.getLogger(BeanList.class);
    private List<SelectItem> ccyList;             // 货币表
    private List<SelectItem> ctrList;              // 国家码

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

     // 获取货币列表
    public List<SelectItem> getCcyList() {
         SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActccyMapper mapper = session.getMapper(ActccyMapper.class);
            List<Actccy> actccyList = mapper.selectByExample(null);
            if(actccyList.size() > 0){
                  ccyList = new ArrayList<SelectItem>();
                  for(Actccy ccy : actccyList){
                       SelectItem si = new SelectItem(ccy.getCurcde(),ccy.getCurnmc());
                       ccyList.add(si);
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

}