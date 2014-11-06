package test;

import org.junit.Test;

import util.LoggerUtil;

public class LoggerUtilTest {
	@Test
	public void logger() {
		LoggerUtil.severe("severe...");
		LoggerUtil.warning("warning...");
		LoggerUtil.info("info...");
		LoggerUtil.config("config...");
		LoggerUtil.fine("fine...");
		LoggerUtil.finer("finer...");
		LoggerUtil.finest("finest...");
	}
}
