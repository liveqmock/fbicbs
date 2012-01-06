package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class ODSBCRDCRTACCT extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new ODSBCRDCRTACCT().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new ODSBCRDCRTACCT().findAndLockByWhere(sSqlWhere);      }       public static ODSBCRDCRTACCT findFirst(String sSqlWhere) {           return (ODSBCRDCRTACCT)new ODSBCRDCRTACCT().findFirstByWhere(sSqlWhere);      }       public static ODSBCRDCRTACCT findFirstAndLock(String sSqlWhere) {           return (ODSBCRDCRTACCT)new ODSBCRDCRTACCT().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new ODSBCRDCRTACCT().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTACCT bean = new ODSBCRDCRTACCT();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTACCT bean = new ODSBCRDCRTACCT();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static ODSBCRDCRTACCT findFirst(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTACCT bean = new ODSBCRDCRTACCT();           bean.setAutoRelease(isAutoRelease);           return (ODSBCRDCRTACCT)bean.findFirstByWhere(sSqlWhere);      }       public static ODSBCRDCRTACCT findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTACCT bean = new ODSBCRDCRTACCT();           bean.setAutoRelease(isAutoRelease);           return (ODSBCRDCRTACCT)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           ODSBCRDCRTACCT bean = new ODSBCRDCRTACCT();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new ODSBCRDCRTACCT().findByWhereByRow(sSqlWhere,row);      } String org_no;
String actp;
String acct_no;
String ods_src_dt;
String curr_cd;
String ochr_customer_org;
String cust_no;
String stmt_day;
String xfr_org_nmbr;
String xfr_type_nmbr;
String xfr_acct_nmbr;
String officer;
String curr_collector;
String perm_collector;
String corp_acct_org;
String corp_acct_nmbr;
String collateral_code;
String sr_user_code_2;
String user_code_3;
String block_code;
String alt_block_code;
String acct_sts;
String of_late_fee_flg;
String of_ovlm_fee_flg;
String of_svc_flg;
String stat_chng_flag;
String auth_point;
String sr_chgoff_status_flag;
String acct_crd_op_dt;
String last_sts_chg_dt;
String acct_crd_en_dt;
String last_consume_dt;
String last_cash_dt;
String dte_lst_delq;
String dte_prior_delq;
String cm_dte_lst_active;
String last_cahs_repay_dt;
String last_consume_repay_dt;
String last_chg_crlim_dt;
String lst_crlimit_dte;
String sr_dte_lst_stmt;
String dte_nxt_stmt;
String dte_pymt_due;
String dte_hi_balance;
String last_repay_dt;
String dte_cr_balance;
String cm_dte_lst_maint;
String dte_lst_rate_chg;
String dte_into_collection;
String birth_dt;
String trn_crd_eff_dt;
String last_fee_dt;
String chgoff_sts_chg_dt;
String dte_cr_bal_range;
String block_dt;
String dte_alt_block_code;
String dte_filler_2;
String dte_filler_1;
double credit_limit;
double lst_crlimit;
int months_balance;
int months_purchase;
String delq_hist_24;
String coll_history_flag;
String ol_flag;
String olc_reason;
double ol_amt_due;
String card_usage_flag;
int total_nmbr_cards;
String pymt_flag;
String sr_ach_flag;
String beg_cycle_due;
String cycle_due;
double low_payment_fee;
String reage_request;
String last_pymt_sw;
int last_pymt_aged_count;
double consume_dr_amt;
double consume_cr_amt;
double consume_bal;
double inac_owe_consume_int;
double rtl_svc_bnp;
double inac_owe_misc_fee;
double rtl_insur_bnp;
double inac_owe_anfe;
double stmt_period_cash_misc_fee;
double consume_argu_amt;
double curr_consume_bal;
double consume_accum_int;
int consume_dr_cnt;
int consume_cr_cnt;
double stmt_period_consume_repay;
double ytd_inac_consume_int;
double rtl_ibnp_lst_stmt;
double ytd_pay_consume_int;
double rtl_lst_ytd_intr_paid;
double rtl_amnt_pymt_rev;
double rtl_std_intr;
double rtl_provis_bal;
double rtl_provis_intr;
double cash_dr_amt;
double cash_cr_amt;
double cash_bal;
double inac_owe_cash_int;
double inac_owe_cash_svc;
double cash_dissent_bal;
double curr_cash_bal;
double cash_accum_int;
int cash_dr_cnt;
int cash_cr_cnt;
double stmt_period_cash_repay;
double ytd_inac_cash_int;
double cash_ibnp_lst_stmt;
double cash_ytd_intr_paid;
double cash_lst_ytd_intr_paid;
double cash_amnt_pymt_rev;
double cash_std_intr;
double rtl_neg_provis_intr;
double rtl_neg_antici_intr;
double curr_bal;
double outst_auth_amt;
int outst_auth_cnt;
double avail_crlmt;
double csh_adv_lim;
double csh_adv_avail;
int grace_days;
double lst_ytd_intr_billed;
double ytd_inac_service_fee;
int disput_count;
double disput_fee;
double consume_apcl_rate;
double cash_apcl_amt;
double lst_pymt_amnt;
int aggr_cash_days;
int aggr_rtl_days;
double dl_amt;
int aggr_ytd_days;
double aggr_ytd_balance;
int aggr_lst_ytd_days;
double aggr_lst_ytd_balance;
String ci_flag;
String ri_flag;
double cash_adv_per_fee_ctd;
String coll_block_code;
double instl_limit;
double part_bal;
double outst_instl_amt;
double use_instl_limit;
String tcm_grace_days;
double perm_credit_limit;
double temp_credit_limit;
String crlimit_temp_eff_dte;
String crlimit_temp_exp_dte;
double cash_limit;
double avl_cash_bal;
int cash_adv_os_auth;
String dte_lst_cash_auth;
double cash_cr_adjust_bnp;
double rtl_cr_adjust_bnp;
double ol_cash_pymt;
double ol_rtl_pymt;
String last_repay_dt_due;
String src_cd;
String last_pri_block_code;
double last_processing_bal;
double pymt_bfr_grace_ctd;
String acct_ledger_sts;
String short_name;
String alpha_key;
String engage_rfn_acct_no_new;
String cr_census_tract;
String ln_prod_cod;
String ods_load_dt;
public static final String TABLENAME ="odsb_crd_crt_acct";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
ODSBCRDCRTACCT abb = new ODSBCRDCRTACCT();
abb.org_no=rs.getString("org_no");abb.setKeyValue("ORG_NO",""+abb.getOrg_no());
abb.actp=rs.getString("actp");abb.setKeyValue("ACTP",""+abb.getActp());
abb.acct_no=rs.getString("acct_no");abb.setKeyValue("ACCT_NO",""+abb.getAcct_no());
abb.ods_src_dt=rs.getString("ods_src_dt");abb.setKeyValue("ODS_SRC_DT",""+abb.getOds_src_dt());
abb.curr_cd=rs.getString("curr_cd");abb.setKeyValue("CURR_CD",""+abb.getCurr_cd());
abb.ochr_customer_org=rs.getString("ochr_customer_org");abb.setKeyValue("OCHR_CUSTOMER_ORG",""+abb.getOchr_customer_org());
abb.cust_no=rs.getString("cust_no");abb.setKeyValue("CUST_NO",""+abb.getCust_no());
abb.stmt_day=rs.getString("stmt_day");abb.setKeyValue("STMT_DAY",""+abb.getStmt_day());
abb.xfr_org_nmbr=rs.getString("xfr_org_nmbr");abb.setKeyValue("XFR_ORG_NMBR",""+abb.getXfr_org_nmbr());
abb.xfr_type_nmbr=rs.getString("xfr_type_nmbr");abb.setKeyValue("XFR_TYPE_NMBR",""+abb.getXfr_type_nmbr());
abb.xfr_acct_nmbr=rs.getString("xfr_acct_nmbr");abb.setKeyValue("XFR_ACCT_NMBR",""+abb.getXfr_acct_nmbr());
abb.officer=rs.getString("officer");abb.setKeyValue("OFFICER",""+abb.getOfficer());
abb.curr_collector=rs.getString("curr_collector");abb.setKeyValue("CURR_COLLECTOR",""+abb.getCurr_collector());
abb.perm_collector=rs.getString("perm_collector");abb.setKeyValue("PERM_COLLECTOR",""+abb.getPerm_collector());
abb.corp_acct_org=rs.getString("corp_acct_org");abb.setKeyValue("CORP_ACCT_ORG",""+abb.getCorp_acct_org());
abb.corp_acct_nmbr=rs.getString("corp_acct_nmbr");abb.setKeyValue("CORP_ACCT_NMBR",""+abb.getCorp_acct_nmbr());
abb.collateral_code=rs.getString("collateral_code");abb.setKeyValue("COLLATERAL_CODE",""+abb.getCollateral_code());
abb.sr_user_code_2=rs.getString("sr_user_code_2");abb.setKeyValue("SR_USER_CODE_2",""+abb.getSr_user_code_2());
abb.user_code_3=rs.getString("user_code_3");abb.setKeyValue("USER_CODE_3",""+abb.getUser_code_3());
abb.block_code=rs.getString("block_code");abb.setKeyValue("BLOCK_CODE",""+abb.getBlock_code());
abb.alt_block_code=rs.getString("alt_block_code");abb.setKeyValue("ALT_BLOCK_CODE",""+abb.getAlt_block_code());
abb.acct_sts=rs.getString("acct_sts");abb.setKeyValue("ACCT_STS",""+abb.getAcct_sts());
abb.of_late_fee_flg=rs.getString("of_late_fee_flg");abb.setKeyValue("OF_LATE_FEE_FLG",""+abb.getOf_late_fee_flg());
abb.of_ovlm_fee_flg=rs.getString("of_ovlm_fee_flg");abb.setKeyValue("OF_OVLM_FEE_FLG",""+abb.getOf_ovlm_fee_flg());
abb.of_svc_flg=rs.getString("of_svc_flg");abb.setKeyValue("OF_SVC_FLG",""+abb.getOf_svc_flg());
abb.stat_chng_flag=rs.getString("stat_chng_flag");abb.setKeyValue("STAT_CHNG_FLAG",""+abb.getStat_chng_flag());
abb.auth_point=rs.getString("auth_point");abb.setKeyValue("AUTH_POINT",""+abb.getAuth_point());
abb.sr_chgoff_status_flag=rs.getString("sr_chgoff_status_flag");abb.setKeyValue("SR_CHGOFF_STATUS_FLAG",""+abb.getSr_chgoff_status_flag());
abb.acct_crd_op_dt=rs.getString("acct_crd_op_dt");abb.setKeyValue("ACCT_CRD_OP_DT",""+abb.getAcct_crd_op_dt());
abb.last_sts_chg_dt=rs.getString("last_sts_chg_dt");abb.setKeyValue("LAST_STS_CHG_DT",""+abb.getLast_sts_chg_dt());
abb.acct_crd_en_dt=rs.getString("acct_crd_en_dt");abb.setKeyValue("ACCT_CRD_EN_DT",""+abb.getAcct_crd_en_dt());
abb.last_consume_dt=rs.getString("last_consume_dt");abb.setKeyValue("LAST_CONSUME_DT",""+abb.getLast_consume_dt());
abb.last_cash_dt=rs.getString("last_cash_dt");abb.setKeyValue("LAST_CASH_DT",""+abb.getLast_cash_dt());
abb.dte_lst_delq=rs.getString("dte_lst_delq");abb.setKeyValue("DTE_LST_DELQ",""+abb.getDte_lst_delq());
abb.dte_prior_delq=rs.getString("dte_prior_delq");abb.setKeyValue("DTE_PRIOR_DELQ",""+abb.getDte_prior_delq());
abb.cm_dte_lst_active=rs.getString("cm_dte_lst_active");abb.setKeyValue("CM_DTE_LST_ACTIVE",""+abb.getCm_dte_lst_active());
abb.last_cahs_repay_dt=rs.getString("last_cahs_repay_dt");abb.setKeyValue("LAST_CAHS_REPAY_DT",""+abb.getLast_cahs_repay_dt());
abb.last_consume_repay_dt=rs.getString("last_consume_repay_dt");abb.setKeyValue("LAST_CONSUME_REPAY_DT",""+abb.getLast_consume_repay_dt());
abb.last_chg_crlim_dt=rs.getString("last_chg_crlim_dt");abb.setKeyValue("LAST_CHG_CRLIM_DT",""+abb.getLast_chg_crlim_dt());
abb.lst_crlimit_dte=rs.getString("lst_crlimit_dte");abb.setKeyValue("LST_CRLIMIT_DTE",""+abb.getLst_crlimit_dte());
abb.sr_dte_lst_stmt=rs.getString("sr_dte_lst_stmt");abb.setKeyValue("SR_DTE_LST_STMT",""+abb.getSr_dte_lst_stmt());
abb.dte_nxt_stmt=rs.getString("dte_nxt_stmt");abb.setKeyValue("DTE_NXT_STMT",""+abb.getDte_nxt_stmt());
abb.dte_pymt_due=rs.getString("dte_pymt_due");abb.setKeyValue("DTE_PYMT_DUE",""+abb.getDte_pymt_due());
abb.dte_hi_balance=rs.getString("dte_hi_balance");abb.setKeyValue("DTE_HI_BALANCE",""+abb.getDte_hi_balance());
abb.last_repay_dt=rs.getString("last_repay_dt");abb.setKeyValue("LAST_REPAY_DT",""+abb.getLast_repay_dt());
abb.dte_cr_balance=rs.getString("dte_cr_balance");abb.setKeyValue("DTE_CR_BALANCE",""+abb.getDte_cr_balance());
abb.cm_dte_lst_maint=rs.getString("cm_dte_lst_maint");abb.setKeyValue("CM_DTE_LST_MAINT",""+abb.getCm_dte_lst_maint());
abb.dte_lst_rate_chg=rs.getString("dte_lst_rate_chg");abb.setKeyValue("DTE_LST_RATE_CHG",""+abb.getDte_lst_rate_chg());
abb.dte_into_collection=rs.getString("dte_into_collection");abb.setKeyValue("DTE_INTO_COLLECTION",""+abb.getDte_into_collection());
abb.birth_dt=rs.getString("birth_dt");abb.setKeyValue("BIRTH_DT",""+abb.getBirth_dt());
abb.trn_crd_eff_dt=rs.getString("trn_crd_eff_dt");abb.setKeyValue("TRN_CRD_EFF_DT",""+abb.getTrn_crd_eff_dt());
abb.last_fee_dt=rs.getString("last_fee_dt");abb.setKeyValue("LAST_FEE_DT",""+abb.getLast_fee_dt());
abb.chgoff_sts_chg_dt=rs.getString("chgoff_sts_chg_dt");abb.setKeyValue("CHGOFF_STS_CHG_DT",""+abb.getChgoff_sts_chg_dt());
abb.dte_cr_bal_range=rs.getString("dte_cr_bal_range");abb.setKeyValue("DTE_CR_BAL_RANGE",""+abb.getDte_cr_bal_range());
abb.block_dt=rs.getString("block_dt");abb.setKeyValue("BLOCK_DT",""+abb.getBlock_dt());
abb.dte_alt_block_code=rs.getString("dte_alt_block_code");abb.setKeyValue("DTE_ALT_BLOCK_CODE",""+abb.getDte_alt_block_code());
abb.dte_filler_2=rs.getString("dte_filler_2");abb.setKeyValue("DTE_FILLER_2",""+abb.getDte_filler_2());
abb.dte_filler_1=rs.getString("dte_filler_1");abb.setKeyValue("DTE_FILLER_1",""+abb.getDte_filler_1());
abb.credit_limit=rs.getDouble("credit_limit");abb.setKeyValue("CREDIT_LIMIT",""+abb.getCredit_limit());
abb.lst_crlimit=rs.getDouble("lst_crlimit");abb.setKeyValue("LST_CRLIMIT",""+abb.getLst_crlimit());
abb.months_balance=rs.getInt("months_balance");abb.setKeyValue("MONTHS_BALANCE",""+abb.getMonths_balance());
abb.months_purchase=rs.getInt("months_purchase");abb.setKeyValue("MONTHS_PURCHASE",""+abb.getMonths_purchase());
abb.delq_hist_24=rs.getString("delq_hist_24");abb.setKeyValue("DELQ_HIST_24",""+abb.getDelq_hist_24());
abb.coll_history_flag=rs.getString("coll_history_flag");abb.setKeyValue("COLL_HISTORY_FLAG",""+abb.getColl_history_flag());
abb.ol_flag=rs.getString("ol_flag");abb.setKeyValue("OL_FLAG",""+abb.getOl_flag());
abb.olc_reason=rs.getString("olc_reason");abb.setKeyValue("OLC_REASON",""+abb.getOlc_reason());
abb.ol_amt_due=rs.getDouble("ol_amt_due");abb.setKeyValue("OL_AMT_DUE",""+abb.getOl_amt_due());
abb.card_usage_flag=rs.getString("card_usage_flag");abb.setKeyValue("CARD_USAGE_FLAG",""+abb.getCard_usage_flag());
abb.total_nmbr_cards=rs.getInt("total_nmbr_cards");abb.setKeyValue("TOTAL_NMBR_CARDS",""+abb.getTotal_nmbr_cards());
abb.pymt_flag=rs.getString("pymt_flag");abb.setKeyValue("PYMT_FLAG",""+abb.getPymt_flag());
abb.sr_ach_flag=rs.getString("sr_ach_flag");abb.setKeyValue("SR_ACH_FLAG",""+abb.getSr_ach_flag());
abb.beg_cycle_due=rs.getString("beg_cycle_due");abb.setKeyValue("BEG_CYCLE_DUE",""+abb.getBeg_cycle_due());
abb.cycle_due=rs.getString("cycle_due");abb.setKeyValue("CYCLE_DUE",""+abb.getCycle_due());
abb.low_payment_fee=rs.getDouble("low_payment_fee");abb.setKeyValue("LOW_PAYMENT_FEE",""+abb.getLow_payment_fee());
abb.reage_request=rs.getString("reage_request");abb.setKeyValue("REAGE_REQUEST",""+abb.getReage_request());
abb.last_pymt_sw=rs.getString("last_pymt_sw");abb.setKeyValue("LAST_PYMT_SW",""+abb.getLast_pymt_sw());
abb.last_pymt_aged_count=rs.getInt("last_pymt_aged_count");abb.setKeyValue("LAST_PYMT_AGED_COUNT",""+abb.getLast_pymt_aged_count());
abb.consume_dr_amt=rs.getDouble("consume_dr_amt");abb.setKeyValue("CONSUME_DR_AMT",""+abb.getConsume_dr_amt());
abb.consume_cr_amt=rs.getDouble("consume_cr_amt");abb.setKeyValue("CONSUME_CR_AMT",""+abb.getConsume_cr_amt());
abb.consume_bal=rs.getDouble("consume_bal");abb.setKeyValue("CONSUME_BAL",""+abb.getConsume_bal());
abb.inac_owe_consume_int=rs.getDouble("inac_owe_consume_int");abb.setKeyValue("INAC_OWE_CONSUME_INT",""+abb.getInac_owe_consume_int());
abb.rtl_svc_bnp=rs.getDouble("rtl_svc_bnp");abb.setKeyValue("RTL_SVC_BNP",""+abb.getRtl_svc_bnp());
abb.inac_owe_misc_fee=rs.getDouble("inac_owe_misc_fee");abb.setKeyValue("INAC_OWE_MISC_FEE",""+abb.getInac_owe_misc_fee());
abb.rtl_insur_bnp=rs.getDouble("rtl_insur_bnp");abb.setKeyValue("RTL_INSUR_BNP",""+abb.getRtl_insur_bnp());
abb.inac_owe_anfe=rs.getDouble("inac_owe_anfe");abb.setKeyValue("INAC_OWE_ANFE",""+abb.getInac_owe_anfe());
abb.stmt_period_cash_misc_fee=rs.getDouble("stmt_period_cash_misc_fee");abb.setKeyValue("STMT_PERIOD_CASH_MISC_FEE",""+abb.getStmt_period_cash_misc_fee());
abb.consume_argu_amt=rs.getDouble("consume_argu_amt");abb.setKeyValue("CONSUME_ARGU_AMT",""+abb.getConsume_argu_amt());
abb.curr_consume_bal=rs.getDouble("curr_consume_bal");abb.setKeyValue("CURR_CONSUME_BAL",""+abb.getCurr_consume_bal());
abb.consume_accum_int=rs.getDouble("consume_accum_int");abb.setKeyValue("CONSUME_ACCUM_INT",""+abb.getConsume_accum_int());
abb.consume_dr_cnt=rs.getInt("consume_dr_cnt");abb.setKeyValue("CONSUME_DR_CNT",""+abb.getConsume_dr_cnt());
abb.consume_cr_cnt=rs.getInt("consume_cr_cnt");abb.setKeyValue("CONSUME_CR_CNT",""+abb.getConsume_cr_cnt());
abb.stmt_period_consume_repay=rs.getDouble("stmt_period_consume_repay");abb.setKeyValue("STMT_PERIOD_CONSUME_REPAY",""+abb.getStmt_period_consume_repay());
abb.ytd_inac_consume_int=rs.getDouble("ytd_inac_consume_int");abb.setKeyValue("YTD_INAC_CONSUME_INT",""+abb.getYtd_inac_consume_int());
abb.rtl_ibnp_lst_stmt=rs.getDouble("rtl_ibnp_lst_stmt");abb.setKeyValue("RTL_IBNP_LST_STMT",""+abb.getRtl_ibnp_lst_stmt());
abb.ytd_pay_consume_int=rs.getDouble("ytd_pay_consume_int");abb.setKeyValue("YTD_PAY_CONSUME_INT",""+abb.getYtd_pay_consume_int());
abb.rtl_lst_ytd_intr_paid=rs.getDouble("rtl_lst_ytd_intr_paid");abb.setKeyValue("RTL_LST_YTD_INTR_PAID",""+abb.getRtl_lst_ytd_intr_paid());
abb.rtl_amnt_pymt_rev=rs.getDouble("rtl_amnt_pymt_rev");abb.setKeyValue("RTL_AMNT_PYMT_REV",""+abb.getRtl_amnt_pymt_rev());
abb.rtl_std_intr=rs.getDouble("rtl_std_intr");abb.setKeyValue("RTL_STD_INTR",""+abb.getRtl_std_intr());
abb.rtl_provis_bal=rs.getDouble("rtl_provis_bal");abb.setKeyValue("RTL_PROVIS_BAL",""+abb.getRtl_provis_bal());
abb.rtl_provis_intr=rs.getDouble("rtl_provis_intr");abb.setKeyValue("RTL_PROVIS_INTR",""+abb.getRtl_provis_intr());
abb.cash_dr_amt=rs.getDouble("cash_dr_amt");abb.setKeyValue("CASH_DR_AMT",""+abb.getCash_dr_amt());
abb.cash_cr_amt=rs.getDouble("cash_cr_amt");abb.setKeyValue("CASH_CR_AMT",""+abb.getCash_cr_amt());
abb.cash_bal=rs.getDouble("cash_bal");abb.setKeyValue("CASH_BAL",""+abb.getCash_bal());
abb.inac_owe_cash_int=rs.getDouble("inac_owe_cash_int");abb.setKeyValue("INAC_OWE_CASH_INT",""+abb.getInac_owe_cash_int());
abb.inac_owe_cash_svc=rs.getDouble("inac_owe_cash_svc");abb.setKeyValue("INAC_OWE_CASH_SVC",""+abb.getInac_owe_cash_svc());
abb.cash_dissent_bal=rs.getDouble("cash_dissent_bal");abb.setKeyValue("CASH_DISSENT_BAL",""+abb.getCash_dissent_bal());
abb.curr_cash_bal=rs.getDouble("curr_cash_bal");abb.setKeyValue("CURR_CASH_BAL",""+abb.getCurr_cash_bal());
abb.cash_accum_int=rs.getDouble("cash_accum_int");abb.setKeyValue("CASH_ACCUM_INT",""+abb.getCash_accum_int());
abb.cash_dr_cnt=rs.getInt("cash_dr_cnt");abb.setKeyValue("CASH_DR_CNT",""+abb.getCash_dr_cnt());
abb.cash_cr_cnt=rs.getInt("cash_cr_cnt");abb.setKeyValue("CASH_CR_CNT",""+abb.getCash_cr_cnt());
abb.stmt_period_cash_repay=rs.getDouble("stmt_period_cash_repay");abb.setKeyValue("STMT_PERIOD_CASH_REPAY",""+abb.getStmt_period_cash_repay());
abb.ytd_inac_cash_int=rs.getDouble("ytd_inac_cash_int");abb.setKeyValue("YTD_INAC_CASH_INT",""+abb.getYtd_inac_cash_int());
abb.cash_ibnp_lst_stmt=rs.getDouble("cash_ibnp_lst_stmt");abb.setKeyValue("CASH_IBNP_LST_STMT",""+abb.getCash_ibnp_lst_stmt());
abb.cash_ytd_intr_paid=rs.getDouble("cash_ytd_intr_paid");abb.setKeyValue("CASH_YTD_INTR_PAID",""+abb.getCash_ytd_intr_paid());
abb.cash_lst_ytd_intr_paid=rs.getDouble("cash_lst_ytd_intr_paid");abb.setKeyValue("CASH_LST_YTD_INTR_PAID",""+abb.getCash_lst_ytd_intr_paid());
abb.cash_amnt_pymt_rev=rs.getDouble("cash_amnt_pymt_rev");abb.setKeyValue("CASH_AMNT_PYMT_REV",""+abb.getCash_amnt_pymt_rev());
abb.cash_std_intr=rs.getDouble("cash_std_intr");abb.setKeyValue("CASH_STD_INTR",""+abb.getCash_std_intr());
abb.rtl_neg_provis_intr=rs.getDouble("rtl_neg_provis_intr");abb.setKeyValue("RTL_NEG_PROVIS_INTR",""+abb.getRtl_neg_provis_intr());
abb.rtl_neg_antici_intr=rs.getDouble("rtl_neg_antici_intr");abb.setKeyValue("RTL_NEG_ANTICI_INTR",""+abb.getRtl_neg_antici_intr());
abb.curr_bal=rs.getDouble("curr_bal");abb.setKeyValue("CURR_BAL",""+abb.getCurr_bal());
abb.outst_auth_amt=rs.getDouble("outst_auth_amt");abb.setKeyValue("OUTST_AUTH_AMT",""+abb.getOutst_auth_amt());
abb.outst_auth_cnt=rs.getInt("outst_auth_cnt");abb.setKeyValue("OUTST_AUTH_CNT",""+abb.getOutst_auth_cnt());
abb.avail_crlmt=rs.getDouble("avail_crlmt");abb.setKeyValue("AVAIL_CRLMT",""+abb.getAvail_crlmt());
abb.csh_adv_lim=rs.getDouble("csh_adv_lim");abb.setKeyValue("CSH_ADV_LIM",""+abb.getCsh_adv_lim());
abb.csh_adv_avail=rs.getDouble("csh_adv_avail");abb.setKeyValue("CSH_ADV_AVAIL",""+abb.getCsh_adv_avail());
abb.grace_days=rs.getInt("grace_days");abb.setKeyValue("GRACE_DAYS",""+abb.getGrace_days());
abb.lst_ytd_intr_billed=rs.getDouble("lst_ytd_intr_billed");abb.setKeyValue("LST_YTD_INTR_BILLED",""+abb.getLst_ytd_intr_billed());
abb.ytd_inac_service_fee=rs.getDouble("ytd_inac_service_fee");abb.setKeyValue("YTD_INAC_SERVICE_FEE",""+abb.getYtd_inac_service_fee());
abb.disput_count=rs.getInt("disput_count");abb.setKeyValue("DISPUT_COUNT",""+abb.getDisput_count());
abb.disput_fee=rs.getDouble("disput_fee");abb.setKeyValue("DISPUT_FEE",""+abb.getDisput_fee());
abb.consume_apcl_rate=rs.getDouble("consume_apcl_rate");abb.setKeyValue("CONSUME_APCL_RATE",""+abb.getConsume_apcl_rate());
abb.cash_apcl_amt=rs.getDouble("cash_apcl_amt");abb.setKeyValue("CASH_APCL_AMT",""+abb.getCash_apcl_amt());
abb.lst_pymt_amnt=rs.getDouble("lst_pymt_amnt");abb.setKeyValue("LST_PYMT_AMNT",""+abb.getLst_pymt_amnt());
abb.aggr_cash_days=rs.getInt("aggr_cash_days");abb.setKeyValue("AGGR_CASH_DAYS",""+abb.getAggr_cash_days());
abb.aggr_rtl_days=rs.getInt("aggr_rtl_days");abb.setKeyValue("AGGR_RTL_DAYS",""+abb.getAggr_rtl_days());
abb.dl_amt=rs.getDouble("dl_amt");abb.setKeyValue("DL_AMT",""+abb.getDl_amt());
abb.aggr_ytd_days=rs.getInt("aggr_ytd_days");abb.setKeyValue("AGGR_YTD_DAYS",""+abb.getAggr_ytd_days());
abb.aggr_ytd_balance=rs.getDouble("aggr_ytd_balance");abb.setKeyValue("AGGR_YTD_BALANCE",""+abb.getAggr_ytd_balance());
abb.aggr_lst_ytd_days=rs.getInt("aggr_lst_ytd_days");abb.setKeyValue("AGGR_LST_YTD_DAYS",""+abb.getAggr_lst_ytd_days());
abb.aggr_lst_ytd_balance=rs.getDouble("aggr_lst_ytd_balance");abb.setKeyValue("AGGR_LST_YTD_BALANCE",""+abb.getAggr_lst_ytd_balance());
abb.ci_flag=rs.getString("ci_flag");abb.setKeyValue("CI_FLAG",""+abb.getCi_flag());
abb.ri_flag=rs.getString("ri_flag");abb.setKeyValue("RI_FLAG",""+abb.getRi_flag());
abb.cash_adv_per_fee_ctd=rs.getDouble("cash_adv_per_fee_ctd");abb.setKeyValue("CASH_ADV_PER_FEE_CTD",""+abb.getCash_adv_per_fee_ctd());
abb.coll_block_code=rs.getString("coll_block_code");abb.setKeyValue("COLL_BLOCK_CODE",""+abb.getColl_block_code());
abb.instl_limit=rs.getDouble("instl_limit");abb.setKeyValue("INSTL_LIMIT",""+abb.getInstl_limit());
abb.part_bal=rs.getDouble("part_bal");abb.setKeyValue("PART_BAL",""+abb.getPart_bal());
abb.outst_instl_amt=rs.getDouble("outst_instl_amt");abb.setKeyValue("OUTST_INSTL_AMT",""+abb.getOutst_instl_amt());
abb.use_instl_limit=rs.getDouble("use_instl_limit");abb.setKeyValue("USE_INSTL_LIMIT",""+abb.getUse_instl_limit());
abb.tcm_grace_days=rs.getString("tcm_grace_days");abb.setKeyValue("TCM_GRACE_DAYS",""+abb.getTcm_grace_days());
abb.perm_credit_limit=rs.getDouble("perm_credit_limit");abb.setKeyValue("PERM_CREDIT_LIMIT",""+abb.getPerm_credit_limit());
abb.temp_credit_limit=rs.getDouble("temp_credit_limit");abb.setKeyValue("TEMP_CREDIT_LIMIT",""+abb.getTemp_credit_limit());
abb.crlimit_temp_eff_dte=rs.getString("crlimit_temp_eff_dte");abb.setKeyValue("CRLIMIT_TEMP_EFF_DTE",""+abb.getCrlimit_temp_eff_dte());
abb.crlimit_temp_exp_dte=rs.getString("crlimit_temp_exp_dte");abb.setKeyValue("CRLIMIT_TEMP_EXP_DTE",""+abb.getCrlimit_temp_exp_dte());
abb.cash_limit=rs.getDouble("cash_limit");abb.setKeyValue("CASH_LIMIT",""+abb.getCash_limit());
abb.avl_cash_bal=rs.getDouble("avl_cash_bal");abb.setKeyValue("AVL_CASH_BAL",""+abb.getAvl_cash_bal());
abb.cash_adv_os_auth=rs.getInt("cash_adv_os_auth");abb.setKeyValue("CASH_ADV_OS_AUTH",""+abb.getCash_adv_os_auth());
abb.dte_lst_cash_auth=rs.getString("dte_lst_cash_auth");abb.setKeyValue("DTE_LST_CASH_AUTH",""+abb.getDte_lst_cash_auth());
abb.cash_cr_adjust_bnp=rs.getDouble("cash_cr_adjust_bnp");abb.setKeyValue("CASH_CR_ADJUST_BNP",""+abb.getCash_cr_adjust_bnp());
abb.rtl_cr_adjust_bnp=rs.getDouble("rtl_cr_adjust_bnp");abb.setKeyValue("RTL_CR_ADJUST_BNP",""+abb.getRtl_cr_adjust_bnp());
abb.ol_cash_pymt=rs.getDouble("ol_cash_pymt");abb.setKeyValue("OL_CASH_PYMT",""+abb.getOl_cash_pymt());
abb.ol_rtl_pymt=rs.getDouble("ol_rtl_pymt");abb.setKeyValue("OL_RTL_PYMT",""+abb.getOl_rtl_pymt());
abb.last_repay_dt_due=rs.getString("last_repay_dt_due");abb.setKeyValue("LAST_REPAY_DT_DUE",""+abb.getLast_repay_dt_due());
abb.src_cd=rs.getString("src_cd");abb.setKeyValue("SRC_CD",""+abb.getSrc_cd());
abb.last_pri_block_code=rs.getString("last_pri_block_code");abb.setKeyValue("LAST_PRI_BLOCK_CODE",""+abb.getLast_pri_block_code());
abb.last_processing_bal=rs.getDouble("last_processing_bal");abb.setKeyValue("LAST_PROCESSING_BAL",""+abb.getLast_processing_bal());
abb.pymt_bfr_grace_ctd=rs.getDouble("pymt_bfr_grace_ctd");abb.setKeyValue("PYMT_BFR_GRACE_CTD",""+abb.getPymt_bfr_grace_ctd());
abb.acct_ledger_sts=rs.getString("acct_ledger_sts");abb.setKeyValue("ACCT_LEDGER_STS",""+abb.getAcct_ledger_sts());
abb.short_name=rs.getString("short_name");abb.setKeyValue("SHORT_NAME",""+abb.getShort_name());
abb.alpha_key=rs.getString("alpha_key");abb.setKeyValue("ALPHA_KEY",""+abb.getAlpha_key());
abb.engage_rfn_acct_no_new=rs.getString("engage_rfn_acct_no_new");abb.setKeyValue("ENGAGE_RFN_ACCT_NO_NEW",""+abb.getEngage_rfn_acct_no_new());
abb.cr_census_tract=rs.getString("cr_census_tract");abb.setKeyValue("CR_CENSUS_TRACT",""+abb.getCr_census_tract());
abb.ln_prod_cod=rs.getString("ln_prod_cod");abb.setKeyValue("LN_PROD_COD",""+abb.getLn_prod_cod());
abb.ods_load_dt=rs.getString("ods_load_dt");abb.setKeyValue("ODS_LOAD_DT",""+abb.getOds_load_dt());
list.add(abb);
abb.operate_mode = "edit";
}public String getOrg_no() { if ( this.org_no == null ) return ""; return this.org_no;}
public String getActp() { if ( this.actp == null ) return ""; return this.actp;}
public String getAcct_no() { if ( this.acct_no == null ) return ""; return this.acct_no;}
public String getOds_src_dt() { if ( this.ods_src_dt == null ) return ""; return this.ods_src_dt;}
public String getCurr_cd() { if ( this.curr_cd == null ) return ""; return this.curr_cd;}
public String getOchr_customer_org() { if ( this.ochr_customer_org == null ) return ""; return this.ochr_customer_org;}
public String getCust_no() { if ( this.cust_no == null ) return ""; return this.cust_no;}
public String getStmt_day() { if ( this.stmt_day == null ) return ""; return this.stmt_day;}
public String getXfr_org_nmbr() { if ( this.xfr_org_nmbr == null ) return ""; return this.xfr_org_nmbr;}
public String getXfr_type_nmbr() { if ( this.xfr_type_nmbr == null ) return ""; return this.xfr_type_nmbr;}
public String getXfr_acct_nmbr() { if ( this.xfr_acct_nmbr == null ) return ""; return this.xfr_acct_nmbr;}
public String getOfficer() { if ( this.officer == null ) return ""; return this.officer;}
public String getCurr_collector() { if ( this.curr_collector == null ) return ""; return this.curr_collector;}
public String getPerm_collector() { if ( this.perm_collector == null ) return ""; return this.perm_collector;}
public String getCorp_acct_org() { if ( this.corp_acct_org == null ) return ""; return this.corp_acct_org;}
public String getCorp_acct_nmbr() { if ( this.corp_acct_nmbr == null ) return ""; return this.corp_acct_nmbr;}
public String getCollateral_code() { if ( this.collateral_code == null ) return ""; return this.collateral_code;}
public String getSr_user_code_2() { if ( this.sr_user_code_2 == null ) return ""; return this.sr_user_code_2;}
public String getUser_code_3() { if ( this.user_code_3 == null ) return ""; return this.user_code_3;}
public String getBlock_code() { if ( this.block_code == null ) return ""; return this.block_code;}
public String getAlt_block_code() { if ( this.alt_block_code == null ) return ""; return this.alt_block_code;}
public String getAcct_sts() { if ( this.acct_sts == null ) return ""; return this.acct_sts;}
public String getOf_late_fee_flg() { if ( this.of_late_fee_flg == null ) return ""; return this.of_late_fee_flg;}
public String getOf_ovlm_fee_flg() { if ( this.of_ovlm_fee_flg == null ) return ""; return this.of_ovlm_fee_flg;}
public String getOf_svc_flg() { if ( this.of_svc_flg == null ) return ""; return this.of_svc_flg;}
public String getStat_chng_flag() { if ( this.stat_chng_flag == null ) return ""; return this.stat_chng_flag;}
public String getAuth_point() { if ( this.auth_point == null ) return ""; return this.auth_point;}
public String getSr_chgoff_status_flag() { if ( this.sr_chgoff_status_flag == null ) return ""; return this.sr_chgoff_status_flag;}
public String getAcct_crd_op_dt() { if ( this.acct_crd_op_dt == null ) return ""; return this.acct_crd_op_dt;}
public String getLast_sts_chg_dt() { if ( this.last_sts_chg_dt == null ) return ""; return this.last_sts_chg_dt;}
public String getAcct_crd_en_dt() { if ( this.acct_crd_en_dt == null ) return ""; return this.acct_crd_en_dt;}
public String getLast_consume_dt() { if ( this.last_consume_dt == null ) return ""; return this.last_consume_dt;}
public String getLast_cash_dt() { if ( this.last_cash_dt == null ) return ""; return this.last_cash_dt;}
public String getDte_lst_delq() { if ( this.dte_lst_delq == null ) return ""; return this.dte_lst_delq;}
public String getDte_prior_delq() { if ( this.dte_prior_delq == null ) return ""; return this.dte_prior_delq;}
public String getCm_dte_lst_active() { if ( this.cm_dte_lst_active == null ) return ""; return this.cm_dte_lst_active;}
public String getLast_cahs_repay_dt() { if ( this.last_cahs_repay_dt == null ) return ""; return this.last_cahs_repay_dt;}
public String getLast_consume_repay_dt() { if ( this.last_consume_repay_dt == null ) return ""; return this.last_consume_repay_dt;}
public String getLast_chg_crlim_dt() { if ( this.last_chg_crlim_dt == null ) return ""; return this.last_chg_crlim_dt;}
public String getLst_crlimit_dte() { if ( this.lst_crlimit_dte == null ) return ""; return this.lst_crlimit_dte;}
public String getSr_dte_lst_stmt() { if ( this.sr_dte_lst_stmt == null ) return ""; return this.sr_dte_lst_stmt;}
public String getDte_nxt_stmt() { if ( this.dte_nxt_stmt == null ) return ""; return this.dte_nxt_stmt;}
public String getDte_pymt_due() { if ( this.dte_pymt_due == null ) return ""; return this.dte_pymt_due;}
public String getDte_hi_balance() { if ( this.dte_hi_balance == null ) return ""; return this.dte_hi_balance;}
public String getLast_repay_dt() { if ( this.last_repay_dt == null ) return ""; return this.last_repay_dt;}
public String getDte_cr_balance() { if ( this.dte_cr_balance == null ) return ""; return this.dte_cr_balance;}
public String getCm_dte_lst_maint() { if ( this.cm_dte_lst_maint == null ) return ""; return this.cm_dte_lst_maint;}
public String getDte_lst_rate_chg() { if ( this.dte_lst_rate_chg == null ) return ""; return this.dte_lst_rate_chg;}
public String getDte_into_collection() { if ( this.dte_into_collection == null ) return ""; return this.dte_into_collection;}
public String getBirth_dt() { if ( this.birth_dt == null ) return ""; return this.birth_dt;}
public String getTrn_crd_eff_dt() { if ( this.trn_crd_eff_dt == null ) return ""; return this.trn_crd_eff_dt;}
public String getLast_fee_dt() { if ( this.last_fee_dt == null ) return ""; return this.last_fee_dt;}
public String getChgoff_sts_chg_dt() { if ( this.chgoff_sts_chg_dt == null ) return ""; return this.chgoff_sts_chg_dt;}
public String getDte_cr_bal_range() { if ( this.dte_cr_bal_range == null ) return ""; return this.dte_cr_bal_range;}
public String getBlock_dt() { if ( this.block_dt == null ) return ""; return this.block_dt;}
public String getDte_alt_block_code() { if ( this.dte_alt_block_code == null ) return ""; return this.dte_alt_block_code;}
public String getDte_filler_2() { if ( this.dte_filler_2 == null ) return ""; return this.dte_filler_2;}
public String getDte_filler_1() { if ( this.dte_filler_1 == null ) return ""; return this.dte_filler_1;}
public double getCredit_limit() { return this.credit_limit;}
public double getLst_crlimit() { return this.lst_crlimit;}
public int getMonths_balance() { return this.months_balance;}
public int getMonths_purchase() { return this.months_purchase;}
public String getDelq_hist_24() { if ( this.delq_hist_24 == null ) return ""; return this.delq_hist_24;}
public String getColl_history_flag() { if ( this.coll_history_flag == null ) return ""; return this.coll_history_flag;}
public String getOl_flag() { if ( this.ol_flag == null ) return ""; return this.ol_flag;}
public String getOlc_reason() { if ( this.olc_reason == null ) return ""; return this.olc_reason;}
public double getOl_amt_due() { return this.ol_amt_due;}
public String getCard_usage_flag() { if ( this.card_usage_flag == null ) return ""; return this.card_usage_flag;}
public int getTotal_nmbr_cards() { return this.total_nmbr_cards;}
public String getPymt_flag() { if ( this.pymt_flag == null ) return ""; return this.pymt_flag;}
public String getSr_ach_flag() { if ( this.sr_ach_flag == null ) return ""; return this.sr_ach_flag;}
public String getBeg_cycle_due() { if ( this.beg_cycle_due == null ) return ""; return this.beg_cycle_due;}
public String getCycle_due() { if ( this.cycle_due == null ) return ""; return this.cycle_due;}
public double getLow_payment_fee() { return this.low_payment_fee;}
public String getReage_request() { if ( this.reage_request == null ) return ""; return this.reage_request;}
public String getLast_pymt_sw() { if ( this.last_pymt_sw == null ) return ""; return this.last_pymt_sw;}
public int getLast_pymt_aged_count() { return this.last_pymt_aged_count;}
public double getConsume_dr_amt() { return this.consume_dr_amt;}
public double getConsume_cr_amt() { return this.consume_cr_amt;}
public double getConsume_bal() { return this.consume_bal;}
public double getInac_owe_consume_int() { return this.inac_owe_consume_int;}
public double getRtl_svc_bnp() { return this.rtl_svc_bnp;}
public double getInac_owe_misc_fee() { return this.inac_owe_misc_fee;}
public double getRtl_insur_bnp() { return this.rtl_insur_bnp;}
public double getInac_owe_anfe() { return this.inac_owe_anfe;}
public double getStmt_period_cash_misc_fee() { return this.stmt_period_cash_misc_fee;}
public double getConsume_argu_amt() { return this.consume_argu_amt;}
public double getCurr_consume_bal() { return this.curr_consume_bal;}
public double getConsume_accum_int() { return this.consume_accum_int;}
public int getConsume_dr_cnt() { return this.consume_dr_cnt;}
public int getConsume_cr_cnt() { return this.consume_cr_cnt;}
public double getStmt_period_consume_repay() { return this.stmt_period_consume_repay;}
public double getYtd_inac_consume_int() { return this.ytd_inac_consume_int;}
public double getRtl_ibnp_lst_stmt() { return this.rtl_ibnp_lst_stmt;}
public double getYtd_pay_consume_int() { return this.ytd_pay_consume_int;}
public double getRtl_lst_ytd_intr_paid() { return this.rtl_lst_ytd_intr_paid;}
public double getRtl_amnt_pymt_rev() { return this.rtl_amnt_pymt_rev;}
public double getRtl_std_intr() { return this.rtl_std_intr;}
public double getRtl_provis_bal() { return this.rtl_provis_bal;}
public double getRtl_provis_intr() { return this.rtl_provis_intr;}
public double getCash_dr_amt() { return this.cash_dr_amt;}
public double getCash_cr_amt() { return this.cash_cr_amt;}
public double getCash_bal() { return this.cash_bal;}
public double getInac_owe_cash_int() { return this.inac_owe_cash_int;}
public double getInac_owe_cash_svc() { return this.inac_owe_cash_svc;}
public double getCash_dissent_bal() { return this.cash_dissent_bal;}
public double getCurr_cash_bal() { return this.curr_cash_bal;}
public double getCash_accum_int() { return this.cash_accum_int;}
public int getCash_dr_cnt() { return this.cash_dr_cnt;}
public int getCash_cr_cnt() { return this.cash_cr_cnt;}
public double getStmt_period_cash_repay() { return this.stmt_period_cash_repay;}
public double getYtd_inac_cash_int() { return this.ytd_inac_cash_int;}
public double getCash_ibnp_lst_stmt() { return this.cash_ibnp_lst_stmt;}
public double getCash_ytd_intr_paid() { return this.cash_ytd_intr_paid;}
public double getCash_lst_ytd_intr_paid() { return this.cash_lst_ytd_intr_paid;}
public double getCash_amnt_pymt_rev() { return this.cash_amnt_pymt_rev;}
public double getCash_std_intr() { return this.cash_std_intr;}
public double getRtl_neg_provis_intr() { return this.rtl_neg_provis_intr;}
public double getRtl_neg_antici_intr() { return this.rtl_neg_antici_intr;}
public double getCurr_bal() { return this.curr_bal;}
public double getOutst_auth_amt() { return this.outst_auth_amt;}
public int getOutst_auth_cnt() { return this.outst_auth_cnt;}
public double getAvail_crlmt() { return this.avail_crlmt;}
public double getCsh_adv_lim() { return this.csh_adv_lim;}
public double getCsh_adv_avail() { return this.csh_adv_avail;}
public int getGrace_days() { return this.grace_days;}
public double getLst_ytd_intr_billed() { return this.lst_ytd_intr_billed;}
public double getYtd_inac_service_fee() { return this.ytd_inac_service_fee;}
public int getDisput_count() { return this.disput_count;}
public double getDisput_fee() { return this.disput_fee;}
public double getConsume_apcl_rate() { return this.consume_apcl_rate;}
public double getCash_apcl_amt() { return this.cash_apcl_amt;}
public double getLst_pymt_amnt() { return this.lst_pymt_amnt;}
public int getAggr_cash_days() { return this.aggr_cash_days;}
public int getAggr_rtl_days() { return this.aggr_rtl_days;}
public double getDl_amt() { return this.dl_amt;}
public int getAggr_ytd_days() { return this.aggr_ytd_days;}
public double getAggr_ytd_balance() { return this.aggr_ytd_balance;}
public int getAggr_lst_ytd_days() { return this.aggr_lst_ytd_days;}
public double getAggr_lst_ytd_balance() { return this.aggr_lst_ytd_balance;}
public String getCi_flag() { if ( this.ci_flag == null ) return ""; return this.ci_flag;}
public String getRi_flag() { if ( this.ri_flag == null ) return ""; return this.ri_flag;}
public double getCash_adv_per_fee_ctd() { return this.cash_adv_per_fee_ctd;}
public String getColl_block_code() { if ( this.coll_block_code == null ) return ""; return this.coll_block_code;}
public double getInstl_limit() { return this.instl_limit;}
public double getPart_bal() { return this.part_bal;}
public double getOutst_instl_amt() { return this.outst_instl_amt;}
public double getUse_instl_limit() { return this.use_instl_limit;}
public String getTcm_grace_days() { if ( this.tcm_grace_days == null ) return ""; return this.tcm_grace_days;}
public double getPerm_credit_limit() { return this.perm_credit_limit;}
public double getTemp_credit_limit() { return this.temp_credit_limit;}
public String getCrlimit_temp_eff_dte() { if ( this.crlimit_temp_eff_dte == null ) return ""; return this.crlimit_temp_eff_dte;}
public String getCrlimit_temp_exp_dte() { if ( this.crlimit_temp_exp_dte == null ) return ""; return this.crlimit_temp_exp_dte;}
public double getCash_limit() { return this.cash_limit;}
public double getAvl_cash_bal() { return this.avl_cash_bal;}
public int getCash_adv_os_auth() { return this.cash_adv_os_auth;}
public String getDte_lst_cash_auth() { if ( this.dte_lst_cash_auth == null ) return ""; return this.dte_lst_cash_auth;}
public double getCash_cr_adjust_bnp() { return this.cash_cr_adjust_bnp;}
public double getRtl_cr_adjust_bnp() { return this.rtl_cr_adjust_bnp;}
public double getOl_cash_pymt() { return this.ol_cash_pymt;}
public double getOl_rtl_pymt() { return this.ol_rtl_pymt;}
public String getLast_repay_dt_due() { if ( this.last_repay_dt_due == null ) return ""; return this.last_repay_dt_due;}
public String getSrc_cd() { if ( this.src_cd == null ) return ""; return this.src_cd;}
public String getLast_pri_block_code() { if ( this.last_pri_block_code == null ) return ""; return this.last_pri_block_code;}
public double getLast_processing_bal() { return this.last_processing_bal;}
public double getPymt_bfr_grace_ctd() { return this.pymt_bfr_grace_ctd;}
public String getAcct_ledger_sts() { if ( this.acct_ledger_sts == null ) return ""; return this.acct_ledger_sts;}
public String getShort_name() { if ( this.short_name == null ) return ""; return this.short_name;}
public String getAlpha_key() { if ( this.alpha_key == null ) return ""; return this.alpha_key;}
public String getEngage_rfn_acct_no_new() { if ( this.engage_rfn_acct_no_new == null ) return ""; return this.engage_rfn_acct_no_new;}
public String getCr_census_tract() { if ( this.cr_census_tract == null ) return ""; return this.cr_census_tract;}
public String getLn_prod_cod() { if ( this.ln_prod_cod == null ) return ""; return this.ln_prod_cod;}
public String getOds_load_dt() { if ( this.ods_load_dt == null ) return ""; return this.ods_load_dt;}
public void setOrg_no(String org_no) { sqlMaker.setField("org_no",org_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOrg_no().equals(org_no)) cf.add("org_no",this.org_no,org_no); } this.org_no=org_no;}
public void setActp(String actp) { sqlMaker.setField("actp",actp,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getActp().equals(actp)) cf.add("actp",this.actp,actp); } this.actp=actp;}
public void setAcct_no(String acct_no) { sqlMaker.setField("acct_no",acct_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAcct_no().equals(acct_no)) cf.add("acct_no",this.acct_no,acct_no); } this.acct_no=acct_no;}
public void setOds_src_dt(String ods_src_dt) { sqlMaker.setField("ods_src_dt",ods_src_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOds_src_dt().equals(ods_src_dt)) cf.add("ods_src_dt",this.ods_src_dt,ods_src_dt); } this.ods_src_dt=ods_src_dt;}
public void setCurr_cd(String curr_cd) { sqlMaker.setField("curr_cd",curr_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCurr_cd().equals(curr_cd)) cf.add("curr_cd",this.curr_cd,curr_cd); } this.curr_cd=curr_cd;}
public void setOchr_customer_org(String ochr_customer_org) { sqlMaker.setField("ochr_customer_org",ochr_customer_org,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOchr_customer_org().equals(ochr_customer_org)) cf.add("ochr_customer_org",this.ochr_customer_org,ochr_customer_org); } this.ochr_customer_org=ochr_customer_org;}
public void setCust_no(String cust_no) { sqlMaker.setField("cust_no",cust_no,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCust_no().equals(cust_no)) cf.add("cust_no",this.cust_no,cust_no); } this.cust_no=cust_no;}
public void setStmt_day(String stmt_day) { sqlMaker.setField("stmt_day",stmt_day,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getStmt_day().equals(stmt_day)) cf.add("stmt_day",this.stmt_day,stmt_day); } this.stmt_day=stmt_day;}
public void setXfr_org_nmbr(String xfr_org_nmbr) { sqlMaker.setField("xfr_org_nmbr",xfr_org_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getXfr_org_nmbr().equals(xfr_org_nmbr)) cf.add("xfr_org_nmbr",this.xfr_org_nmbr,xfr_org_nmbr); } this.xfr_org_nmbr=xfr_org_nmbr;}
public void setXfr_type_nmbr(String xfr_type_nmbr) { sqlMaker.setField("xfr_type_nmbr",xfr_type_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getXfr_type_nmbr().equals(xfr_type_nmbr)) cf.add("xfr_type_nmbr",this.xfr_type_nmbr,xfr_type_nmbr); } this.xfr_type_nmbr=xfr_type_nmbr;}
public void setXfr_acct_nmbr(String xfr_acct_nmbr) { sqlMaker.setField("xfr_acct_nmbr",xfr_acct_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getXfr_acct_nmbr().equals(xfr_acct_nmbr)) cf.add("xfr_acct_nmbr",this.xfr_acct_nmbr,xfr_acct_nmbr); } this.xfr_acct_nmbr=xfr_acct_nmbr;}
public void setOfficer(String officer) { sqlMaker.setField("officer",officer,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOfficer().equals(officer)) cf.add("officer",this.officer,officer); } this.officer=officer;}
public void setCurr_collector(String curr_collector) { sqlMaker.setField("curr_collector",curr_collector,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCurr_collector().equals(curr_collector)) cf.add("curr_collector",this.curr_collector,curr_collector); } this.curr_collector=curr_collector;}
public void setPerm_collector(String perm_collector) { sqlMaker.setField("perm_collector",perm_collector,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getPerm_collector().equals(perm_collector)) cf.add("perm_collector",this.perm_collector,perm_collector); } this.perm_collector=perm_collector;}
public void setCorp_acct_org(String corp_acct_org) { sqlMaker.setField("corp_acct_org",corp_acct_org,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCorp_acct_org().equals(corp_acct_org)) cf.add("corp_acct_org",this.corp_acct_org,corp_acct_org); } this.corp_acct_org=corp_acct_org;}
public void setCorp_acct_nmbr(String corp_acct_nmbr) { sqlMaker.setField("corp_acct_nmbr",corp_acct_nmbr,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCorp_acct_nmbr().equals(corp_acct_nmbr)) cf.add("corp_acct_nmbr",this.corp_acct_nmbr,corp_acct_nmbr); } this.corp_acct_nmbr=corp_acct_nmbr;}
public void setCollateral_code(String collateral_code) { sqlMaker.setField("collateral_code",collateral_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCollateral_code().equals(collateral_code)) cf.add("collateral_code",this.collateral_code,collateral_code); } this.collateral_code=collateral_code;}
public void setSr_user_code_2(String sr_user_code_2) { sqlMaker.setField("sr_user_code_2",sr_user_code_2,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSr_user_code_2().equals(sr_user_code_2)) cf.add("sr_user_code_2",this.sr_user_code_2,sr_user_code_2); } this.sr_user_code_2=sr_user_code_2;}
public void setUser_code_3(String user_code_3) { sqlMaker.setField("user_code_3",user_code_3,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getUser_code_3().equals(user_code_3)) cf.add("user_code_3",this.user_code_3,user_code_3); } this.user_code_3=user_code_3;}
public void setBlock_code(String block_code) { sqlMaker.setField("block_code",block_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBlock_code().equals(block_code)) cf.add("block_code",this.block_code,block_code); } this.block_code=block_code;}
public void setAlt_block_code(String alt_block_code) { sqlMaker.setField("alt_block_code",alt_block_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAlt_block_code().equals(alt_block_code)) cf.add("alt_block_code",this.alt_block_code,alt_block_code); } this.alt_block_code=alt_block_code;}
public void setAcct_sts(String acct_sts) { sqlMaker.setField("acct_sts",acct_sts,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAcct_sts().equals(acct_sts)) cf.add("acct_sts",this.acct_sts,acct_sts); } this.acct_sts=acct_sts;}
public void setOf_late_fee_flg(String of_late_fee_flg) { sqlMaker.setField("of_late_fee_flg",of_late_fee_flg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOf_late_fee_flg().equals(of_late_fee_flg)) cf.add("of_late_fee_flg",this.of_late_fee_flg,of_late_fee_flg); } this.of_late_fee_flg=of_late_fee_flg;}
public void setOf_ovlm_fee_flg(String of_ovlm_fee_flg) { sqlMaker.setField("of_ovlm_fee_flg",of_ovlm_fee_flg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOf_ovlm_fee_flg().equals(of_ovlm_fee_flg)) cf.add("of_ovlm_fee_flg",this.of_ovlm_fee_flg,of_ovlm_fee_flg); } this.of_ovlm_fee_flg=of_ovlm_fee_flg;}
public void setOf_svc_flg(String of_svc_flg) { sqlMaker.setField("of_svc_flg",of_svc_flg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOf_svc_flg().equals(of_svc_flg)) cf.add("of_svc_flg",this.of_svc_flg,of_svc_flg); } this.of_svc_flg=of_svc_flg;}
public void setStat_chng_flag(String stat_chng_flag) { sqlMaker.setField("stat_chng_flag",stat_chng_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getStat_chng_flag().equals(stat_chng_flag)) cf.add("stat_chng_flag",this.stat_chng_flag,stat_chng_flag); } this.stat_chng_flag=stat_chng_flag;}
public void setAuth_point(String auth_point) { sqlMaker.setField("auth_point",auth_point,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAuth_point().equals(auth_point)) cf.add("auth_point",this.auth_point,auth_point); } this.auth_point=auth_point;}
public void setSr_chgoff_status_flag(String sr_chgoff_status_flag) { sqlMaker.setField("sr_chgoff_status_flag",sr_chgoff_status_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSr_chgoff_status_flag().equals(sr_chgoff_status_flag)) cf.add("sr_chgoff_status_flag",this.sr_chgoff_status_flag,sr_chgoff_status_flag); } this.sr_chgoff_status_flag=sr_chgoff_status_flag;}
public void setAcct_crd_op_dt(String acct_crd_op_dt) { sqlMaker.setField("acct_crd_op_dt",acct_crd_op_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAcct_crd_op_dt().equals(acct_crd_op_dt)) cf.add("acct_crd_op_dt",this.acct_crd_op_dt,acct_crd_op_dt); } this.acct_crd_op_dt=acct_crd_op_dt;}
public void setLast_sts_chg_dt(String last_sts_chg_dt) { sqlMaker.setField("last_sts_chg_dt",last_sts_chg_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_sts_chg_dt().equals(last_sts_chg_dt)) cf.add("last_sts_chg_dt",this.last_sts_chg_dt,last_sts_chg_dt); } this.last_sts_chg_dt=last_sts_chg_dt;}
public void setAcct_crd_en_dt(String acct_crd_en_dt) { sqlMaker.setField("acct_crd_en_dt",acct_crd_en_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAcct_crd_en_dt().equals(acct_crd_en_dt)) cf.add("acct_crd_en_dt",this.acct_crd_en_dt,acct_crd_en_dt); } this.acct_crd_en_dt=acct_crd_en_dt;}
public void setLast_consume_dt(String last_consume_dt) { sqlMaker.setField("last_consume_dt",last_consume_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_consume_dt().equals(last_consume_dt)) cf.add("last_consume_dt",this.last_consume_dt,last_consume_dt); } this.last_consume_dt=last_consume_dt;}
public void setLast_cash_dt(String last_cash_dt) { sqlMaker.setField("last_cash_dt",last_cash_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_cash_dt().equals(last_cash_dt)) cf.add("last_cash_dt",this.last_cash_dt,last_cash_dt); } this.last_cash_dt=last_cash_dt;}
public void setDte_lst_delq(String dte_lst_delq) { sqlMaker.setField("dte_lst_delq",dte_lst_delq,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_delq().equals(dte_lst_delq)) cf.add("dte_lst_delq",this.dte_lst_delq,dte_lst_delq); } this.dte_lst_delq=dte_lst_delq;}
public void setDte_prior_delq(String dte_prior_delq) { sqlMaker.setField("dte_prior_delq",dte_prior_delq,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_prior_delq().equals(dte_prior_delq)) cf.add("dte_prior_delq",this.dte_prior_delq,dte_prior_delq); } this.dte_prior_delq=dte_prior_delq;}
public void setCm_dte_lst_active(String cm_dte_lst_active) { sqlMaker.setField("cm_dte_lst_active",cm_dte_lst_active,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCm_dte_lst_active().equals(cm_dte_lst_active)) cf.add("cm_dte_lst_active",this.cm_dte_lst_active,cm_dte_lst_active); } this.cm_dte_lst_active=cm_dte_lst_active;}
public void setLast_cahs_repay_dt(String last_cahs_repay_dt) { sqlMaker.setField("last_cahs_repay_dt",last_cahs_repay_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_cahs_repay_dt().equals(last_cahs_repay_dt)) cf.add("last_cahs_repay_dt",this.last_cahs_repay_dt,last_cahs_repay_dt); } this.last_cahs_repay_dt=last_cahs_repay_dt;}
public void setLast_consume_repay_dt(String last_consume_repay_dt) { sqlMaker.setField("last_consume_repay_dt",last_consume_repay_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_consume_repay_dt().equals(last_consume_repay_dt)) cf.add("last_consume_repay_dt",this.last_consume_repay_dt,last_consume_repay_dt); } this.last_consume_repay_dt=last_consume_repay_dt;}
public void setLast_chg_crlim_dt(String last_chg_crlim_dt) { sqlMaker.setField("last_chg_crlim_dt",last_chg_crlim_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_chg_crlim_dt().equals(last_chg_crlim_dt)) cf.add("last_chg_crlim_dt",this.last_chg_crlim_dt,last_chg_crlim_dt); } this.last_chg_crlim_dt=last_chg_crlim_dt;}
public void setLst_crlimit_dte(String lst_crlimit_dte) { sqlMaker.setField("lst_crlimit_dte",lst_crlimit_dte,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLst_crlimit_dte().equals(lst_crlimit_dte)) cf.add("lst_crlimit_dte",this.lst_crlimit_dte,lst_crlimit_dte); } this.lst_crlimit_dte=lst_crlimit_dte;}
public void setSr_dte_lst_stmt(String sr_dte_lst_stmt) { sqlMaker.setField("sr_dte_lst_stmt",sr_dte_lst_stmt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSr_dte_lst_stmt().equals(sr_dte_lst_stmt)) cf.add("sr_dte_lst_stmt",this.sr_dte_lst_stmt,sr_dte_lst_stmt); } this.sr_dte_lst_stmt=sr_dte_lst_stmt;}
public void setDte_nxt_stmt(String dte_nxt_stmt) { sqlMaker.setField("dte_nxt_stmt",dte_nxt_stmt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_nxt_stmt().equals(dte_nxt_stmt)) cf.add("dte_nxt_stmt",this.dte_nxt_stmt,dte_nxt_stmt); } this.dte_nxt_stmt=dte_nxt_stmt;}
public void setDte_pymt_due(String dte_pymt_due) { sqlMaker.setField("dte_pymt_due",dte_pymt_due,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_pymt_due().equals(dte_pymt_due)) cf.add("dte_pymt_due",this.dte_pymt_due,dte_pymt_due); } this.dte_pymt_due=dte_pymt_due;}
public void setDte_hi_balance(String dte_hi_balance) { sqlMaker.setField("dte_hi_balance",dte_hi_balance,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_hi_balance().equals(dte_hi_balance)) cf.add("dte_hi_balance",this.dte_hi_balance,dte_hi_balance); } this.dte_hi_balance=dte_hi_balance;}
public void setLast_repay_dt(String last_repay_dt) { sqlMaker.setField("last_repay_dt",last_repay_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_repay_dt().equals(last_repay_dt)) cf.add("last_repay_dt",this.last_repay_dt,last_repay_dt); } this.last_repay_dt=last_repay_dt;}
public void setDte_cr_balance(String dte_cr_balance) { sqlMaker.setField("dte_cr_balance",dte_cr_balance,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_cr_balance().equals(dte_cr_balance)) cf.add("dte_cr_balance",this.dte_cr_balance,dte_cr_balance); } this.dte_cr_balance=dte_cr_balance;}
public void setCm_dte_lst_maint(String cm_dte_lst_maint) { sqlMaker.setField("cm_dte_lst_maint",cm_dte_lst_maint,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCm_dte_lst_maint().equals(cm_dte_lst_maint)) cf.add("cm_dte_lst_maint",this.cm_dte_lst_maint,cm_dte_lst_maint); } this.cm_dte_lst_maint=cm_dte_lst_maint;}
public void setDte_lst_rate_chg(String dte_lst_rate_chg) { sqlMaker.setField("dte_lst_rate_chg",dte_lst_rate_chg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_rate_chg().equals(dte_lst_rate_chg)) cf.add("dte_lst_rate_chg",this.dte_lst_rate_chg,dte_lst_rate_chg); } this.dte_lst_rate_chg=dte_lst_rate_chg;}
public void setDte_into_collection(String dte_into_collection) { sqlMaker.setField("dte_into_collection",dte_into_collection,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_into_collection().equals(dte_into_collection)) cf.add("dte_into_collection",this.dte_into_collection,dte_into_collection); } this.dte_into_collection=dte_into_collection;}
public void setBirth_dt(String birth_dt) { sqlMaker.setField("birth_dt",birth_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBirth_dt().equals(birth_dt)) cf.add("birth_dt",this.birth_dt,birth_dt); } this.birth_dt=birth_dt;}
public void setTrn_crd_eff_dt(String trn_crd_eff_dt) { sqlMaker.setField("trn_crd_eff_dt",trn_crd_eff_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTrn_crd_eff_dt().equals(trn_crd_eff_dt)) cf.add("trn_crd_eff_dt",this.trn_crd_eff_dt,trn_crd_eff_dt); } this.trn_crd_eff_dt=trn_crd_eff_dt;}
public void setLast_fee_dt(String last_fee_dt) { sqlMaker.setField("last_fee_dt",last_fee_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_fee_dt().equals(last_fee_dt)) cf.add("last_fee_dt",this.last_fee_dt,last_fee_dt); } this.last_fee_dt=last_fee_dt;}
public void setChgoff_sts_chg_dt(String chgoff_sts_chg_dt) { sqlMaker.setField("chgoff_sts_chg_dt",chgoff_sts_chg_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getChgoff_sts_chg_dt().equals(chgoff_sts_chg_dt)) cf.add("chgoff_sts_chg_dt",this.chgoff_sts_chg_dt,chgoff_sts_chg_dt); } this.chgoff_sts_chg_dt=chgoff_sts_chg_dt;}
public void setDte_cr_bal_range(String dte_cr_bal_range) { sqlMaker.setField("dte_cr_bal_range",dte_cr_bal_range,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_cr_bal_range().equals(dte_cr_bal_range)) cf.add("dte_cr_bal_range",this.dte_cr_bal_range,dte_cr_bal_range); } this.dte_cr_bal_range=dte_cr_bal_range;}
public void setBlock_dt(String block_dt) { sqlMaker.setField("block_dt",block_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBlock_dt().equals(block_dt)) cf.add("block_dt",this.block_dt,block_dt); } this.block_dt=block_dt;}
public void setDte_alt_block_code(String dte_alt_block_code) { sqlMaker.setField("dte_alt_block_code",dte_alt_block_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_alt_block_code().equals(dte_alt_block_code)) cf.add("dte_alt_block_code",this.dte_alt_block_code,dte_alt_block_code); } this.dte_alt_block_code=dte_alt_block_code;}
public void setDte_filler_2(String dte_filler_2) { sqlMaker.setField("dte_filler_2",dte_filler_2,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_filler_2().equals(dte_filler_2)) cf.add("dte_filler_2",this.dte_filler_2,dte_filler_2); } this.dte_filler_2=dte_filler_2;}
public void setDte_filler_1(String dte_filler_1) { sqlMaker.setField("dte_filler_1",dte_filler_1,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_filler_1().equals(dte_filler_1)) cf.add("dte_filler_1",this.dte_filler_1,dte_filler_1); } this.dte_filler_1=dte_filler_1;}
public void setCredit_limit(double credit_limit) { sqlMaker.setField("credit_limit",""+credit_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCredit_limit()!=credit_limit) cf.add("credit_limit",this.credit_limit+"",credit_limit+""); } this.credit_limit=credit_limit;}
public void setLst_crlimit(double lst_crlimit) { sqlMaker.setField("lst_crlimit",""+lst_crlimit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLst_crlimit()!=lst_crlimit) cf.add("lst_crlimit",this.lst_crlimit+"",lst_crlimit+""); } this.lst_crlimit=lst_crlimit;}
public void setMonths_balance(int months_balance) { sqlMaker.setField("months_balance",""+months_balance,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getMonths_balance()!=months_balance) cf.add("months_balance",this.months_balance+"",months_balance+""); } this.months_balance=months_balance;}
public void setMonths_purchase(int months_purchase) { sqlMaker.setField("months_purchase",""+months_purchase,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getMonths_purchase()!=months_purchase) cf.add("months_purchase",this.months_purchase+"",months_purchase+""); } this.months_purchase=months_purchase;}
public void setDelq_hist_24(String delq_hist_24) { sqlMaker.setField("delq_hist_24",delq_hist_24,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDelq_hist_24().equals(delq_hist_24)) cf.add("delq_hist_24",this.delq_hist_24,delq_hist_24); } this.delq_hist_24=delq_hist_24;}
public void setColl_history_flag(String coll_history_flag) { sqlMaker.setField("coll_history_flag",coll_history_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getColl_history_flag().equals(coll_history_flag)) cf.add("coll_history_flag",this.coll_history_flag,coll_history_flag); } this.coll_history_flag=coll_history_flag;}
public void setOl_flag(String ol_flag) { sqlMaker.setField("ol_flag",ol_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOl_flag().equals(ol_flag)) cf.add("ol_flag",this.ol_flag,ol_flag); } this.ol_flag=ol_flag;}
public void setOlc_reason(String olc_reason) { sqlMaker.setField("olc_reason",olc_reason,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOlc_reason().equals(olc_reason)) cf.add("olc_reason",this.olc_reason,olc_reason); } this.olc_reason=olc_reason;}
public void setOl_amt_due(double ol_amt_due) { sqlMaker.setField("ol_amt_due",""+ol_amt_due,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOl_amt_due()!=ol_amt_due) cf.add("ol_amt_due",this.ol_amt_due+"",ol_amt_due+""); } this.ol_amt_due=ol_amt_due;}
public void setCard_usage_flag(String card_usage_flag) { sqlMaker.setField("card_usage_flag",card_usage_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCard_usage_flag().equals(card_usage_flag)) cf.add("card_usage_flag",this.card_usage_flag,card_usage_flag); } this.card_usage_flag=card_usage_flag;}
public void setTotal_nmbr_cards(int total_nmbr_cards) { sqlMaker.setField("total_nmbr_cards",""+total_nmbr_cards,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getTotal_nmbr_cards()!=total_nmbr_cards) cf.add("total_nmbr_cards",this.total_nmbr_cards+"",total_nmbr_cards+""); } this.total_nmbr_cards=total_nmbr_cards;}
public void setPymt_flag(String pymt_flag) { sqlMaker.setField("pymt_flag",pymt_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getPymt_flag().equals(pymt_flag)) cf.add("pymt_flag",this.pymt_flag,pymt_flag); } this.pymt_flag=pymt_flag;}
public void setSr_ach_flag(String sr_ach_flag) { sqlMaker.setField("sr_ach_flag",sr_ach_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSr_ach_flag().equals(sr_ach_flag)) cf.add("sr_ach_flag",this.sr_ach_flag,sr_ach_flag); } this.sr_ach_flag=sr_ach_flag;}
public void setBeg_cycle_due(String beg_cycle_due) { sqlMaker.setField("beg_cycle_due",beg_cycle_due,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBeg_cycle_due().equals(beg_cycle_due)) cf.add("beg_cycle_due",this.beg_cycle_due,beg_cycle_due); } this.beg_cycle_due=beg_cycle_due;}
public void setCycle_due(String cycle_due) { sqlMaker.setField("cycle_due",cycle_due,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCycle_due().equals(cycle_due)) cf.add("cycle_due",this.cycle_due,cycle_due); } this.cycle_due=cycle_due;}
public void setLow_payment_fee(double low_payment_fee) { sqlMaker.setField("low_payment_fee",""+low_payment_fee,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLow_payment_fee()!=low_payment_fee) cf.add("low_payment_fee",this.low_payment_fee+"",low_payment_fee+""); } this.low_payment_fee=low_payment_fee;}
public void setReage_request(String reage_request) { sqlMaker.setField("reage_request",reage_request,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getReage_request().equals(reage_request)) cf.add("reage_request",this.reage_request,reage_request); } this.reage_request=reage_request;}
public void setLast_pymt_sw(String last_pymt_sw) { sqlMaker.setField("last_pymt_sw",last_pymt_sw,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_pymt_sw().equals(last_pymt_sw)) cf.add("last_pymt_sw",this.last_pymt_sw,last_pymt_sw); } this.last_pymt_sw=last_pymt_sw;}
public void setLast_pymt_aged_count(int last_pymt_aged_count) { sqlMaker.setField("last_pymt_aged_count",""+last_pymt_aged_count,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLast_pymt_aged_count()!=last_pymt_aged_count) cf.add("last_pymt_aged_count",this.last_pymt_aged_count+"",last_pymt_aged_count+""); } this.last_pymt_aged_count=last_pymt_aged_count;}
public void setConsume_dr_amt(double consume_dr_amt) { sqlMaker.setField("consume_dr_amt",""+consume_dr_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getConsume_dr_amt()!=consume_dr_amt) cf.add("consume_dr_amt",this.consume_dr_amt+"",consume_dr_amt+""); } this.consume_dr_amt=consume_dr_amt;}
public void setConsume_cr_amt(double consume_cr_amt) { sqlMaker.setField("consume_cr_amt",""+consume_cr_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getConsume_cr_amt()!=consume_cr_amt) cf.add("consume_cr_amt",this.consume_cr_amt+"",consume_cr_amt+""); } this.consume_cr_amt=consume_cr_amt;}
public void setConsume_bal(double consume_bal) { sqlMaker.setField("consume_bal",""+consume_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getConsume_bal()!=consume_bal) cf.add("consume_bal",this.consume_bal+"",consume_bal+""); } this.consume_bal=consume_bal;}
public void setInac_owe_consume_int(double inac_owe_consume_int) { sqlMaker.setField("inac_owe_consume_int",""+inac_owe_consume_int,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getInac_owe_consume_int()!=inac_owe_consume_int) cf.add("inac_owe_consume_int",this.inac_owe_consume_int+"",inac_owe_consume_int+""); } this.inac_owe_consume_int=inac_owe_consume_int;}
public void setRtl_svc_bnp(double rtl_svc_bnp) { sqlMaker.setField("rtl_svc_bnp",""+rtl_svc_bnp,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_svc_bnp()!=rtl_svc_bnp) cf.add("rtl_svc_bnp",this.rtl_svc_bnp+"",rtl_svc_bnp+""); } this.rtl_svc_bnp=rtl_svc_bnp;}
public void setInac_owe_misc_fee(double inac_owe_misc_fee) { sqlMaker.setField("inac_owe_misc_fee",""+inac_owe_misc_fee,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getInac_owe_misc_fee()!=inac_owe_misc_fee) cf.add("inac_owe_misc_fee",this.inac_owe_misc_fee+"",inac_owe_misc_fee+""); } this.inac_owe_misc_fee=inac_owe_misc_fee;}
public void setRtl_insur_bnp(double rtl_insur_bnp) { sqlMaker.setField("rtl_insur_bnp",""+rtl_insur_bnp,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_insur_bnp()!=rtl_insur_bnp) cf.add("rtl_insur_bnp",this.rtl_insur_bnp+"",rtl_insur_bnp+""); } this.rtl_insur_bnp=rtl_insur_bnp;}
public void setInac_owe_anfe(double inac_owe_anfe) { sqlMaker.setField("inac_owe_anfe",""+inac_owe_anfe,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getInac_owe_anfe()!=inac_owe_anfe) cf.add("inac_owe_anfe",this.inac_owe_anfe+"",inac_owe_anfe+""); } this.inac_owe_anfe=inac_owe_anfe;}
public void setStmt_period_cash_misc_fee(double stmt_period_cash_misc_fee) { sqlMaker.setField("stmt_period_cash_misc_fee",""+stmt_period_cash_misc_fee,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getStmt_period_cash_misc_fee()!=stmt_period_cash_misc_fee) cf.add("stmt_period_cash_misc_fee",this.stmt_period_cash_misc_fee+"",stmt_period_cash_misc_fee+""); } this.stmt_period_cash_misc_fee=stmt_period_cash_misc_fee;}
public void setConsume_argu_amt(double consume_argu_amt) { sqlMaker.setField("consume_argu_amt",""+consume_argu_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getConsume_argu_amt()!=consume_argu_amt) cf.add("consume_argu_amt",this.consume_argu_amt+"",consume_argu_amt+""); } this.consume_argu_amt=consume_argu_amt;}
public void setCurr_consume_bal(double curr_consume_bal) { sqlMaker.setField("curr_consume_bal",""+curr_consume_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCurr_consume_bal()!=curr_consume_bal) cf.add("curr_consume_bal",this.curr_consume_bal+"",curr_consume_bal+""); } this.curr_consume_bal=curr_consume_bal;}
public void setConsume_accum_int(double consume_accum_int) { sqlMaker.setField("consume_accum_int",""+consume_accum_int,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getConsume_accum_int()!=consume_accum_int) cf.add("consume_accum_int",this.consume_accum_int+"",consume_accum_int+""); } this.consume_accum_int=consume_accum_int;}
public void setConsume_dr_cnt(int consume_dr_cnt) { sqlMaker.setField("consume_dr_cnt",""+consume_dr_cnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getConsume_dr_cnt()!=consume_dr_cnt) cf.add("consume_dr_cnt",this.consume_dr_cnt+"",consume_dr_cnt+""); } this.consume_dr_cnt=consume_dr_cnt;}
public void setConsume_cr_cnt(int consume_cr_cnt) { sqlMaker.setField("consume_cr_cnt",""+consume_cr_cnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getConsume_cr_cnt()!=consume_cr_cnt) cf.add("consume_cr_cnt",this.consume_cr_cnt+"",consume_cr_cnt+""); } this.consume_cr_cnt=consume_cr_cnt;}
public void setStmt_period_consume_repay(double stmt_period_consume_repay) { sqlMaker.setField("stmt_period_consume_repay",""+stmt_period_consume_repay,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getStmt_period_consume_repay()!=stmt_period_consume_repay) cf.add("stmt_period_consume_repay",this.stmt_period_consume_repay+"",stmt_period_consume_repay+""); } this.stmt_period_consume_repay=stmt_period_consume_repay;}
public void setYtd_inac_consume_int(double ytd_inac_consume_int) { sqlMaker.setField("ytd_inac_consume_int",""+ytd_inac_consume_int,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getYtd_inac_consume_int()!=ytd_inac_consume_int) cf.add("ytd_inac_consume_int",this.ytd_inac_consume_int+"",ytd_inac_consume_int+""); } this.ytd_inac_consume_int=ytd_inac_consume_int;}
public void setRtl_ibnp_lst_stmt(double rtl_ibnp_lst_stmt) { sqlMaker.setField("rtl_ibnp_lst_stmt",""+rtl_ibnp_lst_stmt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_ibnp_lst_stmt()!=rtl_ibnp_lst_stmt) cf.add("rtl_ibnp_lst_stmt",this.rtl_ibnp_lst_stmt+"",rtl_ibnp_lst_stmt+""); } this.rtl_ibnp_lst_stmt=rtl_ibnp_lst_stmt;}
public void setYtd_pay_consume_int(double ytd_pay_consume_int) { sqlMaker.setField("ytd_pay_consume_int",""+ytd_pay_consume_int,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getYtd_pay_consume_int()!=ytd_pay_consume_int) cf.add("ytd_pay_consume_int",this.ytd_pay_consume_int+"",ytd_pay_consume_int+""); } this.ytd_pay_consume_int=ytd_pay_consume_int;}
public void setRtl_lst_ytd_intr_paid(double rtl_lst_ytd_intr_paid) { sqlMaker.setField("rtl_lst_ytd_intr_paid",""+rtl_lst_ytd_intr_paid,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_lst_ytd_intr_paid()!=rtl_lst_ytd_intr_paid) cf.add("rtl_lst_ytd_intr_paid",this.rtl_lst_ytd_intr_paid+"",rtl_lst_ytd_intr_paid+""); } this.rtl_lst_ytd_intr_paid=rtl_lst_ytd_intr_paid;}
public void setRtl_amnt_pymt_rev(double rtl_amnt_pymt_rev) { sqlMaker.setField("rtl_amnt_pymt_rev",""+rtl_amnt_pymt_rev,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_amnt_pymt_rev()!=rtl_amnt_pymt_rev) cf.add("rtl_amnt_pymt_rev",this.rtl_amnt_pymt_rev+"",rtl_amnt_pymt_rev+""); } this.rtl_amnt_pymt_rev=rtl_amnt_pymt_rev;}
public void setRtl_std_intr(double rtl_std_intr) { sqlMaker.setField("rtl_std_intr",""+rtl_std_intr,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_std_intr()!=rtl_std_intr) cf.add("rtl_std_intr",this.rtl_std_intr+"",rtl_std_intr+""); } this.rtl_std_intr=rtl_std_intr;}
public void setRtl_provis_bal(double rtl_provis_bal) { sqlMaker.setField("rtl_provis_bal",""+rtl_provis_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_provis_bal()!=rtl_provis_bal) cf.add("rtl_provis_bal",this.rtl_provis_bal+"",rtl_provis_bal+""); } this.rtl_provis_bal=rtl_provis_bal;}
public void setRtl_provis_intr(double rtl_provis_intr) { sqlMaker.setField("rtl_provis_intr",""+rtl_provis_intr,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_provis_intr()!=rtl_provis_intr) cf.add("rtl_provis_intr",this.rtl_provis_intr+"",rtl_provis_intr+""); } this.rtl_provis_intr=rtl_provis_intr;}
public void setCash_dr_amt(double cash_dr_amt) { sqlMaker.setField("cash_dr_amt",""+cash_dr_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_dr_amt()!=cash_dr_amt) cf.add("cash_dr_amt",this.cash_dr_amt+"",cash_dr_amt+""); } this.cash_dr_amt=cash_dr_amt;}
public void setCash_cr_amt(double cash_cr_amt) { sqlMaker.setField("cash_cr_amt",""+cash_cr_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_cr_amt()!=cash_cr_amt) cf.add("cash_cr_amt",this.cash_cr_amt+"",cash_cr_amt+""); } this.cash_cr_amt=cash_cr_amt;}
public void setCash_bal(double cash_bal) { sqlMaker.setField("cash_bal",""+cash_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_bal()!=cash_bal) cf.add("cash_bal",this.cash_bal+"",cash_bal+""); } this.cash_bal=cash_bal;}
public void setInac_owe_cash_int(double inac_owe_cash_int) { sqlMaker.setField("inac_owe_cash_int",""+inac_owe_cash_int,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getInac_owe_cash_int()!=inac_owe_cash_int) cf.add("inac_owe_cash_int",this.inac_owe_cash_int+"",inac_owe_cash_int+""); } this.inac_owe_cash_int=inac_owe_cash_int;}
public void setInac_owe_cash_svc(double inac_owe_cash_svc) { sqlMaker.setField("inac_owe_cash_svc",""+inac_owe_cash_svc,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getInac_owe_cash_svc()!=inac_owe_cash_svc) cf.add("inac_owe_cash_svc",this.inac_owe_cash_svc+"",inac_owe_cash_svc+""); } this.inac_owe_cash_svc=inac_owe_cash_svc;}
public void setCash_dissent_bal(double cash_dissent_bal) { sqlMaker.setField("cash_dissent_bal",""+cash_dissent_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_dissent_bal()!=cash_dissent_bal) cf.add("cash_dissent_bal",this.cash_dissent_bal+"",cash_dissent_bal+""); } this.cash_dissent_bal=cash_dissent_bal;}
public void setCurr_cash_bal(double curr_cash_bal) { sqlMaker.setField("curr_cash_bal",""+curr_cash_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCurr_cash_bal()!=curr_cash_bal) cf.add("curr_cash_bal",this.curr_cash_bal+"",curr_cash_bal+""); } this.curr_cash_bal=curr_cash_bal;}
public void setCash_accum_int(double cash_accum_int) { sqlMaker.setField("cash_accum_int",""+cash_accum_int,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_accum_int()!=cash_accum_int) cf.add("cash_accum_int",this.cash_accum_int+"",cash_accum_int+""); } this.cash_accum_int=cash_accum_int;}
public void setCash_dr_cnt(int cash_dr_cnt) { sqlMaker.setField("cash_dr_cnt",""+cash_dr_cnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_dr_cnt()!=cash_dr_cnt) cf.add("cash_dr_cnt",this.cash_dr_cnt+"",cash_dr_cnt+""); } this.cash_dr_cnt=cash_dr_cnt;}
public void setCash_cr_cnt(int cash_cr_cnt) { sqlMaker.setField("cash_cr_cnt",""+cash_cr_cnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_cr_cnt()!=cash_cr_cnt) cf.add("cash_cr_cnt",this.cash_cr_cnt+"",cash_cr_cnt+""); } this.cash_cr_cnt=cash_cr_cnt;}
public void setStmt_period_cash_repay(double stmt_period_cash_repay) { sqlMaker.setField("stmt_period_cash_repay",""+stmt_period_cash_repay,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getStmt_period_cash_repay()!=stmt_period_cash_repay) cf.add("stmt_period_cash_repay",this.stmt_period_cash_repay+"",stmt_period_cash_repay+""); } this.stmt_period_cash_repay=stmt_period_cash_repay;}
public void setYtd_inac_cash_int(double ytd_inac_cash_int) { sqlMaker.setField("ytd_inac_cash_int",""+ytd_inac_cash_int,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getYtd_inac_cash_int()!=ytd_inac_cash_int) cf.add("ytd_inac_cash_int",this.ytd_inac_cash_int+"",ytd_inac_cash_int+""); } this.ytd_inac_cash_int=ytd_inac_cash_int;}
public void setCash_ibnp_lst_stmt(double cash_ibnp_lst_stmt) { sqlMaker.setField("cash_ibnp_lst_stmt",""+cash_ibnp_lst_stmt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_ibnp_lst_stmt()!=cash_ibnp_lst_stmt) cf.add("cash_ibnp_lst_stmt",this.cash_ibnp_lst_stmt+"",cash_ibnp_lst_stmt+""); } this.cash_ibnp_lst_stmt=cash_ibnp_lst_stmt;}
public void setCash_ytd_intr_paid(double cash_ytd_intr_paid) { sqlMaker.setField("cash_ytd_intr_paid",""+cash_ytd_intr_paid,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_ytd_intr_paid()!=cash_ytd_intr_paid) cf.add("cash_ytd_intr_paid",this.cash_ytd_intr_paid+"",cash_ytd_intr_paid+""); } this.cash_ytd_intr_paid=cash_ytd_intr_paid;}
public void setCash_lst_ytd_intr_paid(double cash_lst_ytd_intr_paid) { sqlMaker.setField("cash_lst_ytd_intr_paid",""+cash_lst_ytd_intr_paid,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_lst_ytd_intr_paid()!=cash_lst_ytd_intr_paid) cf.add("cash_lst_ytd_intr_paid",this.cash_lst_ytd_intr_paid+"",cash_lst_ytd_intr_paid+""); } this.cash_lst_ytd_intr_paid=cash_lst_ytd_intr_paid;}
public void setCash_amnt_pymt_rev(double cash_amnt_pymt_rev) { sqlMaker.setField("cash_amnt_pymt_rev",""+cash_amnt_pymt_rev,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_amnt_pymt_rev()!=cash_amnt_pymt_rev) cf.add("cash_amnt_pymt_rev",this.cash_amnt_pymt_rev+"",cash_amnt_pymt_rev+""); } this.cash_amnt_pymt_rev=cash_amnt_pymt_rev;}
public void setCash_std_intr(double cash_std_intr) { sqlMaker.setField("cash_std_intr",""+cash_std_intr,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_std_intr()!=cash_std_intr) cf.add("cash_std_intr",this.cash_std_intr+"",cash_std_intr+""); } this.cash_std_intr=cash_std_intr;}
public void setRtl_neg_provis_intr(double rtl_neg_provis_intr) { sqlMaker.setField("rtl_neg_provis_intr",""+rtl_neg_provis_intr,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_neg_provis_intr()!=rtl_neg_provis_intr) cf.add("rtl_neg_provis_intr",this.rtl_neg_provis_intr+"",rtl_neg_provis_intr+""); } this.rtl_neg_provis_intr=rtl_neg_provis_intr;}
public void setRtl_neg_antici_intr(double rtl_neg_antici_intr) { sqlMaker.setField("rtl_neg_antici_intr",""+rtl_neg_antici_intr,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_neg_antici_intr()!=rtl_neg_antici_intr) cf.add("rtl_neg_antici_intr",this.rtl_neg_antici_intr+"",rtl_neg_antici_intr+""); } this.rtl_neg_antici_intr=rtl_neg_antici_intr;}
public void setCurr_bal(double curr_bal) { sqlMaker.setField("curr_bal",""+curr_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCurr_bal()!=curr_bal) cf.add("curr_bal",this.curr_bal+"",curr_bal+""); } this.curr_bal=curr_bal;}
public void setOutst_auth_amt(double outst_auth_amt) { sqlMaker.setField("outst_auth_amt",""+outst_auth_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOutst_auth_amt()!=outst_auth_amt) cf.add("outst_auth_amt",this.outst_auth_amt+"",outst_auth_amt+""); } this.outst_auth_amt=outst_auth_amt;}
public void setOutst_auth_cnt(int outst_auth_cnt) { sqlMaker.setField("outst_auth_cnt",""+outst_auth_cnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOutst_auth_cnt()!=outst_auth_cnt) cf.add("outst_auth_cnt",this.outst_auth_cnt+"",outst_auth_cnt+""); } this.outst_auth_cnt=outst_auth_cnt;}
public void setAvail_crlmt(double avail_crlmt) { sqlMaker.setField("avail_crlmt",""+avail_crlmt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAvail_crlmt()!=avail_crlmt) cf.add("avail_crlmt",this.avail_crlmt+"",avail_crlmt+""); } this.avail_crlmt=avail_crlmt;}
public void setCsh_adv_lim(double csh_adv_lim) { sqlMaker.setField("csh_adv_lim",""+csh_adv_lim,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCsh_adv_lim()!=csh_adv_lim) cf.add("csh_adv_lim",this.csh_adv_lim+"",csh_adv_lim+""); } this.csh_adv_lim=csh_adv_lim;}
public void setCsh_adv_avail(double csh_adv_avail) { sqlMaker.setField("csh_adv_avail",""+csh_adv_avail,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCsh_adv_avail()!=csh_adv_avail) cf.add("csh_adv_avail",this.csh_adv_avail+"",csh_adv_avail+""); } this.csh_adv_avail=csh_adv_avail;}
public void setGrace_days(int grace_days) { sqlMaker.setField("grace_days",""+grace_days,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getGrace_days()!=grace_days) cf.add("grace_days",this.grace_days+"",grace_days+""); } this.grace_days=grace_days;}
public void setLst_ytd_intr_billed(double lst_ytd_intr_billed) { sqlMaker.setField("lst_ytd_intr_billed",""+lst_ytd_intr_billed,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLst_ytd_intr_billed()!=lst_ytd_intr_billed) cf.add("lst_ytd_intr_billed",this.lst_ytd_intr_billed+"",lst_ytd_intr_billed+""); } this.lst_ytd_intr_billed=lst_ytd_intr_billed;}
public void setYtd_inac_service_fee(double ytd_inac_service_fee) { sqlMaker.setField("ytd_inac_service_fee",""+ytd_inac_service_fee,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getYtd_inac_service_fee()!=ytd_inac_service_fee) cf.add("ytd_inac_service_fee",this.ytd_inac_service_fee+"",ytd_inac_service_fee+""); } this.ytd_inac_service_fee=ytd_inac_service_fee;}
public void setDisput_count(int disput_count) { sqlMaker.setField("disput_count",""+disput_count,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getDisput_count()!=disput_count) cf.add("disput_count",this.disput_count+"",disput_count+""); } this.disput_count=disput_count;}
public void setDisput_fee(double disput_fee) { sqlMaker.setField("disput_fee",""+disput_fee,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getDisput_fee()!=disput_fee) cf.add("disput_fee",this.disput_fee+"",disput_fee+""); } this.disput_fee=disput_fee;}
public void setConsume_apcl_rate(double consume_apcl_rate) { sqlMaker.setField("consume_apcl_rate",""+consume_apcl_rate,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getConsume_apcl_rate()!=consume_apcl_rate) cf.add("consume_apcl_rate",this.consume_apcl_rate+"",consume_apcl_rate+""); } this.consume_apcl_rate=consume_apcl_rate;}
public void setCash_apcl_amt(double cash_apcl_amt) { sqlMaker.setField("cash_apcl_amt",""+cash_apcl_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_apcl_amt()!=cash_apcl_amt) cf.add("cash_apcl_amt",this.cash_apcl_amt+"",cash_apcl_amt+""); } this.cash_apcl_amt=cash_apcl_amt;}
public void setLst_pymt_amnt(double lst_pymt_amnt) { sqlMaker.setField("lst_pymt_amnt",""+lst_pymt_amnt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLst_pymt_amnt()!=lst_pymt_amnt) cf.add("lst_pymt_amnt",this.lst_pymt_amnt+"",lst_pymt_amnt+""); } this.lst_pymt_amnt=lst_pymt_amnt;}
public void setAggr_cash_days(int aggr_cash_days) { sqlMaker.setField("aggr_cash_days",""+aggr_cash_days,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAggr_cash_days()!=aggr_cash_days) cf.add("aggr_cash_days",this.aggr_cash_days+"",aggr_cash_days+""); } this.aggr_cash_days=aggr_cash_days;}
public void setAggr_rtl_days(int aggr_rtl_days) { sqlMaker.setField("aggr_rtl_days",""+aggr_rtl_days,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAggr_rtl_days()!=aggr_rtl_days) cf.add("aggr_rtl_days",this.aggr_rtl_days+"",aggr_rtl_days+""); } this.aggr_rtl_days=aggr_rtl_days;}
public void setDl_amt(double dl_amt) { sqlMaker.setField("dl_amt",""+dl_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getDl_amt()!=dl_amt) cf.add("dl_amt",this.dl_amt+"",dl_amt+""); } this.dl_amt=dl_amt;}
public void setAggr_ytd_days(int aggr_ytd_days) { sqlMaker.setField("aggr_ytd_days",""+aggr_ytd_days,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAggr_ytd_days()!=aggr_ytd_days) cf.add("aggr_ytd_days",this.aggr_ytd_days+"",aggr_ytd_days+""); } this.aggr_ytd_days=aggr_ytd_days;}
public void setAggr_ytd_balance(double aggr_ytd_balance) { sqlMaker.setField("aggr_ytd_balance",""+aggr_ytd_balance,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAggr_ytd_balance()!=aggr_ytd_balance) cf.add("aggr_ytd_balance",this.aggr_ytd_balance+"",aggr_ytd_balance+""); } this.aggr_ytd_balance=aggr_ytd_balance;}
public void setAggr_lst_ytd_days(int aggr_lst_ytd_days) { sqlMaker.setField("aggr_lst_ytd_days",""+aggr_lst_ytd_days,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAggr_lst_ytd_days()!=aggr_lst_ytd_days) cf.add("aggr_lst_ytd_days",this.aggr_lst_ytd_days+"",aggr_lst_ytd_days+""); } this.aggr_lst_ytd_days=aggr_lst_ytd_days;}
public void setAggr_lst_ytd_balance(double aggr_lst_ytd_balance) { sqlMaker.setField("aggr_lst_ytd_balance",""+aggr_lst_ytd_balance,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAggr_lst_ytd_balance()!=aggr_lst_ytd_balance) cf.add("aggr_lst_ytd_balance",this.aggr_lst_ytd_balance+"",aggr_lst_ytd_balance+""); } this.aggr_lst_ytd_balance=aggr_lst_ytd_balance;}
public void setCi_flag(String ci_flag) { sqlMaker.setField("ci_flag",ci_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCi_flag().equals(ci_flag)) cf.add("ci_flag",this.ci_flag,ci_flag); } this.ci_flag=ci_flag;}
public void setRi_flag(String ri_flag) { sqlMaker.setField("ri_flag",ri_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getRi_flag().equals(ri_flag)) cf.add("ri_flag",this.ri_flag,ri_flag); } this.ri_flag=ri_flag;}
public void setCash_adv_per_fee_ctd(double cash_adv_per_fee_ctd) { sqlMaker.setField("cash_adv_per_fee_ctd",""+cash_adv_per_fee_ctd,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_adv_per_fee_ctd()!=cash_adv_per_fee_ctd) cf.add("cash_adv_per_fee_ctd",this.cash_adv_per_fee_ctd+"",cash_adv_per_fee_ctd+""); } this.cash_adv_per_fee_ctd=cash_adv_per_fee_ctd;}
public void setColl_block_code(String coll_block_code) { sqlMaker.setField("coll_block_code",coll_block_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getColl_block_code().equals(coll_block_code)) cf.add("coll_block_code",this.coll_block_code,coll_block_code); } this.coll_block_code=coll_block_code;}
public void setInstl_limit(double instl_limit) { sqlMaker.setField("instl_limit",""+instl_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getInstl_limit()!=instl_limit) cf.add("instl_limit",this.instl_limit+"",instl_limit+""); } this.instl_limit=instl_limit;}
public void setPart_bal(double part_bal) { sqlMaker.setField("part_bal",""+part_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getPart_bal()!=part_bal) cf.add("part_bal",this.part_bal+"",part_bal+""); } this.part_bal=part_bal;}
public void setOutst_instl_amt(double outst_instl_amt) { sqlMaker.setField("outst_instl_amt",""+outst_instl_amt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOutst_instl_amt()!=outst_instl_amt) cf.add("outst_instl_amt",this.outst_instl_amt+"",outst_instl_amt+""); } this.outst_instl_amt=outst_instl_amt;}
public void setUse_instl_limit(double use_instl_limit) { sqlMaker.setField("use_instl_limit",""+use_instl_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getUse_instl_limit()!=use_instl_limit) cf.add("use_instl_limit",this.use_instl_limit+"",use_instl_limit+""); } this.use_instl_limit=use_instl_limit;}
public void setTcm_grace_days(String tcm_grace_days) { sqlMaker.setField("tcm_grace_days",tcm_grace_days,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTcm_grace_days().equals(tcm_grace_days)) cf.add("tcm_grace_days",this.tcm_grace_days,tcm_grace_days); } this.tcm_grace_days=tcm_grace_days;}
public void setPerm_credit_limit(double perm_credit_limit) { sqlMaker.setField("perm_credit_limit",""+perm_credit_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getPerm_credit_limit()!=perm_credit_limit) cf.add("perm_credit_limit",this.perm_credit_limit+"",perm_credit_limit+""); } this.perm_credit_limit=perm_credit_limit;}
public void setTemp_credit_limit(double temp_credit_limit) { sqlMaker.setField("temp_credit_limit",""+temp_credit_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getTemp_credit_limit()!=temp_credit_limit) cf.add("temp_credit_limit",this.temp_credit_limit+"",temp_credit_limit+""); } this.temp_credit_limit=temp_credit_limit;}
public void setCrlimit_temp_eff_dte(String crlimit_temp_eff_dte) { sqlMaker.setField("crlimit_temp_eff_dte",crlimit_temp_eff_dte,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrlimit_temp_eff_dte().equals(crlimit_temp_eff_dte)) cf.add("crlimit_temp_eff_dte",this.crlimit_temp_eff_dte,crlimit_temp_eff_dte); } this.crlimit_temp_eff_dte=crlimit_temp_eff_dte;}
public void setCrlimit_temp_exp_dte(String crlimit_temp_exp_dte) { sqlMaker.setField("crlimit_temp_exp_dte",crlimit_temp_exp_dte,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCrlimit_temp_exp_dte().equals(crlimit_temp_exp_dte)) cf.add("crlimit_temp_exp_dte",this.crlimit_temp_exp_dte,crlimit_temp_exp_dte); } this.crlimit_temp_exp_dte=crlimit_temp_exp_dte;}
public void setCash_limit(double cash_limit) { sqlMaker.setField("cash_limit",""+cash_limit,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_limit()!=cash_limit) cf.add("cash_limit",this.cash_limit+"",cash_limit+""); } this.cash_limit=cash_limit;}
public void setAvl_cash_bal(double avl_cash_bal) { sqlMaker.setField("avl_cash_bal",""+avl_cash_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getAvl_cash_bal()!=avl_cash_bal) cf.add("avl_cash_bal",this.avl_cash_bal+"",avl_cash_bal+""); } this.avl_cash_bal=avl_cash_bal;}
public void setCash_adv_os_auth(int cash_adv_os_auth) { sqlMaker.setField("cash_adv_os_auth",""+cash_adv_os_auth,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_adv_os_auth()!=cash_adv_os_auth) cf.add("cash_adv_os_auth",this.cash_adv_os_auth+"",cash_adv_os_auth+""); } this.cash_adv_os_auth=cash_adv_os_auth;}
public void setDte_lst_cash_auth(String dte_lst_cash_auth) { sqlMaker.setField("dte_lst_cash_auth",dte_lst_cash_auth,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getDte_lst_cash_auth().equals(dte_lst_cash_auth)) cf.add("dte_lst_cash_auth",this.dte_lst_cash_auth,dte_lst_cash_auth); } this.dte_lst_cash_auth=dte_lst_cash_auth;}
public void setCash_cr_adjust_bnp(double cash_cr_adjust_bnp) { sqlMaker.setField("cash_cr_adjust_bnp",""+cash_cr_adjust_bnp,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCash_cr_adjust_bnp()!=cash_cr_adjust_bnp) cf.add("cash_cr_adjust_bnp",this.cash_cr_adjust_bnp+"",cash_cr_adjust_bnp+""); } this.cash_cr_adjust_bnp=cash_cr_adjust_bnp;}
public void setRtl_cr_adjust_bnp(double rtl_cr_adjust_bnp) { sqlMaker.setField("rtl_cr_adjust_bnp",""+rtl_cr_adjust_bnp,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getRtl_cr_adjust_bnp()!=rtl_cr_adjust_bnp) cf.add("rtl_cr_adjust_bnp",this.rtl_cr_adjust_bnp+"",rtl_cr_adjust_bnp+""); } this.rtl_cr_adjust_bnp=rtl_cr_adjust_bnp;}
public void setOl_cash_pymt(double ol_cash_pymt) { sqlMaker.setField("ol_cash_pymt",""+ol_cash_pymt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOl_cash_pymt()!=ol_cash_pymt) cf.add("ol_cash_pymt",this.ol_cash_pymt+"",ol_cash_pymt+""); } this.ol_cash_pymt=ol_cash_pymt;}
public void setOl_rtl_pymt(double ol_rtl_pymt) { sqlMaker.setField("ol_rtl_pymt",""+ol_rtl_pymt,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getOl_rtl_pymt()!=ol_rtl_pymt) cf.add("ol_rtl_pymt",this.ol_rtl_pymt+"",ol_rtl_pymt+""); } this.ol_rtl_pymt=ol_rtl_pymt;}
public void setLast_repay_dt_due(String last_repay_dt_due) { sqlMaker.setField("last_repay_dt_due",last_repay_dt_due,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_repay_dt_due().equals(last_repay_dt_due)) cf.add("last_repay_dt_due",this.last_repay_dt_due,last_repay_dt_due); } this.last_repay_dt_due=last_repay_dt_due;}
public void setSrc_cd(String src_cd) { sqlMaker.setField("src_cd",src_cd,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSrc_cd().equals(src_cd)) cf.add("src_cd",this.src_cd,src_cd); } this.src_cd=src_cd;}
public void setLast_pri_block_code(String last_pri_block_code) { sqlMaker.setField("last_pri_block_code",last_pri_block_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLast_pri_block_code().equals(last_pri_block_code)) cf.add("last_pri_block_code",this.last_pri_block_code,last_pri_block_code); } this.last_pri_block_code=last_pri_block_code;}
public void setLast_processing_bal(double last_processing_bal) { sqlMaker.setField("last_processing_bal",""+last_processing_bal,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getLast_processing_bal()!=last_processing_bal) cf.add("last_processing_bal",this.last_processing_bal+"",last_processing_bal+""); } this.last_processing_bal=last_processing_bal;}
public void setPymt_bfr_grace_ctd(double pymt_bfr_grace_ctd) { sqlMaker.setField("pymt_bfr_grace_ctd",""+pymt_bfr_grace_ctd,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getPymt_bfr_grace_ctd()!=pymt_bfr_grace_ctd) cf.add("pymt_bfr_grace_ctd",this.pymt_bfr_grace_ctd+"",pymt_bfr_grace_ctd+""); } this.pymt_bfr_grace_ctd=pymt_bfr_grace_ctd;}
public void setAcct_ledger_sts(String acct_ledger_sts) { sqlMaker.setField("acct_ledger_sts",acct_ledger_sts,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAcct_ledger_sts().equals(acct_ledger_sts)) cf.add("acct_ledger_sts",this.acct_ledger_sts,acct_ledger_sts); } this.acct_ledger_sts=acct_ledger_sts;}
public void setShort_name(String short_name) { sqlMaker.setField("short_name",short_name,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getShort_name().equals(short_name)) cf.add("short_name",this.short_name,short_name); } this.short_name=short_name;}
public void setAlpha_key(String alpha_key) { sqlMaker.setField("alpha_key",alpha_key,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getAlpha_key().equals(alpha_key)) cf.add("alpha_key",this.alpha_key,alpha_key); } this.alpha_key=alpha_key;}
public void setEngage_rfn_acct_no_new(String engage_rfn_acct_no_new) { sqlMaker.setField("engage_rfn_acct_no_new",engage_rfn_acct_no_new,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEngage_rfn_acct_no_new().equals(engage_rfn_acct_no_new)) cf.add("engage_rfn_acct_no_new",this.engage_rfn_acct_no_new,engage_rfn_acct_no_new); } this.engage_rfn_acct_no_new=engage_rfn_acct_no_new;}
public void setCr_census_tract(String cr_census_tract) { sqlMaker.setField("cr_census_tract",cr_census_tract,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCr_census_tract().equals(cr_census_tract)) cf.add("cr_census_tract",this.cr_census_tract,cr_census_tract); } this.cr_census_tract=cr_census_tract;}
public void setLn_prod_cod(String ln_prod_cod) { sqlMaker.setField("ln_prod_cod",ln_prod_cod,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getLn_prod_cod().equals(ln_prod_cod)) cf.add("ln_prod_cod",this.ln_prod_cod,ln_prod_cod); } this.ln_prod_cod=ln_prod_cod;}
public void setOds_load_dt(String ods_load_dt) { sqlMaker.setField("ods_load_dt",ods_load_dt,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getOds_load_dt().equals(ods_load_dt)) cf.add("ods_load_dt",this.ods_load_dt,ods_load_dt); } this.ods_load_dt=ods_load_dt;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"org_no") !=null ) {this.setOrg_no(actionRequest.getFieldValue(i,"org_no"));}
if ( actionRequest.getFieldValue(i,"actp") !=null ) {this.setActp(actionRequest.getFieldValue(i,"actp"));}
if ( actionRequest.getFieldValue(i,"acct_no") !=null ) {this.setAcct_no(actionRequest.getFieldValue(i,"acct_no"));}
if ( actionRequest.getFieldValue(i,"ods_src_dt") !=null ) {this.setOds_src_dt(actionRequest.getFieldValue(i,"ods_src_dt"));}
if ( actionRequest.getFieldValue(i,"curr_cd") !=null ) {this.setCurr_cd(actionRequest.getFieldValue(i,"curr_cd"));}
if ( actionRequest.getFieldValue(i,"ochr_customer_org") !=null ) {this.setOchr_customer_org(actionRequest.getFieldValue(i,"ochr_customer_org"));}
if ( actionRequest.getFieldValue(i,"cust_no") !=null ) {this.setCust_no(actionRequest.getFieldValue(i,"cust_no"));}
if ( actionRequest.getFieldValue(i,"stmt_day") !=null ) {this.setStmt_day(actionRequest.getFieldValue(i,"stmt_day"));}
if ( actionRequest.getFieldValue(i,"xfr_org_nmbr") !=null ) {this.setXfr_org_nmbr(actionRequest.getFieldValue(i,"xfr_org_nmbr"));}
if ( actionRequest.getFieldValue(i,"xfr_type_nmbr") !=null ) {this.setXfr_type_nmbr(actionRequest.getFieldValue(i,"xfr_type_nmbr"));}
if ( actionRequest.getFieldValue(i,"xfr_acct_nmbr") !=null ) {this.setXfr_acct_nmbr(actionRequest.getFieldValue(i,"xfr_acct_nmbr"));}
if ( actionRequest.getFieldValue(i,"officer") !=null ) {this.setOfficer(actionRequest.getFieldValue(i,"officer"));}
if ( actionRequest.getFieldValue(i,"curr_collector") !=null ) {this.setCurr_collector(actionRequest.getFieldValue(i,"curr_collector"));}
if ( actionRequest.getFieldValue(i,"perm_collector") !=null ) {this.setPerm_collector(actionRequest.getFieldValue(i,"perm_collector"));}
if ( actionRequest.getFieldValue(i,"corp_acct_org") !=null ) {this.setCorp_acct_org(actionRequest.getFieldValue(i,"corp_acct_org"));}
if ( actionRequest.getFieldValue(i,"corp_acct_nmbr") !=null ) {this.setCorp_acct_nmbr(actionRequest.getFieldValue(i,"corp_acct_nmbr"));}
if ( actionRequest.getFieldValue(i,"collateral_code") !=null ) {this.setCollateral_code(actionRequest.getFieldValue(i,"collateral_code"));}
if ( actionRequest.getFieldValue(i,"sr_user_code_2") !=null ) {this.setSr_user_code_2(actionRequest.getFieldValue(i,"sr_user_code_2"));}
if ( actionRequest.getFieldValue(i,"user_code_3") !=null ) {this.setUser_code_3(actionRequest.getFieldValue(i,"user_code_3"));}
if ( actionRequest.getFieldValue(i,"block_code") !=null ) {this.setBlock_code(actionRequest.getFieldValue(i,"block_code"));}
if ( actionRequest.getFieldValue(i,"alt_block_code") !=null ) {this.setAlt_block_code(actionRequest.getFieldValue(i,"alt_block_code"));}
if ( actionRequest.getFieldValue(i,"acct_sts") !=null ) {this.setAcct_sts(actionRequest.getFieldValue(i,"acct_sts"));}
if ( actionRequest.getFieldValue(i,"of_late_fee_flg") !=null ) {this.setOf_late_fee_flg(actionRequest.getFieldValue(i,"of_late_fee_flg"));}
if ( actionRequest.getFieldValue(i,"of_ovlm_fee_flg") !=null ) {this.setOf_ovlm_fee_flg(actionRequest.getFieldValue(i,"of_ovlm_fee_flg"));}
if ( actionRequest.getFieldValue(i,"of_svc_flg") !=null ) {this.setOf_svc_flg(actionRequest.getFieldValue(i,"of_svc_flg"));}
if ( actionRequest.getFieldValue(i,"stat_chng_flag") !=null ) {this.setStat_chng_flag(actionRequest.getFieldValue(i,"stat_chng_flag"));}
if ( actionRequest.getFieldValue(i,"auth_point") !=null ) {this.setAuth_point(actionRequest.getFieldValue(i,"auth_point"));}
if ( actionRequest.getFieldValue(i,"sr_chgoff_status_flag") !=null ) {this.setSr_chgoff_status_flag(actionRequest.getFieldValue(i,"sr_chgoff_status_flag"));}
if ( actionRequest.getFieldValue(i,"acct_crd_op_dt") !=null ) {this.setAcct_crd_op_dt(actionRequest.getFieldValue(i,"acct_crd_op_dt"));}
if ( actionRequest.getFieldValue(i,"last_sts_chg_dt") !=null ) {this.setLast_sts_chg_dt(actionRequest.getFieldValue(i,"last_sts_chg_dt"));}
if ( actionRequest.getFieldValue(i,"acct_crd_en_dt") !=null ) {this.setAcct_crd_en_dt(actionRequest.getFieldValue(i,"acct_crd_en_dt"));}
if ( actionRequest.getFieldValue(i,"last_consume_dt") !=null ) {this.setLast_consume_dt(actionRequest.getFieldValue(i,"last_consume_dt"));}
if ( actionRequest.getFieldValue(i,"last_cash_dt") !=null ) {this.setLast_cash_dt(actionRequest.getFieldValue(i,"last_cash_dt"));}
if ( actionRequest.getFieldValue(i,"dte_lst_delq") !=null ) {this.setDte_lst_delq(actionRequest.getFieldValue(i,"dte_lst_delq"));}
if ( actionRequest.getFieldValue(i,"dte_prior_delq") !=null ) {this.setDte_prior_delq(actionRequest.getFieldValue(i,"dte_prior_delq"));}
if ( actionRequest.getFieldValue(i,"cm_dte_lst_active") !=null ) {this.setCm_dte_lst_active(actionRequest.getFieldValue(i,"cm_dte_lst_active"));}
if ( actionRequest.getFieldValue(i,"last_cahs_repay_dt") !=null ) {this.setLast_cahs_repay_dt(actionRequest.getFieldValue(i,"last_cahs_repay_dt"));}
if ( actionRequest.getFieldValue(i,"last_consume_repay_dt") !=null ) {this.setLast_consume_repay_dt(actionRequest.getFieldValue(i,"last_consume_repay_dt"));}
if ( actionRequest.getFieldValue(i,"last_chg_crlim_dt") !=null ) {this.setLast_chg_crlim_dt(actionRequest.getFieldValue(i,"last_chg_crlim_dt"));}
if ( actionRequest.getFieldValue(i,"lst_crlimit_dte") !=null ) {this.setLst_crlimit_dte(actionRequest.getFieldValue(i,"lst_crlimit_dte"));}
if ( actionRequest.getFieldValue(i,"sr_dte_lst_stmt") !=null ) {this.setSr_dte_lst_stmt(actionRequest.getFieldValue(i,"sr_dte_lst_stmt"));}
if ( actionRequest.getFieldValue(i,"dte_nxt_stmt") !=null ) {this.setDte_nxt_stmt(actionRequest.getFieldValue(i,"dte_nxt_stmt"));}
if ( actionRequest.getFieldValue(i,"dte_pymt_due") !=null ) {this.setDte_pymt_due(actionRequest.getFieldValue(i,"dte_pymt_due"));}
if ( actionRequest.getFieldValue(i,"dte_hi_balance") !=null ) {this.setDte_hi_balance(actionRequest.getFieldValue(i,"dte_hi_balance"));}
if ( actionRequest.getFieldValue(i,"last_repay_dt") !=null ) {this.setLast_repay_dt(actionRequest.getFieldValue(i,"last_repay_dt"));}
if ( actionRequest.getFieldValue(i,"dte_cr_balance") !=null ) {this.setDte_cr_balance(actionRequest.getFieldValue(i,"dte_cr_balance"));}
if ( actionRequest.getFieldValue(i,"cm_dte_lst_maint") !=null ) {this.setCm_dte_lst_maint(actionRequest.getFieldValue(i,"cm_dte_lst_maint"));}
if ( actionRequest.getFieldValue(i,"dte_lst_rate_chg") !=null ) {this.setDte_lst_rate_chg(actionRequest.getFieldValue(i,"dte_lst_rate_chg"));}
if ( actionRequest.getFieldValue(i,"dte_into_collection") !=null ) {this.setDte_into_collection(actionRequest.getFieldValue(i,"dte_into_collection"));}
if ( actionRequest.getFieldValue(i,"birth_dt") !=null ) {this.setBirth_dt(actionRequest.getFieldValue(i,"birth_dt"));}
if ( actionRequest.getFieldValue(i,"trn_crd_eff_dt") !=null ) {this.setTrn_crd_eff_dt(actionRequest.getFieldValue(i,"trn_crd_eff_dt"));}
if ( actionRequest.getFieldValue(i,"last_fee_dt") !=null ) {this.setLast_fee_dt(actionRequest.getFieldValue(i,"last_fee_dt"));}
if ( actionRequest.getFieldValue(i,"chgoff_sts_chg_dt") !=null ) {this.setChgoff_sts_chg_dt(actionRequest.getFieldValue(i,"chgoff_sts_chg_dt"));}
if ( actionRequest.getFieldValue(i,"dte_cr_bal_range") !=null ) {this.setDte_cr_bal_range(actionRequest.getFieldValue(i,"dte_cr_bal_range"));}
if ( actionRequest.getFieldValue(i,"block_dt") !=null ) {this.setBlock_dt(actionRequest.getFieldValue(i,"block_dt"));}
if ( actionRequest.getFieldValue(i,"dte_alt_block_code") !=null ) {this.setDte_alt_block_code(actionRequest.getFieldValue(i,"dte_alt_block_code"));}
if ( actionRequest.getFieldValue(i,"dte_filler_2") !=null ) {this.setDte_filler_2(actionRequest.getFieldValue(i,"dte_filler_2"));}
if ( actionRequest.getFieldValue(i,"dte_filler_1") !=null ) {this.setDte_filler_1(actionRequest.getFieldValue(i,"dte_filler_1"));}
if ( actionRequest.getFieldValue(i,"credit_limit") !=null && actionRequest.getFieldValue(i,"credit_limit").trim().length() > 0 ) {this.setCredit_limit(Double.parseDouble(actionRequest.getFieldValue(i,"credit_limit")));}
if ( actionRequest.getFieldValue(i,"lst_crlimit") !=null && actionRequest.getFieldValue(i,"lst_crlimit").trim().length() > 0 ) {this.setLst_crlimit(Double.parseDouble(actionRequest.getFieldValue(i,"lst_crlimit")));}
if ( actionRequest.getFieldValue(i,"months_balance") !=null && actionRequest.getFieldValue(i,"months_balance").trim().length() > 0 ) {this.setMonths_balance(Integer.parseInt(actionRequest.getFieldValue(i,"months_balance")));}
if ( actionRequest.getFieldValue(i,"months_purchase") !=null && actionRequest.getFieldValue(i,"months_purchase").trim().length() > 0 ) {this.setMonths_purchase(Integer.parseInt(actionRequest.getFieldValue(i,"months_purchase")));}
if ( actionRequest.getFieldValue(i,"delq_hist_24") !=null ) {this.setDelq_hist_24(actionRequest.getFieldValue(i,"delq_hist_24"));}
if ( actionRequest.getFieldValue(i,"coll_history_flag") !=null ) {this.setColl_history_flag(actionRequest.getFieldValue(i,"coll_history_flag"));}
if ( actionRequest.getFieldValue(i,"ol_flag") !=null ) {this.setOl_flag(actionRequest.getFieldValue(i,"ol_flag"));}
if ( actionRequest.getFieldValue(i,"olc_reason") !=null ) {this.setOlc_reason(actionRequest.getFieldValue(i,"olc_reason"));}
if ( actionRequest.getFieldValue(i,"ol_amt_due") !=null && actionRequest.getFieldValue(i,"ol_amt_due").trim().length() > 0 ) {this.setOl_amt_due(Double.parseDouble(actionRequest.getFieldValue(i,"ol_amt_due")));}
if ( actionRequest.getFieldValue(i,"card_usage_flag") !=null ) {this.setCard_usage_flag(actionRequest.getFieldValue(i,"card_usage_flag"));}
if ( actionRequest.getFieldValue(i,"total_nmbr_cards") !=null && actionRequest.getFieldValue(i,"total_nmbr_cards").trim().length() > 0 ) {this.setTotal_nmbr_cards(Integer.parseInt(actionRequest.getFieldValue(i,"total_nmbr_cards")));}
if ( actionRequest.getFieldValue(i,"pymt_flag") !=null ) {this.setPymt_flag(actionRequest.getFieldValue(i,"pymt_flag"));}
if ( actionRequest.getFieldValue(i,"sr_ach_flag") !=null ) {this.setSr_ach_flag(actionRequest.getFieldValue(i,"sr_ach_flag"));}
if ( actionRequest.getFieldValue(i,"beg_cycle_due") !=null ) {this.setBeg_cycle_due(actionRequest.getFieldValue(i,"beg_cycle_due"));}
if ( actionRequest.getFieldValue(i,"cycle_due") !=null ) {this.setCycle_due(actionRequest.getFieldValue(i,"cycle_due"));}
if ( actionRequest.getFieldValue(i,"low_payment_fee") !=null && actionRequest.getFieldValue(i,"low_payment_fee").trim().length() > 0 ) {this.setLow_payment_fee(Double.parseDouble(actionRequest.getFieldValue(i,"low_payment_fee")));}
if ( actionRequest.getFieldValue(i,"reage_request") !=null ) {this.setReage_request(actionRequest.getFieldValue(i,"reage_request"));}
if ( actionRequest.getFieldValue(i,"last_pymt_sw") !=null ) {this.setLast_pymt_sw(actionRequest.getFieldValue(i,"last_pymt_sw"));}
if ( actionRequest.getFieldValue(i,"last_pymt_aged_count") !=null && actionRequest.getFieldValue(i,"last_pymt_aged_count").trim().length() > 0 ) {this.setLast_pymt_aged_count(Integer.parseInt(actionRequest.getFieldValue(i,"last_pymt_aged_count")));}
if ( actionRequest.getFieldValue(i,"consume_dr_amt") !=null && actionRequest.getFieldValue(i,"consume_dr_amt").trim().length() > 0 ) {this.setConsume_dr_amt(Double.parseDouble(actionRequest.getFieldValue(i,"consume_dr_amt")));}
if ( actionRequest.getFieldValue(i,"consume_cr_amt") !=null && actionRequest.getFieldValue(i,"consume_cr_amt").trim().length() > 0 ) {this.setConsume_cr_amt(Double.parseDouble(actionRequest.getFieldValue(i,"consume_cr_amt")));}
if ( actionRequest.getFieldValue(i,"consume_bal") !=null && actionRequest.getFieldValue(i,"consume_bal").trim().length() > 0 ) {this.setConsume_bal(Double.parseDouble(actionRequest.getFieldValue(i,"consume_bal")));}
if ( actionRequest.getFieldValue(i,"inac_owe_consume_int") !=null && actionRequest.getFieldValue(i,"inac_owe_consume_int").trim().length() > 0 ) {this.setInac_owe_consume_int(Double.parseDouble(actionRequest.getFieldValue(i,"inac_owe_consume_int")));}
if ( actionRequest.getFieldValue(i,"rtl_svc_bnp") !=null && actionRequest.getFieldValue(i,"rtl_svc_bnp").trim().length() > 0 ) {this.setRtl_svc_bnp(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_svc_bnp")));}
if ( actionRequest.getFieldValue(i,"inac_owe_misc_fee") !=null && actionRequest.getFieldValue(i,"inac_owe_misc_fee").trim().length() > 0 ) {this.setInac_owe_misc_fee(Double.parseDouble(actionRequest.getFieldValue(i,"inac_owe_misc_fee")));}
if ( actionRequest.getFieldValue(i,"rtl_insur_bnp") !=null && actionRequest.getFieldValue(i,"rtl_insur_bnp").trim().length() > 0 ) {this.setRtl_insur_bnp(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_insur_bnp")));}
if ( actionRequest.getFieldValue(i,"inac_owe_anfe") !=null && actionRequest.getFieldValue(i,"inac_owe_anfe").trim().length() > 0 ) {this.setInac_owe_anfe(Double.parseDouble(actionRequest.getFieldValue(i,"inac_owe_anfe")));}
if ( actionRequest.getFieldValue(i,"stmt_period_cash_misc_fee") !=null && actionRequest.getFieldValue(i,"stmt_period_cash_misc_fee").trim().length() > 0 ) {this.setStmt_period_cash_misc_fee(Double.parseDouble(actionRequest.getFieldValue(i,"stmt_period_cash_misc_fee")));}
if ( actionRequest.getFieldValue(i,"consume_argu_amt") !=null && actionRequest.getFieldValue(i,"consume_argu_amt").trim().length() > 0 ) {this.setConsume_argu_amt(Double.parseDouble(actionRequest.getFieldValue(i,"consume_argu_amt")));}
if ( actionRequest.getFieldValue(i,"curr_consume_bal") !=null && actionRequest.getFieldValue(i,"curr_consume_bal").trim().length() > 0 ) {this.setCurr_consume_bal(Double.parseDouble(actionRequest.getFieldValue(i,"curr_consume_bal")));}
if ( actionRequest.getFieldValue(i,"consume_accum_int") !=null && actionRequest.getFieldValue(i,"consume_accum_int").trim().length() > 0 ) {this.setConsume_accum_int(Double.parseDouble(actionRequest.getFieldValue(i,"consume_accum_int")));}
if ( actionRequest.getFieldValue(i,"consume_dr_cnt") !=null && actionRequest.getFieldValue(i,"consume_dr_cnt").trim().length() > 0 ) {this.setConsume_dr_cnt(Integer.parseInt(actionRequest.getFieldValue(i,"consume_dr_cnt")));}
if ( actionRequest.getFieldValue(i,"consume_cr_cnt") !=null && actionRequest.getFieldValue(i,"consume_cr_cnt").trim().length() > 0 ) {this.setConsume_cr_cnt(Integer.parseInt(actionRequest.getFieldValue(i,"consume_cr_cnt")));}
if ( actionRequest.getFieldValue(i,"stmt_period_consume_repay") !=null && actionRequest.getFieldValue(i,"stmt_period_consume_repay").trim().length() > 0 ) {this.setStmt_period_consume_repay(Double.parseDouble(actionRequest.getFieldValue(i,"stmt_period_consume_repay")));}
if ( actionRequest.getFieldValue(i,"ytd_inac_consume_int") !=null && actionRequest.getFieldValue(i,"ytd_inac_consume_int").trim().length() > 0 ) {this.setYtd_inac_consume_int(Double.parseDouble(actionRequest.getFieldValue(i,"ytd_inac_consume_int")));}
if ( actionRequest.getFieldValue(i,"rtl_ibnp_lst_stmt") !=null && actionRequest.getFieldValue(i,"rtl_ibnp_lst_stmt").trim().length() > 0 ) {this.setRtl_ibnp_lst_stmt(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_ibnp_lst_stmt")));}
if ( actionRequest.getFieldValue(i,"ytd_pay_consume_int") !=null && actionRequest.getFieldValue(i,"ytd_pay_consume_int").trim().length() > 0 ) {this.setYtd_pay_consume_int(Double.parseDouble(actionRequest.getFieldValue(i,"ytd_pay_consume_int")));}
if ( actionRequest.getFieldValue(i,"rtl_lst_ytd_intr_paid") !=null && actionRequest.getFieldValue(i,"rtl_lst_ytd_intr_paid").trim().length() > 0 ) {this.setRtl_lst_ytd_intr_paid(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_lst_ytd_intr_paid")));}
if ( actionRequest.getFieldValue(i,"rtl_amnt_pymt_rev") !=null && actionRequest.getFieldValue(i,"rtl_amnt_pymt_rev").trim().length() > 0 ) {this.setRtl_amnt_pymt_rev(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_amnt_pymt_rev")));}
if ( actionRequest.getFieldValue(i,"rtl_std_intr") !=null && actionRequest.getFieldValue(i,"rtl_std_intr").trim().length() > 0 ) {this.setRtl_std_intr(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_std_intr")));}
if ( actionRequest.getFieldValue(i,"rtl_provis_bal") !=null && actionRequest.getFieldValue(i,"rtl_provis_bal").trim().length() > 0 ) {this.setRtl_provis_bal(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_provis_bal")));}
if ( actionRequest.getFieldValue(i,"rtl_provis_intr") !=null && actionRequest.getFieldValue(i,"rtl_provis_intr").trim().length() > 0 ) {this.setRtl_provis_intr(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_provis_intr")));}
if ( actionRequest.getFieldValue(i,"cash_dr_amt") !=null && actionRequest.getFieldValue(i,"cash_dr_amt").trim().length() > 0 ) {this.setCash_dr_amt(Double.parseDouble(actionRequest.getFieldValue(i,"cash_dr_amt")));}
if ( actionRequest.getFieldValue(i,"cash_cr_amt") !=null && actionRequest.getFieldValue(i,"cash_cr_amt").trim().length() > 0 ) {this.setCash_cr_amt(Double.parseDouble(actionRequest.getFieldValue(i,"cash_cr_amt")));}
if ( actionRequest.getFieldValue(i,"cash_bal") !=null && actionRequest.getFieldValue(i,"cash_bal").trim().length() > 0 ) {this.setCash_bal(Double.parseDouble(actionRequest.getFieldValue(i,"cash_bal")));}
if ( actionRequest.getFieldValue(i,"inac_owe_cash_int") !=null && actionRequest.getFieldValue(i,"inac_owe_cash_int").trim().length() > 0 ) {this.setInac_owe_cash_int(Double.parseDouble(actionRequest.getFieldValue(i,"inac_owe_cash_int")));}
if ( actionRequest.getFieldValue(i,"inac_owe_cash_svc") !=null && actionRequest.getFieldValue(i,"inac_owe_cash_svc").trim().length() > 0 ) {this.setInac_owe_cash_svc(Double.parseDouble(actionRequest.getFieldValue(i,"inac_owe_cash_svc")));}
if ( actionRequest.getFieldValue(i,"cash_dissent_bal") !=null && actionRequest.getFieldValue(i,"cash_dissent_bal").trim().length() > 0 ) {this.setCash_dissent_bal(Double.parseDouble(actionRequest.getFieldValue(i,"cash_dissent_bal")));}
if ( actionRequest.getFieldValue(i,"curr_cash_bal") !=null && actionRequest.getFieldValue(i,"curr_cash_bal").trim().length() > 0 ) {this.setCurr_cash_bal(Double.parseDouble(actionRequest.getFieldValue(i,"curr_cash_bal")));}
if ( actionRequest.getFieldValue(i,"cash_accum_int") !=null && actionRequest.getFieldValue(i,"cash_accum_int").trim().length() > 0 ) {this.setCash_accum_int(Double.parseDouble(actionRequest.getFieldValue(i,"cash_accum_int")));}
if ( actionRequest.getFieldValue(i,"cash_dr_cnt") !=null && actionRequest.getFieldValue(i,"cash_dr_cnt").trim().length() > 0 ) {this.setCash_dr_cnt(Integer.parseInt(actionRequest.getFieldValue(i,"cash_dr_cnt")));}
if ( actionRequest.getFieldValue(i,"cash_cr_cnt") !=null && actionRequest.getFieldValue(i,"cash_cr_cnt").trim().length() > 0 ) {this.setCash_cr_cnt(Integer.parseInt(actionRequest.getFieldValue(i,"cash_cr_cnt")));}
if ( actionRequest.getFieldValue(i,"stmt_period_cash_repay") !=null && actionRequest.getFieldValue(i,"stmt_period_cash_repay").trim().length() > 0 ) {this.setStmt_period_cash_repay(Double.parseDouble(actionRequest.getFieldValue(i,"stmt_period_cash_repay")));}
if ( actionRequest.getFieldValue(i,"ytd_inac_cash_int") !=null && actionRequest.getFieldValue(i,"ytd_inac_cash_int").trim().length() > 0 ) {this.setYtd_inac_cash_int(Double.parseDouble(actionRequest.getFieldValue(i,"ytd_inac_cash_int")));}
if ( actionRequest.getFieldValue(i,"cash_ibnp_lst_stmt") !=null && actionRequest.getFieldValue(i,"cash_ibnp_lst_stmt").trim().length() > 0 ) {this.setCash_ibnp_lst_stmt(Double.parseDouble(actionRequest.getFieldValue(i,"cash_ibnp_lst_stmt")));}
if ( actionRequest.getFieldValue(i,"cash_ytd_intr_paid") !=null && actionRequest.getFieldValue(i,"cash_ytd_intr_paid").trim().length() > 0 ) {this.setCash_ytd_intr_paid(Double.parseDouble(actionRequest.getFieldValue(i,"cash_ytd_intr_paid")));}
if ( actionRequest.getFieldValue(i,"cash_lst_ytd_intr_paid") !=null && actionRequest.getFieldValue(i,"cash_lst_ytd_intr_paid").trim().length() > 0 ) {this.setCash_lst_ytd_intr_paid(Double.parseDouble(actionRequest.getFieldValue(i,"cash_lst_ytd_intr_paid")));}
if ( actionRequest.getFieldValue(i,"cash_amnt_pymt_rev") !=null && actionRequest.getFieldValue(i,"cash_amnt_pymt_rev").trim().length() > 0 ) {this.setCash_amnt_pymt_rev(Double.parseDouble(actionRequest.getFieldValue(i,"cash_amnt_pymt_rev")));}
if ( actionRequest.getFieldValue(i,"cash_std_intr") !=null && actionRequest.getFieldValue(i,"cash_std_intr").trim().length() > 0 ) {this.setCash_std_intr(Double.parseDouble(actionRequest.getFieldValue(i,"cash_std_intr")));}
if ( actionRequest.getFieldValue(i,"rtl_neg_provis_intr") !=null && actionRequest.getFieldValue(i,"rtl_neg_provis_intr").trim().length() > 0 ) {this.setRtl_neg_provis_intr(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_neg_provis_intr")));}
if ( actionRequest.getFieldValue(i,"rtl_neg_antici_intr") !=null && actionRequest.getFieldValue(i,"rtl_neg_antici_intr").trim().length() > 0 ) {this.setRtl_neg_antici_intr(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_neg_antici_intr")));}
if ( actionRequest.getFieldValue(i,"curr_bal") !=null && actionRequest.getFieldValue(i,"curr_bal").trim().length() > 0 ) {this.setCurr_bal(Double.parseDouble(actionRequest.getFieldValue(i,"curr_bal")));}
if ( actionRequest.getFieldValue(i,"outst_auth_amt") !=null && actionRequest.getFieldValue(i,"outst_auth_amt").trim().length() > 0 ) {this.setOutst_auth_amt(Double.parseDouble(actionRequest.getFieldValue(i,"outst_auth_amt")));}
if ( actionRequest.getFieldValue(i,"outst_auth_cnt") !=null && actionRequest.getFieldValue(i,"outst_auth_cnt").trim().length() > 0 ) {this.setOutst_auth_cnt(Integer.parseInt(actionRequest.getFieldValue(i,"outst_auth_cnt")));}
if ( actionRequest.getFieldValue(i,"avail_crlmt") !=null && actionRequest.getFieldValue(i,"avail_crlmt").trim().length() > 0 ) {this.setAvail_crlmt(Double.parseDouble(actionRequest.getFieldValue(i,"avail_crlmt")));}
if ( actionRequest.getFieldValue(i,"csh_adv_lim") !=null && actionRequest.getFieldValue(i,"csh_adv_lim").trim().length() > 0 ) {this.setCsh_adv_lim(Double.parseDouble(actionRequest.getFieldValue(i,"csh_adv_lim")));}
if ( actionRequest.getFieldValue(i,"csh_adv_avail") !=null && actionRequest.getFieldValue(i,"csh_adv_avail").trim().length() > 0 ) {this.setCsh_adv_avail(Double.parseDouble(actionRequest.getFieldValue(i,"csh_adv_avail")));}
if ( actionRequest.getFieldValue(i,"grace_days") !=null && actionRequest.getFieldValue(i,"grace_days").trim().length() > 0 ) {this.setGrace_days(Integer.parseInt(actionRequest.getFieldValue(i,"grace_days")));}
if ( actionRequest.getFieldValue(i,"lst_ytd_intr_billed") !=null && actionRequest.getFieldValue(i,"lst_ytd_intr_billed").trim().length() > 0 ) {this.setLst_ytd_intr_billed(Double.parseDouble(actionRequest.getFieldValue(i,"lst_ytd_intr_billed")));}
if ( actionRequest.getFieldValue(i,"ytd_inac_service_fee") !=null && actionRequest.getFieldValue(i,"ytd_inac_service_fee").trim().length() > 0 ) {this.setYtd_inac_service_fee(Double.parseDouble(actionRequest.getFieldValue(i,"ytd_inac_service_fee")));}
if ( actionRequest.getFieldValue(i,"disput_count") !=null && actionRequest.getFieldValue(i,"disput_count").trim().length() > 0 ) {this.setDisput_count(Integer.parseInt(actionRequest.getFieldValue(i,"disput_count")));}
if ( actionRequest.getFieldValue(i,"disput_fee") !=null && actionRequest.getFieldValue(i,"disput_fee").trim().length() > 0 ) {this.setDisput_fee(Double.parseDouble(actionRequest.getFieldValue(i,"disput_fee")));}
if ( actionRequest.getFieldValue(i,"consume_apcl_rate") !=null && actionRequest.getFieldValue(i,"consume_apcl_rate").trim().length() > 0 ) {this.setConsume_apcl_rate(Double.parseDouble(actionRequest.getFieldValue(i,"consume_apcl_rate")));}
if ( actionRequest.getFieldValue(i,"cash_apcl_amt") !=null && actionRequest.getFieldValue(i,"cash_apcl_amt").trim().length() > 0 ) {this.setCash_apcl_amt(Double.parseDouble(actionRequest.getFieldValue(i,"cash_apcl_amt")));}
if ( actionRequest.getFieldValue(i,"lst_pymt_amnt") !=null && actionRequest.getFieldValue(i,"lst_pymt_amnt").trim().length() > 0 ) {this.setLst_pymt_amnt(Double.parseDouble(actionRequest.getFieldValue(i,"lst_pymt_amnt")));}
if ( actionRequest.getFieldValue(i,"aggr_cash_days") !=null && actionRequest.getFieldValue(i,"aggr_cash_days").trim().length() > 0 ) {this.setAggr_cash_days(Integer.parseInt(actionRequest.getFieldValue(i,"aggr_cash_days")));}
if ( actionRequest.getFieldValue(i,"aggr_rtl_days") !=null && actionRequest.getFieldValue(i,"aggr_rtl_days").trim().length() > 0 ) {this.setAggr_rtl_days(Integer.parseInt(actionRequest.getFieldValue(i,"aggr_rtl_days")));}
if ( actionRequest.getFieldValue(i,"dl_amt") !=null && actionRequest.getFieldValue(i,"dl_amt").trim().length() > 0 ) {this.setDl_amt(Double.parseDouble(actionRequest.getFieldValue(i,"dl_amt")));}
if ( actionRequest.getFieldValue(i,"aggr_ytd_days") !=null && actionRequest.getFieldValue(i,"aggr_ytd_days").trim().length() > 0 ) {this.setAggr_ytd_days(Integer.parseInt(actionRequest.getFieldValue(i,"aggr_ytd_days")));}
if ( actionRequest.getFieldValue(i,"aggr_ytd_balance") !=null && actionRequest.getFieldValue(i,"aggr_ytd_balance").trim().length() > 0 ) {this.setAggr_ytd_balance(Double.parseDouble(actionRequest.getFieldValue(i,"aggr_ytd_balance")));}
if ( actionRequest.getFieldValue(i,"aggr_lst_ytd_days") !=null && actionRequest.getFieldValue(i,"aggr_lst_ytd_days").trim().length() > 0 ) {this.setAggr_lst_ytd_days(Integer.parseInt(actionRequest.getFieldValue(i,"aggr_lst_ytd_days")));}
if ( actionRequest.getFieldValue(i,"aggr_lst_ytd_balance") !=null && actionRequest.getFieldValue(i,"aggr_lst_ytd_balance").trim().length() > 0 ) {this.setAggr_lst_ytd_balance(Double.parseDouble(actionRequest.getFieldValue(i,"aggr_lst_ytd_balance")));}
if ( actionRequest.getFieldValue(i,"ci_flag") !=null ) {this.setCi_flag(actionRequest.getFieldValue(i,"ci_flag"));}
if ( actionRequest.getFieldValue(i,"ri_flag") !=null ) {this.setRi_flag(actionRequest.getFieldValue(i,"ri_flag"));}
if ( actionRequest.getFieldValue(i,"cash_adv_per_fee_ctd") !=null && actionRequest.getFieldValue(i,"cash_adv_per_fee_ctd").trim().length() > 0 ) {this.setCash_adv_per_fee_ctd(Double.parseDouble(actionRequest.getFieldValue(i,"cash_adv_per_fee_ctd")));}
if ( actionRequest.getFieldValue(i,"coll_block_code") !=null ) {this.setColl_block_code(actionRequest.getFieldValue(i,"coll_block_code"));}
if ( actionRequest.getFieldValue(i,"instl_limit") !=null && actionRequest.getFieldValue(i,"instl_limit").trim().length() > 0 ) {this.setInstl_limit(Double.parseDouble(actionRequest.getFieldValue(i,"instl_limit")));}
if ( actionRequest.getFieldValue(i,"part_bal") !=null && actionRequest.getFieldValue(i,"part_bal").trim().length() > 0 ) {this.setPart_bal(Double.parseDouble(actionRequest.getFieldValue(i,"part_bal")));}
if ( actionRequest.getFieldValue(i,"outst_instl_amt") !=null && actionRequest.getFieldValue(i,"outst_instl_amt").trim().length() > 0 ) {this.setOutst_instl_amt(Double.parseDouble(actionRequest.getFieldValue(i,"outst_instl_amt")));}
if ( actionRequest.getFieldValue(i,"use_instl_limit") !=null && actionRequest.getFieldValue(i,"use_instl_limit").trim().length() > 0 ) {this.setUse_instl_limit(Double.parseDouble(actionRequest.getFieldValue(i,"use_instl_limit")));}
if ( actionRequest.getFieldValue(i,"tcm_grace_days") !=null ) {this.setTcm_grace_days(actionRequest.getFieldValue(i,"tcm_grace_days"));}
if ( actionRequest.getFieldValue(i,"perm_credit_limit") !=null && actionRequest.getFieldValue(i,"perm_credit_limit").trim().length() > 0 ) {this.setPerm_credit_limit(Double.parseDouble(actionRequest.getFieldValue(i,"perm_credit_limit")));}
if ( actionRequest.getFieldValue(i,"temp_credit_limit") !=null && actionRequest.getFieldValue(i,"temp_credit_limit").trim().length() > 0 ) {this.setTemp_credit_limit(Double.parseDouble(actionRequest.getFieldValue(i,"temp_credit_limit")));}
if ( actionRequest.getFieldValue(i,"crlimit_temp_eff_dte") !=null ) {this.setCrlimit_temp_eff_dte(actionRequest.getFieldValue(i,"crlimit_temp_eff_dte"));}
if ( actionRequest.getFieldValue(i,"crlimit_temp_exp_dte") !=null ) {this.setCrlimit_temp_exp_dte(actionRequest.getFieldValue(i,"crlimit_temp_exp_dte"));}
if ( actionRequest.getFieldValue(i,"cash_limit") !=null && actionRequest.getFieldValue(i,"cash_limit").trim().length() > 0 ) {this.setCash_limit(Double.parseDouble(actionRequest.getFieldValue(i,"cash_limit")));}
if ( actionRequest.getFieldValue(i,"avl_cash_bal") !=null && actionRequest.getFieldValue(i,"avl_cash_bal").trim().length() > 0 ) {this.setAvl_cash_bal(Double.parseDouble(actionRequest.getFieldValue(i,"avl_cash_bal")));}
if ( actionRequest.getFieldValue(i,"cash_adv_os_auth") !=null && actionRequest.getFieldValue(i,"cash_adv_os_auth").trim().length() > 0 ) {this.setCash_adv_os_auth(Integer.parseInt(actionRequest.getFieldValue(i,"cash_adv_os_auth")));}
if ( actionRequest.getFieldValue(i,"dte_lst_cash_auth") !=null ) {this.setDte_lst_cash_auth(actionRequest.getFieldValue(i,"dte_lst_cash_auth"));}
if ( actionRequest.getFieldValue(i,"cash_cr_adjust_bnp") !=null && actionRequest.getFieldValue(i,"cash_cr_adjust_bnp").trim().length() > 0 ) {this.setCash_cr_adjust_bnp(Double.parseDouble(actionRequest.getFieldValue(i,"cash_cr_adjust_bnp")));}
if ( actionRequest.getFieldValue(i,"rtl_cr_adjust_bnp") !=null && actionRequest.getFieldValue(i,"rtl_cr_adjust_bnp").trim().length() > 0 ) {this.setRtl_cr_adjust_bnp(Double.parseDouble(actionRequest.getFieldValue(i,"rtl_cr_adjust_bnp")));}
if ( actionRequest.getFieldValue(i,"ol_cash_pymt") !=null && actionRequest.getFieldValue(i,"ol_cash_pymt").trim().length() > 0 ) {this.setOl_cash_pymt(Double.parseDouble(actionRequest.getFieldValue(i,"ol_cash_pymt")));}
if ( actionRequest.getFieldValue(i,"ol_rtl_pymt") !=null && actionRequest.getFieldValue(i,"ol_rtl_pymt").trim().length() > 0 ) {this.setOl_rtl_pymt(Double.parseDouble(actionRequest.getFieldValue(i,"ol_rtl_pymt")));}
if ( actionRequest.getFieldValue(i,"last_repay_dt_due") !=null ) {this.setLast_repay_dt_due(actionRequest.getFieldValue(i,"last_repay_dt_due"));}
if ( actionRequest.getFieldValue(i,"src_cd") !=null ) {this.setSrc_cd(actionRequest.getFieldValue(i,"src_cd"));}
if ( actionRequest.getFieldValue(i,"last_pri_block_code") !=null ) {this.setLast_pri_block_code(actionRequest.getFieldValue(i,"last_pri_block_code"));}
if ( actionRequest.getFieldValue(i,"last_processing_bal") !=null && actionRequest.getFieldValue(i,"last_processing_bal").trim().length() > 0 ) {this.setLast_processing_bal(Double.parseDouble(actionRequest.getFieldValue(i,"last_processing_bal")));}
if ( actionRequest.getFieldValue(i,"pymt_bfr_grace_ctd") !=null && actionRequest.getFieldValue(i,"pymt_bfr_grace_ctd").trim().length() > 0 ) {this.setPymt_bfr_grace_ctd(Double.parseDouble(actionRequest.getFieldValue(i,"pymt_bfr_grace_ctd")));}
if ( actionRequest.getFieldValue(i,"acct_ledger_sts") !=null ) {this.setAcct_ledger_sts(actionRequest.getFieldValue(i,"acct_ledger_sts"));}
if ( actionRequest.getFieldValue(i,"short_name") !=null ) {this.setShort_name(actionRequest.getFieldValue(i,"short_name"));}
if ( actionRequest.getFieldValue(i,"alpha_key") !=null ) {this.setAlpha_key(actionRequest.getFieldValue(i,"alpha_key"));}
if ( actionRequest.getFieldValue(i,"engage_rfn_acct_no_new") !=null ) {this.setEngage_rfn_acct_no_new(actionRequest.getFieldValue(i,"engage_rfn_acct_no_new"));}
if ( actionRequest.getFieldValue(i,"cr_census_tract") !=null ) {this.setCr_census_tract(actionRequest.getFieldValue(i,"cr_census_tract"));}
if ( actionRequest.getFieldValue(i,"ln_prod_cod") !=null ) {this.setLn_prod_cod(actionRequest.getFieldValue(i,"ln_prod_cod"));}
if ( actionRequest.getFieldValue(i,"ods_load_dt") !=null ) {this.setOds_load_dt(actionRequest.getFieldValue(i,"ods_load_dt"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { ODSBCRDCRTACCT obj = (ODSBCRDCRTACCT)super.clone();obj.setOrg_no(obj.org_no);
obj.setActp(obj.actp);
obj.setAcct_no(obj.acct_no);
obj.setOds_src_dt(obj.ods_src_dt);
obj.setCurr_cd(obj.curr_cd);
obj.setOchr_customer_org(obj.ochr_customer_org);
obj.setCust_no(obj.cust_no);
obj.setStmt_day(obj.stmt_day);
obj.setXfr_org_nmbr(obj.xfr_org_nmbr);
obj.setXfr_type_nmbr(obj.xfr_type_nmbr);
obj.setXfr_acct_nmbr(obj.xfr_acct_nmbr);
obj.setOfficer(obj.officer);
obj.setCurr_collector(obj.curr_collector);
obj.setPerm_collector(obj.perm_collector);
obj.setCorp_acct_org(obj.corp_acct_org);
obj.setCorp_acct_nmbr(obj.corp_acct_nmbr);
obj.setCollateral_code(obj.collateral_code);
obj.setSr_user_code_2(obj.sr_user_code_2);
obj.setUser_code_3(obj.user_code_3);
obj.setBlock_code(obj.block_code);
obj.setAlt_block_code(obj.alt_block_code);
obj.setAcct_sts(obj.acct_sts);
obj.setOf_late_fee_flg(obj.of_late_fee_flg);
obj.setOf_ovlm_fee_flg(obj.of_ovlm_fee_flg);
obj.setOf_svc_flg(obj.of_svc_flg);
obj.setStat_chng_flag(obj.stat_chng_flag);
obj.setAuth_point(obj.auth_point);
obj.setSr_chgoff_status_flag(obj.sr_chgoff_status_flag);
obj.setAcct_crd_op_dt(obj.acct_crd_op_dt);
obj.setLast_sts_chg_dt(obj.last_sts_chg_dt);
obj.setAcct_crd_en_dt(obj.acct_crd_en_dt);
obj.setLast_consume_dt(obj.last_consume_dt);
obj.setLast_cash_dt(obj.last_cash_dt);
obj.setDte_lst_delq(obj.dte_lst_delq);
obj.setDte_prior_delq(obj.dte_prior_delq);
obj.setCm_dte_lst_active(obj.cm_dte_lst_active);
obj.setLast_cahs_repay_dt(obj.last_cahs_repay_dt);
obj.setLast_consume_repay_dt(obj.last_consume_repay_dt);
obj.setLast_chg_crlim_dt(obj.last_chg_crlim_dt);
obj.setLst_crlimit_dte(obj.lst_crlimit_dte);
obj.setSr_dte_lst_stmt(obj.sr_dte_lst_stmt);
obj.setDte_nxt_stmt(obj.dte_nxt_stmt);
obj.setDte_pymt_due(obj.dte_pymt_due);
obj.setDte_hi_balance(obj.dte_hi_balance);
obj.setLast_repay_dt(obj.last_repay_dt);
obj.setDte_cr_balance(obj.dte_cr_balance);
obj.setCm_dte_lst_maint(obj.cm_dte_lst_maint);
obj.setDte_lst_rate_chg(obj.dte_lst_rate_chg);
obj.setDte_into_collection(obj.dte_into_collection);
obj.setBirth_dt(obj.birth_dt);
obj.setTrn_crd_eff_dt(obj.trn_crd_eff_dt);
obj.setLast_fee_dt(obj.last_fee_dt);
obj.setChgoff_sts_chg_dt(obj.chgoff_sts_chg_dt);
obj.setDte_cr_bal_range(obj.dte_cr_bal_range);
obj.setBlock_dt(obj.block_dt);
obj.setDte_alt_block_code(obj.dte_alt_block_code);
obj.setDte_filler_2(obj.dte_filler_2);
obj.setDte_filler_1(obj.dte_filler_1);
obj.setCredit_limit(obj.credit_limit);
obj.setLst_crlimit(obj.lst_crlimit);
obj.setMonths_balance(obj.months_balance);
obj.setMonths_purchase(obj.months_purchase);
obj.setDelq_hist_24(obj.delq_hist_24);
obj.setColl_history_flag(obj.coll_history_flag);
obj.setOl_flag(obj.ol_flag);
obj.setOlc_reason(obj.olc_reason);
obj.setOl_amt_due(obj.ol_amt_due);
obj.setCard_usage_flag(obj.card_usage_flag);
obj.setTotal_nmbr_cards(obj.total_nmbr_cards);
obj.setPymt_flag(obj.pymt_flag);
obj.setSr_ach_flag(obj.sr_ach_flag);
obj.setBeg_cycle_due(obj.beg_cycle_due);
obj.setCycle_due(obj.cycle_due);
obj.setLow_payment_fee(obj.low_payment_fee);
obj.setReage_request(obj.reage_request);
obj.setLast_pymt_sw(obj.last_pymt_sw);
obj.setLast_pymt_aged_count(obj.last_pymt_aged_count);
obj.setConsume_dr_amt(obj.consume_dr_amt);
obj.setConsume_cr_amt(obj.consume_cr_amt);
obj.setConsume_bal(obj.consume_bal);
obj.setInac_owe_consume_int(obj.inac_owe_consume_int);
obj.setRtl_svc_bnp(obj.rtl_svc_bnp);
obj.setInac_owe_misc_fee(obj.inac_owe_misc_fee);
obj.setRtl_insur_bnp(obj.rtl_insur_bnp);
obj.setInac_owe_anfe(obj.inac_owe_anfe);
obj.setStmt_period_cash_misc_fee(obj.stmt_period_cash_misc_fee);
obj.setConsume_argu_amt(obj.consume_argu_amt);
obj.setCurr_consume_bal(obj.curr_consume_bal);
obj.setConsume_accum_int(obj.consume_accum_int);
obj.setConsume_dr_cnt(obj.consume_dr_cnt);
obj.setConsume_cr_cnt(obj.consume_cr_cnt);
obj.setStmt_period_consume_repay(obj.stmt_period_consume_repay);
obj.setYtd_inac_consume_int(obj.ytd_inac_consume_int);
obj.setRtl_ibnp_lst_stmt(obj.rtl_ibnp_lst_stmt);
obj.setYtd_pay_consume_int(obj.ytd_pay_consume_int);
obj.setRtl_lst_ytd_intr_paid(obj.rtl_lst_ytd_intr_paid);
obj.setRtl_amnt_pymt_rev(obj.rtl_amnt_pymt_rev);
obj.setRtl_std_intr(obj.rtl_std_intr);
obj.setRtl_provis_bal(obj.rtl_provis_bal);
obj.setRtl_provis_intr(obj.rtl_provis_intr);
obj.setCash_dr_amt(obj.cash_dr_amt);
obj.setCash_cr_amt(obj.cash_cr_amt);
obj.setCash_bal(obj.cash_bal);
obj.setInac_owe_cash_int(obj.inac_owe_cash_int);
obj.setInac_owe_cash_svc(obj.inac_owe_cash_svc);
obj.setCash_dissent_bal(obj.cash_dissent_bal);
obj.setCurr_cash_bal(obj.curr_cash_bal);
obj.setCash_accum_int(obj.cash_accum_int);
obj.setCash_dr_cnt(obj.cash_dr_cnt);
obj.setCash_cr_cnt(obj.cash_cr_cnt);
obj.setStmt_period_cash_repay(obj.stmt_period_cash_repay);
obj.setYtd_inac_cash_int(obj.ytd_inac_cash_int);
obj.setCash_ibnp_lst_stmt(obj.cash_ibnp_lst_stmt);
obj.setCash_ytd_intr_paid(obj.cash_ytd_intr_paid);
obj.setCash_lst_ytd_intr_paid(obj.cash_lst_ytd_intr_paid);
obj.setCash_amnt_pymt_rev(obj.cash_amnt_pymt_rev);
obj.setCash_std_intr(obj.cash_std_intr);
obj.setRtl_neg_provis_intr(obj.rtl_neg_provis_intr);
obj.setRtl_neg_antici_intr(obj.rtl_neg_antici_intr);
obj.setCurr_bal(obj.curr_bal);
obj.setOutst_auth_amt(obj.outst_auth_amt);
obj.setOutst_auth_cnt(obj.outst_auth_cnt);
obj.setAvail_crlmt(obj.avail_crlmt);
obj.setCsh_adv_lim(obj.csh_adv_lim);
obj.setCsh_adv_avail(obj.csh_adv_avail);
obj.setGrace_days(obj.grace_days);
obj.setLst_ytd_intr_billed(obj.lst_ytd_intr_billed);
obj.setYtd_inac_service_fee(obj.ytd_inac_service_fee);
obj.setDisput_count(obj.disput_count);
obj.setDisput_fee(obj.disput_fee);
obj.setConsume_apcl_rate(obj.consume_apcl_rate);
obj.setCash_apcl_amt(obj.cash_apcl_amt);
obj.setLst_pymt_amnt(obj.lst_pymt_amnt);
obj.setAggr_cash_days(obj.aggr_cash_days);
obj.setAggr_rtl_days(obj.aggr_rtl_days);
obj.setDl_amt(obj.dl_amt);
obj.setAggr_ytd_days(obj.aggr_ytd_days);
obj.setAggr_ytd_balance(obj.aggr_ytd_balance);
obj.setAggr_lst_ytd_days(obj.aggr_lst_ytd_days);
obj.setAggr_lst_ytd_balance(obj.aggr_lst_ytd_balance);
obj.setCi_flag(obj.ci_flag);
obj.setRi_flag(obj.ri_flag);
obj.setCash_adv_per_fee_ctd(obj.cash_adv_per_fee_ctd);
obj.setColl_block_code(obj.coll_block_code);
obj.setInstl_limit(obj.instl_limit);
obj.setPart_bal(obj.part_bal);
obj.setOutst_instl_amt(obj.outst_instl_amt);
obj.setUse_instl_limit(obj.use_instl_limit);
obj.setTcm_grace_days(obj.tcm_grace_days);
obj.setPerm_credit_limit(obj.perm_credit_limit);
obj.setTemp_credit_limit(obj.temp_credit_limit);
obj.setCrlimit_temp_eff_dte(obj.crlimit_temp_eff_dte);
obj.setCrlimit_temp_exp_dte(obj.crlimit_temp_exp_dte);
obj.setCash_limit(obj.cash_limit);
obj.setAvl_cash_bal(obj.avl_cash_bal);
obj.setCash_adv_os_auth(obj.cash_adv_os_auth);
obj.setDte_lst_cash_auth(obj.dte_lst_cash_auth);
obj.setCash_cr_adjust_bnp(obj.cash_cr_adjust_bnp);
obj.setRtl_cr_adjust_bnp(obj.rtl_cr_adjust_bnp);
obj.setOl_cash_pymt(obj.ol_cash_pymt);
obj.setOl_rtl_pymt(obj.ol_rtl_pymt);
obj.setLast_repay_dt_due(obj.last_repay_dt_due);
obj.setSrc_cd(obj.src_cd);
obj.setLast_pri_block_code(obj.last_pri_block_code);
obj.setLast_processing_bal(obj.last_processing_bal);
obj.setPymt_bfr_grace_ctd(obj.pymt_bfr_grace_ctd);
obj.setAcct_ledger_sts(obj.acct_ledger_sts);
obj.setShort_name(obj.short_name);
obj.setAlpha_key(obj.alpha_key);
obj.setEngage_rfn_acct_no_new(obj.engage_rfn_acct_no_new);
obj.setCr_census_tract(obj.cr_census_tract);
obj.setLn_prod_cod(obj.ln_prod_cod);
obj.setOds_load_dt(obj.ods_load_dt);
return obj;}}