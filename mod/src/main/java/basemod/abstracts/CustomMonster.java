package basemod.abstracts;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.RunicDome;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static com.badlogic.gdx.graphics.GL20.GL_ONE;
import static com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA;
import static com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA;

public abstract class CustomMonster extends AbstractMonster
{
	protected AbstractAnimation animation;

	public CustomMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY)
	{
		super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
	}

	public CustomMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY, boolean ignoreBlights)
	{
		super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY, ignoreBlights);
	}

	public CustomMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl)
	{
		super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl);
	}

	@Override
	public void render(SpriteBatch sb)
	{
		if (!isDead && !escaped) {
			if (animation != null && animation.type() == AbstractAnimation.Type.SPRITE) {
				animation.renderSprite(sb, drawX + animX, drawY + animY + AbstractDungeon.sceneOffsetY);
			} else if (atlas == null) {
				sb.setColor(tint.color);
				sb.draw(
						img,
						drawX - img.getWidth() * Settings.scale / 2.0f + animX,
						drawY + animY + AbstractDungeon.sceneOffsetY,
						img.getWidth() * Settings.scale,
						img.getHeight() * Settings.scale,
						0,
						0,
						img.getWidth(),
						img.getHeight(),
						flipHorizontal,
						flipVertical
				);
			} else {
				state.update(Gdx.graphics.getDeltaTime());
				state.apply(skeleton);
				skeleton.updateWorldTransform();
				skeleton.setPosition(drawX + animX, drawY + animY + AbstractDungeon.sceneOffsetY);
				skeleton.setColor(tint.color);
				skeleton.setFlip(flipHorizontal, flipVertical);
				sb.end();
				CardCrawlGame.psb.begin();
				AbstractMonster.sr.draw(CardCrawlGame.psb, skeleton);
				CardCrawlGame.psb.end();
				sb.begin();
				sb.setBlendFunction(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			}
			if (this == AbstractDungeon.getCurrRoom().monsters.hoveredMonster && atlas == null && animation == null) {
				sb.setBlendFunction(GL_SRC_ALPHA, GL_ONE);
				sb.setColor(new Color(1, 1, 1, 0.1f));
				sb.draw(
						img,
						drawX - img.getWidth() * Settings.scale / 2.0f + animX,
						drawY + animY + AbstractDungeon.sceneOffsetY,
						img.getWidth() * Settings.scale,
						img.getHeight() * Settings.scale,
						0,
						0,
						img.getWidth(),
						img.getHeight(),
						flipHorizontal,
						flipVertical
				);
				sb.setBlendFunction(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			}

			if (!isDying && !isEscaping && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
					&& !AbstractDungeon.player.isDead && !AbstractDungeon.player.hasRelic(RunicDome.ID)
					&& intent != Intent.NONE && !Settings.hideCombatElements) {
				renderIntentVfxBehind(sb);
				renderIntent(sb);
				renderIntentVfxAfter(sb);
				renderDamageRange(sb);
			}

			hb.render(sb);
			intentHb.render(sb);
			healthHb.render(sb);
		}
		if (!AbstractDungeon.player.isDead) {
			renderHealth(sb);
			renderName(sb);
		}
	}

	@SpireOverride
	protected void renderDamageRange(SpriteBatch sb)
	{
		SpireSuper.call(sb);
	}

	@SpireOverride
	protected void renderIntentVfxBehind(SpriteBatch sb)
	{
		SpireSuper.call(sb);
	}

	@SpireOverride
	protected void renderIntent(SpriteBatch sb)
	{
		SpireSuper.call(sb);
	}

	@SpireOverride
	protected void renderIntentVfxAfter(SpriteBatch sb)
	{
		SpireSuper.call(sb);
	}

	@SpireOverride
	protected void renderName(SpriteBatch sb)
	{
		SpireSuper.call(sb);
	}
}
