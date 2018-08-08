package basemod.helpers;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardTagsField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.commons.lang3.BooleanUtils;

public class CardTags
{
	// Some default tag definitions
	// For Back to Basics, Vampires, Pandora's Box
	public static final String BASIC_STRIKE = "basemod:basic_strike";
	public static final String BASIC_DEFEND = "basemod:basic_defend";
	// For Perfected Strike
	public static final String STRIKE = "basemod:strike";
	// For My True Form
	public static final String FORM = "basemod:form";

	// TODO: Force tags to have form "modid:tag"
	// TODO: Deal with AbstractCard.makeCopy

	public static boolean hasTag(AbstractCard card, String tag)
	{
		return BooleanUtils.isTrue(CardTagsField.tags.get(card).contains(tag));
	}

	public static void addTags(AbstractCard card, String... tags)
	{
		for (String tag : tags) {
			CardTagsField.tags.get(card).add(tag);
		}
	}

	public static void removeTags(AbstractCard card, String... tags)
	{
		for (String tag : tags) {
			CardTagsField.tags.get(card).remove(tag);
		}
	}

	public static void toggleTag(AbstractCard card, String tag)
	{
		if (hasTag(card, tag)) {
			removeTags(card, tag);
		} else {
			addTags(card, tag);
		}
	}
}
