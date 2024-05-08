package models;

import utils.ConexionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.ConexionDB;

public class Vehicle {
    private String matricula;
    private String descripcion;
    private String marca;
    private int km;
    private int precio;

    public Vehicle() {
    }

    public Vehicle(String matricula, String descripcion, String marca, int km, int precio) {
        this.matricula = matricula;
        this.descripcion = descripcion;
        this.marca = marca;
        this.km = km;
        this.precio = precio;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return matricula;
    }

    public ObservableList<Vehicle> getVehiculos() {
        ObservableList<Vehicle> obs = FXCollections.observableArrayList();
        try {

            ConexionDB conexion = new ConexionDB("jdbc:mariadb://localhost/alquiler_vehiculos", "root", "1234");

            conexion.ejecutarConsulta("select * from vehiculos");

            ResultSet rs = conexion.getResultSet();

            // Recorro los datos
            while (rs.next()) {

                String matricula = rs.getString("matricula");
                String descripcion = rs.getString("descripcion");
                String marca = rs.getString("marca");
                int km = rs.getInt("kilometros");
                int precio = rs.getInt("precio");

                Vehicle v = new Vehicle(matricula, descripcion, marca, km, precio);

                obs.add(v);

            }

            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }

}
