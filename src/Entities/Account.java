package Entities;

/** Class created to set up a structure for Accounts */
public class Account {
    private String name;
    private String cpf;
    private int posX;
    private int posY;

    public Account(String name, String cpf, int posX, int posY){
        this.name = name;
        // processing CPF, to not have dots("."), neither dashes("-")
        this.cpf = cpf.replace(".", "").replace("-","");
        this.posX = posX;
        this.posY = posY;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCpf() {
        return cpf;
    }
}