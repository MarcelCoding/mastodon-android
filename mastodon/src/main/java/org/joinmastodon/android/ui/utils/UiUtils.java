package org.joinmastodon.android.ui.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import org.joinmastodon.android.R;

import java.time.Instant;

import androidx.annotation.ColorRes;
import androidx.browser.customtabs.CustomTabsIntent;

public class UiUtils{
	private UiUtils(){}

	public static void launchWebBrowser(Context context, String url){
		// TODO setting for custom tabs
		new CustomTabsIntent.Builder()
				.build()
				.launchUrl(context, Uri.parse(url));
	}

	public static String formatRelativeTimestamp(Context context, Instant instant){
		long t=instant.toEpochMilli();
		long now=System.currentTimeMillis();
		long diff=now-t;
		if(diff<60_000L){
			return context.getString(R.string.time_seconds, diff/1000L);
		}else if(diff<3600_000L){
			return context.getString(R.string.time_minutes, diff/60_000L);
		}else if(diff<3600_000L*24L){
			return context.getString(R.string.time_hours, diff/3600_000L);
		}else{
			return context.getString(R.string.time_days, diff/(3600_000L*24L));
		}
	}

	/**
	 * Android 6.0 has a bug where start and end compound drawables don't get tinted.
	 * This works around it by setting the tint colors directly to the drawables.
	 * @param textView
	 * @param color
	 */
	public static void fixCompoundDrawableTintOnAndroid6(TextView textView, @ColorRes int color){
		Drawable[] drawables=textView.getCompoundDrawablesRelative();
		for(int i=0;i<drawables.length;i++){
			if(drawables[i]!=null){
				Drawable tinted=drawables[i].mutate();
				tinted.setTintList(textView.getContext().getColorStateList(color));
				drawables[i]=tinted;
			}
		}
		textView.setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3]);
	}
}