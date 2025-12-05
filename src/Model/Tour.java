package Model;

public class Tour {
    private String tourName, startFrom, destination, dayStart, languageGuideNeed, tourState, tourGuideState;
    private Double numberOfDays, price;
    private int maxNumberOfPassengers, currentPassengers, maxNumberOfGuides, currentGuides;

    public Tour() {
        this.tourName = "";
        this.startFrom = "";
        this.destination = "";
        this.dayStart = "";
        this.numberOfDays = 0.0;
        this.price = 0.0;
        this.maxNumberOfPassengers = 0;
        this.currentPassengers = 0;
        this.tourState = "Not Full";
        this.maxNumberOfGuides = 0;
        this.currentGuides = 0;
        this.tourGuideState = "NOT FULL";
        this.languageGuideNeed = "";
    }
    
    public Tour(String tourName, String startFrom, String destination, String dayStart, Double numberOfDays, Double price, int maxNumberOfPassengers, int currentPassengers, String tourState, int maxNumberOfGuides, int currentGuides, String tourGuideState, String languageGuideNeed) {
        this.tourName = tourName;
        this.startFrom = startFrom;
        this.destination = destination;
        this.dayStart = dayStart;
        this.numberOfDays = numberOfDays;
        this.price = price;
        this.maxNumberOfPassengers = maxNumberOfPassengers;
        this.currentPassengers = currentPassengers;
        this.tourState = tourState;
        this.maxNumberOfGuides = maxNumberOfGuides;
        this.currentGuides = currentGuides;
        this.tourGuideState = tourGuideState;
        this.languageGuideNeed = languageGuideNeed;
    }
//    Getter
    public String getTourName() {
        return tourName;
    }

    public String getStartFrom() {
        return startFrom;
    }

    public String getDestination() {
        return destination;
    }

    public String getDayStart() {
        return dayStart;
    }

    public String getLanguageGuideNeed() {
        return languageGuideNeed;
    }

    public String getTourState() {
        return tourState;
    }

    public String getTourGuideState() {
        return tourGuideState;
    }

    public Double getNumberOfDays() {
        return numberOfDays;
    }

    public Double getPrice() {
        return price;
    }

    public int getMaxNumberOfPassengers() {
        return maxNumberOfPassengers;
    }

    public int getCurrentPassengers() {
        return currentPassengers;
    }

    public int getMaxNumberOfGuides() {
        return maxNumberOfGuides;
    }

    public int getCurrentGuides() {
        return currentGuides;
    }
    
//    Setter

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public void setStartFrom(String startFrom) {
        this.startFrom = startFrom;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public void setLanguageGuideNeed(String languageGuideNeed) {
        this.languageGuideNeed = languageGuideNeed;
    }

    public void setTourState(int passengersSign) {
        if(this.getCurrentPassengers() + passengersSign <= this.getMaxNumberOfPassengers()) {
            this.tourState = "Not Full";
        }
        else this.tourState = "Full";
    }

    public void setTourGuideState() {
        if(this.getCurrentGuides() + 1 <= this.getMaxNumberOfGuides()) {
            this.tourGuideState = "NOT FULL";
        }
        else this.tourGuideState = "FULL";
    }

    public void setNumberOfDays(Double numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setMaxNumberOfPassengers(int maxNumberOfPassengers) {
        this.maxNumberOfPassengers = maxNumberOfPassengers;
    }

    public void setCurrentPassengers(int currentPassengers) {
        this.currentPassengers += currentPassengers;
    }

    public void setMaxNumberOfGuides(int maxNumberOfGuides) {
        this.maxNumberOfGuides = maxNumberOfGuides;
    }

    public void setCurrentGuides(int currentGuides) {
        this.currentGuides += currentGuides;
    }

}