/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.ConexionController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Sergio
 */
public class Servicio {

    private int id_servicio;
    private String nif;
    private String matricula;
    private LocalDate fecha_alquiler;
    private LocalDate fecha_entrega;
    private int total_precio;
    private String marca;
    private int precio;

    Cliente cliente = new Cliente();
    Coche coche = new Coche();

    public Servicio() {
    }

    public Servicio(String nif, String matricula, LocalDate fecha_alquiler, LocalDate fecha_entrega, int total_precio) {
        this.nif = nif;
        this.matricula = matricula;
        this.fecha_alquiler = fecha_alquiler;
        this.fecha_entrega = fecha_entrega;
        this.total_precio = total_precio;
    }

    public Servicio(int id_servicio, String nif, String matricula, LocalDate fecha_alquiler, LocalDate fecha_entrega, int total_precio) {
        this.id_servicio = id_servicio;
        this.nif = nif;
        this.matricula = matricula;
        this.fecha_alquiler = fecha_alquiler;
        this.fecha_entrega = fecha_entrega;
        this.total_precio = total_precio;
    }

    public Servicio(Cliente cliente, Coche coche, LocalDate fecha_alquiler, LocalDate fecha_entrega, int total_precio) {
        this.cliente = cliente;
        this.coche = coche;
        this.fecha_alquiler = fecha_alquiler;
        this.fecha_entrega = fecha_entrega;
        this.total_precio = total_precio;

    }

    public Servicio(String matricula, LocalDate fecha_alquiler, LocalDate fecha_entrega, int total_precio, String marca, int precio) {
        this.matricula = matricula;
        this.fecha_alquiler = fecha_alquiler;
        this.fecha_entrega = fecha_entrega;
        this.total_precio = total_precio;
        this.marca = marca;
        this.precio = precio;
    }

    public Servicio(String nif, String matricula, LocalDate fecha_alquiler, LocalDate fecha_entrega, int total_precio, String marca, int precio) {
        this.nif = nif;
        this.matricula = matricula;
        this.fecha_alquiler = fecha_alquiler;
        this.fecha_entrega = fecha_entrega;
        this.total_precio = total_precio;
        this.marca = marca;
        this.precio = precio;
    }

    public Servicio(int id_servicio, String nif, String matricula, LocalDate fecha_alquiler, LocalDate fecha_entrega, int total_precio, String marca, int precio) {
        this.id_servicio = id_servicio;
        this.nif = nif;
        this.matricula = matricula;
        this.fecha_alquiler = fecha_alquiler;
        this.fecha_entrega = fecha_entrega;
        this.total_precio = total_precio;
        this.marca = marca;
        this.precio = precio;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public String getNif() {
        return nif;
    }

    public String getMatricula() {
        return matricula;
    }

    public LocalDate getFecha_alquiler() {
        return fecha_alquiler;
    }

    public LocalDate getFecha_entrega() {
        return fecha_entrega;
    }

    public int getTotal_precio() {
        return total_precio;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setFecha_alquiler(LocalDate fecha_alquiler) {
        this.fecha_alquiler = fecha_alquiler;
    }

    public void setFecha_entrega(LocalDate fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public void setTotal_precio(int total_precio) {
        this.total_precio = total_precio;
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

    public boolean insertarDatos() throws ClassNotFoundException, SQLException {
        ConexionController c = ConexionController.getInstancia();
        String SQL = "INSERT INTO SERVICIOS (nif, matricula, fecha_alquiler, fecha_entrega, total_precio) values ('" + cliente.getNif() + "', '" + coche.getMatricula() + "', '" + fecha_alquiler + "', '" + fecha_entrega + "', '" + total_precio + "')";
        System.out.println(SQL);
        int filas=c.ejecutarInstruccion(SQL);
        
        if(filas==0){
            return false;
        } else return true; 
        
        
    }

    public ObservableList<Servicio> cargarServicio(LocalDate fecha_alquiler, LocalDate fecha_entrega, String nif) throws ClassNotFoundException, SQLException { //Con este método devuelvo un ObservableList, que voy a necesitar 
        //para meter los datos en el Combobox correspondiente

        //Primero cojo los datos
        ConexionController c = ConexionController.getInstancia();

        ObservableList<Servicio> observicio = FXCollections.observableArrayList();//Crear ArrayList

        String SQL = "select c.matricula, c.marca, c.precio, s.fecha_alquiler, s.fecha_entrega, s.total_precio " //Hago la consulta
                + "FROM coches c, servicios s "
                + "WHERE c.matricula=s.matricula";

        if (fecha_alquiler != null) {

            SQL += " AND s.fecha_alquiler >= '" + fecha_alquiler.toString() + "'"; //Esto es para que aparezcan también si por ejemplo he alquilado del 20 al 22 y quiero ver del 19 al 22 (que también estaría alquilado, pues para que aparezca).
        }
        if (fecha_entrega != null) {
            SQL += " AND s.fecha_entrega <= '" + fecha_entrega.toString() + "'"; //Le pongo toString para que devuelva la fecha en formato YY/MM/DD
        }

        if (nif != null) {
            SQL += " AND s.nif='" + nif + "'";

        }

        ResultSet rs = c.ejecutarConsulta(SQL); //Almaceno los datos en el ResultSet
        System.out.println(SQL);

        while (rs.next()) { //Cojo los datos
            String matricula = rs.getString("matricula");
            String marca = rs.getString("marca");
            int precio = rs.getInt("precio");
            int total_precio = rs.getInt("total_precio");
            LocalDate fecha_alquiler_bd = rs.getDate("fecha_alquiler").toLocalDate();
            LocalDate fecha_entrega_bd = rs.getDate("fecha_entrega").toLocalDate();

            Servicio servicio = new Servicio(nif, matricula, fecha_alquiler_bd, fecha_entrega_bd, total_precio, marca, precio);
            observicio.add(servicio);
        }
        return observicio;

    }
}
