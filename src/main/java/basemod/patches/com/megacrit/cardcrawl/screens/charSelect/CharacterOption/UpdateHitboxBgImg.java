package basemod.patches.com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
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
			return new Texture(BaseMod.getPlayerPortrait(chosenClass));
		}
	}
	
}
