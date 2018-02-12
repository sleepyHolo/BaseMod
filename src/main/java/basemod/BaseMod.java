package basemod;

import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    
    private static ArrayList<AbstractCard> redToAdd;
    private static ArrayList<AbstractCard> redToRemove;
    private static ArrayList<AbstractCard> greenToAdd;
    private static ArrayList<AbstractCard> greenToRemove;
    private static ArrayList<AbstractCard> colorlessToAdd;
    private static ArrayList<AbstractCard> colorlessToRemove;
    private static ArrayList<AbstractCard> curseToAdd;
    private static ArrayList<AbstractCard> curseToRemove;
    
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
        
        BaseModInit baseModInit = new BaseModInit();
        BaseMod.subscribeToPostInitialize(baseModInit);

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

        Map localizationStrings = (Map) getPrivateStatic(LocalizedStrings.class, typeMap);
        localizationStrings.putAll(new HashMap(gson.fromJson(jsonString, typeToken)));
        setPrivateStaticFinal(LocalizedStrings.class, typeMap, localizationStrings);
    }

    // loadCustomRelicStrings - loads custom RelicStrings from provided JSON
    @Deprecated
    public static void loadCustomRelicStrings(String jsonString) {
        loadJsonStrings(RelicStrings.class, jsonString);
    }
    
    //
    // Cards
    //
    
    // red add -
    public static ArrayList<AbstractCard> getRedCardsToAdd() {
    	return redToAdd;
    }
    
    // red remove -
    public static ArrayList<AbstractCard> getRedCardsToRemove() {
    	return redToRemove;
    }
    
    // green add -
    public static ArrayList<AbstractCard> getGreenCardsToAdd() {
    	return greenToAdd;
    }
    
    // green remove -
    public static ArrayList<AbstractCard> getGreenCardsToRemove() {
    	return greenToRemove;
    }
    
    // colorless add -
    public static ArrayList<AbstractCard> getColorlessCardsToAdd() {
    	return colorlessToAdd;
    }
    
    // colorless remove -
    public static ArrayList<AbstractCard> getColorlessCardsToRemove() {
    	return colorlessToRemove;
    }
    
    // curse add -
    public static ArrayList<AbstractCard> getCurseCardsToAdd() {
    	return curseToAdd;
    }
    
    // curse remove -
    public static ArrayList<AbstractCard> getCurseCardsToRemove() {
    	return curseToRemove;
    }
    
    // add card
    @SuppressWarnings("incomplete-switch")
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
    	}
    }
    
    // remove card
    @SuppressWarnings("incomplete-switch")
	public static void removeCard(AbstractCard card) {
    	switch (card.color) {
    	case RED:
    		redToRemove.add(card);
    		break;
    	case GREEN:
    		greenToRemove.add(card);
    		break;
    	case COLORLESS:
    		colorlessToRemove.add(card);
    		break;
    	case CURSE:
    		curseToRemove.add(card);
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
    	switch(type) {
    	case SHARED:
    		HashMap<String, AbstractRelic> sharedRelics = (HashMap<String, AbstractRelic>) getPrivateStatic(RelicLibrary.class, "sharedRelics");
    		if (sharedRelics.containsKey(relic.relicId)) {
    			sharedRelics.remove(relic.relicId);
    			RelicLibrary.totalRelicCount--;
    			removeRelicFromTierList(relic);
    		}
    		break;
    	case RED:
    		HashMap<String, AbstractRelic> redRelics = (HashMap<String, AbstractRelic>) getPrivateStatic(RelicLibrary.class, "redRelics");
    		if (redRelics.containsKey(relic.relicId)) {
    			redRelics.remove(relic.relicId);
    			RelicLibrary.totalRelicCount--;
    			removeRelicFromTierList(relic);
    		}
    		break;
    	case GREEN:
    		HashMap<String, AbstractRelic> greenRelics = (HashMap<String, AbstractRelic>) getPrivateStatic(RelicLibrary.class, "greenRelics");
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
    
    //
    // Reflection hacks
    //
    
    // getPrivateStatic - read private static variables
    @SuppressWarnings("rawtypes")
	public static Object getPrivateStatic(Class objClass, String fieldName) {
        try {
            Field targetField = objClass.getDeclaredField(fieldName);
            targetField.setAccessible(true);
            return targetField.get(null);
        } catch (Exception e) {
            logger.error("Exception occured when getting private static field " + fieldName + " of " + objClass.getName(), e);
        }
        
        return null;
    }
    
    // setPrivateStatic - modify private static variables
    @SuppressWarnings("rawtypes")
	public static void setPrivateStatic(Class objClass, String fieldName, Object newValue) {
    	setPrivateStaticFinal(objClass, fieldName, newValue);
    }
    
    // setPrivateStaticFinal - modify (private) static (final) variables
    @SuppressWarnings("rawtypes")
	public static void setPrivateStaticFinal(Class objClass, String fieldName, Object newValue) {
        try {
            Field targetField = objClass.getDeclaredField(fieldName);
            
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(targetField, targetField.getModifiers() & ~Modifier.FINAL);
            
            targetField.setAccessible(true);
            targetField.set(null, newValue);
        } catch (Exception e) {
            logger.error("Exception occured when setting private static (final) field " + fieldName + " of " + objClass.getName(), e);
        }
    }

    // getPrivate - read private varibles of an object
    @SuppressWarnings("rawtypes")
	public static Object getPrivate(Object obj, Class objClass, String fieldName) {
        try {
            Field targetField = objClass.getDeclaredField(fieldName);
            targetField.setAccessible(true);
            return targetField.get(obj);
        } catch (Exception e) {
            logger.error("Exception occured when getting private field " + fieldName + " of " + objClass.getName(), e);
        }

        return null;
    }

    // setPrivate - set private variables of an object
    @SuppressWarnings("rawtypes")
	public static void setPrivate(Object obj, Class objClass, String fieldName, Object newValue) {
        try {
            Field targetField = objClass.getDeclaredField(fieldName);
            targetField.setAccessible(true);
            targetField.set(obj, newValue);
        } catch (Exception e) {
            logger.error("Exception occured when setting private field " + fieldName + " of " + objClass.getName(), e);
        }
    }
    
    // setPrivateInherited - set private variable of superclass of an object
    @SuppressWarnings("rawtypes")
	public static void setPrivateInherited(Object obj, Class objClass, String fieldName, Object newValue) {
    	try {
    		Field targetField = objClass.getSuperclass().getDeclaredField(fieldName);
    		targetField.setAccessible(true);
    		targetField.set(obj, newValue);
    	} catch (Exception e) {
    		logger.error("Exception occured when setting private field " + fieldName + " of the superclass of " + objClass.getName(), e);
    	}
    }

}