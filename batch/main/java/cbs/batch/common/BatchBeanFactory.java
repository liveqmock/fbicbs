package cbs.batch.common;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-25
 * Time: ионГ9:14
 * To change this template use File | Settings | File Templates.
 */
public interface BatchBeanFactory {
    AbstractBatchJobLogic getAbstractBatchJobLogic(String configFileName, String executeClassName);
}
