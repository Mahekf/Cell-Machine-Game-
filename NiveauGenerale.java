/* import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;

public class NiveauGenerale extends JPanel {
   private static final long serialVersionUID = 1L;
    Tableau tableau; 
   
    private int selectedX = -1, selectedY = -1;
    private int dragX = -1, dragY = -1;  // Pour suivre la position du glissement
    //Images 
    private boolean deplacementAutorise = true; 
    private volatile boolean enCours = false; // contrôle du thread d’exécution
    

    Image moveImage, pushImage, slideImage, spinnerImage, ennemyImage, directionalImage, generatorImage, defaultImage, teleportorImage, trashImage, defaultImage2;

    private JButton executeButton;
    private JButton moveButton;
    private JButton menu;

    public NiveauGenerale(Tableau tableau) {
        this.tableau = tableau;
        setFocusable(true);

        // Initialisation du bouton "Exécuter"
        executeButton = new JButton("Execute");
        executeButton.addActionListener(e -> {
            if (!enCours) {
                enCours = true;
                executerDeplacement();
                deplacementAutorise = false;
            }
        });
        
        // Initialisation du bouton "Déplacer"
        JButton moveButton = new JButton("Step by Step");
        moveButton.addActionListener(e -> {
            System.out.println("Le bouton 'Step by Step' a été cliqué !");
            mettreAJourGrille();
            repaint();
            deplacementAutorise = false;
        });

        // Initialisation du bouton "Menu"
        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> {
        System.out.println("Le bouton 'Menu' a été cliqué !");
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
        window.dispose(); // ferme la fenêtre du niveau
        }
        new Levels();
     });

        // Ajouter le bouton à la fenêtre
        JPanel panel = new JPanel();
        
        // Bouton "Exécuter"
        panel.add(executeButton);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        
       

        // Bouton "Déplacer"
        panel.add(moveButton);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);

         // Bouton "Menu" 
        panel.add(menuButton);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH); 
        
    

        // Écouteur de la souris pour sélectionner et déplacer les cellules
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!deplacementAutorise) return; 
                int cellSize = 50;
                int clickX = e.getX() / cellSize;
                int clickY = e.getY() / cellSize;

                //On peut pas déplacer certaines cellules comme =:
                if(tableau.getCellule(clickX, clickY).getType() == TypeCell.ENEMY || tableau.getCellule(clickX, clickY).getType() == TypeCell.TRASH || tableau.getCellule(clickX, clickY).getType() == TypeCell.TELEPORTOR) return;

                if (selectedX == -1 && selectedY == -1) {
                    // Sélectionner la cellule en cliquant dessus
                    if (clickX >= tableau.getlargeur() || clickY >= tableau.getHauteur()) {
                        return;
                    }
                    selectedX = clickX;
                    selectedY = clickY;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!deplacementAutorise) return;
                // Lors du relâchement de la souris, vérifier la position et déplacer la cellule
                if (selectedX != -1 && selectedY != -1) {
                    int cellSize = 50;
                    int releaseX = e.getX() / cellSize;
                    int releaseY = e.getY() / cellSize;

                    if (releaseX >= tableau.getlargeur() || releaseY >= tableau.getHauteur()) {
                        return;
                    }

                    // Déplacer la cellule si la case est constructible
                    if (tableau.estConstructible(releaseX, releaseY)) { 
                        Cellule cellule = tableau.getCellule(selectedX, selectedY);
                        if (cellule != null) {
                        	tableau.deplacerCellule(selectedX, selectedY, releaseX, releaseY);
                        }
                    }
                    // Réinitialiser la sélection
                    selectedX = -1;
                    selectedY = -1;
                    repaint();
                }
            }
        });

        // Ajouter un MouseMotionListener pour déplacer la cellule sélectionnée
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int cellSize = 50;
                // Calculer la position du glissement
                dragX = e.getX() / cellSize;
                dragY = e.getY() / cellSize;

                repaint(); // Redessiner la grille en suivant le glissement
            }
        });

        try { 
            moveImage = ImageIO.read(new File("./images /mover.png"));
            pushImage = ImageIO.read(new File("./images /push.png"));
            slideImage = ImageIO.read(new File("./images /slide.png"));
            spinnerImage = ImageIO.read(new File("./images /spinner.png"));
            ennemyImage = ImageIO.read(new File("./images /enemy.png"));
            directionalImage = ImageIO.read(new File("./images /directional.png"));
            generatorImage = ImageIO.read(new File("./images /generator.png"));
            defaultImage = ImageIO.read(new File("./images /bg.png"));
            teleportorImage = ImageIO.read(new File("./images /teleporter.png"));
            trashImage = ImageIO.read(new File("./images /trash.png"));
            defaultImage2 = ImageIO.read(new File("./images /bg_cons.png"));
        } catch (IOException e) {
            System.err.println("Erreur de chargement de l'image : " + e.getMessage());
        }
    }
    
    private Image chargerImage(String nomFichier) {
        try {
            return ImageIO.read(new File("C:/Users/RimeC/eclipse-workspace/ProjectS3/src/" + nomFichier));
        } catch (IOException e) {
            System.err.println("Erreur de chargement de l'image : " + nomFichier + " -> " + e.getMessage());
            return null;
        }
    }

 @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int cellSize = 50;
    int rows = tableau.getHauteur();
    int cols = tableau.getlargeur();

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int x = j * cellSize;
            int y = i * cellSize;

            // Remplir la cellule avec la couleur appropriée
           if (tableau.estConstructible(j, i)) {
                g.drawImage(defaultImage2, x, y, cellSize, cellSize, this);
                //g.setColor(Color.GREEN);  // Zone constructible
            } else {
                g.drawImage(defaultImage, x, y, cellSize, cellSize, this);
                //g.setColor(Color.RED);  // Zone non constructible
                //g.fillRect(x, y, cellSize, cellSize);
            }
            // Afficher les cellules
            Cellule cellule = tableau.getCellule(j, i);
            if (cellule != null) {
                Image img = null;
                TypeCell type = cellule.getType();

                if (type == TypeCell.MOVE && moveImage != null) {
                    //g.drawImage(moveImage, x, y, cellSize, cellSize, this);
                      img = moveImage;
                } else if (type == TypeCell.PUSHER && pushImage != null) {
                    img = pushImage;
                    //g.drawImage(pushImage, x, y, cellSize, cellSize, this);
                } else if (type == TypeCell.SLIDE && slideImage != null) {
                    //g.drawImage(slideImage, x, y, cellSize, cellSize, this);
                    img = slideImage;
                } else if (type == TypeCell.SPINNER_DIRECT && spinnerImage != null) {
                    //g.drawImage(spinnerImage, x, y, cellSize, cellSize, this);
                    img = spinnerImage;
                } else if (type == TypeCell.ENEMY && ennemyImage != null) {
                    //img = ennemyImage;
                    g.drawImage(ennemyImage, x, y, cellSize, cellSize, this);
                } else if (type == TypeCell.GENERATOR && generatorImage != null) {
                    //g.drawImage(generatorImage, x, y, cellSize, cellSize, this);
                    img = generatorImage;
                } else if (type == TypeCell.DIRECTIONAL && directionalImage != null) {
                    //g.drawImage(directionalImage, x, y, cellSize, cellSize, this);
                    img = directionalImage;
                } else if (type == TypeCell.TRASH && trashImage != null) {
                    g.drawImage(trashImage, x, y, cellSize, cellSize, this);
                    //img = trashImage;
                } else if (type == TypeCell.TELEPORTOR && teleportorImage != null) {
                    g.drawImage(teleportorImage, x, y, cellSize, cellSize, this);
                }
                if (img != null) {
                        Graphics2D g2d = (Graphics2D) g;
                        AffineTransform oldTransform = g2d.getTransform();

                        double centerX = x + cellSize / 2.0;
                        double centerY = y + cellSize / 2.0;
                        double angle = 0;

                        switch (cellule.getOrientation()) {
                        case EAST:  angle = 0; break;                        // EAST = 0 rad
                        case SOUTH: angle = Math.PI / 2; break;              // SOUTH = 90°
                        case WEST:  angle = Math.PI; break;                  // WEST = 180°
                        case NORTH: angle = -Math.PI / 2; break;             // NORTH = -90°
                        default: break;
                    }
                        g2d.translate(centerX, centerY);
                        g2d.rotate(angle);
                        g2d.drawImage(img, -cellSize / 2, -cellSize / 2, cellSize, cellSize, this);
                        g2d.setTransform(oldTransform);

                    }
                }
            
            // Dessiner une bordure autour de chaque cellule
            g.setColor(Color.BLACK);
            g.drawRect(x, y, cellSize, cellSize);

            // Mettre en surbrillance la cellule sélectionnée (si la souris est relâchée, la cellule sera déplacée)
            if (selectedX >= 0 && selectedY >= 0) {
                g.setColor(Color.RED);
                g.drawRect(selectedX * 50, selectedY * 50, 50, 50);
            }
            // Si une cellule est en train de glisser, afficher la cellule à la position du curseur
            if (selectedX >= 0 && selectedY >= 0 && dragX >= 0 && dragY >= 0) {
                int xDrag = dragX * 50;
                int yDrag = dragY * 50;

                Cellule celluleDrag = tableau.getCellule(selectedX, selectedY);
                if (celluleDrag != null) {
                    TypeCell typeDrag = celluleDrag.getType();

                    if (typeDrag == TypeCell.MOVE && moveImage != null) {
                        g.drawImage(moveImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.PUSHER && pushImage != null) {
                        g.drawImage(pushImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.SLIDE && slideImage != null) {
                        g.drawImage(slideImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.SPINNER_DIRECT && spinnerImage != null) {
                        g.drawImage(spinnerImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.ENEMY && ennemyImage != null) {
                        g.drawImage(ennemyImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.GENERATOR && generatorImage != null) {
                        g.drawImage(generatorImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.TRASH && trashImage != null) {
                        g.drawImage(trashImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.TELEPORTOR && teleportorImage != null) {
                        g.drawImage(teleportorImage, xDrag, yDrag, 50, 50, this);
                    }
                }
            }
        }
    }
} 

    private void executerDeplacement() {
     enCours = true;

     new Thread(() -> {
         while (enCours) {
             if (boutonRestartClique()) {
                 System.out.println("Bouton restart cliqué. Arrêt du thread.");
                 enCours = false;
                 MainN1.restartDemande=false ;
                 MainN2.restartDemande=false;
                 MainN3.restartDemande=false;
                 MainN4.restartDemande=false;
                 MainN5.restartDemande=false;
                 break;                 
             }
             mettreAJourGrille();
             if (plusAucunEnnemi()) {
                 System.out.println("Fin du niveau.");
                 enCours = false;
                 break;
             }

             SwingUtilities.invokeLater(() -> {
                 tableau.afficherTableau();
                 repaint();
             });

             try {
                 Thread.sleep(500);
             } catch (InterruptedException e) {
                 e.printStackTrace();
                 enCours = false;
             }
           
         }

         System.out.println("Thread deplacement arrêté");
     }).start();
 }



    
 private boolean plusAucunEnnemi() {
	    for (int y = 0; y < tableau.getHauteur(); y++) {
	        for (int x = 0; x < tableau.getlargeur(); x++) {
	            Cellule c = tableau.getCellule(x, y);
	            if (c != null && c.getType() == TypeCell.ENEMY) {
	                return false; // au moins un ennemi présent
	            }
	        }
	    }
	    return true; // aucun ennemi trouvé
	}
 
 

 public boolean boutonRestartClique() {
     return MainN1.restartDemande ||MainN2.restartDemande||MainN3.restartDemande||MainN4.restartDemande||MainN5.restartDemande;
 }


    public void mettreAJourGrille() {
         System.out.println("mettreAJourGrille appelée !");
        // Créer une liste des positions des cellules à déplacer
        List<Point> positionsACheck = new ArrayList<>();

        for (int j = 0; j < tableau.getHauteur(); j++) {
            for (int i = 0; i < tableau.getlargeur(); i++) {
                Cellule c = tableau.getCellule(i, j);
                if (c != null && !estCelluleFixe(c)) {
                    positionsACheck.add(new Point(i, j));
                }
            }
        }

        // Trier les cellules selon leur orientation pour éviter les conflits de déplacement
        positionsACheck.sort((a, b) -> {
            Cellule ca = tableau.getCellule(a.x, a.y);
            Cellule cb = tableau.getCellule(b.x, b.y);

            if (ca == null || cb == null) return 0;

            Orientation oa = ca.getOrientation();
            Orientation ob = cb.getOrientation();

            // Priorité selon la direction de déplacement
            if (oa == Orientation.EAST && ob == Orientation.EAST) {
                return Integer.compare(b.x, a.x); // droite → droite à gauche
            } else if (oa == Orientation.WEST && ob == Orientation.WEST) {
                return Integer.compare(a.x, b.x); // gauche → gauche à droite
            } else if (oa == Orientation.NORTH && ob == Orientation.NORTH) {
                return Integer.compare(a.y, b.y); // haut → haut vers bas
            } else if (oa == Orientation.SOUTH && ob == Orientation.SOUTH) {
                return Integer.compare(b.y, a.y); // bas → bas vers haut
            }
            // Sinon, pas d’ordre spécifique
            return 0;
        });

        // Traiter les cellules dans l’ordre trié
        for (Point p : positionsACheck) {
            int i = p.x;
            int j = p.y;

            Cellule courante = tableau.getCellule(i, j);
            if (courante == null) continue;

            if (courante.isSpinnerDevant(courante, tableau)) {
                courante.interagirAvecSpinner(courante, tableau);
                
            }

            if (courante.getType() == TypeCell.MOVE) {
                int targetI = i + courante.getOrientation().dj();
                int targetJ = j + courante.getOrientation().di(); 
                System.out.println( courante + " à " + targetI + "," + targetJ);

                // Vérifier si la cellule à la position cible est dans les limites de la grille
            if (targetI >= 0 && targetI < tableau.getlargeur() && targetJ >= 0 && targetJ < tableau.getHauteur()) {
                Cellule celluleCible = tableau.getCellule(targetI, targetJ);
                // Si on rencontre un GENERATOR, on applique la méthode dupliquer
            if (celluleCible != null && celluleCible.getType() == TypeCell.GENERATOR) {
                        System.out.println("GENERATOR détecté en (" + targetI + "," + targetJ + ")");
                        // Appliquer la méthode dupliquer
                        courante.dupliquer(tableau, targetI, targetJ);
                    } else if (celluleCible != null && celluleCible.getType() == TypeCell.TELEPORTOR) { 
                        System.out.println("TELEPORTOR détecté en (" + targetI + "," + targetJ + ")");
                        // Appliquer la méthode téléporter
                        courante.Teleporter(tableau, celluleCible, courante); 
                    } else {
                        tableau.deplacerCellule(i, j);
                        tableau.pousserCellule(i, j);
                        tableau.avancerEtPousser(i, j);
                        System.out.println("on avance");                       
                    }
                }
            }
        } 
        
    repaint();
    if (plusAucunEnnemi()) {
        javax.swing.JOptionPane.showMessageDialog(null, "Bravo, vous avez gagné !");
    }
}
           

    private boolean estCelluleFixe(Cellule c) {
        return c.getType() == TypeCell.SPINNER_DIRECT 
            || c.getType() == TypeCell.SPINNER_INDIRECT 
            || c.getType() == TypeCell.DIRECTIONAL
            || c.getType() == TypeCell.SLIDE
            || c.getType() == TypeCell.GENERATOR
        	|| c.getType() == TypeCell.PUSHER
            || c.getType() == TypeCell.TRASH;
        }

        //Getter
        public void setDeplacement(boolean b) {
            this.deplacementAutorise = b;
        }
} */


