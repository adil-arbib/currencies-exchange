package com.arbib.currencyexchanege.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arbib.currencyexchanege.R;

import java.util.ArrayList;

public class Crypto extends Fragment {
    private ArrayList<String> availableCrypto;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_crypto, container, false);

        fillCrypto();


        return view;
    }


    private void fillCrypto(){
        availableCrypto = new ArrayList<>();
        availableCrypto.add("ada");
        availableCrypto.add("amd");
        availableCrypto.add("atom");
        availableCrypto.add("avax");
        availableCrypto.add("bch");
        availableCrypto.add("bnb");
        availableCrypto.add("btc");
        availableCrypto.add("busd");
        availableCrypto.add("chz");
        availableCrypto.add("cro");
        availableCrypto.add("dai");
        availableCrypto.add("doge");
        availableCrypto.add("dot");
        availableCrypto.add("egld");
        availableCrypto.add("enj");
        availableCrypto.add("etc");
        availableCrypto.add("eth");
        availableCrypto.add("fil");
        availableCrypto.add("ftt");
        availableCrypto.add("grt");
        availableCrypto.add("icp");
        availableCrypto.add("inj");
        availableCrypto.add("ksm");
        availableCrypto.add("link");
        availableCrypto.add("ltc");
        availableCrypto.add("luna");
        availableCrypto.add("matic");
        availableCrypto.add("one");
        availableCrypto.add("theta");
        availableCrypto.add("trx");
        availableCrypto.add("uni");
        availableCrypto.add("usdc");
        availableCrypto.add("usdt");
        availableCrypto.add("vet");
        availableCrypto.add("wbtc");
        availableCrypto.add("xag");
        availableCrypto.add("xau");
        availableCrypto.add("xdr");
        availableCrypto.add("xlm");
        availableCrypto.add("xmr");
        availableCrypto.add("xrp");




    }
}