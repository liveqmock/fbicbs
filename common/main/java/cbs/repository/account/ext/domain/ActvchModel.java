package cbs.repository.account.ext.domain;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2010-11-29
 * Time: 13:19:38
 * To change this template use File | Settings | File Templates.
 */
public class ActvchModel {

    private String tlrnum;
    private String vchset;
    private String setseq;
    private String glcode;
    private String cusidt;
    private String apcode;
    private String curcde;
    private Long txnamt;
    private String rvslbl;
    private String anacde;
    private String furinf;
    private String erytim;
    private String borlen;
    private String accode;
    private String clrbak;

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

    public String getSetseq() {
        return setseq;
    }

    public void setSetseq(String setseq) {
        this.setseq = setseq;
    }


    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public String getCusidt() {
        return cusidt;
    }

    public void setCusidt(String cusidt) {
        this.cusidt = cusidt;
    }

    public String getApcode() {
        return apcode;
    }

    public void setApcode(String apcode) {
        this.apcode = apcode;
    }

    public String getCurcde() {
        return curcde;
    }

    public void setCurcde(String curcde) {
        this.curcde = curcde;
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
            if (rvslbl.equals("*")) this.rvslbl = "冲正";
            else if (rvslbl.equalsIgnoreCase("T")) this.rvslbl = "转账";
            else if (rvslbl.equalsIgnoreCase("B")) this.rvslbl = "冲账";
            else if (rvslbl.equalsIgnoreCase("C")) this.rvslbl = "现金";
            else this.rvslbl = rvslbl;
        }
    }

    public String getFurinf() {
        return furinf;
    }

    public void setFurinf(String furinf) {
        this.furinf = furinf;
    }

    public String getErytim() {
        return erytim;
    }

    public void setErytim(String erytim) {
        this.erytim = erytim;
    }

    public String getAccode() {
        return accode;
    }

    public void setAccode(String accode) {
        this.accode = accode;
    }

    public String getBorlen() {
        return borlen;
    }

    public void setBorlen(String borlen) {
        if (borlen != null) {
            this.borlen = borlen.trim().startsWith("-") ? "借" : "贷";
        }
    }

    public String getAnacde() {
        return anacde;
    }

    public void setAnacde(String anacde) {
        this.anacde = anacde;
    }

    public String getClrbak() {
        return clrbak;
    }

    public void setClrbak(String clrbak) {
        if(" ".equals(clrbak)){
         this.clrbak = "否";
        }else if("1".equals(clrbak)){
           this.clrbak = "是";
        }
    }
}
