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
//        Population pop = new Population(5, 8);
//        System.out.println(pop);

        // Check function
//        checkAccouplement();
        roulette();

    }

    public static void roulette(){
        Population pop = new Population(5, 9);
        Population popChild = new Population();
        popChild.setGenePoolSize(pop.getGenePoolSize());

        while(popChild.getSize() <= pop.getSize()){
            int Min = 0;
            int Max = pop.getSize()-1;
            int random1 = Min + (int)(Math.random() * ((Max - Min) + 1));
            int random2 = Min + (int)(Math.random() * ((Max - Min) + 1));

            popChild.addIndividual(new Individual(pop.getIndividualAt(random1), pop.getIndividualAt(random2), pop.getGenePoolSize()/2));
            popChild.addIndividual(new Individual(pop.getIndividualAt(random2), pop.getIndividualAt(random1), pop.getGenePoolSize()/2));
            System.out.println(popChild.getSize());
        }

        System.out.println(popChild);
    }

    public static void checkAccouplement(){
        Population pop = new Population(5, 9);

        Individual parent1 = pop.getIndividualAt(0);
        Individual parent2 = pop.getIndividualAt(1);

        Individual child1 = new Individual(parent1, parent2, 7);
        Individual child2 = new Individual(parent2, parent1, 7);

        System.out.println("Parent 1 : " + parent1);
        System.out.println("Parent 2 : " + parent2);
        System.out.println("Child 1 : " + child1);
        System.out.println("Child 2: " + child2);
    }
}
