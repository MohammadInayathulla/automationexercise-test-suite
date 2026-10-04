package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By signupLoginLink = By.cssSelector("a[href='/login']");
    private final By logoutLink = By.cssSelector("a[href='/logout']");
    private final By loggedInAs = By.xpath("//a[contains(text(),'Logged in as')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickSignupLogin() {
        click(signupLoginLink);
        return new LoginPage(driver);
    }

    public boolean isLoggedIn() {
        return waitForVisible(loggedInAs).isDisplayed();
    }

    public String getLoggedInUsername() {
        // text looks like "Logged in as <name>"
        return getText(loggedInAs).replace("Logged in as", "").trim();
    }

    public LoginPage logout() {
        click(logoutLink);
        return new LoginPage(driver);
    }
}