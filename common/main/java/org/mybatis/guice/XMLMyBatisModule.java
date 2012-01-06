/*
 *    Copyright 2010 The myBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.guice;

import org.apache.ibatis.ognl.DefaultMemberAccess;
import org.apache.ibatis.ognl.OgnlContext;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.apache.ibatis.io.Resources.getResourceAsReader;
import static org.apache.ibatis.ognl.Ognl.getValue;

/**
 * Easy to use helper Module that alleviates users to write the boilerplate
 * google-guice bindings to create the SqlSessionFactory, via XML configuration.
 *
 * @version $Id: XMLMyBatisModule.java 3939 2011-10-10 14:33:43Z jeffgbutler $
 */
public abstract class XMLMyBatisModule extends AbstractMyBatisModule {

    private static final String DEFAULT_CONFIG_RESOURCE = "mybatis-config.xml";

    private static final String DEFAULT_ENVIRONMENT_ID = "development";

    private static final String KNOWN_MAPPERS = "mapperRegistry.knownMappers";

    private static final String TYPE_HANDLERS = "typeHandlerRegistry.TYPE_HANDLER_MAP.values()";

    private static final String INTERCEPTORS = "interceptorChain.interceptors";

    private String classPathResource = DEFAULT_CONFIG_RESOURCE;

    private String environmentId = DEFAULT_ENVIRONMENT_ID;

    private Properties properties = new Properties();

    /**
     * Set the MyBatis configuration class path resource.
     *
     * @param classPathResource the MyBatis configuration class path resource
     */
    protected final void setClassPathResource(String classPathResource) {
        if (classPathResource == null) {
            throw new IllegalArgumentException("Parameter 'classPathResource' must be not null");
        }
        this.classPathResource = classPathResource;
    }

    /**
     * Set the MyBatis configuration environment id.
     *
     * @param environmentId the MyBatis configuration environment id
     */
    protected final void setEnvironmentId(String environmentId) {
        if (environmentId == null) {
            throw new IllegalArgumentException("Parameter 'environmentId' must be not null");
        }
        this.environmentId = environmentId;
    }

    /**
     * Add the variables will be used to replace placeholders in the MyBatis configuration.
     *
     * @param properties the variables will be used to replace placeholders in the MyBatis configuration
     */
    protected final void addProperties(Properties properties) {
        if (properties != null) {
            this.properties.putAll(properties);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final void internalConfigure() {
        this.initialize();

        Reader reader = null;
        try {
            reader = getResourceAsReader(this.getClass().getClassLoader(), this.classPathResource);

            //zhanrui 2011-12-28
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,
                    this.environmentId,
                    this.properties);

            binder().bind(SqlSessionFactory.class).toInstance(sessionFactory);

            Configuration configuration = sessionFactory.getConfiguration();

            OgnlContext context = new OgnlContext();
            context.setMemberAccess(new DefaultMemberAccess(true, true, true));
            context.setRoot(configuration);

            // bind mappers
            @SuppressWarnings("unchecked")
            Set<Class<?>> mapperClasses = (Set<Class<?>>) getValue(KNOWN_MAPPERS, context, configuration);
            for (Class<?> mapperType : mapperClasses) {
                bindMapper(mapperType);
            }

            // request injection for type handlers
            @SuppressWarnings("unchecked")
            Collection<Map<JdbcType, TypeHandler<?>>> mappedTypeHandlers = (Collection<Map<JdbcType, TypeHandler<?>>>) getValue(TYPE_HANDLERS, context, configuration);
            for (Map<JdbcType, TypeHandler<?>> mappedTypeHandler: mappedTypeHandlers) {
                for (TypeHandler<?> handler : mappedTypeHandler.values()) {
                    binder().requestInjection(handler);
                }
            }

            // request injection for interceptors
            @SuppressWarnings("unchecked")
            Collection<Interceptor> interceptors = (Collection<Interceptor>) getValue(INTERCEPTORS, context, configuration);
            for (Interceptor interceptor : interceptors) {
                binder().requestInjection(interceptor);
            }
        } catch (Exception e) {
            binder().addError("sible to read classpath resource '%s', see nested exceptions: %s",
                    classPathResource,
                    e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // close quietly
                }
            }
        }
    }

}
