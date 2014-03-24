package com.tjoonz.utils;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;

public class NFCUtils {
	
	public static NdefMessage createNdefMessage(String messageToSend) {
	    byte[] textBytes = messageToSend.getBytes();
	    
	    // Creating NdefRecord which will be sent
	    NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,"text/plain".getBytes(), new byte[] {}, textBytes);
	    
	    return new NdefMessage(new NdefRecord[] { textRecord });
	}
	
    public static NdefMessage[] readNdefMessages(Intent intent) {
	    NdefMessage[] msgs = null;
	    String action = intent.getAction();
	    
	    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
	        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	        
	        if (rawMsgs != null) {
	            msgs = new NdefMessage[rawMsgs.length];
	            for (int i = 0; i < rawMsgs.length; i++) {
	                msgs[i] = (NdefMessage) rawMsgs[i];
	            }
	        } 
	        else {
	            byte[] empty = new byte[] {};
	            NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
	            NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
	            
	            msgs = new NdefMessage[] { msg };
	        }
	    }
	    
	    return msgs;
	}
}
