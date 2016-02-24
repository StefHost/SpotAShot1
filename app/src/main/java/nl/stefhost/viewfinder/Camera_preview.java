package nl.stefhost.viewfinder;

import android.content.Context;
import android.hardware.Camera;
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

    public Camera_preview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public Camera_preview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.setDisplayOrientation(90);

            Camera.Parameters params = mCamera.getParameters();
            List<String> focusModes = params.getSupportedFocusModes();
            if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }

            List<String> flashModes = params.getSupportedFlashModes();
            if (flashModes != null){
                if (flashModes.contains(Camera.Parameters.FLASH_MODE_AUTO)){
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                }
            }

            //params.setColorEffect(Camera.Parameters.EFFECT_SEPIA);
            params.setPictureSize(640, 480);

            String os_version = System.getProperty("os.version");
            Log.d("Viewfinder", os_version);
            if (os_version.contains("GEENIDEE")){
                params.setZoom(1);
            }

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
