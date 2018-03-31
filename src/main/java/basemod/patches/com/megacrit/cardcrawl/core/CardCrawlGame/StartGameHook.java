package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.screens.DungeonTransitionScreen;

@SpirePatch(cls="com.megacrit.cardcrawl.core.CardCrawlGame", method="startNewGame")
public class StartGameHook {
	
    @SpireInsertPatch
    public static void Insert(Object __obj_instance) {
        BaseMod.publishStartGame();
    }
    
    public static int[] Locator(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
		Matcher finalMatcher = new Matcher.NewExprMatcher(DungeonTransitionScreen.class.getName());
		
		return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
	}
    
}
