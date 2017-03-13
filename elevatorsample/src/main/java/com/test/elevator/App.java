package com.test.elevator;

import org.apache.commons.lang.StringUtils;

public class App {

	public static void main(String[] args) {

		String mode = args[1];
		String fileName = args[0];

		if (StringUtils.equalsIgnoreCase(mode, "A")) {
			new AppModeA(mode, fileName);
		}
		else if (StringUtils.equalsIgnoreCase(mode, "B")) {
			new AppModeB(mode, fileName);
		}
		else{
			System.err.println("Invalid MODE specified.");
		}
	}

}
