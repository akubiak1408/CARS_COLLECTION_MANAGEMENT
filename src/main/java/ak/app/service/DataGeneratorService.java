package ak.app.service;

import ak.app.converters.CarsJsonConverter;
import ak.app.model.Car;
import ak.app.model.CarColor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

class DataGeneratorService {

    private final String filename;
    private final String[] carMarks = {"AUDI", "BMW", "JEEP", "HYUNDAI", "MERCEDES", "FIAT"};
    private final CarColor[] carColors = {CarColor.RED, CarColor.GREEN, CarColor.BLACK, CarColor.WHITE};
    private final String[] carComponentsList = {"Audio", "Cameras", "Sensors", "ABS",
            "Wiring harnesses", "Air conditioning system", "Fog lights"};

    DataGeneratorService(String filename) {
        this.filename = filename;
    }

    void generateDataToJson(int numberOfElements) {
        new CarsJsonConverter(filename).toJson(carList(numberOfElements));
    }

    void beforeSaved(List<Car> carList) {
        new CarsJsonConverter(filename).toJson(carList);
    }

    private List<Car> carList(int number) {
        List<Car> list = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            list.add(generateCar());
        }
        return list;
    }


    private Car generateCar() {
        Random r = new Random();

        return Car.builder()
                .model(carMarks[r.nextInt(carMarks.length)])
                .price(new BigDecimal(r.nextInt(50000) + 50000))
                .color(carColors[r.nextInt(carColors.length)])
                .mileage(new BigDecimal(r.nextDouble() * 100000).setScale(2, RoundingMode.HALF_UP).doubleValue())
                .components(new HashSet<>(generateComponents(r.nextInt(carComponentsList.length))))
                .build();

    }

    Car manualGenerateCar(String model, BigDecimal price, CarColor color, double mileage, Set<String> components) {
        return Car.builder()
                .model(model)
                .price(price)
                .color(color)
                .mileage(mileage)
                .components(components)
                .build();
    }

    private List<String> generateComponents(int size) {
        List<String> list = new ArrayList<>();
        Random random = new Random();

        if (size == 0)
            list.add("No components");

        for (int i = 0; i < size; i++) {
            list.add(carComponentsList[random.nextInt(size)]);
        }

        return list.stream().distinct().collect(Collectors.toList());
    }

}
