package com.machinarium.model.userBin;

public class Stats {
	private final String userName;
	private final int firstCount;
	private final int secondCount;
	private final int thirdCount;
	private final int loseCount;


	public Stats(String userName, int firstCount, int secondCount,
				                  int thirdCount, int loseCount) {
		this.userName = userName;
		this.firstCount = firstCount;
		this.secondCount = secondCount;
		this.thirdCount = thirdCount;
		this.loseCount = loseCount;
	}


	public String getUserName() {
		return userName;
	}

	public int getFirstCount() {
		return firstCount;
	}

	public int getSecondCount() {
		return secondCount;
	}

	public int getThirdCount() {
		return thirdCount;
	}

	public int getLoseCount() {
		return loseCount;
	}

}
