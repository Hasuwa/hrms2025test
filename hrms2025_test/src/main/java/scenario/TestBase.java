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
//		final String LOGIN_BUTTON_XPATH = "//button[text()='ログイン']";
		// input mailaddress
		input(MAILADDRESS_INPUT_XPATH, mailAddress);
		// input password
	      
		input(PASSWORD_INPUT_XPATH, password);

		// click login button
	      click("//input[@type='submit' and @value='ログイン']");
	      
	      wait.until(ExpectedConditions.titleIs("ホーム"));

//		click(LOGIN_BUTTON_XPATH);
	}
	
	public void setDate(String elementId, String dateValue) {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("document.getElementById('" + elementId + "').value='" + dateValue + "';");
    }

	/**
	 * 社員情報を入力
	 * @param employeeNum
	 * @param mailaddress
	 */
	
	/**
	 * 社員情報を入力（必須項目＋入社日・生年月日・ランク・所属・パスワード含む）
	 * @param employeeNum 社員番号
	 * @param name 氏名
	 * @param mailaddress メールアドレス
	 * @param hireDate 入社日（例："2025-04-01"）
	 * @param birthDate 生年月日（例："2002-05-02"）
	 */
	public void inputEmployeeInfo(String employeeNum, String name, String mailaddress, String hireDate, String birthDate, String rank, String organizationName) {
	    // 社員番号、氏名、メールアドレスの入力
	    input("//*[@id=\"employeeNo\"]", employeeNum);
	    input("//*[@id=\"fullName\"]", name);
	    input("//*[@id=\"mailAddress\"]", mailaddress);

	    // 性別選択
	    click("//*[@id=\"gender1\"]");

	    // 入社日
	    click("//*[@id=\"hireDate\"]");
	    setDate("hireDate", hireDate);

	    // 生年月日
	    click("//*[@id=\"birthDate\"]");
	    setDate("birthDate", birthDate);


	 // ランク選択（rankに応じたli要素を選択）
	    click("//*[@id=\"employeeRank\"]");
	    click("//*[@id=\"employeeRank\"]/div[1]/option");

	          // 所属選択
	    WebElement button =
	        driver.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[9]/td/button"));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
	 
	    // 所属dialog
	   
	    wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[3]/button")));
	    click("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[1]/span");

	    // パスワード入力
	    input("//*[@id=\"password\"]", "P@ssw0rd");

	    // パスワード（確認用）入力
	    input("//*[@id=\"confirmPassword\"]", "P@ssw0rd");
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
