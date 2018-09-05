package basemod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

public abstract class ClickableUIElement {

    protected Texture image;
    protected float x;
    protected float y;
    protected float hb_w;
    protected float hb_h;
    protected Hitbox hitbox;
    private boolean clickable;

    public ClickableUIElement(Texture image) {
        this(image, 0,0, 64.0f, 64.0f);
    }

    public ClickableUIElement(Texture image, float x, float y, float hb_w, float hb_h) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.hb_w = hb_w * Settings.scale;
        this.hb_h = hb_h * Settings.scale;
        this.hitbox = new Hitbox(this.hb_w, this.hb_h);
        clickable = true;
    }

    public void update(){

        updateHitbox();

        if(this.hitbox.hovered) {
            onHover();
        }
        if(this.hitbox.hovered && InputHelper.justClickedLeft) {
            if(clickable){
                onClick();
            }
        }

    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public boolean isClickable() {
        return clickable;
    }

    protected void updateHitbox() {
        hitbox.move(x + hb_w / 2, y + hb_h / 2);
        hitbox.update();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        hitbox.render(sb);
        sb.draw(image, x, y, image.getWidth() * Settings.scale, image.getHeight() * Settings.scale);
    }

    protected abstract void onHover();
    protected abstract void onClick();

}
