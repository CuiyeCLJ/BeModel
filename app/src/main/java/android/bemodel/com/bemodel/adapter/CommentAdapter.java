package android.bemodel.com.bemodel.adapter;

import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.bean.CommentInfo;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017.07.28.
 */

public class CommentAdapter extends ArrayAdapter<CommentInfo> {

    private int resourceId;

    public CommentAdapter(@NonNull Context context, @LayoutRes int resourceId, @NonNull List<CommentInfo> objects) {
        super(context, resourceId, objects);
        this.resourceId = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CommentInfo commentInfo = getItem(position);

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.reviewerImage = (ImageView)view.findViewById(R.id.iv_reviewer_image);
            viewHolder.reviewerName = (TextView)view.findViewById(R.id.tv_reviewer_name);
            viewHolder.reviewTime = (TextView)view.findViewById(R.id.tv_review_time);
            viewHolder.reviewContent = (TextView)view.findViewById(R.id.tv_review_content);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.reviewerImage.setImageResource();
        viewHolder.reviewerName.setText(commentInfo.getReviewer().getUsername());
        viewHolder.reviewTime.setText(commentInfo.getCreatedAt());
        viewHolder.reviewContent.setText(commentInfo.getText());

        return view;
    }

    class ViewHolder {
        ImageView reviewerImage;
        TextView reviewerName;
        TextView reviewTime;
        TextView reviewContent;
    }
}
