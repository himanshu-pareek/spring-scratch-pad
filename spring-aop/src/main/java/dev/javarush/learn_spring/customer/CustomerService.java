package dev.javarush.learn_spring.customer;

import dev.javarush.learn_spring.annotations.Transactional;

public interface CustomerService {
    @Transactional
    void create();

    void add();
}