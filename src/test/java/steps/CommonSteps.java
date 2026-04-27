package steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import org.google.shipping.config.ConfigManager;
import world.ShippingWorld;
import io.cucumber.java.en.Given;

/**
 * CommonSteps — shared step definitions used across all feature files.
 * e.g. login, navigation setup, shared preconditions.
 */
public class CommonSteps {

    private final ShippingWorld world;

    // Cucumber injects ShippingWorld via constructor injection
    public CommonSteps(ShippingWorld world) {
        this.world = world;
    }

    /**
     * Background step used in all feature files.
     * Injects the wixSession2 cookie to bypass the login form
     * and lands on the Wix account sites page.
     */
    @Given("I am logged in as an admin")
    public void iAmLoggedInAsAnAdmin() {
        world.loginPage().loginViaSessionCookie();
    }

    @And("I navigate to the Shipping Settings page")
    public void iNavigateToTheShippingSettingsPage() {
        world.shippingSettingsPage().open();
    }
}
