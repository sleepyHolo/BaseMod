package basemod.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.function.Predicate;

public abstract class CustomBottleRelic extends CustomRelic
{
	public Predicate<AbstractCard> isOnCard;

	public CustomBottleRelic(String id, Texture texture, RelicTier tier, LandingSound sfx, Predicate<AbstractCard> isOnCard)
	{
		super(id, texture, tier, sfx);
		this.isOnCard = isOnCard;
	}

	public CustomBottleRelic(String id, Texture texture, RelicTier tier, LandingSound sfx, SpireField<Boolean> isOnCard)
	{
		this(id, texture, tier, sfx, isOnCard::get);
	}

	public CustomBottleRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx, Predicate<AbstractCard> isOnCard)
	{
		super(id, texture, outline, tier, sfx);
		this.isOnCard = isOnCard;
	}

	public CustomBottleRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx, SpireField<Boolean> isOnCard)
	{
		this(id, texture, outline, tier, sfx, isOnCard::get);
	}

	public CustomBottleRelic(String id, String imgName, RelicTier tier, LandingSound sfx, Predicate<AbstractCard> isOnCard)
	{
		super(id, imgName, tier, sfx);
		this.isOnCard = isOnCard;
	}

	public CustomBottleRelic(String id, String imgName, RelicTier tier, LandingSound sfx, SpireField<Boolean> isOnCard)
	{
		this(id, imgName, tier, sfx, isOnCard::get);
	}
}
