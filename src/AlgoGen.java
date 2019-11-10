import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class AlgoGen {

    /*
    But du TP:
    Créer un programme d'algorithmique génétique permettant de calculer la plus haute valeur possible
    pour un entier de N bits.

    1. Générer une population aléatoire
    2. Utiliser la roulette pour sélectionner des individus
    3. Faire se reproduire les individus avec un croisement 1 point
    4. Utiliser les nouveaux individus pour constituer une nouvelle population
    5. Recommencer les étapes 2 à 4 jusqu'à convergence
     */

    /**
     * TODO : Implémenter la détection de la Convergence
     * TODO : Implémenter la mutation (BitFlip)
     */

    public static void main(String[] args) {
//        ConvergenceVisuel("TOURNOI");
        ConvergenceDetected("TOURNOI", 4);

        // Check function
//        checkAccouplement();
//        checkRandom();
//        checkRoulette();
//        checkTournoi();
    }


    // -- FUNCTION
    public static void ConvergenceVisuel(String mode){
        Population populationOriginal = new Population(10, 7);
        System.out.println("*****************\nPopulation de départ : [" + populationOriginal.getSize() + ";" + populationOriginal.getGenePoolSize() +"]\n" + populationOriginal + "*****************");

        Population pop = new Population(populationOriginal);
        boolean run = true;
        while (run) {
            // Generate child pop
            Population childPop;
            if (mode.equals("ROULETTE"))
                childPop = pop.genChildPopByRoulette();
            else if (mode.equals("TOURNOI"))
                childPop = pop.genChildPopByTournoi();
            else // default generation is random
                childPop = pop.genChildPopByRandom();

            // Display childPop
            System.out.println("Child pop :\n" + childPop);

            // Override pop by childPop
            pop = childPop;

            // Ask if convergence is reach
            Scanner scanner = new Scanner(System.in);
            System.out.println("La convergence est-elle atteinte ? (Y/N)");
            String userIntput = scanner.nextLine();
            while(!(userIntput.equalsIgnoreCase("Y") || userIntput.equalsIgnoreCase("N"))){ // making sure that userInput is Y or N ONLY
                System.out.println("Répondez par Y ou N uniquement");
                userIntput = scanner.nextLine();
            }
            if(userIntput.equalsIgnoreCase("Y"))
                run = false;
        }
    }

    public static void ConvergenceDetected(String mode, int convThreshhold){
        Population populationOriginal = new Population(10, 7);
        System.out.println("*****************\nPopulation de départ : [" + populationOriginal.getSize() + ";" + populationOriginal.getGenePoolSize() +"]\n" + populationOriginal + "*****************");

        Population pop = new Population(populationOriginal);

        boolean isConvergenceReached = false;
        int tmp = 0;
        while(!isConvergenceReached){
            // Generate child pop
            Population childPop;
            if (mode.equals("ROULETTE"))
                childPop = pop.genChildPopByRoulette();
            else if (mode.equals("TOURNOI"))
                childPop = pop.genChildPopByTournoi();
            else // default generation is random
                childPop = pop.genChildPopByRandom();

            // Display childPop
            System.out.println("Child pop :\n" + childPop);

            // Compare Max
            if(childPop.getBestIndividual().isEqualTo(pop.getBestIndividual())){
                tmp++;
            } else {
                tmp = 0;
            }

            // Override pop by childPop
            pop = childPop;

            // Detection of Convergence
            if(tmp >= convThreshhold)
                // si convThreshhold population d'affilés ont le même max alors la convergence est atteinte
                isConvergenceReached = true;
        }
        System.out.println("La convergence a été atteinte après " + convThreshhold + " valeur maximal succéssive atteinte.");
    }

    // -- CHECK FUNCTION
    public static void checkAccouplement(){
        Population pop = new Population(6, 9);

        Individual parent1 = pop.getIndividualAt(0);
        Individual parent2 = pop.getIndividualAt(1);

        Individual child1 = new Individual(parent1, parent2, 7);
        Individual child2 = new Individual(parent2, parent1, 7);

        System.out.println("Parent 1 : " + parent1);
        System.out.println("Parent 2 : " + parent2);
        System.out.println("Child 1 : " + child1);
        System.out.println("Child 2 : " + child2);
    }

    public static void checkRandom(){
        Population pop = new Population(6, 9);
        System.out.println(pop + "\n");

        Population popChild = pop.genChildPopByRandom();
        System.out.println(popChild);
    }

    public static void checkRoulette(){
        Population populationOriginal = new Population(10, 7);
        System.out.println("*****************\nPopulation de départ : [" + populationOriginal.getSize() + ";" + populationOriginal.getGenePoolSize() +"]\n" + populationOriginal + "*****************");

        Population childPop = populationOriginal.genChildPopByRoulette();
        System.out.println(childPop);
    }

    public static void checkTournoi(){
        Population populationOriginal = new Population(10, 7);
        System.out.println("*****************\nPopulation de départ : [" + populationOriginal.getSize() + ";" + populationOriginal.getGenePoolSize() +"]\n" + populationOriginal + "*****************");

        Population childPop = populationOriginal.genChildPopByTournoi();
        System.out.println(childPop);
    }
}
