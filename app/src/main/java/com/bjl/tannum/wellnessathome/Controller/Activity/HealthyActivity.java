package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
//import android.graphics.Camera;
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
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class HealthyActivity extends AppCompatActivity implements SurfaceHolder.Callback , Camera.PreviewCallback{

    static final int MY_PERMISSIONS_ACCESS_CAMERA = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView result;
    Button button;
    private Uri imageUri;
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



    TextView HeartReateView;

    Camera mCamera;
    SurfaceView mPreview;
    boolean saveState = false;
    GraphView graphView;
    LineGraphSeries<DataPoint> series;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_healthy);

        CheckCameraPermission();


        mPreview = (SurfaceView)findViewById(R.id.preview);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        HeartReateView = (TextView)findViewById(R.id.txtHeartRate);
        graphView = (GraphView)findViewById(R.id.graph);

        double y,x;
        x = -5.0;
        series = new LineGraphSeries<DataPoint>();
        for(int i = 0;i<500;i++){
            x = x + 0.5;
            y = Math.sin(x);
            series.appendData(new DataPoint(x,y),true,500);
        }
        graphView.addSeries(series);

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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        try{
//            mCamera.setPreviewDisplay(mPreview.getHolder());
//        }catch (Exception e){
//            Log.d("debug",e.toString());
//        }
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



    @Override
    public void onPreviewFrame(final byte[] data, Camera cam) {
//        Log.d("debug","onPreviewFrame !!!!*******");
//        if(bytes != null) {
//            runOnUiThread(new Runnable() {
//                Bitmap bitmap;
//                int w = mCamera.getParameters().getPreviewSize().width;
//                int h = mCamera.getParameters().getPreviewSize().height;
//                int[] rgbs = new int[w * h];
//                public void run() {
//                    decodeYUV420(rgbs, bytes, w, h);
//                    bitmap = Bitmap.createBitmap(rgbs, w, h, Bitmap.Config.ARGB_8888);
//
//                    // นำตัวแปร rgb หรือ bitmap ไปใช้งานได้ตามต้องการ
//                    Log.d("debug","rgb = " + String.valueOf(rgbs.length));
//                }
//            });
//        }

        if (data == null) throw new NullPointerException();
        Camera.Size size = cam.getParameters().getPreviewSize();
        if (size == null) throw new NullPointerException();

        if (!processing.compareAndSet(false, true)) return;

        int width = size.width;
        int height = size.height;

        int imgAvg = ImageProcessing.decodeYUV420SPtoRedAvg(data.clone(), height, width);
        // Log.i(TAG, "imgAvg="+imgAvg);
        if (imgAvg == 0 || imgAvg == 255) {
            processing.set(false);
            return;
        }

        int averageArrayAvg = 0;
        int averageArrayCnt = 0;
        for (int i = 0; i < averageArray.length; i++) {
            if (averageArray[i] > 0) {
                averageArrayAvg += averageArray[i];
                averageArrayCnt++;
            }
        }

        int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
        TYPE newType = currentType;
        if (imgAvg < rollingAverage) {
            newType = TYPE.RED;
            if (newType != currentType) {
                beats++;
                // Log.d(TAG, "BEAT!! beats="+beats);
            }
        } else if (imgAvg > rollingAverage) {
            newType = TYPE.GREEN;
        }

        if (averageIndex == averageArraySize) averageIndex = 0;
        averageArray[averageIndex] = imgAvg;
        averageIndex++;

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

            Log.d("debug","array size = " + String.valueOf(beatsArray.length));
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
            Log.d("debug","HardReate = " + String.valueOf(beatsAvg));
            startTime = System.currentTimeMillis();
            beats = 0;
        }
        processing.set(false);


    }

    public void decodeYUV420(int[] rgb, byte[] yuv420, int width, int height) {
        final int frameSize = width * height;

        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width, u = 0, v = 0;
            for (int i = 0; i < width; i++, yp++) {
                int y = (0xff & ((int) yuv420[yp])) - 16;
                if (y < 0) y = 0;
                if ((i & 1) == 0) {
                    v = (0xff & yuv420[uvp++]) - 128;
                    u = (0xff & yuv420[uvp++]) - 128;
                }

                int y1192 = 1192 * y;
                int r = (y1192 + 1634 * v);
                int g = (y1192 - 833 * v - 400 * u);
                int b = (y1192 + 2066 * u);

                if (r < 0) r = 0; else if (r > 262143) r = 262143;
                if (g < 0) g = 0; else if (g > 262143) g = 262143;
                if (b < 0) b = 0; else if (b > 262143) b = 262143;

                rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2)
                        & 0xff00) | ((b >> 10) & 0xff);
            }
        }
    }
}
