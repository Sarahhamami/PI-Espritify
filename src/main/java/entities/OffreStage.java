package entities;



public class OffreStage {

    private int id;
    private String titre;
    private String description;
    private String competance;
    private String description_societe;
    private TypeStage typeStage;
    private String nomSociete;

    public OffreStage(int id, String titre, String description, String competance, String description_societe, TypeStage typeStage, String nomSociete) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.competance = competance;
        this.description_societe = description_societe;
        this.typeStage = typeStage;
        this.nomSociete = nomSociete;
    }

    public OffreStage(String titre, String description, String competance, String description_societe, TypeStage typeStage, String nomSociete) {
        this.titre = titre;
        this.description = description;
        this.competance = competance;
        this.description_societe = description_societe;
        this.typeStage = typeStage;
        this.nomSociete = nomSociete;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompetance() {
        return competance;
    }

    public String getDescription_societe() {
        return description_societe;
    }

    public TypeStage getTypeStage() {
        return typeStage;
    }

    public void setCompetance(String competance) {
        this.competance = competance;
    }

    public void setDescription_societe(String description_societe) {
        this.description_societe = description_societe;
    }

    public void setTypeStage(TypeStage typeStage) {
        this.typeStage = typeStage;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    @Override
    public String toString() {
        return "OffreStage{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", competance='" + competance + '\'' +
                ", description_societe='" + description_societe + '\'' +
                ", typeStage=" + typeStage +
                ", nomSociete='" + nomSociete + '\'' +
                '}';
    }
}
