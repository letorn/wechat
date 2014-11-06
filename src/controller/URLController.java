package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MsgService;
import util.IOUtil;
import util.MsgUtil;
import util.SignatureUtil;

import com.mongodb.BasicDBObject;

import factory.ServiceFactory;

public class URLController extends HttpServlet {
	private MsgService msgService = ServiceFactory.newInstance(MsgService.class);

	/**
	 * 验证URL有效性
	 */
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		httpServletRequest.setCharacterEncoding("utf-8");
		httpServletResponse.setCharacterEncoding("utf-8");
		String signature = httpServletRequest.getParameter("signature");
		String timestamp = httpServletRequest.getParameter("timestamp");
		String nonce = httpServletRequest.getParameter("nonce");
		String echostr = httpServletRequest.getParameter("echostr");
		if (SignatureUtil.check(signature, timestamp, nonce)) {
			httpServletResponse.getWriter().print(echostr);
		}
	}

	/**
	 * 请求处理
	 */
	protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		httpServletRequest.setCharacterEncoding("utf-8");
		httpServletResponse.setCharacterEncoding("utf-8");
		BasicDBObject requestDBObject = MsgUtil.decode(IOUtil.toString(httpServletRequest.getReader()));
		if (this.msgService.check(requestDBObject)) {
			BasicDBObject responseDBObject = null;
			Object msgType = requestDBObject.get("msgType");
			if ("text".equals(msgType)) {
				responseDBObject = this.msgService.textMsgService(requestDBObject);
			} else if ("image".equals(msgType)) {
				responseDBObject = this.msgService.imageMsgService(requestDBObject);
			} else if ("voice".equals(msgType)) {
				responseDBObject = this.msgService.voiceMsgService(requestDBObject);
			} else if ("video".equals(msgType)) {
				responseDBObject = this.msgService.videoMsgService(requestDBObject);
			} else if ("location".equals(msgType)) {
				responseDBObject = this.msgService.locationMsgService(requestDBObject);
			} else if ("link".equals(msgType)) {
				responseDBObject = this.msgService.linkMsgService(requestDBObject);
			}
			httpServletResponse.getWriter().print(MsgUtil.encode(responseDBObject));
		} else {
			httpServletResponse.getWriter().print("");
		}
	}
}
