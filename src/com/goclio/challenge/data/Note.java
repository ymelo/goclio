package com.goclio.challenge.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
	private int id, regardingId;
	private String regardingType, subject, detail, createdAt, updatedAt, date;

	
	public Note(String subject,
			String detail, String regardingType, int regardingId) {
		super();
		this.regardingType = regardingType;
		this.regardingId = regardingId;
		this.subject = subject;
		this.detail = detail;
	}
	public Note(int id, String subject, String detail, String createdAt,
			String updatedAt, String date) {
		super();
		this.id = id;
		this.subject = subject;
		this.detail = detail;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegardingType() {
		return regardingType;
	}

	public void setRegardingType(String regardingType) {
		this.regardingType = regardingType;
	}

	public int getRegardingId() {
		return regardingId;
	}

	public void setRegardingId(int regardingId) {
		this.regardingId = regardingId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeInt(id);
		dest.writeString(subject);
		dest.writeString(detail);
		dest.writeString(createdAt);
		dest.writeString(updatedAt);
		dest.writeString(date);
		dest.writeString(regardingType);
		dest.writeInt(regardingId);
	}

	public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
		public Note createFromParcel(Parcel in) {
			return new Note(in);
		}

		public Note[] newArray(int size) {
			return new Note[size];
		}
	};
	
	private Note(Parcel in) {
		id = in.readInt();
		subject = in.readString();
		detail = in.readString();
		createdAt = in.readString();
		updatedAt = in.readString();
		date = in.readString();
		regardingType = in.readString();
		regardingId = in.readInt();
	}
	
	@Override
	public String toString() {
		return String.valueOf(id);
	}

}
