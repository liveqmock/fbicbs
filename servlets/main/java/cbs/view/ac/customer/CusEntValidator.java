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
    private static final String INPUT_ERROR = "���������";
    private static final String LENGTH_ERROR = "���ȼ�����";
    // ������
    private static final String NO_CHINESE_REGEX = "^[^\\u4e00-\\u9fa5]{0,}$";
    /*�绰����ƥ���ʽ��
    11λ�ֻ����� 3-4λ���ţ�7-8λֱ�����룬1��4λ�ֻ��� �磺12345678901��1234-12345678-1234
    */
    private static final String PHONE_REGEX = "(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$";
     // ������λС������ʵ��
    private static final String REAL_NUMBER_REGEX =  "^(0|[1-9][0-9]*)|[0-9]+(.[0-9]{0,2})$";
          //   "^[0-9]+(.[0-9]{0,2})$";
    // ��ͷ��㿪ͷ������
    private static final String NUMBER_REGEX = "^(0|[1-9][0-9]*)$";

    /*
    ��֤�ͻ����Ƿ�Ϊ�ջ��Ѵ���
    */
    public void validateCusidt(FacesContext context, UIComponent com, Object object) {
        SqlSession session = IbatisFactory.ORACLE.getInstance().openSession();
        ActcusMapper cusMapper = session.getMapper(ActcusMapper.class);
        String cusidt = (String) object;
        if (cusidt == null || "".equalsIgnoreCase(cusidt.trim()) || "0000000".equalsIgnoreCase(cusidt)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "��������ȷ�Ŀͻ��ţ�"));            
        } else {
            ActcusExample example = new ActcusExample();
            example.createCriteria().andCusidtEqualTo(cusidt.trim());
            int cnt = 0;
            cnt = cusMapper.countByExample(example);
            if (cnt > 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INPUT_ERROR, "�ÿͻ����Ѵ��ڣ�"));
            }
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
            String value = (String) object;
            if (value.getBytes().length > length) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, LENGTH_ERROR, attrs.get(LABEL) + "�������ݳ�����"));
            }
        }
    }

    /*
   ��֤�Ǳ���������Ҳ���������
    */
    public void validateRealNumberAndLength(FacesContext context, UIComponent com, Object object) {
        orValidateLength(context, com, object);
        validateRealNumber(context, com, object);
    }

    /*
   ��֤�Ǳ���������Ҳ���������
    */
    public void validateNoChineseAndLength(FacesContext context, UIComponent com, Object object) {
        orValidateLength(context, com, object);
        validateNoChinese(context, com, object);
    }

    /*
   ��֤������������ǵ绰����
    */
    public void validatePhoneAndLength(FacesContext context, UIComponent com, Object object) {
        mustValidateLength(context, com, object);
        validatePhone(context, com, object);
    }

    /*
   ��֤����
    */
    public void validateMoneyLength(FacesContext context, UIComponent com, Object object) {
        validateRealNumber(context, com, object);
        Map<String, Object> attrs = com.getAttributes();
        // ���ڽ���maxlength�����ݿ��ֶγ��ȴ�1
        int length = (Integer) attrs.get(MAXLENGTH) - 1;
        if (object != null && length > 0) {
            String money = (String)object;
            if(!"".equalsIgnoreCase(money.trim())){
            if (String.valueOf(Double.parseDouble(money) * 100).length() > length) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, LENGTH_ERROR, attrs.get(LABEL) + "������"));
            }
            }
        }
    }

    /*
   ��֤�Ǳ���������ͷ��㿪ͷ������
    */
    public void validateNumber(FacesContext context, UIComponent com, Object object) {
        this.validateRegex(com, object, NUMBER_REGEX, "ӦΪ������");
    }

    /*
   ��֤����������
    */
    public void validateNoChinese(FacesContext context, UIComponent com, Object object) {
        this.validateRegex(com, object, NO_CHINESE_REGEX, "���ܰ������ģ�");
    }

    /*
    ��������绰������֤
     */
    public void validatePhone(FacesContext context, UIComponent com, Object object) {
        this.validateRegex(com, object, PHONE_REGEX, null);
    }

    /*
   ��ֻ֤�������������λС������ʵ��
    */
    public void validateRealNumber(FacesContext context, UIComponent com, Object object) {
        this.validateRegex(com, object, REAL_NUMBER_REGEX, "Ϊ�������λС������ʵ����");
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
