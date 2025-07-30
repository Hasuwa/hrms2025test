package scenario;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utility.CommonUtil;

public class HirayamaScenarioTest extends TestBase {

	/**
	 * Chromeでアプリを開く
	 */
	@BeforeMethod(alwaysRun = true)
	public void before() {
		driver = new ChromeDriver();
		driver.get(SHINABLE_CLOUD_PATH);
		driver.manage().window().maximize();
	}

	/**
	 * 各テスト後の処理
	 * テストがfailした場合、スクショをとる
	 * @param result
	 */
	@AfterMethod(alwaysRun = true)
	public void after(ITestResult result) {
		//failしたらスクショ
		if (result.getStatus() != ITestResult.SUCCESS) {
			CommonUtil.takeScreenShot(driver, ".\\result", result.getMethod().getMethodName());
		}
		//ブラウザを閉じる
		driver.quit();
	}
	
	/**
	 * 1.ログイン
	 * 2.必須項目情報のみで社員登録
	 * 3.登録した社員を削除
	 * 4.ログアウト
	 */
	@Test(groups = "scenario")
	public void scenarioTest001() {
		//1.ログイン
		//1-1 メールアドレス入力
	    waitForElementVisible("//*[@id=\"usernameInput\"]");
        driver.findElement(By.xpath("//*[@id=\"usernameInput\"]")).sendKeys("admin@jpd.co.jp");
		//1-2 パスワード入力
		waitForElementVisible("//*[@id=\"passwordInput\"]");
		driver.findElement(By.xpath("//*[@id=\"passwordInput\"]")).sendKeys("test");
		//1-3 ログインボタン押下
		waitForElementVisible("/html/body/div[2]/div/form/div[3]/input");
		driver.findElement(By.xpath("/html/body/div[2]/div/form/div[3]/input")).click();
		//トップページに遷移したことを確認
//	    waitTime = Duration.ofSeconds(20);
//        wait = new WebDriverWait(driver, waitTime);
//        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath
//            ("/html/body/div[2]/div[1]/div/div/form[1]/button/text()")), 
//            "            マイプロフィール"
//            + "              "));
//	    ExpectedConditions.titleIs(
//	        "            マイプロフィール"
//	        + "              ")
		waitForElementVisible("/html/body/div[2]/div[1]/div/div/form[1]/button");
		//2-1 従業員情報ボタン押下
		waitForElementVisible("/html/body/div[2]/div[1]/div/div/form[3]/button");
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/form[3]/button")).click();
		// 従業員情報一覧画面に遷移したことを確認
		waitForElementTextChange("/html/body/div[2]/div[1]/h2", "従業員情報一覧");
        ExpectedConditions.titleIs("従業員情報一覧");
		//2-2　新規登録ボタン押下
		click("/html/body/div[2]/div[3]/form/input");
		// 従業員情報登録画面に遷移したことを確認
		waitForElementTextChange("/html/body/div[2]/h2", "新規従業員登録");
        ExpectedConditions.titleIs("新規従業員登録");
		//社員番号を入力
        input("//*[@id=\"employeeNo\"]","1111");
		//2-3-1 氏名入力		
		input("//*[@id=\"fullName\"]", "テストマン");
		//2-3-2 メールアドレス入力
		input("//*[@id=\"mailAddress\"]","test@jpd.co.jp");
		//性別の入力
		click("//*[@id=\"gender1\"]");
		//入社日入力
		waitForElementVisible("//*[@id=\"hireDate\"]");
		driver.findElement(By.xpath("//*[@id=\"hireDate\"]")).sendKeys("002020-04-01");
		//退職日入力
		waitForElementVisible("//*[@id=\"quitDate\"]");
        driver.findElement(By.xpath("//*[@id=\"quitDate\"]")).sendKeys("002020-04-01");
		//生年月日入力
		waitForElementVisible("//*[@id=\"birthDate\"]");
		driver.findElement(By.xpath("//*[@id=\"birthDate\"]")).sendKeys("002000-04-01");
		//2-3-3 ランク選択

		// ランクのセレクトボックスを取得
		WebElement rankSelectElement = driver.findElement(By.name("employeeRank")); // または By.xpath("//select[@name='employeeRank']")

		// Selectクラスを使って選択
		Select rankSelect = new Select(rankSelectElement);
		rankSelect.selectByVisibleText("AS0"); 
		
		//所属選択
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 所属選択ボタンをクリック
        WebElement selectButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-select")));
        
//        System.out.println("表示されているか: " + selectButton.isDisplayed());
//        System.out.println("有効か: " + selectButton.isEnabled());
//        System.out.println("タグ名: " + selectButton.getTagName());
//        System.out.println("クラス属性: " + selectButton.getAttribute("class"));
//        System.out.println("data-bs-toggle: " + selectButton.getAttribute("data-bs-toggle"));
//        System.out.println("data-bs-target: " + selectButton.getAttribute("data-bs-target"));
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", selectButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("organizationModal")));

        WebElement orgSpan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='本社']")));
        js.executeScript("arguments[0].click();", orgSpan);
        

