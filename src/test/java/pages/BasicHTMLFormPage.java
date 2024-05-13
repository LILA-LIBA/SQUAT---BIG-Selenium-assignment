package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.PageBase;

public class BasicHTMLFormPage extends PageBase {
	private final By usernameField = By.name("username");
	private final By passwordField = By.name("password");
	private final By commentsArea = By.name("comments");
	private final By fileUploadInput = By.name("filename");
	private final By[] checkboxes = new By[] {
			By.xpath("//input[@type='checkbox'][@value='cb1']"),
			By.xpath("//input[@type='checkbox'][@value='cb2']"),
			By.xpath("//input[@type='checkbox'][@value='cb3']") };
	private final By[] radioButtons = new By[] {
			By.xpath("//input[@type='radio'][@value='rd1']"),
			By.xpath("//input[@type='radio'][@value='rd2']"),
			By.xpath("//input[@type='radio'][@value='rd3']") };
	private final By multiSelectValues = By.name("multipleselect[]");
	private final By dropdownValues = By.name("dropdown");
	private final By cancelButton = By.xpath("//input[@type='reset']");
	private final By submitButton = By.xpath("//input[@type='submit']");

	private final By submittedUsername = By.id("_username");
	private final By submittedPassword = By.id("_password");
	private final By submittedComments = By.id("_comments");
	private final By submittedFileName = By.id("_filename");
	private final By submittedCheckboxValues = By.id("_checkboxes");
	private final By submittedRadioButtonValue = By.id("_radioval");
	private final By submittedMultiSelectValues = By.id("_multipleselect");
	private final By submittedDropdownValues = By.id("_dropdown");
	private final By backToFormButton = By.id("back_to_form");

//--------------------------------------------------------------------------------------------------------------------------------------------------------------

	public BasicHTMLFormPage(WebDriver driver, boolean navigate) {
		super(driver, new WebDriverWait(driver, 5));
		if (navigate) {
			super.driver.get("https://testpages.eviltester.com/styled/basic-html-form-test.html");
		}
	}

	@Override
	public boolean isCorrectlyOpened() {
		return getPageHeaderText().equals("Basic HTML Form Example") &&
				getBodyText().contains("Submit this simple HTML form will POST your details to be processed by another part of the application and shown on a results page.");
	}

	public boolean isCorrectlySubmitted() {
		return getPageHeaderText().equals("Processed Form Details") &&
				getBodyText().contains("You submitted a form. The details below show the values you entered for processing.");
	}

	public boolean submitForm() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(submitButton).click();
			return true;
		}, false);
	}

	public boolean clearForm() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(cancelButton).click();
			return true;
		}, false);
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------

	public boolean setUsername(String username) {
		return returnDefaultIfNotFound(() -> {
			WebElement usernameElement = waitAndReturnElement(usernameField);
			usernameElement.clear();
			usernameElement.sendKeys(username);
			return true;
		}, false);
	}

	public boolean setPassword(String password) {
		return returnDefaultIfNotFound(() -> {
			WebElement passwordElement = waitAndReturnElement(passwordField);
			passwordElement.clear();
			passwordElement.sendKeys(password);
			return true;
		}, false);
	}

	public boolean setComments(String comments) {
		return returnDefaultIfNotFound(() -> {
			WebElement commentsElement = waitAndReturnElement(commentsArea);
			commentsElement.clear();
			commentsElement.sendKeys(comments);
			return true;
		}, false);
	}

	public boolean setFileUploadInput(String filePath) {
		return returnDefaultIfNotFound(() -> {
			WebElement fileInput = waitAndReturnElement(fileUploadInput);
			fileInput.sendKeys(filePath);
			return true;
		}, false);
	}

	public boolean checkCheckbox(int index) {
		return returnDefaultIfNotFound(() -> {
			try {
				waitAndReturnElement(checkboxes[index]).click();
				return true;
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
		}, false);
	}

	public boolean clickRadioButton(int index) {
		return returnDefaultIfNotFound(() -> {
			try {
				waitAndReturnElement(radioButtons[index]).click();
				return true;
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
		}, false);
	}

	public boolean selectMultiselectValue(int index) {
		return returnDefaultIfNotFound(() -> {
			Select select = new Select(waitAndReturnElement(multiSelectValues));
			select.selectByIndex(index);
			return true;
		}, false);
	}

	public boolean selectDropdownValue(int index) {
		return returnDefaultIfNotFound(() -> {
			Select select = new Select(waitAndReturnElement(dropdownValues));
			select.selectByIndex(index);
			return true;
		}, false);
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------

	public String getUsernameText() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(usernameField).getAttribute("value"), null);
	}

	public String getPasswordText() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(passwordField).getAttribute("value"), null);
	}

	public String getCommentsText() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(commentsArea).getAttribute("value"), null);
	}

	public String getFileUploadInputText() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(fileUploadInput).getAttribute("value"), null);
	}

	public boolean getCheckboxValue(int index) {
		return returnDefaultIfNotFound(() -> "true".equals(waitAndReturnElement(checkboxes[index]).getAttribute("checked")), null);
	}

	public boolean getRadioButtonValue(int index) {
		return returnDefaultIfNotFound(() -> "true".equals(waitAndReturnElement(radioButtons[index]).getAttribute("checked")), null);
	}

	public String[] getMultiSelectValuesText() {
		return returnDefaultIfNotFound(() -> {
			Select select = new Select(waitAndReturnElement(multiSelectValues));
			return select.getAllSelectedOptions().stream().map(WebElement::getText).toArray(String[]::new);
		}, null);
	}

	public String getDropdownValueText() {
		return returnDefaultIfNotFound(() -> {
			Select select = new Select(waitAndReturnElement(dropdownValues));
			return select.getFirstSelectedOption().getText();
		}, null);
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------


	public String getSubmittedDropdownValues() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(submittedDropdownValues).getText(), null);
	}

	public String getSubmittedMultiSelectValues() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(submittedMultiSelectValues).getText(), null);
	}

	public String getSubmittedRadioButtonValue() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(submittedRadioButtonValue).getText(), null);
	}

	public String getSubmittedCheckboxValues() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(submittedCheckboxValues).getText(), null);
	}

	public String getSubmittedFileName() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(submittedFileName).getText(), null);
	}

	public String getSubmittedComments() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(submittedComments).getText(), null);
	}

	public String getSubmittedPassword() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(submittedPassword).getText(), null);
	}

	public String getSubmittedUsername() {
		return returnDefaultIfNotFound(() -> waitAndReturnElement(submittedUsername).getText(), null);
	}

	public boolean backToForm() {
		return returnDefaultIfNotFound(() -> {
			waitAndReturnElement(backToFormButton).click();
			return true;
		}, false);
	}
}
