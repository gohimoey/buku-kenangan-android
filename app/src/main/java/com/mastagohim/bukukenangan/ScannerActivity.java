package com.mastagohim.bukukenangan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

/**
 * QR Scanner untuk Android 16+ (Camera API legacy)
 * Menggunakan Camera API yang tersedia sejak API 1
 */
public class ScannerActivity extends Activity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private android.hardware.Camera camera;
    private boolean previewRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        surfaceView = new SurfaceView(this);
        FrameLayout layout = new FrameLayout(this);
        layout.addView(surfaceView);
        setContentView(layout);
        
        surfaceView.getHolder().addCallback(this);
        surfaceView.setKeepScreenOn(true);
        
        // Auto-start camera
        layout.post(() -> {
            try {
                startCamera();
            } catch (Exception e) {
                finish();
            }
        });
    }

    private void startCamera() {
        camera = android.hardware.Camera.open();
        if (camera != null) {
            camera.setPreviewDisplay(surfaceView.getHolder());
            camera.startPreview();
            previewRunning = true;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Camera dijalankan di onStart
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (previewRunning && camera != null) {
            camera.stopPreview();
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            previewRunning = false;
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            previewRunning = false;
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}