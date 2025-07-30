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

	/**
	 * ログイン
	 * @param mailAddress
	 * @param password
	 */
	public void login(String mailAddress, String password) {
		final String MAILADDRESS_INPUT_XPATH = "//*[@id=\"usernameInput\"]";
		final String PASSWORD_INPUT_XPATH = "//*[@id=\"passwordInput\"]";
		final String LOGIN_BUTTON_XPATH = "/html/body/div[2]/div/form/div[3]/input";
		// input mailaddress
		input(MAILADDRESS_INPUT_XPATH, mailAddress);
		// input password
		input(PASSWORD_INPUT_XPATH, password);
		// click login button
		click(LOGIN_BUTTON_XPATH);
	}


	public void setDate(String elementId, String dateValue) {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("document.getElementById('" + elementId + "').value='" + dateValue + "';");
	}


	/**
	 * 社員情報を入力
	 * @param employeeNum
	 * @param mailaddress
	 * @throws InterruptedException
	 */
	public void inputEmployeeInfo(String employeeNo, String mailAddress) throws InterruptedException {
		// input employee num
	    input("//*[@id=\"employeeNo\"]", employeeNo);
		// input name
		input("//*[@id=\"fullName\"]", "日本プロ太");
		// input mailaddress
		input("//*[@id=\"mailAddress\"]", mailAddress);
		//2-3-4 性別入力
        click("//*[@id=\"gender1\"]");
        //2-3-5 入社日選択
        setDate("hireDate", "2025-04-01");
        //2-3-6 退社日選択
        setDate("quitDate", "2027-03-29");
        //2-3-6 生年月日入力
        setDate("birthDate", "2002-05-02");
		// select rank
        click("//*[@id=\"employeeRank\"]");
        click("//*[@id=\"employeeRank\"]/div[1]/option");
        //2-3-8 所属入力
        WebElement button =
            driver.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[9]/td/button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        // dialog
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[3]/button")));
        click("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[3]/span");
        //入力出来ていない時があったのでリトライを入れる
		if (driver.findElement(By.xpath("//*[@id=\"selectedOrganizationName\"]")).getText().equals(null)) {
			// select group
			click("/html/body/div[2]/form/table/tbody/tr[9]/td/button");
			//dialog
			click("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[3]/span");

		}
		//2-3-9 パスワード入力
        input("//*[@id=\"password\"]", "p@ss");
        //2-3-10 パスワード（確認用）入力
        input("//*[@id=\"confirmPassword\"]", "p@ss");
	}

	/**
	 * ページタイトルの確認
	 * @param page title
	 */
	public void assertPageTitle(String title) {
	  waitTime = Duration.ofSeconds(20);
      wait = new WebDriverWait(driver, waitTime);
      wait.until(ExpectedConditions.titleIs(title));
      assertEquals(driver.getTitle(), title);
	}

	/**
	 * ページタイトルの確認（メッセージバー表示時）
	 * @param page title
	 */
	public void assertPageTitleDisplayedMessageBar(String title) {
		waitForElementTextChange("/html/body/div[2]/div[1]/h2", title);
		ExpectedConditions.titleIs(title);
	}

	/**
	 * メッセージバーの確認
	 * @param message
	 */
	public void assertMessageBar(String message) {
		waitForElementVisible("/html/body/div[1]/div/div/div[3]");
		assertText("/html/body/div[1]/div/div/div[3]/span", message);
	}
}
