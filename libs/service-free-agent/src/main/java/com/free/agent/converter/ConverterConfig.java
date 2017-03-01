package com.free.agent.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

/**
 * Created by antonPC on 28.02.17.
 */
@Configuration
public class ConverterConfig {

    @Bean
    public ConversionService getConversionService(Set<Converter<?, ?>> converters) {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(converters);
        bean.afterPropertiesSet();
        return bean.getObject();
    }

}
