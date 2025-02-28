package models;

import utils.ConexionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Client {
    private String NIF;
    private String NyA;
    private String direccion;
    private String poblacion;

    public Client() {
    }

    public Client(String NIF, String NyA, String direccion, String poblacion) {
        this.NIF = NIF;
        this.NyA = NyA;
        this.direccion = direccion;
        this.poblacion = poblacion;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getNyA() {
        return NyA;
    }

    public void setNyA(String NyA) {
        this.NyA = NyA;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    @Override
    public String toString() {
        return NyA;
    }

    public ObservableList<Client> getClientes() {
        ObservableList<Client> obs = FXCollections.observableArrayList();
        try {

            // Abro la conexion
            ConexionDB conexion = new ConexionDB("jdbc:mariadb://localhost/alquiler_vehiculos", "root", "1234");
            conexion.ejecutarConsulta("select * from clientes");
            ResultSet rs = conexion.getResultSet();

            while (rs.next()) {

                // Cojo los datos
                String NIF = rs.getString("NIF");
                String nombre = rs.getString("NyA");
                String direccion = rs.getString("direcion");
                String poblacion = rs.getString("poblacion");

                // Creo el cliente
                Client c = new Client(NIF, nombre, direccion, poblacion);

                obs.add(c);

            }

            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }
}
