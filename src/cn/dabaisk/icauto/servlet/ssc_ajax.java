package cn.dabaisk.icauto.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * Servlet implementation class ssc_ajaxashx
 */
@WebServlet("/tools/ssc_ajax.ashx")
public class ssc_ajax extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ssc_ajax() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String A = request.getParameter("A");
		FileUtil fu = new FileUtil();
		String path = getServletContext().getRealPath("/WEB-INF");
		String body = fu.readUtf8String(path + "/result.json");
		JSONObject json = JSONUtil.parseObj(body);
		System.out.println(json.get(A));
		JSONObject result = json.getJSONObject(A);
		Object data = result.get("Data");
		if (data != null) {
			result.remove("Data");
			result.put("Data", new Date().getTime() + "");
		}
		System.out.println(A + "---" + result);
		response.getWriter().append(result.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
