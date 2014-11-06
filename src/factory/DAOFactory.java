package factory;

import java.util.HashMap;
import java.util.Map;

import util.LoggerUtil;

public class DAOFactory {
	public static boolean singleton = true;
	public static Map<String, Object> heap = new HashMap<String, Object>();

	public static <T> T newInstance(Class<T> clazz) {
		T t = null;
		if (singleton) {
			if (heap.containsKey(clazz.getName())) {
				t = (T) heap.get(clazz.getName());
			} else {
				try {
					t = clazz.newInstance();
					heap.put(clazz.getName(), t);
				} catch (Exception e) {
					LoggerUtil.severe(e);
				}
			}
		} else {
			try {
				t = clazz.newInstance();
			} catch (Exception e) {
				LoggerUtil.severe(e);
			}
		}
		return t;
	}
}
