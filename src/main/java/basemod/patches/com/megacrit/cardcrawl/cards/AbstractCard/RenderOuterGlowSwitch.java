package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import basemod.BaseMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "renderOuterGlow")
public class RenderOuterGlowSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpireInsertPatch(loc = 1237)
	public static void Insert(Object __obj_instance, Object sbObj) {
		AbstractCard card = (AbstractCard) __obj_instance;
		CardColor color = card.color;
		SpriteBatch sb = (SpriteBatch) sbObj;
		if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
				&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
			Color glowColor = BaseMod.getGlowColor(color.toString());
			if (glowColor == null) {
				glowColor = Color.WHITE;
			}
			try {
				// use reflection hacks to invoke renderHelper (with float scale)
				Field drawX;
				drawX = card.getClass().getSuperclass().getSuperclass().getDeclaredField("drawX");
				drawX.setAccessible(true);
				Field drawY;
				drawY = card.getClass().getSuperclass().getSuperclass().getDeclaredField("drawY");
				drawY.setAccessible(true);
				Field tintColor;
				tintColor = card.getClass().getSuperclass().getSuperclass().getDeclaredField("tintColor");
				tintColor.setAccessible(true);
				Method renderHelperMethod = card.getClass().getSuperclass().getSuperclass().getDeclaredMethod("renderHelper", SpriteBatch.class,
						Color.class, Texture.class, float.class, float.class, float.class);
				renderHelperMethod.setAccessible(true);
				renderHelperMethod.invoke(card, sb, glowColor, card.getCardBg(),
						drawX.get(card), drawY.get(card), 1.0F + ((Color)tintColor.get(card)).a / 5.0f);
			} catch (Exception e) {
				logger.error("could not render outer glow for card " + card.getClass().toString() + " with color " + color.toString());
			}
		}
	}
}