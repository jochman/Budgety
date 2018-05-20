package com.barhochman.theproject.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.barhochman.theproject.Fragments.AddTransfer;
import com.barhochman.theproject.Fragments.MainList;
import com.barhochman.theproject.R;

public class IOHandler {
    private static String addID, mainID;

    static {
        addID = "addID";
        mainID = "mainID";
    }

    public static String getAddID() {
        return addID;
    }

    public static String getMainID() {
        return mainID;
    }

    public static void FragmentSwapper(FragmentManager fragmentManager, int name){
        Fragment fragment = null;
        String id = null;
        switch (name){
            case R.layout.fragment_main_list:
                fragment = new MainList();
                id = mainID;
                break;
            case R.layout.fragment_add_transfer:
                fragment = new AddTransfer();
                id = addID;
                break;
        }
        if (fragment != null){
            fragmentManager.beginTransaction().add(R.id.container, fragment, id).addToBackStack(lastAdded).commit();}
    }

    public static void back(FragmentManager fragmentManager){
        fragmentManager.popBackStackImmediate();
    }
    public static String lastAdded;


}
