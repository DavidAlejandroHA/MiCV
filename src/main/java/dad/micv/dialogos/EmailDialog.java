package dad.micv.dialogos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Email;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
 
public class EmailDialog extends Dialog<Email> implements Initializable {
 
	// model
	
	private StringProperty direccion = new SimpleStringProperty();
	
	// view
 
    @FXML
    private TextField emailText;
 
    @FXML
    private GridPane view;
 
	public EmailDialog() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevoEmail.fxml"));
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
		getDialogPane().setGraphic(new ImageView("/com/sun/javafx/scene/control/skin/caspian/dialog-confirm.png"));
		setWidth(200);
		setHeight(180);
		setTitle("Nuevo Email");
		setHeaderText("Introduzca un nuevo email.");
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);
 
		setResultConverter(this::onConvertResult);
 
		Button crearButton = (Button) getDialogPane().lookupButton(crearButtonType);
		crearButton.disableProperty().bind(direccion.isEmpty());
 
		// bindings
 
		direccion.bind(emailText.textProperty());
 
	}
 
	private Email onConvertResult(ButtonType buttonType) {
		if (buttonType.getButtonData() == ButtonData.OK_DONE) {
			Email email = new Email(direccion.get());
			return email;
		}
		return null;
	}

	public final StringProperty direccionProperty() {
		return this.direccion;
	}
	

	public final String getDireccion() {
		return this.direccionProperty().get();
	}
	

	public final void setDireccion(final String direccion) {
		this.direccionProperty().set(direccion);
	}
}
