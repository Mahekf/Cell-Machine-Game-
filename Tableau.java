/* import java.util.ArrayList;
import java.util.List;


public class Tableau {
    private int largeur;
    private int hauteur;
    private Case[][] grille;
    private Cellule[][] cellules;
    private boolean[][] zoneConstructible; 
    
    // Constructeur
    public Tableau(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.grille = new Case[hauteur][largeur];
        this.cellules = new Cellule[hauteur][largeur];
        this.zoneConstructible = new boolean[hauteur][largeur];

        // Initialisation de la grille avec des cases vides
        for (int j = 0; j < hauteur; j++) {
            for (int i = 0; i < largeur; i++) {
                grille[j][i] = new Case(i, j); // Création de la case
                zoneConstructible[j][i] = false; // Par défaut, aucune zone constructible
            }
        }
    }
    
    //GETTERS 
    public int getlargeur() {
        return this.largeur; 
    }
    public int getHauteur() {
        return this.hauteur; 
    }
    public Cellule[][] getCellules() {
        return cellules;
    }
    
       // Ajouter une cellule à une position spécifique -- en tenant compte de la zone constructibe
    public void ajouterCellule(Cellule cellule, int i, int j) {
        if (i < 0 || i >= largeur|| j < 0 || j >= hauteur) { 
            System.out.println("Coordonnée hors limites! (" + j + "," + i + ")");
            return;
        }
        if (!estConstructible(i, j)) { 
            if (cellule.getTypCell() != TypeCell.ENEMY) {
            System.out.println("Zone non constructible à (" + j + "," + i + ")");
            return;
            }
        }
        if (cellules[j][i] != null) {
            System.out.println("Déjà occupé par une autre cellule à (" + j + "," + i + ")");
            return;
        }
        
        cellules[j][i] = cellule;
        System.out.println("Cellule ajoutée à (" + j + "," + i + ")");
    }

     // Ajouter une cellule à une position spécifique -- sans prendre en tenant compte de la zone constructibe --> Utile pour la duplication des cellule par le generator 
    public void ajouterCelluleSansVerification(Cellule cellule, int i, int j) {
    if (i < 0 || i >= largeur || j < 0 || j >= hauteur) {
        System.out.println("Coordonnée hors limites! (" + j + "," + i + ")");
        return;
    }
    
    // Ne pas vérifier la zone constructible
    if (cellules[j][i] != null) {
        System.out.println("Déjà occupé par une autre cellule à (" + j + "," + i + ")");
        return;
    }
    
    cellules[j][i] = cellule;
    System.out.println("Cellule ajoutée à (" + j + "," + i + ")");
    }

    // Vérifie si une position est valide et libre
    public boolean estPositionValide(int i, int j) {
    return i >= 0 && i < largeur && j >= 0 && j < hauteur && cellules[j][i] == null;
}

    // Retirer une cellule de la grille
    public void retirerCellule(int i, int j) {
    if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
        cellules[j][i] = null; 
    }
}

    public Cellule getCellule(int i, int j) {
        if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
            return cellules[j][i];
        }
        return null; // Si la position est invalide, retourner null
    }

  public void setCellule(int i, int j, Cellule cellule) {
    if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
        cellules[j][i] = cellule;
    }
}


// Vérifier si une case est constructible
public boolean estConstructible(int i, int j) {
    if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
        return zoneConstructible[j][i]; 
    }
    return false;
}
 // Définir une case comme constructible
 public void setZoneConstructible(int i, int j, boolean estConstructible) {
    if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
        zoneConstructible[j][i] = estConstructible;
    }
}

//Pour le bouton re-start en Niveau : 
public void reinitialiserJeu(boolean[][] zonesConstructiblesInitiales, List<Cellule> cellulesInitiales) {
    // Réinitialiser la grille de cellules à un état "vide"
    for (int i = 0; i < hauteur; i++) {
        for (int j = 0; j < largeur; j++) {
            cellules[i][j] = null;  // Vider toutes les cellules
        }
    }
    // Réinitialiser les zones constructibles avec les zones passées en paramètre
    for (int j = 0; j < hauteur; j++) {
        for (int i = 0; i < largeur; i++) {
            zoneConstructible[j][i] = zonesConstructiblesInitiales[i][j]; //ICI zonesConstructiblesInitiales[j][i]
        }
    }

    // Ajouter les cellules spécifiées dans la liste de cellulesInitiales
    for (Cellule cellule : cellulesInitiales) {
        Case position = cellule.getCase();
        ajouterCellule(cellule, position.getX(), position.getY());
    }
}

// Méthode qui compte le nombre de cases contructiole (lignes et colonnes) 
public int[] getNombreColonnesLignesConstructibles() {
    int nbLignes = 0;
    int nbColonnes = 0;
    // Compter les lignes constructibles
    for (int j = 0; j < hauteur; j++) {
        for (int i = 0; i < largeur; i++) {
            if (zoneConstructible[j][i]) {
                nbLignes++;
                break;
            }
        }
    }
    // Compter les colonnes constructibles
    for (int i = 0; i < largeur; i++) {
        for (int j = 0; j < hauteur; j++) {
            if (zoneConstructible[j][i]) {
                nbColonnes++;
                break;
            }
        }
    }
    return new int[]{nbLignes, nbColonnes};
}


    // Afficher le tableau (grille + cellules)
    public void afficherTableau() {
        for (int j = 0; j < hauteur; j++) {
            for (int i = 0; i < largeur; i++) {
                if (cellules[j][i] != null) {
                    System.out.print("[" + cellules[j][i].getType().toString().charAt(0) + "] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Déplacer une cellule en fonction de son orientation
    public void deplacerCellule(int i, int j) {
        if (i < 0 || i >= largeur || j < 0 || j >= hauteur || cellules[j][i] == null) {
            return;
        }

        Cellule cellule = cellules[j][i];

        // Vérification spécifique pour PUSHER ou SLIDE
        if (cellule.getType() == TypeCell.PUSHER || cellule.getType() == TypeCell.SLIDE) {
            int targetX = i + cellule.getOrientation().dj(); // dj = déplacement horizontal (x)
            int targetY = j + cellule.getOrientation().di(); // di = déplacement vertical (y)

            if (targetX >= 0 && targetX < largeur && targetY >= 0 && targetY < hauteur) {
                Cellule celluleDevant = cellules[targetY][targetX];
                if (celluleDevant == null) {
                    System.out.println("Le pusher ou slide est seul et ne peut pas se déplacer.");
                    return; // rien à pousser, donc on arrête ici
                }
            }
        }

          // Ces cellules --> On ne fait rien (elle ne se déplace pas)
            if (cellule.getType() == TypeCell.ENEMY || cellule.getType() == TypeCell.TRASH || cellule.getType() == TypeCell.EMPTY || cellule.getType() == TypeCell.TELEPORTOR || cellule.getType() == TypeCell.SPINNER_DIRECT || cellule.getType() == TypeCell.SPINNER_INDIRECT) {
            return; // Empêcher le déplacement
        } 

        // Calcul de la nouvelle position
        int newX = i + cellule.getOrientation().dj(); // dj = colonne (x)
        int newY = j + cellule.getOrientation().di(); // di = ligne (y)

        // Vérification des limites et de l’occupation
        if (newX >= 0 && newX < largeur && newY >= 0 && newY < hauteur && cellules[newY][newX] == null) {
            cellules[newY][newX] = cellule; // déplacer cellule à la nouvelle position
            cellule.setCase(new Case(newX, newY));
            cellules[j][i] = null; // vider l’ancienne position
            } else if (cellules[newY][newX].getType() == TypeCell.ENEMY) {
            System.out.println("Collision avec ENEMY : suppression !");
            cellules[newY][newX] = cellule;
            cellules[j][i] = null;
            cellules[newY][newX] = null;
        } else if (cellules[newY][newX].getType() == TypeCell.TRASH) {
            cellules[j][i] = null;
        }
    }

    public void deplacerCellule(int fromX, int fromY, int toX, int toY) {
        // Vérification des limites
        if (fromX < 0 || fromX >= largeur || fromY < 0 || fromY >= hauteur ||
            toX < 0 || toX >= largeur || toY < 0 || toY >= hauteur) {
            return;
        }

        Cellule cellule = cellules[fromY][fromX];
        if (cellule == null) return;

        // Empêcher le déplacement de certains types
        if (cellule.getType() == TypeCell.ENEMY || cellule.getType() == TypeCell.TRASH ||
            cellule.getType() == TypeCell.EMPTY || cellule.getType() == TypeCell.TELEPORTOR) {
            return;
        }

        // Vérifie si la destination est libre
        if (cellules[toY][toX] == null) {
            cellules[toY][toX] = cellule;
            cellule.setCase(new Case(toX, toY));
            cellules[fromY][fromX] = null;
        } else if (cellules[toY][toX].getType() == TypeCell.ENEMY) {
            System.out.println("Collision avec ENEMY : suppression !");
            cellules[fromY][fromX] = null;
            cellules[toY][toX] = null;
        } else if (cellules[toY][toX].getType() == TypeCell.TRASH) {
            cellules[fromY][fromX] = null;
        }
    }


    // Pousser une cellule si un pusher est devant
    public void pousserCellule(int i, int j) {
        if (i < 0 || i >= largeur || j < 0 || j >= hauteur || cellules[j][i] == null) {
            return;
        }

        Cellule moveCell = cellules[j][i];
        if (moveCell.getType() != TypeCell.MOVE) return;

        int targetI = i + moveCell.getOrientation().dj();
        int targetJ = j + moveCell.getOrientation().di();

        System.out.println("Avant pousser : MOVE (" + i + "," + j + ") → cible (" + targetI + "," + targetJ + ")");
        if (targetI >= 0 && targetI < largeur && targetJ >= 0 && targetJ < hauteur) {
            Cellule cellule = cellules[targetJ][targetI];

            boolean conditionDeBase =
                cellule != null && (
                    moveCell.isPusherDevant(cellule, moveCell)
                    || (moveCell.isSlideDevant(cellule, moveCell) && moveCell.memeAxe(moveCell, cellule))
                    || (moveCell.isDirectionalDevant(cellule, moveCell) && moveCell.getOrientation() == cellule.getOrientation())
                );

            if (conditionDeBase) {
                System.out.println("MOVE rencontre un PUSHER en (" + targetI + "," + targetJ + ")");
                System.out.println("MOVE rencontre un PUSHER en (" + targetI + "," + targetJ + ")");

                boolean pushed = pousserChaine(targetI, targetJ, moveCell.getOrientation(), moveCell);
                if (pushed) {
                    cellules[targetJ][targetI] = moveCell;
                    cellules[j][i] = null;
                } else {
                    System.out.println("Chaîne bloquée, déplacement impossible.");
                }
            }
        }
    }
    private boolean pousserChaine(int i, int j, Orientation orientation, Cellule precedente) {
        if (i < 0 || i >= largeur || j < 0 || j >= hauteur) return false;

        Cellule courante = cellules[j][i];
        if (courante == null) return true; // Fin de chaîne → on peut pousser

        int nextI = i + orientation.dj();
        int nextJ = j + orientation.di();

        // Si on sort de la grille → bloqué
        if (nextI < 0 || nextI >= largeur || nextJ < 0 || nextJ >= hauteur) return false;

        Cellule suivante = cellules[nextJ][nextI];

        // Si la cellule suivante est un ENEMY, on le supprime, et la cellule courante le remplace
        if (suivante != null && suivante.getType() == TypeCell.ENEMY) {
            System.out.println("ENEMY détecté en (" + nextI + "," + nextJ + ") — supprimé par " + courante.getType());
            cellules[nextJ][nextI] = null;
            cellules[j][i] = null;
            return true;
        }

        // Vérification classique : peut-on pousser ?
        boolean peutPousser = 
            precedente.suivantePusherPossible(precedente, courante) &&
            (
                courante.getType() == TypeCell.PUSHER ||
                (courante.getType() == TypeCell.SLIDE && courante.memeAxe(precedente, courante)) ||
                (courante.getType() == TypeCell.DIRECTIONAL && courante.getOrientation() == orientation)
            );

        if (!peutPousser) return false;

        // Pousser la suite de la chaîne
        if (!pousserChaine(nextI, nextJ, orientation, courante)) return false;

        // Si tout est bon → on pousse
        cellules[nextJ][nextI] = courante;
        cellules[j][i] = null;

        return true;
    }



    public void avancerEtPousser(int i, int j) {
        if (i < 0 || i >= largeur || j < 0 || j >= hauteur) return;

        Cellule moveCell = cellules[j][i];
        if (moveCell == null || moveCell.getType() != TypeCell.MOVE) return;

        // On récupère les coordonnées actuelles
        int x = i;
        int y = j;

        while (true) {
            // Coordonnées de la case devant
            int nextX = x + moveCell.getOrientation().dj();
            int nextY = y + moveCell.getOrientation().di();

            // Si on sort de la grille, on arrête
            if (nextX < 0 || nextX >= largeur || nextY < 0 || nextY >= hauteur) return;

            Cellule devant = cellules[nextY][nextX];

            if (devant == null) {
                // Si la case est vide, on avance
                deplacerCellule(x, y);
                x = nextX;
                y = nextY;
            } else if (moveCell.isPusherDevant(devant, moveCell)) {
                // Si on est juste devant un pusher, on pousse
                pousserCellule(x, y);
                return;
            } else {
                // Si c’est un obstacle non poussable, on arrête
                return;
            }
        }
    }
} */

