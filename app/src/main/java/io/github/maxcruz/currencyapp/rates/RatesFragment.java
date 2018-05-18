package io.github.maxcruz.currencyapp.rates;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.github.maxcruz.currencyapp.CurrencyApp;
import io.github.maxcruz.currencyapp.R;
import io.github.maxcruz.currencyapp.rates.list.RatesAdapter;

/**
 * Currencies with conversion rate screen.
 */
public class RatesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    protected RatesViewModelFactory viewModelFactory;

    private Context context;
    private RatesViewModel viewModel;
    private RatesAdapter ratesAdapter;

    @BindView(R.id.constraintLayout)
    ConstraintLayout layout;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.outputRecyclerView)
    protected RecyclerView outputRecyclerView;

    @BindView(R.id.editTextInput)
    protected EditText editTextInput;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        setupInjection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rates, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        outputRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        ratesAdapter = new RatesAdapter(new ArrayList<>());
        outputRecyclerView.setAdapter(ratesAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RatesViewModel.class);
        viewModel.getStatus().observe(this, this::processResponse);
        viewModel.getRates().observe(this, list -> ratesAdapter.addItems(list));
        viewModel.synchronize();
    }

    @OnClick(R.id.imageButtonClear)
    protected void onClearInput() {
        editTextInput.setText("");
    }

    @OnTextChanged(R.id.editTextInput)
    protected void onInputChanged() {
        String inputText = editTextInput.getText().toString();
        Double input = (! inputText.isEmpty()) ? Double.parseDouble(inputText) : 0.0;
        ratesAdapter.setInput(input);
    }

    private void processResponse(RatesViewModel.Status status) {
        switch (status) {
            case LOADING:
                swipeRefreshLayout.setRefreshing(true);
                break;
            case SUCCESS:
                swipeRefreshLayout.setRefreshing(false);
                break;
            case ERROR:
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(layout, R.string.message_network_error, Snackbar.LENGTH_LONG).show();
                break;
            case COMPLETE:
                viewModel.loadConversionRates();
                break;
        }
    }

    private void setupInjection() {
        ((CurrencyApp) context.getApplicationContext())
                .getRatesComponent(context).inject(this);
    }

    @Override
    public void onRefresh() {
        onClearInput();
        ratesAdapter.addItems(new ArrayList<>());
        viewModel.synchronize();
    }
}
