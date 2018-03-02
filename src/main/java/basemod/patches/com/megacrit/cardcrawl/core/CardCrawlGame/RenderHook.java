package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import basemod.BaseMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.core.CardCrawlGame", method = "render")
public class RenderHook {
	@SpireInsertPatch(rloc = 49, localvars = { "sb" })
	public static void Insert(Object __obj_instance, SpriteBatch sb) {
		BaseMod.publishRender(sb);
	}
}
