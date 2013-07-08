import java.io.BufferedWriter;
import java.io.FileWriter;


public class PatronGeneral {
	int hora_ingreso;
	Patron pat;
	int npat;
	
	public PatronGeneral(int n,int a, Patron p) {
		this.hora_ingreso = a;
		this.pat = p;
		this.npat = n;
		
	}
	void ImprimeA (FileWriter fw){
		try {
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < pat.getPatron().length;i++){
				if (pat.getPatron()[i] == 1) {
					int e = hora_ingreso+i;
					bw.write(npat+"\t"+e+"\t"+1+"\r\n");
				}	
			}
			
			bw.flush();
		}
		catch (Exception e) {System.out.println("error de escritura patron: "+npat);}
		
	}
}
