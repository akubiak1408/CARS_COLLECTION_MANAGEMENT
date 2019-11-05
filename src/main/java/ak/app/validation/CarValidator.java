package ak.app.validation;

import ak.app.model.Car;
import ak.app.model.CarColor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarValidator {
    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> validate(Car car) {
        errors.clear();

        if (car == null) {
            errors.put("Car", "Car is NULL");
        }

        if (!isModelValid(car)) {
            errors.put("Model", "Model is not correct");
        }

        if (!isPriceValid(car)) {
            errors.put("Price", "Price is not correct");
        }

        if (!isColorValid(car)) {
            errors.put("Color", "Color is not correct");
        }

        if (!isMileageValid(car)) {
            errors.put("Mileage", "Mileage is not correct");
        }
        if (!areComponentsValid(car)) {
            errors.put("Components", "No components");
        }

        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    private boolean isModelValid(Car car) {
        return car.getModel() != null && car.getModel().matches("[A-Z ]+");
    }

    private boolean isPriceValid(Car car) {
        return car.getPrice() != null && car.getPrice().compareTo(new BigDecimal(0)) > 0;
    }

    private boolean isColorValid(Car car) {
        return
                car.getColor().compareTo(CarColor.RED) >= 0
                        || car.getColor().compareTo(CarColor.WHITE) >= 0
                        || car.getColor().compareTo(CarColor.BLACK) >= 0
                        || car.getColor().compareTo(CarColor.GREEN) >= 0;
    }

    private boolean isMileageValid(Car car) {
        return car.getMileage() >= 0 || car.getMileage() <= 10000000;
    }


    private boolean areComponentsValid(Car car) {
        return car.getComponents() != null;
    }
}
