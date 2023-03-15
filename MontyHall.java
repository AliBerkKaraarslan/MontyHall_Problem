//Simulates MontyHall Problem.
//There are 3 doors(or another number),and there is a car behind one of the doors.
//Rest of the doors has goats behind themselves.Game asks player to choose one door.
//After the selection, game reveals one of the goat doors (The one that not selected).
//Therefore,there are only 2 doors left. Finally, game asks user if she wants to change her door to other one.
//(26.12.2022) Ali Berk Karaarslan

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MontyHall {
    private boolean[][] doorArray;
    private int chosenDoor=-1;
    private int numberOfDoors=-1;
    private String changeOption="yes";
    private String automateOption="false";
    private static int gamesPlayed=0;
    private static int gamesWon=0;

    Scanner scan= new Scanner(System.in);
    Random generator= new Random();

    //Starts game and ask if user wants it to play it manual or automate it
    public void startGame(){
        System.out.println("Welcome to MontyHall Problem!");
        System.out.println();
        System.out.print("How many doors do you want to play? ");
        numberOfDoors= scan.nextInt();
        scan.nextLine();
        System.out.print("Do you want to automate it?(yes or no) ");
        automateOption =scan.nextLine();
        System.out.println();

        if(automateOption.equals("yes")){
            startAutomaticGame();

        }else{
            startManuelGame();
        }

    }
    //Automatic game
    private void startAutomaticGame(){
        System.out.print("How many times do you want to automate simulation: ");
        int simNumber=scan.nextInt();
        scan.nextLine();

        System.out.println("Which option do you want to automate?");
        System.out.println("[1]Always change the selection");
        System.out.println("[2]Never change the selection");
        System.out.println("[3]Randomize");
        int simOption=scan.nextInt();
        scan.nextLine();

        if(simOption==1){
            changeOption="yes";
        }
        if(simOption==2){
            changeOption="no";
        }

        //Simulates the game in given number
        for(int i=0;i<simNumber;i++){

            //Game chooses change the door or stay at the old door randomly
            if(simOption==3){
                if(generator.nextInt(2)==0){
                    changeOption="no";
                }else{
                    changeOption="yes";
                }
            }
            makeDoors();
            randomDoorSelect();
            revealDoors();
            //printDoors();
            askChange();
            //printDoors();
            checkResult();
        }
    }
    //User, plays it himself
    private void startManuelGame(){
        makeDoors();
        printDoors();
        askDoors();
        revealDoors();
        printDoors();
        askChange();
        printDoors();
        checkResult();
        askReplay();
    }
    //Randomly chooses one door for automatic game.(User choice simulation)
    private void randomDoorSelect(){
        chosenDoor=generator.nextInt(numberOfDoors)+1;
        //System.out.println(chosenDoor);
        doorArray[chosenDoor-1][1]=true;
    }
    //Makes doors and fills it false. After that chooses one car door.
    private void makeDoors(){
        doorArray = new boolean[numberOfDoors][3];

        //Fills the door array. First array is for door number. Second one is for variables
        //First variable is "Car Door". Second one is "User Choice". Third One is "Revealed". booleans.
        for(int i =0;i<doorArray.length;i++){
            doorArray[i][0]=false;
            doorArray[i][1]=false;
            doorArray[i][2]=false;
        }
        doorArray[generator.nextInt(numberOfDoors)][0]=true;
    }

    //Checks games played and won. Then prints them
    private void checkResult(){
        if((doorArray[chosenDoor-1][0]==true)&&(doorArray[chosenDoor-1][1]==true)){
            gamesWon++;
        }
        gamesPlayed++;
        System.out.println("Games Played So Far: "+gamesPlayed);
        System.out.println("Games Won So Far: "+gamesWon);
        System.out.println("Win rate: "+"%"+((double)gamesWon/gamesPlayed)*100);
        System.out.println();

    }

    //Asks user to play it again. If yes recalls start manuel game
    private void askReplay(){
        System.out.print("Do you want to play again?(yes or no) ");
        String replay = scan.nextLine();

        if(replay.equals("yes")){
            startManuelGame();
        }
    }
    //Chooses n-2 goat doors and reveals them.
    private void revealDoors(){
        ArrayList<Integer> goatDoors = new ArrayList<Integer>();
        System.out.println("These doors are revealed: ");
        //Finds goat doors
        for(int i=0;i<doorArray.length;i++){
            if((doorArray[i][0]==false)&&(doorArray[i][1]==false)){
                goatDoors.add(i);
            }
        }

        for(int i=0;i<doorArray.length-2;i++){
            doorArray[goatDoors.get(i)][2]=true;
        }
    }
    //Asks user to choose one door to open
    private void askDoors(){
        System.out.print("Choose a door: ");
        chosenDoor= scan.nextInt();
        scan.nextLine();
        doorArray[chosenDoor-1][1]=true;
    }

    //Asks user to change the selected door.
    private void askChange(){
        if(!automateOption.equals("yes")) {
            System.out.print("Do you want to change your door?(yes or no): ");
            changeOption = scan.nextLine();
        }

            for(int i=0;i<doorArray.length;i++){
                if((doorArray[i][2]==false)&&(doorArray[i][1]==false)) {
                    if (changeOption.equals("yes")) {
                        doorArray[i][1] = true;
                        doorArray[i][2] = true;
                        doorArray[chosenDoor - 1][1] = false;
                        doorArray[chosenDoor - 1][2] = true;
                        chosenDoor=i+1;
                        break;
                    }else{
                        doorArray[i][2]=true;
                        doorArray[chosenDoor-1][2]=true;
                    }
                }
        }
    }
    //Prints the doors
    public void printDoors(){

        for(int i=0; i<doorArray.length;i++){
            /*System.out.print(doorArray[i][0]+" ");
            System.out.print(doorArray[i][1]+" ");
            System.out.print(doorArray[i][2]+" ");*/

            //Prints the revealed car door
            if((doorArray[i][0]==true)&&(doorArray[i][2]==true)) {
                System.out.print("\uD83D\uDE97 ");
            }
            //Prints the revealed goat door
            else if((doorArray[i][0]==false)&&(doorArray[i][2]==true)){
                System.out.print("\uD83D\uDC10 ");
            }
            //Prints unrevealed doors
            else{
                System.out.print("\uD83D\uDEAA ");
            }
        }
        System.out.println();
    }
}
