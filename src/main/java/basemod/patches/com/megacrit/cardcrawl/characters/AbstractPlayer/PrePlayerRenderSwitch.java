package basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.characters.AbstractPlayer", method="render")
public class PrePlayerRenderSwitch {

	public static void Prefix(Object __obj_instance, SpriteBatch sb) {
		BaseMod.publishAnimationRender(sb);
	}
	
}
