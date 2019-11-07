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

    public Population genChildPopByRoulette(){
        Population res = new Population();
        res.setGenePoolSize(this.getGenePoolSize());

        while(res.getSize() < this.getSize()){
//            int Min = 0;
//            int Max = res.getSize()-1;
//            int random1 = Min + (int)(Math.random() * ((Max - Min) + 1));
//            int random2 = Min + (int)(Math.random() * ((Max - Min) + 1));
//            System.out.println("rand1 : " + random1 + "\nrand2 : " + random2);
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
