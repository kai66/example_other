package widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kai.testwebview.R;

import java.util.List;

public class StepView extends FrameLayout {
    public static final String TAG="StepView";
    /**步骤指示器*/
    private StepBar mStepBar;
    /**用来存放显示步骤名称的布局*/
    private FrameLayout mTitleGroup;
    /**所有步骤的标题*/
    private List<String> mStepTitles;

    private FrameLayout mImgGroup;


    public StepView(Context context) {
        super(context);
        init(context,null,0);
    }

    public StepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StepView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context mContext,AttributeSet attrs,int defStyleAttr){
        LayoutInflater.from(mContext).inflate(R.layout.step_view,this,true);
        mStepBar=(StepBar)this.findViewById(R.id.step_bar);
        mTitleGroup=(FrameLayout)this.findViewById(R.id.step_title);
        mImgGroup = (FrameLayout)this.findViewById(R.id.step_img);
        TypedArray array = mContext.obtainStyledAttributes(attrs,R.styleable.StepView,defStyleAttr,0);

        mStepBar.setLineHeight(array.getDimensionPixelOffset(R.styleable.StepView_lineheight, StepBar.DEFAULT_LINE_HEIGHT));
        mStepBar.setSmallRadius(array.getDimensionPixelOffset(R.styleable.StepView_smallradius, StepBar.DEFAULT_SMALL_CIRCLE_RADIUS));
        mStepBar.setLargeRadius(array.getDimensionPixelOffset(R.styleable.StepView_largeradius, StepBar.DEFAULT_LARGE_CIRCLE_RADIUS));
        mStepBar.setUnDoneColor(array.getColor(R.styleable.StepView_undonecolor, StepBar.COLOR_BAR_UNDONE));
        mStepBar.setDoneColor(array.getColor(R.styleable.StepView_donecolor, StepBar.COLOR_BAR_DONE));
        mStepBar.setTotalStep(array.getInteger(R.styleable.StepView_totalstep, 0));
        mStepBar.setCompleteStep(array.getInteger(R.styleable.StepView_completestep, 0));

        //在StepBar布局完成之后开始添加title
        mStepBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {
                initStepTitle();
                initStepImg();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    mStepBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    mStepBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
        array.recycle();

    }

    private void initStepTitle(){
        if(mStepTitles==null){
            return;
        }
        mTitleGroup.removeAllViews();

        if(mStepTitles.size()!=mStepBar.getTotalStep()){
            throw new IllegalStateException("设置的Title的个数和步骤数不一致！");
        }
        int stepNum=mStepBar.getTotalStep();
        for(int i=1;i<=stepNum;i++){
            final float stepPos=mStepBar.getPositionByStep(i);
            final TextView title=new TextView(this.getContext());
            title.setText(mStepTitles.get(i - 1));
            title.setSingleLine();
            title.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    title.setTranslationX(stepPos - title.getMeasuredWidth() / 2);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        title.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        title.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
            LayoutParams lp=new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            mTitleGroup.addView(title,lp);
        }
    }

    private void initStepImg(){
        if(mStepTitles==null){
            return;
        }
        mImgGroup.removeAllViews();

        if(mStepTitles.size()!=mStepBar.getTotalStep()){
            throw new IllegalStateException("设置的Title的个数和步骤数不一致！");
        }
        int stepNum=mStepBar.getTotalStep();

        for(int i=1;i<=stepNum;i++){
            final float stepPos=mStepBar.getPositionByStep(i);
            final ImageView imageView=new ImageView(this.getContext());
            imageView.setImageResource(R.mipmap.paobu);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    imageView.setTranslationX(stepPos - imageView.getMeasuredWidth() / 2);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            });
            LayoutParams lp=new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.width = dp2px(getContext(),24);
            lp.height = dp2px(getContext(),24);
            mImgGroup.addView(imageView,lp);
        }
    }

    /**
     * dp转换成px
     */
    private int dp2px(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    //1:为动画  2为跑步  3为error 4为stop
    public void setStepStatus(int status){
        int completeStep = mStepBar.getCompleteStep();
        int count = mImgGroup.getChildCount();
        for (int i=0;i<count;i++){
            if(i== completeStep){
                mImgGroup.getChildAt(i).setVisibility(View.VISIBLE);
                if(status == 1){
                    ((ImageView)mImgGroup.getChildAt(i)).setImageResource(R.drawable.step_img_anim);
                    AnimationDrawable animationDrawable = (AnimationDrawable) ((ImageView)mImgGroup.getChildAt(i)).getDrawable();
                    animationDrawable.start();
                }else if(status == 2){
                    ((ImageView)mImgGroup.getChildAt(i)).setImageResource(R.mipmap.paobu);
                }else if(status == 3){
                    ((ImageView)mImgGroup.getChildAt(i)).setImageResource(R.mipmap.step_error);
                }else if(status == 4){
                    ((ImageView)mImgGroup.getChildAt(i)).setImageResource(R.mipmap.step_stop);
                }else{
                    ((ImageView)mImgGroup.getChildAt(i)).setImageResource(R.drawable.step_img_anim);
                    AnimationDrawable animationDrawable = (AnimationDrawable) ((ImageView)mImgGroup.getChildAt(i)).getDrawable();
                    animationDrawable.start();
                }
            }else {
                mImgGroup.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }


    public void setStepTitles(List<String> stepTitles){
        this.mStepTitles=stepTitles;
    }

    public void setLineHeight(float mLineHeight) {
        mStepBar.setLineHeight(mLineHeight);
    }

    public void setSmallRadius(float mSmallRadius) {
        mStepBar.setSmallRadius(mSmallRadius);
    }

    public void setLargeRadius(float mLargeRadius) {
        mStepBar.setLargeRadius(mLargeRadius);
    }

    public void setUnDoneColor(int mUnDoneColor) {
        mStepBar.setUnDoneColor(mUnDoneColor);
    }

    public void setDoneColor(int mDoneColor) {
        mStepBar.setDoneColor(mDoneColor);
    }


    /**
     * 设置总的步骤数
     * @param mTotalStep
     */
    public void setTotalStep(int mTotalStep){
       mStepBar.setTotalStep(mTotalStep);
    }

    /**
     * 获取步骤总数
     * @return
     */
    public int getTotalStep(){
        return mStepBar.getTotalStep();
    }

    /**
     * 进入下一个步骤
     */
    public void nextStep(){
        mStepBar.nextStep();
    }

    /**
     * 重置步骤
     */
    public void reset(){
        mStepBar.reset();
    }


}
