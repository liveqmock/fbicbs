package cbs.common.utils;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-2-24
 * Time: 16:07:53
 * To change this template use File | Settings | File Templates.
 */
public class DataFormater {
    public static String formatNum(Short decpos,long num) {
        DecimalFormat df0 = new DecimalFormat("###,##0");
        DecimalFormat df1 = new DecimalFormat("###,##0.0");
        DecimalFormat df2 = new DecimalFormat("###,##0.00");
        DecimalFormat df3 = new DecimalFormat("###,##0,000");
        String strNum = "";
        if (decpos == 2) {
            double dbNum = (double)num/100;
            strNum = df2.format(dbNum);
        } else if (decpos == 3) {
            double dbNum = (double)num/1000;
            strNum = df3.format(dbNum);
        } else if (decpos == 1) {
            double dbNum = (double)num/10;
            strNum = df1.format(dbNum);
        } else if (decpos == 0) {
            strNum = df0.format(num);
        }
        return strNum;
    }
    public static String formatNum(Short decpos,double num) {
        DecimalFormat df0 = new DecimalFormat("###,##0");
        DecimalFormat df1 = new DecimalFormat("###,##0.0");
        DecimalFormat df2 = new DecimalFormat("###,##0.00");
        DecimalFormat df3 = new DecimalFormat("###,##0,000");
        String strNum = "";
        if (decpos == 2) {
            double dbNum = (double)num/100;
            strNum = df2.format(dbNum);
        } else if (decpos == 3) {
            double dbNum = (double)num/1000;
            strNum = df3.format(dbNum);
        } else if (decpos == 1) {
            double dbNum = (double)num/10;
            strNum = df1.format(dbNum);
        } else if (decpos == 0) {
            strNum = df0.format(num);
        }
        return strNum;
    }
}
