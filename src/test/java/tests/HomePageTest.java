package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

    @Test
    public void verifyHomePageTitle() {
        Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                "Unexpected title: " + driver.getTitle());
    }
}