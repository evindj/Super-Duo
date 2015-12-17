package it.jaschke.alexandria.scan;

import com.google.zxing.integration.android.IntentIntegrator;

import android.content.Intent;
import android.support.v4.app.*;

/**
 * Created by evindj on 12/12/15.
 */
public class FragmentIntentIntegrator extends IntentIntegrator {
    private final Fragment fragment ;
    public FragmentIntentIntegrator(Fragment fragment){
        super(fragment.getActivity());
        this.fragment= fragment;
    }
    @Override
    protected void startActivityForResult(Intent intent, int code){
        fragment.startActivityForResult(intent,code);
    }
}
