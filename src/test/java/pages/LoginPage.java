package pages;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageBase;

public class LoginPage extends PageBase {
	private final By explanationDiv = By.xpath("//div[contains(@class, 'explanation')]");

	private final By usernameField = By.id("username");
	private final By passwordField = By.id("password");
	private final By loginButton = By.id("login");
	private final By logoutButton = By.id("navadminlogout");

	private final By adminViewLink = By.id("navadminview");
	private final By superAdminViewLink = By.id("navadminsuperview");

	public LoginPage(WebDriver driver, boolean navigate) {
		super(driver, new WebDriverWait(driver, 2));
		if (navigate) {
			super.driver.get("https://testpages.eviltester.com/styled/cookies/adminlogin.html");
		}
	}

	@Override
	public boolean isCorrectlyOpened() {
		return returnDefaultIfNotFound(() -> {
			WebElement explanation = waitAndReturnElement(explanationDiv);
			return getPageHeaderText().equals("Cookie Controlled Admin") &&
					explanation.getText().contains("Once you login using the form below") &&
					getBodyText().contains("PS: Admin / AdminPass");
		}, false);
	}

	public boolean login(String username, String password) {
		return returnDefaultIfNotFound(() -> {
			WebElement usernameElement = waitAndReturnElement(usernameField);
			WebElement passwordElement = waitAndReturnElement(passwordField);
			WebElement loginButtonElement = waitAndReturnElement(loginButton);

			usernameElement.sendKeys(username);
			passwordElement.sendKeys(password);

			loginButtonElement.click();
			return true;
		}, false);
	}

	public boolean logout() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(logoutButton).click();
			return true;
		}, false);
	}

	public boolean isAdminLoginSuccessful() {
		return returnDefaultIfNotFound(() -> getPageHeaderText().equals("Admin View") &&
				getBodyText().contains("You are logged in to the Admin View."),
				false);
	}

	public boolean isSuperAdminLoginSuccessful() {
		return returnDefaultIfNotFound(() -> getPageHeaderText().equals("Super Admin View") &&
						getBodyText().contains("You are logged in to the Super Admin View."),
				false);
	}

	public boolean navigateToAdminView() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(adminViewLink).click();
			return isAdminLoginSuccessful();
		}, false);
	}

	public boolean navigateToSuperAdminView() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(superAdminViewLink).click();
			return isSuperAdminLoginSuccessful();
		}, false);
	}

	public BasicHTMLFormPage navigateToBasicHtmlForm() {
		return returnDefaultIfNotFound(() -> navigateToMainPage().navigateToBasicFormPage(), null);
	}

	public void clearAndAddAdminCookie(boolean isSuper) {
		driver.manage().deleteAllCookies();
		driver.manage().addCookie(new Cookie.Builder("loggedin", isSuper ? "SuperAdmin" : "Admin")
				.domain("testpages.eviltester.com")
				.expiresOn(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()))
				.path("/").build());
	}

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}
}
