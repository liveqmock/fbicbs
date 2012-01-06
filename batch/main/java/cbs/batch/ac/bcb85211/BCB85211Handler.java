package cbs.batch.ac.bcb85211;

import cbs.batch.ac.bcb85211.dao.BCB85211Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.utils.DataFormater;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.model.Actact;
import cbs.repository.account.maininfo.model.Actobf;
import cbs.repository.code.model.Actccy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-2-24
 * Time: 13:17:42
 * remark:核对 ACT 与 OBF
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB85211Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85211Handler.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    @Inject
    private BCB85211Mapper bcb85211Map;
    private List<Actact> actactList;
    private Actobf obf;
    private Short ccyDecpos = 0;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            initActactList();
            processChecking();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initActactList() throws Exception {
        actactList = bcb85211Map.selectActact(ACEnum.SYSIDT_AC.getStatus(), ACEnum.RECSTS_VALID.getStatus(), ACEnum.ACTSTS_VALID.getStatus());
    }
    /*
    * 核对数据*/

    private void processChecking() throws Exception  {
        for (Actact act : this.actactList) {
            Actccy actccy = this.bcb85211Map.selectActccyByPK(act.getCurcde());
            ccyDecpos = actccy.getDecpos();
            obf = this.bcb85211Map.selectActobfByPK(ACEnum.SYSIDT_AC.getStatus(), act.getOrgidt(), act.getCusidt(),
                    act.getApcode(), act.getCurcde());
            String actKey = ACEnum.SYSIDT_AC.getStatus() + " " + act.getOrgidt() + " " + act.getCusidt() +
                    " " + act.getApcode() + " " + act.getCurcde();
            if (obf == null) {
                logger.info("THIS ACCOUNT NOT IN OBF !");
                logger.info(actKey);
            } else {
                if (act.getBokbal() != obf.getBokbal()) {
                    long diff = act.getBokbal() - obf.getBokbal();
                    String diffFormat = DataFormater.formatNum(ccyDecpos, diff);
                    logger.info(actKey + " BAL OF ACT  <> OBF,DIFF = " + diffFormat);
                }
                if (act.getActsts() != obf.getActsts()) {
                    logger.info(actKey + " ACTSTS OF ACT <> ACTSTS OF OBF");
                }
                if (act.getAvabal() != obf.getAvabal()) {
                    long diff = act.getAvabal() - obf.getAvabal();
                    String diffFormat = DataFormater.formatNum(ccyDecpos, diff);
                    logger.info(actKey + " AVABAL OF ACT <> OBF,DIFF=" + diffFormat);
                }
                if (act.getCifbal() != obf.getCifbal()) {
                    logger.info(actKey + " CIFBAL OF ACT <> CIFBAL OF OBF");
                }
                if (act.getDifbal() != obf.getDifbal()) {
                    logger.info(actKey + " DIFBAL OF ACT <> DIFBAL OF OBF");
                }
                if (act.getFrzsts() != obf.getFrzsts()) {
                    logger.info(actKey + " FRZSTS OF ACT <> FRZSTS OF OBF");
                }
                if (act.getOvelim() != obf.getOvelim()) {
                    logger.info(actKey + " OVELIM OF ACT <> OVELIM OF OBF");
                }
                String actOveexp = sdf.format(act.getOveexp());
                if (!actOveexp.equals(obf.getOveexp())) {
                    logger.info(actKey + " OVEEXP OF ACT <> OVEEXP OF OBF");
                }
            }

        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    public void setBcb85211Map(BCB85211Mapper bcb85211Map) {
        this.bcb85211Map = bcb85211Map;
    }
}
