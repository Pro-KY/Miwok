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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener{

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

        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(
                this,
                words,
                R.color.category_family
        );

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        // set listener to each item in the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // release media resource if it exists, because we are going to play
                // another sound
                releaseMediaPlayer();

                // get the Word object at the given position when user clicked on the list item
                int audioResourceId = words.get(position).getAudioResourceId();

                // Request audio focus for playback
                if (requestAudioFocus(FamilyActivity.this)) {
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, audioResourceId);
                    mMediaPlayer.start();

                    // setup a listener on the media player, so we can release when the sound
                    // finished playing
                    mMediaPlayer.setOnCompletionListener(FamilyActivity.this);
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

    // callback method, runs when playback audio file is finished
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
