package cbs.view.ac.customer;

import cbs.common.IbatisFactory;
import cbs.repository.customer.dao.ActcusMapper;
import cbs.repository.customer.model.ActcusExample;
import org.apache.ibatis.session.SqlSession;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean(name = "cusEntValidator")
@RequestScoped
public class CusEntValidator {
    private static final String MAXLENGTH = "maxlength";
    private static final String LABEL = "label";
    private static final String INPUT_ERROR = "输入检查错误";
    private static final String LENGTH_ERROR = "长度检查错误";
    // 非中文
    private static final String NO_CHINESE_REGEX = "^[^\\u4e00-\\u9fa5]{0,}$";
    /*电话号码匹配格式：
    11位手机号码 3-4位区号，7-8位直播号码，1－4位分机号 如：12345678901、1234-12345678-1234
    */
    private static final String PHONE_REGEX = "(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
     // 至多两位小数的正实数
    private static final String REAL_NUMBER_REGEX =  "^(0|[1-9][0-9]*)|[0-9]+(.[0-9]{0,2})$";
          //   "^[0-9]+(.[0-9]{0,2})$";
    // 零和非零开头的数字
    private static final String NUMBER_REGEX = "^(0|[1-9][0-9]*)$";

    /*
    验证客户号是否为空或已存在
    */
    public void validateCusidt(FacesContext context, UIComponent com, Object object) {
        SqlSession session = IbatisFactory.ORACLE.getInstance().openSession();
        ActcusMapper cusMapper = session.getMapper(ActcusMapper.class);
        String cusidt = (String) object;
        if (cusidt == null || "".equalsIgnoreCase(cusidt.trim()) || "0000000".equalsIgnoreCase(cusidt)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "请输入正确的客户号！"));            
        } else {
            ActcusExample example = new ActcusExample();
            example.createCriteria().andCusidtEqualTo(cusidt.trim());
            int cnt = 0;
            cnt = cusMapper.countByExample(example);
            if (cnt > 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "该客户号已存在！"));
            }
        }
    }

    /*
        必须输入项验证输入长度 输入项属性中必须包含maxlength 和 label
     */
    public void mustValidateLength(FacesContext context, UIComponent com, Object object) {

        Map<String, Object> attrs = com.getAttributes();
        String value = (String) object;
        if (value == null || value.trim().equals("")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "必须输入" + attrs.get(LABEL) + "!"));
        } else {
            int length = (Integer) attrs.get(MAXLENGTH);
            if (value.getBytes().length > length) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, LENGTH_ERROR, attrs.get(LABEL) + "输入内容超长！"));
            }
        }
    }

    /*
       可选输入项验证输入长度 输入项属性中必须包含maxlength 和 label
    */
    public void orValidateLength(FacesContext context, UIComponent com, Object object) {
        Map<String, Object> attrs = com.getAttributes();
        int length = (Integer) attrs.get(MAXLENGTH);
        if (object != null && length > 0) {
            String value = (String) object;
            if (value.getBytes().length > length) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, LENGTH_ERROR, attrs.get(LABEL) + "输入内容超长！"));
            }
        }
    }

    /*
   验证非必填项不超长且不包含中文
    */
    public void validateRealNumberAndLength(FacesContext context, UIComponent com, Object object) {
        orValidateLength(context, com, object);
        validateRealNumber(context, com, object);
    }

    /*
   验证非必填项不超长且不包含中文
    */
    public void validateNoChineseAndLength(FacesContext context, UIComponent com, Object object) {
        orValidateLength(context, com, object);
        validateNoChinese(context, com, object);
    }

    /*
   验证必填项不超长且是电话号码
    */
    public void validatePhoneAndLength(FacesContext context, UIComponent com, Object object) {
        mustValidateLength(context, com, object);
        validatePhone(context, com, object);
    }

    /*
   验证金额长度
    */
    public void validateMoneyLength(FacesContext context, UIComponent com, Object object) {
        validateRealNumber(context, com, object);
        Map<String, Object> attrs = com.getAttributes();
        // 由于界面maxlength比数据库字段长度大1
        int length = (Integer) attrs.get(MAXLENGTH) - 1;
        if (object != null && length > 0) {
            String money = (String)object;
            if(!"".equalsIgnoreCase(money.trim())){
            if (String.valueOf(Double.parseDouble(money) * 100).length() > length) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, LENGTH_ERROR, attrs.get(LABEL) + "金额过大！"));
            }
            }
        }
    }

    /*
   验证非必填项是零和非零开头的数字
    */
    public void validateNumber(FacesContext context, UIComponent com, Object object) {
        this.validateRegex(com, object, NUMBER_REGEX, "应为整数！");
    }

    /*
   验证不包含中文
    */
    public void validateNoChinese(FacesContext context, UIComponent com, Object object) {
        this.validateRegex(com, object, NO_CHINESE_REGEX, "不能包含中文！");
    }

    /*
    必须输入电话号码验证
     */
    public void validatePhone(FacesContext context, UIComponent com, Object object) {
        this.validateRegex(com, object, PHONE_REGEX, null);
    }

    /*
   验证只能输入最多有两位小数的正实数
    */
    public void validateRealNumber(FacesContext context, UIComponent com, Object object) {
        this.validateRegex(com, object, REAL_NUMBER_REGEX, "为最多有两位小数的正实数！");
    }


    /*
    根据正则表达式验证jsf组件的输入内容
     */
    private void validateRegex(UIComponent com, Object object, String regex, String msg) {
        if (object != null ) {
            Map<String, Object> attrs = com.getAttributes();
            String value = (String)object;
            if(!"".equalsIgnoreCase(value.trim())){
                if (!this.matchRegex(regex, value)) {
                String showMsg = (msg == null) ? "格式错误！" : msg;
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, attrs.get(LABEL) + showMsg));
                }
            }           
        }
    }

    /*
       根据正则表达式验证
    */
    private boolean matchRegex(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
