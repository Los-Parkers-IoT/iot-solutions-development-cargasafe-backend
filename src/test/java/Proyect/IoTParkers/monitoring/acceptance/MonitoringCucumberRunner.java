package Proyect.IoTParkers.monitoring.acceptance;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

/**
 * Cucumber Test Runner for Monitoring Bounded Context
 * Executes all BDD scenarios in the features/monitoring directory
 */
@Suite
@IncludeEngines("cucumber")
@SelectPackages("Proyect.IoTParkers.monitoring.acceptance")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/monitoring.html, json:target/cucumber-reports/monitoring.json")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "Proyect.IoTParkers.monitoring.acceptance")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features/monitoring")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @Ignore")
public class MonitoringCucumberRunner {
}
