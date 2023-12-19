import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Score points by scanning valuable fish faster than your opponent.
 **/
class Player {

    public static void main(String args[]) {

        Player p = new Player();

        int creatureCount = in.nextInt();
        for (int i = 0; i < creatureCount; i++) {
            int creatureId = in.nextInt();
            int color = in.nextInt();
            int type = in.nextInt();
            if(type==0){
                poissonNonScan0.add(creatureId);
            }else if(type==1){
                poissonNonScan1.add(creatureId);
            }else{
                poissonNonScan2.add(creatureId);
            }
        }

        // game loop
        while (true) {
            p.runDefautParcours();
        }
    }

    private static final Scanner in = new Scanner(System.in);
    private static Set<Integer> poissonNonScan0 = new HashSet<>();
    private static Set<Integer> poissonNonScan1 = new HashSet<>();
    private static Set<Integer> poissonNonScan2 = new HashSet<>();
    private static Map<Integer,Fish> fishs = new HashMap<>();
    private static Map<Integer,String> radars = new HashMap<>();
    private int droneX;
    private int droneY;
    private boolean stop = false;

    public void readInputTurn(){
        int myScore = in.nextInt();
        int foeScore = in.nextInt();
        int myScanCount = in.nextInt();
        System.err.println( "SCANNED COUNT "+ myScanCount);
        for (int i = 0; i < myScanCount; i++) {
            int creatureId = in.nextInt();
            System.err.println( "SCANNED "+ creatureId);
            System.err.println( poissonNonScan0.remove(creatureId));
            System.err.println( poissonNonScan1.remove(creatureId));
            System.err.println( poissonNonScan2.remove(creatureId));
        }
        int foeScanCount = in.nextInt();
        for (int i = 0; i < foeScanCount; i++) {
            int creatureId = in.nextInt();
        }
        int myDroneCount = in.nextInt();
        for (int i = 0; i < myDroneCount; i++) {
            int droneId = in.nextInt();
            this.droneX = in.nextInt();
            this.droneY = in.nextInt();
            int emergency = in.nextInt();
            int battery = in.nextInt();
        }
        int foeDroneCount = in.nextInt();
        for (int i = 0; i < foeDroneCount; i++) {
            int droneId = in.nextInt();
            int droneX = in.nextInt();
            int droneY = in.nextInt();
            int emergency = in.nextInt();
            int battery = in.nextInt();
        }
        int droneScanCount = in.nextInt();
        for (int i = 0; i < droneScanCount; i++) {
            int droneId = in.nextInt();
            int creatureId = in.nextInt();

            System.err.println( "SCANNED "+ creatureId);
            System.err.println( poissonNonScan0.remove(creatureId));
            System.err.println( poissonNonScan1.remove(creatureId));
            System.err.println( poissonNonScan2.remove(creatureId));
        }
        int visibleCreatureCount = in.nextInt();
        for (int i = 0; i < visibleCreatureCount; i++) {
            int creatureId = in.nextInt();
            int creatureX = in.nextInt();
            int creatureY = in.nextInt();
            int creatureVx = in.nextInt();
            int creatureVy = in.nextInt();
            Fish fi = new Player.Fish();
            fi.creatureX = creatureX;
            fi.creatureY = creatureY;
            fi.creatureVx = creatureVx;
            fi.creatureVy = creatureVy;
            fishs.put(creatureId, fi);
        }
        int radarBlipCount = in.nextInt();
        for (int i = 0; i < radarBlipCount; i++) {
            int droneId = in.nextInt();
            int creatureId = in.nextInt();
            String radar = in.next();
            radars.put(creatureId, radar);

        }
    }



    public void run() {
            readInputTurn();
            int myDroneCount = 1; // TODO remove quand 2 drones
            for (int i = 0; i < myDroneCount; i++) {
                if(!poissonNonScan0.isEmpty()){
                    lookHorizontally(radars,poissonNonScan0);
                    return;
                }
                if(!poissonNonScan1.isEmpty()){
                    lookHorizontally(radars,poissonNonScan1);
                    return;
                }
                if(!poissonNonScan2.isEmpty()){
                    lookHorizontally(radars,poissonNonScan2);
                    return;
                }

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");

                System.out.println("MOVE "+this.droneX+" "+0+" 0"); // MOVE <x> <y> <light (1|0)> | WAIT <light (1|0)>

        }
    }

    public void runDefautParcours() {
        readInputTurn();
        int newX = this.droneX;
        int newY = this.droneY;

        if(stop){
            /* remonte */
            newX = droneX;
            newY = 0;
        }else

        /* surface - premiere descente */
        if (droneY < 3300) {
            newX = 800;
            newY = 3300;
        } else

            /* premier pallier 3300*/
            if (droneY < 4800) {
                if (droneX < 9100) {
                    newX = 9200;
                    newY = 3300;
                } else {
                    newX = 9200;
                    newY = 4900;
                }
            } else
                /* deuxieme pallier 4900*/
                if (droneY < 6400) {
                    if (droneX > 800) {
                        newX = 800;
                        newY = 4900;
                    } else {
                        newX = 800;
                        newY = 6500;
                    }
                } else
            /* troisieme pallier 6500*/
        if(droneY<8000){
            if(droneX<9100){
                newX = 9200;
                newY = 6500;
            }else{
                newX = 9200;
                newY = 8100;
            }
        }else
            /* quatrieme pallier 8100*/
            if (droneY < 9600) {
                if (droneX > 800) {
                    newX = 800;
                    newY = 8100;
                } else {
                    newX = 800;
                    newY = 9700;
                }
            }else
                /* cinquieme pallier 9700*/
                    if(droneX<9100){
                        newX = 9200;
                        newY = 9700;
                    }else{
                        newX = droneX;
                        newY = 0;
                        stop = true;
                    }

        System.out.println("MOVE "+newX+" "+newY+" 1");

    }




    public void lookHorizontally( Map<Integer,String> radars, Set<Integer> poissonNonScan){
        Optional<Integer> nextFishId = poissonNonScan.stream().findFirst();
        if(nextFishId.isPresent()){


            String r = radars.get(nextFishId.get());

            int newX = this.droneX ;
            int newY = this.droneY ;

            if(r.contains("T")){
                newY -= 500;
            }else{
                newY += 500;
            }

            if(r.contains("L")){
                newX -= 500;
            }else{
                newX += 500;
            }



            System.out.println("MOVE "+newX+" "+newY+" 0 CHASSE "+nextFishId.get());

        }else{
            System.out.println("WAIT 1"); // MOVE <x> <y> <light (1|0)> | WAIT <light (1|0)>
        }


    }



    public class Fish{
        public int creatureId;
        public int creatureX;
        public int creatureY;
        public int creatureVx;
        public int creatureVy;
    }

}

    