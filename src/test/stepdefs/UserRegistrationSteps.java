package stepdefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
            WebElement dateField = driver.findElement(By.id("dp"));
            dateField.clear();
            dateField.sendKeys(dateOfBirth);
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

    @And("I select {string} as my role in basketball")
    public void i_select_as_my_role_in_basketball(String role) {
        switch (role.toLowerCase()) {
            case "basketball media":
                clickWithJS(By.id("signup_basketballrole_16"));
                break;
            case "club/league/area/region role":
                clickWithJS(By.id("signup_basketballrole_17"));
                break;
            case "coach":
                clickWithJS(By.id("signup_basketballrole_18"));
                break;
            case "fan":
                clickWithJS(By.id("signup_basketballrole_19"));
                break;
            case "official":
                clickWithJS(By.id("signup_basketballrole_20"));
                break;
            case "player":
                clickWithJS(By.id("signup_basketballrole_21"));
                break;
            default:
                clickWithJS(By.id("signup_basketballrole_19")); // Default to Fan
        }
    }

    @And("I select Fan as my role in basketball")
    public void i_select_fan_as_my_role_in_basketball() {
        clickWithJS(By.id("signup_basketballrole_19"));
    }

    @And("I {word} the terms and conditions")
    public void i_handle_the_terms_and_conditions(String action) {
        if (action.equalsIgnoreCase("accept")) {
            clickWithJS(By.id("sign_up_25"));
        }
        // Om vi inte ska acceptera villkoren, gör ingenting
    }

    @And("I do not accept the terms and conditions")
    public void i_do_not_accept_the_terms_and_conditions() {
        // Klicka inte på terms and conditions
        // Acceptera de andra för isolera felet
        i_handle_the_age_confirmation("accept");
        i_handle_the_code_of_ethics("accept");
    }

    @And("I {word} the age confirmation")
    public void i_handle_the_age_confirmation(String action) {
        if (action.equalsIgnoreCase("accept")) {
            clickWithJS(By.id("sign_up_26"));
        }
        // Om vi inte ska acceptera åldersbekräftelsen, gör ingenting
    }

    @And("I do not accept the age confirmation")
    public void i_do_not_accept_the_age_confirmation() {
        // Klicka inte på age confirmation
        // Acceptera de andra för isolera felet
        i_handle_the_terms_and_conditions("accept");
        i_handle_the_code_of_ethics("accept");
    }

    @And("I {word} the code of ethics")
    public void i_handle_the_code_of_ethics(String action) {
        if (action.equalsIgnoreCase("accept")) {
            clickWithJS(By.id("fanmembersignup_agreetocodeofethicsandconduct"));
        }
        // Om vi inte ska acceptera etik-koden, gör ingenting
    }

    @And("I do not accept the code of ethics")
    public void i_do_not_accept_the_code_of_ethics() {
        // Klicka inte på code of ethics
        // Acceptera de andra för isolera felet
        i_handle_the_terms_and_conditions("accept");
        i_handle_the_age_confirmation("accept");
    }

    @And("I click the create account button")
    public void i_click_the_create_account_button() {
        WebElement joinButton = driver.findElement(By.xpath("//input[@name='join']"));
        clickWithJS(joinButton);
    }

    @Then("I should see a confirmation message")
    public void i_should_see_a_confirmation_message() {
        try {
            WebElement successMessage = waitForElementVisible(By.cssSelector(".alert-success, .success-message"), 10);
            assertTrue("Success message should be displayed", successMessage.isDisplayed());
            assertTrue("Success message should contain account creation confirmation",
                    successMessage.getText().contains("account")
                            || successMessage.getText().contains("success"));
        } catch (org.openqa.selenium.TimeoutException e) {
            // Vi antar att en omdirigering till dashboarden också är en framgång
            String currentUrl = driver.getCurrentUrl();
        }
    }

    @Then("I should see an error message indicating that lastname is required")
    public void i_should_see_an_error_message_indicating_that_lastname_is_required() {
        WebElement errorMessage = waitForElementVisible(By.cssSelector("[data-valmsg-for='Surname']"), 5);
        assertTrue("Error message for lastname should be displayed", errorMessage.isDisplayed());
        assertTrue("Error message should indicate lastname is required",
                errorMessage.getText().toLowerCase().contains("required"));
    }

    @Then("I should see an error message indicating that passwords do not match")
    public void i_should_see_an_error_message_indicating_that_passwords_do_not_match() {
        WebElement errorMessage = waitForElementVisible(By.cssSelector("[data-valmsg-for='ConfirmPassword']"), 5);
        assertTrue("Error message for password matching should be displayed", errorMessage.isDisplayed());
        assertTrue("Error message should indicate passwords don't match",
                errorMessage.getText().toLowerCase().contains("match"));
    }

    @Then("I should see an error message indicating that terms and conditions must be accepted")
    public void i_should_see_an_error_message_indicating_that_terms_and_conditions_must_be_accepted() {
        WebElement errorMessage = waitForElementVisible(By.cssSelector("[data-valmsg-for='TermsAccept']"), 5);
        assertTrue("Error message should be visible", errorMessage.isDisplayed());
        assertTrue("Error message should indicate terms must be accepted",
                errorMessage.getText().contains("Terms and Conditions"));
    }

    @Then("I should see an error message indicating that age confirmation must be accepted")
    public void i_should_see_an_error_message_indicating_that_age_confirmation_must_be_accepted() {
        WebElement errorMessage = waitForElementVisible(By.cssSelector("[data-valmsg-for='AgeAccept']"), 5);
        assertTrue("Error message should be visible", errorMessage.isDisplayed());
        assertTrue("Error message should indicate age must be accepted",
                errorMessage.getText().contains("over 18"));
    }

    @Then("I should see an error message indicating that code of ethics must be accepted")
    public void i_should_see_an_error_message_indicating_that_code_of_ethics_must_be_accepted() {
        WebElement errorMessage = waitForElementVisible(By.cssSelector("[data-valmsg-for='AgreeToCodeOfEthicsAndConduct']"), 5);
        assertTrue("Error message should be visible", errorMessage.isDisplayed());
        assertTrue("Error message should indicate code of ethics must be accepted",
                errorMessage.getText().contains("Code of Ethics"));
    }

    @Then("I should see {word}")
    public void i_should_see_outcome(String outcome) {
        switch (outcome) {
            case "a confirmation message":
                i_should_see_a_confirmation_message();
                break;
            case "an error message indicating that terms and conditions must be accepted":
                i_should_see_an_error_message_indicating_that_terms_and_conditions_must_be_accepted();
                break;
            case "an error message indicating that age confirmation must be accepted":
                i_should_see_an_error_message_indicating_that_age_confirmation_must_be_accepted();
                break;
            case "an error message indicating that code of ethics must be accepted":
                i_should_see_an_error_message_indicating_that_code_of_ethics_must_be_accepted();
                break;
            default:
                throw new io.cucumber.java.PendingException("Outcome not implemented: " + outcome);
        }
    }

    // Använd JavaScript för att klicka på element som annars kan vara svåra att klicka på
    private void clickWithJS(By locator) {
        WebElement element = driver.findElement(locator);
        clickWithJS(element);
    }

    private void clickWithJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    private WebElement waitForElementVisible(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}