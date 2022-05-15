package com.arbib.currencyexchanege.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.arbib.currencyexchanege.fragments.Convert;
import com.arbib.currencyexchanege.fragments.Crypto;
import com.arbib.currencyexchanege.fragments.Currency;
import com.arbib.currencyexchanege.fragments.Statistics;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Currency();
            case 1:
                return new Crypto();
            case 2:
                return new Statistics();
            case 3:
                return new Convert();
            default:
                return new Currency();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
