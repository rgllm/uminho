
class User{
    int id;
    String username;
    String password;
    int tipo; // 1 -> comprador ; 2 -> vendedor

    public User(int id, String username,String password, int tipo){
        this.id=id;
        this.username=username;
        this.password=password;
        this.tipo=tipo;
    }
}

