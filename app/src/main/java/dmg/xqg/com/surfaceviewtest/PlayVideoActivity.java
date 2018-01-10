package dmg.xqg.com.surfaceviewtest;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class PlayVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPause;
    private SurfaceView sv;
    private MediaPlayer mediaPlayer;
    private int currentPosition;
    private Uri uri;
    private Button btnStart;
    private Button btnStop;
    private Button btnReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initView();
        initListener();

        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.guide_video01);

        // android4.0以下要加上，4.0后废弃了，告诉surfaceview 不要自己维护缓冲区 而是 等待多媒体播放器的框架 把数据填充过来
        sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        // 2.3 2.2模拟器有bug,播放不了视频， 需要用1.6模拟器或者真机

        sv.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    currentPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                Log.e("chris","holder 销毁了。。。播放位置：" + currentPosition);
            }
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Log.e("chris","holder 创建了。。。");
                if (currentPosition > 0) {
                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(getApplicationContext(), uri);
                        mediaPlayer.setDisplay(sv.getHolder());
                        mediaPlayer.prepareAsync();
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                Log.e("chris","从"+currentPosition+"位置开始播放");
                                mediaPlayer.seekTo(currentPosition);
                                mediaPlayer.start();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.e("chris","holder 变化了。。。");
            }
        });
    }

    private void initListener() {
        btnStart.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnReply.setOnClickListener(this);
    }

    private void initView() {
        btnStart = (Button) this.findViewById(R.id.btnStart);
        btnPause = (Button) this.findViewById(R.id.btnPause);
        btnStop = (Button) this.findViewById(R.id.btnStop);
        btnReply = (Button) this.findViewById(R.id.btnReply);
        sv = (SurfaceView) this.findViewById(R.id.sv);
    }

    /**
     * 开始播放视频
     */
    public void start() {
        try {
            btnPause.setText("暂停");

            // 先停止
            stop();

            // 再开始
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(),uri);
            mediaPlayer.setLooping(false);// 循环播放
            SurfaceHolder sh = sv.getHolder();
            mediaPlayer.setDisplay(sh);// 关联 SurfaceHolder
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
        } catch (Exception e) {
            Log.e("chris","Exception=" + e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 暂停播放视频
     *
     */
    public void pause() {
        if (mediaPlayer!=null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            btnPause.setText("暂停");
            return;
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPause.setText("续播");
        }
    }
    /**
     * 停止播放视频
     */
    public void stop() {
        btnPause.setText("暂停");
        if (mediaPlayer != null ) {// && mediaPlayer.isPlaying()
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    /**
     * 重新播放视频
     */
    public void reply() {
        btnPause.setText("暂停");

        // 先停止
        stop();

//        if (mediaPlayer != null && mediaPlayer.isPlaying()){
//            mediaPlayer.seekTo(0);
//        }

        // 再播放
        start();
    }

    @Override
    public void onClick(View v) {
        if (v == btnStart){
            start();
        }else if (v == btnPause){
            pause();
        }else if (v == btnReply){
            reply();
        }else if (v == btnStop){
            stop();
        }
    }
}
