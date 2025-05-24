import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainN6 {
	public static volatile boolean restartDemande = false;
    public static void lancerFenetre() {
        SwingUtilities.invokeLater(() -> {
          // Créer un tableau et initialiser les zones constructibles
        Tableau tableau = new Tableau(8, 5);
        for (int x = 1; x < 4; x++) {
            for (int y = 1; y < 5; y++) {
                tableau.setZoneConstructible(x, y, true);  // Zone constructible
            }
        }

        Cellule cellule = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1, 4));
        Cellule cellule2 = new Cellule(TypeCell.GENERATOR, Action.DUPLICATE, Orientation.SOUTH, new Case(2,2));
        Cellule cellule3 = new Cellule(TypeCell.GENERATOR, Action.DUPLICATE, Orientation.SOUTH, new Case(2,3));
        Cellule e1 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(6, 1));
        Cellule e2 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(6, 3));
        Cellule e3 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(7, 1));
    
        tableau.ajouterCellule(cellule, 1,4);
        tableau.ajouterCellule(cellule2, 2, 2);
        tableau.ajouterCellule(cellule3, 2, 3);
        tableau.ajouterCellule(e1, 6, 1);
        tableau.ajouterCellule(e2, 6, 3);
        tableau.ajouterCellule(e3, 7, 1);
        

            // --- Création de la fenêtre ---
            JFrame frame = new JFrame("Level 6");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setSize(530, 570);
            frame.setSize(410, 320);
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
                for (int x = 1; x < 4; x++) {
                    for (int y = 1; y < 5; y++) {
                        zones[x][y] = true;
                    }
                }
                List<Cellule> cellulesInit = new ArrayList<>();
            // Créer une cellule à déplacer et ajouter un pusher
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.EAST, new Case(1, 4)));
                cellulesInit.add(new Cellule(TypeCell.GENERATOR, Action.DUPLICATE, Orientation.SOUTH, new Case(2,2)));
                cellulesInit.add(new Cellule(TypeCell.GENERATOR, Action.DUPLICATE, Orientation.SOUTH, new Case(2,3)));
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(6, 1)));
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(6, 3)));
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(7, 1)));

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
} 