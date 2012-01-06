package cbs.common;

import cbs.common.utils.PropertyManager;
import cbs.repository.code.dao.ActsctMapper;
import cbs.repository.code.model.Actsct;
import cbs.repository.code.model.ActsctExample;
import cbs.repository.platform.dao.PtdeptMapper;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.model.Ptdept;
import cbs.repository.platform.model.Ptenudetail;
import cbs.repository.platform.model.PtenudetailExample;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-16
 * Time: 20:58:24
 * To change this template use File | Settings | File Templates.
 */
/*
 * updated by zhangxiaobo
 * Date: 2011-03-10
 * 新增变量 sdfdate10
  * 新增方法 getBizDate10
  * 新增金额格式化函数
 */
//@ManagedBean(eager = true)
public class SystemService {

    static private SqlSessionFactory sessionFactory = IbatisFactory.ORACLE.getInstance();
    static private SimpleDateFormat sdfdate = new SimpleDateFormat("yyyyMMdd");
    static private SimpleDateFormat sdfdate10 = new SimpleDateFormat("yyyy-MM-dd");
    static private SimpleDateFormat sdfdateYM = new SimpleDateFormat("yyyy年MM月");
    static private SimpleDateFormat sdftime6 = new SimpleDateFormat("HHmmss");
    static private SimpleDateFormat sdftime8 = new SimpleDateFormat("HH:mm:ss");

    public static String getSysidtAC() {
        return "8";
    }

    public static String getBatchResultFilePath(){
        return PropertyManager.getProperty("BATCH_RESULT_FILE_PATH");
    }

    //获取chkflag（新老传票余额透支检查标志）
    public static String getChkflag() {
        SqlSession session = sessionFactory.openSession();
        PtenudetailMapper enudetailmap = session.getMapper(PtenudetailMapper.class);
        PtenudetailExample enudetailexm = new PtenudetailExample();
        enudetailexm.clear();
        enudetailexm.createCriteria().andEnutypeEqualTo("CHKALLVCH");
        List<Ptenudetail> enudetailList = enudetailmap.selectByExample(enudetailexm);
        Ptenudetail enudetail = enudetailList.get(0);
        return enudetail.getEnuitemvalue();
    }

    // 获取机构名
    public static String getDepnamByDeptid(String deptId) {
         String deptNam = null;
        SqlSession session = sessionFactory.openSession();
        PtdeptMapper mapper = session.getMapper(PtdeptMapper.class);
        Ptdept dept = mapper.selectByPrimaryKey(deptId);
        if(dept != null) {
            deptNam = dept.getDeptname();
        }
        return deptNam;
    }

     /*
    * 获取满页最大行数*/
    public static int getLineLimit() {
        SqlSession session = sessionFactory.openSession();
        PtenudetailMapper enudetailmap = session.getMapper(PtenudetailMapper.class);
        PtenudetailExample enudetailexm = new PtenudetailExample();
        enudetailexm.clear();
        enudetailexm.createCriteria().andEnutypeEqualTo("LINELIMIT");
        List<Ptenudetail> enudetailList = enudetailmap.selectByExample(enudetailexm);
        Ptenudetail enudetail = enudetailList.get(0);
        return Integer.parseInt(enudetail.getEnuitemvalue());
    }

     //TODO 系统中文字体
    public static String getPdfFont() {
        return "c:\\windows\\fonts\\simsun.ttc,1";
    }

    public static String getBizDate(){
        //TODO
        String result = "";
        SqlSession session = sessionFactory.openSession();
        try {
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct sct = mapper.selectByExample(example).get(0);
            result = sdfdate.format(sct.getCrndat());
        }catch (Exception e){
            throw new RuntimeException("获取系统业务时间错误！");
        }
        return result;
    }

    public static String getBizDate10(){
        String result = "";
        SqlSession session = sessionFactory.openSession();
        try {
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct  sct = mapper.selectByExample(example).get(0);
            result = sdfdate10.format(sct.getCrndat());
        }catch (Exception e) {
            throw new RuntimeException("获取系统业务时间错误！");
        }
        return result;
    }

    public static String getBizDateYM(){
        String result = "";
        SqlSession session = sessionFactory.openSession();
        try {
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct  sct = mapper.selectByExample(example).get(0);
            result = sdfdateYM.format(sct.getCrndat());
        }catch (Exception e){
            throw new RuntimeException("获取系统业务时间错误！");
        }
        return result;
    }

    public static Date getIpydat(){
        Date ipydat = null;
        SqlSession session = sessionFactory.openSession();
        try {
            ActsctMapper mapper = session.getMapper(ActsctMapper.class);
            ActsctExample example = new ActsctExample();
            example.createCriteria().andSctnumEqualTo(Short.parseShort(getSysidtAC()));
            Actsct  sct = mapper.selectByExample(example).get(0);
            ipydat = sct.getIpydat();
        }catch (Exception e){
            throw new RuntimeException("获取系统业务时间错误！");
        }
        return ipydat;
    }

    /**
     * 例："9999.88" ->  "9,999.88"
     * @param str
     * @return
     */
    public static String formatStrAmt(String str) {
        NumberFormat n = NumberFormat.getNumberInstance();
        double d = 0.00;
        String outStr = null;
        try {
             d = Double.parseDouble(str);
             outStr = n.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outStr;
    }

    public static short getBizYear2(){
        String bizdate = getBizDate();
        return Short.parseShort(bizdate.substring(2,4));
    }

    public static String getBizTime6(){
         return sdftime6.format(new Date());
    }
    public static String getBizTime8(){
         return sdftime8.format(new Date());
    }

}