import javax.swing.*;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;

public class NiveauGenerale extends JPanel {
   private static final long serialVersionUID = 1L;
    Tableau tableau; 
   
    private int selectedX = -1, selectedY = -1;
    private int dragX = -1, dragY = -1;  // Pour suivre la position du glissement
    //Images 
    private boolean deplacementAutorise = true; 
    private volatile boolean enCours = false; // contrôle du thread d’exécution
    Image moveImage, pushImage, slideImage, spinnerImage, ennemyImage, directionalImage, generatorImage, defaultImage, teleportorImage, trashImage, defaultImage2;

    public NiveauGenerale(Tableau tableau) {
        this.tableau = tableau;
        setFocusable(true);

        // Initialisation du bouton "Exécuter"
        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(e -> {
            if (!enCours) {
            	
                enCours = true;
                executerDeplacement();
                deplacementAutorise = false;
            }
        });
        
        // Initialisation du bouton "Déplacer"
        JButton moveButton = new JButton("Step by Step");
        moveButton.addActionListener(e -> {
            System.out.println("Le bouton 'Step by Step' a été cliqué !");
            mettreAJourGrille();
            repaint();
            deplacementAutorise = false;
        });

        // Initialisation du bouton "Menu"
        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> {
        System.out.println("Le bouton 'Menu' a été cliqué !");
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
        window.dispose(); // ferme la fenêtre du niveau
        }
        new Levels();
     });

        // Ajouter le bouton à la fenêtre
        JPanel panel = new JPanel();
        
        // Bouton "Exécuter"
        panel.add(executeButton);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);
        
        // Bouton "Déplacer"
        panel.add(moveButton);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH);

         // Bouton "Menu" 
        panel.add(menuButton);
        setLayout(new BorderLayout());
        add(panel, BorderLayout.SOUTH); 
        
    
        // Écouteur de la souris pour sélectionner et déplacer les cellules
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!deplacementAutorise) return; 
                int cellSize = 50;
                int clickX = e.getX() / cellSize;
                int clickY = e.getY() / cellSize;

                //On peut pas déplcaer certaines cellules comme =:
                if(tableau.getCellule(clickX, clickY).getType() == TypeCell.ENEMY || tableau.getCellule(clickX, clickY).getType() == TypeCell.TRASH || tableau.getCellule(clickX, clickY).getType() == TypeCell.TELEPORTOR) return;

                if (selectedX == -1 && selectedY == -1) {
                    // Sélectionner la cellule en cliquant dessus
                    if (clickX >= tableau.getlargeur() || clickY >= tableau.getHauteur()) {
                        return;
                    }
                    selectedX = clickX;
                    selectedY = clickY;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!deplacementAutorise) return;
                // Lors du relâchement de la souris, vérifier la position et déplacer la cellule
                if (selectedX != -1 && selectedY != -1) {
                    int cellSize = 50;
                    int releaseX = e.getX() / cellSize;
                    int releaseY = e.getY() / cellSize;

                    if (releaseX >= tableau.getlargeur() || releaseY >= tableau.getHauteur()) {
                        return;
                    }

                    // Déplacer la cellule si la case est constructible
                    if (tableau.estConstructible(releaseX, releaseY)) { 
                        Cellule cellule = tableau.getCellule(selectedX, selectedY);
                        if (cellule != null) {
                        	tableau.deplacerCellule(selectedX, selectedY, releaseX, releaseY);
                        }
                    }
                    // Réinitialiser la sélection
                    selectedX = -1;
                    selectedY = -1;
                    repaint();
                }
            }
        });

        // Ajouter un MouseMotionListener pour déplacer la cellule sélectionnée
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int cellSize = 50;
                // Calculer la position du glissement
                dragX = e.getX() / cellSize;
                dragY = e.getY() / cellSize;

                repaint(); // Redessiner la grille en suivant le glissement
            }
        });

        try { 
            moveImage = ImageIO.read(new File("./images /mover.png"));
            pushImage = ImageIO.read(new File("./images /push.png"));
            slideImage = ImageIO.read(new File("./images /slide.png"));
            spinnerImage = ImageIO.read(new File("./images /spinner.png"));
            ennemyImage = ImageIO.read(new File("./images /enemy.png"));
            directionalImage = ImageIO.read(new File("./images /directional.png"));
            generatorImage = ImageIO.read(new File("./images /generator.png"));
            defaultImage = ImageIO.read(new File("./images /bg.png"));
            teleportorImage = ImageIO.read(new File("./images /teleporter.png"));
            trashImage = ImageIO.read(new File("./images /trash.png"));
            defaultImage2 = ImageIO.read(new File("./images /bg_cons.png"));
        } catch (IOException e) {
            System.err.println("Erreur de chargement de l'image : " + e.getMessage());
        }
    }
    
    private Image chargerImage(String nomFichier) {
        try {
            return ImageIO.read(new File("C:/Users/RimeC/eclipse-workspace/ProjectS3/src/" + nomFichier));
        } catch (IOException e) {
            System.err.println("Erreur de chargement de l'image : " + nomFichier + " -> " + e.getMessage());
            return null;
        }
    }

 @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int cellSize = 50;
    int rows = tableau.getHauteur();
    int cols = tableau.getlargeur();

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int x = j * cellSize;
            int y = i * cellSize;

            // Remplir la cellule avec la couleur appropriée
           if (tableau.estConstructible(j, i)) {
                g.drawImage(defaultImage2, x, y, cellSize, cellSize, this);
                //g.setColor(Color.GREEN);  // Zone constructible
            } else {
                g.drawImage(defaultImage, x, y, cellSize, cellSize, this);
                //g.setColor(Color.RED);  // Zone non constructible
                //g.fillRect(x, y, cellSize, cellSize);
            }
            // Afficher les cellules
            Cellule cellule = tableau.getCellule(j, i);
            if (cellule != null) {
                Image img = null;
                TypeCell type = cellule.getType();

                if (type == TypeCell.MOVE && moveImage != null) {
                    //g.drawImage(moveImage, x, y, cellSize, cellSize, this);
                      img = moveImage;
                } else if (type == TypeCell.PUSHER && pushImage != null) {
                    img = pushImage;
                    //g.drawImage(pushImage, x, y, cellSize, cellSize, this);
                } else if (type == TypeCell.SLIDE && slideImage != null) {
                    //g.drawImage(slideImage, x, y, cellSize, cellSize, this);
                    img = slideImage;
                } else if (type == TypeCell.SPINNER_DIRECT && spinnerImage != null) {
                    //g.drawImage(spinnerImage, x, y, cellSize, cellSize, this);
                    img = spinnerImage;
                } else if (type == TypeCell.ENEMY && ennemyImage != null) {
                    //img = ennemyImage;
                    g.drawImage(ennemyImage, x, y, cellSize, cellSize, this);
                } else if (type == TypeCell.GENERATOR && generatorImage != null) {
                    //g.drawImage(generatorImage, x, y, cellSize, cellSize, this);
                    img = generatorImage;
                } else if (type == TypeCell.DIRECTIONAL && directionalImage != null) {
                    //g.drawImage(directionalImage, x, y, cellSize, cellSize, this);
                    img = directionalImage;
                } else if (type == TypeCell.TRASH && trashImage != null) {
                    g.drawImage(trashImage, x, y, cellSize, cellSize, this);
                    //img = trashImage;
                } else if (type == TypeCell.TELEPORTOR && teleportorImage != null) {
                    g.drawImage(teleportorImage, x, y, cellSize, cellSize, this);
                }
                if (img != null) {
                        Graphics2D g2d = (Graphics2D) g;
                        AffineTransform oldTransform = g2d.getTransform();

                        double centerX = x + cellSize / 2.0;
                        double centerY = y + cellSize / 2.0;
                        double angle = 0;

                        switch (cellule.getOrientation()) {
                        case EAST:  angle = 0; break;                        // EAST = 0 rad
                        case SOUTH: angle = Math.PI / 2; break;              // SOUTH = 90°
                        case WEST:  angle = Math.PI; break;                  // WEST = 180°
                        case NORTH: angle = -Math.PI / 2; break;             // NORTH = -90°
                        default: break;
                    }
                        g2d.translate(centerX, centerY);
                        g2d.rotate(angle);
                        g2d.drawImage(img, -cellSize / 2, -cellSize / 2, cellSize, cellSize, this);
                        g2d.setTransform(oldTransform);

                    }
                }
            
            // Dessiner une bordure autour de chaque cellule
            g.setColor(Color.BLACK);
            g.drawRect(x, y, cellSize, cellSize);

            // Mettre en surbrillance la cellule sélectionnée (si la souris est relâchée, la cellule sera déplacée)
            if (selectedX >= 0 && selectedY >= 0) {
                g.setColor(Color.RED);
                g.drawRect(selectedX * 50, selectedY * 50, 50, 50);
            }
            // Si une cellule est en train de glisser, afficher la cellule à la position du curseur
            if (selectedX >= 0 && selectedY >= 0 && dragX >= 0 && dragY >= 0) {
                int xDrag = dragX * 50;
                int yDrag = dragY * 50;

                Cellule celluleDrag = tableau.getCellule(selectedX, selectedY);
                if (celluleDrag != null) {
                    TypeCell typeDrag = celluleDrag.getType();

                    if (typeDrag == TypeCell.MOVE && moveImage != null) {
                        g.drawImage(moveImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.PUSHER && pushImage != null) {
                        g.drawImage(pushImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.SLIDE && slideImage != null) {
                        g.drawImage(slideImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.SPINNER_DIRECT && spinnerImage != null) {
                        g.drawImage(spinnerImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.ENEMY && ennemyImage != null) {
                        g.drawImage(ennemyImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.GENERATOR && generatorImage != null) {
                        g.drawImage(generatorImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.TRASH && trashImage != null) {
                        g.drawImage(trashImage, xDrag, yDrag, 50, 50, this);
                    } else if (typeDrag == TypeCell.TELEPORTOR && teleportorImage != null) {
                        g.drawImage(teleportorImage, xDrag, yDrag, 50, 50, this);
                    }
                }
            }
        }
    }
} 

 private void executerDeplacement() {
     enCours = true;

     new Thread(() -> {
         while (enCours) {
             if (boutonRestartClique()) {
                 System.out.println("Bouton restart cliqué. Arrêt du thread.");
                 enCours = false;
                 MainN1.restartDemande=false ;
                 MainN2.restartDemande=false;
                 MainN3.restartDemande=false;
                 MainN4.restartDemande=false;
                 MainN5.restartDemande=false;
                 MainN6.restartDemande=false;
                 MainN7.restartDemande=false;
                 MainN8.restartDemande=false;
                 MainN9.restartDemande=false;
                 break;
             }

             mettreAJourGrille();

             if (plusAucunEnnemi()) {
                 System.out.println("Fin du niveau.");
                 enCours = false;
                 break;
             }

             SwingUtilities.invokeLater(() -> {
                 tableau.afficherTableau();
                 repaint();
             });

             try {
                 Thread.sleep(350);
             } catch (InterruptedException e) {
                 e.printStackTrace();
                 enCours = false;
             }
           
         }

         System.out.println("Thread deplacement arrêté");
     }).start();
 }



    
 private boolean plusAucunEnnemi() {
	    for (int y = 0; y < tableau.getHauteur(); y++) {
	        for (int x = 0; x < tableau.getlargeur(); x++) {
	            Cellule c = tableau.getCellule(x, y);
	            if (c != null && c.getType() == TypeCell.ENEMY) {
	                return false; // au moins un ennemi présent
	            }
	        }
	    }
	    return true; // aucun ennemi trouvé
	}
 
 

 public boolean boutonRestartClique() {
     return MainN1.restartDemande ||MainN2.restartDemande||MainN3.restartDemande||MainN4.restartDemande
    		 ||MainN5.restartDemande||MainN6.restartDemande||MainN7.restartDemande||MainN8.restartDemande||MainN9.restartDemande;
 }


    public void mettreAJourGrille() {
         System.out.println("mettreAJourGrille appelée !");
        // Créer une liste des positions des cellules à déplacer
        List<Point> positionsACheck = new ArrayList<>();

        for (int j = 0; j < tableau.getHauteur(); j++) {
            for (int i = 0; i < tableau.getlargeur(); i++) {
                Cellule c = tableau.getCellule(i, j);
                if (c != null && !estCelluleFixe(c)) {
                    positionsACheck.add(new Point(i, j));
                }
            }
        }

        // Trier les cellules selon leur orientation pour éviter les conflits de déplacement
        positionsACheck.sort((a, b) -> {
            Cellule ca = tableau.getCellule(a.x, a.y);
            Cellule cb = tableau.getCellule(b.x, b.y);

            if (ca == null || cb == null) return 0;

            Orientation oa = ca.getOrientation();
            Orientation ob = cb.getOrientation();

            // Priorité selon la direction de déplacement
            if (oa == Orientation.EAST && ob == Orientation.EAST) {
                return Integer.compare(b.x, a.x); // droite → droite à gauche
            } else if (oa == Orientation.WEST && ob == Orientation.WEST) {
                return Integer.compare(a.x, b.x); // gauche → gauche à droite
            } else if (oa == Orientation.NORTH && ob == Orientation.NORTH) {
                return Integer.compare(a.y, b.y); // haut → haut vers bas
            } else if (oa == Orientation.SOUTH && ob == Orientation.SOUTH) {
                return Integer.compare(b.y, a.y); // bas → bas vers haut
            }
            // Sinon, pas d’ordre spécifique
            return 0;
        });

        // Traiter les cellules dans l’ordre trié
        for (Point p : positionsACheck) {
            int i = p.x;
            int j = p.y;

            Cellule courante = tableau.getCellule(i, j);
            if (courante == null) continue;

            if (courante.isSpinnerDevant(courante, tableau)) {
                courante.interagirAvecSpinner(courante, tableau);
                
            }

            if (courante.getType() == TypeCell.MOVE) {
                int targetI = i + courante.getOrientation().dj();
                int targetJ = j + courante.getOrientation().di(); 
                System.out.println( courante + " à " + targetI + "," + targetJ);

                // Vérifier si la cellule à la position cible est dans les limites de la grille
            if (targetI >= 0 && targetI < tableau.getlargeur() && targetJ >= 0 && targetJ < tableau.getHauteur()) {
                Cellule celluleCible = tableau.getCellule(targetI, targetJ);
                // Si on rencontre un GENERATOR, on applique la méthode dupliquer
            if (celluleCible != null && celluleCible.getType() == TypeCell.GENERATOR) {
                        System.out.println("GENERATOR détecté en (" + targetI + "," + targetJ + ")");
                        // Appliquer la méthode dupliquer
                        courante.dupliquer(tableau, targetI, targetJ);
                    } else if (celluleCible != null && celluleCible.getType() == TypeCell.TELEPORTOR) { 
                        System.out.println("TELEPORTOR détecté en (" + targetI + "," + targetJ + ")");
                        // Appliquer la méthode dupliquer
                        courante.Teleporter(tableau, celluleCible, courante); 
                    } else {
                    	
                        tableau.deplacerCellule(i, j);
                        tableau.pousserCellule(i, j);
                        tableau.avancerEtPousser(i, j);            
                        System.out.println("on avance");
                       
                    }
                }
            }
        } 
        
    repaint();
    if (plusAucunEnnemi()) {
        javax.swing.JOptionPane.showMessageDialog(null, "Bravo, vous avez gagné !");
    }
}
           

    private boolean estCelluleFixe(Cellule c) {
        return c.getType() == TypeCell.SPINNER_DIRECT 
            || c.getType() == TypeCell.SPINNER_INDIRECT 
            || c.getType() == TypeCell.DIRECTIONAL
            || c.getType() == TypeCell.SLIDE
            || c.getType() == TypeCell.GENERATOR
        	|| c.getType() == TypeCell.PUSHER
            || c.getType() == TypeCell.TRASH;
        }

        //Getter
        public void setDeplacement(boolean b) {
            this.deplacementAutorise = b;
        }
} 


