package interno.poprocket.objetos;

public class MinhasFuncoes {
	public double d2 (double valor) {		
		int    i_val;
		double rs;		
		i_val = (int)(valor*100);
		rs    = (double)(i_val / 100d);
		return rs;
	}

}
