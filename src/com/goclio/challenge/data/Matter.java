/*
   GoClioChallnge, a challenge (test) prior to an interview with goclio.com
    Copyright (C) <2014>  <Yohann MELO>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
