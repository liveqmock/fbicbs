package cbs.repository.account.other.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Acttvc {
    @Length(max = 3)
    @NotEmpty (message = "机构号不能为空!")
    private String orgidt;

    @Length(max = 4)
    @NotEmpty (message = "柜员号不能为空!")
    private String tlrnum;

    @Max(value = 9999,message = "传票套号最大值不能超过9999!")
    private Short vchset;

    @Max(99)
    private Short setseq;

    private String cusidt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.APCODE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String apcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.CURCDE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String curcde;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.PRDCDE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String prdcde;

    private Long txnamt;

    //@Date8
    private String valdat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.RVSLBL
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String rvslbl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.ANACDE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    @Length(max = 4, message="凭证种类长度不能超过4位")
    @NotNull(message = "凭证种类不能为空！")
    private String anacde;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.FURINF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    @Size(max = 32,message="摘要(凭证号码)内容不能超过32个汉字")
    @NotNull(message = "凭证号码不能为空！")
    private String furinf;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.FXRATE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private BigDecimal fxrate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.SECCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String secccy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.SECAMT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private Long secamt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.CORAPC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String corapc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.VCHATT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    @Max(value = 999, message="附件数不能超过999！")
    private Short vchatt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.VCHAUT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String vchaut;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.DEPNUM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String depnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.TXNBAK
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String txnbak;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.ACTBAK
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String actbak;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.CLRBAK
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String clrbak;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.ERYDAT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String erydat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.ERYTIM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String erytim;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.OUFCRE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String oufcre;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.VCHSTS
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String vchsts;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.FXEFLG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String fxeflg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.RECSTS
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String recsts;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.ERYTYP
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String erytyp;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ACTTVC.REGNUM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    private String regnum;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.ORGIDT
     *
     * @return the value of ACTTVC.ORGIDT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getOrgidt() {
        return orgidt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.ORGIDT
     *
     * @param orgidt the value for ACTTVC.ORGIDT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setOrgidt(String orgidt) {
        this.orgidt = orgidt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.TLRNUM
     *
     * @return the value of ACTTVC.TLRNUM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getTlrnum() {
        return tlrnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.TLRNUM
     *
     * @param tlrnum the value for ACTTVC.TLRNUM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setTlrnum(String tlrnum) {
        this.tlrnum = tlrnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.VCHSET
     *
     * @return the value of ACTTVC.VCHSET
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Short getVchset() {
        return vchset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.VCHSET
     *
     * @param vchset the value for ACTTVC.VCHSET
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setVchset(Short vchset) {
        this.vchset = vchset;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.SETSEQ
     *
     * @return the value of ACTTVC.SETSEQ
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Short getSetseq() {
        return setseq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.SETSEQ
     *
     * @param setseq the value for ACTTVC.SETSEQ
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setSetseq(Short setseq) {
        this.setseq = setseq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.CUSIDT
     *
     * @return the value of ACTTVC.CUSIDT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getCusidt() {
        return cusidt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.CUSIDT
     *
     * @param cusidt the value for ACTTVC.CUSIDT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setCusidt(String cusidt) {
        this.cusidt = cusidt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.APCODE
     *
     * @return the value of ACTTVC.APCODE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getApcode() {
        return apcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.APCODE
     *
     * @param apcode the value for ACTTVC.APCODE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setApcode(String apcode) {
        this.apcode = apcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.CURCDE
     *
     * @return the value of ACTTVC.CURCDE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getCurcde() {
        return curcde;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.CURCDE
     *
     * @param curcde the value for ACTTVC.CURCDE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setCurcde(String curcde) {
        this.curcde = curcde;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.PRDCDE
     *
     * @return the value of ACTTVC.PRDCDE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getPrdcde() {
        return prdcde;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.PRDCDE
     *
     * @param prdcde the value for ACTTVC.PRDCDE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setPrdcde(String prdcde) {
        this.prdcde = prdcde;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.TXNAMT
     *
     * @return the value of ACTTVC.TXNAMT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Long getTxnamt() {
        return txnamt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.TXNAMT
     *
     * @param txnamt the value for ACTTVC.TXNAMT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setTxnamt(Long txnamt) {
        this.txnamt = txnamt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.VALDAT
     *
     * @return the value of ACTTVC.VALDAT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getValdat() {
        return valdat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.VALDAT
     *
     * @param valdat the value for ACTTVC.VALDAT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setValdat(String valdat) {
        this.valdat = valdat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.RVSLBL
     *
     * @return the value of ACTTVC.RVSLBL
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getRvslbl() {
        return rvslbl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.RVSLBL
     *
     * @param rvslbl the value for ACTTVC.RVSLBL
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setRvslbl(String rvslbl) {
        this.rvslbl = rvslbl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.ANACDE
     *
     * @return the value of ACTTVC.ANACDE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getAnacde() {
        return anacde;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.ANACDE
     *
     * @param anacde the value for ACTTVC.ANACDE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setAnacde(String anacde) {
        this.anacde = anacde;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.FURINF
     *
     * @return the value of ACTTVC.FURINF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getFurinf() {
        return furinf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.FURINF
     *
     * @param furinf the value for ACTTVC.FURINF
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setFurinf(String furinf) {
        this.furinf = furinf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.FXRATE
     *
     * @return the value of ACTTVC.FXRATE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public BigDecimal getFxrate() {
        return fxrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.FXRATE
     *
     * @param fxrate the value for ACTTVC.FXRATE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setFxrate(BigDecimal fxrate) {
        this.fxrate = fxrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.SECCCY
     *
     * @return the value of ACTTVC.SECCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getSecccy() {
        return secccy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.SECCCY
     *
     * @param secccy the value for ACTTVC.SECCCY
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setSecccy(String secccy) {
        this.secccy = secccy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.SECAMT
     *
     * @return the value of ACTTVC.SECAMT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Long getSecamt() {
        return secamt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.SECAMT
     *
     * @param secamt the value for ACTTVC.SECAMT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setSecamt(Long secamt) {
        this.secamt = secamt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.CORAPC
     *
     * @return the value of ACTTVC.CORAPC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getCorapc() {
        return corapc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.CORAPC
     *
     * @param corapc the value for ACTTVC.CORAPC
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setCorapc(String corapc) {
        this.corapc = corapc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.VCHATT
     *
     * @return the value of ACTTVC.VCHATT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public Short getVchatt() {
        return vchatt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.VCHATT
     *
     * @param vchatt the value for ACTTVC.VCHATT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setVchatt(Short vchatt) {
        this.vchatt = vchatt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.VCHAUT
     *
     * @return the value of ACTTVC.VCHAUT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getVchaut() {
        return vchaut;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.VCHAUT
     *
     * @param vchaut the value for ACTTVC.VCHAUT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setVchaut(String vchaut) {
        this.vchaut = vchaut;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.DEPNUM
     *
     * @return the value of ACTTVC.DEPNUM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getDepnum() {
        return depnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.DEPNUM
     *
     * @param depnum the value for ACTTVC.DEPNUM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setDepnum(String depnum) {
        this.depnum = depnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.TXNBAK
     *
     * @return the value of ACTTVC.TXNBAK
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getTxnbak() {
        return txnbak;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.TXNBAK
     *
     * @param txnbak the value for ACTTVC.TXNBAK
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setTxnbak(String txnbak) {
        this.txnbak = txnbak;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.ACTBAK
     *
     * @return the value of ACTTVC.ACTBAK
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getActbak() {
        return actbak;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.ACTBAK
     *
     * @param actbak the value for ACTTVC.ACTBAK
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setActbak(String actbak) {
        this.actbak = actbak;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.CLRBAK
     *
     * @return the value of ACTTVC.CLRBAK
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getClrbak() {
        return clrbak;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.CLRBAK
     *
     * @param clrbak the value for ACTTVC.CLRBAK
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setClrbak(String clrbak) {
        this.clrbak = clrbak;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.ERYDAT
     *
     * @return the value of ACTTVC.ERYDAT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getErydat() {
        return erydat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.ERYDAT
     *
     * @param erydat the value for ACTTVC.ERYDAT
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setErydat(String erydat) {
        this.erydat = erydat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.ERYTIM
     *
     * @return the value of ACTTVC.ERYTIM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getErytim() {
        return erytim;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.ERYTIM
     *
     * @param erytim the value for ACTTVC.ERYTIM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setErytim(String erytim) {
        this.erytim = erytim;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.OUFCRE
     *
     * @return the value of ACTTVC.OUFCRE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getOufcre() {
        return oufcre;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.OUFCRE
     *
     * @param oufcre the value for ACTTVC.OUFCRE
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setOufcre(String oufcre) {
        this.oufcre = oufcre;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.VCHSTS
     *
     * @return the value of ACTTVC.VCHSTS
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getVchsts() {
        return vchsts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.VCHSTS
     *
     * @param vchsts the value for ACTTVC.VCHSTS
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setVchsts(String vchsts) {
        this.vchsts = vchsts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.FXEFLG
     *
     * @return the value of ACTTVC.FXEFLG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getFxeflg() {
        return fxeflg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.FXEFLG
     *
     * @param fxeflg the value for ACTTVC.FXEFLG
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setFxeflg(String fxeflg) {
        this.fxeflg = fxeflg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.RECSTS
     *
     * @return the value of ACTTVC.RECSTS
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getRecsts() {
        return recsts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.RECSTS
     *
     * @param recsts the value for ACTTVC.RECSTS
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setRecsts(String recsts) {
        this.recsts = recsts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.ERYTYP
     *
     * @return the value of ACTTVC.ERYTYP
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getErytyp() {
        return erytyp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.ERYTYP
     *
     * @param erytyp the value for ACTTVC.ERYTYP
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setErytyp(String erytyp) {
        this.erytyp = erytyp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ACTTVC.REGNUM
     *
     * @return the value of ACTTVC.REGNUM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public String getRegnum() {
        return regnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ACTTVC.REGNUM
     *
     * @param regnum the value for ACTTVC.REGNUM
     *
     * @mbggenerated Sun Nov 21 21:36:06 CST 2010
     */
    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }
}