// 9-1
// members テーブルに対応した DAO を作るために、MemberDao インターフェースを作成
// 次にMemberDaoインターフェースを実装したMemberDaoImplクラスをdaoパッケージ内に作成する。
package dao;

import java.util.List;

import domain.Member;

public interface MemberDao {
	
	// 「すべての要素を見つける」という意味合いの名前
	// データベースやコレクション内の全レコードを取得するメソッド
	List<Member>findAll() throws Exception;
	
	
	// 指定されたID (id) をもとに、Memberオブジェクトを検索するメソッド
	// データベースやコレクションから特定のIDに対応するメンバーを取得するときに使用
	Member findById(Integer id)throws Exception;
	
	
	// 渡されたMemberオブジェクトをデータベースやコレクションに新しく追加するメソッド
	// 新しいメンバーのデータを保存するときに使用
	void insert (Member member)throws Exception;
	
	
	// 渡されたMemberオブジェクトのデータを更新するメソッド
	// データベースやコレクション内の既存のデータを変更するときに使います
	// 例えば、メンバー情報（名前やメールアドレス）を編集するときに使用します
	void update(Member member)throws Exception;
	
	
	// 渡されたMemberオブジェクトを削除するメソッド
	// データベースやコレクションから、該当するメンバーを削除します
	// 特定のメンバーを削除（退会、削除処理など）するときに使用します
	void delete (Member member)throws Exception;

}

/*
throws Exception
このメソッドを呼び出したときに、Exception型の例外がスローされる可能性があることを示しています。
例外は、エラーや予期しない状況を通知する仕組みです。
この記述により、メソッドを呼び出す側は例外をキャッチする必要があるかもしれません。

throws：
「スローする」という意味です。
これは、メソッド内でエラーや異常が発生した場合に、
そのエラーを呼び出し元に通知することを示します。

Exception：
Javaで「例外」と呼ばれるエラーの一種を表します。
Exceptionは、エラーや異常な状況を表すクラスで、
多くの場合、プログラムの正常な実行を中断する可能性があります。
*/