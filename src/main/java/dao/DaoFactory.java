// 9-1 DAOクラスのインスタンスを生成するDaoFactoryクラスを作成
package dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DaoFactory {

	public static MemberDao createMemberDao() {
		return new MemberDaoImpl(getDataSource());
		/*
		MemberDao 型のオブジェクトを生成して返します。
		具体的には、MemberDaoImpl というクラスのインスタンスを作成し、引数に getDataSource() から取得したデータソースを渡しています。*/
	}

	public static AdminDao createAdminDao() {
		return new AdminDaoImpl(getDataSource());
		/*
		AdminDao 型のオブジェクトを生成して返します。
		こちらも、AdminDaoImpl クラスのインスタンスを作成し、同様に getDataSource() を使用しています。*/
	}

	/*
	getDataSource()
	データソース（通常はデータベース接続情報）を取得するメソッドだと思われます。
	このメソッドは、どちらの DAO（データアクセスオブジェクト）も使用しています。*/

	private static DataSource getDataSource() {
		InitialContext ctx = null;
		DataSource ds = null;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");
		} catch (NamingException e) {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e1) { // リソースが見つからない場合や JNDI エラー時に対応
					throw new RuntimeException(e1);
				}
			}
			throw new RuntimeException(e);
		}
		return ds;
	}
	/*
	コードの動き
	
	初期化
	InitialContext ctx を作成して JNDI を利用可能にする。
	
	リソースの検索
	ctx.lookup() を使って DataSource を検索し、取得します。
	
	例外処理
	NamingException（JNDI 関連のエラー）が発生した場合：
	必要に応じて ctx を閉じる。
	新たに RuntimeException をスローし、エラーの原因を外部に伝える。
	
	取得した DataSource を返す
	最後に DataSource を呼び出し元に返します。*/

}
