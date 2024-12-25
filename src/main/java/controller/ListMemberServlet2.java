// 8-3
// サーブレット自身が直接データベースアクセスを行います。
// JNDIを使ってデータソース（java:comp/env/jdbc/mydb）を取得し、JDBCコードでSQLを実行してデータを取得します。
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import domain.Member;

@WebServlet("/listMember2")
public class ListMemberServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");
			con = ds.getConnection();
			System.out.println("接続成功");

			String sql = "SELECT"
					+ " members.id,members.name,members.age,"
					+ " members.address,members.created,"
					+ " member_types.name AS type_name"
					+ " FROM members"
					+ " JOIN member_types"
					+ " ON members.type_id=member_types.id";

			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			List<Member> memberList = new ArrayList<>();
			while (rs.next()) {
				Integer id = (Integer) rs.getObject("id");
				String name = rs.getString("name");
				Integer age = (Integer) rs.getObject("age");
				String address = rs.getString("address");
				Integer typeId = (Integer) rs.getObject("type_id");
				String typeName = rs.getString("type_name");
				Date created = rs.getTimestamp("created");

				Member member = new Member(id, name, age, address, typeId, typeName, created);

				memberList.add(member);
			}

			request.setAttribute("memberList", memberList);
			request.getRequestDispatcher("/WEB-INF/view/listMember.jsp")
					.forward(request, response);

		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}

	}

}
/*
ListMemberServletとの比較
Daoを使用しないデメリット

1.責務の分離が不十分
サーブレットがデータベース操作まで担当し、再利用性が低い。

2.可読性・保守性が低下
サーブレット内にSQLやJDBCコードが埋め込まれており、変更や修正が難しい。

3.エラーハンドリングが複雑
リソース管理（ConnectionやResultSet）でコードが冗長になる。

4.テストが難しい
データベース依存のため、モック化や単体テストが困難。

5.スケーラビリティに問題
コードの重複が増え、大規模プロジェクトでは管理が難しくなる。
*/