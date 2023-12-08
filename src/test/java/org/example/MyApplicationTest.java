package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MyApplicationTest {

    private static final MySQLContainer<?> mySQLContainer;
    
    static {
        mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                .withUsername("admin")
                .withPassword("admin_pass")
                .withReuse(false)
                .withInitScript("db/init.sql")
                .withConfigurationOverride("db/mysql_conf_override");
        mySQLContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        mySQLContainer.stop();
    }

    @Test
    public void testMySQLContainerIsRunning() {
        assertTrue(mySQLContainer.isRunning());
    }
}