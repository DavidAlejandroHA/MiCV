package dad.micv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import dad.micv.controller.ConocimientosController;
import dad.micv.controller.ContactoController;
import dad.micv.controller.ExperienciaController;
import dad.micv.controller.FormacionController;
import dad.micv.controller.PersonalController;
import dad.micv.model.CV;
import dad.micv.model.Contacto;
import dad.micv.model.Experiencia;
import dad.micv.model.LocalDateAdapter;
import dad.micv.model.Personal;
import dad.micv.model.Titulo;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class MainController implements Initializable {

	@FXML
	private MenuItem abrirItem;

	@FXML
	private MenuItem acercaDeMenu;

	@FXML
	private Menu archivoMenu;

	@FXML
	private Menu ayudaMenu;

	@FXML
	private Tab conocimientosTab;

	@FXML
	private Tab contactoTab;

	@FXML
	private Tab experienciaTab;

	@FXML
	private Tab formacionTab;

	@FXML
	private MenuItem guardarComoItem;

	@FXML
	private MenuItem guardarItem;

	@FXML
	private MenuBar menu;

	@FXML
	private MenuItem nuevoItem;

	@FXML
	private Tab personalTab;

	@FXML
	private TabPane pestanasTabPane;

	@FXML
	private MenuItem salirItem;

	@FXML
	private BorderPane view;

	private static Gson gson = FxGson.fullBuilder().setPrettyPrinting()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

	// Controllers
	
	PersonalController personalController = new PersonalController();
	ContactoController contactoController = new ContactoController();
	FormacionController formacionController = new FormacionController();
	ExperienciaController experienciaController = new ExperienciaController();
	ConocimientosController conocimientosController = new ConocimientosController();
	
	// models

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		personalTab.setContent(personalController.getView());
		contactoTab.setContent(contactoController.getView());
		formacionTab.setContent(formacionController.getView());
		experienciaTab.setContent(experienciaController.getView());
		conocimientosTab.setContent(conocimientosController.getView());
		
	}

	public MainController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onAbrirAction(ActionEvent event) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("."));
		// https://jenkov.com/tutorials/javafx/filechooser.html
		File selectedFile = fileChooser.showOpenDialog(MiCVApp.primaryStage);
		CV cv = gson.fromJson(new FileReader(selectedFile), CV.class);
		
		personalController.setPersonal(cv.getPersonal());
		
		contactoController.setContacto(cv.getContacto());//  */
		
		formacionController.setFormacion(cv.getFormacion());
		
		experienciaController.setExperiencias(cv.getExperiencias());
		//experienciaController.experienciasProperty().bind(cv.experienciasProperty());
	}

	@FXML
	void onGuardarAction(ActionEvent event) throws IOException {
		Personal personal = personalController.getPersonal();
		
		Contacto contacto = contactoController.getContacto();
		/*contacto.setTelefonos(contactoController.getModel().getTelefonos());
		contacto.setEmails(contactoController.getModel().getEmails());
		contacto.setWebs(contactoController.getModel().getWebs());*/
		
		ListProperty<Titulo> titulos = new SimpleListProperty<>(FXCollections.observableArrayList());
		titulos.set(formacionController.getTitulos());
		
		ListProperty<Experiencia> experiencias = new SimpleListProperty<>(FXCollections.observableArrayList());
		titulos.set(formacionController.getTitulos());
		
		CV cv = new CV();
		cv.setPersonal(personal);
		cv.setContacto(contacto);
		cv.setFormacion(titulos);
		cv.setExperiencias(experiencias); ////////
		
		String json = gson.toJson(cv, CV.class);
		
		File cvFile = new File("cv.json"); 
		
		Files.writeString(cvFile.toPath(), json, StandardCharsets.UTF_8);
	}

	@FXML
	void onGuardarComoAction(ActionEvent event) {

	}

	@FXML
	void onNuevoAction(ActionEvent event) {
		System.out.println("test");
	}

	@FXML
	void onSalirAction(ActionEvent event) {

	}

}
