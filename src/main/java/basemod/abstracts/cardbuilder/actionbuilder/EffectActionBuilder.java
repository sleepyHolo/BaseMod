package basemod.abstracts.cardbuilder.actionbuilder;

import java.util.function.Supplier;

import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class EffectActionBuilder extends ApplyPowerActionBuilder {
	
	public static enum Effect {
		STRENGTH, DEXTERITY, VULNERABLE, WEAK, FRAIL
	}
	
	private static ApplyPowerActionBuilder.Function buildPower(Effect type) {
		return (card, player, monster, amount) -> {
			switch (type) {
			case STRENGTH:
				return new StrengthPower(player, amount);
			case DEXTERITY:
				return new DexterityPower(player, amount);
			case FRAIL:
				return new FrailPower(player, amount, false);
			case VULNERABLE:
				return new VulnerablePower(player, amount, false);
			case WEAK:
				return new WeakPower(player, amount, false);
			}
			return null;
		};
	}
	
	private static Supplier<String> effectName(Effect type) {
		return () -> {
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
		};
	}
	
	public EffectActionBuilder(Effect type) {
		super(buildPower(type), effectName(type));		
	}
	
	public EffectActionBuilder(Effect type, int amount, int upgradedAmount) {
		super(amount, upgradedAmount, buildPower(type), effectName(type));
	}
	
}