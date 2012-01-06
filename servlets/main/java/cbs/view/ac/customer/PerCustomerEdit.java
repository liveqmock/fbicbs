package cbs.view.ac.customer;

import cbs.common.IbatisManager;
import cbs.common.utils.MessageUtil;
import cbs.repository.customer.dao.ActcusMapper;
import cbs.repository.customer.dao.ActperMapper;
import cbs.repository.customer.model.Actcus;
import cbs.repository.customer.model.ActcusExample;
import cbs.repository.customer.model.Actper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.component.datatable.DataTable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-11-16
 * Time: 15:06:01
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name="percustEdit")
//@SessionScoped
//@RequestScoped
@ViewScoped
public class PerCustomerEdit implements java.io.Serializable {
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private static final Log logger = LogFactory.getLog(PerCustomerEdit.class);
    private HtmlInputText myCusidtTxt;
    private Actcus actcus = new Actcus();
    private Actcus selectRec;
    private List<Actcus> actcusLst;
    private final String sysid = "8";
    private final String recsts = "D";
     
    @PostConstruct
    private void init() {
        queryInfo("");
    }

    //查询点击按钮
    public String onQuery() {
        queryInfo(this.actcus.getCusidt());
        return null;
    }
    /*
    * 查询方法
    * param: 1 reqCusidt 客户号*/
    private void queryInfo(String reqCusidt) {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        ActcusMapper cusmap = session.getMapper(ActcusMapper.class);
        ActcusExample example = new ActcusExample();
        example.setOrderByClause("to_number(CUSIDT)");
        if (!reqCusidt.equals("")) {
            example.createCriteria().andRecstsEqualTo(" ").andCusidtEqualTo(reqCusidt).andCuskidEqualTo("2");
        } else {
            example.createCriteria().andRecstsEqualTo(" ").andCuskidEqualTo("2");
        }
        actcusLst = cusmap.selectByExample(example);

        session.close();
        DataTable dataTable = (DataTable)FacesContext.getCurrentInstance().getViewRoot().findComponent("queryForm:actcusInfo");
        if (dataTable != null) {
            dataTable.setFirst(0);
            dataTable.setPage(1);
        }

    }
    /*
    * 删除数据操作*/
    public String onDeleteRecClick() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        String reqCusidt = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cusidt1");
        String inputCusidt = (String)this.myCusidtTxt.getValue();
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ActcusMapper cusmap = session.getMapper(ActcusMapper.class);
            ActperMapper permap = session.getMapper(ActperMapper.class);
            Actcus upcusSts = new Actcus();
            Actper upperSts = new Actper();
            upcusSts.setCusidt(reqCusidt);
            upcusSts.setSysidt(sysid);
            upcusSts.setRecsts(recsts);
            upperSts.setSysidt(sysid);
            upperSts.setCusidt(reqCusidt);
            upperSts.setRecsts(recsts);
            cusmap.updateByPrimaryKeySelective(upcusSts);
            permap.updateByPrimaryKeySelective(upperSts);
            session.commit();
            queryInfo(inputCusidt);
            MessageUtil.addInfo("数据删除成功！");
//            context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"数据删除成功！","success"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return null;
    }

    public HtmlInputText getMyCusidtTxt() {
        return myCusidtTxt;
    }

    public void setMyCusidtTxt(HtmlInputText myCusidtTxt) {
        this.myCusidtTxt = myCusidtTxt;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    /*public Actcus[] getSelectRec() {
        return selectRec;
    }

    public void setSelectRec(Actcus[] selectRec) {
        this.selectRec = selectRec;
    }*/

    public Actcus getSelectRec() {
        return selectRec;
    }

    public void setSelectRec(Actcus selectRec) {
        this.selectRec = selectRec;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public Actcus getActcus() {
        return actcus;
    }

    public void setActcus(Actcus actcus) {
        this.actcus = actcus;
    }

    public List<Actcus> getActcusLst() {
        return actcusLst;
    }

    public void setActcusLst(List<Actcus> actcusLst) {
        this.actcusLst = actcusLst;
    }

/*
    * 查询数据（edit页面）*/
    public String onQueryCustInfo() {
        String reqCusidt = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cusidt");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("paramCusidt",reqCusidt);
        return "perCustomerSingleEdit?faces-redirect=true";
    }
}
