package dungeon.crawler.Menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import dungeon.crawler.Observers.MenuInputObserver;

public class MenuInputHandler extends InputAdapter{
	public Stage uiStage;
	public BaseMenu menuTable;
	public boolean showMenu = false;

	public int menuColumns = 0;
	public int menuRows = 0;
	public int menuColumnIndex = 0;
	public int menuRowIndex = 0;
	
	private final List<MenuInputObserver> listeners = new ArrayList<>();
	
	public MenuInputHandler(
		Stage uiStage,
		BaseMenu menuTable
	) {
		this.uiStage = uiStage;
		this.menuTable = menuTable;
	}
	
	public void addListener(MenuInputObserver listener) {
		if(listener != null) {
			listeners.add(listener);
		}
	}
	
	public void removeListener(MenuInputObserver listener) {
		if(listener != null) {
			listeners.remove(listener);
		}
	}
	
	public void notifyOnMenuToggled(boolean showMenu) {

        for (MenuInputObserver listener : listeners) {
            listener.onMenuToggled(showMenu);
        }	
	}

	@Override
	public boolean keyDown(int keyCode) {
		if(keyCode == Input.Keys.E && menuTable instanceof Toggleable) {
			showMenu = !showMenu;
			notifyOnMenuToggled(showMenu);
			reinitializeMenu();
			return true;
		}

		if(showMenu) {
			if(keyCode == Input.Keys.ENTER){
				Actor focused = uiStage.getKeyboardFocus();
				if (focused instanceof Button) {
					Gdx.app.log("Button", "BTTTN clicked");
					((Button) focused).toggle(); // Toggles isChecked and fires the listener
				}
			}
			if(keyCode == Input.Keys.DOWN) {
				int nextRow = menuRowIndex + 1;
				if(menuTable.buttonList.size() > nextRow) {
					menuRowIndex = nextRow;
					TextButton currentButton = menuTable.buttonList.get(nextRow);
					uiStage.setKeyboardFocus(currentButton);
				}
			}
			if(keyCode == Input.Keys.UP) {
				int nextRow = menuRowIndex -1;
				if(nextRow >=0) {
					menuRowIndex = nextRow;
					TextButton currentButton = menuTable.buttonList.get(nextRow);
					uiStage.setKeyboardFocus(currentButton);
				}
			}

		}
		return false;
	}
	
	public void reinitializeMenu(){
		menuColumnIndex = 0;
		menuRowIndex = 0;
		uiStage.setKeyboardFocus(menuTable.buttonList.get(0));
	}
	public void setShowMenu(boolean showMenu) {
		this.showMenu = showMenu;
		reinitializeMenu();
	}

}
