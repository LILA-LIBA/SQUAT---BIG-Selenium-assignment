package base;

import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.MainPage;

public abstract class PageBase {
	protected WebDriver driver;
	protected WebDriverWait wait;

	private final By bodyLocator = By.tagName("body");
	private final By pageHeaderLocator = By.tagName("h1");
	private final By indexLink = By.xpath("//a[contains(@href, 'index.html')]");

	public PageBase(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	protected WebElement waitAndReturnElement(By locator) throws TimeoutException {
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return driver.findElement(locator);
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getPageHeaderText() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(pageHeaderLocator).getText(), "");
	}

	public String getBodyText() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(bodyLocator).getText(), "");
	}

	public MainPage navigateToMainPage() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(indexLink).click();
			return new MainPage(driver, false);
		}, null);
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public abstract boolean isCorrectlyOpened();

	protected <E> E returnDefaultIfNotFound(Supplier<E> lambda, E defaultReturn) {
		try {
			return lambda.get();
		} catch (TimeoutException e) {
			return defaultReturn;
		}
	}
}
