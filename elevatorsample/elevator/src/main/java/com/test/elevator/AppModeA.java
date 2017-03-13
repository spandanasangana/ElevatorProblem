package com.test.elevator;

import org.apache.commons.lang.StringUtils;

public class AppModeA extends Helper
{
	int currentLocation = 0;
	int start = 0;
	int end = 0;

	public static void main( String[] args )
	{
		sanityChecks(args);
		new AppModeA().run(args);
	}
	private void run(String[] args) {
		for (int index = 0; index < inputList.size(); index++) {
			modeAProcess(index);
		}
	}
	
	private void modeAProcess(int index) {
		String input = inputList.get(index);
		String floorsList[] = StringUtils.split(input, ",");
		currentLocation = Integer.parseInt(StringUtils.split(floorsList[0], ":")[0]);
		String[] trips;
		int totalTraversalPathCount = 0;
		String traversePath = "";
		for (int j = 0; j < floorsList.length; j++) {
			String trip = floorsList[j];
			trips = trip.split("-");
			start = readSource(trips);
			end = readDest(trips);
			traversePath += " " + start + " " + end; 
			if(j == 0){ totalTraversalPathCount += Math.abs(currentLocation - start); }
			else { totalTraversalPathCount = totalTraversalPathCount + Math.abs(currentLocation-start); }
			currentLocation = start;
			totalTraversalPathCount = totalTraversalPathCount + Math.abs(start - end);
			currentLocation = end;
		}
		log(trim(traversePath) + " (" + totalTraversalPathCount + ")");
		totalTraversalPathCount = 0;
	}

}