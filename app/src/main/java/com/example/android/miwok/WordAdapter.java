package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 *  Custom ArrayAdapter that provide the layout for each list item based on the list of objects Word
 */

public class WordAdapter  extends ArrayAdapter<Word> {

    private int mlistItemColorId;

    public WordAdapter(Context context, ArrayList<Word> words, int color) {
        super(context, 0, words);
        mlistItemColorId = color;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,
                    parent,
                    false
            );
        }

        View textContainer = listItemView.findViewById(R.id.text_container);

        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mlistItemColorId);
        textContainer.setBackgroundColor(color);

        // Get the object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getEnglishTranslation());
        Log.d("englishText", currentWord.getEnglishTranslation());

        // Find the TextView in the list_item.xml layout with the ID english_text_view
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english_text_view);
        englishTextView.setText(currentWord.getMiwokTranslation());
        Log.d("miwokTranslation", currentWord.getMiwokTranslation());

        // if getImageResourceId() returns 0, it means that there is no image, so we don't
        // need to set image to the ImageView, otherwise set an image to the ImageView
        if(currentWord.getImageResourceId() != 0) {
            // Find the ImageView in the list_item.xml layout with the ID image
            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
            imageView.setImageResource(currentWord.getImageResourceId());
        }

        return listItemView;
    }
}
