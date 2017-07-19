package com.example.reube.droby.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Activities.ClothesBasket;
import com.example.reube.droby.Activities.FinalOutfitActivity;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.R;

import java.util.ArrayList;
import java.util.Random;

import static com.example.reube.droby.Activities.MainActivity.AllClothes;
import static com.example.reube.droby.Activities.MainActivity.i1;
import static com.example.reube.droby.Activities.MainActivity.i2;
import static com.example.reube.droby.Activities.MainActivity.i3;
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

    private ArrayList<String> outfitFinalList = new ArrayList<String>();
    private  ArrayList<Clothes> finalClothesList = new ArrayList<Clothes>();
    private ArrayList<String> suggestedOutfitList = new ArrayList<String>();
    DatabaseHandler db;
//    private static ArrayList<Clothes> AllClothes = new ArrayList<Clothes>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

        View rootView = inflater.inflate(R.layout.fragment_styles, container, false);
        // Inflate the layout for this fragment
        getActivity().setTitle("Style Recommendation");

        FloatingActionButton stylesFloatingButton = (FloatingActionButton) rootView.findViewById(StylesFab);
        stylesFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> extraStrings = new ArrayList<String>();
                extraStrings = adapter.clothes_basket_cart;
                Intent clothesBasketIntent2 = new Intent(getActivity(), ClothesBasket.class);
                clothesBasketIntent2.putExtra("StringList", extraStrings);
                startActivity(clothesBasketIntent2);
            }
        });

        //Suggestion 1 random    generator
//        db = new DatabaseHandler(getActivity());
//        AllClothes = db.getAllClothesTest();
//        int min = 0;
//        int max = AllClothes.size();


//        Random r = new Random();
//        int i1 = r.nextInt(max - min) + min;
//        int i2 = r.nextInt(max - min) + min;
//        int i3 = r.nextInt(max - min) + min;
        ImageView s1ImageTop = (ImageView) rootView.findViewById(R.id.s1Top);
        ImageView s1ImageBottom = (ImageView) rootView.findViewById(R.id.s1Bottom);
        ImageView s1ImageOuter = (ImageView) rootView.findViewById(R.id.s1Outerwear);
        if (i1>0){

            s1ImageTop.setImageBitmap(convertToBitmap(AllClothes.get(i1).getImage()));
            s1ImageBottom.setImageBitmap(convertToBitmap(AllClothes.get(i2).getImage()));
            s1ImageOuter.setImageBitmap(convertToBitmap(AllClothes.get(i3).getImage()));
            suggestedOutfitList.add(Integer.toString(AllClothes.get(i1).getId()));
            suggestedOutfitList.add(Integer.toString(AllClothes.get(i2).getId()));
            suggestedOutfitList.add(Integer.toString(AllClothes.get(i3).getId()));
        }



        //Clicking suggestion 1 wear text
        TextView wears1 = (TextView) rootView.findViewById(R.id.wearText1);
        wears1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pick1Intent = new Intent(getActivity(), FinalOutfitActivity.class);
                pick1Intent.putExtra("OutfitList", suggestedOutfitList);
                startActivity(pick1Intent);
            }
        });

        //Clicking select styles text
        TextView stylesText = (TextView) rootView.findViewById(R.id.StylesText);
        stylesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWindow(v);

            }
        });

        Intent intent = getActivity().getIntent();
        LinearLayout finalLL = (LinearLayout) rootView.findViewById(R.id.finalisedOutfitLL);
        LinearLayout promptLL = (LinearLayout) rootView.findViewById(R.id.plannerPromptLL);
        LinearLayout s1LL = (LinearLayout) rootView.findViewById(R.id.suggestion1LL);
        LinearLayout s2LL = (LinearLayout) rootView.findViewById(R.id.suggestion2LL);
        LinearLayout s3LL = (LinearLayout) rootView.findViewById(R.id.suggestion3LL);
        LinearLayout stylesPrompt = (LinearLayout) rootView.findViewById(R.id.stylePromptLL);
        FloatingActionButton stylesFab = (FloatingActionButton) rootView.findViewById(R.id.StylesFab);
        ImageView finalisedTop = (ImageView) rootView.findViewById(R.id.fTop);
        ImageView finalisedBottom = (ImageView) rootView.findViewById(R.id.fBottom);
        ImageView finalisedOuterwear = (ImageView) rootView.findViewById(R.id.fOuterwear);

        if(intent != null){
            String string = intent.getStringExtra("test");  //check if intent comes from FinalOutfitActivity
            if(string == null){
                finalLL.setVisibility(LinearLayout.GONE);
                promptLL.setVisibility(LinearLayout.GONE);
                s1LL.setVisibility(LinearLayout.VISIBLE);
                s2LL.setVisibility(LinearLayout.VISIBLE);
                s3LL.setVisibility(LinearLayout.VISIBLE);
                stylesFab.setVisibility(FloatingActionButton.VISIBLE);
                stylesPrompt.setVisibility(LinearLayout.VISIBLE);
            }
            else if(string.equals(string)){
                outfitFinalList = (ArrayList<String>)intent.getExtras().getSerializable("outfitClothes");
                db = new DatabaseHandler(getActivity());
                finalClothesList = db.getSelectedClothesIdTest(outfitFinalList);
                finalLL.setVisibility(LinearLayout.VISIBLE);
                promptLL.setVisibility(LinearLayout.VISIBLE);
                s1LL.setVisibility(LinearLayout.GONE);
                s2LL.setVisibility(LinearLayout.GONE);
                s3LL.setVisibility(LinearLayout.GONE);
                stylesPrompt.setVisibility(LinearLayout.GONE);
                stylesFab.setVisibility(FloatingActionButton.GONE);
                finalisedTop.setImageBitmap(convertToBitmap(finalClothesList.get(0).getImage()));
                finalisedBottom.setImageBitmap(convertToBitmap(finalClothesList.get(1).getImage()));
                finalisedOuterwear.setImageBitmap(convertToBitmap(finalClothesList.get(2).getImage()));
            }

        }

        return rootView;
    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    private void popUpWindow(View v){
        try{
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.styles_pop_up, (ViewGroup) v.findViewById(R.id.stylesFrameLayout) );

            final PopupWindow popWindow = new PopupWindow(layout, 800, 1200, true);
            popWindow.setAnimationStyle(-1);
            popWindow.setElevation(5);
            popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            final FrameLayout layout_main = (FrameLayout) v.findViewById(R.id.stylesFrameLayout);


            TextView cancel = (TextView) layout.findViewById(R.id.cancelButton);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindow.dismiss();
                }
            });

            TextView okButton = (TextView) layout.findViewById(R.id.okButton);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindow.dismiss();
                }
            });

        }

        catch (Exception e) {
            e.printStackTrace();
        }
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
