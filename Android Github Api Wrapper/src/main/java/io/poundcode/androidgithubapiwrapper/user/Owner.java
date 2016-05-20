package io.poundcode.androidgithubapiwrapper.user;

import java.io.Serializable;

public class Owner implements Serializable {
    String login;

    public Owner (){}

    public Owner(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
