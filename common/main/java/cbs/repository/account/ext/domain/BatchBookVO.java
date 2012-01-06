package cbs.repository.account.ext.domain;

import cbs.repository.account.other.model.Acttvc;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-10
 * Time: 11:18:22
 * To change this template use File | Settings | File Templates.
 */
public class BatchBookVO extends Acttvc {

    @Size(min = 4, max = 14, message="请输入正确位数帐号。")
    @NotNull(message = "帐号不能为空！")
    private String actno;

    private String formatedActno;
    private String formatedTxnType;

    private String actnam;

    //交易类型
    @Size(min = 1, max = 2,message="交易类型请输入正确位数（1-2）")
    private String type;

    //输入类型     TODO !! 正常输入为 0  外汇输入 为2   现金？
    private String voErytyp;

    //上次主管授权时输入的valdat值
    private String lastvaldat;

    //凭证种类
    private String formatedAnacde;

    public String getActno() {
        return actno;
    }

    public void setActno(String actno) {
        this.actno = actno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActnam() {
        return actnam;
    }

    public void setActnam(String actnam) {
        this.actnam = actnam;
    }

    public String getVoErytyp() {
        return voErytyp;
    }

    public void setVoErytyp(String voErytyp) {
        this.voErytyp = voErytyp;
    }

    public String getFormatedActno() {
        return formatedActno;
    }

    public void setFormatedActno(String formatedActno) {
        this.formatedActno = formatedActno;
    }

    public String getLastvaldat() {
        return lastvaldat;
    }

    public void setLastvaldat(String lastvaldat) {
        this.lastvaldat = lastvaldat;
    }

    public String getFormatedAnacde() {
        return formatedAnacde;
    }

    public void setFormatedAnacde(String formatedAnacde) {
        this.formatedAnacde = formatedAnacde;
    }

    public String getFormatedTxnType() {
        return formatedTxnType;
    }

    public void setFormatedTxnType(String formatedTxnType) {
        this.formatedTxnType = formatedTxnType;
    }
}

