package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="initializeStarterRelics", paramtypes={"com.megacrit.cardcrawl.characters.AbstractPlayer$PlayerClass"})
public class PostInitializeStarterRelicsHook {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpireInsertPatch(loc=271, localvars={"relics"})
    public static void Insert(Object mObj, PlayerClass chosenClass, Object relicsObj) {
    	AbstractPlayer me = (AbstractPlayer) mObj;
    	ArrayList<String> theRelics = (ArrayList<String>) relicsObj;
    	logger.info("Adding relics to player");
        BaseMod.publishPostCreateStartingRelics(me.chosenClass, theRelics);
    }
}
