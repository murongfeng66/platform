package com.jwzhu.platform.plugs.jsonEscape.base;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.jwzhu.platform.plugs.jsonEscape.bind.JsonEscaper;

/**
 * JSON转义序列化器扫描器
 */
public class JsonEscaperScanner extends ClassPathBeanDefinitionScanner {

    private static Logger logger = LoggerFactory.getLogger(JsonEscaperScanner.class);

    JsonEscaperScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment, ResourceLoader resourceLoader) {
        super(registry, useDefaultFilters, environment, resourceLoader);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            logger.info("扫描到：{}",beanDefinitionHolder.getBeanName());
        }
        return beanDefinitionHolders;
    }

    @Override
    protected void registerDefaultFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(JsonEscaper.class));
    }
}
