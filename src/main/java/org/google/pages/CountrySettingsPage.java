package org.google.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


import java.util.List;

/**
 * Country / Target Market Settings Page
 * Covers: adding/removing target countries, enabling shipping per country,
 * setting country-specific rates and verifying country configurations.
 *
 * NOTE: CSS selectors are best-assumption placeholders.
 * Replace with actual selectors from your app's DOM.
 */
public class CountrySettingsPage extends BasePage {

    // ── URL ────────────────────────────────────────────────────
    private static final String PATH = "/settings/shipping/countries";

    // ── Country Search / Add ───────────────────────────────────
    private final Locator addCountryButton        = findByCss("button[data-action='add-country']");
    private final Locator countrySearchInput      = findByCss("input[data-field='country-search']");
    private final Locator countrySearchDropdown   = findByCss(".country-search-dropdown");
    private final Locator countryList             = findByCss(".target-country-list");

    // ── Save / Feedback ────────────────────────────────────────
    private final Locator saveButton              = findByCss("button[data-action='save-countries']");
    private final Locator successMessage          = findByCss(".alert--success");
    private final Locator validationError         = findByCss(".alert--error, .field-error");

    // ── Constructor ────────────────────────────────────────────

    public CountrySettingsPage(Page page) {
        super(page);
    }

    // ── Navigation ─────────────────────────────────────────────

    public void open(String baseUrl) {
        navigateTo(baseUrl + PATH);
        waitForPageLoad();
    }

    // ── Add Country ────────────────────────────────────────────

    public void addTargetCountry(String countryName) {
        click(addCountryButton);
        waitForVisible(countrySearchInput);
        fill(countrySearchInput, countryName);
        waitForVisible(countrySearchDropdown);

        // Select matching option from dropdown
        Locator countryOption = findByCss(
                ".country-search-dropdown .country-option[data-country='" + countryName + "']"
        );
        waitForVisible(countryOption);
        click(countryOption);
    }

    // ── Remove Country ─────────────────────────────────────────

    public void removeTargetCountry(String countryName) {
        Locator removeButton = getCountryRow(countryName)
                .locator("button[data-action='remove-country']");
        click(removeButton);
    }

    // ── Enable / Disable Per Country ───────────────────────────

    public void enableShippingForCountry(String countryName) {
        Locator toggle = getCountryRow(countryName)
                .locator("input[data-field='country-shipping-enabled']");
        check(toggle);
    }

    public void disableShippingForCountry(String countryName) {
        Locator toggle = getCountryRow(countryName)
                .locator("input[data-field='country-shipping-enabled']");
        uncheck(toggle);
    }

    public boolean isShippingEnabledForCountry(String countryName) {
        Locator toggle = getCountryRow(countryName)
                .locator("input[data-field='country-shipping-enabled']");
        return toggle.isChecked();
    }

    // ── Country-Specific Rates ─────────────────────────────────

    public void setFlatRateForCountry(String countryName, String rate) {
        Locator rateInput = getCountryRow(countryName)
                .locator("input[data-field='country-flat-rate']");
        fill(rateInput, rate);
    }

    public String getDisplayedRateForCountry(String countryName) {
        Locator rateInput = getCountryRow(countryName)
                .locator("input[data-field='country-flat-rate']");
        return getValue(rateInput);
    }

    // ── Shipping Method Per Country ────────────────────────────

    public void setShippingMethodForCountry(String countryName, String method) {
        Locator methodDropdown = getCountryRow(countryName)
                .locator("select[data-field='country-shipping-method']");
        selectOption(methodDropdown, method);
    }

    public String getShippingMethodForCountry(String countryName) {
        Locator methodDropdown = getCountryRow(countryName)
                .locator("select[data-field='country-shipping-method']");
        return methodDropdown.inputValue();
    }

    // ── Country List State ─────────────────────────────────────

    public boolean isCountryInList(String countryName) {
        return getCountryRow(countryName).isVisible();
    }

    public int getTargetCountryCount() {
        return countryList.locator(".country-row").count();
    }

    public List<String> getAllTargetCountries() {
        return countryList
                .locator(".country-row .country-name")
                .allInnerTexts();
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

    // ── Private Helpers ────────────────────────────────────────

    /**
     * Returns the country row locator for a given country name.
     * Used as the scoped parent for all per-country element lookups.
     */
    private Locator getCountryRow(String countryName) {
        return countryList.locator(
                ".country-row[data-country='" + countryName + "']"
        );
    }
}
