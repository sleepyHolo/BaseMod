package basemod;

import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
    
    private static final String MODNAME = "BaseMod";
    private static final String AUTHOR = "t-larson";
    private static final String DESCRIPTION = "v1.4.2 NL Provides hooks and a console.";
    
    private static final int BADGES_PER_ROW = 16;
    private static final float BADGES_X = 640.0f;
    private static final float BADGES_Y = 250.0f;
    public static final float BADGE_W = 40.0f;
    public static final float BADGE_H = 40.0f;

    private static InputProcessor oldInputProcessor = null;

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
    @SuppressWarnings({"unchecked", "ConstantConditions"})
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
        
        // BaseMod post initialize handling
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addLabel("", 475.0f, 700.0f, (me) -> {
            if (me.parent.waitingOnEvent) {
                me.text = "Press key";
            } else {
                me.text = "Change console hotkey (" + Keys.toString(DevConsole.toggleKey) + ")";
            }
        });
        
        settingsPanel.addButton(350.0f, 650.0f, (me) -> {
            me.parent.waitingOnEvent = true;
            oldInputProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(new InputAdapter() {
                @Override
                public boolean keyUp(int keycode) {
                    DevConsole.toggleKey = keycode;
                    me.parent.waitingOnEvent = false;
                    Gdx.input.setInputProcessor(oldInputProcessor);
                    return true;
                }
            });
        });
        
        Texture badgeTexture = new Texture(Gdx.files.internal("img/BaseModBadge.png"));
        registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
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
    
    //
    // Subsciption handlers
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
    
    //
    // Reflection hacks
    //
    
    // getPrivateStatic - read private static variables
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
    
    // setPrivateStaticFinal - modify (private) static (final) variables
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
    public static void setPrivate(Object obj, Class objClass, String fieldName, Object newValue) {
        try {
            Field targetField = objClass.getDeclaredField(fieldName);
            targetField.setAccessible(true);
            targetField.set(obj, newValue);
        } catch (Exception e) {
            logger.error("Exception occured when setting private field " + fieldName + " of " + objClass.getName(), e);
        }
    }
}