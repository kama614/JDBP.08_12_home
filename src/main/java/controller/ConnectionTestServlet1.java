// 8-1 DriverManager 版 
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ConnectionTestServlet
 */
@WebServlet("/test1")
public class ConnectionTestServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		PrintWriter writer = response.getWriter();

		String url = "jdbc:mysql://127.0.0.1:3306/mydb"
				+ " ?useUnicode=true"
				+ " &characterEncoding=utf-8"
				+ " &serverTimezone=Asia/Tokyo";
		String user = "root";
		String password = "";

		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
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
