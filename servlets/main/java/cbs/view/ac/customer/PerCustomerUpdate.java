package cbs.view.ac.customer;

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.utils.MessageUtil;
import cbs.repository.customer.dao.ActcusMapper;
import cbs.repository.customer.dao.ActperMapper;
import cbs.repository.customer.model.Actcus;
import cbs.repository.customer.model.ActcusKey;
import cbs.repository.customer.model.Actper;
import cbs.repository.customer.model.ActperKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import pub.platform.security.OperatorManager;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-11-18
 * Time: 13:32:40
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "perCustUpdate")
@ViewScoped
public class PerCustomerUpdate implements java.io.Serializable {
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private static final Log logger = LogFactory.getLog(PerCustomerUpdate.class);
    public Actper actper = new Actper();
    public Actcus actcus = new Actcus();
    public String operDate = null;
    public String queryCuwsidt = "";
    private final String orgid = "010";
    private final String depid = "60";
    private final String sysid = "8";
    private String operatorName;            //登陆用户名
     @PostConstruct
    private void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getRenderResponse()) {
            this.queryCuwsidt = (String)context.getExternalContext().getSessionMap().get("paramCusidt");
            if (!queryCuwsidt.equals("")) {
                SqlSession session = ibatisManager.getSessionFactory().openSession();
                try {
                     ActcusMapper cusmap = session.getMapper(ActcusMapper.class);
                    ActperMapper permap = session.getMapper(ActperMapper.class);
                    ActcusKey cuskey = new ActcusKey();
                    cuskey.setCusidt(this.queryCuwsidt);
                    cuskey.setSysidt(sysid);
                    ActperKey perkey = new ActperKey();
                    perkey.setCusidt(queryCuwsidt);
                    perkey.setSysidt(sysid);
                    this.actcus = cusmap.selectByPrimaryKey(cuskey);
                    this.actper = permap.selectByPrimaryKey(perkey);
                } catch (Exception ex) {
                    MessageUtil.addInfo("数据加载失败");
                    logger.error("数据加载失败：" + ex.getMessage());
                    ex.printStackTrace();
                } finally {
                    session.close();
                }

            }
        }
    }

    /*
    * 验证数据*/
    public void validateCusidt(FacesContext context, UIComponent com,Object value) {
        String strText = value.toString();
        if (!strText.equals("")) {
            if (!validateNum(strText)) {
                throw new ValidatorException(new FacesMessage("错误输入！"));
            }
        }
    }
    /*
    * 验证数字*/
    private boolean validateNum(String str) {
        try {
            Long.parseLong(str);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    /*
    * 数据保存*/
    public String PerCustSave() {
        FacesContext context = FacesContext.getCurrentInstance();
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        OperatorManager opm = OnlineService.getOperatorManager();
        ActcusMapper cusmap = session.getMapper(ActcusMapper.class);
        ActperMapper permap = session.getMapper(ActperMapper.class);
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bizDate = SystemService.getBizDate10();
        try {
            actcus.setSysidt(sysid);
            actcus.setAmdtlr(opm.getOperatorId());
            actper.setSysidt(sysid);
            actper.setCusidt(actcus.getCusidt());
            actper.setAmdtlr(opm.getOperatorId());
            Date dt = dtFormat.parse(bizDate);
            actcus.setUpddat(dt);
            actper.setUpddat(dt);
            cusmap.updateByPrimaryKeySelective(actcus);
            permap.updateByPrimaryKeySelective(actper);
            session.commit();
            MessageUtil.addInfo("数据更新成功！");
        } catch (Exception ex) {
            MessageUtil.addInfo("数据更新失败！");
            logger.error("数据更新失败：" + ex.getMessage());
            ex.printStackTrace();
            return null;
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

    public Actper getActper() {
        return actper;
    }

    public void setActper(Actper actper) {
        this.actper = actper;
    }

    public Actcus getActcus() {
        return actcus;
    }

    public void setActcus(Actcus actcus) {
        this.actcus = actcus;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }
    public String getOperatorName() {
        OperatorManager opm = OnlineService.getOperatorManager();
        return opm.getOperatorName();
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
