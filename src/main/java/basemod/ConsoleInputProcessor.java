package basemod;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import java.awt.event.KeyEvent;

public class ConsoleInputProcessor implements InputProcessor {

	public static final long FIRST_WAIT = 700; // 1000 MS required before starting repeating
	public static final long REPEAT_INTERVAL = 300; // 400 MS interval for
													// repeating a key
	private int keyDown;
	private boolean isKeyDown;

	public ConsoleInputProcessor() {
		isKeyDown = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		keyDown = keycode;
		isKeyDown = true;
		RepeaterThread repeater = new RepeaterThread();
		repeater.start();
		return true;
	}

	private class RepeaterThread extends Thread {

		@Override
		public void run() {
			processKey(keyDown);
			try {
				Thread.sleep(FIRST_WAIT);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while (isKeyDown) {
				processKey(keyDown);
				try {
					Thread.sleep(REPEAT_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void processKey(int keycode) {
		switch (keycode) {
		case Keys.BACKSPACE: {
			if (DevConsole.currentText.length() > 0) {
				DevConsole.currentText = DevConsole.currentText.substring(0, DevConsole.currentText.length() - 1);
				return;
			} else {
				return;
			}
		}
		case Keys.ENTER: {
			if (DevConsole.currentText.length() > 0) {
				DevConsole.execute();
			}
			return;
		}
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		isKeyDown = false;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// For some reason the validation procedure thinks uppercase D is
		// invalid
		// uppercase K as well
		if (character == 'D' || character == 'K') {
			DevConsole.currentText += character;
			return true;
		}

		Character.UnicodeBlock block = Character.UnicodeBlock.of(character);

		boolean badBlock = (block == null || block == Character.UnicodeBlock.SPECIALS);
		boolean isToggle = (character == DevConsole.toggleKey);
		boolean isControl = (Character.isISOControl(character) || character == KeyEvent.CHAR_UNDEFINED);
		if (badBlock || isToggle || isControl) {
			return false;
		}

		DevConsole.currentText += character;
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}