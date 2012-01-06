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
package org.mybatis.guice.datasource.builtin;

import java.sql.SQLException;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;

/**
 * Provides the myBatis built-in PooledDataSource.
 *
 * @version $Id: PooledDataSourceProvider.java 3687 2011-03-16 13:37:53Z simone.tripodi $
 */
public final class PooledDataSourceProvider implements Provider<DataSource> {

    /**
     * The PooledDataSource reference.
     */
    private final PooledDataSource dataSource;

    /**
     * Creates a new PooledDataSource using the needed parameter.
     *
     * @param driver The JDBC driver class.
     * @param url the database URL of the form <code>jdbc:subprotocol:subname</code>.
     * @param username the database user.
     * @param password the user's password.
     */
    @Inject
    public PooledDataSourceProvider(@Named("JDBC.driver") final String driver,
            @Named("JDBC.url") final String url,
            @Named("JDBC.username") final String username,
            @Named("JDBC.password") final String password) {
        this.dataSource = new PooledDataSource(this.getClass().getClassLoader(), driver, url, username, password);
    }

    /**
     * 
     *
     * @param autoCommit
     */
    @com.google.inject.Inject(optional = true)
    public void setAutoCommit(@Named("JDBC.autoCommit") final boolean autoCommit) {
        this.dataSource.setDefaultAutoCommit(autoCommit);
    }

    /**
     * 
     *
     * @param loginTimeout
     */
    @com.google.inject.Inject(optional = true)
    public void setLoginTimeout(@Named("JDBC.loginTimeout") final int loginTimeout) {
        try {
            this.dataSource.setLoginTimeout(loginTimeout);
        } catch (SQLException e) {
            throw new RuntimeException("Impossible to set login timeout '"
                    + loginTimeout
                    + "' to Unpooled Data Source", e);
        }
    }

    @com.google.inject.Inject(optional = true)
    public void setDriverProperties(@Named("JDBC.driverProperties") final Properties driverProperties) {
        this.dataSource.setDriverProperties(driverProperties);
    }

    /**
     * 
     *
     * @param maximumActiveConnections
     */
    @com.google.inject.Inject(optional = true)
    public void setMaximumActiveConnections(@Named("mybatis.pooled.maximumActiveConnections") final int maximumActiveConnections) {
        this.dataSource.setPoolMaximumActiveConnections(maximumActiveConnections);
    }

    /**
     * 
     *
     * @param maximumCheckoutTime
     */
    @com.google.inject.Inject(optional = true)
    public void setMaximumCheckoutTime(@Named("mybatis.pooled.maximumCheckoutTime") final int maximumCheckoutTime) {
        this.dataSource.setPoolMaximumCheckoutTime(maximumCheckoutTime);
    }

    /**
     * 
     *
     * @param maximumIdleConnections
     */
    @com.google.inject.Inject(optional = true)
    public void setMaximumIdleConnections(@Named("mybatis.pooled.maximumIdleConnections") final int maximumIdleConnections) {
        this.dataSource.setPoolMaximumIdleConnections(maximumIdleConnections);
    }

    /**
     * 
     *
     * @param pingConnectionsNotUsedFor
     */
    @com.google.inject.Inject(optional = true)
    public void setPingConnectionsNotUsedFor(@Named("mybatis.pooled.pingConnectionsNotUsedFor") final int pingConnectionsNotUsedFor) {
        this.dataSource.setPoolPingConnectionsNotUsedFor(pingConnectionsNotUsedFor);
    }

    /**
     * 
     *
     * @param pingEnabled
     */
    @com.google.inject.Inject(optional = true)
    public void setPingEnabled(@Named("mybatis.pooled.pingEnabled") final boolean pingEnabled) {
        this.dataSource.setPoolPingEnabled(pingEnabled);
    }

    /**
     * 
     *
     * @param pingQuery
     */
    @com.google.inject.Inject(optional = true)
    public void setPingEnabled(@Named("mybatis.pooled.pingQuery") final String pingQuery) {
        this.dataSource.setPoolPingQuery(pingQuery);
    }

    /**
     * 
     *
     * @param timeToWait
     */
    @com.google.inject.Inject(optional = true)
    public void setTimeToWait(@Named("mybatis.pooled.timeToWait") final int timeToWait) {
        this.dataSource.setPoolTimeToWait(timeToWait);
    }

    /**
     * {@inheritDoc}
     */
    public DataSource get() {
        return this.dataSource;
    }

}
