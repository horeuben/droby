package com.example.reube.droby.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Activities.ClothesBasket;
import com.example.reube.droby.Activities.FinalOutfitActivity;
import com.example.reube.droby.Activities.MainActivity;
import com.example.reube.droby.Activities.TakeClothesFromWardrobe;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Database.DatabaseHandler;
import com.example.reube.droby.R;

import java.util.ArrayList;

import static com.example.reube.droby.Fragments.ClothesFragment.adapter;
import static com.example.reube.droby.R.id.StylesFab;
import static com.example.reube.droby.R.id.suggestion2LL;

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
    private  ArrayList<Clothes> list = new ArrayList<Clothes>();
    private ArrayList<String> suggestedOutfitList = new ArrayList<String>();
    private ArrayList<String> chosenStyle = new ArrayList<String>();
    private ArrayList<String> locationList = new ArrayList<String>();
    DatabaseHandler db;
    ArrayList<String> styleOutfit = new ArrayList<String>();
    private  ArrayList<Clothes> styleOutfitList = new ArrayList<Clothes>();
    ProgressDialog pd;
    ImageView s1ImageTop;
    ImageView s1ImageBottom;
    ImageView s1ImageOuter;
    ImageView s1ImageOnepiece;
    ImageView s2ImageTop;
    ImageView s2ImageBottom;
    ImageView s2ImageOuter;
    ImageView s2ImageOnepiece;
    LinearLayout s1LL;
    LinearLayout s2LL;
    LinearLayout s3LL;
    LinearLayout stylesPrompt;
    TextView s1text;
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

        db = new DatabaseHandler(getActivity());

        Intent intent = getActivity().getIntent();
        LinearLayout finalLL = (LinearLayout) rootView.findViewById(R.id.finalisedOutfitLL);
        LinearLayout promptLL = (LinearLayout) rootView.findViewById(R.id.plannerPromptLL);
        s1LL = (LinearLayout) rootView.findViewById(R.id.suggestion1LL);
        s2LL = (LinearLayout) rootView.findViewById(R.id.suggestion2LL);
        s3LL = (LinearLayout) rootView.findViewById(R.id.suggestion3LL);
        stylesPrompt = (LinearLayout) rootView.findViewById(R.id.stylePromptLL);
        FloatingActionButton stylesFab = (FloatingActionButton) rootView.findViewById(R.id.StylesFab);
        ImageView finalisedTop = (ImageView) rootView.findViewById(R.id.fTop);
        ImageView finalisedBottom = (ImageView) rootView.findViewById(R.id.fBottom);
        ImageView finalisedOuterwear = (ImageView) rootView.findViewById(R.id.fOuterwear);
        ImageView finalisedOnepiece = (ImageView) rootView.findViewById(R.id.fOnepiece);
        s1ImageTop = (ImageView) rootView.findViewById(R.id.s1Top);
        s1ImageBottom = (ImageView) rootView.findViewById(R.id.s1Bottom);
        s1ImageOuter = (ImageView) rootView.findViewById(R.id.s1Outerwear);
        s1ImageOnepiece = (ImageView) rootView.findViewById(R.id.s1Onepiece);
        s2ImageTop = (ImageView) rootView.findViewById(R.id.s2Top);
        s2ImageBottom = (ImageView) rootView.findViewById(R.id.s2Bottom);
        s2ImageOuter = (ImageView) rootView.findViewById(R.id.s2Outerwear);
        s2ImageOnepiece = (ImageView) rootView.findViewById(R.id.s2Onepiece);
        s1text = (TextView) rootView.findViewById(R.id.s1Text);

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

        //check if intent comes from FinalOutfitActivity
        if(intent != null){
            String string = intent.getStringExtra("test");
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
                finalClothesList = db.getSelectedClothesIdTest(outfitFinalList);
                finalLL.setVisibility(LinearLayout.VISIBLE);
                promptLL.setVisibility(LinearLayout.VISIBLE);
                s1LL.setVisibility(LinearLayout.GONE);
                s2LL.setVisibility(LinearLayout.GONE);
                s3LL.setVisibility(LinearLayout.GONE);
                stylesPrompt.setVisibility(LinearLayout.GONE);
                stylesFab.setVisibility(FloatingActionButton.GONE);

                if(finalClothesList.size() ==1){
                    finalisedOuterwear.setVisibility(View.GONE);
                    finalisedTop.setVisibility(View.GONE);
                    finalisedBottom.setVisibility(View.GONE);
                    finalisedOnepiece.setImageBitmap(convertToBitmap(finalClothesList.get(0).getImage()));
                }
                else if (finalClothesList.size()==2){
                    if(finalClothesList.get(0).getCategory_id().equals("Top")||finalClothesList.get(0).getCategory_id().equals("Bottom")){
                        finalisedOuterwear.setVisibility(View.GONE);
                        for (int i=0; i<finalClothesList.size();i++){
                            if(finalClothesList.get(i).getCategory_id().equals("Top")){
                                finalisedTop.setImageBitmap(convertToBitmap(finalClothesList.get(i).getImage()));
                            }
                            else{
                                finalisedBottom.setImageBitmap(convertToBitmap(finalClothesList.get(i).getImage()));
                            }
                        }
                    }
                    else{
                        for (int i=0; i<finalClothesList.size();i++){
                            if(finalClothesList.get(i).getCategory_id().equals("Onepiece")){
                                finalisedOnepiece.setImageBitmap(convertToBitmap(finalClothesList.get(i).getImage()));
                            }
                            else{
                                finalisedOuterwear.setImageBitmap(convertToBitmap(finalClothesList.get(i).getImage()));
                            }
                        }
                    }
                }
                else{
                    for (int i=0; i<finalClothesList.size();i++){
                        if(finalClothesList.get(i).getCategory_id().equals("Top")){
                            finalisedTop.setImageBitmap(convertToBitmap(finalClothesList.get(i).getImage()));
                        }
                        else if(finalClothesList.get(i).getCategory_id().equals("Bottom")){
                            finalisedBottom.setImageBitmap(convertToBitmap(finalClothesList.get(i).getImage()));
                        }
                        else{
                            finalisedOuterwear.setImageBitmap(convertToBitmap(finalClothesList.get(i).getImage()));
                        }
                    }
                }
            }

            //wear now bluetooth connection onclicklistener
            TextView sendBluetoothConnection = (TextView) rootView.findViewById(R.id.wearTextF);
            sendBluetoothConnection.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent wearIntent = new Intent(getActivity(), TakeClothesFromWardrobe.class);
                    list = db.getAllClothes(MainActivity.user,outfitFinalList);
                    for (int i=0; i<list.size(); i++){
                        locationList.add(Integer.toString(list.get(i).getLocation()));
                    }
                    wearIntent.putExtra("wearList", locationList);
                    startActivity(wearIntent);
                }
            });
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


            final PopupWindow popWindow = new PopupWindow(layout, 900, 1200, true);
            ListView lv = (ListView) popWindow.getContentView().findViewById(R.id.listview);
            ArrayList<String> s = new ArrayList<String>();
            s.add("Preppy");
            s.add("Chic");
            s.add("Sexy");
            s.add("Urban");
            s.add("Outdoor");
            s.add("Glamourous");
            s.add("Boho");
            s.add("Office");
            StylesAdapter a = new StylesAdapter(getActivity(), s);
            lv.setAdapter(a);
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
                    if(chosenStyle.size()<1){
                        Toast.makeText(getContext(), "No style chosen", Toast.LENGTH_SHORT).show();
                    }
                    else if(chosenStyle.size()>1){
                        Toast.makeText(getContext(), "Choose only 1 style!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (isNetworkAvailable()){
                            new SyncStyles().execute(chosenStyle.get(0));
                        } else{
                            Toast.makeText(getContext(),"No network! Unable to generate recommendation!",Toast.LENGTH_SHORT).show();
                        }
                        s1text.setText(chosenStyle.get(0));
                        popWindow.dismiss();
                        chosenStyle.clear();
                    }
                }
            });
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class StylesAdapter extends ArrayAdapter<String> {
        ArrayList<String> styles = new ArrayList<String>();

        public StylesAdapter(Activity context, ArrayList<String> styles) {
            super(context,0,styles);
            this.styles = styles;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.list_view_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.styleText);
                viewHolder.button = (CheckBox) convertView.findViewById(R.id.styleCheckBox);
                convertView.setTag(viewHolder);
                convertView.setTag(R.id.description, viewHolder.title);
                convertView.setTag(R.id.btn1, viewHolder.button);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    CheckBox checkbox = (CheckBox) buttonView;
                    if(checkbox.isChecked()){
                        chosenStyle.add(styles.get(position));
                    }
                    else{

                        chosenStyle.remove(styles.get(position));
                    }
                }
            });
