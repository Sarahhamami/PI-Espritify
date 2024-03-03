package entities;

import entities.ROLE;

public class User {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private int tel;
    private String image;
    private String rank;
    private int score;
    private String niveau ;
    private ROLE role;
    private String nom_soc;

    public User() {
    }

    public User(String nom) {

        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {

        return id;
    }

    public User(int id, String nom, String prenom, String email, String mdp, int tel, String image, String rank, int score, String nom_soc, String niveau, ROLE role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.image = image;
        this.rank = rank;
        this.score = score;
        this.nom_soc=nom_soc;
        this.niveau = niveau;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", tel=" + tel +
                ", image='" + image + '\'' +
                ", rank='" + rank + '\'' +
                ", score=" + score +
                ", niveau='" + niveau + '\'' +
                ", role=" + role +
                ", nom_soc='" + nom_soc + '\'' +
                '}';
    }
}
