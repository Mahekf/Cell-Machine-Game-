public class Case {
	enum CaseConstrubrible{WALL, LIBRE, FIXE;}
	
	public  int i; // pour le voisinage: x+-1 pour proche et x pour lointain
	public  int j; 
	//double angle;// pour le voisinage: Y+-1 pour proche et Y pour lointain
	public TypeCell type;
	
	public Case(int i, int j) {
		this.i = i;
		this.j = j;
		//this.type = type;
	}
    
    public int getY() {
    	return j;
    }
    public int getX() {
    	return i;
    }
    public TypeCell getType() {
		return this.type;
	}
}
