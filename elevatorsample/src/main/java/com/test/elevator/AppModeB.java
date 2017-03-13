package com.test.elevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class AppModeB extends Helper{

	static ArrayList<Node> upward = new ArrayList<Node>();
	static ArrayList<Node> downward = new ArrayList<Node>();
	static ArrayList<Node> combined = new ArrayList<Node>();
	static ArrayList<Node> trivarsePath = new ArrayList<Node>();
	static ArrayList<Node> currentTraversePath = new ArrayList<Node>();
	static int totalSteps = Integer.MAX_VALUE;
	static int currentIteration = 0;
	static int initialFloor = 0;

	public AppModeB(String mode, String fileName) {
		sanityChecks(fileName, mode);
		run();
	}

	public AppModeB() {
		// TODO Auto-generated constructor stub
	}

	static void initialize() {
		upward = new ArrayList<Node>();
		downward = new ArrayList<Node>();
		combined = new ArrayList<Node>();
		trivarsePath = new ArrayList<Node>();
		currentTraversePath = new ArrayList<Node>();
		totalSteps = Integer.MAX_VALUE;
		currentIteration = 0;
		initialFloor = 0;
	}
	
	private void run() {
		for (int index = 0; index < inputList.size(); index++) {
			//9:1-5,1- 6,1-5
			modeBProcess(index);
		}
	}
	
	public void modeBProcess(int index){
		String data;
		String content = inputList.get(index);
		initialize();
		String[] tokens = content.split(":");
		initialFloor = Integer.parseInt(tokens[0]);
		data = tokens[1];
		data = data.substring(data.indexOf(":") + 1);
		String[] floors = data.split(",");
		for (String floor : floors) {
			determineDirection(floor);
		}
		populateEdges();
		computeTotalStops();
	}

	public static void main(String args[]) throws Exception, IOException {
	//	sanityChecks(this.fileName);
	//	new AppModeB().run();
	}

	private static void computeTotalStops() {
		for (Node node : combined) {
			currentTraversePath.clear();
			setAllNodesNotVisited();

			currentIteration = Math.abs(initialFloor - node.start) + Math.abs(node.start - node.end);
			currentTraversePath.add(node);
			computedStepsFromNode(node);
			if (totalSteps > currentIteration) {
				totalSteps = currentIteration;
				trivarsePath.clear();
				for (Node _node : currentTraversePath) {
					trivarsePath.add(_node);
				}
			}

		}
		StringBuffer output = new StringBuffer();
		if (trivarsePath.get(0).stops.first() != initialFloor)
			output.append(initialFloor);
		for (Node node : trivarsePath) {
			for (Integer floor : node.stops) {
				output.append(" " + floor);
			}
		}
		output.append(" (" + totalSteps + ")");
		System.out.println(trim(output.toString()));
	}

	private static void computedStepsFromNode(Node node) {

		// TODO Auto-generated method stub
		node.setVisited(true);

		for (Edge edge : node.edges) {
			Node _currentNode = edge.end;
			if (!_currentNode.isVisted()) {
				currentTraversePath.add(_currentNode);
				currentIteration += edge.weight + Math.abs(_currentNode.start - _currentNode.end);
				edge.end.setVisited(true);
				computedStepsFromNode(_currentNode);
			}

		}
	}

	private static void setAllNodesNotVisited() {
		for (Node node : combined) {
			node.setVisited(false);
		}

	}

	private static void populateEdges() {
		// TODO Auto-generated method stub
		combined.addAll(upward);
		combined.addAll(downward);

		for (Node node : combined) {
			for (Node currentNode : combined) {
				if (node == currentNode) {
					continue;
				}
				node.edges.add(new Edge(node, currentNode, Math.abs(node.end - currentNode.start)));
			}

		}
	}

	private static void determineDirection(String request) {
		String floors[] = request.split("-");
		int start = Integer.parseInt(trim(floors[0]));
		int end = Integer.parseInt(trim(floors[1]));
		if (end > start) {
			processUpward(start, end);
		} else {
			processDownward(start, end);
		}

	}

	private static void processUpward(int start, int end) {
		boolean processed = false;
		Node node;
		for (int i = 0; i < upward.size(); i++) {
			node = upward.get(i);
			if ((end < node.start) || (start > node.end)) {
				continue;
			} else if (start <= node.start && end >= node.end) {
				node.stops.add(start);
				node.stops.add(end);
				node.stops.add(node.start);
				node.stops.add(node.end);

				node.start = start;
				node.end = end;
				upward.set(i, node);
				processed = true;
				break;

			}

			else if (start <= node.start && (node.start >= end && end <= node.end)) {
				node.stops.add(start);
				node.stops.add(node.start);
				node.start = start;
				upward.set(i, node);
				processed = true;
				break;
			} else if (start <= node.end && (node.start <= start && end >= node.end)) {

				node.stops.add(end);
				node.stops.add(node.end);
				node.end = end;
				upward.set(i, node);
				processed = true;
				break;
			} else if (start >= node.start && end <= node.end) {
				node.stops.add(start);
				node.stops.add(end);

				processed = true;
				break;
			}
		}

		if (!processed) {
			node = new Node(start, end, "U");
			node.stops.add(start);
			node.stops.add(end);

			upward.add(node);
		}
	}

	private static void processDownward(int start, int end) {
		boolean processed = false;
		Node node;
		for (int i = 0; i < downward.size(); i++) {
			node = downward.get(i);
			if ((end > node.start) || (start < node.end)) {
				continue;
			} else if (start >= node.start && end <= node.end) {
				node.stops.add(start);
				node.stops.add(end);
				node.stops.add(node.start);
				node.stops.add(node.end);
				node.start = start;
				node.end = end;
				downward.set(i, node);
				processed = true;
				break;

			} else if (start >= node.start && (node.start >= end && end >= node.end)) {
				node.stops.add(start);
				node.stops.add(node.start);
				node.start = start;
				downward.set(i, node);
				processed = true;
				break;
			}

			else if (end <= node.end && (node.start >= start && start >= node.end)) {
				node.stops.add(end);
				node.stops.add(node.end);

				node.end = end;
				downward.set(i, node);
				processed = true;
				break;
			}
		}

		if (!processed) {
			node = new Node(start, end, "D");
			node.stops.add(start);
			node.stops.add(end);

			downward.add(node);
		}

	}

}

class Node {
	public String direction;
	public TreeSet<Integer> stops = new TreeSet<Integer>();
	public boolean visited;
	public int start;
	public int end;
	public ArrayList<Edge> edges = new ArrayList<Edge>();

	public Node(int _start, int _end, String _direction) {
		start = _start;
		end = _end;
		direction = _direction;
		if (_direction.equals("D")) {
			stops = new TreeSet<Integer>(new Comparator<Integer>() {
				public int compare(Integer i1, Integer i2) {
					return i2.compareTo(i1);
				}
			});
		} else {
			stops = new TreeSet<Integer>(new Comparator<Integer>() {
				public int compare(Integer i1, Integer i2) {
					return i1.compareTo(i2);
				}
			});
		}

	}

	public String toString() {
		return start + " : " + end;
	}

	public boolean equals(Node _obj) {
		if (this.start == _obj.start && this.start == _obj.end) {
			return true;
		}
		return false;

	}

	public boolean isVisted() {
		return visited;
	}

	public void setVisited(boolean _visited) {
		visited = _visited;
	}
}

class Edge {
	public Node start;
	public Node end;
	public double weight;

	public Edge(Node _start, Node _end, int _weight) {
		start = _start;
		end = _end;
		weight = _weight;
	}
}