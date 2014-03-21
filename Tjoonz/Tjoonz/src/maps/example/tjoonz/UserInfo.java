package com.example.tjoonz;

public class UserInfo {

	private String username;
	private String currentSong;
	private double latitude;
	private double longitude;
	
	public UserInfo(String username, String currentSong, double latitude, double longitude){
		
		this.username = username;
		this.currentSong = currentSong;
		this.latitude = latitude;
		this.longitude = longitude;
		
	}

	public String getUsername(){
		return username;
	}
	
	public String getCurrentSong(){
		return currentSong;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongitude(){
		return longitude;
	}

	public String toString(){
		String u = "Username : " + username + "\n";
		String c = "Current song : " + currentSong + "\n";
		String p = "Position ("+Double.toString(latitude)+","+Double.toString(longitude)+")" + "\n";
		return u + " " + c + " " + p;
	}
	
}
