package cbs.batch.ac.bcb8543;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-14
 * Time: 15:05:07
 * To change this template use File | Settings | File Templates.
 */
public class IblPojo {
    private long sumCinbal;
    private long sumDinbal;
    private BigDecimal sumCinbalIrt;
    private BigDecimal sumDinbalIrt;

    public long getSumCinbal() {
        return sumCinbal;
    }

    public void setSumCinbal(long sumCinbal) {
        this.sumCinbal = sumCinbal;
    }

    public long getSumDinbal() {
        return sumDinbal;
    }

    public void setSumDinbal(long sumDinbal) {
        this.sumDinbal = sumDinbal;
    }

    public BigDecimal getSumCinbalIrt() {
        return sumCinbalIrt;
    }

    public void setSumCinbalIrt(BigDecimal sumCinbalIrt) {
        this.sumCinbalIrt = sumCinbalIrt;
    }

    public BigDecimal getSumDinbalIrt() {
        return sumDinbalIrt;
    }

    public void setSumDinbalIrt(BigDecimal sumDinbalIrt) {
        this.sumDinbalIrt = sumDinbalIrt;
    }
}
