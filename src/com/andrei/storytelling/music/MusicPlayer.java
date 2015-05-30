package com.andrei.storytelling.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.andrei.storytelling.util.Tools;

public class MusicPlayer {

	private  MediaPlayer player;


	private MusicPlayer() {
	};

	public static MusicPlayer create(Context context, String songName) {

		MusicPlayer mPlayer = new MusicPlayer();
		mPlayer.player = MediaPlayer.create(context, Tools.getSong(context, songName));
		
		return mPlayer;
	}

	public  boolean isPlaying() {
		if (this.player != null) {
			return this.player.isPlaying();
		}
		return false;
	}

	public void stop() {

		if (player != null) {
			release();
			player = null;
		}
	}

	public void togglePlayPause() {

		if (player != null) {

			if (player.isPlaying()) {
				player.pause();
			} else {
				player.start();
			}
		} else {
			play();
		}
	}
	public void pause () {
		if (player != null) {

			if (player.isPlaying()) {
				player.pause();
			}
		}
	}
	public void release() {
		
		if (player != null) {
			player.stop();
			player.release();
			player = null;
		}		
	}

	/**
	 * Starts or resumes playback. If playback had previously been paused, playback will continue
	 *  from where it was paused. If playback had been stopped, or never started before, playback will start at the beginning.
	 */
	public  void play() {
		System.out.println("ttt player: " + player);
		if (player == null) {
			return;
		}
		player.seekTo(0);
		player.start();
	}
	
	public void setVolume(float leftVolume, float rightVolume) {
		if (player != null) {
			player.setVolume(leftVolume, rightVolume);
		}
	}
//    private void goBlooey(Throwable t, Context context) {
//    	
//        AlertDialog.Builder builder=new AlertDialog.Builder(context);
//        builder
//            .setTitle("Exception!")
//            .setMessage(t.toString())
//            .setPositiveButton("OK", null)
//            .show();
//    }


}
