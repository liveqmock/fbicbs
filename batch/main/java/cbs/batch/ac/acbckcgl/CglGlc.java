package cbs.batch.ac.acbckcgl;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-3
 * Time: 11:18:23
 *  ORGIDT,GLCODE, CURCDE,RECTYP,GLDATE,DLSBAL,CLSBAL,  DRAMNT,CRAMNT,DRBALA,CRBALA
 */
public class CglGlc {
    private String orgidt;
    private String glcode;
    private String curcde;
    private String rectyp;
    private Date gldate;
    private Long dlsbal;
    private Long clsbal;
    private Long dramnt;
    private Long cramnt;
    private Long drbala;
    private Long crbala;

    public String getOrgidt() {
        return orgidt;
    }

    public void setOrgidt(String orgidt) {
        this.orgidt = orgidt;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public String getCurcde() {
        return curcde;
    }

    public void setCurcde(String curcde) {
        this.curcde = curcde;
    }

    public String getRectyp() {
        return rectyp;
    }

    public void setRectyp(String rectyp) {
        this.rectyp = rectyp;
    }

    public Date getGldate() {
        return gldate;
    }

    public void setGldate(Date gldate) {
        this.gldate = gldate;
    }

    public Long getDlsbal() {
        return dlsbal;
    }

    public void setDlsbal(Long dlsbal) {
        this.dlsbal = dlsbal;
    }

    public Long getClsbal() {
        return clsbal;
    }

    public void setClsbal(Long clsbal) {
        this.clsbal = clsbal;
    }

    public Long getDramnt() {
        return dramnt;
    }

    public void setDramnt(Long dramnt) {
        this.dramnt = dramnt;
    }

    public Long getCramnt() {
        return cramnt;
    }

    public void setCramnt(Long cramnt) {
        this.cramnt = cramnt;
    }

    public Long getDrbala() {
        return drbala;
    }

    public void setDrbala(Long drbala) {
        this.drbala = drbala;
    }

    public Long getCrbala() {
        return crbala;
    }

    public void setCrbala(Long crbala) {
        this.crbala = crbala;
    }
}
