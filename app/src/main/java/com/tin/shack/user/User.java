package com.tin.shack.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aayushsubedi on 9/4/17.
 */

public class User implements Parcelable {
	private String uid;
	private String fid;
	private String name;
	private String profilePicture;
	
	protected User(Parcel in) {
		uid = in.readString();
		fid = in.readString();
		name = in.readString();
		profilePicture = in.readString();
	}
	
	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}
		
		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getFid() {
		return fid;
	}
	
	public void setFid(String fid) {
		this.fid = fid;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProfilePicture() {
		return profilePicture;
	}
	
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(uid);
		dest.writeString(fid);
		dest.writeString(name);
		dest.writeString(profilePicture);
	}
}
