package cbs.view.ac.customer;

import cbs.common.IbatisManager;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.repository.code.dao.ActccyMapper;
import cbs.repository.code.dao.ActctrMapper;
import cbs.repository.code.model.Actccy;
import cbs.repository.code.model.Actctr;
import cbs.repository.customer.dao.ActcusMapper;
import cbs.repository.customer.dao.ActentMapper;
import cbs.repository.customer.model.Actcus;
import cbs.repository.customer.model.ActcusExample;
import cbs.repository.customer.model.Actent;
import cbs.repository.customer.model.ActentExample;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.model.Ptenudetail;
import cbs.repository.platform.model.PtenudetailExample;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.component.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class CusEntQry {

    private static final Logger logger = LoggerFactory.getLogger(CusEntQry.class);

    private String cusidt;
    private String cusnam;
    private List<Actcus> cusList;
    private Actcus selectedCus;
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private SqlSession session;
    private Map<String,String> enuPasMap;          // 证件类型

    public Map<String, String> getEnuPasMap() {
        return enuPasMap;
    }

    public void setEnuPasMap(Map<String, String> enuPasMap) {
        this.enuPasMap = enuPasMap;
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if(context != null && context.getRenderResponse()){
            session = ibatisManager.getSessionFactory().openSession();
             this.enuPasMap = initPtenudetailMap("ENTPASTYPE");
            session.close();
            queryRecords();
        }      
    }
    // 查询按钮的响应，查询客户记录
    public void queryRecords() {
        try {
            session = ibatisManager.getSessionFactory().openSession();
            cusList =  selectByCondition();
            if(cusList == null || cusList.size()==0 ){
                MessageUtil.addInfoWithClientID("msgs","M546");
                return;
            }else{
            for(Actcus cus : cusList){
                   cus.setPastyp(enuPasMap.get(cus.getPastyp()));
            }
            }
            setTableToFirstPage("cusMsgForm:cusMsgDataTable");
        } catch (Exception e) {
            logger.error("客户信息查询失败："+e.getMessage());
            MessageUtil.addErrorWithClientID("msgs","M932");
        }
        finally {
            closeSession(session);
        }
    }
    // 组装两个查询条件
    private List<Actcus> selectByCondition(){
        ActcusMapper cusmapper = session.getMapper(ActcusMapper.class);
         if (!isEmpty(cusidt) && !isEmpty(cusnam)) {
               return cusmapper.selectCusByIdNam(cusidt.trim(),cusnam.trim());
         } else if (!isEmpty(cusidt)) {
             return cusmapper.selectCusByCusidt(cusidt.trim());
            } else if (!isEmpty(cusnam)) {
             return cusmapper.selectCusByCusnam(cusnam.trim());
            } else {
             return cusmapper.selectCus();
            }
    }

    public String action(){
        return "entcusEdit";
    }
    private Map<String,String> initPtenudetailMap(String enutyp){
       PtenudetailMapper detailMapper = session.getMapper(PtenudetailMapper.class);
       PtenudetailExample pe = new PtenudetailExample();
       pe.createCriteria().andEnutypeEqualTo(enutyp);
       pe.setOrderByClause("DISPNO");
       List<Ptenudetail> enudetailList = detailMapper.selectByExample(pe);
       Map<String,String> map = new HashMap<String,String>();
            for(Ptenudetail p:enudetailList){
                   map.put(p.getEnuitemvalue(),p.getEnuitemlabel());
            }
        return map;
    }
      
    // 设置为第一页
    private void setTableToFirstPage(String formDT){

        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(formDT);
        if(dataTable != null){        
        dataTable.setFirst(0);
        dataTable.setPage(1);
        }
    }

    private boolean isEmpty(String field) {
        return (field == null || "".equalsIgnoreCase(field)) ? true : false;
    }

     // 关闭SqlSession
    private void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    public List<Actcus> getCusList() {
        return cusList;
    }

    public void setCusList(List<Actcus> cusList) {
        this.cusList = cusList;
    }

    public Actcus getSelectedCus() {
        return selectedCus;
    }

    public void setSelectedCus(Actcus selectedCus) {
        this.selectedCus = selectedCus;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public String getCusidt() {
        return cusidt;
    }

    public void setCusidt(String cusidt) {
        this.cusidt = cusidt;
    }

    public String getCusnam() {
        return cusnam;
    }

    public void setCusnam(String cusnam) {
        this.cusnam = cusnam;
    }
    
}
