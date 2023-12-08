package org.example;

import org.example.service.MyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;

@DisabledInAotMode
public class SecondTest extends BaseTest {

    @MockBean
    MyService myService;

    @Test
    void foo() {
        
    }
}
