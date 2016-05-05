package com.scanbee.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.scanbee.scanbee.R;

/**
 * Created by kshitij on 5/5/2016.
 */
public class PaymentGetWayFragment extends Fragment {
   View viewMain;
    Button continueToInvoiceBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewMain=inflater.inflate(R.layout.payment_getway_fragment,null,false);
        continueToInvoiceBtn=(Button)viewMain.findViewById(R.id.continueToInvoiceBtn);
        continueToInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new InvoiceFragment()).commit();
            }
        });
        return viewMain;
    }
}
