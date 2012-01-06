package cbs.view.ac.accountinfo;

import cbs.common.IbatisManager;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.model.Actact;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-11-1
 * Time: 15:56:09
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@RequestScoped
public class ActInfo {
    int count = -1;
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;

    private static final Logger logger = Logger.getLogger(ActInfo.class);

    public void run() {
        logger.info("run()...");
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActactMapper mapper = session.getMapper(ActactMapper.class);
            List<Actact> allRecords = mapper.selectByExample(null);
            count = allRecords.size();
            logger.info("list size = " + count);
        } finally {
            session.close();
        }
    }

    public int getCount() {
        run();
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }
}
