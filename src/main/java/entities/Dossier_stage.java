package entities;

public class Dossier_stage {
    private int id_user;
    private String cv;
    private String convention;
    private String copie_cin;
    private int id_offre;

    public Dossier_stage(int id, String cv, String convention, String copie_cin, int id_offre) {
        this.id_user = id;
        this.cv = cv;
        this.convention = convention;
        this.copie_cin = copie_cin;
        this.id_offre = id_offre;
    }

    public Dossier_stage(String cv, String convention, String copie_cin, int id_offre) {
        this.cv = cv;
        this.convention = convention;
        this.copie_cin = copie_cin;
        this.id_offre = id_offre;
    }

    public int getId() {
        return id_user;
    }

    public String getCv() {
        return cv;
    }

    public String getConvention() {
        return convention;
    }

    public String getCopie_cin() {
        return copie_cin;
    }

    public int getId_offre() {
        return id_offre;
    }

    @Override
    public String toString() {
        return "Dossier_stage{" +
                "id=" + id_user +
                ", cv='" + cv + '\'' +
                ", convention='" + convention + '\'' +
                ", copie_cin='" + copie_cin + '\'' +
                ", id_offre=" + id_offre +
                '}';
    }
}
