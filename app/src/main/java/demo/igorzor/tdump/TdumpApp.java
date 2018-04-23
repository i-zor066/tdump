package demo.igorzor.tdump;

import android.app.Application;

import demo.igorzor.tdump.di.AppComponent;
import demo.igorzor.tdump.di.DaggerAppComponent;
import demo.igorzor.tdump.di.DataSourceModule;


public class TdumpApp extends Application {
    private static final String TAG = "TdumpApp";

    private static TdumpApp sSharedInstance;
    private AppComponent mAppComponent;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static TdumpApp getSharedInstance() {
        return sSharedInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSharedInstance = this;
        mAppComponent = DaggerAppComponent.builder()
                .dataSourceModule(new DataSourceModule())
                .build();
    }



}
