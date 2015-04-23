package com.andrei.storytelling.models.animations;

import org.json.JSONArray;
import org.json.JSONObject;

import com.andrei.storytelling.BookParser;
import com.andrei.storytelling.util.Tools;

import android.graphics.drawable.AnimationDrawable;

public class FrameAnimation extends AnimationDrawable {
	private int totalDuration = 0;
	
	public int getTotalDuration() {
		return totalDuration;
	}

	public static FrameAnimation getFrameAnimation(JSONObject object) {

		FrameAnimation animation = new FrameAnimation();

		if (object != null) {
			JSONArray frames = object.optJSONArray("frames");
			if (frames == null)
				return animation;
			String name;
			int duration;

			for (int i = 0, n = frames.length(); i < n; i++) {
				duration = frames.optJSONObject(i).optInt("duration");
				animation.totalDuration += duration;
				name = frames.optJSONObject(i).optString("name");
				animation.addFrame(
						Tools.getDrawable(BookParser.getmContext(), name),
						duration);
			}
			animation.setOneShot(true);
		}

		return animation;
	}
}
