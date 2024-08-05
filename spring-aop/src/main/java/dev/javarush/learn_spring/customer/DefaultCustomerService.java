package dev.javarush.learn_spring.customer;

import org.springframework.stereotype.Service;

@Service
public class DefaultCustomerService implements CustomerService {
    @Override
    public void create() {
        System.out.println("Creating customer ...");
        throw new RuntimeException("There is some error");
    }

    @Override
    public void add() {
        System.out.println("Adding customer ...");
    }
}