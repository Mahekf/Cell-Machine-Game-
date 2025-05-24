/* import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;  

public class MainN4 extends JFrame  {

    public static void lancerFenetre() {
        SwingUtilities.invokeLater(() -> {
          // Créer un tableau et initialiser les zones constructibles
        Tableau tableau = new Tableau(10, 10);
        for (int x = 1; x < 5; x++) {
            for (int y = 1; y < 5; y++) {
                tableau.setZoneConstructible(x, y, true);  // Zone constructible
            }
        }

       // Créer une cellule à déplacer et ajouter un pusher
        Cellule moveCell = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1,1));
        Cellule pusherCell2 = new Cellule(TypeCell.GENERATOR, Action.DUPLICATE, Orientation.EAST, new Case(3,3));
        
        Cellule Ennemycell1 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(7, 3));
        Cellule Ennemycell2 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(8, 3));
        Cellule Ennemycell3 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(6, 3));


        tableau.ajouterCellule(moveCell, 1, 1); 
        tableau.ajouterCellule(pusherCell2, 3, 3);
        tableau.ajouterCellule(Ennemycell1 , 7, 3);
        tableau.ajouterCellule(Ennemycell2 , 8, 3);
        tableau.ajouterCellule(Ennemycell2 , 6, 3);
        

            // --- Création de la fenêtre ---
            JFrame frame = new JFrame("Level 4");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(530, 570);
            frame.setLayout(new BorderLayout());

            // --- Ajout du panneau de jeu ---
            NiveauGenerale gridPanel = new NiveauGenerale(tableau);
            frame.add(gridPanel, BorderLayout.CENTER);

            // --- Création et ajout du bouton Re-start ---
            JButton restartButton = new JButton("Restart");
            restartButton.addActionListener(e -> {
                System.out.println("Restart button clicked !!");
                gridPanel.setDeplacement(true); //Pour que les cellules soit déplaçable 
                // Exemple de réinitialisation : même logique que lors du premier lancement
                boolean[][] zones = new boolean[10][10];
                for (int x = 1; x < 5; x++) {
                    for (int y = 1; y < 5; y++) {
                        zones[x][y] = true;
                    }
                }
                List<Cellule> cellulesInit = new ArrayList<>();
            // Créer une cellule à déplacer et ajouter un pusher
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1,1)));
                
                cellulesInit.add(new Cellule(TypeCell.GENERATOR, Action.DUPLICATE, Orientation.EAST, new Case(3,3)));
                
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(7, 3)));
                
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(8, 3)));
                
                cellulesInit.add( new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(6, 3)));

                tableau.reinitialiserJeu(zones, cellulesInit);
                gridPanel.repaint();
            });

            // Ajout à droite des boutons existants
               //frame.add(restartButton, BorderLayout.SOUTH);
                Component comp = gridPanel.getComponent(0);
                if (comp instanceof JPanel) {
                JPanel buttonPanel = (JPanel) comp;
                buttonPanel.add(restartButton);  // on ajoute le Re-start à droite des autres
                buttonPanel.revalidate(); // mise à jour du layout
                buttonPanel.repaint();
            }
            // On affiche tout
                frame.revalidate();
                frame.repaint(); 


            // --- Affichage final ---
                frame.setLocationRelativeTo(null); // centre la fenêtre
                frame.setVisible(true);
        });
    } 

        public static void main(String[] args) {
        lancerFenetre();
    } 
} */

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;  

public class MainN4 extends JFrame  {
	public static volatile boolean restartDemande = false;
    public static void lancerFenetre() {
        SwingUtilities.invokeLater(() -> {
          // Créer un tableau et initialiser les zones constructibles
        Tableau tableau = new Tableau(10, 10);
        for (int x = 1; x < 5; x++) {
            for (int y = 1; y < 5; y++) {
                tableau.setZoneConstructible(x, y, true);  // Zone constructible
            }
        }

       // Créer une cellule à déplacer et ajouter un pusher
        Cellule moveCell = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1,1));
        Cellule pusherCell2 = new Cellule(TypeCell.GENERATOR, Action.DUPLICATE, Orientation.EAST, new Case(3,3));
        
        Cellule Ennemycell1 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(7, 3));
        Cellule Ennemycell2 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(8, 3));
        Cellule Ennemycell3 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(6, 3));


        tableau.ajouterCellule(moveCell, 1, 1); 
        tableau.ajouterCellule(pusherCell2, 3, 3);
        tableau.ajouterCellule(Ennemycell1 , 7, 3);
        tableau.ajouterCellule(Ennemycell2 , 8, 3);
        tableau.ajouterCellule(Ennemycell2 , 6, 3);
        

            // --- Création de la fenêtre ---
            JFrame frame = new JFrame("Level 4");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(530, 570);
            frame.setLayout(new BorderLayout());

            // --- Ajout du panneau de jeu ---
            NiveauGenerale gridPanel = new NiveauGenerale(tableau);
            frame.add(gridPanel, BorderLayout.CENTER);

            // --- Création et ajout du bouton Re-start ---
            JButton restartButton = new JButton("Restart");
            restartButton.addActionListener(e -> {
            	restartDemande = true;
                System.out.println("Restart button clicked !!");
                gridPanel.setDeplacement(true); //Pour que les cellules soit déplaçable 
                // Exemple de réinitialisation : même logique que lors du premier lancement
                boolean[][] zones = new boolean[10][10];
                for (int x = 1; x < 5; x++) {
                    for (int y = 1; y < 5; y++) {
                        zones[x][y] = true;
                    }
                }
                List<Cellule> cellulesInit = new ArrayList<>();
            // Créer une cellule à déplacer et ajouter un pusher
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1,1)));
                
                cellulesInit.add(new Cellule(TypeCell.GENERATOR, Action.DUPLICATE, Orientation.EAST, new Case(3,3)));
                
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(7, 3)));
                
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(8, 3)));
                
                cellulesInit.add( new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(6, 3)));

                tableau.reinitialiserJeu(zones, cellulesInit);
                gridPanel.repaint();
            });

            // Ajout à droite des boutons existants
               //frame.add(restartButton, BorderLayout.SOUTH);
                Component comp = gridPanel.getComponent(0);
                if (comp instanceof JPanel) {
                JPanel buttonPanel = (JPanel) comp;
                buttonPanel.add(restartButton);  // on ajoute le Re-start à droite des autres
                buttonPanel.revalidate(); // mise à jour du layout
                buttonPanel.repaint();
            }
            // On affiche tout
                frame.revalidate();
                frame.repaint(); 


            // --- Affichage final ---
                frame.setLocationRelativeTo(null); // centre la fenêtre
                frame.setVisible(true);
        });
    } 
    public static void resetRestartFlag() {
        restartDemande = false;
    }

        public static void main(String[] args) {
        lancerFenetre();
    } 
}
