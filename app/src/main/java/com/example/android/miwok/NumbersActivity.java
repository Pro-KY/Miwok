/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    // Handles playback of all the sound files
    private MediaPlayer mMediaPlayer;
    private ArrayList<Word> words;

    //Handles playback of all the sound files
    private AudioManager mAudioManager;

    // keep track of your status and react to those changes
    private AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    //restart/resume your sound
                    mMediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    //Loss of audio focus for a long time
                    //Stop playing the sound
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    //Loss of audio focus for a short time
                    //Pause playing the sound
                    mMediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    // Loss of audio focus for a short time.
                    // But one can duck. Lower the volume of playing the sound
                    // Pause playback and reset player to the start of the file. That way, we can
                    // play the word from the beginning when we resume playback.
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create and setup the AudioManager to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Create an arrayList of words
        words = new ArrayList<>();

        words.add(new Word(R.string.number_one, R.string.miwok_number_one,
                R.drawable.number_one, R.raw.number_one));
        words.add(new Word(R.string.number_two, R.string.miwok_number_two,
                R.drawable.number_two, R.raw.number_two));
        words.add(new Word(R.string.number_three, R.string.miwok_number_three,
                R.drawable.number_three, R.raw.number_three));
        words.add(new Word(R.string.number_four, R.string.miwok_number_four,
                R.drawable.number_four, R.raw.number_four));
        words.add(new Word(R.string.number_five, R.string.miwok_number_five,
                R.drawable.number_five, R.raw.number_five));
        words.add(new Word(R.string.number_six, R.string.miwok_number_six,
                R.drawable.number_six, R.raw.number_six));
        words.add(new Word(R.string.number_seven, R.string.miwok_number_seven,
                R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word(R.string.number_eight, R.string.miwok_number_eight,
                R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word(R.string.number_nine, R.string.miwok_number_nine,
                R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word(R.string.number_ten, R.string.miwok_number_ten,
                R.drawable.number_ten, R.raw.number_ten));

        WordAdapter adapter = new WordAdapter(
                this,
                words,
                R.color.category_numbers
        );

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        // set click listener to each item in the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // release media resource if it exists, because we are going to play
                // another sound
                releaseMediaPlayer();

                // get the Word object at the given position when user clicked on the list item
                int audioResourceId = words.get(position).getAudioResourceId();

                // Request audio focus for playback
                if (requestAudioFocus(NumbersActivity.this)) {
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, audioResourceId);
                    mMediaPlayer.start();

                    // setup a listener on the media player, so we can release when the sound
                    // finished playing
                    mMediaPlayer.setOnCompletionListener(NumbersActivity.this);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    // Clean up the media player by releasing its resources.
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // abandon audio focus
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        // When the sound file has finished playing, release the media player resources.
        releaseMediaPlayer();
    }

    // Request audio focus for playback
    private boolean requestAudioFocus(final Context context) {

        int result = mAudioManager.requestAudioFocus(
                afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

}
