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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Victor on 17-6-21.
 */
@Configuration
@MapperScan(basePackages = CommonUtil.NORMAL_MAPPER_SCAN_PACKAGE, sqlSessionFactoryRef = "sqlSessionFactory"/*, sqlSessionTemplateRef = "sqlSession"*/)
public class NormalFactoryConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = CommonUtil.NORMAL_ENVIRONMENT_PREFIX)
    @Primary
    public DataSource normalSource() {
        return DataSourceBuilder.create().type(BasicDataSource.class).build();
    }


    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(normalSource());
    }

    @Bean("sqlSessionFactory")
    @Primary
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(normalSource());
        sqlSessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(CommonUtil.MYBATIS_CONFIG_FILE));
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(CommonUtil.NORMAL_MAPPER_XML_LOCATIONS));
        return sqlSessionFactory;
    }

    @Bean("sqlSession")
    @Primary
    public SqlSessionTemplate sqlSession() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory().getObject());
        return template;
    }
}
