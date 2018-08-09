package basemod.helpers;

import basemod.BaseMod;

public class BaseModTags
{
	private static final String MODID = BaseMod.class.getSimpleName();

	// For Back to Basics, Vampires, Pandora's Box
	@CardTags.AutoTag public static CardTags.Tag BASIC_STRIKE;
	@CardTags.AutoTag public static CardTags.Tag BASIC_DEFEND;
	// For Perfected Strike
	@CardTags.AutoTag public static CardTags.Tag STRIKE;
	// For My True Form
	@CardTags.AutoTag public static CardTags.Tag FORM;
}
