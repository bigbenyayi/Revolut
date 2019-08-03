package com.myapp.android.revolut.view.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.android.revolut.R;
import com.myapp.android.revolut.RevolutApplication;
import com.myapp.android.revolut.model.db.HawkLocalStorage;
import com.myapp.android.revolut.model.dto.Currency;
import com.myapp.android.revolut.util.LiteTextWatcher;
import com.myapp.android.revolut.util.Logger;
import com.myapp.android.revolut.util.Mappers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.paranoid.Obfuscate;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static com.myapp.android.revolut.util.CurrencyUtil.getCurrencyNameResByISO;
import static com.myapp.android.revolut.util.CurrencyUtil.getFlagResByISO;

@Obfuscate
public class LastCurrencyAdapter extends RecyclerView.Adapter<LastCurrencyAdapter.ViewHolder> {

    private static Logger LOGGER = Logger.build(LastCurrencyAdapter.class);

    private List<Currency> actualList = new ArrayList<>();
    private boolean binding;

    private RecyclerView mRecyclerView;

    @Inject
    public Context context;
    @Inject
    public HawkLocalStorage storage;

    @Inject
    public LastCurrencyAdapter() {
        RevolutApplication.getInjector().inject(this);
        actualList.add(0, storage.getMainCurrency());
    }

    private void setDefaultCurrencyValues() {
        LOGGER.log("setDefaultCurrencyValues");
        List<Currency> zeroList = new ArrayList<>();
        for (int i = 1; i < actualList.size(); i++) {
            zeroList.add(Currency.create(actualList.get(i).name(), 0.0));
        }
        setVolatileCurrencyValues(zeroList);
    }

    public void setVolatileCurrencyValues(List<Currency> list) {
        LOGGER.log("setVolatileCurrencyValues");
        for (int index = actualList.size() - 1; index > 0; index--)
            actualList.remove(index);
        this.actualList.addAll(1, new ArrayList<>(list));
        if (!binding) notifyItemRangeChanged(1, list.size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false), new MyCustomEditTextListener());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        binding = true;
        Currency current = actualList.get(position);
        holder.myCustomEditTextListener.updatePosition(position);
        holder.flagImageView.setImageResource(getFlagResByISO(current.name()));
        holder.textIso.setText(current.name());
        holder.textView.setText(getCurrencyNameResByISO(current.name()));
        holder.cardView.setOnClickListener((view) -> smoothToFirstElementFromPosition(position));
        holder.currencyEditText.setText(String.valueOf(current.value()));
        holder.setFocusableIfNeed(position);

        binding = false;
    }

    private void smoothToFirstElementFromPosition(int position) {
        Currency newMainCurrency = actualList.get(position);

        actualList.remove(position);
        actualList.add(0, newMainCurrency);

        if (!binding) {
            mRecyclerView.scrollToPosition(0);
            notifyItemMoved(position, 0);
            new Handler().postDelayed(this::notifyDataSetChanged, 500);
        }

        storage.saveMainCurrency(newMainCurrency);
    }

    @Override
    public int getItemCount() {
        return actualList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.currency_edit_text)
        EditText currencyEditText;

        @BindView(R.id.flag_image_view)
        ImageView flagImageView;

        @BindView(R.id.text_iso)
        TextView textIso;

        @BindView(R.id.text_name)
        TextView textView;

        public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View itemView, MyCustomEditTextListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            myCustomEditTextListener = listener;
            currencyEditText.addTextChangedListener(myCustomEditTextListener);
        }

        public void setFocusableIfNeed(int position){
            currencyEditText.setInputType(position == 0 ? TYPE_CLASS_NUMBER : 0);
        }

    }


    private class MyCustomEditTextListener extends LiteTextWatcher {

        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i2, int i3) {

            if (position != 0) return;
            Double value = Mappers.parseDouble(s.toString());
            Currency updatedMainCurrency = Currency.create(storage.getMainCurrency().name(), value);
            storage.saveMainCurrency(updatedMainCurrency);
            actualList.remove(0);
            actualList.add(0, updatedMainCurrency);

            if (value.equals(0.0)) setDefaultCurrencyValues();
        }
    }

}