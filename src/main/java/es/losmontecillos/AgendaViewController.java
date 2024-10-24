package es.losmontecillos;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AgendaViewController implements Initializable {
    private DataUtil dataUtil;
    private ObservableList<Provincia> olProvincias;
    private ObservableList<Persona> olPersonas;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private AnchorPane rootAgendaView;
    @FXML
    private TableView tableViewAgenda;
    @javafx.fxml.FXML
    private TableColumn columnNombre;
    @javafx.fxml.FXML
    private TableColumn columnProvincia;
    @javafx.fxml.FXML
    private TableColumn columnEmail;
    @javafx.fxml.FXML
    private TableColumn columnApellidos;
    @javafx.fxml.FXML
    private TextField textFieldApellidos;

    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public void setOlProvincias(ObservableList<Provincia> olProvincias) {
        this.olProvincias = olProvincias;
    }

    public void setOlPersonas(ObservableList<Persona> olPersonas) {
        this.olPersonas = olPersonas;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));


    }

    @FXML
    public void onActionButtonSuprimir(ActionEvent actionEvent) {
        System.out.println("Suprimir pulsado");


        System.out.println(dataUtil.getOlPersonas());

        tableViewAgenda.setItems(olPersonas);

    }

    @FXML
    public void onActionButtonEditar(ActionEvent actionEvent) {
        System.out.println("Editar pulsado");
    }

    @FXML
    public void onActionButtonGuardar(ActionEvent actionEvent) {
        System.out.println("Guardar pulsado");
    }

    @FXML
    public void onActionButtonNuevo(ActionEvent actionEvent) {
        System.out.println("Nuevo pulsado");
    }
}