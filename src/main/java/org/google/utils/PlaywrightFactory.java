
package org.google.utils;

import com.microsoft.playwright.*;

/**
 * Manages Playwright browser, context and page lifecycle.
 * One instance per scenario via ShippingWorld.
 */
public class PlaywrightFactory {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public void initBrowser() {
        playwright = Playwright.create();

        // Chrome in headless mode - swap to headed for local debugging:
        // .setHeadless(false)
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setChannel("chrome")
                        .setHeadless(true)
        );

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1920, 1080)
        );

        page = context.newPage();
    }

    public Page getPage() {
        return page;
    }

    public BrowserContext getContext() {
        return context;
    }

    public void tearDown() {
        if (page != null)     page.close();
        if (context != null)  context.close();
        if (browser != null)  browser.close();
        if (playwright != null) playwright.close();
    }
}