//        viewHolder.button.setTag(position); // This line is important.
//        viewHolder.button.setChecked(styles.get(position).isSelected());

            viewHolder.title.setText(styles.get(position));

            return convertView;
        }

        private class ViewHolder {

            TextView title;
            CheckBox button;
        }
    }

    private class SyncStyles extends AsyncTask<String, String, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            db = new DatabaseHandler(getActivity());
            ArrayList<String> a = (db.syncStyles(params[0],""));
            db.close();
            return a;
        }


        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            ArrayList<Clothes> styleOutfitList2 = new ArrayList<Clothes>();
            styleOutfitList2 = db.getAllClothes(MainActivity.user, result);
            Toast.makeText(getContext(),Integer.toString(styleOutfitList2.size()),Toast.LENGTH_SHORT).show();
            if (styleOutfitList2.size()==2){

                s1ImageTop.setImageBitmap(convertToBitmap(styleOutfitList2.get(0).getImage()));
                s1ImageBottom.setImageBitmap(convertToBitmap(styleOutfitList2.get(1).getImage()));
                suggestedOutfitList.add(Integer.toString(styleOutfitList2.get(0).getId()));
                suggestedOutfitList.add(Integer.toString(styleOutfitList2.get(1).getId()));
                s1ImageOuter.setVisibility(View.GONE);
                s1ImageOnepiece.setVisibility(View.GONE);
            }
            else if(result.size()==1){
                s1ImageOnepiece.setImageBitmap(convertToBitmap(styleOutfitList2.get(0).getImage()));
                suggestedOutfitList.add(Integer.toString(styleOutfitList2.get(0).getId()));
                s1ImageTop.setVisibility(View.GONE);
                s1ImageBottom.setVisibility(View.GONE);
                s1ImageOuter.setVisibility(View.GONE);
            }
            else if(styleOutfit.size()==3){
                s1ImageTop.setImageBitmap(convertToBitmap(styleOutfitList.get(0).getImage()));
                s1ImageBottom.setImageBitmap(convertToBitmap(styleOutfitList.get(1).getImage()));
                s1ImageOuter.setImageBitmap(convertToBitmap(styleOutfitList.get(2).getImage()));
                suggestedOutfitList.add(Integer.toString(styleOutfitList.get(0).getId()));
                suggestedOutfitList.add(Integer.toString(styleOutfitList.get(1).getId()));
                suggestedOutfitList.add(Integer.toString(styleOutfitList.get(2).getId()));
                s1ImageOnepiece.setVisibility(View.GONE);
            }
            s2LL.setVisibility(View.GONE);
            s3LL.setVisibility(View.GONE);
        }


        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getActivity());
            pd.setMessage("Styling");
            pd.setCancelable(false);
            pd.show();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class SyncColour extends AsyncTask<Integer, String, ArrayList<String>> {
        int type;
        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            db = new DatabaseHandler(getActivity());
            ArrayList<String> a = (db.syncColour());
            type = params[1];
            db.close();
            return a;
        }


        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (type ==1){
                ArrayList<Clothes> tops = mDbHelper.getAllClothes(MainActivity.user, result);
                clothingTop.setImageBitmap(convertToBitmap(tops.get(0).getImage()));
                msg_top.setVisibility(View.GONE);
                recommend_top.setVisibility(View.GONE);
                clothesListId.add(Integer.toString(tops.get(0).getId()));
            }
            else if (type ==2){
                ArrayList<Clothes> bottoms = mDbHelper.getAllClothes(MainActivity.user, result);
                clothingBottom.setImageBitmap(convertToBitmap(bottoms.get(1).getImage()));
                msg_bottom.setVisibility(View.GONE);
                recommend_bottom.setVisibility(View.GONE);
                clothesListId.add(Integer.toString(bottoms.get(1).getId()));
            }

        }


        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(FinalOutfitActivity.this);
            pd.setMessage("Loading");
            pd.setCancelable(false);
            pd.show();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
