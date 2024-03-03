package org.example;

import entities.*;
import services.DossierStageService;
import services.EntretienService;
import services.OffreStageService;
import services.UserService;

import java.util.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        User u= new User("sarah");
        UserService us= new UserService();
        //us.add(u);

        OffreStage o=new OffreStage(101,"STAGEE", "2", "3", "4", TypeStage.remote,"(nn)");
        OffreStageService os=new OffreStageService();
        //os.add(o);
        //os.update(o);
        //os.delete(104);
        /*Boolean isAdded= os.add(o);
        if (isAdded)
            System.out.println("added");*/
       /* for (OffreStage obj : os.readAll()) {
            System.out.println(obj.getId());
        }*/
        int id_user=99;
        Dossier_stage d= new Dossier_stage( id_user,"arij", "arij,", "arij", 101);
        DossierStageService ds= new DossierStageService();
        System.out.println(d.toString());
        //ds.add(d);
       // ds.delete(2);
        //ds.update(d);
       /* for (Dossier_stage obj : ds.readAll()) {
            System.out.println(obj);
        }*/
        //ds.update(d);
        System.out.println(us.readAll().toString());
        //Entretien entretien=new Entretien()
        EntretienService entretienService=new EntretienService();
        entretienService.readAll();

        Date date= new Date();
       // Entretien entretien=new Entretien(77,101,"sara","sara",date, "", true );
        //entretienService.add(entretien);
        /*for (Entretien obj : entretienService.readAll()) {
            System.out.println(obj);
        }
        if (entretienService.deleteEntre(77,101)) {
            System.out.println("entretien deletedSucceffully");
        }*/

        System.out.println(os.getCountType("presentiel"));
        System.out.println(entretienService.getCountByMonth(2024,3));
        System.out.println(entretienService.getDataByLocationAndDate());
    }
}