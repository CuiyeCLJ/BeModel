package android.bemodel.com.bemodel.adapter;

import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.activity.MainActivity;
import android.bemodel.com.bemodel.db.MessagesInfo;
import android.bemodel.com.bemodel.db.ModelCircleInfo;
import android.bemodel.com.bemodel.db.UserInfo;
import android.bemodel.com.bemodel.util.Location;
import android.bemodel.com.bemodel.util.MyUtils;
import android.bemodel.com.bemodel.util.loader.ImageLoader;
import android.bemodel.com.bemodel.view.CommentActivity;
import android.bemodel.com.bemodel.view.LoginActivity;
import android.bemodel.com.bemodel.view.ModelCircleFragment;
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

import com.baidu.mapapi.model.LatLng;

import java.io.PrintWriter;
import java.util.List;

import cn.bmob.v3.BmobUser;

import static android.bemodel.com.bemodel.util.Utility.getLongDistance;
import static android.bemodel.com.bemodel.util.Utility.getTimeDifference;
import static com.baidu.mapapi.model.CoordUtil.getDistance;

/**
 * Created by Administrator on 2017.07.23.
 */

public class ModelCircleAdapter extends RecyclerView.Adapter<ModelCircleAdapter.ViewHolder> {

    private List<ModelCircleInfo> mModelCircleInfoList;
    private Context mContext;
    private UserInfo user;
    private ImageLoader mImageLoader;
    private int screenWidth;

    public ModelCircleAdapter(Context mContext, List<ModelCircleInfo> mModelCircleInfoList) {
        this.mModelCircleInfoList = mModelCircleInfoList;
        this.mContext = mContext;
        this.user = (UserInfo) BmobUser.getCurrentUser();
        this.screenWidth = MyUtils.getScreenMetrics(mContext).widthPixels;
        this.mImageLoader = ImageLoader.build(mContext);

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
                BmobUser user = BmobUser.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(mContext, CommentActivity.class);
                    intent.putExtra("modelCircleInfo_data", modelCircleInfo);
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }

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

        holder.userName.setText(modelCircleInfo.getUser().getUsername());

        holder.time.setText(getTimeDifference(modelCircleInfo.getCreatedAt()));

//        LatLng start = new LatLng(user.getGeo().getLatitude(), user.getGeo().getLongitude());
//        LatLng end = new LatLng(modelCircleInfo.getGeo().getLatitude(), modelCircleInfo.getGeo().getLongitude());
//        int distance = (int) Location.getDistance(start, end);
        holder.distance.setText(getLongDistance(user.getGeo().getLongitude(), user.getGeo().getLatitude(), modelCircleInfo.getGeo().getLongitude(), modelCircleInfo.getGeo().getLatitude()));
        ImageView imageView = holder.photograph;
        final String tag = (String)imageView.getTag();
        mImageLoader.bindBitmap(modelCircleInfo.getThumbnailPic(), imageView, screenWidth, 180);
//        holder.photograph.setImageResource();

        holder.location.setText(modelCircleInfo.getAddress());

        holder.describe.setText(modelCircleInfo.getText());

        holder.comment.setText("评论(" + modelCircleInfo.getCommentsCount() + ")");
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
