package com.jwzhu.platform.plugs.jsonescape.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * JSON转义序列化器注册
 */
public class JsonEscaperRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware, ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(JsonEscaperRegister.class);
    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(JsonEscaperScan.class.getName()));

        List<String> basePackages = new ArrayList<>();
        if(annotationAttributes != null){
            for (String basePackage : annotationAttributes.getStringArray("basePackages")) {
                if (StringUtils.hasText(basePackage)) {
                    basePackages.add(basePackage);
                }
            }
        }

        if(basePackages.isEmpty()){
            basePackages.add(((StandardAnnotationMetadata) annotationMetadata).getIntrospectedClass().getPackage().getName());
        }


        JsonEscaperScanner scanner = new JsonEscaperScanner(beanDefinitionRegistry, true, environment, resourceLoader);
        Set<BeanDefinitionHolder> beanDefinitionHolders = scanner.doScan(StringUtils.toStringArray(basePackages));
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            logger.info("JSON转义序列化器：{}",beanDefinitionHolder.getBeanDefinition().getBeanClassName());
            beanDefinitionRegistry.registerBeanDefinition(beanDefinitionHolder.getBeanName(), beanDefinitionHolder.getBeanDefinition());
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
