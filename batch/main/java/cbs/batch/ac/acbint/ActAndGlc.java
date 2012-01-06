package cbs.batch.ac.acbint;

import java.math.BigDecimal;
import java.util.Date;

public class ActAndGlc {
    /*
                          ACTACT.SYSIDT,ACTACT.ORGIDT,
                            ACTACT.CUSIDT,ACTACT.APCODE,
                            ACTACT.CURCDE, ACTACT.ACTGLC,
                            ACTOBF.GLCBAL,
                            ACTACT.ACTNAM,ACTACT.AVABAL,
                            ACTACT.DINRAT,ACTACT.CINRAT,
                            ACTACT.INTTRA,ACTACT.DEPNUM,
                            ACTACT.CACINT,ACTACT.DACINT,
                            ACTACT.CRATSF,ACTACT.DRATSF,
                            ACTACT.INTFLG,ACTACT.INTCYC,
                            ACTACT.DRACCM,ACTACT.CRACCM,
                            ACTACT.LINTDT,ACTACT.OPNDAT
     */
    private String sysidt;
    private String orgidt;
    private String cusidt;
    private String apcode;
    private String curcde;
    private String actglc;
    private String glcbal;
    private String actnam;
    private Long avabal;
    private String dinrat;
    private String cinrat;
    private String inttra;
    private String depnum;
    private Long cacint;
    private Long dacint;
    private BigDecimal cratsf;
    private BigDecimal dratsf;
    private String intflg;
    private String intcyc;
    private Long draccm;
    private Long craccm;
    private Date lintdt;
    private Date opndat;

    public String getSysidt() {
        return sysidt;
    }

    public void setSysidt(String sysidt) {
        this.sysidt = sysidt;
    }

    public String getOrgidt() {
        return orgidt;
    }

    public void setOrgidt(String orgidt) {
        this.orgidt = orgidt;
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

    public String getActglc() {
        return actglc;
    }

    public void setActglc(String actglc) {
        this.actglc = actglc;
    }

    public String getGlcbal() {
        return glcbal;
    }

    public void setGlcbal(String glcbal) {
        this.glcbal = glcbal;
    }

    public String getActnam() {
        return actnam;
    }

    public void setActnam(String actnam) {
        this.actnam = actnam;
    }

    public Long getAvabal() {
        return avabal;
    }

    public void setAvabal(Long avabal) {
        this.avabal = avabal;
    }

    public String getDinrat() {
        return dinrat;
    }

    public void setDinrat(String dinrat) {
        this.dinrat = dinrat;
    }

    public String getCinrat() {
        return cinrat;
    }

    public void setCinrat(String cinrat) {
        this.cinrat = cinrat;
    }

    public String getInttra() {
        return inttra;
    }

    public void setInttra(String inttra) {
        this.inttra = inttra;
    }

    public String getDepnum() {
        return depnum;
    }

    public void setDepnum(String depnum) {
        this.depnum = depnum;
    }

    public Long getCacint() {
        return cacint;
    }

    public void setCacint(Long cacint) {
        this.cacint = cacint;
    }

    public Long getDacint() {
        return dacint;
    }

    public void setDacint(Long dacint) {
        this.dacint = dacint;
    }

    public BigDecimal getCratsf() {
        return cratsf;
    }

    public void setCratsf(BigDecimal cratsf) {
        this.cratsf = cratsf;
    }

    public BigDecimal getDratsf() {
        return dratsf;
    }

    public void setDratsf(BigDecimal dratsf) {
        this.dratsf = dratsf;
    }

    public String getIntflg() {
        return intflg;
    }

    public void setIntflg(String intflg) {
        this.intflg = intflg;
    }

    public String getIntcyc() {
        return intcyc;
    }

    public void setIntcyc(String intcyc) {
        this.intcyc = intcyc;
    }

    public Long getDraccm() {
        return draccm;
    }

    public void setDraccm(Long draccm) {
        this.draccm = draccm;
    }

    public Long getCraccm() {
        return craccm;
    }

    public void setCraccm(Long craccm) {
        this.craccm = craccm;
    }

    public Date getLintdt() {
        return lintdt;
    }

    public void setLintdt(Date lintdt) {
        this.lintdt = lintdt;
    }

    public Date getOpndat() {
        return opndat;
    }

    public void setOpndat(Date opndat) {
        this.opndat = opndat;
    }
}
