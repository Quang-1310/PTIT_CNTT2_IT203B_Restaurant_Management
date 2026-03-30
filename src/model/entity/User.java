package model.entity;

import model.enums.UserRole;

public class User {
    private int userID;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
    private boolean isActive;

    public User() {
    }

    public User(int userID, String userName, String email, String password, String phone, UserRole role, boolean isActive) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.isActive = isActive;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void displayData(){
        System.out.printf("| %-10s | %-25s | %-25s | %-15s | %-15s | %-10s | %-10s |\n",
                this.userID, this.userName, this.email, this.password.substring(0,10), this.phone, this.role, this.isActive);
    }
}
