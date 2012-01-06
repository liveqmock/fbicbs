package cbs.batch.ac.bcb8523;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-12-13
 * Time: 15:48:58
 * To change this template use File | Settings | File Templates.
 */
public class ActblhParaBean {
    String actOrgidt;
    String actApcode;
    String actCusidt;
    String actCurcde;

    long wkTxnamt;

    Date tmpValdat;
    Date actLintdt;

    Date vchValdat;

    //余额方向标志
    String wktGlcbal;

    public String getActOrgidt() {
        return actOrgidt;
    }

    public void setActOrgidt(String actOrgidt) {
        this.actOrgidt = actOrgidt;
    }

    public String getActApcode() {
        return actApcode;
    }

    public void setActApcode(String actApcode) {
        this.actApcode = actApcode;
    }

    public String getActCusidt() {
        return actCusidt;
    }

    public void setActCusidt(String actCusidt) {
        this.actCusidt = actCusidt;
    }

    public String getActCurcde() {
        return actCurcde;
    }

    public void setActCurcde(String actCurcde) {
        this.actCurcde = actCurcde;
    }

    public long getWkTxnamt() {
        return wkTxnamt;
    }

    public void setWkTxnamt(long wkTxnamt) {
        this.wkTxnamt = wkTxnamt;
    }

    public Date getTmpValdat() {
        return tmpValdat;
    }

    public void setTmpValdat(Date tmpValdat) {
        this.tmpValdat = tmpValdat;
    }

    public Date getActLintdt() {
        return actLintdt;
    }

    public void setActLintdt(Date actLintdt) {
        this.actLintdt = actLintdt;
    }

    public String getWktGlcbal() {
        return wktGlcbal;
    }

    public void setWktGlcbal(String wktGlcbal) {
        this.wktGlcbal = wktGlcbal;
    }

    public Date getVchValdat() {
        return vchValdat;
    }

    public void setVchValdat(Date vchValdat) {
        this.vchValdat = vchValdat;
    }
}
