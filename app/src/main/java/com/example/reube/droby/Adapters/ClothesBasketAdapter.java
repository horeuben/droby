package com.example.reube.droby.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Activities.ClothesBasket;
import com.example.reube.droby.Database.Clothes;
import com.example.reube.droby.Fragments.ClothesFragment;
import com.example.reube.droby.R;

import java.util.ArrayList;

import static android.R.attr.data;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.reube.droby.Fragments.ClothesFragment.clothes;


/**
 * Created by Family on 14/6/2017.
 */

public class ClothesBasketAdapter extends ArrayAdapter<Clothes>  {



    public ClothesBasketAdapter(Activity context, ArrayList<Clothes> clothesBasket) {

        super(context,0,clothesBasket);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.clothes_basket_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.discard = (ImageView) listItemView.findViewById(R.id.trash);
            viewHolder.check = (CheckBox) listItemView.findViewById(R.id.boxx);
            viewHolder.clothes_image = (ImageView) listItemView.findViewById(R.id.selected_clothes);
            viewHolder.description_clothes = (TextView) listItemView.findViewById(R.id.selected_clothes_descr);
            listItemView.setTag(viewHolder);
            listItemView.setTag(R.id.trash, viewHolder.discard);
            listItemView.setTag(R.id.boxx, viewHolder.check);
            listItemView.setTag(R.id.selected_clothes_descr, viewHolder.description_clothes);
        }
        else{
            viewHolder = (ViewHolder) listItemView.getTag();
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), position + " Discarded", Toast.LENGTH_SHORT).show();
                finalViewHolder.check.setChecked(false);
                ClothesBasket.basketList.remove(position);

                notifyDataSetChanged();
            }
        });




        viewHolder.clothes_image.setImageBitmap(convertToBitmap(getItem(position).getImage()));
        viewHolder.description_clothes.setText(getItem(position).getDescription());

//        Clothes currentClothe = getItem(position);
//
//        TextView clothes_description = (TextView) listItemView.findViewById(R.id.selected_clothes_descr);
//
//        clothes_description.setText(currentClothe.getDescription());
//
//        ImageView clothes_image = (ImageView) listItemView.findViewById(R.id.selected_clothes);
//
//        clothes_image.setImageBitmap(convertToBitmap(currentClothe.getImage()));

        return listItemView;

    }

    private class ViewHolder {

        ImageView discard;
        ImageView clothes_image;
        TextView description_clothes;
        CheckBox check;
    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

    //extra stuff to try to reduce bitmap size but doesnt seems like its working
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                          int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

}