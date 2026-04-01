package com.piggydoom;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
// import java.awt.image.RenderedImage;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
// import javafx.embed.swing.SwingFXUtils;

public class Controller {

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Canvas canvas;

    @FXML
    public Canvas gridCanvas;

    @FXML
    public ColorPicker gridColourSetting;

    @FXML
    public CheckMenuItem gridDisplaySetting;

    @FXML
    public ColorPicker colourSel;

    GraphicsContext ctx;
    GraphicsContext ctxGrid;
    double x = 0;
    double y = 0;
    boolean mouseDown = false;

    public void toggleGCSdisp() {
        if (gridColourSetting.isVisible()) {
            gridColourSetting.hide();
        } else {
            gridColourSetting.show();
        }
    }

    public void downloadCanvas(){
        // WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        // canvas.snapshot(null, writableImage);
        // RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
        System.out.println(System.getProperty("javafx.runtime.version"));

    }

    public void updateGrid() {
        ctxGrid.setFill(Color.WHITE);
        ctxGrid.fillRect(0, 0, 500, 500);

        if (gridDisplaySetting.isSelected()) {
            ctxGrid.setStroke(gridColourSetting.getValue());
            ctxGrid.setLineWidth(2);
            for (int gridYhop = 0; gridYhop < 510; gridYhop += 10) {
                ctxGrid.strokeLine(0, gridYhop, 500, gridYhop);
            }
            for (int gridXhop = 0; gridXhop < 510; gridXhop += 10) {
                ctxGrid.strokeLine(gridXhop, 0, gridXhop, 500);
            }
        } else {
            ctxGrid.setFill(Color.WHITE);
            ctxGrid.fillRect(0, 0, 500, 500);
        }
    }

    @FXML
    public void initialize() {
        ctx = canvas.getGraphicsContext2D();
        ctxGrid = gridCanvas.getGraphicsContext2D();
        gridColourSetting.setValue(Color.BLACK);
        updateGrid();
    }

    @FXML
    public void updateColor() {
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
