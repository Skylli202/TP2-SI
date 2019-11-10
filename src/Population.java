import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * TODO: Contest with a parameter for contest size
 *
 * */

public class Population {
    // Attribute
    private int size;
    private int genePoolSize;
    private Individual[] population;

    // Constructor
    public Population(){ // Void Constructor
        this.size = 0;
//        this.genePoolSize = 0;
        this.population = new Individual[size];
        initEmptyPop();
    }

    public Population(int size, int genePoolSize){ // Constructor
        /***
         * :param size Taille de la population
         * :param genePoolSize Nombre de gènes par individu
         */
        this.size = size;
        this.genePoolSize = genePoolSize;
        this.population = new Individual[size];
        initEmptyPop();
    }

    public Population(Population p){ // Copy Constructor
        this.size = p.size;
        this.genePoolSize = p.genePoolSize;
        this.population = new Individual[this.size];
        for (int i = 0; i < this.size; i++) {
            this.population[i] = p.population[i];
        }
    }

    // Method
    public Individual getIndividualAt(int i){
        return population[i];
    }

    public Individual getBestIndividual(){
        // Return the best Individual in the population
        Individual res;
        int bestFitness = -1;
        int indice = -1;
        for (int i = 0; i < this.getSize(); i++) {
            if(this.getIndividualAt(i).getFitnessScore() > bestFitness){
                bestFitness = this.getIndividualAt(i).getFitnessScore();
                indice = i;
            }
        }

        if(indice == -1){
            // should be a unreachable code
            throw new IllegalArgumentException("this pop is fucked up with no fitness score greater than -1");
        } else {
            res = new Individual(this.getIndividualAt(indice));
        }
        return res;
    }

    public void addIndividual(Individual i){
        if(this.genePoolSize != i.getNoGenes()){
            throw new IllegalArgumentException("Individual geneSize do not match with population GenePoolSize");
        } else {
            this.size++;
            Individual[] tmp = new Individual[size];
            for (int j = 0; j < this.population.length; j++) {
                tmp[j] = this.population[j];
            }
            tmp[size-1] = i;
            this.population = tmp;
        }
    }

    public int computeFitnessPop(){
        int res = 0;
        for (int i = 0; i < this.size; i++) {
            res += this.getIndividualAt(i).getFitnessScore();
        }
        return res;
    }

    public void initEmptyPop(){
        // Création aléatoire de la population
        for(int i=0; i < this.size; i++)
            this.population[i] = new Individual(this.genePoolSize);
    }

    // Generation Method
    public Population genChildPopByRandom(){
        Population res = new Population();
        res.setGenePoolSize(this.getGenePoolSize());

        while(res.getSize() < this.getSize()){

            Random rand = new Random();
            int random1 = rand.nextInt(this.size);
            int random2 = rand.nextInt(this.size);
//            System.out.println("rand1 : " + random1 + "\nrand2 : " + random2);

            res.addIndividual(new Individual(this.getIndividualAt(random1), this.getIndividualAt(random2), this.getGenePoolSize()/2));
            if(res.getSize()+1 < this.getSize()) // Checking that the pop size of child pop won't be bigger than parent pop one
                res.addIndividual(new Individual(this.getIndividualAt(random2), this.getIndividualAt(random1), this.getGenePoolSize()/2));
        }
        return res;
    }

    public Population genChildPopByRoulette(){
        /**
         * Génération d'une population d'enfant (reproduction de deux parents)
         * Chaque parent a une chance d'être sélectionner proportionnelle a son score de fitness
         * Cours :
         *      p(Si) = f(Si)/fitnessPop
         */

        // Initialize the return variable
        Population res = new Population();
        res.setGenePoolSize(this.getGenePoolSize());

        // Compute the population's fitness
        int popFitness = computeFitnessPop();

        // Fill the child pop until child pop size is equal to parent pop
        while(res.getSize() < this.getSize()){

            // Select two parent
            Individual parent1 = new Individual(), parent2 = new Individual();

            Random rand = new Random();
            int r1 = rand.nextInt(popFitness);
            int r2 = rand.nextInt(popFitness);

            // Find which individual is at r
            int i = 0;
            boolean run = true;
            boolean p1 = false, p2 = false;
            int tmp = 0; // tmp pour temporaire
            while (run) {
                int tmp2 = tmp + this.getIndividualAt(i).getFitnessScore();
                if(r1 > tmp && r1 < tmp2){
                    parent1 = new Individual(this.getIndividualAt(i));
                    p1 = true;
                }
                if(r2 > tmp && r2 < tmp2){
                    parent2 = new Individual(this.getIndividualAt(i));
                    p2 = true;
                }
                tmp = tmp2;
                i++;
                run = !(p1 && p2);
            }

            // Here parent1 and parent2 are found.
//            System.out.println("rand1 : " + r1 + " | rand2 : " + r2);
//            System.out.println("parent1 : " + parent1 + " | parent2 : " + parent2);

            res.addIndividual(new Individual(parent1, parent2, this.getGenePoolSize()/2));
            if(res.getSize()+1 < this.getSize()) // Checking that the pop size of child pop won't be bigger than parent pop one
                res.addIndividual(new Individual(parent2, parent1, this.getGenePoolSize()/2));


        }
        return res;
    }

