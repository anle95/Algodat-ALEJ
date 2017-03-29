import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.io.FileReader;

public class Prim {
	
	public static String rmvQuotes(String s) {
		return s.replaceAll("“|”| |\"", "");
	}
	
	public static class Edge implements Comparable<Edge> {
		Node a;
		Node b;
		int weight;
		boolean visited;
		
		public Edge(Node n1, Node n2, int w) {
			a = n1;
			b = n2;
			weight = w;
			visited = false;
		}

		@Override
		public int compareTo(Edge e) {
			return weight - e.weight;
		}
	}
	
	public static class Node {
		String name;
		LinkedList<Edge> edges;
		public Node(String n) {
			name = n;
			edges = new LinkedList<>();
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(new File(args[0])));
		HashMap<String, Node> g = new HashMap<>();
		String s = rmvQuotes(r.readLine());
		Node last = null;
		while(s.charAt(s.length() - 1) != ']') {
			g.put(s, new Node(s));
			s = rmvQuotes(r.readLine());
		}
		
		while(!s.equals("")) {
			String[] parts = s.split("--");
			String[] parts2 = parts[1].split("\\[");
			Node n1 = g.get(parts[0]);
			if(n1 == null) System.out.println(s + " " + parts[0]);
			Node n2 = g.get(parts2[0]);
			if(n2 == null) System.out.println(s + " " + parts2[0]);
			int weight = Integer.parseInt(parts2[1].substring(0, parts2[1].length()-1));
			Edge e = new Edge(n1, n2, weight);
			n1.edges.add(e);
			n2.edges.add(e);
			s = rmvQuotes(r.readLine());
			last = n1;
		}
		
		HashSet<Node> v = new HashSet<>();
		v.add(last);
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		for(Edge e : last.edges) {
			e.visited = true;
			pq.add(e);
		}
		int sum = 0;
		while(g.size() > v.size()) {
			Edge e = pq.poll();
			if(!v.contains(e.a)) {
				sum += e.weight;
				v.add(e.a);
				System.out.println(e.a.name + " " + e.b.name);
				for(Edge ed : e.a.edges) {
					if(!ed.visited) {
						ed.visited = true;
						pq.add(ed);
						
					}
				}
			} else if(!v.contains(e.b)) {
				sum += e.weight;
				v.add(e.b);
				System.out.println(e.a.name + " " + e.b.name);
				for(Edge ed : e.b.edges) {
					if(!ed.visited) {
						ed.visited = true;
						pq.add(ed);
					}
				}
			}
		}
		System.out.println(sum);
	}

}

