package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import dad.micv.MainController;
import dad.micv.MiCVApp;
import dad.micv.dialogos.NacionalidadDialog;
import dad.micv.model.Nacionalidad;
import dad.micv.model.Personal;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {

	// model

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();

	// view

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

	public PersonalController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		paisComboBox.setPromptText("Select Subject");
		paisComboBox.setButtonCell(new ListCell<String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText("Seleciona un país");
				} else {
					setText(item);
				}
			}
		});

		// bindings

		menosBoton.disableProperty()
				.bind(Bindings.when(nacionalidadesListView.getSelectionModel().selectedItemProperty().isNull())
						.then(true).otherwise(false));

		// load data

		try {
			paisComboBox.getItems().setAll(loadPaises("/csv/paises.csv"));
		} catch (Exception e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("Error al cargar la lista de países");
			alerta.showAndWait();
		}

		// listeners

		personal.addListener(this::onPersonalChanged);

	}

	private void onPersonalChanged(ObservableValue<? extends Personal> o, Personal ov, Personal nv) {

		if (ov != null) {

			dniTexto.textProperty().unbindBidirectional(ov.identificacionProperty());
			nombreTexto.textProperty().unbindBidirectional(ov.nombreProperty());
			apellidosTexto.textProperty().unbindBidirectional(ov.apellidosProperty());
			fechaDatePicker.valueProperty().unbindBidirectional(ov.fechaNacimientoProperty());
			direccionTexto.textProperty().unbindBidirectional(ov.direccionProperty());
			cPostaltexto.textProperty().unbindBidirectional(ov.codigoPostalProperty());
			localidadTexto.textProperty().unbindBidirectional(ov.localidadProperty());
			ov.paisProperty().unbind();
			nacionalidadesListView.itemsProperty().unbind();

		}

		if (nv != null) {
			dniTexto.textProperty().bindBidirectional(nv.identificacionProperty());
			nombreTexto.textProperty().bindBidirectional(nv.nombreProperty());
			apellidosTexto.textProperty().bindBidirectional(nv.apellidosProperty());
			fechaDatePicker.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
			direccionTexto.textProperty().bindBidirectional(nv.direccionProperty());
			cPostaltexto.textProperty().bindBidirectional(nv.codigoPostalProperty());
			localidadTexto.textProperty().bindBidirectional(nv.localidadProperty());
			paisComboBox.getSelectionModel().select(nv.getPais());

			nv.paisProperty().bind(paisComboBox.getSelectionModel().selectedItemProperty());

			nacionalidadesListView.itemsProperty().bind(nv.nacionalidadesProperty());

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
		Optional<Nacionalidad> valores = dialogo.showAndWait();
		if (valores.isPresent()) {
			Nacionalidad nacionalidad = new Nacionalidad(valores.get().getDenominacion());
			nacionalidadesListView.getItems().add(nacionalidad);
		}
	}

	@FXML
	void quitarNacionalidad(ActionEvent event) {
		nacionalidadesListView.getItems()
				.remove(nacionalidadesListView.getSelectionModel().selectedIndexProperty().get());
	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}

	public static List<String> loadPaises(String filename) throws Exception {
		URL url = MainController.class.getResource(filename); // no usar File para acceder a resources
		List<String> lines = Files.readAllLines(Paths.get(url.toURI()), StandardCharsets.UTF_8);
		return lines.stream() // flujo de objetos de la colección
				.filter(s -> s.length() > 0) // esto es por si existe líneas "vacías" que contienen el retorno de carro
				.collect(Collectors.toList());
	}

	public ComboBox<String> getPaisComboBox() {
		return paisComboBox;
	}

}
