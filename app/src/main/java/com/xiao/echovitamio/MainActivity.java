package com.xiao.echovitamio;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener {

    private TextView percentTv,netSpeedTv;
    private VideoView mVideoView;


    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;
    private FrameLayout fl_controller;
    boolean isPortrait=true;
    private long mPosition = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initVitamio();
        initView();
        setListen();
    }

    private void initVitamio() {
        /**
         * Vitamio在使用之前必须对其进行初始化操作，调用Vitamio.initialize(this)方法可对其进行初始化操作，
         * 该方法有一个返回值表示初始化是否成功，当初始化成功后我们再来进行进一步的操作
         */
        if (Vitamio.isInitialized(this)) {
            mVideoView = (VideoView) findViewById(R.id.vitamio);
            mVideoView.setVideoPath("http://flv2.bn.netease.com/videolib3/1704/06/GNzeA0559/SD/GNzeA0559-mobile.mp4");

            mVideoView.setMediaController(new MediaController(this));  //设置控制器
            mVideoView.start();
        }
    }


    private void initView() {
        //显示缓冲百分比的TextView
        percentTv = (TextView) findViewById(R.id.buffer_percent);
        //显示下载网速的TextView
        netSpeedTv = (TextView) findViewById(R.id.net_speed);


        fl_controller= (FrameLayout) findViewById(R.id.fl_controller);
        pb = (ProgressBar) findViewById(R.id.probar);
        downloadRateView = (TextView) findViewById(R.id.download_rate);
        loadRateView = (TextView) findViewById(R.id.load_rate);
    }

    private void setListen() {

        //监听缓冲百分比，percent表示当前缓冲百分比
        mVideoView.setOnBufferingUpdateListener(this);

        //监听缓冲的整个过程
        mVideoView.setOnInfoListener(this);
    }

    /**
     * @param mp    the MediaPlayer the info pertains to.
     * @param what  缓冲的时机
     * @param extra 当前的下载网速
     * 根据what参数我们可以判断出当前是开始缓冲还是缓冲结束还是正在缓冲，
     *              开始缓冲的时候，我们将左上角的两个控件显示出来，同时让播放器暂停播放，
     *              缓冲结束时将左上角两个控件隐藏起来，同时播放器开始播放，
     *              正在缓冲的时候我们就来显示当前的下载网速。
     */
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {

            //开始缓冲
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                percentTv.setVisibility(View.VISIBLE);
                netSpeedTv.setVisibility(View.VISIBLE);
                mp.pause();
                break;

            //缓冲结束
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                percentTv.setVisibility(View.GONE);
                netSpeedTv.setVisibility(View.GONE);
                mp.start();
                break;

            //正在缓冲
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                netSpeedTv.setText("当前网速:" + extra + "kb/s");
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        percentTv.setText("已缓冲：" + percent + "%");
    }
}
