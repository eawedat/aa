package com.eawedat.myplayer;

import android.app.Dialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean wasPaused = false;
    ListView listView;
    //ImageView btnPlayPause, btnStop;
    TextView songName;
    MediaPlayer mediaPlayer;

    ArrayList<Music> music;

    String[] songNames = {"الفاتحة", "النبأ", "النازعات", "التكوير"};
    String[] songURLS = {"https://server7.mp3quran.net/s_gmd/001.mp3", "https://server7.mp3quran.net/s_gmd/078.mp3", "https://server7.mp3quran.net/s_gmd/079.mp3", "https://server7.mp3quran.net/s_gmd/081.mp3"};

    Dialog dialog;
    TextView txtBackground, txtExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        music = new ArrayList<>();

        for (int i = 0; i < songNames.length; i++) {
            music.add(new Music(songNames[i], songURLS[i]));
        }

        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);

    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return music.size();
        }

        @Override
        public Object getItem(int position) {
            return music.get(position).songName;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final int songID = position;


            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.music_layout, parent, false);


            final ImageView btnPlayPause = view.findViewById(R.id.btnPlayPause);
            final ImageView btnStop = view.findViewById(R.id.btnStop);
            final TextView songName = view.findViewById(R.id.songName);


            btnPlayPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.song);
                    //mediaPlayer.start();

                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(music.get(songID).songURL);
                            mediaPlayer.prepare();
                            btnPlayPause.setImageResource(R.drawable.pause);
                            mediaPlayer.start();
                        } catch (IllegalArgumentException e) {

                            //e.printStackTrace();
                        } catch (SecurityException e) {

                            //e.printStackTrace();
                        } catch (IllegalStateException e) {

                            //e.printStackTrace();
                        } catch (IOException e) {

                            //e.printStackTrace();
                        }

                    } else {
                        if (mediaPlayer.isPlaying()) {
                            btnPlayPause.setImageResource(R.drawable.play);
                            mediaPlayer.pause();
                        } else {
                            btnPlayPause.setImageResource(R.drawable.pause);
                            mediaPlayer.start();
                        }
                    }

                }
            });

            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*if (mediaPlayer != null) {
                        mediaPlayer.stop();

                    }*/
                    btnPlayPause.setImageResource(R.drawable.play);
                    mediaPlayer.stop();
                    mediaPlayer = null;

                }
            });

            songName.setText(position +" "+songID+" "+ music.get(songID).songName);


            return view;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        //dialog = new Dialog(this, R.style.Dialog);
        dialog = new Dialog(this);


        dialog.setTitle("Please Select");
        dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);

        dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.play);

        dialog.setContentView(R.layout.backpressed_dialog);
        txtBackground = dialog.findViewById(R.id.txtBackgroud);
        txtExit = dialog.findViewById(R.id.txtExit);
        txtExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        /*sound.release();
        sound = null;*/
    }
}
