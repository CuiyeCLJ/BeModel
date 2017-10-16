package android.bemodel.com.bemodel.util;

import android.app.Application;
import android.icu.util.IslamicCalendar;

/**
 * Created by Zheng Lifu on 2017.10.16.
 */

public final class Utils {

    private static Application sApplication;

    public static void init(final Application application) {
        Utils.sApplication = application;
        application.registerActivityLifecycleCallbacks(mCallbacks);
    }

    public static Application getApp() {
        if (sApplication != null){
            return sApplication;
        }
    }
}
