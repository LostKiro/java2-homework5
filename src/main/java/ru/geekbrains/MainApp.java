package ru.geekbrains;

public class MainApp {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {

        float[] arr1 = new float[SIZE];
        float[] arr2 = new float[SIZE];

        firstMethod(arr1);
        secondMethod(arr2);
    }

    public static void firstMethod(float[] arr) {

        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long firstMethod = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                    Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() - firstMethod) + ".ms");
    }

    public static void secondMethod(float[] arr) {
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis();

        float[] leftHalf = new float[HALF];
        float[] rightHalf = new float[HALF];

        System.arraycopy(arr, 0, leftHalf, 0, HALF);
        System.arraycopy(arr, 0, rightHalf, 0, HALF);

        Thread tr1 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                leftHalf[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });
        Thread tr2 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                rightHalf[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) *
                        Math.cos(0.4f + i / 2));
            }
        });
        tr1.start();
        tr2.start();

        try {
            tr1.join();
            tr2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(leftHalf, 0, arr, 0, HALF);
        System.arraycopy(rightHalf, 0, arr, HALF, HALF);

        System.out.println(System.currentTimeMillis() - startTime);

    }
}


