package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="initializeStarterDeck")
public class PostInitializeStarterDeckHookSwitch {
    @SpireInsertPatch(loc=246, localvars={"cards"})
    public static void Insert(Object mObj, @ByRef ArrayList<String>[] cardsObj) {
    	AbstractPlayer me = (AbstractPlayer) mObj;
		ArrayList<String> theCards = cardsObj[0];
        AbstractPlayer.PlayerClass selection = me.chosenClass;
        if (!selection.toString().equals("IRONCLAD") && !selection.toString().equals("THE_SILENT") &&
				!selection.toString().equals("CROWBOT")) {
        	theCards = BaseMod.getStartingDeck(me.chosenClass.toString());
        	cardsObj[0] = theCards;
        } else {
        	BaseMod.publishPostCreateStartingDeck(me.chosenClass, theCards);
        }
    }
}
