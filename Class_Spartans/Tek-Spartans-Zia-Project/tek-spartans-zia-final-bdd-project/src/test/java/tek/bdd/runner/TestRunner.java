package tek.bdd.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "tek.bdd.steps",
        tags = "@Regression",
        dryRun = false,
        plugin = {
                "html:target/cucumber-report/report.html",
                "json:target/jsonReport/report.json"

        }
)
public class TestRunner {
}