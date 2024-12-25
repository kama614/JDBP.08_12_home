// 8-1 接続プール版	
/*準備として Tomcat の設定ファイルに、データソース(データベース接続のための情報)の設定を記載します。
 * Servers > Tomcat9_Java17-config 内にある server.xml と context.xml を開き、設定を追記*/
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ConnectionTestServlet2
 */
@WebServlet("/test2")
public class ConnectionTestServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter writer = response.getWriter();

		Connection con = null;
		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");
			con = ds.getConnection();
			writer.println("接続しました");
		} catch (Exception e) {
			e.printStackTrace();
			writer.println("接続に失敗しました");
		} finally {
			try {
				if (con != null) {
					con.close();
					writer.println("切断しました");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				writer.println("切断に失敗しました");
			}
		}

	}

}
