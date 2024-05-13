package pages.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageBase;

public class CalculatorPage extends PageBase {
	private final By calculatedDisplayLocator = By.id("calculated-display");

	private final Random randomGenerator = new Random();

	private final JavascriptExecutor jse;

	public CalculatorPage(WebDriver driver, boolean navigate) {
		super(driver, new WebDriverWait(driver, 2));
		jse = (JavascriptExecutor) driver;
		if (navigate) {
			super.driver.get("https://testpages.eviltester.com/styled/apps/calculator.html");
		}
	}

	@Override
	public boolean isCorrectlyOpened() {
		return getPageHeaderText().equals("Button Based Calculator") &&
				getBodyText().contains("This is a simple button based calculator.");
	}

	public String calculate(NumberExecutors number1, OperationExecutors operation1, NumberExecutors number2) {
		return returnDefaultIfNotFound(() -> {
			jse.executeScript(number1.getScript());
			jse.executeScript(operation1.getScript());
			jse.executeScript(number2.getScript());
			jse.executeScript(OperationExecutors.EQUALS.getScript());
			return waitAndReturnElement(calculatedDisplayLocator).getAttribute("value");
		}, null);
	}

	public String calculate(String script) {
		return returnDefaultIfNotFound(() -> {
			for (CalculatorExecutors button : parseCalculatorString(script.replaceAll("[^0-9|+\\-*/.]", ""))) {
				jse.executeScript(button.getScript());
			}
			jse.executeScript(OperationExecutors.EQUALS.getScript());
			return waitAndReturnElement(calculatedDisplayLocator).getAttribute("value");
		}, null);
	}

	public boolean testRandomData(int testCases) {
		assert testCases > 0;
		for (int i = 0; i < testCases; i++) {
			int number1 = randomGenerator.nextInt(1_000_000);
			int number2 = randomGenerator.nextInt(1_000_000);
			List<NumberExecutors> numberExecutors1 = parseCalculatorString(String.valueOf(number1));
			List<NumberExecutors> numberExecutors2 = parseCalculatorString(String.valueOf(number2));
			OperationExecutors operator = OperationExecutors.values()[randomGenerator.nextInt(1, OperationExecutors.values().length - 1)];

			numberExecutors1.forEach(this::pressButton);
			pressButton(operator);
			numberExecutors2.forEach(this::pressButton);
			pressButton(OperationExecutors.EQUALS);

			if (Long.parseLong(waitAndReturnElement(calculatedDisplayLocator).getAttribute("value")
					.replaceAll("\\.(.*)", "")) != executeOperation(operator.getSymbol(), number1, number2)) {
				return false;
			}

			pressButton(MemoryExecutors.ALL_CLEAR);
		}
		return true;
	}

	public boolean pressButton(CalculatorExecutors button) {
		return returnDefaultIfNotFound(() -> {
			jse.executeScript(button.getScript());
			return true;
		}, false);
	}

	public boolean clearEntry() {
		return returnDefaultIfNotFound(() -> {
			jse.executeScript(MemoryExecutors.CLEAR_ENTRY.getScript());
			return waitAndReturnElement(calculatedDisplayLocator).getAttribute("value") == null;
		}, false);
	}

	public boolean clearAll() {
		return returnDefaultIfNotFound(() -> {
			jse.executeScript(MemoryExecutors.ALL_CLEAR.getScript());
			return waitAndReturnElement(calculatedDisplayLocator).getAttribute("value") == null;
		}, false);
	}

	public String getResult() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(calculatedDisplayLocator).getAttribute("value"), null);
	}

	private <E extends CalculatorExecutors> List<E> parseCalculatorString(String script) {
		List<E> calculatorButtons = new ArrayList<>();
		script.chars().forEach(c -> {
			E button = (E) NumberExecutors.getExecutor(Character.toString(c));
			if (button == null) {
				button = (E) OperationExecutors.getExecutor(Character.toString(c));
			}

			if (button == null) {
				throw new IllegalArgumentException("Unknown button: " + Character.toString(c));
			} else {
				calculatorButtons.add(button);
			}
		});
		return calculatorButtons;
	}

	private long executeOperation(String operator, int number1, int number2) {
		return switch (operator) {
			case "+" -> (long) number1 + number2;
			case "-" -> (long) number1 - number2;
			case "*" -> (long) number1 * number2;
			case "/" -> (long) number1 / number2;
			default -> throw new IllegalArgumentException("Unknown operator: " + operator);
		};
	}
}
