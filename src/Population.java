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
