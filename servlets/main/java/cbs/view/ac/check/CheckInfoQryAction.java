package cbs.view.ac.check;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-12-6
 * Time: обнГ5:34
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class CheckInfoQryAction {

    private List<String> checkInfoList;

    public List<String> getCheckInfoList() {
        return checkInfoList;
    }

    public void setCheckInfoList(List<String> checkInfoList) {
        this.checkInfoList = checkInfoList;
    }
}
