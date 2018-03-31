package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;


import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.DungeonTransitionScreen;

import basemod.BaseMod;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(cls="com.megacrit.cardcrawl.core.CardCrawlGame", method="update")
public class StartActHook {
	
    @SpireInsertPatch
    public static void Insert(Object __obj_instance) {
    	if (CardCrawlGame.dungeonTransitionScreen.levelName.equals("Exordium")) {
    		BaseMod.publishPreStartGame();
    	}
    	
        BaseMod.publishStartAct();
    }
    
    private static int[] offset(int[] originalArr, int offset) {
    	int[] resultArr = new int[originalArr.length];
    	for (int i = 0; i < originalArr.length; i++) {
    		resultArr[i] = originalArr[i] + offset;
    	}
    	return resultArr;
    }
    
    public static int[] Locator(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
		Matcher finalMatcher = new Matcher.NewExprMatcher(DungeonTransitionScreen.class.getName());
		
		int[] beforeLines = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
		
		// offset by 1 to be called **after** the found method call
		return offset(beforeLines, 1);
	}
    
}