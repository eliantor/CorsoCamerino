package com.baasbox.samples;

import android.app.Application;
import com.baasbox.android.BaasBox;

/**
 * Created by eto on 12/04/14.
 */
public class App extends Application {
    private BaasBox box;
    private static App self;

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        BaasBox.Builder builder =
                new BaasBox.Builder(this);

        box =builder.setPort(9000) // 80
                .setApiDomain("192.168.56.1") //indirizzo macchina
                .setAppCode("1234567890") // app code
                .setAuthentication(BaasBox.Config.AuthType.SESSION_TOKEN)
                .init();
    }

    public static BaasBox getBox(){
        return self.box;
    }
}
