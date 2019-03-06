package softuni.domain.models.service;

public class UserServiceModel {
    private String id;
    private String username;
    private String password;
    private String email;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //^[A-z0-9._%-]+@[A-z]+\.[A-z]{2,6}$
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
