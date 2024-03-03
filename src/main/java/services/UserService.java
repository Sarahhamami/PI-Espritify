package services;

import entities.OffreStage;
import entities.ROLE;
import entities.TypeStage;
import entities.User;
import utils.DataSources;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {
    private Connection conn;
    private Statement ste;
    public UserService() {
        conn= DataSources.getInstance().getCnx();

    }
    @Override
    public boolean add(User user) {
        String requete= "insert into utilisateur (nom,prenom,email,mdp,tel,image,rank,score) values " +
                "('"+user.getNom()+"','"+user.getNom()+"', '"+user.getNom()+"', '"+user.getNom()+"', 89999, '"+user.getNom()+"','"+user.getNom()+"', 999 )";
        try {
            ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public List<User> readAll() {
        String requete = "SELECT * FROM utilisateur";
        List<User> lst = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            User user=null;
            while (rs.next()){
                String enumString = rs.getString("role");
                ROLE role = ROLE.valueOf(enumString);
                user=new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
                        rs.getInt(9), rs.getString(10), rs.getString(11), role);
                lst.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lst;
    }

    @Override
    public User readById(int id) {
        String requete="SELECT * FROM `utilisateur` where id='"+id+"'";
        User user=null;
        try {
            ste=conn.createStatement();
            ResultSet rs=ste.executeQuery(requete);
            while (rs.next()){
                String enumString = rs.getString("role");
                ROLE role = ROLE.valueOf(enumString);
                user=new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8),
                rs.getInt(9), rs.getString(10), rs.getString(11), role);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public int getCountEtudiant() {
        String requete = "SELECT COUNT(*) AS count FROM utilisateur WHERE role = 'etudiant'";
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
