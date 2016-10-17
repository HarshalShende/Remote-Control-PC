package com.example.remotecontrolpc.mediaplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.remotecontrolpc.CallbackReceiver;
import com.example.remotecontrolpc.MusicImageAvatar;
import com.example.remotecontrolpc.Utility;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

public abstract class SongsList extends AsyncTask<Void, Void, ArrayList<MusicImageAvatar>> implements CallbackReceiver {
	Context context;
	public SongsList(Context context) {
		this.context = context;
	}
	protected ArrayList<MusicImageAvatar> doInBackground(Void... params) {
		ArrayList <MusicImageAvatar> songsList = new ArrayList<MusicImageAvatar>();
		ContentResolver musicResolver = context.getContentResolver();
    	Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
    	if (musicCursor!=null && musicCursor.moveToFirst()) {
    		int titleColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
    		int artistColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
    		int dataColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA);
    		int durationColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DURATION);
    		int albumIdColumn = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM_ID);
    		do {
    			String thisTitle = musicCursor.getString(titleColumn);
    			String thisArtist = musicCursor.getString(artistColumn);
    			String thisData = musicCursor.getString(dataColumn);
    			int thisDuration = musicCursor.getInt(durationColumn);
    			int thisAlbumId = musicCursor.getInt(albumIdColumn);
    			String subHeading = thisArtist + ", " + new Utility().getDuration(thisDuration);
    			songsList.add(new MusicImageAvatar(thisAlbumId, thisTitle, subHeading, thisData, "music"));
    		} while (musicCursor.moveToNext());
    	}
    	Collections.sort(songsList,new Comparator<MusicImageAvatar>() {
			public int compare(MusicImageAvatar a,MusicImageAvatar b) {
				return a.getHeading().compareTo(b.getHeading());
			}
		});
		return songsList;
	}
	@Override
	protected void onPostExecute(ArrayList<MusicImageAvatar> songsList) {
		receiveData(songsList);
	}
	@Override
	public abstract void receiveData(Object result);
}