import java.util.ArrayList;
import java.util.List;


public class Tableau {
    private int largeur;
    private int hauteur;
    private Case[][] grille;
    private Cellule[][] cellules;
    private boolean[][] zoneConstructible; 
    
    // Constructeur
    public Tableau(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.grille = new Case[hauteur][largeur];
        this.cellules = new Cellule[hauteur][largeur];
        this.zoneConstructible = new boolean[hauteur][largeur];

        // Initialisation de la grille avec des cases vides
        for (int j = 0; j < hauteur; j++) {
            for (int i = 0; i < largeur; i++) {
                grille[j][i] = new Case(i, j); // Création de la case
                zoneConstructible[j][i] = false; // Par défaut, aucune zone constructible
            }
        }
    }
    
    //GETTERS 
    public int getlargeur() {
        return this.largeur; 
    }
    public int getHauteur() {
        return this.hauteur; 
    }
    public Cellule[][] getCellules() {
        return cellules;
    }
    
       // Ajouter une cellule à une position spécifique -- en tenant compte de la zone constructibe
    public void ajouterCellule(Cellule cellule, int i, int j) {
        if (i < 0 || i >= largeur|| j < 0 || j >= hauteur) { 
            System.out.println("Coordonnée hors limites! (" + j + "," + i + ")");
            return;
        }
        if (!estConstructible(i, j)) { 
            if (cellule.getTypCell() != TypeCell.ENEMY) {
            System.out.println("Zone non constructible à (" + j + "," + i + ")");
            return;
            }
        }
        if (cellules[j][i] != null) {
            System.out.println("Déjà occupé par une autre cellule à (" + j + "," + i + ")");
            return;
        }
        
        cellules[j][i] = cellule;
        System.out.println("Cellule ajoutée à (" + j + "," + i + ")");
    }

