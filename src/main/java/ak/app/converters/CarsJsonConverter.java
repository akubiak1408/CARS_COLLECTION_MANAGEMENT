package ak.app.converters;

import ak.app.model.Car;

import java.util.List;

public class CarsJsonConverter extends JsonConverter<List<Car>> {

    public CarsJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
