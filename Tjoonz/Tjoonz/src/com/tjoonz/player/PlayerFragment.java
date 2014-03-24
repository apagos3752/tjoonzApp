package com.tjoonz.player;

import com.tjoonz.app.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

public class PlayerFragment extends Fragment{

	private Button playBtn;
	private Button nextBtn;
	private Button stopBtn;

	static int[] playlist = new int[]{R.raw.octopus, R.raw.deconstrukt};
	static int currentSong = 0;
	private int nbsong = 2;
	Intent player;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

		View rootView;

		rootView = inflater.inflate(R.layout.fragment_player, container, false);
		((WebView)rootView.findViewById(R.id.webView)).loadData(getResources().getString(R.string.text_sample), "text/html", null);
		playBtn = ((Button)rootView.findViewById(R.id.btn_play));
		nextBtn = ((Button)rootView.findViewById(R.id.btn_next));
		stopBtn = ((Button)rootView.findViewById(R.id.btn_stop));
		playerFragment();

		return rootView;
	}

	private void playerFragment(){

		player = new Intent(this.getActivity(), Player.class);
		playBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				playMusic();
			}
		});
		stopBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopMusic();
			}
		});			
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(currentSong == nbsong-1){
					currentSong = 0;
				}else{
					currentSong++;
				}
				stopMusic();
				playMusic();
			}
		});	
	}

	public void playMusic(){

		playBtn.setBackgroundResource(R.drawable.pause_btn);
		player.putExtra("audio", "MUST DIE! - Octopus.mp3");
		this.getActivity().startService(player);	
	}

	private void stopMusic(){

		playBtn.setBackgroundResource(R.drawable.play_btn);
		this.getActivity().stopService(player);
	}

}