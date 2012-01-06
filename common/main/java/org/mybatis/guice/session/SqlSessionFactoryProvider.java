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
package org.mybatis.guice.session;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Builds the SqlSessionFactory ant let google-guice injects his components.
 *
 * @version $Id: SqlSessionFactoryProvider.java 3605 2011-01-28 15:24:32Z simone.tripodi $
 */
@Singleton
public final class SqlSessionFactoryProvider implements Provider<SqlSessionFactory> {

    /**
     * The SqlSessionFactory reference.
     */
    private SqlSessionFactory sqlSessionFactory;

    /**
     * @since 1.0.1
     */
    public SqlSessionFactoryProvider() {
        
    }

    /**
     * Creates a new SqlSessionFactory from the specified configuration.
     *
     * @param configuration the specified configration.
     */
    @Deprecated
    public SqlSessionFactoryProvider(final Configuration configuration) {
        // do nothing
    }

    /**
     * Creates a new SqlSessionFactory from the specified configuration.
     *
     * @param configuration the specified configuration.
     * @since 1.0.1
     */
    @Inject
    public void createNewSqlSessionFactory(final Configuration configuration) {
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    /**
     * {@inheritDoc}
     */
    public SqlSessionFactory get() {
        return this.sqlSessionFactory;
    }

}
