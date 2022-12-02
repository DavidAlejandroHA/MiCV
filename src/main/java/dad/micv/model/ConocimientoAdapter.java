package dad.micv.model;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ConocimientoAdapter implements JsonDeserializer<Conocimiento>, JsonSerializer<Conocimiento> {
	
	@Override
	public JsonElement serialize(Conocimiento src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("denominacion", src.getDenominacion());
		jo.addProperty("nivel", src.getNivel().toString());
		jo.addProperty("type", src.getClass().getSimpleName());
		if(src instanceof Idioma) {
			jo.addProperty("certificacion", ((Idioma) src).getCertificacion());
		}
		return jo;
	}

	@Override
	public Conocimiento deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		Conocimiento conocimiento = new Conocimiento();
		JsonObject jo = json.getAsJsonObject();
		if(jo.get("type").getAsString().equals(Idioma.class.getSimpleName())) {
			Idioma idioma = new Idioma();
			idioma.setCertificacion(jo.get("certificacion").getAsString());
			conocimiento = idioma;
		}
		conocimiento.setDenominacion(jo.get("denominacion").getAsString());
		conocimiento.setNivel(Nivel.valueOf(jo.get("nivel").getAsString()));
		return conocimiento;
	}

} 
