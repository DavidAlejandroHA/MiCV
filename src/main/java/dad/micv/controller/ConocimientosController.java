package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.MiCVApp;
import dad.micv.dialogos.ConocimientoDialog;
import dad.micv.dialogos.ConocimientoIdiomaDialog;
import dad.micv.model.Conocimiento;
import dad.micv.model.Idioma;
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

public class ConocimientosController implements Initializable {

	// model

	private ListProperty<Conocimiento> habilidades = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<ListProperty<Conocimiento>> habilidadesObjectProp = new SimpleObjectProperty<>(habilidades);

	// view

	@FXML
	private Button anadirConBoton;

	@FXML
	private Button anadirIdiomaBoton;

	@FXML
	private TableView<Conocimiento> conocimientosTabla;

	@FXML
	private TableColumn<Conocimiento, String> denominacionColumna;

	@FXML
	private Button eliminarConBoton;

	@FXML
	private TableColumn<Conocimiento, String> nivelColumna;

	@FXML
	private BorderPane view;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		denominacionColumna.setCellValueFactory(valor -> valor.getValue().denominacionProperty());
		nivelColumna.setCellValueFactory(valor -> valor.getValue().nivelProperty().asString());
		
		// bindings
		conocimientosTabla.itemsProperty().bindBidirectional(habilidades); // es útil para cuando se carga el documento o se crea uno nuevo (ctrl + n)
		eliminarConBoton.disableProperty()
				.bind(Bindings.when(conocimientosTabla.getSelectionModel().selectedItemProperty().isNull()).then(true)
						.otherwise(false));

		// listeners

		habilidadesObjectProp.addListener(this::onHabiliadesChanged);

	}

	public void onHabiliadesChanged(ObservableValue<? extends ListProperty<Conocimiento>> o,
			ListProperty<Conocimiento> ov, ListProperty<Conocimiento> nv) {

		if (ov != null) {
			conocimientosTabla.itemsProperty().unbindBidirectional(ov);
		}

		if (nv != null) {
			conocimientosTabla.itemsProperty().bindBidirectional(ov);
		}

	}

	public ConocimientosController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	void onAnadirConocimiento(ActionEvent event) {
		ConocimientoDialog dialogo = new ConocimientoDialog();
		dialogo.initOwner(MiCVApp.primaryStage);
		Optional<Conocimiento> valores = dialogo.showAndWait();
		if (valores.isPresent()) { // El isPresent del Optional permite comprobar si el valor retornado es distinto de nulo
			habilidades.add(valores.get());
		}
	}

	@FXML
	void onAnadirIdioma(ActionEvent event) {
		ConocimientoIdiomaDialog dialogo = new ConocimientoIdiomaDialog();
		dialogo.initOwner(MiCVApp.primaryStage);
		Optional<Idioma> valores = dialogo.showAndWait();
		if (valores.isPresent()) {
			habilidades.add(valores.get());
		}
	}

	@FXML
	void onEliminarAction(ActionEvent event) {
		habilidades.remove(conocimientosTabla.getSelectionModel().selectedItemProperty().get());
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Conocimiento> habilidadesProperty() {
		return this.habilidades;
	}

	public final ObservableList<Conocimiento> getHabilidades() {
		return this.habilidadesProperty().get();
	}

	public final void setHabilidades(final ObservableList<Conocimiento> habilidades) {
		this.habilidadesProperty().set(habilidades);
	}

}
