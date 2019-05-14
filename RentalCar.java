package txstate.edu.amb309.rentalcarapp;

import org.json.JSONObject;

public class RentalCar {
    private int id;
    private String name;
    private String brand;
    private double rentalCostPerDay;
    private String color;

    public RentalCar(JSONObject carObject){
        try{
            this.id = carObject.getInt("id");
            this.name = carObject.getString("name");
            this.brand = carObject.getString("brand");
            this.rentalCostPerDay = (float)carObject.getDouble("rentalCostPerDay");
            this.color = carObject.getString("color");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public RentalCar() {
    }

    public RentalCar(int id, String name, String brand, double rentalCostPerDay, String color) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.rentalCostPerDay = rentalCostPerDay;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getRentalCostPerDay() {
        return rentalCostPerDay;
    }

    public void setRentalCostPerDay(double rentalCostPerDay) {
        this.rentalCostPerDay = rentalCostPerDay;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }
}
