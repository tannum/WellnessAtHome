package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bjl.tannum.wellnessathome.Model.XYValue;
import com.bjl.tannum.wellnessathome.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class HeartRateActivity extends AppCompatActivity {


    PointsGraphSeries<DataPoint> xySeries;

    GraphView mScatterPlot;

    LineGraphSeries series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        mScatterPlot = (GraphView)findViewById(R.id.scatter_graph);

        double y,x;
        x = -5.0;

        series = new LineGraphSeries<DataPoint>();
        for(int i = 0;i<500;i++){
            x = x + 0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x,y),true,500);
        }
        mScatterPlot.addSeries(series);


        //createScatterPlot();
    }

    private void createScatterPlot(){
        xySeries = new PointsGraphSeries();

        ArrayList<XYValue> xyValueArray = new ArrayList<>();
        double start = -100;
        double end = 100;
        for(int i = 0;i<40;i++){
            double randomX = new Random().nextDouble();
            double randomY = new Random().nextDouble();
            double x = start + (randomX * (end - start));
            double y = start+(randomY * (end - start));
            xyValueArray.add(new XYValue(x,y));
        }


        xyValueArray =  sortArray(xyValueArray);

        for(int i = 0;i<xyValueArray.size();i++){
            double x = xyValueArray.get(i).getX();

            double y = xyValueArray.get(i).getY();
            xySeries.appendData(new DataPoint(x,y),true,1000);
        }

        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.BLUE);
        xySeries.setSize(20f);

        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScrollableY(true);


        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(150);
        mScatterPlot.getViewport().setMinY(-150);


        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(150);
        mScatterPlot.getViewport().setMinX(-150);

        mScatterPlot.addSeries(xySeries);



//        xySeries.appendData(new DataPoint(1.0,2.0),true,1000);
//        xySeries.appendData(new DataPoint(9.0,5.0),true,1000);
//        xySeries.appendData(new DataPoint(3.0,-9.3),true,1000);
//        xySeries.appendData(new DataPoint(4.0,2.5),true,1000);
//        xySeries.appendData(new DataPoint(2.0,9.7),true,1000);
//        xySeries.appendData(new DataPoint(6.0,11.6),true,1000);





    }



    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size()-1;
        int count = 0;
        Log.d("debug", "sortArray: Sorting the XYArray.");

        while(true){
            m--;
            if(m <= 0){
                m = array.size() - 1;
            }
            Log.d("debug", "sortArray: m = " + m);
            try{
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m-1).getY();
                double tempX = array.get(m-1).getX();
                if(tempX > array.get(m).getX() ){
                    array.get(m-1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m-1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                }
                else if(tempY == array.get(m).getY()){
                    count++;
                    Log.d("debug", "sortArray: count = " + count);
                }

                else if(array.get(m).getX() > array.get(m-1).getX()){
                    count++;
                    Log.d("debug", "sortArray: count = " + count);
                }
                //break when factorial is done
                if(count == factor ){
                    break;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                Log.e("debug", "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }
}
