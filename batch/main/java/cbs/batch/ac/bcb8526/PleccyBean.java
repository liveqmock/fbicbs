package cbs.batch.ac.bcb8526;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-2-27
 * Time: 14:26:06
 * To change this template use File | Settings | File Templates.
 */
public class PleccyBean {

    private String orgidt;
    private String cusidt;
    private String apcode;
    private String curcde;
    private Long sumAvabal;
    private short decpos;

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

    public Long getSumAvabal() {
        return sumAvabal;
    }

    public void setSumAvabal(Long sumAvabal) {
        this.sumAvabal = sumAvabal;
    }

    public short getDecpos() {
        return decpos;
    }

    public void setDecpos(short decpos) {
        this.decpos = decpos;
    }
}
