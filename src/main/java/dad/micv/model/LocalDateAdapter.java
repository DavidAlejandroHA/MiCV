package dad.micv.model;

import java.lang.reflect.Type;
import java.time.LocalDate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

	/*
	{
		"year": 2010,
		"month": 11,
		"day": 30
	}
	*/
	
	@Override
	public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jo = new JsonObject();
		jo.addProperty("year", src.getYear());
		jo.addProperty("month", src.getMonthValue());
		jo.addProperty("day", src.getDayOfMonth());
		return jo;
	}

	@Override
	public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jo = json.getAsJsonObject();
		return LocalDate.of(
			jo.get("year").getAsInt(),
			jo.get("month").getAsInt(),
			jo.get("day").getAsInt()
		);
	}

} 
