public class State {
    private int statesNum;

    public void setStatesNum(int statesNum) {
        this.statesNum = statesNum;
    }

    private int symbolsNum;

    public void setSymbolsNum(int symbolsNum) {
        this.symbolsNum = symbolsNum;
    }

    private int startState;

    public void setStartState(int startState) {
        this.startState = startState;
    }

    private List<Integer> finalStates;

    public void setFinalStates(List<Integer> finalStates) {
        this.finalStates = finalStates;
    }

    private Map<Transition, List<Integer>> transitions = new HashMap<>();

    public void addTransition(Transition transition, int from) {
        if (!transitions.containsKey(transition)) {
            transitions.put(transition, new ArrayList<>());
        }

        transitions.get(transition).add(from);
    }

    private Map<Integer, List<Integer>> graph = new HashMap<>();

    public void addGraphEnd(int from, int to) {
        if (!graph.containsKey(from)) {
            graph.put(from, new ArrayList<>());
        }

        graph.get(from).add(to);
    }

    private Set<Integer> reachableStates = new HashSet<>();

    public void determineReachableStates() {
        reachableStates.add(startState);
        List<Integer> states = graph.get(startState);

        reachablesDFS(startState, states);
    }

    private void reachablesDFS(int currentState, List<Integer> availableStates) {
        reachableStates.add(currentState);

        if (availableStates == null) {
            return;
        }

        for (int state : availableStates) {
            reachablesDFS(state, graph.get(state));
        }
    }

    public boolean isValid(String word) {
        for (int finalState : finalStates) {
            if (iterate(word, finalState)) {
                return true;
            }
        }

        return false;
    }

    private boolean iterate(String word, int currentState) {
        if (word.length() == 0) {
            return checkWayFromStartState(currentState);
        }

        Transition transition = new Transition(currentState, word.charAt(word.length() - 1));

        if (transitions.get(transition) == null) {
            return false;
        }

        for (Integer from : transitions.get(transition)) {
            if (iterate(word.substring(0, word.length() - 1), from)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkWayFromStartState(int state) {
        return finalStates.contains(state);
    }
}
