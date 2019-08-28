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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class ConsultarController implements Initializable {

    @FXML
    private MenuItem itmenu;
    @FXML
    private DatePicker dpfechainicial;
    @FXML
    private DatePicker dpfechafinal;
    @FXML
    private TableView<Servicio> tbclientes;
    @FXML
    private TableColumn<Servicio, String> colmatricula;
    @FXML
    private TableColumn<Servicio, String> colmarca;
    @FXML
    private TableColumn<Servicio, Integer> colprecio;
    @FXML
    private TableColumn<Servicio, LocalDate> colfechaalquiler;
    @FXML
    private TableColumn<Servicio, LocalDate> colfechaentrega;
    @FXML
    private TableColumn<Servicio, Integer> coltotal;
    @FXML
    private ComboBox<Cliente> cbclientes;

    ObservableList<Cliente> obcliente;
    ObservableList<Servicio> observicio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarCliente();
        this.colmatricula.setCellValueFactory(new PropertyValueFactory<Servicio, String>("matricula"));
        this.colmarca.setCellValueFactory(new PropertyValueFactory<Servicio, String>("marca"));
        this.colprecio.setCellValueFactory(new PropertyValueFactory<Servicio, Integer>("precio"));
        this.colfechaalquiler.setCellValueFactory(new PropertyValueFactory<Servicio, LocalDate>("fecha_alquiler"));
        this.colfechaentrega.setCellValueFactory(new PropertyValueFactory<Servicio, LocalDate>("fecha_entrega"));
        this.coltotal.setCellValueFactory(new PropertyValueFactory<Servicio, Integer>("total_precio"));

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
    private void volverMenu(ActionEvent event) {
        try {
            // Abro la ventana

            // Cargamos la vista secundaria
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/menu.fxml"));

            // Cargo el padre
            Parent root = (Parent) fxmlLoader.load();

            // cargo la ventana secundaria
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Menú");
            stage.setScene(scene);

            // Muestro la ventana
            stage.show();
            
            // Código para cerrar la ventana actual cuando abra la que quiero abrir
            Stage Mystage = (Stage) this.cbclientes.getScene().getWindow(); 
            Mystage.close();

        } catch (IOException ex) {
            Logger.getLogger(ConsultarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void seleccionarFechaInicial(ActionEvent event) throws ClassNotFoundException, SQLException {
        filtro();
    }

    @FXML
    private void seleccionarFechaFinal(ActionEvent event) throws ClassNotFoundException, SQLException {
        filtro();
    }
    
    private void filtro(){
      
            try {
                LocalDate fecha_alquiler=this.dpfechainicial.getValue();
                LocalDate fecha_entrega=this.dpfechafinal.getValue();
                Cliente cliente=this.cbclientes.getValue();
                String nif=null;
                
                if (cliente!=null){
                    nif=cliente.getNif();
                }
                
                
                
                Servicio servicio=new Servicio();
                observicio=servicio.cargarServicio(fecha_alquiler, fecha_entrega, nif);
                this.tbclientes.setItems(observicio);
                
                //Servicio servicio=new Servicio (id_servicio, nif, matricula, fecha_alquiler, fecha_entrega, total_precio);
                
                
                
                //String matricula=this.cbclientes.get
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ConsultarController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ConsultarController.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        }

    @FXML
    private void llamarFiltro(ActionEvent event) throws ClassNotFoundException, SQLException {
        filtro();
    }
}
    
