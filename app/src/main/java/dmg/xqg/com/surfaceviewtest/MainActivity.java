package dmg.xqg.com.surfaceviewtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvPlayVideo;
    private TextView tvCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        tvPlayVideo.setOnClickListener(this);
        tvCustomView.setOnClickListener(this);
    }

    private void initView() {
        tvPlayVideo = (TextView) findViewById(R.id.tvPlayVideo);
        tvCustomView = (TextView) findViewById(R.id.tvCustomView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvPlayVideo){
            startActivity(new Intent(MainActivity.this,PlayVideoActivity.class));
        }else if (v.getId() == R.id.tvCustomView){
            startActivity(new Intent(MainActivity.this,PlayVideoActivity.class));
        }
    }
}
