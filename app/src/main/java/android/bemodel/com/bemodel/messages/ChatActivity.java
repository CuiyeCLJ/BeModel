package android.bemodel.com.bemodel.messages;

import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.bean.ChatUser;
import android.bemodel.com.bemodel.widget.EmoticonsEditText;
import android.bemodel.com.bemodel.widget.xlist.XListView;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.EventListener;

import butterknife.BindView;

public class ChatActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, EventListener {

    @BindView(R.id.mListView)
    XListView mXListView;

    @BindView(R.id.btn_chat_add)
    Button btn_chat_add;
    @BindView(R.id.btn_chat_emo)
    Button btn_chat_emo;
    @BindView(R.id.btn_chat_keyboard)
    Button btn_chat_keyboard;
    @BindView(R.id.btn_chat_voice)
    Button btn_chat_voice;
    @BindView(R.id.btn_chat_send)
    Button btn_chat_send;
    @BindView(R.id.btn_speak)
    Button btn_speak;

    @BindView(R.id.layout_more)
    LinearLayout layout_more;
    @BindView(R.id.layout_emo)
    LinearLayout layout_emo;
    @BindView(R.id.layout_add)
    LinearLayout layout_add;

    @BindView(R.id.edit_user_comment)
    EmoticonsEditText edit_user_comment;

    @BindView(R.id.tv_picture)
    TextView tv_picture;
    @BindView(R.id.tv_camera)
    TextView tv_camera;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.pager_emo)
    ViewPager pager_emo;


    String targetId = "";

    ChatUser targetUser;

    private static int MsgPagerNum;

    // 语音有关
    RelativeLayout layout_record;
    TextView tv_voice_tips;
    ImageView iv_record;

    private Drawable[] drawable_Anims;// 话筒动画

    BmobRecordManager recordManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {

    }
}
