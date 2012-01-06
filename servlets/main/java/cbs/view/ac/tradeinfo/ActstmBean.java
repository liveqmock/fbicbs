package cbs.view.ac.tradeinfo;

import cbs.common.IbatisManager;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.ext.domain.ActnoName;
import cbs.repository.account.ext.domain.ActstmModel;
import cbs.repository.account.maininfo.dao.ActoacMapper;
import cbs.repository.account.maininfo.dao.ActstmMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.component.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2010-11-27
 * Time: 8:08:38
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ActstmBean {

    private static final Logger logger = LoggerFactory.getLogger(ActstmBean.class);
    private ActstmVO vo = new ActstmVO();
    private ActnoName act = new ActnoName();
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private ActstmModel selectedRecord;
    private List<ActstmModel> stmList;

    public void queryRecords() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActoacMapper oacMapper = session.getMapper(ActoacMapper.class);
            act = oacMapper.selectActnoNameByNo(vo.getAccode().trim());
            ActstmMapper mapper = session.getMapper(ActstmMapper.class);
            stmList = mapper.selectStmByCondition(vo.getTlrnum(), vo.getBgndat(), vo.getEnddat(),
                    vo.getAccode(), handleAmt(vo.getTxnamt()));
            if (stmList == null || stmList.size() == 0) {
                MessageUtil.addInfoWithClientID("msgs", "M546");
            }
            setTableToFirstPage("resultForm:resultDataTable");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询传票操作失败");
            MessageUtil.addErrorWithClientID("msgs", "M932");
        } finally {
            closeSession(session);
        }
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public ActstmModel getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(ActstmModel selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public ActnoName getAct() {
        return act;
    }

    public void setAct(ActnoName act) {
        this.act = act;
    }

    public List<ActstmModel> getStmList() {
        return stmList;
    }

    public void setStmList(List<ActstmModel> stmList) {
        this.stmList = stmList;
    }

    public ActstmVO getVo() {
        return vo;
    }

    public void setVo(ActstmVO vo) {
        this.vo = vo;
    }

    /**
     * 交易金额乘以10
     *
     * @param amt
     * @return
     */
    private String handleAmt(String amt) {
        String rtnAmt = null;
        if (amt != null && !StringUtils.isEmpty(amt.trim())) {
            if (!amt.contains(".")) {
                rtnAmt = amt + "00";
            } else if (amt.endsWith(".")) {
                rtnAmt = amt.replace(".", "") + "00";
            } else if (amt.substring(amt.indexOf(".")).length() == 2) {
                rtnAmt = amt.replace(".", "") + "0";
            } else {
                rtnAmt = amt.replace(".", "");
            }
        }
        return rtnAmt;
    }

    // 设置为第一页

    private void setTableToFirstPage(String formDT) {

        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(formDT);
        if (dataTable != null) {
            dataTable.setFirst(0);
            dataTable.setPage(1);
        }
    }
    // 关闭SqlSession

    private void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
            session = null;
        }
    }
}