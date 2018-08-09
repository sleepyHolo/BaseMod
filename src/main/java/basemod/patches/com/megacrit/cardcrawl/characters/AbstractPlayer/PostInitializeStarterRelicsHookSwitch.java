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
	@SpireInsertPatch(rloc=13, localvars={"relics"})
    public static void Insert(Object mObj, PlayerClass chosenClass, @ByRef ArrayList<String>[] relicsObj) {
    	AbstractPlayer me = (AbstractPlayer) mObj;
		ArrayList<String> theRelics = relicsObj[0];
    	if (!BaseMod.isBaseGameCharacter(chosenClass)) {
        	theRelics = BaseMod.getStartingRelics(me.chosenClass);
        	relicsObj[0] = theRelics;
        } else {
        	BaseMod.publishPostCreateStartingRelics(me.chosenClass, theRelics);
        }
    }
}
