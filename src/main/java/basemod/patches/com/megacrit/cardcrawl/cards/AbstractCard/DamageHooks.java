	package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.BaseMod;

public class DamageHooks {

	@SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method="calculateCardDamage")
	public static class CalculateCardDamage {
		
		@SpireInsertPatch(rloc=7, localvars={"tmp"})
		public static void Insert(Object __obj_instance, Object monster, @ByRef float[] tmp) {
			AbstractCard c = (AbstractCard) __obj_instance;
			AbstractMonster mo = (AbstractMonster) monster;
			AbstractPlayer player = AbstractDungeon.player;
			tmp[0] = BaseMod.calculateCardDamage(player, mo, c, tmp[0]);
		}
		
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method="calculateCardDamage")
	public static class CalculateCardDamageMulti {
		
		@SpireInsertPatch(rloc=77, localvars={"tmp"})
		public static void Insert(Object __obj_instance, Object monster, float[] tmp) {
			AbstractCard c = (AbstractCard) __obj_instance;
			AbstractMonster mo = (AbstractMonster) monster;
			AbstractPlayer player = AbstractDungeon.player;
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = BaseMod.calculateCardDamage(player, mo, c, tmp[i]);
			}
		}
		
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method="applyPowers")
	public static class ApplyPowers {
		
		@SpireInsertPatch(rloc=18, localvars={"tmp"})
		public static void Insert(Object __obj_instance, @ByRef float[] tmp) {
			AbstractCard c = (AbstractCard) __obj_instance;
			System.out.println("apply powers called for: " + c.cardID + " with damage " + tmp[0]);
			AbstractPlayer player = AbstractDungeon.player;
			tmp[0] = BaseMod.calculateCardDamage(player, c, tmp[0]);
		}
		
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method="applyPowers")
	public static class ApplyPowersMulti {
		
		@SpireInsertPatch(rloc=59, localvars={"tmp"})
		public static void Insert(Object __obj_instance, float[] tmp) {
			AbstractCard c = (AbstractCard) __obj_instance;
			System.out.println("apply powers called for: " + c.cardID + " with damage " + tmp[0]);
			AbstractPlayer player = AbstractDungeon.player;
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = BaseMod.calculateCardDamage(player, c, tmp[i]);
			}
		}
		
	}
	
	@SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method="applyPowers")
	public static class ApplyPowersAlways {
		
		public static void Prefix(Object __obj_instance) {
			AbstractCard c = (AbstractCard) __obj_instance;
			System.out.println("apply powers called for: " + c.cardID);
		}
	}
	
}
