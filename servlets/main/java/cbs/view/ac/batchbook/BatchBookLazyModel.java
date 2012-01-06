package cbs.view.ac.batchbook;

import cbs.common.IbatisManager;
import cbs.repository.account.ext.domain.BatchBookVO;
import cbs.repository.account.other.dao.ActtvcMapper;
import cbs.repository.account.other.model.Acttvc;
import org.apache.ibatis.session.SqlSession;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Map;

/**
 * 批量录入 （8401）
 * User: zhanrui
 * Date: 2010-11-10
 * Time: 16:52:00
 */

@ManagedBean
//@RequestScoped
@ViewScoped
public class BatchBookLazyModel {
    private static final Logger logger = LoggerFactory.getLogger(BatchBookLazyModel.class);

    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;

    private String orgidt;
    private String tlrnum;
    private short vchset;


    private Acttvc acttvc;
    private List<Acttvc> acttvcList;

    private BatchBookVO vo = new BatchBookVO();
    private List<BatchBookVO> voList;

    private LazyDataModel<Acttvc> lazyModel;

    @PostConstruct
    public void init() {

        //TODO
        this.orgidt = "010";
        this.tlrnum = "SYS1";
        this.vchset = 1111;

        //vo = new BatchBookVO();
        //voList = new ArrayList<BatchBookVO>();
/*
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActtvcMapper dao = session.getMapper(ActtvcMapper.class);
            acttvcList = dao.selectByExample(null);
        } finally {
            session.close();
        }
*/

        lazyModel = new LazyDataModel<Acttvc>() {
            @Override
            public List<Acttvc> load(int first, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters) {
                logger.info("Loading the lazy car data between {} and {}", first, first + pageSize);
                //List<Acttvc> lazyRecords = new ArrayList<Acttvc>();
                //populateLazyRecords(lazyRecords, pageSize, first);
                first++;
                List<Acttvc> lazyRecords = queryLazyRecords(pageSize, first);
//                voList = new ArrayList<BatchBookVO>();
//                populateVORecords(voList, lazyRecords);

                return lazyRecords;
            }
        };

        lazyModel.setRowCount(queryCountOfRecords());


    }

    /**
     * 组VIEW 对象
     *
     * @param voList
     * @param lazyRecords
     */
    private void populateVORecords(List<BatchBookVO> voList, List<Acttvc> lazyRecords) {
        for (Acttvc acttvc : lazyRecords) {
            BatchBookVO vo = new BatchBookVO();
            vo.setActno(acttvc.getCusidt() + acttvc.getApcode() + acttvc.getCurcde());
            vo.setType(acttvc.getRvslbl());
            vo.setTxnamt(acttvc.getTxnamt());
            voList.add(vo);
        }
    }

/*
    private void populateLazyRecords(List<Acttvc> lazyRecords, int pageSize, int first) {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActtvcMapper dao = session.getMapper(ActtvcMapper.class);
            lazyRecords = dao.selectByExample(null);
        } finally {
            session.close();
        }
    }
*/

    /**
     * 查询数据库 获得当前套内的记录总数
     * @return
     */
    private Integer queryCountOfRecords() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        Integer count;
        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            count = mapper.countValidRecordOfVchset(this.orgidt, this.tlrnum, this.vchset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        if (count == null) {
            return 0;
        }else{
            return count;
        }
    }

    private List<Acttvc> queryLazyRecords(int pageSize, int first) {
        List<Acttvc> lazyRecords;
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
//            lazyRecords = dao.selectByExample(null);
            lazyRecords = mapper.selectPagedRecords(first, first + pageSize, this.orgidt, this.tlrnum, this.vchset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return lazyRecords;
    }

    /**
     * 增加新纪录
     * @return
     */
    public String createNewRecord() {
//        voList.add(vo);
//        this.vo = new BatchBookVO();

        acttvc = new Acttvc();
        //TODO 机构号 柜员号

        acttvc.setOrgidt(this.orgidt);
        acttvc.setTlrnum(this.tlrnum);

        String actno = vo.getActno();
        acttvc.setCusidt(actno.substring(0, 7));
        acttvc.setApcode(actno.substring(7, 11));
        acttvc.setCurcde(actno.substring(11));

//        short vchset = 1234;
        acttvc.setVchset(vchset);
//        acttvc.setSetseq((short)56);

        acttvc.setTxnamt((long) 1231223);

        SqlSession session = ibatisManager.getSessionFactory().openSession();

        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            Integer setseq = mapper.selectMaxSetseq(this.orgidt, this.tlrnum, this.vchset);
            if (setseq == null) {
                setseq = 0;
            } else {
                if (setseq >= 99) {
                    //TODO Business Exception
                    throw new RuntimeException("笔数超过范围：99笔");
                }
            }
            setseq++;
            acttvc.setSetseq(setseq.shortValue());

            mapper.insert(acttvc);
            session.commit();
        } finally {
            session.close();
        }

        int count = queryCountOfRecords();
        lazyModel.setRowCount(count);
        int pagesize = lazyModel.getPageSize();
        int startnum = (count/pagesize)*pagesize;
        lazyModel.load(startnum,pagesize,null,false,null);
        return null;
    }

    public void createNew() {
//        voList.add(vo);
//        this.vo = new BatchBookVO();

        acttvc = new Acttvc();
        //TODO 机构号 柜员号

        acttvc.setOrgidt(this.orgidt);
        acttvc.setTlrnum(this.tlrnum);

        String actno = vo.getActno();
        acttvc.setCusidt(actno.substring(0, 7));
        acttvc.setApcode(actno.substring(7, 11));
        acttvc.setCurcde(actno.substring(11));

//        short vchset = 1234;
        acttvc.setVchset(vchset);
//        acttvc.setSetseq((short)56);

        acttvc.setTxnamt((long) 1231223);

        SqlSession session = ibatisManager.getSessionFactory().openSession();

        try {
            ActtvcMapper mapper = session.getMapper(ActtvcMapper.class);
            Integer setseq = mapper.selectMaxSetseq(this.orgidt, this.tlrnum, this.vchset);
            if (setseq == null) {
                setseq = 0;
            } else {
                if (setseq >= 99) {
                    //TODO Business Exception
                    throw new RuntimeException("笔数超过范围：99笔");
                }
            }
            setseq++;
            acttvc.setSetseq(setseq.shortValue());

            mapper.insert(acttvc);
            session.commit();
        } finally {
            session.close();
        }

        int count = queryCountOfRecords();
        lazyModel.setRowCount(count);
        int pagesize = lazyModel.getPageSize();
        int startnum = (count/pagesize)*pagesize;
        lazyModel.load(startnum,pagesize,null,false,null);
        return;
    }


    public List<Acttvc> getActtvcList() {
        return acttvcList;
    }


    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }

    public Acttvc getActtvc() {
        return acttvc;
    }

    public void setActtvc(Acttvc acttvc) {
        this.acttvc = acttvc;
    }

    public BatchBookVO getVo() {
        return vo;
    }

    public void setVo(BatchBookVO vo) {
        this.vo = vo;
    }

    public List<BatchBookVO> getVoList() {
        return voList;
    }

    public void setVoList(List<BatchBookVO> voList) {
        this.voList = voList;
    }

    public LazyDataModel<Acttvc> getLazyModel() {
        return lazyModel;
    }

}
