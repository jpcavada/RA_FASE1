
public class Patron {

	int[] pat;

	
	public Patron (int l_setup, int l_t1, int l_descanso, int l_final, int l_jornada ) {
		pat = new int[l_jornada];
		int pos = 0;
		//no trabajo durante el setup
		for (int i = 0; i < l_setup; i++) {
			pat[pos] = 0;
			pos++;		
		}
		
		//trabajo durante el ti
		for (int i = 0; i < l_t1; i++) {
			pat[pos] = 1;
			pos++;		
		}
		
		//descanso
		for (int i = 0; i < l_descanso; i++) {
			pat[pos] = 0;
			pos++;		
		}
			
		//trabajo durante el t2
		for (int i = 0; i < (l_jornada - l_setup - l_t1 - l_descanso - l_final-1); i++) {
			pat[pos] = 1;
			pos++;		
		}	

		//No trabajo durante el wrapup
		for (int i = 0; i < l_final; i++) {
			pat[pos] = 0;
			pos++;		
		}
	}

	public int[] getPatron() {
		return pat;
	}
	
	public void printPatron() {
		for (int i = 0; i < pat.length; i++) {
			System.out.print(pat[i]+";");
			
		}
		System.out.println("");
	}

}