        WebElement selectedName = driver.findElement(By.id("selectedOrganizationName"));
        String value = selectedName.getAttribute("value");
        Assert.assertEquals(value, "本社");

        
		input("//*[@id=\"password\"]", "P@ssw0rd");
		//2-3-5 パスワード（確認用）入力
        input("//*[@id=\"confirmPassword\"]","P@ssw0rd");
		//2-3-6 登録ボタン押下
        click("/html/body/div[2]/form/div[1]/input");
		// 従業員詳細画面が表示されることを確認
        waitForElementTextChange("/html/body/div[2]/div[1]/h2", "従業員情報詳細");
        ExpectedConditions.titleIs("従業員情報詳細");
		//　MSG-I-EMP-001が表示されることを確認
        waitForElementVisible("/html/body/div[1]/div/div/div[3]/span");
        assertText("/html/body/div[1]/div/div/div[3]/span","従業員情報の登録が完了しました。");
        click("/html/body/div[1]/div/div/div[3]/button");
		// 入力された内容が表示されることを確認
        waitForElementVisible("//html/body/div[2]/div[3]/table/tbody/tr[2]/td");
        assertText("/html/body/div[2]/div[3]/table/tbody/tr[2]/td","テストマン");
		//3.登録した社員を削除
		//3-1　削除ボタン押下
		click("/html/body/div[2]/div[1]/div/button");
		// 削除確認ダイアログが表示されることを確認
		waitForElementTextChange("//*[@id=\"deleteModal\"]/div/div/div[1]/h5", "削除確認");
        ExpectedConditions.titleIs("削除確認");		
		// 確認メッセージが正しいことを確認
        waitForElementVisible("//*[@id=\"deleteModal\"]/div/div/div[2]/p[1]");
        assertText("//*[@id=\"deleteModal\"]/div/div/div[2]/p[1]","以下の従業員情報を削除してもよろしいでしょうか。");
		//3-2 削除ボタン押下
	    click("//*[@id=\"deleteModal\"]/div/div/div[3]/form/button[1]");
		// 従業員情報一覧画面が表示されることを確認
//	    waitForElementTextChange("/html/body/div[2]/div[1]/h2", "従業員情報一覧");
//        ExpectedConditions.titleIs("従業員情報一覧");
	    waitForElementVisible("/html/body/div[2]/div[1]/h2");
        assertText("/html/body/div[2]/div[1]/h2","従業員情報一覧");
		//　MSG-I-EMP-003が表示されることを確認	
	    waitForElementVisible("/html/body/div[1]/div/div/div[3]/span");
        assertText("/html/body/div[1]/div/div/div[3]/span","従業員情報の削除が完了しました。");
	    
	    click("/html/body/div[1]/div/div/div[3]/button");

		//4.ログアウト
		//4-1　グローバルナビからログアウトを選択
	    click("/html/body/div[1]/div/div/div[2]/div/button");
		//5.ログアウト
		click("//*[@id=\"menu\"]/ul/form[2]/button");
		// ログイン画面に遷移することを確認
		waitForElementVisible("/html/body/div[2]/div/div/p");
        assertText("/html/body/div[2]/div/div/p","ログアウトしました");
        
	}
}