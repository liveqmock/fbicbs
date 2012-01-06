package cbs.batch.ac.bcb8512;

import cbs.batch.ac.bcb8512.dao.BCB8512Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.code.model.Actirt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-10
 * Time: 9:48:16
 * 生成当前利率
 */
@Service
public class BCB8512Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8512Handler.class);
    @Inject
    private BCB8512Mapper mapper;
    @Inject
    private BatchSystemService systemService;
    private List<Actirt> irtList;
    private String crndat;
    private int wkDitcst;
    private BigDecimal wkNxtval;

    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("BCB8512 生成当前利率 BEGIN");
        try {
            initProps();
            mapper.deleteCir();
            if (irtList != null && !irtList.isEmpty()) {
                for (Actirt irt : irtList) {
                    if (ACEnum.TRMUNT_YEAR.getStatus().equalsIgnoreCase(irt.getTrmunt())) {
                        this.wkDitcst = 100;
                    }
                    if (ACEnum.TRMUNT_MONTH.getStatus().equalsIgnoreCase(irt.getTrmunt())) {
                        this.wkDitcst = 1000;
                    }
                    if ("D".equalsIgnoreCase(irt.getTrmunt())) {
                        this.wkDitcst = 10000;
                    }
                    //  RoundingMode.DOWN
                    this.wkNxtval = irt.getIrtval().divide(new BigDecimal(irt.getIrttrm() * wkDitcst + ""), 15, RoundingMode.DOWN);

                    logger.info(irt.getCurcde() + "," + irt.getIrtkd1() + "," + irt.getIrtkd2());
                    if(irt.getModflg() == null) {
                        irt.setModflg("");
                    }
                    mapper.insertCir(irt.getCurcde(), irt.getIrtkd1(), irt.getIrtkd2(), this.wkNxtval,
                            irt.getEffdat(), irt.getModflg(), irt.getIrtval());

                    if (ACEnum.MODFLG_TRUE.getStatus().equalsIgnoreCase(irt.getModflg())) {
                        BigDecimal cirNxtirt = mapper.selectNxtirt(wkDitcst, irt.getCurcde(), irt.getIrtkd1(), irt.getIrtkd2(),
                                this.crndat);
                        if(cirNxtirt == null) cirNxtirt = new BigDecimal(0);
                        mapper.updateCir(wkNxtval, cirNxtirt, irt.getCurcde(), irt.getIrtkd1(), irt.getIrtkd2());
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("BCB8512 生成当前利率 END");
    }

    protected void initBatch(final BatchParameterData batchParam) {
    }

    private void initProps() {
        wkDitcst = 0;
        wkNxtval = new BigDecimal(0);
        crndat = systemService.getBizDate();
        irtList = mapper.selectIrtsByFlg(ACEnum.CURFLG_TRUE.getStatus());
    }
}
