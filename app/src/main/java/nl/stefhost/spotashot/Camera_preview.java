package nl.stefhost.spotashot;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("deprecation")
public class Camera_preview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private String selfie;

    public Camera_preview(Context context, Camera camera, String string) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        selfie = string;
        mHolder.addCallback(this);
    }

    public Camera_preview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            if (Build.MODEL.equals("Nexus 5X") && selfie.equals("nee")){
                mCamera.setDisplayOrientation(270);
            }else{
                mCamera.setDisplayOrientation(90);
            }

            Camera.Parameters params = mCamera.getParameters();
            List<String> focusModes = params.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }

            //params.setColorEffect(Camera.Parameters.EFFECT_SEPIA);
            params.setPictureSize(640, 480);

            String model = Build.MODEL;
            String os = System.getProperty("os.version");

            /*if (model.equals("GT-I9505")){
                if (!os.contains("cyanogenmod")){
                    params.setZoom(1);
                }
            }*/

            mCamera.setParameters(params);
        } catch (IOException e) {
            Log.d("Viewfinder", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        if (mHolder.getSurface() == null){
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // Exception
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e){
            Log.d("Camera Test", "Error starting camera preview: " + e.getMessage());
        }
    }
}
