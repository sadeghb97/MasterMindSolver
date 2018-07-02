public class MasterMind {
    public static void main(String[] args) {
        while(menu());
    }
    
    public static boolean menu(){
        StylishPrinter.println("\nMenu: ", StylishPrinter.BOLD_RED, StylishPrinter.BG_YELLOW);
        System.out.println("1: Solve a new problem");
        System.out.println("2: Exit");
        System.out.print("\nEnter Your Choice: ");
        int choice = SbproScanner.inputInt(1, 2);
        
        if(choice==1) new MasterMindSolver().fiveGuessAlgorithm();
        else return false;
        
        return true;
    }
}
