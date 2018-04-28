package Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kai on 2018/4/18.
 */

public class Store {

    private static final String SP_RX = "sp_rx";
    private static final String TOKEN = "token";

    private SharedPreferences mStore;


    private Store() {
        mStore = Utils.getApplication().getSharedPreferences(SP_RX, Context.MODE_PRIVATE);
    }

    public static Store getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final Store INSTANCE = new Store();
    }

    public void setToken(String token) {
        mStore.edit().putString(TOKEN, token).apply();
    }

    public String getToken() {
        return mStore.getString(TOKEN, "");
    }

}
