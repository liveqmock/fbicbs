package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class LSSYSINFO extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new LSSYSINFO().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new LSSYSINFO().findAndLockByWhere(sSqlWhere);      }       public static LSSYSINFO findFirst(String sSqlWhere) {           return (LSSYSINFO)new LSSYSINFO().findFirstByWhere(sSqlWhere);      }       public static LSSYSINFO findFirstAndLock(String sSqlWhere) {           return (LSSYSINFO)new LSSYSINFO().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new LSSYSINFO().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           LSSYSINFO bean = new LSSYSINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           LSSYSINFO bean = new LSSYSINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static LSSYSINFO findFirst(String sSqlWhere,boolean isAutoRelease) {           LSSYSINFO bean = new LSSYSINFO();           bean.setAutoRelease(isAutoRelease);           return (LSSYSINFO)bean.findFirstByWhere(sSqlWhere);      }       public static LSSYSINFO findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           LSSYSINFO bean = new LSSYSINFO();           bean.setAutoRelease(isAutoRelease);           return (LSSYSINFO)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           LSSYSINFO bean = new LSSYSINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new LSSYSINFO().findByWhereByRow(sSqlWhere,row);      } String areacode;
int bdgagencyversion;
String businessdate;
public static final String TABLENAME ="ls_sysinfo";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
LSSYSINFO abb = new LSSYSINFO();
abb.areacode=rs.getString("areacode");abb.setKeyValue("AREACODE",""+abb.getAreacode());
abb.bdgagencyversion=rs.getInt("bdgagencyversion");abb.setKeyValue("BDGAGENCYVERSION",""+abb.getBdgagencyversion());
abb.businessdate=rs.getString("businessdate");abb.setKeyValue("BUSINESSDATE",""+abb.getBusinessdate());
list.add(abb);
abb.operate_mode = "edit";
}public String getAreacode() { if ( this.areacode == null ) return ""; return this.areacode;}
public int getBdgagencyversion() { return this.bdgagencyversion;}
public String getBusinessdate() { if ( this.businessdate == null ) return ""; return this.businessdate;}
public void setAreacode(String areacode) { sqlMaker.setField("areacode",areacode,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAreacode().equals(areacode)) cf.add("areacode",this.areacode,areacode); } this.areacode=areacode;}
public void setBdgagencyversion(int bdgagencyversion) { sqlMaker.setField("bdgagencyversion",""+bdgagencyversion,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getBdgagencyversion()!=bdgagencyversion) cf.add("bdgagencyversion",this.bdgagencyversion+"",bdgagencyversion+""); } this.bdgagencyversion=bdgagencyversion;}
public void setBusinessdate(String businessdate) { sqlMaker.setField("businessdate",businessdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBusinessdate().equals(businessdate)) cf.add("businessdate",this.businessdate,businessdate); } this.businessdate=businessdate;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"areacode") !=null ) {this.setAreacode(actionRequest.getFieldValue(i,"areacode"));}
if ( actionRequest.getFieldValue(i,"bdgagencyversion") !=null && actionRequest.getFieldValue(i,"bdgagencyversion").trim().length() > 0 ) {this.setBdgagencyversion(Integer.parseInt(actionRequest.getFieldValue(i,"bdgagencyversion")));}
if ( actionRequest.getFieldValue(i,"businessdate") !=null ) {this.setBusinessdate(actionRequest.getFieldValue(i,"businessdate"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { LSSYSINFO obj = (LSSYSINFO)super.clone();obj.setAreacode(obj.areacode);
obj.setBdgagencyversion(obj.bdgagencyversion);
obj.setBusinessdate(obj.businessdate);
return obj;}}