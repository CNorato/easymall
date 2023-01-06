package com.norato.easymall.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        //创建mybatis-plus拦截器
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //创建mybatis-plus内部拦截器  及具体拦截操作
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());//分页拦截器
        /*
         * 后面需要更多的拦截操作只需要按上面添加即可
         */
        return interceptor;
    }
}

