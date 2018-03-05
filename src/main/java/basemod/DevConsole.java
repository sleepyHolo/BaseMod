package basemod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import basemod.helpers.ConvertHelper;
import basemod.interfaces.PostEnergyRechargeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostRenderSubscriber;
import basemod.interfaces.PostUpdateSubscriber;

public class DevConsole
		implements PostEnergyRechargeSubscriber, PostInitializeSubscriber, PostRenderSubscriber, PostUpdateSubscriber {
	public static final Logger logger = LogManager.getLogger(DevConsole.class.getName());

	private static final float CONSOLE_X = 75.0f;
	private static final float CONSOLE_Y = 75.0f;
	private static final float CONSOLE_W = 800.0f;
	private static final float CONSOLE_H = 40.0f;
	private static final float CONSOLE_PAD_X = 15.0f;
	private static final int CONSOLE_TEXT_SIZE = 30;
	private static final int MAX_LINES = 8;
	private static final String PROMPT = "$> ";

	private static BitmapFont consoleFont = null;
	private static Color consoleColor = new Color(0.0f, 0.0f, 0.0f, 0.4f);
	private static InputProcessor consoleInputProcessor;
	private static InputProcessor otherInputProcessor = null;
	private static ShapeRenderer consoleBackground = null;

	private static boolean infiniteEnergy = false;
	public static boolean forceUnlocks = false;
	public static int unlockLevel = -1;

	public static boolean visible = false;
	public static int toggleKey = Keys.GRAVE;
	public static String currentText = "";

	public static int priorKey = Keys.UP;
	public static int nextKey = Keys.DOWN;
	public static ArrayList<String> priorCommands;
	public static ArrayList<String> log;
	public static ArrayList<Boolean> prompted;
	public static int commandPos;

	public DevConsole() {
		BaseMod.subscribeToPostEnergyRecharge(this);
		BaseMod.subscribeToPostInitialize(this);
		BaseMod.subscribeToPostRender(this);
		BaseMod.subscribeToPostUpdate(this);

		priorCommands = new ArrayList<>();
		commandPos = 0;
		log = new ArrayList<>();
		prompted = new ArrayList<>();
	}

	public static void execute() {
		String[] tokens = currentText.split(" ");
		if (priorCommands.size() == 0 || !priorCommands.get(0).equals(currentText)) {
			priorCommands.add(0, currentText);
		}
		log.add(0, currentText);
		prompted.add(0, true);
		commandPos = 0;
		currentText = "";

		if (tokens.length < 1)
			return;
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].trim();
		}

		switch (tokens[0].toLowerCase()) {
		case "relic": {
			cmdRelic(tokens);
			break;
		}
		case "hand": {
			cmdHand(tokens);
			break;
		}
		case "info": {
			Settings.isInfo = !Settings.isInfo;
			break;
		}
		case "kill": {
			cmdKill(tokens);
			break;
		}
		case "gold": {
			cmdGold(tokens);
			break;
		}
		case "energy": {
			cmdEnergy(tokens);
			break;
		}
		case "deck": {
			cmdDeck(tokens);
			break;
		}
		case "draw": {
			cmdDraw(tokens);
			break;
		}
		case "fight": {
			cmdFight(tokens);
			break;
		}
		case "event": {
			cmdEvent(tokens);
			break;
		}
		case "potion": {
			cmdPotion(tokens);
			break;
		}
		case "unlock": {
			cmdUnlock(tokens);
			break;
		}
		case "power": {
			cmdPower(tokens);
			break;
		}
		case "clear": {
			cmdClear(tokens);
			break;
		}
		case "help": {
			cmdHelp();
			break;
		}
		case "hp": {
			cmdHP(tokens);
			break;
		}
		case "maxhp": {
			cmdMaxHP(tokens);
			break;
		}
		default: {
			log("invalid command");
			break;
		}
		}
	}

	private static void cmdClear(String[] tokens) {
		if (tokens.length < 2) {
			clearLog();
			clearCmds();
		} else if (tokens[1].equals("log")) {
			clearLog();
		} else if (tokens[1].equals("cmd")) {
			clearCmds();
		} else {
			cmdClearHelp();
		}
	}

	// clear log
	private static void clearLog() {
		while (log.size() > 0) {
			log.remove(0);
		}
		while (prompted.size() > 0) {
			prompted.remove(0);
		}
	}

	// clear command list
	private static void clearCmds() {
		while (priorCommands.size() > 0) {
			priorCommands.remove(0);
		}
	}

	private static void cmdClearHelp() {
		couldNotParse();
		log("options are:");
		log("* log");
		log("* cmd");
	}

	// print help info
	private static void cmdHelp() {
		log("options are: relic hand info kill gold energy deck...");
		log("draw fight event potion unlock power clear help hp maxhp");
	}

	private static void cmdPower(String[] tokens) {
		String powerID = "";
		int amount = 1;
		for (int i = 1; i < tokens.length - 1; i++) {
			powerID = powerID.concat(tokens[i]);
			if (i != tokens.length - 2) {
				powerID = powerID.concat(" ");
			}
		}
		try {
			amount = Integer.parseInt(tokens[tokens.length - 1]);
		} catch (Exception e) {
			powerID = powerID.concat(tokens[tokens.length - 1]);
		}
		try {
			@SuppressWarnings({ "rawtypes", "unused" })
			Class power = BaseMod.getPowerClass(powerID);
		} catch (Exception e) {
			logger.info("failed to load power " + powerID);
			log("could not load power");
			cmdPowerHelp();
			return;
		}

		try {
			@SuppressWarnings("unused")
			ConsoleTargetedPower ctp = new ConsoleTargetedPower(BaseMod.getPowerClass(powerID), amount);
		} catch (Exception e) {
			log("could not make power");
			cmdPowerHelp();
		}
	}

	private static void cmdPowerHelp() {
		couldNotParse();
		log("options are:");
		log("* [id] [amt]");
	}

	private static void cmdRelic(String[] tokens) {
		if (AbstractDungeon.player != null) {
			if (tokens.length < 2) {
				cmdRelicHelp();
				return;
			}

			if (tokens[1].toLowerCase().equals("remove") && tokens.length > 2) {
				String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
				String relicName = String.join(" ", relicNameArray);
				AbstractDungeon.player.loseRelic(relicName);
			} else if (tokens[1].toLowerCase().equals("add") && tokens.length > 2) {
				String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
				String relicName = String.join(" ", relicNameArray);
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2,
						RelicLibrary.getRelic(relicName).makeCopy());
			} else if (tokens[1].toLowerCase().equals("desc") && tokens.length > 2) {
				String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
				String relicName = String.join(" ", relicNameArray);
				log(RelicLibrary.getRelic(relicName).description);
			} else if (tokens[1].toLowerCase().equals("flavor") && tokens.length > 2) {
				String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
				String relicName = String.join(" ", relicNameArray);
				log(RelicLibrary.getRelic(relicName).flavorText);
			} else if (tokens[1].toLowerCase().equals("pool") && tokens.length > 2) {
				String[] relicNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
				String relicName = String.join(" ", relicNameArray);
				log(RelicLibrary.getRelic(relicName).tier.toString());
			} else if (tokens[1].toLowerCase().equals("list")) {
				if (tokens.length < 3) {
					cmdRelicListHelp();
					return;
				}
				switch (tokens[2]) {
				case "starter": {
					Collections.sort(RelicLibrary.starterList);
					logger.info(RelicLibrary.starterList);
					log(RelicLibrary.starterList);
					break;
				}
				case "common": {
					Collections.sort(RelicLibrary.commonList);
					logger.info(RelicLibrary.commonList);
					log(RelicLibrary.commonList);
					break;
				}
				case "uncommon": {
					Collections.sort(RelicLibrary.uncommonList);
					logger.info(RelicLibrary.uncommonList);
					log(RelicLibrary.uncommonList);
					break;
				}
				case "rare": {
					Collections.sort(RelicLibrary.rareList);
					logger.info(RelicLibrary.rareList);
					log(RelicLibrary.rareList);
				}
				case "boss": {
					Collections.sort(RelicLibrary.bossList);
					logger.info(RelicLibrary.bossList);
					log(RelicLibrary.bossList);
					break;
				}
				case "special": {
					Collections.sort(RelicLibrary.specialList);
					logger.info(RelicLibrary.specialList);
					log(RelicLibrary.specialList);
					break;
				}
				case "shop": {
					Collections.sort(RelicLibrary.shopList);
					logger.info(RelicLibrary.shopList);
					log(RelicLibrary.shopList);
					break;
				}
				default: {
					cmdRelicListHelp();
				}
				}
			} else {
				cmdRelicHelp();
			}
		}
	}

	private static void cmdRelicHelp() {
		couldNotParse();
		log("options are:");
		log("* remove [id]");
		log("* add [id]");
		log("* desc [id]");
		log("* flavor [id]");
		log("* pool [id]");
		log("* list [type]");
	}

	private static void cmdRelicListHelp() {
		log("options are: starter common uncommon rare boss special shop");
	}

	private static void cmdHand(String[] tokens) {
		if (AbstractDungeon.player != null) {
			if (tokens.length < 3) {
				return;
			}

			int upgradeIndex = 3;
			while (upgradeIndex < tokens.length && ConvertHelper.tryParseInt(tokens[upgradeIndex], 0) == 0) {
				++upgradeIndex;
			}

			if (tokens[1].toLowerCase().equals("add")) {
				String[] cardNameArray = Arrays.copyOfRange(tokens, 2, upgradeIndex);
				String cardName = String.join(" ", cardNameArray);
				AbstractCard c = CardLibrary.getCard(cardName);

				if (c != null) {
					c = c.makeCopy();

					if (upgradeIndex != tokens.length) {
						int upgradeCount = ConvertHelper.tryParseInt(tokens[upgradeIndex], 0);
						for (int i = 0; i < upgradeCount; i++) {
							c.upgrade();
						}
					}

					AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
				}
			} else if (tokens[1].toLowerCase().equals("r")) {
				if (tokens[2].toLowerCase().equals("all")) {
					for (AbstractCard c : new ArrayList<>(AbstractDungeon.player.hand.group)) {
						AbstractDungeon.player.hand.moveToExhaustPile(c);
					}
					return;
				}

				String[] cardNameArray = Arrays.copyOfRange(tokens, 2, tokens.length);
				String cardName = String.join(" ", cardNameArray);

				boolean removed = false;
				AbstractCard toRemove = null;
				for (AbstractCard c : AbstractDungeon.player.hand.group) {
					if (removed)
						break;
					if (c.cardID.equals(cardName)) {
						toRemove = c;
						removed = true;
					}
				}
				if (removed)
					AbstractDungeon.player.hand.moveToExhaustPile(toRemove);
			}
		}
	}

	private static void cmdKill(String[] tokens) {
		if (AbstractDungeon.getCurrRoom().monsters != null) {
			if (tokens.length != 2) {
				return;
			}

			if (tokens[1].toLowerCase().equals("all")) {
				int monsterCount = AbstractDungeon.getCurrRoom().monsters.monsters.size();
				int[] multiDamage = new int[monsterCount];
				for (int i = 0; i < monsterCount; ++i) {
					multiDamage[i] = 999;
				}

				AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage,
						DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.NONE));
			} else if (tokens[1].toLowerCase().equals("self")) {
				AbstractDungeon.actionManager
						.addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 999));
			}
		}
	}

	private static void cmdHP(String[] tokens) {
		if (tokens.length < 2) {
			cmdHPHelp();
			return;
		}

		if (tokens[1].toLowerCase().equals("add") && tokens.length > 2) {
			int i;
			try {
				i = Integer.parseInt(tokens[2]);
				AbstractDungeon.actionManager
						.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, i));
			} catch (Exception e) {
				cmdHPHelp();
			}
		} else if (tokens[1].toLowerCase().equals("lose") && tokens.length > 2) {
			int i;
			try {
				i = Integer.parseInt(tokens[2]);
				AbstractDungeon.actionManager.addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player,
						i, AbstractGameAction.AttackEffect.NONE));
			} catch (Exception e) {
				cmdHPHelp();
			}
		} else {
			cmdHPHelp();
		}
	}

	private static void cmdHPHelp() {
		couldNotParse();
		log("options are:");
		log("* add [amt]");
		log("* lose [amt]");
	}

	private static void cmdMaxHP(String[] tokens) {
		if (tokens.length < 2) {
			cmdMaxHPHelp();
			return;
		}
		if (tokens[1].toLowerCase().equals("add") && tokens.length > 2) {
			int i;
			try {
				i = Integer.parseInt(tokens[2]);
				AbstractDungeon.player.increaseMaxHp(i, true);
			} catch (Exception e) {
				cmdMaxHPHelp();
			}
		} else if (tokens[1].toLowerCase().equals("lose") && tokens.length > 2) {
			int i;
			try {
				i = Integer.parseInt(tokens[2]);
				AbstractDungeon.player.decreaseMaxHealth(i);
			} catch (Exception e) {
				cmdMaxHPHelp();
			}
		} else {
			cmdMaxHPHelp();
		}
	}

	private static void cmdMaxHPHelp() {
		couldNotParse();
		log("options are:");
		log("* add [amt]");
		log("* lose [amt]");
	}

	private static void cmdGold(String[] tokens) {
		if (AbstractDungeon.player != null) {
			if (tokens.length != 3) {
				cmdGoldHelp();
				return;
			}

			int amount = ConvertHelper.tryParseInt(tokens[2], 0);
			if (tokens[1].toLowerCase().equals("add")) {
				AbstractDungeon.player.displayGold += amount;
				AbstractDungeon.player.gainGold(amount);
			} else if (tokens[1].toLowerCase().equals("lose")) {
				AbstractDungeon.player.displayGold = Math.max(AbstractDungeon.player.displayGold - amount, 0);
				AbstractDungeon.player.loseGold(amount);
			} else {
				cmdGoldHelp();
			}
		}
	}

	private static void cmdGoldHelp() {
		couldNotParse();
		log("options are:");
		log("* add [amt]");
		log("* lose [amt]");
	}

	private static void cmdEnergy(String[] tokens) {
		if (AbstractDungeon.player != null) {
			if (tokens.length < 2) {
				cmdEnergyHelp();
				return;
			}

			if (tokens[1].toLowerCase().equals("add") && tokens.length > 2) {
				AbstractDungeon.player.gainEnergy(ConvertHelper.tryParseInt(tokens[2], 0));
			} else if (tokens[1].toLowerCase().equals("lose") && tokens.length > 2) {
				AbstractDungeon.player.loseEnergy(ConvertHelper.tryParseInt(tokens[2], 0));
			} else if (tokens[1].toLowerCase().equals("inf")) {
				infiniteEnergy = !infiniteEnergy;
				if (infiniteEnergy) {
					AbstractDungeon.player.gainEnergy(9999);
				}
			} else {
				cmdEnergyHelp();
			}
		}
	}

	private static void cmdEnergyHelp() {
		couldNotParse();
		log("options are:");
		log("* add [amt]");
		log("* lose [amt]");
		log("* inf");
	}

	private static void couldNotParse() {
		log("could not parse previous command");
	}

	private static void cmdUnlock(String[] tokens) {
		if (tokens.length < 2) {
			return;
		}

		if (tokens[1].toLowerCase().equals("always")) {
			forceUnlocks = !forceUnlocks;
		} else if (tokens[1].toLowerCase().equals("level") && tokens.length > 2) {
			unlockLevel = ConvertHelper.tryParseInt(tokens[2]);
		}
	}

	private static void cmdDeck(String[] tokens) {
		if (AbstractDungeon.player != null) {
			if (tokens.length < 3) {
				return;
			}

			int upgradeIndex = tokens.length - 1;
			while (ConvertHelper.tryParseInt(tokens[upgradeIndex], 0) != 0) {
				upgradeIndex--;
			}

			String[] cardNameArray = Arrays.copyOfRange(tokens, 2, upgradeIndex + 1);
			String cardName = String.join(" ", cardNameArray);

			if (tokens[1].toLowerCase().equals("add")) {
				AbstractCard c = CardLibrary.getCard(cardName);
				if (c != null) {
					// card count
					int count = 1;
					if (tokens.length > 3 && ConvertHelper.tryParseInt(tokens[3], 0) != 0) {
						count = ConvertHelper.tryParseInt(tokens[3], 0);
					}

					int upgradeCount = 0;
					if (tokens.length > 4) {
						upgradeCount = ConvertHelper.tryParseInt(tokens[4], 0);
					}
					
					log("adding " + count + (count == 1 ? " copy of " : " copies of ") + cardName + " with " + upgradeCount + " upgrade(s)");

					for (int i = 0; i < count; i++) {
						AbstractCard copy = c.makeCopy();
						for (int j = 0; j < upgradeCount; j++) {
							copy.upgrade();
						}

						AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(copy, (float) Settings.WIDTH / 2.0f,
								(float) Settings.HEIGHT / 2.0f));
					}
				} else {
					log("could not find card " + cardName);
				}
			} else if (tokens[1].toLowerCase().equals("remove")) {
				// remove all cards
				if (tokens[2].equals("all")) {
					for (String str : AbstractDungeon.player.masterDeck.getCardNames()) {
						AbstractDungeon.player.masterDeck.removeCard(str);
					}
					// remove single card
				} else {
					AbstractDungeon.player.masterDeck.removeCard(cardName);
				}
			} else {
				cmdDeckHelp();
			}
		} else {
			log("cannot add cards when player doesn't exist");
		}
	}
	
	private static void cmdDeckHelp() {
		couldNotParse();
		log("options are:");
		log("* add [id] {count} {upgrade amt}");
		log("* remove [id]");
		log("* remove all");
	}

	private static void cmdDraw(String[] tokens) {
		if (AbstractDungeon.getCurrRoom().monsters != null) {
			if (tokens.length != 2) {
				cmdDrawHelp();
				return;
			}

			AbstractDungeon.actionManager
					.addToTop(new DrawCardAction(AbstractDungeon.player, ConvertHelper.tryParseInt(tokens[1], 0)));
		} else {
			log("cannot draw when not in combat");
		}
	}
	
	private static void cmdDrawHelp() {
		couldNotParse();
		log("options are:");
		log("* draw [amt]");
	}

	private static void cmdFight(String[] tokens) {
		if (tokens.length < 2) {
			return;
		}

		String[] encounterArray = Arrays.copyOfRange(tokens, 1, tokens.length);
		String encounterName = String.join(" ", encounterArray);
		AbstractDungeon.monsterList.add(0, encounterName);

		MapRoomNode cur = AbstractDungeon.currMapNode;
		MapRoomNode node = new MapRoomNode(cur.x, cur.y);
		node.room = new MonsterRoom();

		ArrayList<MapEdge> curEdges = cur.getEdges();
		for (MapEdge edge : curEdges) {
			node.addEdge(edge);
		}

		AbstractDungeon.nextRoom = node;
		AbstractDungeon.nextRoomTransitionStart();
	}

	private static void cmdEvent(String[] tokens) {
		if (tokens.length < 2) {
			return;
		}

		String[] eventArray = Arrays.copyOfRange(tokens, 1, tokens.length);
		String eventName = String.join(" ", eventArray);
		AbstractDungeon.eventList.add(0, eventName);

		MapRoomNode cur = AbstractDungeon.currMapNode;
		MapRoomNode node = new MapRoomNode(cur.x, cur.y);
		node.room = new CustomEventRoom();

		ArrayList<MapEdge> curEdges = cur.getEdges();
		for (MapEdge edge : curEdges) {
			node.addEdge(edge);
		}

		AbstractDungeon.previousScreen = null;
		AbstractDungeon.dynamicBanner.hide();
		AbstractDungeon.dungeonMapScreen.closeInstantly();
		AbstractDungeon.closeCurrentScreen();
		AbstractDungeon.topPanel.unhoverHitboxes();
		AbstractDungeon.fadeIn();
		AbstractDungeon.genericEventDialog.clear();
		AbstractDungeon.dialog.hide();
		AbstractDungeon.dialog.clear();
		AbstractDungeon.effectList.clear();
		AbstractDungeon.topLevelEffects.clear();
		AbstractDungeon.topLevelEffectsQueue.clear();
		AbstractDungeon.effectsQueue.clear();
		AbstractDungeon.dungeonMapScreen.dismissable = true;
		AbstractDungeon.nextRoom = node;
		AbstractDungeon.setCurrMapNode(node);
		AbstractDungeon.getCurrRoom().onPlayerEntry();
		AbstractDungeon.scene.nextRoom(node.room);
		AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;
	}

	private static void cmdPotion(String[] tokens) {
		if (tokens.length < 3) {
			cmdPotionHelp();
			return;
		}

		int i;
		try {
			i = Integer.parseInt(tokens[1]);
		} catch (Exception e) {
			// check if we want to list potions
			if (tokens[1].equals("list")) {
				Collections.sort(PotionHelper.potions);
				logger.info(PotionHelper.potions);
			} else {
				cmdPotionHelp();
			}
			return;
		}

		String potionID = "";
		for (int k = 2; k < tokens.length; k++) {
			potionID = potionID.concat(tokens[k]);
			if (k != tokens.length - 1) {
				potionID = potionID.concat(" ");
			}
		}

		AbstractPotion p = null;
		if (PotionHelper.potions.contains(potionID)) {
			p = PotionHelper.getPotion(potionID);
		}
		if (PotionHelper.potions.contains(potionID + " Potion")) {
			p = PotionHelper.getPotion(potionID + " Potion");
		}

		if (p == null) {
			log("invalid potion id");
			log("use potion list to see valid ids");
			return;
		}

		CardCrawlGame.sound.play("POTION_1");
		p.moveInstantly(AbstractDungeon.player.potions[i].currentX, AbstractDungeon.player.potions[i].currentY);
		BaseMod.publishPotionGet(p);
		p.isObtained = true;
		p.isDone = true;
		p.isAnimating = false;
		p.flash();
		AbstractDungeon.player.potions[i] = p;
	}

	public static void cmdPotionHelp() {
		couldNotParse();
		log("options are:");
		log("* list");
		log("* [slot] [id]");
	}

	public void receivePostEnergyRecharge() {
		if (infiniteEnergy) {
			EnergyPanel.setEnergy(9999);
		}
	}

	public void receivePostInitialize() {
		consoleInputProcessor = new ConsoleInputProcessor();

		// Console font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Kreon-Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = (int) (CONSOLE_TEXT_SIZE * Settings.scale);
		consoleFont = generator.generateFont(parameter);
		generator.dispose();
	}

	public static void log(String text) {
		log.add(0, text);
		prompted.add(0, false);
	}

	public static void log(@SuppressWarnings("rawtypes") ArrayList list) {
		for (Object o : list) {
			log(o.toString());
		}
	}

	public void receivePostRender(SpriteBatch sb) {
		if (visible && consoleFont != null) {
			// Since we need a shape renderer, need to end then restart the
			// SpriteBatch
			// Should probably just make a background texture for the console so
			// this doesn't need to be done
			sb.end();

			if (consoleBackground == null) {
				consoleBackground = new ShapeRenderer();
			}

			int sizeToDraw = log.size() + 1;
			if (sizeToDraw > MAX_LINES) {
				sizeToDraw = MAX_LINES;
			}

			consoleBackground.begin(ShapeType.Filled);
			consoleBackground.setColor(consoleColor);
			consoleBackground.rect(CONSOLE_X, CONSOLE_Y, (CONSOLE_W * Settings.scale),
					(CONSOLE_H * Settings.scale + (CONSOLE_TEXT_SIZE * Settings.scale * (sizeToDraw - 1))));
			consoleBackground.end();

			sb.begin();

			float x = (CONSOLE_X + (CONSOLE_PAD_X * Settings.scale));
			float y = (CONSOLE_Y + (float) Math.floor(CONSOLE_TEXT_SIZE * Settings.scale));
			consoleFont.draw(sb, PROMPT + currentText, x, y);
			for (int i = 0; i < sizeToDraw - 1; i++) {
				y += (float) Math.floor(CONSOLE_TEXT_SIZE * Settings.scale);
				consoleFont.draw(sb, (prompted.get(i) ? PROMPT : "") + log.get(i), x, y);
			}
		}
	}

	public void receivePostUpdate() {
		if (Gdx.input.isKeyJustPressed(toggleKey)) {
			if (visible) {
				currentText = "";
				commandPos = 0;
			} else {
				otherInputProcessor = Gdx.input.getInputProcessor();
			}

			Gdx.input.setInputProcessor(visible ? otherInputProcessor : consoleInputProcessor);
			visible = !visible;
		}

		// get previous commands
		if (Gdx.input.isKeyJustPressed(priorKey)) {
			if (visible) {
				currentText = priorCommands.get(commandPos);
				if (commandPos + 1 < priorCommands.size()) {
					commandPos++;
				}
			}
		}
		if (Gdx.input.isKeyJustPressed(nextKey)) {
			if (visible) {
				if (commandPos - 1 < 0) {
					currentText = "";
				} else {
					commandPos--;
					currentText = priorCommands.get(commandPos);
				}
			}
		}
	}
}