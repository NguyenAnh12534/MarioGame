package org.example.util;

import org.lwjgl.glfw.GLFW;

public class MouseListener {
    private static MouseListener instace;
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0;
        this.scrollY = 0;
        this.xPos = 0;
        this.yPos = 0;
        this.lastY = this.yPos;
        this.lastX = this.xPos;
    }

    public static MouseListener get() {
        if(instace == null) {
            instace = new MouseListener();
        }

        return instace;
    }

    public static void mousePosCallBack(long windowId, double xPos, double yPos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;

        get().xPos = xPos;
        get().yPos = yPos;
        get().isDragging = get().mouseButtonPressed[0];
    }

     public static void  mouseButtonCallBack(long windowId, int button, int action, int mods) {
        if(button < get().mouseButtonPressed.length) {
            switch (action) {
                case GLFW.GLFW_PRESS:
                    get().mouseButtonPressed[button] = true;
                    break;
                case GLFW.GLFW_RELEASE:
                    get().mouseButtonPressed[button] = false;
                    break;
                default:
                    break;
            }
        }
    }

    public static void mouseScrollCallBack(long windowId, double  scrollX, double  scrollY) {
        get().scrollX = scrollX;
        get().scrollY = scrollY;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    public static float getDx() {
        return (float) (get().lastX - get().xPos);
    }

    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }

    public static boolean isMouseButtonDown(int button) {
        return get().mouseButtonPressed[button];
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }
}
