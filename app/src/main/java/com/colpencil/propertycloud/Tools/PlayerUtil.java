package com.colpencil.propertycloud.Tools;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * @author 陈 宝
 * @Description:播放录音的工具类
 * @Email 1041121352@qq.com
 * @date 2016/11/30
 */
public class PlayerUtil {

    public MediaPlayer player;
    private OnCompleteListener listener;

    public PlayerUtil() {
    }

    public void start(String path) {
        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (listener != null) {
                    listener.complete();
                }
            }
        });
        try {
            player.setDataSource(path);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e("error------", e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {
        if (player != null && player.isPlaying()) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public void setOnCompleteListener(OnCompleteListener listener) {
        this.listener = listener;
    }

    public interface OnCompleteListener {
        void complete();
    }
}
