package com.example.arthurmanoha.android3d;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class HelloOpenGLES10 extends Activity {

    private GLSurfaceView mGLView;
    private Empty userEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userEmpty = new Empty();
        userEmpty.setPos(0, 0, -5);
        userEmpty.setTarget(1, 0, 0);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new HelloOpenGLES10SurfaceView(this, userEmpty);
        setContentView(mGLView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }
}

class HelloOpenGLES10SurfaceView extends GLSurfaceView {

    public HelloOpenGLES10SurfaceView(Context context, Empty e) {
        super(context);

        // Set the Renderer for drawing on the GLSurfaceView
        HelloOpenGLES10RendererYolo renderer = new HelloOpenGLES10RendererYolo(getWidth(), getHeight());
        renderer.setEmpty(e);
        setRenderer(renderer);
    }
}