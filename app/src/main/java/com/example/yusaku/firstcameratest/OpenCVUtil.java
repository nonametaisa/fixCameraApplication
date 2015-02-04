package com.example.yusaku.firstcameratest;

/**
 * Created by Yusaku on 2014/12/09.
 */

import android.graphics.Bitmap;
import android.provider.Settings;
import android.util.Log;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.imgproc.Imgproc;

import java.lang.reflect.Array;
import java.util.List;


/**
 * Created by Yusaku on 2014/11/28.
 */
public class OpenCVUtil {

    public static String circleValue;

    public Mat binarize(CameraBridgeViewBase.CvCameraViewFrame src, Mat outputFrame) {

        //平滑化
        Imgproc.GaussianBlur(src.gray(),outputFrame,new Size(5,3),8,6); //OK
      //  Imgproc.Canny(outputFrame, outputFrame, 80, 100); //OK
        Mat edg = src.gray();
        Mat circleMat = new Mat();
        Mat lines = new Mat();

        Imgproc.HoughCircles(outputFrame,circleMat,Imgproc.CV_HOUGH_GRADIENT,2,10,160,50,10,20);

        double[] data = new double[3];


        for(int i = 0 ;i<circleMat.cols();i ++) {

            data =circleMat.get(0, i);
            Point center = new Point(Math.round(data[0]), Math.round(data[1]));
                circleValue = center.toString();

            double raduian = Math.round(data[2]);
            Core.circle(edg, center, (int) raduian, new Scalar(255, 255, 255), 2);

        }

        Point pt1 = new Point();
        Point pt2 = new Point();
        double x , y1  = 0 , y2 = 999;
        double a,b;

        Imgproc.Canny(outputFrame, outputFrame, 80, 100);
        Imgproc.HoughLinesP(outputFrame, lines, 1, Math.PI / 180 , 100, 100 ,10);

        for (int i = 0; i < lines.cols(); i++){
            data = lines.get(0, i);
/*            pt1.x = data[0];
            pt1.y = data[1];
            pt2.x = data[2];
            pt2.y = data[3]; */

            a = (data[1] - data[3]) / (data[0] - data[2]);
            b = data[1] - (a * data[0]);

            pt1.y = y1;
            pt2.y = y2;

            pt1.x = (y1 - b) / a;
            pt2.x = (y2 - b) / a;







            Core.line(edg, pt1, pt2, new Scalar(255, 0, 0), 1);
        }





/*




        Mat circleMat = new Mat();
        Imgproc.HoughCircles(gray,circleMat,Imgproc.CV_HOUGH_GRADIENT,2,10,160,50,10,20);

        Log.d("NANOHA","FATE");

        double[] data = new double[3];

        String s = String.valueOf(circleMat.cols());

        for(int i = 0 ;i<circleMat.cols();i ++) {

                data =circleMat.get(0, i);
                Point center = new Point(Math.round(data[0]), Math.round(data[1]));
                double raduian = Math.round(data[2]);
                Core.circle(gray, center, (int) raduian, new Scalar(255, 255, 255), 2);

        }

        return gray;
        */
        return edg;
    }
    //検出された点と点をつなぐ

    }


