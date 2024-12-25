// loginサーブレットからじゃないとエラーになる
// 後ろに＝○○（数字）がつくようにしないと動かない
package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoFactory;
import dao.MemberDao;
import domain.Member;

@WebServlet("/updateMember")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Getパラメーターの取得
		String strId = request.getParameter("id");
		Integer id = Integer.parseInt(strId);

		try {
			// 編集する会員データの取得
			MemberDao memberDao = DaoFactory.createMemberDao();
			Member member = memberDao.findById(id);

			// 編集ページの表示
			request.setAttribute("name", member.getName());
			request.setAttribute("age", member.getAge());
			request.setAttribute("address", member.getAddress());
			request.setAttribute("typeId", member.getTypeId());
			request.getRequestDispatcher("/WEB-INF/view/updateMember.jsp")
					.forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Getパラメーターの取得
		String strId = request.getParameter("id");
		Integer id = Integer.parseInt(strId);

		//バリデーション用のフラグ
		boolean isError = false;

		//各パラメータの取得とバリデーション
		String name = request.getParameter("name");
		request.setAttribute("name", name);//再表示用
		if (name.isBlank()) {
			//エラーメッセージの作成
			request.setAttribute("nameError", "名前が未入力です。");
			isError = true;//入力に不備ありと判定
		} else if (name.length() > 10) {
			request.setAttribute("nameError", "10字以内で入力してください。");
			isError = true;
		}

		// 年齢は文字列として取得。未入力でなければ整数へ変換
		String strAge = request.getParameter("age");
		Integer age = null;
		request.setAttribute("age", strAge);// 再表示用
		if (!strAge.isEmpty()) {
			try {
				age = Integer.parseInt(strAge);
			} catch (NumberFormatException e) {
				// 整数に変換できない場合の処理
				request.setAttribute("ageError", "年齢は整数で入力してください。");
				isError = true;

			}
		}

		//会員種別、住所はバリデーション不要
		Integer typeId = Integer.parseInt(request.getParameter("typeId"));
		String address = request.getParameter("address");
		request.setAttribute("typeId", typeId); // 再表示用
		request.setAttribute("address", address); // 再表示用

		//入力不備がある場合は、フォームを再表示し、処理を中断
		if (isError == true) {
			request.getRequestDispatcher("/WEB-INF/view/updateMember.jsp")
					.forward(request, response);
			return;
		}

		// 入力に不備がなければ、データの更新
		Member member = new Member();
		member.setId(id);
		member.setName(name);
		member.setAge(age);
		member.setTypeId(typeId);
		member.setAddress(address);
		try {
			// データの更新
			MemberDao memberDao = DaoFactory.createMemberDao();
			memberDao.update(member);

			// 更新完了ページの表示
			request.getRequestDispatcher("/WEB-INF/view/updateMemberdone.jsp")
					.forward(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

}
