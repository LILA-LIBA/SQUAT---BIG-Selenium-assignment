package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageBase;
import pages.calculator.CalculatorPage;
import pages.draganddrop.DragAndDropPage;

public class MainPage extends PageBase {
	public static final By htmlFormTestLink = By.id("htmlformtest");
	public static final By adminLoginTestLink = By.id("adminlogin");
	public static final By hoverTestLink = By.id("csspseudohover");
	public static final By dragAndDropLink = By.id("useractionstest");
	public static final By calculatorLink = By.id("buttoncalculator");

	private final By explanationDiv = By.className("explanation");

	public MainPage(WebDriver driver, boolean navigate) {
		super(driver, new WebDriverWait(driver, 3));
		if (navigate) {
			super.driver.get("https://testpages.eviltester.com/styled/index.html");
		}
	}

	@Override
	public boolean isCorrectlyOpened() {
		return returnDefaultIfNotFound(() -> {
			WebElement explanation = waitAndReturnElement(explanationDiv);
			return getPageHeaderText().equals("Practice Applications and Pages For Automating and Testing") &&
					explanation.getText().contains("This is a set of applications and example pages for practicing Automation, Software Testing, Web Automating, Exploratory Testing, and JavaScript Hacking") &&
					getBodyText().contains("Examples") &&
					getBodyText().contains("Testing References") &&
					getBodyText().contains("Challenges");
		}, false);
	}

	public BasicHTMLFormPage navigateToBasicFormPage() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(htmlFormTestLink).click();
			return new BasicHTMLFormPage(driver, false);
		}, null);
	}

	public LoginPage navigateToAdminLoginPage() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(adminLoginTestLink).click();
			return new LoginPage(driver, false);
		}, null);
	}

	public HoverPage navigateToHoverPage() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(hoverTestLink).click();
			return new HoverPage(driver, false);
		}, null);
	}

	public DragAndDropPage navigateToDragAndDropPage() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(dragAndDropLink).click();
			return new DragAndDropPage(driver, false);
		}, null);
	}

	public CalculatorPage navigateToCalculatorPage() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(calculatorLink).click();
			return new CalculatorPage(driver, false);
		}, null);
	}
}
