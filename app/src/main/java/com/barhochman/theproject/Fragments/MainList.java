package com.barhochman.theproject.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barhochman.theproject.Adapters.RecAdapter;
import com.barhochman.theproject.Adapters.StringsHelper;
import com.barhochman.theproject.Nodes.DBBank;
import com.barhochman.theproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainList.OnMainListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainList extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DBBANK_PARAM1 = "dbbank";
    //incomes/outcomes recycler view
    RecyclerView incomesView, outcomesView;
    LinearLayoutManager inLayoutManager, outLayoutManager;
    RecAdapter incomesAdapter, outcomesAdapter;


    // TODO: Rename and change types of parameters

    private OnMainListFragmentInteractionListener mListener;

    View fragmentView;

    public MainList() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainList.
     */
    // TODO: Rename and change types and number of parameters
    public static MainList newInstance() {
        MainList fragment = new MainList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_main_list, container, false);
        //set incomes/outcomes view
        incomesView = fragmentView.findViewById(R.id.incomes);
        outcomesView = fragmentView.findViewById(R.id.outcomes);
        //improving performance
        incomesView.setHasFixedSize(true);
        outcomesView.setHasFixedSize(true);

        //setting manager
        inLayoutManager = new LinearLayoutManager(getContext());
        outLayoutManager = new LinearLayoutManager(getContext());
        incomesView.setLayoutManager(inLayoutManager);
        outcomesView.setLayoutManager(outLayoutManager);

        incomesView.setBackgroundColor(Color.parseColor("#86b24f"));
        outcomesView.setBackgroundColor(Color.parseColor("#c40824"));

        // add adapters
        incomesAdapter = new RecAdapter(DBBank.getIncomes());
        //incomesAdapter.setColor("#86b24f");
        outcomesAdapter = new RecAdapter(DBBank.getOutcomes());
        //outcomesAdapter.setColor("#c40824");

        incomesView.setAdapter(incomesAdapter);
        outcomesView.setAdapter(outcomesAdapter);

        invalidate();


        // Inflate the layout for this fragment
        return fragmentView;
    }

    private void invalidate() {
        TextView total = fragmentView.findViewById(R.id.totalSpent);
        total.setText(StringsHelper.numberFormatter(DBBank.getTotal()));
        if (DBBank.getTotal() < 0){
            fragmentView.findViewById(R.id.top_total).setBackgroundColor(Color.parseColor("#c40824"));
        }else{
            fragmentView.findViewById(R.id.top_total).setBackgroundColor(Color.parseColor("#86b24f"));
        }
    }

    public void updateUI(Object object){

        if (object instanceof String){
            String str = (String) object;
            if (str.equals(StringsHelper.UpdateUI.getTotalUpdated())){
                invalidate();
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMainListFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainListFragmentInteractionListener) {
            mListener = (OnMainListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMainListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMainListFragmentInteraction(Uri uri);
    }
}
