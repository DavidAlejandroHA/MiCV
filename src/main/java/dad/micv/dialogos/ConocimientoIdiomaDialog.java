package dad.micv.dialogos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Idioma;
import dad.micv.model.Nivel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class ConocimientoIdiomaDialog extends Dialog<Idioma> implements Initializable {

	// model

	private StringProperty denominacion = new SimpleStringProperty();
	private ObjectProperty<Nivel> nivel = new SimpleObjectProperty<>();
	private StringProperty certificaion = new SimpleStringProperty();

	// view

	@FXML
	private Button botonXNivel;

	@FXML
	private ComboBox<Nivel> comboBoxNivel;

	@FXML
	private TextField textFieldDenom;

	@FXML
	private GridPane view;
	
	@FXML
    private TextField textFieldCertif;

	public ConocimientoIdiomaDialog() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoIdioma.fxml"));
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

		setResizable(true);
		getDialogPane().setPrefSize(getDialogPane().getMinWidth(), getDialogPane().getMinHeight());
		setTitle("Nuevo conocimiento");
		getDialogPane().setContent(view);

		getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);
		setResultConverter(this::onConvertResult);

		Button crearButton = (Button) getDialogPane().lookupButton(crearButtonType);
		Button cancelarButton = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);
		crearButton.disableProperty().bind(Bindings
				.when(denominacion.isEmpty().or(comboBoxNivel.getSelectionModel().selectedItemProperty().isNull()))
				.then(true).otherwise(false));

		ButtonBar.setButtonData(crearButton, ButtonData.RIGHT);
		ButtonBar.setButtonData(cancelarButton, ButtonData.RIGHT);

		// bindings

		denominacion.bind(textFieldDenom.textProperty());
		nivel.bind(comboBoxNivel.getSelectionModel().selectedItemProperty());
		certificaion.bind(textFieldCertif.textProperty());

		// load combo

		comboBoxNivel.getItems().setAll(FXCollections.observableArrayList(Nivel.values()));

	}

	private Idioma onConvertResult(ButtonType buttonType) {
		if (buttonType.getButtonData() == ButtonData.OK_DONE) {
			Idioma idioma = new Idioma(denominacion.get(), nivel.get(), certificaion.get());
			return idioma;
		}
		return null;
	}

	@FXML
	void onPresionarX(ActionEvent event) {
		comboBoxNivel.getSelectionModel().clearSelection();
	}
}
