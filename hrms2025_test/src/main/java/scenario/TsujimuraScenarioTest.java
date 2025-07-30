package scenario;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utility.CommonUtil;

public class TsujimuraScenarioTest extends TestBase {

	/**
	 * Chromeでアプリを開く
	 */
	@BeforeMethod(alwaysRun = true)
	public void before() {
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
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
	    //1-2 パスワード入力
      login("admin@jpd.co.jp", "test");		
		//1-3 ログインボタン押下
		//トップページに遷移したことを確認
      wait.until(ExpectedConditions.titleIs("ホーム"));
      assertPageTitle("ホーム");
		//2.必須項目情報のみで社員登録
		//2-1 従業員情報ボタン押下
      click("/html/body/div[2]/div[1]/div/div/form[3]/button/span");
		// 従業員情報一覧画面に遷移したことを確認
      assertPageTitle("従業員情報一覧");
		//2-2　新規登録ボタン押下
      click("/html/body/div[2]/div[3]/form/input");
		// 従業員情報登録画面に遷移したことを確認
      
      //必須項目入力
      final String employeeNum = "0001";
		//2-3-1 氏名入力
      final String name = "日本プロ太";
		//2-3-2 メールアドレス入力
      final String mailaddress = "Purota.NIHON@jpd.co.jp";
      final String hireDate = "2025-04-01";
      final String quitDate = "2026-07-29";
      final String birthDate = "2002-05-02";
      final String rank = "AS0";
      final String organizationName = "日立事業所";

      inputEmployeeInfo(employeeNum, name, mailaddress, hireDate, quitDate, birthDate, rank, organizationName);

		//2-3-6 登録ボタン押下
      click("/html/body/div[2]/form/div[1]/input");

		//3.登録した社員を削除
		//3-1　削除ボタン押下
      click("/html/body/div[2]/div[1]/div/button");
		// 削除確認ダイアログが表示されることを確認
		//3-2 削除ボタン押下
      click("//*[@id=\"deleteModal\"]/div/div/div[3]/form/button[1]");
		//4.ログアウト
		//4-1　グローバルナビからログアウトを選択
		//5.ログアウト
      click("/html/body/div[1]/div/div/div[2]/div/button");
      click("//*[@id=\"menu\"]/ul/form[2]/button");
		// ログイン画面に遷移することを確認
      assertPageTitle("ログイン");

	}
}