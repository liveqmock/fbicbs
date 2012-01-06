package cbs.batch.ac.getirt;

import cbs.batch.ac.getirt.dao.GetirtMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.code.model.Actirt;
import cbs.common.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 处理利息表actirt的当天使用字段(Curflg)
 */
@Service("GetirtHandler")
public class GetirtHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(GetirtHandler.class);
    @Inject
    private GetirtMapper mapper;
    private Date wkCrndat;
    private SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
    private int wkEndFlag;
    private List<Actirt> irtList;
    private String wkCurcde;
    private String wkIrtkd1;
    private String wkIrtkd2;
    private BigDecimal wkIrtval;
    private Date wkEffdat;

    private String tmpCurcde;
    private String tmpIrtkd1;
    private String tmpIrtkd2;
    private BigDecimal tmpIrtval;
    private BigDecimal tmpIrtval1;
    private Date tmpEffdat;

    private String wk1Curcde;
    private String wk1Irtkd1;
    private String wk1Irtkd2;
    private BigDecimal wk1Irtval;
    private Date wk1Effdat;

    private int irtIndex = 0;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        //事务处理
        try {
            wkCrndat = sdf8.parse(SystemService.getBizDate());
            mapper.updateIrtCurflg();
            wkEndFlag = 0;
            irtList = mapper.qryIrts(ACEnum.RECSTS_VALID.getStatus(), wkCrndat);
            if (irtList == null || irtList.isEmpty()) {
                throw new RuntimeException("查询ACTIRT结果为空");
            }
            // GET THE FIRST RECORD
            Actirt irt = irtList.get(0);
            wkCurcde = irt.getCurcde();
            wkIrtkd1 = irt.getIrtkd1();
            wkIrtkd2 = irt.getIrtkd2();
            wkIrtval = irt.getIrtval();
            wkEffdat = irt.getEffdat();
            tmpCurcde = wkCurcde;
            tmpIrtkd1 = wkIrtkd1;
            tmpIrtkd2 = wkIrtkd2;
            tmpIrtval = wkIrtval;
            tmpIrtval1 = wkIrtval;
            tmpEffdat = wkEffdat;
            irtIndex = 1;
            while (wkEndFlag != 1) {
                process1();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 5100-process1
    private void process1() {
        if (irtIndex >= irtList.size()) {
            wk1Curcde = tmpCurcde;
            wk1Irtkd1 = tmpIrtkd1;
            wk1Irtkd2 = tmpIrtkd2;
            wk1Irtval = tmpIrtval;
            wk1Effdat = tmpEffdat;
            mapper.updateIrtCurflgTo1(wk1Curcde, wk1Irtkd1, wk1Irtkd2, wk1Effdat);
            wkEndFlag = 1;
            return;
        }
        Actirt actirt = irtList.get(irtIndex);
        wkCurcde = actirt.getCurcde();
        wkIrtkd1 = actirt.getIrtkd1();
        wkIrtkd2 = actirt.getIrtkd2();
        wkIrtval = actirt.getIrtval();
        wkEffdat = actirt.getEffdat();
        if (!wkCurcde.equals(tmpCurcde) || !wkIrtkd1.equals(tmpIrtkd1) || !wkIrtkd2.equals(tmpIrtkd2)) {
            wk1Curcde = tmpCurcde;
            wk1Irtkd1 = tmpIrtkd1;
            wk1Irtkd2 = tmpIrtkd2;
            wk1Irtval = tmpIrtval;
            wk1Effdat = tmpEffdat;
            mapper.updateIrtCurflgTo1(wk1Curcde, wk1Irtkd1, wk1Irtkd2, wk1Effdat);
        }
        tmpCurcde = wkCurcde;
        tmpIrtkd1 = wkIrtkd1;
        tmpIrtkd2 = wkIrtkd2;
        tmpIrtval = wkIrtval;
        tmpIrtval1 = wkIrtval;
        tmpEffdat = wkEffdat;
        irtIndex++;
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }
}