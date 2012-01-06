package cbs.repository.account.maininfo.model;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 2011-1-17
 * Time: 16:17:06
 * To change this template use File | Settings | File Templates.
 */
public class ActactForPlcRpt extends Actact  {
    private String plctyp;   //损益类型
    private long totalBokbal;    //总金额
    private String plcnam;  //损益项名称

    public String getPlctyp() {
        return plctyp;
    }

    public void setPlctyp(String plctyp) {
        this.plctyp = plctyp;
    }

    public long getTotalBokbal() {
        return totalBokbal;
    }

    public void setTotalBokbal(long totalBokbal) {
        this.totalBokbal = totalBokbal;
    }

    public String getPlcnam() {
        return plcnam;
    }

    public void setPlcnam(String plcnam) {
        this.plcnam = plcnam;
    }
}
