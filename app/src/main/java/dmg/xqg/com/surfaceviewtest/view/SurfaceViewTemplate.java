package dmg.xqg.com.surfaceviewtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by qhsj on 2018/1/10.
 */

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private  static  final  String TAG="chris";
    //SurfaceHolder
    private SurfaceHolder mHolder;
    //用于绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsSurfaceCreated;
    //画笔
    private Paint mPaint;
    //路径
    private Path mPath;

    public SurfaceViewTemplate(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 获取 holder
        mHolder = getHolder();
        //添加回调
        mHolder.addCallback(this);
        // 路径
        mPath = new Path();
        //初始化画笔
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    //Surface的生命周期
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsSurfaceCreated = true;

        // 开启线程去绘制
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsSurfaceCreated = false;
    }

    @Override
    public void run() {

        // 这是一个循环
        while(mIsSurfaceCreated){

            // 画
            draw();

            //通过线程休眠以控制刷新速度（帧率）
            try {
                Thread.sleep(50);// 控制1s内刷新20次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void draw() {
        try{
            //锁定画布并返回画布对象
            mCanvas = mHolder.lockCanvas();
            //接下去就是在画布上进行一下draw
            mCanvas.drawColor(Color.WHITE);// 画板的底色
            mCanvas.drawPath(mPath,mPaint);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //当画布内容不为空时，才post，避免出现黑屏的情况。
            if(mCanvas!=null){
                mHolder.unlockCanvasAndPost(mCanvas);// 释放画布
            }
        }
    }

    /**
     * 绘制触摸滑动路径
     * @param event MotionEvent
     * @return true
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int) event.getX();
        int y= (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    /**
     * 清屏
     * @return true
     */
    public boolean clean(){
        mPath.reset();// 清空路径
        return true;
    }

}
