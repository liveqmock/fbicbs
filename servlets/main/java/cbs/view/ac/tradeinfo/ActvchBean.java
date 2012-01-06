package cbs.view.ac.tradeinfo;

import cbs.common.IbatisManager;
import cbs.common.utils.MessageUtil;
import cbs.repository.account.ext.domain.ActvchModel;
import cbs.repository.account.maininfo.dao.ActvchMapper;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.model.Ptenudetail;
import cbs.repository.platform.model.PtenudetailExample;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.component.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2010-11-27
 * Time: 8:08:38
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@ViewScoped
public class ActvchBean {

    private static final Logger logger = LoggerFactory.getLogger(ActvchBean.class);
    private ActvchVO vo = new ActvchVO();
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private SqlSession session;
    private ActvchModel selectedRecord;
    private List<ActvchModel> vchList;
    private Map<String, String> voucherTypeMap;          // 凭证类型

    public void queryRecords() {
        session = ibatisManager.getSessionFactory().openSession();
        try {
            ActvchMapper mapper = session.getMapper(ActvchMapper.class);
            vchList = mapper.selectByCondition(vo.getTlrnum(), vo.getCusidt(), vo.getVchset(),
                    vo.getCurcde(), vo.getGlcode(), vo.getApcode(),
                    handleAmt(vo.getTxnamt()), vo.getAccode());
            if (vchList == null || vchList.size() == 0) {
                MessageUtil.addInfoWithClientID("msgs", "M546");
                return;
            } else {
                this.voucherTypeMap = initPtenudetailMap("VOUCHERTYPE");
                for (ActvchModel vch : vchList) {
                    vch.setAnacde(voucherTypeMap.get(vch.getAnacde()));
                }
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

    public ActvchModel getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(ActvchModel selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public List<ActvchModel> getVchList() {
        return vchList;
    }

    public void setVchList(List<ActvchModel> vchList) {
        this.vchList = vchList;
    }

    public ActvchVO getVo() {
        return vo;
    }

    public void setVo(ActvchVO vo) {
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
        if (amt != null && !"".equals(amt.trim())) {
            if (amt.endsWith(".") || !amt.contains(".")) {
                rtnAmt = amt + "00";
            } else if (amt.substring(amt.indexOf(".")).length() < 2) {
                rtnAmt = amt.replace(".", "") + "0";
            } else {
                rtnAmt = amt.replace(".", "");
            }
        }
        return rtnAmt;
    }

    private Map<String, String> initPtenudetailMap(String enutyp) {
        PtenudetailMapper detailMapper = session.getMapper(PtenudetailMapper.class);
        PtenudetailExample pe = new PtenudetailExample();
        pe.createCriteria().andEnutypeEqualTo(enutyp);
        pe.setOrderByClause("DISPNO");
        List<Ptenudetail> enudetailList = detailMapper.selectByExample(pe);
        Map<String, String> map = new HashMap<String, String>();
        for (Ptenudetail p : enudetailList) {
            map.put(p.getEnuitemvalue(), p.getEnuitemlabel());
        }
        closeSession(session);
        return map;
    }

    // 重置按钮

    public void reset() {
        if (vo != null) {
            vo.setAccode(null);
            vo.setApcode(null);
            vo.setCurcde(null);
            vo.setCusidt(null);
            vo.setGlcode(null);
            vo.setTlrnum(null);
            vo.setTxnamt(null);
            vo.setVchset(null);
        }
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
