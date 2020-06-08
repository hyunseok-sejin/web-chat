package Chat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chatListServlet")
public class chatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String listType = request.getParameter("listType");
		if (listType == null || listType.equals("")) {
			response.getWriter().write("");
		} else if (listType.equals("today")) {
			response.getWriter().write(getToday());
		}
	}

	public String getToday() {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatlist = chatDAO.getChatList(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

		for (int i = 0; i < chatlist.size(); i++) {
			result.append("[{\"value\": \"" + chatlist.get(i).getChatName() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatcontent() + "\"},");
			result.append("{\"value\": \"" + chatlist.get(i).getChatTime() + "\"}]");
			if (i != chatlist.size() - 1) {
				result.append(",");
			}
		}result.append("]};");
		System.out.println(result);
		return result.toString();
	}

}
