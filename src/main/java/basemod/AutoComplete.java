package basemod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;

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

	private static int ID = -1;

	public static final int SMALL_NUMBERS = 12345;
	public static final int BIG_NUMBERS = 54321;
	public static final int CMDS = ++ID;
	public static final int CLEAR = ++ID;
	public static final int DECK = ++ID;
	public static final int DRAW = ++ID;
	public static final int ENERGY = ++ID;
	public static final int EVENT = ++ID;
	public static final int FIGHT = ++ID;
	public static final int GOLD = ++ID;
	public static final int HAND = ++ID;
	public static final int HELP = ++ID;
	public static final int HP = ++ID;
	public static final int INFO = ++ID;
	public static final int KILL = ++ID;
	public static final int MAX_HP = ++ID;
	public static final int POTION = ++ID;
	public static final int POWER = ++ID;
	public static final int RELIC = ++ID;
	public static final int UNLOCK = ++ID;
	public static final int HAND_ADD = ++ID;
	public static final int HAND_REMOVE = ++ID;

	public static final String[] COMMANDS = { "clear", "deck", "draw", "energy", "event", "fight", "gold", "hand",
			"help", "hp", "info", "kill", "maxhp", "potion", "power", "relic", "unlock" };

	private static final int MAX_SUGGESTIONS = 5;
	private static final Color TEXT_COLOR = Color.GRAY.cpy();

	public static boolean enabled = true;
	public static int selectKey = Keys.SHIFT_LEFT;
	public static int fillKey1 = Keys.RIGHT; // TODO LEFT to delete last token
	public static int fillKey2 = Keys.TAB;
	public static int selected = 0;
	private static ArrayList<String> lastCompletions;
	private static Stack<Pair> completionPairs;
	private static String[] tokens;
	private static boolean foundStart, foundEnd, noMatch;
	private static boolean alreadySorted;
	private static boolean commandComplete;
	private static int currentID;
	private static int lastLength = 0;
	private static int whiteSpaces = 0;
	private static int lastWhiteSpaces = 0;
	private static Pattern spacePattern = Pattern.compile(DevConsole.PATTERN);

	private static boolean implementedYet = false;
	private static float drawX;
	private static float promptWidth = 0;
	private static GlyphLayout glyphs;

	// TODO reorder and comment

	public static void init() {
		reset();
	}

	public static void postInit() {
		glyphs = new GlyphLayout(DevConsole.consoleFont, DevConsole.PROMPT);
		promptWidth = glyphs.width;
		calculateDrawX();
		complete(false);
	}

	public static void reset() {
		calculateDrawX();
		currentID = -1;
		lastCompletions = new ArrayList<>();
		completionPairs = new Stack<>();
		tokens = new String[] {};
		selected = 0;
		foundStart = foundEnd = noMatch = false;
		whiteSpaces = 0;
		lastWhiteSpaces = 0;
		lastLength = 0;
	}

	public static void selectUp() {
		if (selected > 0 && !noMatch && !lastCompletions.isEmpty() && !completionPairs.isEmpty()) {
			selected--;
		}
	}

	public static void selectDown() {
		if (!noMatch && !lastCompletions.isEmpty() && !completionPairs.isEmpty()) {
			Pair pair = completionPairs.peek();
			if (selected < pair.end - pair.start && selected < lastCompletions.size() - 1) {
				selected++;
			}
		}
	}

	public static void fillInSuggestion() {
		if (!noMatch && !lastCompletions.isEmpty() && !completionPairs.isEmpty()) {
			DevConsole.currentText = getUncompletedText()
					+ lastCompletions.get(selected + completionPairs.peek().start) + " ";
			reset();
			complete(false);
		}
	}

	// returns the text before the last Space (with the Space appended)
	private static String getUncompletedText() {
		int lastSpace = DevConsole.currentText.lastIndexOf(' ');
		String text = "";
		if (lastSpace != -1) {
			text = DevConsole.currentText.substring(0, lastSpace + 1);
		}
		return text;
	}

	public static void complete(boolean isCharacterRemoved) {
		// To get the tokens, we first trim the current Text (removing whitespaces from the start and end)
		// then we split it using a pattern that matches one or more consecutive whitespaces
		// The resulting array tokens only has Strings with no whitespaces
		tokens = DevConsole.currentText.trim().split(DevConsole.PATTERN);

		Matcher spaceMatcher = spacePattern.matcher(DevConsole.currentText);
		whiteSpaces = 0;
		while (spaceMatcher.find()) {
			if (spaceMatcher.start() != 0) {
				whiteSpaces++;
			}
		}

		createCMDCompletions();

		// clear if a new token is started TODO sometimes completionPairs is not
		// cleared? Clear somewhere else too or completely get rid of the stack
		if (tokenLengthChanged() || DevConsole.currentText.isEmpty()) {
			completionPairs.clear();
		}

		// if the user just deleted the last character we don't calculate the pairs
		// again but instead just use the pair before this one on the stack if it exists
		if (isCharacterRemoved && !tokenLengthChanged() && completionPairs.size() >= 2) {
			completionPairs.pop();
			selected = 0;
			if (completionPairs.peek().end <= -1) {
				noMatch = true;
			}
		} else {
			createPair();
		}
		calculateDrawX();
		lastWhiteSpaces = whiteSpaces;
	}

	private static boolean tokenLengthChanged() {
		return lastLength != tokens.length;
	}

	private static boolean tokenLengthIncreased() {
		return lastLength != tokens.length;
	}

	private static boolean whiteSpacesIncreased() {
		return whiteSpaces > lastWhiteSpaces;
	}

	private static boolean whiteSpacesChanged() {
		return whiteSpaces != lastWhiteSpaces;
	}

	private static void createPair() {
		if (whiteSpacesIncreased()) {
			createPair(" ");
			return;
		}
		createPair(tokens[tokens.length - 1]);
	}

	private static void createPair(String prefix) {
		selected = 0;
		Pair pair = null;
		if (!completionPairs.isEmpty()) {
			// if we already have pairs get the last pair to set a smaller searching scope
			pair = completionPairs.peek().cpy();
		} else {
			pair = new Pair(0, lastCompletions.size() - 1);
		}
		if (prefix == null || prefix.isEmpty() || prefix.equals(" ")) {
			foundStart = foundEnd = true;
			noMatch = false;
			completionPairs.push(pair.set(0, lastCompletions.size()));
			return;
		}
		linearSearch(pair, prefix);
	}

	// I tried implementing a binary search for this but that didn't work so this
	// has to suffice. If anyone can do this please help
	private static void linearSearch(Pair pair, String prefix) {

		foundStart = foundEnd = noMatch = false;
		int size = lastCompletions.size();
		// Search for the start
		while (!foundStart && !noMatch) {
			if (pair.start >= size) {
				noMatch = true;
			} else {
				// The first completion that starts with our prefix is our start
				if (lastCompletions.get(pair.start).startsWith(prefix)) {
					foundStart = true;
				} else {
					pair.start++;
				}
			}
		}

		// Search for the End only if you found the Start
		if (foundStart) {
			pair.end = pair.start + 1;
			while (!foundEnd) {
				// The last element that starts with the prefix is the last element of
				// lastCompletions OR the current element doesnt start with the prefix
				// Either way the index we found is the Element directly before this element
				if (pair.end >= size || !lastCompletions.get(pair.end).startsWith(prefix)) {
					foundEnd = true;
					pair.end--;
				} else {
					// if the above condition is not true inspect the next Element
					pair.end++;
				}
			}
		}

		if (noMatch) {
			// No match value. leads to search automatically returning with noMatch
			// because start is bigger than end (and size)
			pair.set(Integer.MAX_VALUE, Integer.MIN_VALUE);
		}
		completionPairs.push(pair);
	}

	private static void createCMDCompletions() {
		alreadySorted = false;
		implementedYet = false;
		commandComplete = false;
		if (whiteSpaces == 0) {
			implementedYet = true;
			alreadySorted = true;
			if (currentID != CMDS) {
				currentID = CMDS;
				lastCompletions.clear();
				lastCompletions.addAll(Arrays.asList(COMMANDS));
			}
		} else {
			switch (tokens[0].toLowerCase()) {
			case "relic": {
				createRelicCompletions();
				break;
			}
			case "hand": {
				createHandCompletions();
				break;
			}
			case "info": {
				commandComplete = true;
				implementedYet = true;
				break;
			}
			case "kill": {
				createKillCompletions();
				break;
			}
			case "gold": {
				createGoldCompletions();
				break;
			}
			case "energy": {
				createEnergyCompletions();
				break;
			}
			case "deck": {
				createDeckCompletions();
				break;
			}
			case "draw": {
				createDrawCompletions();
				break;
			}
			case "fight": {
				createFightCompletions();
				break;
			}
			case "event": {
				createEventCompletions();
				break;
			}
			case "potion": {
				createPotionCompletions();
				break;
			}
			case "unlock": {
				createUnlockCompletions();
				break;
			}
			case "power": {
				createPowerCompletions();
				break;
			}
			case "clear": {
				createClearCompletions();
				break;
			}
			case "help": {
				commandComplete = true;
				implementedYet = true;
				break;
			}
			case "hp": {
				createHPCompletions();
				break;
			}
			case "maxhp": {
				createMaxHPCompletions();
				break;
			}
			default: {
				noMatch = true;
				break;
			}
			}
			if (!alreadySorted) {
				alreadySorted = true;
				Collections.sort(lastCompletions);
			}
		}
	}

	private static void createMaxHPCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createHPCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createClearCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createPowerCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createUnlockCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createPotionCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createEventCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createFightCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createDrawCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createDeckCompletions() {
		// TODO Auto-generated method stub

	}

	private static void createEnergyCompletions() {
		// TODO Auto-generated method stub

	}

	private static final String[] GOLD_CMDS = { "add", "lose" };

	private static void createGoldCompletions() {
		implementedYet = true;
		if (whiteSpaces == 1) {
			alreadySorted = true;
			if (currentID == GOLD) {
				return;
			}
			currentID = GOLD;
			lastCompletions.clear();
			lastCompletions.addAll(Arrays.asList(GOLD_CMDS));
		} else if (whiteSpaces == 2){
			bigNumbers();
		} else {
			commandComplete = true;
		}
	}

	private static void createKillCompletions() {
		// TODO Auto-generated method stub

	}

	private static final String[] HAND_CMDS = { "add", "remove" };

	private static void createHandCompletions() {
		implementedYet = true;
		if (whiteSpaces == 1) {
			alreadySorted = true;
			if (currentID == HAND) {
				return;
			}
			currentID = HAND;
			lastCompletions.clear();
			lastCompletions.addAll(Arrays.asList(HAND_CMDS));
		} else if (whiteSpaces == 2) {
			if (tokens[1].equalsIgnoreCase("add") || tokens[1].equalsIgnoreCase("a")) {
				if (currentID == HAND_ADD) {
					alreadySorted = true;
					return;
				}
				currentID = HAND_ADD;
				cardIDList(false);
			} else if (tokens[1].equalsIgnoreCase("remove") || tokens[1].equalsIgnoreCase("r")) {
				if (currentID == HAND_REMOVE) {
					alreadySorted = true;
					return;
				}
				currentID = HAND_REMOVE;
				cardIDList(true);
			}
		} else if (whiteSpaces == 3 || whiteSpaces == 4 && !tokens[2].equalsIgnoreCase("all")) {
			smallNumbers();
		} else {
			commandComplete = true;
		}
	}

	private static void cardIDList(boolean isRemove) {
		lastCompletions.clear();
		if (isRemove) {
			lastCompletions.add("all");
		}
		// Replace spaces with underscores to avoid an id being more than one token
		for (String key : CardLibrary.cards.keySet()) {
			lastCompletions.add(key.replace(' ', '_'));
		}
	}

	private static void createRelicCompletions() {
		// TODO Auto-generated method stub

	}

	private static void smallNumbers() {
		alreadySorted = true;
		if (currentID == SMALL_NUMBERS) {
			return;
		}
		currentID = SMALL_NUMBERS;
		lastCompletions.clear();
		for (int i = 1; i < 10; i++) {
			lastCompletions.add(String.valueOf(i));
		}
	}

	private static void bigNumbers() {
		alreadySorted = true;
		if (currentID == BIG_NUMBERS) {
			return;
		}
		currentID = BIG_NUMBERS;
		lastCompletions.clear();
		for (int i = 10; i < 100; i += 10) {
			lastCompletions.add(String.valueOf(i * 10));
			lastCompletions.add(String.valueOf(i * 100));
		}
	}

	private static void calculateDrawX() {
		drawX = DevConsole.CONSOLE_X * Settings.scale + promptWidth + DevConsole.CONSOLE_PAD_X * Settings.scale
				+ textLength();
	}

	private static float textLength() {
		if (glyphs == null || DevConsole.currentText.isEmpty()) {
			return 0;
		} else {
			glyphs.setText(DevConsole.consoleFont, getUncompletedText());
			return glyphs.width;
		}
	}

	public static void render(SpriteBatch sb) {
		DevConsole.consoleFont.setColor(TEXT_COLOR);

		if (noMatch || completionPairs.isEmpty() || lastCompletions.isEmpty() || !implementedYet || commandComplete) {
			sb.draw(DevConsole.consoleBackground, getBGX(), getY(1), getWidth(), getHeight());
			String text = "[No Match found]";
			if (!implementedYet) {
				text = "[Not implemented yet]";
			}
			if (commandComplete) {
				text = "[Command is complete]";
			}
			DevConsole.consoleFont.draw(sb, text, drawX, DevConsole.CONSOLE_Y * Settings.scale);
		} else {
			Pair pair = completionPairs.peek();
			int amount = pair.end - selected;
			if (amount > MAX_SUGGESTIONS) {
				amount = MAX_SUGGESTIONS;
			}
			if (amount > 1 + pair.end - pair.start) {
				amount = 1 + pair.end - pair.start;
			}

			float y = (DevConsole.CONSOLE_Y * Settings.scale
					+ (float) Math.floor(DevConsole.CONSOLE_TEXT_SIZE * Settings.scale));
			DevConsole.consoleFont.draw(sb, lastCompletions.get(selected + pair.start), drawX, y);
			for (int i = 1; i <= amount; i++) {
				int item = selected + pair.start + i;
				if (item > pair.end || item >= lastCompletions.size()) {
					break;
				}
				y -= (float) Math.floor(DevConsole.CONSOLE_TEXT_SIZE * Settings.scale);
				sb.draw(DevConsole.consoleBackground, getBGX(), getY(i), getWidth(), getHeight());
				DevConsole.consoleFont.draw(sb, lastCompletions.get(item), drawX, y);
			}
		}
		DevConsole.consoleFont.setColor(Color.WHITE);
	}

	private static float getBGX() {
		return drawX - DevConsole.CONSOLE_PAD_X * Settings.scale;
	}

	private static float getY(int i) {
		return DevConsole.CONSOLE_Y * Settings.scale
				- (float) Math.floor(DevConsole.CONSOLE_TEXT_SIZE * Settings.scale * i);
	}

	private static float getWidth() {
		return DevConsole.CONSOLE_W * Settings.scale - drawX + DevConsole.CONSOLE_X * Settings.scale
				+ DevConsole.CONSOLE_PAD_X * Settings.scale;
	}

	private static float getHeight() {
		return DevConsole.CONSOLE_TEXT_SIZE * Settings.scale;
	}
}
