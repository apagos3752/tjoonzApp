package fr.istic.nfc.test;

import com.example.nfc.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity{
	NfcAdapter mNfcAdapter;
	PendingIntent mNfcPendingIntent;
	IntentFilter[] mNdefExchangeFilters;
	boolean mWriteMode =false ;
	TextView mTextView;
	String msgCurrentSong = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc);
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
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
	
	@Override
	protected void onResume() {
		super.onResume();
		enableNdefExchangeMode();
	}

	@Override
	protected void onPause() {
		super.onPause();
		//enableNdefExchangeMode();
	}
	
	private void enableNdefExchangeMode() {
	    mNfcAdapter.setNdefPushMessage( getNdefMessage(), MainActivity.this);
	    mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
	}
	
	public NdefMessage getNdefMessage() {
	    byte[] textBytes = msgCurrentSong.getBytes();
	    
	    // Creating NdefRecord which will be sent
	    NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,"text/plain".getBytes(), new byte[] {}, textBytes);
	    
	    return new NdefMessage(new NdefRecord[] { textRecord });
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    // NDEF exchange mode
	    if (!mWriteMode && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
	        NdefMessage[] msgs = getNdefMessages(intent);
	        for (int i = 0; i < msgs.length; i++) {
	        	Toast.makeText(this, new String(msgs[0].getRecords()[0].getPayload()) +" MSG "+i, Toast.LENGTH_LONG).show();
			}
	        
	        TextView tv = (TextView) findViewById(R.id.textView1);
	        tv.setText(new String(msgs[0].getRecords()[0].getPayload()));
	    }
	}
	
	public NdefMessage[] getNdefMessages(Intent intent) {
	    // Parse the intent
	    NdefMessage[] msgs = null;
	    String action = intent.getAction();
	    
	    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
	        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	        
	        if (rawMsgs != null) {
	            msgs = new NdefMessage[rawMsgs.length];
	            for (int i = 0; i < rawMsgs.length; i++) {
	                msgs[i] = (NdefMessage) rawMsgs[i];
	            }
	        } else {
	            byte[] empty = new byte[] {};
	            NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
	            NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
	            
	            msgs = new NdefMessage[] { msg };
	        }
	    } else {
	        finish();
	    }
	    
	    return msgs;
	}
}
