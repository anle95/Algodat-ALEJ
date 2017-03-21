package edaf05;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
//		InputStream in = new FileInputStream(new File("data\\" + args[0] + ".txt"));
//        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
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
        for(int i = 0; i < n; i++) {
        	s = r.readLine();
        	parts = s.split(" ");
        	int[] prefs = new int[n];
        	for(int j = 0; j < n; j++) {
        		prefs[j] = (Integer.parseInt(parts[j+1]) - 1)/2;
        	}
        	ms[i].setPrefs(prefs);
        	
        	s = r.readLine();
        	parts = s.split(" ");
        	prefs = new int[n];
        	for(int j = 0; j < n; j++) {
        		prefs[j] = Integer.parseInt(parts[j+1])/2;
        	}
        	ws[i].setPrefs(prefs);
        }
        
        String ans = galeShapley(ms, ws);
        System.out.print(ans);
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

