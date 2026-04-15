package org.google.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.google.pages.BasePage;

/**
 * Global Shipping Settings Page
 * Covers: enable/disable shipping, shipping methods (flat rate, by quantity),
 * rate configuration and saving settings.
 *
 * NOTE: CSS selectors are best-assumption placeholders.
 * Replace with actual selectors from your app's DOM.
 */
public class ShippingSettingsPage extends BasePage {

    // ── URL ────────────────────────────────────────────────────
    private static final String PATH = "/settings/shipping";

    // ── Global Toggle ──────────────────────────────────────────
    private final Locator globalShippingToggle    = findByCss("input[data-field='shipping-enabled']");
    private final Locator shippingEnabledLabel    = findByCss(".shipping-status-label");

    // ── Shipping Method ────────────────────────────────────────
    private final Locator shippingMethodDropdown  = findByCss("select[data-field='shipping-method']");

    // ── Flat Rate ──────────────────────────────────────────────
    private final Locator flatRateSection         = findByCss(".flat-rate-section");
    private final Locator flatRateInput           = findByCss("input[data-field='flat-rate-amount']");

    // ── By Quantity ────────────────────────────────────────────
    private final Locator byQuantitySection       = findByCss(".by-quantity-section");
    private final Locator addQuantityRuleButton   = findByCss("button[data-action='add-quantity-rule']");

    // ── Save / Feedback ────────────────────────────────────────
    private final Locator saveButton              = findByCss("button[data-action='save-shipping']");
    private final Locator successMessage          = findByCss(".alert--success");
    private final Locator validationError         = findByCss(".alert--error, .field-error");

    // ── Constructor ────────────────────────────────────────────

    public ShippingSettingsPage(Page page) {
        super(page);
    }

    // ── Navigation ─────────────────────────────────────────────

    public void open(String baseUrl) {
        navigateTo(baseUrl + PATH);
        waitForPageLoad();
    }

    // ── Global Shipping Toggle ─────────────────────────────────

    public void enableShippingGlobally() {
        check(globalShippingToggle);
    }

    public void disableShippingGlobally() {
        uncheck(globalShippingToggle);
    }

    public boolean isShippingEnabled() {
        return globalShippingToggle.isChecked();
    }

    public String getShippingStatusLabel() {
        return getText(shippingEnabledLabel);
    }

    // ── Shipping Method ────────────────────────────────────────

    public void selectShippingMethod(String method) {
        selectOption(shippingMethodDropdown, method);
        waitForPageLoad();
    }

    public String getSelectedShippingMethod() {
        return shippingMethodDropdown.inputValue();
    }

    // ── Flat Rate ──────────────────────────────────────────────

    public void enterFlatRate(String rate) {
        waitForVisible(flatRateSection);
        fill(flatRateInput, rate);
    }

    public String getDisplayedFlatRate() {
        return getValue(flatRateInput);
    }

    public boolean isFlatRateSectionVisible() {
        return isVisible(flatRateSection);
    }

    // ── By Quantity ────────────────────────────────────────────

    public void enterQuantityRate(String quantity, String rate) {
        waitForVisible(byQuantitySection);

        // Each quantity rule row has quantity + rate inputs
        // Rows are dynamically added — locate by quantity value
        Locator quantityInput = findByCss(
                ".quantity-rule-row[data-quantity='" + quantity + "'] input[data-field='quantity-value']"
        );
        Locator rateInput = findByCss(
                ".quantity-rule-row[data-quantity='" + quantity + "'] input[data-field='quantity-rate']"
        );

        // If the row doesn't exist yet, add a new rule first
        if (!quantityInput.isVisible()) {
            click(addQuantityRuleButton);
            waitForVisible(quantityInput);
        }

        fill(quantityInput, quantity);
        fill(rateInput, rate);
    }

    public boolean isByQuantitySectionVisible() {
        return isVisible(byQuantitySection);
    }

    // ── Save ───────────────────────────────────────────────────

    public void saveSettings() {
        click(saveButton);
        waitForVisible(successMessage);
    }

    // ── Feedback ───────────────────────────────────────────────

    public boolean isSuccessMessageDisplayed() {
        return isVisible(successMessage);
    }

    public boolean isValidationErrorDisplayed() {
        return isVisible(validationError);
    }

    public String getSuccessMessageText() {
        return getText(successMessage);
    }

    public String getValidationErrorText() {
        return getText(validationError);
    }
}