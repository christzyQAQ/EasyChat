package com.easychat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.easychat.config.LongToTimestampTypeHandler;
import com.easychat.config.DateToTimestampTypeHandler;
import javax.sql.DataSource;


@EnableAsync
@SpringBootApplication(scanBasePackages = "com.easychat")
@MapperScan(basePackages = {"com.easychat.mappers"})
@EnableRedisRepositories
@EnableTransactionManagement
@EnableScheduling
public class EasyChatApplication {
public static void main(String[] args) {
    SpringApplication.run(EasyChatApplication.class, args);
}
    
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        
        // 注册自定义的类型处理器
        factoryBean.setTypeHandlers(new TypeHandler[] {
            new LongToTimestampTypeHandler(),
            new DateToTimestampTypeHandler()
        });
        
        return factoryBean.getObject();
    }
}
