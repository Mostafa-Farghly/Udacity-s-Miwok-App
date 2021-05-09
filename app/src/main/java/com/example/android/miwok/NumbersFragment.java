package com.example.android.miwok;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    /** Handles audio interruptions */
    private AudioManager mAudioManager;

    /** Sets the playback attributes for the media player */
    private AudioAttributes mPlaybackAttributes;

    /** Handles playback of all sound files */
    private MediaPlayer mMediaPlayer = null;

    /** Requests the audio focus */
    private AudioFocusRequest mAudioFocusRequest;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed playing the
     * audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener =
            new MediaPlayer.OnCompletionListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onCompletion(MediaPlayer mp) {
                    releaseMediaPlayer();
                }
            };


    /**
     * This listener gets triggered when the audio focus changes.
     */
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_GAIN:
                            mMediaPlayer.start();
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS:
                            releaseMediaPlayer();
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                            mMediaPlayer.pause();
                            mMediaPlayer.seekTo(0);
                            break;
                    }
                }
            };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Get the audio system service for the audio manager
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Initialize the audio playback attributes
        mPlaybackAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        mAudioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                .setOnAudioFocusChangeListener(mAudioFocusChangeListener)
                .setAudioAttributes(mPlaybackAttributes)
                .setWillPauseWhenDucked(true)
                .build();

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("one", "lutti",
                R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko",
                R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu",
                R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa",
                R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka",
                R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka",
                R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku",
                R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta",
                R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e",
                R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha",
                R.drawable.number_ten, R.raw.number_ten));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        ListView listView = rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word wordItem = words.get(position);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mAudioFocusRequest);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), wordItem.getAudioResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void releaseMediaPlayer() {
        mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest);

        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}