     // Ajouter une cellule à une position spécifique -- sans prendre en tenant compte de la zone constructibe --> Utile pour la duplication des cellule par le generator 
    public void ajouterCelluleSansVerification(Cellule cellule, int i, int j) {
    if (i < 0 || i >= largeur || j < 0 || j >= hauteur) {
        System.out.println("Coordonnée hors limites! (" + j + "," + i + ")");
        return;
    }
    
    // Ne pas vérifier la zone constructible
    if (cellules[j][i] != null) {
        System.out.println("Déjà occupé par une autre cellule à (" + j + "," + i + ")");
        return;
    }
    
    cellules[j][i] = cellule;
    System.out.println("Cellule ajoutée à (" + j + "," + i + ")");
    }

    // Vérifie si une position est valide et libre
    public boolean estPositionValide(int i, int j) {
    return i >= 0 && i < largeur && j >= 0 && j < hauteur && cellules[j][i] == null;
}

    // Retirer une cellule de la grille
    public void retirerCellule(int i, int j) {
    if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
        cellules[j][i] = null; 
    }
}

    public Cellule getCellule(int i, int j) {
        if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
            return cellules[j][i];
        }
        return null; // Si la position est invalide, retourner null
    }

  public void setCellule(int i, int j, Cellule cellule) {
    if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
        cellules[j][i] = cellule;
    }
}


