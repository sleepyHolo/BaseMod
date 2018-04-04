package basemod.abstracts.cardbuilder.actionbuilder;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.abstracts.cardbuilder.CardBasic;

public class EffectActionBuilder extends ApplyPowerActionBuilder {
	
	public static enum Effect {
		STRENGTH, DEXTERITY, VULNERABLE, WEAK, FRAIL
	}

	private Effect type;
	
	public EffectActionBuilder(Effect type) {
		super();
		this.type = type;
	}
	
	public EffectActionBuilder(Effect type, int amount, int upgradedAmount) {
		super(amount, upgradedAmount);
		this.type = type;
	}
	
	@Override
	public AbstractPower buildPower(CardBasic card, AbstractPlayer player, AbstractMonster monster) {
		switch (type) {
		case STRENGTH:
			return new StrengthPower(player, getAmount(card));
		case DEXTERITY:
			return new DexterityPower(player, getAmount(card));
		case FRAIL:
			return new FrailPower(player, getAmount(card), false);
		case VULNERABLE:
			return new VulnerablePower(player, getAmount(card), false);
		case WEAK:
			return new WeakPower(player, getAmount(card), false);
		}
		return null;
	}
	
	@Override
	public String effectName() {
		switch (type) {
		case STRENGTH:
			return "Strength";
		case DEXTERITY:
			return "Dexterity";
		case FRAIL:
			return "Frail";
		case VULNERABLE:
			return "Vulnerable";
		case WEAK:
			return "Weak";
		}
		return null;
	}
	
}