package android.bemodel.com.bemodel.adapter;

import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.bean.MessagesInfo;
import android.bemodel.com.bemodel.widget.CircleImageView;
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
    private List<MessagesInfo> messagesList;

    public MessagesAdapter(List<MessagesInfo> messagesItemInfoList) {
        this.messagesList = messagesItemInfoList;
    }

    public MessagesAdapter(Context context, List<MessagesInfo> messagesInfoList) {
        this.context = context;
        this.messagesList = messagesInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessagesInfo messages = messagesList.get(position);
        holder.ivAvatar.setImageResource();
        holder.tvUsername.setText(messages.getUser());
        holder.tvLocation.setText();
        holder.tvDistance.setText();
    }

    /*
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(convertView);
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        MessagesInfo messages = messagesList.get(position);
        holder.ivAvatar;
        holder.tvUsername.setText();
        holder.tvLocation.setText();
        holder.tvDistance.setText();
        return convertView;
    }
     */

    public MessagesInfo getItem(final int position) {
        return messagesList.get(position);
    }

    public long getItemId(final int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivAvatar;
        TextView tvUsername;
        TextView tvLocation;
        TextView tvDistance;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (CircleImageView)itemView.findViewById(R.id.iv_avatar_conversation);
            tvUsername = (TextView)itemView.findViewById(R.id.tv_username_conversation);
            tvLocation = (TextView)itemView.findViewById(R.id.tv_location_conversation);
            tvDistance = (TextView)itemView.findViewById(R.id.tv_distance_conversation);
        }
    }

}
