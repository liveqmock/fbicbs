package cbs.view.re.extsys;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-12-6
 * Time: ����5:34
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ExtPayMngAction {

    private List<String> checkInfoList;
    private String txnType;
    private List<SelectItem> txnTypeList;

    @PostConstruct
    public void init() {
        txnTypeList = new ArrayList<SelectItem>();
        // ����֧�����������ʡ������������ڲ�ת�ˡ���������
        SelectItem si = new SelectItem("1", "����֧��");
        txnTypeList.add(si);
        si = new SelectItem("2", "��������");
        txnTypeList.add(si);
        si = new SelectItem("3", "��������");
        txnTypeList.add(si);
        si = new SelectItem("4", "�ڲ�ת��");
        txnTypeList.add(si);
        si = new SelectItem("5", "��������");
        txnTypeList.add(si);
    }

    public List<String> getCheckInfoList() {
        return checkInfoList;
    }

    public void setCheckInfoList(List<String> checkInfoList) {
        this.checkInfoList = checkInfoList;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public List<SelectItem> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<SelectItem> txnTypeList) {
        this.txnTypeList = txnTypeList;
    }
}
