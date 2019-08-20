package com.icbc.provider.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kaiya Xiong
 * @date 2019-08-14
 */

@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.druid") //用于把applicatio.properties和class、datasource、properties的数据关系绑定
    @Bean(initMethod = "init", destroyMethod = "close") //在spring boot容器启动后 datasource也要跟着启动 销毁时同理
    public DruidDataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return druidDataSource;
    }

    @Bean
    public Filter statFilter() {
        StatFilter statFilter = new StatFilter();
        //设置慢日志的时间 5000 1比较好查看
        statFilter.setSlowSqlMillis(1);
        //是否打印出慢日志
        statFilter.setLogSlowSql(true);
        //是否合并打印的慢日志
        statFilter.setMergeSql(true);
        return statFilter;
    }

    //设置druid数据连接池的监控平台链接
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }

}
