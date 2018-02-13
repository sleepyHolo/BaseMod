package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="initializeStarterRelics", paramtypes={"com.megacrit.cardcrawl.characters.AbstractPlayer$PlayerClass"})
public class PostInitializeStarterRelicsHookSwitch {
	@SpireInsertPatch(loc=271, localvars={"relics"})
    public static void Insert(Object mObj, PlayerClass chosenClass, @ByRef Object relicsObj) {
    	AbstractPlayer me = (AbstractPlayer) mObj;
    	@SuppressWarnings("unchecked")
		ArrayList<String> theRelics = (ArrayList<String>) relicsObj;
    	if (!chosenClass.toString().equals("IRONCLAD") && !chosenClass.toString().equals("THE_SILENT") &&
				!chosenClass.toString().equals("CROWBOT")) {
        	theRelics = BaseMod.getStartingRelics(me.chosenClass.toString());
        } else {
        	BaseMod.publishPostCreateStartingRelics(me.chosenClass, theRelics);
        }
    }
}
