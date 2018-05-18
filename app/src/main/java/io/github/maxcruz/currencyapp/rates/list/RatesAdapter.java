package io.github.maxcruz.currencyapp.rates.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.maxcruz.currencyapp.R;
import io.github.maxcruz.domain.model.ConversionRate;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> {

    private final List<ConversionRate> rates;

    public RatesAdapter(List<ConversionRate> rates) {
        this.rates = rates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_currency, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConversionRate conversionRate = rates.get(position);
        holder.textViewCode.setText(conversionRate.getCode());
    }

    @Override
    public int getItemCount() {
        return this.rates.size();
    }

    public List<ConversionRate> getRates() {
        return rates;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewFlag)
        ImageView imageViewFlag;

        @BindView(R.id.textViewCode)
        TextView textViewCode;

        @BindView(R.id.textViewValue)
        TextView textViewValue;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
