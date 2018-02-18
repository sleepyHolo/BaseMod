package basemod;

import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.characters.Ironclad;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpireInitializer
public class BaseMod {
    public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
    
    private static final int BADGES_PER_ROW = 16;
    private static final float BADGES_X = 640.0f;
    private static final float BADGES_Y = 250.0f;
    public static final float BADGE_W = 40.0f;
    public static final float BADGE_H = 40.0f;

    private static HashMap<Type, String> typeMaps;
    private static HashMap<Type, Type> typeTokens;

    private static ArrayList<ModBadge> modBadges;
    private static ArrayList<StartActSubscriber> startActSubscribers;
    private static ArrayList<PostCampfireSubscriber> postCampfireSubscribers;
    private static ArrayList<PostDrawSubscriber> postDrawSubscribers;
    private static ArrayList<PostDungeonInitializeSubscriber> postDungeonInitializeSubscribers;
    private static ArrayList<PostEnergyRechargeSubscriber> postEnergyRechargeSubscribers;
    private static ArrayList<PostInitializeSubscriber> postInitializeSubscribers;
    private static ArrayList<PreMonsterTurnSubscriber> preMonsterTurnSubscribers;
    private static ArrayList<RenderSubscriber> renderSubscribers;
    private static ArrayList<PostRenderSubscriber> postRenderSubscribers;
    private static ArrayList<PreStartGameSubscriber> preStartGameSubscribers;
    private static ArrayList<StartGameSubscriber> startGameSubscribers;
    private static ArrayList<PreUpdateSubscriber> preUpdateSubscribers;
    private static ArrayList<PostUpdateSubscriber> postUpdateSubscribers;
    private static ArrayList<PostCreateStartingDeckSubscriber> postCreateStartingDeckSubscribers;
    private static ArrayList<PostCreateStartingRelicsSubscriber> postCreateStartingRelicsSubscribers;
    private static ArrayList<PostCreateShopRelicSubscriber> postCreateShopRelicSubscribers;
    private static ArrayList<PostCreateShopPotionSubscriber> postCreateShopPotionSubscribers;
    private static ArrayList<EditCardsSubscriber> editCardsSubscribers;
    private static ArrayList<EditRelicsSubscriber> editRelicsSubscribers;
    private static ArrayList<EditCharactersSubscriber> editCharactersSubscribers;
    private static ArrayList<EditStringsSubscriber> editStringsSubscribers;
    
    private static ArrayList<AbstractCard> redToAdd;
    private static ArrayList<String> redToRemove;
    private static ArrayList<AbstractCard> greenToAdd;
    private static ArrayList<String> greenToRemove;
    private static ArrayList<AbstractCard> colorlessToAdd;
    private static ArrayList<String> colorlessToRemove;
    private static ArrayList<AbstractCard> curseToAdd;
    private static ArrayList<String> curseToRemove;
    private static ArrayList<AbstractCard> customToAdd;
    private static ArrayList<String> customToRemove;
    private static ArrayList<String> customToRemoveColors;
    
	@SuppressWarnings("rawtypes")
	private static HashMap<String, Class> playerClassMap;
	private static HashMap<String, String> playerTitleStringMap;
	private static HashMap<String, String> playerClassStringMap;
	private static HashMap<String, String> playerColorMap;
	private static HashMap<String, String> playerSelectTextMap;
	private static HashMap<String, String> playerSelectButtonMap;
	private static HashMap<String, String> playerPortraitMap;
	
	private static HashMap<String, com.badlogic.gdx.graphics.Color> colorBgColorMap;
	private static HashMap<String, com.badlogic.gdx.graphics.Color> colorBackColorMap;
	private static HashMap<String, com.badlogic.gdx.graphics.Color> colorFrameColorMap;
	private static HashMap<String, com.badlogic.gdx.graphics.Color> colorFrameOutlineColorMap;
	private static HashMap<String, com.badlogic.gdx.graphics.Color> colorDescBoxColorMap;
	private static HashMap<String, com.badlogic.gdx.graphics.Color> colorTrailVfxMap;
	private static HashMap<String, com.badlogic.gdx.graphics.Color> colorGlowColorMap;
	private static HashMap<String, Integer> colorCardCountMap;
	private static HashMap<String, Integer> colorCardSeenCountMap;
    private static HashMap<String, String> colorAttackBgMap;
    private static HashMap<String, String> colorSkillBgMap;
    private static HashMap<String, String> colorPowerBgMap;
    private static HashMap<String, String> colorEnergyOrbMap;
    private static HashMap<String, com.badlogic.gdx.graphics.Texture> colorAttackBgTextureMap;
    private static HashMap<String, com.badlogic.gdx.graphics.Texture> colorSkillBgTextureMap;
    private static HashMap<String, com.badlogic.gdx.graphics.Texture> colorPowerBgTextureMap;
    private static HashMap<String, com.badlogic.gdx.graphics.Texture> colorEnergyOrbTextureMap;
    
	
    public static DevConsole console;
    public static Gson gson;
    public static boolean modSettingsUp = false;
    
    // Map generation
    public static float mapPathDensityMultiplier = 1.0f;

    // Not implemented
    //public static int mapFirstEliteCampfireRoom = 4;
    //public static int mapLastCampfireRoom = 13;
    //public static int mapTreasureRoom = 8;
    
    // 
    // Initialization
    //
    
    // initialize -
    public static void initialize() {
        logger.info("========================= BASEMOD INIT =========================");
        logger.info("isModded: " + Settings.isModded);

        modBadges = new ArrayList<>();

        initializeGson();
        initializeTypeMaps();
        initializeSubscriptions();
        initializeCardLists();
        initializeCharacterMap();
        initializeColorMap();
        
        BaseModInit baseModInit = new BaseModInit();
        BaseMod.subscribeToPostInitialize(baseModInit);

        EditCharactersInit editCharactersInit = new EditCharactersInit();
        BaseMod.subscribeToPostInitialize(editCharactersInit);
        
        console = new DevConsole();
        
        logger.info("================================================================");
    }
    
