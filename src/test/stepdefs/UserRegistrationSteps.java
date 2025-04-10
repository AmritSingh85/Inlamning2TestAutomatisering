package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserRegistrationSteps {
    private WebDriver driver;
    private String browser;

    @Before
    public void setUp() {

        browser = System.getProperty("browser", "chrome");

        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                driver = new ChromeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the Basketball England registration page")
    public void i_am_on_the_basketball_england_registration_page() {
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
        String title = driver.getTitle();
        assertTrue("Expected to be on the registration page",
                title.contains("Basketball England")
                || driver.getCurrentUrl().contains("NewSupporterAccount"));
    }

    @When("I enter {string} as date of birth")
    public void i_enter_as_date_of_birth(String dateOfBirth) {
        if (!dateOfBirth.isEmpty()) {
            // Klicka på datumfältet och ange datum
            WebElement dateField = driver.findElement(By.id("dp"));
            dateField.clear();
            dateField.sendKeys(dateOfBirth);
            // Klicka någon annanstans för att stänga eventuell datepicker
            driver.findElement(By.tagName("body")).click();
        }
    }

    @When("I enter {string} as firstname")
    public void i_enter_as_firstname(String firstname) {
        driver.findElement(By.id("member_firstname")).sendKeys(firstname);
    }

    @And("I enter {string} as lastname")
    public void i_enter_as_lastname(String lastname) {
        driver.findElement(By.id("member_lastname")).sendKeys(lastname);
    }

    @And("I enter {string} as email")
    public void i_enter_as_email(String email) {
        driver.findElement(By.id("member_emailaddress")).sendKeys(email);
    }

    @And("I enter {string} as confirm email")
    public void i_enter_as_confirm_email(String confirmEmail) {
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(confirmEmail);
    }

    @And("I enter {string} as password")
    public void i_enter_as_password(String password) {
        driver.findElement(By.id("signupunlicenced_password")).sendKeys(password);
    }

    @And("I enter {string} as confirm password")
    public void i_enter_as_confirm_password(String confirmPassword) {
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys(confirmPassword);
    }

    @And("I select Fan as my role in basketball")
    public void i_select_as_my_role_in_basketball() {
        WebElement box = driver.findElement(By.xpath("//div[4]/div/label/span[3]"));
        box.click();

    }

    @And("I accept the terms and conditions")
    public void i_accept_the_terms_and_conditions() {
        driver.findElement(By.cssSelector(".md-checkbox > .md-checkbox:nth-child(1) .box")).click();
    }

    @And("I do not accept the terms and conditions")
    public void i_do_not_accept_the_terms_and_conditions() {
        i_accept_the_age_confirmation();
        i_accept_the_code_of_ethics();
    }

    @And("I accept the age confirmation")
    public void i_accept_the_age_confirmation() {
        driver.findElement(By.cssSelector(".md-checkbox:nth-child(2) > label > .box")).click();

    }

    @And("I accept the code of ethics")
    public void i_accept_the_code_of_ethics() {
        driver.findElement(By.cssSelector(".md-checkbox:nth-child(7) .box")).click();

    }

    @And("I click the create account button")
    public void i_click_the_create_account_button() {
        WebElement joinButton = driver.findElement(By.xpath("//input[@name='join']"));
        joinButton.click();
    }

    @Then("I should see a confirmation message")
    public void i_should_see_a_confirmation_message() {
        WebElement successMessage = waitForElementVisible(By.cssSelector(".alert-success, .success-message"), 10);
        assertTrue("Success message should be displayed", successMessage.isDisplayed());
        assertTrue("Success message should contain account creation confirmation",
                successMessage.getText().contains("account")
                        || successMessage.getText().contains("success"));
    }

    @Then("I should see an error message indicating that lastname is required")
    public void i_should_see_an_error_message_indicating_that_lastname_is_required() {
        WebElement errorMessage = waitForElementVisible(By.cssSelector("[data-valmsg-for='Surname']"), 5);
        assertTrue("Error message for lastname should be displayed", errorMessage.isDisplayed());
        assertTrue("Error message should indicate lastname is required", errorMessage.getText().toLowerCase().contains("required"));

        assertEquals("Last Name is required", errorMessage.getText());
    }

    @Then("I should see an error message indicating that passwords do not match")
    public void i_should_see_an_error_message_indicating_that_passwords_do_not_match() {
        WebElement errorMessage = waitForElementVisible(By.cssSelector("[data-valmsg-for='ConfirmPassword']"), 5);
        assertTrue("Error message for password matching should be displayed", errorMessage.isDisplayed());
        assertTrue("Error message should indicate passwords don't match", errorMessage.getText().toLowerCase().contains("match"));

        assertEquals("Password did not match", errorMessage.getText());
    }

    @Then("I should see an error message indicating that terms and conditions must be accepted")
    public void i_should_see_an_error_message_indicating_that_terms_and_conditions_must_be_accepted() {
        WebElement errorMessage = driver.findElement(By.xpath("//span[@data-valmsg-for='TermsAccept']/span[@for='TermsAccept']"));

        assertTrue("Error message should be visible", errorMessage.isDisplayed());
        assertEquals("You must confirm that you have read and accepted our Terms and Conditions", errorMessage.getText());

    }

    private WebElement waitForElementVisible(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


}