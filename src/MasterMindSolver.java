
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MasterMindSolver {
    public static final int RED = 1;
    public static final int BLUE = 2;
    public static final int GREEN = 3;
    public static final int YELLOW = 4;
    public static final int PURPLE = 5;
    public static final int CYAN = 6;
    
    public static final int WHITE_PEG = 7;
    public static final int BLACK_PEG = 8;

    public ArrayList<int[]> guessesList;
    public ArrayList<int[]> possibles;
    public ArrayList<int[]> pegScoresList;
    
    public MasterMindSolver(){
        guessesList = new ArrayList<>();
        for(int i=1; 6>=i; i++){
            for(int j=1; 6>=j; j++){
                for(int k=1; 6>=k; k++){
                    for(int l=1; 6>=l; l++){
                        int temp[] = new int[4];
                        temp[0] = i;
                        temp[1] = j;
                        temp[2] = k;
                        temp[3] = l;
                        guessesList.add(temp);
                    }   
                }   
            }
        }
        
        possibles = new ArrayList<>();
        for(int i=0; guessesList.size()>i; i++){
            possibles.add(guessesList.get(i));
        }
        
        pegScoresList = new ArrayList<>();
        pegScoresList.add(new int[]{0,0});
        pegScoresList.add(new int[]{0,1});
        pegScoresList.add(new int[]{0,2});
        pegScoresList.add(new int[]{0,3});
        pegScoresList.add(new int[]{0,4});
        pegScoresList.add(new int[]{1,0});
        pegScoresList.add(new int[]{1,1});
        pegScoresList.add(new int[]{1,2});
        pegScoresList.add(new int[]{1,3});
        pegScoresList.add(new int[]{2,0});
        pegScoresList.add(new int[]{2,1});
        pegScoresList.add(new int[]{2,2});
        pegScoresList.add(new int[]{3,0});
        pegScoresList.add(new int[]{3,1});
        pegScoresList.add(new int[]{4,0});
    }
    
    public static int[] rateGuess(int[] guess, int[] answer){
        int[] guessClone = guess.clone();
        int[] answerClone = answer.clone();

        int black=0;
        for(int m=0; 4>m; m++){
            if(guessClone[m] == answerClone[m]){
                guessClone[m]=0;
                answerClone[m]=0;
                black++;
            }
        }

        int white=0;
        for(int m=0; 4>m; m++){
            if(guessClone[m]==0) continue;
            for(int n=0; 4>n; n++){
                if(guessClone[m] == answerClone[n]){
                    guessClone[m]=0;
                    answerClone[n]=0;
                    white++;
                    break;
                }
            }
        }
        
        return new int[]{black, white};
    }
    
    private int[] popNextGuess(){
        int maxIndex=0;
        int maxScore=0;
        
        for(int i=0; guessesList.size()>i; i++){
            int[] guess = guessesList.get(i);
            int minScore=0;
            
            for(int j=0; pegScoresList.size()>j; j++){
                int[] pegScores = pegScoresList.get(j);
                int remNumber=0;
                
                for(int k=0; possibles.size()>k; k++){
                    int rate[] = rateGuess(guess, possibles.get(k));
                    int black = rate[0];
                    int white = rate[1];
                    
                    if(pegScores[0] != black || pegScores[1] != white) remNumber++; 
                }
                
                if(j==0 || remNumber<minScore) minScore=remNumber;
            }
            
            if(i==0 || minScore>maxScore){
                maxIndex=i;
                maxScore=minScore;
            }
        }

        return guessesList.remove(maxIndex);
    }
    
    private void effectPegScore(int[] guess, int[] pegScores){
        int removed=0;
        for(int k=0; possibles.size()>k; k++){
            int rate[] = rateGuess(guess, possibles.get(k));
            int black = rate[0];
            int white = rate[1];
            
            if(pegScores[0] != black || pegScores[1] != white){
                possibles.remove(k);
                removed++;
                k--;
            }
        }
        
        StylishPrinter.print("Removed: ", StylishPrinter.BOLD_RED);
        System.out.print(String.valueOf(removed));
        System.out.print(" | ");
        StylishPrinter.print("Remaining: ", StylishPrinter.BOLD_RED);
        System.out.println(String.valueOf(possibles.size()));
    }
    
    public static int[] inputPegScores(){
        int[] ps = new int[2];
        System.out.print("Enter number of black pegs: ");
        ps[0] = SbproScanner.inputInt(0, 4);
        
        if(ps[0]==4) ps[1]=0;
        else{
            System.out.print("Enter number of white pegs: ");
            ps[1] = SbproScanner.inputInt(0, 4-ps[0]);
        }
        
        return ps;
    }
    
    public static void printGuess(int[] guess){
        for(int i=0; 4>i; i++){
            if(i!=0) System.out.print("-");
            if(guess[i] == RED) StylishPrinter.print("RED", StylishPrinter.BOLD_RED);
            else if(guess[i] == BLUE) StylishPrinter.print("BLUE", StylishPrinter.BOLD_BLUE);
            else if(guess[i] == GREEN) StylishPrinter.print("GREEN", StylishPrinter.BOLD_GREEN);
            else if(guess[i] == YELLOW) StylishPrinter.print("YELLOW", StylishPrinter.BOLD_YELLOW);
            else if(guess[i] == PURPLE) StylishPrinter.print("PURPLE", StylishPrinter.BOLD_PURPLE);
            else if(guess[i] == CYAN) StylishPrinter.print("CYAN", StylishPrinter.BOLD_CYAN);
        }
        System.out.println();
    }
    
    public int[] fiveGuessAlgorithm(){
        StylishPrinter.println("\nMasterMind FiveGuess Algorithm: ",
                StylishPrinter.BOLD_RED, StylishPrinter.BG_YELLOW);
        
        int[] guess = new int[]{1, 1, 2, 2};
        System.out.print("Guess: ");
        printGuess(guess);
        
        while(true){
            int[] pegScores = inputPegScores();
            effectPegScore(guess, pegScores);
            
            if(possibles.size()>1){
                guess = popNextGuess();
                System.out.print("\nGuess: ");
                printGuess(guess);      
            }
            else if(possibles.size()==1){
                int[] answer = possibles.get(0);
                System.out.print("\nAnswer: ");
                printGuess(answer);
                return answer;
            }
            else{
                StylishPrinter.println("\nError! There is not any remaining possible guess.",
                        StylishPrinter.BOLD_RED);
                break;
            }
        }
        
        return null;
    }
}
