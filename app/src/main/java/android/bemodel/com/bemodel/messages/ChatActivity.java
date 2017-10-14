package android.bemodel.com.bemodel.messages;

import android.app.Activity;
import android.bemodel.com.bemodel.adapter.MessageChatAdapter;
import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.bean.ChatUser;
import android.bemodel.com.bemodel.bean.FaceText;
import android.bemodel.com.bemodel.util.CommonUtils;
import android.bemodel.com.bemodel.util.FaceTextUtils;
import android.bemodel.com.bemodel.widget.EmoticonsEditText;
import android.bemodel.com.bemodel.widget.MySecondTitlebar;
import android.bemodel.com.bemodel.widget.xlist.XListView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import butterknife.BindView;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobRecordManager;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.inteface.UploadListener;
import cn.bmob.im.util.BmobLog;

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

    @BindView(R.id.vp_emo)
    ViewPager pager_emo;


    String targetId = "";

    ChatUser targetUser;

    private static int MsgPagerNum;

    private MySecondTitlebar mySecondTitlebar;

    // 语音有关
    RelativeLayout layout_record;
    TextView tv_voice_tips;
    ImageView iv_record;

    private Drawable[] drawable_Anims;// 话筒动画
    BmobRecordManager recordManager;
    BmobChatManager manager;
    MessageChatAdapter mAdapter;
    Toast toast;


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
        mySecondTitlebar = (MySecondTitlebar)findViewById(R.id.udtb_chat);
        mySecondTitlebar.setTitleText(targetUser.getUsername());
        initButtomView();
        initXListView();
        initVoiceView();
    }



    private void initButtomView() {
        btn_chat_voice.setOnClickListener(this);
        btn_chat_keyboard.setOnClickListener(this);
        btn_chat_add.setOnClickListener(this);
        btn_chat_emo.setOnClickListener(this);
        btn_chat_send.setOnClickListener(this);
        initAddView();
        initEmoView();

        edit_user_comment.setOnClickListener(this);
        edit_user_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!TextUtils.isEmpty(s)) {    //==false 有字
                    btn_chat_send.setVisibility(View.VISIBLE);
                    btn_chat_keyboard.setVisibility(View.GONE);
//                    btn_chat_voice.setVisibility(View.GONE);
                    btn_chat_add.setVisibility(View.GONE);
                } else {
                    if (btn_chat_add.getVisibility() != View.VISIBLE) {
                        btn_chat_add.setVisibility(View.VISIBLE);
                        btn_chat_send.setVisibility(View.GONE);
                        btn_chat_keyboard.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initAddView() {
        tv_picture.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
    }

//    List<FaceText> emos;
//    private void initEmoView() {
//        emos = FaceTextUtils.faceTexts;
//
//        List<View> views = new ArrayList<View>();
//        for (int i = 0; i< 2; i++) {
//            views.add(getGridView(i));
//        }
//        pager_emo.setAdapter(new EmoViewPagerAdapter(views));
//    }

    /**
     *
     * 初始化语音布局
     */
    private void initVoiceView() {
        btn_speak.setOnTouchListener(new VoiceTouchListen());
        initVoiceAnimRes();
        initRecordManager();
    }

    /**
     * 长按说话
     * @ClassName: VoiceTouchListen
     */
    class VoiceTouchListen implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!CommonUtils.checkSdCard()) {
                        showToast("发送语音需要sdcard支持！");
                        return false;
                    }
                    try {
                        v.setPressed(true);
                        layout_record.setVisibility(View.VISIBLE);
                        tv_voice_tips.setText(getString(R.string.voice_cancel_tips));
                        // 开始录音
                        recordManager.startRecording(targetId);
                    } catch (Exception e) {
                    }
                    return true;
                case MotionEvent.ACTION_MOVE: {
                    if (event.getY() < 0) {
                        tv_voice_tips
                                .setText(getString(R.string.voice_cancel_tips));
                        tv_voice_tips.setTextColor(Color.RED);
                    } else {
                        tv_voice_tips.setText(getString(R.string.voice_up_tips));
                        tv_voice_tips.setTextColor(Color.WHITE);
                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                    v.setPressed(false);
                    layout_record.setVisibility(View.INVISIBLE);
                    try {
                        if (event.getY() < 0) {// 放弃录音
                            recordManager.cancelRecording();
                            BmobLog.i("voice", "放弃发送语音");
                        } else {
                            int recordTime = recordManager.stopRecording();
                            if (recordTime > 1) {
                                // 发送语音文件
                                BmobLog.i("voice", "发送语音");
                                sendVoiceMessage(recordManager.getRecordFilePath(targetId), recordTime);
                            } else {// 录音时间过短，则提示录音过短的提示
                                layout_record.setVisibility(View.GONE);
                                showShortToast().show();
                            }
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    return true;
                default:
                    return false;
            }
        }
    }

    private void sendVoiceMessage(String local, int length) {
        manager.sendVoiceMessage(targetUser, local, length, new UploadListener() {
            @Override
            public void onStart(BmobMsg bmobMsg) {
                refreshMessage(bmobMsg);
            }

            @Override
            public void onSuccess() {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("上传语音失败 -->s: " + s);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 显示录音时间过短的Toast
     * @return
     */
    private Toast showShortToast() {
        if (toast == null) {
            toast = new Toast(this);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.include_chat_voice_short, null);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(50);
        return toast;
    }

    /**
     * 刷新信息界面
     * @param bmobMsg
     */
    private void refreshMessage(BmobMsg bmobMsg) {
        //更新界面
        mAdapter.add(bmobMsg);
        mXListView.setSelection(mAdapter.getCount() - 1);
        edit_user_comment.setText("");
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

        switch (v.getId()) {
            case R.id.btn_chat_voice:       //触动语音按钮
                btn_chat_voice.setVisibility(View.GONE);
                btn_chat_keyboard.setVisibility(View.VISIBLE);
                edit_user_comment.setVisibility(View.GONE);
                btn_speak.setVisibility(View.VISIBLE);
                hideSoftInputView();    //隐藏软键盘
                break;

            case R.id.btn_chat_keyboard:    //触动键盘按钮
                showEditState(false);
                break;

            case R.id.btn_chat_emo:         //触动笑脸按钮
                if (layout_more.getVisibility() == View.GONE) {
                    showEditState(true);
                } else {
                    if (layout_add.getVisibility() == View.VISIBLE) {
                        layout_add.setVisibility(View.GONE);
                        layout_emo.setVisibility(View.VISIBLE);
                    } else {
                        layout_more.setVisibility(View.GONE);
                    }
                }
                break;

            case R.id.btn_chat_add:        //触动加号按钮
                if (layout_more.getVisibility() == View.GONE) {
                    layout_more.setVisibility(View.VISIBLE);
                    layout_add.setVisibility(View.VISIBLE);
                    layout_emo.setVisibility(View.GONE);
                    hideSoftInputView();//隐藏软键盘
                } else {
                    if (layout_emo.getVisibility() == View.VISIBLE) {
                        layout_emo.setVisibility(View.GONE);
                        layout_add.setVisibility(View.VISIBLE);
                    } else {
                        layout_more.setVisibility(View.GONE);
                    }
                }
                break;

            case R.id.btn_chat_send:        //触动发送按钮
                final String msg = edit_user_comment.getText().toString();
                if (msg.equals("")) {
                    showToast("请输入发送信息！");
                    return;
                }
                boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
                if (!isNetConnected) {
                    showToast(R.string.network_tips);
                }
                //组装BmobMsg对象
                BmobMsg message = BmobMsg.createTagSendMsg(this, targetId, msg);
                message.setExtra("Bmob");
                //默认发送完成，将数据保存到本地消息表和最近会话表中
                manager.sendTextMessage(targetUser, message);
                //刷新信息界面
                refreshMessage(message);
                break;

            case R.id.edit_user_comment:    //触动点击文本输入框
                mXListView.setSelection(mXListView.getCount() - 1);
                if (layout_more.getVisibility() == View.VISIBLE) {
                    layout_add.setVisibility(View.GONE);
                    layout_emo.setVisibility(View.GONE);
                    layout_more.setVisibility(View.GONE);
                }
                showSoftInputView();
                break;

            case R.id.tv_camera:            //触动拍照按钮
                selectImageFromCamera();
                break;

            case R.id.tv_picture:           //触动图片按钮
                selectImageFromLocal();
                break;

            case R.id.tv_location:          //触动位置按钮
                selectLocationFromMap();
                break;

            case R.id.tv_phone:             //触动电话按钮
                selectcommunicateByphone();
                break;

            default:
                break;

        }
    }
    //----------------------------------------------------------------------------------------------------------------------------------
    /**
     *
     * @param isEmo 是否点击笑脸，false表示没点击笑脸，true表示点击笑脸
     */
    private void showEditState(boolean isEmo) {
        edit_user_comment.setVisibility(View.VISIBLE);
        btn_chat_keyboard.setVisibility(View.GONE);
        btn_chat_voice.setVisibility(View.VISIBLE);
        btn_speak.setVisibility(View.GONE);
        edit_user_comment.requestFocus();
        if (isEmo) {    //为true 表示点击笑脸
            layout_more.setVisibility(View.VISIBLE);
            layout_emo.setVisibility(View.VISIBLE);
            layout_add.setVisibility(View.GONE);
            hideSoftInputView();
        } else {    //为false 表示没点击笑脸
            layout_more.setVisibility(View.GONE);
            showSoftInputView();
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInputView() {
        if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(edit_user_comment, 0);
        }
    }
    //------------------------------------------------------------------------------------------------------------------------------------

}
