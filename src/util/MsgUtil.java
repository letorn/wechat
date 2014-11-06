package util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class MsgUtil {
	/**
	 * xml字符串转BasicDBObject对象
	 * @param msgString
	 * @return
	 */
	public static BasicDBObject decode(String msgString) {
		Document document = null;
		try {
			document = DocumentHelper.parseText(msgString);
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
		return document != null ? decode(document.getRootElement()) : null;
	}

	private static BasicDBObject decode(Element element) {
		BasicDBObject dbObject = new BasicDBObject();
		if (element.isTextOnly()) {
			dbObject.put(StringUtil.toLowerCaseFirstOne(element.getName()), element.getText());
		} else {
			List<Element> eles = element.elements();
			for (Element ele : eles) {
				if (ele.isTextOnly()) {
					dbObject.put(StringUtil.toLowerCaseFirstOne(ele.getName()), ele.getText());
				} else {
					dbObject.put(StringUtil.toLowerCaseFirstOne(ele.getName()), decode(ele));
				}
			}
		}
		return dbObject;
	}

	/**
	 * BasicDBObject对象转字符串
	 * @param dbObject
	 * @return
	 */
	public static String encode(BasicDBObject dbObject) {
		return encode("xml", dbObject).asXML();
	}

	private static Element encode(String name, BasicDBObject dbObject) {
		Element element = DocumentHelper.createElement(name);
		for (String key : dbObject.keySet()) {
			if ("msgId".equals(key) || "createTime".equals(key)) {
				element.addElement(StringUtil.toUpperCaseFirstOne(key)).setText(dbObject.getString(key));
			} else {
				Object value = dbObject.get(key);
				if (value instanceof BasicDBObject) {
					element.add(encode(StringUtil.toUpperCaseFirstOne(key), (BasicDBObject) value));
				} else {
					element.addElement(StringUtil.toUpperCaseFirstOne(key)).addCDATA(value.toString());
				}
			}
		}
		return element;
	}

	/**
	 * 跨域请求
	 * @param url
	 * @return
	 */
	public static BasicDBObject request(String url) {
		BasicDBObject dbObject = null;
		try {
			LoggerUtil.info("url(method=get): " + url);
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
			dbObject = (BasicDBObject) JSON.parse(IOUtil.toString(urlConnection.getInputStream()));
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
		LoggerUtil.info("response: " + dbObject.toString());
		return dbObject;
	}

	public static BasicDBObject request(String url, String method) {
		BasicDBObject dbObject = null;
		try {
			LoggerUtil.info("url(method=" + method + "): " + url);
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
			urlConnection.setRequestMethod(method);
			dbObject = (BasicDBObject) JSON.parse(IOUtil.toString(urlConnection.getInputStream()));
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
		LoggerUtil.info("response: " + dbObject.toString());
		return dbObject;
	}

	public static BasicDBObject request(String url, String method, String content) {
		BasicDBObject dbObject = null;
		try {
			LoggerUtil.info("url(method=" + method + "): " + url);
			LoggerUtil.info("request: " + content);
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
			urlConnection.setRequestMethod(method);
			urlConnection.setDoOutput(true);
			urlConnection.getOutputStream().write(content.getBytes("utf-8"));
			dbObject = (BasicDBObject) JSON.parse(IOUtil.toString(urlConnection.getInputStream()));
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
		LoggerUtil.info("response: " + dbObject.toString());
		return dbObject;
	}

	public static BasicDBObject request(String url, String method, BasicDBObject properties) {
		BasicDBObject dbObject = null;
		try {
			LoggerUtil.info("url(method=" + method + "): " + url);
			LoggerUtil.info("request: " + properties.toString());
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
			urlConnection.setRequestMethod(method);
			for (String key : properties.keySet()) {
				urlConnection.addRequestProperty(key, properties.getString(key));
			}
			dbObject = (BasicDBObject) JSON.parse(IOUtil.toString(urlConnection.getInputStream()));
		} catch (Exception e) {
			LoggerUtil.severe(e);
		}
		LoggerUtil.info("response: " + dbObject.toString());
		return dbObject;
	}

	public static BasicDBObject menuToNode(BasicDBObject dbObject) {
		BasicDBList childrenDBList = (BasicDBList) dbObject.removeField("button");
		dbObject.put("children", childrenDBList);
		for (Object object : childrenDBList) {
			BasicDBObject childrenDBObject = (BasicDBObject) object;
			BasicDBList subChildrenDBList = (BasicDBList) childrenDBObject.removeField("sub_button");
			if (subChildrenDBList == null || subChildrenDBList.size() <= 0) { //二级菜单
				childrenDBObject.put("value", "click".equals(childrenDBObject.getString("type")) ? childrenDBObject.removeField("key") : childrenDBObject.removeField("url"));
				childrenDBObject.put("leaf", true);
			} else {
				childrenDBObject.put("leaf", false);
				childrenDBObject.put("children", subChildrenDBList);
				for (Object subObject : subChildrenDBList) {
					BasicDBObject subChildrenDBObject = (BasicDBObject) subObject;
					subChildrenDBObject.put("value", "click".equals(subChildrenDBObject.getString("type")) ? subChildrenDBObject.removeField("key") : subChildrenDBObject.removeField("url"));
					subChildrenDBObject.put("leaf", true);
				}
			}
		}
		return dbObject;
	}

	public static BasicDBObject nodeToMenu(BasicDBObject dbObject) {
		BasicDBList buttonDBList = (BasicDBList) dbObject.removeField("children");
		dbObject.put("button", buttonDBList);
		for (Object object : buttonDBList) {
			BasicDBObject buttonDBObject = (BasicDBObject) object;
			if (buttonDBObject.containsField("children")) {
				BasicDBList subButtonDBList = (BasicDBList) buttonDBObject.removeField("children");
				buttonDBObject.put("sub_button", subButtonDBList);
				for (Object subObject : subButtonDBList) {
					BasicDBObject subButtonDBObject = (BasicDBObject) subObject;
					subButtonDBObject.put("click".equals(subButtonDBObject.getString("type")) ? "key" : "url", subButtonDBObject.removeField("value"));
					subButtonDBObject.removeField("leaf");
				}
			} else {
				buttonDBObject.put("click".equals(buttonDBObject.getString("type")) ? "key" : "view", buttonDBObject.removeField("value"));
			}
			buttonDBObject.removeField("leaf");
		}
		return dbObject;
	}
}
