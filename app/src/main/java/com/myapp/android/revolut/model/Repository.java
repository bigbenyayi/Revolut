package com.myapp.android.revolut.model;

import com.myapp.android.revolut.RevolutApplication;
import com.myapp.android.revolut.arch.IRepository;
import com.myapp.android.revolut.model.api.CurrencyAPI;
import com.myapp.android.revolut.model.db.HawkLocalStorage;
import com.myapp.android.revolut.model.dto.Currency;
import com.myapp.android.revolut.util.Mappers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class Repository implements IRepository {

    @Inject
    CurrencyAPI currencyAPI;
    @Inject
    HawkLocalStorage localStorage;

    @Inject
    public Repository() {
        RevolutApplication.getInjector().inject(this);
    }

    public Observable<List<Currency>> updateCurrencies(String base, Double amount) {
        return currencyAPI.getCurrencies(base, amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(localStorage::saveCurrencies)
                .onErrorReturn(throwable -> localStorage.getCurrencies())
                .filter((remoteCurrencyDTO) -> !amount.equals(0.0))
                .map(Mappers::mapRemoteToLocal)
                .onErrorReturn(throwable -> new ArrayList<>());
    }

}