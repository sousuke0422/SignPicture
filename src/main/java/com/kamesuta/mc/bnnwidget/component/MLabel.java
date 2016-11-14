package com.kamesuta.mc.bnnwidget.component;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import org.apache.commons.lang3.StringUtils;

import com.kamesuta.mc.bnnwidget.WBase;
import com.kamesuta.mc.bnnwidget.WEvent;
import com.kamesuta.mc.bnnwidget.WGui;
import com.kamesuta.mc.bnnwidget.position.Area;
import com.kamesuta.mc.bnnwidget.position.Point;
import com.kamesuta.mc.bnnwidget.position.R;
import com.kamesuta.mc.signpic.render.RenderHelper;

public class MLabel extends WBase {
	protected int textcolor = 14737632;
	protected String text = "";
	protected boolean shadow;
	protected String watermark;
	protected int watermarkcolor = 0x777777;
	protected Align align = Align.CENTER;

	public MLabel(final R position) {
		super(position);
	}

	public MLabel setAlign(final Align align) {
		this.align = align;
		return this;
	}

	public Align getAlign() {
		return this.align;
	}

	public MLabel setWatermark(final String watermark) {
		this.watermark = watermark;
		return this;
	}

	public String getWatermark() {
		return this.watermark;
	}

	public MLabel setWatermarkColor(final int watermark) {
		this.watermarkcolor = watermark;
		return this;
	}

	public int getWatermarkColor() {
		return this.watermarkcolor;
	}

	public MLabel setShadow(final boolean b) {
		this.shadow = b;
		return this;
	}

	public boolean isShadow() {
		return this.shadow;
	}

	public MLabel setColor(final int color) {
		this.textcolor = color;
		return this;
	}

	public int getColor() {
		return this.textcolor;
	}

	public MLabel setText(final String s) {
		if (StringUtils.equals(s, getText()))
			return this;
		final String oldText = getText();
		this.text = s;
		onTextChanged(oldText);
		return this;
	}

	public String getText() {
		return this.text;
	}

	protected void onTextChanged(final String oldText) {
	}

	@Override
	public void draw(final WEvent ev, final Area pgp, final Point p, final float frame, final float popacity) {
		final Area out = getGuiPosition(pgp);
		drawText(out, getGuiOpacity(popacity));
	}

	protected float wscale = 1f;

	public MLabel setScaleWidth(final float f) {
		this.wscale = f;
		return this;
	}

	public float getScaleWidth(final Area a) {
		return this.wscale;
	}

	protected float hscale = 1f;

	public MLabel setScaleHeight(final float f) {
		this.hscale = f;
		return this;
	}

	public float getScaleHeight(final Area a) {
		return this.hscale;
	}

	protected void drawText(final Area a, final float opacity) {
		glPushMatrix();
		glTranslated(a.x1()+a.w()/2, a.y1()+a.h()/2, 0);
		glScaled(getScaleWidth(a), getScaleHeight(a), 1);
		RenderHelper.startTexture();
		final Color c = new Color(getColor());
		final Color c_ = new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) Math.max(4, opacity*c.getAlpha()));
		this.align.drawString(getText(), 0, 0, 0, 0, c_.getRGB(), isShadow());
		if (!StringUtils.isEmpty(getWatermark())&&StringUtils.isEmpty(getText())) {
			final Color w = new Color(getWatermarkColor());
			final Color w_ = new Color(w.getRed(), w.getGreen(), w.getBlue(), (int) Math.max(4, opacity*c.getAlpha()));
			this.align.drawString(getWatermark(), 0, 0, 0, 0, w_.getRGB(), isShadow());
		}
		glPopMatrix();
	}

	public static enum Align {
		LEFT {
			@Override
			public void drawString(final String text, final float x, final float y, final float w, final float h, final int colour, final boolean shadow) {
				WGui.drawString(text, x, y, colour, shadow);
			}
		},
		CENTER {
			@Override
			public void drawString(final String text, final float x, final float y, final float w, final float h, final int colour, final boolean shadow) {
				WGui.drawStringC(text, x, y, w, h, colour, shadow);
			}
		},
		RIGHT {
			@Override
			public void drawString(final String text, final float x, final float y, final float w, final float h, final int colour, final boolean shadow) {
				WGui.drawStringR(text, x+w, y, colour, shadow);
			}
		},
		;
		public abstract void drawString(String text, float x, float y, float w, float h, int colour, boolean shadow);
	}
}