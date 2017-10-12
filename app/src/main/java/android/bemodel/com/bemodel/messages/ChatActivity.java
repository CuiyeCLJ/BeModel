package android.bemodel.com.bemodel.messages;

import android.bemodel.com.bemodel.adapter.MessageChatAdapter;
import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.bean.ChatUser;
import android.bemodel.com.bemodel.widget.EmoticonsEditText;
import android.bemodel.com.bemodel.widget.xlist.XListView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.EventListener;

import butterknife.BindView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobRecordManager;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;

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
    BmobChatManager manager;

    MessageChatAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        manager = BmobChatManager.getInstance(this);
        MsgPagerNum = 0;
        //组装聊天对象
        targetUser = (ChatUser)getIntent().getSerializableExtra("user");
        targetId = targetUser.getObjectId();
        //注册广播接收器
        initNewMessageBroadCast();

    }


    NewBroadcastReceiver  receiver;
    private void initNewMessageBroadCast() {
        // 注册接收消息广播
        receiver = new NewBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_NEW_MESSAGE);
        //设置广播的优先级别大于Mainacitivity,这样如果消息来的时候正好在chat页面，直接显示消息，而不是提示消息未读
        intentFilter.setPriority(5);
        registerReceiver(receiver, intentFilter);
    }

    private class NewBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String from = intent.getStringExtra("fromId");
            String msgId = intent.getStringExtra("msgId");
            String msgTime = intent.getStringExtra("msgTime");
            // 收到这个广播的时候，message已经在消息表中，可直接获取
            if (!TextUtils.isEmpty(from) && !TextUtils.isEmpty(msgId) && !TextUtils.isEmpty(msgTime)) {
                BmobMsg msg = BmobChatManager.getInstance(ChatActivity.this).getMessage(msgId, msgTime);
                if (!from.equals(targetId)) {   // 如果不是当前正在聊天对象的消息，不处理
                    return;
                }


            }
        }
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
