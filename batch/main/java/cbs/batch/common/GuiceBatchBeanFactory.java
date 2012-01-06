package cbs.batch.common;

import com.google.inject.Injector;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.name.Names.bindProperties;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-25
 * Time: 上午7:22
 * To change this template use File | Settings | File Templates.
 */
public class GuiceBatchBeanFactory implements BatchBeanFactory {
   private Injector injector;
    public AbstractBatchJobLogic getAbstractBatchJobLogic(String  configFileName, String executeClassName) {
        BeanFactory factory = getBeanFactory(configFileName);
        return (AbstractBatchJobLogic) getLogicInstance(executeClassName, factory);
    }

    private BeanFactory getBeanFactory(String configFileName) {

        BeanFactory factory = null;
        try {
            factory = new ClassPathXmlApplicationContext(configFileName);
        } catch (BeanDefinitionStoreException ex) {
            throw new RuntimeException("运行环境定义错误！", ex);
        }
        return factory;
    }

    private Object getLogicInstance(String logicClass, BeanFactory factory) {
        Object logic = null;

        try {
            logic = factory.getBean(logicClass);
            return logic;
        } catch (NoSuchBeanDefinitionException ex) {
            throw new RuntimeException("运行文件调用错误！", ex);
        }
    }

    private void setupMyBatisGuice() throws Exception {
        // bindings
        this.injector = createInjector(new MyBatisModule() {

                    @Override
                    protected void initialize() {
                        install(JdbcHelper.Oracle_Thin);

                        bindDataSourceProviderType(PooledDataSourceProvider.class);
                        bindTransactionFactoryType(JdbcTransactionFactory.class);
                        //addMapperClass(UserMapper.class);

                        bindProperties(binder(), createTestProperties());
                        //bind(FooService.class).to(FooServiceMapperImpl.class);
                    }

                }
        );

        // prepare the test db
        /*Environment environment = this.injector.getInstance(SqlSessionFactory.class).getConfiguration().getEnvironment();
        DataSource dataSource = environment.getDataSource();
        ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
        runner.setAutoCommit(true);
        runner.setStopOnError(true);
        runner.runScript(getResourceAsReader("org/mybatis/guice/sample/db/database-schema.sql"));
        runner.runScript(getResourceAsReader("org/mybatis/guice/sample/db/database-test-data.sql"));
        runner.closeConnection();*/

        //this.fooService = this.injector.getInstance(FooService.class);
    }

    protected static Properties createTestProperties() {
        Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("mybatis.environment.id", "test");
        myBatisProperties.setProperty("JDBC.host", "localhost");
        myBatisProperties.setProperty("derby.create", "true");
        myBatisProperties.setProperty("JDBC.username", "cbs");
        myBatisProperties.setProperty("JDBC.password", "cbs");
        myBatisProperties.setProperty("JDBC.autoCommit", "false");
        return myBatisProperties;
    }

}
