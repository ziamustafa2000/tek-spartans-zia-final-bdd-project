package tek.bdd.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import tek.bdd.browsers.BaseBrowser;
import tek.bdd.browsers.ChromeBrowser;
import tek.bdd.browsers.EdgeBrowser;
import tek.bdd.browsers.FireFoxBrowser;
import tek.bdd.exceptions.ExceptionsHandling;
import tek.bdd.utilities.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * BaseSetup is an abstract class that provides common setup and tear down functionalities
 * for initializing and managing WebDriver instances in test automation.
 */
public abstract class BaseSetup {

    // Static WebDriver instance shared across subclasses
    private static WebDriver driver;

    // Logger for logging messages and debugging information
    private static final Logger LOGGER = LogManager.getLogger(BaseSetup.class);

    // Properties object to hold configuration settings from a properties file
    private final Properties properties;

    /**
     * Constructor for BaseSetup class. It loads the configuration from a properties file.
     */
    public BaseSetup() {
        try {
            // Construct the path to the configuration file
            String configFilePath = System.getProperty("user.dir") + "/src/test/resources/configs/dev-config.properties";
            LOGGER.info("Reading Config file {}", configFilePath);

            // Create a File object for the configuration file
            File file = new File(configFilePath);

            // Create a FileInputStream to read the configuration file
            FileInputStream fileInputStream = new FileInputStream(file);

            // Initialize Properties object and load properties from the file
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException exception) {
            // Log error and throw a custom exception if there is an issue reading the config file
            LOGGER.error("Error reading config file", exception);
            throw new ExceptionsHandling("Something wrong with config file");
        }
    }

    /**
     * Opens the browser based on the configuration settings and navigates to the specified URL.
     */
    public void openBrowser() {
        // Retrieve browser type and headless mode settings from the properties file
        String browserType = properties.getProperty("ui.browser");
        boolean isHeadless = Boolean.parseBoolean(properties.getProperty("ui.browser.headless"));

        LOGGER.info("Running on browser {}", browserType);
        LOGGER.info("Is Headless ON {}", isHeadless);
        BaseBrowser browser;

        // Instantiate the appropriate browser based on the configuration
        if (browserType.equalsIgnoreCase("chrome")) {
            browser = new ChromeBrowser();
        } else if (browserType.equalsIgnoreCase("edge")) {
            browser = new EdgeBrowser();
        } else if (browserType.equalsIgnoreCase("firefox")) {
            browser = new FireFoxBrowser();
        } else {
            // Throw a custom exception if the browser type is unsupported
            throw new ExceptionsHandling("Wrong browser type, choose another browser such as chrome, firefox and edge");
        }

        // Open the browser and set the WebDriver instance
        driver = browser.openBrowser(isHeadless);

        // Retrieve the URL from the properties file and navigate to it
        String url = properties.getProperty("ui.url");
        LOGGER.debug("Using URL {}", url);
        driver.get(url);

        // Maximize the browser window and set timeouts
        LOGGER.info("Maximizing the browser");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.WAIT_IN_SECONDS));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Constants.WAIT_IN_SECONDS));
    }

    /**
     * Quits the browser and closes all associated windows.
     */
    public void quitBrowser() {
        if (driver != null) {
            LOGGER.info("Quitting the Browser");
            driver.quit();
        }
    }

    /**
     * Gets the WebDriver instance.
     *
     * @return The WebDriver instance.
     */
    public WebDriver getDriver() {
        LOGGER.info("Getting the driver");
        return driver;
    }
}