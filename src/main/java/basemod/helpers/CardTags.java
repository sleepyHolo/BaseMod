package basemod.helpers;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardTagsField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.commons.lang3.BooleanUtils;

public class CardTags
{
	// Some default tag definitions
	// For Back to Basics, Vampires, Pandora's Box
	public static final String BASIC_STRIKE = "basic_strike";
	public static final String BASIC_DEFEND = "basic_defend";
	// For Perfected Strike
	public static final String STRIKE = "strike";
	// For My True Form
	public static final String FORM = "form";

	public static boolean hasTag(AbstractCard card, String tag)
	{
		return BooleanUtils.isTrue(CardTagsField.tags.get(card).get(tag));
	}

	public static void addTag(AbstractCard card, String tag)
	{
		CardTagsField.tags.get(card).put(tag, true);
	}

	public static void removeTag(AbstractCard card, String tag)
	{
		CardTagsField.tags.get(card).remove(tag);
	}

	public static void toggleTag(AbstractCard card, String tag)
	{
		if (hasTag(card, tag)) {
			removeTag(card, tag);
		} else {
			addTag(card, tag);
		}
	}
}
