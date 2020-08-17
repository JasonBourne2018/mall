package tmall.bean;

public class User {
    private String password;
    private String name;
    private int id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnonymousName() {
        if (null==name) {
            return null;
        }
        if (name.length()<=1) {
            return "*";
        }
        char[] cs = name.toCharArray();
        for (int i = 1; i < name.length() - 1; i++) {
            cs[i] = '*';
        }
        return new String(cs);
    }
}
