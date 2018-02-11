package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import basemod.BaseMod;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="initializeStarterDeck")
public class PostInitializeStarterDeckHook {
    @SpireInsertPatch(loc=246, localvars={"cards"})
    public static void Insert(Object mObj, Object cardsObj) {
    	AbstractPlayer  me = (AbstractPlayer) mObj;
    	ArrayList<String> theCards = (ArrayList<String>) cardsObj;
        BaseMod.publishPostCreateStartingDeck(me.chosenClass, theCards);
    }
}
