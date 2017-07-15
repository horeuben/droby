package com.example.reube.droby.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Activities.ClothesBasket;
import com.example.reube.droby.Activities.FinalOutfitActivity;
import com.example.reube.droby.R;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.reube.droby.Fragments.ClothesFragment.adapter;
import static com.example.reube.droby.R.id.StylesFab;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StylesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StylesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StylesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private OnFragmentInteractionListener mListener;

    public StylesFragment() {
        // Required empty public constructor
    }

    public static StylesFragment newInstance(String param1, String param2) {
        StylesFragment fragment = new StylesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_outfit, container, false);
        // Inflate the layout for this fragment
        getActivity().setTitle("Style Recommendation");

        FloatingActionButton stylesFloatingButton = (FloatingActionButton) rootView.findViewById(StylesFab);
        stylesFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> extraStrings = new ArrayList<String>();
                extraStrings = ClothesFragment.adapter.clothes_basket_cart;
                Intent clothesBasketIntent2 = new Intent(getActivity(), ClothesBasket.class);
                clothesBasketIntent2.putExtra("StringList", extraStrings);
                startActivity(clothesBasketIntent2);
            }
        });

        TextView wears1 = (TextView) rootView.findViewById(R.id.wearText1);
        wears1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wears1Intent = new Intent(getActivity(), FinalOutfitActivity.class);
                startActivity(wears1Intent);
            }
        });

        Intent intent = getActivity().getIntent();
        LinearLayout finalLL = (LinearLayout) rootView.findViewById(R.id.finalisedOutfitLL);
        LinearLayout promptLL = (LinearLayout) rootView.findViewById(R.id.plannerPromptLL);
        LinearLayout s1LL = (LinearLayout) rootView.findViewById(R.id.suggestion1LL);
        LinearLayout s2LL = (LinearLayout) rootView.findViewById(R.id.suggestion2LL);
        LinearLayout s3LL = (LinearLayout) rootView.findViewById(R.id.suggestion3LL);
        FloatingActionButton stylesFab = (FloatingActionButton) rootView.findViewById(R.id.StylesFab);

        if(intent != null){
            String string = intent.getStringExtra("test");
            if(string == null){
                finalLL.setVisibility(LinearLayout.GONE);
                promptLL.setVisibility(LinearLayout.GONE);
                s1LL.setVisibility(LinearLayout.VISIBLE);
                s2LL.setVisibility(LinearLayout.VISIBLE);
                s3LL.setVisibility(LinearLayout.VISIBLE);
                stylesFab.setVisibility(FloatingActionButton.VISIBLE);
            }
            else if(string.equals(string)){
                finalLL.setVisibility(LinearLayout.VISIBLE);
                promptLL.setVisibility(LinearLayout.VISIBLE);
                s1LL.setVisibility(LinearLayout.GONE);
                s2LL.setVisibility(LinearLayout.GONE);
                s3LL.setVisibility(LinearLayout.GONE);
                stylesFab.setVisibility(FloatingActionButton.GONE);
            }

        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
