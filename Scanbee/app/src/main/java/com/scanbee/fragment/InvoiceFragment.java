package com.scanbee.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scanbee.scanbee.R;

/**
 * Created by kshitij on 5/5/2016.
 */
public class InvoiceFragment extends Fragment {
    View viewMain;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain=inflater.inflate(R.layout.invoice_frgment,null,false);
        return viewMain;
    }
}
