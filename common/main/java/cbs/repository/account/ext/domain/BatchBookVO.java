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

    @Size(min = 4, max = 14, message="��������ȷλ���ʺš�")
    @NotNull(message = "�ʺŲ���Ϊ�գ�")
    private String actno;

    private String formatedActno;
    private String formatedTxnType;

    private String actnam;

    //��������
    @Size(min = 1, max = 2,message="����������������ȷλ����1-2��")
    private String type;

    //��������     TODO !! ��������Ϊ 0  ������� Ϊ2   �ֽ�
    private String voErytyp;

    //�ϴ�������Ȩʱ�����valdatֵ
    private String lastvaldat;

    //ƾ֤����
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

