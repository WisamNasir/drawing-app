/**
 *  Applicativo Canvas che consente all'utente di disegnare qualcosa
 *  e poi salvarlo come un immagine .png.
 *
 *  Ci potrebbero essere ancora presenti dei piccolo bug.
 *
 * @version 2.0
 * @author Wisam Nasir
 * @created 12/12/2020
 *
 *  ultima modifica 15/12/2020
 */

package esapplicativocanvas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("esapplicativocanvas.fxml"));
        primaryStage.setTitle("Applicativo Canvas - creato da Wisam Nasir");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
