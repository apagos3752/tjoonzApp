package com.example.tjoonz;

import java.text.DecimalFormat;
import java.util.HashMap;

public class ServerStub {

	HashMap<String,UserInfo> data;
	
	public ServerStub(){
		
		data = new HashMap<String,UserInfo>();
	}
	
	 
	public void genMarkersInfo(){
		
		this.createAndAddUserInfo("Iduine", "Octopus", 48.115195, -1.640156);
		this.createAndAddUserInfo("Okazari", "Deconstrukt", 48.115219, -1.640072);
		this.createAndAddUserInfo("Cisco", "KissMyLips", 48.115198, -1.639971);
		this.createAndAddUserInfo("Zev", "Raptor", 48.115265, -1.640042);
		this.createAndAddUserInfo("Koopa", "Legends", 35.093911, -87.858114);
		this.createAndAddUserInfo("Masskass", "SmellYourD*ck", 36.240363, 138.194081);
		this.createAndAddUserInfo("Peach", "GloryHole", -33.581606, 150.245096);
		this.createAndAddUserInfo("Teijiro", "Decisions", -29.077322, -38.186912);

	}
	
	public HashMap<String, UserInfo> getData() {
		return data;
	}
	
	private void createAndAddUserInfo(String username, String currentSong, double latitude, double longitude){
		
		data.put(username, new UserInfo(username, currentSong, latitude, longitude));
		
	}
	
	public static void main(String[] args){
		
		ServerStub ss = new ServerStub();
		ss.genMarkersInfo();
		HashMap<String, UserInfo> hm = ss.getData();
		for(String key: hm.keySet()){
			System.out.println(hm.get(key).toString());
		}
		
	}
	
}