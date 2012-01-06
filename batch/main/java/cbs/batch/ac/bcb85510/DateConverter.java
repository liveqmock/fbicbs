package cbs.batch.ac.bcb85510;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-12-9
 * Time: 15:08:21
 * To change this template use File | Settings | File Templates.
 */
public class DateConverter implements Converter {
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public Object convert(Class type, Object value) {
        // TODO Auto-generated method stub
        if (type.equals(java.util.Date.class)) {
            try {
                return sdf.parse(value.toString());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}