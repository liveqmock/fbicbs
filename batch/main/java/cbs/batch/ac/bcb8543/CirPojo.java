package cbs.batch.ac.bcb8543;
import cbs.repository.account.tempinfo.model.Actcir;

import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-13
 * Time: 21:46:00
 * To change this template use File | Settings | File Templates.
 */
public class CirPojo {
    private Date effdat;
    private String modflg;
    private BigDecimal curirt;
    private BigDecimal nxtirt;
    private BigDecimal irtval;
    private int effdats;
    private Date effdat1;

    public Date getEffdat() {
        return effdat;
    }

    public void setEffdat(Date effdat) {
        this.effdat = effdat;
    }

    public String getModflg() {
        return modflg;
    }

    public void setModflg(String modflg) {
        this.modflg = modflg;
    }

    public BigDecimal getCurirt() {
        return curirt;
    }

    public void setCurirt(BigDecimal curirt) {
        this.curirt = curirt;
    }

    public BigDecimal getNxtirt() {
        return nxtirt;
    }

    public void setNxtirt(BigDecimal nxtirt) {
        this.nxtirt = nxtirt;
    }

    public BigDecimal getIrtval() {
        return irtval;
    }

    public void setIrtval(BigDecimal irtval) {
        this.irtval = irtval;
    }

    public int getEffdats() {
        return effdats;
    }

    public void setEffdats(int effdats) {
        this.effdats = effdats;
    }

    public Date getEffdat1() {
        return effdat1;
    }

    public void setEffdat1(Date effdat1) {
        this.effdat1 = effdat1;
    }
}
