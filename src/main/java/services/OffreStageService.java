package services;

import entities.OffreStage;
import entities.TypeStage;
import utils.DataSources;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreStageService implements IService<OffreStage> {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public OffreStageService() {
        conn= DataSources.getInstance().getCnx();

    }
    @Override
    public boolean add(OffreStage offreStage) {

            String requete = "INSERT INTO `offrestage` (`titre`, `description`, `competance`, `desc_soc`, `type`, `nom_soc`)  VALUES (?, ?, ?, ?, ?, ?)";

            try {
                pst = conn.prepareStatement(requete);
                pst.setString(1, offreStage.getTitre());
                pst.setString(2, offreStage.getDescription());
                pst.setString(3, offreStage.getCompetance());
                pst.setString(4, offreStage.getDescription_societe());
                pst.setString(5, offreStage.getTypeStage().name());

                System.out.println(offreStage.getTypeStage().name());
                pst.setString(6, offreStage.getNomSociete());
                int rowsAffected = pst.executeUpdate();
                // If rowsAffected > 0, it means the item was successfully added
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace(); // Print the exception for debugging purposes
                return false; // Return false indicating failure
            }

    }



    @Override
    public boolean delete(int id) {
        String requete= "delete from offrestage where id='"+id+"'";
        try {
            ste=conn.createStatement();

            int rowsAffected = ste.executeUpdate(requete);
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(OffreStage offreStage) {
        String requete= "update offrestage set titre=? , description=?, competance=?, desc_soc=?, type=?, nom_soc=?  where id=?";
        try {
            pst=conn.prepareStatement(requete);
            pst.setString(1, offreStage.getTitre());
            pst.setString(2, offreStage.getDescription());
            pst.setString(3, offreStage.getCompetance());
            pst.setString(4, offreStage.getDescription_societe());
            pst.setString(5, offreStage.getTypeStage().name());
            pst.setString(6,offreStage.getNomSociete());
            pst.setInt(7, offreStage.getId());
            pst.executeUpdate();
            int rowsAffected = pst.executeUpdate();
            // If rowsAffected > 0, it means the item was successfully added
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OffreStage> readAll() {
        String requete = "SELECT * FROM offrestage";
        List<OffreStage> lst = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                String enumString = rs.getString("type");
                TypeStage typeStage = TypeStage.valueOf(enumString); // Convert string to enum
                lst.add(new OffreStage(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), typeStage, rs.getString(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lst;
    }

    @Override
    public OffreStage readById(int id) {
        String requete="select * from offrestage where id='"+id+"'";
        OffreStage offreStage=null;
        try {
            ste=conn.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            while (rs.next()){
                String enumString = rs.getString("type");
                TypeStage typeStage = TypeStage.valueOf(enumString);
                offreStage=new OffreStage(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), typeStage, rs.getString(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return offreStage;

    }

    public int getCountType(String type) {
        String requete = "SELECT COUNT(type) FROM offrestage WHERE type=?";
        int count = 0;
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, type);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    public int getCountOffre() {
        String requete = "SELECT COUNT(*) AS count FROM offrestage ";
        int count = 0; // Initialize count to 0
        try (Statement ste = conn.createStatement();
             ResultSet rs = ste.executeQuery(requete)) {
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }


}
