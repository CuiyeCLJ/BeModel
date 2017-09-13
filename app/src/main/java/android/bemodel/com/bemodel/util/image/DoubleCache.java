package android.bemodel.com.bemodel.util.image;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017.09.13.
 */

public class DoubleCache implements ImageCache {

    MemoryCache mMemoryCache = new MemoryCache();
    DiskCache mDiskCache = new DiskCache();

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
        mDiskCache.put(url, bitmap);
    }
}
