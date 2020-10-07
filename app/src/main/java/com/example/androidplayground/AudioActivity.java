package com.example.androidplayground;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.AssetDataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class AudioActivity extends AppCompatActivity {
    
    private PlayerControlView playerview;
    private SimpleExoPlayer simpleplayer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        playerview = findViewById(R.id.exo);
    
        simpleplayer = new SimpleExoPlayer.Builder(this).build();
        playerview.setPlayer(simpleplayer);
        // ---------- changes when reading from DB instead of asset folder
        DataSpec dataSpec = new DataSpec(Uri.parse("asset:///" + "voicemail.mp3"));
        final AssetDataSource assetDataSource = new AssetDataSource(this);
        try {
            assetDataSource.open(dataSpec);
        } catch (AssetDataSource.AssetDataSourceException e) {
            e.printStackTrace();
        }
        // ---------- changes when reading from DB instead of asset folder
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ZooVerse"));
        MediaItem mediaItem = MediaItem.fromUri(assetDataSource.getUri());
        simpleplayer.setMediaItem(mediaItem);
        simpleplayer.prepare();
        //simpleplayer.play();
    }
    
    @Override
    protected void onDestroy() {
        playerview.setPlayer(null);
        simpleplayer.release();
        simpleplayer = null;
        super.onDestroy();
    }
}