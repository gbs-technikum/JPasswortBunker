package jpasswortbunker.mgm.Database;

import java.util.Objects;

public class Login {

    private String loginame;
    private String password;

    public Login(String loginame, String password) {
        this.loginame = loginame;
        this.password = password;
    }

    public String getLoginame() {
        return loginame;
    }

    public void setLoginame(String loginame) {
        this.loginame = loginame;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(loginame, login.loginame) &&
                Objects.equals(password, login.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(loginame, password);
    }

    @Override
    public String toString() {
        return "Login{" +
                "loginame='" + loginame + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
