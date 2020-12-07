package ABCPlayerPerformance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ABCPlayerPerformance {
    private static ArrayList<String> teamNames = new ArrayList<>(); // Array List for Tournament team
    private static ArrayList<String[]> players = new ArrayList<>(); // Array List for Players' Details

    // Declare Variables for Team count and Player count
    private static final int TEAM_COUNT = 10;
    private static final int PLAYER_COUNT = 11;

    // Declare Variable for match details
    private static String[][][] matchDetails = new String[4][TEAM_COUNT*PLAYER_COUNT][5];

    public static void main(String[] args) {
        int currentMatch = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Player Performance Recording System please add team data first");

        // Call Initial Data input method
        initialDataInsert();

        while (true){
            System.out.println("\n- Player Performance Recording System -");

            // Give menu options
            System.out.println("A - Add match details ("+currentMatch+"/4)\nP - View all players of a team" +
                    "\nS - Search for a player\nW - View all 3 winners\nM - View match information");
            String option = input.next();

            // Check conditions of Menu options
            if (option.equalsIgnoreCase("A")) { // Condition for Secondary data input
                if (currentMatch>4) {
                    System.out.println("** Data for all 4 matches are filled");
                    continue;
                }

                // Method for secondary data input
                secondaryDataInsert(currentMatch);
                currentMatch++;
            }else if (option.equalsIgnoreCase("P")){ // Condition for Display team details

                // Call method for display team details
                teamPrint();
            }else if (option.equalsIgnoreCase("S")){ // Condition for Display player details

                // Call method for display player details
                playerPrint();

            }else if (option.equalsIgnoreCase("W")){ // Condition for 3 winners of the match

                if(currentMatch==0){
                    System.out.println("** Please fill match data first for at least one match");
                    continue;
                }

                // Call method for display 3 winners of the match
                winnerPrint(currentMatch);

            }else if (option.equalsIgnoreCase("M")){ // Condition for match details (Venue)
                if(currentMatch==0){
                    System.out.println("** Please fill match data first for at least one match");
                    continue;
                }

                // Call method for display match details
                matchDataPrint(currentMatch);
            }
            System.out.println("\n\n"+Arrays.deepToString(matchDetails));
            System.out.println(Arrays.deepToString(players.toArray()));
        }
    }

    // Method for print match details (Venues)
    private static void matchDataPrint(int currentMatch) {
        for (int x=0; x<players.size(); x++) {
            for (int y=0;y<currentMatch;y++) {
                System.out.println(matchDetails[y][x][4]);
            }
        }
    }

    // Method for find 3 winners of the tournament
    private static void winnerPrint(int currentMatch) {

        // Declare Variables
        int bestMarks=0;
        int bestBoundaries=0;
        double bestAverage=0;
        int bestMarksIndex=0;
        int bestBoundariesIndex=0;
        int bestAverageIndex=0;


        for (int x=0; x<players.size(); x++) {
            int tempTot=0;
            int tempBound=0;
            double tempAvg=0.0;
            for (int y=0;y<currentMatch;y++) {
                tempTot += (Integer.parseInt(matchDetails[y][x][0]));
                tempBound += (Integer.parseInt(matchDetails[y][x][1]));
                double curTempAvg = Double.parseDouble(matchDetails[y][x][2]);
                if(tempAvg<curTempAvg){
                    tempAvg = curTempAvg;
                }
            }
            if (bestMarks<tempTot) {
                bestMarks = tempTot;
                bestMarksIndex = x;
            }
            if (bestBoundaries<tempBound) {
                bestBoundaries = tempBound;
                bestBoundariesIndex = x;
            }
            if (bestAverage<tempAvg) {
                bestAverage = tempAvg;
                bestAverageIndex = x;
            }
        }
        String[] player = players.get(bestMarksIndex);
        System.out.println("Best marks - "+bestMarks+" "+player[1]+" "+player[3]+" age "+player[2]+" team "+player[0]);
        player = players.get(bestBoundariesIndex);
        System.out.println("Best boundaries - "+bestBoundaries+" "+player[1]+" "+player[3]+" age "+player[2]+" team "+player[0]);
        player = players.get(bestAverageIndex);
        System.out.println("Best average - "+bestAverage+" "+player[1]+" "+player[3]+" age "+player[2]+" team "+player[0]);
    }

    // Method for Player details print
    private static void playerPrint() {

        // Enter player's name for search option
        Scanner input = new Scanner(System.in);
        System.out.print("Enter player name - ");
        String search = input.nextLine();

        String[] found = null;

        for (int x=0; x<players.size(); x++) {
            if(players.get(x)[1].equalsIgnoreCase(search)) {
                found = players.get(x);
                System.out.println(found[1]+" "+found[3]+" age "+found[2]+" team "+found[0]);
                for(int y=0;y<4;y++){
                    System.out.print("\nMatch "+(y+1)+" details");
                    if(matchDetails[y][x][0]==null) System.out.println(" - Records unavailable");

                    else {
                        System.out.println("\nRuns - " + matchDetails[y][x][0]);
                        System.out.println("Boundaries executed - " + matchDetails[y][x][1]);
                        System.out.println("Strike rate - " + matchDetails[y][x][2]);
                        System.out.println("Important incidents - " + matchDetails[y][x][3]);
                        System.out.println("Venue/match specific information - " + matchDetails[y][x][4]);
                    }
                }
            }
        }
        if(found==null) System.out.println("No players found for "+search);
    }

    // Method for display All players in a team
    private static void teamPrint() {
        int tempIndex;
        Scanner intInput;
        for(int x=0;x<TEAM_COUNT;x++){
            System.out.println((x+1)+" - "+teamNames.get(x));
        }
        while (true) {
            intInput = new Scanner(System.in);
            System.out.print("Enter team index to print - ");
            try {
                tempIndex = intInput.nextInt();
                tempIndex-=1;
                if (tempIndex<0 || tempIndex>=TEAM_COUNT) throw new InputMismatchException();
            }catch (InputMismatchException e){
                System.out.println("** Invalid Team Index");
                continue;
            }
            break;
        }
        for (int x=0;x<PLAYER_COUNT;x++){
            String[] player = players.get(PLAYER_COUNT*tempIndex+x);
            System.out.println(player[1]+" "+player[3]+" age "+player[2]+" team "+player[0]);
        }
    }


    // Method for Initial Data Input
    private static void initialDataInsert() {
        Scanner input = new Scanner(System.in);
        Scanner intInput;
        String teamName;
        String playerName;
        String role;
        int age;
        for (int x=0; x< TEAM_COUNT;){
            System.out.print("Enter school team name ("+x+"/"+TEAM_COUNT+") :- ");
            teamName = input.nextLine();
            if(teamNames.contains(teamName.toUpperCase())){
                System.out.println("** Team Name Already Entered");
                continue;
            }
            teamNames.add(teamName.toUpperCase());
            x++;
            for (int y = 0; y< PLAYER_COUNT; y++){
                System.out.print("Enter Player name ("+y+"/"+PLAYER_COUNT+") :- ");
                playerName = input.nextLine();
                while (true) {
                    intInput = new Scanner(System.in);
                    System.out.print("Enter age :- ");
                    try {
                        age = intInput.nextInt();
                        if (age<5 || age>20) throw new InputMismatchException();
                    }catch (InputMismatchException e){
                        System.out.println("** Age must be a valid number");
                        continue;
                    }
                    break;
                }
                while (true) {
                    intInput = new Scanner(System.in);
                    System.out.println("1 - Captain\n2 - Vice-Captain\n3 - Wicket-Keeper\n4 - Batsman\n5 - Bowler");
                    System.out.print("Enter role index :- ");
                    int temp;
                    try {
                        temp = intInput.nextInt();
                        if (temp<1 || temp>5) throw new InputMismatchException();
                    }catch (InputMismatchException e){
                        System.out.println("** Invalid Role index enter a number from the List");
                        continue;
                    }
                    if (temp==1) role = "Captain";
                    else if (temp==2) role = "Vice-Captain";
                    else if (temp==3) role = "Wicket-Keeper";
                    else if (temp==4) role = "Batsman";
                    else role = "Bowler";
                    break;
                }
                String[] tempArray = {teamName,playerName,String.valueOf(age),role};
                players.add(tempArray);
                System.out.println("> Player "+playerName+" successfully added");
            }
        }
    }

    // Method for Secondary data Input (After the Match)
    private static void secondaryDataInsert(int current) {
        System.out.println("Insert data for match "+current+" out of 4");
        for (int x = 0; x< TEAM_COUNT; x++) {
            System.out.println("Data for team "+players.get(x*PLAYER_COUNT)[0]+"("+x+"/"+TEAM_COUNT+")");

            for (int y=0; y<PLAYER_COUNT;y++){
                Scanner intInput = new Scanner(System.in);
                Scanner input = new Scanner(System.in);
                String[] curPlayer = players.get((x*PLAYER_COUNT)+y);

                System.out.println("Data for player ("+y+"/"+PLAYER_COUNT+") - "+curPlayer[1]+" "+curPlayer[3]);
                System.out.print("Runs scored - ");
                String runs = intInput.next();

                while (!scoreCheck(runs,1)) {
                    System.out.print("Runs scored - ");
                    runs = intInput.next();
                }

                System.out.print("Boundaries executed - ");
                String boundaries = intInput.next();

                while (!scoreCheck(boundaries,1)) {
                    System.out.print("Boundaries executed - ");
                    boundaries = intInput.next();
                }
                System.out.print("Strike rate - ");
                String strikeRate = intInput.next();

                while (!scoreCheck(strikeRate,0)) {
                    System.out.print("Strike rate - ");
                    strikeRate = intInput.next();
                }

                System.out.print("Important incidents - ");
                String specialIncidents = input.nextLine();

                System.out.print("Venue/match specific information - ");
                String specificInfo = input.next();

                matchDetails[current][(x*PLAYER_COUNT)+y] = new String[]{runs,boundaries,strikeRate,specialIncidents,specificInfo};
            }
        }
    }

    // Method for check tournament score
    private static boolean scoreCheck(String score,int opt){
        try {
            if (opt==0) Double.parseDouble(score);
            else Integer.parseInt(score);
        }catch (Exception e){
            System.out.println("** Invalid value");
            return false;
        }
        return true;
    }
}













