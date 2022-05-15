package com.arbib.currencyexchanege.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.arbib.currencyexchanege.Item;
import com.arbib.currencyexchanege.R;
import com.arbib.currencyexchanege.adapters.CurrencyAdapter;
import com.google.type.Date;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Currency extends Fragment {
    private View view;
    private RecyclerView rcView;
    private CurrencyAdapter currencyAdapter;
    private ArrayList<Item> allCurrencies;
    private TextView txt_current_date;
    private ArrayList<String> availableCurrencies;
    private ArrayList<String> currenciesFullName;
    private ImageView icon_search;
    private EditText edt_search;
    private boolean icon_clicked = false;
    private ArrayList<Item> searchedArray;
    private ArrayList<String> yesterdayRates;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_currency, container, false);
        rcView = view.findViewById(R.id.recyclerView);
        txt_current_date = view.findViewById(R.id.date);
        icon_search = view.findViewById(R.id.icon_search);
        edt_search = view.findViewById(R.id.edt_search);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        txt_current_date.setText(currentDate);




        fillCurrencies();
        currenciesFullName = new ArrayList<>();
        getFullName(currenciesFullName);
        getYesterdayRates();
        getCurrencies();
        searchedArray = new ArrayList<>();









        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!icon_clicked){
                    edt_search.setVisibility(View.VISIBLE);
                    edt_search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if(edt_search.getText().equals("")){
                                showRecyclerView(allCurrencies);
                            }else {
                                searchCurrencies();
                                showRecyclerView(searchedArray);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    searchCurrencies();
                    showRecyclerView(searchedArray);
                }else edt_search.setVisibility(View.GONE);

                icon_clicked = !icon_clicked;
            }
        });

        return view;
    }


    private void showRecyclerView(ArrayList<Item> arrayList){
        currencyAdapter = new CurrencyAdapter(arrayList);
        rcView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcView.setAdapter(currencyAdapter);
    }

    private void getCurrencies(){
        allCurrencies = new ArrayList<>();
        String todayURL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/"+getToday()+"/currencies/eur.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, todayURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject baseObject = new JSONObject(response);
                    JSONObject eur = baseObject.getJSONObject("eur");
                    for(int i=0; i<availableCurrencies.size(); i++){
                        String name = availableCurrencies.get(i);

                        double todayRate = 1 / Double.parseDouble(eur.getString(name));
                        double yesterdayRate = 1/ Double.parseDouble(yesterdayRates.get(i));
                        double diff = (todayRate*100)/yesterdayRate - 100;
                        allCurrencies.add(new Item(name, eur.getString(name), diff, currenciesFullName.get(i)));
                    }
                }catch (JSONException e){
                    Toast.makeText(getActivity(), "Error try again", Toast.LENGTH_SHORT).show();
                }
                showRecyclerView(allCurrencies);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }

    private void getFullName(ArrayList<String> arrayList){
        String fullNameURL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, fullNameURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject baseObject = new JSONObject(response);
                    for(int i=0; i<availableCurrencies.size(); i++){
                        arrayList.add(baseObject.getString(availableCurrencies.get(i)));
                    }
                }catch (JSONException e){
                    Toast.makeText(getActivity(), "error try again", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error on getting names", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void getYesterdayRates(){
        yesterdayRates = new ArrayList<>();
        String yesterdayURL = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/"+getYesterday()+"/currencies/eur.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, yesterdayURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject baseObject = new JSONObject(response);
                    JSONObject eur = baseObject.getJSONObject("eur");
                    for(int i=0; i<availableCurrencies.size(); i++){
                        yesterdayRates.add(eur.getString(availableCurrencies.get(i)));
                    }
                }catch (JSONException e){
                    Toast.makeText(getActivity(), "Error try again", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error on getting names", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void searchCurrencies(){
        searchedArray.clear();
        String input = edt_search.getText().toString().toLowerCase().trim();
        for(Item item : allCurrencies){
            if(item.getName().toLowerCase().startsWith(input) || item.getFullname().toLowerCase().startsWith(input)){
                searchedArray.add(item);
            }
        }
    }


    private String getYesterday(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return dateFormat.format(calendar.getTime());
    }

    private String getToday(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        return dateFormat.format(calendar.getTime());
    }

    private void fillCurrencies(){
        availableCurrencies = new ArrayList<>();
        availableCurrencies.add("aed");
        availableCurrencies.add("afn");
        availableCurrencies.add("algo");
        availableCurrencies.add("all");
        availableCurrencies.add("ang");
        availableCurrencies.add("aoa");
        availableCurrencies.add("ars");
        availableCurrencies.add("aud");
        availableCurrencies.add("awg");
        availableCurrencies.add("azn");
        availableCurrencies.add("bam");
        availableCurrencies.add("bbd");
        availableCurrencies.add("bdt");
        availableCurrencies.add("bgn");
        availableCurrencies.add("bhd");
        availableCurrencies.add("bif");
        availableCurrencies.add("bmd");
        availableCurrencies.add("bnd");
        availableCurrencies.add("bob");
        availableCurrencies.add("brl");
        availableCurrencies.add("bsd");
        availableCurrencies.add("btn");
        availableCurrencies.add("bwp");
        availableCurrencies.add("byn");
        availableCurrencies.add("bzd");
        availableCurrencies.add("cad");
        availableCurrencies.add("cdf");
        availableCurrencies.add("chf");
        availableCurrencies.add("clf");
        availableCurrencies.add("clp");
        availableCurrencies.add("cny");
        availableCurrencies.add("cop");
        availableCurrencies.add("crc");
        availableCurrencies.add("cuc");
        availableCurrencies.add("cup");
        availableCurrencies.add("cve");
        availableCurrencies.add("czk");
        availableCurrencies.add("djf");
        availableCurrencies.add("dkk");
        availableCurrencies.add("dop");
        availableCurrencies.add("dzd");
        availableCurrencies.add("egp");
        availableCurrencies.add("ern");
        availableCurrencies.add("etb");
        availableCurrencies.add("eur");
        availableCurrencies.add("fjd");
        availableCurrencies.add("fkp");
        availableCurrencies.add("gbp");
        availableCurrencies.add("gel");
        availableCurrencies.add("ggp");
        availableCurrencies.add("ghs");
        availableCurrencies.add("gip");
        availableCurrencies.add("gmd");
        availableCurrencies.add("gnf");
        availableCurrencies.add("gtq");
        availableCurrencies.add("gyd");
        availableCurrencies.add("hkd");
        availableCurrencies.add("hnl");
        availableCurrencies.add("hrk");
        availableCurrencies.add("htg");
        availableCurrencies.add("huf");
        availableCurrencies.add("idr");
        availableCurrencies.add("ils");
        availableCurrencies.add("imp");
        availableCurrencies.add("inr");
        availableCurrencies.add("iqd");
        availableCurrencies.add("irr");
        availableCurrencies.add("isk");
        availableCurrencies.add("jep");
        availableCurrencies.add("jmd");
        availableCurrencies.add("jod");
        availableCurrencies.add("jpy");
        availableCurrencies.add("kes");
        availableCurrencies.add("kgs");
        availableCurrencies.add("khr");
        availableCurrencies.add("kmf");
        availableCurrencies.add("kpw");
        availableCurrencies.add("krw");
        availableCurrencies.add("kwd");
        availableCurrencies.add("kyd");
        availableCurrencies.add("kzt");
        availableCurrencies.add("lak");
        availableCurrencies.add("lbp");
        availableCurrencies.add("lkr");
        availableCurrencies.add("lrd");
        availableCurrencies.add("lsl");
        availableCurrencies.add("ltl");
        availableCurrencies.add("lvl");
        availableCurrencies.add("lyd");
        availableCurrencies.add("mad");
        availableCurrencies.add("mdl");
        availableCurrencies.add("mga");
        availableCurrencies.add("mkd");
        availableCurrencies.add("mmk");
        availableCurrencies.add("mnt");
        availableCurrencies.add("mop");
        availableCurrencies.add("mro");
        availableCurrencies.add("mur");
        availableCurrencies.add("mvr");
        availableCurrencies.add("mwk");
        availableCurrencies.add("mxn");
        availableCurrencies.add("myr");
        availableCurrencies.add("mzn");
        availableCurrencies.add("nad");
        availableCurrencies.add("ngn");
        availableCurrencies.add("nio");
        availableCurrencies.add("nok");
        availableCurrencies.add("npr");
        availableCurrencies.add("nzd");
        availableCurrencies.add("omr");
        availableCurrencies.add("pab");
        availableCurrencies.add("pen");
        availableCurrencies.add("pgk");
        availableCurrencies.add("php");
        availableCurrencies.add("pkr");
        availableCurrencies.add("pln");
        availableCurrencies.add("pyg");
        availableCurrencies.add("qar");
        availableCurrencies.add("ron");
        availableCurrencies.add("rsd");
        availableCurrencies.add("rub");
        availableCurrencies.add("rwf");
        availableCurrencies.add("sar");
        availableCurrencies.add("sbd");
        availableCurrencies.add("scr");
        availableCurrencies.add("sdg");
        availableCurrencies.add("sek");
        availableCurrencies.add("sgd");
        availableCurrencies.add("shib");
        availableCurrencies.add("shp");
        availableCurrencies.add("sll");
        availableCurrencies.add("sol");
        availableCurrencies.add("sos");
        availableCurrencies.add("srd");
        availableCurrencies.add("std");
        availableCurrencies.add("svc");
        availableCurrencies.add("syp");
        availableCurrencies.add("szl");
        availableCurrencies.add("thb");
        availableCurrencies.add("tjs");
        availableCurrencies.add("tmt");
        availableCurrencies.add("tnd");
        availableCurrencies.add("top");
        availableCurrencies.add("try");
        availableCurrencies.add("ttd");
        availableCurrencies.add("twd");
        availableCurrencies.add("tzs");
        availableCurrencies.add("uah");
        availableCurrencies.add("ugx");
        availableCurrencies.add("usd");
        availableCurrencies.add("uyu");
        availableCurrencies.add("uzs");
        availableCurrencies.add("vef");
        availableCurrencies.add("vnd");
        availableCurrencies.add("vuv");
        availableCurrencies.add("wst");
        availableCurrencies.add("xaf");
        availableCurrencies.add("xcd");
        availableCurrencies.add("xof");
        availableCurrencies.add("xpf");
        availableCurrencies.add("yer");
        availableCurrencies.add("zar");
        availableCurrencies.add("zmk");
        availableCurrencies.add("zmw");
        availableCurrencies.add("zwl");

    }
}