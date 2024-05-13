import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import base.PageBase;
import pages.BasicHTMLFormPage;
import pages.HoverPage;
import pages.LoginPage;
import pages.MainPage;
import pages.calculator.CalculatorPage;
import pages.calculator.MemoryExecutors;
import pages.calculator.NumberExecutors;
import pages.calculator.OperationExecutors;
import pages.draganddrop.DragAndDropPage;
import pages.draganddrop.Draggables;
import pages.draganddrop.Droppables;


public class BigSeleniumAssignment {
	private WebDriver driver;

	private static final List<Constructor<? extends PageBase>> pages;
	static {
		try {
			pages = List.of(
					MainPage.class.getConstructor(WebDriver.class, boolean.class),
					LoginPage.class.getConstructor(WebDriver.class, boolean.class),
					BasicHTMLFormPage.class.getConstructor(WebDriver.class, boolean.class)
			);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Before
	public void Setup() throws MalformedURLException {
		ChromeOptions options = new ChromeOptions();
		driver = new RemoteWebDriver(URI.create("http://selenium:4444/wd/hub").toURL(), options);
		driver.manage().window().maximize();
	}

	@After
	public void close() {
		if (driver != null) {
			driver.quit();
		}
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Test
	public void multipleStaticPageTests() throws InvocationTargetException, InstantiationException, IllegalAccessException {
		//Multiple simple page tests from static list
		for (Constructor<? extends PageBase> pageConstructor : pages) {
			PageBase page = pageConstructor.newInstance(driver, true);
			assertNotNull(page);
			assertTrue(page.isCorrectlyOpened());
			System.out.println(page.getPageTitle());
		}
	}

	@Test
	public void mainPageTest() {
		MainPage mainPage = new MainPage(driver, true);
		System.out.println(mainPage.getBodyText());

		assertEquals("Web Testing and Automation Practice Application Pages", mainPage.getPageTitle());
		assertTrue(mainPage.isCorrectlyOpened());

		// Basic navigation tests
		LoginPage loginPage = mainPage.navigateToAdminLoginPage();
		assertNotNull(loginPage);
		assertTrue(loginPage.isCorrectlyOpened());
		assertNotNull(loginPage.navigateToMainPage());
		assertTrue(mainPage.isCorrectlyOpened());

		BasicHTMLFormPage htmlFormPage = mainPage.navigateToBasicFormPage();
		assertNotNull(htmlFormPage);
		assertTrue(htmlFormPage.isCorrectlyOpened());
		assertNotNull(htmlFormPage.navigateToMainPage());
		assertTrue(mainPage.isCorrectlyOpened());

		HoverPage hoverPage = mainPage.navigateToHoverPage();
		assertNotNull(hoverPage);
		assertTrue(hoverPage.isCorrectlyOpened());
		assertNotNull(hoverPage.navigateToMainPage());
		assertTrue(mainPage.isCorrectlyOpened());

		DragAndDropPage dragAndDropPage = mainPage.navigateToDragAndDropPage();
		assertNotNull(dragAndDropPage);
		assertTrue(dragAndDropPage.isCorrectlyOpened());
		assertNotNull(dragAndDropPage.navigateToMainPage());
		assertTrue(mainPage.isCorrectlyOpened());

		CalculatorPage calculatorPage = mainPage.navigateToCalculatorPage();
		assertNotNull(calculatorPage);
		assertTrue(calculatorPage.isCorrectlyOpened());
		assertNotNull(calculatorPage.navigateToMainPage());
		assertTrue(mainPage.isCorrectlyOpened());
	}

	@Test
	public void loginPageTests() {
		LoginPage loginPage = new LoginPage(driver, true);
		System.out.println(loginPage.getBodyText());

		assertEquals("Cookie Controlled Admin Login", loginPage.getPageTitle());
		assertTrue(loginPage.isCorrectlyOpened());

		// Admin is not Super Admin
		assertTrue(loginPage.login("Admin", "AdminPass"));
		assertTrue(loginPage.isAdminLoginSuccessful());
		assertFalse(loginPage.navigateToSuperAdminView());
		assertFalse(loginPage.isSuperAdminLoginSuccessful());

		// Check if user is still logged in
		MainPage mainPage = loginPage.navigateToMainPage();
		assertNotNull(mainPage);
		assertTrue(mainPage.isCorrectlyOpened());

		assertNotNull(mainPage.navigateToAdminLoginPage());
		assertTrue(loginPage.isAdminLoginSuccessful());

		assertTrue(loginPage.logout());
		assertTrue(loginPage.isCorrectlyOpened());

		// Super Admin is still an admin
		assertTrue(loginPage.login("SuperAdmin", "AdminPass"));
		assertTrue(loginPage.isSuperAdminLoginSuccessful());
		assertTrue(loginPage.navigateToAdminView());
		assertTrue(loginPage.isAdminLoginSuccessful());

		// Sending a simple form with a logged-in user
		BasicHTMLFormPage htmlFormPage = loginPage.navigateToBasicHtmlForm();
		assertNotNull(htmlFormPage);
		assertTrue(htmlFormPage.isCorrectlyOpened());
		assertTrue(htmlFormPage.setUsername("SuperAdmin"));
		assertTrue(htmlFormPage.setPassword("AdminPass"));
		assertEquals("SuperAdmin", htmlFormPage.getUsernameText());
		assertEquals("AdminPass", htmlFormPage.getPasswordText());
		assertTrue(htmlFormPage.submitForm());
		assertTrue(htmlFormPage.isCorrectlySubmitted());
		assertTrue(htmlFormPage.getSubmittedUsername().contains("SuperAdmin"));
		assertTrue(htmlFormPage.getSubmittedPassword().contains("AdminPass"));

		// Navigate back to user
		assertNotNull(htmlFormPage.navigateToMainPage());
		assertNotNull(mainPage.navigateToAdminLoginPage());
		assertTrue(loginPage.isSuperAdminLoginSuccessful());
	}

	@Test
	public void htmlFormPageTests() {
		BasicHTMLFormPage formPage = new BasicHTMLFormPage(driver, true);
		System.out.println(formPage.getBodyText());

		assertEquals("HTML Form Elements", formPage.getPageTitle());
		assertTrue(formPage.isCorrectlyOpened());

		// Fill a simple form
		assertTrue(formPage.setUsername("TestUsername"));
		assertEquals("TestUsername", formPage.getUsernameText());
		assertTrue(formPage.setPassword("TestPassword"));
		assertEquals("TestPassword", formPage.getPasswordText());
		assertTrue(formPage.setComments("TestComment"));
		assertEquals("TestComment", formPage.getCommentsText());
		assertTrue(formPage.setFileUploadInput("/etc/host.conf"));
		assertTrue(formPage.getFileUploadInputText().contains("host.conf"));

		assertTrue(formPage.getCheckboxValue(2));
		assertFalse(formPage.getCheckboxValue(0));
		assertTrue(formPage.checkCheckbox(0));
		assertTrue(formPage.getCheckboxValue(0));
		assertTrue(formPage.getCheckboxValue(2));

		assertTrue(formPage.getRadioButtonValue(1));
		assertFalse(formPage.getRadioButtonValue(0));
		assertTrue(formPage.clickRadioButton(0));
		assertTrue(formPage.getRadioButtonValue(0));
		assertFalse(formPage.getRadioButtonValue(1));

		assertTrue(formPage.selectMultiselectValue(0));
		assertTrue(formPage.selectMultiselectValue(1));
		assertTrue(formPage.selectMultiselectValue(3));

		assertTrue(formPage.selectDropdownValue(5));

		// Submit form
		assertTrue(formPage.submitForm());
		assertTrue(formPage.isCorrectlySubmitted());

		// Check submitted form
		assertTrue(formPage.getSubmittedUsername().contains("TestUsername"));
		assertTrue(formPage.getSubmittedPassword().contains("TestPassword"));
		assertTrue(formPage.getSubmittedComments().contains("TestComment"));
		assertTrue(formPage.getSubmittedFileName().contains("host.conf"));

		String submittedCheckboxValues = formPage.getSubmittedCheckboxValues();
		assertTrue(submittedCheckboxValues.contains("cb1"));
		assertFalse(submittedCheckboxValues.contains("cb2"));
		assertTrue(submittedCheckboxValues.contains("cb3"));

		String submittedRadioButtonValue = formPage.getSubmittedRadioButtonValue();
		assertTrue(submittedRadioButtonValue.contains("rd1"));
		assertFalse(submittedRadioButtonValue.contains("rd2"));
		assertFalse(submittedRadioButtonValue.contains("rd3"));

		String submittedMultiSelectValues = formPage.getSubmittedMultiSelectValues();
		assertTrue(submittedMultiSelectValues.contains("ms1"));
		assertTrue(submittedMultiSelectValues.contains("ms2"));
		assertFalse(submittedMultiSelectValues.contains("ms3"));
		assertTrue(submittedMultiSelectValues.contains("ms4"));

		String submittedDropdownValue = formPage.getSubmittedDropdownValues();
		assertFalse(submittedDropdownValue.contains("dd1"));
		assertFalse(submittedDropdownValue.contains("dd2"));
		assertFalse(submittedDropdownValue.contains("dd3"));
		assertFalse(submittedDropdownValue.contains("dd4"));
		assertFalse(submittedDropdownValue.contains("dd5"));
		assertTrue(submittedDropdownValue.contains("dd6"));

		// Go back
		assertTrue(formPage.backToForm());
		assertTrue(formPage.isCorrectlyOpened());
	}

	@Test
	public void hoverPageTests() {
		HoverPage hoverPage = new HoverPage(driver, true);
		System.out.println(hoverPage.getBodyText());

		assertEquals("Test Page For Element Attributes", hoverPage.getPageTitle());
		assertTrue(hoverPage.isCorrectlyOpened());

		// The text below is not part of the page's body until the given element is hovered over
		assertFalse(hoverPage.getBodyText().contains("You can see this paragraph now that you hovered on the above 'button'."));
		assertTrue(hoverPage.executeWhileHoveringOverParagraph(actions -> hoverPage.getBodyText()).contains("You can see this paragraph now that you hovered on the above 'button'."));

		// You can't see the text or click the link below until you hover over the given element
		assertFalse(hoverPage.getBodyText().contains("You can see this child div content now that you hovered on the above 'button'."));
		assertFalse(hoverPage.clickMe());
		assertTrue(hoverPage.executeWhileHoveringOverDiv(actions -> hoverPage.getBodyText()).contains("You can see this child div content now that you hovered on the above 'button'."));
		assertTrue(hoverPage.executeWhileHoveringOverDiv(actions -> hoverPage.clickMe()));
	}

	// Manipulation cookies to grant SuperAdmin privileges to a simple Admin user
	@Test
	public void cookieManipulationTest() {
		LoginPage loginPage = new LoginPage(driver, true);
		System.out.println(loginPage.getBodyText());

		assertEquals("Cookie Controlled Admin Login", loginPage.getPageTitle());
		assertTrue(loginPage.isCorrectlyOpened());

		// Admin is not Super Admin
		assertTrue(loginPage.login("Admin", "AdminPass"));
		assertTrue(loginPage.isAdminLoginSuccessful());
		assertFalse(loginPage.navigateToSuperAdminView());
		assertFalse(loginPage.isSuperAdminLoginSuccessful());

		// Cookie manipulation: Admin became SuperAdmin
		loginPage.clearAndAddAdminCookie(true);
		loginPage.refreshPage();
		assertTrue(loginPage.navigateToSuperAdminView());
		assertTrue(loginPage.isSuperAdminLoginSuccessful());

		// Cookie manipulation: Logging out
		loginPage.deleteAllCookies();
		loginPage.refreshPage();
		assertFalse(loginPage.navigateToSuperAdminView());
		assertFalse(loginPage.isAdminLoginSuccessful());
		assertFalse(loginPage.navigateToSuperAdminView());
		assertFalse(loginPage.isSuperAdminLoginSuccessful());

		// Cookie manipulation: Logging in as Admin
		loginPage.clearAndAddAdminCookie(false);
		loginPage.refreshPage();
		assertTrue(loginPage.isAdminLoginSuccessful());
		assertFalse(loginPage.navigateToSuperAdminView());
		assertFalse(loginPage.isSuperAdminLoginSuccessful());
	}

	@Test
	public void dragAndDropTest() {
		DragAndDropPage dragAndDropPage = new DragAndDropPage(driver, true);
		System.out.println(dragAndDropPage.getBodyText());

		assertEquals("GUI User Interactions", dragAndDropPage.getPageTitle());
		assertTrue(dragAndDropPage.isCorrectlyOpened());

		// Check the initial states of the boxes (divs)
		assertTrue(dragAndDropPage.getDroppableText(Droppables.DIV_1).contains("Drop here"));
		assertTrue(dragAndDropPage.getDroppableText(Droppables.DIV_2).contains("No Drop here"));

		// Drag and drop yellow box 1 onto red box 1 then outside
		assertTrue(dragAndDropPage.executeDraggingAndDropping(Draggables.DIV_1, Droppables.DIV_1));
		assertTrue(dragAndDropPage.getDroppableText(Droppables.DIV_1).contains("Dropped!"));
		assertTrue(dragAndDropPage.executeDraggingAndDropping(Draggables.DIV_1, Droppables.OUTSIDE));

		// Drag and drop yellow box 2 onto red box 1 then outside
		assertTrue(dragAndDropPage.executeDraggingAndDropping(Draggables.DIV_2, Droppables.DIV_1));
		assertTrue(dragAndDropPage.getDroppableText(Droppables.DIV_1).contains("Get Off Me!"));
		assertTrue(dragAndDropPage.executeDraggingAndDropping(Draggables.DIV_2, Droppables.OUTSIDE));

		// Drag and drop yellow box 2 onto red box 1 then outside
		assertTrue(dragAndDropPage.executeDraggingAndDropping(Draggables.DIV_1, Droppables.DIV_2));
		assertTrue(dragAndDropPage.getDroppableText(Droppables.DIV_2).contains("Dropped!"));
		assertTrue(dragAndDropPage.executeDraggingAndDropping(Draggables.DIV_1, Droppables.OUTSIDE));

		// Drag and drop yellow box 2 onto red box 2 then outside
		assertTrue(dragAndDropPage.executeDraggingAndDropping(Draggables.DIV_2, Droppables.DIV_2));
		assertTrue(dragAndDropPage.getDroppableText(Droppables.DIV_2).contains("Dropped!"));
		assertTrue(dragAndDropPage.executeDraggingAndDropping(Draggables.DIV_2, Droppables.OUTSIDE));
	}

	@Test
	public void calculatorWithRandomDataTest() {
		CalculatorPage calculatorPage = new CalculatorPage(driver, true);
		System.out.println(calculatorPage.getBodyText());

		assertEquals("Calculator", calculatorPage.getPageTitle());
		assertTrue(calculatorPage.isCorrectlyOpened());

		// Manual button pressing tests
		assertTrue(calculatorPage.pressButton(NumberExecutors.TWO));
		assertTrue(calculatorPage.pressButton(OperationExecutors.PLUS));
		assertTrue(calculatorPage.pressButton(NumberExecutors.TWO));
		assertTrue(calculatorPage.pressButton(OperationExecutors.EQUALS));
		assertEquals("4", calculatorPage.getResult());

		assertTrue(calculatorPage.pressButton(OperationExecutors.MULTIPLY));
		assertTrue(calculatorPage.pressButton(NumberExecutors.FOUR));
		assertTrue(calculatorPage.pressButton(OperationExecutors.EQUALS));
		assertEquals("16", calculatorPage.getResult());

		assertTrue(calculatorPage.pressButton(OperationExecutors.MULTIPLY));
		assertTrue(calculatorPage.pressButton(NumberExecutors.NINE));
		assertTrue(calculatorPage.pressButton(MemoryExecutors.CLEAR_ENTRY));
		assertTrue(calculatorPage.pressButton(NumberExecutors.TWO));
		assertTrue(calculatorPage.pressButton(OperationExecutors.EQUALS));
		assertEquals("32", calculatorPage.getResult());

		assertTrue(calculatorPage.pressButton(MemoryExecutors.ALL_CLEAR));
		assertEquals("", calculatorPage.getResult());

		// Simple calculation tests
		assertEquals("18", calculatorPage.calculate(NumberExecutors.NINE, OperationExecutors.PLUS, NumberExecutors.NINE));
		calculatorPage.clearAll();
		assertEquals("1.6", calculatorPage.calculate(NumberExecutors.EIGHT, OperationExecutors.DIVIDE, NumberExecutors.FIVE));
		calculatorPage.clearAll();
		assertEquals("0", calculatorPage.calculate(NumberExecutors.ZERO, OperationExecutors.DIVIDE, NumberExecutors.ONE));
		calculatorPage.clearAll();

		// Calculation script tests
		assertEquals("172125", calculatorPage.calculate("459*375"));
		calculatorPage.clearAll();
		assertEquals("18561.418685121105", calculatorPage.calculate("1072.85/0.0578"));
		calculatorPage.clearAll();
		assertEquals("-469.5238095238095", calculatorPage.calculate("12*25+1001/42-500.5"));
		calculatorPage.clearAll();
		assertEquals("32", calculatorPage.calculate("gd=gs2*=sídg2*2*f=fsa2*2========="));
		calculatorPage.clearAll();

		// Random calculations tests
		assertTrue(calculatorPage.testRandomData(100));
	}
}
