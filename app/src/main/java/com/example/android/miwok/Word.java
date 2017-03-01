package com.example.android.miwok;

/**
 *  The class represents one vocabulary word in the ListView
 */

public class Word {
    // English translation for the word
    private String mEnglishTranslation;

    // Miwok translation for the word
    private String mMiwokTranslation;

    // Corresponding image to the word
    private int mImageId;


    // default constructor, creates a new Word object.
    public Word(String englishTranslation, String miwokTranslation) {
        mEnglishTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    public Word(String englishTranslation, String miwokTranslation, int imageId) {
        mEnglishTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageId = imageId;
    }


    public String getEnglishTranslation() {
        return mEnglishTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return mImageId;
    }
}
