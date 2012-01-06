package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class LSBDGAGENCYH extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new LSBDGAGENCYH().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new LSBDGAGENCYH().findAndLockByWhere(sSqlWhere);      }       public static LSBDGAGENCYH findFirst(String sSqlWhere) {           return (LSBDGAGENCYH)new LSBDGAGENCYH().findFirstByWhere(sSqlWhere);      }       public static LSBDGAGENCYH findFirstAndLock(String sSqlWhere) {           return (LSBDGAGENCYH)new LSBDGAGENCYH().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new LSBDGAGENCYH().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           LSBDGAGENCYH bean = new LSBDGAGENCYH();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           LSBDGAGENCYH bean = new LSBDGAGENCYH();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static LSBDGAGENCYH findFirst(String sSqlWhere,boolean isAutoRelease) {           LSBDGAGENCYH bean = new LSBDGAGENCYH();           bean.setAutoRelease(isAutoRelease);           return (LSBDGAGENCYH)bean.findFirstByWhere(sSqlWhere);      }       public static LSBDGAGENCYH findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           LSBDGAGENCYH bean = new LSBDGAGENCYH();           bean.setAutoRelease(isAutoRelease);           return (LSBDGAGENCYH)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           LSBDGAGENCYH bean = new LSBDGAGENCYH();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new LSBDGAGENCYH().findByWhereByRow(sSqlWhere,row);      } String areacode;
String code;
String name;
String guid;
String levelno;
String supercode;
String isleaf;
int version;
String action;
String actiondate;
String actiontime;
String remark;
String operid;
String operdate;
public static final String TABLENAME ="ls_bdgagency_h";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
LSBDGAGENCYH abb = new LSBDGAGENCYH();
abb.areacode=rs.getString("areacode");abb.setKeyValue("AREACODE",""+abb.getAreacode());
abb.code=rs.getString("code");abb.setKeyValue("CODE",""+abb.getCode());
abb.name=rs.getString("name");abb.setKeyValue("NAME",""+abb.getName());
abb.guid=rs.getString("guid");abb.setKeyValue("GUID",""+abb.getGuid());
abb.levelno=rs.getString("levelno");abb.setKeyValue("LEVELNO",""+abb.getLevelno());
abb.supercode=rs.getString("supercode");abb.setKeyValue("SUPERCODE",""+abb.getSupercode());
abb.isleaf=rs.getString("isleaf");abb.setKeyValue("ISLEAF",""+abb.getIsleaf());
abb.version=rs.getInt("version");abb.setKeyValue("VERSION",""+abb.getVersion());
abb.action=rs.getString("action");abb.setKeyValue("ACTION",""+abb.getAction());
abb.actiondate=rs.getString("actiondate");abb.setKeyValue("ACTIONDATE",""+abb.getActiondate());
abb.actiontime=rs.getString("actiontime");abb.setKeyValue("ACTIONTIME",""+abb.getActiontime());
abb.remark=rs.getString("remark");abb.setKeyValue("REMARK",""+abb.getRemark());
abb.operid=rs.getString("operid");abb.setKeyValue("OPERID",""+abb.getOperid());
abb.operdate=rs.getTimeString("operdate");abb.setKeyValue("OPERDATE",""+abb.getOperdate());
list.add(abb);
abb.operate_mode = "edit";
}public String getAreacode() { if ( this.areacode == null ) return ""; return this.areacode;}
public String getCode() { if ( this.code == null ) return ""; return this.code;}
public String getName() { if ( this.name == null ) return ""; return this.name;}
public String getGuid() { if ( this.guid == null ) return ""; return this.guid;}
public String getLevelno() { if ( this.levelno == null ) return ""; return this.levelno;}
public String getSupercode() { if ( this.supercode == null ) return ""; return this.supercode;}
public String getIsleaf() { if ( this.isleaf == null ) return ""; return this.isleaf;}
public int getVersion() { return this.version;}
public String getAction() { if ( this.action == null ) return ""; return this.action;}
public String getActiondate() { if ( this.actiondate == null ) return ""; return this.actiondate;}
public String getActiontime() { if ( this.actiontime == null ) return ""; return this.actiontime;}
public String getRemark() { if ( this.remark == null ) return ""; return this.remark;}
public String getOperid() { if ( this.operid == null ) return ""; return this.operid;}
public String getOperdate() {  if ( this.operdate == null ) { return ""; } else { return this.operdate.trim().split(" ")[0];} }public String getOperdateTime() {  if ( this.operdate == null ) return ""; return this.operdate.split("\\.")[0];}
public void setAreacode(String areacode) { sqlMaker.setField("areacode",areacode,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAreacode().equals(areacode)) cf.add("areacode",this.areacode,areacode); } this.areacode=areacode;}
public void setCode(String code) { sqlMaker.setField("code",code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCode().equals(code)) cf.add("code",this.code,code); } this.code=code;}
public void setName(String name) { sqlMaker.setField("name",name,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getName().equals(name)) cf.add("name",this.name,name); } this.name=name;}
public void setGuid(String guid) { sqlMaker.setField("guid",guid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getGuid().equals(guid)) cf.add("guid",this.guid,guid); } this.guid=guid;}
public void setLevelno(String levelno) { sqlMaker.setField("levelno",levelno,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLevelno().equals(levelno)) cf.add("levelno",this.levelno,levelno); } this.levelno=levelno;}
public void setSupercode(String supercode) { sqlMaker.setField("supercode",supercode,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSupercode().equals(supercode)) cf.add("supercode",this.supercode,supercode); } this.supercode=supercode;}
public void setIsleaf(String isleaf) { sqlMaker.setField("isleaf",isleaf,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getIsleaf().equals(isleaf)) cf.add("isleaf",this.isleaf,isleaf); } this.isleaf=isleaf;}
public void setVersion(int version) { sqlMaker.setField("version",""+version,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getVersion()!=version) cf.add("version",this.version+"",version+""); } this.version=version;}
public void setAction(String action) { sqlMaker.setField("action",action,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAction().equals(action)) cf.add("action",this.action,action); } this.action=action;}
public void setActiondate(String actiondate) { sqlMaker.setField("actiondate",actiondate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getActiondate().equals(actiondate)) cf.add("actiondate",this.actiondate,actiondate); } this.actiondate=actiondate;}
public void setActiontime(String actiontime) { sqlMaker.setField("actiontime",actiontime,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getActiontime().equals(actiontime)) cf.add("actiontime",this.actiontime,actiontime); } this.actiontime=actiontime;}
public void setRemark(String remark) { sqlMaker.setField("remark",remark,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRemark().equals(remark)) cf.add("remark",this.remark,remark); } this.remark=remark;}
public void setOperid(String operid) { sqlMaker.setField("operid",operid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOperid().equals(operid)) cf.add("operid",this.operid,operid); } this.operid=operid;}
public void setOperdate(String operdate) { sqlMaker.setField("operdate",operdate,Field.DATE); if (this.operate_mode.equals("edit")) { if (!this.getOperdate().equals(operdate)) cf.add("operdate",this.operdate,operdate); } this.operdate=operdate;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"areacode") !=null ) {this.setAreacode(actionRequest.getFieldValue(i,"areacode"));}
if ( actionRequest.getFieldValue(i,"code") !=null ) {this.setCode(actionRequest.getFieldValue(i,"code"));}
if ( actionRequest.getFieldValue(i,"name") !=null ) {this.setName(actionRequest.getFieldValue(i,"name"));}
if ( actionRequest.getFieldValue(i,"guid") !=null ) {this.setGuid(actionRequest.getFieldValue(i,"guid"));}
if ( actionRequest.getFieldValue(i,"levelno") !=null ) {this.setLevelno(actionRequest.getFieldValue(i,"levelno"));}
if ( actionRequest.getFieldValue(i,"supercode") !=null ) {this.setSupercode(actionRequest.getFieldValue(i,"supercode"));}
if ( actionRequest.getFieldValue(i,"isleaf") !=null ) {this.setIsleaf(actionRequest.getFieldValue(i,"isleaf"));}
if ( actionRequest.getFieldValue(i,"version") !=null && actionRequest.getFieldValue(i,"version").trim().length() > 0 ) {this.setVersion(Integer.parseInt(actionRequest.getFieldValue(i,"version")));}
if ( actionRequest.getFieldValue(i,"action") !=null ) {this.setAction(actionRequest.getFieldValue(i,"action"));}
if ( actionRequest.getFieldValue(i,"actiondate") !=null ) {this.setActiondate(actionRequest.getFieldValue(i,"actiondate"));}
if ( actionRequest.getFieldValue(i,"actiontime") !=null ) {this.setActiontime(actionRequest.getFieldValue(i,"actiontime"));}
if ( actionRequest.getFieldValue(i,"remark") !=null ) {this.setRemark(actionRequest.getFieldValue(i,"remark"));}
if ( actionRequest.getFieldValue(i,"operid") !=null ) {this.setOperid(actionRequest.getFieldValue(i,"operid"));}
if ( actionRequest.getFieldValue(i,"operdate") !=null ) {this.setOperdate(actionRequest.getFieldValue(i,"operdate"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { LSBDGAGENCYH obj = (LSBDGAGENCYH)super.clone();obj.setAreacode(obj.areacode);
obj.setCode(obj.code);
obj.setName(obj.name);
obj.setGuid(obj.guid);
obj.setLevelno(obj.levelno);
obj.setSupercode(obj.supercode);
obj.setIsleaf(obj.isleaf);
obj.setVersion(obj.version);
obj.setAction(obj.action);
obj.setActiondate(obj.actiondate);
obj.setActiontime(obj.actiontime);
obj.setRemark(obj.remark);
obj.setOperid(obj.operid);
obj.setOperdate(obj.operdate);
return obj;}}