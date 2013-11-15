/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nickgallimore
 */
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;
import static com.leapmotion.leap.Gesture.Type.TYPE_CIRCLE;
import static com.leapmotion.leap.Gesture.Type.TYPE_SWIPE;
import com.leapmotion.leap.Vector;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.SwipeGesture;
import java.awt.AWTException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class LeapListener extends Listener
{
    Tetris tetris;
    
    public LeapListener(Tetris t)
    {
        tetris = t;
        t.mouse = false;
        
    }

    @Override
    public void onInit(Controller controller)
    {
        System.out.println("Initialized");
    }

    @Override
    public void onConnect(Controller controller)
    {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
    }

    @Override
    public void onDisconnect(Controller controller)
    {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    @Override
    public void onExit(Controller controller)
    {
        System.out.println("Exited");
    }
    int counter = 0, count = 0;

    @Override
    public void onFrame(Controller controller)
    {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        
        if (!frame.hands().isEmpty())
        {
            // Get the first hand
            Hand hand = frame.hands().get(0);

            // Check if the hand has any fingers
            FingerList fingers = hand.fingers();
            if (!fingers.isEmpty())
            {

                GestureList gestures = frame.gestures();

                for (int i = 0; i < gestures.count(); i++)
                {
                    Gesture gesture = gestures.get(i);

                    if (TYPE_CIRCLE == gesture.type())
                    {
                        CircleGesture circle = new CircleGesture(gesture);

                        // Calculate clock direction using the angle between circle normal and pointable
                        boolean clockwise = circle.pointable().direction().angleTo(circle.normal()) <= Math.PI / 4;
                        if (clockwise) {
                            count++;
                        }
                        if (count >= 15)
                        {
                            count = 0;
                            Tetris.rotatePiece((Tetris.currentRotation == 0) ? 0 : Tetris.currentRotation - 1);
                        }
                        else if (!clockwise) {
                            counter++;
                        }
                        if (counter >= 15)
                        {
                           counter = 0;
                            Tetris.rotatePiece((Tetris.currentRotation == 3) ? 3 : Tetris.currentRotation + 1);
                        }
                    }
                    if (TYPE_SWIPE == gesture.type())
                    {
                        counter = 0;
                        count = 0;
                        
                        SwipeGesture swipe = new SwipeGesture(gesture);
                            if (swipe.startPosition().getX() <= swipe.direction().getX())
                            {
                                Tetris.currentCol--;
                                return;
                            }
                            else
                            {
                                Tetris.currentCol++;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }


class LeapControl
{

    public static void main(String[] args)
    {
        LeapListener listener = new LeapListener(new Tetris(true));
        Controller controller = new Controller();
        controller.addListener(listener);
        listener.tetris.startGame();
        
        System.out.println("Press enter to quit...");
        
        try
        {
            System.in.read();
        } catch (IOException ex)
        {
            Logger.getLogger(LeapControl.class.getName()).log(Level.SEVERE, null, ex);
        }

        controller.removeListener(listener);

    }
}
