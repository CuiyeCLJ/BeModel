package android.bemodel.com.bemodel.adapter;

import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.db.MessagesItemMsg;
import android.bemodel.com.bemodel.db.ModelCircleInfo;
import android.bemodel.com.bemodel.view.ModelCircleFragment;
import android.bemodel.com.bemodel.view.ReviewActivity;
import android.content.Context;
import android.content.Intent;
import android.os.HardwarePropertiesManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Administrator on 2017.07.23.
 */

public class ModelCircleAdapter extends RecyclerView.Adapter<ModelCircleAdapter.ViewHolder> {

    private List<ModelCircleInfo> mModelCircleInfoList;
    private Context mContext;

    public ModelCircleAdapter(Context mContext, List<ModelCircleInfo> mModelCircleInfoList) {
        this.mModelCircleInfoList = mModelCircleInfoList;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_line, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        //注册“评论”按钮的点击事件
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                ModelCircleInfo modelCircleInfo = mModelCircleInfoList.get(position);
                Intent intent = new Intent(mContext, ReviewActivity.class);
                mContext.startActivity(intent);

            }
        });
        //注册“私聊”按钮的点击事件
        viewHolder.privateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                ModelCircleInfo modelCircleInfo = mModelCircleInfoList.get(position);


            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelCircleInfo modelCircleInfo = mModelCircleInfoList.get(position);

    }

    @Override
    public int getItemCount() {
        return mModelCircleInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View modelCircleInfoView;

        ImageView headImage;
        TextView userName;
        TextView location;
        TextView time;
        TextView distance;
        ImageView photograph;
        TextView describe;
        Button comment;
        Button privateChat;

        public ViewHolder(View itemView) {
            super(itemView);
            modelCircleInfoView = itemView;
            headImage = (ImageView)itemView.findViewById(R.id.iv_item_head_sculpture);
            userName = (TextView)itemView.findViewById(R.id.tv_item_username);
            location = (TextView)itemView.findViewById(R.id.tv_item_location);
            time = (TextView)itemView.findViewById(R.id.tv_item_time);
            distance = (TextView)itemView.findViewById(R.id.tv_item_distance);
            photograph = (ImageView)itemView.findViewById(R.id.item_photograph);
            describe = (TextView)itemView.findViewById(R.id.tv_describe);
            comment = (Button)itemView.findViewById(R.id.bt_comment);
            privateChat = (Button)itemView.findViewById(R.id.bt_private_chat);
        }
    }
}
