package com.tjoonz.app;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Player extends Service implements OnCompletionListener, OnPreparedListener, OnErrorListener, OnSeekCompleteListener, OnInfoListener, OnBufferingUpdateListener{

	private MediaPlayer mp = new MediaPlayer();

	@Override
	public void onCreate() {
	
		mp.setOnCompletionListener(this);
		mp.setOnErrorListener(this);
		mp.setOnPreparedListener(this);
		mp.setOnBufferingUpdateListener(this);
		mp.setOnSeekCompleteListener(this);
		mp.setOnInfoListener(this);
		mp.reset();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		if(mp != null){
			if(mp.isPlaying())
				mp.stop();
			mp.release();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		mp.reset();
		if(!mp.isPlaying()){
			try {
				mp.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/" + DummySectionFragment.playlist[DummySectionFragment.currentSong]));
//				mp.setDataSource("http://licensing.glowingpigs.com/Audio/10.mp3");
				mp.prepareAsync();
			} catch (Exception e) {
				Log.d("iduine", "pb sur la lecture");
				e.printStackTrace();
			}
		}

		return START_STICKY;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {

		if(!mp.isPlaying())
			mp.start();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {

		if(mp.isPlaying())
			mp.stop();
		stopSelf();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {

		switch(what){
		case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
			Toast.makeText(this, "Media error " + extra + " : Not valid for progressive playback.", Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			Toast.makeText(this, "Media error " + extra + " : Server Died.", Toast.LENGTH_SHORT).show();
			break;
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			Toast.makeText(this, "Media error " + extra + " : Unknown error.", Toast.LENGTH_SHORT).show();
			break;
		}
		return false;
	}

	@Override
	public IBinder onBind(Intent intent) { return null; }
	

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) { return false; }

	@Override
	public void onSeekComplete(MediaPlayer mp) {}
}