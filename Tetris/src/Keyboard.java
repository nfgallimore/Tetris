
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nickgallimore
 */
public class Keyboard extends KeyAdapter
{

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode()) 
        {

            /*
             * Drop - When pressed, we check to see that the game is not
             * paused and that there is no drop cooldown, then set the
             * logic timer to run at a speed of 25 cycles per second.
             */
            case KeyEvent.VK_S: {
                if (!Tetris.isPaused && Tetris.dropCooldown == 0) {
                    Tetris.logicTimer.setCyclesPerSecond(25.0f);
                }
                break;
            }

            /*
             * Move Left - When pressed, we check to see that the game is
             * not paused and that the position to the left of the current
             * position is valid. If so, we decrement the current column by 1.
             */
            case KeyEvent.VK_A: {
                if (!Tetris.isPaused && Tetris.board.isValidAndEmpty(Tetris.currentType, 
                        Tetris.currentCol - 1, Tetris.currentRow, Tetris.currentRotation)) 
                {
                    Tetris.currentCol--;
                }
                break;
            }

            /*
             * Move Right - When pressed, we check to see that the game is
             * not paused and that the position to the right of the current
             * position is valid. If so, we increment the current column by 1.
             */
            case KeyEvent.VK_D: {
                if (!Tetris.isPaused && Tetris.board.isValidAndEmpty(Tetris.currentType, 
                        Tetris.currentCol + 1, Tetris.currentRow, Tetris.currentRotation)) 
                {
                    Tetris.currentCol++;
                }
                break;
            }

            /*
             * Rotate Anticlockwise - When pressed, check to see that the game is not paused
             * and then attempt to rotate the piece anticlockwise. Because of the size and
             * complexity of the rotation code, as well as it's similarity to clockwise
             * rotation, the code for rotating the piece is handled in another method.
             */
            case KeyEvent.VK_Q: {
                if (!Tetris.isPaused) {
                    Tetris.rotatePiece((Tetris.currentRotation == 0) ? 3 : Tetris.currentRotation - 1);
                }
                break;
            }

            /*
             * Rotate Clockwise - When pressed, check to see that the game is not paused
             * and then attempt to rotate the piece clockwise. Because of the size and
             * complexity of the rotation code, as well as it's similarity to anticlockwise
             * rotation, the code for rotating the piece is handled in another method.
             */
            case KeyEvent.VK_E: {
                if (!Tetris.isPaused) {
                    Tetris.rotatePiece((Tetris.currentRotation == 3) ? 0 : Tetris.currentRotation + 1);
                }
                break;
            }

            /*
             * Pause Game - When pressed, check to see that we're currently playing a game.
             * If so, toggle the pause variable and update the logic timer to reflect this
             * change, otherwise the game will execute a huge number of updates and essentially
             * cause an instant game over when we unpause if we stay paused for more than a
             * minute or so.
             */
            case KeyEvent.VK_P: {
                if (!Tetris.isGameOver && !Tetris.isNewGame) {
                    Tetris.isPaused = !Tetris.isPaused;
                    Tetris.logicTimer.setPaused(Tetris.isPaused);
                }
                break;
            }

            /*
             * Start Game - When pressed, check to see that we're in either a game over or new
             * game state. If so, reset the game.
             */
            case KeyEvent.VK_ENTER: {
                if (Tetris.isGameOver || Tetris.isNewGame) {
                    Tetris.resetGame();
                }
                break;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            Tetris.logicTimer.setCyclesPerSecond(Tetris.gameSpeed);
            Tetris.logicTimer.reset();
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }
    
}
