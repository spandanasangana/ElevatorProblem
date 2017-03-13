package com.test.elevator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class Helper {
	private static String fileName;
	protected static List<String> inputList;

	public Helper() { }

	protected static void sanityChecks(String[] args) {
		fileName = args[0];
		String mode = args[1];
		boolean isValidMode = validateMode(mode);

		if(!isValidMode){ warn("Invalid mode specified. The application will exit."); }

		log("Mode of operation " + mode);
		isFileAvailable();
		inputList= readAllLinesFromFile(fileName);

		if(inputList == null) { warn("Unable to read the file contents"); }

	}
	private static void isFileAvailable() {
		File f = new File(fileName);
		if(!f.exists()){
			warn("Input file is not availble at given location " + f.getAbsolutePath());
			warn("Application now will exit.");
		}
	}
	
	protected static int readSource(String[] trips) {
		String src = StringUtils.trim(trips[0]);
		if(getIndex(src) != -1){
			String temp = StringUtils.substring(src, getIndex(src) + 1, src.length());
			return Integer.parseInt(temp);
		}
		return Integer.parseInt(src);
	}

	protected static int readDest(String[] trips) {
		return Integer.parseInt(trim(trips[1]));
	}

	protected static String trim(String str) {
		return StringUtils.trim(str);
	}


	protected static int getIndex(String src) {
		return StringUtils.indexOf(src, ":");
	}

	public static boolean validateMode(String mode) {
		return StringUtils.equalsIgnoreCase(mode, "A") || StringUtils.equalsIgnoreCase(mode, "B");
	}
	protected static void warn(String message) {
		System.err.println(message);
		System.exit(0);
	}
	@SuppressWarnings("unchecked")
	protected static List<String> readAllLinesFromFile(String fileName) {
		try {
			return FileUtils.readLines(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected static void log(String message) {
		System.out.println(message);
	}

}
