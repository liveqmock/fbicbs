package cbs.batch.ac.bcb8541;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-10
 * Time: 15:01:04
 * To change this template use File | Settings | File Templates.
 */
public class BvaBean {
    private String orgidt;
    private String cusidt;
    private String apcode;
    private String curcde;
    private long sumBvaamt;

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

    public long getSumBvaamt() {
        return sumBvaamt;
    }

    public void setSumBvaamt(long sumBvaamt) {
        this.sumBvaamt = sumBvaamt;
    }
}
