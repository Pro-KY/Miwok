package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesFragment extends Fragment {

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

    /**
     * This listener gets triggered when the MediaPlayer has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    public PhrasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the AudioManager to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create an arrayList of words
        words = new ArrayList<>();

        words.add(new Word(R.string.phrase_where_are_you_going,
                R.string.miwok_phrase_where_are_you_going, R.raw.phrase_where_are_you_going));
        words.add(new Word(R.string.phrase_what_is_your_name,
                R.string.miwok_phrase_what_is_your_name, R.raw.phrase_what_is_your_name));
        words.add(new Word(R.string.phrase_my_name_is,
                R.string.miwok_phrase_my_name_is, R.raw.phrase_my_name_is));
        words.add(new Word(R.string.phrase_how_are_you_feeling,
                R.string.miwok_phrase_how_are_you_feeling, R.raw.phrase_how_are_you_feeling));
        words.add(new Word(R.string.phrase_im_feeling_good,
                R.string.miwok_phrase_im_feeling_good, R.raw.phrase_im_feeling_good));
        words.add(new Word(R.string.phrase_are_you_coming,
                R.string.miwok_phrase_are_you_coming, R.raw.phrase_are_you_coming));
        words.add(new Word(R.string.phrase_yes_im_coming,
                R.string.miwok_phrase_yes_im_coming, R.raw.phrase_yes_im_coming));
        words.add(new Word(R.string.phrase_im_coming,
                R.string.miwok_phrase_im_coming, R.raw.phrase_im_coming));
        words.add(new Word(R.string.phrase_lets_go,
                R.string.miwok_phrase_lets_go, R.raw.phrase_lets_go));
        words.add(new Word(R.string.phrase_come_here,
                R.string.miwok_phrase_come_here, R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(
                getActivity(),
                words,
                R.color.category_phrases
        );

        ListView listView = (ListView) rootView.findViewById(R.id.list);
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
                if (requestAudioFocus(getActivity())) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), audioResourceId);
                    mMediaPlayer.start();

                    // setup a listener on the media player, so we can release when the sound
                    // finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
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

