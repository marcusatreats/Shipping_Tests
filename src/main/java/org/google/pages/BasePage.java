package org.google.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * BasePage — your Playwright wrapper layer.
 * Mirrors the wrapper style you had in Selenium.
 * All pages extend this class.
 */
public abstract class BasePage {

    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    // ── Navigation ─────────────────────────────────────────────

    public void navigateTo(String url) {
        page.navigate(url);
    }

    public String getPageTitle() {
        return page.title();
    }

    // ── Locator Helpers ────────────────────────────────────────

    protected Locator findByTestId(String testId) {
        return page.getByTestId(testId);
    }

    protected Locator findByLabel(String label) {
        return page.getByLabel(label);
    }

    protected Locator findByRole(AriaRole role, String name) {
        return page.getByRole(role, new Page.GetByRoleOptions().setName(name));
    }

    protected Locator findByText(String text) {
        return page.getByText(text);
    }

    protected Locator findByCss(String selector) {
        return page.locator(selector);
    }

    // ── Actions ────────────────────────────────────────────────

    protected void click(Locator locator) {
        locator.click();
    }

    protected void fill(Locator locator, String value) {
        locator.clear();
        locator.fill(value);
    }

    protected void selectOption(Locator locator, String value) {
        locator.selectOption(value);
    }

    protected void check(Locator locator) {
        if (!locator.isChecked()) locator.check();
    }

    protected void uncheck(Locator locator) {
        if (locator.isChecked()) locator.uncheck();
    }

    // ── Assertions / State ─────────────────────────────────────

    protected boolean isVisible(Locator locator) {
        return locator.isVisible();
    }

    protected boolean isEnabled(Locator locator) {
        return locator.isEnabled();
    }

    protected String getText(Locator locator) {
        return locator.innerText();
    }

    protected String getValue(Locator locator) {
        return locator.inputValue();
    }

    // ── Waits ──────────────────────────────────────────────────

    protected void waitForVisible(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    protected void waitForHidden(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN));
    }

    protected void waitForPageLoad() {
        page.waitForLoadState();
    }
}