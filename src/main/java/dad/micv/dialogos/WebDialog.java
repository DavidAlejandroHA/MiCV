package dad.micv.dialogos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Web;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class WebDialog extends Dialog<Web> implements Initializable {

	// model

	private StringProperty url = new SimpleStringProperty();

	// view

	@FXML
	private TextField webText;

	@FXML
	private GridPane view;

	public WebDialog() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NuevaWeb.fxml"));
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
		setTitle("Nueva Web");
		setHeaderText("Introduzca una nueva web.");
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);

		setResultConverter(this::onConvertResult);

		Button crearButton = (Button) getDialogPane().lookupButton(crearButtonType);
		

		// bindings

		url.bind(webText.textProperty());
		crearButton.disableProperty().bind(url.isEmpty());

	}

	private Web onConvertResult(ButtonType buttonType) {
		if (buttonType.getButtonData() == ButtonData.OK_DONE) {
			Web web = new Web(url.get());
			return web;
		}
		return null;
	}

	public final StringProperty urlProperty() {
		return this.url;
	}

	public final String getUrl() {
		return this.urlProperty().get();
	}

	public final void setUrl(final String url) {
		this.urlProperty().set(url);
	}
}
