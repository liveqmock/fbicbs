package cbs.repository.account.maininfo.model;

import cbs.repository.account.billinfo.model.Actlgc;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-12-16
 * Time: 11:30:50
 * To change this template use File | Settings | File Templates.
 */
public class ActstmForStmCotent extends Actlgc {
    private String cpcode;   //传票号
    private Long dtxnamt;    //借方发生额
    private Long ctxnamt;    //贷方发生额
    private int daynum;      //距结息日天数
    private int stmpny;
    private int nstmpg;
    public String getCpcode() {
        return cpcode;
    }

    public void setCpcode(String cpcode) {
        this.cpcode = cpcode;
    }

    public Long getDtxnamt() {
        return dtxnamt;
    }

    public void setDtxnamt(Long dtxnamt) {
        this.dtxnamt = dtxnamt;
    }

    public Long getCtxnamt() {
        return ctxnamt;
    }

    public void setCtxnamt(Long ctxnamt) {
        this.ctxnamt = ctxnamt;
    }

    public int getDaynum() {
        return daynum;
    }

    public void setDaynum(int daynum) {
        this.daynum = daynum;
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
}
