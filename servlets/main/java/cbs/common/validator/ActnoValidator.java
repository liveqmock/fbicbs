package cbs.common.validator;

import cbs.common.IbatisFactory;
import cbs.repository.code.dao.ActaniMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-14
 * Time: 17:09:01
 * To change this template use File | Settings | File Templates.
 */
@FacesValidator(value = "actnoValidator")
public class ActnoValidator implements Validator {

//    static private SqlSessionFactory sessionFactory = IbatisFactory.ORACLE.getInstance();

    public void validate(FacesContext context, UIComponent component,
                         Object value) throws ValidatorException {
        String actno = (String) value;
        if (StringUtils.isBlank(actno)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "’ ∫≈ºÏ≤È¥ÌŒÛ", "’ ∫≈Œ™ø’£°"));
        }
        
        int count = 0;
        try {
            SqlSessionFactory sessionFactory = IbatisFactory.ORACLE.getInstance();
            SqlSession session = sessionFactory.openSession();
            ActaniMapper mapper = session.getMapper(ActaniMapper.class);
            count = mapper.countByOldacn(actno.trim());
        } catch (Exception e) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "’ ∫≈ºÏ≤È¥ÌŒÛ", actno));
        }

        if (count == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "’ ∫≈ºÏ≤È¥ÌŒÛ", "¥À’ ∫≈≤ª¥Ê‘⁄£°"));
        }
    }

}