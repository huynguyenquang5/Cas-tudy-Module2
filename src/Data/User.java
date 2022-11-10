package Data;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private String userId;
    private String name;
    private String telephone;
    private String address;
    private LocalDate dateRegister;
    private String password;

    public User(String userId, String name, String telephone, String address, LocalDate dateRegister, String password) {
        this.userId = userId;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.dateRegister = dateRegister;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(LocalDate dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User.txt{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", dateRegister=" + dateRegister +
                ", password='" + password + '\'' +
                '}';
    }

}
