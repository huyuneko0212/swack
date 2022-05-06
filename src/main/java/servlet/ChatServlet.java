package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.ChatModel;

@WebServlet("/ChatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// パラメータ取得
		String roomId = request.getParameter("roomId");
		String message = request.getParameter("message");
		System.out.println(roomId);
		System.out.println(message);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// 処理
		try {
			// つぶやく
			new ChatModel().saveChatLog(roomId, user.getUserId(), message);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		response.sendRedirect("MainServlet?roomId=" + roomId);

	}

}
