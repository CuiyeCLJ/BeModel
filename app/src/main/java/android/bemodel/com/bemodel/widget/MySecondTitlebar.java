package android.bemodel.com.bemodel.widget;

import android.bemodel.com.bemodel.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017.10.13.
 */

public class MySecondTitlebar extends RelativeLayout {

    private TextView mTitleView;
    private Button backButton;

    public MySecondTitlebar(Context context) {
        super(context);
    }

    public MySecondTitlebar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySecondTitlebar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        backButton = (Button) findViewById(R.id.btn_backward);
        mTitleView = (TextView) findViewById(R.id.tv_titlename);
    }

    public void setTitleText(String text) {
        mTitleView.setVisibility(View.VISIBLE);
        mTitleView.setText(text);
    }
}
