package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class ODSBCRDCRTTRAD extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new ODSBCRDCRTTRAD().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new ODSBCRDCRTTRAD().findAndLockByWhere(sSqlWhere);      }       public static ODSBCRDCRTTRAD findFirst(String sSqlWhere) {           return (ODSBCRDCRTTRAD)new ODSBCRDCRTTRAD().findFirstByWhere(sSqlWhere);      }       public static ODSBCRDCRTTRAD findFirstAndLock(String sSqlWhere) {           return (ODSBCRDCRTTRAD)new ODSBCRDCRTTRAD().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new ODSBCRDCRTTRAD().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTTRAD bean = new ODSBCRDCRTTRAD();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTTRAD bean = new ODSBCRDCRTTRAD();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static ODSBCRDCRTTRAD findFirst(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTTRAD bean = new ODSBCRDCRTTRAD();           bean.setAutoRelease(isAutoRelease);           return (ODSBCRDCRTTRAD)bean.findFirstByWhere(sSqlWhere);      }       public static ODSBCRDCRTTRAD findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTTRAD bean = new ODSBCRDCRTTRAD();           bean.setAutoRelease(isAutoRelease);           return (ODSBCRDCRTTRAD)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTTRAD bean = new ODSBCRDCRTTRAD();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new ODSBCRDCRTTRAD().findByWhereByRow(sSqlWhere,row);      } String actp;
String crd_typ;
String crd_no;
String tx_day;
String tx_cd;
double inac_amt;
String inac_date;
String src_cd;
String dhi_auth_code;
String ref_date;
String ref_batch_id;
String ref_seq_no;
String merchant_org;
String dhi_merch_acct;
String merchant_knd_no;
String filler_2;
String filler_3;
String tx_dscrp_cntry;
String merch_state;
String dhi_posting_flag;
String ref_nmbr;
String chgbk_flg;
String orig_curr_code;
double orig_curr_amt;
int orig_curr_decimal;
String onus_curr_conv;
String dhi_pos_mode;
String mail_order_flg;
String cmbi_merch_no;
String dhi_installment_ind;
String trad_chanel;
String tx_type;
String tx_cd_new;
public static final String TABLENAME ="odsb_crd_crt_trad";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
ODSBCRDCRTTRAD abb = new ODSBCRDCRTTRAD();
abb.actp=rs.getString("actp");abb.setKeyValue("ACTP",""+abb.getActp());
abb.crd_typ=rs.getString("crd_typ");abb.setKeyValue("CRD_TYP",""+abb.getCrd_typ());
abb.crd_no=rs.getString("crd_no");abb.setKeyValue("CRD_NO",""+abb.getCrd_no());
abb.tx_day=rs.getString("tx_day");abb.setKeyValue("TX_DAY",""+abb.getTx_day());
abb.tx_cd=rs.getString("tx_cd");abb.setKeyValue("TX_CD",""+abb.getTx_cd());
abb.inac_amt=rs.getDouble("inac_amt");abb.setKeyValue("INAC_AMT",""+abb.getInac_amt());
abb.inac_date=rs.getString("inac_date");abb.setKeyValue("INAC_DATE",""+abb.getInac_date());
abb.src_cd=rs.getString("src_cd");abb.setKeyValue("SRC_CD",""+abb.getSrc_cd());
abb.dhi_auth_code=rs.getString("dhi_auth_code");abb.setKeyValue("DHI_AUTH_CODE",""+abb.getDhi_auth_code());
abb.ref_date=rs.getString("ref_date");abb.setKeyValue("REF_DATE",""+abb.getRef_date());
abb.ref_batch_id=rs.getString("ref_batch_id");abb.setKeyValue("REF_BATCH_ID",""+abb.getRef_batch_id());
abb.ref_seq_no=rs.getString("ref_seq_no");abb.setKeyValue("REF_SEQ_NO",""+abb.getRef_seq_no());
abb.merchant_org=rs.getString("merchant_org");abb.setKeyValue("MERCHANT_ORG",""+abb.getMerchant_org());
abb.dhi_merch_acct=rs.getString("dhi_merch_acct");abb.setKeyValue("DHI_MERCH_ACCT",""+abb.getDhi_merch_acct());
abb.merchant_knd_no=rs.getString("merchant_knd_no");abb.setKeyValue("MERCHANT_KND_NO",""+abb.getMerchant_knd_no());
abb.filler_2=rs.getString("filler_2");abb.setKeyValue("FILLER_2",""+abb.getFiller_2());
abb.filler_3=rs.getString("filler_3");abb.setKeyValue("FILLER_3",""+abb.getFiller_3());
abb.tx_dscrp_cntry=rs.getString("tx_dscrp_cntry");abb.setKeyValue("TX_DSCRP_CNTRY",""+abb.getTx_dscrp_cntry());
abb.merch_state=rs.getString("merch_state");abb.setKeyValue("MERCH_STATE",""+abb.getMerch_state());
abb.dhi_posting_flag=rs.getString("dhi_posting_flag");abb.setKeyValue("DHI_POSTING_FLAG",""+abb.getDhi_posting_flag());
abb.ref_nmbr=rs.getString("ref_nmbr");abb.setKeyValue("REF_NMBR",""+abb.getRef_nmbr());
abb.chgbk_flg=rs.getString("chgbk_flg");abb.setKeyValue("CHGBK_FLG",""+abb.getChgbk_flg());
abb.orig_curr_code=rs.getString("orig_curr_code");abb.setKeyValue("ORIG_CURR_CODE",""+abb.getOrig_curr_code());
abb.orig_curr_amt=rs.getDouble("orig_curr_amt");abb.setKeyValue("ORIG_CURR_AMT",""+abb.getOrig_curr_amt());
abb.orig_curr_decimal=rs.getInt("orig_curr_decimal");abb.setKeyValue("ORIG_CURR_DECIMAL",""+abb.getOrig_curr_decimal());
abb.onus_curr_conv=rs.getString("onus_curr_conv");abb.setKeyValue("ONUS_CURR_CONV",""+abb.getOnus_curr_conv());
abb.dhi_pos_mode=rs.getString("dhi_pos_mode");abb.setKeyValue("DHI_POS_MODE",""+abb.getDhi_pos_mode());
abb.mail_order_flg=rs.getString("mail_order_flg");abb.setKeyValue("MAIL_ORDER_FLG",""+abb.getMail_order_flg());
abb.cmbi_merch_no=rs.getString("cmbi_merch_no");abb.setKeyValue("CMBI_MERCH_NO",""+abb.getCmbi_merch_no());
abb.dhi_installment_ind=rs.getString("dhi_installment_ind");abb.setKeyValue("DHI_INSTALLMENT_IND",""+abb.getDhi_installment_ind());
abb.trad_chanel=rs.getString("trad_chanel");abb.setKeyValue("TRAD_CHANEL",""+abb.getTrad_chanel());
abb.tx_type=rs.getString("tx_type");abb.setKeyValue("TX_TYPE",""+abb.getTx_type());
abb.tx_cd_new=rs.getString("tx_cd_new");abb.setKeyValue("TX_CD_NEW",""+abb.getTx_cd_new());
list.add(abb);
abb.operate_mode = "edit";
}public String getActp() { if ( this.actp == null ) return ""; return this.actp;}
public String getCrd_typ() { if ( this.crd_typ == null ) return ""; return this.crd_typ;}
public String getCrd_no() { if ( this.crd_no == null ) return ""; return this.crd_no;}
public String getTx_day() { if ( this.tx_day == null ) return ""; return this.tx_day;}
public String getTx_cd() { if ( this.tx_cd == null ) return ""; return this.tx_cd;}
public double getInac_amt() { return this.inac_amt;}
public String getInac_date() { if ( this.inac_date == null ) return ""; return this.inac_date;}
public String getSrc_cd() { if ( this.src_cd == null ) return ""; return this.src_cd;}
public String getDhi_auth_code() { if ( this.dhi_auth_code == null ) return ""; return this.dhi_auth_code;}
public String getRef_date() { if ( this.ref_date == null ) return ""; return this.ref_date;}
public String getRef_batch_id() { if ( this.ref_batch_id == null ) return ""; return this.ref_batch_id;}
public String getRef_seq_no() { if ( this.ref_seq_no == null ) return ""; return this.ref_seq_no;}
public String getMerchant_org() { if ( this.merchant_org == null ) return ""; return this.merchant_org;}
public String getDhi_merch_acct() { if ( this.dhi_merch_acct == null ) return ""; return this.dhi_merch_acct;}
public String getMerchant_knd_no() { if ( this.merchant_knd_no == null ) return ""; return this.merchant_knd_no;}
public String getFiller_2() { if ( this.filler_2 == null ) return ""; return this.filler_2;}
public String getFiller_3() { if ( this.filler_3 == null ) return ""; return this.filler_3;}
public String getTx_dscrp_cntry() { if ( this.tx_dscrp_cntry == null ) return ""; return this.tx_dscrp_cntry;}
public String getMerch_state() { if ( this.merch_state == null ) return ""; return this.merch_state;}
public String getDhi_posting_flag() { if ( this.dhi_posting_flag == null ) return ""; return this.dhi_posting_flag;}
public String getRef_nmbr() { if ( this.ref_nmbr == null ) return ""; return this.ref_nmbr;}
public String getChgbk_flg() { if ( this.chgbk_flg == null ) return ""; return this.chgbk_flg;}
public String getOrig_curr_code() { if ( this.orig_curr_code == null ) return ""; return this.orig_curr_code;}
public double getOrig_curr_amt() { return this.orig_curr_amt;}
public int getOrig_curr_decimal() { return this.orig_curr_decimal;}
public String getOnus_curr_conv() { if ( this.onus_curr_conv == null ) return ""; return this.onus_curr_conv;}
public String getDhi_pos_mode() { if ( this.dhi_pos_mode == null ) return ""; return this.dhi_pos_mode;}
public String getMail_order_flg() { if ( this.mail_order_flg == null ) return ""; return this.mail_order_flg;}
public String getCmbi_merch_no() { if ( this.cmbi_merch_no == null ) return ""; return this.cmbi_merch_no;}
public String getDhi_installment_ind() { if ( this.dhi_installment_ind == null ) return ""; return this.dhi_installment_ind;}
public String getTrad_chanel() { if ( this.trad_chanel == null ) return ""; return this.trad_chanel;}
public String getTx_type() { if ( this.tx_type == null ) return ""; return this.tx_type;}
public String getTx_cd_new() { if ( this.tx_cd_new == null ) return ""; return this.tx_cd_new;}
public void setActp(String actp) { sqlMaker.setField("actp",actp,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getActp().equals(actp)) cf.add("actp",this.actp,actp); } this.actp=actp;}
public void setCrd_typ(String crd_typ) { sqlMaker.setField("crd_typ",crd_typ,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrd_typ().equals(crd_typ)) cf.add("crd_typ",this.crd_typ,crd_typ); } this.crd_typ=crd_typ;}
public void setCrd_no(String crd_no) { sqlMaker.setField("crd_no",crd_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrd_no().equals(crd_no)) cf.add("crd_no",this.crd_no,crd_no); } this.crd_no=crd_no;}
public void setTx_day(String tx_day) { sqlMaker.setField("tx_day",tx_day,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTx_day().equals(tx_day)) cf.add("tx_day",this.tx_day,tx_day); } this.tx_day=tx_day;}
public void setTx_cd(String tx_cd) { sqlMaker.setField("tx_cd",tx_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTx_cd().equals(tx_cd)) cf.add("tx_cd",this.tx_cd,tx_cd); } this.tx_cd=tx_cd;}
public void setInac_amt(double inac_amt) { sqlMaker.setField("inac_amt",""+inac_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getInac_amt()!=inac_amt) cf.add("inac_amt",this.inac_amt+"",inac_amt+""); } this.inac_amt=inac_amt;}
public void setInac_date(String inac_date) { sqlMaker.setField("inac_date",inac_date,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getInac_date().equals(inac_date)) cf.add("inac_date",this.inac_date,inac_date); } this.inac_date=inac_date;}
public void setSrc_cd(String src_cd) { sqlMaker.setField("src_cd",src_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSrc_cd().equals(src_cd)) cf.add("src_cd",this.src_cd,src_cd); } this.src_cd=src_cd;}
public void setDhi_auth_code(String dhi_auth_code) { sqlMaker.setField("dhi_auth_code",dhi_auth_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDhi_auth_code().equals(dhi_auth_code)) cf.add("dhi_auth_code",this.dhi_auth_code,dhi_auth_code); } this.dhi_auth_code=dhi_auth_code;}
public void setRef_date(String ref_date) { sqlMaker.setField("ref_date",ref_date,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRef_date().equals(ref_date)) cf.add("ref_date",this.ref_date,ref_date); } this.ref_date=ref_date;}
public void setRef_batch_id(String ref_batch_id) { sqlMaker.setField("ref_batch_id",ref_batch_id,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRef_batch_id().equals(ref_batch_id)) cf.add("ref_batch_id",this.ref_batch_id,ref_batch_id); } this.ref_batch_id=ref_batch_id;}
public void setRef_seq_no(String ref_seq_no) { sqlMaker.setField("ref_seq_no",ref_seq_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRef_seq_no().equals(ref_seq_no)) cf.add("ref_seq_no",this.ref_seq_no,ref_seq_no); } this.ref_seq_no=ref_seq_no;}
public void setMerchant_org(String merchant_org) { sqlMaker.setField("merchant_org",merchant_org,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMerchant_org().equals(merchant_org)) cf.add("merchant_org",this.merchant_org,merchant_org); } this.merchant_org=merchant_org;}
public void setDhi_merch_acct(String dhi_merch_acct) { sqlMaker.setField("dhi_merch_acct",dhi_merch_acct,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDhi_merch_acct().equals(dhi_merch_acct)) cf.add("dhi_merch_acct",this.dhi_merch_acct,dhi_merch_acct); } this.dhi_merch_acct=dhi_merch_acct;}
public void setMerchant_knd_no(String merchant_knd_no) { sqlMaker.setField("merchant_knd_no",merchant_knd_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMerchant_knd_no().equals(merchant_knd_no)) cf.add("merchant_knd_no",this.merchant_knd_no,merchant_knd_no); } this.merchant_knd_no=merchant_knd_no;}
public void setFiller_2(String filler_2) { sqlMaker.setField("filler_2",filler_2,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getFiller_2().equals(filler_2)) cf.add("filler_2",this.filler_2,filler_2); } this.filler_2=filler_2;}
public void setFiller_3(String filler_3) { sqlMaker.setField("filler_3",filler_3,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getFiller_3().equals(filler_3)) cf.add("filler_3",this.filler_3,filler_3); } this.filler_3=filler_3;}
public void setTx_dscrp_cntry(String tx_dscrp_cntry) { sqlMaker.setField("tx_dscrp_cntry",tx_dscrp_cntry,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTx_dscrp_cntry().equals(tx_dscrp_cntry)) cf.add("tx_dscrp_cntry",this.tx_dscrp_cntry,tx_dscrp_cntry); } this.tx_dscrp_cntry=tx_dscrp_cntry;}
public void setMerch_state(String merch_state) { sqlMaker.setField("merch_state",merch_state,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMerch_state().equals(merch_state)) cf.add("merch_state",this.merch_state,merch_state); } this.merch_state=merch_state;}
public void setDhi_posting_flag(String dhi_posting_flag) { sqlMaker.setField("dhi_posting_flag",dhi_posting_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDhi_posting_flag().equals(dhi_posting_flag)) cf.add("dhi_posting_flag",this.dhi_posting_flag,dhi_posting_flag); } this.dhi_posting_flag=dhi_posting_flag;}
public void setRef_nmbr(String ref_nmbr) { sqlMaker.setField("ref_nmbr",ref_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRef_nmbr().equals(ref_nmbr)) cf.add("ref_nmbr",this.ref_nmbr,ref_nmbr); } this.ref_nmbr=ref_nmbr;}
public void setChgbk_flg(String chgbk_flg) { sqlMaker.setField("chgbk_flg",chgbk_flg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getChgbk_flg().equals(chgbk_flg)) cf.add("chgbk_flg",this.chgbk_flg,chgbk_flg); } this.chgbk_flg=chgbk_flg;}
public void setOrig_curr_code(String orig_curr_code) { sqlMaker.setField("orig_curr_code",orig_curr_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOrig_curr_code().equals(orig_curr_code)) cf.add("orig_curr_code",this.orig_curr_code,orig_curr_code); } this.orig_curr_code=orig_curr_code;}
public void setOrig_curr_amt(double orig_curr_amt) { sqlMaker.setField("orig_curr_amt",""+orig_curr_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOrig_curr_amt()!=orig_curr_amt) cf.add("orig_curr_amt",this.orig_curr_amt+"",orig_curr_amt+""); } this.orig_curr_amt=orig_curr_amt;}
public void setOrig_curr_decimal(int orig_curr_decimal) { sqlMaker.setField("orig_curr_decimal",""+orig_curr_decimal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOrig_curr_decimal()!=orig_curr_decimal) cf.add("orig_curr_decimal",this.orig_curr_decimal+"",orig_curr_decimal+""); } this.orig_curr_decimal=orig_curr_decimal;}
public void setOnus_curr_conv(String onus_curr_conv) { sqlMaker.setField("onus_curr_conv",onus_curr_conv,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOnus_curr_conv().equals(onus_curr_conv)) cf.add("onus_curr_conv",this.onus_curr_conv,onus_curr_conv); } this.onus_curr_conv=onus_curr_conv;}
public void setDhi_pos_mode(String dhi_pos_mode) { sqlMaker.setField("dhi_pos_mode",dhi_pos_mode,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDhi_pos_mode().equals(dhi_pos_mode)) cf.add("dhi_pos_mode",this.dhi_pos_mode,dhi_pos_mode); } this.dhi_pos_mode=dhi_pos_mode;}
public void setMail_order_flg(String mail_order_flg) { sqlMaker.setField("mail_order_flg",mail_order_flg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMail_order_flg().equals(mail_order_flg)) cf.add("mail_order_flg",this.mail_order_flg,mail_order_flg); } this.mail_order_flg=mail_order_flg;}
public void setCmbi_merch_no(String cmbi_merch_no) { sqlMaker.setField("cmbi_merch_no",cmbi_merch_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCmbi_merch_no().equals(cmbi_merch_no)) cf.add("cmbi_merch_no",this.cmbi_merch_no,cmbi_merch_no); } this.cmbi_merch_no=cmbi_merch_no;}
public void setDhi_installment_ind(String dhi_installment_ind) { sqlMaker.setField("dhi_installment_ind",dhi_installment_ind,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDhi_installment_ind().equals(dhi_installment_ind)) cf.add("dhi_installment_ind",this.dhi_installment_ind,dhi_installment_ind); } this.dhi_installment_ind=dhi_installment_ind;}
public void setTrad_chanel(String trad_chanel) { sqlMaker.setField("trad_chanel",trad_chanel,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTrad_chanel().equals(trad_chanel)) cf.add("trad_chanel",this.trad_chanel,trad_chanel); } this.trad_chanel=trad_chanel;}
public void setTx_type(String tx_type) { sqlMaker.setField("tx_type",tx_type,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTx_type().equals(tx_type)) cf.add("tx_type",this.tx_type,tx_type); } this.tx_type=tx_type;}
public void setTx_cd_new(String tx_cd_new) { sqlMaker.setField("tx_cd_new",tx_cd_new,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTx_cd_new().equals(tx_cd_new)) cf.add("tx_cd_new",this.tx_cd_new,tx_cd_new); } this.tx_cd_new=tx_cd_new;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"actp") !=null ) {this.setActp(actionRequest.getFieldValue(i,"actp"));}
if ( actionRequest.getFieldValue(i,"crd_typ") !=null ) {this.setCrd_typ(actionRequest.getFieldValue(i,"crd_typ"));}
if ( actionRequest.getFieldValue(i,"crd_no") !=null ) {this.setCrd_no(actionRequest.getFieldValue(i,"crd_no"));}
if ( actionRequest.getFieldValue(i,"tx_day") !=null ) {this.setTx_day(actionRequest.getFieldValue(i,"tx_day"));}
if ( actionRequest.getFieldValue(i,"tx_cd") !=null ) {this.setTx_cd(actionRequest.getFieldValue(i,"tx_cd"));}
if ( actionRequest.getFieldValue(i,"inac_amt") !=null && actionRequest.getFieldValue(i,"inac_amt").trim().length() > 0 ) {this.setInac_amt(Double.parseDouble(actionRequest.getFieldValue(i,"inac_amt")));}
if ( actionRequest.getFieldValue(i,"inac_date") !=null ) {this.setInac_date(actionRequest.getFieldValue(i,"inac_date"));}
if ( actionRequest.getFieldValue(i,"src_cd") !=null ) {this.setSrc_cd(actionRequest.getFieldValue(i,"src_cd"));}
if ( actionRequest.getFieldValue(i,"dhi_auth_code") !=null ) {this.setDhi_auth_code(actionRequest.getFieldValue(i,"dhi_auth_code"));}
if ( actionRequest.getFieldValue(i,"ref_date") !=null ) {this.setRef_date(actionRequest.getFieldValue(i,"ref_date"));}
if ( actionRequest.getFieldValue(i,"ref_batch_id") !=null ) {this.setRef_batch_id(actionRequest.getFieldValue(i,"ref_batch_id"));}
if ( actionRequest.getFieldValue(i,"ref_seq_no") !=null ) {this.setRef_seq_no(actionRequest.getFieldValue(i,"ref_seq_no"));}
if ( actionRequest.getFieldValue(i,"merchant_org") !=null ) {this.setMerchant_org(actionRequest.getFieldValue(i,"merchant_org"));}
if ( actionRequest.getFieldValue(i,"dhi_merch_acct") !=null ) {this.setDhi_merch_acct(actionRequest.getFieldValue(i,"dhi_merch_acct"));}
if ( actionRequest.getFieldValue(i,"merchant_knd_no") !=null ) {this.setMerchant_knd_no(actionRequest.getFieldValue(i,"merchant_knd_no"));}
if ( actionRequest.getFieldValue(i,"filler_2") !=null ) {this.setFiller_2(actionRequest.getFieldValue(i,"filler_2"));}
if ( actionRequest.getFieldValue(i,"filler_3") !=null ) {this.setFiller_3(actionRequest.getFieldValue(i,"filler_3"));}
if ( actionRequest.getFieldValue(i,"tx_dscrp_cntry") !=null ) {this.setTx_dscrp_cntry(actionRequest.getFieldValue(i,"tx_dscrp_cntry"));}
if ( actionRequest.getFieldValue(i,"merch_state") !=null ) {this.setMerch_state(actionRequest.getFieldValue(i,"merch_state"));}
if ( actionRequest.getFieldValue(i,"dhi_posting_flag") !=null ) {this.setDhi_posting_flag(actionRequest.getFieldValue(i,"dhi_posting_flag"));}
if ( actionRequest.getFieldValue(i,"ref_nmbr") !=null ) {this.setRef_nmbr(actionRequest.getFieldValue(i,"ref_nmbr"));}
if ( actionRequest.getFieldValue(i,"chgbk_flg") !=null ) {this.setChgbk_flg(actionRequest.getFieldValue(i,"chgbk_flg"));}
if ( actionRequest.getFieldValue(i,"orig_curr_code") !=null ) {this.setOrig_curr_code(actionRequest.getFieldValue(i,"orig_curr_code"));}
if ( actionRequest.getFieldValue(i,"orig_curr_amt") !=null && actionRequest.getFieldValue(i,"orig_curr_amt").trim().length() > 0 ) {this.setOrig_curr_amt(Double.parseDouble(actionRequest.getFieldValue(i,"orig_curr_amt")));}
if ( actionRequest.getFieldValue(i,"orig_curr_decimal") !=null && actionRequest.getFieldValue(i,"orig_curr_decimal").trim().length() > 0 ) {this.setOrig_curr_decimal(Integer.parseInt(actionRequest.getFieldValue(i,"orig_curr_decimal")));}
if ( actionRequest.getFieldValue(i,"onus_curr_conv") !=null ) {this.setOnus_curr_conv(actionRequest.getFieldValue(i,"onus_curr_conv"));}
if ( actionRequest.getFieldValue(i,"dhi_pos_mode") !=null ) {this.setDhi_pos_mode(actionRequest.getFieldValue(i,"dhi_pos_mode"));}
if ( actionRequest.getFieldValue(i,"mail_order_flg") !=null ) {this.setMail_order_flg(actionRequest.getFieldValue(i,"mail_order_flg"));}
if ( actionRequest.getFieldValue(i,"cmbi_merch_no") !=null ) {this.setCmbi_merch_no(actionRequest.getFieldValue(i,"cmbi_merch_no"));}
if ( actionRequest.getFieldValue(i,"dhi_installment_ind") !=null ) {this.setDhi_installment_ind(actionRequest.getFieldValue(i,"dhi_installment_ind"));}
if ( actionRequest.getFieldValue(i,"trad_chanel") !=null ) {this.setTrad_chanel(actionRequest.getFieldValue(i,"trad_chanel"));}
if ( actionRequest.getFieldValue(i,"tx_type") !=null ) {this.setTx_type(actionRequest.getFieldValue(i,"tx_type"));}
if ( actionRequest.getFieldValue(i,"tx_cd_new") !=null ) {this.setTx_cd_new(actionRequest.getFieldValue(i,"tx_cd_new"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { ODSBCRDCRTTRAD obj = (ODSBCRDCRTTRAD)super.clone();obj.setActp(obj.actp);
obj.setCrd_typ(obj.crd_typ);
obj.setCrd_no(obj.crd_no);
obj.setTx_day(obj.tx_day);
obj.setTx_cd(obj.tx_cd);
obj.setInac_amt(obj.inac_amt);
obj.setInac_date(obj.inac_date);
obj.setSrc_cd(obj.src_cd);
obj.setDhi_auth_code(obj.dhi_auth_code);
obj.setRef_date(obj.ref_date);
obj.setRef_batch_id(obj.ref_batch_id);
obj.setRef_seq_no(obj.ref_seq_no);
obj.setMerchant_org(obj.merchant_org);
obj.setDhi_merch_acct(obj.dhi_merch_acct);
obj.setMerchant_knd_no(obj.merchant_knd_no);
obj.setFiller_2(obj.filler_2);
obj.setFiller_3(obj.filler_3);
obj.setTx_dscrp_cntry(obj.tx_dscrp_cntry);
obj.setMerch_state(obj.merch_state);
obj.setDhi_posting_flag(obj.dhi_posting_flag);
obj.setRef_nmbr(obj.ref_nmbr);
obj.setChgbk_flg(obj.chgbk_flg);
obj.setOrig_curr_code(obj.orig_curr_code);
obj.setOrig_curr_amt(obj.orig_curr_amt);
obj.setOrig_curr_decimal(obj.orig_curr_decimal);
obj.setOnus_curr_conv(obj.onus_curr_conv);
obj.setDhi_pos_mode(obj.dhi_pos_mode);
obj.setMail_order_flg(obj.mail_order_flg);
obj.setCmbi_merch_no(obj.cmbi_merch_no);
obj.setDhi_installment_ind(obj.dhi_installment_ind);
obj.setTrad_chanel(obj.trad_chanel);
obj.setTx_type(obj.tx_type);
obj.setTx_cd_new(obj.tx_cd_new);
return obj;}}