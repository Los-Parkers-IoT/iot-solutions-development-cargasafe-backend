package Proyect.IoTParkers.monitoring.acceptance;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Cucumber Context Configuration for BDD Tests
 * Integrates Cucumber with Spring Boot Test Context
 */
@CucumberContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class CucumberSpringConfiguration {
}
