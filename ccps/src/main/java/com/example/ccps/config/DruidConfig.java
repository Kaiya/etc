//package com.example.ccps.config;
//
//import com.alibaba.druid.filter.Filter;
//import com.alibaba.druid.filter.stat.StatFilter;
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import com.google.common.collect.Lists;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class DruidConfig {
//    @ConfigurationProperties(prefix = "spring.druid") //用于把applicatio.properties和class、datasource、properties的数据关系绑定
//    @Bean(initMethod = "init",destroyMethod = "close") //在spring boot容器启动后 datasource也要跟着启动 销毁时同理
//    public DruidDataSource dataSource(){
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setProxyFilters(Lists.newArrayList(statFilter()));
//        return druidDataSource;
//    }
//
//    @Bean
//    public Filter statFilter(){
//        StatFilter statFilter = new StatFilter();
//        //设置慢日志的时间 5000 1比较好查看
//        statFilter.setSlowSqlMillis(1);
//        //是否打印出慢日志
//        statFilter.setLogSlowSql(true);
//        //是否合并打印的慢日志
//        statFilter.setMergeSql(true);
//        return statFilter;
//    }
//    //设置druid数据连接池的监控平台链接
//    @Bean
//    public ServletRegistrationBean servletRegistrationBean(){
//        return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
//    }
//}


package com.example.ccps.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return  new DruidDataSource();
    }

    //配置Druid的监控
    //1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();

        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("allow","");//默认就是允许所有访问
//        initParams.put("deny","192.168.15.21");

        bean.setInitParameters(initParams);
        return bean;
    }


    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));

        return  bean;
    }
}