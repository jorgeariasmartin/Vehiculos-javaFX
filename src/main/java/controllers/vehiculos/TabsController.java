package controllers.vehiculos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TabsController implements Initializable {
    @FXML
    private Button btnIntroducir;
    @FXML
    private Button btnConsultar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void setServices(ActionEvent event) {

        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/add-services.fxml"));
            Parent root = loader.load();
            InsertVehicleService controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> controlador.closeWindows());
            Stage myStage = (Stage) this.btnIntroducir.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(TabsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void consultarServicios(ActionEvent event) {

        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/search-services.fxml"));
            Parent root = loader.load();
            ConsultVehicleService controlador = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> controlador.closeWindows());
            Stage myStage = (Stage) this.btnIntroducir.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(TabsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}