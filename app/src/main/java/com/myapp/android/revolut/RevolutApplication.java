package com.myapp.android.revolut;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.hawk.Hawk;
import com.myapp.android.revolut.dagger.ContextModule;
import com.myapp.android.revolut.dagger.DaggerInjector;
import com.myapp.android.revolut.dagger.Injector;
import com.myapp.android.revolut.model.db.HawkAutoValueParser;
import com.myapp.android.revolut.util.Logger;

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