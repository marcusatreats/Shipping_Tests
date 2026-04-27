package world;

import com.microsoft.playwright.Page;
import org.google.pages.LoginPage;
import org.google.pages.ShippingSettingsPage;
import org.google.pages.CountrySettingsPage;
import org.google.utils.PlaywrightFactory;
import io.cucumber.java.Scenario;

public class ShippingWorld {

    private final PlaywrightFactory factory = new PlaywrightFactory();
    private Page page;

    private ShippingSettingsPage shippingSettingsPage;
    private LoginPage loginPage;
    private CountrySettingsPage countrySettingsPage;

    public void init() {
        factory.initBrowser();
        page = factory.getPage();
    }
    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(page, factory.getContext());
        }
        return loginPage;
    }


    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = page.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        factory.tearDown();
    }

    public Page getPage() {
        return page;
    }

    public ShippingSettingsPage shippingSettingsPage() {
        if (shippingSettingsPage == null) {
            shippingSettingsPage = new ShippingSettingsPage(page);
        }
        return shippingSettingsPage;
    }
    public CountrySettingsPage countrySettingsPage() {
        if (countrySettingsPage == null) {
            countrySettingsPage = new CountrySettingsPage(page);
        }
        return countrySettingsPage;
    }
}