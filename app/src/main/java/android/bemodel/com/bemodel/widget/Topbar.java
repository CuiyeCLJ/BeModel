package android.bemodel.com.bemodel.widget;

import android.bemodel.com.bemodel.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017.10.13.
 */

public class Topbar extends RelativeLayout {

    private Button mLeftButton;
    private Button mRightButton;
    private TextView mTitleView;

    // 布局属性，用来控制组件元素在ViewGroup中的位置
    private LayoutParams mLeftParams;
    private LayoutParams mTitlepParams;
    private LayoutParams mRightParams;

    // 左按钮的属性值，即我们在attrs.xml文件中定义的属性
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;

    // 右按钮的属性值，即我们在attrs.xml文件中定义的属性
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;

    // 标题的属性值，即我们在attrs.xml文件中定义的属性
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mTitle;

    private titlebarClickListener mListener;

    public Topbar(Context context) {
        super(context);
    }

    public Topbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(0xFFF59563);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

        // 从TypedArray中取出对应的值来为要设置的属性赋值
        mLeftTextColor = typedArray.getColor(R.styleable.Topbar_leftTextColor, 0);
        mLeftBackground = typedArray.getDrawable(R.styleable.Topbar_leftBackground);
        mLeftText = typedArray.getString(R.styleable.Topbar_leftText);

        mRightTextColor = typedArray.getColor(R.styleable.Topbar_rightTextColor, 0);
        mRightBackground = typedArray.getDrawable(R.styleable.Topbar_rightBackground);
        mRightText = typedArray.getString(R.styleable.Topbar_rightText);

        mTitleTextSize = typedArray.getDimension(R.styleable.Topbar_titleTextSize, 10);
        mTitleTextColor = typedArray.getColor(R.styleable.Topbar_titleTextColor, 0);
        mTitle = typedArray.getString(R.styleable.Topbar_title);

        typedArray.recycle();

        mLeftButton = new Button(context);
        mRightButton = new Button(context);
        mTitleView = new TextView(context);

        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setText(mLeftText);

        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);
        mRightButton.setText(mRightText);

        mTitleView.setText(mTitle);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setGravity(Gravity.CENTER);

        mLeftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);

        addView(mLeftButton, mLeftParams);

        mRightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);

        addView(mRightButton, mRightParams);

        mTitlepParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mTitlepParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);

        addView(mTitleView, mTitlepParams);

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.leftClick();
            }
        });

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.rightClick();
            }
        });

    }

    public void setOnTitlebarClickListener(titlebarClickListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 设置按钮的显示与否 通过id区分按钮，flag区分是否显示
     * @param id
     * @param flag
     */
    public void setButtonVisable(int id, boolean flag) {
        if (flag) {
            if (id == 0) {
                mLeftButton.setVisibility(View.VISIBLE);
            } else {
                mRightButton.setVisibility(View.VISIBLE);
            }
        } else {
            if (id == 0) {
                mLeftButton.setVisibility(View.GONE);
            } else {
                mRightButton.setVisibility(View.GONE);
            }
        }
    }

    public interface titlebarClickListener {
        // 左按钮点击事件
        void leftClick();
        // 右按钮点击事件
        void rightClick();
    }

}
