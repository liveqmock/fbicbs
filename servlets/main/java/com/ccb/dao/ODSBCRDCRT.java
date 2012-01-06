package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class ODSBCRDCRT extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new ODSBCRDCRT().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new ODSBCRDCRT().findAndLockByWhere(sSqlWhere);      }       public static ODSBCRDCRT findFirst(String sSqlWhere) {           return (ODSBCRDCRT)new ODSBCRDCRT().findFirstByWhere(sSqlWhere);      }       public static ODSBCRDCRT findFirstAndLock(String sSqlWhere) {           return (ODSBCRDCRT)new ODSBCRDCRT().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new ODSBCRDCRT().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRT bean = new ODSBCRDCRT();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRT bean = new ODSBCRDCRT();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static ODSBCRDCRT findFirst(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRT bean = new ODSBCRDCRT();           bean.setAutoRelease(isAutoRelease);           return (ODSBCRDCRT)bean.findFirstByWhere(sSqlWhere);      }       public static ODSBCRDCRT findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRT bean = new ODSBCRDCRT();           bean.setAutoRelease(isAutoRelease);           return (ODSBCRDCRT)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRT bean = new ODSBCRDCRT();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new ODSBCRDCRT().findByWhereByRow(sSqlWhere,row);      } String crd_no;
String org_no;
String curr_cd;
String crd_typ;
String ods_src_dt;
String out_crd_instn_cd;
String ochr_customer_org;
String cust_no;
String suppl_cust_org;
String suppl_cust_id;
String short_name;
String officer;
String block_code;
String alt_block_code;
String sr_ai_prim_ind;
String rela_desc;
String crd_sts;
String erea_cd;
String stmt_day;
int stmt_freq;
double credit_limit;
double lst_crlimit;
double perm_credit_limit;
double temp_credit_limit;
double avail_crlmt;
double ctd_used_amnt;
double outst_auth_amt;
int outst_auth_cnt;
double cash_limit;
double csh_adv_lim;
double csh_adv_avail;
double avl_cash_bal;
double ln_totl_ln_amt_hypo_amt;
double instl_limit;
double part_bal;
double outst_instl_amt;
double use_instl_limit;
double large_instl_amnt_outst;
String dte_next_stmt;
String birth_dt;
String last_sts_chg_dt;
String open_card_dt;
String close_dt;
String dte_join;
String crd_valid_dt;
String dte_lst_card_expire;
String crd_prod_dt;
String dte_lst_req;
String join_dark_name_list;
String block_dt;
String dte_alt_block_code;
String trn_crd_eff_dt;
String year_cost_dt;
String dte_activation;
String dte_lst_activation;
String crlimit_temp_eff_dte;
String crlimit_temp_exp_dte;
String last_consume_dt;
String dte_usage_fee;
String last_cash_dt;
String cm_dte_lst_active;
String dte_lst_card_issue;
String dte_rtl_cnt_rst;
String dte_lst_rtl_cnt_rst;
String dte_crlimit;
String dte_lst_crlimit;
String cm_dte_lst_maint;
String dte_lst_stmt;
String embosser_name1;
String embosser_name2;
String embosser_name3;
String embosser_name4;
String card_action;
int nmbr_requested;
String kind_typ;
int issue_cnt;
int nmbr_cards_ret;
String req_card_type;
String card_prev_action;
String pre_reissue_action;
String reissue_action;
String xfr_org_nmbr;
String xfr_type_nmbr;
String xfr_acct_nmbr;
String crd_corr_acct;
String account1_org;
String account1_type;
String account1_curr_code;
String account2_nmbr;
String account2_org;
String account2_type;
String account2_curr_code;
String account3_org;
String account3_type;
String account3_nmbr;
String account3_curr_code;
String first_usg_flag;
String cash_adv_flg;
String of_annual_fee_flg;
String card_mailer_ind;
String activation_cd;
String last_activation_cd;
String vip_card;
String amrl_alimony_agreement;
String sms_flag;
double verify_password_amnt;
int nmbr_rtl_auth_ytd;
int lst_nmbr_rtl_auth_ytd;
String appid;
String src_cd;
String market;
String market_org_cd;
String ods_load_dt;
public static final String TABLENAME ="odsb_crd_crt";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
ODSBCRDCRT abb = new ODSBCRDCRT();
abb.crd_no=rs.getString("crd_no");abb.setKeyValue("CRD_NO",""+abb.getCrd_no());
abb.org_no=rs.getString("org_no");abb.setKeyValue("ORG_NO",""+abb.getOrg_no());
abb.curr_cd=rs.getString("curr_cd");abb.setKeyValue("CURR_CD",""+abb.getCurr_cd());
abb.crd_typ=rs.getString("crd_typ");abb.setKeyValue("CRD_TYP",""+abb.getCrd_typ());
abb.ods_src_dt=rs.getString("ods_src_dt");abb.setKeyValue("ODS_SRC_DT",""+abb.getOds_src_dt());
abb.out_crd_instn_cd=rs.getString("out_crd_instn_cd");abb.setKeyValue("OUT_CRD_INSTN_CD",""+abb.getOut_crd_instn_cd());
abb.ochr_customer_org=rs.getString("ochr_customer_org");abb.setKeyValue("OCHR_CUSTOMER_ORG",""+abb.getOchr_customer_org());
abb.cust_no=rs.getString("cust_no");abb.setKeyValue("CUST_NO",""+abb.getCust_no());
abb.suppl_cust_org=rs.getString("suppl_cust_org");abb.setKeyValue("SUPPL_CUST_ORG",""+abb.getSuppl_cust_org());
abb.suppl_cust_id=rs.getString("suppl_cust_id");abb.setKeyValue("SUPPL_CUST_ID",""+abb.getSuppl_cust_id());
abb.short_name=rs.getString("short_name");abb.setKeyValue("SHORT_NAME",""+abb.getShort_name());
abb.officer=rs.getString("officer");abb.setKeyValue("OFFICER",""+abb.getOfficer());
abb.block_code=rs.getString("block_code");abb.setKeyValue("BLOCK_CODE",""+abb.getBlock_code());
abb.alt_block_code=rs.getString("alt_block_code");abb.setKeyValue("ALT_BLOCK_CODE",""+abb.getAlt_block_code());
abb.sr_ai_prim_ind=rs.getString("sr_ai_prim_ind");abb.setKeyValue("SR_AI_PRIM_IND",""+abb.getSr_ai_prim_ind());
abb.rela_desc=rs.getString("rela_desc");abb.setKeyValue("RELA_DESC",""+abb.getRela_desc());
abb.crd_sts=rs.getString("crd_sts");abb.setKeyValue("CRD_STS",""+abb.getCrd_sts());
abb.erea_cd=rs.getString("erea_cd");abb.setKeyValue("EREA_CD",""+abb.getErea_cd());
abb.stmt_day=rs.getString("stmt_day");abb.setKeyValue("STMT_DAY",""+abb.getStmt_day());
abb.stmt_freq=rs.getInt("stmt_freq");abb.setKeyValue("STMT_FREQ",""+abb.getStmt_freq());
abb.credit_limit=rs.getDouble("credit_limit");abb.setKeyValue("CREDIT_LIMIT",""+abb.getCredit_limit());
abb.lst_crlimit=rs.getDouble("lst_crlimit");abb.setKeyValue("LST_CRLIMIT",""+abb.getLst_crlimit());
abb.perm_credit_limit=rs.getDouble("perm_credit_limit");abb.setKeyValue("PERM_CREDIT_LIMIT",""+abb.getPerm_credit_limit());
abb.temp_credit_limit=rs.getDouble("temp_credit_limit");abb.setKeyValue("TEMP_CREDIT_LIMIT",""+abb.getTemp_credit_limit());
abb.avail_crlmt=rs.getDouble("avail_crlmt");abb.setKeyValue("AVAIL_CRLMT",""+abb.getAvail_crlmt());
abb.ctd_used_amnt=rs.getDouble("ctd_used_amnt");abb.setKeyValue("CTD_USED_AMNT",""+abb.getCtd_used_amnt());
abb.outst_auth_amt=rs.getDouble("outst_auth_amt");abb.setKeyValue("OUTST_AUTH_AMT",""+abb.getOutst_auth_amt());
abb.outst_auth_cnt=rs.getInt("outst_auth_cnt");abb.setKeyValue("OUTST_AUTH_CNT",""+abb.getOutst_auth_cnt());
abb.cash_limit=rs.getDouble("cash_limit");abb.setKeyValue("CASH_LIMIT",""+abb.getCash_limit());
abb.csh_adv_lim=rs.getDouble("csh_adv_lim");abb.setKeyValue("CSH_ADV_LIM",""+abb.getCsh_adv_lim());
abb.csh_adv_avail=rs.getDouble("csh_adv_avail");abb.setKeyValue("CSH_ADV_AVAIL",""+abb.getCsh_adv_avail());
abb.avl_cash_bal=rs.getDouble("avl_cash_bal");abb.setKeyValue("AVL_CASH_BAL",""+abb.getAvl_cash_bal());
abb.ln_totl_ln_amt_hypo_amt=rs.getDouble("ln_totl_ln_amt_hypo_amt");abb.setKeyValue("LN_TOTL_LN_AMT_HYPO_AMT",""+abb.getLn_totl_ln_amt_hypo_amt());
abb.instl_limit=rs.getDouble("instl_limit");abb.setKeyValue("INSTL_LIMIT",""+abb.getInstl_limit());
abb.part_bal=rs.getDouble("part_bal");abb.setKeyValue("PART_BAL",""+abb.getPart_bal());
abb.outst_instl_amt=rs.getDouble("outst_instl_amt");abb.setKeyValue("OUTST_INSTL_AMT",""+abb.getOutst_instl_amt());
abb.use_instl_limit=rs.getDouble("use_instl_limit");abb.setKeyValue("USE_INSTL_LIMIT",""+abb.getUse_instl_limit());
abb.large_instl_amnt_outst=rs.getDouble("large_instl_amnt_outst");abb.setKeyValue("LARGE_INSTL_AMNT_OUTST",""+abb.getLarge_instl_amnt_outst());
abb.dte_next_stmt=rs.getString("dte_next_stmt");abb.setKeyValue("DTE_NEXT_STMT",""+abb.getDte_next_stmt());
abb.birth_dt=rs.getString("birth_dt");abb.setKeyValue("BIRTH_DT",""+abb.getBirth_dt());
abb.last_sts_chg_dt=rs.getString("last_sts_chg_dt");abb.setKeyValue("LAST_STS_CHG_DT",""+abb.getLast_sts_chg_dt());
abb.open_card_dt=rs.getString("open_card_dt");abb.setKeyValue("OPEN_CARD_DT",""+abb.getOpen_card_dt());
abb.close_dt=rs.getString("close_dt");abb.setKeyValue("CLOSE_DT",""+abb.getClose_dt());
abb.dte_join=rs.getString("dte_join");abb.setKeyValue("DTE_JOIN",""+abb.getDte_join());
abb.crd_valid_dt=rs.getString("crd_valid_dt");abb.setKeyValue("CRD_VALID_DT",""+abb.getCrd_valid_dt());
abb.dte_lst_card_expire=rs.getString("dte_lst_card_expire");abb.setKeyValue("DTE_LST_CARD_EXPIRE",""+abb.getDte_lst_card_expire());
abb.crd_prod_dt=rs.getString("crd_prod_dt");abb.setKeyValue("CRD_PROD_DT",""+abb.getCrd_prod_dt());
abb.dte_lst_req=rs.getString("dte_lst_req");abb.setKeyValue("DTE_LST_REQ",""+abb.getDte_lst_req());
abb.join_dark_name_list=rs.getString("join_dark_name_list");abb.setKeyValue("JOIN_DARK_NAME_LIST",""+abb.getJoin_dark_name_list());
abb.block_dt=rs.getString("block_dt");abb.setKeyValue("BLOCK_DT",""+abb.getBlock_dt());
abb.dte_alt_block_code=rs.getString("dte_alt_block_code");abb.setKeyValue("DTE_ALT_BLOCK_CODE",""+abb.getDte_alt_block_code());
abb.trn_crd_eff_dt=rs.getString("trn_crd_eff_dt");abb.setKeyValue("TRN_CRD_EFF_DT",""+abb.getTrn_crd_eff_dt());
abb.year_cost_dt=rs.getString("year_cost_dt");abb.setKeyValue("YEAR_COST_DT",""+abb.getYear_cost_dt());
abb.dte_activation=rs.getString("dte_activation");abb.setKeyValue("DTE_ACTIVATION",""+abb.getDte_activation());
abb.dte_lst_activation=rs.getString("dte_lst_activation");abb.setKeyValue("DTE_LST_ACTIVATION",""+abb.getDte_lst_activation());
abb.crlimit_temp_eff_dte=rs.getString("crlimit_temp_eff_dte");abb.setKeyValue("CRLIMIT_TEMP_EFF_DTE",""+abb.getCrlimit_temp_eff_dte());
abb.crlimit_temp_exp_dte=rs.getString("crlimit_temp_exp_dte");abb.setKeyValue("CRLIMIT_TEMP_EXP_DTE",""+abb.getCrlimit_temp_exp_dte());
abb.last_consume_dt=rs.getString("last_consume_dt");abb.setKeyValue("LAST_CONSUME_DT",""+abb.getLast_consume_dt());
abb.dte_usage_fee=rs.getString("dte_usage_fee");abb.setKeyValue("DTE_USAGE_FEE",""+abb.getDte_usage_fee());
abb.last_cash_dt=rs.getString("last_cash_dt");abb.setKeyValue("LAST_CASH_DT",""+abb.getLast_cash_dt());
abb.cm_dte_lst_active=rs.getString("cm_dte_lst_active");abb.setKeyValue("CM_DTE_LST_ACTIVE",""+abb.getCm_dte_lst_active());
abb.dte_lst_card_issue=rs.getString("dte_lst_card_issue");abb.setKeyValue("DTE_LST_CARD_ISSUE",""+abb.getDte_lst_card_issue());
abb.dte_rtl_cnt_rst=rs.getString("dte_rtl_cnt_rst");abb.setKeyValue("DTE_RTL_CNT_RST",""+abb.getDte_rtl_cnt_rst());
abb.dte_lst_rtl_cnt_rst=rs.getString("dte_lst_rtl_cnt_rst");abb.setKeyValue("DTE_LST_RTL_CNT_RST",""+abb.getDte_lst_rtl_cnt_rst());
abb.dte_crlimit=rs.getString("dte_crlimit");abb.setKeyValue("DTE_CRLIMIT",""+abb.getDte_crlimit());
abb.dte_lst_crlimit=rs.getString("dte_lst_crlimit");abb.setKeyValue("DTE_LST_CRLIMIT",""+abb.getDte_lst_crlimit());
abb.cm_dte_lst_maint=rs.getString("cm_dte_lst_maint");abb.setKeyValue("CM_DTE_LST_MAINT",""+abb.getCm_dte_lst_maint());
abb.dte_lst_stmt=rs.getString("dte_lst_stmt");abb.setKeyValue("DTE_LST_STMT",""+abb.getDte_lst_stmt());
abb.embosser_name1=rs.getString("embosser_name1");abb.setKeyValue("EMBOSSER_NAME1",""+abb.getEmbosser_name1());
abb.embosser_name2=rs.getString("embosser_name2");abb.setKeyValue("EMBOSSER_NAME2",""+abb.getEmbosser_name2());
abb.embosser_name3=rs.getString("embosser_name3");abb.setKeyValue("EMBOSSER_NAME3",""+abb.getEmbosser_name3());
abb.embosser_name4=rs.getString("embosser_name4");abb.setKeyValue("EMBOSSER_NAME4",""+abb.getEmbosser_name4());
abb.card_action=rs.getString("card_action");abb.setKeyValue("CARD_ACTION",""+abb.getCard_action());
abb.nmbr_requested=rs.getInt("nmbr_requested");abb.setKeyValue("NMBR_REQUESTED",""+abb.getNmbr_requested());
abb.kind_typ=rs.getString("kind_typ");abb.setKeyValue("KIND_TYP",""+abb.getKind_typ());
abb.issue_cnt=rs.getInt("issue_cnt");abb.setKeyValue("ISSUE_CNT",""+abb.getIssue_cnt());
abb.nmbr_cards_ret=rs.getInt("nmbr_cards_ret");abb.setKeyValue("NMBR_CARDS_RET",""+abb.getNmbr_cards_ret());
abb.req_card_type=rs.getString("req_card_type");abb.setKeyValue("REQ_CARD_TYPE",""+abb.getReq_card_type());
abb.card_prev_action=rs.getString("card_prev_action");abb.setKeyValue("CARD_PREV_ACTION",""+abb.getCard_prev_action());
abb.pre_reissue_action=rs.getString("pre_reissue_action");abb.setKeyValue("PRE_REISSUE_ACTION",""+abb.getPre_reissue_action());
abb.reissue_action=rs.getString("reissue_action");abb.setKeyValue("REISSUE_ACTION",""+abb.getReissue_action());
abb.xfr_org_nmbr=rs.getString("xfr_org_nmbr");abb.setKeyValue("XFR_ORG_NMBR",""+abb.getXfr_org_nmbr());
abb.xfr_type_nmbr=rs.getString("xfr_type_nmbr");abb.setKeyValue("XFR_TYPE_NMBR",""+abb.getXfr_type_nmbr());
abb.xfr_acct_nmbr=rs.getString("xfr_acct_nmbr");abb.setKeyValue("XFR_ACCT_NMBR",""+abb.getXfr_acct_nmbr());
abb.crd_corr_acct=rs.getString("crd_corr_acct");abb.setKeyValue("CRD_CORR_ACCT",""+abb.getCrd_corr_acct());
abb.account1_org=rs.getString("account1_org");abb.setKeyValue("ACCOUNT1_ORG",""+abb.getAccount1_org());
abb.account1_type=rs.getString("account1_type");abb.setKeyValue("ACCOUNT1_TYPE",""+abb.getAccount1_type());
abb.account1_curr_code=rs.getString("account1_curr_code");abb.setKeyValue("ACCOUNT1_CURR_CODE",""+abb.getAccount1_curr_code());
abb.account2_nmbr=rs.getString("account2_nmbr");abb.setKeyValue("ACCOUNT2_NMBR",""+abb.getAccount2_nmbr());
abb.account2_org=rs.getString("account2_org");abb.setKeyValue("ACCOUNT2_ORG",""+abb.getAccount2_org());
abb.account2_type=rs.getString("account2_type");abb.setKeyValue("ACCOUNT2_TYPE",""+abb.getAccount2_type());
abb.account2_curr_code=rs.getString("account2_curr_code");abb.setKeyValue("ACCOUNT2_CURR_CODE",""+abb.getAccount2_curr_code());
abb.account3_org=rs.getString("account3_org");abb.setKeyValue("ACCOUNT3_ORG",""+abb.getAccount3_org());
abb.account3_type=rs.getString("account3_type");abb.setKeyValue("ACCOUNT3_TYPE",""+abb.getAccount3_type());
abb.account3_nmbr=rs.getString("account3_nmbr");abb.setKeyValue("ACCOUNT3_NMBR",""+abb.getAccount3_nmbr());
abb.account3_curr_code=rs.getString("account3_curr_code");abb.setKeyValue("ACCOUNT3_CURR_CODE",""+abb.getAccount3_curr_code());
abb.first_usg_flag=rs.getString("first_usg_flag");abb.setKeyValue("FIRST_USG_FLAG",""+abb.getFirst_usg_flag());
abb.cash_adv_flg=rs.getString("cash_adv_flg");abb.setKeyValue("CASH_ADV_FLG",""+abb.getCash_adv_flg());
abb.of_annual_fee_flg=rs.getString("of_annual_fee_flg");abb.setKeyValue("OF_ANNUAL_FEE_FLG",""+abb.getOf_annual_fee_flg());
abb.card_mailer_ind=rs.getString("card_mailer_ind");abb.setKeyValue("CARD_MAILER_IND",""+abb.getCard_mailer_ind());
abb.activation_cd=rs.getString("activation_cd");abb.setKeyValue("ACTIVATION_CD",""+abb.getActivation_cd());
abb.last_activation_cd=rs.getString("last_activation_cd");abb.setKeyValue("LAST_ACTIVATION_CD",""+abb.getLast_activation_cd());
abb.vip_card=rs.getString("vip_card");abb.setKeyValue("VIP_CARD",""+abb.getVip_card());
abb.amrl_alimony_agreement=rs.getString("amrl_alimony_agreement");abb.setKeyValue("AMRL_ALIMONY_AGREEMENT",""+abb.getAmrl_alimony_agreement());
abb.sms_flag=rs.getString("sms_flag");abb.setKeyValue("SMS_FLAG",""+abb.getSms_flag());
abb.verify_password_amnt=rs.getDouble("verify_password_amnt");abb.setKeyValue("VERIFY_PASSWORD_AMNT",""+abb.getVerify_password_amnt());
abb.nmbr_rtl_auth_ytd=rs.getInt("nmbr_rtl_auth_ytd");abb.setKeyValue("NMBR_RTL_AUTH_YTD",""+abb.getNmbr_rtl_auth_ytd());
abb.lst_nmbr_rtl_auth_ytd=rs.getInt("lst_nmbr_rtl_auth_ytd");abb.setKeyValue("LST_NMBR_RTL_AUTH_YTD",""+abb.getLst_nmbr_rtl_auth_ytd());
abb.appid=rs.getString("appid");abb.setKeyValue("APPID",""+abb.getAppid());
abb.src_cd=rs.getString("src_cd");abb.setKeyValue("SRC_CD",""+abb.getSrc_cd());
abb.market=rs.getString("market");abb.setKeyValue("MARKET",""+abb.getMarket());
abb.market_org_cd=rs.getString("market_org_cd");abb.setKeyValue("MARKET_ORG_CD",""+abb.getMarket_org_cd());
abb.ods_load_dt=rs.getString("ods_load_dt");abb.setKeyValue("ODS_LOAD_DT",""+abb.getOds_load_dt());
list.add(abb);
abb.operate_mode = "edit";
}public String getCrd_no() { if ( this.crd_no == null ) return ""; return this.crd_no;}
public String getOrg_no() { if ( this.org_no == null ) return ""; return this.org_no;}
public String getCurr_cd() { if ( this.curr_cd == null ) return ""; return this.curr_cd;}
public String getCrd_typ() { if ( this.crd_typ == null ) return ""; return this.crd_typ;}
public String getOds_src_dt() { if ( this.ods_src_dt == null ) return ""; return this.ods_src_dt;}
public String getOut_crd_instn_cd() { if ( this.out_crd_instn_cd == null ) return ""; return this.out_crd_instn_cd;}
public String getOchr_customer_org() { if ( this.ochr_customer_org == null ) return ""; return this.ochr_customer_org;}
public String getCust_no() { if ( this.cust_no == null ) return ""; return this.cust_no;}
public String getSuppl_cust_org() { if ( this.suppl_cust_org == null ) return ""; return this.suppl_cust_org;}
public String getSuppl_cust_id() { if ( this.suppl_cust_id == null ) return ""; return this.suppl_cust_id;}
public String getShort_name() { if ( this.short_name == null ) return ""; return this.short_name;}
public String getOfficer() { if ( this.officer == null ) return ""; return this.officer;}
public String getBlock_code() { if ( this.block_code == null ) return ""; return this.block_code;}
public String getAlt_block_code() { if ( this.alt_block_code == null ) return ""; return this.alt_block_code;}
public String getSr_ai_prim_ind() { if ( this.sr_ai_prim_ind == null ) return ""; return this.sr_ai_prim_ind;}
public String getRela_desc() { if ( this.rela_desc == null ) return ""; return this.rela_desc;}
public String getCrd_sts() { if ( this.crd_sts == null ) return ""; return this.crd_sts;}
public String getErea_cd() { if ( this.erea_cd == null ) return ""; return this.erea_cd;}
public String getStmt_day() { if ( this.stmt_day == null ) return ""; return this.stmt_day;}
public int getStmt_freq() { return this.stmt_freq;}
public double getCredit_limit() { return this.credit_limit;}
public double getLst_crlimit() { return this.lst_crlimit;}
public double getPerm_credit_limit() { return this.perm_credit_limit;}
public double getTemp_credit_limit() { return this.temp_credit_limit;}
public double getAvail_crlmt() { return this.avail_crlmt;}
public double getCtd_used_amnt() { return this.ctd_used_amnt;}
public double getOutst_auth_amt() { return this.outst_auth_amt;}
public int getOutst_auth_cnt() { return this.outst_auth_cnt;}
public double getCash_limit() { return this.cash_limit;}
public double getCsh_adv_lim() { return this.csh_adv_lim;}
public double getCsh_adv_avail() { return this.csh_adv_avail;}
public double getAvl_cash_bal() { return this.avl_cash_bal;}
public double getLn_totl_ln_amt_hypo_amt() { return this.ln_totl_ln_amt_hypo_amt;}
public double getInstl_limit() { return this.instl_limit;}
public double getPart_bal() { return this.part_bal;}
public double getOutst_instl_amt() { return this.outst_instl_amt;}
public double getUse_instl_limit() { return this.use_instl_limit;}
public double getLarge_instl_amnt_outst() { return this.large_instl_amnt_outst;}
public String getDte_next_stmt() { if ( this.dte_next_stmt == null ) return ""; return this.dte_next_stmt;}
public String getBirth_dt() { if ( this.birth_dt == null ) return ""; return this.birth_dt;}
public String getLast_sts_chg_dt() { if ( this.last_sts_chg_dt == null ) return ""; return this.last_sts_chg_dt;}
public String getOpen_card_dt() { if ( this.open_card_dt == null ) return ""; return this.open_card_dt;}
public String getClose_dt() { if ( this.close_dt == null ) return ""; return this.close_dt;}
public String getDte_join() { if ( this.dte_join == null ) return ""; return this.dte_join;}
public String getCrd_valid_dt() { if ( this.crd_valid_dt == null ) return ""; return this.crd_valid_dt;}
public String getDte_lst_card_expire() { if ( this.dte_lst_card_expire == null ) return ""; return this.dte_lst_card_expire;}
public String getCrd_prod_dt() { if ( this.crd_prod_dt == null ) return ""; return this.crd_prod_dt;}
public String getDte_lst_req() { if ( this.dte_lst_req == null ) return ""; return this.dte_lst_req;}
public String getJoin_dark_name_list() { if ( this.join_dark_name_list == null ) return ""; return this.join_dark_name_list;}
public String getBlock_dt() { if ( this.block_dt == null ) return ""; return this.block_dt;}
public String getDte_alt_block_code() { if ( this.dte_alt_block_code == null ) return ""; return this.dte_alt_block_code;}
public String getTrn_crd_eff_dt() { if ( this.trn_crd_eff_dt == null ) return ""; return this.trn_crd_eff_dt;}
public String getYear_cost_dt() { if ( this.year_cost_dt == null ) return ""; return this.year_cost_dt;}
public String getDte_activation() { if ( this.dte_activation == null ) return ""; return this.dte_activation;}
public String getDte_lst_activation() { if ( this.dte_lst_activation == null ) return ""; return this.dte_lst_activation;}
public String getCrlimit_temp_eff_dte() { if ( this.crlimit_temp_eff_dte == null ) return ""; return this.crlimit_temp_eff_dte;}
public String getCrlimit_temp_exp_dte() { if ( this.crlimit_temp_exp_dte == null ) return ""; return this.crlimit_temp_exp_dte;}
public String getLast_consume_dt() { if ( this.last_consume_dt == null ) return ""; return this.last_consume_dt;}
public String getDte_usage_fee() { if ( this.dte_usage_fee == null ) return ""; return this.dte_usage_fee;}
public String getLast_cash_dt() { if ( this.last_cash_dt == null ) return ""; return this.last_cash_dt;}
public String getCm_dte_lst_active() { if ( this.cm_dte_lst_active == null ) return ""; return this.cm_dte_lst_active;}
public String getDte_lst_card_issue() { if ( this.dte_lst_card_issue == null ) return ""; return this.dte_lst_card_issue;}
public String getDte_rtl_cnt_rst() { if ( this.dte_rtl_cnt_rst == null ) return ""; return this.dte_rtl_cnt_rst;}
public String getDte_lst_rtl_cnt_rst() { if ( this.dte_lst_rtl_cnt_rst == null ) return ""; return this.dte_lst_rtl_cnt_rst;}
public String getDte_crlimit() { if ( this.dte_crlimit == null ) return ""; return this.dte_crlimit;}
public String getDte_lst_crlimit() { if ( this.dte_lst_crlimit == null ) return ""; return this.dte_lst_crlimit;}
public String getCm_dte_lst_maint() { if ( this.cm_dte_lst_maint == null ) return ""; return this.cm_dte_lst_maint;}
public String getDte_lst_stmt() { if ( this.dte_lst_stmt == null ) return ""; return this.dte_lst_stmt;}
public String getEmbosser_name1() { if ( this.embosser_name1 == null ) return ""; return this.embosser_name1;}
public String getEmbosser_name2() { if ( this.embosser_name2 == null ) return ""; return this.embosser_name2;}
public String getEmbosser_name3() { if ( this.embosser_name3 == null ) return ""; return this.embosser_name3;}
public String getEmbosser_name4() { if ( this.embosser_name4 == null ) return ""; return this.embosser_name4;}
public String getCard_action() { if ( this.card_action == null ) return ""; return this.card_action;}
public int getNmbr_requested() { return this.nmbr_requested;}
public String getKind_typ() { if ( this.kind_typ == null ) return ""; return this.kind_typ;}
public int getIssue_cnt() { return this.issue_cnt;}
public int getNmbr_cards_ret() { return this.nmbr_cards_ret;}
public String getReq_card_type() { if ( this.req_card_type == null ) return ""; return this.req_card_type;}
public String getCard_prev_action() { if ( this.card_prev_action == null ) return ""; return this.card_prev_action;}
public String getPre_reissue_action() { if ( this.pre_reissue_action == null ) return ""; return this.pre_reissue_action;}
public String getReissue_action() { if ( this.reissue_action == null ) return ""; return this.reissue_action;}
public String getXfr_org_nmbr() { if ( this.xfr_org_nmbr == null ) return ""; return this.xfr_org_nmbr;}
public String getXfr_type_nmbr() { if ( this.xfr_type_nmbr == null ) return ""; return this.xfr_type_nmbr;}
public String getXfr_acct_nmbr() { if ( this.xfr_acct_nmbr == null ) return ""; return this.xfr_acct_nmbr;}
public String getCrd_corr_acct() { if ( this.crd_corr_acct == null ) return ""; return this.crd_corr_acct;}
public String getAccount1_org() { if ( this.account1_org == null ) return ""; return this.account1_org;}
public String getAccount1_type() { if ( this.account1_type == null ) return ""; return this.account1_type;}
public String getAccount1_curr_code() { if ( this.account1_curr_code == null ) return ""; return this.account1_curr_code;}
public String getAccount2_nmbr() { if ( this.account2_nmbr == null ) return ""; return this.account2_nmbr;}
public String getAccount2_org() { if ( this.account2_org == null ) return ""; return this.account2_org;}
public String getAccount2_type() { if ( this.account2_type == null ) return ""; return this.account2_type;}
public String getAccount2_curr_code() { if ( this.account2_curr_code == null ) return ""; return this.account2_curr_code;}
public String getAccount3_org() { if ( this.account3_org == null ) return ""; return this.account3_org;}
public String getAccount3_type() { if ( this.account3_type == null ) return ""; return this.account3_type;}
public String getAccount3_nmbr() { if ( this.account3_nmbr == null ) return ""; return this.account3_nmbr;}
public String getAccount3_curr_code() { if ( this.account3_curr_code == null ) return ""; return this.account3_curr_code;}
public String getFirst_usg_flag() { if ( this.first_usg_flag == null ) return ""; return this.first_usg_flag;}
public String getCash_adv_flg() { if ( this.cash_adv_flg == null ) return ""; return this.cash_adv_flg;}
public String getOf_annual_fee_flg() { if ( this.of_annual_fee_flg == null ) return ""; return this.of_annual_fee_flg;}
public String getCard_mailer_ind() { if ( this.card_mailer_ind == null ) return ""; return this.card_mailer_ind;}
public String getActivation_cd() { if ( this.activation_cd == null ) return ""; return this.activation_cd;}
public String getLast_activation_cd() { if ( this.last_activation_cd == null ) return ""; return this.last_activation_cd;}
public String getVip_card() { if ( this.vip_card == null ) return ""; return this.vip_card;}
public String getAmrl_alimony_agreement() { if ( this.amrl_alimony_agreement == null ) return ""; return this.amrl_alimony_agreement;}
public String getSms_flag() { if ( this.sms_flag == null ) return ""; return this.sms_flag;}
public double getVerify_password_amnt() { return this.verify_password_amnt;}
public int getNmbr_rtl_auth_ytd() { return this.nmbr_rtl_auth_ytd;}
public int getLst_nmbr_rtl_auth_ytd() { return this.lst_nmbr_rtl_auth_ytd;}
public String getAppid() { if ( this.appid == null ) return ""; return this.appid;}
public String getSrc_cd() { if ( this.src_cd == null ) return ""; return this.src_cd;}
public String getMarket() { if ( this.market == null ) return ""; return this.market;}
public String getMarket_org_cd() { if ( this.market_org_cd == null ) return ""; return this.market_org_cd;}
public String getOds_load_dt() { if ( this.ods_load_dt == null ) return ""; return this.ods_load_dt;}
public void setCrd_no(String crd_no) { sqlMaker.setField("crd_no",crd_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrd_no().equals(crd_no)) cf.add("crd_no",this.crd_no,crd_no); } this.crd_no=crd_no;}
public void setOrg_no(String org_no) { sqlMaker.setField("org_no",org_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOrg_no().equals(org_no)) cf.add("org_no",this.org_no,org_no); } this.org_no=org_no;}
public void setCurr_cd(String curr_cd) { sqlMaker.setField("curr_cd",curr_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCurr_cd().equals(curr_cd)) cf.add("curr_cd",this.curr_cd,curr_cd); } this.curr_cd=curr_cd;}
public void setCrd_typ(String crd_typ) { sqlMaker.setField("crd_typ",crd_typ,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrd_typ().equals(crd_typ)) cf.add("crd_typ",this.crd_typ,crd_typ); } this.crd_typ=crd_typ;}
public void setOds_src_dt(String ods_src_dt) { sqlMaker.setField("ods_src_dt",ods_src_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOds_src_dt().equals(ods_src_dt)) cf.add("ods_src_dt",this.ods_src_dt,ods_src_dt); } this.ods_src_dt=ods_src_dt;}
public void setOut_crd_instn_cd(String out_crd_instn_cd) { sqlMaker.setField("out_crd_instn_cd",out_crd_instn_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOut_crd_instn_cd().equals(out_crd_instn_cd)) cf.add("out_crd_instn_cd",this.out_crd_instn_cd,out_crd_instn_cd); } this.out_crd_instn_cd=out_crd_instn_cd;}
public void setOchr_customer_org(String ochr_customer_org) { sqlMaker.setField("ochr_customer_org",ochr_customer_org,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOchr_customer_org().equals(ochr_customer_org)) cf.add("ochr_customer_org",this.ochr_customer_org,ochr_customer_org); } this.ochr_customer_org=ochr_customer_org;}
public void setCust_no(String cust_no) { sqlMaker.setField("cust_no",cust_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCust_no().equals(cust_no)) cf.add("cust_no",this.cust_no,cust_no); } this.cust_no=cust_no;}
public void setSuppl_cust_org(String suppl_cust_org) { sqlMaker.setField("suppl_cust_org",suppl_cust_org,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSuppl_cust_org().equals(suppl_cust_org)) cf.add("suppl_cust_org",this.suppl_cust_org,suppl_cust_org); } this.suppl_cust_org=suppl_cust_org;}
public void setSuppl_cust_id(String suppl_cust_id) { sqlMaker.setField("suppl_cust_id",suppl_cust_id,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSuppl_cust_id().equals(suppl_cust_id)) cf.add("suppl_cust_id",this.suppl_cust_id,suppl_cust_id); } this.suppl_cust_id=suppl_cust_id;}
public void setShort_name(String short_name) { sqlMaker.setField("short_name",short_name,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getShort_name().equals(short_name)) cf.add("short_name",this.short_name,short_name); } this.short_name=short_name;}
public void setOfficer(String officer) { sqlMaker.setField("officer",officer,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOfficer().equals(officer)) cf.add("officer",this.officer,officer); } this.officer=officer;}
public void setBlock_code(String block_code) { sqlMaker.setField("block_code",block_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBlock_code().equals(block_code)) cf.add("block_code",this.block_code,block_code); } this.block_code=block_code;}
public void setAlt_block_code(String alt_block_code) { sqlMaker.setField("alt_block_code",alt_block_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAlt_block_code().equals(alt_block_code)) cf.add("alt_block_code",this.alt_block_code,alt_block_code); } this.alt_block_code=alt_block_code;}
public void setSr_ai_prim_ind(String sr_ai_prim_ind) { sqlMaker.setField("sr_ai_prim_ind",sr_ai_prim_ind,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSr_ai_prim_ind().equals(sr_ai_prim_ind)) cf.add("sr_ai_prim_ind",this.sr_ai_prim_ind,sr_ai_prim_ind); } this.sr_ai_prim_ind=sr_ai_prim_ind;}
public void setRela_desc(String rela_desc) { sqlMaker.setField("rela_desc",rela_desc,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRela_desc().equals(rela_desc)) cf.add("rela_desc",this.rela_desc,rela_desc); } this.rela_desc=rela_desc;}
public void setCrd_sts(String crd_sts) { sqlMaker.setField("crd_sts",crd_sts,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrd_sts().equals(crd_sts)) cf.add("crd_sts",this.crd_sts,crd_sts); } this.crd_sts=crd_sts;}
public void setErea_cd(String erea_cd) { sqlMaker.setField("erea_cd",erea_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getErea_cd().equals(erea_cd)) cf.add("erea_cd",this.erea_cd,erea_cd); } this.erea_cd=erea_cd;}
public void setStmt_day(String stmt_day) { sqlMaker.setField("stmt_day",stmt_day,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getStmt_day().equals(stmt_day)) cf.add("stmt_day",this.stmt_day,stmt_day); } this.stmt_day=stmt_day;}
public void setStmt_freq(int stmt_freq) { sqlMaker.setField("stmt_freq",""+stmt_freq,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getStmt_freq()!=stmt_freq) cf.add("stmt_freq",this.stmt_freq+"",stmt_freq+""); } this.stmt_freq=stmt_freq;}
public void setCredit_limit(double credit_limit) { sqlMaker.setField("credit_limit",""+credit_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCredit_limit()!=credit_limit) cf.add("credit_limit",this.credit_limit+"",credit_limit+""); } this.credit_limit=credit_limit;}
public void setLst_crlimit(double lst_crlimit) { sqlMaker.setField("lst_crlimit",""+lst_crlimit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLst_crlimit()!=lst_crlimit) cf.add("lst_crlimit",this.lst_crlimit+"",lst_crlimit+""); } this.lst_crlimit=lst_crlimit;}
public void setPerm_credit_limit(double perm_credit_limit) { sqlMaker.setField("perm_credit_limit",""+perm_credit_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getPerm_credit_limit()!=perm_credit_limit) cf.add("perm_credit_limit",this.perm_credit_limit+"",perm_credit_limit+""); } this.perm_credit_limit=perm_credit_limit;}
public void setTemp_credit_limit(double temp_credit_limit) { sqlMaker.setField("temp_credit_limit",""+temp_credit_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getTemp_credit_limit()!=temp_credit_limit) cf.add("temp_credit_limit",this.temp_credit_limit+"",temp_credit_limit+""); } this.temp_credit_limit=temp_credit_limit;}
public void setAvail_crlmt(double avail_crlmt) { sqlMaker.setField("avail_crlmt",""+avail_crlmt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAvail_crlmt()!=avail_crlmt) cf.add("avail_crlmt",this.avail_crlmt+"",avail_crlmt+""); } this.avail_crlmt=avail_crlmt;}
public void setCtd_used_amnt(double ctd_used_amnt) { sqlMaker.setField("ctd_used_amnt",""+ctd_used_amnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCtd_used_amnt()!=ctd_used_amnt) cf.add("ctd_used_amnt",this.ctd_used_amnt+"",ctd_used_amnt+""); } this.ctd_used_amnt=ctd_used_amnt;}
public void setOutst_auth_amt(double outst_auth_amt) { sqlMaker.setField("outst_auth_amt",""+outst_auth_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOutst_auth_amt()!=outst_auth_amt) cf.add("outst_auth_amt",this.outst_auth_amt+"",outst_auth_amt+""); } this.outst_auth_amt=outst_auth_amt;}
public void setOutst_auth_cnt(int outst_auth_cnt) { sqlMaker.setField("outst_auth_cnt",""+outst_auth_cnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOutst_auth_cnt()!=outst_auth_cnt) cf.add("outst_auth_cnt",this.outst_auth_cnt+"",outst_auth_cnt+""); } this.outst_auth_cnt=outst_auth_cnt;}
public void setCash_limit(double cash_limit) { sqlMaker.setField("cash_limit",""+cash_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_limit()!=cash_limit) cf.add("cash_limit",this.cash_limit+"",cash_limit+""); } this.cash_limit=cash_limit;}
public void setCsh_adv_lim(double csh_adv_lim) { sqlMaker.setField("csh_adv_lim",""+csh_adv_lim,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCsh_adv_lim()!=csh_adv_lim) cf.add("csh_adv_lim",this.csh_adv_lim+"",csh_adv_lim+""); } this.csh_adv_lim=csh_adv_lim;}
public void setCsh_adv_avail(double csh_adv_avail) { sqlMaker.setField("csh_adv_avail",""+csh_adv_avail,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCsh_adv_avail()!=csh_adv_avail) cf.add("csh_adv_avail",this.csh_adv_avail+"",csh_adv_avail+""); } this.csh_adv_avail=csh_adv_avail;}
public void setAvl_cash_bal(double avl_cash_bal) { sqlMaker.setField("avl_cash_bal",""+avl_cash_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAvl_cash_bal()!=avl_cash_bal) cf.add("avl_cash_bal",this.avl_cash_bal+"",avl_cash_bal+""); } this.avl_cash_bal=avl_cash_bal;}
public void setLn_totl_ln_amt_hypo_amt(double ln_totl_ln_amt_hypo_amt) { sqlMaker.setField("ln_totl_ln_amt_hypo_amt",""+ln_totl_ln_amt_hypo_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLn_totl_ln_amt_hypo_amt()!=ln_totl_ln_amt_hypo_amt) cf.add("ln_totl_ln_amt_hypo_amt",this.ln_totl_ln_amt_hypo_amt+"",ln_totl_ln_amt_hypo_amt+""); } this.ln_totl_ln_amt_hypo_amt=ln_totl_ln_amt_hypo_amt;}
public void setInstl_limit(double instl_limit) { sqlMaker.setField("instl_limit",""+instl_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getInstl_limit()!=instl_limit) cf.add("instl_limit",this.instl_limit+"",instl_limit+""); } this.instl_limit=instl_limit;}
public void setPart_bal(double part_bal) { sqlMaker.setField("part_bal",""+part_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getPart_bal()!=part_bal) cf.add("part_bal",this.part_bal+"",part_bal+""); } this.part_bal=part_bal;}
public void setOutst_instl_amt(double outst_instl_amt) { sqlMaker.setField("outst_instl_amt",""+outst_instl_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOutst_instl_amt()!=outst_instl_amt) cf.add("outst_instl_amt",this.outst_instl_amt+"",outst_instl_amt+""); } this.outst_instl_amt=outst_instl_amt;}
public void setUse_instl_limit(double use_instl_limit) { sqlMaker.setField("use_instl_limit",""+use_instl_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getUse_instl_limit()!=use_instl_limit) cf.add("use_instl_limit",this.use_instl_limit+"",use_instl_limit+""); } this.use_instl_limit=use_instl_limit;}
public void setLarge_instl_amnt_outst(double large_instl_amnt_outst) { sqlMaker.setField("large_instl_amnt_outst",""+large_instl_amnt_outst,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLarge_instl_amnt_outst()!=large_instl_amnt_outst) cf.add("large_instl_amnt_outst",this.large_instl_amnt_outst+"",large_instl_amnt_outst+""); } this.large_instl_amnt_outst=large_instl_amnt_outst;}
public void setDte_next_stmt(String dte_next_stmt) { sqlMaker.setField("dte_next_stmt",dte_next_stmt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_next_stmt().equals(dte_next_stmt)) cf.add("dte_next_stmt",this.dte_next_stmt,dte_next_stmt); } this.dte_next_stmt=dte_next_stmt;}
public void setBirth_dt(String birth_dt) { sqlMaker.setField("birth_dt",birth_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBirth_dt().equals(birth_dt)) cf.add("birth_dt",this.birth_dt,birth_dt); } this.birth_dt=birth_dt;}
public void setLast_sts_chg_dt(String last_sts_chg_dt) { sqlMaker.setField("last_sts_chg_dt",last_sts_chg_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_sts_chg_dt().equals(last_sts_chg_dt)) cf.add("last_sts_chg_dt",this.last_sts_chg_dt,last_sts_chg_dt); } this.last_sts_chg_dt=last_sts_chg_dt;}
public void setOpen_card_dt(String open_card_dt) { sqlMaker.setField("open_card_dt",open_card_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOpen_card_dt().equals(open_card_dt)) cf.add("open_card_dt",this.open_card_dt,open_card_dt); } this.open_card_dt=open_card_dt;}
public void setClose_dt(String close_dt) { sqlMaker.setField("close_dt",close_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getClose_dt().equals(close_dt)) cf.add("close_dt",this.close_dt,close_dt); } this.close_dt=close_dt;}
public void setDte_join(String dte_join) { sqlMaker.setField("dte_join",dte_join,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_join().equals(dte_join)) cf.add("dte_join",this.dte_join,dte_join); } this.dte_join=dte_join;}
public void setCrd_valid_dt(String crd_valid_dt) { sqlMaker.setField("crd_valid_dt",crd_valid_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrd_valid_dt().equals(crd_valid_dt)) cf.add("crd_valid_dt",this.crd_valid_dt,crd_valid_dt); } this.crd_valid_dt=crd_valid_dt;}
public void setDte_lst_card_expire(String dte_lst_card_expire) { sqlMaker.setField("dte_lst_card_expire",dte_lst_card_expire,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_card_expire().equals(dte_lst_card_expire)) cf.add("dte_lst_card_expire",this.dte_lst_card_expire,dte_lst_card_expire); } this.dte_lst_card_expire=dte_lst_card_expire;}
public void setCrd_prod_dt(String crd_prod_dt) { sqlMaker.setField("crd_prod_dt",crd_prod_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrd_prod_dt().equals(crd_prod_dt)) cf.add("crd_prod_dt",this.crd_prod_dt,crd_prod_dt); } this.crd_prod_dt=crd_prod_dt;}
public void setDte_lst_req(String dte_lst_req) { sqlMaker.setField("dte_lst_req",dte_lst_req,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_req().equals(dte_lst_req)) cf.add("dte_lst_req",this.dte_lst_req,dte_lst_req); } this.dte_lst_req=dte_lst_req;}
public void setJoin_dark_name_list(String join_dark_name_list) { sqlMaker.setField("join_dark_name_list",join_dark_name_list,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getJoin_dark_name_list().equals(join_dark_name_list)) cf.add("join_dark_name_list",this.join_dark_name_list,join_dark_name_list); } this.join_dark_name_list=join_dark_name_list;}
public void setBlock_dt(String block_dt) { sqlMaker.setField("block_dt",block_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBlock_dt().equals(block_dt)) cf.add("block_dt",this.block_dt,block_dt); } this.block_dt=block_dt;}
public void setDte_alt_block_code(String dte_alt_block_code) { sqlMaker.setField("dte_alt_block_code",dte_alt_block_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_alt_block_code().equals(dte_alt_block_code)) cf.add("dte_alt_block_code",this.dte_alt_block_code,dte_alt_block_code); } this.dte_alt_block_code=dte_alt_block_code;}
public void setTrn_crd_eff_dt(String trn_crd_eff_dt) { sqlMaker.setField("trn_crd_eff_dt",trn_crd_eff_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTrn_crd_eff_dt().equals(trn_crd_eff_dt)) cf.add("trn_crd_eff_dt",this.trn_crd_eff_dt,trn_crd_eff_dt); } this.trn_crd_eff_dt=trn_crd_eff_dt;}
public void setYear_cost_dt(String year_cost_dt) { sqlMaker.setField("year_cost_dt",year_cost_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getYear_cost_dt().equals(year_cost_dt)) cf.add("year_cost_dt",this.year_cost_dt,year_cost_dt); } this.year_cost_dt=year_cost_dt;}
public void setDte_activation(String dte_activation) { sqlMaker.setField("dte_activation",dte_activation,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_activation().equals(dte_activation)) cf.add("dte_activation",this.dte_activation,dte_activation); } this.dte_activation=dte_activation;}
public void setDte_lst_activation(String dte_lst_activation) { sqlMaker.setField("dte_lst_activation",dte_lst_activation,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_activation().equals(dte_lst_activation)) cf.add("dte_lst_activation",this.dte_lst_activation,dte_lst_activation); } this.dte_lst_activation=dte_lst_activation;}
public void setCrlimit_temp_eff_dte(String crlimit_temp_eff_dte) { sqlMaker.setField("crlimit_temp_eff_dte",crlimit_temp_eff_dte,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrlimit_temp_eff_dte().equals(crlimit_temp_eff_dte)) cf.add("crlimit_temp_eff_dte",this.crlimit_temp_eff_dte,crlimit_temp_eff_dte); } this.crlimit_temp_eff_dte=crlimit_temp_eff_dte;}
public void setCrlimit_temp_exp_dte(String crlimit_temp_exp_dte) { sqlMaker.setField("crlimit_temp_exp_dte",crlimit_temp_exp_dte,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrlimit_temp_exp_dte().equals(crlimit_temp_exp_dte)) cf.add("crlimit_temp_exp_dte",this.crlimit_temp_exp_dte,crlimit_temp_exp_dte); } this.crlimit_temp_exp_dte=crlimit_temp_exp_dte;}
public void setLast_consume_dt(String last_consume_dt) { sqlMaker.setField("last_consume_dt",last_consume_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_consume_dt().equals(last_consume_dt)) cf.add("last_consume_dt",this.last_consume_dt,last_consume_dt); } this.last_consume_dt=last_consume_dt;}
public void setDte_usage_fee(String dte_usage_fee) { sqlMaker.setField("dte_usage_fee",dte_usage_fee,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_usage_fee().equals(dte_usage_fee)) cf.add("dte_usage_fee",this.dte_usage_fee,dte_usage_fee); } this.dte_usage_fee=dte_usage_fee;}
public void setLast_cash_dt(String last_cash_dt) { sqlMaker.setField("last_cash_dt",last_cash_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_cash_dt().equals(last_cash_dt)) cf.add("last_cash_dt",this.last_cash_dt,last_cash_dt); } this.last_cash_dt=last_cash_dt;}
public void setCm_dte_lst_active(String cm_dte_lst_active) { sqlMaker.setField("cm_dte_lst_active",cm_dte_lst_active,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCm_dte_lst_active().equals(cm_dte_lst_active)) cf.add("cm_dte_lst_active",this.cm_dte_lst_active,cm_dte_lst_active); } this.cm_dte_lst_active=cm_dte_lst_active;}
public void setDte_lst_card_issue(String dte_lst_card_issue) { sqlMaker.setField("dte_lst_card_issue",dte_lst_card_issue,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_card_issue().equals(dte_lst_card_issue)) cf.add("dte_lst_card_issue",this.dte_lst_card_issue,dte_lst_card_issue); } this.dte_lst_card_issue=dte_lst_card_issue;}
public void setDte_rtl_cnt_rst(String dte_rtl_cnt_rst) { sqlMaker.setField("dte_rtl_cnt_rst",dte_rtl_cnt_rst,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_rtl_cnt_rst().equals(dte_rtl_cnt_rst)) cf.add("dte_rtl_cnt_rst",this.dte_rtl_cnt_rst,dte_rtl_cnt_rst); } this.dte_rtl_cnt_rst=dte_rtl_cnt_rst;}
public void setDte_lst_rtl_cnt_rst(String dte_lst_rtl_cnt_rst) { sqlMaker.setField("dte_lst_rtl_cnt_rst",dte_lst_rtl_cnt_rst,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_rtl_cnt_rst().equals(dte_lst_rtl_cnt_rst)) cf.add("dte_lst_rtl_cnt_rst",this.dte_lst_rtl_cnt_rst,dte_lst_rtl_cnt_rst); } this.dte_lst_rtl_cnt_rst=dte_lst_rtl_cnt_rst;}
public void setDte_crlimit(String dte_crlimit) { sqlMaker.setField("dte_crlimit",dte_crlimit,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_crlimit().equals(dte_crlimit)) cf.add("dte_crlimit",this.dte_crlimit,dte_crlimit); } this.dte_crlimit=dte_crlimit;}
public void setDte_lst_crlimit(String dte_lst_crlimit) { sqlMaker.setField("dte_lst_crlimit",dte_lst_crlimit,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_crlimit().equals(dte_lst_crlimit)) cf.add("dte_lst_crlimit",this.dte_lst_crlimit,dte_lst_crlimit); } this.dte_lst_crlimit=dte_lst_crlimit;}
public void setCm_dte_lst_maint(String cm_dte_lst_maint) { sqlMaker.setField("cm_dte_lst_maint",cm_dte_lst_maint,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCm_dte_lst_maint().equals(cm_dte_lst_maint)) cf.add("cm_dte_lst_maint",this.cm_dte_lst_maint,cm_dte_lst_maint); } this.cm_dte_lst_maint=cm_dte_lst_maint;}
public void setDte_lst_stmt(String dte_lst_stmt) { sqlMaker.setField("dte_lst_stmt",dte_lst_stmt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_stmt().equals(dte_lst_stmt)) cf.add("dte_lst_stmt",this.dte_lst_stmt,dte_lst_stmt); } this.dte_lst_stmt=dte_lst_stmt;}
public void setEmbosser_name1(String embosser_name1) { sqlMaker.setField("embosser_name1",embosser_name1,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEmbosser_name1().equals(embosser_name1)) cf.add("embosser_name1",this.embosser_name1,embosser_name1); } this.embosser_name1=embosser_name1;}
public void setEmbosser_name2(String embosser_name2) { sqlMaker.setField("embosser_name2",embosser_name2,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEmbosser_name2().equals(embosser_name2)) cf.add("embosser_name2",this.embosser_name2,embosser_name2); } this.embosser_name2=embosser_name2;}
public void setEmbosser_name3(String embosser_name3) { sqlMaker.setField("embosser_name3",embosser_name3,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEmbosser_name3().equals(embosser_name3)) cf.add("embosser_name3",this.embosser_name3,embosser_name3); } this.embosser_name3=embosser_name3;}
public void setEmbosser_name4(String embosser_name4) { sqlMaker.setField("embosser_name4",embosser_name4,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEmbosser_name4().equals(embosser_name4)) cf.add("embosser_name4",this.embosser_name4,embosser_name4); } this.embosser_name4=embosser_name4;}
public void setCard_action(String card_action) { sqlMaker.setField("card_action",card_action,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCard_action().equals(card_action)) cf.add("card_action",this.card_action,card_action); } this.card_action=card_action;}
public void setNmbr_requested(int nmbr_requested) { sqlMaker.setField("nmbr_requested",""+nmbr_requested,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getNmbr_requested()!=nmbr_requested) cf.add("nmbr_requested",this.nmbr_requested+"",nmbr_requested+""); } this.nmbr_requested=nmbr_requested;}
public void setKind_typ(String kind_typ) { sqlMaker.setField("kind_typ",kind_typ,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getKind_typ().equals(kind_typ)) cf.add("kind_typ",this.kind_typ,kind_typ); } this.kind_typ=kind_typ;}
public void setIssue_cnt(int issue_cnt) { sqlMaker.setField("issue_cnt",""+issue_cnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getIssue_cnt()!=issue_cnt) cf.add("issue_cnt",this.issue_cnt+"",issue_cnt+""); } this.issue_cnt=issue_cnt;}
public void setNmbr_cards_ret(int nmbr_cards_ret) { sqlMaker.setField("nmbr_cards_ret",""+nmbr_cards_ret,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getNmbr_cards_ret()!=nmbr_cards_ret) cf.add("nmbr_cards_ret",this.nmbr_cards_ret+"",nmbr_cards_ret+""); } this.nmbr_cards_ret=nmbr_cards_ret;}
public void setReq_card_type(String req_card_type) { sqlMaker.setField("req_card_type",req_card_type,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getReq_card_type().equals(req_card_type)) cf.add("req_card_type",this.req_card_type,req_card_type); } this.req_card_type=req_card_type;}
public void setCard_prev_action(String card_prev_action) { sqlMaker.setField("card_prev_action",card_prev_action,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCard_prev_action().equals(card_prev_action)) cf.add("card_prev_action",this.card_prev_action,card_prev_action); } this.card_prev_action=card_prev_action;}
public void setPre_reissue_action(String pre_reissue_action) { sqlMaker.setField("pre_reissue_action",pre_reissue_action,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getPre_reissue_action().equals(pre_reissue_action)) cf.add("pre_reissue_action",this.pre_reissue_action,pre_reissue_action); } this.pre_reissue_action=pre_reissue_action;}
public void setReissue_action(String reissue_action) { sqlMaker.setField("reissue_action",reissue_action,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getReissue_action().equals(reissue_action)) cf.add("reissue_action",this.reissue_action,reissue_action); } this.reissue_action=reissue_action;}
public void setXfr_org_nmbr(String xfr_org_nmbr) { sqlMaker.setField("xfr_org_nmbr",xfr_org_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getXfr_org_nmbr().equals(xfr_org_nmbr)) cf.add("xfr_org_nmbr",this.xfr_org_nmbr,xfr_org_nmbr); } this.xfr_org_nmbr=xfr_org_nmbr;}
public void setXfr_type_nmbr(String xfr_type_nmbr) { sqlMaker.setField("xfr_type_nmbr",xfr_type_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getXfr_type_nmbr().equals(xfr_type_nmbr)) cf.add("xfr_type_nmbr",this.xfr_type_nmbr,xfr_type_nmbr); } this.xfr_type_nmbr=xfr_type_nmbr;}
public void setXfr_acct_nmbr(String xfr_acct_nmbr) { sqlMaker.setField("xfr_acct_nmbr",xfr_acct_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getXfr_acct_nmbr().equals(xfr_acct_nmbr)) cf.add("xfr_acct_nmbr",this.xfr_acct_nmbr,xfr_acct_nmbr); } this.xfr_acct_nmbr=xfr_acct_nmbr;}
public void setCrd_corr_acct(String crd_corr_acct) { sqlMaker.setField("crd_corr_acct",crd_corr_acct,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrd_corr_acct().equals(crd_corr_acct)) cf.add("crd_corr_acct",this.crd_corr_acct,crd_corr_acct); } this.crd_corr_acct=crd_corr_acct;}
public void setAccount1_org(String account1_org) { sqlMaker.setField("account1_org",account1_org,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount1_org().equals(account1_org)) cf.add("account1_org",this.account1_org,account1_org); } this.account1_org=account1_org;}
public void setAccount1_type(String account1_type) { sqlMaker.setField("account1_type",account1_type,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount1_type().equals(account1_type)) cf.add("account1_type",this.account1_type,account1_type); } this.account1_type=account1_type;}
public void setAccount1_curr_code(String account1_curr_code) { sqlMaker.setField("account1_curr_code",account1_curr_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount1_curr_code().equals(account1_curr_code)) cf.add("account1_curr_code",this.account1_curr_code,account1_curr_code); } this.account1_curr_code=account1_curr_code;}
public void setAccount2_nmbr(String account2_nmbr) { sqlMaker.setField("account2_nmbr",account2_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount2_nmbr().equals(account2_nmbr)) cf.add("account2_nmbr",this.account2_nmbr,account2_nmbr); } this.account2_nmbr=account2_nmbr;}
public void setAccount2_org(String account2_org) { sqlMaker.setField("account2_org",account2_org,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount2_org().equals(account2_org)) cf.add("account2_org",this.account2_org,account2_org); } this.account2_org=account2_org;}
public void setAccount2_type(String account2_type) { sqlMaker.setField("account2_type",account2_type,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount2_type().equals(account2_type)) cf.add("account2_type",this.account2_type,account2_type); } this.account2_type=account2_type;}
public void setAccount2_curr_code(String account2_curr_code) { sqlMaker.setField("account2_curr_code",account2_curr_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount2_curr_code().equals(account2_curr_code)) cf.add("account2_curr_code",this.account2_curr_code,account2_curr_code); } this.account2_curr_code=account2_curr_code;}
public void setAccount3_org(String account3_org) { sqlMaker.setField("account3_org",account3_org,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount3_org().equals(account3_org)) cf.add("account3_org",this.account3_org,account3_org); } this.account3_org=account3_org;}
public void setAccount3_type(String account3_type) { sqlMaker.setField("account3_type",account3_type,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount3_type().equals(account3_type)) cf.add("account3_type",this.account3_type,account3_type); } this.account3_type=account3_type;}
public void setAccount3_nmbr(String account3_nmbr) { sqlMaker.setField("account3_nmbr",account3_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount3_nmbr().equals(account3_nmbr)) cf.add("account3_nmbr",this.account3_nmbr,account3_nmbr); } this.account3_nmbr=account3_nmbr;}
public void setAccount3_curr_code(String account3_curr_code) { sqlMaker.setField("account3_curr_code",account3_curr_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAccount3_curr_code().equals(account3_curr_code)) cf.add("account3_curr_code",this.account3_curr_code,account3_curr_code); } this.account3_curr_code=account3_curr_code;}
public void setFirst_usg_flag(String first_usg_flag) { sqlMaker.setField("first_usg_flag",first_usg_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getFirst_usg_flag().equals(first_usg_flag)) cf.add("first_usg_flag",this.first_usg_flag,first_usg_flag); } this.first_usg_flag=first_usg_flag;}
public void setCash_adv_flg(String cash_adv_flg) { sqlMaker.setField("cash_adv_flg",cash_adv_flg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCash_adv_flg().equals(cash_adv_flg)) cf.add("cash_adv_flg",this.cash_adv_flg,cash_adv_flg); } this.cash_adv_flg=cash_adv_flg;}
public void setOf_annual_fee_flg(String of_annual_fee_flg) { sqlMaker.setField("of_annual_fee_flg",of_annual_fee_flg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOf_annual_fee_flg().equals(of_annual_fee_flg)) cf.add("of_annual_fee_flg",this.of_annual_fee_flg,of_annual_fee_flg); } this.of_annual_fee_flg=of_annual_fee_flg;}
public void setCard_mailer_ind(String card_mailer_ind) { sqlMaker.setField("card_mailer_ind",card_mailer_ind,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCard_mailer_ind().equals(card_mailer_ind)) cf.add("card_mailer_ind",this.card_mailer_ind,card_mailer_ind); } this.card_mailer_ind=card_mailer_ind;}
public void setActivation_cd(String activation_cd) { sqlMaker.setField("activation_cd",activation_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getActivation_cd().equals(activation_cd)) cf.add("activation_cd",this.activation_cd,activation_cd); } this.activation_cd=activation_cd;}
public void setLast_activation_cd(String last_activation_cd) { sqlMaker.setField("last_activation_cd",last_activation_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_activation_cd().equals(last_activation_cd)) cf.add("last_activation_cd",this.last_activation_cd,last_activation_cd); } this.last_activation_cd=last_activation_cd;}
public void setVip_card(String vip_card) { sqlMaker.setField("vip_card",vip_card,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getVip_card().equals(vip_card)) cf.add("vip_card",this.vip_card,vip_card); } this.vip_card=vip_card;}
public void setAmrl_alimony_agreement(String amrl_alimony_agreement) { sqlMaker.setField("amrl_alimony_agreement",amrl_alimony_agreement,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAmrl_alimony_agreement().equals(amrl_alimony_agreement)) cf.add("amrl_alimony_agreement",this.amrl_alimony_agreement,amrl_alimony_agreement); } this.amrl_alimony_agreement=amrl_alimony_agreement;}
public void setSms_flag(String sms_flag) { sqlMaker.setField("sms_flag",sms_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSms_flag().equals(sms_flag)) cf.add("sms_flag",this.sms_flag,sms_flag); } this.sms_flag=sms_flag;}
public void setVerify_password_amnt(double verify_password_amnt) { sqlMaker.setField("verify_password_amnt",""+verify_password_amnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getVerify_password_amnt()!=verify_password_amnt) cf.add("verify_password_amnt",this.verify_password_amnt+"",verify_password_amnt+""); } this.verify_password_amnt=verify_password_amnt;}
public void setNmbr_rtl_auth_ytd(int nmbr_rtl_auth_ytd) { sqlMaker.setField("nmbr_rtl_auth_ytd",""+nmbr_rtl_auth_ytd,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getNmbr_rtl_auth_ytd()!=nmbr_rtl_auth_ytd) cf.add("nmbr_rtl_auth_ytd",this.nmbr_rtl_auth_ytd+"",nmbr_rtl_auth_ytd+""); } this.nmbr_rtl_auth_ytd=nmbr_rtl_auth_ytd;}
public void setLst_nmbr_rtl_auth_ytd(int lst_nmbr_rtl_auth_ytd) { sqlMaker.setField("lst_nmbr_rtl_auth_ytd",""+lst_nmbr_rtl_auth_ytd,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLst_nmbr_rtl_auth_ytd()!=lst_nmbr_rtl_auth_ytd) cf.add("lst_nmbr_rtl_auth_ytd",this.lst_nmbr_rtl_auth_ytd+"",lst_nmbr_rtl_auth_ytd+""); } this.lst_nmbr_rtl_auth_ytd=lst_nmbr_rtl_auth_ytd;}
public void setAppid(String appid) { sqlMaker.setField("appid",appid,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAppid().equals(appid)) cf.add("appid",this.appid,appid); } this.appid=appid;}
public void setSrc_cd(String src_cd) { sqlMaker.setField("src_cd",src_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSrc_cd().equals(src_cd)) cf.add("src_cd",this.src_cd,src_cd); } this.src_cd=src_cd;}
public void setMarket(String market) { sqlMaker.setField("market",market,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMarket().equals(market)) cf.add("market",this.market,market); } this.market=market;}
public void setMarket_org_cd(String market_org_cd) { sqlMaker.setField("market_org_cd",market_org_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMarket_org_cd().equals(market_org_cd)) cf.add("market_org_cd",this.market_org_cd,market_org_cd); } this.market_org_cd=market_org_cd;}
public void setOds_load_dt(String ods_load_dt) { sqlMaker.setField("ods_load_dt",ods_load_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOds_load_dt().equals(ods_load_dt)) cf.add("ods_load_dt",this.ods_load_dt,ods_load_dt); } this.ods_load_dt=ods_load_dt;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"crd_no") !=null ) {this.setCrd_no(actionRequest.getFieldValue(i,"crd_no"));}
if ( actionRequest.getFieldValue(i,"org_no") !=null ) {this.setOrg_no(actionRequest.getFieldValue(i,"org_no"));}
if ( actionRequest.getFieldValue(i,"curr_cd") !=null ) {this.setCurr_cd(actionRequest.getFieldValue(i,"curr_cd"));}
if ( actionRequest.getFieldValue(i,"crd_typ") !=null ) {this.setCrd_typ(actionRequest.getFieldValue(i,"crd_typ"));}
if ( actionRequest.getFieldValue(i,"ods_src_dt") !=null ) {this.setOds_src_dt(actionRequest.getFieldValue(i,"ods_src_dt"));}
if ( actionRequest.getFieldValue(i,"out_crd_instn_cd") !=null ) {this.setOut_crd_instn_cd(actionRequest.getFieldValue(i,"out_crd_instn_cd"));}
if ( actionRequest.getFieldValue(i,"ochr_customer_org") !=null ) {this.setOchr_customer_org(actionRequest.getFieldValue(i,"ochr_customer_org"));}
if ( actionRequest.getFieldValue(i,"cust_no") !=null ) {this.setCust_no(actionRequest.getFieldValue(i,"cust_no"));}
if ( actionRequest.getFieldValue(i,"suppl_cust_org") !=null ) {this.setSuppl_cust_org(actionRequest.getFieldValue(i,"suppl_cust_org"));}
if ( actionRequest.getFieldValue(i,"suppl_cust_id") !=null ) {this.setSuppl_cust_id(actionRequest.getFieldValue(i,"suppl_cust_id"));}
if ( actionRequest.getFieldValue(i,"short_name") !=null ) {this.setShort_name(actionRequest.getFieldValue(i,"short_name"));}
if ( actionRequest.getFieldValue(i,"officer") !=null ) {this.setOfficer(actionRequest.getFieldValue(i,"officer"));}
if ( actionRequest.getFieldValue(i,"block_code") !=null ) {this.setBlock_code(actionRequest.getFieldValue(i,"block_code"));}
if ( actionRequest.getFieldValue(i,"alt_block_code") !=null ) {this.setAlt_block_code(actionRequest.getFieldValue(i,"alt_block_code"));}
if ( actionRequest.getFieldValue(i,"sr_ai_prim_ind") !=null ) {this.setSr_ai_prim_ind(actionRequest.getFieldValue(i,"sr_ai_prim_ind"));}
if ( actionRequest.getFieldValue(i,"rela_desc") !=null ) {this.setRela_desc(actionRequest.getFieldValue(i,"rela_desc"));}
if ( actionRequest.getFieldValue(i,"crd_sts") !=null ) {this.setCrd_sts(actionRequest.getFieldValue(i,"crd_sts"));}
if ( actionRequest.getFieldValue(i,"erea_cd") !=null ) {this.setErea_cd(actionRequest.getFieldValue(i,"erea_cd"));}
if ( actionRequest.getFieldValue(i,"stmt_day") !=null ) {this.setStmt_day(actionRequest.getFieldValue(i,"stmt_day"));}
if ( actionRequest.getFieldValue(i,"stmt_freq") !=null && actionRequest.getFieldValue(i,"stmt_freq").trim().length() > 0 ) {this.setStmt_freq(Integer.parseInt(actionRequest.getFieldValue(i,"stmt_freq")));}
if ( actionRequest.getFieldValue(i,"credit_limit") !=null && actionRequest.getFieldValue(i,"credit_limit").trim().length() > 0 ) {this.setCredit_limit(Double.parseDouble(actionRequest.getFieldValue(i,"credit_limit")));}
if ( actionRequest.getFieldValue(i,"lst_crlimit") !=null && actionRequest.getFieldValue(i,"lst_crlimit").trim().length() > 0 ) {this.setLst_crlimit(Double.parseDouble(actionRequest.getFieldValue(i,"lst_crlimit")));}
if ( actionRequest.getFieldValue(i,"perm_credit_limit") !=null && actionRequest.getFieldValue(i,"perm_credit_limit").trim().length() > 0 ) {this.setPerm_credit_limit(Double.parseDouble(actionRequest.getFieldValue(i,"perm_credit_limit")));}
if ( actionRequest.getFieldValue(i,"temp_credit_limit") !=null && actionRequest.getFieldValue(i,"temp_credit_limit").trim().length() > 0 ) {this.setTemp_credit_limit(Double.parseDouble(actionRequest.getFieldValue(i,"temp_credit_limit")));}
if ( actionRequest.getFieldValue(i,"avail_crlmt") !=null && actionRequest.getFieldValue(i,"avail_crlmt").trim().length() > 0 ) {this.setAvail_crlmt(Double.parseDouble(actionRequest.getFieldValue(i,"avail_crlmt")));}
if ( actionRequest.getFieldValue(i,"ctd_used_amnt") !=null && actionRequest.getFieldValue(i,"ctd_used_amnt").trim().length() > 0 ) {this.setCtd_used_amnt(Double.parseDouble(actionRequest.getFieldValue(i,"ctd_used_amnt")));}
if ( actionRequest.getFieldValue(i,"outst_auth_amt") !=null && actionRequest.getFieldValue(i,"outst_auth_amt").trim().length() > 0 ) {this.setOutst_auth_amt(Double.parseDouble(actionRequest.getFieldValue(i,"outst_auth_amt")));}
if ( actionRequest.getFieldValue(i,"outst_auth_cnt") !=null && actionRequest.getFieldValue(i,"outst_auth_cnt").trim().length() > 0 ) {this.setOutst_auth_cnt(Integer.parseInt(actionRequest.getFieldValue(i,"outst_auth_cnt")));}
if ( actionRequest.getFieldValue(i,"cash_limit") !=null && actionRequest.getFieldValue(i,"cash_limit").trim().length() > 0 ) {this.setCash_limit(Double.parseDouble(actionRequest.getFieldValue(i,"cash_limit")));}
if ( actionRequest.getFieldValue(i,"csh_adv_lim") !=null && actionRequest.getFieldValue(i,"csh_adv_lim").trim().length() > 0 ) {this.setCsh_adv_lim(Double.parseDouble(actionRequest.getFieldValue(i,"csh_adv_lim")));}
if ( actionRequest.getFieldValue(i,"csh_adv_avail") !=null && actionRequest.getFieldValue(i,"csh_adv_avail").trim().length() > 0 ) {this.setCsh_adv_avail(Double.parseDouble(actionRequest.getFieldValue(i,"csh_adv_avail")));}
if ( actionRequest.getFieldValue(i,"avl_cash_bal") !=null && actionRequest.getFieldValue(i,"avl_cash_bal").trim().length() > 0 ) {this.setAvl_cash_bal(Double.parseDouble(actionRequest.getFieldValue(i,"avl_cash_bal")));}
if ( actionRequest.getFieldValue(i,"ln_totl_ln_amt_hypo_amt") !=null && actionRequest.getFieldValue(i,"ln_totl_ln_amt_hypo_amt").trim().length() > 0 ) {this.setLn_totl_ln_amt_hypo_amt(Double.parseDouble(actionRequest.getFieldValue(i,"ln_totl_ln_amt_hypo_amt")));}
if ( actionRequest.getFieldValue(i,"instl_limit") !=null && actionRequest.getFieldValue(i,"instl_limit").trim().length() > 0 ) {this.setInstl_limit(Double.parseDouble(actionRequest.getFieldValue(i,"instl_limit")));}
if ( actionRequest.getFieldValue(i,"part_bal") !=null && actionRequest.getFieldValue(i,"part_bal").trim().length() > 0 ) {this.setPart_bal(Double.parseDouble(actionRequest.getFieldValue(i,"part_bal")));}
if ( actionRequest.getFieldValue(i,"outst_instl_amt") !=null && actionRequest.getFieldValue(i,"outst_instl_amt").trim().length() > 0 ) {this.setOutst_instl_amt(Double.parseDouble(actionRequest.getFieldValue(i,"outst_instl_amt")));}
if ( actionRequest.getFieldValue(i,"use_instl_limit") !=null && actionRequest.getFieldValue(i,"use_instl_limit").trim().length() > 0 ) {this.setUse_instl_limit(Double.parseDouble(actionRequest.getFieldValue(i,"use_instl_limit")));}
if ( actionRequest.getFieldValue(i,"large_instl_amnt_outst") !=null && actionRequest.getFieldValue(i,"large_instl_amnt_outst").trim().length() > 0 ) {this.setLarge_instl_amnt_outst(Double.parseDouble(actionRequest.getFieldValue(i,"large_instl_amnt_outst")));}
if ( actionRequest.getFieldValue(i,"dte_next_stmt") !=null ) {this.setDte_next_stmt(actionRequest.getFieldValue(i,"dte_next_stmt"));}
if ( actionRequest.getFieldValue(i,"birth_dt") !=null ) {this.setBirth_dt(actionRequest.getFieldValue(i,"birth_dt"));}
if ( actionRequest.getFieldValue(i,"last_sts_chg_dt") !=null ) {this.setLast_sts_chg_dt(actionRequest.getFieldValue(i,"last_sts_chg_dt"));}
if ( actionRequest.getFieldValue(i,"open_card_dt") !=null ) {this.setOpen_card_dt(actionRequest.getFieldValue(i,"open_card_dt"));}
if ( actionRequest.getFieldValue(i,"close_dt") !=null ) {this.setClose_dt(actionRequest.getFieldValue(i,"close_dt"));}
if ( actionRequest.getFieldValue(i,"dte_join") !=null ) {this.setDte_join(actionRequest.getFieldValue(i,"dte_join"));}
if ( actionRequest.getFieldValue(i,"crd_valid_dt") !=null ) {this.setCrd_valid_dt(actionRequest.getFieldValue(i,"crd_valid_dt"));}
if ( actionRequest.getFieldValue(i,"dte_lst_card_expire") !=null ) {this.setDte_lst_card_expire(actionRequest.getFieldValue(i,"dte_lst_card_expire"));}
if ( actionRequest.getFieldValue(i,"crd_prod_dt") !=null ) {this.setCrd_prod_dt(actionRequest.getFieldValue(i,"crd_prod_dt"));}
if ( actionRequest.getFieldValue(i,"dte_lst_req") !=null ) {this.setDte_lst_req(actionRequest.getFieldValue(i,"dte_lst_req"));}
if ( actionRequest.getFieldValue(i,"join_dark_name_list") !=null ) {this.setJoin_dark_name_list(actionRequest.getFieldValue(i,"join_dark_name_list"));}
if ( actionRequest.getFieldValue(i,"block_dt") !=null ) {this.setBlock_dt(actionRequest.getFieldValue(i,"block_dt"));}
if ( actionRequest.getFieldValue(i,"dte_alt_block_code") !=null ) {this.setDte_alt_block_code(actionRequest.getFieldValue(i,"dte_alt_block_code"));}
if ( actionRequest.getFieldValue(i,"trn_crd_eff_dt") !=null ) {this.setTrn_crd_eff_dt(actionRequest.getFieldValue(i,"trn_crd_eff_dt"));}
if ( actionRequest.getFieldValue(i,"year_cost_dt") !=null ) {this.setYear_cost_dt(actionRequest.getFieldValue(i,"year_cost_dt"));}
if ( actionRequest.getFieldValue(i,"dte_activation") !=null ) {this.setDte_activation(actionRequest.getFieldValue(i,"dte_activation"));}
if ( actionRequest.getFieldValue(i,"dte_lst_activation") !=null ) {this.setDte_lst_activation(actionRequest.getFieldValue(i,"dte_lst_activation"));}
if ( actionRequest.getFieldValue(i,"crlimit_temp_eff_dte") !=null ) {this.setCrlimit_temp_eff_dte(actionRequest.getFieldValue(i,"crlimit_temp_eff_dte"));}
if ( actionRequest.getFieldValue(i,"crlimit_temp_exp_dte") !=null ) {this.setCrlimit_temp_exp_dte(actionRequest.getFieldValue(i,"crlimit_temp_exp_dte"));}
if ( actionRequest.getFieldValue(i,"last_consume_dt") !=null ) {this.setLast_consume_dt(actionRequest.getFieldValue(i,"last_consume_dt"));}
if ( actionRequest.getFieldValue(i,"dte_usage_fee") !=null ) {this.setDte_usage_fee(actionRequest.getFieldValue(i,"dte_usage_fee"));}
if ( actionRequest.getFieldValue(i,"last_cash_dt") !=null ) {this.setLast_cash_dt(actionRequest.getFieldValue(i,"last_cash_dt"));}
if ( actionRequest.getFieldValue(i,"cm_dte_lst_active") !=null ) {this.setCm_dte_lst_active(actionRequest.getFieldValue(i,"cm_dte_lst_active"));}
if ( actionRequest.getFieldValue(i,"dte_lst_card_issue") !=null ) {this.setDte_lst_card_issue(actionRequest.getFieldValue(i,"dte_lst_card_issue"));}
if ( actionRequest.getFieldValue(i,"dte_rtl_cnt_rst") !=null ) {this.setDte_rtl_cnt_rst(actionRequest.getFieldValue(i,"dte_rtl_cnt_rst"));}
if ( actionRequest.getFieldValue(i,"dte_lst_rtl_cnt_rst") !=null ) {this.setDte_lst_rtl_cnt_rst(actionRequest.getFieldValue(i,"dte_lst_rtl_cnt_rst"));}
if ( actionRequest.getFieldValue(i,"dte_crlimit") !=null ) {this.setDte_crlimit(actionRequest.getFieldValue(i,"dte_crlimit"));}
if ( actionRequest.getFieldValue(i,"dte_lst_crlimit") !=null ) {this.setDte_lst_crlimit(actionRequest.getFieldValue(i,"dte_lst_crlimit"));}
if ( actionRequest.getFieldValue(i,"cm_dte_lst_maint") !=null ) {this.setCm_dte_lst_maint(actionRequest.getFieldValue(i,"cm_dte_lst_maint"));}
if ( actionRequest.getFieldValue(i,"dte_lst_stmt") !=null ) {this.setDte_lst_stmt(actionRequest.getFieldValue(i,"dte_lst_stmt"));}
if ( actionRequest.getFieldValue(i,"embosser_name1") !=null ) {this.setEmbosser_name1(actionRequest.getFieldValue(i,"embosser_name1"));}
if ( actionRequest.getFieldValue(i,"embosser_name2") !=null ) {this.setEmbosser_name2(actionRequest.getFieldValue(i,"embosser_name2"));}
if ( actionRequest.getFieldValue(i,"embosser_name3") !=null ) {this.setEmbosser_name3(actionRequest.getFieldValue(i,"embosser_name3"));}
if ( actionRequest.getFieldValue(i,"embosser_name4") !=null ) {this.setEmbosser_name4(actionRequest.getFieldValue(i,"embosser_name4"));}
if ( actionRequest.getFieldValue(i,"card_action") !=null ) {this.setCard_action(actionRequest.getFieldValue(i,"card_action"));}
if ( actionRequest.getFieldValue(i,"nmbr_requested") !=null && actionRequest.getFieldValue(i,"nmbr_requested").trim().length() > 0 ) {this.setNmbr_requested(Integer.parseInt(actionRequest.getFieldValue(i,"nmbr_requested")));}
if ( actionRequest.getFieldValue(i,"kind_typ") !=null ) {this.setKind_typ(actionRequest.getFieldValue(i,"kind_typ"));}
if ( actionRequest.getFieldValue(i,"issue_cnt") !=null && actionRequest.getFieldValue(i,"issue_cnt").trim().length() > 0 ) {this.setIssue_cnt(Integer.parseInt(actionRequest.getFieldValue(i,"issue_cnt")));}
if ( actionRequest.getFieldValue(i,"nmbr_cards_ret") !=null && actionRequest.getFieldValue(i,"nmbr_cards_ret").trim().length() > 0 ) {this.setNmbr_cards_ret(Integer.parseInt(actionRequest.getFieldValue(i,"nmbr_cards_ret")));}
if ( actionRequest.getFieldValue(i,"req_card_type") !=null ) {this.setReq_card_type(actionRequest.getFieldValue(i,"req_card_type"));}
if ( actionRequest.getFieldValue(i,"card_prev_action") !=null ) {this.setCard_prev_action(actionRequest.getFieldValue(i,"card_prev_action"));}
if ( actionRequest.getFieldValue(i,"pre_reissue_action") !=null ) {this.setPre_reissue_action(actionRequest.getFieldValue(i,"pre_reissue_action"));}
if ( actionRequest.getFieldValue(i,"reissue_action") !=null ) {this.setReissue_action(actionRequest.getFieldValue(i,"reissue_action"));}
if ( actionRequest.getFieldValue(i,"xfr_org_nmbr") !=null ) {this.setXfr_org_nmbr(actionRequest.getFieldValue(i,"xfr_org_nmbr"));}
if ( actionRequest.getFieldValue(i,"xfr_type_nmbr") !=null ) {this.setXfr_type_nmbr(actionRequest.getFieldValue(i,"xfr_type_nmbr"));}
if ( actionRequest.getFieldValue(i,"xfr_acct_nmbr") !=null ) {this.setXfr_acct_nmbr(actionRequest.getFieldValue(i,"xfr_acct_nmbr"));}
if ( actionRequest.getFieldValue(i,"crd_corr_acct") !=null ) {this.setCrd_corr_acct(actionRequest.getFieldValue(i,"crd_corr_acct"));}
if ( actionRequest.getFieldValue(i,"account1_org") !=null ) {this.setAccount1_org(actionRequest.getFieldValue(i,"account1_org"));}
if ( actionRequest.getFieldValue(i,"account1_type") !=null ) {this.setAccount1_type(actionRequest.getFieldValue(i,"account1_type"));}
if ( actionRequest.getFieldValue(i,"account1_curr_code") !=null ) {this.setAccount1_curr_code(actionRequest.getFieldValue(i,"account1_curr_code"));}
if ( actionRequest.getFieldValue(i,"account2_nmbr") !=null ) {this.setAccount2_nmbr(actionRequest.getFieldValue(i,"account2_nmbr"));}
if ( actionRequest.getFieldValue(i,"account2_org") !=null ) {this.setAccount2_org(actionRequest.getFieldValue(i,"account2_org"));}
if ( actionRequest.getFieldValue(i,"account2_type") !=null ) {this.setAccount2_type(actionRequest.getFieldValue(i,"account2_type"));}
if ( actionRequest.getFieldValue(i,"account2_curr_code") !=null ) {this.setAccount2_curr_code(actionRequest.getFieldValue(i,"account2_curr_code"));}
if ( actionRequest.getFieldValue(i,"account3_org") !=null ) {this.setAccount3_org(actionRequest.getFieldValue(i,"account3_org"));}
if ( actionRequest.getFieldValue(i,"account3_type") !=null ) {this.setAccount3_type(actionRequest.getFieldValue(i,"account3_type"));}
if ( actionRequest.getFieldValue(i,"account3_nmbr") !=null ) {this.setAccount3_nmbr(actionRequest.getFieldValue(i,"account3_nmbr"));}
if ( actionRequest.getFieldValue(i,"account3_curr_code") !=null ) {this.setAccount3_curr_code(actionRequest.getFieldValue(i,"account3_curr_code"));}
if ( actionRequest.getFieldValue(i,"first_usg_flag") !=null ) {this.setFirst_usg_flag(actionRequest.getFieldValue(i,"first_usg_flag"));}
if ( actionRequest.getFieldValue(i,"cash_adv_flg") !=null ) {this.setCash_adv_flg(actionRequest.getFieldValue(i,"cash_adv_flg"));}
if ( actionRequest.getFieldValue(i,"of_annual_fee_flg") !=null ) {this.setOf_annual_fee_flg(actionRequest.getFieldValue(i,"of_annual_fee_flg"));}
if ( actionRequest.getFieldValue(i,"card_mailer_ind") !=null ) {this.setCard_mailer_ind(actionRequest.getFieldValue(i,"card_mailer_ind"));}
if ( actionRequest.getFieldValue(i,"activation_cd") !=null ) {this.setActivation_cd(actionRequest.getFieldValue(i,"activation_cd"));}
if ( actionRequest.getFieldValue(i,"last_activation_cd") !=null ) {this.setLast_activation_cd(actionRequest.getFieldValue(i,"last_activation_cd"));}
if ( actionRequest.getFieldValue(i,"vip_card") !=null ) {this.setVip_card(actionRequest.getFieldValue(i,"vip_card"));}
if ( actionRequest.getFieldValue(i,"amrl_alimony_agreement") !=null ) {this.setAmrl_alimony_agreement(actionRequest.getFieldValue(i,"amrl_alimony_agreement"));}
if ( actionRequest.getFieldValue(i,"sms_flag") !=null ) {this.setSms_flag(actionRequest.getFieldValue(i,"sms_flag"));}
if ( actionRequest.getFieldValue(i,"verify_password_amnt") !=null && actionRequest.getFieldValue(i,"verify_password_amnt").trim().length() > 0 ) {this.setVerify_password_amnt(Double.parseDouble(actionRequest.getFieldValue(i,"verify_password_amnt")));}
if ( actionRequest.getFieldValue(i,"nmbr_rtl_auth_ytd") !=null && actionRequest.getFieldValue(i,"nmbr_rtl_auth_ytd").trim().length() > 0 ) {this.setNmbr_rtl_auth_ytd(Integer.parseInt(actionRequest.getFieldValue(i,"nmbr_rtl_auth_ytd")));}
if ( actionRequest.getFieldValue(i,"lst_nmbr_rtl_auth_ytd") !=null && actionRequest.getFieldValue(i,"lst_nmbr_rtl_auth_ytd").trim().length() > 0 ) {this.setLst_nmbr_rtl_auth_ytd(Integer.parseInt(actionRequest.getFieldValue(i,"lst_nmbr_rtl_auth_ytd")));}
if ( actionRequest.getFieldValue(i,"appid") !=null ) {this.setAppid(actionRequest.getFieldValue(i,"appid"));}
if ( actionRequest.getFieldValue(i,"src_cd") !=null ) {this.setSrc_cd(actionRequest.getFieldValue(i,"src_cd"));}
if ( actionRequest.getFieldValue(i,"market") !=null ) {this.setMarket(actionRequest.getFieldValue(i,"market"));}
if ( actionRequest.getFieldValue(i,"market_org_cd") !=null ) {this.setMarket_org_cd(actionRequest.getFieldValue(i,"market_org_cd"));}
if ( actionRequest.getFieldValue(i,"ods_load_dt") !=null ) {this.setOds_load_dt(actionRequest.getFieldValue(i,"ods_load_dt"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { ODSBCRDCRT obj = (ODSBCRDCRT)super.clone();obj.setCrd_no(obj.crd_no);
obj.setOrg_no(obj.org_no);
obj.setCurr_cd(obj.curr_cd);
obj.setCrd_typ(obj.crd_typ);
obj.setOds_src_dt(obj.ods_src_dt);
obj.setOut_crd_instn_cd(obj.out_crd_instn_cd);
obj.setOchr_customer_org(obj.ochr_customer_org);
obj.setCust_no(obj.cust_no);
obj.setSuppl_cust_org(obj.suppl_cust_org);
obj.setSuppl_cust_id(obj.suppl_cust_id);
obj.setShort_name(obj.short_name);
obj.setOfficer(obj.officer);
obj.setBlock_code(obj.block_code);
obj.setAlt_block_code(obj.alt_block_code);
obj.setSr_ai_prim_ind(obj.sr_ai_prim_ind);
obj.setRela_desc(obj.rela_desc);
obj.setCrd_sts(obj.crd_sts);
obj.setErea_cd(obj.erea_cd);
obj.setStmt_day(obj.stmt_day);
obj.setStmt_freq(obj.stmt_freq);
obj.setCredit_limit(obj.credit_limit);
obj.setLst_crlimit(obj.lst_crlimit);
obj.setPerm_credit_limit(obj.perm_credit_limit);
obj.setTemp_credit_limit(obj.temp_credit_limit);
obj.setAvail_crlmt(obj.avail_crlmt);
obj.setCtd_used_amnt(obj.ctd_used_amnt);
obj.setOutst_auth_amt(obj.outst_auth_amt);
obj.setOutst_auth_cnt(obj.outst_auth_cnt);
obj.setCash_limit(obj.cash_limit);
obj.setCsh_adv_lim(obj.csh_adv_lim);
obj.setCsh_adv_avail(obj.csh_adv_avail);
obj.setAvl_cash_bal(obj.avl_cash_bal);
obj.setLn_totl_ln_amt_hypo_amt(obj.ln_totl_ln_amt_hypo_amt);
obj.setInstl_limit(obj.instl_limit);
obj.setPart_bal(obj.part_bal);
obj.setOutst_instl_amt(obj.outst_instl_amt);
obj.setUse_instl_limit(obj.use_instl_limit);
obj.setLarge_instl_amnt_outst(obj.large_instl_amnt_outst);
obj.setDte_next_stmt(obj.dte_next_stmt);
obj.setBirth_dt(obj.birth_dt);
obj.setLast_sts_chg_dt(obj.last_sts_chg_dt);
obj.setOpen_card_dt(obj.open_card_dt);
obj.setClose_dt(obj.close_dt);
obj.setDte_join(obj.dte_join);
obj.setCrd_valid_dt(obj.crd_valid_dt);
obj.setDte_lst_card_expire(obj.dte_lst_card_expire);
obj.setCrd_prod_dt(obj.crd_prod_dt);
obj.setDte_lst_req(obj.dte_lst_req);
obj.setJoin_dark_name_list(obj.join_dark_name_list);
obj.setBlock_dt(obj.block_dt);
obj.setDte_alt_block_code(obj.dte_alt_block_code);
obj.setTrn_crd_eff_dt(obj.trn_crd_eff_dt);
obj.setYear_cost_dt(obj.year_cost_dt);
obj.setDte_activation(obj.dte_activation);
obj.setDte_lst_activation(obj.dte_lst_activation);
obj.setCrlimit_temp_eff_dte(obj.crlimit_temp_eff_dte);
obj.setCrlimit_temp_exp_dte(obj.crlimit_temp_exp_dte);
obj.setLast_consume_dt(obj.last_consume_dt);
obj.setDte_usage_fee(obj.dte_usage_fee);
obj.setLast_cash_dt(obj.last_cash_dt);
obj.setCm_dte_lst_active(obj.cm_dte_lst_active);
obj.setDte_lst_card_issue(obj.dte_lst_card_issue);
obj.setDte_rtl_cnt_rst(obj.dte_rtl_cnt_rst);
obj.setDte_lst_rtl_cnt_rst(obj.dte_lst_rtl_cnt_rst);
obj.setDte_crlimit(obj.dte_crlimit);
obj.setDte_lst_crlimit(obj.dte_lst_crlimit);
obj.setCm_dte_lst_maint(obj.cm_dte_lst_maint);
obj.setDte_lst_stmt(obj.dte_lst_stmt);
obj.setEmbosser_name1(obj.embosser_name1);
obj.setEmbosser_name2(obj.embosser_name2);
obj.setEmbosser_name3(obj.embosser_name3);
obj.setEmbosser_name4(obj.embosser_name4);
obj.setCard_action(obj.card_action);
obj.setNmbr_requested(obj.nmbr_requested);
obj.setKind_typ(obj.kind_typ);
obj.setIssue_cnt(obj.issue_cnt);
obj.setNmbr_cards_ret(obj.nmbr_cards_ret);
obj.setReq_card_type(obj.req_card_type);
obj.setCard_prev_action(obj.card_prev_action);
obj.setPre_reissue_action(obj.pre_reissue_action);
obj.setReissue_action(obj.reissue_action);
obj.setXfr_org_nmbr(obj.xfr_org_nmbr);
obj.setXfr_type_nmbr(obj.xfr_type_nmbr);
obj.setXfr_acct_nmbr(obj.xfr_acct_nmbr);
obj.setCrd_corr_acct(obj.crd_corr_acct);
obj.setAccount1_org(obj.account1_org);
obj.setAccount1_type(obj.account1_type);
obj.setAccount1_curr_code(obj.account1_curr_code);
obj.setAccount2_nmbr(obj.account2_nmbr);
obj.setAccount2_org(obj.account2_org);
obj.setAccount2_type(obj.account2_type);
obj.setAccount2_curr_code(obj.account2_curr_code);
obj.setAccount3_org(obj.account3_org);
obj.setAccount3_type(obj.account3_type);
obj.setAccount3_nmbr(obj.account3_nmbr);
obj.setAccount3_curr_code(obj.account3_curr_code);
obj.setFirst_usg_flag(obj.first_usg_flag);
obj.setCash_adv_flg(obj.cash_adv_flg);
obj.setOf_annual_fee_flg(obj.of_annual_fee_flg);
obj.setCard_mailer_ind(obj.card_mailer_ind);
obj.setActivation_cd(obj.activation_cd);
obj.setLast_activation_cd(obj.last_activation_cd);
obj.setVip_card(obj.vip_card);
obj.setAmrl_alimony_agreement(obj.amrl_alimony_agreement);
obj.setSms_flag(obj.sms_flag);
obj.setVerify_password_amnt(obj.verify_password_amnt);
obj.setNmbr_rtl_auth_ytd(obj.nmbr_rtl_auth_ytd);
obj.setLst_nmbr_rtl_auth_ytd(obj.lst_nmbr_rtl_auth_ytd);
obj.setAppid(obj.appid);
obj.setSrc_cd(obj.src_cd);
obj.setMarket(obj.market);
obj.setMarket_org_cd(obj.market_org_cd);
obj.setOds_load_dt(obj.ods_load_dt);
return obj;}}