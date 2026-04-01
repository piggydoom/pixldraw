package com.piggydoom;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
import java.awt.image.RenderedImage;
import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

// import javafx.embed.swing.SwingFXUtils;

public class Controller {

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Canvas canvas;

    @FXML
    public Canvas overlayCanvas;

    @FXML
    public ColorPicker gridColourSetting;

    @FXML
    public CheckMenuItem gridDisplaySetting;

    @FXML
    public ColorPicker colourSel;

    GraphicsContext ctx;
    GraphicsContext ctxOverlay;
    double x = 0;
    double y = 0;
    double Ox = 0;
    double Oy = 0;
    boolean mouseDown = false;

    public void toggleGCSdisp() {
        if (gridColourSetting.isShowing()) {
            gridColourSetting.hide();
        } else {
            gridColourSetting.show();
        }
    }

    public void downloadCanvas() throws IOException{
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(null, writableImage);
        ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", new File("testName.png"));
    }

    public void updateOverlay(MouseEvent event) {
        updateGrid();
        Ox = Math.floor(event.getX() / 10) * 10;
        Oy = Math.floor(event.getY() / 10) * 10;

        ctxOverlay.setFill(Color.CYAN);
        ctxOverlay.fillRect(Ox, Oy, 10, 10);
    }

    public void updateGrid() {
        ctxOverlay.setFill(Color.WHITE);
        ctxOverlay.fillRect(0, 0, 500, 500);

        if (gridDisplaySetting.isSelected()) {
            ctxOverlay.setStroke(gridColourSetting.getValue());
            ctxOverlay.setLineWidth(2);
            for (int gridYhop = 0; gridYhop < 510; gridYhop += 10) {
                ctxOverlay.strokeLine(0, gridYhop, 500, gridYhop);
            }
            for (int gridXhop = 0; gridXhop < 510; gridXhop += 10) {
                ctxOverlay.strokeLine(gridXhop, 0, gridXhop, 500);
            }
        } else {
            ctxOverlay.setFill(Color.WHITE);
            ctxOverlay.fillRect(0, 0, 500, 500);
        }
    }

    public void initialize() {
        ctx = canvas.getGraphicsContext2D();
        ctxOverlay = overlayCanvas.getGraphicsContext2D();
        gridColourSetting.setValue(Color.BLACK);
        updateGrid();
    }

    public void updateColor() {
        Color selectedColor = colourSel.getValue();
        ctx.setFill(selectedColor);
        System.out.println(selectedColor);
    }

    public void drawPixel(MouseEvent event) {
        x = Math.floor(event.getX() / 10) * 10;
        y = Math.floor(event.getY() / 10) * 10;
        ctx.fillRect(x, y, 10, 10);
    }

    

}
