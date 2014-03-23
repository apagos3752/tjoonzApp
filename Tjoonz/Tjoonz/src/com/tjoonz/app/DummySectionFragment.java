package com.tjoonz.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class DummySectionFragment extends Fragment{

		public static final String ARG_SECTION_NUMBER = "section_number";
		private Button playBtn;
		private Button nextBtn;
		private Button stopBtn;
		private boolean playBtnStatus = false;
		static int[] playlist;
		static int currentSong;
		private int nbsong;
		Intent player;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
			nbsong = 2;
			playlist = new int[nbsong];
			playlist[0] = R.raw.octopus;
			playlist[1] = R.raw.sleepaway;
			
			currentSong = 0;
			
			View rootView;
			TextView dummyTextView;
			
			
			
			switch (getArguments().getInt(ARG_SECTION_NUMBER)){

				case 1 :	rootView = inflater.inflate(R.layout.fragment_home_dummy, container, false);
							dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
							dummyTextView.setText("Hey c'est la section 1! :)");
							break;

				case 2 : 	rootView = inflater.inflate(R.layout.fragment_home_dummy, container, false);
							dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
							dummyTextView.setText("ca c'est la section 2");
							break;

				case 3 :	rootView = inflater.inflate(R.layout.fragment_player_dummy, container, false);
							((WebView)rootView.findViewById(R.id.webView)).loadData(getResources().getString(R.string.text_sample), "text/html", null);
							playBtn = ((Button)rootView.findViewById(R.id.btn_play));
							nextBtn = ((Button)rootView.findViewById(R.id.btn_next));
							stopBtn = ((Button)rootView.findViewById(R.id.btn_stop));
							playerFragment();
							break;

				default :	rootView = inflater.inflate(R.layout.fragment_home_dummy, container, false);
							dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
							dummyTextView.setText("Wow, there's a problem with this fragment! :s");
			}
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

		private void playMusic(){

			playBtn.setBackgroundResource(R.drawable.pause_btn);
			player.putExtra("audio", "MUST DIE! - Octopus.mp3");
			this.getActivity().startService(player);	
		}

		private void stopMusic(){

			playBtn.setBackgroundResource(R.drawable.play_btn);
			this.getActivity().stopService(player);
		}
		
	}