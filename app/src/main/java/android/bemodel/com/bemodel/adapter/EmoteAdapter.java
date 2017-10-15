package android.bemodel.com.bemodel.adapter;

import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.base.BaseArrayListAdapter;
import android.bemodel.com.bemodel.bean.FaceText;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2017.10.15.
 */

public class EmoteAdapter extends BaseArrayListAdapter {

    public EmoteAdapter(Context mContext, List<FaceText> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_face_text, null);
            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.iv_face_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        FaceText faceText = (FaceText)getItem(position);
        String key = faceText.text.substring(1);
        Drawable drawable = mContext.getResources().getDrawable(mContext.getResources().getIdentifier(key, "drawable", mContext.getPackageName()));
        viewHolder.ivImage.setImageDrawable(drawable);
        return convertView;
    }

    class ViewHolder {
        ImageView ivImage;
    }
}
