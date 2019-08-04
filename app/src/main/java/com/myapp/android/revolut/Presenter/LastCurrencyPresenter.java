package com.myapp.android.revolut.Presenter;

import android.content.Context;
import android.content.Intent;

import com.myapp.android.revolut.RevolutApplication;
import com.myapp.android.revolut.Architecture.BasePresenter;
import com.myapp.android.revolut.Architecture.LastCurrencyContract;
import com.myapp.android.revolut.Model.Repository;
import com.myapp.android.revolut.Model.db.HawkLocalStorage;
import com.myapp.android.revolut.Services.PeriodicalService;
import com.myapp.android.revolut.Utils.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.michaelrocks.paranoid.Obfuscate;
import rx.Subscription;
//Using the Architecture folder
@Obfuscate
@Singleton
public class LastCurrencyPresenter extends BasePresenter<LastCurrencyContract.ILastCurrencyView>
        implements LastCurrencyContract.ILastCurrencyPresenter {

    private static Logger LOGGER = Logger.build(LastCurrencyPresenter.class);

    private Subscription subscription;

    @Inject
    Repository repository;
    @Inject
    Context context;
    @Inject
    HawkLocalStorage storage;

    @Inject
    public LastCurrencyPresenter() {
        RevolutApplication.getInjector().inject(this);
    }

    @Override
    public void attachView(LastCurrencyContract.ILastCurrencyView view) {
        LOGGER.log("attachView");
        super.attachView(view);
        getLastUpdatedCurrency();
        context.startService(new Intent(context, PeriodicalService.class));
    }

    @Override
    public void detachView() {
        LOGGER.log("detachView");
        context.stopService(new Intent(context, PeriodicalService.class));
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.detachView();
    }

    @Override
    public void getLastUpdatedCurrency(String code, Double amount) {
        LOGGER.log("getLastUpdatedCurrency");
        subscription = repository.updateCurrencies(code, amount)
                .subscribe(
                        list -> {
                            if (!list.isEmpty())
                                getMvpView().onCurrencyUpdated(list);
                            else
                                getMvpView().onNoDataAvailable();
                        }, LOGGER::error
                );
    }

    @Override
    public void getLastUpdatedCurrency() {
        getLastUpdatedCurrency(storage.getMainCurrency().name(), storage.getMainCurrency().value());
    }


}