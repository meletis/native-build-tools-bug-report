package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MyApplicationTest {

    @ServiceConnection
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

    @Test
    public void testMySQLContainerIsRunning() {
        assertTrue(mySQLContainer.isRunning());
    }
}