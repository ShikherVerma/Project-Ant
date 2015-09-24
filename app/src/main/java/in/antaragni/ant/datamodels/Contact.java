package in.antaragni.ant.datamodels;


import in.antaragni.ant.R;
import java.util.Random;

public class Contact {
    private final String name;
    private final String eventname;
    private final String post;
    private final String number;
    private static final Random RANDOM = new Random();
    public Contact(String name,String eventname,String post,String number){
        this.name = name;
        this.eventname = eventname;
        this.post = post;
        this.number = number;
    }

    public String getName()
    {
        return name;
    }
    public String getEventname() { return eventname; }
    public String getNumber() { return number; }
    public String getPost() { return post; }
    public static int getRandomContactDrawable() {
        switch (RANDOM.nextInt(5)) {
            default:
            case 0:
                return R.drawable.cheese_1;
            case 1:
                return R.drawable.cheese_2;
            case 2:
                return R.drawable.cheese_3;
            case 3:
                return R.drawable.cheese_4;
            case 4:
                return R.drawable.cheese_5;
        }
    }

}
