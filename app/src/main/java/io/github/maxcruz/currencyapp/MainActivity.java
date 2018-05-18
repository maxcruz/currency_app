package io.github.maxcruz.currencyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Main activity that should hold fragments in JetPack Navigation Architecture.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
