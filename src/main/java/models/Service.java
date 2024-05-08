package models;

import utils.ConexionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Service {
    private int idServicio;
    private String matriculaVehiculo;
    private String NIFCliente;
    private LocalDate fechaAlquiler;
    private LocalDate fechaEntrega;
    private int total;

    private String marca;
    private int precio;

    public Service() {
    }

    public Service(int idServicio, String matriculaVehiculo, String NIFCliente, LocalDate fechaAlquiler, LocalDate fechaEntrega, int total) {
        this.idServicio = idServicio;
        this.matriculaVehiculo = matriculaVehiculo;
        this.NIFCliente = NIFCliente;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaEntrega = fechaEntrega;
        this.total = total;
    }

    public Service(String matriculaVehiculo, String NIFCliente, LocalDate fechaAlquiler, LocalDate fechaEntrega, int total) {
        this.matriculaVehiculo = matriculaVehiculo;
        this.NIFCliente = NIFCliente;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaEntrega = fechaEntrega;
        this.total = total;
    }

    // Usado para la tabla
    public Service(String matriculaVehiculo, LocalDate fechaAlquiler, LocalDate fechaEntrega, int total, String marca, int precio) {
        this.matriculaVehiculo = matriculaVehiculo;
        this.fechaAlquiler = fechaAlquiler;
        this.fechaEntrega = fechaEntrega;
        this.total = total;
        this.marca = marca;
        this.precio = precio;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getMatriculaVehiculo() {
        return matriculaVehiculo;
    }

    public void setMatriculaVehiculo(String matriculaVehiculo) {
        this.matriculaVehiculo = matriculaVehiculo;
    }

    public String getNIFCliente() {
        return NIFCliente;
    }

    public void setNIFCliente(String NIFCliente) {
        this.NIFCliente = NIFCliente;
    }

    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }



    public boolean insertarServicio() {

        try {
            ConexionDB conexion = new ConexionDB("jdbc:mariadb://localhost/alquiler_vehiculos", "root", "1234");

            String SQL = "INSERT INTO servicios (matricula_vehiculo, nif_cliente, fecha_alquiler, fecha_entrega, total) "
                    + "values("
                    + "'" + this.getMatriculaVehiculo() + "', "
                    + "'" + this.getNIFCliente() + "', "
                    + "'" + this.fechaAlquiler.toString() + "', "
                    + "'" + this.fechaEntrega.toString() + "',"
                    + total
                    + " )";

            int filas = conexion.ejecutarInstruccion(SQL);

            conexion.cerrarConexion();

            if (filas > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Service.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public ObservableList<Service> getServicios() {
        ObservableList<Service> obs = FXCollections.observableArrayList();
        try {

            ConexionDB conexion = new ConexionDB("jdbc:mariadb://localhost/alquiler_vehiculos", "root", "1234");

            conexion.ejecutarConsulta("select v.matricula, v.marca, v.precio, s.fecha_alquiler, s.fecha_entrega, s.total "
                    + "from servicios s, vehiculos v "
                    + "where s.matricula_vehiculo = v.matricula");

            ResultSet rs = conexion.getResultSet();

            while (rs.next()) {

                String matricula = rs.getString("matricula");
                String marca = rs.getString("marca");
                int precio = rs.getInt("precio");
                LocalDate fechaAlquiler = rs.getDate("fecha_alquiler").toLocalDate();
                LocalDate fechaEntrega = rs.getDate("fecha_entrega").toLocalDate();
                int total = rs.getInt("total");

                Service s = new Service(matricula, fechaAlquiler, fechaEntrega, total, marca, precio);

                obs.add(s);

            }

            conexion.cerrarConexion();

        } catch (SQLException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obs;
    }
}
