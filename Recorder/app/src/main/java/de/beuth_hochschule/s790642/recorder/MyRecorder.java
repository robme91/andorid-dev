package de.beuth_hochschule.s790642.recorder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Robin on 16.01.2016.
 */
public class MyRecorder {

    private final Context parentContext;
    private MediaRecorder recorder;
    private boolean isRecording = false;
    private final String audioPath;
    private final String AUDIO_FORMAT = ".m4a";
    private String audioFilePath;
    private String audioFileName="";

    public MyRecorder(final Context parentContext){
        this.parentContext = parentContext;
        this.audioFilePath = "";
        this.audioPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/recorder/";
        //create Folder if its not there
        File storageFolder = new File(audioPath);
        storageFolder.mkdirs();
    }

    public void setAudioFileName (final String audioFileName){
        this.audioFileName = audioFileName + AUDIO_FORMAT;
        this.audioFilePath = this.audioPath + audioFileName + this.AUDIO_FORMAT;
    }

    public String getAudioFileName (){
        return this.audioFileName;
    }

    /**
     * starts Recorder with the given audioFilePath,
     * if the device has a microphone and this Instance does not record something.
     */
    public void startRecord (final String audioFileName) {
        if(hasMicrophone()) {
            if (!isRecording) {
                this.recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                recorder.setAudioEncodingBitRate(16);
                recorder.setAudioSamplingRate(44100);

                setAudioFileName(audioFileName);
                recorder.setOutputFile(this.audioFilePath);
                try {
                    recorder.prepare();
                } catch (IOException e) {
                    Toast.makeText(parentContext, "prepare geht nicht", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                recorder.start();   // Recording is now started
                isRecording = true;
            } else {
                Toast.makeText(parentContext, "Already Recording", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(parentContext, "No Microphone Package installed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * stops the Recorder and release it.
     */
    public void stopRecord() {
        if(isRecording) {
            this.recorder.stop();
            isRecording = false;
            this.recorder.reset();
            this.recorder.release();
        }else{
            Toast.makeText(parentContext, "Nothing is recording", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Pause the recording
     */
    public void pauseRecord() {
        //TODO impl this
    }

    //TODO if time, impl cancel current record


    private boolean hasMicrophone() {
        PackageManager pmanager = parentContext.getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }

    /**
     *  checks if a file already exists with the given Name.
     *  Looking up in default Storage Directory of this app.
     *  returns true if it exists.
     */
    public boolean isFileNameAlreadyExistent(final String fileName){
        setAudioFileName(fileName);
        final File file = new File(this.audioFilePath);
        return file.exists();
    }
        
}
