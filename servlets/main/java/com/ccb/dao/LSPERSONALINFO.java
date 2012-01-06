package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class LSPERSONALINFO extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new LSPERSONALINFO().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new LSPERSONALINFO().findAndLockByWhere(sSqlWhere);      }       public static LSPERSONALINFO findFirst(String sSqlWhere) {           return (LSPERSONALINFO)new LSPERSONALINFO().findFirstByWhere(sSqlWhere);      }       public static LSPERSONALINFO findFirstAndLock(String sSqlWhere) {           return (LSPERSONALINFO)new LSPERSONALINFO().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new LSPERSONALINFO().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           LSPERSONALINFO bean = new LSPERSONALINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           LSPERSONALINFO bean = new LSPERSONALINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static LSPERSONALINFO findFirst(String sSqlWhere,boolean isAutoRelease) {           LSPERSONALINFO bean = new LSPERSONALINFO();           bean.setAutoRelease(isAutoRelease);           return (LSPERSONALINFO)bean.findFirstByWhere(sSqlWhere);      }       public static LSPERSONALINFO findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           LSPERSONALINFO bean = new LSPERSONALINFO();           bean.setAutoRelease(isAutoRelease);           return (LSPERSONALINFO)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           LSPERSONALINFO bean = new LSPERSONALINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new LSPERSONALINFO().findByWhereByRow(sSqlWhere,row);      } String pername;
String perid;
String deptcode;
String recinsequence;
int recversion;
int createcode;
String createdate;
String superdeptcode;
public static final String TABLENAME ="ls_personalinfo";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
LSPERSONALINFO abb = new LSPERSONALINFO();
abb.pername=rs.getString("pername");abb.setKeyValue("PERNAME",""+abb.getPername());
abb.perid=rs.getString("perid");abb.setKeyValue("PERID",""+abb.getPerid());
abb.deptcode=rs.getString("deptcode");abb.setKeyValue("DEPTCODE",""+abb.getDeptcode());
abb.recinsequence=rs.getString("recinsequence");abb.setKeyValue("RECINSEQUENCE",""+abb.getRecinsequence());
abb.recversion=rs.getInt("recversion");abb.setKeyValue("RECVERSION",""+abb.getRecversion());
abb.createcode=rs.getInt("createcode");abb.setKeyValue("CREATECODE",""+abb.getCreatecode());
abb.createdate=rs.getTimeString("createdate");abb.setKeyValue("CREATEDATE",""+abb.getCreatedate());
abb.superdeptcode=rs.getString("superdeptcode");abb.setKeyValue("SUPERDEPTCODE",""+abb.getSuperdeptcode());
list.add(abb);
abb.operate_mode = "edit";
}public String getPername() { if ( this.pername == null ) return ""; return this.pername;}
public String getPerid() { if ( this.perid == null ) return ""; return this.perid;}
public String getDeptcode() { if ( this.deptcode == null ) return ""; return this.deptcode;}
public String getRecinsequence() { if ( this.recinsequence == null ) return ""; return this.recinsequence;}
public int getRecversion() { return this.recversion;}
public int getCreatecode() { return this.createcode;}
public String getCreatedate() {  if ( this.createdate == null ) { return ""; } else { return this.createdate.trim().split(" ")[0];} }public String getCreatedateTime() {  if ( this.createdate == null ) return ""; return this.createdate.split("\\.")[0];}
public String getSuperdeptcode() { if ( this.superdeptcode == null ) return ""; return this.superdeptcode;}
public void setPername(String pername) { sqlMaker.setField("pername",pername,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getPername().equals(pername)) cf.add("pername",this.pername,pername); } this.pername=pername;}
public void setPerid(String perid) { sqlMaker.setField("perid",perid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getPerid().equals(perid)) cf.add("perid",this.perid,perid); } this.perid=perid;}
public void setDeptcode(String deptcode) { sqlMaker.setField("deptcode",deptcode,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDeptcode().equals(deptcode)) cf.add("deptcode",this.deptcode,deptcode); } this.deptcode=deptcode;}
public void setRecinsequence(String recinsequence) { sqlMaker.setField("recinsequence",recinsequence,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRecinsequence().equals(recinsequence)) cf.add("recinsequence",this.recinsequence,recinsequence); } this.recinsequence=recinsequence;}
public void setRecversion(int recversion) { sqlMaker.setField("recversion",""+recversion,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRecversion()!=recversion) cf.add("recversion",this.recversion+"",recversion+""); } this.recversion=recversion;}
public void setCreatecode(int createcode) { sqlMaker.setField("createcode",""+createcode,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCreatecode()!=createcode) cf.add("createcode",this.createcode+"",createcode+""); } this.createcode=createcode;}
public void setCreatedate(String createdate) { sqlMaker.setField("createdate",createdate,Field.DATE); if (this.operate_mode.equals("edit")) { if (!this.getCreatedate().equals(createdate)) cf.add("createdate",this.createdate,createdate); } this.createdate=createdate;}
public void setSuperdeptcode(String superdeptcode) { sqlMaker.setField("superdeptcode",superdeptcode,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSuperdeptcode().equals(superdeptcode)) cf.add("superdeptcode",this.superdeptcode,superdeptcode); } this.superdeptcode=superdeptcode;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"pername") !=null ) {this.setPername(actionRequest.getFieldValue(i,"pername"));}
if ( actionRequest.getFieldValue(i,"perid") !=null ) {this.setPerid(actionRequest.getFieldValue(i,"perid"));}
if ( actionRequest.getFieldValue(i,"deptcode") !=null ) {this.setDeptcode(actionRequest.getFieldValue(i,"deptcode"));}
if ( actionRequest.getFieldValue(i,"recinsequence") !=null ) {this.setRecinsequence(actionRequest.getFieldValue(i,"recinsequence"));}
if ( actionRequest.getFieldValue(i,"recversion") !=null && actionRequest.getFieldValue(i,"recversion").trim().length() > 0 ) {this.setRecversion(Integer.parseInt(actionRequest.getFieldValue(i,"recversion")));}
if ( actionRequest.getFieldValue(i,"createcode") !=null && actionRequest.getFieldValue(i,"createcode").trim().length() > 0 ) {this.setCreatecode(Integer.parseInt(actionRequest.getFieldValue(i,"createcode")));}
if ( actionRequest.getFieldValue(i,"createdate") !=null ) {this.setCreatedate(actionRequest.getFieldValue(i,"createdate"));}
if ( actionRequest.getFieldValue(i,"superdeptcode") !=null ) {this.setSuperdeptcode(actionRequest.getFieldValue(i,"superdeptcode"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { LSPERSONALINFO obj = (LSPERSONALINFO)super.clone();obj.setPername(obj.pername);
obj.setPerid(obj.perid);
obj.setDeptcode(obj.deptcode);
obj.setRecinsequence(obj.recinsequence);
obj.setRecversion(obj.recversion);
obj.setCreatecode(obj.createcode);
obj.setCreatedate(obj.createdate);
obj.setSuperdeptcode(obj.superdeptcode);
return obj;}}