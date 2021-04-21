/**
 * @author Wisam Nasir
 */

package esapplicativocanvas;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;

public class Controller {

    // serve per disegnare sul canvass
    private GraphicsContext graphicsContext;

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker clrpkrColorePenna;

    @FXML
    private ColorPicker clrpkrColoreSfondo;

    @FXML
    private CheckBox chkbxGomma;

    @FXML
    private CheckBox chkbxLineaDiritta;

    @FXML
    private CheckBox chkbxRettangolo;

    @FXML
    private TextArea txtareaErrori;

    @FXML
    private Slider sldrDimensionePunta;

    @FXML
    private Label sldrLabel;

    private final double[] dimensionePenna = {0};
    @FXML
    void initialize() {
        graphicsContext = canvas.getGraphicsContext2D();
        resetCanvas();

        clrpkrColorePenna.setValue(Color.BLACK);
        clrpkrColoreSfondo.setValue(Color.WHITE);


        dimensionePenna[0] = 10;

        // imposta il slider a 10 come default
        sldrDimensionePunta.setValue(10);
        sldrLabel.setText("Valore: "+(int)sldrDimensionePunta.getValue());

        // listener per avere la dimensione della penna dal slider
        sldrDimensionePunta.valueProperty().addListener((observable, oldValue, newValue) -> {
            sldrLabel.setText("Valore: "+(int)sldrDimensionePunta.getValue());
            dimensionePenna[0] = sldrDimensionePunta.getValue();
        });


        // quando l'utente muove il mouse per disegnare
        canvas.setOnMouseDragged(event -> {
            // coordinate x y del mouse
            double x = event.getX();
            double y = event.getY();

            // se l'utente ha selezionato la gomma
            if (chkbxGomma.isSelected()) {
                // imposta il colore della penna come quello dello sfondo
                graphicsContext.setFill(clrpkrColoreSfondo.getValue());
            // altrimenti
            } else {
                // imposta il colore della penna come richiesto dall'utente
                graphicsContext.setFill(clrpkrColorePenna.getValue());
            }

            // ogni volta che l'utente muove la penna disegna un quadrato
            // di dimensioni prese dal slider alle coordinate x y del mouse
            graphicsContext.fillRect(x, y, dimensionePenna[0], dimensionePenna[0]);
        });
    }

    /* per disegnare una retta */
    public void onLineaDirittaSelected() {
        canvas.setOnMouseClicked(event -> {
            if (chkbxLineaDiritta.isSelected()) {
                final double[] x = {event.getX()};
                final double[] y = {event.getY()};
                canvas.setOnMouseClicked(event1 -> {
                    double y2 = event1.getY();
                    double x2 = event1.getX();
                    graphicsContext.setLineWidth(dimensionePenna[0]);
                    graphicsContext.strokeLine(x[0], y[0], x2, y2);
                    x[0] = x2;
                    y[0] = y2;
                });
            }
        });
    }

    /* per disegnare un rettangolo */
    public void onRettangoloSelected() {
        canvas.setOnMouseClicked(event -> {
            if (chkbxRettangolo.isSelected()) {
                final double[] x = {event.getX()};
                final double[] y = {event.getY()};
                canvas.setOnMouseClicked(event1 -> {
                    final double[] y2 = {event1.getY()};
                    final double[] x2 = {event1.getX()};
                    graphicsContext.setLineWidth(dimensionePenna[0]);
                    graphicsContext.strokeLine(x[0], y[0], x2[0], y[0]);
                    graphicsContext.strokeLine(x2[0], y[0], x2[0], y2[0]);
                    graphicsContext.strokeLine(x2[0], y2[0], x[0], y2[0]);
                    graphicsContext.strokeLine(x[0], y2[0], x[0], y[0]);
                });
            }
        });
    }


    /* metodo per cambiare lo sfondo del canvas */
    public void cambiaColoreSfondo() {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(clrpkrColoreSfondo.getValue());
        graphicsContext.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    /* metodo per il reset del canvas */
    public void resetCanvas() {
        cambiaColoreSfondo();
    }

    /* metodo per salvare l'immagine come .png */
    public void salvaImmagine() {
        graphicsContext = canvas.getGraphicsContext2D();
        // nuova immagine di dimensione del canvas
        WritableImage writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        // mette una foto del canvas sul writableImage
        canvas.snapshot(null, writableImage);
        // new file di tipo .png
        File immagine = new File("Immagine.png");
        try {
            // scrittura immagine
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", immagine);
        } catch (Exception e) {
            txtareaErrori.setText("IMPOSSIBILE SALVARE IL FILE!");
        }
    }

    /* metodo per chiudere il programma */
    public void onEsci() {
        System.exit(1);
        Platform.exit();
    }

    /* metodo per resettare tutto il programma con valori di default */
    public void resetTotale() {
        clrpkrColoreSfondo.setValue(Color.WHITE);
        clrpkrColorePenna.setValue(Color.BLACK);
        sldrDimensionePunta.setValue(10);
        sldrLabel.setText("Valore: "+(int)sldrDimensionePunta.getValue());
        chkbxGomma.setSelected(false);
        txtareaErrori.clear();
        chkbxRettangolo.setSelected(false);
        chkbxLineaDiritta.setSelected(false);
        resetCanvas();
    }
}
