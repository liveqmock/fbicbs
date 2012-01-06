package cbs.batch.ac.bcb8533;

import cbs.repository.account.maininfo.model.Actcgl;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-2
 * Time: 10:12:23
 * To change this template use File | Settings | File Templates.
 */
/*
ACTCGL.ORGIDT,ACTCGL.GLCODE,ACTCGL.CURCDE,
                              ACTCGL.DRAMNT,ACTCGL.CRAMNT,ACTCGL.DRBALA,
                              ACTCGL.CRBALA,ACTCGL.DLSBAL,ACTCGL.CLSBAL,
                              ACTCCY.DECPOS,ACTCGL.RECTYP
 */
public class CglccyProp {
    private String orgidt;
    private String glcode;
    private String curcde;
    private long dramnt;
    private long cramnt;
    private long drbala;
    private long crbala;
    private long dlsbal;
    private long clsbal;
    private short decpos;
    private String rectyp;

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

    public long getDramnt() {
        return dramnt;
    }

    public void setDramnt(long dramnt) {
        this.dramnt = dramnt;
    }

    public long getCramnt() {
        return cramnt;
    }

    public void setCramnt(long cramnt) {
        this.cramnt = cramnt;
    }

    public long getDrbala() {
        return drbala;
    }

    public void setDrbala(long drbala) {
        this.drbala = drbala;
    }

    public long getCrbala() {
        return crbala;
    }

    public void setCrbala(long crbala) {
        this.crbala = crbala;
    }

    public long getDlsbal() {
        return dlsbal;
    }

    public void setDlsbal(long dlsbal) {
        this.dlsbal = dlsbal;
    }

    public long getClsbal() {
        return clsbal;
    }

    public void setClsbal(long clsbal) {
        this.clsbal = clsbal;
    }

    public short getDecpos() {
        return decpos;
    }

    public void setDecpos(short decpos) {
        this.decpos = decpos;
    }

    public String getRectyp() {
        return rectyp;
    }

    public void setRectyp(String rectyp) {
        this.rectyp = rectyp;
    }
}
