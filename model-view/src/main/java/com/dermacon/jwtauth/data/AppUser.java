package com.dermacon.jwtauth.data;

// todo check if lombok works here
public class AppUser {

    private String username;

    private String password;

    private String email;

    private UserRole role;

    public static class Builder {
        private String username;
        private String password;
        private UserRole role;
        private String email;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public AppUser build() {
            return new AppUser(this);
        }

    }

    private AppUser(Builder b) {
        this.username = b.username;
        this.password = b.password;
        this.role = b.role;
        this.email = b.email;
    }

    public AppUser() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUser user = (AppUser) o;

        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
