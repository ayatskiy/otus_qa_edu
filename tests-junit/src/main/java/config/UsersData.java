package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config/users_data.properties")
public interface UsersData extends Config {
    String login();
    String password();
    String rusName();
    String rusSurname();
    String engName();
    String engSurname();
    String birth();
    String country();
    String city();
    String engLevel();
}