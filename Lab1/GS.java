import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.Stack;

public class GS {
	static int n;
	public static String galeShapley(Man[] ms, Woman[] ws) {
		Stack<Integer> m = new Stack<>();
		for(int i = 0; i < n; i++) {
			m.push(i);
		}
		while(!m.isEmpty()) {
			int currMan = m.pop();
			int currWomanIndex = ms[currMan].prefs.pop();
			Woman currWoman = ws[currWomanIndex];
			if(currWoman.partner == -1) {
				currWoman.partner = currMan;
				ms[currMan].partner = currWomanIndex;
			} else if(currWoman.prefs[currMan] < currWoman.prefs[currWoman.partner]) {
				ms[currWoman.partner].partner = -1;
				m.push(currWoman.partner);
				currWoman.partner = currMan;
				ms[currMan].partner = currWomanIndex;
			} else {
				m.push(currMan);
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < n; i++) {
			sb.append(ms[i].name + " -- " + ws[ms[i].partner].name + '\n');
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
				BufferedReader r = new BufferedReader(new FileReader(new File(args[0])));
        String s = r.readLine();
        while(s.charAt(0) == '#') {
        	s = r.readLine();
        }
        String[] parts = s.split("=");
        n = Integer.parseInt(parts[1]);
        Man[] ms = new Man[n];
        Woman[] ws = new Woman[n];
        for(int i = 0; i < n; i++) {
        	s = r.readLine();
        	parts = s.split(" ");
        	ms[i] = new Man(parts[1]);
        	s = r.readLine();
        	parts = s.split(" ");
        	ws[i] = new Woman(parts[1]);
        }

				r.readLine();
        int men = 0;
        int women = 0;
        for(int i = 0; i < 2*n; i++) {
        	s = r.readLine();
        	parts = s.split(" ");
        	int[] prefs = new int[n];
        	for(int j = 0; j < n; j++) {
        		prefs[j] = (Integer.parseInt(parts[j+1]) - 1)/2;
        	}
        	if(Integer.parseInt(parts[0].substring(0, parts[0].length()-1)) % 2 == 1) {
        		ms[men++].setPrefs(prefs);
        	} else {
        		ws[women++].setPrefs(prefs);
        	}
        }


        String ans = galeShapley(ms, ws);
				System.out.print(ans);
				//String writePath = args[0] + ".antonyeskil.out.txt";
				//PrintWriter w = new PrintWriter(writePath, "UTF-8");
				//w.println(ans);
			//	w.close();
	}

	private static class Man {
		String name;
		Stack<Integer> prefs;
		int partner;

		public Man(String name) {
			this.name = name;
			partner = -1;
		}

		public void setPrefs(int[] p) {
			prefs = new Stack<>();
			for(int i = n-1; i >= 0; i--) {
				prefs.push(p[i]);
			}
		}
	}

	private static class Woman {
		String name;
		int[] prefs;
		int partner;

		public Woman(String name) {
			this.name = name;
			partner = -1;
		}

		public void setPrefs(int[] p) {
			prefs = new int[n];
			for(int i = 0; i < n; i++) {
				prefs[p[i]] = i;
			}
		}
	}
}
