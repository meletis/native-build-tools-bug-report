package org.example;

import org.example.service.MyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FirstTest extends BaseTest {

    @Autowired
    MyService myService;

    @Test
    void foo() {
        
    }
}