    // initializeGson -
    private static void initializeGson() {
        logger.info("initializeGson");
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    // initializeTypeTokens -
    private static void initializeTypeMaps() {
        logger.info("initializeTypeMaps");

        typeMaps = new HashMap<>();
        typeMaps.put(AchievementStrings.class, "acheivements");
        typeMaps.put(CardStrings.class, "cards");
        typeMaps.put(CharacterStrings.class, "characters");
        typeMaps.put(CreditStrings.class, "credits");
        typeMaps.put(EventStrings.class, "events");
        typeMaps.put(KeywordStrings.class, "keywords");
        typeMaps.put(MonsterStrings.class, "monsters");
        typeMaps.put(PotionStrings.class, "potions");
        typeMaps.put(PowerStrings.class, "powers");
        typeMaps.put(RelicStrings.class, "relics");
        typeMaps.put(ScoreBonusStrings.class, "scoreBonuses");
        typeMaps.put(TutorialStrings.class, "tutorials");
        typeMaps.put(UIStrings.class, "ui");

        typeTokens = new HashMap<>();
        typeTokens.put(AchievementStrings.class, new TypeToken<Map<String, AchievementStrings>>(){}.getType());
        typeTokens.put(CardStrings.class, new TypeToken<Map<String, CardStrings>>(){}.getType());
        typeTokens.put(CharacterStrings.class, new TypeToken<Map<String, CharacterStrings>>(){}.getType());
        typeTokens.put(CreditStrings.class, new TypeToken<Map<String, CreditStrings>>(){}.getType());
        typeTokens.put(EventStrings.class, new TypeToken<Map<String, EventStrings>>(){}.getType());
        typeTokens.put(KeywordStrings.class, new TypeToken<Map<String, KeywordStrings>>(){}.getType());
        typeTokens.put(MonsterStrings.class, new TypeToken<Map<String, MonsterStrings>>(){}.getType());
        typeTokens.put(PotionStrings.class, new TypeToken<Map<String, PotionStrings>>(){}.getType());
        typeTokens.put(PowerStrings.class, new TypeToken<Map<String, PowerStrings>>(){}.getType());
        typeTokens.put(RelicStrings.class, new TypeToken<Map<String, RelicStrings>>(){}.getType());
        typeTokens.put(ScoreBonusStrings.class, new TypeToken<Map<String, ScoreBonusStrings>>(){}.getType());
        typeTokens.put(TutorialStrings.class, new TypeToken<Map<String, TutorialStrings>>(){}.getType());
        typeTokens.put(UIStrings.class, new TypeToken<Map<String, UIStrings>>(){}.getType());
    }

    // initializeSubscriptions -
    private static void initializeSubscriptions() {
        startActSubscribers = new ArrayList<>();
        postCampfireSubscribers = new ArrayList<>();
        postDrawSubscribers = new ArrayList<>();
        postDungeonInitializeSubscribers = new ArrayList<>();
        postEnergyRechargeSubscribers = new ArrayList<>();
        postInitializeSubscribers = new ArrayList<>();
        preMonsterTurnSubscribers = new ArrayList<>();
        renderSubscribers = new ArrayList<>();
        postRenderSubscribers = new ArrayList<>();
        preStartGameSubscribers = new ArrayList<>();
        startGameSubscribers = new ArrayList<>();
        preUpdateSubscribers = new ArrayList<>();
        postUpdateSubscribers = new ArrayList<>();
        postCreateStartingDeckSubscribers = new ArrayList<>();
        postCreateStartingRelicsSubscribers = new ArrayList<>();
        postCreateShopRelicSubscribers = new ArrayList<>();
        postCreateShopPotionSubscribers = new ArrayList<>();
        editCardsSubscribers = new ArrayList<>();
        editRelicsSubscribers = new ArrayList<>();
        editCharactersSubscribers = new ArrayList<>();
        editStringsSubscribers = new ArrayList<>();
    }
    
    // initializeCardLists -
    private static void initializeCardLists() {
    	redToAdd = new ArrayList<>();
    	redToRemove = new ArrayList<>();
    	greenToAdd = new ArrayList<>();
    	greenToRemove = new ArrayList<>();
    	colorlessToAdd = new ArrayList<>();
    	colorlessToRemove = new ArrayList<>();
    	curseToAdd = new ArrayList<>();
    	curseToRemove = new ArrayList<>();
    	customToAdd = new ArrayList<>();
    	customToRemove = new ArrayList<>();
    	customToRemoveColors = new ArrayList<>();
    }
    
    // initializeCharacterMap -
    private static void initializeCharacterMap() {
    	playerClassMap = new HashMap<>();
    	playerTitleStringMap = new HashMap<>();
       	playerClassStringMap = new HashMap<>();
    	playerColorMap = new HashMap<>();
    	playerSelectTextMap = new HashMap<>();
    	playerSelectButtonMap = new HashMap<>();
    	playerPortraitMap = new HashMap<>();
    }
    
    // initializeColorMap -
    private static void initializeColorMap() {
    	colorBgColorMap = new HashMap<>();
    	colorBackColorMap = new HashMap<>();
    	colorFrameColorMap = new HashMap<>();
    	colorFrameOutlineColorMap = new HashMap<>();
    	colorDescBoxColorMap = new HashMap<>();
    	colorTrailVfxMap = new HashMap<>();
    	colorGlowColorMap = new HashMap<>();
    	colorCardCountMap = new HashMap<>();
    	colorCardSeenCountMap = new HashMap<>();
    	colorAttackBgMap = new HashMap<>();
    	colorSkillBgMap = new HashMap<>();
    	colorPowerBgMap = new HashMap<>();
    	colorEnergyOrbMap = new HashMap<>();
    	colorAttackBgTextureMap = new HashMap<>();
    	colorSkillBgTextureMap = new HashMap<>();
    	colorPowerBgTextureMap = new HashMap<>();
    	colorEnergyOrbTextureMap = new HashMap<>();
    }
    
    //
    // Mod badges
    //
    public static void registerModBadge(Texture t, String name, String author, String desc, ModPanel settingsPanel) {
        logger.info("registerModBadge : " + name);
        int modBadgeCount = modBadges.size();
        int col = (modBadgeCount % BADGES_PER_ROW);
        int row = (modBadgeCount / BADGES_PER_ROW);
        float x = (BADGES_X * Settings.scale) + (col * BADGE_W * Settings.scale);
        float y = (BADGES_Y * Settings.scale) - (row * BADGE_H * Settings.scale);
        
        ModBadge badge = new ModBadge(t, x, y, name, author, desc, settingsPanel);
        modBadges.add(badge);
    }
    
    //
    // Localization
    //
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static void loadJsonStrings(Type stringType, String jsonString) {
        logger.info("loadJsonStrings: " + stringType.getClass().getCanonicalName());

        String typeMap = typeMaps.get(stringType);
        Type typeToken = typeTokens.get(stringType);

        Map localizationStrings = (Map) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, typeMap);
        localizationStrings.putAll(new HashMap(gson.fromJson(jsonString, typeToken)));
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, typeMap, localizationStrings);
    }

    // loadCustomRelicStrings - loads custom RelicStrings from provided JSON
    // should be done inside the callback of an implementation of EditStringsSubscriber
    public static void loadCustomStrings(@SuppressWarnings("rawtypes") Class stringType, String jsonString) {
        loadJsonStrings(stringType, jsonString);
    }
    
    //
    // Cards
    //
    
    // red add -
    public static ArrayList<AbstractCard> getRedCardsToAdd() {
    	return redToAdd;
    }
    
    // red remove -
    public static ArrayList<String> getRedCardsToRemove() {
    	return redToRemove;
    }
    
    // green add -
    public static ArrayList<AbstractCard> getGreenCardsToAdd() {
    	return greenToAdd;
    }
    
    // green remove -
    public static ArrayList<String> getGreenCardsToRemove() {
    	return greenToRemove;
    }
    
    // colorless add -
    public static ArrayList<AbstractCard> getColorlessCardsToAdd() {
    	return colorlessToAdd;
    }
    
    // colorless remove -
    public static ArrayList<String> getColorlessCardsToRemove() {
    	return colorlessToRemove;
    }
    
    // curse add -
    public static ArrayList<AbstractCard> getCurseCardsToAdd() {
    	return curseToAdd;
    }
    
    // curse remove -
    public static ArrayList<String> getCurseCardsToRemove() {
    	return curseToRemove;
    }
    
    // custom add -
    public static ArrayList<AbstractCard> getCustomCardsToAdd() {
    	return customToAdd;
    }
    
    // custom remove -
    public static ArrayList<String> getCustomCardsToRemove() {
    	return customToRemove;
    }
    
 // custom remove colors -
    public static ArrayList<String> getCustomCardsToRemoveColors() {
    	return customToRemoveColors;
    }
    
    // add card
	public static void addCard(AbstractCard card) {
    	switch (card.color) {
    	case RED:
    		redToAdd.add(card);
    		break;
    	case GREEN:
    		greenToAdd.add(card);
    		break;
    	case COLORLESS:
    		colorlessToAdd.add(card);
    		break;
    	case CURSE:
    		curseToAdd.add(card);
    		break;
    	default:
    		customToAdd.add(card);
    		break;
    	}
    }
    
    // remove card
	public static void removeCard(String card, String color) {
    	switch (color) {
    	case "RED":
    		redToRemove.add(card);
    		break;
    	case "GREEN":
    		greenToRemove.add(card);
    		break;
    	case "COLORLESS":
    		colorlessToRemove.add(card);
    		break;
    	case "CURSE":
    		curseToRemove.add(card);
    		break;
    	default:
    		customToRemove.add(card);
    		customToRemoveColors.add(color);
    		break;
    	}
    }
    
    //
    // Relics
    //
    // these adders and removers prevent modders from having to deal with RelicLibrary's need to keep track
    // of counts and multiple lists by abstracting it away to simple addRelic and removeRelic calls
    //
    
    // add relic -
    public static void addRelic(AbstractRelic relic, RelicType type) {
    	switch (type) {
    	case SHARED:
    		RelicLibrary.add(relic);
    		break;
    	case RED:
    		RelicLibrary.addRed(relic);
    		break;
    	case GREEN:
    		RelicLibrary.addGreen(relic);
    		break;
    	default:
    		logger.info("tried to add relic of unsupported type: " + relic + " " + type);
    	}
    }
    
    
    // remove relic -
    @SuppressWarnings("unchecked")
	public static void removeRelic(AbstractRelic relic, RelicType type) {
    	// note that this has to use reflection hacks to change the private
    	// variables in RelicLibrary to successfully remove the relics
    	//
    	// this could be accomplished without reflection hacks by creating a
    	// @SpirePatch to enable relic removal functionality
    	//
    	// as of right now I'm not sure which method is preferable
    	// removeCard is using the @SpirePatch method
    	switch(type) {
    	case SHARED:
    		HashMap<String, AbstractRelic> sharedRelics = (HashMap<String, AbstractRelic>) ReflectionHacks.getPrivateStatic(RelicLibrary.class, "sharedRelics");
    		if (sharedRelics.containsKey(relic.relicId)) {
    			sharedRelics.remove(relic.relicId);
    			RelicLibrary.totalRelicCount--;
    			removeRelicFromTierList(relic);
    		}
    		break;
    	case RED:
    		HashMap<String, AbstractRelic> redRelics = (HashMap<String, AbstractRelic>) ReflectionHacks.getPrivateStatic(RelicLibrary.class, "redRelics");
    		if (redRelics.containsKey(relic.relicId)) {
    			redRelics.remove(relic.relicId);
    			RelicLibrary.totalRelicCount--;
    			removeRelicFromTierList(relic);
    		}
    		break;
    	case GREEN:
    		HashMap<String, AbstractRelic> greenRelics = (HashMap<String, AbstractRelic>) ReflectionHacks.getPrivateStatic(RelicLibrary.class, "greenRelics");
    		if (greenRelics.containsKey(relic.relicId)) {
    			greenRelics.remove(relic.relicId);
    			RelicLibrary.totalRelicCount--;
    			removeRelicFromTierList(relic);
    		}
    		break;
    	default:
    		logger.info("tried to remove relic of unsupported type: " + relic + " " + type);
    	}
    }
    
    private static void removeRelicFromTierList(AbstractRelic relic) {
    	switch (relic.tier) {
    	case STARTER:
    		RelicLibrary.starterList.remove(relic.relicId);
    		break;
    	case COMMON:
    		RelicLibrary.commonList.remove(relic.relicId);
    		break;
    	case UNCOMMON:
    		RelicLibrary.uncommonList.remove(relic.relicId);
    		break;
    	case RARE:
    		RelicLibrary.rareList.remove(relic.relicId);
    		break;
    	case SHOP:
    		RelicLibrary.shopList.remove(relic.relicId);
    		break;
    	case SPECIAL:
    		RelicLibrary.specialList.remove(relic.relicId);
    		break;
    	case BOSS:
    		RelicLibrary.bossList.remove(relic.relicId);
    		break;
    	case DEPRECATED:
    		logger.info(relic.relicId + " is deprecated.");
    		break;
    	default:
    		logger.info(relic.relicId + "is undefined tier.");
    	}
    }
    
    // force remove relic -
    public static void removeRelic(AbstractRelic relic) {
    	removeRelic(relic, RelicType.SHARED);
    	removeRelic(relic, RelicType.RED);
    	removeRelic(relic, RelicType.GREEN);
    }
    
    //
    // Characters
    //
    
    // add character - the String characterID *must* be the exact same as what you put in the PlayerClass enum
    public static void addCharacter(@SuppressWarnings("rawtypes") Class characterClass, String titleString,
    		String classString, String color, String selectText, String selectButton, String portrait, String characterID) {
    	playerClassMap.put(characterID, characterClass);
    	playerTitleStringMap.put(characterID, titleString);
    	playerClassStringMap.put(characterID, classString);
    	playerColorMap.put(characterID, color);
    	playerSelectTextMap.put(characterID, selectText);
    	playerSelectButtonMap.put(characterID, selectButton);
    	playerPortraitMap.put(characterID, portrait);
    }
    
    // I have no idea if this implementation comes even remotely close to working
    public static void removeCharacter(String characterID) {
    	playerClassMap.remove(characterID);
    }
    
    // convert a playerClass String (fake ENUM) into the actual player class
    // used by CardCrawlGame when creating the player for the game
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static AbstractPlayer createCharacter(String playerClass, String playerName) {
    	Class playerClassAsClass = playerClassMap.get(playerClass);
    	Constructor ctor;
		try {
			ctor = playerClassAsClass.getConstructor(String.class, AbstractPlayer.PlayerClass.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// if we fail to get the constructor using java reflections just start the run as the Ironclad
			logger.error("could not get constructor for " + playerClassAsClass.getName());
			logger.info("running as the Ironclad instead");
			CardCrawlGame.chosenCharacter = AbstractPlayer.PlayerClass.IRONCLAD;
			return new Ironclad(playerName, AbstractPlayer.PlayerClass.IRONCLAD);
		}
    	AbstractPlayer thePlayer;
		try {
			thePlayer = (AbstractPlayer) ctor.newInstance(new Object[] {playerName, PlayerClass.valueOf(playerClass)});
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// if we fail to instantiate using java reflections just start the run as the Ironclad
			logger.error("could not instantiate " + playerClassAsClass.getName() + " with the constructor " + ctor.getName());
			logger.info("running as the Ironclad instead");
			logger.error("error was: " + e.getMessage());
			e.printStackTrace();
			CardCrawlGame.chosenCharacter = AbstractPlayer.PlayerClass.IRONCLAD;
			return new Ironclad(playerName, AbstractPlayer.PlayerClass.IRONCLAD);
		}
    	return thePlayer;
    }
    
    // convert a playerClass String (fake ENUM) into the actual title string for that class
    // used by AbstractPlayer when getting the title string for the player
    public static String getTitle(String playerClass) {
    	return playerTitleStringMap.get(playerClass);
    }
    
    // convert a playerClass String (fake ENUM) into the actual starting deck for that class
    @SuppressWarnings("unchecked")
	public static ArrayList<String> getStartingDeck(String playerClass) {
    	@SuppressWarnings("rawtypes")
		Class playerClassAsClass = playerClassMap.get(playerClass);
    	Method getStartingDeck;
		try {
			getStartingDeck = playerClassAsClass.getMethod("getStartingDeck");
		} catch (NoSuchMethodException | SecurityException e1) {
			// if we fail to get the getStartingDeck method using java reflections just start with the Ironclad deck
			logger.error("could not get starting deck method for " + playerClassAsClass.getName());
			return Ironclad.getStartingDeck();
		}
    	Object startingDeckObj;
    	try {
			startingDeckObj = getStartingDeck.invoke(null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// if we fail to get the starting deck using java reflections just start with the Ironclad deck
			logger.error("could not get starting deck for " + playerClassAsClass.getName());
			return Ironclad.getStartingDeck();
		}
    	return (ArrayList<String>) startingDeckObj;
    }
    
    // convert a playerClass String (fake ENUM) into the actual starting relics for that class
    @SuppressWarnings("unchecked")
	public static ArrayList<String> getStartingRelics(String playerClass) {
    	@SuppressWarnings("rawtypes")
		Class playerClassAsClass = playerClassMap.get(playerClass);
    	Method getStartingRelics;
		try {
			getStartingRelics = playerClassAsClass.getMethod("getStartingRelics");
		} catch (NoSuchMethodException | SecurityException e1) {
			// if we fail to get the getStartingRelics method using java reflections just start with the Ironclad relics
			logger.error("could not get starting relic method for " + playerClassAsClass.getName());
			return Ironclad.getStartingRelics();
		}
    	Object startingRelicsObj;
    	try {
    		startingRelicsObj = getStartingRelics.invoke(null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// if we fail to get the starting relics using java reflections just start with the Ironclad relics
			logger.error("could not get starting deck for " + playerClassAsClass.getName());
			return Ironclad.getStartingRelics();
		}
    	return (ArrayList<String>) startingRelicsObj;
    }
    
    // convert a playerClass String (fake ENUM) into the actual class ID for that class
    public static String getClass(String playerClass) {
    	return playerClassStringMap.get(playerClass);
    }
    
    // convert a playerClass String (fake ENUM) into the actual color String (fake ENUM) for that class
    public static String getColor(String playerClass) {
    	return playerColorMap.get(playerClass);
    }
    
    // convert a playerClass String (fake ENUM) into the actual player class
    @SuppressWarnings("rawtypes")
	public static Class getPlayerClass(String playerClass) {
    	return playerClassMap.get(playerClass);
    }
    
    // convert a playerClass String (fake ENUM) into the player select button
    public static String getPlayerButton(String playerClass) {
    	return playerSelectButtonMap.get(playerClass);
    }
    
    // convert a playerClass String (fake ENUM) into the player portrait
    public static String getPlayerPortrait(String playerClass) {
    	return playerPortraitMap.get(playerClass);
    }
    
    // generate character options for CharacterSelectScreen based on added players
    public static ArrayList<CharacterOption> generateCharacterOptions() {
    	ArrayList<CharacterOption> options = new ArrayList<>();
    	for (String character : playerClassMap.keySet()) {
    		// the game by default prepends "images/ui/charSelect" to the image load request
    		// so we override that with "../../.."
    		CharacterOption option = new CharacterOption(playerSelectTextMap.get(character), 
    				AbstractPlayer.PlayerClass.valueOf(character),
    				// note that these will fail so we patch this in basemode.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterOption.CtorSwitch
    				playerSelectButtonMap.get(character),
    				playerPortraitMap.get(character));
    		options.add(option);
    	}
    	return options;
    }
    
    //
    // Colors
    //
    
    // add a color -
    public static void addColor(String color, 
    		com.badlogic.gdx.graphics.Color bgColor,
    		com.badlogic.gdx.graphics.Color backColor,
    		com.badlogic.gdx.graphics.Color frameColor,
    		com.badlogic.gdx.graphics.Color frameOutlineColor,
    		com.badlogic.gdx.graphics.Color descBoxColor,
    		com.badlogic.gdx.graphics.Color trailVfxColor,
    		com.badlogic.gdx.graphics.Color glowColor,
    		String attackBg,
    		String skillBg,
    		String powerBg,
    		String energyOrb) {
    	colorBgColorMap.put(color, bgColor);
    	colorBackColorMap.put(color, backColor);
    	colorFrameColorMap.put(color, frameColor);
    	colorFrameOutlineColorMap.put(color, frameOutlineColor);
    	colorDescBoxColorMap.put(color, descBoxColor);
    	colorTrailVfxMap.put(color, trailVfxColor);
    	colorGlowColorMap.put(color, glowColor);
    	colorCardCountMap.put(color, 0);
    	colorCardSeenCountMap.put(color, 0);
    	colorAttackBgMap.put(color, attackBg);
    	colorSkillBgMap.put(color, skillBg);
    	colorPowerBgMap.put(color, powerBg);
    	colorEnergyOrbMap.put(color, energyOrb);
    }
    
    // remove a custom color -
    // removing existing colors not currently supported
    public static void removeColor(String color) {
    	colorBgColorMap.remove(color);
    	colorBackColorMap.remove(color);
    	colorFrameColorMap.remove(color);
    	colorFrameOutlineColorMap.remove(color);
    	colorDescBoxColorMap.remove(color);
    	colorTrailVfxMap.remove(color);
    	colorGlowColorMap.remove(color);
    	colorCardCountMap.remove(color);
    	colorCardSeenCountMap.remove(color);
    	colorAttackBgMap.remove(color);
    	colorSkillBgMap.remove(color);
    	colorPowerBgMap.remove(color);
    	colorEnergyOrbMap.remove(color);
    }
    
    // convert a color String (fake ENUM) into a background color
    public static com.badlogic.gdx.graphics.Color getBgColor(String color) {
    	return colorBgColorMap.get(color);
    }
    
    // convert a color String (fake ENUM) into a back color
    public static com.badlogic.gdx.graphics.Color getBackColor(String color) {
    	return colorBackColorMap.get(color);
    }
    
    // convert a color String (fake ENUM) into a frame color
    public static com.badlogic.gdx.graphics.Color getFrameColor(String color) {
    	return colorFrameColorMap.get(color);
    }
    
    // convert a color String (fake ENUM) into a frame outline color
    public static com.badlogic.gdx.graphics.Color getFrameOutlineColor(String color) {
    	return colorFrameOutlineColorMap.get(color);
    }
    
    // convert a color String (fake ENUM) into a desc box color
    public static com.badlogic.gdx.graphics.Color getDescBoxColor(String color) {
    	return colorDescBoxColorMap.get(color);
    }
    
    // convert a color String (fake ENUM) into a trail vfx color
    public static com.badlogic.gdx.graphics.Color getTrailVfxColor(String color) {
    	return colorTrailVfxMap.get(color);
    }
    
    // increment the card count for a color String (fake ENUM)
    public static void incrementCardCount(String color) {
    	Integer count = colorCardCountMap.get(color);
    	if (count != null) {
    		colorCardCountMap.put(color, count+1);
    	}
    }
    
    // decrement the card count for a color String (fake ENUM)
    public static void decrementCardCount(String color) {
    	Integer count = colorCardCountMap.get(color);
    	if (count != null) {
    		colorCardCountMap.put(color, count-1);
    	}
    }
    
    // increment the seen card count for a color String (fake ENUM)
    public static void incrementSeenCardCount(String color) {
    	Integer count = colorCardSeenCountMap.get(color);
    	if (count != null) {
    		colorCardSeenCountMap.put(color, count+1);
    	}
    }
    
    // convert a color String (fake ENUM) into a glow color
    public static com.badlogic.gdx.graphics.Color getGlowColor(String color) {
    	return colorGlowColorMap.get(color);
    }
    
    // convert a color String (fake ENUM) into an attack background texture path
    public static String getAttackBg(String color) {
    	return colorAttackBgMap.get(color);
    }
    
    // convert a color String (fake ENUM) into an skill background texture path
    public static String getSkillBg(String color) {
    	return colorSkillBgMap.get(color);
    }
    
    // convert a color String (fake ENUM) into an power background texture path
    public static String getPowerBg(String color) {
    	return colorPowerBgMap.get(color);
    }
    
    // convert a color String (fake ENUM) into an energy texture path
    public static String getEnergyOrb(String color) {
    	return colorEnergyOrbMap.get(color);
    }
    
    // convert a color String (fake ENUM) into an attack background texture
    public static com.badlogic.gdx.graphics.Texture getAttackBgTexture(String color) {
    	return colorAttackBgTextureMap.get(color);
    }
    
    // convert a color String (fake ENUM) into an skill background texture
    public static com.badlogic.gdx.graphics.Texture getSkillBgTexture(String color) {
    	return colorSkillBgTextureMap.get(color);
    }
    
    // convert a color String (fake ENUM) into an power background texture
    public static com.badlogic.gdx.graphics.Texture getPowerBgTexture(String color) {
    	return colorPowerBgTextureMap.get(color);
    }
    
    // convert a color String (fake ENUM) into an energy texture
    public static com.badlogic.gdx.graphics.Texture getEnergyOrbTexture(String color) {
    	return colorEnergyOrbTextureMap.get(color);
    }
    
    // save a attack background texture for a color String (fake ENUM)
    public static void saveAttackBgTexture(String color, com.badlogic.gdx.graphics.Texture tex) {
    	colorAttackBgTextureMap.put(color, tex);
    }
    
    // save a skill background texture for a color String (fake ENUM)
    public static void saveSkillBgTexture(String color, com.badlogic.gdx.graphics.Texture tex) {
    	colorSkillBgTextureMap.put(color, tex);
    }
    
    // save a power background texture for a color String (fake ENUM)
    public static void savePowerBgTexture(String color, com.badlogic.gdx.graphics.Texture tex) {
    	colorPowerBgTextureMap.put(color, tex);
    }
    
    // save an energy orb texture for a color String (fake ENUM)
    public static void saveEnergyOrbTexture(String color, com.badlogic.gdx.graphics.Texture tex) {
    	colorEnergyOrbTextureMap.put(color, tex);
    }
    
    
    //
    // Publishers
    //

    // publishStartAct -
    public static void publishStartAct() {
        logger.info("publishStartAct");
        for (StartActSubscriber sub : startActSubscribers) {
            sub.receiveStartAct();
        }
    }

    // publishPostCampfire - false allows an additional option to be selected
    public static boolean publishPostCampfire() {
        logger.info("publishPostCampfire");
        
        boolean campfireDone = true;
        
        for (PostCampfireSubscriber sub : postCampfireSubscribers) {
            if (!sub.receivePostCampfire()) {
                campfireDone = false;
            }
        }
        
        return campfireDone;
    }
    
    // publishPostDraw -
    public static void publishPostDraw(AbstractCard c) {
        logger.info("publishPostDraw");
        for (PostDrawSubscriber sub : postDrawSubscribers) {
            sub.receivePostDraw(c);
        }
    }

    // publishPostDungeonInitialize -
    public static void publishPostDungeonInitialize() {
        logger.info("publishPostDungeonInitialize");

        for (PostDungeonInitializeSubscriber sub : postDungeonInitializeSubscribers) {
            sub.receivePostDungeonInitialize();
        }
    }

    // publishPostEnergyRecharge -
    public static void publishPostEnergyRecharge() {
        logger.info("publishPostEnergyRecharge");
        for (PostEnergyRechargeSubscriber sub : postEnergyRechargeSubscribers) {
            sub.receivePostEnergyRecharge();
        }
    }
    
    // publishPostInitialize -
    public static void publishPostInitialize() {
        logger.info("publishPostInitialize");
        
        // Publish
        for (PostInitializeSubscriber sub : postInitializeSubscribers) {
            sub.receivePostInitialize();
        }
    }
    
    // publishPreMonsterTurn - false skips monster turn
    public static boolean publishPreMonsterTurn(AbstractMonster m) {
        logger.info("publishPreMonsterTurn");
        
        boolean takeTurn = true;
        
        for (PreMonsterTurnSubscriber sub : preMonsterTurnSubscribers) {
            if (!sub.receivePreMonsterTurn(m)) {
                takeTurn = false;
            }
        }
        
        return takeTurn;
    }
    
    // publishRender -
    public static void publishRender(SpriteBatch sb) {
        for (RenderSubscriber sub : renderSubscribers) {
            sub.receiveRender(sb);
        }
    }
    
    // publishPostRender -
    public static void publishPostRender(SpriteBatch sb) {
        for (PostRenderSubscriber sub : postRenderSubscribers) {
            sub.receivePostRender(sb);
        }
    }
    
    // publishPreStartGame -
    public static void publishPreStartGame() {
        logger.info("publishPreStartGame");
        
        // Publish
        for (PreStartGameSubscriber sub : preStartGameSubscribers) {
            sub.receivePreStartGame();
        }
    }

    public static void publishStartGame() {
        logger.info("publishStartGame");

        for (StartGameSubscriber sub : startGameSubscribers) {
            sub.receiveStartGame();
        }

        logger.info("mapDensityMultiplier: " + mapPathDensityMultiplier);
    }

    // publishPreUpdate -
    public static void publishPreUpdate() {
        for (PreUpdateSubscriber sub : preUpdateSubscribers) {
            sub.receivePreUpdate();
        }
    }
    
    // publishPostUpdate -
    public static void publishPostUpdate() {
        for (PostUpdateSubscriber sub : postUpdateSubscribers) {
            sub.receivePostUpdate();
        }
    }
    
    // publishPostCreateStartingDeck -
    public static void publishPostCreateStartingDeck(PlayerClass chosenClass, ArrayList<String> cards) {
    	logger.info("postCreateStartingDeck for: " + chosenClass);
    	
    	boolean clearDefault = false;
    	ArrayList<String> cardsToAdd = new ArrayList<String>();
    	
    	for (PostCreateStartingDeckSubscriber sub : postCreateStartingDeckSubscribers) {
    		logger.info("postCreateStartingDeck modifying starting deck for: " + sub);
    		switch (chosenClass) {
    		case IRONCLAD:
    			if (sub instanceof PostCreateIroncladStartingDeckSubscriber) {
    				if (sub.receivePostCreateStartingDeck(cardsToAdd)) {
    					clearDefault = true;
    				}
    			}
    			break;
    		case THE_SILENT:
    			if (sub instanceof PostCreateSilentStartingDeckSubscriber) {
    				if (sub.receivePostCreateStartingDeck(cardsToAdd)) {
    					clearDefault = true;
    				}
    			}
    			break;
    		default:
    			break;
    		}
    	}
    	
    	String logString = "postCreateStartingDeck adding [ ";
    	for (String card : cardsToAdd) {
    		logString += (card + " ");
    	}
    	logString += "]";
    	logger.info(logString);
    	
    	if (clearDefault) {
    		logger.info("postCreateStartingDeck clearing initial deck");
    		cards.clear();
    	}
    	cards.addAll(cardsToAdd);
    }
    
    public static ArrayList<String> relicsThatNeedSpecificPlayer = new ArrayList<>();
    
    // populate relics that require a specific player for copy list
    static {
    	String[] relicsThatNeedSpecificPlayerStrArr = {"Ancient Tea Set", "Art of War", "Happy Flower", "Lantern", "Dodecahedron", "Sundial", "Cursed Key", "Ectoplasm", "Mark of Pain", "Philosopher's Stone", "Runic Dome", "Sozu", "Velvet Choker"};
    	for (int i = 0; i < relicsThatNeedSpecificPlayerStrArr.length; i++) {
    		relicsThatNeedSpecificPlayer.add(relicsThatNeedSpecificPlayerStrArr[i]);
    	}
    }
    
    
    // publishPostCreateStartingRelics -
    public static void publishPostCreateStartingRelics(PlayerClass chosenClass, ArrayList<String> relics) {
    	logger.info("postCreateStartingRelics for: " + chosenClass);
    	
    	boolean clearDefault = false;
    	ArrayList<String> relicsToAdd = new ArrayList<String>();
    	
    	for (PostCreateStartingRelicsSubscriber sub : postCreateStartingRelicsSubscribers) {
    		logger.info("postCreateStartingRelics modifying starting relics for: " + sub);
    		switch (chosenClass) {
    		case IRONCLAD:
    			if (sub instanceof PostCreateIroncladStartingRelicsSubscriber) {
    				if (sub.receivePostCreateStartingRelics(relicsToAdd)) {
    					clearDefault = true;
    				}
    			}
    			break;
    		case THE_SILENT:
    			if (sub instanceof PostCreateSilentStartingRelicsSubscriber) {
    				if (sub.receivePostCreateStartingRelics(relicsToAdd)) {
    					clearDefault = true;
    				}
    			}
    			break;
    		default:
    			break;
    		}
    	}
    	
    	String logString = "postCreateStartingRelics adding [ ";
    	for (String relic : relicsToAdd) {
    		logString += (relic + " ");
    	}
    	logString += "]";
    	logger.info(logString);
    	
    	// mark as seen
    	for (String relic : relicsToAdd) {
    		UnlockTracker.markRelicAsSeen(relic);
    	}
    	
    	// the default setup for adding starting relics does not do
    	// equip triggers on the relics so we circumvent that by
    	// adding relics ourself on dungeon initialize and force
    	// the equip trigger
		subscribeToPostDungeonInitialize(new PostDungeonInitializeSubscriber() {

			@Override
			public void receivePostDungeonInitialize() {
				int relicIndex = AbstractDungeon.player.relics.size();
				int relicRemoveIndex = relicsToAdd.size() - 1;
				while (relicsToAdd.size() > 0) {
					System.out.println("Attempting to add: " + relicsToAdd.get(relicRemoveIndex));
					AbstractRelic relic = RelicLibrary.getRelic(relicsToAdd.remove(relicRemoveIndex));
					System.out.println("Found relic is: " + relic);
					AbstractRelic relicCopy;
					// without checking if the relic wants to have a player class provided
					// the makeCopy() method would return null in cases where the relic
					// didn't implement it
					if (relicsThatNeedSpecificPlayer.contains(relic.name)) {
						relicCopy = relic.makeCopy(AbstractDungeon.player.chosenClass);
					} else {
						relicCopy = relic.makeCopy();
					}
					relicCopy.instantObtain(AbstractDungeon.player, relicIndex, true);
					relicRemoveIndex--;
					relicIndex++;
				}
			}
			
		});
    	
    	if (clearDefault) {
    		logger.info("postCreateStartingRelics clearing initial relics");
    		relics.clear();
    	}
    	
    	AbstractDungeon.relicsToRemoveOnStart.addAll(relicsToAdd);
    }
    
    // publishPostCreateShopRelic -
    public static void publishPostCreateShopRelics(ArrayList<StoreRelic> relics, ShopScreen screenInstance) {
    	logger.info("postCreateShopRelics for: " + relics);

    	for (PostCreateShopRelicSubscriber sub : postCreateShopRelicSubscribers) {
    		sub.receiveCreateShopRelics(relics, screenInstance);
    	}
    }
    
    // publishPostCreateShopPotion -
    public static void publishPostCreateShopPotions(ArrayList<StorePotion> potions, ShopScreen screenInstance) {
    	logger.info("postCreateShopPotions for: " + potions);
    	
    	for (PostCreateShopPotionSubscriber sub : postCreateShopPotionSubscribers) {
    		sub.receiveCreateShopPotions(potions, screenInstance);
    	}
    }
    
    // publishEditCards -
    public static void publishEditCards() {
    	logger.info("begin editing cards");
		
		for (EditCardsSubscriber sub : editCardsSubscribers) {
			sub.receiveEditCards();
		}
    }
    
    // publishEditRelics -
    public static void publishEditRelics() {
    	logger.info("begin editing relics");
    	
    	for (EditRelicsSubscriber sub : editRelicsSubscribers) {
    		sub.receiveEditRelics();
    	}
    }
    
    // publishEditCharacters -
    public static void publishEditCharacters() {
    	logger.info("begin editing characters");
    	
    	for (EditCharactersSubscriber sub : editCharactersSubscribers) {
    		sub.receiveEditCharacters();
    	}
    }
    // publishEditStrings -
    public static void publishEditStrings() {
    	logger.info("begin editing localization strings");
    	
    	for (EditStringsSubscriber sub : editStringsSubscribers) {
    		sub.receiveEditStrings();
    	}
    }

    //
    // Subscription handlers
    //

    // subscribeToStartAct -
    public static void subscribeToStartAct(StartActSubscriber sub) {
        startActSubscribers.add(sub);
    }

    // unsubscribeFromStartAct -
    public static void unsubscribeFromStartAct(StartActSubscriber sub) {
        startActSubscribers.remove(sub);
    }

    // subscribeToPostCampfire -
    public static void subscribeToPostCampfire(PostCampfireSubscriber sub) {
        postCampfireSubscribers.add(sub);
    }
    
    // unsubscribeFromPostCampfire -
    public static void unsubscribeFromPostCampfire(PostCampfireSubscriber sub) {
        postCampfireSubscribers.remove(sub);
    }
    
    // subscribeToPostDraw -
    public static void subscribeToPostDraw(PostDrawSubscriber sub) {
        postDrawSubscribers.add(sub);
    }
    
    // unsubscribeFromPostDraw -
    public static void unsubscribeFromPostDraw(PostDrawSubscriber sub) {
        postDrawSubscribers.remove(sub);
    }

    // subscribeToPostDungeonInitialize -
    public static void subscribeToPostDungeonInitialize(PostDungeonInitializeSubscriber sub) {
        postDungeonInitializeSubscribers.add(sub);
    }

    // unsubcribeFromPostDungeonInitialize -
    public static void unsubscribeFromPostDungeonInitialize(PostDungeonInitializeSubscriber sub) {
        postDungeonInitializeSubscribers.remove(sub);
    }

    // subscribeToPostEnergyRecharge -
    public static void subscribeToPostEnergyRecharge(PostEnergyRechargeSubscriber sub) {
        postEnergyRechargeSubscribers.add(sub);
    }
    
    // unsubscribeFromPostEnergyRecharge -
    public static void unsubscribeFromPostEnergyRecharge(PostEnergyRechargeSubscriber sub) {
        postEnergyRechargeSubscribers.remove(sub);
    }
    
    // subscribeToPreMonsterTurn -
    public static void subscribeToPreMonsterTurn(PreMonsterTurnSubscriber sub) {
        preMonsterTurnSubscribers.add(sub);
    }
    
    // unsubscribeFromPreMonsterTurn -
    public static void unsubscribeFromPreMonsterTurn(PreMonsterTurnSubscriber sub) {
        preMonsterTurnSubscribers.remove(sub);
    }
    
    // subscribeToPostInitialize -
    public static void subscribeToPostInitialize(PostInitializeSubscriber sub) {
        postInitializeSubscribers.add(sub);
    }
    
    // unsubscribeFromPostRender -
    public static void unsubscribeFromPostInitialize(PostInitializeSubscriber sub) {
        postInitializeSubscribers.remove(sub);
    }
    
    // subscribeToRender -
    public static void subscribeToRender(RenderSubscriber sub) {
        renderSubscribers.add(sub);
    }
    
    // unsubscribeFromRender -
    public static void unsubscribeFromRender(RenderSubscriber sub) {
        renderSubscribers.remove(sub);
    }
    
    // subscribeToPostRender -
    public static void subscribeToPostRender(PostRenderSubscriber sub) {
        postRenderSubscribers.add(sub);
    }
    
    // unsubscribeFromPostRender -
    public static void unsubscribeFromPostRender(PostRenderSubscriber sub) {
        postRenderSubscribers.remove(sub);
    }

    // subscribeToStartGame -
    public static void subscribeToStartGame(StartGameSubscriber sub) {
        startGameSubscribers.add(sub);
    }

    // unsubscribeFromStartGame -
    public static void unsubscribeFromStartGame(StartGameSubscriber sub) {
        startGameSubscribers.remove(sub);
    }

    // subscribeToPreStartGame -
    public static void subscribeToPreStartGame(PreStartGameSubscriber sub) {
        preStartGameSubscribers.add(sub);
    }
    
    // unsubscribeFromPreStartGame -
    public static void unsubscribeFromPreStartGame(PreStartGameSubscriber sub) {
        preStartGameSubscribers.remove(sub);
    }
    
    // subscribeToPreUpdate -
    public static void subscribeToPreUpdate(PreUpdateSubscriber sub) {
        preUpdateSubscribers.add(sub);
    }
    
    // unsubscribeFromPreUpdate -
    public static void unsubscribeFromPreUpdate(PreUpdateSubscriber sub) {
        preUpdateSubscribers.remove(sub);
    }
    
    // subscribeToPostUpdate -
    public static void subscribeToPostUpdate(PostUpdateSubscriber sub) {
        postUpdateSubscribers.add(sub);
    }
    
    // unsubscribeFromUpdate -
    public static void unsubscribeFromPostUpdate(PostUpdateSubscriber sub) {
        postUpdateSubscribers.remove(sub);
    }
    
    // subscribeToPostCreateStartingDeck -
    public static void subscribeToPostCreateStartingDeck(PostCreateStartingDeckSubscriber sub) {
    	postCreateStartingDeckSubscribers.add(sub);
    }
    
    // unsubscribeToPostCreateStartingDeck -
    public static void unsubscribeToPostCreateStartingDeck(PostCreateStartingDeckSubscriber sub) {
    	postCreateStartingDeckSubscribers.remove(sub);
    }
    
    // subscribeToPostCreateStartingRelics -
    public static void subscribeToPostCreateStartingRelics(PostCreateStartingRelicsSubscriber sub) {
    	postCreateStartingRelicsSubscribers.add(sub);
    }
    
    // unsubscribeToPostCreateStartingRelics -
    public static void unsubscribeToPostCreateStartingRelics(PostCreateStartingRelicsSubscriber sub) {
    	postCreateStartingRelicsSubscribers.remove(sub);
    }
    
    // subscribeToPostCreateShopRelic -
    public static void subscribeToPostCreateShopRelic(PostCreateShopRelicSubscriber sub) {
    	postCreateShopRelicSubscribers.add(sub);
    }
    
    // unsubscribeToPostCreateShopRelic
    public static void unsubscribeToPostCreateShopRelic(PostCreateShopRelicSubscriber sub) {
    	postCreateShopRelicSubscribers.remove(sub);
    }
    
    // subscribeToPostCreateShopPotion -
    public static void subscribeToPostCreateShopPotion(PostCreateShopPotionSubscriber sub) {
    	postCreateShopPotionSubscribers.add(sub);
    }
    
    // unsubscribeToPostCreateShopRelic
    public static void unsubscribeToPostCreateShopPotion(PostCreateShopPotionSubscriber sub) {
    	postCreateShopPotionSubscribers.remove(sub);
    }
    
    // subscribeToEditCards -
    public static void subscribeToEditCards(EditCardsSubscriber sub) {
    	editCardsSubscribers.add(sub);
    }
    
    // unsubscribeToEditCards -
    public static void unsubscribeToEditCards(EditCardsSubscriber sub) {
    	editCardsSubscribers.remove(sub);
    }
    
    // subscribeToEditRelics -
    public static void subscribeToEditRelics(EditRelicsSubscriber sub) {
    	editRelicsSubscribers.add(sub);
    }
    
    // unsubscribeToEditRelics -
    public static void unsubscribeToEditRelics(EditRelicsSubscriber sub) {
    	editRelicsSubscribers.remove(sub);
    }
    
    // subscribeToEditCharacters -
    public static void subscribeToEditCharacters(EditCharactersSubscriber sub) {
    	editCharactersSubscribers.add(sub);
    }
    
    // unsubscribeToEditCharacters -
    public static void unsubscribeToEditCharacters(EditCharactersSubscriber sub) {
    	editCharactersSubscribers.remove(sub);
    }
    
    // subscribeToEditStrings
    public static void subscribeToEditStrings(EditStringsSubscriber sub) {
    	editStringsSubscribers.add(sub);
    }
    
    // unsubscribeToEditStrings
    public static void unsubscribeToEditStrings(EditStringsSubscriber sub) {
    	editStringsSubscribers.remove(sub);
    }

}