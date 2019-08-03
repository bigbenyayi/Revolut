package com.myapp.android.revolut.dagger;

import com.myapp.android.revolut.RevolutApplication;
import com.myapp.android.revolut.model.Repository;
import com.myapp.android.revolut.presenter.LastCurrencyPresenter;
import com.myapp.android.revolut.services.PeriodicalService;
import com.myapp.android.revolut.view.activities.LastCurrencyActivity;
import com.myapp.android.revolut.view.adapter.LastCurrencyAdapter;

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