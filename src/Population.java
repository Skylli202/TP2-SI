import java.util.Random;

public class Population {
    private int size;
    private int genePoolSize;
    private Individual[] population;

    public Population(int size, int genePoolSize){
        /***
         * :param size Taille de la population
         * :param genePoolSize Nombre de gènes par individu
         */
        this.size = size;
        this.genePoolSize = genePoolSize;
        this.population = new Individual[size];
        initEmptyPop();
    }

    public Population(){
        this.size = 0;
//        this.genePoolSize = 0;
        this.population = new Individual[size];
        initEmptyPop();
    }

    public Population(Population p){
        this.size = p.size;
        this.genePoolSize = p.genePoolSize;
        this.population = new Individual[this.size];
        for (int i = 0; i < this.size; i++) {
            this.population[i] = p.population[i];
        }
    }

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

    public Individual getIndividualAt(int i){
        return population[i];
    }

    public int computeFitnessPop(){
        int res = 0;
        for (int i = 0; i < this.size; i++) {
            res += this.getIndividualAt(i).getFitnessScore();
        }
        return res;
    }

    public Population genChildPopByRandom(){
        Population res = new Population();
        res.setGenePoolSize(this.getGenePoolSize());

        while(res.getSize() < this.getSize()){

            Random rand = new Random();
            int random1 = (int)rand.nextInt(this.size);
            int random2 = (int)rand.nextInt(this.size);
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
            int r1 = (int) rand.nextInt(popFitness);
            int r2 = (int) rand.nextInt(popFitness);

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

        // Calculate the sum of all fitness of all individual
        int popFitness = 0; // int isn't a problem as a fitness score cannot be a float number



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


    public void initEmptyPop(){
        // Création aléatoire de la population
        for(int i=0; i < this.size; i++)
            this.population[i] = new Individual(this.genePoolSize);
    }

    @Override
    public String toString() {
        // Renvoie une String contenant chaque individu avec son score de fitness
        String res = "";
        for(int i=0; i < this.size; i++)
            res += "Individu "+ i +": "+ this.population[i] + "\n";
        return res;
    }
}
