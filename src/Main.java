
import java.util.*;
import java.io.*;
import java.lang.*;
import java.io.File;

public class Main {

    public class Transition {
        public int to;
        public char letter;

        Transition(int to, char letter){
            this.to = to;
            this.letter = letter;
        }

        @Override
        public String toString() {
            return "{ " + to + ", " + letter + " } ";
        }
    }


    public int verticesCount;
    public ArrayList<Transition>[] automata;
    public boolean[] isTerminate;

    public static void main(String []args) {

        new Main().solve("./file.txt");


    }

    public void solve(String filename){

        try{

            readData(filename);

        }
        catch (FileNotFoundException e) {
            System.out.println(String.format("file %s not found", filename));
        }
    }

    public boolean dfs(int v, String currstr, String line, int parent){

        System.out.println("Entering state " + v + " with current string \"" + currstr+"\"");

        if (currstr.length()>line.length()) {
            System.out.println("Collected string is longer than \""+line+"\" \n returning back");
            return false;
        }
        if (currstr.compareTo(line)==0) {
            System.out.println("Collected string is equal to \"" + line + "\" and current state is "+(isTerminate[v] ? "terminated" : "not terminated"));
            return isTerminate[v];
        }


        boolean ans = false;
        for (Transition x : automata[v]){
            if (x.letter==line.charAt(currstr.length())) ans |= dfs(x.to, currstr+x.letter, line, v);
            //if (ans) return true;
        }
        System.out.println("Solution from this state is " + (ans==false ? "not" : "") + " found... Performing rollback to state "+parent);
        return ans;
    }

    public void readData(String filename) throws FileNotFoundException{
        Scanner sc = new Scanner(new File(filename));


        int n = sc.nextInt();
        isTerminate = new boolean[n];
        //System.out.println(isTerminate[0]);
        automata = new ArrayList[n];
        for (int i=0; i<n; i++)
            automata[i] = new ArrayList<Transition>();


        int m = sc.nextInt();
        while (m-- > 0){
            int from = sc.nextInt();
            int to = sc.nextInt();
            char edge = sc.next().charAt(0);
            Transition transition = new Transition(to, edge);
            automata[from].add(transition);
        }
        int term = sc.nextInt();
        //System.out.println("Stuff: "+term);
        while (term-- > 0){
            int state= sc.nextInt();
            isTerminate[state] = true;
            //System.out.println(state);
        }


        int tests = sc.nextInt();
        while (tests-- > 0){
            String line = sc.next();
            if (dfs(0, "", line, -1)==true) System.out.println(line + " was found in automata"); else
                System.out.println(line + " was not found in automata");

        }

    }

}
