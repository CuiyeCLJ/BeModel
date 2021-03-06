package android.bemodel.com.bemodel.adapter;

import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.bean.ModelCircleInfo;
import android.bemodel.com.bemodel.bean.UserInfo;
import android.bemodel.com.bemodel.util.MyUtils;
import android.bemodel.com.bemodel.util.TimeUtils;
import android.bemodel.com.bemodel.util.loader.ImageLoader;
import android.bemodel.com.bemodel.ui.CommentActivity;
import android.bemodel.com.bemodel.ui.LoginActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.bemodel.com.bemodel.util.Utility.getLongDistance;

/**
 * Created by Administrator on 2017.07.23.
 */

public class ModelCircleAdapter extends RecyclerView.Adapter<ModelCircleAdapter.ViewHolder> {

    private List<ModelCircleInfo> mModelCircleInfoList;
    private Context mContext;
    private UserInfo user;
    private ImageLoader mImageLoader;
    private int screenWidth;
    public OnItemClickListener itemClickListener;
    public OnLoadMoreListener onLoadMoreListener;
    private android.bemodel.com.bemodel.util.image.ImageLoader imageLoader;
    private boolean isLoading = false;

    public ModelCircleAdapter(Context mContext, List<ModelCircleInfo> mModelCircleInfoList, RecyclerView recyclerView) {
        this.mModelCircleInfoList = mModelCircleInfoList;
        this.mContext = mContext;
        this.user = BmobUser.getCurrentUser(mContext, UserInfo.class);
        this.screenWidth = MyUtils.getScreenMetrics(mContext).widthPixels;
        this.mImageLoader = ImageLoader.build(mContext);
        this.imageLoader = new android.bemodel.com.bemodel.util.image.ImageLoader();
        init(recyclerView);
    }

    private void init(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int itemCount = linearLayoutManager.getItemCount();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                //dy > 0 表示上滑
                if (!isLoading && dy > 0 && lastVisibleItemPosition >= itemCount - 1) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }

    public void setModelCircleInfoList(List<ModelCircleInfo> mModelCircleInfoList) {
        this.mModelCircleInfoList = mModelCircleInfoList;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
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
                BmobUser user = BmobUser.getCurrentUser(mContext, UserInfo.class);
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
//        ImageView imageView = holder.photograph;
//        final String tag = (String)imageView.getTag();
//        mImageLoader.bindBitmap(modelCircleInfo.getThumbnailPic(), imageView, screenWidth, 180);
        imageLoader.dispalyImage(user.getAvatar(), holder.headImage);
        holder.userName.setText(modelCircleInfo.getUser().getUsername());
        holder.location.setText(modelCircleInfo.getAddress());
        holder.time.setText(TimeUtils.getDescriptionTimeFromTimestamp(TimeUtils.stringToLong(modelCircleInfo.getCreatedAt(), "yyyy-MM-dd HH:mm:ss")));
        holder.distance.setText(getLongDistance(user.getGeo().getLongitude(), user.getGeo().getLatitude(), modelCircleInfo.getGeo().getLongitude(), modelCircleInfo.getGeo().getLatitude()));
        mImageLoader.bindBitmap(modelCircleInfo.getBmiddlePic(), holder.photograph);
        holder.describe.setText(modelCircleInfo.getText());
        holder.comment.setText("评论(" + modelCircleInfo.getCommentsCount() + ")");
    }



    @Override
    public int getItemCount() {
        return mModelCircleInfoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View modelCircleInfoView;

        CircleImageView headImage;
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
            headImage = (CircleImageView) itemView.findViewById(R.id.iv_item_head_sculpture);
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
