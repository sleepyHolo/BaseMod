package basemod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

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

	public static final String[] COMMANDS = { "clear", "deck", "draw", "energy", "event", "fight", "gold", "hand",
			"help", "hp", "info", "kill", "maxhp", "potion", "power", "relic", "unlock" };

	private static final int MAX_SUGGESTIONS = 5;
	private static final Color TEXT_COLOR = Color.GRAY.cpy();

	public static boolean enabled = true;
	public static int selectKey = Keys.SHIFT_LEFT;
	public static int fillKey = Keys.RIGHT;
	public static int selected = 0;
	private static int tokenStart = 0;
	private static ArrayList<String> lastCompletions;
	private static Stack<Pair> completionPairs;
	private static String[] tokens;
	private static int lastLength;
	private static char lastChar = (char) -1;
	private static boolean foundStart, foundEnd, noMatch;
	private static boolean alreadySorted;


	private static float drawX;
	private static float promptWidth = 0;
	private static GlyphLayout glyphs;

	public static void init() {
		reset();
	}

	public static void fillInSuggestion() {
		if (!noMatch && !lastCompletions.isEmpty() && !completionPairs.isEmpty()) {
			String temp = stripCompletingCommand(true);

			DevConsole.currentText = (temp + " " + lastCompletions.get(selected + completionPairs.peek().start)).trim()
					+ " ";
			reset();
			complete(false);
		}
	}

	private static String stripCompletingCommand(boolean trim) {
		String temp = DevConsole.currentText;
		if (trim) {
			temp = temp.trim();
		}
		int i;
		for (i = tokens.length; i > tokenStart; i--) {
			int j = findWhiteSpace(temp);
			if (j > 0) {
				temp = temp.substring(0, j);
				if (trim) {
					temp = temp.trim();
				}
			} else {
				temp = "";
				break;
			}
		}
		return temp;
	}

	private static int findWhiteSpace(String temp) {
		int j;
		for (j = temp.length() - 1; j >= 0; j--) {
			if (j < 0) {
				return -1;
			} else if (Character.isWhitespace(temp.charAt(j))) {
				break;
			}
		}
		return j;
	}

	public static void postInit() {
		glyphs = new GlyphLayout(DevConsole.consoleFont, DevConsole.PROMPT);
		promptWidth = glyphs.width;
		calculateDrawX();
		complete(false);
	}

	public static void reset() {
		calculateDrawX();
		lastCompletions = new ArrayList<>();
		completionPairs = new Stack<>();
		tokens = new String[]{};
		lastLength = 0;
		selected = 0;
		foundStart = foundEnd = noMatch = false;
		tokenStart = 0;
	}

	public static void selectUp() {
		if (selected > 0 && !noMatch && !lastCompletions.isEmpty() && !completionPairs.isEmpty()) {
			selected--;
		}
	}

	public static void selectDown() {
		if (!noMatch && !lastCompletions.isEmpty() && !completionPairs.isEmpty()) {
			Pair pair = completionPairs.peek();
			if (selected < pair.end - pair.start) {
				selected++;
			}
		}
	}

	public static void complete(boolean isCharacterRemoved) {
		// To get the tokens, we first trim the current Text (removing whitespaces from the start and end)
		// then we split it using a pattern that matches one or more consecutive whitespaces
		// The resulting array tokens only has Strings with no whitespaces
		tokens = DevConsole.currentText.trim().split(DevConsole.PATTERN);

		if (tokens.length < 2) {
			tokenStart = 0;
			if (isCharacterRemoved) {
				if (!tokensSizeChanged()) {
					if (completionPairs.size() >= 2) {
						completionPairs.pop();
						if (completionPairs.peek().end <= -1) {
							noMatch = true;
						} else {
							noMatch = false;
							createCompletions();
							createPair(tokens[tokenStart]);
						}
					} else {
						createCompletions();
						createPair(tokens[tokenStart]);
					}
				} else {
					createCompletions();
					createPair(tokens[tokenStart]);
				}
			} else {
				if (!tokensSizeChanged()) {
					createPair(tokens[tokenStart]);
				} else {
					createCompletions();
					createPair(tokens[tokenStart]);
				}
			}
		}
		calculateDrawX();
		lastLength = tokens.length;
		int size = DevConsole.currentText.length();
		if (size > 0) {
			lastChar = DevConsole.currentText.charAt(size - 1);
		} else {
			lastChar = (char) -1;
		}
	}

	private static void createPair(String prefix) {
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


	private static void createCompletions() {
		alreadySorted = false;
		lastCompletions.clear();
		if (tokens.length < 20) {
			// alreadySorted = true;
			// lastCompletions.addAll(Arrays.asList(COMMANDS));
			lastCompletions.addAll(Arrays.asList(Gdx.files.internal("img/words.txt").readString().split("\n")));
		} else {

		}
		if (!alreadySorted) {
			alreadySorted = true;
			Collections.sort(lastCompletions);
		}
	}

	private static void calculateDrawX() {
		drawX = DevConsole.CONSOLE_X * Settings.scale + promptWidth + DevConsole.CONSOLE_PAD_X * Settings.scale
				+ textLength();
	}

	private static float textLength() {
		if (glyphs == null) {
			return 0;
		} else {
			String text = stripCompletingCommand(false);
			if (!text.isEmpty()) {
				text = " " + text;
			}
			glyphs.setText(DevConsole.consoleFont, text);
			return glyphs.width;
		}
	}

	public static boolean tokensSizeChanged() {
		return tokens.length != lastLength;
	}

	public static void render(SpriteBatch sb) {
		DevConsole.consoleFont.setColor(TEXT_COLOR);

		if (noMatch || completionPairs.isEmpty()) {
			sb.draw(DevConsole.consoleBackground, getBGX(),
					getY(1),
					getWidth(), getHeight());
			DevConsole.consoleFont.draw(sb, "No Match found", drawX, DevConsole.CONSOLE_Y * Settings.scale);
		} else {
			Pair pair = completionPairs.peek();
			int amount = pair.end - selected;
			if (amount > MAX_SUGGESTIONS) {
				amount = MAX_SUGGESTIONS;
			}
			if (amount > 1 + pair.end - pair.start ) {
				amount = 1 + pair.end - pair.start;
			}

			float y = (DevConsole.CONSOLE_Y * Settings.scale
					+ (float) Math.floor(DevConsole.CONSOLE_TEXT_SIZE * Settings.scale));
			DevConsole.consoleFont.draw(sb, lastCompletions.get(selected + pair.start), drawX, y);
			for (int i = 1; i <= amount; i++) {
				if ((selected + pair.start + i) > pair.end) {
					break;
				}
				y -= (float) Math.floor(DevConsole.CONSOLE_TEXT_SIZE * Settings.scale);
				sb.draw(DevConsole.consoleBackground,
						getBGX(), getY(i),
						getWidth(),
						getHeight());
				DevConsole.consoleFont.draw(sb, lastCompletions.get(selected + pair.start + i), drawX, y);
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
