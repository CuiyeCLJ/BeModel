package android.bemodel.com.bemodel.messages;

import android.bemodel.com.bemodel.adapter.MessagesAdapter;
import android.bemodel.com.bemodel.bean.ChatUser;
import android.bemodel.com.bemodel.bean.MessagesInfo;
import android.bemodel.com.bemodel.widget.dialog.DialogTips;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.TreeTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.im.db.BmobDB;
import cn.bmob.v3.b.V;

public class MessagesFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener {

    private View view;

    RecyclerView recyclerView;

    MessagesAdapter adapter;

    private List<MessagesInfo> messagesInfoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_messages, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessagesAdapter(messagesInfoList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }


    private void initViews() {
        recyclerView.setOnClickListener((View.OnClickListener) this);
        recyclerView.setOnLongClickListener((View.OnLongClickListener) this);
    }

    private void loadData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MessagesInfo messages = adapter.getItem(position);
        //重置未读信息
        BmobDB.create(getActivity()).resetUnread(messages.getTargetid());
        //组装聊天对象
        ChatUser chatUser = new ChatUser();
        chatUser.setAvatar(messages.getAvatar());
        chatUser.setNick(messages.getNick());
        chatUser.setUsername(messages.getUserName());
        chatUser.setObjectId(messages.getTargetid());
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("user", chatUser);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        MessagesInfo messages = adapter.getItem(position);
        showDeleteDialog(messages, position);
        return false;
    }

    public void showDeleteDialog(final MessagesInfo messages, final int position) {
        DialogTips dialog = new DialogTips(getActivity(), messages,getUserName(), "删除会话", "确定", true, true);
        //设置成功事件
        dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteConversation(messages, position);
            }
        });
        //显示确认对话框
        dialog.show();
        dialog = null;
    }

    /**
     * 删除会话
     * @param messages
     */
    private void deleteConversation(MessagesInfo messages, int position) {
        adapter.removeItem(position);
        BmobDB.create(getActivity()).deleteRecent(messages.getTargetid());
    }
}