// Vérifier si une case est constructible
public boolean estConstructible(int i, int j) {
    if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
        return zoneConstructible[j][i]; 
    }
    return false;
}
 // Définir une case comme constructible
 public void setZoneConstructible(int i, int j, boolean estConstructible) {
    if (i >= 0 && i < largeur && j >= 0 && j < hauteur) {
        zoneConstructible[j][i] = estConstructible;
    }
}

//Pour le bouton re-start en Niveau : 
public void reinitialiserJeu(boolean[][] zonesConstructiblesInitiales, List<Cellule> cellulesInitiales) {
    // Réinitialiser la grille de cellules à un état "vide"
    for (int i = 0; i < hauteur; i++) {
        for (int j = 0; j < largeur; j++) {
            cellules[i][j] = null;  // Vider toutes les cellules
        }
    }
    // Réinitialiser les zones constructibles avec les zones passées en paramètre
    for (int j = 0; j < hauteur; j++) {
        for (int i = 0; i < largeur; i++) {
            zoneConstructible[j][i] = zonesConstructiblesInitiales[i][j]; //ICI zonesConstructiblesInitiales[j][i]
        }
    }

    // Ajouter les cellules spécifiées dans la liste de cellulesInitiales
    for (Cellule cellule : cellulesInitiales) {
        Case position = cellule.getCase();
        ajouterCellule(cellule, position.getX(), position.getY());
    }
}

