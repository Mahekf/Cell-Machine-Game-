import javax.swing.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;  

public class MainN7 extends JFrame  {
	public static volatile boolean restartDemande = false;
    public static void lancerFenetre() {
        SwingUtilities.invokeLater(() -> {
            // --- Initialisation du plateau ---
             Tableau tableau = new Tableau(13, 10);
        for (int x = 1; x < 12; x++) {
            for (int y = 1; y < 4; y++) {
                tableau.setZoneConstructible(x, y, true);  // Zone constructible
            }
        }
        tableau.setZoneConstructible(2, 6, true);
        tableau.setZoneConstructible(2, 5, true);
        tableau.setZoneConstructible(3, 6, true);
            // Ajouter des cellules initiales
              // Créer une cellule à déplacer et ajouter un pusher
        Cellule push1 = new Cellule(TypeCell.PUSHER, Action.PUSH, Orientation.NORTH, new Case(1,1));
        Cellule push2 = new Cellule(TypeCell.PUSHER, Action.PUSH, Orientation.SOUTH, new Case(6,2));
        Cellule slide = new Cellule(TypeCell.SLIDE, Action.PUSH, Orientation.SOUTH, new Case(3,6));

        Cellule moveCell1 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(4,1));
        Cellule moveCell2 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(7,1));
        Cellule moveCell3 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(2,6));
        Cellule moveCell4 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(2,5));

        Cellule spinner = new Cellule(TypeCell.SPINNER_DIRECT, Action.ROTATE, Orientation.EAST, new Case(2,2));

        Cellule Ennemy1 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(11, 6));
        Cellule Ennemy2 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(3, 8));
        Cellule Ennemy3 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(3, 7));
        Cellule Ennemy4 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(10, 6));
        

        Cellule teleporteur = new Cellule(TypeCell.TELEPORTOR, Action.TELEPORT, Orientation.NORTH, new Case(9, 1));
        
        tableau.ajouterCellule(push1, 1, 1);
        tableau.ajouterCellule(push2, 6, 2);
        tableau.ajouterCellule(slide, 3, 6);
        tableau.ajouterCellule(moveCell1, 4, 1);
        tableau.ajouterCellule(moveCell2, 7, 1);
        tableau.ajouterCellule(moveCell3, 2, 6);

        tableau.ajouterCellule(Ennemy1 , 11, 6);
        tableau.ajouterCellule(Ennemy2 , 3, 8);
        tableau.ajouterCellule(Ennemy3 , 3, 7);
        tableau.ajouterCellule(Ennemy4 , 10, 6);
        tableau.ajouterCellule(teleporteur , 9, 1);
        tableau.ajouterCellule(spinner , 2, 2);
        

            // --- Création de la fenêtre ---
            JFrame frame = new JFrame("Level 7");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setSize(530, 570);
            frame.setSize(655, 570);
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
                boolean[][] zones = new boolean[13][10];
                for (int x = 1; x < 12; x++) {
                    for (int y = 1; y < 4; y++) {
                        zones[x][y] = true;
                    }
                }
                zones[2][6] = true;
                zones[3][6] = true;
                List<Cellule> cellulesInit = new ArrayList<>();
               cellulesInit.add( new Cellule(TypeCell.PUSHER, Action.PUSH, Orientation.NORTH, new Case(1,1)));
               cellulesInit.add(new Cellule(TypeCell.PUSHER, Action.PUSH, Orientation.SOUTH, new Case(6,2)));
               cellulesInit.add( new Cellule(TypeCell.SLIDE, Action.PUSH, Orientation.SOUTH, new Case(3,6)));
               cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(2,6)));  
               cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.SOUTH, new Case(4,1)));
               cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(7,1)));
               cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(11, 6)));
               cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(3, 8)));
               cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(3, 7)));
               cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(10, 6)));
               cellulesInit.add(new Cellule(TypeCell.SPINNER_DIRECT, Action.ROTATE, Orientation.SOUTH, new Case(2, 2)));
               cellulesInit.add(new Cellule(TypeCell.TELEPORTOR, Action.TELEPORT, Orientation.SOUTH, new Case(9, 1)));


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

