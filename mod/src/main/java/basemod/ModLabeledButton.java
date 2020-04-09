package basemod;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import java.util.function.Consumer;

public class ModLabeledButton implements IUIElement {
    private static final float HB_SHRINK = 14.0F;
    private Consumer<ModLabeledButton> click;
    private Hitbox hb;
    private Texture texture;
    private float x;
    private float y;
    private float w;
    private float h;
    private String label;
    public ModPanel parent;
    public Color color, colorHover;
    public BitmapFont font;

    // this "sinks" the text to the sides - i.e. makes the middle part of button a bit shorter than the text width,
    // looks a bit more balanced to the eye
    private static final float TEXT_OFFSET = 9F;

    private Texture textureLeft, textureRight, textureMiddle;

    public ModLabeledButton(String label, float xPos, float yPos, ModPanel p, Consumer<ModLabeledButton> c) {
        this.label = label;
        this.textureLeft = ImageMaster.loadImage("img/ButtonLeft.png");
        this.textureRight = ImageMaster.loadImage("img/ButtonRight.png");
        this.textureMiddle = ImageMaster.loadImage("img/ButtonMiddle.png");
        this.font = FontHelper.buttonLabelFont;
        this.x = xPos * Settings.scale;
        this.y = yPos * Settings.scale;
        this.w = (this.textureLeft.getWidth() + this.textureRight.getWidth() - TEXT_OFFSET * 2) * Settings.scale +
                FontHelper.getSmartWidth(font, label, 9999f, 0f);
        this.h = (float)this.textureLeft.getHeight() * Settings.scale;
        this.hb = new Hitbox(this.x + 1F * Settings.scale, this.y + 1F * Settings.scale, this.w - 2F * Settings.scale, this.h - 2F * Settings.scale);
        this.parent = p;
        this.click = c;
        this.color = Color.WHITE;
        this.colorHover = Color.GREEN;
    }

    public void render(SpriteBatch sb) {
        float text_width = FontHelper.getSmartWidth(this.font, this.label, 9999f, 0.0f) -
                (2 * TEXT_OFFSET) * Settings.scale;
        text_width = Math.max(0, text_width);

        sb.draw(this.textureLeft, this.x, this.y, this.textureLeft.getWidth() * Settings.scale, this.h);
        sb.draw(this.textureMiddle, this.x + this.textureLeft.getWidth() * Settings.scale, this.y, text_width, this.h);
        sb.draw(this.textureRight, this.x + this.textureLeft.getWidth() * Settings.scale + text_width, this.y,
                this.textureRight.getWidth() * Settings.scale, this.h);

        this.hb.render(sb);

        sb.setColor(Color.WHITE);
        if (this.hb.hovered)
            FontHelper.renderFontCentered(sb, this.font, this.label, this.hb.cX, this.hb.cY, this.colorHover );
        else
            FontHelper.renderFontCentered(sb, this.font, this.label, this.hb.cX, this.hb.cY, this.color);
    }

    public void update() {
        this.hb.update();
        if (this.hb.justHovered) {
            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
        }

        if (this.hb.hovered && InputHelper.justClickedLeft) {
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
            this.hb.clickStarted = true;
        }

        if (this.hb.clicked) {
            this.hb.clicked = false;
            this.onClick();
        }

    }

    private void onClick() {
        this.click.accept(this);
    }

    public int renderLayer() {
        return 1;
    }

    public int updateOrder() {
        return 1;
    }
}
