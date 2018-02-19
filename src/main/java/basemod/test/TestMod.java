package basemod.test;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostCampfireSubscriber;
import basemod.interfaces.PostCreateIroncladStartingDeckSubscriber;
import basemod.interfaces.PostCreateIroncladStartingRelicsSubscriber;
import basemod.interfaces.PostCreateShopPotionSubscriber;
import basemod.interfaces.PostCreateShopRelicSubscriber;
import basemod.interfaces.PostCreateSilentStartingDeckSubscriber;
import basemod.interfaces.PostCreateSilentStartingRelicsSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostEnergyRechargeSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostRenderSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import basemod.interfaces.PreMonsterTurnSubscriber;
import basemod.interfaces.PreStartGameSubscriber;
import basemod.interfaces.PreUpdateSubscriber;
import basemod.interfaces.RenderSubscriber;
import basemod.interfaces.StartActSubscriber;
import basemod.interfaces.StartGameSubscriber;

@SpireInitializer
public class TestMod implements
	EditCardsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber,
	EditStringsSubscriber, PostCampfireSubscriber, PostCreateIroncladStartingDeckSubscriber,
	PostCreateIroncladStartingRelicsSubscriber, PostCreateShopPotionSubscriber,
	PostCreateShopRelicSubscriber, PostCreateSilentStartingDeckSubscriber,
	PostCreateSilentStartingRelicsSubscriber, PostDrawSubscriber,
	PostDungeonInitializeSubscriber, PostEnergyRechargeSubscriber,
	PostInitializeSubscriber, PostRenderSubscriber, PostUpdateSubscriber,
	PreMonsterTurnSubscriber, PreStartGameSubscriber,
	PreUpdateSubscriber, RenderSubscriber, StartActSubscriber,
	StartGameSubscriber {
	public static final Logger logger = LogManager.getLogger(TestMod.class.getName());
	
	private static final String LOG_FILE = "testmod.log";
	
	private static final String MODNAME = "TestMod";
	private static final String AUTHOR = "Test447";
	private static final String DESCRIPTION = "v1.6.4";
	
	//
	// Subscriptions
	//
	
	private static interface SubInterface {
		void doSubscribe();
	}
	
	private static void subscribe(String msg, SubInterface subCall) {
		logger.info("registering subscriber: " + msg);
		try {
			subCall.doSubscribe();
		} catch (Exception e) {
			logger.info("failed");
			loudWrite(writer, "subscribe to " + msg + " 0/1");
			return;
		}
		logger.info("done");
		loudWrite(writer, "subscribe to " + msg + " 1/1");
	}
	
	//
	// Logging
	//
	
	@SuppressWarnings("unused")
	private static void silentWrite(BufferedWriter writer, String msg) {
		try {
			writer.write(msg);
		} catch (IOException e) {
			logger.error("IOException on writing: " + msg);
			logger.error("ERROR: " + e.toString());
			e.printStackTrace();
		}
	}
	
	private static void loudWrite(BufferedWriter writer, String msg) {
		try {
			writer.write(msg);
		} catch (IOException e) {
			logger.error("IOException on writing: " + msg);
			logger.error("ERROR: " + e.toString());
			e.printStackTrace();
			Gdx.app.exit();
		}
	}
	
	//
	// Error handling
	//
	
	private static void couldNotCrash(String msg, Exception e) {
		logger.error("COULD NOT " + msg + " FOR TESTMOD - EXITING - ERROR BELOW");
		logger.error("ERROR: " + e.toString());
		e.printStackTrace();
		Gdx.app.exit();
	}
	
	//
	// Test driver
	//
	
	private static void beginDriving() {
		
	}
	
	private static BufferedWriter writer = null;
	private static Robot driver = null;
	
	public TestMod() {
		loudWrite(writer, "Begin subscribing to hooks");
		subscribe("editCards", () -> BaseMod.subscribeToEditCards(this));
		subscribe("editCharacters", () -> BaseMod.subscribeToEditCharacters(this));
		subscribe("editRelics", () -> BaseMod.subscribeToEditRelics(this));
		subscribe("editStrings", () -> BaseMod.subscribeToEditStrings(this));
		subscribe("postCampfire", () -> BaseMod.subscribeToPostCampfire(this));
		subscribe("postCreateShopPotion", () -> BaseMod.subscribeToPostCreateShopPotion(this));
		subscribe("postCreateShopRelic", () -> BaseMod.subscribeToPostCreateShopRelic(this));
		subscribe("postCreateStartingDeck", () -> BaseMod.subscribeToPostCreateStartingDeck(this));
		subscribe("postCreateStartingRelics", () -> BaseMod.subscribeToPostCreateStartingRelics(this));
		subscribe("postDraw", () -> BaseMod.subscribeToPostDraw(this));
		subscribe("postDungeonInitialize", () -> BaseMod.subscribeToPostDungeonInitialize(this));
		subscribe("postEnergyRecharge", () -> BaseMod.subscribeToPostEnergyRecharge(this));
		subscribe("postInitialize", () -> BaseMod.subscribeToPostInitialize(this));
		subscribe("postRender", () -> BaseMod.subscribeToPostRender(this));
		subscribe("postUpdate", () -> BaseMod.subscribeToPostUpdate(this));
		subscribe("preMonsterTurn", () -> BaseMod.subscribeToPreMonsterTurn(this));
		subscribe("postEnergyRecharge", () -> BaseMod.subscribeToPreStartGame(this));
		subscribe("postInitialize", () -> BaseMod.subscribeToPreUpdate(this));
		subscribe("postRender", () -> BaseMod.subscribeToRender(this));
		subscribe("postUpdate", () -> BaseMod.subscribeToRender(this));
		subscribe("preMonsterTurn", () -> BaseMod.subscribeToPreUpdate(this));
		loudWrite(writer, "End subscribing to hooks");
	}
	
	public static void initialize() {
		logger.info("========================= TESTMOD INIT =========================");
		
		File logFile = new File(LOG_FILE);
		try {
			if (writer == null) {
				writer = new BufferedWriter(new FileWriter(logFile));
			}
		} catch (IOException e) {
			couldNotCrash("CREATE LOG FILE", e);
		}
		
		@SuppressWarnings("unused")
		TestMod test = new TestMod();
		
		logger.info("================================================================");
	}
	
	//
	// Subscriptions
	//

	@Override
	public boolean receivePostCreateStartingDeck(ArrayList<String> addCardsToMe) {
		// add one copy of Transmutation, Panacea, and Panache
		// to the starting deck
		addCardsToMe.add("Transmutation");
		addCardsToMe.add("Panacea");
		addCardsToMe.add("Panache");
		// do not overwrite the starting deck
		return false;
	}

	@Override
	public boolean receivePostCreateStartingRelics(ArrayList<String> addRelicsToMe) {
		// add one Snecko Eye, and Velvet Choker
		// to the starting relics
		addRelicsToMe.add("Snecko Eye");
		addRelicsToMe.add("Velvet Choker");
		// do not overwrite the starting relics
		return false;
	}

	@Override
	public void receiveStartGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveStartAct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveRender(SpriteBatch sb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePreUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePreStartGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean receivePreMonsterTurn(AbstractMonster m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void receivePostUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePostRender(SpriteBatch sb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePostInitialize() {
        try {
        	// setup ModBadge for TestMod
            Texture badgeTexture = new Texture(Gdx.files.internal("img/BaseModBadge.png"));
            ModPanel settingsPanel = new ModPanel();
            settingsPanel.addLabel("Test Mod - no settings", 400.0f, 700.0f, (me) -> {});
            BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        } catch (Exception e) {
        	couldNotCrash("CREATE MOD BADGE", e);
        }
		
       	try {
            // setup Robot testing
			driver = new Robot();
			
			// begin Robot testing
			beginDriving();
		} catch (AWTException e) {
			couldNotCrash("CREATE ROBOT DRIVER", e);
		}
	}

	@Override
	public void receivePostEnergyRecharge() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePostDungeonInitialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePostDraw(AbstractCard c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveCreateShopRelics(ArrayList<StoreRelic> relics, ShopScreen screenInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveCreateShopPotions(ArrayList<StorePotion> potions, ShopScreen screenInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean receivePostCampfire() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void receiveEditStrings() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveEditRelics() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveEditCharacters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveEditCards() {
		// TODO Auto-generated method stub
		
	}
}
