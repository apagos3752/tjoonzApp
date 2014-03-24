package com.tjoonz.app;

import java.util.Locale;

import maps.example.tjoonz.MapsFrag;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Toast;

import com.tjoonz.player.PlayerFragment;
import com.tjoonz.utils.NFCUtils;


public class Home extends FragmentActivity implements ActionBar.TabListener{

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	private PlayerFragment player;
	
	/*Some variables avout NFC */
	NfcAdapter mNfcAdapter;
	PendingIntent mNfcPendingIntent;
	IntentFilter[] mNdefExchangeFilters;

	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		final ActionBar actionBar = getActionBar();
		player = new PlayerFragment();
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

			@Override
			public void onPageSelected(int position){

				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
	
		IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		
		if(mNfcAdapter == null){
			Toast.makeText(this, "NFC not available on your device", Toast.LENGTH_LONG).show();
		}
		
		try {
		    ndefDetected.addDataType("text/plain");
		} catch (MalformedMimeTypeException e) {
		
		}
		

		mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter{

		public SectionsPagerAdapter(FragmentManager fm){ super(fm); }

		@Override
		public Fragment getItem(int position){

			switch(position){
				case 0: return player;
				case 1: return new MapsFrag();
				default: return null;
			}
		}

		@Override
		public int getCount(){ return 2; }

		@Override
		public CharSequence getPageTitle(int position){

			Locale l = Locale.getDefault();
			switch (position){
				case 0: return getString(R.string.title_section1).toUpperCase(l);
				case 1: return getString(R.string.title_section2).toUpperCase(l);
				default: return null;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){

		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction){

		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction){}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction){}
	

	@Override
	protected void onResume() {
		super.onResume();
		enableNdefExchangeMode();
	}

	private void enableNdefExchangeMode() {
	    mNfcAdapter.setNdefPushMessage( NFCUtils.createNdefMessage("Song to play"), Home.this);
	    mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
	}
	
	
	@Override
	protected void onNewIntent(Intent intent) {
	    // NDEF exchange mode
	    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
	        NdefMessage[] msgs = NFCUtils.readNdefMessages(intent);
	        
	        for (int i = 0; i < msgs.length; i++) {
	        	Toast.makeText(this, new String(msgs[0].getRecords()[0].getPayload()) +" MSG "+i, Toast.LENGTH_LONG).show();
			}
	        
	        //player.playMusic();
	    }
	}
	
}