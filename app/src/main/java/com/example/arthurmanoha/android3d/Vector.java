package com.example.arthurmanoha.android3d;

public class Vector {

    private float x, y, z;

    public Vector() {
        this(0, 0, 0);
    }

    public Vector(float newX, float newY, float newZ) {
        x = newX;
        y = newY;
        z = newZ;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    /**
     * Changes this vector.
     */
    public void add(Vector v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }

    /**
     * Returns a newly created vector that is the sum of this and the parameter.
     */
    public Vector sum(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    /**
     * Returns a newly created vector that is the scalar product of this and the parameter.
     */
    public Vector mult(float fact) {
        return new Vector(fact * x, fact * y, fact * z);
    }

    /**
     * Rotate the vector around the global Z axis.
     */
    public void rotateGlobalZ(float angle) {
        float oldX = x;
        float oldY = y;
        x = (float) (oldX * Math.cos(angle) - oldY * Math.sin(angle));
        y = (float) (oldX * Math.sin(angle) + oldY * Math.cos(angle));
    }

}
