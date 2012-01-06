package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class LSCARDBASEINFO extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new LSCARDBASEINFO().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new LSCARDBASEINFO().findAndLockByWhere(sSqlWhere);      }       public static LSCARDBASEINFO findFirst(String sSqlWhere) {           return (LSCARDBASEINFO)new LSCARDBASEINFO().findFirstByWhere(sSqlWhere);      }       public static LSCARDBASEINFO findFirstAndLock(String sSqlWhere) {           return (LSCARDBASEINFO)new LSCARDBASEINFO().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new LSCARDBASEINFO().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           LSCARDBASEINFO bean = new LSCARDBASEINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           LSCARDBASEINFO bean = new LSCARDBASEINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static LSCARDBASEINFO findFirst(String sSqlWhere,boolean isAutoRelease) {           LSCARDBASEINFO bean = new LSCARDBASEINFO();           bean.setAutoRelease(isAutoRelease);           return (LSCARDBASEINFO)bean.findFirstByWhere(sSqlWhere);      }       public static LSCARDBASEINFO findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           LSCARDBASEINFO bean = new LSCARDBASEINFO();           bean.setAutoRelease(isAutoRelease);           return (LSCARDBASEINFO)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           LSCARDBASEINFO bean = new LSCARDBASEINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new LSCARDBASEINFO().findByWhereByRow(sSqlWhere,row);      } String account;
String cardname;
String bdgagency;
String gatheringbankacctname;
String gatheringbankname;
String gatheringbankacctcode;
String idnumber;
String digest;
int bank;
String createdate;
String startdate;
String enddate;
String action;
String remark;
String operid;
String operdate;
String sentflag;
String txlog;
String odsbdate;
String odsbtime;
int recversion;
String status;
public static final String TABLENAME ="ls_cardbaseinfo";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
LSCARDBASEINFO abb = new LSCARDBASEINFO();
abb.account=rs.getString("account");abb.setKeyValue("ACCOUNT",""+abb.getAccount());
abb.cardname=rs.getString("cardname");abb.setKeyValue("CARDNAME",""+abb.getCardname());
abb.bdgagency=rs.getString("bdgagency");abb.setKeyValue("BDGAGENCY",""+abb.getBdgagency());
abb.gatheringbankacctname=rs.getString("gatheringbankacctname");abb.setKeyValue("GATHERINGBANKACCTNAME",""+abb.getGatheringbankacctname());
abb.gatheringbankname=rs.getString("gatheringbankname");abb.setKeyValue("GATHERINGBANKNAME",""+abb.getGatheringbankname());
abb.gatheringbankacctcode=rs.getString("gatheringbankacctcode");abb.setKeyValue("GATHERINGBANKACCTCODE",""+abb.getGatheringbankacctcode());
abb.idnumber=rs.getString("idnumber");abb.setKeyValue("IDNUMBER",""+abb.getIdnumber());
abb.digest=rs.getString("digest");abb.setKeyValue("DIGEST",""+abb.getDigest());
abb.bank=rs.getInt("bank");abb.setKeyValue("BANK",""+abb.getBank());
abb.createdate=rs.getString("createdate");abb.setKeyValue("CREATEDATE",""+abb.getCreatedate());
abb.startdate=rs.getString("startdate");abb.setKeyValue("STARTDATE",""+abb.getStartdate());
abb.enddate=rs.getString("enddate");abb.setKeyValue("ENDDATE",""+abb.getEnddate());
abb.action=rs.getString("action");abb.setKeyValue("ACTION",""+abb.getAction());
abb.remark=rs.getString("remark");abb.setKeyValue("REMARK",""+abb.getRemark());
abb.operid=rs.getString("operid");abb.setKeyValue("OPERID",""+abb.getOperid());
abb.operdate=rs.getTimeString("operdate");abb.setKeyValue("OPERDATE",""+abb.getOperdate());
abb.sentflag=rs.getString("sentflag");abb.setKeyValue("SENTFLAG",""+abb.getSentflag());
abb.txlog=rs.getString("txlog");abb.setKeyValue("TXLOG",""+abb.getTxlog());
abb.odsbdate=rs.getString("odsbdate");abb.setKeyValue("ODSBDATE",""+abb.getOdsbdate());
abb.odsbtime=rs.getString("odsbtime");abb.setKeyValue("ODSBTIME",""+abb.getOdsbtime());
abb.recversion=rs.getInt("recversion");abb.setKeyValue("RECVERSION",""+abb.getRecversion());
abb.status=rs.getString("status");abb.setKeyValue("STATUS",""+abb.getStatus());
list.add(abb);
abb.operate_mode = "edit";
}public String getAccount() { if ( this.account == null ) return ""; return this.account;}
public String getCardname() { if ( this.cardname == null ) return ""; return this.cardname;}
public String getBdgagency() { if ( this.bdgagency == null ) return ""; return this.bdgagency;}
public String getGatheringbankacctname() { if ( this.gatheringbankacctname == null ) return ""; return this.gatheringbankacctname;}
public String getGatheringbankname() { if ( this.gatheringbankname == null ) return ""; return this.gatheringbankname;}
public String getGatheringbankacctcode() { if ( this.gatheringbankacctcode == null ) return ""; return this.gatheringbankacctcode;}
public String getIdnumber() { if ( this.idnumber == null ) return ""; return this.idnumber;}
public String getDigest() { if ( this.digest == null ) return ""; return this.digest;}
public int getBank() { return this.bank;}
public String getCreatedate() { if ( this.createdate == null ) return ""; return this.createdate;}
public String getStartdate() { if ( this.startdate == null ) return ""; return this.startdate;}
public String getEnddate() { if ( this.enddate == null ) return ""; return this.enddate;}
public String getAction() { if ( this.action == null ) return ""; return this.action;}
public String getRemark() { if ( this.remark == null ) return ""; return this.remark;}
public String getOperid() { if ( this.operid == null ) return ""; return this.operid;}
public String getOperdate() {  if ( this.operdate == null ) { return ""; } else { return this.operdate.trim().split(" ")[0];} }public String getOperdateTime() {  if ( this.operdate == null ) return ""; return this.operdate.split("\\.")[0];}
public String getSentflag() { if ( this.sentflag == null ) return ""; return this.sentflag;}
public String getTxlog() { if ( this.txlog == null ) return ""; return this.txlog;}
public String getOdsbdate() { if ( this.odsbdate == null ) return ""; return this.odsbdate;}
public String getOdsbtime() { if ( this.odsbtime == null ) return ""; return this.odsbtime;}
public int getRecversion() { return this.recversion;}
public String getStatus() { if ( this.status == null ) return ""; return this.status;}
public void setAccount(String account) { sqlMaker.setField("account",account,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount().equals(account)) cf.add("account",this.account,account); } this.account=account;}
public void setCardname(String cardname) { sqlMaker.setField("cardname",cardname,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCardname().equals(cardname)) cf.add("cardname",this.cardname,cardname); } this.cardname=cardname;}
public void setBdgagency(String bdgagency) { sqlMaker.setField("bdgagency",bdgagency,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBdgagency().equals(bdgagency)) cf.add("bdgagency",this.bdgagency,bdgagency); } this.bdgagency=bdgagency;}
public void setGatheringbankacctname(String gatheringbankacctname) { sqlMaker.setField("gatheringbankacctname",gatheringbankacctname,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getGatheringbankacctname().equals(gatheringbankacctname)) cf.add("gatheringbankacctname",this.gatheringbankacctname,gatheringbankacctname); } this.gatheringbankacctname=gatheringbankacctname;}
public void setGatheringbankname(String gatheringbankname) { sqlMaker.setField("gatheringbankname",gatheringbankname,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getGatheringbankname().equals(gatheringbankname)) cf.add("gatheringbankname",this.gatheringbankname,gatheringbankname); } this.gatheringbankname=gatheringbankname;}
public void setGatheringbankacctcode(String gatheringbankacctcode) { sqlMaker.setField("gatheringbankacctcode",gatheringbankacctcode,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getGatheringbankacctcode().equals(gatheringbankacctcode)) cf.add("gatheringbankacctcode",this.gatheringbankacctcode,gatheringbankacctcode); } this.gatheringbankacctcode=gatheringbankacctcode;}
public void setIdnumber(String idnumber) { sqlMaker.setField("idnumber",idnumber,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getIdnumber().equals(idnumber)) cf.add("idnumber",this.idnumber,idnumber); } this.idnumber=idnumber;}
public void setDigest(String digest) { sqlMaker.setField("digest",digest,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDigest().equals(digest)) cf.add("digest",this.digest,digest); } this.digest=digest;}
public void setBank(int bank) { sqlMaker.setField("bank",""+bank,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getBank()!=bank) cf.add("bank",this.bank+"",bank+""); } this.bank=bank;}
public void setCreatedate(String createdate) { sqlMaker.setField("createdate",createdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCreatedate().equals(createdate)) cf.add("createdate",this.createdate,createdate); } this.createdate=createdate;}
public void setStartdate(String startdate) { sqlMaker.setField("startdate",startdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getStartdate().equals(startdate)) cf.add("startdate",this.startdate,startdate); } this.startdate=startdate;}
public void setEnddate(String enddate) { sqlMaker.setField("enddate",enddate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEnddate().equals(enddate)) cf.add("enddate",this.enddate,enddate); } this.enddate=enddate;}
public void setAction(String action) { sqlMaker.setField("action",action,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAction().equals(action)) cf.add("action",this.action,action); } this.action=action;}
public void setRemark(String remark) { sqlMaker.setField("remark",remark,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRemark().equals(remark)) cf.add("remark",this.remark,remark); } this.remark=remark;}
public void setOperid(String operid) { sqlMaker.setField("operid",operid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOperid().equals(operid)) cf.add("operid",this.operid,operid); } this.operid=operid;}
public void setOperdate(String operdate) { sqlMaker.setField("operdate",operdate,Field.DATE); if (this.operate_mode.equals("edit")) { if (!this.getOperdate().equals(operdate)) cf.add("operdate",this.operdate,operdate); } this.operdate=operdate;}
public void setSentflag(String sentflag) { sqlMaker.setField("sentflag",sentflag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSentflag().equals(sentflag)) cf.add("sentflag",this.sentflag,sentflag); } this.sentflag=sentflag;}
public void setTxlog(String txlog) { sqlMaker.setField("txlog",txlog,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTxlog().equals(txlog)) cf.add("txlog",this.txlog,txlog); } this.txlog=txlog;}
public void setOdsbdate(String odsbdate) { sqlMaker.setField("odsbdate",odsbdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOdsbdate().equals(odsbdate)) cf.add("odsbdate",this.odsbdate,odsbdate); } this.odsbdate=odsbdate;}
public void setOdsbtime(String odsbtime) { sqlMaker.setField("odsbtime",odsbtime,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOdsbtime().equals(odsbtime)) cf.add("odsbtime",this.odsbtime,odsbtime); } this.odsbtime=odsbtime;}
public void setRecversion(int recversion) { sqlMaker.setField("recversion",""+recversion,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRecversion()!=recversion) cf.add("recversion",this.recversion+"",recversion+""); } this.recversion=recversion;}
public void setStatus(String status) { sqlMaker.setField("status",status,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getStatus().equals(status)) cf.add("status",this.status,status); } this.status=status;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"account") !=null ) {this.setAccount(actionRequest.getFieldValue(i,"account"));}
if ( actionRequest.getFieldValue(i,"cardname") !=null ) {this.setCardname(actionRequest.getFieldValue(i,"cardname"));}
if ( actionRequest.getFieldValue(i,"bdgagency") !=null ) {this.setBdgagency(actionRequest.getFieldValue(i,"bdgagency"));}
if ( actionRequest.getFieldValue(i,"gatheringbankacctname") !=null ) {this.setGatheringbankacctname(actionRequest.getFieldValue(i,"gatheringbankacctname"));}
if ( actionRequest.getFieldValue(i,"gatheringbankname") !=null ) {this.setGatheringbankname(actionRequest.getFieldValue(i,"gatheringbankname"));}
if ( actionRequest.getFieldValue(i,"gatheringbankacctcode") !=null ) {this.setGatheringbankacctcode(actionRequest.getFieldValue(i,"gatheringbankacctcode"));}
if ( actionRequest.getFieldValue(i,"idnumber") !=null ) {this.setIdnumber(actionRequest.getFieldValue(i,"idnumber"));}
if ( actionRequest.getFieldValue(i,"digest") !=null ) {this.setDigest(actionRequest.getFieldValue(i,"digest"));}
if ( actionRequest.getFieldValue(i,"bank") !=null && actionRequest.getFieldValue(i,"bank").trim().length() > 0 ) {this.setBank(Integer.parseInt(actionRequest.getFieldValue(i,"bank")));}
if ( actionRequest.getFieldValue(i,"createdate") !=null ) {this.setCreatedate(actionRequest.getFieldValue(i,"createdate"));}
if ( actionRequest.getFieldValue(i,"startdate") !=null ) {this.setStartdate(actionRequest.getFieldValue(i,"startdate"));}
if ( actionRequest.getFieldValue(i,"enddate") !=null ) {this.setEnddate(actionRequest.getFieldValue(i,"enddate"));}
if ( actionRequest.getFieldValue(i,"action") !=null ) {this.setAction(actionRequest.getFieldValue(i,"action"));}
if ( actionRequest.getFieldValue(i,"remark") !=null ) {this.setRemark(actionRequest.getFieldValue(i,"remark"));}
if ( actionRequest.getFieldValue(i,"operid") !=null ) {this.setOperid(actionRequest.getFieldValue(i,"operid"));}
if ( actionRequest.getFieldValue(i,"operdate") !=null ) {this.setOperdate(actionRequest.getFieldValue(i,"operdate"));}
if ( actionRequest.getFieldValue(i,"sentflag") !=null ) {this.setSentflag(actionRequest.getFieldValue(i,"sentflag"));}
if ( actionRequest.getFieldValue(i,"txlog") !=null ) {this.setTxlog(actionRequest.getFieldValue(i,"txlog"));}
if ( actionRequest.getFieldValue(i,"odsbdate") !=null ) {this.setOdsbdate(actionRequest.getFieldValue(i,"odsbdate"));}
if ( actionRequest.getFieldValue(i,"odsbtime") !=null ) {this.setOdsbtime(actionRequest.getFieldValue(i,"odsbtime"));}
if ( actionRequest.getFieldValue(i,"recversion") !=null && actionRequest.getFieldValue(i,"recversion").trim().length() > 0 ) {this.setRecversion(Integer.parseInt(actionRequest.getFieldValue(i,"recversion")));}
if ( actionRequest.getFieldValue(i,"status") !=null ) {this.setStatus(actionRequest.getFieldValue(i,"status"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { LSCARDBASEINFO obj = (LSCARDBASEINFO)super.clone();obj.setAccount(obj.account);
obj.setCardname(obj.cardname);
obj.setBdgagency(obj.bdgagency);
obj.setGatheringbankacctname(obj.gatheringbankacctname);
obj.setGatheringbankname(obj.gatheringbankname);
obj.setGatheringbankacctcode(obj.gatheringbankacctcode);
obj.setIdnumber(obj.idnumber);
obj.setDigest(obj.digest);
obj.setBank(obj.bank);
obj.setCreatedate(obj.createdate);
obj.setStartdate(obj.startdate);
obj.setEnddate(obj.enddate);
obj.setAction(obj.action);
obj.setRemark(obj.remark);
obj.setOperid(obj.operid);
obj.setOperdate(obj.operdate);
obj.setSentflag(obj.sentflag);
obj.setTxlog(obj.txlog);
obj.setOdsbdate(obj.odsbdate);
obj.setOdsbtime(obj.odsbtime);
obj.setRecversion(obj.recversion);
obj.setStatus(obj.status);
return obj;}}