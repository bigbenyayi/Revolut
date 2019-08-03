package com.myapp.android.revolut.arch;

import com.myapp.android.revolut.model.dto.Currency;

import java.util.List;

import rx.Observable;

public interface IRepository {

    Observable<List<Currency>> updateCurrencies(String base, Double amount);

}