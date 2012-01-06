package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class LNMORTINFO extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new LNMORTINFO().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new LNMORTINFO().findAndLockByWhere(sSqlWhere);      }       public static LNMORTINFO findFirst(String sSqlWhere) {           return (LNMORTINFO)new LNMORTINFO().findFirstByWhere(sSqlWhere);      }       public static LNMORTINFO findFirstAndLock(String sSqlWhere) {           return (LNMORTINFO)new LNMORTINFO().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new LNMORTINFO().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           LNMORTINFO bean = new LNMORTINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           LNMORTINFO bean = new LNMORTINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static LNMORTINFO findFirst(String sSqlWhere,boolean isAutoRelease) {           LNMORTINFO bean = new LNMORTINFO();           bean.setAutoRelease(isAutoRelease);           return (LNMORTINFO)bean.findFirstByWhere(sSqlWhere);      }       public static LNMORTINFO findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           LNMORTINFO bean = new LNMORTINFO();           bean.setAutoRelease(isAutoRelease);           return (LNMORTINFO)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           LNMORTINFO bean = new LNMORTINFO();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new LNMORTINFO().findByWhereByRow(sSqlWhere,row);      } String mortid;
String loanid;
String mortdate;
String mortecentercd;
String releasecondcd;
String keepcont;
String expressno;
String expressendsdate;
String paperrtndate;
String expressnote;
String nomortreason;
String sendflag;
String relayflag;
String mortstatus;
String operid;
String operdate;
int recversion;
String deptid;
String nomortreasoncd;
String rptmortdate;
String chgpaperdate;
String chgpaperreason;
String chgpaperreasoncd;
String chgpaperrtndate;
String clrpaperdate;
String mortexpiredate;
String mortoverrtndate;
String mortregstatus;
String receiptdate;
String receiptid;
String boxid;
String expressrtndate;
String rptnomortdate;
String documentid;
int warrantcnt;
public static final String TABLENAME ="ln_mortinfo";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
LNMORTINFO abb = new LNMORTINFO();
abb.mortid=rs.getString("mortid");abb.setKeyValue("MORTID",""+abb.getMortid());
abb.loanid=rs.getString("loanid");abb.setKeyValue("LOANID",""+abb.getLoanid());
abb.mortdate=rs.getString("mortdate");abb.setKeyValue("MORTDATE",""+abb.getMortdate());
abb.mortecentercd=rs.getString("mortecentercd");abb.setKeyValue("MORTECENTERCD",""+abb.getMortecentercd());
abb.releasecondcd=rs.getString("releasecondcd");abb.setKeyValue("RELEASECONDCD",""+abb.getReleasecondcd());
abb.keepcont=rs.getString("keepcont");abb.setKeyValue("KEEPCONT",""+abb.getKeepcont());
abb.expressno=rs.getString("expressno");abb.setKeyValue("EXPRESSNO",""+abb.getExpressno());
abb.expressendsdate=rs.getString("expressendsdate");abb.setKeyValue("EXPRESSENDSDATE",""+abb.getExpressendsdate());
abb.paperrtndate=rs.getString("paperrtndate");abb.setKeyValue("PAPERRTNDATE",""+abb.getPaperrtndate());
abb.expressnote=rs.getString("expressnote");abb.setKeyValue("EXPRESSNOTE",""+abb.getExpressnote());
abb.nomortreason=rs.getString("nomortreason");abb.setKeyValue("NOMORTREASON",""+abb.getNomortreason());
abb.sendflag=rs.getString("sendflag");abb.setKeyValue("SENDFLAG",""+abb.getSendflag());
abb.relayflag=rs.getString("relayflag");abb.setKeyValue("RELAYFLAG",""+abb.getRelayflag());
abb.mortstatus=rs.getString("mortstatus");abb.setKeyValue("MORTSTATUS",""+abb.getMortstatus());
abb.operid=rs.getString("operid");abb.setKeyValue("OPERID",""+abb.getOperid());
abb.operdate=rs.getTimeString("operdate");abb.setKeyValue("OPERDATE",""+abb.getOperdate());
abb.recversion=rs.getInt("recversion");abb.setKeyValue("RECVERSION",""+abb.getRecversion());
abb.deptid=rs.getString("deptid");abb.setKeyValue("DEPTID",""+abb.getDeptid());
abb.nomortreasoncd=rs.getString("nomortreasoncd");abb.setKeyValue("NOMORTREASONCD",""+abb.getNomortreasoncd());
abb.rptmortdate=rs.getString("rptmortdate");abb.setKeyValue("RPTMORTDATE",""+abb.getRptmortdate());
abb.chgpaperdate=rs.getString("chgpaperdate");abb.setKeyValue("CHGPAPERDATE",""+abb.getChgpaperdate());
abb.chgpaperreason=rs.getString("chgpaperreason");abb.setKeyValue("CHGPAPERREASON",""+abb.getChgpaperreason());
abb.chgpaperreasoncd=rs.getString("chgpaperreasoncd");abb.setKeyValue("CHGPAPERREASONCD",""+abb.getChgpaperreasoncd());
abb.chgpaperrtndate=rs.getString("chgpaperrtndate");abb.setKeyValue("CHGPAPERRTNDATE",""+abb.getChgpaperrtndate());
abb.clrpaperdate=rs.getString("clrpaperdate");abb.setKeyValue("CLRPAPERDATE",""+abb.getClrpaperdate());
abb.mortexpiredate=rs.getString("mortexpiredate");abb.setKeyValue("MORTEXPIREDATE",""+abb.getMortexpiredate());
abb.mortoverrtndate=rs.getString("mortoverrtndate");abb.setKeyValue("MORTOVERRTNDATE",""+abb.getMortoverrtndate());
abb.mortregstatus=rs.getString("mortregstatus");abb.setKeyValue("MORTREGSTATUS",""+abb.getMortregstatus());
abb.receiptdate=rs.getString("receiptdate");abb.setKeyValue("RECEIPTDATE",""+abb.getReceiptdate());
abb.receiptid=rs.getString("receiptid");abb.setKeyValue("RECEIPTID",""+abb.getReceiptid());
abb.boxid=rs.getString("boxid");abb.setKeyValue("BOXID",""+abb.getBoxid());
abb.expressrtndate=rs.getString("expressrtndate");abb.setKeyValue("EXPRESSRTNDATE",""+abb.getExpressrtndate());
abb.rptnomortdate=rs.getString("rptnomortdate");abb.setKeyValue("RPTNOMORTDATE",""+abb.getRptnomortdate());
abb.documentid=rs.getString("documentid");abb.setKeyValue("DOCUMENTID",""+abb.getDocumentid());
abb.warrantcnt=rs.getInt("warrantcnt");abb.setKeyValue("WARRANTCNT",""+abb.getWarrantcnt());
list.add(abb);
abb.operate_mode = "edit";
}public String getMortid() { if ( this.mortid == null ) return ""; return this.mortid;}
public String getLoanid() { if ( this.loanid == null ) return ""; return this.loanid;}
public String getMortdate() { if ( this.mortdate == null ) return ""; return this.mortdate;}
public String getMortecentercd() { if ( this.mortecentercd == null ) return ""; return this.mortecentercd;}
public String getReleasecondcd() { if ( this.releasecondcd == null ) return ""; return this.releasecondcd;}
public String getKeepcont() { if ( this.keepcont == null ) return ""; return this.keepcont;}
public String getExpressno() { if ( this.expressno == null ) return ""; return this.expressno;}
public String getExpressendsdate() { if ( this.expressendsdate == null ) return ""; return this.expressendsdate;}
public String getPaperrtndate() { if ( this.paperrtndate == null ) return ""; return this.paperrtndate;}
public String getExpressnote() { if ( this.expressnote == null ) return ""; return this.expressnote;}
public String getNomortreason() { if ( this.nomortreason == null ) return ""; return this.nomortreason;}
public String getSendflag() { if ( this.sendflag == null ) return ""; return this.sendflag;}
public String getRelayflag() { if ( this.relayflag == null ) return ""; return this.relayflag;}
public String getMortstatus() { if ( this.mortstatus == null ) return ""; return this.mortstatus;}
public String getOperid() { if ( this.operid == null ) return ""; return this.operid;}
public String getOperdate() {  if ( this.operdate == null ) { return ""; } else { return this.operdate.trim().split(" ")[0];} }public String getOperdateTime() {  if ( this.operdate == null ) return ""; return this.operdate.split("\\.")[0];}
public int getRecversion() { return this.recversion;}
public String getDeptid() { if ( this.deptid == null ) return ""; return this.deptid;}
public String getNomortreasoncd() { if ( this.nomortreasoncd == null ) return ""; return this.nomortreasoncd;}
public String getRptmortdate() { if ( this.rptmortdate == null ) return ""; return this.rptmortdate;}
public String getChgpaperdate() { if ( this.chgpaperdate == null ) return ""; return this.chgpaperdate;}
public String getChgpaperreason() { if ( this.chgpaperreason == null ) return ""; return this.chgpaperreason;}
public String getChgpaperreasoncd() { if ( this.chgpaperreasoncd == null ) return ""; return this.chgpaperreasoncd;}
public String getChgpaperrtndate() { if ( this.chgpaperrtndate == null ) return ""; return this.chgpaperrtndate;}
public String getClrpaperdate() { if ( this.clrpaperdate == null ) return ""; return this.clrpaperdate;}
public String getMortexpiredate() { if ( this.mortexpiredate == null ) return ""; return this.mortexpiredate;}
public String getMortoverrtndate() { if ( this.mortoverrtndate == null ) return ""; return this.mortoverrtndate;}
public String getMortregstatus() { if ( this.mortregstatus == null ) return ""; return this.mortregstatus;}
public String getReceiptdate() { if ( this.receiptdate == null ) return ""; return this.receiptdate;}
public String getReceiptid() { if ( this.receiptid == null ) return ""; return this.receiptid;}
public String getBoxid() { if ( this.boxid == null ) return ""; return this.boxid;}
public String getExpressrtndate() { if ( this.expressrtndate == null ) return ""; return this.expressrtndate;}
public String getRptnomortdate() { if ( this.rptnomortdate == null ) return ""; return this.rptnomortdate;}
public String getDocumentid() { if ( this.documentid == null ) return ""; return this.documentid;}
public int getWarrantcnt() { return this.warrantcnt;}
public void setMortid(String mortid) { sqlMaker.setField("mortid",mortid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMortid().equals(mortid)) cf.add("mortid",this.mortid,mortid); } this.mortid=mortid;}
public void setLoanid(String loanid) { sqlMaker.setField("loanid",loanid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLoanid().equals(loanid)) cf.add("loanid",this.loanid,loanid); } this.loanid=loanid;}
public void setMortdate(String mortdate) { sqlMaker.setField("mortdate",mortdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMortdate().equals(mortdate)) cf.add("mortdate",this.mortdate,mortdate); } this.mortdate=mortdate;}
public void setMortecentercd(String mortecentercd) { sqlMaker.setField("mortecentercd",mortecentercd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMortecentercd().equals(mortecentercd)) cf.add("mortecentercd",this.mortecentercd,mortecentercd); } this.mortecentercd=mortecentercd;}
public void setReleasecondcd(String releasecondcd) { sqlMaker.setField("releasecondcd",releasecondcd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getReleasecondcd().equals(releasecondcd)) cf.add("releasecondcd",this.releasecondcd,releasecondcd); } this.releasecondcd=releasecondcd;}
public void setKeepcont(String keepcont) { sqlMaker.setField("keepcont",keepcont,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getKeepcont().equals(keepcont)) cf.add("keepcont",this.keepcont,keepcont); } this.keepcont=keepcont;}
public void setExpressno(String expressno) { sqlMaker.setField("expressno",expressno,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getExpressno().equals(expressno)) cf.add("expressno",this.expressno,expressno); } this.expressno=expressno;}
public void setExpressendsdate(String expressendsdate) { sqlMaker.setField("expressendsdate",expressendsdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getExpressendsdate().equals(expressendsdate)) cf.add("expressendsdate",this.expressendsdate,expressendsdate); } this.expressendsdate=expressendsdate;}
public void setPaperrtndate(String paperrtndate) { sqlMaker.setField("paperrtndate",paperrtndate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getPaperrtndate().equals(paperrtndate)) cf.add("paperrtndate",this.paperrtndate,paperrtndate); } this.paperrtndate=paperrtndate;}
public void setExpressnote(String expressnote) { sqlMaker.setField("expressnote",expressnote,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getExpressnote().equals(expressnote)) cf.add("expressnote",this.expressnote,expressnote); } this.expressnote=expressnote;}
public void setNomortreason(String nomortreason) { sqlMaker.setField("nomortreason",nomortreason,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getNomortreason().equals(nomortreason)) cf.add("nomortreason",this.nomortreason,nomortreason); } this.nomortreason=nomortreason;}
public void setSendflag(String sendflag) { sqlMaker.setField("sendflag",sendflag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSendflag().equals(sendflag)) cf.add("sendflag",this.sendflag,sendflag); } this.sendflag=sendflag;}
public void setRelayflag(String relayflag) { sqlMaker.setField("relayflag",relayflag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRelayflag().equals(relayflag)) cf.add("relayflag",this.relayflag,relayflag); } this.relayflag=relayflag;}
public void setMortstatus(String mortstatus) { sqlMaker.setField("mortstatus",mortstatus,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMortstatus().equals(mortstatus)) cf.add("mortstatus",this.mortstatus,mortstatus); } this.mortstatus=mortstatus;}
public void setOperid(String operid) { sqlMaker.setField("operid",operid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOperid().equals(operid)) cf.add("operid",this.operid,operid); } this.operid=operid;}
public void setOperdate(String operdate) { sqlMaker.setField("operdate",operdate,Field.DATE); if (this.operate_mode.equals("edit")) { if (!this.getOperdate().equals(operdate)) cf.add("operdate",this.operdate,operdate); } this.operdate=operdate;}
public void setRecversion(int recversion) { sqlMaker.setField("recversion",""+recversion,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRecversion()!=recversion) cf.add("recversion",this.recversion+"",recversion+""); } this.recversion=recversion;}
public void setDeptid(String deptid) { sqlMaker.setField("deptid",deptid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDeptid().equals(deptid)) cf.add("deptid",this.deptid,deptid); } this.deptid=deptid;}
public void setNomortreasoncd(String nomortreasoncd) { sqlMaker.setField("nomortreasoncd",nomortreasoncd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getNomortreasoncd().equals(nomortreasoncd)) cf.add("nomortreasoncd",this.nomortreasoncd,nomortreasoncd); } this.nomortreasoncd=nomortreasoncd;}
public void setRptmortdate(String rptmortdate) { sqlMaker.setField("rptmortdate",rptmortdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRptmortdate().equals(rptmortdate)) cf.add("rptmortdate",this.rptmortdate,rptmortdate); } this.rptmortdate=rptmortdate;}
public void setChgpaperdate(String chgpaperdate) { sqlMaker.setField("chgpaperdate",chgpaperdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getChgpaperdate().equals(chgpaperdate)) cf.add("chgpaperdate",this.chgpaperdate,chgpaperdate); } this.chgpaperdate=chgpaperdate;}
public void setChgpaperreason(String chgpaperreason) { sqlMaker.setField("chgpaperreason",chgpaperreason,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getChgpaperreason().equals(chgpaperreason)) cf.add("chgpaperreason",this.chgpaperreason,chgpaperreason); } this.chgpaperreason=chgpaperreason;}
public void setChgpaperreasoncd(String chgpaperreasoncd) { sqlMaker.setField("chgpaperreasoncd",chgpaperreasoncd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getChgpaperreasoncd().equals(chgpaperreasoncd)) cf.add("chgpaperreasoncd",this.chgpaperreasoncd,chgpaperreasoncd); } this.chgpaperreasoncd=chgpaperreasoncd;}
public void setChgpaperrtndate(String chgpaperrtndate) { sqlMaker.setField("chgpaperrtndate",chgpaperrtndate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getChgpaperrtndate().equals(chgpaperrtndate)) cf.add("chgpaperrtndate",this.chgpaperrtndate,chgpaperrtndate); } this.chgpaperrtndate=chgpaperrtndate;}
public void setClrpaperdate(String clrpaperdate) { sqlMaker.setField("clrpaperdate",clrpaperdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getClrpaperdate().equals(clrpaperdate)) cf.add("clrpaperdate",this.clrpaperdate,clrpaperdate); } this.clrpaperdate=clrpaperdate;}
public void setMortexpiredate(String mortexpiredate) { sqlMaker.setField("mortexpiredate",mortexpiredate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMortexpiredate().equals(mortexpiredate)) cf.add("mortexpiredate",this.mortexpiredate,mortexpiredate); } this.mortexpiredate=mortexpiredate;}
public void setMortoverrtndate(String mortoverrtndate) { sqlMaker.setField("mortoverrtndate",mortoverrtndate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMortoverrtndate().equals(mortoverrtndate)) cf.add("mortoverrtndate",this.mortoverrtndate,mortoverrtndate); } this.mortoverrtndate=mortoverrtndate;}
public void setMortregstatus(String mortregstatus) { sqlMaker.setField("mortregstatus",mortregstatus,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMortregstatus().equals(mortregstatus)) cf.add("mortregstatus",this.mortregstatus,mortregstatus); } this.mortregstatus=mortregstatus;}
public void setReceiptdate(String receiptdate) { sqlMaker.setField("receiptdate",receiptdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getReceiptdate().equals(receiptdate)) cf.add("receiptdate",this.receiptdate,receiptdate); } this.receiptdate=receiptdate;}
public void setReceiptid(String receiptid) { sqlMaker.setField("receiptid",receiptid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getReceiptid().equals(receiptid)) cf.add("receiptid",this.receiptid,receiptid); } this.receiptid=receiptid;}
public void setBoxid(String boxid) { sqlMaker.setField("boxid",boxid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBoxid().equals(boxid)) cf.add("boxid",this.boxid,boxid); } this.boxid=boxid;}
public void setExpressrtndate(String expressrtndate) { sqlMaker.setField("expressrtndate",expressrtndate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getExpressrtndate().equals(expressrtndate)) cf.add("expressrtndate",this.expressrtndate,expressrtndate); } this.expressrtndate=expressrtndate;}
public void setRptnomortdate(String rptnomortdate) { sqlMaker.setField("rptnomortdate",rptnomortdate,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRptnomortdate().equals(rptnomortdate)) cf.add("rptnomortdate",this.rptnomortdate,rptnomortdate); } this.rptnomortdate=rptnomortdate;}
public void setDocumentid(String documentid) { sqlMaker.setField("documentid",documentid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDocumentid().equals(documentid)) cf.add("documentid",this.documentid,documentid); } this.documentid=documentid;}
public void setWarrantcnt(int warrantcnt) { sqlMaker.setField("warrantcnt",""+warrantcnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getWarrantcnt()!=warrantcnt) cf.add("warrantcnt",this.warrantcnt+"",warrantcnt+""); } this.warrantcnt=warrantcnt;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"mortid") !=null ) {this.setMortid(actionRequest.getFieldValue(i,"mortid"));}
if ( actionRequest.getFieldValue(i,"loanid") !=null ) {this.setLoanid(actionRequest.getFieldValue(i,"loanid"));}
if ( actionRequest.getFieldValue(i,"mortdate") !=null ) {this.setMortdate(actionRequest.getFieldValue(i,"mortdate"));}
if ( actionRequest.getFieldValue(i,"mortecentercd") !=null ) {this.setMortecentercd(actionRequest.getFieldValue(i,"mortecentercd"));}
if ( actionRequest.getFieldValue(i,"releasecondcd") !=null ) {this.setReleasecondcd(actionRequest.getFieldValue(i,"releasecondcd"));}
if ( actionRequest.getFieldValue(i,"keepcont") !=null ) {this.setKeepcont(actionRequest.getFieldValue(i,"keepcont"));}
if ( actionRequest.getFieldValue(i,"expressno") !=null ) {this.setExpressno(actionRequest.getFieldValue(i,"expressno"));}
if ( actionRequest.getFieldValue(i,"expressendsdate") !=null ) {this.setExpressendsdate(actionRequest.getFieldValue(i,"expressendsdate"));}
if ( actionRequest.getFieldValue(i,"paperrtndate") !=null ) {this.setPaperrtndate(actionRequest.getFieldValue(i,"paperrtndate"));}
if ( actionRequest.getFieldValue(i,"expressnote") !=null ) {this.setExpressnote(actionRequest.getFieldValue(i,"expressnote"));}
if ( actionRequest.getFieldValue(i,"nomortreason") !=null ) {this.setNomortreason(actionRequest.getFieldValue(i,"nomortreason"));}
if ( actionRequest.getFieldValue(i,"sendflag") !=null ) {this.setSendflag(actionRequest.getFieldValue(i,"sendflag"));}
if ( actionRequest.getFieldValue(i,"relayflag") !=null ) {this.setRelayflag(actionRequest.getFieldValue(i,"relayflag"));}
if ( actionRequest.getFieldValue(i,"mortstatus") !=null ) {this.setMortstatus(actionRequest.getFieldValue(i,"mortstatus"));}
if ( actionRequest.getFieldValue(i,"operid") !=null ) {this.setOperid(actionRequest.getFieldValue(i,"operid"));}
if ( actionRequest.getFieldValue(i,"operdate") !=null ) {this.setOperdate(actionRequest.getFieldValue(i,"operdate"));}
if ( actionRequest.getFieldValue(i,"recversion") !=null && actionRequest.getFieldValue(i,"recversion").trim().length() > 0 ) {this.setRecversion(Integer.parseInt(actionRequest.getFieldValue(i,"recversion")));}
if ( actionRequest.getFieldValue(i,"deptid") !=null ) {this.setDeptid(actionRequest.getFieldValue(i,"deptid"));}
if ( actionRequest.getFieldValue(i,"nomortreasoncd") !=null ) {this.setNomortreasoncd(actionRequest.getFieldValue(i,"nomortreasoncd"));}
if ( actionRequest.getFieldValue(i,"rptmortdate") !=null ) {this.setRptmortdate(actionRequest.getFieldValue(i,"rptmortdate"));}
if ( actionRequest.getFieldValue(i,"chgpaperdate") !=null ) {this.setChgpaperdate(actionRequest.getFieldValue(i,"chgpaperdate"));}
if ( actionRequest.getFieldValue(i,"chgpaperreason") !=null ) {this.setChgpaperreason(actionRequest.getFieldValue(i,"chgpaperreason"));}
if ( actionRequest.getFieldValue(i,"chgpaperreasoncd") !=null ) {this.setChgpaperreasoncd(actionRequest.getFieldValue(i,"chgpaperreasoncd"));}
if ( actionRequest.getFieldValue(i,"chgpaperrtndate") !=null ) {this.setChgpaperrtndate(actionRequest.getFieldValue(i,"chgpaperrtndate"));}
if ( actionRequest.getFieldValue(i,"clrpaperdate") !=null ) {this.setClrpaperdate(actionRequest.getFieldValue(i,"clrpaperdate"));}
if ( actionRequest.getFieldValue(i,"mortexpiredate") !=null ) {this.setMortexpiredate(actionRequest.getFieldValue(i,"mortexpiredate"));}
if ( actionRequest.getFieldValue(i,"mortoverrtndate") !=null ) {this.setMortoverrtndate(actionRequest.getFieldValue(i,"mortoverrtndate"));}
if ( actionRequest.getFieldValue(i,"mortregstatus") !=null ) {this.setMortregstatus(actionRequest.getFieldValue(i,"mortregstatus"));}
if ( actionRequest.getFieldValue(i,"receiptdate") !=null ) {this.setReceiptdate(actionRequest.getFieldValue(i,"receiptdate"));}
if ( actionRequest.getFieldValue(i,"receiptid") !=null ) {this.setReceiptid(actionRequest.getFieldValue(i,"receiptid"));}
if ( actionRequest.getFieldValue(i,"boxid") !=null ) {this.setBoxid(actionRequest.getFieldValue(i,"boxid"));}
if ( actionRequest.getFieldValue(i,"expressrtndate") !=null ) {this.setExpressrtndate(actionRequest.getFieldValue(i,"expressrtndate"));}
if ( actionRequest.getFieldValue(i,"rptnomortdate") !=null ) {this.setRptnomortdate(actionRequest.getFieldValue(i,"rptnomortdate"));}
if ( actionRequest.getFieldValue(i,"documentid") !=null ) {this.setDocumentid(actionRequest.getFieldValue(i,"documentid"));}
if ( actionRequest.getFieldValue(i,"warrantcnt") !=null && actionRequest.getFieldValue(i,"warrantcnt").trim().length() > 0 ) {this.setWarrantcnt(Integer.parseInt(actionRequest.getFieldValue(i,"warrantcnt")));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { LNMORTINFO obj = (LNMORTINFO)super.clone();obj.setMortid(obj.mortid);
obj.setLoanid(obj.loanid);
obj.setMortdate(obj.mortdate);
obj.setMortecentercd(obj.mortecentercd);
obj.setReleasecondcd(obj.releasecondcd);
obj.setKeepcont(obj.keepcont);
obj.setExpressno(obj.expressno);
obj.setExpressendsdate(obj.expressendsdate);
obj.setPaperrtndate(obj.paperrtndate);
obj.setExpressnote(obj.expressnote);
obj.setNomortreason(obj.nomortreason);
obj.setSendflag(obj.sendflag);
obj.setRelayflag(obj.relayflag);
obj.setMortstatus(obj.mortstatus);
obj.setOperid(obj.operid);
obj.setOperdate(obj.operdate);
obj.setRecversion(obj.recversion);
obj.setDeptid(obj.deptid);
obj.setNomortreasoncd(obj.nomortreasoncd);
obj.setRptmortdate(obj.rptmortdate);
obj.setChgpaperdate(obj.chgpaperdate);
obj.setChgpaperreason(obj.chgpaperreason);
obj.setChgpaperreasoncd(obj.chgpaperreasoncd);
obj.setChgpaperrtndate(obj.chgpaperrtndate);
obj.setClrpaperdate(obj.clrpaperdate);
obj.setMortexpiredate(obj.mortexpiredate);
obj.setMortoverrtndate(obj.mortoverrtndate);
obj.setMortregstatus(obj.mortregstatus);
obj.setReceiptdate(obj.receiptdate);
obj.setReceiptid(obj.receiptid);
obj.setBoxid(obj.boxid);
obj.setExpressrtndate(obj.expressrtndate);
obj.setRptnomortdate(obj.rptnomortdate);
obj.setDocumentid(obj.documentid);
obj.setWarrantcnt(obj.warrantcnt);
return obj;}}