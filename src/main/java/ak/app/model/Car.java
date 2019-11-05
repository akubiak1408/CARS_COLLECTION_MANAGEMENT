package ak.app.model;


import lombok.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Car {
    public static final String MODEL = "\nModel: ";
    public static final String PRICE = "\nPrice: ";
    public static final String COLOR = "\nColor: ";
    public static final String MILEAGE = "\nMileage: ";
    public static final String COMPONENTS = "\nComponents: ";
    public static final String MILES = " miles";
    private String model;
    private BigDecimal price;
    private CarColor color;
    private double mileage;
    private Set<String> components;


    @Override
    public String toString() {
        return MODEL + getModel() +
                PRICE + getPrice().round(new MathContext(6, RoundingMode.CEILING)) + " $" +
                COLOR + getColor() +
                MILEAGE + getMileage() + MILES +
                COMPONENTS + getComponents() + "\n";
    }

}
