package basemod.patches.com.megacrit.cardcrawl.helpers.input.ScrollInputProcessor;

import basemod.interfaces.TextReceiver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.helpers.input.ScrollInputProcessor;

import java.util.ArrayList;

public class TextInput {
    public static String text = "";

    private static final char BACKSPACE = 8;
    private static final char ENTER_DESKTOP = '\r';
    private static final char ENTER_ANDROID = '\n';
    private static final char TAB = '\t';
    //private static final char DELETE = 127;
    //private static final char BULLET = 149;

    private static int currentCharLimit = -1;

    private static ArrayList<TextReceiver> receivers = new ArrayList<>();

    public static boolean isTextInputActive()
    {
        return receivers.size() > 0;
    }
    public static void startTextReceiver(TextReceiver t)
    {
        receivers.remove(t); //if it's already in the list, remove it first, moving it to the top.
        receivers.add(t); //A different data structure would probably be more efficient for this, but this is good enough.................................
        currentCharLimit = t.getCharLimit();
    }
    public static void stopTextReceiver(TextReceiver t)
    {
        receivers.remove(t);

        if (receivers.size() > 0) {
            TextReceiver next = receivers.get(receivers.size() - 1);
            currentCharLimit = next.getCharLimit();
        }
    }

    @SpirePatch(
            clz = ScrollInputProcessor.class,
            method = "keyTyped"
    )
    public static class receiveTyping
    {
        @SpirePrefixPatch
        public static void readKeyboardInput(ScrollInputProcessor __instance, char character)
        {
            if (receivers.isEmpty()) return;

            TextReceiver t = receivers.get(receivers.size() - 1);
            String text = t.getCurrentText();

            // Disallow "typing" most ASCII control characters
            switch (character) {
                case ENTER_ANDROID:
                case ENTER_DESKTOP:
                    if (t.onPushEnter())
                        return;
                case TAB:
                    if (t.onPushTab())
                        return;
                case BACKSPACE:
                    if (t.onPushBackspace())
                        return;
                    break;
                default:
                    if (character < 32) return;
            }

            if (UIUtils.isMac && Gdx.input.isKeyPressed(Input.Keys.SYM)) return;

            boolean backspace = character == BACKSPACE;
            boolean add = t.acceptCharacter(character);

            if (backspace && text.length() > 1) {
                t.setText(text.substring(0, text.length() - 1));
                return;
            }
            else if (backspace) {
                t.setText("");
                return;
            }
            if (add) {
                if (currentCharLimit == -1 || text.length() < currentCharLimit) {
                    t.setText(text.concat(String.valueOf(character)));
                }
            }
        }
    }

    @SpirePatch(
            clz = ScrollInputProcessor.class,
            method = "keyDown"
    )
    public static class receiveKeyDown
    {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> onKeyDown(ScrollInputProcessor __instance, int keycode)
        {
            if (receivers.isEmpty())
                return SpireReturn.Continue();

            return SpireReturn.Return(receivers.get(receivers.size() - 1).onKeyDown(keycode));
        }
    }

    @SpirePatch(
            clz = ScrollInputProcessor.class,
            method = "keyUp"
    )
    public static class receiveKeyUp
    {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> onKeyUp(ScrollInputProcessor __instance, int keycode)
        {
            if (receivers.isEmpty())
                return SpireReturn.Continue();

            return SpireReturn.Return(receivers.get(receivers.size() - 1).onKeyUp(keycode));
        }
    }
}
