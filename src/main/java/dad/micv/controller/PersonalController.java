package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogos.NacionalidadDialog;
import dad.micv.model.Nacionalidad;
import dad.micv.model.Personal;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {
	@FXML
	private TextField apellidosTexto;

	@FXML
	private TextField cPostaltexto;

	@FXML
	private TextArea direccionTexto;

	@FXML
	private TextField dniTexto;

	@FXML
	private DatePicker fechaDatePicker;

	@FXML
	private TextField localidadTexto;

	@FXML
	private Button masBoton;

	@FXML
	private Button menosBoton;

	@FXML
	private ListView<Nacionalidad> nacionalidadesListView;

	@FXML
	private TextField nombreTexto;

	@FXML
	private ComboBox<String> paisComboBox;

	@FXML
	private GridPane view;

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//
		personal.get().identificacionProperty().bindBidirectional(dniTexto.textProperty());

	}

	public PersonalController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GridPane getView() {
		return view;
	}

	@FXML
	void anadirNacionalidad(ActionEvent event) {
		NacionalidadDialog dialogo = new NacionalidadDialog();
		dialogo.initOwner(MiCVApp.primaryStage);
		dialogo.setTitle("Añadir Nacionalidad");
		dialogo.setHeaderText("Añade una nacionalidad a la lista");
		dialogo.showAndWait();
		if (dialogo.getDenominacion() != null) {
			Nacionalidad nacionalidad = new Nacionalidad(dialogo.getDenominacion());
			nacionalidadesListView.getItems().add(nacionalidad);
		}
	}

	@FXML
	void quitarNacionalidad(ActionEvent event) {
		nacionalidadesListView.getItems()
				.remove(nacionalidadesListView.getSelectionModel().selectedIndexProperty().get());
	}

	public TextField getApellidosTexto() {
		return apellidosTexto;
	}

	public TextField getcPostaltexto() {
		return cPostaltexto;
	}

	public TextArea getDireccionTexto() {
		return direccionTexto;
	}

	public TextField getDniTexto() {
		return dniTexto;
	}

	public DatePicker getFechaDatePicker() {
		return fechaDatePicker;
	}

	public TextField getLocalidadTexto() {
		return localidadTexto;
	}

	public Button getMasBoton() {
		return masBoton;
	}

	public Button getMenosBoton() {
		return menosBoton;
	}

	public ListView<Nacionalidad> getNacionalidadesListView() {
		return nacionalidadesListView;
	}

	public TextField getNombreTexto() {
		return nombreTexto;
	}

	public ComboBox<String> getPaisComboBox() {
		return paisComboBox;
	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		
		//this.personal.get().setIdentificacion(dniTexto.getText());
		this.personal.get().setNombre(nombreTexto.getText());
		this.personal.get().setApellidos(apellidosTexto.getText());
		this.personal.get().setFechaNacimiento((fechaDatePicker.getValue()));
		this.personal.get().setDireccion(direccionTexto.getText());
		this.personal.get().setCodigoPostal(cPostaltexto.getText());
		this.personal.get().setLocalidad(localidadTexto.getText());
		this.personal.get().setPais(paisComboBox.getSelectionModel().selectedItemProperty().get());
		this.personal.get().setNacionalidades(nacionalidadesListView.itemsProperty().get());
		
		return this.personalProperty().get();
	}
	

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
		
		//getPersonal().identificacionProperty().bind(dniTexto.textProperty());
		
		
		
		//dniTexto.textProperty().set(this.personal.get().getIdentificacion());
		nombreTexto.textProperty().set(this.personal.get().getNombre());
		apellidosTexto.textProperty().set(this.personal.get().getApellidos());
		fechaDatePicker.setValue(this.personal.get().getFechaNacimiento());
		direccionTexto.textProperty().set(this.personal.get().getDireccion());
		cPostaltexto.textProperty().set(this.personal.get().getCodigoPostal());
		localidadTexto.textProperty().set(this.personal.get().getLocalidad());
		paisComboBox.getSelectionModel().select(this.personal.get().getPais());
		nacionalidadesListView.itemsProperty().set(this.personal.get().getNacionalidades());
	}

}
