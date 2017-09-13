package android.bemodel.com.bemodel.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017.09.13.
 */

public class ImageLoader {

    ImageCache mImageCache = new MemoryCache();

    boolean isUseDiskCache = false;

    boolean isUseDoubleCache = false;

    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void setImageCache(ImageCache cache) {
        mImageCache = cache;
    }

    public void dispalyImage(final String imageUrl, final ImageView imageView) {

        Bitmap bitmap = mImageCache.get(imageUrl);

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        submitLoadRequest(imageUrl, imageView);
    }

    private void submitLoadRequest(final String imageUrl, final ImageView imageView) {
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(imageUrl)) {
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(imageUrl, bitmap);
            }
        });
    }

    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void useDiskCache(boolean useDiskCache) {
        isUseDiskCache = useDiskCache;
    }

    public void useDoubleCache(boolean useDoubleCache) {
        isUseDoubleCache = useDoubleCache;
    }

}
