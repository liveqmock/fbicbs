package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class LSPAYBACKINFO extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new LSPAYBACKINFO().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new LSPAYBACKINFO().findAndLockByWhere(sSqlWhere);      }       public static LSPAYBACKINFO findFirst(String sSqlWhere) {           return (LSPAYBACKINFO)new LSPAYBACKINFO().findFirstByWhere(sSqlWhere);      }       public static LSPAYBACKINFO findFirstAndLock(String sSqlWhere) {           return (LSPAYBACKINFO)new LSPAYBACKINFO().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new LSPAYBACKINFO().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           LSPAYBACKINFO bean = new LSPAYBACKINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           LSPAYBACKINFO bean = new LSPAYBACKINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static LSPAYBACKINFO findFirst(String sSqlWhere,boolean isAutoRelease) {           LSPAYBACKINFO bean = new LSPAYBACKINFO();           bean.setAutoRelease(isAutoRelease);           return (LSPAYBACKINFO)bean.findFirstByWhere(sSqlWhere);      }       public static LSPAYBACKINFO findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           LSPAYBACKINFO bean = new LSPAYBACKINFO();           bean.setAutoRelease(isAutoRelease);           return (LSPAYBACKINFO)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           LSPAYBACKINFO bean = new LSPAYBACKINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new LSPAYBACKINFO().findByWhereByRow(sSqlWhere,row);      } String areacode;
String voucherid;
String account;
String cardname;
double amt;
String querydate;
String operid;
String operdate;
String status;
String paybackdate;
String remark;
String year;
String branchid;
public static final String TABLENAME ="ls_paybackinfo";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
LSPAYBACKINFO abb = new LSPAYBACKINFO();
abb.areacode=rs.getString("areacode");abb.setKeyValue("AREACODE",""+abb.getAreacode());
abb.voucherid=rs.getString("voucherid");abb.setKeyValue("VOUCHERID",""+abb.getVoucherid());
abb.account=rs.getString("account");abb.setKeyValue("ACCOUNT",""+abb.getAccount());
abb.cardname=rs.getString("cardname");abb.setKeyValue("CARDNAME",""+abb.getCardname());
abb.amt=rs.getDouble("amt");abb.setKeyValue("AMT",""+abb.getAmt());
abb.querydate=rs.getString("querydate");abb.setKeyValue("QUERYDATE",""+abb.getQuerydate());
abb.operid=rs.getString("operid");abb.setKeyValue("OPERID",""+abb.getOperid());
abb.operdate=rs.getTimeString("operdate");abb.setKeyValue("OPERDATE",""+abb.getOperdate());
abb.status=rs.getString("status");abb.setKeyValue("STATUS",""+abb.getStatus());
abb.paybackdate=rs.getString("paybackdate");abb.setKeyValue("PAYBACKDATE",""+abb.getPaybackdate());
abb.remark=rs.getString("remark");abb.setKeyValue("REMARK",""+abb.getRemark());
abb.year=rs.getString("year");abb.setKeyValue("YEAR",""+abb.getYear());
abb.branchid=rs.getString("branchid");abb.setKeyValue("BRANCHID",""+abb.getBranchid());
list.add(abb);
abb.operate_mode = "edit";
}public String getAreacode() { if ( this.areacode == null ) return ""; return this.areacode;}
public String getVoucherid() { if ( this.voucherid == null ) return ""; return this.voucherid;}
public String getAccount() { if ( this.account == null ) return ""; return this.account;}
public String getCardname() { if ( this.cardname == null ) return ""; return this.cardname;}
public double getAmt() { return this.amt;}
public String getQuerydate() { if ( this.querydate == null ) return ""; return this.querydate;}
public String getOperid() { if ( this.operid == null ) return ""; return this.operid;}
public String getOperdate() {  if ( this.operdate == null ) { return ""; } else { return this.operdate.trim().split(" ")[0];} }public String getOperdateTime() {  if ( this.operdate == null ) return ""; return this.operdate.split("\\.")[0];}
public String getStatus() { if ( this.status == null ) return ""; return this.status;}
public String getPaybackdate() { if ( this.paybackdate == null ) return ""; return this.paybackdate;}
public String getRemark() { if ( this.remark == null ) return ""; return this.remark;}
public String getYear() { if ( this.year == null ) return ""; return this.year;}
public String getBranchid() { if ( this.branchid == null ) return ""; return this.branchid;}
public void setAreacode(String areacode) { sqlMaker.setField("areacode",areacode,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAreacode().equals(areacode)) cf.add("areacode",this.areacode,areacode); } this.areacode=areacode;}
public void setVoucherid(String voucherid) { sqlMaker.setField("voucherid",voucherid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getVoucherid().equals(voucherid)) cf.add("voucherid",this.voucherid,voucherid); } this.voucherid=voucherid;}
public void setAccount(String account) { sqlMaker.setField("account",account,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount().equals(account)) cf.add("account",this.account,account); } this.account=account;}
public void setCardname(String cardname) { sqlMaker.setField("cardname",cardname,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCardname().equals(cardname)) cf.add("cardname",this.cardname,cardname); } this.cardname=cardname;}
public void setAmt(double amt) { sqlMaker.setField("amt",""+amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAmt()!=amt) cf.add("amt",this.amt+"",amt+""); } this.amt=amt;}
public void setQuerydate(String querydate) { sqlMaker.setField("querydate",querydate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getQuerydate().equals(querydate)) cf.add("querydate",this.querydate,querydate); } this.querydate=querydate;}
public void setOperid(String operid) { sqlMaker.setField("operid",operid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOperid().equals(operid)) cf.add("operid",this.operid,operid); } this.operid=operid;}
public void setOperdate(String operdate) { sqlMaker.setField("operdate",operdate,Field.DATE); if (this.operate_mode.equals("edit")) { if (!this.getOperdate().equals(operdate)) cf.add("operdate",this.operdate,operdate); } this.operdate=operdate;}
public void setStatus(String status) { sqlMaker.setField("status",status,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getStatus().equals(status)) cf.add("status",this.status,status); } this.status=status;}
public void setPaybackdate(String paybackdate) { sqlMaker.setField("paybackdate",paybackdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getPaybackdate().equals(paybackdate)) cf.add("paybackdate",this.paybackdate,paybackdate); } this.paybackdate=paybackdate;}
public void setRemark(String remark) { sqlMaker.setField("remark",remark,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRemark().equals(remark)) cf.add("remark",this.remark,remark); } this.remark=remark;}
public void setYear(String year) { sqlMaker.setField("year",year,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getYear().equals(year)) cf.add("year",this.year,year); } this.year=year;}
public void setBranchid(String branchid) { sqlMaker.setField("branchid",branchid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBranchid().equals(branchid)) cf.add("branchid",this.branchid,branchid); } this.branchid=branchid;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"areacode") !=null ) {this.setAreacode(actionRequest.getFieldValue(i,"areacode"));}
if ( actionRequest.getFieldValue(i,"voucherid") !=null ) {this.setVoucherid(actionRequest.getFieldValue(i,"voucherid"));}
if ( actionRequest.getFieldValue(i,"account") !=null ) {this.setAccount(actionRequest.getFieldValue(i,"account"));}
if ( actionRequest.getFieldValue(i,"cardname") !=null ) {this.setCardname(actionRequest.getFieldValue(i,"cardname"));}
if ( actionRequest.getFieldValue(i,"amt") !=null && actionRequest.getFieldValue(i,"amt").trim().length() > 0 ) {this.setAmt(Double.parseDouble(actionRequest.getFieldValue(i,"amt")));}
if ( actionRequest.getFieldValue(i,"querydate") !=null ) {this.setQuerydate(actionRequest.getFieldValue(i,"querydate"));}
if ( actionRequest.getFieldValue(i,"operid") !=null ) {this.setOperid(actionRequest.getFieldValue(i,"operid"));}
if ( actionRequest.getFieldValue(i,"operdate") !=null ) {this.setOperdate(actionRequest.getFieldValue(i,"operdate"));}
if ( actionRequest.getFieldValue(i,"status") !=null ) {this.setStatus(actionRequest.getFieldValue(i,"status"));}
if ( actionRequest.getFieldValue(i,"paybackdate") !=null ) {this.setPaybackdate(actionRequest.getFieldValue(i,"paybackdate"));}
if ( actionRequest.getFieldValue(i,"remark") !=null ) {this.setRemark(actionRequest.getFieldValue(i,"remark"));}
if ( actionRequest.getFieldValue(i,"year") !=null ) {this.setYear(actionRequest.getFieldValue(i,"year"));}
if ( actionRequest.getFieldValue(i,"branchid") !=null ) {this.setBranchid(actionRequest.getFieldValue(i,"branchid"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { LSPAYBACKINFO obj = (LSPAYBACKINFO)super.clone();obj.setAreacode(obj.areacode);
obj.setVoucherid(obj.voucherid);
obj.setAccount(obj.account);
obj.setCardname(obj.cardname);
obj.setAmt(obj.amt);
obj.setQuerydate(obj.querydate);
obj.setOperid(obj.operid);
obj.setOperdate(obj.operdate);
obj.setStatus(obj.status);
obj.setPaybackdate(obj.paybackdate);
obj.setRemark(obj.remark);
obj.setYear(obj.year);
obj.setBranchid(obj.branchid);
return obj;}}