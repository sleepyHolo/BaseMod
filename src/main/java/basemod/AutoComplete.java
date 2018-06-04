package basemod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.badlogic.gdx.Input.Keys;

public class AutoComplete {
	
	public static class Pair {
		public int start, end;
		public Pair(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		public Pair cpy() {
			return new Pair(start, end);
		}
		
		public Pair set(int start, int end) {
			this.start = start;
			this.end = end;
			return this;
		}
	}
	
	public static final String[] COMMANDS = {"relic" , "hand", "info", "kill",
			"gold", "energy", "deck", "draw", "fight", "event", "potion", "unlock",
			"power", "clear", "help", "hp", "maxhp"};
	
	public static boolean enabled = true;
	public static int selectKey = Keys.SHIFT_LEFT;
	public static int offset = 0;
	private static ArrayList<String> lastCompletions;
	private static ArrayList<Pair> completionPairs;
	private static String[] tokens;
	private static int lastLength;
	private static boolean foundMatch;
	
	public static void reset() {
		lastCompletions = new ArrayList<>();
		completionPairs = new ArrayList<>();
		tokens = new String[]{};
		lastLength = 0;
		foundMatch = false;
	}

	public static void complete(boolean isCharacterRemoved) {
		System.out.println("Completing " + DevConsole.currentText);
		lastLength = tokens.length;
		// To get the tokens, we first trim the current Text (removing whitespaces from the start and end)
		// then we split it using a pattern that matches one or more consecutive whitespaces
		// The resulting array tokens only has Strings with no whitespaces
		tokens = DevConsole.currentText.trim().split(DevConsole.PATTERN);
		if (tokens.length < 1) {
			return;
		}
		
		if (tokens.length < 2)
		if (isCharacterRemoved) {
			if (!tokensSizeChanged()) {
				completionPairs.remove(completionPairs.size() - 1);
			} else {
				createCompletions();
				createPair(tokens[0]);
			}
		} else {
			if (!tokensSizeChanged()) {
				createPair(tokens[0]);
			} else {
				createCompletions();
				createPair(tokens[0]);
			}
		}
	}
	
	private static void createPair(String beginning) {
		Pair pair = null;
		if (!completionPairs.isEmpty()) {
			pair = completionPairs.get(completionPairs.size() - 1).cpy();
		} else {
			pair = new Pair(0, lastCompletions.size());
		}
		binarySearch(pair, beginning);
	}

	private static void binarySearch(Pair pair, String beginning) {
		foundMatch = false;
		if (pair.end >= pair.start) {
			return;
		}
		int middle = pair.start + (pair.start - pair.end) / 2;
		int comparison = lastCompletions.get(middle).compareTo(beginning);
		if (lastCompletions.get(middle).startsWith(beginning)) {
			foundMatch = true;
			pair.set(middle, middle);
		} else if (comparison > 0) {
			binarySearch(pair.set(pair.start, middle - 1), beginning);
		} else if (comparison < 0) {
			binarySearch(pair.set(middle + 1, pair.end), beginning);
		}
	}

	private static void createCompletions() {
		lastCompletions.clear();
		if (tokens.length < 2) {
			lastCompletions.addAll(Arrays.asList(COMMANDS));
		} else {
			
		}
		Collections.sort(lastCompletions);
	}

	public static boolean tokensSizeChanged() {
		return tokens.length != lastLength;
	}
}
