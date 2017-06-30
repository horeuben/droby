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
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reube.droby.Activities.Clothes;
import com.example.reube.droby.R;

import java.util.ArrayList;


/**
 * Created by Family on 14/6/2017.
 */

public class ClothesBasketAdapter extends ArrayAdapter<Clothes>  {




    public ClothesBasketAdapter(Activity context, ArrayList<Clothes> clothes) {

        super(context,0,clothes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.clothes_basket_item, parent, false);
        }

        Clothes currentClothe = getItem(position);

        TextView clothes_description = (TextView) listItemView.findViewById(R.id.selected_clothes_descr);

        clothes_description.setText(currentClothe.getClothesDescription());

        ImageView clothes_image = (ImageView) listItemView.findViewById(R.id.selected_clothes);

        clothes_image.setImageResource(currentClothe.getImageResourceId());
//        clothes_image.setImageBitmap(
//                decodeSampledBitmapFromResource(getContext().getResources(), currentClothe.getImageResourceId(), 100, 100));
        return listItemView;

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
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        ViewHolder mainViewholder = null;
//        if(convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.clothes_basket_item, parent, false);
//            ViewHolder viewHolder = new ViewHolder();
//            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.add_to_cart);
//            viewHolder.title = (TextView) convertView.findViewById(R.id.description);
//            viewHolder.button = (Button) convertView.findViewById(btn1);
//            convertView.setTag(viewHolder);
//
//        }
//        mainViewholder = (ViewHolder) convertView.getTag();
//        mainViewholder.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
//                Button button1 = (Button) v.findViewById(R.id.btn1);
//                button1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent clothesBasketIntent = new Intent(getContext(), ClothesBasket.class);
//                        getContext().startActivity(clothesBasketIntent);
//                    }
//                });
//
//            }
//        });
//        //mainViewholder.title.setText(getItem(position));
//
//        Clothes currentClothe = getItem(position);
//
//        TextView activity_clothes_description = (TextView) convertView.findViewById(R.id.description);
//
//        activity_clothes_description.setText(currentClothe.getClothesDescription());
//
//        ImageView clothes_image = (ImageView) convertView.findViewById(R.id.clothes_image);
//
//        clothes_image.setImageResource(currentClothe.getImageResourceId());
//
//        return convertView;
//    }
//
//    public class ViewHolder {
//
//        ImageView thumbnail;
//        TextView title;
//        Button button;
//    }
//
//

}