package android.bemodel.com.bemodel.widget;

import android.app.Activity;
import android.bemodel.com.bemodel.R;
import android.net.wifi.SupplicantState;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017.10.10.
 */

public class TitleActivity extends Activity implements View.OnClickListener {

    private TextView mTitleTextView;
    private Button leftButton;
    private Button rightButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        super.setContentView(R.layout.my_title_bar);
        mTitleTextView = (TextView)findViewById(R.id.title_text);
        leftButton = (Button)findViewById(R.id.left_btn);
        rightButton = (Button)findViewById(R.id.right_btn);
    }

    /**
     * 是否显示左按钮
     * @param leftResId 文字
     * @param show  true为显示
     */
    public void showLeftView(int leftResId, boolean show) {
        if (leftButton != null) {
            if (show) {
                leftButton.setText(leftResId);
                leftButton.setVisibility(View.VISIBLE);
            } else {
                leftButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 是否显示右按钮
     * @param rightResId    文字
     * @param show  ture为显示
     */
    public void showRightView(int rightResId, boolean show) {
        if (rightButton != null) {
            if (show) {
                rightButton.setText(rightResId);
                rightButton.setVisibility(View.VISIBLE);
            } else {
                rightButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 设置标题内容
     * @param titleId
     */
    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }

    /**
     * 设置标题内容
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void onClick(View v) {

    }
}
