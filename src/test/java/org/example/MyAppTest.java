package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

public class MyAppTest {

    private static final MySQLContainer<?> mySQLContainer;
    
    static {
        mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:5.7"))
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