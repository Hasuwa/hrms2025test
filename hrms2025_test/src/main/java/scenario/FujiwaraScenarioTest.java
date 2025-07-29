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
	 */
	@Test(groups = "scenario")
	public void scenarioTest001() {
		//1.ログイン
		//1-1 メールアドレス入力
		
		//1-2 パスワード入力
		
		//1-3 ログインボタン押下
		
		//トップページに遷移したことを確認
		
		//2.必須項目情報のみで社員登録
		//2-1 従業員情報ボタン押下
		
		// 従業員情報一覧画面に遷移したことを確認
		
		//2-2　新規登録ボタン押下
		
		// 従業員情報登録画面に遷移したことを確認
		
		//2-3-1 氏名入力
		
		//2-3-2 メールアドレス入力
		
		//2-3-3 ランク選択
		
		//2-3-4 パスワード入力
		
		//2-3-5 パスワード（確認用）入力
				
		//2-3-6 登録ボタン押下
		
		// 従業員詳細画面が表示されることを確認
		
		//　MSG-I-EMP-001が表示されることを確認
		
		// 入力された内容が表示されることを確認

		//3.登録した社員を削除
		//3-1　削除ボタン押下
		
		// 削除確認ダイアログが表示されることを確認
		
		// 確認メッセージが正しいことを確認
		
		//3-2 削除ボタン押下
		
		// 従業員情報一覧画面が表示されることを確認
		
		//　MSG-I-EMP-003が表示されることを確認		

		//4.ログアウト
		//4-1　グローバルナビからログアウトを選択
		//5.ログアウト
			
		// ログイン画面に遷移することを確認
	}
}