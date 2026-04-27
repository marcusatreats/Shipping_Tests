package org.google.pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.Cookie;
import org.google.shipping.config.ConfigManager;

import java.util.Collections;

public class LoginPage extends BasePage {

    private final BrowserContext context;

    public LoginPage(Page page, BrowserContext context) {
        super(page);
        this.context = context;
    }

    public void loginViaSessionCookie() {

        navigateTo("https://www.wix.com");
        waitForPageLoad();
        Cookie cookie = new Cookie("wixSession2", ConfigManager.get("wix.session.cookie"));
        cookie.domain = ".wix.com";
        cookie.path = "/";
        cookie.httpOnly = false;
        cookie.secure = false;
        context.addCookies(Collections.singletonList(cookie));
        navigateTo("https://manage.wix.com/account/sites");
        page.waitForURL("**/account/sites**");
        waitForPageLoad();
        System.out.println("Current URL: " + page.url());
    }
}