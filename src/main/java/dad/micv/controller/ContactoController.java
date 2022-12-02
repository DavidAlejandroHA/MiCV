package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogos.EmailDialog;
import dad.micv.dialogos.TelefonoDialog;
import dad.micv.dialogos.WebDialog;
import dad.micv.model.Contacto;
import dad.micv.model.Email;
import dad.micv.model.Telefono;
import dad.micv.model.TipoTelefono;
import dad.micv.model.Web;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ContactoController implements Initializable {

	// model

	private ObjectProperty<Contacto> contacto = new SimpleObjectProperty<>();

	// view

	@FXML
	private Button emailAnadirButton;

	@FXML
	private TableColumn<Email, String> emailColumn;

	@FXML
	private Button emailEliminarButton;

	@FXML
	private TableView<Email> emailTable;

	@FXML
	private TableColumn<Telefono, String> numeroColumn;

	@FXML
	private Button telefonosAnadirButton;

	@FXML
	private Button telefonosEliminarButton;

	@FXML
	private TableView<Telefono> telefonosTable;

	@FXML
	private TableColumn<Telefono, TipoTelefono> tipoColumn;

	@FXML
	private Button urlAnadirButton;

	@FXML
	private TableColumn<Web, String> urlColumn;

	@FXML
	private Button urlEliminarButton;

	@FXML
	private BorderPane view;

	@FXML
	private TableView<Web> webTable;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		numeroColumn.setCellValueFactory(valor -> valor.getValue().numeroProperty());
		tipoColumn.setCellValueFactory(valor -> valor.getValue().tipoProperty());
		emailColumn.setCellValueFactory(valor -> valor.getValue().direccionProperty());
		urlColumn.setCellValueFactory(valor -> valor.getValue().urlProperty());
		telefonosEliminarButton.disableProperty().bind(Bindings
				.when(telefonosTable.getSelectionModel().selectedItemProperty().isNull()).then(true).otherwise(false));
		emailEliminarButton.disableProperty().bind(Bindings
				.when(emailTable.getSelectionModel().selectedItemProperty().isNull()).then(true).otherwise(false));
		urlEliminarButton.disableProperty().bind(Bindings
				.when(webTable.getSelectionModel().selectedItemProperty().isNull()).then(true).otherwise(false));

		// listeners

		contacto.addListener(this::onContactoChanged);
	}

	private void onContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {
		if (ov != null) {
			telefonosTable.itemsProperty().unbindBidirectional(ov.telefonosProperty());
			emailTable.itemsProperty().unbindBidirectional(ov.emailsProperty());
			webTable.itemsProperty().unbindBidirectional(ov.websProperty());
		}

		if (nv != null) {
			telefonosTable.itemsProperty().bindBidirectional(nv.telefonosProperty());
			emailTable.itemsProperty().bindBidirectional(nv.emailsProperty());
			webTable.itemsProperty().bindBidirectional(nv.websProperty());
		}
	}

	public ContactoController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onAnadirTelefono(ActionEvent event) {
		TelefonoDialog dialogo = new TelefonoDialog();
		dialogo.initOwner(MiCVApp.primaryStage);
		Optional<Telefono> valores = dialogo.showAndWait();
		if (valores.isPresent()) { // El isPresent del Optional permite comprobar si el valor retornado es distinto
									// de nulo
			getContacto().telefonosProperty().add(valores.get());
		}
	}

	@FXML
	void onEliminarTelefono(ActionEvent event) {
		getContacto().telefonosProperty().remove(telefonosTable.getSelectionModel().selectedIndexProperty().get());
	}

	@FXML
	void onAnadirCorreo(ActionEvent event) {
		EmailDialog dialogo = new EmailDialog();
		dialogo.initOwner(MiCVApp.primaryStage);
		Optional<Email> valor = dialogo.showAndWait();
		if (valor.isPresent()) // El Optional permite comprobar si todos los valores están presentes
			getContacto().emailsProperty().add(new Email(dialogo.getDireccion()));
	}

	@FXML
	void onEliminarCorreo(ActionEvent event) {
		getContacto().emailsProperty().remove(emailTable.getSelectionModel().selectedIndexProperty().get());
	}

	@FXML
	void onAnadirWeb(ActionEvent event) {
		WebDialog dialogo = new WebDialog();
		dialogo.initOwner(MiCVApp.primaryStage);
		Optional<Web> valor = dialogo.showAndWait();
		if (valor.isPresent()) // El Optional permite comprobar si todos los valores están presentes
			getContacto().websProperty().add(new Web(dialogo.getUrl()));
	}

	@FXML
	void onEliminarWeb(ActionEvent event) {
		getContacto().websProperty().remove(webTable.getSelectionModel().selectedIndexProperty().get());
	}

	public BorderPane getView() {
		return view;
	}

	public Button getEmailAnadirButton() {
		return emailAnadirButton;
	}

	public TableColumn<Email, String> getEmailColumn() {
		return emailColumn;
	}

	public Button getEmailEliminarButton() {
		return emailEliminarButton;
	}

	public TableView<Email> getEmailTable() {
		return emailTable;
	}

	public TableColumn<Telefono, String> getNumeroColumn() {
		return numeroColumn;
	}

	public Button getTelefonosAnadirButton() {
		return telefonosAnadirButton;
	}

	public Button getTelefonosEliminarButton() {
		return telefonosEliminarButton;
	}

	public TableView<Telefono> getTelefonosTable() {
		return telefonosTable;
	}

	public TableColumn<Telefono, TipoTelefono> getTipoColumn() {
		return tipoColumn;
	}

	public Button getUrlAnadirButton() {
		return urlAnadirButton;
	}

	public TableColumn<Web, String> getUrlColumn() {
		return urlColumn;
	}

	public Button getUrlEliminarButton() {
		return urlEliminarButton;
	}

	public TableView<Web> getWebTable() {
		return webTable;
	}

	public final ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}

	public final Contacto getContacto() {
		return this.contactoProperty().get();
	}

	public final void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}
}
