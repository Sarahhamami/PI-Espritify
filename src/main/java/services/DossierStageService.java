package services;

import entities.Dossier_stage;
import entities.OffreStage;
import entities.TypeStage;
import utils.DataSources;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DossierStageService implements IService<Dossier_stage> {

    private Connection conn;
    private Statement ste;
    private PreparedStatement pst;
    public DossierStageService() {
        conn= DataSources.getInstance().getCnx();

    }
    @Override
    public boolean add(Dossier_stage dossierStage) {

        String requete = "INSERT INTO `dossier_stage` (`cv`, `convention`, `copie_cin`, `id_offre`, `id_user`) VALUES (?, ?,?, ?,?);";

        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, dossierStage.getCv());
            pst.setString(2, dossierStage.getConvention());
            pst.setString(3, dossierStage.getCopie_cin());
            pst.setInt(4, dossierStage.getId_offre());
            pst.setInt(5, dossierStage.getId());
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
        return false;
    }

    public boolean deleteDos(int id, int id_offre) {
        String requete= "delete from dossier_stage where id_user='"+id+"' and id_offre='"+id_offre+"' ";
        try {
            ste=conn.createStatement();

            int rowsAffected = ste.executeUpdate(requete);
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Dossier_stage dossierStage) {
        String requete= "update dossier_stage set cv=? , convention=?, copie_cin=? where id_user=? and id_offre=?";
        try {
            pst=conn.prepareStatement(requete);
            pst.setString(1, dossierStage.getCv());
            pst.setString(2, dossierStage.getConvention());
            pst.setString(3, dossierStage.getCopie_cin());
            pst.setInt(4, dossierStage.getId());
            pst.setInt(5, dossierStage.getId_offre());


            int rowsAffected = pst.executeUpdate();
            // If rowsAffected > 0, it means the item was successfully added
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dossier_stage> readAll() {
        String requete = "SELECT * FROM `dossier_stage`";
        List<Dossier_stage> lst = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                lst.add(new Dossier_stage(rs.getInt(5), rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lst;
    }

    @Override
    public Dossier_stage readById(int id) {

        return null;
    }

    public int getCountDossier() {
        String requete = "SELECT COUNT(*) AS count FROM dossier_stage ";
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
    public boolean didParticipate(int id_user, int id_offre) {
        String requete = "SELECT * FROM dossier_stage WHERE id_user = ? AND id_offre = ?";
        boolean participated = false; // Initialize with false

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(requete);
            preparedStatement.setInt(1, id_user);
            preparedStatement.setInt(2, id_offre);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                participated = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return participated;
    }

}
