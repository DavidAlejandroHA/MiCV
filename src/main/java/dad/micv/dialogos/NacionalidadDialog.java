package dad.micv.dialogos;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import dad.micv.model.Nacionalidad;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class NacionalidadDialog extends Dialog<Nacionalidad> implements Initializable {

	// model

	private StringProperty denominacion = new SimpleStringProperty();

	// view

	@FXML
	private ComboBox<Nacionalidad> nacionalidadCombo;

	@FXML
	private GridPane view;

	public NacionalidadDialog() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevaNacionalidad.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// init dialog

		ButtonType crearButtonType = new ButtonType("Aceptar", ButtonData.OK_DONE);

		setWidth(200);
		setHeight(180);
		setTitle("Seleccionar nacionalidad");
		setHeaderText("Seleccione una nueva nacionalidad.");
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);

		setResultConverter(this::onConvertResult);

		Button crearButton = (Button) getDialogPane().lookupButton(crearButtonType);
		Button cancelarButton = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);
		crearButton.disableProperty().bind(nacionalidadCombo.getSelectionModel().selectedItemProperty().isNull());
		
		ButtonBar.setButtonData(crearButton, ButtonData.RIGHT);
		ButtonBar.setButtonData(cancelarButton, ButtonData.RIGHT);
		// bindings

		denominacion.bind(nacionalidadCombo.getSelectionModel().selectedItemProperty().asString());

		// load combo

		try {
			nacionalidadCombo.getItems().setAll(cargarNacionalidades("/csv/nacionalidades.csv"));
		} catch (IOException | URISyntaxException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("Error al cargar las nacionalidades.");
			alerta.setContentText("Ha habido un problema al cargar el fichero que contiene las nacionalidades.");
			alerta.showAndWait();
		}

	}

	private ObservableList<Nacionalidad> cargarNacionalidades(String filename) throws IOException, URISyntaxException {
		URL url = NacionalidadDialog.class.getResource(filename); // no usar File para acceder a resources
		List<String> lines = Files.readAllLines(Paths.get(url.toURI()), StandardCharsets.UTF_8);
		List<Nacionalidad> lista = lines.stream() // flujo de objetos de la colección
				.filter(s -> s.length() > 0) // esto es por si existe líneas "vacías" que contienen el retorno de carro
				.map(linea -> {
					return new Nacionalidad(linea);
				}).collect(Collectors.toList());
		return FXCollections.observableArrayList(lista);
	}

	private Nacionalidad onConvertResult(ButtonType buttonType) {
		if (buttonType.getButtonData() == ButtonData.OK_DONE) {
			Nacionalidad nacionalidad = new Nacionalidad(denominacion.get());
			return nacionalidad;
		}
		return null;
	}

	public final StringProperty denominacionProperty() {
		return this.denominacion;
	}

	public final String getDenominacion() {
		return this.denominacionProperty().get();
	}

	public final void setDenominacion(final String denominacion) {
		this.denominacionProperty().set(denominacion);
	}
}