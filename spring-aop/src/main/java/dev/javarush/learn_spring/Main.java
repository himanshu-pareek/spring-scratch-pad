package dev.javarush.learn_spring;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import dev.javarush.learn_spring.annotations.Transactional;
import dev.javarush.learn_spring.config.ProjectConfig;
import dev.javarush.learn_spring.customer.CustomerService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world 🌎");

        // ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // NotVeryUsefulAspect aspect = context.getBean(NotVeryUsefulAspect.class);
        // System.out.println(aspect);

        // var target = new DefaultCustomerService();

        // var proxyInstance = (CustomerService) Proxy.newProxyInstance(
        // target.getClass().getClassLoader(),
        // target.getClass().getInterfaces(),
        // new InvocationHandler() {
        // @Override
        // public Object invoke(Object proxy, Method method, Object[] a) throws
        // Throwable {
        // System.out.println("Calling " + method.getName() + " with arguments " + a);
        // boolean insideTransaction = (method.getAnnotation(Transactional.class) !=
        // null)
        // || (method.getClass().getAnnotation(Transactional.class) != null);

        // if (insideTransaction) {
        // System.out.println("Initializing transaction for method " +
        // method.getName());
        // }

        // Object result = null;
        // try {
        // result = method.invoke(target, a);
        // } catch (Exception e) {
        // if (insideTransaction) {
        // System.out.println("Rolling back transaction for method " +
        // method.getName());
        // }
        // throw new RuntimeException("Transaction failed", e);
        // }

        // if (insideTransaction) {
        // System.out.println("Committing transaction for method " + method.getName());
        // }
        // return result;
        // }
        // }
        // );

        // var pf = new ProxyFactory();
        // pf.setInterfaces(target.getClass().getInterfaces());
        // pf.setTarget(target);
        // pf.addAdvice(new MethodInterceptor() {
        //     @Override
        //     public Object invoke(MethodInvocation invocation) throws Throwable {
        //         var method = invocation.getMethod();
        //         var a = invocation.getArguments();
        //         System.out.println("Calling " + method.getName() + " with arguments " + a);
        //         boolean insideTransaction = (method.getAnnotation(Transactional.class) != null)
        //                 || (method.getClass().getAnnotation(Transactional.class) != null);

        //         if (insideTransaction) {
        //             System.out.println("Initializing transaction for method " + method.getName());
        //         }

        //         Object result = null;
        //         try {
        //             result = method.invoke(target, a);
        //         } catch (Exception e) {
        //             if (insideTransaction) {
        //                 System.out.println("Rolling back transaction for method " + method.getName());
        //             }
        //             throw new RuntimeException("Transaction failed", e);
        //         }

        //         if (insideTransaction) {
        //             System.out.println("Committing transaction for method " + method.getName());
        //         }
        //         return result;
        //     }
        // });
        // var proxyInstance = (CustomerService) pf.getProxy(Main.class.getClassLoader());

        // try {
        //     proxyInstance.create();
        // } catch (Exception e) {
        // }
        // proxyInstance.add();

        var applicationContext = new AnnotationConfigApplicationContext(ProjectConfig.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        try {
            customerService.create();
        } catch (Exception e) {
            // TODO: handle exception
        }
        customerService.add();
        applicationContext.close();
    }
}
