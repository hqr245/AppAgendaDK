package es.losmontecillos;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

import java.sql.Date;
import java.text.ParseException;
import java.util.ResourceBundle;

public class PersonaDetalleViewController implements Initializable {
    //Atributos
    @javafx.fxml.FXML
    private AnchorPane rootPersonaDetalleView;
    @javafx.fxml.FXML
    private TextField textFieldNombre;
    @javafx.fxml.FXML
    private TextField textFieldApellidos;
    @javafx.fxml.FXML
    private TextField textFieldTelefono;
    @javafx.fxml.FXML
    private TextField textFieldEmail;
    @javafx.fxml.FXML
    private TextField textFieldNumHijos;
    @javafx.fxml.FXML
    private TextField textFieldSalario;
    @javafx.fxml.FXML
    private CheckBox checkBoxJubilado;
    @javafx.fxml.FXML
    private ComboBox<Provincia> comboBoxProvincia;

    ToggleGroup estadoCivil = new ToggleGroup();

    @javafx.fxml.FXML
    private RadioButton radioButtonSoltero;
    public static final String SOLTERO="S";
    @javafx.fxml.FXML
    private RadioButton radioButtonCasado;
    public static final String CASADO="C";
    @javafx.fxml.FXML
    private RadioButton radioButtonViudo;
    public static final String VIUDO="V";
    @javafx.fxml.FXML
    private DatePicker datePickerFechaNacimiento;
    @javafx.fxml.FXML
    private ImageView imageViewFoto;
    public static final String CARPETA_FOTOS="Fotos";
    private AnchorPane rootAgendaView;
    private TableView tableViewPrevio;
    private Persona persona;
    private DataUtil dataUtil;
    private boolean nuevaPersona;
    private boolean errorFormato = false;
    private InicioController inicioController;
    private Persona selectedPersona;

    public void setPersona(Persona persona){
        this.persona=persona;
    }

    //Métodos
    public void setRootAgendaView(AnchorPane rootAgendaView) {this.rootAgendaView = rootAgendaView;}

    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public void setTableViewPrevio(TableView tableViewPrevio) {this.tableViewPrevio=tableViewPrevio;}


    public void mostrarDatos() throws ParseException {



    }

    @javafx.fxml.FXML
    private void onActionButtonExaminar(ActionEvent event) {

        System.err.println("Botón examinar presionado");

    }

    @javafx.fxml.FXML
    private void onActionSuprimirFoto(ActionEvent event){

        System.err.println("Botón suprimir presionado");


    }

    @javafx.fxml.FXML
    private void onActionButtonGuardar(ActionEvent event) throws ParseException{

        Persona persona=new Persona();


        try {

            checkData();

            persona.setNombre(textFieldNombre.getText());
            persona.setApellidos(textFieldApellidos.getText());
            persona.setTelefono(textFieldTelefono.getText());
            persona.setEmail(textFieldEmail.getText());
            persona.setFechaNacimiento(datePickerFechaNacimiento.getValue().toString());
            persona.setNumHijos(Integer.parseInt(textFieldNumHijos.getText()));

            if(radioButtonSoltero.isSelected()){persona.setEstadoCivil("S");}
            if(radioButtonCasado.isSelected()){persona.setEstadoCivil("C");}
            if(radioButtonViudo.isSelected()){persona.setEstadoCivil("V");}


            persona.setSalario(Double.parseDouble(textFieldSalario.getText()));
            persona.setJubilado(checkBoxJubilado.isSelected() ? 1 : 0);
            persona.setProvincia(comboBoxProvincia.getValue());
            //TODO persona.setFoto...



            if(this.selectedPersona!=null){dataUtil.actualizarPersona(persona);}else{
            dataUtil.addPersona(persona);}


            tableViewPrevio.getItems().add(persona);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Persona Creada Con Éxito");
            alert.setHeaderText(null);
            alert.setContentText("La persona Con nombre "+persona.nombreProperty().get()+" Ha sido creada con éxito." );
            alert.showAndWait();

            rootPersonaDetalleView.setVisible(false);
            rootAgendaView.setVisible(true);

        }catch (Exception e){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();


        }



    }

    private void checkData()throws Exception {


        if(textFieldNombre.getText().isEmpty()){throw new Exception("Error en el campo nombre");}
        if (textFieldApellidos.getText().isEmpty()){throw new Exception("Error en el campo apellido");}
        if (textFieldEmail.getText().isEmpty()){throw new Exception("Error en el campo email");}
        if (datePickerFechaNacimiento.getValue()==null){throw new Exception("Error en el campo fecha nacimiento");}
        try{Integer.parseInt(textFieldNumHijos.getText());}
        catch (NumberFormatException e) {throw new Exception("Error en el campo num hijos");}
        if(comboBoxProvincia.getValue()==null) {throw new Exception("Error en el campo provincia");}

    }

    @javafx.fxml.FXML
    private void onActionButtonCancelar(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText(null);
        alert.setContentText("Estás a punto de cancelar la operación, ¿Estás seguro?");
        if(alert.showAndWait().get().getButtonData().isDefaultButton()) {

            rootPersonaDetalleView.setVisible(false);
            rootAgendaView.setVisible(true);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioButtonSoltero.setSelected(true);
        radioButtonSoltero.setToggleGroup(estadoCivil);
        radioButtonCasado.setToggleGroup(estadoCivil);
        radioButtonViudo.setToggleGroup(estadoCivil);

        comboBoxProvincia.setOnMouseClicked(e->{
            if (comboBoxProvincia.getItems().isEmpty()) {comboBoxProvincia.setItems(dataUtil.getOlProvincias());}
        });

        comboBoxProvincia.setCellFactory(listView->new ListCell<>(){

            public void updateItem(Provincia item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {

                    setText(item.nombreProperty().get());//return String, actual name of material

                }
                else {
                    setText(null);
                }
            }

        });

        comboBoxProvincia.setButtonCell(new ListCell<>(){

            public void updateItem(Provincia item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.nombreProperty().get());
                }
                else {
                    setText(null);
                }
            }
        });


        textFieldNombre.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {

                if(persona!=null){

                    textFieldNombre.setText(persona.getNombre());
                    textFieldApellidos.setText(persona.getApellidos());
                    textFieldTelefono.setText(persona.getTelefono());
                    textFieldEmail.setText(persona.getEmail());
                    datePickerFechaNacimiento.setValue(Date.valueOf(persona.getFechaNacimiento()).toLocalDate());
                    textFieldNumHijos.setText(persona.getNumHijos().toString());
                    setSelectedRadButton();
                    textFieldSalario.setText(persona.getSalario().toString());
                    checkBoxJubilado.setSelected(persona.getJubilado()==1);
                    comboBoxProvincia.setValue(persona.getProvincia());


                }


            }
        });

    }

    private void setSelectedRadButton() {


        if(persona.getEstadoCivil().equals("S")){radioButtonSoltero.setSelected(true);}

        if(persona.getEstadoCivil().equals("C")){radioButtonCasado.setSelected(true);}
        if(persona.getEstadoCivil().equals("V")){radioButtonViudo.setSelected(true);}

    }

    public void setInicioController(InicioController inicioController) {
        this.inicioController = inicioController;
    }
}