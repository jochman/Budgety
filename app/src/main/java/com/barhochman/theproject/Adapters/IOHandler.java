package com.barhochman.theproject.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.barhochman.theproject.Fragments.mainList;
import com.barhochman.theproject.R;

public class IOHandler {

    public IOHandler(Context context){
        IOHandler.context = context;
    }
    public void FragmentSwapper(String name){
        Intent fragment = null;
    }

    private void goTo(Fragment fragment) {
        ((Activity) context).getFragmentManager().beginTransaction();
    }

    private static Context context;
}
