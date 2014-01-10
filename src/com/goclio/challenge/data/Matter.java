package com.goclio.challenge.data;

public class Matter {

	private int id;
	private String displayNumber, description;
	public Matter(int id, String displayNumber, String description) {
		this.id = id;
		this.displayNumber = displayNumber;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDisplayNumber() {
		return displayNumber;
	}
	public void setDisplayNumber(String displayNumber) {
		this.displayNumber = displayNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString() {
		return displayNumber;
	}
}
