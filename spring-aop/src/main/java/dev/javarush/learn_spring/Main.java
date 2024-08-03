package dev.javarush.learn_spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world 🌎");

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        NotVeryUsefulAspect aspect = context.getBean(NotVeryUsefulAspect.class);
        System.out.println(aspect);
    }
}
