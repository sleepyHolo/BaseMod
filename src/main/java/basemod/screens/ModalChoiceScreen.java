package basemod.screens;

import basemod.helpers.ModalChoice;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.InputHelper;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.List;

public class ModalChoiceScreen
{
    @SpireEnum
    public static AbstractDungeon.CurrentScreen MODAL_CHOICE;

    private static final Logger logger = LogManager.getLogger(ModalChoiceScreen.class.getName());
    private static float PAD_X;
    private static Vector2[] points;
    private static final Color ARROW_COLOR = new Color(1.0f, 0.2f, 0.3f, 1.0f);
    private static Method updateReticle;
    public List<AbstractCard> cardGroup;
    private String header;
    private ModalChoice.ModalChoiceCallback callback;
    private boolean nestedOpen = false;
    private AbstractCard draggingCard;
    private float arrowX;
    private float arrowY;
    private AbstractCreature hoveredCreature;

    static {
        points = new Vector2[20];
        for (int i=0; i<points.length; ++i) {
            points[i] = new Vector2();
        }

        try {
            updateReticle = AbstractCreature.class.getDeclaredMethod("updateReticle");
            updateReticle.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void open(List<AbstractCard> cards, String header, ModalChoice.ModalChoiceCallback callback)
    {
        PAD_X = 40.0f * Settings.scale;
        nestedOpen = true;
        draggingCard = null;

        this.callback = callback;
        AbstractDungeon.topPanel.unhoverHitboxes();
        cardGroup = cards;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = MODAL_CHOICE;
        this.header = header;
        AbstractDungeon.dynamicBanner.appear(header);
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.showBlackScreen();
        placeCards(Settings.WIDTH / 2.0f, Settings.HEIGHT * 0.45f);
    }

    public void reopen()
    {
        AbstractDungeon.screen = MODAL_CHOICE;
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.dynamicBanner.appear(header);
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update()
    {
        if (draggingCard == null) {
            cardSelectUpdate();
        } else {
            AbstractDungeon.player.update();
            AbstractDungeon.getCurrRoom().monsters.update();
            updateTargetting();
        }
    }

    private void cardSelectUpdate()
    {
        AbstractCard hoveredCard = null;
        for (AbstractCard c : cardGroup) {
            c.update();
            c.updateHoverLogic();
            if (c.hb.justHovered) {
                CardCrawlGame.sound.playV("CARD_OBTAIN", 0.4f);
            }
            if (c.hb.hovered) {
                hoveredCard = c;
            }
        }

        if (hoveredCard != null && InputHelper.justClickedLeft) {
            hoveredCard.hb.clickStarted = true;
        //}
        //if (hoveredCard != null && hoveredCard.hb.clicked) {
            //hoveredCard.hb.clicked = false;
            nestedOpen = false;
            // TODO: Targetting logic
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.hideBlackScreen();
            draggingCard = hoveredCard;
            GameCursor.hidden = true;
            draggingCard.untip();
            arrowX = InputHelper.mX;
            arrowY = InputHelper.mY;
            /*
            hoveredCard.use(AbstractDungeon.player, null);
            if (!nestedOpen) {
                AbstractDungeon.screen = AbstractDungeon.CurrentScreen.CARD_REWARD;
                AbstractDungeon.closeCurrentScreen();
            }
            */
        }
    }

    public void updateTargetting()
    {
        if (InputHelper.justClickedRight) {
            CardCrawlGame.sound.play("UI_CLICK_2");
            draggingCard = null;
            GameCursor.hidden = false;
            AbstractDungeon.dynamicBanner.appear(header);
            AbstractDungeon.overlayMenu.proceedButton.hide();
            AbstractDungeon.overlayMenu.showBlackScreen();
            return;
        }

        hoveredCreature = null;
        AbstractDungeon.player.hb.update();
        if (AbstractDungeon.player.hb.hovered && !AbstractDungeon.player.isDying) {
            hoveredCreature = AbstractDungeon.player;
        }
        if (hoveredCreature == null) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                m.hb.update();
                if (m.hb.hovered && !m.isDying && !m.isEscaping && m.currentHealth > 0) {
                    hoveredCreature = m;
                    break;
                }
            }
        }

        if (hoveredCreature != null && InputHelper.justReleasedClickLeft) {
            InputHelper.justReleasedClickLeft = false;
        }
    }

    public void render(SpriteBatch sb)
    {
        if (draggingCard == null) {
            renderCardReward(sb);
        } else {
            renderHoverReticle(sb);
            renderTargetting(sb);
        }
    }

    private void renderTargetting(SpriteBatch sb)
    {
        arrowX = MathHelper.mouseLerpSnap(arrowX, InputHelper.mX);
        arrowY = MathHelper.mouseLerpSnap(arrowY, InputHelper.mY);

        AbstractCard cardInUse = AbstractDungeon.player.cardInUse;

        Vector2 controlPoint = new Vector2(cardInUse.current_x - (arrowX - cardInUse.current_x) / 4.0f,
                arrowY + (arrowY - cardInUse.current_y) / 2.0f);
        // TODO
        float arrowScale;
        if (hoveredCreature == null) {
            arrowScale = Settings.scale;
            sb.setColor(Color.WHITE);
        } else {
            arrowScale = Settings.scale;
            sb.setColor(ARROW_COLOR);
        }

        Vector2 tmp = new Vector2(controlPoint.x - arrowX, controlPoint.y - arrowY);
        tmp.nor();

        drawCurvedLine(sb, new Vector2(cardInUse.current_x, cardInUse.current_y), new Vector2(arrowX, arrowY), controlPoint);

        sb.draw(ImageMaster.TARGET_UI_ARROW, arrowX - 128.0f, arrowY - 128.0f, 128.0f, 128.0f, 256.0f, 256.0f, arrowScale, arrowScale,
                tmp.angle() + 90.0f, 0, 0, 256, 256, false, false);
    }

    private void renderHoverReticle(SpriteBatch sb)
    {
        switch (draggingCard.target) {
            case ENEMY:
                if (hoveredCreature != null) {
                    hoveredCreature.renderReticle(sb);
                }
                break;
            case ALL_ENEMY:
                AbstractDungeon.getCurrRoom().monsters.renderReticle(sb);
                break;
            case SELF:
                AbstractDungeon.player.renderReticle(sb);
                break;
            case SELF_AND_ENEMY:
                AbstractDungeon.player.renderReticle(sb);
                if (hoveredCreature != null) {
                    hoveredCreature.renderReticle(sb);
                }
                break;
            case ALL:
                AbstractDungeon.player.renderReticle(sb);
                AbstractDungeon.getCurrRoom().monsters.renderReticle(sb);
                break;
        }
    }

    private void renderCardReward(SpriteBatch sb)
    {
        for (AbstractCard c : cardGroup) {
            c.render(sb);
        }
        for (AbstractCard c : cardGroup) {
            c.renderCardTip(sb);
        }
    }

    private void placeCards(float x, float y)
    {
        float spacing = AbstractCard.IMG_WIDTH;
        if (cardGroup.size() <= 5) {
            spacing += PAD_X;
        } else if (cardGroup.size() > 6){
            spacing -= PAD_X;
        }
        for (int i=0; i<cardGroup.size(); ++i) {
            cardGroup.get(i).target_y = y;

            cardGroup.get(i).target_x = Settings.WIDTH / 2.0f;
            int posOffset = i - (cardGroup.size() / 2);
            cardGroup.get(i).target_x += posOffset * spacing;

            if (cardGroup.size() % 2 == 0) {
                // if even, shift everything one half spacing
                cardGroup.get(i).target_x += (spacing / 2.0f);
            }
        }

        for (AbstractCard c : cardGroup) {
            c.drawScale = 0.75f;
            c.targetDrawScale = 0.75f;
            c.current_x = x;
            c.current_y = y;
        }
    }

    private static void drawCurvedLine(SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control)
    {
        float radius = 7.0F * Settings.scale;

        for (int i = 0; i < points.length - 1; i++) {
            points[i] = ((Vector2)com.badlogic.gdx.math.Bezier.quadratic(points[i], i / 20.0F, start, control, end, new Vector2()));
            radius += 0.4F * Settings.scale;

            float angle;

            if (i != 0) {
                Vector2 tmp = new Vector2(points[(i - 1)].x - points[i].x, points[(i - 1)].y - points[i].y);
                angle = tmp.nor().angle() + 90.0F;
            } else {
                Vector2 tmp = new Vector2(control.x - points[i].x, control.y - points[i].y);
                angle = tmp.nor().angle() + 270.0F;
            }

            sb.draw(ImageMaster.TARGET_UI_CIRCLE, points[i].x - 64.0F, points[i].y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, radius / 18.0F, radius / 18.0F, angle, 0, 0, 128, 128, false, false);
        }
    }
}
