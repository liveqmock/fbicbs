package cbs.view.ac.tradeinfo;

import cbs.common.IbatisManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedProperty;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2010-11-29
 * Time: 13:12:18
 * To change this template use File | Settings | File Templates.
 */
public class ActstmVO {
    /*账号:
起始记账日期:      截止记账日期:
金额:              记账员:*/
    private static final Logger logger = LoggerFactory.getLogger(ActstmVO.class);
    private String accode;
    private String bgndat;
    private String enddat;
    private String txnamt;
    private String tlrnum;

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;

    public String getAccode() {
        return accode;
    }

    public void setAccode(String accode) {
        this.accode = accode.trim();
    }

    public String getBgndat() {
        return bgndat;
    }

    public void setBgndat(String bgndat) {
         if("____-__-__".equals(bgndat) || StringUtils.isEmpty(bgndat)) {
                  this.bgndat = null;
            }else{
             this.bgndat = bgndat;
         }
    }

    public String getEnddat() {
        return enddat;
    }

    public void setEnddat(String enddat) {
         if("____-__-__".equals(enddat) || StringUtils.isEmpty(enddat)) {
                  this.enddat = null;
            }else{
             this.enddat = enddat;
         }
    }

    public String getTxnamt() {
        return txnamt;
    }

    public void setTxnamt(String txnamt) {
        this.txnamt = (txnamt==null || StringUtils.isEmpty(txnamt)) ? null : txnamt.trim();
    }

    public String getTlrnum() {
        return tlrnum;
    }

    public void setTlrnum(String tlrnum) {
         this.tlrnum = (tlrnum==null  || StringUtils.isEmpty(tlrnum)) ? null : tlrnum.trim();
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }
}