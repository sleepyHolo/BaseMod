package basemod.helpers;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardTagsField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.commons.lang3.BooleanUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class CardTags
{
	private static final String DELIMITER = ":";

	@Target({ElementType.FIELD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AutoTag {}

	public static abstract class Tag
	{
		protected abstract String getModID();
		protected abstract String getTagName();

		@Override
		public String toString()
		{
			return getModID() + DELIMITER + getTagName();
		}

		@Override
		public boolean equals(Object o)
		{
			if (o == this) {
				return true;
			}
			if (!(o instanceof Tag)) {
				return false;
			}

			Tag tag = (Tag) o;
			return toString().equals(tag.toString());
		}

		@Override
		public int hashCode()
		{
			return toString().hashCode();
		}
	}

	public static class BasicTag extends Tag
	{
		private final String modID;
		private final String tag;

		public BasicTag(String modID, String tag)
		{
			this.modID = modID;
			this.tag = tag;
		}

		@Override
		protected String getModID() {
			return modID;
		}

		@Override
		protected String getTagName() {
			return tag;
		}
	}


	public static boolean hasTag(AbstractCard card, Tag tag)
	{
		return BooleanUtils.isTrue(CardTagsField.tags.get(card).contains(tag));
	}

	public static List<Tag> getAllTags(AbstractCard card)
	{
		return new ArrayList<>(CardTagsField.tags.get(card));
	}

	public static void addTags(AbstractCard card, Tag... tags)
	{
		for (Tag tag : tags) {
			CardTagsField.tags.get(card).add(tag);
		}
	}

	public static void removeTags(AbstractCard card, Tag... tags)
	{
		for (Tag tag : tags) {
			CardTagsField.tags.get(card).remove(tag);
		}
	}

	public static void removeAllTags(AbstractCard card)
	{
		CardTagsField.tags.get(card).clear();
	}

	public static void toggleTag(AbstractCard card, Tag tag)
	{
		if (hasTag(card, tag)) {
			removeTags(card, tag);
		} else {
			addTags(card, tag);
		}
	}
}
