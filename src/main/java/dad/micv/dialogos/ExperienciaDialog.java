package dad.micv.dialogos;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.micv.model.Experiencia;
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

public class ExperienciaDialog extends Dialog<Experiencia> implements Initializable {

	// model

	private ObjectProperty<LocalDate> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> hasta = new SimpleObjectProperty<>();
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty empleador = new SimpleStringProperty();

	// view

	@FXML
	private DatePicker desdeDatePicker;

	@FXML
	private DatePicker hastaDatePicker;

	@FXML
	private TextField denominacionTextField;

	@FXML
	private TextField empleadorTextField;

	@FXML
	private GridPane view;

	public ExperienciaDialog() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevaExperiencia.fxml"));
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
		setWidth(800);
		setHeight(280);
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
		empleador.bind(empleadorTextField.textProperty());
		desde.bind(desdeDatePicker.valueProperty());
		hasta.bind(hastaDatePicker.valueProperty());

		crearButton.disableProperty()
				.bind(Bindings.when(desdeDatePicker.valueProperty().isNull()
						.or(hastaDatePicker.valueProperty().isNull()).or(denominacionTextField.textProperty().isEmpty())
						.or(empleadorTextField.textProperty().isEmpty())).then(true).otherwise(false));

	}

	private Experiencia onConvertResult(ButtonType buttonType) {
		if (buttonType.getButtonData() == ButtonData.OK_DONE) {
			Experiencia experiencia = new Experiencia();
			experiencia.setDenominacion(denominacion.get());
			experiencia.setEmpleador(empleador.get());
			experiencia.setDesde(desde.get());
			experiencia.setHasta(hasta.get());
			return experiencia;
		}
		return null;
	}

}
