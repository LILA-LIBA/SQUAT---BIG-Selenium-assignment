package pages.draganddrop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageBase;

public class DragAndDropPage extends PageBase {
	public DragAndDropPage(WebDriver driver, boolean navigate) {
		super(driver, new WebDriverWait(driver, 2));
		if (navigate) {
			super.driver.get("https://testpages.eviltester.com/styled/drag-drop-javascript.html");
		}
	}

	@Override
	public boolean isCorrectlyOpened() {
		return getPageHeaderText().equals("Drag And Drop Examples") &&
				getBodyText().contains("You can move the yellow squares to drag them on the red drop points.");
	}

	public boolean executeDraggingAndDropping(Draggables draggable, Droppables droppable) {
		return returnDefaultIfNotFound(() -> {
			WebElement draggableDiv = waitAndReturnElement(draggable.getLocator());
			WebElement droppableDiv = waitAndReturnElement(droppable.getLocator());
			Actions actions = new Actions(driver);

			actions.dragAndDrop(draggableDiv, droppableDiv).perform();
			return true;
		}, false);
	}

	public String getDroppableText(Droppables droppable) {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(droppable.getLocator()).getText(), null);
	}
}
