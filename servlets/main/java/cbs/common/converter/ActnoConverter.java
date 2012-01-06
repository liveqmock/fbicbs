package cbs.common.converter;

import org.apache.commons.lang.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-14
 * Time: 8:11:50
 * To change this template use File | Settings | File Templates.
 */
@FacesConverter(value = "actnoConverter")
public class ActnoConverter implements Converter {
    /**
     * <p>Convert the specified string value, which is associated with
     * the specified {@link javax.faces.component.UIComponent}, into a model data object that
     * is appropriate for being stored during the <em>Apply Request
     * Values</em> phase of the request processing lifecycle.</p>
     *
     * @param context   {@link javax.faces.context.FacesContext} for the request being processed
     * @param component {@link javax.faces.component.UIComponent} with which this model object
     *                  value is associated
     * @param value     String value to be converted (may be <code>null</code>)
     * @return <code>null</code> if the value to convert is <code>null</code>,
     *         otherwise the result of the conversion
     * @throws javax.faces.convert.ConverterException
     *                              if conversion cannot be successfully
     *                              performed
     * @throws NullPointerException if <code>context</code> or
     *                              <code>component</code> is <code>null</code>
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (StringUtils.isNotEmpty(value)) {
            try {
                if (value.length() == 14) {
                    return value.substring(0, 7) + "-" + value.substring(7, 11) + "-" + value.substring(11, 14);
                }else{
                    return value.substring(0, 3) + "-" + value.substring(3);
                }
            } catch (Exception e) {
                FacesMessage message = new FacesMessage();
                message.setDetail("ÕÊºÅ×ª»»´íÎó£¡");
                message.setSummary("×ª»»´íÎó:" + value);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ConverterException(message, e);
            }
        }
        return null;
    }

    /**
     * <p>Convert the specified model object value, which is associated with
     * the specified {@link javax.faces.component.UIComponent}, into a String that is suitable
     * for being included in the response generated during the
     * <em>Render Response</em> phase of the request processing
     * lifeycle.</p>
     *
     * @param context   {@link javax.faces.context.FacesContext} for the request being processed
     * @param component {@link javax.faces.component.UIComponent} with which this model object
     *                  value is associated
     * @param value     Model object value to be converted
     *                  (may be <code>null</code>)
     * @return a zero-length String if value is <code>null</code>,
     *         otherwise the result of the conversion
     * @throws javax.faces.convert.ConverterException
     *                              if conversion cannot be successfully
     *                              performed
     * @throws NullPointerException if <code>context</code> or
     *                              <code>component</code> is <code>null</code>
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            try {
                String actno = value.toString();
                if (actno.length() == 14) {
                    return actno.substring(0, 7) + actno.substring(8, 12) + actno.substring(13, 16);
                }else{
                    return actno.substring(0, 3) + actno.substring(4);
                }
            } catch (Exception e) {
                throw new ConverterException("ÕÊºÅ×ª»»´íÎó£¡", e);
            }
        }
        return null;
    }
}
