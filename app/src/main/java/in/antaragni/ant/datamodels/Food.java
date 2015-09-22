package in.antaragni.ant.datamodels;


public class Food {
    private final String name;
    private Venue venue;

    public Food(String name, Venue venue) {
        this.name = name;
        this.venue = venue;
    }

    public String getName(){ return name; }
    public Venue getVenue(){ return venue; }
}
