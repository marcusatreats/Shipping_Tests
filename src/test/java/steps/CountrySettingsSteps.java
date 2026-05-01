package steps;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.Cookie;
import com.microsoft.playwright.options.LoadState;
import io.cucumber.java.PendingException;
import org.google.shipping.config.ConfigManager;
import world.ShippingWorld;
import com.microsoft.playwright.*;
import io.cucumber.java.en.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CountrySettingsSteps {

    private final ShippingWorld world;
    private final String baseUrl = ConfigManager.get("base.url");
    public static String url = "https://users.wix.com/signin??";

    public CountrySettingsSteps(ShippingWorld world) {
        this.world = world;
    }

    // ── Navigation ─────────────────────────────────────────────

    @Given("I navigate to the Country Settings page")
    public void iNavigateToCountrySettingsPage() {
        world.countrySettingsPage().open(baseUrl);
    }

    // ── Add / Remove Countries ─────────────────────────────────

    @When("I add {string} as a target country")
    public void iAddAsATargetCountry(String country) {
        world.countrySettingsPage().addTargetCountry(country);
    }

    @When("I remove {string} as a target country")
    public void iRemoveAsATargetCountry(String country) {
        world.countrySettingsPage().removeTargetCountry(country);
    }

    // ── Enable / Disable Per Country ───────────────────────────

    @When("I enable shipping for country {string}")
    public void iEnableShippingForCountry(String country) {
        world.countrySettingsPage().enableShippingForCountry(country);
    }

    @When("I disable shipping for country {string}")
    public void iDisableShippingForCountry(String country) {
        world.countrySettingsPage().disableShippingForCountry(country);
    }

    @Then("shipping is enabled for {string}")
    public void shippingIsEnabledFor(String country) {
        assertTrue(world.countrySettingsPage().isShippingEnabledForCountry(country),
                "Expected shipping to be enabled for " + country);
    }

    @Then("shipping is disabled for {string}")
    public void shippingIsDisabledFor(String country) {
        assertFalse(world.countrySettingsPage().isShippingEnabledForCountry(country),
                "Expected shipping to be disabled for " + country);
    }

    // ── Country-Specific Rates ─────────────────────────────────

    @When("I set a flat rate of {string} for {string}")
    public void iSetAFlatRateOfFor(String rate, String country) {
        world.countrySettingsPage().setFlatRateForCountry(country, rate);
    }

    @Then("the flat rate {string} is displayed for country {string}")
    public void theFlatRateIsDisplayedForCountry(String expectedRate, String country) {
        String actualRate = world.countrySettingsPage().getDisplayedRateForCountry(country);
        assertEquals(expectedRate, actualRate,
                "Flat rate mismatch for country: " + country);
    }

    // ── Shipping Method Per Country ────────────────────────────

    @When("I set the shipping method to {string} for {string}")
    public void iSetTheShippingMethodToFor(String method, String country) {
        world.countrySettingsPage().setShippingMethodForCountry(country, method);
    }

    @Then("the shipping method {string} is displayed for {string}")
    public void theShippingMethodIsDisplayedFor(String expectedMethod, String country) {
        String actualMethod = world.countrySettingsPage().getShippingMethodForCountry(country);
        assertEquals(expectedMethod, actualMethod,
                "Shipping method mismatch for country: " + country);
    }

    // ── Country List Assertions ────────────────────────────────

    @Then("{string} is listed as a target country")
    public void isListedAsATargetCountry(String country) {
        assertTrue(world.countrySettingsPage().isCountryInList(country),
                "Expected " + country + " to be in the target country list");
    }

    @Then("{string} is not listed as a target country")
    public void isNotListedAsATargetCountry(String country) {
        assertFalse(world.countrySettingsPage().isCountryInList(country),
                "Expected " + country + " to NOT be in the target country list");
    }

    @Then("there are {int} target countries configured")
    public void thereAreTargetCountriesConfigured(int expectedCount) {
        int actualCount = world.countrySettingsPage().getTargetCountryCount();
        assertEquals(expectedCount, actualCount,
                "Expected " + expectedCount + " countries but found " + actualCount);
    }

    @Then("the target countries include:")
    public void theTargetCountriesInclude(List<String> expectedCountries) {
        List<String> actualCountries = world.countrySettingsPage().getAllTargetCountries();
        assertTrue(actualCountries.containsAll(expectedCountries),
                "Expected countries " + expectedCountries + " but found " + actualCountries);
    }

    // ── Save ───────────────────────────────────────────────────

    @When("I save the country settings")
    public void iSaveTheCountrySettings() {
        world.countrySettingsPage().saveSettings();
    }

    // ── Feedback ───────────────────────────────────────────────

    @Then("the country settings are saved successfully")
    public void theCountrySettingsAreSavedSuccessfully() {
        assertTrue(world.countrySettingsPage().isSuccessMessageDisplayed(),
                "Expected a success message after saving country settings");
    }

    @When("I select the Target country {string}")
    public void iSelectTheTargetCountry(String arg0) {
        world.loginPage().navigateTo(ConfigManager.get("shipping.url"));
        world.getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Select Target Countries")).click();


    }

    @Given("I am logged in as an admin")
    public void iAmLoggedInAsAnAdmin() {
        world.loginPage().loginViaSessionCookie();
    }

    @And("I click Continue")
    public void iClickContinue() {
        world.getPage().locator("[data-hook='baseModalLayout-primary-button']").click();
    }

    @And("I click Save on my Delivery Times")
    public void iClickSaveOnMyDeliveryTimes() {
        world.getPage().locator("[data-hook='baseModalLayout-primary-button']").click();
    }

    @And("I save the shipping settings")
    public void iSaveTheShippingSettings() {
        world.getPage().locator("[data-hook='breadcrumbs-item']").getByText("Google Merchant Solutions").click();
    }
}
