package com.message.service.config;

import com.message.service.util.CommonUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Victor on 17-6-21.
 */
@Configuration
@MapperScan(basePackages = CommonUtil.DATA_CENTER_MAPPER_SCAN_PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory2"/*,sqlSessionTemplateRef = "sqlSession2"*/)
public class DataCenterFactoryConfig {

    @Bean(name = "dsForDataCenter")
    @ConfigurationProperties(prefix = CommonUtil.DATA_CENTER_ENVIRONMENT_PREFIX)
    public DataSource dsForDataCenter() {
        return DataSourceBuilder.create().type(BasicDataSource.class).build();
    }


    @Bean(name = "transactionManager2")
    public DataSourceTransactionManager transactionManager2() {
        return new DataSourceTransactionManager(dsForDataCenter());
    }

    @Bean("sqlSessionFactory2")
    public SqlSessionFactoryBean sqlSessionFactory2() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory2 = new SqlSessionFactoryBean();
        sqlSessionFactory2.setDataSource(dsForDataCenter());
        sqlSessionFactory2.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(CommonUtil.MYBATIS_CONFIG_FILE));
        sqlSessionFactory2.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(CommonUtil.DATA_CENTER_MAPPER_XML_LOCATIONS));
        return sqlSessionFactory2;
    }

    @Bean("sqlSession2")
    public SqlSessionTemplate sqlSession2() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory2().getObject());
        return template;
    }
}
