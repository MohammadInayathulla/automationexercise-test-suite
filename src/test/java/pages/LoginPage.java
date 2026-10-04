package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By loginHeading = By.xpath("//h2[text()='Login to your account']");
    private final By loginEmail = By.cssSelector("input[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private final By loginError = By.xpath("//p[contains(text(),'Your email or password is incorrect!')]");

    // signup form (used later in SignupTest)
    private final By signupName = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("button[data-qa='signup-button']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoginFormVisible() {
        return waitForVisible(loginHeading).isDisplayed();
    }

    public HomePage login(String email, String password) {
        type(loginEmail, email);
        type(loginPassword, password);
        click(loginButton);
        return new HomePage(driver);
    }

    public LoginPage loginExpectingFailure(String email, String password) {
        type(loginEmail, email);
        type(loginPassword, password);
        click(loginButton);
        return this;
    }

    public String getLoginErrorMessage() {
        return getText(loginError);
    }

    public void startSignup(String name, String email) {
        type(signupName, name);
        type(signupEmail, email);
        click(signupButton);
    }
}