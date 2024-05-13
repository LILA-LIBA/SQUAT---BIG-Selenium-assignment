package pages.draganddrop;

import org.openqa.selenium.By;

public enum Draggables {
	DIV_1(By.id("draggable1")), DIV_2(By.id("draggable2"));
	private final By locator;

	Draggables(By locator) {
		this.locator = locator;
	}

	public By getLocator() {
		return locator;
	}
}
