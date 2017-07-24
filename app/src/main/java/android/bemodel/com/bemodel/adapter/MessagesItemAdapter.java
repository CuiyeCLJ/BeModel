package android.bemodel.com.bemodel.adapter;

import android.bemodel.com.bemodel.util.MessagesItemMsg;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017.07.24.
 */

public class MessagesItemAdapter extends RecyclerView.Adapter<MessagesItemAdapter.BaseViewHolder> {

    private Context context;
    private List<MessagesItemMsg> messagesItemMsgList;

    public MessagesItemAdapter(Context context, List<MessagesItemMsg> messagesItemMsgList) {
        this.context = context;
        this.messagesItemMsgList = messagesItemMsgList;
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
