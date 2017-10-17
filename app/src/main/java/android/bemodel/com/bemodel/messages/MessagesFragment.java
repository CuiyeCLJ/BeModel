package android.bemodel.com.bemodel.messages;

import android.bemodel.com.bemodel.adapter.MessagesAdapter;
import android.bemodel.com.bemodel.bean.MessagesInfo;
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

import java.util.ArrayList;
import java.util.List;

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
        recyclerView.setOnClickListener(this);
        recyclerView.setOnLongClickListener();
    }

    /**
     * 删除会话
     * @param messages
     */
    private void deleteConversation(MessagesInfo messages) {

    }

    private void loadData() {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        MessagesInfo messagesInfo = adapter
        return false;
    }
}
