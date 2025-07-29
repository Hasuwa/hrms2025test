package scenario;

import static org.testng.Assert.*;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {

	WebDriver driver;
	Duration waitTime;
	WebDriverWait wait;

	// アプリのパス
	static final String SHINABLE_CLOUD_PATH = "http://localhost:8081/hrms2025/login";

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

		public void testDirectDateInput(String xpath, String text) {
				// 日付入力フィールドを取得（IDやnameなどは実際のHTMLに合わせて変更）
				WebElement dateInput = driver.findElement(By.xpath(xpath));
				// 直接日付を入力（フォーマットは input の仕様に合わせる）

				((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", dateInput, text);


				// 必要に応じて、送信ボタンをクリックしたり、値の検証を行う
				// WebElement submitButton = driver.findElement(By.id("submit"));
				// submitButton.click();
		}


	/**
	 * ログイン
	 * @param usernameInput
	 * @param passwordInput
	 */
	public void login(String usernameInput, String passwordInput) {
		final String MAILADDRESS_INPUT_XPATH = "//*[@id=\"usernameInput\"]";
		final String PASSWORD_INPUT_XPATH = "//*[@id=\"passwordInput\"]";
		final String LOGIN_BUTTON_XPATH = "/html/body/div[2]/div/form/div[3]/input";
		// input mailaddress
		input(MAILADDRESS_INPUT_XPATH, usernameInput);
		// input password
		input(PASSWORD_INPUT_XPATH, passwordInput);
		// click login button
		click(LOGIN_BUTTON_XPATH);
	}

	/**
	 * 社員情報を入力
	 * @param employeeNum
	 * @param mailaddress
	 */
	public void inputEmployeeInfo(String employeeNo, String mailAddress) {
		// input employee num
		input("//*[@id=\"employeeNo\"]", employeeNo);
		// input name
		input("//*[@id=\"fullName\"]", "テスト太郎");
		// input mailaddress
		input("//*[@id=\"mailAddress\"]", mailAddress);
		//select 性別
		click("//*[@id=\"gender1\"]");
		//input 入社日
		testDirectDateInput("//*[@id=\"hireDate\"]","2025-01-01");
		//input 生年月日
		testDirectDateInput("//*[@id=\"birthDate\"]","2000-01-01");
		// select rank
		click("//*[@id=\"employeeRank\"]");
		click("//*[@id=\"employeeRank\"]/div[1]/option");
		// select group
//		click("/html/body/div[2]/form/table/tbody/tr[9]/td/button");
//		System.out.println("ここまで");
//
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//		// モーダルが表示されるまで待機
////		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"organizationModal\"]")));
//		
//
//		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/form/table/tbody/tr[9]/td/button")));
//		
//		driver.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[9]/td/button")).click();
//		

		WebElement button = driver.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[9]/td/button"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

		
		// dialog

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[3]/button")));
		click("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[3]/button");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"38b3cfbb-07c2-4eb9-af49-6be4bec9d5b0\"]/li[3]/button")));
		click("//*[@id=\"38b3cfbb-07c2-4eb9-af49-6be4bec9d5b0\"]/li[3]/button");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"11068ebb-2443-48a6-84fb-4d9796288f96\"]/li")));  
		click("//*[@id=\"11068ebb-2443-48a6-84fb-4d9796288f96\"]/li/span");
		
		//入力出来ていない時があったのでリトライを入れる
//		if (driver.findElement(By.xpath("//*[@id=\"organizationName\"]")).getText().equals(null)) {
//			// select group
//			click("//*[@id=\"open\"]");
//			//dialog
//			click("//*[@id=\"node0\"]");
//			click("//*[@id=\"select\"]");
//		}
		//input password
		input("//*[@id=\"password\"]", "P@ssw0rd");
		input("//*[@id=\"confirmPassword\"]","P@ssw0rd");
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
