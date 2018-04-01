package basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.mainMenu.CardLibSortHeader;
import com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen;

import javassist.CannotCompileException;
import javassist.CtBehavior;

/**
 * 
 * @author kioeeht from branch custom-content on ModTheSpire
 * https://github.com/kiooeht/ModTheSpire/tree/custom-content
 *
 */
public class EverythingFix
{
    @SpirePatch(
        cls="com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen",
        method="initialize"
    )
    public static class Initialize
    {
        public static Map<AbstractCard.CardColor, CardGroup> cardGroupMap = new HashMap<>();
        public static Map<AbstractCard.CardColor, CardLibSortHeader> cardHeaderMap = new HashMap<>();

        @SpireInsertPatch
        public static void Insert(Object __obj_instance)
        {
            try {
                CardLibraryScreen screen = (CardLibraryScreen) __obj_instance;

                AbstractCard.CardColor[] colors = AbstractCard.CardColor.values();
                for (int icolor = AbstractCard.CardColor.CURSE.ordinal() + 1; icolor < colors.length; ++icolor) {
                    CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    group.group = CardLibrary.getCardList(CardLibrary.LibraryType.valueOf(colors[icolor].name()));
                    cardGroupMap.put(colors[icolor], group);

                    CardLibSortHeader header = new CardLibSortHeader(group);
                    cardHeaderMap.put(colors[icolor], header);
                    Field headersField = CardLibraryScreen.class.getDeclaredField("headers");
                    headersField.setAccessible(true);
                    @SuppressWarnings("unchecked")
					ArrayList<CardLibSortHeader> headers = (ArrayList<CardLibSortHeader>)headersField.get(screen);
                    headers.add(header);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public static class Locator extends SpireInsertLocator
    	{
    		@Override
    		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
    		{
    			Matcher finalMatcher = new Matcher.MethodCallMatcher("com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen", "calculateScrollBounds");

    			return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
    		}
    	}
    }
    
    @SpirePatch(
    	cls="com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen",
    	method="setLockStatus"
    )
    public static class setLockStatus
    {
    	public static void Postfix(Object __obj_instance)
    	{
    		 try {
                 CardLibraryScreen screen = (CardLibraryScreen) __obj_instance;

                 AbstractCard.CardColor[] colors = AbstractCard.CardColor.values();
                 for (int icolor = AbstractCard.CardColor.CURSE.ordinal() + 1; icolor < colors.length; ++icolor) {
                	 CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                     group.group = CardLibrary.getCardList(CardLibrary.LibraryType.valueOf(colors[icolor].name()));
                     
                     @SuppressWarnings("rawtypes")
                     Class[] cArg = new Class[1];
                     cArg[0] = CardGroup.class;
                     Method lockStatusHelper = screen.getClass().getDeclaredMethod("lockStatusHelper", cArg);
                     lockStatusHelper.setAccessible(true);
                     lockStatusHelper.invoke(screen, group);
                 }
    		 } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
    			 e.printStackTrace();
    		 }
    	}
    }

    @SpirePatch(
        cls="com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen",
        method="calculateScrollBounds"
    )
    public static class CalculateScrollBounds
    {
        @SpireInsertPatch(localvars={"size"})
        public static void Insert(Object __obj_instance, @ByRef int[] size)
        {
            System.out.println(size[0]);
            for (Map.Entry<AbstractCard.CardColor, CardGroup> cards : Initialize.cardGroupMap.entrySet()) {
                size[0] += cards.getValue().size();
            }
            System.out.println(size[0]);
        }
        
        public static class Locator extends SpireInsertLocator
    	{
            private static int[] offset(int[] originalArr, int offset) {
            	int[] resultArr = new int[originalArr.length];
            	for (int i = 0; i < originalArr.length; i++) {
            		resultArr[i] = originalArr[i] + offset;
            	}
            	return resultArr;
            }
        	
    		@Override
    		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
    		{
    			Matcher finalMatcher = new Matcher.FieldAccessMatcher("com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen", "curseCards");

    			// offset by 1 to find line **after** the match
    			return offset(LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher), 1);
    		}
    	}
        
    }
    
