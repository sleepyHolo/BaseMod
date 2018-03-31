package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.helpers.DrawMaster;

import basemod.BaseMod;
import javassist.CannotCompileException;
import javassist.CtBehavior;

@SpirePatch(cls = "com.megacrit.cardcrawl.core.CardCrawlGame", method = "render")
public class RenderHook {
	
	@SpireInsertPatch(localvars = { "sb" })
	public static void Insert(Object __obj_instance, SpriteBatch sb) {
		BaseMod.publishRender(sb);
	}
	
	public static int[] Locator(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
		Matcher finalMatcher = new Matcher.MethodCallMatcher(DrawMaster.class.getName(), "draw");
		
		return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
	}
	
}
