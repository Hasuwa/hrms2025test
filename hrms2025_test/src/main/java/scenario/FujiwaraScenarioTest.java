package scenario;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utility.CommonUtil;

public class FujiwaraScenarioTest extends TestBase {

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
	 * @throws InterruptedException
	 */
	@Test(groups = "scenario")
	public void scenarioTest001() throws InterruptedException {
		//1.ログイン
		//1-1 メールアドレス入力
		//1-2 パスワード入力
	    login("admin@jpd.co.jp", "test");
		//1-3 ログインボタン押下

		//トップページに遷移したことを確認
	    assertPageTitle("ホーム");

		//2.必須項目情報のみで社員登録
		//2-1 従業員情報ボタン押下
	    click("/html/body/div[2]/div[1]/div/div/form[3]/button");
		// 従業員情報一覧画面に遷移したことを確認
	    assertPageTitle("従業員情報一覧");
		//2-2　新規登録ボタン押下
	    click("/html/body/div[2]/div[3]/form/input");
		// 従業員情報登録画面に遷移したことを確認
	    assertPageTitle("従業員フォーム");
	    //2-3-1 社員番号入力
	    final String employeeNo = "1111";
		//2-3-3 メールアドレス入力
		final String mailAddress = "nnihonn.purot@jpd.co.jp";
		inputEmployeeInfo(employeeNo, mailAddress);
		//2-3-6 登録ボタン押下
		click("/html/body/div[2]/form/div[1]/input");
		// 従業員詳細画面が表示されることを確認
		assertPageTitleDisplayedMessageBar("従業員情報詳細");
		//　MSG-I-EMP-001が表示されることを確認
		assertMessageBar("従業員情報の登録が完了しました。");
		// 入力された内容が表示されることを確認

		//3.登録した社員を削除
		//3-1　削除ボタン押下
		click("/html/body/div[2]/div[1]/div/button");
		// 削除確認ダイアログが表示されることを確認
		assertText("//*[@id=\"deleteModal\"]/div/div/div[1]/h5", "削除確認");
		// 確認メッセージが正しいことを確認
		assertText("//*[@id=\"deleteModal\"]/div/div/div[2]/p[2]/strong", "1111：日本プロ太");
		//3-2 削除ボタン押下
		click("//*[@id=\"deleteModal\"]/div/div/div[3]/form/button[1]");
		// 従業員情報一覧画面が表示されることを確認
		assertPageTitle("従業員情報一覧");
		//　MSG-I-EMP-003が表示されることを確認
		assertMessageBar("従業員情報の削除が完了しました。");
		//4.ログアウト
		//4-1　グローバルナビからログアウトを選択
		click("/html/body/div[1]/div/div/div[2]/div/button");
		//5.ログアウト
		click("//*[@id=\"menu\"]/ul/form[2]/button");
		// ログイン画面に遷移することを確認
		assertPageTitle("ログイン");
	}
}