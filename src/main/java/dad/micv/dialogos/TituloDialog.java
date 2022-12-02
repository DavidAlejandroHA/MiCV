package dad.micv.dialogos;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.micv.model.Titulo;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class TituloDialog extends Dialog<Titulo> implements Initializable {

	// model

	private ObjectProperty<LocalDate> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> hasta = new SimpleObjectProperty<>();
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty organizador = new SimpleStringProperty();

	// view

	@FXML
	private DatePicker desdeDatePicker;

	@FXML
	private DatePicker hastaDatePicker;

	@FXML
	private TextField denominacionTextField;

	@FXML
	private TextField organizadorTextField;

	@FXML
	private GridPane view;

	public TituloDialog() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoTitulo.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// init dialog

		ButtonType crearButtonType = new ButtonType("Crear", ButtonData.OK_DONE);
		// getDialogPane().setGraphic(new
		// ImageView("/com/sun/javafx/scene/control/skin/caspian/dialog-confirm.png"));
		setWidth(200);
		setHeight(180);
		setTitle("Nuevo Título");
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);

		setResultConverter(this::onConvertResult);

		Button crearButton = (Button) getDialogPane().lookupButton(crearButtonType);
		Button cancelarButton = (Button) getDialogPane().lookupButton(ButtonType.CANCEL);

		ButtonBar.setButtonData(crearButton, ButtonData.RIGHT);
		ButtonBar.setButtonData(cancelarButton, ButtonData.RIGHT);

		// bindings

		denominacion.bind(denominacionTextField.textProperty());
		organizador.bind(organizadorTextField.textProperty());
		desde.bind(desdeDatePicker.valueProperty());
		hasta.bind(hastaDatePicker.valueProperty());

		crearButton.disableProperty()
				.bind(Bindings.when(desdeDatePicker.valueProperty().isNull()
						.or(hastaDatePicker.valueProperty().isNull()).or(denominacionTextField.textProperty().isEmpty())
						.or(organizadorTextField.textProperty().isEmpty())).then(true).otherwise(false));

	}

	private Titulo onConvertResult(ButtonType buttonType) {
		if (buttonType.getButtonData() == ButtonData.OK_DONE) {
			Titulo titulo = new Titulo();
			titulo.setDenominacion(denominacion.get());
			titulo.setOrganizador(organizador.get());
			titulo.setDesde(desde.get());
			titulo.setHasta(hasta.get());
			return titulo;
		}
		return null;
	}

}
