package com.myapp.android.revolut.Dagger;

import com.myapp.android.revolut.RevolutApplication;
import com.myapp.android.revolut.Model.Repository;
import com.myapp.android.revolut.Presenter.LastCurrencyPresenter;
import com.myapp.android.revolut.Services.PeriodicalService;
import com.myapp.android.revolut.View.Activities.LastCurrencyActivity;
import com.myapp.android.revolut.View.Adapter.LastCurrencyAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                ContextModule.class,
                ApiModule.class
        }
)
public interface Injector {

    void inject(RevolutApplication keeperApplication);

    void inject(LastCurrencyActivity lastCurrencyActivity);

    void inject(LastCurrencyPresenter lastCurrencyPresenter);

    void inject(Repository repository);

    void inject(LastCurrencyAdapter adapter);

    void inject(PeriodicalService periodicalService);

}