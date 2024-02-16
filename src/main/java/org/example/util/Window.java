package org.example.util;

import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
public class Window {
    private int width, height;
    private String title;
    private long glfwWindow;
    private static Window window = null;
    private float r, g, b, a;
    private boolean isFadingToBlack;


    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
        this.r = 1;
        this.g = 1;
        this.b = 1;
        this.a = 1;
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        //Free the memory
        Callbacks.glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and free the erro callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // Setup mouse handler callbacks
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallBack);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallBack);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallBack);

        // Setup keyboard handler callbacks
        glfwSetKeyCallback(glfwWindow, KeyListener::keyInputCallBack);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            GL11.glClearColor(r, g, b, a);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            if(isFadingToBlack) {
                this.r -= 0.01f;
                this.g -= 0.01f;
                this.b -= 0.01f;
                this.a -= 0.01f;

                this.isFadingToBlack = false;
            }

            if(KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                System.out.println("Space key is pressed");
                isFadingToBlack = true;
            }

            if(MouseListener.isMouseButtonDown(0)) {
                System.out.println("Left mouse is pressed");
            }

            if(MouseListener.isMouseButtonDown(1)) {
                System.out.println("Right mouse is pressed");
            }

            if(MouseListener.isMouseButtonDown(2)) {
                System.out.println("Middle mouse is pressed");
            }

            if(MouseListener.isDragging()) {
                System.out.println("Mouse is being dragged");
            }

            glfwSwapBuffers(glfwWindow);
        }
    }
}
