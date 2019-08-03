package com.myapp.android.revolut.Dagger;

import com.myapp.android.revolut.Model.api.CurrencyAPI;

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