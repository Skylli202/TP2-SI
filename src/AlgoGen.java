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

    public static void main(String[] args) {
        // Création d'une population de 5 individus comportant 8 gènes
        ConvergenceVisuel();

        // Check function
//        checkAccouplement();
//        checkRoulette();

    }


    // -- FUNCTION
    public static void ConvergenceVisuel(){
        Population populationOriginal = new Population(10, 7);
        System.out.println("*****************\nPopulation de départ : [" + populationOriginal.getSize() + ";" + populationOriginal.getGenePoolSize() +"]\n" + populationOriginal + "*****************");

        Population pop = populationOriginal; // REFERENCE NOT COPY
        pop.addIndividual(new Individual(7));
        System.out.println(pop + "" + populationOriginal);
        boolean run = true;
        while (run) {

        }
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
        System.out.println("Child 2: " + child2);
    }

    public static void checkRoulette(){
        Population pop = new Population(6, 9);
        System.out.println(pop + "\n");

        Population popChild = pop.genChildPopByRoulette();
        System.out.println(popChild);
    }
}
