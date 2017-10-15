package android.bemodel.com.bemodel.base;

import android.bemodel.com.bemodel.bean.FaceText;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017.10.15.
 */

public class BaseArrayListAdapter extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected List<FaceText> mDatas = new ArrayList<FaceText>();

    public BaseArrayListAdapter(Context mContext, List<FaceText> mDatas) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        if (mDatas != null && mDatas.size() > 0) {
            this.mDatas = mDatas;
        }
    }

    public BaseArrayListAdapter(Context context, FaceText... datas) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        if (datas != null && datas.length > 0) {
            mDatas = Arrays.asList(datas);
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
