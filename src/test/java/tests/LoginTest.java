package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginShowsLoggedInUser() {
        LoginPage loginPage = new HomePage(driver).clickSignupLogin();
        Assert.assertTrue(loginPage.isLoginFormVisible(), "Login form not displayed");

        HomePage home = loginPage.login(
                ConfigReader.get("user.email"),
                ConfigReader.get("user.password"));

        Assert.assertTrue(home.isLoggedIn(), "User was not logged in");
    }

    @Test
    public void invalidLoginShowsError() {
        LoginPage loginPage = new HomePage(driver).clickSignupLogin();

        loginPage.loginExpectingFailure("wrong_user@example.com", "wrongPassword123");

        Assert.assertEquals(loginPage.getLoginErrorMessage(),
                "Your email or password is incorrect!");
    }

    @Test
    public void logoutReturnsToLoginPage() {
        LoginPage loginPage = new HomePage(driver).clickSignupLogin();
        HomePage home = loginPage.login(
                ConfigReader.get("user.email"),
                ConfigReader.get("user.password"));
        Assert.assertTrue(home.isLoggedIn(), "Precondition failed: login did not work");

        LoginPage afterLogout = home.logout();

        Assert.assertTrue(afterLogout.isLoginFormVisible(), "Not redirected to login page after logout");
    }
}