package com.example.android.miwok;

/**
 *  The class represents one vocabulary word in the ListView
 */

public class Word {
    //  English translation for the word
    private String mEnglishTranslation;

    //  Miwok translation for the word
    private String mMiwokTranslation;

    // default constructor, creates a new Word object.
    public Word(String englishTranslation, String miwokTranslation) {
        mEnglishTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    public String getEnglishTranslation() {
        return mEnglishTranslation;
    }

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }
}
