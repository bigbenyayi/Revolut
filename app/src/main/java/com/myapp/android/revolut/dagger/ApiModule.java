package com.myapp.android.revolut.dagger;

import com.myapp.android.revolut.model.api.CurrencyAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public CurrencyAPI providesAPI() {
        return new CurrencyAPI.Factory().makeService();
    }

}