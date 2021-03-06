package cbs.view.ac.tradeinfo;

import cbs.common.IbatisManager;
import org.apache.ecs.html.Big;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedProperty;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2010-11-29
 * Time: 13:12:18
 * To change this template use File | Settings | File Templates.
 */
public class ActvchVO {
    // ����Ա��  ��Ʊ�׺ţ� �ұ� ��Ŀ�� �����룺  ���:       �˺� �ͻ���
    private static final Logger logger = LoggerFactory.getLogger(ActvchVO.class);
    private String tlrnum;
    private String vchset;
    private String curcde;
    private String glcode;
    private String apcode;
    private String txnamt;
    private String accode;
    private String cusidt;

    //14-10-27����
    private String anacde;
    private String rvslbl;
    private String minamt;
    private String maxamt;

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;

    public String getTlrnum() {
        return tlrnum;
    }

    public void setTlrnum(String tlrnum) {
        this.tlrnum = "".equals(tlrnum) ? null : tlrnum;
    }

    public String getVchset() {
        return vchset;
    }

    public void setVchset(String vchset) {
        this.vchset = "".equals(vchset) ? null : vchset;
    }

    public String getCurcde() {
        return curcde;
    }

    public void setCurcde(String curcde) {
        this.curcde = "".equals(curcde) ? null : curcde;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = "".equals(glcode) ? null : glcode;
    }

    public String getApcode() {
        return apcode;
    }

    public void setApcode(String apcode) {
        this.apcode = "".equals(apcode) ? null : apcode;
    }

    public String getTxnamt() {
        return this.txnamt;
    }

    public void setTxnamt(String txnamt) {
       this.txnamt = "".equals(txnamt) ? null : txnamt;
    }

    public String getAccode() {
        return accode;
    }

    public void setAccode(String accode) {
        this.accode = "".equals(accode) ? null : accode;
    }

    public String getCusidt() {
        return cusidt;
    }

    public void setCusidt(String cusidt) {
        this.cusidt = "".equals(cusidt) ? null : cusidt;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public String getAnacde() {
        return anacde;
    }

    public void setAnacde(String anacde) {
        this.anacde = "".equals(anacde) ? null :anacde;
    }

    public String getRvslbl() {
        return rvslbl;
    }

    public void setRvslbl(String rvslbl) {
        this.rvslbl = "".equals(rvslbl) ? null : rvslbl;
    }

    public String getMinamt() {
        return minamt;
    }

    public void setMinamt(String minamt) {
        this.minamt = "".equals(minamt) ? null : minamt;
    }

    public String getMaxamt() {
        return maxamt;
    }

    public void setMaxamt(String maxamt) {
        this.maxamt = "".equals(maxamt) ? null : maxamt;
    }
}
