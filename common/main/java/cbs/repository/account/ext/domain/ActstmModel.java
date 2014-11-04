package cbs.repository.account.ext.domain;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2010-11-30
 * Time: 12:50:47
 * To change this template use File | Settings | File Templates.
 */
public class ActstmModel {

    private String stmdat;
    private String valdat;
    private String borlen;        // 借贷别
    private Long txnamt;           // 金额
    private String boramt;          //   借方
    private String lenamt;           // 贷方
    private String rvslbl;
    private Double actbal;
    private String thrref;
    private String furinf;
    private String tlrnum;
    private String vchset;
    private String stmtim;
    private int stmpny;
    private int nstmpg;
    private int paglin;

    public String getStmdat() {
        return this.stmdat;
    }

    public void setStmdat(String stmdat) {
        if(stmdat != null && stmdat.length() >= 10) {
            this.stmdat = stmdat.substring(0,11);
        }else this.stmdat = stmdat;
    }

    public String getValdat() {
        return valdat.substring(0, 11);
    }

    public void setValdat(String valdat) {
        this.valdat = valdat;
    }

    public String getBorlen() {
        return borlen;
    }

    public void setBorlen(String borlen) {
        if (borlen != null) {
            String borlenStr = borlen.trim();
            if(borlenStr.startsWith("-")){
               this.borlen = "借";
               this.boramt = String .format("%.2f",Double.parseDouble(borlenStr)/100);
               this.lenamt = "";
            }else {
             this.borlen =  "贷";
             this.lenamt = String .format("%.2f",Double.parseDouble(borlenStr)/100);
             this.boramt = "";
            }
        }
    }

    public Long getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(Long txnamt) {
        this.txnamt = txnamt;
    }

    public String getRvslbl() {
        return rvslbl;
    }

    public void setRvslbl(String rvslbl) {
        if (rvslbl != null) {
            if (rvslbl.equalsIgnoreCase(" ")) this.rvslbl = "正常";
            else if (rvslbl.equalsIgnoreCase("*")) this.rvslbl = "冲正";
            else if (rvslbl.equalsIgnoreCase("T")) this.rvslbl = "转账";
            else if (rvslbl.equalsIgnoreCase("B")) this.rvslbl = "补帐";
            else if (rvslbl.equalsIgnoreCase("C")) this.rvslbl = "现金";
            else this.rvslbl = rvslbl;
        }
    }

    public Double getActbal() {
        return actbal;
    }

    public void setActbal(Double actbal) {
        this.actbal = actbal/ 100.00;
    }

    public String getFurinf() {
        return furinf;
    }

    public void setFurinf(String furinf) {
        this.furinf = furinf;
    }

    public String getTlrnum() {
        return tlrnum;
    }

    public void setTlrnum(String tlrnum) {
        this.tlrnum = tlrnum;
    }

    public String getVchset() {
        return vchset;
    }

    public void setVchset(String vchset) {
        this.vchset = vchset;
    }

    public String getStmtim() {
        return stmtim;
    }

    public void setStmtim(String stmtim) {
        this.stmtim = stmtim;
    }

    public String getBoramt() {
        return boramt;
    }

    public void setBoramt(String boramt) {
        this.boramt = boramt;
    }

    public String getLenamt() {
        return lenamt;
    }

    public void setLenamt(String lenamt) {
        this.lenamt = lenamt;
    }

    public String getThrref() {
        return thrref;
    }

    public void setThrref(String thrref) {
        this.thrref = thrref;
    }

    public int getStmpny() {
        return stmpny;
    }

    public void setStmpny(int stmpny) {
        this.stmpny = stmpny;
    }

    public int getNstmpg() {
        return nstmpg;
    }

    public void setNstmpg(int nstmpg) {
        this.nstmpg = nstmpg;
    }

    public int getPaglin() {
        return paglin;
    }

    public void setPaglin(int paglin) {
        this.paglin = paglin;
    }
}
