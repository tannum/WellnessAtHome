package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
//import android.graphics.Camera;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjl.tannum.wellnessathome.Controller.Library.ImageProcessing;
import com.bjl.tannum.wellnessathome.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HealthyActivity extends AppCompatActivity implements SurfaceHolder.Callback , Camera.PreviewCallback{

    static final int MY_PERMISSIONS_ACCESS_CAMERA = 0;

    private static final AtomicBoolean processing = new AtomicBoolean(false);


    private static int averageIndex = 0;
    private static final int averageArraySize = 4;
    private static final int[] averageArray = new int[averageArraySize];


    public static enum TYPE {
        GREEN, RED
    };

    private static TYPE currentType = TYPE.GREEN;

    public static TYPE getCurrent() {
        return currentType;
    }

    private static int beatsIndex = 0;
    private static final int beatsArraySize = 3;
    private static final int[] beatsArray = new int[beatsArraySize];
    private static double beats = 0;
    private static long startTime = 0;
    private static int realTimeHeartbeatSize = 50;
    private static final double[] realTimeHeartbeat = new double[realTimeHeartbeatSize];
    private static int realTimeHeartbeatCount = 0;

    LineGraphSeries series;

    TextView HeartReateView;
    Camera mCamera;
    SurfaceView mPreview;
    boolean saveState = false;
    GraphView graphView;
    //LineGraphSeries<DataPoint> series;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_healthy);
        CheckCameraPermission();


        //Mask: Initial value to array
        for(int i = 0;i<realTimeHeartbeatSize;i++) {
           realTimeHeartbeat[i] = 255.0;
        }

        //Init GraphView
        graphView = (GraphView)findViewById(R.id.graph);
        graphView.getViewport().setMinY(220);
        graphView.getViewport().setMaxY(255);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(50);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);

        //Mask: Initial Surface View
        mPreview = (SurfaceView)findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //Mask: TestView
        HeartReateView = (TextView)findViewById(R.id.txtHeartRate);

    }

    public void onResume() {
        Log.d("System","onResume");
        super.onResume();
        mCamera = Camera.open();
        startTime = System.currentTimeMillis();
    }
    public void onPause() {
        Log.d("System","onPause");
        super.onPause();
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    private void CheckCameraPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_ACCESS_CAMERA);
        }
    }

    private void PlotLinearGraph(double[] yValue){


        double[] xValue = new double[realTimeHeartbeatSize];
        for(int i = 0;i<realTimeHeartbeatSize;i++)
        {
            xValue[i] = Double.valueOf(i);
        }
        graphView.removeAllSeries();
        series = new LineGraphSeries<DataPoint>();


        // styling series
        series.setTitle("Random Curve 1");
        series.setColor(Color.DKGRAY);
        //series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);





        for(int i = 0;i<realTimeHeartbeatSize;i++){
            series.appendData(new DataPoint(xValue[i],yValue[i]),true,500);
        }
        graphView.addSeries(series);



    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }
    private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea < resultArea) result = size;
                }
            }
        }

        return result;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Log.d("debug","surfaceChanged*******");
        Camera.Parameters params = mCamera.getParameters();
        //List<Camera.Size> previewSize = params.getSupportedPreviewSizes();
//        List<Camera.Size> pictureSize = params.getSupportedPictureSizes();

        Camera.Size size = getSmallestPreviewSize(width,height,params);
        if(size != null){
            params.setPreviewSize(size.width, size.height);
            //params.setPreviewSize(1,1);
        }



//        params.setPictureSize(pictureSize.get(0).width, pictureSize.get(0).height);
        //params.setPreviewSize(previewSize.get(0).width, previewSize.get(0).height);
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        //params.setJpegQuality(100);
        mCamera.setParameters(params);

        try {
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewDisplay(mPreview.getHolder());
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d("debug","Error ---- " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private static double[] shiftLeft(double[] arr, int shift) {
        double[] tmp = new double[arr.length];
        System.arraycopy(arr, shift, tmp, 0, arr.length-shift);
        System.arraycopy(arr, 0, tmp, arr.length-shift, shift);
        return tmp;
    }

    @Override
    public void onPreviewFrame(final byte[] data, Camera cam) {

        if (data == null) throw new NullPointerException();
        Camera.Size size = cam.getParameters().getPreviewSize();
        if (size == null) throw new NullPointerException();

        if (!processing.compareAndSet(false, true)) return;

        int width = size.width;
        int height = size.height;

        int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
         //Log.d("debug", "********* imgAvg="+imgAvg);
        if (imgAvg == 0 || imgAvg == 255) {
            processing.set(false);
            return;
        }

        int averageArrayAvg = 0;
        int averageArrayCnt = 0;
        for (int i = 0; i < averageArray.length; i++) {
            //Log.d("debug","averageArray["+String.valueOf(i)+"] = " + String.valueOf(averageArray[i]) );
            if (averageArray[i] > 0) {
                averageArrayAvg += averageArray[i];
                averageArrayCnt++;
            }
        }

        int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
        //Log.d("debug","rollingAverage = " + rollingAverage);
        TYPE newType = currentType;
        if (imgAvg < rollingAverage) {
            newType = TYPE.RED;
            if (newType != currentType) {
                beats++;
                 Log.d("debug", "BEAT!! beats="+beats);
            }
        } else if (imgAvg > rollingAverage) {
            newType = TYPE.GREEN;
        }

        if (averageIndex == averageArraySize) averageIndex = 0;
        averageArray[averageIndex] = imgAvg;
        averageIndex++;



        double[] temp = new double[realTimeHeartbeatSize];
        System.arraycopy(realTimeHeartbeat,1,temp,0,realTimeHeartbeatSize-1);
        temp[realTimeHeartbeatSize-1] = Double.valueOf(rollingAverage);
        PlotLinearGraph(temp);
        System.arraycopy(temp,0,realTimeHeartbeat,0,realTimeHeartbeatSize);



        // Transitioned from one state to another to the same
        if (newType != currentType) {
            currentType = newType;
            //image.postInvalidate();
        }

        long endTime = System.currentTimeMillis();
        double totalTimeInSecs = (endTime - startTime) / 1000d;
        if (totalTimeInSecs >= 10) {
            double bps = (beats / totalTimeInSecs);
            int dpm = (int) (bps * 60d);
            if (dpm < 30 || dpm > 180) {
                startTime = System.currentTimeMillis();
                beats = 0;
                processing.set(false);
                return;
            }

            // Log.d(TAG,
            // "totalTimeInSecs="+totalTimeInSecs+" beats="+beats);

            if (beatsIndex == beatsArraySize) beatsIndex = 0;
            beatsArray[beatsIndex] = dpm;
            beatsIndex++;

            int beatsArrayAvg = 0;
            int beatsArrayCnt = 0;

           // Log.d("debug","array size = " + String.valueOf(beatsArray.length));
            for (int i = 0; i < beatsArray.length; i++) {
                if (beatsArray[i] > 0) {
                    beatsArrayAvg += beatsArray[i];
                    beatsArrayCnt++;
                }
            }
            //Mask: Calculate average value
            int beatsAvg = (beatsArrayAvg / beatsArrayCnt);
            //text.setText(String.valueOf(beatsAvg));
            HeartReateView.setText(String.valueOf(beatsAvg));
            //Log.d("debug","HardReate = " + String.valueOf(beatsAvg));
            startTime = System.currentTimeMillis();
            beats = 0;
        }
        processing.set(false);


    }

}
