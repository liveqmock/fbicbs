package cbs.view.cd.validate;

import cbs.common.IbatisFactory;
import cbs.common.enums.ACEnum;
import cbs.repository.code.dao.ActapcMapper;
import cbs.repository.code.dao.ActglcMapper;
import cbs.repository.code.model.ActapcExample;
import cbs.repository.code.model.ActglcExample;
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

@ManagedBean(name = "codeValidator")
@RequestScoped
public class CodeValidator {

    private static final String MAXLENGTH = "maxlength";
    private static final String LABEL = "label";
    private static final String INPUT_ERROR = "输入检查错误";
    private static final String LENGTH_ERROR = "长度检查错误";
    // 至多两位小数的正实数
    private static final String REAL_NUMBER_REGEX =  "^(0|[1-9][0-9]*)|[0-9]+(.[0-9]{0,2})$";

    // 验证核算码是否存在
    public void checkExistApcode(FacesContext context, UIComponent com, Object object) {
        mustValidateLength(context, com, object);
        SqlSession session = IbatisFactory.ORACLE.getInstance().openSession();
        ActapcMapper apcMapper = session.getMapper(ActapcMapper.class);
        String code = (String) object;
        ActapcExample apcExample = new ActapcExample();
        apcExample.createCriteria().andApcodeEqualTo(code.trim()).andRecstsEqualTo(ACEnum.RECSTS_VALID.getStatus());
        int cnt = 0;
        cnt = apcMapper.countByExample(apcExample);
        if (cnt > 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "该核算码已存在！"));
        }
    }

    //验证总账码是否存在
    public void checkExistGlcode(FacesContext context, UIComponent com, Object object) {
        mustValidateLength(context, com, object);
        SqlSession session = IbatisFactory.ORACLE.getInstance().openSession();
        ActglcMapper glcMapper = session.getMapper(ActglcMapper.class);
        String code = (String) object;
        ActglcExample glcExample = new ActglcExample();
        glcExample.createCriteria().andGlcodeEqualTo(code.trim()).andRecstsEqualTo(ACEnum.RECSTS_VALID.getStatus());
        int cnt = 0;
        cnt = glcMapper.countByExample(glcExample);
        if (cnt > 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "该总账码已存在！"));
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
            String value = (String) (object+"");
            if (value.getBytes().length > length) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, LENGTH_ERROR, attrs.get(LABEL) + "输入内容超长！"));
            }
        }
    }
    /*
   验证只能输入最多有两位小数的正实数
    */
    public void validateRealNumber(FacesContext context, UIComponent com, Object object) {
        this.orValidateLength(context,com,object.toString());
        this.validateRegex(com, object.toString(), REAL_NUMBER_REGEX, null);
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
