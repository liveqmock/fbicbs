package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class LSCARDSTATUS extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new LSCARDSTATUS().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new LSCARDSTATUS().findAndLockByWhere(sSqlWhere);      }       public static LSCARDSTATUS findFirst(String sSqlWhere) {           return (LSCARDSTATUS)new LSCARDSTATUS().findFirstByWhere(sSqlWhere);      }       public static LSCARDSTATUS findFirstAndLock(String sSqlWhere) {           return (LSCARDSTATUS)new LSCARDSTATUS().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new LSCARDSTATUS().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           LSCARDSTATUS bean = new LSCARDSTATUS();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           LSCARDSTATUS bean = new LSCARDSTATUS();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static LSCARDSTATUS findFirst(String sSqlWhere,boolean isAutoRelease) {           LSCARDSTATUS bean = new LSCARDSTATUS();           bean.setAutoRelease(isAutoRelease);           return (LSCARDSTATUS)bean.findFirstByWhere(sSqlWhere);      }       public static LSCARDSTATUS findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           LSCARDSTATUS bean = new LSCARDSTATUS();           bean.setAutoRelease(isAutoRelease);           return (LSCARDSTATUS)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           LSCARDSTATUS bean = new LSCARDSTATUS();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new LSCARDSTATUS().findByWhereByRow(sSqlWhere,row);      } String account;
String cardname;
String status;
String bdgagency;
String guid;
public static final String TABLENAME ="ls_cardstatus";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
LSCARDSTATUS abb = new LSCARDSTATUS();
abb.account=rs.getString("account");abb.setKeyValue("ACCOUNT",""+abb.getAccount());
abb.cardname=rs.getString("cardname");abb.setKeyValue("CARDNAME",""+abb.getCardname());
abb.status=rs.getString("status");abb.setKeyValue("STATUS",""+abb.getStatus());
abb.bdgagency=rs.getString("bdgagency");abb.setKeyValue("BDGAGENCY",""+abb.getBdgagency());
abb.guid=rs.getString("guid");abb.setKeyValue("GUID",""+abb.getGuid());
list.add(abb);
abb.operate_mode = "edit";
}public String getAccount() { if ( this.account == null ) return ""; return this.account;}
public String getCardname() { if ( this.cardname == null ) return ""; return this.cardname;}
public String getStatus() { if ( this.status == null ) return ""; return this.status;}
public String getBdgagency() { if ( this.bdgagency == null ) return ""; return this.bdgagency;}
public String getGuid() { if ( this.guid == null ) return ""; return this.guid;}
public void setAccount(String account) { sqlMaker.setField("account",account,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount().equals(account)) cf.add("account",this.account,account); } this.account=account;}
public void setCardname(String cardname) { sqlMaker.setField("cardname",cardname,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCardname().equals(cardname)) cf.add("cardname",this.cardname,cardname); } this.cardname=cardname;}
public void setStatus(String status) { sqlMaker.setField("status",status,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getStatus().equals(status)) cf.add("status",this.status,status); } this.status=status;}
public void setBdgagency(String bdgagency) { sqlMaker.setField("bdgagency",bdgagency,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBdgagency().equals(bdgagency)) cf.add("bdgagency",this.bdgagency,bdgagency); } this.bdgagency=bdgagency;}
public void setGuid(String guid) { sqlMaker.setField("guid",guid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getGuid().equals(guid)) cf.add("guid",this.guid,guid); } this.guid=guid;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"account") !=null ) {this.setAccount(actionRequest.getFieldValue(i,"account"));}
if ( actionRequest.getFieldValue(i,"cardname") !=null ) {this.setCardname(actionRequest.getFieldValue(i,"cardname"));}
if ( actionRequest.getFieldValue(i,"status") !=null ) {this.setStatus(actionRequest.getFieldValue(i,"status"));}
if ( actionRequest.getFieldValue(i,"bdgagency") !=null ) {this.setBdgagency(actionRequest.getFieldValue(i,"bdgagency"));}
if ( actionRequest.getFieldValue(i,"guid") !=null ) {this.setGuid(actionRequest.getFieldValue(i,"guid"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { LSCARDSTATUS obj = (LSCARDSTATUS)super.clone();obj.setAccount(obj.account);
obj.setCardname(obj.cardname);
obj.setStatus(obj.status);
obj.setBdgagency(obj.bdgagency);
obj.setGuid(obj.guid);
return obj;}}