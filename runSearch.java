import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.List;


public class runSearch {
    public static void main(String[] args){
	int missionaries = 3;
        int cannibals = 3; 
	int boatPosition = 1;
	
	State baseState = new State(missionaries, cannibals, boatPosition);
	bfsearch search = new bfsearch(); 
	State solvedPath = search.execute(baseState);
        System.out.println("Executed.");
	printer(solvedPath);
    }
    public static void printer(State solvedPath){
	if (null == solvedPath) {
	    System.out.print("\nNo solution found.");
	} else {
	    System.out.println("\nSolution (cannibalLeft,missionaryLeft,boat,cannibalRight,missionaryRight): ");
	}
        List<State> solutionPath = new ArrayList<State>();
	State state = solvedPath;
	while(state != null){
	    solutionPath.add(state);
	    state = State.getParent();
	}
	int treeDepth = solutionPath.size() - 1; 
	for(int i = treeDepth; i >= 0; i--){
	    state = solutionPath.get(i);
	    if(state.isGoal()){
                System.out.print(state);
		System.out.print("Solution: " + state.toString());
	    }
	    else{
		System.out.print(state.toString());
	    }
	}
	
	
    }
}
class State{
    private int missionaries;
    private int cannibals; 
    private int boatPosition;
    private static State parent; 
    
    public State(int m, int c, int b){
	this.missionaries = m;  //positive for left, 0 for some number on other side
	this.cannibals = c;     // ^^
	this.boatPosition = b;  // 1 for left side, 0 for other
    }
    public boolean isGoal() {
	return cannibals == 0 && missionaries == 0;
    }
    public boolean isValid(){
	return missionaries >= cannibals || (missionaries != 0 ||
					     cannibals != 0);
    }
    public void addAction(List<State> actions, State newState){
	actions.add(newState);
    }
    public List<State> generateActions() {   //{{1, 0, 1},{0, 1, 1},{2, 0, 1},{0, 2, 1},{1, 1, 1}} possible actions 
	List<State> actions = new ArrayList<State>(); // holds the actions
	if(boatPosition == 0){
	    State newState = new State(missionaries - 1, cannibals, boatPosition - 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	    newState = new State(missionaries, cannibals - 1, boatPosition - 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	    newState = new State(missionaries - 2, cannibals, boatPosition - 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	    newState = new State(missionaries, cannibals - 2, boatPosition - 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	    newState = new State(missionaries - 1, cannibals - 1, boatPosition - 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	}
	if(boatPosition == 1){
	    State newState = new State(missionaries + 1, cannibals, boatPosition + 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	    newState = new State(missionaries, cannibals + 1, boatPosition + 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	    newState = new State(missionaries + 2, cannibals, boatPosition + 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	    newState = new State(missionaries, cannibals + 2, boatPosition + 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	    newState = new State(missionaries + 1, cannibals + 1, boatPosition + 1);
	    if(newState.isValid()){
		newState.setParent(this);
		addAction(actions, newState);
	    }
	}
	return actions;
    }
    private void setParent(State parent) {
	this.parent = parent; 
	
    }
    public static State getParent(){
	return parent;
    }
    @Override
	public String toString(){
	return missionaries + "," + cannibals + "," + boatPosition;
    }

}
class bfsearch{
    public State execute(State baseState){
	
	if(baseState.isGoal()){   // for the future: name variables s/t expressions are as close to English as possible. 
	    return baseState;
	}
	
	Queue <State> frontier = new LinkedList<State>();  //queue
	Set<State> explored = new HashSet<State>();   //cannot contain duplicates!!! found out hashset is o(1) for contains since it just holds keys (like a hashtable!)
	frontier.add(baseState);
	    
	while(true){   // better while condition?
	    if(!frontier.isEmpty()){
                System.out.println("null");  
		return null;
	    }
	    State newState = frontier.poll(); // marks as explored 
	    explored.add(newState);  
	            
	    List<State> actions = newState.generateActions(); 
	            
	    for(int i = 0; i < actions.size(); i++){
		if(!explored.contains(newState) || !frontier.contains(newState)){  //checking for repeated states 
		    if(newState.isGoal()){
			return newState; // as solution
			
		    }
		}
	    }
	}
    }
}
