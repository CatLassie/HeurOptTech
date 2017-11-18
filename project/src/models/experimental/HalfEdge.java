package models.experimental;

public class HalfEdge {
	
	public int v2;
	public int p = -1;

	public HalfEdge(int v2, int p){
		this.v2 = v2;
		this.p = p;
	}
	
	public String toString(){
		return "v2: " +v2+ " p: " +p;
	}
	
}
