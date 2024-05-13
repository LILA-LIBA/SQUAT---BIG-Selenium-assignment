package pages.draganddrop;

import org.openqa.selenium.By;

public enum Droppables {
	DIV_1(By.id("droppable1")), DIV_2(By.id("droppable2")), OUTSIDE(By.className("explanation"));
	private final By locator;

	Droppables(By locator) {
		this.locator = locator;
	}

	public By getLocator() {
		return locator;
	}
}
