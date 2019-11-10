import java.util.Scanner;

/**
 * Author : Elouan GOUINGUENET
 *          David NAISSE
 *          4A ILC (2019-20) TP2
 */
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
     * WARNING : TOURNOI EST LA SEUL METHODE DE SELECTION QUI DISPOSE DE LA MUTATION
     * TODO : Implémenter la mutation (BitFlip)
     */

    public static void main(String[] args) {
        System.out.println("Elouan GOUINGUENET - David NAISSE");
//      TP2 (K. Naudin)
//        ConvergenceVisuel("TOURNOI");
//        ConvergenceDetected("TOURNOI", 4);

        // Check function
//        checkAccouplement();
//        checkRandom();
//        checkRoulette();
//        checkTournoi();
//        checkMutate();
//        checkMutateInContest();
//        checkCross2Point();
        checkCrossKPoint();


        /**
         * Sujet TP2 K. Naudin
         * Compéter les méthodes laisser vide : Fait.
         * Génération d'une population de Y individus : OK
         * Reproduction a l'aide du système de roulette : OK (cf Population.genChildPopByRoulette())
         * Recommencer jusqu'a l'obtention de la convergence (détecter visuellement) : OK (cf AlgoGen.ConvergenceVisuel(mode))
         * Implémentation de la sélection par tournoi : OK (cf Population.genChildPopByTournoi())
         * Implémentation de la détection de convergence : OK (cf AlgoGen.ConvergenceDetected())
         * Implémentation de la mutation (type : BitFlip) : OK (cf Gene.mutate(), cf Population.genChildPopByTournoi() (sous section // Mutation))
         *
         */


//        TP3 (W. Abdou)
        /**
         * Exercice 1 :
         *
         * 1) L'espace de recherche est de (2^35)-1 soit : 34 359 738 367
         *
         * 2) Roulette : cf méthode Population.genChildPopByRoulette()
         *    Tournoi : cf méthode Population.genChildPopByTournoi()
         *
         * 3) Croisement :
         *      1-point : cf Constructeur Individual(Individual parent1, Individual parent2, int crossPoint)
         *      2-point : cf Constructeur Individual(Individual parent1, Individual parent2, int crossPoint1, int crossPoint2) and AlgoGen.checkCross2Point() for exemple/check
         *      k-point : cf Constructeur Individual(Individual parent1, Individual parent2, int[] crossPoint) TODO
         *
         * 4) Réalisation de la méthode de mutation de type BitFlip : cf Gene.mutate()
         *    Mise en application de la mutation de type BitFlip : cf Population.genChildPopByTournoi() (sous section // Mutation)
         */

        /**
         * Exercice 2 :
         * N = nombre de bits
         * Y = taille de la population
         * TODO : run our program and display the result
         *
         */
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

    public static void checkMutate(){
        Individual indiv = new Individual(10);
        System.out.println(indiv);

        indiv.mutateGenAt(1);
        System.out.println(indiv);
    }

    public static void checkMutateInContest(){
        // UNCOMMENT LISTENER IN genChildPopByTournoi MUTATE SECTION
        Population populationOriginal = new Population(10, 7);
        System.out.println("*****************\nPopulation de départ : [" + populationOriginal.getSize() + ";" + populationOriginal.getGenePoolSize() +"]\n" + populationOriginal + "*****************");

        Population childPop = populationOriginal.genChildPopByTournoi();
        System.out.println(childPop);
    }

    public static void checkCross2Point(){
        Individual parent1 = new Individual(10);
        Individual parent2 = new Individual(10);

        // Display
        System.out.println("Parent 1 : " + parent1 + "\nParent 2 : " + parent2);

        // Create an Individual with Constructor 2crossPoint
        Individual child = new Individual(parent1, parent2, 2, 7);

        // Display
        System.out.println("Child    : " + child);
        /**
         * Expect to get indice 0, 1, 8, 9 from parent 1
         *                      2, ..., 7 from parent 2
         *            Check : OK
         */

    }

    public static void checkCrossKPoint(){
        Individual parent1 = new Individual(new int[]{0, 1, 0, 1});
        Individual parent2 = new Individual(new int[]{0, 0, 1, 1});

        // Display
        System.out.println("Parent 1 : " + parent1 + "\nParent 2 : " + parent2);

        // Create an Individual with Constructor 2crossPoint
        Individual child = new Individual(parent1, parent2, new int[]{1});

        // Display
        System.out.println("Child    : " + child);
    }
}
