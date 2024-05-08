package controllers.vehiculos;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Client;
import models.Service;
import models.Vehicle;

public class InsertVehicleService implements Initializable {
    @FXML
    private Button btnGrabar;
    @FXML
    private ComboBox<Client> cmbClientes;
    @FXML
    private ComboBox<Vehicle> cmbVehiculos;
    @FXML
    private TextField txtDirCli;
    @FXML
    private TextField txtNIFCli;
    @FXML
    private TextField txtPrecioVeh;
    @FXML
    private TextField txtKmVeh;
    @FXML
    private TextField txtMarcaVeh;
    @FXML
    private TextField txtDescripcionVeh;
    @FXML
    private TextField txtPobCli;
    @FXML
    private DatePicker dtpFechaAlquiler;
    @FXML
    private DatePicker dtpFechaEntrega;
    @FXML
    private TextField txtTotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCombos();
    }

    public void initCombos() {

        Client c = new Client();
        ObservableList<Client> obsClients = c.getClientes();
        this.cmbClientes.setItems(obsClients);
        Vehicle v = new Vehicle();
        ObservableList<Vehicle> obsVehicles = v.getVehiculos();
        this.cmbVehiculos.setItems(obsVehicles);

    }

    public void closeWindows() {
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/index.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage myStage = (Stage) this.btnGrabar.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(TabsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void seleccionarCliente(ActionEvent event) {

        Client c = this.cmbClientes.getValue();

        if (c != null) {

            this.txtNIFCli.setText(c.getNIF());
            this.txtDirCli.setText(c.getDireccion());
            this.txtPobCli.setText(c.getPoblacion());

        }

    }

    @FXML
    private void seleccionarVehiculo(ActionEvent event) {

        Vehicle v = this.cmbVehiculos.getValue();

        if (v != null) {

            // Seteo los valores
            this.txtDescripcionVeh.setText(v.getDescripcion());
            this.txtMarcaVeh.setText(v.getMarca());
            this.txtKmVeh.setText(v.getKm() + "");
            this.txtPrecioVeh.setText(v.getPrecio() + "");
            calcularTotalServicio();

        }

    }

    @FXML
    private void seleccionarFechaAlquiler(ActionEvent event) {
        calcularTotalServicio();
    }

    @FXML
    private void seleccionarFechaEntrega(ActionEvent event) {
        calcularTotalServicio();
    }

    public void calcularTotalServicio() {

        LocalDate inicio = this.dtpFechaAlquiler.getValue();
        LocalDate fin = this.dtpFechaEntrega.getValue();

        if (inicio != null && fin != null && this.cmbVehiculos.getValue() != null) {

            // Obtengo la diferencia de dias
            Period p = Period.between(inicio, fin);
            long dias = p.getDays();

            // Calculo el total
            long total = dias * Integer.parseInt(this.txtPrecioVeh.getText() + "");

            if (total < 0) {
                this.txtTotal.setText("0");
            } else {
                this.txtTotal.setText(total + "");
            }

        } else {
            this.txtTotal.setText("0");
        }

    }

    @FXML
    private void setService(ActionEvent event) {

        Vehicle v = this.cmbVehiculos.getValue();
        Client c = this.cmbClientes.getValue();
        LocalDate inicio = this.dtpFechaAlquiler.getValue();
        LocalDate fin = this.dtpFechaEntrega.getValue();
        int total = Integer.parseInt(this.txtTotal.getText());

        String errores = "";

        if (c == null) {
            errores += "- Debes seleccionar un cliente\n";
        }

        if (v == null) {
            errores += "- Debes seleccionar un vehiculo\n";
        }

        if (inicio == null) {
            errores += "- Debes seleccionar una fecha de alquiler\n";
        }

        if (fin == null) {
            errores += "- Debes seleccionar una fecha de entrega\n";
        }

        if (inicio != null && fin != null && inicio.isAfter(fin)) {
            errores += "- La fecha de alquiler no puede superar a la de entrega\n";
        }

        if (total == 0) {
            errores += "- El total no puede ser 0\n";
        }

        if (errores.isEmpty()) {

            Service s = new Service(v.getMatricula(), c.getNIF(), inicio, fin, total);

            if (s.insertarServicio()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(errores);
            alert.showAndWait();
        }

    }
}
