package com.barhochman.theproject.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.barhochman.theproject.R;

public class IOHandler {

    public void FragmentSwapper(Context context, String name){
        switch (name){
            case "mainList": goTo(context,null);
        }

    }

    private void goTo(Context context,Fragment newFragment) {
        ((Activity) context).getFragmentManager().beginTransaction().replace(R.id.container, newFragment).addToBackStack(context.getClass().getName()).commit();
    }

    //private static Context context;
}
