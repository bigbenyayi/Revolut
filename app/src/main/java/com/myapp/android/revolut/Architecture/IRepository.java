package com.myapp.android.revolut.Architecture;

import com.myapp.android.revolut.Model.dto.Currency;

import java.util.List;

import rx.Observable;

public interface IRepository {

    Observable<List<Currency>> updateCurrencies(String base, Double amount);

}