package me.leon.skeetgui.gui.utils;

public class MathUtils implements IUtil {
    public static double clamp(double min, double max, double val) {
        return Math.min(max, Math.max(min, val));
    }

    public static int argMin(double[] arr) {
        int ret = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] < arr[ret]) {
                ret = i;
            }
        }
        return ret;
    }

    public static int argMin(float[] arr) {
        int ret = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] < arr[ret]) {
                ret = i;
            }
        }
        return ret;
    }

    public static int argMin(int[] arr) {
        int ret = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] < arr[ret]) {
                ret = i;
            }
        }
        return ret;
    }
}
