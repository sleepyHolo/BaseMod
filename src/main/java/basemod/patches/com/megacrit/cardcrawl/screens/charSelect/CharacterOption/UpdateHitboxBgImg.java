package basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.charSelect.CharacterOption", method="updateHitbox")
public class UpdateHitboxBgImg {

	public static ExprEditor Instrument()
	{
		return new ExprEditor() {
			@Override
			public void edit(FieldAccess f) throws CannotCompileException {
				if (f.getFieldName().equals("bgCharImg") && f.isWriter()) {
					f.replace("$proceed(basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterOption.UpdateHitboxBgImg.getPlayerPortrait($1));");
				}
			}
		};
	}

	public static Texture getPlayerPortrait(Object original)
	{
		AbstractPlayer.PlayerClass chosenClass = CardCrawlGame.chosenCharacter;
		if (BaseMod.isBaseGameCharacter(chosenClass)) {
			return (Texture)original;
		} else {
			return ImageMaster.loadImage(BaseMod.getPlayerPortrait(chosenClass));
		}
	}

	@SpireInsertPatch(
			rloc=64,
			localvars={"pref"}
	)
	public static void Insert(CharacterOption __instance, @ByRef Prefs[] pref)
	{
		if (!BaseMod.isBaseGameCharacter(__instance.c)) {
			System.out.println(BaseMod.playerStatsMap.get(__instance.c).pref);
			pref[0] = BaseMod.playerStatsMap.get(__instance.c).pref;
		}
	}
	
}
