import android.app.Application;



import Util.Utils;

/**
 * Created by kai on 2018/3/1.
 */

public class TestApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.setApplication(TestApplication.this);
        //通联支付
        /*
        NetConstent.initEnvronment(Constant.ENVIRONMENT.IS_TEST, Constant.ENVIRONMENT.URL_VERSION, false);
        SPUtil.init(this);
        */
        //end
    }





}
