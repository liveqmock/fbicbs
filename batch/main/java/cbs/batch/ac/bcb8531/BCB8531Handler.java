package cbs.batch.ac.bcb8531;

import cbs.batch.ac.bcb8531.dao.BCB8531Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.dao.ActcglMapper;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actcgl;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-1
 * Time: 10:43:52
 * 修改当前总帐 ACTGLF 表更新已删除   需要输入命令行参数
 */
@Service
public class BCB8531Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8531Handler.class);
    @Inject
    private BCB8531Mapper mapper;
    @Inject
    private ActcglMapper cglMapper;
    private String validRecsts = ACEnum.RECSTS_VALID.getStatus();
    private List<Actact> actList;
    private SctBean sct;
    private String oldOrgidt;
    private String oldActglc;
    private String oldCurcde;
    private Actcgl cgl;
    private Actact act;
    private long sumDrbala = 0;
    private long sumCrbala = 0;
    private long sumDramnt = 0;
    private long sumCramnt = 0;
    private long sumDrcunt = 0;
    private long sumCrcunt = 0;
    private String inBuf;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("BCB8531 修改当前总帐  BEGIN");
        try {
            // 命令行参数
            String[] args = parameterData.getCommandLine();
            if (args == null || args.length < 1) {
                logger.error("命令行参数不能为空！");
                return;
            }
            inBuf = parameterData.getCommandLine()[0];
            initProps();
            if (sct == null) {
                logger.info("SCT  NOT   FOUND");
                return;
            }
            if (this.actList != null && !actList.isEmpty()) {
                int wkFlag = 0;
                int actIndex = 0;
                act = actList.get(actIndex);
                oldOrgidt = act.getOrgidt();
                oldActglc = act.getActglc();
                oldCurcde = act.getCurcde();
                while (wkFlag == 0) {
                    logger.info("act.getOrgidt =" + act.getOrgidt() +
                            " act.getActglc =" + act.getActglc() + " act.getCurcde =" + act.getCurcde());
                    if (!act.getOrgidt().equalsIgnoreCase(oldOrgidt) ||
                            !act.getActglc().equalsIgnoreCase(oldActglc) ||
                            !act.getCurcde().equalsIgnoreCase(oldCurcde)) {
                        setCgl();
                    }
                    if (act.getBokbal() < 0) {
                        sumDrbala = sumDrbala + act.getBokbal();
                    } else {
                        sumCrbala = sumCrbala + act.getBokbal();
                    }
                    sumDramnt = sumDramnt + act.getDdramt();
                    sumCramnt = sumCramnt + act.getDcramt();
                    sumDrcunt = sumDrcunt + act.getDdrcnt();
                    sumCrcunt = sumCrcunt + act.getDcrcnt();
                    actIndex++;
                    if (actIndex >= actList.size()) {
                        wkFlag = 1;
                        setCgl();
                    } else {
                        act = actList.get(actIndex);
                    }
                }
            } else {
                logger.info("ACTACT NOT FOUND");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("BCB8531 修改当前总帐  END");
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    private void initProps() {
        sct = mapper.selectSctByNum((short) 8);
        actList = mapper.selectActBySts(validRecsts, ACEnum.ACTSTS_VALID.getStatus());
    }

    // 5171

    private void setCgl() {
        setCglByTyp("D");
        setCglByTyp("M");
        setCglByTyp("S");
        setCglByTyp("Y");
        mapper.updateCgl(sumDrbala, sumCrbala, sumDramnt, sumCramnt, sumDrcunt, sumCrcunt,
                sct.getCrndat(), sct.getCrndat(), oldOrgidt, oldActglc, oldCurcde, "D");
        if ("Y".equalsIgnoreCase(inBuf)) {
            mapper.updateCglForAdd(sumDrbala, sumCrbala, sumDramnt, sumCramnt, sumDrcunt, sumCrcunt,
                    sct.getCrndat(), sct.getWorkMDate(), oldOrgidt, oldActglc, oldCurcde, "M");
            mapper.updateCglForAdd(sumDrbala, sumCrbala, sumDramnt, sumCramnt, sumDrcunt, sumCrcunt,
                    sct.getCrndat(), sct.getWorkSDate(), oldOrgidt, oldActglc, oldCurcde, "S");
            mapper.updateCglForAdd(sumDrbala, sumCrbala, sumDramnt, sumCramnt, sumDrcunt, sumCrcunt,
                    sct.getCrndat(), sct.getWorkYDate(), oldOrgidt, oldActglc, oldCurcde, "Y");
        }
        sumDrbala = 0;
        sumCrbala = 0;
        sumDramnt = 0;
        sumCramnt = 0;
        sumDrcunt = 0;
        sumCrcunt = 0;
        this.oldOrgidt = act.getOrgidt();
        this.oldActglc = act.getActglc();
        this.oldCurcde = act.getCurcde();
    }

    private void setCglByTyp(String rectyp) {
        cgl = mapper.selectCgl(oldOrgidt, oldActglc, oldCurcde, rectyp);
        if (cgl == null) {
            cgl = new Actcgl();
            long zero = 0L;
            cgl.setOrgidt(oldOrgidt);
            cgl.setGlcode(oldActglc);
            cgl.setCurcde(oldCurcde);
            cgl.setDrbala(zero);
            cgl.setCrbala(zero);
            cgl.setDlsbal(zero);
            cgl.setClsbal(zero);
            cgl.setDramnt(zero);
            cgl.setCramnt(zero);
            cgl.setDrcunt(0);
            cgl.setCrcunt(0);
            cgl.setDavbal(zero);
            cgl.setCavbal(zero);
            cgl.setRecsts(" ");
            cgl.setRectyp(rectyp);
            cgl.setCredat(sct.getCrndat());
            if ("D".equalsIgnoreCase(rectyp)) {
                cgl.setGldate(sct.getCrndat());
            } else if ("M".equalsIgnoreCase(rectyp)) {
                cgl.setGldate(sct.getWorkMDate());
            } else if ("S".equalsIgnoreCase(rectyp)) {
                cgl.setGldate(sct.getWorkSDate());
            } else if ("Y".equalsIgnoreCase(rectyp)) {
                cgl.setGldate(sct.getWorkYDate());
            }
            cglMapper.insert(cgl);
        }
    }

}
