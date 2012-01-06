package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class LSCONSUMEINFO extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new LSCONSUMEINFO().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new LSCONSUMEINFO().findAndLockByWhere(sSqlWhere);      }       public static LSCONSUMEINFO findFirst(String sSqlWhere) {           return (LSCONSUMEINFO)new LSCONSUMEINFO().findFirstByWhere(sSqlWhere);      }       public static LSCONSUMEINFO findFirstAndLock(String sSqlWhere) {           return (LSCONSUMEINFO)new LSCONSUMEINFO().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new LSCONSUMEINFO().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           LSCONSUMEINFO bean = new LSCONSUMEINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           LSCONSUMEINFO bean = new LSCONSUMEINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static LSCONSUMEINFO findFirst(String sSqlWhere,boolean isAutoRelease) {           LSCONSUMEINFO bean = new LSCONSUMEINFO();           bean.setAutoRelease(isAutoRelease);           return (LSCONSUMEINFO)bean.findFirstByWhere(sSqlWhere);      }       public static LSCONSUMEINFO findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           LSCONSUMEINFO bean = new LSCONSUMEINFO();           bean.setAutoRelease(isAutoRelease);           return (LSCONSUMEINFO)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           LSCONSUMEINFO bean = new LSCONSUMEINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new LSCONSUMEINFO().findByWhereByRow(sSqlWhere,row);      } String lsh;
String account;
String busidate;
double busimoney;
String businame;
String cardname;
String limitdate;
String remark;
String operid;
String operdate;
String status;
String tx_cd;
String ref_number;
String cmbi_merch_no;
String inac_date;
double inac_amt;
String txlog;
String odsbdate;
String odsbtime;
int recversion;
public static final String TABLENAME ="ls_consumeinfo";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
LSCONSUMEINFO abb = new LSCONSUMEINFO();
abb.lsh=rs.getString("lsh");abb.setKeyValue("LSH",""+abb.getLsh());
abb.account=rs.getString("account");abb.setKeyValue("ACCOUNT",""+abb.getAccount());
abb.busidate=rs.getString("busidate");abb.setKeyValue("BUSIDATE",""+abb.getBusidate());
abb.busimoney=rs.getDouble("busimoney");abb.setKeyValue("BUSIMONEY",""+abb.getBusimoney());
abb.businame=rs.getString("businame");abb.setKeyValue("BUSINAME",""+abb.getBusiname());
abb.cardname=rs.getString("cardname");abb.setKeyValue("CARDNAME",""+abb.getCardname());
abb.limitdate=rs.getString("limitdate");abb.setKeyValue("LIMITDATE",""+abb.getLimitdate());
abb.remark=rs.getString("remark");abb.setKeyValue("REMARK",""+abb.getRemark());
abb.operid=rs.getString("operid");abb.setKeyValue("OPERID",""+abb.getOperid());
abb.operdate=rs.getTimeString("operdate");abb.setKeyValue("OPERDATE",""+abb.getOperdate());
abb.status=rs.getString("status");abb.setKeyValue("STATUS",""+abb.getStatus());
abb.tx_cd=rs.getString("tx_cd");abb.setKeyValue("TX_CD",""+abb.getTx_cd());
abb.ref_number=rs.getString("ref_number");abb.setKeyValue("REF_NUMBER",""+abb.getRef_number());
abb.cmbi_merch_no=rs.getString("cmbi_merch_no");abb.setKeyValue("CMBI_MERCH_NO",""+abb.getCmbi_merch_no());
abb.inac_date=rs.getString("inac_date");abb.setKeyValue("INAC_DATE",""+abb.getInac_date());
abb.inac_amt=rs.getDouble("inac_amt");abb.setKeyValue("INAC_AMT",""+abb.getInac_amt());
abb.txlog=rs.getString("txlog");abb.setKeyValue("TXLOG",""+abb.getTxlog());
abb.odsbdate=rs.getString("odsbdate");abb.setKeyValue("ODSBDATE",""+abb.getOdsbdate());
abb.odsbtime=rs.getString("odsbtime");abb.setKeyValue("ODSBTIME",""+abb.getOdsbtime());
abb.recversion=rs.getInt("recversion");abb.setKeyValue("RECVERSION",""+abb.getRecversion());
list.add(abb);
abb.operate_mode = "edit";
}public String getLsh() { if ( this.lsh == null ) return ""; return this.lsh;}
public String getAccount() { if ( this.account == null ) return ""; return this.account;}
public String getBusidate() { if ( this.busidate == null ) return ""; return this.busidate;}
public double getBusimoney() { return this.busimoney;}
public String getBusiname() { if ( this.businame == null ) return ""; return this.businame;}
public String getCardname() { if ( this.cardname == null ) return ""; return this.cardname;}
public String getLimitdate() { if ( this.limitdate == null ) return ""; return this.limitdate;}
public String getRemark() { if ( this.remark == null ) return ""; return this.remark;}
public String getOperid() { if ( this.operid == null ) return ""; return this.operid;}
public String getOperdate() {  if ( this.operdate == null ) { return ""; } else { return this.operdate.trim().split(" ")[0];} }public String getOperdateTime() {  if ( this.operdate == null ) return ""; return this.operdate.split("\\.")[0];}
public String getStatus() { if ( this.status == null ) return ""; return this.status;}
public String getTx_cd() { if ( this.tx_cd == null ) return ""; return this.tx_cd;}
public String getRef_number() { if ( this.ref_number == null ) return ""; return this.ref_number;}
public String getCmbi_merch_no() { if ( this.cmbi_merch_no == null ) return ""; return this.cmbi_merch_no;}
public String getInac_date() { if ( this.inac_date == null ) return ""; return this.inac_date;}
public double getInac_amt() { return this.inac_amt;}
public String getTxlog() { if ( this.txlog == null ) return ""; return this.txlog;}
public String getOdsbdate() { if ( this.odsbdate == null ) return ""; return this.odsbdate;}
public String getOdsbtime() { if ( this.odsbtime == null ) return ""; return this.odsbtime;}
public int getRecversion() { return this.recversion;}
public void setLsh(String lsh) { sqlMaker.setField("lsh",lsh,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLsh().equals(lsh)) cf.add("lsh",this.lsh,lsh); } this.lsh=lsh;}
public void setAccount(String account) { sqlMaker.setField("account",account,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount().equals(account)) cf.add("account",this.account,account); } this.account=account;}
public void setBusidate(String busidate) { sqlMaker.setField("busidate",busidate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBusidate().equals(busidate)) cf.add("busidate",this.busidate,busidate); } this.busidate=busidate;}
public void setBusimoney(double busimoney) { sqlMaker.setField("busimoney",""+busimoney,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getBusimoney()!=busimoney) cf.add("busimoney",this.busimoney+"",busimoney+""); } this.busimoney=busimoney;}
public void setBusiname(String businame) { sqlMaker.setField("businame",businame,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBusiname().equals(businame)) cf.add("businame",this.businame,businame); } this.businame=businame;}
public void setCardname(String cardname) { sqlMaker.setField("cardname",cardname,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCardname().equals(cardname)) cf.add("cardname",this.cardname,cardname); } this.cardname=cardname;}
public void setLimitdate(String limitdate) { sqlMaker.setField("limitdate",limitdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLimitdate().equals(limitdate)) cf.add("limitdate",this.limitdate,limitdate); } this.limitdate=limitdate;}
public void setRemark(String remark) { sqlMaker.setField("remark",remark,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRemark().equals(remark)) cf.add("remark",this.remark,remark); } this.remark=remark;}
public void setOperid(String operid) { sqlMaker.setField("operid",operid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOperid().equals(operid)) cf.add("operid",this.operid,operid); } this.operid=operid;}
public void setOperdate(String operdate) { sqlMaker.setField("operdate",operdate,Field.DATE); if (this.operate_mode.equals("edit")) { if (!this.getOperdate().equals(operdate)) cf.add("operdate",this.operdate,operdate); } this.operdate=operdate;}
public void setStatus(String status) { sqlMaker.setField("status",status,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getStatus().equals(status)) cf.add("status",this.status,status); } this.status=status;}
public void setTx_cd(String tx_cd) { sqlMaker.setField("tx_cd",tx_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTx_cd().equals(tx_cd)) cf.add("tx_cd",this.tx_cd,tx_cd); } this.tx_cd=tx_cd;}
public void setRef_number(String ref_number) { sqlMaker.setField("ref_number",ref_number,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRef_number().equals(ref_number)) cf.add("ref_number",this.ref_number,ref_number); } this.ref_number=ref_number;}
public void setCmbi_merch_no(String cmbi_merch_no) { sqlMaker.setField("cmbi_merch_no",cmbi_merch_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCmbi_merch_no().equals(cmbi_merch_no)) cf.add("cmbi_merch_no",this.cmbi_merch_no,cmbi_merch_no); } this.cmbi_merch_no=cmbi_merch_no;}
public void setInac_date(String inac_date) { sqlMaker.setField("inac_date",inac_date,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getInac_date().equals(inac_date)) cf.add("inac_date",this.inac_date,inac_date); } this.inac_date=inac_date;}
public void setInac_amt(double inac_amt) { sqlMaker.setField("inac_amt",""+inac_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getInac_amt()!=inac_amt) cf.add("inac_amt",this.inac_amt+"",inac_amt+""); } this.inac_amt=inac_amt;}
public void setTxlog(String txlog) { sqlMaker.setField("txlog",txlog,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTxlog().equals(txlog)) cf.add("txlog",this.txlog,txlog); } this.txlog=txlog;}
public void setOdsbdate(String odsbdate) { sqlMaker.setField("odsbdate",odsbdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOdsbdate().equals(odsbdate)) cf.add("odsbdate",this.odsbdate,odsbdate); } this.odsbdate=odsbdate;}
public void setOdsbtime(String odsbtime) { sqlMaker.setField("odsbtime",odsbtime,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOdsbtime().equals(odsbtime)) cf.add("odsbtime",this.odsbtime,odsbtime); } this.odsbtime=odsbtime;}
public void setRecversion(int recversion) { sqlMaker.setField("recversion",""+recversion,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRecversion()!=recversion) cf.add("recversion",this.recversion+"",recversion+""); } this.recversion=recversion;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"lsh") !=null ) {this.setLsh(actionRequest.getFieldValue(i,"lsh"));}
if ( actionRequest.getFieldValue(i,"account") !=null ) {this.setAccount(actionRequest.getFieldValue(i,"account"));}
if ( actionRequest.getFieldValue(i,"busidate") !=null ) {this.setBusidate(actionRequest.getFieldValue(i,"busidate"));}
if ( actionRequest.getFieldValue(i,"busimoney") !=null && actionRequest.getFieldValue(i,"busimoney").trim().length() > 0 ) {this.setBusimoney(Double.parseDouble(actionRequest.getFieldValue(i,"busimoney")));}
if ( actionRequest.getFieldValue(i,"businame") !=null ) {this.setBusiname(actionRequest.getFieldValue(i,"businame"));}
if ( actionRequest.getFieldValue(i,"cardname") !=null ) {this.setCardname(actionRequest.getFieldValue(i,"cardname"));}
if ( actionRequest.getFieldValue(i,"limitdate") !=null ) {this.setLimitdate(actionRequest.getFieldValue(i,"limitdate"));}
if ( actionRequest.getFieldValue(i,"remark") !=null ) {this.setRemark(actionRequest.getFieldValue(i,"remark"));}
if ( actionRequest.getFieldValue(i,"operid") !=null ) {this.setOperid(actionRequest.getFieldValue(i,"operid"));}
if ( actionRequest.getFieldValue(i,"operdate") !=null ) {this.setOperdate(actionRequest.getFieldValue(i,"operdate"));}
if ( actionRequest.getFieldValue(i,"status") !=null ) {this.setStatus(actionRequest.getFieldValue(i,"status"));}
if ( actionRequest.getFieldValue(i,"tx_cd") !=null ) {this.setTx_cd(actionRequest.getFieldValue(i,"tx_cd"));}
if ( actionRequest.getFieldValue(i,"ref_number") !=null ) {this.setRef_number(actionRequest.getFieldValue(i,"ref_number"));}
if ( actionRequest.getFieldValue(i,"cmbi_merch_no") !=null ) {this.setCmbi_merch_no(actionRequest.getFieldValue(i,"cmbi_merch_no"));}
if ( actionRequest.getFieldValue(i,"inac_date") !=null ) {this.setInac_date(actionRequest.getFieldValue(i,"inac_date"));}
if ( actionRequest.getFieldValue(i,"inac_amt") !=null && actionRequest.getFieldValue(i,"inac_amt").trim().length() > 0 ) {this.setInac_amt(Double.parseDouble(actionRequest.getFieldValue(i,"inac_amt")));}
if ( actionRequest.getFieldValue(i,"txlog") !=null ) {this.setTxlog(actionRequest.getFieldValue(i,"txlog"));}
if ( actionRequest.getFieldValue(i,"odsbdate") !=null ) {this.setOdsbdate(actionRequest.getFieldValue(i,"odsbdate"));}
if ( actionRequest.getFieldValue(i,"odsbtime") !=null ) {this.setOdsbtime(actionRequest.getFieldValue(i,"odsbtime"));}
if ( actionRequest.getFieldValue(i,"recversion") !=null && actionRequest.getFieldValue(i,"recversion").trim().length() > 0 ) {this.setRecversion(Integer.parseInt(actionRequest.getFieldValue(i,"recversion")));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { LSCONSUMEINFO obj = (LSCONSUMEINFO)super.clone();obj.setLsh(obj.lsh);
obj.setAccount(obj.account);
obj.setBusidate(obj.busidate);
obj.setBusimoney(obj.busimoney);
obj.setBusiname(obj.businame);
obj.setCardname(obj.cardname);
obj.setLimitdate(obj.limitdate);
obj.setRemark(obj.remark);
obj.setOperid(obj.operid);
obj.setOperdate(obj.operdate);
obj.setStatus(obj.status);
obj.setTx_cd(obj.tx_cd);
obj.setRef_number(obj.ref_number);
obj.setCmbi_merch_no(obj.cmbi_merch_no);
obj.setInac_date(obj.inac_date);
obj.setInac_amt(obj.inac_amt);
obj.setTxlog(obj.txlog);
obj.setOdsbdate(obj.odsbdate);
obj.setOdsbtime(obj.odsbtime);
obj.setRecversion(obj.recversion);
return obj;}}