import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormTest {

    @Test
    public void testFormSubmission() throws Exception {
        // Set the path to ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Configure Chrome options for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run Chrome in headless mode
        options.addArguments("--no-sandbox"); // Prevent sandbox issues
        options.addArguments("--disable-dev-shm-usage"); // Use shared memory for stability
        options.addArguments("--disable-gpu"); // Disable GPU for headless environments
        options.addArguments("--disable-background-timer-throttling"); // Optimize timers
        options.addArguments("--disable-renderer-backgrounding");

        // Initialize the WebDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            // Navigate to the application
            driver.get(System.getProperty("base.url", "http://34.72.83.173:8081"));

            // Use WebDriverWait to handle dynamic elements
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Locate form fields
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
            WebElement emailField = driver.findElement(By.id("email"));
            WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

            // Fill in the form
            nameField.sendKeys("John Doe");
            emailField.sendKeys("john.doe@example.com");

            // Submit the form
            submitButton.click();

            // Validate the success message
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
            String actualMessage = successMessage.getText();
            String expectedMessage = "Thank You, John Doe!";
            assertEquals(expectedMessage, actualMessage, "Success message should match");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Test failed due to an exception: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
