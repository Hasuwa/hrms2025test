package scenario;

import static org.testng.Assert.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {

	WebDriver driver;
	Duration waitTime;
	WebDriverWait wait;

	// アプリのパス
	static final String SHINABLE_CLOUD_PATH = "https://shinable.azurewebsites.net/login";

	/**
	 * 入力
	 * @param xpath
	 * @param data
	 */
	public void input(String xpath, String data) {
		waitForElementVisible(xpath);
		driver.findElement(By.xpath(xpath)).sendKeys(data);
	}

	/**
	 * クリック
	 * @param xpath
	 */
	public void click(String xpath) {
		waitForElementVisible(xpath);
		driver.findElement(By.xpath(xpath)).click();
	}

	/**
	 * 表示待ち
	 * @param xpath
	 */
	public void waitForElementVisible(String xpath) {
		waitTime = Duration.ofSeconds(20);
		wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	/**
	 * テキストの表示待ち
	 * @param xpath
	 */
	public void waitForElementTextChange(String xpath, String text) {
		waitTime = Duration.ofSeconds(20);
		wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(xpath)), text));
	}

	/**
	 * テキストの確認
	 * @param xpath
	 * @param name
	 */
	public void assertText(String xpath, String text) {
		waitForElementVisible(xpath);
		assertEquals(driver.findElement(By.xpath(xpath)).getText(), text);
	}

	/**
	 * ログイン
	 * @param mailAddress
	 * @param password
	 */
	public void login(String mailAddress, String password) {
		final String MAILADDRESS_INPUT_XPATH = "//*[@id=\"mailAddress\"]";
		final String PASSWORD_INPUT_XPATH = "//*[@id=\"password\"]";
		final String LOGIN_BUTTON_XPATH = "//button[text()='ログイン']";
		// input mailaddress
		input(MAILADDRESS_INPUT_XPATH, mailAddress);
		// input password
		input(PASSWORD_INPUT_XPATH, password);
		// click login button
		click(LOGIN_BUTTON_XPATH);
	}

	/**
	 * 社員情報を入力
	 * @param employeeNum
	 * @param mailaddress
	 */
	public void inputEmployeeInfo(String employeeNum, String mailaddress) {
		// input employee num
		input("//*[@id=\"number\"]", employeeNum);
		// input name
		input("//*[@id=\"fullName\"]", "日本プロ太");
		// input mailaddress
		input("//*[@id=\"mailAddress\"]", mailaddress);
		// select rank
		click("/html/body/div[2]/div/form/div[8]/div/div/input");
		click("//*[@class=\"dropdown-content select-dropdown\"]/li[2]");
		// select group
		click("//*[@id=\"open\"]");
		// dialog
		click("//*[@id=\"node0\"]");
		click("//*[@id=\"select\"]");
		//入力出来ていない時があったのでリトライを入れる
		if (driver.findElement(By.xpath("//*[@id=\"organizationName\"]")).getText().equals(null)) {
			// select group
			click("//*[@id=\"open\"]");
			//dialog
			click("//*[@id=\"node0\"]");
			click("//*[@id=\"select\"]");
		}
		//input password
		input("//*[@id=\"password\"]", "P@ssw0rd");
	}

	/**
	 * ページタイトルの確認
	 * @param page title
	 */
	public void assertPageTitle(String title) {
		waitForElementTextChange("/html/body/div[2]/h1", title);
		ExpectedConditions.titleIs(title);
	}

	/**
	 * ページタイトルの確認（メッセージバー表示時）
	 * @param page title
	 */
	public void assertPageTitleDisplayedMessageBar(String title) {
		waitForElementTextChange("/html/body/div[2]/div[1]/h1", title);
		ExpectedConditions.titleIs(title);
	}

	/**
	 * メッセージバーの確認
	 * @param message
	 */
	public void assertMessageBar(String message) {
		waitForElementVisible("//*[@id=\"messageBar\"]");
		assertText("//*[@id=\"messageBar\"]/span", message);
	}
}
