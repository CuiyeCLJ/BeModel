package android.bemodel.com.bemodel.adapter;

import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.bean.MessagesInfo;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017.07.24.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private Context context;
    private List<MessagesInfo> messagesItemInfoList;

    public MessagesAdapter(Context context, List<MessagesInfo> messagesInfoList) {
        this.context = context;
        this.messagesItemInfoList = messagesItemInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessagesInfo messages = messagesItemInfoList.get(position);
        holder.ivAvatar.setImageResource();
        holder.tvUsername.setText(messages.getUser());
    }

    @Override
    public int getItemCount() {
        return messagesItemInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAvatar;
        TextView tvUsername;
        TextView tvLocation;
        TextView tvDistance;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView)itemView.findViewById(R.id.iv_avatar_conversation);
            tvUsername = (TextView)itemView.findViewById(R.id.tv_username_conversation);
            tvLocation = (TextView)itemView.findViewById(R.id.tv_location_conversation);
            tvDistance = (TextView)itemView.findViewById(R.id.tv_distance_conversation);
        }
    }

}
