package dmg.xqg.com.surfaceviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import dmg.xqg.com.surfaceviewtest.view.SurfaceViewTemplate;

public class CustomViewActivity extends AppCompatActivity {

    private TextView tvClean;
    private SurfaceViewTemplate surfaceViewTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        initView();
        initListener();
    }

    private void initListener() {
        // 清屏
        tvClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surfaceViewTemplate.clean();
            }
        });
    }

    private void initView() {
        tvClean = (TextView) findViewById(R.id.tvClean);
        surfaceViewTemplate = (SurfaceViewTemplate) findViewById(R.id.svt);
    }

}
