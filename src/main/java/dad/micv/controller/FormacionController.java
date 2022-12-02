package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogos.TituloDialog;
import dad.micv.model.Titulo;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class FormacionController implements Initializable {
	
	// model
	
	private ListProperty<Titulo> formacion = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<ListProperty<Titulo>> formacionObjectProp = new SimpleObjectProperty<>(formacion);
	
	// view
	
	@FXML
	private Button anadirFormacionBoton;

	@FXML
	private TableColumn<Titulo, String> denominacionColumna;

	@FXML
	private TableColumn<Titulo, LocalDate> desdeColumna;

	@FXML
	private Button eliminarFormacionBoton;

	@FXML
	private TableView<Titulo> formacionTabla;

	@FXML
	private TableColumn<Titulo, LocalDate> hastaColumna;

	@FXML
	private TableColumn<Titulo, String> organizadorColumna;

	@FXML
	private BorderPane view;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		desdeColumna.setCellValueFactory(valor -> valor.getValue().desdeProperty());
		hastaColumna.setCellValueFactory(valor -> valor.getValue().hastaProperty());
		
		// bindings
		formacionTabla.itemsProperty().bindBidirectional(formacion); // binding inicial, hasta que el listener lo reemplaze
		denominacionColumna.setCellValueFactory(valor -> valor.getValue().denominacionProperty());
		organizadorColumna.setCellValueFactory(valor -> valor.getValue().organizadorProperty());

		eliminarFormacionBoton.disableProperty().bind(Bindings
				.when(formacionTabla.getSelectionModel().selectedItemProperty().isNull()).then(true).otherwise(false));
		// listeners

		formacionObjectProp.addListener(this::onFormacionChanged);
	}

	public void onFormacionChanged(ObservableValue<? extends ListProperty<Titulo>> o, ListProperty<Titulo> ov,
			ListProperty<Titulo> nv) {
		if (ov != null) {
			formacionTabla.itemsProperty().unbindBidirectional(ov);
		}

		if (nv != null) {
			formacionTabla.itemsProperty().bindBidirectional(nv);
		}
	}

	public FormacionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onAnadirFormacion(ActionEvent event) {
		TituloDialog dialogo = new TituloDialog();
		dialogo.initOwner(MiCVApp.primaryStage);
		Optional<Titulo> valores = dialogo.showAndWait();
		if (valores.isPresent()) { // El isPresent del Optional permite comprobar si el valor retornado es distinto de nulo
			formacion.add(valores.get());
		}
	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onEliminarFormacion(ActionEvent event) {
		formacion.remove(formacionTabla.getSelectionModel().selectedIndexProperty().get());
	}

	public final ListProperty<Titulo> formacionProperty() {
		return this.formacion;
	}

	public final ObservableList<Titulo> getFormacion() {
		return this.formacionProperty().get();
	}

	public final void setFormacion(final ObservableList<Titulo> formacion) {
		this.formacionProperty().set(formacion);
		// formacionTabla.itemsProperty().set(formacion);
	}

	public ListProperty<Titulo> getTitulos() {
		return this.formacion;
	}
}
