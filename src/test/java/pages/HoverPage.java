package pages;

import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageBase;

public class HoverPage extends PageBase {
	private final By hoverParaLocator = By.xpath("//*[@id='hoverpara']/parent::div");
	private final By hoverDivLocator = By.id("hoverdiv");
	private final By hoverLinkLocator = By.id("hoverlink");


	public HoverPage(WebDriver driver, boolean navigate) {
		super(driver, new WebDriverWait(driver, 2));
		if (navigate) {
			super.driver.get("https://testpages.eviltester.com/styled/csspseudo/css-hover.html");
		}
	}

	@Override
	public boolean isCorrectlyOpened() {
		return getPageHeaderText().equals("CSS Pseudo Class - hover") &&
				getBodyText().contains("In this example the elements on the left have 'hover' implemented.");
	}

	public <E> E executeWhileHoveringOverParagraph(Function<Actions, E> action) {
		return returnDefaultIfNotFound(() -> {
			WebElement hoverParagraph = waitAndReturnElement(hoverParaLocator);
			Actions actions = new Actions(driver);

			actions.moveToElement(hoverParagraph).perform();
			return action.apply(actions);
		}, null);
	}

	public <E> E executeWhileHoveringOverDiv(Function<Actions, E> action) {
		return returnDefaultIfNotFound(() -> {
			WebElement hoverDiv = waitAndReturnElement(hoverDivLocator);
			Actions actions = new Actions(driver);

			actions.moveToElement(hoverDiv).perform();
			return action.apply(actions);
		}, null);
	}

	public boolean clickMe() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(hoverLinkLocator).click();
			return true;
		}, false);
	}
}
