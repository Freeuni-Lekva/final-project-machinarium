package com.machinarium.model_v1;

public class User {
	private String name;
	private String password;
	private String mail;

	private int numRaces;
	private int numWins;

	private Garage garage;


	public User(String name, String password, String mail) {
		this.name = name;
		this.password = password;
		this.mail = mail;

		numRaces = 0;
		numWins = 0;

		garage = new Garage();
	}


	public String getName() { return name; }
//	public void setName(String name) { this.name = name; }  //!!

	public String getPassword() { return password; }
//	public void setPassword(String password) { this.password = password; }  //!!

	public String getMail() { return mail; }
	public void setMail(String mail) { this.mail = mail; }


	public int getNumRaces() { return numRaces; }
	public void setNumRaces(int numRaces) { this.numRaces = numRaces; }
	public void incrNumRaces() { numRaces = numRaces + 1; }

	public int getNumWins() { return numWins; }
	public void setNumWins(int numWins) { this.numWins = numWins; }
	public void incrNumWins() { numWins = numWins + 1; }


	public Garage getGarage() { return garage; }
	public void setGarage(Garage garage) { this.garage = garage; }


}
