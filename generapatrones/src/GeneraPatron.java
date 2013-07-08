import java.io.*;
import java.util.*;

public class GeneraPatron {

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int total_dias = 1;
		int largo_dia = 144;
		
		
		int largo_jornada = 8*6;
		int largo_descanso = 6;
		int largo_setup = 3;
		int largo_wrapup = 3;
		
		int min_t1 = 2*6;
		int max_t1 = 5*6;
		
		int hor = total_dias*largo_dia;
		int[] periodo_ingreso = new int[hor];
		 
		//Generación dummy de periodos de descanso
		for (int i = 0; i < periodo_ingreso.length;i++) {
			if (i%10 == 0)
				periodo_ingreso[i] = 1;
			else
				periodo_ingreso[i] = 0;
		}
		
		
		
		//creo todos los patrones de jornada entre min_t1 y max_t1
		ArrayList <Patron> listapatrones = CreaPatrones(min_t1, max_t1, largo_setup, largo_descanso , largo_wrapup, largo_jornada );

		/*for (int i = 0; i < listapatrones.size();i++) {
			listapatrones.get(i).printPatron();
		}*/
		
		//Replicar lista de patrones para todas las horas de entrada.
		int npat = 1;
		ArrayList <PatronGeneral> lista = new ArrayList <PatronGeneral> ();
		
		
		for (int i = 0; i < periodo_ingreso.length; i++) {
			if (periodo_ingreso[i] == 1) {
				for (int j = 0; j < listapatrones.size();j++){
					PatronGeneral paux = new PatronGeneral (npat,i,listapatrones.get(j));
					lista.add(paux);
					npat++;
				}
			}
		}
		
		
		
		//IMPRIMO f1_patrones.dat
		try{
			 
    		File file = new File("f1_patrones.dat");
 
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");}
			} catch (Exception e) {}
		try {
			FileWriter fw = new FileWriter ("f1_patrones.dat", true);
			
			fw.write("data;\r\n");
			fw.write("param npat := "+npat+";\r\n");
			fw.write("param hor := "+hor+";\r\n");
			
			fw.write("param a default 0 := \r\n");
			for (int i = 0; i < lista.size(); i++) {
				lista.get(i).ImprimeA(fw);
				
			}
			fw.write(";\r\n");
			//imprimo requerimiento
			//imprimeR(fw);
			imprimeRRandom(fw,hor);
			fw.write("end;\r\n");
			fw.write(";\r\n");
			fw.close();
		} catch (Exception e) {}
		
	}

	public static ArrayList <Patron> CreaPatrones (int min_t1, int max_t1, int largo_setup, int largo_descanso, int largo_wrapup, int largo_jornada ) {
		ArrayList <Patron> patrones = new ArrayList<Patron>();
		for (int i = min_t1; i < max_t1; i++) {
			int largo_t1 = i;
			Patron p = new Patron(largo_setup,largo_t1, largo_descanso, largo_wrapup, largo_jornada);
			patrones.add(p);
		}
		
		patrones.trimToSize();
		
		return patrones;
	}

	static void imprimeR(FileWriter fw) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader("r.txt"));
			String str;
			while ((str = bf.readLine()) != null) {
				fw.write(str+"\r\n");
			}
			bf.close();
		}
		catch (Exception e) {}
		
	return;
	}
	static void imprimeRRandom(FileWriter fw, int hor) {
		try {
			fw.write("param R :=\r\n");
			for (int i = 0; i < hor; i++) {
				String str = i+"\t"+ (int)Math.random()*15;
				fw.write(str+"\r\n");
			} 
			fw.write(";\r\n");
		}
		catch (Exception e) {}
		
	return;
	}
}
