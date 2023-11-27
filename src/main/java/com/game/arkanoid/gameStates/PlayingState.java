package com.game.arkanoid.gameStates;

import com.game.arkanoid.viewControllers.GameViewController;

public class PlayingState implements GameState {
    private final GameViewController gameViewController;

    public PlayingState(GameViewController gameViewController){
        this.gameViewController = gameViewController;
    }

    @Override
    public void startGame() {
        gameViewController.countDownThenStartGame();
    }

    @Override
    public void pauseGame() {
        gameViewController.toggleGamePause();
    }

    @Override
    public void resumeGame() {
        gameViewController.countDownThenStartGame();
    }

    @Override
    public void endGame() {
        gameViewController.gameLoop.stop();
    }
}
