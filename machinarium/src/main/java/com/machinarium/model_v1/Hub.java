package com.machinarium.model_v1;

import java.util.ArrayList;
import java.util.List;

public class Hub {
	private List<User> users;

	public Hub() {
		users = new ArrayList<>();
	}

	public Hub(List<User> users) {
		this.users = users;
	}


	public List<User> getUsers() {return users; }

	public void setUsers(List<User> users) { this.users = users; }


}