// Méthode qui compte le nombre de cases contructible (lignes et colonnes) 
public int[] getNombreColonnesLignesConstructibles() {
    int nbLignes = 0;
    int nbColonnes = 0;
    // Compter les lignes constructibles
    for (int j = 0; j < hauteur; j++) {
        for (int i = 0; i < largeur; i++) {
            if (zoneConstructible[j][i]) {
                nbLignes++;
                break;
            }
        }
    }
    // Compter les colonnes constructibles
    for (int i = 0; i < largeur; i++) {
        for (int j = 0; j < hauteur; j++) {
            if (zoneConstructible[j][i]) {
                nbColonnes++;
                break;
            }
        }
    }
    return new int[]{nbLignes, nbColonnes};
}


    // Afficher le tableau (grille + cellules)
    public void afficherTableau() {
        for (int j = 0; j < hauteur; j++) {
            for (int i = 0; i < largeur; i++) {
                if (cellules[j][i] != null) {
                    System.out.print("[" + cellules[j][i].getType().toString().charAt(0) + "] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Déplacer une cellule en fonction de son orientation
    public void deplacerCellule(int i, int j) {
        if (i < 0 || i >= largeur || j < 0 || j >= hauteur || cellules[j][i] == null) {
            return;
        }

        Cellule cellule = cellules[j][i];

        if (cellule.getType() == TypeCell.MOVE) {
            String relation = cellule.getRelationAvecGenerateur(this);

            
            if (relation.equals("DUPLICATE")) {
                // Cherche le générateur derrière
                for (Orientation o : Orientation.values()) {
                    int nx = i + o.dj();
                    int ny = j + o.di();

                    if (nx >= 0 && nx < largeur && ny >= 0 && ny < hauteur) {
                        Cellule gen = cellules[ny][nx];
                        if (gen != null && gen.getType() == TypeCell.GENERATOR) {
                            // Si le MOVE est derrière le générateur
                            if (i == nx - gen.getOrientation().dj() && j == ny - gen.getOrientation().di()) {
                                gen.dupliquer(this, nx, ny);
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Vérification spécifique pour PUSHER ou SLIDE
        
        if (cellule.getType() == TypeCell.PUSHER || cellule.getType() == TypeCell.SLIDE) {
            int targetX = i + cellule.getOrientation().dj(); // dj = déplacement horizontal (x)
            int targetY = j + cellule.getOrientation().di(); // di = déplacement vertical (y)

            if (targetX >= 0 && targetX < largeur && targetY >= 0 && targetY < hauteur) {
                Cellule celluleDevant = cellules[targetY][targetX];
                if (celluleDevant == null) {
                    System.out.println("Le pusher ou slide est seul et ne peut pas se déplacer.");
                    return; // rien à pousser, donc on arrête ici
                }
            }
        }

          // Ces cellules --> On ne fait rien (elle ne se déplace pas)
            if (cellule.getType() == TypeCell.ENEMY || cellule.getType() == TypeCell.TRASH || cellule.getType() == TypeCell.EMPTY || cellule.getType() == TypeCell.TELEPORTOR || cellule.getType() == TypeCell.SPINNER_DIRECT || cellule.getType() == TypeCell.SPINNER_INDIRECT) {
            return; // Empêcher le déplacement
        } 

        // Calcul de la nouvelle position
        int newX = i + cellule.getOrientation().dj(); // dj = colonne (x)
        int newY = j + cellule.getOrientation().di(); // di = ligne (y)

        // Vérification des limites et de l’occupation
        if (newX >= 0 && newX < largeur && newY >= 0 && newY < hauteur && cellules[newY][newX] == null) {
            cellules[newY][newX] = cellule; // déplacer cellule à la nouvelle position
            cellule.setCase(new Case(newX, newY));
            cellules[j][i] = null; // vider l’ancienne position
            } else if (cellules[newY][newX].getType() == TypeCell.ENEMY) {
            System.out.println("Collision avec ENEMY : suppression !");
            cellules[newY][newX] = cellule;
            cellules[j][i] = null;
            cellules[newY][newX] = null;
        } else if (cellules[newY][newX].getType() == TypeCell.TRASH) {
            cellules[j][i] = null;
        }
    }

    public void deplacerCellule(int fromX, int fromY, int toX, int toY) {
        // Vérification des limites
        if (fromX < 0 || fromX >= largeur || fromY < 0 || fromY >= hauteur ||
            toX < 0 || toX >= largeur || toY < 0 || toY >= hauteur) {
            return;
        }

        Cellule cellule = cellules[fromY][fromX];
        if (cellule == null) return;

        // Empêcher le déplacement de certains types
        if (cellule.getType() == TypeCell.ENEMY || cellule.getType() == TypeCell.TRASH ||
            cellule.getType() == TypeCell.EMPTY || cellule.getType() == TypeCell.TELEPORTOR) {
            return;
        }

        // Vérifie si la destination est libre
        if (cellules[toY][toX] == null) {
            cellules[toY][toX] = cellule;
            cellule.setCase(new Case(toX, toY));
            cellules[fromY][fromX] = null;
        } else if (cellules[toY][toX].getType() == TypeCell.ENEMY) {
            System.out.println("Collision avec ENEMY : suppression !");
            cellules[fromY][fromX] = null;
            cellules[toY][toX] = null;
        } else if (cellules[toY][toX].getType() == TypeCell.TRASH) {
            cellules[fromY][fromX] = null;
        }
    }


    // Pousser une cellule si un pusher est devant
    public void pousserCellule(int i, int j) {
        if (i < 0 || i >= largeur || j < 0 || j >= hauteur || cellules[j][i] == null) {
            return;
        }

        Cellule moveCell = cellules[j][i];
        if (moveCell.getType() != TypeCell.MOVE) return;

        int targetI = i + moveCell.getOrientation().dj();
        int targetJ = j + moveCell.getOrientation().di();

        System.out.println("Avant pousser : MOVE (" + i + "," + j + ") → cible (" + targetI + "," + targetJ + ")");
        if (targetI >= 0 && targetI < largeur && targetJ >= 0 && targetJ < hauteur) {
            Cellule cellule = cellules[targetJ][targetI];

            boolean conditionDeBase =
                cellule != null && (
                    moveCell.isPusherDevant(cellule, moveCell)
                    || (moveCell.isSlideDevant(cellule, moveCell) && moveCell.memeAxe(moveCell, cellule))
                    || (moveCell.isDirectionalDevant(cellule, moveCell) && moveCell.getOrientation() == cellule.getOrientation())
                );

            if (conditionDeBase) {
                System.out.println("MOVE rencontre un PUSHER en (" + targetI + "," + targetJ + ")");
                System.out.println("MOVE rencontre un PUSHER en (" + targetI + "," + targetJ + ")");

                boolean pushed = pousserChaine(targetI, targetJ, moveCell.getOrientation(), moveCell);
                if (pushed) {
                    cellules[targetJ][targetI] = moveCell;
                    cellules[j][i] = null;
                } else {
                    System.out.println("Chaîne bloquée, déplacement impossible.");
                }
            }
        }
    }
    private boolean pousserChaine(int i, int j, Orientation orientation, Cellule precedente) {
        if (i < 0 || i >= largeur || j < 0 || j >= hauteur) return false;

        Cellule courante = cellules[j][i];
        if (courante == null) return true; // Fin de chaîne → on peut pousser

        int nextI = i + orientation.dj();
        int nextJ = j + orientation.di();

        // Si on sort de la grille → bloqué
        if (nextI < 0 || nextI >= largeur || nextJ < 0 || nextJ >= hauteur) return false;

        Cellule suivante = cellules[nextJ][nextI];

        // Si la cellule suivante est un ENEMY, on le supprime, et la cellule courante le remplace
        if (suivante != null && suivante.getType() == TypeCell.ENEMY) {
            System.out.println("ENEMY détecté en (" + nextI + "," + nextJ + ") — supprimé par " + courante.getType());
            cellules[nextJ][nextI] = null;
            cellules[j][i] = null;
            return true;
        }

        // Vérification classique : peut-on pousser ?
        boolean peutPousser = 
            precedente.suivantePusherPossible(precedente, courante) &&
            (
                courante.getType() == TypeCell.PUSHER ||
                (courante.getType() == TypeCell.SLIDE && courante.memeAxe(precedente, courante)) ||
                (courante.getType() == TypeCell.SLIDE && precedente.getType()==TypeCell.PUSHER) ||
                (courante.getType() == TypeCell.DIRECTIONAL && courante.getOrientation() == orientation)
                
            );

        if (!peutPousser) return false;

        // Pousser la suite de la chaîne
        if (!pousserChaine(nextI, nextJ, orientation, courante)) return false;

        // Si tout est bon → on pousse
        cellules[nextJ][nextI] = courante;
        cellules[j][i] = null;

        return true;
    }
    

    public void avancerEtPousser(int i, int j) {
        if (i < 0 || i >= largeur || j < 0 || j >= hauteur) return;

        Cellule moveCell = cellules[j][i];
        if (moveCell == null || moveCell.getType() != TypeCell.MOVE) return;

        // On récupère les coordonnées actuelles
        int x = i;
        int y = j;

        while (true) {
            // Coordonnées de la case devant
            int nextX = x + moveCell.getOrientation().dj();
            int nextY = y + moveCell.getOrientation().di();

            // Si on sort de la grille, on arrête
            if (nextX < 0 || nextX >= largeur || nextY < 0 || nextY >= hauteur) return;

            Cellule devant = cellules[nextY][nextX];

            if (devant == null) {
                // Si la case est vide, on avance
                deplacerCellule(x, y);
                x = nextX;
                y = nextY;
            } else if (moveCell.isPusherDevant(devant, moveCell)) {
                // Si on est juste devant un pusher, on pousse
                pousserCellule(x, y);
                return;
            } else {
                // Si c’est un obstacle non poussable, on arrête
                return;
            }
        }
    }





}




