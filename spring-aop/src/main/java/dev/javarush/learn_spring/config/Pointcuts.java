package dev.javarush.learn_spring.config;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {}

    @Pointcut("within(com.xyz.trading..*)")
    public void inTrading() {}

    @Pointcut("publicMethod() && inTrading()")
    public void tradingOperation() {}

}