    @SpirePatch(
    	cls="com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen",
    	method="sortOnOpen"
    )
    public static class SortOnOpen
    {
    	public static void Postfix(Object __obj_instance)
    	{
             AbstractCard.CardColor[] colors = AbstractCard.CardColor.values();
             for (int icolor = AbstractCard.CardColor.CURSE.ordinal() + 1; icolor < colors.length; ++icolor) {               
                 Initialize.cardHeaderMap.get(colors[icolor]).justSorted = true;
                 CardGroup cardGroup = Initialize.cardGroupMap.get(colors[icolor]);
                 cardGroup.sortAlphabetically(false);
                 cardGroup.sortByRarity(false);
                 cardGroup.sortByStatus(false);
             }
    	}
    }

    @SpirePatch(
        cls = "com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen",
        method = "updateCards"
    )
    public static class UpdateCards
    {
        @SpireInsertPatch(localvars = {"lineNum", "drawStartX", "drawStartY", "padX", "padY", "currentDiffY"})
        public static void Insert(Object __obj_instance, @ByRef int[] lineNum, float drawStartX, float drawStartY, float padX, float padY, float currentDiffY)
        {
            try {
                Field hoveredCard = CardLibraryScreen.class.getDeclaredField("hoveredCard");
                hoveredCard.setAccessible(true);

                ArrayList<AbstractCard> cards;
                CardLibSortHeader header;
                for (Map.Entry<AbstractCard.CardColor, CardGroup> entry : Initialize.cardGroupMap.entrySet()) {
                    cards = entry.getValue().group;
                    header = Initialize.cardHeaderMap.get(entry.getKey());

                    for (int i = 0; i < cards.size(); ++i) {
                        int mod = i % 5;
                        if ((mod == 0) && i != 0) {
                            lineNum[0]++;
                        }
                        cards.get(i).target_x = drawStartX + mod * padX;
                        cards.get(i).target_y = drawStartY + currentDiffY - lineNum[0] * padY;
                        cards.get(i).update();
                        cards.get(i).updateHoverLogic();
                        if (cards.get(i).hb.hovered) {
                            hoveredCard.set(__obj_instance, cards.get(i));
                        }
                    }

                    if (header.justSorted) {
                        for (AbstractCard c : cards) {
                            c.current_x = c.target_x;
                            c.current_y = c.target_y;
                        }
                        header.justSorted = false;
                    }
                    
                    lineNum[0] += 2;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public static class Locator extends SpireInsertLocator
    	{
        	
    		@Override
    		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
    		{
    			Matcher finalMatcher = new Matcher.FieldAccessMatcher("com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen", "colorlessCards");

    			return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
    		}
    	}
        
    }

    @SpirePatch(
        cls="com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen",
        method="render"
    )
    public static class Render
    {
        @SpireInsertPatch
        public static void Insert(Object __obj_instance, Object sbObj)
        {
            try {
                Method renderGroup = CardLibraryScreen.class.getDeclaredMethod("renderGroup", SpriteBatch.class, CardGroup.class, String.class, String.class);
                renderGroup.setAccessible(true);

                for (Map.Entry<AbstractCard.CardColor, CardGroup> cards : Initialize.cardGroupMap.entrySet()) {
                	renderGroup.invoke(__obj_instance, sbObj, cards.getValue(), capitalizeWord(cards.getKey().name()), "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public static class Locator extends SpireInsertLocator
    	{
        	
    		@Override
    		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
    		{
    			Matcher finalMatcher = new Matcher.FieldAccessMatcher("com.megacrit.cardcrawl.screens.mainMenu.CardLibraryScreen", "colorlessCards");

    			return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
    		}
    	}
    }
    
    private static String capitalizeWord(String str)
    {
        if (str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + (str.length() > 1 ? str.substring(1).toLowerCase() : "");
    }
}