    public Population genChildPopByTournoi(){
        Population res = new Population();
        res.setGenePoolSize(this.getGenePoolSize());

        /**
         * Tournoi a k participant :
         *      -> Sélectionner k participants
         *      -> Prendre le meilleur de cette sélection : Parent 1
         *      -> Recommencer pour obtenir : Parent 2
         *      -> reproduction des deux parents sélectionner dans le but d'obtenir une nouvelle population
         *
         * Note : Malgré les ressources a notre disposition nous avons des incertitudes sur le travail a réalisé.
         * Nous partirons donc de ce postulat :
         *      -> Le tounoi sélectionne les parents, qui sont ensuite croiser pour générer un enfant qui est ajouté a la nouvelle population généré
         *          -> Et non pas : Le tounoi sélectionne des individues qui sont ajoutés directement dans la population enfant
         *      -> Le vainqueur d'un tournoi n'est pas supprimé de la liste des potentiels participant a un tournoi ultérieurs
         *      -> k sera égale a 2.
         * */

        while(res.getSize() < this.getSize()){
            // Void instantiation
            Individual parent1 = new Individual(), parent2 = new Individual();
            Individual participant1 = new Individual(), participant2 = new Individual();

            // Select k=2 participant for our contest
            Random rand = new Random();
            int random_tournoi1_1 = rand.nextInt(this.getSize());
            int random_tournoi1_2 = rand.nextInt(this.getSize());
            int random_tournoi2_1 = rand.nextInt(this.getSize());
            int random_tournoi2_2 = rand.nextInt(this.getSize());

            // Contest 1
            participant1 = new Individual(this.getIndividualAt(random_tournoi1_1));
            participant2 = new Individual(this.getIndividualAt(random_tournoi1_2));

            if(participant1.isBetterThan(participant2)){
                parent1 = new Individual(participant1);
            } else {
                parent1 = new Individual(participant2);
            }

            // Contest 2
            participant1 = new Individual(this.getIndividualAt(random_tournoi2_1));
            participant2 = new Individual(this.getIndividualAt(random_tournoi2_2));

            if(participant1.isBetterThan(participant2)){
                parent2 = new Individual(participant1);
            } else {
                parent2 = new Individual(participant2);
            }

            // Here parent1 and parent2 are found.
            res.addIndividual(new Individual(parent1, parent2, this.getGenePoolSize()/2));
            if(res.getSize()+1 < this.getSize()) // Checking that the pop size of child pop won't be bigger than parent pop one
                res.addIndividual(new Individual(parent2, parent1, this.getGenePoolSize()/2));

            // Mutation
            /**
             * Uncomment those two listener in order to observe the mutation
             */
            int willMutate = rand.nextInt(100);
            if(willMutate < 15){ // pseudo simulation of 15% chance of mutating
//                System.out.println("--- Mutation ---\nIndiv avant : "+ res.getIndividualAt(res.size-1));
                willMutate = rand.nextInt(res.getGenePoolSize());
                res.getIndividualAt(res.size-1).mutateGenAt(willMutate);
                res.getIndividualAt(res.size-1).computeFitness();
//                System.out.println("Indiv après : "+res.getIndividualAt(res.size-1)+"\n--- -------- ---");
            }
        }

        return res;
    }

    // Accessor
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getGenePoolSize() {
        return genePoolSize;
    }

    public void setGenePoolSize(int genePoolSize) {
        this.genePoolSize = genePoolSize;
    }

    public Individual[] getPopulation() {
        return population;
    }

    public void setPopulation(Individual[] population) {
        this.population = population;
    }

    // Generic Method
    @Override
    public String toString() {
        // Renvoie une String contenant chaque individu avec son score de fitness
        String res = "";
        for(int i=0; i < this.size; i++)
            res += "Individu "+ i +": "+ this.population[i] + "\n";
        return res;
    }
}
