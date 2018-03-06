package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import basemod.BaseMod;
import basemod.abstracts.CustomCardWithRender;
import basemod.helpers.SuperclassFinder;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class CreateCardImageSwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "createCardImage")
	public static class FixTexturesAndColors {
		
		@SpireInsertPatch(rloc = 41)
		public static void Insert(Object __obj_instance) {
			AbstractCard card = (AbstractCard) __obj_instance;
			CardColor color = card.color;
			if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
					&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
				try {
					Field bgColor;		
					bgColor = SuperclassFinder.getSuperclassField(card.getClass(), "bgColor");
					bgColor.setAccessible(true);
					bgColor.set(card, BaseMod.getBgColor(color.toString()));
						
					Field backColor;
					backColor = SuperclassFinder.getSuperclassField(card.getClass(), "backColor");
					backColor.setAccessible(true);
					backColor.set(card, BaseMod.getBackColor(color.toString()));
						
					Field frameColor = SuperclassFinder.getSuperclassField(card.getClass(), "frameColor");
					frameColor.setAccessible(true);
					frameColor.set(card, BaseMod.getFrameColor(color.toString()));
						
					Field frameOutlineColor = SuperclassFinder.getSuperclassField(card.getClass(), "frameOutlineColor");
					frameOutlineColor.setAccessible(true);
					frameOutlineColor.set(card, BaseMod.getFrameOutlineColor(color.toString()));
						
					Field descBoxColor = SuperclassFinder.getSuperclassField(card.getClass(), "descBoxColor");
					descBoxColor.setAccessible(true);
					descBoxColor.set(card, BaseMod.getDescBoxColor(color.toString()));
					
				} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
					logger.error("could not set card image properties on card " + card.getClass().toString() + " with color " + color.toString());
					logger.error("with exception: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "createCardImage")
	public static class FixLogOutput {
		
		public static ExprEditor Instrument()
		{
		    return new ExprEditor() {
		        @Override
		        public void edit(MethodCall m) throws CannotCompileException
		        {
		            if (m.getMethodName().equals("info")) {
		                m.replace("");
		            }
		        }
		    };
		}
		
	}
	
}
