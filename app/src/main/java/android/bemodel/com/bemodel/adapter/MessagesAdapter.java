package android.bemodel.com.bemodel.adapter;

import android.bemodel.com.bemodel.bean.MessagesInfo;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017.07.24.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.BaseViewHolder> {

    private Context context;
    private List<MessagesInfo> messagesItemInfoList;

    public MessagesAdapter(Context context, List<MessagesInfo> messagesInfoList) {
        this.context = context;
        this.messagesItemInfoList = messagesItemInfoList;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

}
