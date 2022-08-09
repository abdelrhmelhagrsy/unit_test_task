package com.example.demo;


public class Player {

	private String name;
	private int [] arr = new int[21]; 
	private int totla_score;
	private boolean performance;
	
	public Player() {
		super();
	}

	public Player(String name, int[] arr, int totla_score, boolean performance) {
		super();
		this.name = name;
		this.arr = arr;
		this.totla_score = totla_score;
		this.performance = performance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getArr() {
		return arr;
	}

	public void setArr(int[] arr) {
		this.arr = arr;
	}

	public int getTotla_score() {
		return totla_score;
	}

	public void setTotla_score(int totla_score) {
		this.totla_score = totla_score;
	}

	public boolean getPerformance() {
		return performance;
	}

	public void setPerformance(boolean performance) {
		this.performance = performance;
	}
	
	
	
}
