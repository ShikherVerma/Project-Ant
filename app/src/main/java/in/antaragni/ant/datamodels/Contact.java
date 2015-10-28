package in.antaragni.ant.datamodels;




public class Contact {
    private final String name;
    private final String category;
    private final String post;
    private final String number;

    public Contact(String name,String category,String post,String number){
        this.name = name;
        this.category = category;
        this.post = post;
        this.number = number;
    }

    public String getName()
    {
        return name;
    }
    public String getCategory() { return category; }
    public String getNumber() { return number; }
    public String getPost() { return post; }

}
