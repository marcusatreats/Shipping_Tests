package world;


import com.microsoft.playwright.Page;
import com.yourcompany.shipping.pages.shipping.ShippingSettingsPage;
import com.yourcompany.shipping.pages.shipping.ShippingProfilesPage;
import com.yourcompany.shipping.pages.shipping.UspsSettingsPage;
import org.google.utils.PlaywrightFactory;
import io.cucumber.java.Scenario;

/**
 * Cucumber World — shared state across step definitions per scenario.
 * Inject this into any step class via constructor injection.
 */
public class ShippingWorld {

    private final PlaywrightFactory factory = new PlaywrightFactory();
    private Page page;

    // Pages — lazily initialised after browser is up
    private ShippingSettingsPage shippingSettingsPage;
    private ShippingProfilesPage shippingProfilesPage;
    private UspsSettingsPage uspsSettingsPage;

    // ── Lifecycle ──────────────────────────────────────────────

    public void init() {
        factory.initBrowser();
        page = factory.getPage();
    }

    public void tearDown(Scenario scenario) {
        // Capture screenshot on failure
        if (scenario.isFailed()) {
            byte[] screenshot = page.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        factory.tearDown();
    }

    // ── Page Accessors ─────────────────────────────────────────

    public Page getPage() {
        return page;
    }

    public ShippingSettingsPage shippingSettingsPage() {
        if (shippingSettingsPage == null) {
            shippingSettingsPage = new ShippingSettingsPage(page);
        }
        return shippingSettingsPage;
    }

    public ShippingProfilesPage shippingProfilesPage() {
        if (shippingProfilesPage == null) {
            shippingProfilesPage = new ShippingProfilesPage(page);
        }
        return shippingProfilesPage;
    }

    public UspsSettingsPage uspsSettingsPage() {
        if (uspsSettingsPage == null) {
            uspsSettingsPage = new UspsSettingsPage(page);
        }
        return uspsSettingsPage;
    }
}
