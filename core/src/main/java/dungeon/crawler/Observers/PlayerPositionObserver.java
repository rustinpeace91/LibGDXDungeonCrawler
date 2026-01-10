package dungeon.crawler.Observers;

import dungeon.crawler.Player.PlayerDirection;

public interface PlayerPositionObserver {
	void onDirectionChange(PlayerDirection newDirection);
}
