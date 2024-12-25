// 8-2 membersテーブルからデータを取り出してMemberオブジェクトのリストを作成し、表示用のJSPにフォワードするListMemberServletを作成
// データベース接続の枠組みを作成
// 9-1 DAOを利用して会員のリストを取得するための変更(DaoFactory.MemberDao.MemberDaoImplの作成)
// メンテナンスが容易。例えば、データベースの構造が変わった場合はDAOの修正だけで済みます。
package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.MemberDao;
import domain.Member;

@WebServlet("/listMember")
public class ListMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			MemberDao memberDao = DaoFactory.createMemberDao();
			List<Member> memberList = memberDao.findAll();

			request.setAttribute("memberList", memberList);
			request.getRequestDispatcher("/WEB-INF/view/listMember.jsp")
					.forward(request, response);

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

}
