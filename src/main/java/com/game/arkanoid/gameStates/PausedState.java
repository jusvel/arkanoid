package com.game.arkanoid.gameStates;

import com.game.arkanoid.viewControllers.GameViewController;

public class PausedState implements GameState {
    private final GameViewController gameController;

    public PausedState(GameViewController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void startGame() {}

    @Override
    public void pauseGame() {}

    @Override
    public void resumeGame() {
        gameController.countDownThenStartGame();
        gameController.setGameState(new PlayingState(gameController));
    }

    @Override
    public void endGame() {
        gameController.gameLoop.stop();
    }
}