package dad.micv.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Main {
	
	private static Gson gson = FxGson.fullBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
			.create();
	
	public static void main(String[] args) throws IOException {
		
		saveCV();
		
		loadCV();
		
	}

	private static void loadCV() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		
		File cvFile = new File("cv.json"); 
		
		CV cv = gson.fromJson(new FileReader(cvFile), CV.class); // mapea el json a objectproperty personal a un json
		
		System.out.println(cv.getPersonal().getNombre() + " " + cv.getPersonal().getApellidos());
		
	}

	private static void saveCV() throws IOException {
		Personal personal = new Personal();
		personal.setIdentificacion("12345678Z");
		personal.setNombre("Chuck");
		personal.setApellidos("Norris");
		personal.getNacionalidades().add(new Nacionalidad("Española"));
		personal.getNacionalidades().add(new Nacionalidad("Americana"));
		personal.setPais("Estados Unidos");
		personal.setFechaNacimiento(LocalDate.of(1954, 11, 25));
		
		CV cv = new CV();
		cv.setPersonal(personal);
		
		String json = gson.toJson(cv, CV.class);
		
		File cvFile = new File("cv.json"); 
		
		Files.writeString(cvFile.toPath(), json, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
	}

}