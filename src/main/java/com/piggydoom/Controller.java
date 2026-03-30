package com.piggydoom;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;



public class Controller {
    @FXML
    public Canvas canvas;


    @FXML
    public void handleMouseMoved(MouseEvent event) {
        // Get the coordinates relative to the Canvas
        double x = event.getX();
        double y = event.getY();
        System.out.println("x= " + x);
        System.out.println("x= " + y);
    }
}
