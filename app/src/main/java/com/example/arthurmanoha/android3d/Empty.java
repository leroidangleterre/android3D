package com.example.arthurmanoha.android3d;

/**
 * This class describes the too that positions and orients a solid or a camera in space.
 */

public class Empty {

    private Vector pos;
    private Vector target, left, vertic; // Given in the reference of the empty, so always of norm 1.
    private float width; // Half-distance between the two cameras
    private Vector posLeft, posRight;

    public Empty() {
        pos = new Vector();
        resetRotation();
        width = 0.3f;
        computeLeftRightPos();
    }

    public void resetRotation() {
        target = new Vector(1, 0, 0);
        left = new Vector(0, 1, 0);
        vertic = new Vector(0, 0, 1);
    }

    private void computeLeftRightPos() {
        posLeft = pos.sum(left.mult(width));
        posRight = pos.sum(left.mult(-width));
    }

    /**
     * Set the position of the origin.
     */
    public void setPos(float x, float y, float z) {
        pos = new Vector(x, y, z);
        computeLeftRightPos();
    }

    public Vector getPos() {
        return pos;
    }

    /**
     * Set the position of the target.
     */
    public void setTarget(float x, float y, float z) {
        target = new Vector(x, y, z);
        computeLeftRightPos();
    }

    /**
     * Get the position of a point located on the left of this empty.
     */
    public Vector getLeftPos() {
        return posLeft;
    }

    /**
     * Get the position of a point located on the left of this empty.
     */
    public Vector getRightPos() {
        return posRight;
    }

    public Vector getTarget() {
        return target;
    }

    public Vector getVertic() {
        return vertic;
    }

    /**
     * Rotate the empty around the Z axis, without changing its center position.
     */
    public void rotateGlobalZ(float angle) {

        target.rotateGlobalZ(angle);
        left.rotateGlobalZ(angle);
        vertic.rotateGlobalZ(angle);
    }

    /**
     * Rotate the empty around the Z axis and around the target.
     */
    public void rotateGlobalZAroundTarget(float angle) {
        float dx = 1;
        this.pos.add(target);
        this.rotateGlobalZ(angle);
        this.pos.add(target.mult(-1));
    }

}
