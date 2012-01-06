package cbs.batch.ac.bcb8526;

import cbs.batch.ac.bcb8526.dao.BCB8526Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.model.Actact;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-2-27
 * Time: 14:15:58
 * 核对ACTACT与ACTPLE
 */
@Service
public class BCB8526Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8526Handler.class);

    private BatchResultFileHandler fileHandler;
    @Inject
    private BCB8526Mapper mapper;
    @Inject
    private BatchSystemService systemService;
    private List<PleccyBean> pleccyList;
    private List<Actact> actList;
    private String validRecsts = ACEnum.RECSTS_VALID.getStatus();
    private String sysidtAC;
    private short decpos;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            logger.info("BCB8526 核对ACTACT与ACTPLE BEGIN");
            this.pleccyList = mapper.qryPleccy(validRecsts, validRecsts, sysidtAC);
            this.actList = mapper.qryActByTyp(validRecsts, sysidtAC, ACEnum.ACTTYP_PLE.getStatus());
            this.fileHandler.setFileTitle("------------------核对ACTACT与ACTPLE------------------");
            this.fileHandler.setSpaces("       ");
            this.fileHandler.setFileHeadByArray(new String[]{"ORGIDT", "CUSIDT", "APCODE",
                    "CURCDE", "BOKBAL", "AVABAL", "DIFF"});
            if (pleccyList != null && !this.pleccyList.isEmpty()) {
                int wkStop = 0;
                int pleIndex = 0;
                int actIndex = 0;
                PleccyBean pleccy = pleccyList.get(pleIndex);
                Actact act = actList.get(actIndex);
                while (wkStop == 0) {
                    String pleActnum = pleccy.getCusidt() + pleccy.getApcode() + pleccy.getCurcde();
                    String actActnum = act.getCusidt() + act.getApcode() + act.getCurcde();
                    if ((pleccy.getOrgidt().compareToIgnoreCase(act.getOrgidt()) > 0)
                            || (pleccy.getOrgidt().equalsIgnoreCase(act.getOrgidt()) && pleActnum.compareToIgnoreCase(actActnum) > 0)) {
                        if (act.getBokbal() != 0) {
                            logger.info(act.getOrgidt() + "," + actActnum + " NOT IN ACTPLE");
                        }
                        actIndex++;
                        if (actIndex >= actList.size()) {
                            pleIndex++;
                            pleccy = pleccyList.get(pleIndex);
                            logger.info(pleccy.getOrgidt() + "," + pleActnum + " NOT IN ACTACT");
                            wkStop = 1;
                        } else {
                            act = actList.get(actIndex);
                        }
                    }
                    if ((pleccy.getOrgidt().compareToIgnoreCase(act.getOrgidt()) < 0)
                            || (pleccy.getOrgidt().equalsIgnoreCase(act.getOrgidt()) && pleActnum.compareToIgnoreCase(actActnum) < 0)) {
                        if (pleccy.getSumAvabal() != 0) {
                            logger.info(pleccy.getOrgidt() + "," + pleActnum + " NOT IN ACTACT");
                        }
                        pleIndex++;
                        if (pleIndex >= pleccyList.size()) {
                            actIndex++;
                            act = actList.get(actIndex);
                            logger.info(act.getOrgidt() + "," + actActnum + " NOT IN ACTPLE");
                            wkStop = 1;
                        } else {
                            pleccy = pleccyList.get(pleIndex);
                        }
                    }
                    if (pleccy.getOrgidt().equalsIgnoreCase(act.getOrgidt()) && pleActnum.equalsIgnoreCase(actActnum)) {
                        if (act.getBokbal() != pleccy.getSumAvabal()) {
                            this.decpos = pleccy.getDecpos();
                            this.fileHandler.setFileBodyByArray(new String[]{pleccy.getOrgidt(), pleccy.getCusidt(),
                                    pleccy.getApcode(), pleccy.getCurcde(), String.valueOf(act.getBokbal()), String.valueOf(pleccy.getSumAvabal())});
                            logger.info(new StringBuffer(act.getOrgidt()).append(",").append(actActnum).append(",'A',")
                                    .append(String.valueOf(act.getBokbal())).append(",'P',").append(String.valueOf(pleccy.getSumAvabal())).toString());
                        }
                        pleIndex++;
                        if (pleIndex >= pleccyList.size()) {
                            actIndex++;
                            act = actList.get(actIndex);
                            logger.info(act.getOrgidt() + "," + actActnum + " NOT IN ACTPLE");
                            wkStop = 1;
                        } else {
                            pleccy = pleccyList.get(pleIndex);
                        }
                        actIndex++;
                        if (actIndex >= actList.size()) {
                            pleIndex++;
                            pleccy = pleccyList.get(pleIndex);
                            logger.info(pleccy.getOrgidt() + "," + pleActnum + " NOT IN ACTACT");
                            wkStop = 1;
                        } else {
                            act = actList.get(actIndex);
                        }
                    }
                }
            } else {
                logger.info("ACTPLE NO RECORD!");
                return;
            }
            logger.info("BCB8526 核对ACTACT与ACTPLE END");
            this.fileHandler.writeToFile();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {
        this.fileHandler = new BatchResultFileHandler("list8526.txt");
        this.sysidtAC = systemService.getSysidtAC();
    }
}
