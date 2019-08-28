/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import static java.time.temporal.ChronoUnit.DAYS;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Cliente;
import modelo.Coche;
import modelo.Servicio;

/**
 * FXML Controller class
 *
 * @author Sergio
 */
public class MenuController implements Initializable {

    @FXML
    private DatePicker dpinicio;
    @FXML
    private DatePicker dpfinal;
    @FXML
    private ComboBox<Coche> cbvehiculo;
    @FXML
    private ComboBox<Cliente> cbclientes;
    @FXML
    private TextField tfdescripcion;
    @FXML
    private TextField tfmarca;
    @FXML
    private TextField tfkilometros;
    @FXML
    private TextField tfprecio;
    @FXML
    private TextField tfnif;
    @FXML
    private TextField tfdireccion;
    @FXML
    private TextField tfpoblacion;
    @FXML
    private TextField tfpreciototal;
    @FXML
    private Button btgrabar;
    @FXML
    private MenuItem itconsultar;

    ObservableList<Coche> obcoche;
    ObservableList<Coche> obcocheocupado;
    ObservableList<Cliente> obcliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCliente();
        cargarCoche();

    }

    @FXML
    private void grabar(ActionEvent event) throws ClassNotFoundException, SQLException {

        Cliente cliente = this.cbclientes.getSelectionModel().getSelectedItem();
        Coche coche = this.cbvehiculo.getSelectionModel().getSelectedItem();
        LocalDate fecha_alquiler = this.dpinicio.getValue();
        LocalDate fecha_entrega = this.dpfinal.getValue();
        int total_precio = Integer.parseInt(this.tfpreciototal.getText());

        String errores = "";

        if (cliente == null) {
            errores = "Has de insertar un cliente";
        }

        if (coche == null) {
            errores = "Has de insertar un coche";
        }

        if (fecha_alquiler == null) {
            errores = "Has de introducir una fecha de alquiler";
        }

        if (fecha_entrega == null) {
            errores = "Has de introducir una fecha de entrega";
        }

        if (fecha_alquiler.isAfter(fecha_entrega)) {
            errores = "La fecha de inicio ha de estar antes que la fecha de entrega";
        }

        if (fecha_entrega.isBefore(fecha_alquiler)) {
            errores = "La fecha de entrega ha de estar después que la fecha de alquiler";
        }

        if (fecha_alquiler.equals(fecha_entrega)) {
            errores = "La fecha de alquiler no puede ser la misma que la de entrega";
        }

        if (total_precio <= 0) {
            errores = "El precio total no puede ser inferior o igual que 0";
        }

        if (errores.isEmpty()) {
            Servicio servicio = new Servicio(cliente, coche, fecha_alquiler, fecha_entrega, total_precio);

            if (servicio.insertarDatos()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("La reserva se ha realizado de forma correcta");
                alert.showAndWait();
                alert.close();
                if (coche.actualizarOcupado()) {
                    this.obcoche.remove(coche);
                    this.cbvehiculo.setItems(obcoche);
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Información");
                    alerta.setContentText("El coche está marcado como ocupado");
                    alerta.showAndWait();
                    alerta.close();
                } else {
                    Alert alertaocupado = new Alert(Alert.AlertType.ERROR);
                    alertaocupado.setTitle("Error");
                    alertaocupado.setContentText("El coche no se ha marcado como ocupado");
                    alertaocupado.showAndWait();
                    alertaocupado.close();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("La reserva no se ha realizado");
                alert.showAndWait();
                alert.close();
                
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errores);
            alert.showAndWait();
        }

    }

    @FXML
    private void consultarClientes(ActionEvent event) {
        try {
            // Abro la ventana

            // Cargamos la vista secundaria
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/consultar.fxml"));

            // Cargo el padre
            Parent root = (Parent) fxmlLoader.load();

            // cargo la ventana secundaria
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Consultar Clientes");
            stage.setScene(scene);

            // Muestro la ventana
            stage.show();
            // Código para cerrar la ventana actual cuando abra la que quiero abrir
            Stage Mystage = (Stage) this.btgrabar.getScene().getWindow();
            Mystage.close();

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarCoche() {
        try {
            Coche coche = new Coche(); //Creo objetos para el combobox
            obcoche = coche.cargarCoche();
            this.cbvehiculo.setItems(obcoche);
            this.cbvehiculo.getSelectionModel().select(0);//Con esto dejo seleccionado el primero para que no aparezca el combobox vacío

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarCliente() {
        try {
            Cliente cliente = new Cliente(); //Creo objetos para el combobox
            obcliente = cliente.cargarCliente();
            this.cbclientes.setItems(obcliente);
            this.cbclientes.getSelectionModel().select(0); //Con esto dejo seleccionado el primero para que no aparezca el combobox vacío

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void seleccionarDatosCoche() {
        //coger el objeto seleccionado en el combobox
        //con dicho objeto y sus get rellenar los distintos textfields

        Coche coche = this.cbvehiculo.getSelectionModel().getSelectedItem(); //Con esto cojo el objeto del combobox

        if (coche != null) {
            this.tfdescripcion.setText(coche.getDescripcion());
            this.tfmarca.setText(coche.getMarca());
            this.tfkilometros.setText(coche.getKlm() + "");
            this.tfprecio.setText(coche.getPrecio() + "");
            calcularPrecioTotal();

        }

    }

    @FXML
    private void seleccionarDatosCliente() {
        //coger el objeto seleccionado en el combobox
        //con dicho objeto y sus get rellenar los distintos textfields
        Cliente cliente = this.cbclientes.getSelectionModel().getSelectedItem(); //Con esto cojo el objeto del combobox

        if (cliente != null) {
            this.tfnif.setText(cliente.getNif());
            this.tfdireccion.setText(cliente.getDireccion());
            this.tfpoblacion.setText(cliente.getPoblacion());

        }

    }

    public static long diferenciaDias(LocalDate fechainicio, LocalDate fechafinal) {
        Period period = Period.between(fechainicio, fechafinal);
        long dias = period.getDays();
        return dias;
    }

    @FXML
    private void seleccionarDiaInicioAlquiler(ActionEvent event) {
        calcularPrecioTotal();
    }

    @FXML
    private void seleccionarDiaFinAlquiler(ActionEvent event) {
        calcularPrecioTotal();
    }

    private void calcularPrecioTotal() {
        LocalDate fechainicio = this.dpinicio.getValue();
        LocalDate fechafinal = this.dpfinal.getValue();

        if (fechainicio != null && fechafinal != null) {
            // Period period = Period.between(fechainicio, fechafinal);
            //long dias = period.getDays();

            long dias = calcularDias(fechainicio, fechafinal);

            Coche coche = this.cbvehiculo.getValue();
            int precio = coche.getPrecio();

            if (dias < 0) {
                this.tfpreciototal.setText("0");
            } else {
                int preciototal = (int) (dias * precio);
                this.tfpreciototal.setText(preciototal + "");
            }

        }

    }

    public long calcularDias(LocalDate inicio, LocalDate fin) {

        //Creo los LocalDate si no le paso parámetros
        //LocalDate inicio= LocalDate.of(2019, 1, 1);
        //LocalDate fin= LocalDate.of(2020, 1, 1);
        //Usando ChronoUnit calculo el número de días
        long dias = DAYS.between(inicio, fin);

        System.out.println("Número de días " + dias);
        return dias;
    }

}
