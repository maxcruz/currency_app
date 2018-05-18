package io.github.maxcruz.currencyapp.rates;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.github.maxcruz.currencyapp.R;
import io.github.maxcruz.currencyapp.rates.list.RatesAdapter;
import io.github.maxcruz.domain.interactors.DownloadRemoteRates;
import io.github.maxcruz.domain.interactors.GetSavedRates;
import io.github.maxcruz.domain.repository.Repository;
import io.github.maxcruz.repository.CurrencyRepository;
import io.github.maxcruz.repository.local.ConversionRateDao;
import io.github.maxcruz.repository.local.RatesDatabase;
import io.github.maxcruz.repository.remote.CurrencyService;
import io.github.maxcruz.repository.remote.ServiceFactory;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RatesFragment extends Fragment {

    protected RatesViewModelFactory viewModelFactory;

    private RatesViewModel viewModel;
    private RatesAdapter ratesAdapter;

    @BindView(R.id.outputRecyclerView)
    protected RecyclerView outputRecyclerView;

    @BindView(R.id.editTextInput)
    protected EditText editTextInput;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        outputRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ratesAdapter = new RatesAdapter(new ArrayList<>());
        outputRecyclerView.setAdapter(ratesAdapter);
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
                Toast.makeText(getContext(), "LOADING", Toast.LENGTH_LONG).show();
                break;
            case SUCCESS:
                Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_LONG).show();
                break;
            case ERROR:
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
                break;
            case COMPLETE:
                viewModel.loadConversionRates();
                break;
        }
    }

    private void setupInjection() {
        // TODO: Setup dependency injection
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        CurrencyService currencyService = new ServiceFactory(builder, interceptor).createService(CurrencyService.class, CurrencyService.URL);
        ConversionRateDao conversionRateDao = RatesDatabase.getDatabase(getContext()).getConversionRateDao();
        Repository repository = new CurrencyRepository(conversionRateDao, currencyService);
        Scheduler subscribeOn = Schedulers.io();
        Scheduler observeOn = AndroidSchedulers.mainThread();
        DownloadRemoteRates downloadRemoteRates = new DownloadRemoteRates(repository, subscribeOn, observeOn);
        GetSavedRates getSavedRates = new GetSavedRates(repository, subscribeOn, observeOn);
        viewModelFactory = new RatesViewModelFactory(downloadRemoteRates, getSavedRates);
    }

}
