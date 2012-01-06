package cbs.batch.common;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-25
 * Time: ����7:22
 * To change this template use File | Settings | File Templates.
 */
public class SpringXmlBatchBeanFactory implements BatchBeanFactory {

    public AbstractBatchJobLogic getAbstractBatchJobLogic(String configFileName, String executeClassName) {
        BeanFactory factory = getBeanFactory(configFileName);
        return (AbstractBatchJobLogic) getLogicInstance(executeClassName, factory);
    }

    private BeanFactory getBeanFactory(String configFileName) {

        BeanFactory factory = null;
        try {
            factory = new ClassPathXmlApplicationContext(configFileName);
        } catch (BeanDefinitionStoreException ex) {
            throw new RuntimeException("���л����������", ex);
        }
        return factory;
    }

    private Object getLogicInstance(String logicClass, BeanFactory factory) {
        Object logic = null;
        try {
            logic = factory.getBean(logicClass);
            return logic;
        } catch (NoSuchBeanDefinitionException ex) {
            throw new RuntimeException("�����ļ����ô���", ex);
        }
    }

}
