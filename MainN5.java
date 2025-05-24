/* import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;  

    public class MainN5 extends JFrame  {

    public static void lancerFenetre() {
        //public static volatile boolean restartDemande = false;
        SwingUtilities.invokeLater(() -> {
          // Créer un tableau et initialiser les zones constructibles
        Tableau tableau = new Tableau(10, 10);
        for (int x = 1; x < 7; x++) {
            for (int y = 1; y < 7; y++) {
                tableau.setZoneConstructible(x, y, true);  // Zone constructible
            }
        }

        Cellule cellule = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1, 2));
        Cellule cellule2 = new Cellule(TypeCell.SLIDE, Action.PUSH, Orientation.EAST, new Case(2, 2)); 
        Cellule cellule3 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(3, 2));
        Cellule cellule4 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(4, 2));
        Cellule cellule6 = new Cellule(TypeCell.SPINNER_DIRECT, Action.ROTATE, Orientation.SOUTH, new Case(6, 5));
        Cellule Trash = new Cellule(TypeCell.TRASH, Action.NONE, Orientation.SOUTH, new Case(6, 3));

        Cellule e1 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(8, 2));
        Cellule e3 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(0, 5));
        Cellule e4 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(9, 2));
        Cellule e5 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(4, 8));
    
        tableau.ajouterCellule(cellule, 1,2);
        tableau.ajouterCellule(cellule2, 2, 2);
        tableau.ajouterCellule(cellule3, 3, 2); 
        tableau.ajouterCellule(cellule4, 4, 2);
        tableau.ajouterCellule(cellule6, 6, 5);
        tableau.ajouterCellule(Trash, 6, 3);

        tableau.ajouterCellule(e1, 8, 2);
        tableau.ajouterCellule(e4, 9, 2);
        tableau.ajouterCellule(e3, 0, 5); 
        tableau.ajouterCellule(e5, 4, 8);

            // --- Création de la fenêtre ---
            JFrame frame = new JFrame("Level 5");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(530, 570);
            frame.setLayout(new BorderLayout());

            // --- Ajout du panneau de jeu ---
            NiveauGenerale gridPanel = new NiveauGenerale(tableau);
            frame.add(gridPanel, BorderLayout.CENTER);

            // --- Création et ajout du bouton Re-start ---
            JButton restartButton = new JButton("Restart");
            restartButton.addActionListener(e -> {
                //restartDemande = true;
                System.out.println("Restart button clicked !!");
                gridPanel.setDeplacement(true); //Pour que les cellules soit déplaçable 
                // Exemple de réinitialisation : même logique que lors du premier lancement
                boolean[][] zones = new boolean[10][10];
                for (int x = 1; x < 7; x++) {
                    for (int y = 1; y < 7; y++) {
                        zones[x][y] = true;
                    }
                }
                List<Cellule> cellulesInit = new ArrayList<>();
            // Créer une cellule à déplacer et ajouter un pusher
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1, 2)));
                
                cellulesInit.add(new Cellule(TypeCell.SLIDE, Action.PUSH, Orientation.EAST, new Case(2, 2)));
                
                cellulesInit.add( new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(3, 2)));
                
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(4, 2)));
                
                cellulesInit.add(new Cellule(TypeCell.SPINNER_DIRECT, Action.ROTATE, Orientation.SOUTH, new Case(6, 5)));

                cellulesInit.add(new Cellule(TypeCell.TRASH, Action.NONE, Orientation.SOUTH, new Case(6, 3)));

                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(8, 2)));

                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(0, 5)));

                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(9, 2)));

                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(4, 8)));

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

    public class MainN5 extends JFrame  {
    public static volatile boolean restartDemande = false;
    public static void lancerFenetre() {
        SwingUtilities.invokeLater(() -> {
          // Créer un tableau et initialiser les zones constructibles
        Tableau tableau = new Tableau(10, 10);
        for (int x = 1; x < 7; x++) {
            for (int y = 1; y < 7; y++) {
                tableau.setZoneConstructible(x, y, true);  // Zone constructible
            }
        }

        Cellule cellule = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1, 2));
        Cellule cellule2 = new Cellule(TypeCell.SLIDE, Action.PUSH, Orientation.EAST, new Case(2, 2)); 
        Cellule cellule3 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(3, 2));
        Cellule cellule4 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(4, 2));
        Cellule cellule6 = new Cellule(TypeCell.SPINNER_DIRECT, Action.ROTATE, Orientation.SOUTH, new Case(6, 5));
        Cellule Trash = new Cellule(TypeCell.TRASH, Action.NONE, Orientation.SOUTH, new Case(6, 3));

        Cellule e1 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(8, 2));
        Cellule e3 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(0, 5));
        Cellule e4 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(9, 2));
        Cellule e5 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(4, 8));
    
        tableau.ajouterCellule(cellule, 1,2);
        tableau.ajouterCellule(cellule2, 2, 2);
        tableau.ajouterCellule(cellule3, 3, 2); 
        tableau.ajouterCellule(cellule4, 4, 2);
        tableau.ajouterCellule(cellule6, 6, 5);
        tableau.ajouterCellule(Trash, 6, 3);

        tableau.ajouterCellule(e1, 8, 2);
        tableau.ajouterCellule(e4, 9, 2);
        tableau.ajouterCellule(e3, 0, 5); 
        tableau.ajouterCellule(e5, 4, 8);

            // --- Création de la fenêtre ---
            JFrame frame = new JFrame("Level 5");
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
                for (int x = 1; x < 7; x++) {
                    for (int y = 1; y < 7; y++) {
                        zones[x][y] = true;
                    }
                }
                List<Cellule> cellulesInit = new ArrayList<>();
            // Créer une cellule à déplacer et ajouter un pusher
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1, 2)));
                
                cellulesInit.add(new Cellule(TypeCell.SLIDE, Action.PUSH, Orientation.EAST, new Case(2, 2)));
                
                cellulesInit.add( new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(3, 2)));
                
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(4, 2)));
                
                cellulesInit.add(new Cellule(TypeCell.SPINNER_DIRECT, Action.ROTATE, Orientation.SOUTH, new Case(6, 5)));

                cellulesInit.add(new Cellule(TypeCell.TRASH, Action.NONE, Orientation.SOUTH, new Case(6, 3)));

                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(8, 2)));

                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(0, 5)));

                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(9, 2)));

                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(4, 8)));

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