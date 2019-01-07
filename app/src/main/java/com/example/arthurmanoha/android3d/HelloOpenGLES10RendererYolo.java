package com.example.arthurmanoha.android3d;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES10.glEnable;
import static javax.microedition.khronos.opengles.GL10.GL_DEPTH_TEST;

public class HelloOpenGLES10RendererYolo implements GLSurfaceView.Renderer {

    private FloatBuffer triangleVB;
    private FloatBuffer squareVB;
    private FloatBuffer cubeVB;
    private float angle;
    private int width, height;

    // Point of view; the two images will be rendered from either side of this point.
    private Empty viewerEmpty;


    public HelloOpenGLES10RendererYolo(int widthParam, int heightParam) {
        width = widthParam;
        height = heightParam;
    }

    public void setEmpty(Empty e) {
        viewerEmpty = e;
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        glEnable(GL_DEPTH_TEST);

        // Set the background frame color
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        // initialize the triangle vertex array
        initShapes();

        // Enable use of vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // Init the orientation of the triangle
        angle = 0;
    }

    public void onDrawFrame(GL10 gl) {
        // Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);


        long time = SystemClock.uptimeMillis();
        angle = 0.0009f * ((int) time);
        viewerEmpty.resetRotation();
//        viewerEmpty.rotateGlobalZ(angle);
        viewerEmpty.rotateGlobalZAroundTarget(angle);

        float eyeX, eyeY, eyeZ;

//        eyeX = (float) (5 * Math.sin(angle));
//        eyeY = 0;
//        eyeZ = (float) (5 * Math.cos(angle));

        eyeX = viewerEmpty.getLeftPos().getX();
        eyeY = viewerEmpty.getLeftPos().getY();
        eyeZ = viewerEmpty.getLeftPos().getZ();
//        eyeX = viewerEmpty.getPos().getX();
//        eyeY = viewerEmpty.getPos().getY();
//        eyeZ = viewerEmpty.getPos().getZ();


        // Left image
        gl.glLoadIdentity();
        gl.glViewport(0, 0, width / 2, height);
        GLU.gluLookAt(gl, eyeX, eyeY, eyeZ,
                viewerEmpty.getTarget().getX(), viewerEmpty.getTarget().getY(), viewerEmpty.getTarget().getZ(),
                viewerEmpty.getVertic().getX(), viewerEmpty.getVertic().getY(), viewerEmpty.getVertic().getZ());
        drawHalfFrame(gl);

        eyeX = viewerEmpty.getRightPos().getX();
        eyeY = viewerEmpty.getRightPos().getY();
        eyeZ = viewerEmpty.getRightPos().getZ();

        // Right image
        gl.glLoadIdentity();
        gl.glViewport(width / 2, 0, width / 2, height);
        GLU.gluLookAt(gl, eyeX, eyeY, eyeZ,
                viewerEmpty.getTarget().getX(), viewerEmpty.getTarget().getY(), viewerEmpty.getTarget().getZ(),
                viewerEmpty.getVertic().getX(), viewerEmpty.getVertic().getY(), viewerEmpty.getVertic().getZ());
        drawHalfFrame(gl);


    }

    private void drawHalfFrame(GL10 gl) {

        // Draw the triangle
        gl.glColor4f(0.63671875f, 0.76953125f, 0.22265625f, 0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleVB);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        // Draw the square
        gl.glColor4f(1.0f, 0, 0, 0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, squareVB);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        // Draw the cube
        gl.glColor4f(0.0f, 0, 1.0f, 0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVB);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 14);
    }

    public void onSurfaceChanged(GL10 gl, int widthParam, int heightParam) {

        width = widthParam;
        height = heightParam;

        gl.glViewport(0, 0, widthParam, heightParam);

        // make adjustments for screen ratio
        float ratio = (float) (widthParam / heightParam) / 2; // For the double image
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix
    }

    private void initShapes() {

        float triangleCoords[] = {
                // X, Y, Z
                -0.5f, -0.25f, 0,
                0.5f, -0.25f, 0,
                0.0f, 0.559016994f, 0
        };
        float squareCoords[] = {
                -0.5f, -0.5f, 1,
                -0.5f, 0.5f, 1,
                0.5f, -0.5f, 1,
                0.5f, 0.5f, 1
        };
        float cubeCoords[] = {
                -1.f, 1.f, 1.f,     // Front-top-left
                1.f, 1.f, 1.f,      // Front-top-right
                -1.f, -1.f, 1.f,    // Front-bottom-left
                1.f, -1.f, 1.f,     // Front-bottom-right
                1.f, -1.f, -1.f,    // Back-bottom-right
                1.f, 1.f, 1.f,      // Front-top-right
                1.f, 1.f, -1.f,     // Back-top-right
                -1.f, 1.f, 1.f,     // Front-top-left
                -1.f, 1.f, -1.f,    // Back-top-left
                -1.f, -1.f, 1.f,    // Front-bottom-left
                -1.f, -1.f, -1.f,   // Back-bottom-left
                1.f, -1.f, -1.f,    // Back-bottom-right
                -1.f, 1.f, -1.f,    // Back-top-left
                1.f, 1.f, -1.f      // Back-top-right
        };

        // initialize vertex Buffer for triangle
        ByteBuffer vbb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);
        vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
        triangleVB = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
        triangleVB.put(triangleCoords);    // add the coordinates to the FloatBuffer
        triangleVB.position(0);            // set the buffer to read the first coordinate

        // ... and for square
        ByteBuffer vbbSquare = ByteBuffer.allocateDirect(squareCoords.length * 4);
        vbbSquare.order(ByteOrder.nativeOrder());
        squareVB = vbbSquare.asFloatBuffer();
        squareVB.put(squareCoords);
        squareVB.position(0);

        // ... and for cube
        ByteBuffer vbbCube = ByteBuffer.allocateDirect(cubeCoords.length * 4);
        vbbCube.order(ByteOrder.nativeOrder());
        cubeVB = vbbCube.asFloatBuffer();
        cubeVB.put(cubeCoords);
        cubeVB.position(0);

    }

}