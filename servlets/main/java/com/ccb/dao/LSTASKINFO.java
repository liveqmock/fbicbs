package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class LSTASKINFO extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new LSTASKINFO().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new LSTASKINFO().findAndLockByWhere(sSqlWhere);      }       public static LSTASKINFO findFirst(String sSqlWhere) {           return (LSTASKINFO)new LSTASKINFO().findFirstByWhere(sSqlWhere);      }       public static LSTASKINFO findFirstAndLock(String sSqlWhere) {           return (LSTASKINFO)new LSTASKINFO().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new LSTASKINFO().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           LSTASKINFO bean = new LSTASKINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           LSTASKINFO bean = new LSTASKINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static LSTASKINFO findFirst(String sSqlWhere,boolean isAutoRelease) {           LSTASKINFO bean = new LSTASKINFO();           bean.setAutoRelease(isAutoRelease);           return (LSTASKINFO)bean.findFirstByWhere(sSqlWhere);      }       public static LSTASKINFO findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           LSTASKINFO bean = new LSTASKINFO();           bean.setAutoRelease(isAutoRelease);           return (LSTASKINFO)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           LSTASKINFO bean = new LSTASKINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new LSTASKINFO().findByWhereByRow(sSqlWhere,row);      } String taskid;
String tablename;
String tablepk;
String tasktype;
String tasktime;
String operid;
String remarks;
public static final String TABLENAME ="ls_taskinfo";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
LSTASKINFO abb = new LSTASKINFO();
abb.taskid=rs.getString("taskid");abb.setKeyValue("TASKID",""+abb.getTaskid());
abb.tablename=rs.getString("tablename");abb.setKeyValue("TABLENAME",""+abb.getTablename());
abb.tablepk=rs.getString("tablepk");abb.setKeyValue("TABLEPK",""+abb.getTablepk());
abb.tasktype=rs.getString("tasktype");abb.setKeyValue("TASKTYPE",""+abb.getTasktype());
abb.tasktime=rs.getTimeString("tasktime");abb.setKeyValue("TASKTIME",""+abb.getTasktime());
abb.operid=rs.getString("operid");abb.setKeyValue("OPERID",""+abb.getOperid());
abb.remarks=rs.getString("remarks");abb.setKeyValue("REMARKS",""+abb.getRemarks());
list.add(abb);
abb.operate_mode = "edit";
}public String getTaskid() { if ( this.taskid == null ) return ""; return this.taskid;}
public String getTablename() { if ( this.tablename == null ) return ""; return this.tablename;}
public String getTablepk() { if ( this.tablepk == null ) return ""; return this.tablepk;}
public String getTasktype() { if ( this.tasktype == null ) return ""; return this.tasktype;}
public String getTasktime() {  if ( this.tasktime == null ) { return ""; } else { return this.tasktime.trim().split(" ")[0];} }public String getTasktimeTime() {  if ( this.tasktime == null ) return ""; return this.tasktime.split("\\.")[0];}
public String getOperid() { if ( this.operid == null ) return ""; return this.operid;}
public String getRemarks() { if ( this.remarks == null ) return ""; return this.remarks;}
public void setTaskid(String taskid) { sqlMaker.setField("taskid",taskid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTaskid().equals(taskid)) cf.add("taskid",this.taskid,taskid); } this.taskid=taskid;}
public void setTablename(String tablename) { sqlMaker.setField("tablename",tablename,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTablename().equals(tablename)) cf.add("tablename",this.tablename,tablename); } this.tablename=tablename;}
public void setTablepk(String tablepk) { sqlMaker.setField("tablepk",tablepk,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTablepk().equals(tablepk)) cf.add("tablepk",this.tablepk,tablepk); } this.tablepk=tablepk;}
public void setTasktype(String tasktype) { sqlMaker.setField("tasktype",tasktype,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTasktype().equals(tasktype)) cf.add("tasktype",this.tasktype,tasktype); } this.tasktype=tasktype;}
public void setTasktime(String tasktime) { sqlMaker.setField("tasktime",tasktime,Field.DATE); if (this.operate_mode.equals("edit")) { if (!this.getTasktime().equals(tasktime)) cf.add("tasktime",this.tasktime,tasktime); } this.tasktime=tasktime;}
public void setOperid(String operid) { sqlMaker.setField("operid",operid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOperid().equals(operid)) cf.add("operid",this.operid,operid); } this.operid=operid;}
public void setRemarks(String remarks) { sqlMaker.setField("remarks",remarks,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRemarks().equals(remarks)) cf.add("remarks",this.remarks,remarks); } this.remarks=remarks;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"taskid") !=null ) {this.setTaskid(actionRequest.getFieldValue(i,"taskid"));}
if ( actionRequest.getFieldValue(i,"tablename") !=null ) {this.setTablename(actionRequest.getFieldValue(i,"tablename"));}
if ( actionRequest.getFieldValue(i,"tablepk") !=null ) {this.setTablepk(actionRequest.getFieldValue(i,"tablepk"));}
if ( actionRequest.getFieldValue(i,"tasktype") !=null ) {this.setTasktype(actionRequest.getFieldValue(i,"tasktype"));}
if ( actionRequest.getFieldValue(i,"tasktime") !=null ) {this.setTasktime(actionRequest.getFieldValue(i,"tasktime"));}
if ( actionRequest.getFieldValue(i,"operid") !=null ) {this.setOperid(actionRequest.getFieldValue(i,"operid"));}
if ( actionRequest.getFieldValue(i,"remarks") !=null ) {this.setRemarks(actionRequest.getFieldValue(i,"remarks"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { LSTASKINFO obj = (LSTASKINFO)super.clone();obj.setTaskid(obj.taskid);
obj.setTablename(obj.tablename);
obj.setTablepk(obj.tablepk);
obj.setTasktype(obj.tasktype);
obj.setTasktime(obj.tasktime);
obj.setOperid(obj.operid);
obj.setRemarks(obj.remarks);
return obj;}}