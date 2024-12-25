// サーブレットフィルターとして動作し、
// リクエストやレスポンスの文字エンコーディングをUTF-8に設定する役割を持つ
package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class CharsetFiltere
 */
@WebFilter("/*") // すべてのURLパターン（/*）に対してこのフィルターを適用することを示しています。
public class CharsetFiltere implements Filter { 
	// Filterインターフェースを実装することで、フィルター機能を定義。

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public CharsetFiltere() {
		super();
		// デフォルトコンストラクタです。HttpFilterクラスを継承しているのでsuper()が呼び出されています。
		// 特に初期化処理がない場合は空のままで問題ありません。
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// フィルターの終了処理を行うメソッドです。
		// アプリケーションが終了するとき、またはフィルターが破棄される際に呼び出されます。
		// 今回は何も処理を書いていません。
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		// リクエストの文字エンコーディングをUTF-8に設定します。
		// フォームのデータやリクエストパラメータの文字化けを防ぎます。
		
		response.setCharacterEncoding("UTF-8");
		// レスポンスの文字エンコーディングもUTF-8に設定します。
		// サーバーからクライアントに返すレスポンスの文字化けを防ぎます。
		
		chain.doFilter(request, response);
		// 次のフィルターまたは最終サーブレットへ処理を渡します。
		// フィルターの中核部分です。ここを呼び出さないとリクエストがサーブレットに届きません。
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// フィルターの初期化処理を行うメソッドです。
		// フィルターが初めてロードされる際に呼び出されます。
		// 現在は空の実装ですが、設定情報を取得する場合はここに記述します。
	}

}
