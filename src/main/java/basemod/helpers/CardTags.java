package basemod.helpers;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardTagsField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.commons.lang3.BooleanUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CardTags
{
	private static final String DELIMITER = ":";

	@Deprecated
	@Target({ElementType.FIELD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AutoTag {}

	@Deprecated
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

	@Deprecated
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

	@Deprecated
	private static Map<Tag, AbstractCard.CardTags> oldStyleMap = new HashMap<>();

	static
	{
		oldStyleMap.put(BaseModTags.STRIKE, AbstractCard.CardTags.STRIKE);
		oldStyleMap.put(BaseModTags.BASIC_STRIKE, BaseModCardTags.BASIC_STRIKE);
		oldStyleMap.put(BaseModTags.BASIC_DEFEND, BaseModCardTags.BASIC_DEFEND);
		oldStyleMap.put(BaseModTags.GREMLIN_MATCH, BaseModCardTags.GREMLIN_MATCH);
		oldStyleMap.put(BaseModTags.FORM, BaseModCardTags.FORM);
	}


	@Deprecated
	public static boolean hasTag(AbstractCard card, Tag tag)
	{
		AbstractCard.CardTags realTag = oldStyleMap.get(tag);
		if (realTag == null) {
			return BooleanUtils.isTrue(CardTagsField.tags.get(card).contains(tag));
		}
		return hasTag(card, realTag);
	}

	public static boolean hasTag(AbstractCard card, AbstractCard.CardTags tag)
	{
		return card.hasTag(tag);
	}

	public static List<AbstractCard.CardTags> getAllTags(AbstractCard card)
	{
		return card.tags;
	}

	@Deprecated
	public static void addTags(AbstractCard card, Tag... tags)
	{
		AbstractCard.CardTags[] realTags = Arrays.stream(tags)
				.flatMap(t -> {
					AbstractCard.CardTags realTag = oldStyleMap.get(t);
					if (realTag == null) {
						CardTagsField.tags.get(card).add(t);
						return Stream.empty();
					}
					return Stream.of(realTag);
				})
				.toArray(AbstractCard.CardTags[]::new);

		addTags(card, realTags);
	}

	public static void addTags(AbstractCard card, AbstractCard.CardTags... tags)
	{
		card.tags.addAll(Arrays.asList(tags));
	}

	@Deprecated
	public static void removeTags(AbstractCard card, Tag... tags)
	{
		AbstractCard.CardTags[] realTags = Arrays.stream(tags)
				.flatMap(t -> {
					AbstractCard.CardTags realTag = oldStyleMap.get(t);
					if (realTag == null) {
						CardTagsField.tags.get(card).remove(t);
						return Stream.empty();
					}
					return Stream.of(realTag);
				})
				.toArray(AbstractCard.CardTags[]::new);

		removeTags(card, realTags);
	}

	public static void removeTags(AbstractCard card, AbstractCard.CardTags... tags)
	{
		card.tags.removeAll(Arrays.asList(tags));
	}

	public static void removeAllTags(AbstractCard card)
	{
		card.tags.clear();
	}

	@Deprecated
	public static void toggleTag(AbstractCard card, Tag tag)
	{
		AbstractCard.CardTags realTag = oldStyleMap.get(tag);
		if (realTag == null) {
			if (hasTag(card, tag)) {
				removeTags(card, tag);
			} else {
				addTags(card, tag);
			}
			return;
		}
		toggleTag(card, realTag);
	}

	public static void toggleTag(AbstractCard card, AbstractCard.CardTags tag)
	{
		if (hasTag(card, tag)) {
			removeTags(card, tag);
		} else {
			addTags(card, tag);
		}
	}
}
