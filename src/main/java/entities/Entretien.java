package entities;

import java.util.Date;

public class Entretien {
    private int id_user;
    private int id_stage;
    private String type;
    private String description;
    private Date dateEntre;
    private String lieu;
    private boolean etat;




    public Entretien(int id_user, int id_stage, String type, String description, Date dateEntre, String lieu, boolean etat) {
        this.id_user = id_user;
        this.id_stage = id_stage;
        this.type = type;
        this.description = description;
        this.dateEntre = dateEntre;
        this.lieu = lieu;
        this.etat = etat;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_stage() {
        return id_stage;
    }

    public void setId_stage(int id_stage) {
        this.id_stage = id_stage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateEntre() {
        return dateEntre;
    }

    public void setDateEntre(Date dateEntre) {
        this.dateEntre = dateEntre;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "entretien{" +
                "id_user=" + id_user +
                ", id_stage=" + id_stage +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", dateEntre=" + dateEntre +
                ", lieu='" + lieu + '\'' +
                ", etat=" + etat +
                '}';
    }
}
