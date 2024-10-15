import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

// ------------------------------------------------------------------
// MyFirstBot
// ------------------------------------------------------------------
// A sample bot original made for Robocode by Mathew Nelson.
// Ported to Robocode Tank Royale by Flemming N. Larsen.
//
// Probably the first bot you will learn about.
// Moves in a seesaw motion, and spins the gun around at each end.
// ------------------------------------------------------------------
public class t10_tank extends Bot {
    boolean peek; // Don't turn if there's a bot there
    // The main method starts our bot
    static int count=0 ;
    double moveAmount; // How much to move

    public static void main(String[] args) {
        new t10_tank().start();
    }

    // Constructor, which loads the bot config file
    t10_tank() {
        super(BotInfo.fromFile("t10_tank.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        // Set colors
        setBodyColor(Color.YELLOW);
        setTurretColor(Color.YELLOW);
        setRadarColor(Color.ORANGE);
        setBulletColor(Color.RED);
        setScanColor(Color.CYAN);
        
//        moveAmount = Math.max(getArenaWidth()-50, getArenaHeight()-50);

//        forward(moveAmount);

        // Repeat while the bot is running
        while (isRunning()) {

            peek = true;
            turnRight(40);
            forward(120);
            turnGunRight(360);
            back(20);
            turnGunRight(360);


            peek = false;
            forward(100);
            turnRight(90);
            back(20);

             // Tell the game that when we take move, we'll also want to turn right... a lot
             setTurnLeft(10_000);
             // Limit our speed to 5
             setMaxSpeed(5);
             // Start moving (and turning)
             forward(10_000);


        }
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        count++;
        if (count == 3){
            fire(2);
            count = 0;
        }
     
    if (peek) {
        rescan();
        }
    }    
    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate the bearing to the direction of the bullet
        var bearing = calcBearing(e.getBullet().getDirection());

        // Turn 90 degrees to the bullet direction based on the bearing
        turnLeft(90 - bearing);
        forward(30);

    }
    @Override
    public void onHitBot(HitBotEvent e) {
        var direction = directionTo(e.getX(), e.getY());
        var bearing = calcBearing(direction);
        if (bearing > -10 && bearing < 10) {
            fire(2);
        }
        if (e.isRammed()) {
            turnLeft(10);
            turnGunLeft(100);
            forward(30);
            turnGunRight(180);
        }
        
    }
}
