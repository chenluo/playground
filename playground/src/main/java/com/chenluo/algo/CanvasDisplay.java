package com.chenluo.algo;

import java.util.ArrayList;
import java.util.List;

public class CanvasDisplay {
    public static void main(String[] args) {
        RectAngleShape shape1 = new RectAngleShape(0, 0, 1, 2, '1');
        Canvas canvas = new Canvas(4, 5);
        canvas.bindShape(shape1);
        canvas.draw();

        shape1.mov(1, 1);
    }

    static class Canvas {
        int m;
        int n;
        List<RectAngleShape> shapes;
        char[][] realCanvas;

        public Canvas(int m, int n) {
            this.n = n;
            this.m = m;
            shapes = new ArrayList<>();
        }

        public void bindShape(RectAngleShape shape) {
            shapes.add(shape);
            shape.canvas = this;
        }

        public void draw() {
            initCanvas();
            for (RectAngleShape shape : shapes) {
                shape.fill(realCanvas);
            }
            show();
        }

        public void show() {
            for (int i = 0; i < realCanvas.length; i++) {
                System.out.println(realCanvas[i]);
            }
        }

        private void initCanvas() {
            realCanvas = new char[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    realCanvas[i][j] = '0';
                }
            }
        }
    }

    static class RectAngleShape {
        Canvas canvas;
        int posRow;
        int posColumn;
        int width;
        int height;
        char marker;

        public RectAngleShape(int posRow, int posColumn, int width, int height, char marker) {
            this.posColumn = posColumn;
            this.posRow = posRow;
            this.marker = marker;
            this.width = width;
            this.height = height;
        }

        void mov(int targetRow, int targetCol) {
            System.out.println(
                    "Move from [" + this.posRow + "," + this.posColumn + "] to [" + targetRow +
                            "," + targetCol + "]");
            posRow = targetRow;
            posColumn = targetCol;
            System.out.println("repainting");
            canvas.draw();
        }

        public void fill(char[][] realCanvas) {
            for (int i = posRow; i < posRow + height; i++) {
                for (int j = posColumn; j < posColumn + width; j++) {
                    realCanvas[i][j] = marker;
                }
            }
        }
    }
}
