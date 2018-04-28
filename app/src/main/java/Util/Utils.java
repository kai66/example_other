package Util;

import android.app.Application;

/**
 * Created by kai on 2018/4/18.
 */

public class Utils {

   private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static void setApplication(Application app) {
        application = app;
    }
}
