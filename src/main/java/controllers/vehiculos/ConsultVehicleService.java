package controllers.vehiculos;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Client;
import models.Service;

public class ConsultVehicleService {
    @FXML
    private DatePicker dtpFechaInicial;
    @FXML
    private ComboBox<Client> cmbClientes;
    @FXML
    private TableView<Service> tblServicios;
    @FXML
    private TableColumn<Service, String> colMatricula;
    @FXML
    private TableColumn<Service, String> colMarca;
    @FXML
    private TableColumn<Service, Integer> colPrecio;
    @FXML
    private TableColumn<Service, LocalDate> colFechaAlquiler;
    @FXML
    private TableColumn<Service, LocalDate> colFechaEntrega;
    @FXML
    private TableColumn<Service, Integer> colTotal;
    @FXML
    private DatePicker dtpFechaFinal;

    public void initialize(URL url, ResourceBundle rb) {

        this.colMatricula.setCellValueFactory(new PropertyValueFactory("matriculaVehiculo"));
        this.colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        this.colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        this.colFechaAlquiler.setCellValueFactory(new PropertyValueFactory("fechaAlquiler"));
        this.colFechaEntrega.setCellValueFactory(new PropertyValueFactory("fechaEntrega"));
        this.colTotal.setCellValueFactory(new PropertyValueFactory("total"));

        Service s = new Service();
        ObservableList<Service> items = s.getServicios();
        this.tblServicios.setItems(items);
        Client c = new Client();
        ObservableList<Client> obsClientes = c.getClientes();
        this.cmbClientes.setItems(obsClientes);

    }

    public void closeWindows() {

        try {
            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/index.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) this.cmbClientes.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(TabsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
