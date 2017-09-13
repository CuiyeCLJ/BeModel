package android.bemodel.com.bemodel.util.image;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017.09.13.
 */

public interface ImageCache {
    public Bitmap get(String url);
    public void put(String url, Bitmap bitmap);
}
