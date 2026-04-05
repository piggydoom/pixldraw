package com.piggydoom;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.layout.AnchorPane;
import java.awt.image.BufferedImage;
import javafx.scene.control.TextField;

import javafx.embed.swing.SwingFXUtils;

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
    public CheckMenuItem gridXYLines;

    @FXML
    public ColorPicker colourSel;

    @FXML
    private TextField canvasSize;

    int cS = 500;
    GraphicsContext ctx;
    GraphicsContext ctxOverlay;
    double x = 0;
    double y = 0;
    double Ox = 0;
    double Oy = 0;
    double pOx = 0;
    double pOy = 0;
    double xStart = 0;
    double yStart = 0;
    double rectX = 0;
    double rectY = 0;
    double rectW = 0;
    double rectH = 0;
    double radius = 0;
    boolean mouseDown = false;
    boolean firstPointDefined = false;
    String drawMode = "base";

    public void initialize() {
        ctx = canvas.getGraphicsContext2D();
        ctxOverlay = overlayCanvas.getGraphicsContext2D();
        gridColourSetting.setValue(Color.BLACK);
        updateGrid();

        overlayCanvas.widthProperty().bind(canvas.widthProperty());
        overlayCanvas.heightProperty().bind(canvas.heightProperty());
        overlayCanvas.layoutXProperty().bind(canvas.layoutXProperty());
        overlayCanvas.layoutYProperty().bind(canvas.layoutYProperty());

        canvasSize.setOnAction(event -> {
            cS = Integer.parseInt(canvasSize.getText());
            updateGrid();
        });
    }

    public void updateGrid() {
        canvas.setWidth(cS);
        canvas.setHeight(cS);

        if (cS < 650) {
            canvas.setLayoutX((650 - cS) / 2);
            canvas.setLayoutY((650 - cS) / 2);
        } else {
            canvas.setLayoutX(75);
            canvas.setLayoutY(80);
        }

        ctxOverlay.clearRect(0, 0, cS, cS);
        ctxOverlay.setFill(Color.WHITE);
        ctxOverlay.fillRect(0, 0, cS, cS);

        if (gridDisplaySetting.isSelected()) {

            ctxOverlay.setStroke(gridColourSetting.getValue());
            ctxOverlay.setLineWidth(2);
            for (int gridYhop = 0; gridYhop < cS + 10; gridYhop += 10) {
                ctxOverlay.strokeLine(0, gridYhop, cS, gridYhop);
            }
            for (int gridXhop = 0; gridXhop < cS + 10; gridXhop += 10) {
                ctxOverlay.strokeLine(gridXhop, 0, gridXhop, cS);
            }

            if (gridXYLines.isSelected()) {
                ctxOverlay.setStroke(Color.rgb(255, 0, 0, 1));
                ctxOverlay.strokeLine(0, cS / 2, cS, cS / 2);
                ctxOverlay.setStroke(Color.rgb(0, 255, 0, 1));
                ctxOverlay.strokeLine(cS / 2, 0, cS / 2, cS);
            }
        }
    }

    public void updateOverlay(MouseEvent event) {
        ctxOverlay.setFill(Color.WHITE);
        ctxOverlay.fillRect(pOx, pOy, 10, 10);
        ctxOverlay.setStroke(gridColourSetting.getValue());
        ctxOverlay.strokeRect(pOx, pOy, 10, 10);

        if (gridXYLines.isSelected()) {
            ctxOverlay.setStroke(Color.rgb(255, 0, 0, 1));
            ctxOverlay.strokeLine(0, cS / 2, cS, cS / 2);
            ctxOverlay.setStroke(Color.rgb(0, 255, 0, 1));
            ctxOverlay.strokeLine(cS / 2, 0, cS / 2, cS);
        }

        Ox = Math.floor(event.getX() / 10) * 10;
        Oy = Math.floor(event.getY() / 10) * 10;

        Paint currentColor = ctx.getFill();
        ctxOverlay.setFill(currentColor);
        ctxOverlay.fillRect(Ox, Oy, 10, 10);
        pOx = Ox;
        pOy = Oy;

        switch (drawMode) {
            case "base":
                break;

            case "rect":
                updateGrid();
                ctxOverlay.setFill(currentColor);
                if (firstPointDefined) {
                    if (xStart < Ox) {
                        rectW = Ox - xStart + 10;
                        rectX = xStart;
                    } else {
                        rectW = xStart - Ox + 10;
                        rectX = Ox;
                    }

                    if (yStart < Oy) {
                        rectH = Oy - yStart + 10;
                        rectY = yStart;
                    } else {
                        rectH = yStart - Oy + 10;
                        rectY = Oy;
                    }

                    ctxOverlay.fillRect(rectX, rectY, rectW, rectH);
                    break;
                }

            case "circle":

                updateGrid();
                if (firstPointDefined) {
                    ctxOverlay.setFill(currentColor);
                    radius = Math.floor(Math.sqrt(Math.pow((Ox - xStart), 2) + Math.pow((Oy - yStart), 2)) / 10) * 10;

                    for (double checkY = yStart - radius; checkY < yStart + radius; checkY += 10) {

                        for (double checkX = xStart - radius; checkX < xStart + radius; checkX += 10) {
                            double distance = Math.sqrt(Math.pow(checkX - xStart, 2) + Math.pow(checkY - yStart, 2));

                            if (distance < radius) {
                                ctxOverlay.fillRect(checkX, checkY, 10, 10);
                            }
                        }
                    }
                }
                break;
        }
    }

    public void swapDrawMode(ActionEvent event) {
        String id = ((Button) event.getSource()).getId();
        switch (id) {
            case "baseDrawButton":
                drawMode = "base";
                break;
            case "rectDrawButton":
                drawMode = "rect";
                break;
            case "circleDrawButton":
                drawMode = "circle";
                break;
            case "clearCanvasButton":
                ctx.clearRect(0, 0, cS, cS);
                break;
        }
        System.out.println(drawMode);
        firstPointDefined = false;
    }

    public void toggleGCSdisp() {
        if (gridColourSetting.isShowing()) {
            gridColourSetting.hide();
        } else {
            gridColourSetting.show();
        }
    }

    public void downloadCanvas() throws IOException {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        canvas.snapshot(params, writableImage);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        ImageIO.write(bufferedImage, "png", new File("testName.png"));
    }

    public void updateColor() {
        Color selectedColor = colourSel.getValue();
        ctx.setFill(selectedColor);
        System.out.println(selectedColor);
    }

    public void drawPixel(MouseEvent event) {
        if (drawMode.equals("base") && !event.isShiftDown()) {
            x = Math.floor(event.getX() / 10) * 10;
            y = Math.floor(event.getY() / 10) * 10;
            ctx.fillRect(x, y, 10, 10);
        }

        if (event.isShiftDown()) {
            x = Math.floor(event.getX() / 10) * 10;
            y = Math.floor(event.getY() / 10) * 10;
            ctx.clearRect(x, y, 10, 10);
        }
    }

    public void singleClickDrawFunctions(MouseEvent event) {
        x = Math.floor(event.getX() / 10) * 10;
        y = Math.floor(event.getY() / 10) * 10;
        // x = event.getX();
        // y = event.getY();

        switch (drawMode) {
            case "base":
                if (!event.isShiftDown()) {
                    ctx.fillRect(x, y, 10, 10);
                }
                break;

            case "rect":
                if (!firstPointDefined) {
                    xStart = x;
                    yStart = y;
                    firstPointDefined = true;
                } else {
                    if (xStart < x) {
                        rectW = x - xStart + 10;
                        rectX = xStart;
                    } else {
                        rectW = xStart - x + 10;
                        rectX = x;
                    }

                    if (yStart < y) {
                        rectH = y - yStart + 10;
                        rectY = yStart;
                    } else {
                        rectH = yStart - y + 10;
                        rectY = y;
                    }

                    ctx.fillRect(rectX, rectY, rectW, rectH);
                    firstPointDefined = false;
                }
                break;

            case "circle":
                if (!firstPointDefined) {
                    xStart = x;
                    yStart = y;
                    firstPointDefined = true;
                } else {
                    radius = Math.floor(Math.sqrt(Math.pow((x - xStart), 2) + Math.pow((y - yStart), 2)) / 10) * 10;

                    for (double checkY = yStart - radius; checkY < yStart + radius; checkY += 10) {

                        for (double checkX = xStart - radius; checkX < xStart + radius; checkX += 10) {
                            double distance = Math.sqrt(Math.pow(checkX - xStart, 2) + Math.pow(checkY - yStart, 2));

                            if (distance < radius) {
                                ctx.fillRect(checkX, checkY, 10, 10);
                            }
                        }

                    }

                    firstPointDefined = false;
                }
        }
    }

}
