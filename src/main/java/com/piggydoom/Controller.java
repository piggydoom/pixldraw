package com.piggydoom;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Controller {

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Canvas canvas;
    
    @FXML
    public ColorPicker colourSel;
    
    GraphicsContext ctx;
    double x = 0; 
    double y = 0;
    boolean mouseDown = false;

    @FXML
    public void initialize() {
        ctx = canvas.getGraphicsContext2D();
        ctx.setFill(Color.WHITE);
        ctx.fillRect(0, 0,  500, 500);
        ctx.setFill(Color.BLACK);
        }

    @FXML
    public void updateColor(){
    Color selectedColor = colourSel.getValue();
    ctx.setFill(selectedColor);
    System.out.println(selectedColor);
    }

    @FXML
    public void drawPixel(MouseEvent event) {
        x = Math.floor(event.getX() / 10) * 10;
        y = Math.floor(event.getY() / 10) * 10;
        ctx.fillRect(x, y, 10, 10);
    }
}
