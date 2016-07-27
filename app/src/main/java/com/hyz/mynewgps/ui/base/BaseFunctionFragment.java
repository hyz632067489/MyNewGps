package com.hyz.mynewgps.ui.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by ${hyz} on 2016/7/21.
 */
public abstract class BaseFunctionFragment extends Fragment {

    protected BaseMoudleActivity rootActivity ;
    protected android.app.FragmentManager fragManager;
    protected LayoutInflater inflater;
    private View view;
    protected  Bundle bundle;
    protected String orgiId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);

        fragManager =  getFragmentManager();
        rootActivity = (BaseMoudleActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;

//        view = setView();
        ButterKnife.bind(this,view);
        return view;
    }

//    private abstract View setView() ;



}
