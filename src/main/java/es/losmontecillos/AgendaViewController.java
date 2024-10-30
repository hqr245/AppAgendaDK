package es.losmontecillos;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
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
    private TableColumn<Persona, String> columnNombre;
    @javafx.fxml.FXML
    private TableColumn<Persona, String> columnProvincia;
    @javafx.fxml.FXML
    private TableColumn<Persona,String> columnEmail;
    @javafx.fxml.FXML
    private TableColumn<Persona,String> columnApellidos;
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
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

        columnProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        columnProvincia.setCellValueFactory(
                cellData->{
                    SimpleStringProperty property=new SimpleStringProperty();
                    if (cellData.getValue().getProvincia()!= null){
                        property.setValue(cellData.getValue().getProvincia().getNombre());
                    }
                    return property;
                });

        tableViewAgenda.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                textFieldNombre.setText(((Persona)t1).getNombre());
                textFieldApellidos.setText(((Persona)t1).getApellidos());
            }
        });



    }

    @FXML
    public void onActionButtonSuprimir(ActionEvent actionEvent) {


    }

    @FXML
    public void onActionButtonEditar(ActionEvent actionEvent) {
        System.out.println("Editar pulsado");

        try{

// Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PersonaDetalleView.fxml"));

            Parent rootDetalleView=fxmlLoader.load();
            PersonaDetalleViewController personaDetalleViewController=fxmlLoader.getController();
            personaDetalleViewController.setDataUtil(dataUtil);
            personaDetalleViewController.setRootAgendaView(rootAgendaView);
            personaDetalleViewController.setTableViewPrevio(tableViewAgenda);
            personaDetalleViewController.setPersona((Persona)tableViewAgenda.getSelectionModel().getSelectedItem());

            // Ocultar la vista de la lista
            rootAgendaView.setVisible(false);
            StackPane rootMain = (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
        } catch (IOException ex){
            ex.printStackTrace();}

    }



    @FXML
    public void onActionButtonGuardar(ActionEvent actionEvent) {

    Persona kekw=(Persona)tableViewAgenda.getSelectionModel().getSelectedItem();
        System.out.println(kekw.getId());
    }

    @FXML
    public void onActionButtonNuevo(ActionEvent actionEvent) {


        try{


// Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PersonaDetalleView.fxml"));

            Parent rootDetalleView=fxmlLoader.load();
            PersonaDetalleViewController personaDetalleViewController=fxmlLoader.getController();
            personaDetalleViewController.setDataUtil(dataUtil);
            personaDetalleViewController.setRootAgendaView(rootAgendaView);

            personaDetalleViewController.setTableViewPrevio(tableViewAgenda);

            // Ocultar la vista de la lista
            rootAgendaView.setVisible(false);
            StackPane rootMain = (StackPane) rootAgendaView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);

        } catch (IOException ex){
            ex.printStackTrace();}


    }

    public void cargarTodasPersonas() {

        tableViewAgenda.setItems(olPersonas);

    }
}