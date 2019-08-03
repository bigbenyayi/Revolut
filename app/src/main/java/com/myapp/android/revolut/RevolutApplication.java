package com.myapp.android.revolut;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.hawk.Hawk;
import com.myapp.android.revolut.Dagger.ContextModule;
import com.myapp.android.revolut.Dagger.DaggerInjector;
import com.myapp.android.revolut.Dagger.Injector;
import com.myapp.android.revolut.Model.db.HawkAutoValueParser;
import com.myapp.android.revolut.Utils.Logger;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class RevolutApplication extends Application {

    private Logger LOGGER = Logger.build(RevolutApplication.class);

    public static Injector getInjector() {
        return injector;
    }

    private static Injector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        LOGGER.log("onCreate");
        injector = DaggerInjector
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);

        Hawk.init(this)
                .setParser(new HawkAutoValueParser())
                .setLogInterceptor(LOGGER::log)
                .build();

    }

}