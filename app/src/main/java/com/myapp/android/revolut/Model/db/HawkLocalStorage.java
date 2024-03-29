package com.myapp.android.revolut.Model.db;

import com.orhanobut.hawk.Hawk;
import com.myapp.android.revolut.Model.dto.Currency;
import com.myapp.android.revolut.Model.dto.RemoteCurrencyDTO;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.myapp.android.revolut.Model.db.HawkKeyStorage.getCurrencyKey;
import static com.myapp.android.revolut.Model.db.HawkKeyStorage.getMainCurrecyKey;

@Singleton
public class HawkLocalStorage {

    @Inject
    public HawkLocalStorage() {
    }

    public RemoteCurrencyDTO saveCurrencies(RemoteCurrencyDTO remoteCurrencyDTO) {
        Hawk.put(getCurrencyKey(), remoteCurrencyDTO);
        return remoteCurrencyDTO;
    }

    public RemoteCurrencyDTO getCurrencies() {
        return Hawk.get(getCurrencyKey());
    }

    public Currency saveMainCurrency(Currency mainCurrency){
        Hawk.put(getMainCurrecyKey(), mainCurrency);
        return mainCurrency;
    }

    public Currency getMainCurrency(){
        return Hawk.get(getMainCurrecyKey(), Currency.create("AUD", 100.0));
    }

}