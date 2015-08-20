package in.antaragni.antaragni.datamodels;

/**
 * Created by shikher on 4/7/15.
 */

public class Venue
{
  private final String location;
  //add google map marker and other things that you can think of

  public Venue(String location)
  {
    this.location = location;
  }

  public String getLocation()
  {
    return location;
  }
}
