public abstract class User {
    protected String id;
    protected String name;
    protected int age;
    protected String username;
    protected String password;

    public User(String id, String name, int age, String username, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }
}
