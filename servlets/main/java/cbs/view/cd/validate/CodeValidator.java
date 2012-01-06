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
    private static final String INPUT_ERROR = "���������";
    private static final String LENGTH_ERROR = "���ȼ�����";
    // ������λС������ʵ��
    private static final String REAL_NUMBER_REGEX =  "^(0|[1-9][0-9]*)|[0-9]+(.[0-9]{0,2})$";

    // ��֤�������Ƿ����
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
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "�ú������Ѵ��ڣ�"));
        }
    }

    //��֤�������Ƿ����
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
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "���������Ѵ��ڣ�"));
        }
    }

    /*
       ������������֤���볤�� �����������б������maxlength �� label
    */
    public void mustValidateLength(FacesContext context, UIComponent com, Object object) {

        Map<String, Object> attrs = com.getAttributes();
        String value = (String) object;
        if (value == null || value.trim().equals("")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "��������" + attrs.get(LABEL) + "!"));
        } else {
            int length = (Integer) attrs.get(MAXLENGTH);
            if (value.getBytes().length > length) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, LENGTH_ERROR, attrs.get(LABEL) + "�������ݳ�����"));
            }
        }
    }

    /*
       ��ѡ��������֤���볤�� �����������б������maxlength �� label
    */
    public void orValidateLength(FacesContext context, UIComponent com, Object object) {
        Map<String, Object> attrs = com.getAttributes();
        int length = (Integer) attrs.get(MAXLENGTH);
        if (object != null && length > 0) {
            String value = (String) (object+"");
            if (value.getBytes().length > length) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, LENGTH_ERROR, attrs.get(LABEL) + "�������ݳ�����"));
            }
        }
    }
    /*
   ��ֻ֤�������������λС������ʵ��
    */
    public void validateRealNumber(FacesContext context, UIComponent com, Object object) {
        this.orValidateLength(context,com,object.toString());
        this.validateRegex(com, object.toString(), REAL_NUMBER_REGEX, null);
    }
     /*
    ����������ʽ��֤jsf�������������
     */
    private void validateRegex(UIComponent com, Object object, String regex, String msg) {
        if (object != null ) {
            Map<String, Object> attrs = com.getAttributes();
            String value = (String)object;
            if(!"".equalsIgnoreCase(value.trim())){
                if (!this.matchRegex(regex, value)) {
                String showMsg = (msg == null) ? "��ʽ����" : msg;
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, attrs.get(LABEL) + showMsg));
                }
            }
        }
    }

    /*
       ����������ʽ��֤
    */
    private boolean matchRegex(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
