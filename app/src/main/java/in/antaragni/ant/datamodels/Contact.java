package in.antaragni.ant.datamodels;


public class Contact {
    private final String name;
    private final String eventname;
    private final String post;
    private final String number;

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
}
