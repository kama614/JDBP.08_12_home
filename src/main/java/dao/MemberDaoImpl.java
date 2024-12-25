// 9-1 MemberDao インターフェースを実装
// このクラスは、コンストラクタの引数で DataSource の情報を受け取り、オブジェクト内に保持する。
// そして、必要に応じて DataSource からデータベースへの接続を取得して利用。
// 9-2 新しい会員を追加する機能を実装
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import domain.Member;

public class MemberDaoImpl implements MemberDao {

	private DataSource ds;

	public MemberDaoImpl(DataSource ds) {
		this.ds = ds;
	}

	// データベースやコレクション内の全レコードを取得
	@Override
	public List<Member> findAll() throws Exception {
		List<Member> memberList = new ArrayList<>();

		// tryで囲む（例外が発生した時のために）
		try (Connection con = ds.getConnection()) { // DBとの接続を取得
			String sql = "SELECT" // データベースからデータを取得
					+ " members.id, members.name, members.age,"
					+ " members.address, members.type_id, members.created,"
					+ " member_types.name as type_name"
					+ " FROM members" // membersテーブルからデータを取得
					+ " JOIN member_types" // member_typesテーブルと結合
					+ " ON members.type_id = member_types.id" // membersテーブルのtype_idとmember_typesテーブルのidを結合条件にして
					+ " ORDER BY id ASC"; //結果をid列で昇順に並べ替え

			PreparedStatement stmt = con.prepareStatement(sql);// データベースにSQLを実行する準備
			ResultSet rs = stmt.executeQuery(); // データベースからクエリ結果を取得・SQL文をデータベースに送信し、その実行結果をResultSetオブジェクトとして取得
			while (rs.next()) {
				memberList.add(mapToMember(rs));
				// while：ResultSetに次の行がある間、ループを繰り返します。
				// rs.next()：次の行にカーソルを移動し、その行にデータがあればtrueを返します。
				// memberList.add()：リストに新しいメンバーを追加します。
				// mapToMember(rs)：ResultSetの現在の行をMemberオブジェクトにマッピングするメソッド。
				// このメソッドは通常、各カラムの値を取得してMemberのフィールドに設定するロジックを持っています。

				// tryの括弧内でリソース（ここではConnection）を宣言すると、
				// tryブロックを抜けたときに自動的にリソースがクローズされます。

			}
		} catch (Exception e) {
			throw e;
			/*			
			発生した例外を捕捉し、何もせずにそのまま再スロー
			※例外発生時にはログ出力やメッセージが出るように記述した方が良い
			*/
		}

		return memberList;
		// 最終的に作成されたmemberList（すべてのメンバーのリスト）を呼び出し元に返します。
	}

	// データベースやコレクションから特定のIDに対応するメンバーを取得
	// IDを指定して特定のメンバーを検索し、Memberを返す
	@Override
	public Member findById(Integer id) throws Exception {
		Member member = new Member();

		try (Connection con = ds.getConnection()) {
			String sql = "SELECT"
					+ " members.id, members.name, members.age,"
					+ " members.address, members.type_id, members.created,"
					+ " member_types.name as type_name"
					+ " FROM members"
					+ " JOIN member_types"
					+ " ON members.type_id = member_types.id"
					+ " WHERE members.id = ?"; // [?]プレースホルダー（置き換え用の記号）82行で ? を具体的な値に置き換え

			// PreparedStatementを作成
			PreparedStatement stmt = con.prepareStatement(sql);// データベースにSQLを実行する準備
			// プレースホルダーに値を設定
			stmt.setObject(1, id, Types.INTEGER); // PreparedStatement を作成した後かつクエリを実行する前に記述
			// クエリを実行
			ResultSet rs = stmt.executeQuery();
			if (rs.next() == true) {
				member = mapToMember(rs);
				// 複数の行が結果として返る場合、このコードは最初の1行だけを処理します。
				// 複数行を処理したい場合は while (rs.next()) を使用します。
			}
		} catch (Exception e) {
			throw e;
			/*			
			発生した例外を捕捉し、何もせずにそのまま再スロー
			※例外発生時にはログ出力やメッセージが出るように記述した方が良い
			*/
		}

		return member;
	}

	// 新しい会員を追加する機能を実装
	// 新しいメンバーをデータベースやコレクションに追加
	@Override
	public void insert(Member member) throws Exception {
		try (Connection con = ds.getConnection()) {
			String sql = "INSERT INTO members"
					+ " (name, age, address, type_id, created)"
					+ " VALUES(?,?,?,?,NOW())";
			//　　　↑SQLのmydbのmembersテーブル情報
			//　　　必ずSQLで実行できてからのやつを貼り付ける

			// PreparedStatementを作成
			PreparedStatement stmt = con.prepareStatement(sql);

			// ？の部分、パラメータをセット
			stmt.setString(1, member.getName());
			stmt.setObject(2, member.getAge(), Types.INTEGER);
			stmt.setString(3, member.getAddress());
			stmt.setObject(4, member.getTypeId(), Types.INTEGER);

			// INSERT 文を実行
			stmt.executeUpdate();// 左側の戻り値が無くても実行はされる
			/*
			戻り値を使うべき状況:
			操作の結果（挿入・更新・削除された行数）を確認したい場合には、戻り値を受け取るべきです。*/
		} catch (Exception e) {
			throw e;
			/*			
			発生した例外を捕捉し、何もせずにそのまま再スロー
			※例外発生時にはログ出力やメッセージが出るように記述した方が良い
			*/
		}

	}

	// 既存のメンバーのデータを更新するメソッド
	@Override
	public void update(Member member) throws Exception {
		try (Connection con = ds.getConnection()) {
			String sql = "UPDATE members"
					+ " SET name = ?, age = ?, address = ?, type_id = ?"
					+ " WHERE id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, member.getName());
			stmt.setObject(2, member.getAge(), Types.INTEGER);
			stmt.setString(3, member.getAddress());
			stmt.setObject(4, member.getTypeId(), Types.INTEGER);
			stmt.setObject(5, member.getId(), Types.INTEGER);

			// INSERT 文を実行
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
	}

	// 指定されたメンバーを削除するメソッド
	@Override
	public void delete(Member member) throws Exception {
		try (Connection con = ds.getConnection()) {
			String sql = "DELETE FROM members WHERE id = ?";

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setObject(1, member.getId(), Types.INTEGER);

			// INSERT 文を実行
			stmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		}

	}

	private Member mapToMember(ResultSet rs) throws Exception {
		Integer id = (Integer) rs.getObject("id");
		String name = rs.getString("name");
		Integer age = (Integer) rs.getObject("age");
		String address = rs.getString("address");
		Integer typeId = (Integer) rs.getObject("type_id");
		String typeName = rs.getString("type_name");
		Date created = rs.getTimestamp("created");

		return new Member(id, name, age, address, typeId, typeName, created);
	}

}
