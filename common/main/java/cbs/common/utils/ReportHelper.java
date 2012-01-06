package cbs.common.utils;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-12-29
 * Time: 11:36:26
 * To change this template use File | Settings | File Templates.
 */
public class ReportHelper {

    //左对齐
    public static String getLeftSpaceStr(String strValue, int totleBytesLen) {
        if(strValue == null) strValue = "";
        if (strValue.getBytes().length < totleBytesLen) {
            int spacelen = totleBytesLen - strValue.getBytes().length;
            for (int i = 0; i < spacelen; i++) {
                strValue += " ";
            }
        }
        return strValue;
    }
    //右对齐
    public static String getRightSpaceStr(String strValue, int totleBytesLen) {
        if(strValue == null) strValue = "";
        if (strValue.getBytes().length < totleBytesLen) {
            int spacelen = totleBytesLen - strValue.getBytes().length;
            for (int i = 0; i < spacelen; i++) {
                strValue = " " + strValue;
            }
        }
        return strValue;
    }
    //左侧补齐空格
    public static String setLeftSpace(String strValue,int spaceBytesLen) {
        for (int i = 0; i < spaceBytesLen; i++) {
            strValue = " " + strValue;
        }
        return strValue;
    }

    //右侧补齐空格
    public static String setRightSpace(String strValue,int spaceBytesLen) {
        for (int i = 0; i < spaceBytesLen; i++) {
            strValue = strValue + " ";
        }
        return strValue;
    }

    public static void main(String args[]) {
//        SimpleDateFormat dateformat = new SimpleDateFormat("yy");
//        Date dt = new Date();
//        String aa = dateformat.format(dt);
        String a = "12|34";
        String[] strArry = new String[2];
        strArry = a.split("\\|");
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        double i = 0.00;
        double j = 1234522 / 100;
        String t = df0.format(i);
        /*List<String> strLst = new ArrayList();
        strLst.add(0,"a");
        strLst.add(1,"d");
        strLst.add(0,strLst.get(0)+"dda");
        System.out.println(strLst.get(0));*/
        /*String strValue = "介入接";
        int totleBytesLen = 8;
        if (strValue.getBytes().length < totleBytesLen) {
            int spacelen = totleBytesLen - strValue.getBytes().length;
            for (int i=0;i<spacelen;i++) {
                strValue += " ";
            }
            String d = strValue;
        }
       DecimalFormat myformat1 = new DecimalFormat("###,###.00");//使用系统默认的格式
       System.out.println(myformat1.format(11111123456.12));

       Locale.setDefault(Locale.US);
       DecimalFormat myformat2 = new DecimalFormat("###,###.0000");//使用美国的格式
       System.out.println(myformat2.format(111111123456.12));

       //----------------------------also use applypattern------------------------------//

       DecimalFormat myformat3 = new DecimalFormat();
       myformat3.applyPattern("##,###.000");
       System.out.println(myformat3.format(11112345.12345));
    //-----------------控制指数输出-------------------------------------------------//

          DecimalFormat myformat4 = new DecimalFormat();
       myformat4.applyPattern("0.000E0000");
       System.out.println(myformat4.format(10000));
       System.out.println(myformat4.format(12345678.345));
    //------------------百分数的输出-------------------------------------------//

            DecimalFormat myformat5 = null;
       try{
           myformat5 = (DecimalFormat) NumberFormat.getPercentInstance();
       }catch(ClassCastException e)
       {
        System.err.println(e);
       }
       myformat5.applyPattern("00.0000%");
       System.out.println(myformat5.format(0.34567));
       System.out.println(myformat5.format(1.34567));
*/


    }


}