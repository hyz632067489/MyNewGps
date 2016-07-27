package com.hyz.mynewgps.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyz.mynewgps.App;
import com.hyz.mynewgps.R;
import com.hyz.mynewgps.bean.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by ${hyz} on 2016/7/21.
 */
public abstract class BaseMoudleActivity extends FragmentActivity {


    TextView title;

    ImageView retBack;

    FrameLayout content;
    public LayoutInflater  inflater;
    public Activity mAc;
    public Context mCx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_module);

        mCx = App.app;
        mAc = this;

        inflater = LayoutInflater.from(mAc);

        title = (TextView) findViewById(R.id.title);

        retBack = (ImageView) findViewById(R.id.back);
        content= (FrameLayout) findViewById(R.id.frag_container);
        retBack.setOnClickListener(v -> finish());


        content.addView(setContent());

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        onCreate();

    }

    private void onCreate() {

    }


    public abstract View setContent();

    /**
     * 替换fragment
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openFragment(Event.OpenNewFragmentEvent event){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
}
