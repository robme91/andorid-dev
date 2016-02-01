package de.beuth_hochschule.s790642.recorder;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 26.01.2016.
 */
public class Player {

    private final Context parentContext;
    private MediaPlayer player;
    private String audioFilePath="";
    private boolean isPaused = false;
    private boolean isPlaying = false;

    public Player(final Context parentContext){
        this.parentContext = parentContext;
        this.player = new MediaPlayer();
        this.player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            }
        });

    }

    public void setAudioFilePath(final String path){
        if(path != null && !path.isEmpty()){
            this.audioFilePath = path;
        }else{
            Toast.makeText(parentContext, "Empty Path passed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets the path to the given FileName. Works only if the name includes the file ending.
     * This function or setAudioFilePath() must be called before start playing.
     * @param fileName The FileName with the type ending
     */
    public void setAudioFileName(final String fileName){
        this.audioFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath()
                + "/recorder/" + fileName;
    }

    public void playFile(final String fileName){
        if (!this.isPlaying) {
            if(!isPaused) {
                setAudioFileName(fileName);
                this.player = new MediaPlayer();
                try {
                    player.setDataSource(this.audioFilePath);
                    player.prepare();
                    this.isPlaying = true;
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(parentContext, "File could not loaded. Maybe wrong path.", Toast.LENGTH_SHORT).show();
                }
            }else{
                this.isPlaying = true;
                player.start();
            }
        }
    }

    public void stopFile(){
        if(this.isPlaying || this.isPaused) {
            this.isPlaying = false;
            this.isPaused = false;
            player.stop();
            player.reset();
            player.release();
        }
    }

    public void pauseFile(){
        if(this.isPlaying) {
            isPaused = true;
            this.isPlaying = false;
            player.pause();
        }
    }

    /**
     * Gets all Filenames of the Directory of the recorder. Not the paths.
     * @return Filenames
     */
    public List<String> getAudioFiles(){
        final List<String> fileNames = new ArrayList<String>();
        final String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/recorder/";
        final File dir = new File(dirPath);
        final File [] files = dir.listFiles();
        //check if directory is empty
        if(files == null || files.length < 1){
            fileNames.add("Empty Directory");
            return fileNames;
        }
        for(final File f : files){
            fileNames.add(f.getName());
        }
        return fileNames;
    }

    public boolean isPlaying(){
        return this.isPlaying;
    }

    public void resetPlayer(){
        player.reset();
        player.release();
    }

}
