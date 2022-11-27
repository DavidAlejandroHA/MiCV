package dad.micv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
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
import dad.micv.model.LocalDateAdapter;
import dad.micv.model.Personal;
import dad.micv.model.Titulo;
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
		try {
			personalController.getPaisComboBox().getItems().setAll(loadPaises("/csv/paises.csv"));
		} catch (Exception e) {
			// TODO Añadir alert
			System.out.println("test1");
			e.printStackTrace();
		}
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
		personalController.getDniTexto().textProperty().set(cv.getPersonal().getIdentificacion());
		personalController.getNombreTexto().textProperty().set(cv.getPersonal().getNombre());
		personalController.getApellidosTexto().textProperty().set(cv.getPersonal().getApellidos());
		personalController.getFechaDatePicker().setValue(cv.getPersonal().getFechaNacimiento());
		personalController.getDireccionTexto().textProperty().set(cv.getPersonal().getDireccion());
		personalController.getcPostaltexto().textProperty().set(cv.getPersonal().getCodigoPostal());
		personalController.getLocalidadTexto().textProperty().set(cv.getPersonal().getLocalidad());
		personalController.getPaisComboBox().getSelectionModel().select(cv.getPersonal().getPais());
		personalController.getNacionalidadesListView().itemsProperty().set(cv.getPersonal().getNacionalidades());
		
		contactoController.getTelefonosTable().itemsProperty().set(cv.getContacto().getTelefonos());
		contactoController.getEmailTable().itemsProperty().set(cv.getContacto().getEmails());
		contactoController.getWebTable().itemsProperty().set(cv.getContacto().getWebs());
		
		formacionController.getFormacionTabla().itemsProperty().set(cv.getFormacion());
	}

	@FXML
	void onGuardarAction(ActionEvent event) throws IOException {
		Personal personal = new Personal();
		personal.setIdentificacion(personalController.getDniTexto().textProperty().get());
		personal.setNombre(personalController.getNombreTexto().textProperty().get());
		personal.setApellidos(personalController.getApellidosTexto().textProperty().get());
		personal.setFechaNacimiento(personalController.getFechaDatePicker().getValue());
		personal.setDireccion(personalController.getDireccionTexto().textProperty().get());
		personal.setCodigoPostal(personalController.getcPostaltexto().textProperty().get());
		personal.setLocalidad(personalController.getLocalidadTexto().textProperty().get());
		personal.setPais(personalController.getPaisComboBox().getSelectionModel().getSelectedItem());
		//personal.getNacionalidades().add(new Nacionalidad("Española"));
		//personal.getNacionalidades().add(new Nacionalidad("Americana"));
		personal.setNacionalidades(personalController.getNacionalidadesListView().getItems());
		
		Contacto contacto = new Contacto();
		contacto.setTelefonos(contactoController.getModel().getTelefonos());
		contacto.setEmails(contactoController.getModel().getEmails());
		contacto.setWebs(contactoController.getModel().getWebs());
		
		CV cv = new CV();
		cv.setPersonal(personal);
		cv.setContacto(contacto);
		cv.setFormacion(formacionController.getTitulos()); ////////
		
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
	
	public static List<String> loadPaises(String filename) throws Exception{
		URL url = MainController.class.getResource(filename); // no usar File para acceder a resources
		List<String> lines = Files.readAllLines(Paths.get(url.toURI()), StandardCharsets.UTF_8);
		return lines.stream() // flujo de objetos de la colección
					.filter(s -> s.length()>0) // esto es por si existe líneas "vacías" que contienen el retorno de carro
					.collect(Collectors.toList());
	}

}
