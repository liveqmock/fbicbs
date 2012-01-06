package cbs.batch.ac.bcb8542;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-15
 * Time: 10:30:27
 * MODFLG,CURIRT,NXTIRT,EFFDAT - TO_DATE(#{baseDate}
 */
public class CirProp {
    private Date effdat;
    private String modflg;
    private BigDecimal curirt;
    private BigDecimal nxtirt;
    private int effdats;

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

    public int getEffdats() {
        return effdats;
    }

    public void setEffdats(int effdats) {
        this.effdats = effdats;
    }
}
