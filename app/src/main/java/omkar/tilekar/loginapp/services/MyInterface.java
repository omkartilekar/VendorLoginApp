package omkar.tilekar.loginapp.services;

public interface MyInterface {
    void register();
    void login(String orgname,String name,String email,String created_at);
    void logout();
}
