package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MenuService;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

import factory.ServiceFactory;

public class MenuController extends HttpServlet {
	private MenuService menuService = ServiceFactory.newInstance(MenuService.class);

	protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		httpServletRequest.setCharacterEncoding("utf-8");
		httpServletResponse.setCharacterEncoding("utf-8");
		String action = httpServletRequest.getParameter("action");
		if ("get".equals(action)) {
			BasicDBObject menuDBObject = this.menuService.get();
			httpServletResponse.getWriter().print(menuDBObject.toString());
		} else if ("save".equals(action)) {
			String nodeString = httpServletRequest.getParameter("nodeString");
			BasicDBObject nodeDBObject = (BasicDBObject) JSON.parse(nodeString);
			BasicDBObject menuDBObject = this.menuService.save(nodeDBObject);
			httpServletResponse.getWriter().print(menuDBObject.toString());
		}
	}
}
