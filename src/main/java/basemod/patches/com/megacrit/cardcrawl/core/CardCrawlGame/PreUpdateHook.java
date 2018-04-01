package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.helpers.InputHelper;

import basemod.BaseMod;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(cls="com.megacrit.cardcrawl.core.CardCrawlGame", method="update")
public class PreUpdateHook {
    @SpireInsertPatch
    public static void Insert(Object __obj_instance) {
        BaseMod.publishPreUpdate();
    }
    
    private static int[] offset(int[] originalArr, int offset) {
    	int[] resultArr = new int[originalArr.length];
    	for (int i = 0; i < originalArr.length; i++) {
    		resultArr[i] = originalArr[i] + offset;
    	}
    	return resultArr;
    }

    public static class Locator extends SpireInsertLocator
	{
		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
		{
			Matcher finalMatcher = new Matcher.MethodCallMatcher(InputHelper.class.getName(), "updateFirst");

			int[] beforeLines = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);

			// offset by 1 to be called **after** the found method call
			return offset(beforeLines, 1);
		}
	}
}
