package cbs.view.ac.customer;

//import cbs.view.ac.customer.

import cbs.common.IbatisManager;
import cbs.common.OnlineService;
import cbs.common.SystemService;
import cbs.common.utils.MessageUtil;
import cbs.repository.customer.dao.ActcusMapper;
import cbs.repository.customer.dao.ActperMapper;
import cbs.repository.customer.model.Actcus;
import cbs.repository.customer.model.ActcusKey;
import cbs.repository.customer.model.Actper;
import oracle.jdbc.OracleTypes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import pub.platform.security.OperatorManager;
import pub.platform.utils.BusinessDate;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-10-20
 * Time: 14:13:33
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "actperAdd")
@RequestScoped
public class PerCustomerAdd implements java.io.Serializable {
    //    @ManagedProperty("#{listbond}")
//    ListBond listbond;
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private static final Log logger = LogFactory.getLog(PerCustomerAdd.class);
    public Actper actper = new Actper();
    public Actcus actcus = new Actcus();
    public String operDate = null;
    public String queryCuwsidt = "";
    private final String sysid = "8";
    private final String orgid = "010";
    private final String depid = "60";
    private final String recsts = " ";
    private String operatorName;            //登陆用户名


    @PostConstruct
    private void init() {
        this.actcus.setCusidt(generateCusidt());
//         this.actcus.setCusidt(listbond.getCusidt());
    }

    /*
           生成客户号
    */
    private String generateCusidt() {

        String newCusidt = null;
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActcusMapper mapper = session.getMapper(ActcusMapper.class);
            int maxCusidt = 0;
            maxCusidt = mapper.selectMaxCusidt() + 1;
            if(maxCusidt != 0 && maxCusidt<= 8999999){
                newCusidt = String.valueOf(maxCusidt);
            }else{

                int minCusidt = 0;
                minCusidt = mapper.selectMinCusidt();
                do{
                    newCusidt = String.valueOf(minCusidt+1);
                }while(mapper.selectCusCountById(newCusidt)!=0);
            }
        } catch (Exception e) {
            MessageUtil.addError("MH00");
            logger.error(e.getMessage());
            return null;
        } finally {
            session.close();
        }
        return pattern(newCusidt,"0000000");
    }
    /*
    * 获取当前最大客户号+1*/
    public String getCusid() {
        Connection con = null;
        CallableStatement cstmt = null;
        String cidt = null;
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            con = session.getConnection();
            cstmt = con.prepareCall("{call get_custidt(?)}");
            cstmt.registerOutParameter(1, OracleTypes.VARCHAR);
            cstmt.execute();
            cidt = (String)cstmt.getString(1);
            cidt = pattern(cidt,"0000000");
            session.commit();
            return cidt;
        } catch (Exception ex) {
            session.close();
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }


    /*
    * 数字左侧自动补零*/
    private String pattern(String str,String pattern) {
        int tempStr = Integer.parseInt(str);
        DecimalFormat di = new DecimalFormat(pattern);
        return di.format(tempStr);

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
        return operDate=BusinessDate.getToday();
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
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
    public String perAddjsf() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        OperatorManager opm = OnlineService.getOperatorManager();
        ActcusMapper cusmap = session.getMapper(ActcusMapper.class);
        if (actcus.getCusidt().equals("")) {
            actcus.setCusidt(generateCusidt());
        }
        //验证是否重复数据
        ActcusKey cuskey = new ActcusKey();
        cuskey.setCusidt(actcus.getCusidt());
        cuskey.setSysidt(sysid);
        Actcus acus = cusmap.selectByPrimaryKey(cuskey);
        FacesContext context = FacesContext.getCurrentInstance();
        //该客户已存在
        if (acus != null) {
            MessageUtil.addInfo("M113");
            return null;
        }
        actcus.setSysidt(sysid);
        actcus.setRecsts(actper.getRecsts());
        actcus.setRecsts(recsts);
        actcus.setOrgidt(this.orgid);
        actcus.setDepnum(this.depid);
        actcus.setAmdtlr(opm.getOperatorId());
        actper.setSysidt(sysid);
        actper.setCusidt(actcus.getCusidt());
        actper.setRecsts(recsts);
        actper.setAmdtlr(opm.getOperatorId());
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bizDate = SystemService.getBizDate10();
        try {
            Date dt = dtFormat.parse(bizDate);
            actcus.setUpddat(dt);
            actper.setUpddat(dt);
            cusmap.insertSelective(this.actcus);
            ActperMapper entmap = session.getMapper(ActperMapper.class);
            entmap.insertSelective(this.actper);
            session.commit();
            //交易成功
            MessageUtil.addInfo("W301");
            actcus = new Actcus();
            this.actcus.setCusidt(generateCusidt());
        } catch (Exception ex) {
            session.close();
            MessageUtil.addInfo("交易失败！");
            logger.error("个人信息添加记录出错：" + ex.getMessage());
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }
        return null;
    }

    public String getOperatorName() {
        OperatorManager opm = OnlineService.getOperatorManager();
        return opm.getOperatorName();
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}