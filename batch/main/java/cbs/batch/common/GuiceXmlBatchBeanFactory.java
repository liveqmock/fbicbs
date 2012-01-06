package cbs.batch.common;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.mybatis.guice.XMLMyBatisModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.name.Names.bindProperties;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-25
 * Time: …œŒÁ9:22
 * To change this template use File | Settings | File Templates.
 */
public class GuiceXmlBatchBeanFactory implements BatchBeanFactory {
    private Injector injector;

    public AbstractBatchJobLogic getAbstractBatchJobLogic(String configFileName, String executeClassName) {
        List<Module> modules = this.createMyBatisBaseModule(configFileName, executeClassName);

        try {
            final Class clazz = Class.forName(executeClassName);
            modules.add(new Module() {
                public void configure(Binder binder) {
                    bindProperties(binder, createBatisProperties());
                    binder.bind(AbstractBatchJobLogic.class).to(clazz);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Guice ≥ı ºªØ¥ÌŒÛ°£", e);
        }

        this.injector = createInjector(modules);
        return this.injector.getInstance(AbstractBatchJobLogic.class);
    }

    protected List<Module> createMyBatisBaseModule(final String applicationContext, final String executeClassName) {
        List<Module> modules = new ArrayList<Module>(2);
        modules.add(new XMLMyBatisModule() {

            @Override
            protected void initialize() {
                setEnvironmentId("development");
                setClassPathResource(applicationContext);
            }

        });
        return modules;
    }

    protected Module createMyBatisExtraModule(final String applicationContext) {
        return (new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                //setEnvironmentId("development");
                setClassPathResource(applicationContext);
            }
        });
    }

    protected Properties createBatisProperties() {
        return new Properties();
    }
}
