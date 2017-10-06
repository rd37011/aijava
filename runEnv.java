/* Author - Ryan Diaz
 * Date - 8/28/17
 * 
 * TODO:
 * figure out how to set states & print
 * figure out how to edit state of 
 * 
 */
package assignment1;

import java.util.Random;
import java.util.Scanner;

public class runEnv {

	public static int points;
	public static boolean dirt;
	public static String mark;
	public String dirtmarker;
	public boolean dirtstatus;

	public static void main(String[] args) throws Exception {

		Scanner in = new Scanner(System.in);
		int size; // also dirtorder 1114a
		System.out.println("Enter the environment size as int:");// throws
																	// exception
		size = in.nextInt();

		runEnv[] env = new runEnv[size];
		randomStates(env);
		agent.start(env);

	}

	public runEnv(boolean dirt, String mark) { // test case here to make sure
												// dirt is being set
		this.dirtstatus = dirt;
		this.dirtmarker = mark;
	}

	public boolean getDirt() {
		return this.dirtstatus;
	}

	public void setDirt(boolean b, runEnv[] env, int index) {
		env[index].dirtstatus = b;
	}

	// public static void sequentialStates(runEnv[] env){ // todo
	//
	// }
	public static void randomStates(runEnv[] env) throws Exception {
		int rands = new Random().nextInt(env.length) + 1;
		int indices = 0;
		System.out.println(rands + " dirty tiles ");
		for (int i = 0; i < rands; i++) {
			indices = new Random().nextInt(rands); // would take these points
													// and use them to seed the
													// environment
			System.out.println("at index " + indices);
			env[indices] = new runEnv(true, "x");
		}
		if (rands == env.length) {
			for (int i = 0; i < env.length; i++) {
				env[i] = new runEnv(true, "x");
			}
		} else {
			for (int i = 0; i < env.length; i++) {
				if (i == indices) {
					env[i] = new runEnv(true, "x"); // battery will be
													// maintained by the agent,
													// points will be handled by
													// this environment
				} else {
					env[i] = new runEnv(false, " ");
				}
			}
		}
	}
}

// has to be a scoring system somewhere
// print the "screen" using formatted printing?
class agent { // add points in the environment
	private static int battery = 20;// position will be updated not once, but
									// every time the agent moves. really smart
									// to make this global?
	private static int position;
	static agent reflex = new agent(20, 0);

	public agent(int battery, int position) { // recursion
		this.battery = battery;
		this.position = position;
	}

	public static String[] start(runEnv[] env) throws Exception {
		// create a state array to match what is going on in the environment
		// randomize
		String[] agentCursor = new String[env.length]; // position must be
														// updated
		agentCursor[reflex.position] = "^";
		for (int i = 0; i < agentCursor.length; i++) {
			if (i == reflex.position) {
				agentCursor[i] = "^";
			} else {
				agentCursor[i] = " ";
			}
		}

		printState(env, agentCursor);

		while (battery != 0) { // should this run until battery dies? //
								// interpret input (percept)
			System.out.println(reflex.position); // must refresh the position
			if (env[reflex.position].getDirt() == true) { // action
				succ(env, agentCursor);

			} else if (position == 0 && env[position].getDirt() == false) { // action
				position = moveRight(env, agentCursor);
				printState(env, agentCursor);
			} else if (position == 1 && env[position].getDirt() == false) {
				position = moveLeft(env, agentCursor);
				printState(env, agentCursor);
			} else {
				runEnv.randomStates(env);
			}
		}
		return agentCursor;
	}

	public static void printState(runEnv[] env, String[] agentCursor) {
		for (int i = 0; i < env.length - 1; i++) {
			System.out.print("[" + env[i].dirtmarker + "]"); // ASCII values to
																// label the
																// tiles
		}
		System.out.println("[" + env[env.length - 1].dirtmarker + "]");

		for (int i = 0; i < agentCursor.length - 1; i++) {
			System.out.print("[" + agentCursor[i] + "]");
		}
		System.out.println("[" + agentCursor[agentCursor.length - 1] + "]");
		System.out.println("Battery: " + reflex.battery);
		System.out.println("Points: " + runEnv.points);
		System.out.println("Agent position: " + reflex.position);
	}

	public static int getPosition() {
		return agent.position;
	}

	public static int moveLeft(runEnv[] env, String agentCursor[]) {
		reflex.battery--;
		reflex.position--;
		int temp = reflex.position + 1;
		agentCursor[temp] = agentCursor[reflex.position];
		agentCursor[position] = " ";
		System.out.println("Moving left to position A.");
		return position;
	}

	public static int moveRight(runEnv[] env, String agentCursor[]) {

		reflex.battery--;
		reflex.position++;
		int temp = reflex.position - 1;
		agentCursor[reflex.position] = agentCursor[temp];
		agentCursor[temp] = " ";
		System.out.println("Moving right to position B.");
		return position;
	}

	public static void succ(runEnv[] env, String agentCursor[]) {
		env[reflex.position].setDirt(false, env, reflex.position);
		env[reflex.position].dirtmarker = " ";
		reflex.battery--;
		runEnv.points++;
		System.out.println("Sucking.");
	}
}

