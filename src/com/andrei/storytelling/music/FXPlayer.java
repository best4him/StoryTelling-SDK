package com.andrei.storytelling.music;

import java.util.HashMap;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Build;

public class FXPlayer implements OnLoadCompleteListener {
	
	private SoundPool mSoundPool;
	private HashMap<Integer, Integer> mSoundPoolMap;
	private AudioManager mAudioManager;
	private Context mContext;
	private byte channels = 1;

	public FXPlayer(Context theContext) {
		initSounds(theContext);
	}

	public FXPlayer(byte channels, Context theContext) {
		this.channels = channels;
		initSounds(theContext);
	}

	private void initSounds(Context theContext) {
		mContext = theContext;
		// The first number here is the number of audio channels

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			createNewSoundPool();
		} else {
			createOldSoundPool();
		}
		
		mSoundPoolMap = new HashMap<Integer, Integer>();
		mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
	}

	public void addSound(int index, int SoundID) {
		try {
			mSoundPoolMap.put(index, mSoundPool.load(mContext, SoundID, 1));
		} catch (Exception e) {
			System.out.println("aaa: eroare");
			
			e.printStackTrace();
		}
	}

	public void playSound(int index, int loop) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, loop, 1f);
	}

	// Returns the stream id of the sound.
	public int playLoopedSound(int index) {
		float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		return mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
	}

	// Pause a stream specified by a streamID returned by play.
	public void pause(int streamID) {
		mSoundPool.pause(streamID);
	}

	// Resume a stream specified by a streamID returned by play.
	public void resume(int streamID) {
		mSoundPool.resume(streamID);
	}

	public void release() {
		
		if (mSoundPool != null) {
			mSoundPool.release();
			mSoundPool = null;
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	protected void createNewSoundPool() {
		AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
				.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
		mSoundPool = new SoundPool.Builder().setAudioAttributes(attributes).build();
	}

	@SuppressWarnings("deprecation")
	protected void createOldSoundPool() {
		mSoundPool = new SoundPool(channels, AudioManager.STREAM_MUSIC, 0);
	}

	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		
		if (status == 0) {
			
		}
	}
}