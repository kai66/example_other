package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by kai on 2018/2/26.
 */

public class SimpleLineView extends View{

    //View 的宽和高
    private int mWidth, mHeight;

    private String[] mXLabels ;
    //线条的宽度
    private float mStrokeWidth = 3.0f;
    private Paint axisPaint;//x轴文字

    private Paint axisYPaint;//Y轴文字

    private Paint lineYPaint;//线

    private  Paint pointPaint;//点

    private final int divider = 10 ;

    //点的半径
    private float mPointRadius = 6;

    public SimpleLineView(Context context) {
        this(context, null);
    }

    public SimpleLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){

        axisPaint = new Paint();
        axisPaint.setTextSize(18);
        axisPaint.setAntiAlias(true);
        axisPaint.setColor(Color.parseColor("#3F51B5"));


        axisYPaint = new Paint();
        axisYPaint.setTextSize(18);
        axisYPaint.setAntiAlias(true);
        axisYPaint.setColor(Color.parseColor("#3F51B5"));

        lineYPaint = new Paint();
        lineYPaint.setColor(Color.parseColor("#3F51B5"));
        lineYPaint.setAntiAlias(true);
        //设置线条宽度
        lineYPaint.setStrokeWidth(mStrokeWidth);
        lineYPaint.setStyle(Paint.Style.FILL);

        //画点
        pointPaint = new Paint();
        pointPaint.setColor(Color.parseColor("#3F51B5"));
        pointPaint.setStyle(Paint.Style.FILL);

    }

    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }else if(widthMode == MeasureSpec.AT_MOST){
            throw new IllegalArgumentException("width must be EXACTLY,you should set like android:width=\"200dp\"");
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }else if(widthMeasureSpec == MeasureSpec.AT_MOST){
            throw new IllegalArgumentException("height must be EXACTLY,you should set like android:height=\"200dp\"");
        }
        setMeasuredDimension(mWidth, mHeight);
    }
    */
    /**
     * 比onDraw先执行
     * <p>
     * 一个MeasureSpec封装了父布局传递给子布局的布局要求，每个MeasureSpec代表了一组宽度和高度的要求。
     * 一个MeasureSpec由大小和模式组成
     * 它有三种模式：UNSPECIFIED(未指定),父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小;
     * EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
     * AT_MOST(至多)，子元素至多达到指定大小的值。
     * <p>
     * 它常用的三个函数：
     * 1.static int getMode(int measureSpec):根据提供的测量值(格式)提取模式(上述三个模式之一)
     * 2.static int getSize(int measureSpec):根据提供的测量值(格式)提取大小值(这个大小也就是我们通常所说的大小)
     * 3.static int makeMeasureSpec(int size,int mode):根据提供的大小值和模式创建一个测量值(格式)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        // Log.e("YView", "---minimumWidth = " + minimumWidth + "");
        // Log.e("YView", "---minimumHeight = " + minimumHeight + "");
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        mWidth = width;
        mHeight = height;
        Log.v("TAG", "kevin mWidth=" + mWidth + " mHeight=" + mHeight);
        setMeasuredDimension(width, height);
    }
    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        ///Log.e("YViewWidth", "---speSize = " + specSize + "");
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = Math.min(defaultWidth, specSize);
                //defaultWidth = (int) mPaint.measureText(mText) + getPaddingLeft() + getPaddingRight();
                // Log.e("YViewWidth", "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                // Log.e("YViewWidth", "---speMode = EXACTLY");
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                // Log.e("YViewWidth", "---speMode = UNSPECIFIED");
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        // Log.e("YViewHeight", "---speSize = " + specSize + "");
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = Math.max(defaultHeight, specSize);
                //defaultHeight = (int) (-mPaint.ascent() + mPaint.descent()) + getPaddingTop() + getPaddingBottom();
                // Log.e("YViewHeight", "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                //Log.e("YViewHeight", "---speSize = EXACTLY");
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                //Log.e("YViewHeight", "---speSize = UNSPECIFIED");
//        1.基准点是baseline
//        2.ascent：是baseline之上至字符最高处的距离
//        3.descent：是baseline之下至字符最低处的距离
//        4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
//        5.top：是指的是最高字符到baseline的值,即ascent的最大值
//        6.bottom：是指最低字符到baseline的值,即descent的最大值
                break;
        }
        return defaultHeight;
    }

    private String[] mYLabels ;
    public void setYLabels(String[] ylabels){
        mYLabels = ylabels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mXLabelsSize = mXLabels.length;
        int xStep = mWidth/mXLabelsSize;
        int textHeight = (int) (axisPaint.descent()-axisPaint.ascent());
        int textwidth = (int)axisPaint.measureText(mXLabels[0]);

        int textYHeight = (int) (axisYPaint.descent()-axisYPaint.ascent());
        int textYwidth = (int)axisYPaint.measureText(mYLabels[mYLabels.length-1]);
        int yStep = mHeight/mYLabels.length;

        for (int i = 0;i<mXLabelsSize;i++){//x轴文字
            canvas.drawText(mXLabels[i], i * xStep + textYwidth , mHeight - textHeight/2, axisPaint);

            //画点
            // canvas.drawCircle(i * xStep + divider + textYwidth, mHeight - textHeight - divider, mPointRadius, pointPaint);
        }



        for(int j =0;j<mYLabels.length;j++){
            canvas.drawText(mYLabels[j],  0 , mHeight - textYHeight/2 - yStep * j -divider, axisYPaint);
            canvas.drawLine(divider+textYwidth,mHeight -textHeight - yStep * j -divider, mWidth,mHeight -textHeight - yStep * j -divider,lineYPaint);
        }

        canvas.drawLine(divider+textYwidth,mHeight - textHeight  - divider,mWidth ,mHeight - textHeight - divider,lineYPaint);//X轴

        canvas.drawLine(0 + divider + textYwidth,mHeight - textHeight  - divider,0 + divider + textYwidth ,0,lineYPaint);//Y轴


        int[] yPosition = calYPoints(yStep,textHeight);

        Point[] linepoint = new Point[yPosition.length];

        for (int k= 0;k<yPosition.length;k++){
            linepoint[k] = new Point(k * xStep + divider + textYwidth,yPosition[k]- divider);
            //linepoint[k].set(k * xStep + divider + textYwidth,yPosition[k]- divider);
            canvas.drawCircle(k * xStep + divider + textYwidth, yPosition[k]  - divider, mPointRadius, pointPaint);
        }

        for(int m=0;m<linepoint.length-1;m++){
            canvas.drawLine(linepoint[m].x,linepoint[m].y,linepoint[m+1].x ,linepoint[m+1].y,lineYPaint);
        }

    }

    private int[] calYPoints(int ystep,int textheight){
        int[] mYpints =  new int[mDatas.length];
        float mYstart =  Float.valueOf(mYLabels[0]);
        for(int i =0;i<mDatas.length;i++){
            float mFloat =  Float.valueOf(mDatas[i]);
            float mResult = mFloat - mYstart;
            float divider = Float.valueOf(mYLabels[1])- Float.valueOf(mYLabels[0]);
            //int position = (int)(mResult/divider);
            mYpints[i] = mHeight - (int) (mResult * ystep/divider) -textheight;
        }
        return mYpints;
    }

    private String[] mDatas;

    public void setData(String[] data){
        mDatas = data;
    }

    public void setXLabels(String[] xlabels){
        mXLabels = xlabels;
    }


}
