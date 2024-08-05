package dev.javarush.learn_spring.config;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import dev.javarush.learn_spring.annotations.Transactional;

@Configuration
@ComponentScan("dev.javarush.learn_spring.customer")
public class ProjectConfig {

    @Bean
    static BeanPostProcessor transactionalBeanPostProcessor() {
        return new TransactionalBeanPostProcessor();
    }

    static class TransactionalBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object target, String beanName) throws BeansException {
            if (isTransactional(target)) {
                var pf = new ProxyFactory();
                pf.setInterfaces(target.getClass().getInterfaces());
                pf.setTarget(target);
                pf.addAdvice(new MethodInterceptor() {
                    @Override
                    public Object invoke(MethodInvocation invocation) throws Throwable {
                        var method = invocation.getMethod();
                        var arguments = invocation.getArguments();
                        System.out.println("Calling " + method.getName() + " with arguments " + arguments);
                        boolean insideTransaction = (method.getAnnotation(Transactional.class) != null)
                                || (method.getClass().getAnnotation(Transactional.class) != null);

                        if (insideTransaction) {
                            System.out.println("Initializing transaction for method " + method.getName());
                        }

                        Object result = null;
                        try {
                            result = method.invoke(target, arguments);
                        } catch (Exception e) {
                            if (insideTransaction) {
                                System.out.println("Rolling back transaction for method " + method.getName());
                            }
                            throw new RuntimeException("Transaction failed", e);
                        }

                        if (insideTransaction) {
                            System.out.println("Committing transaction for method " + method.getName());
                        }
                        return result;
                    }
                });
                var proxyInstance = pf.getProxy(getClass().getClassLoader());
                return proxyInstance;
            }
            return target;
        }
    }

    private static boolean isTransactional(Object object) {
        Class classOfObject = object.getClass();
        
    }

    private static boolean isDirectTransactional(Class clazz) {
        if (clazz.getAnnotation(Transactional.class) != null) {
            return true;
        }
        for (Method method : clazz.getMethods()) {
            if (method.getAnnotation(Transactional.class) != null) {
                return true;
            }
        }
        System.out.println("Returning false for " + object);
        return false; 
    }
}
