public class Individual {
    private int noGenes;
    private int fitnessScore;
    private Gene[] genes;

    public Individual(){
        this.noGenes = -1;
        this.fitnessScore = -1;
        this.genes = new Gene[0];
    }

    public Individual(int noGenes){
        /**
         * Créé aléatoirement un individu
         * :param noGenes Nombre de gènes par individu
         */
        this.noGenes = noGenes;
        this.genes = new Gene[noGenes];
        initGenes();
        computeFitness();
    }

    public Individual(Individual i){
        this.noGenes = i.noGenes;
        this.fitnessScore = i.fitnessScore;
        this.genes = new Gene[this.noGenes];
        for (int j = 0; j < noGenes; j++) {
            this.genes[j] = i.genes[j];
        }
    }

    public Individual(int[] i){
        /**
         * Créer un Individue dont les genes sont passées en parametre
         * :param i tableau de la valeur des genes
         */
        this.noGenes = i.length;
        this.genes = new Gene[i.length];
        for (int j = 0; j < i.length; j++) {
            this.genes[j] = new Gene(i[j]);
        }
        computeFitness();
    }

    public Individual(Individual parent1, Individual parent2, int crossPoint){
        /**
         * Créé un invidu à partir de 2 parents
         * :param parent1 Le premier parent
         * :param parent2 Le second parent
         * :param crossPoint le crossPoint
         */
//        int pivot = (this.genes.length/2);
        if(parent1.noGenes != parent2.noGenes){
            throw new IllegalArgumentException("Parents genes length are different");
        } else {
            this.noGenes = parent1.noGenes;
            this.genes = new Gene[noGenes];

            int pivot = crossPoint;
            for (int i = 0; i < parent1.noGenes; i++) {
                if(i < pivot){
                    this.genes[i] = new Gene(parent1.genes[i].getValue());
                } else {
                    this.genes[i] = new Gene(parent2.genes[i].getValue());
                }
            }
        }
        computeFitness();
    }

    public Individual(Individual parent1, Individual parent2, int crossPoint1, int crossPoint2){
        /**
         * Créé un invidu à partir de 2 parents
         * :param parent1 Le premier parent
         * :param parent2 Le second parent
         * :param crosspoint1 le premier crossPoint
         * :param crosspoint2 le second crossPoint
         */
//        int pivot = (this.genes.length/2);
        if(parent1.noGenes != parent2.noGenes){
            throw new IllegalArgumentException("Parents genes length are different");
        } else {
            this.noGenes = parent1.noGenes;
            this.genes = new Gene[noGenes];

            for (int i = 0; i < parent1.noGenes; i++) {
                if(i < crossPoint1 || i > crossPoint2){
                    this.genes[i] = new Gene(parent1.genes[i].getValue());
                } else {
                    this.genes[i] = new Gene(parent2.genes[i].getValue());
                }
            }
        }
        computeFitness();
    }

    public Individual(Individual parent1, Individual parent2, int[] crossPoint){
        /**
         * Créé un invidu à partir de 2 parents
         * :param parent1 Le premier parent
         * :param parent2 Le second parent
         * :param crosspoint contient la liste des k crossPoint
         */
//        int pivot = (this.genes.length/2);
        if(parent1.noGenes != parent2.noGenes){
            throw new IllegalArgumentException("Parents genes length are different");
        } else {
            for (int i = 0; i < crossPoint.length; i++) {
                if(i%2 == 0){
                    for (int j = 0; j < parent1.noGenes; j++) {
                        if(j > crossPoint[i])
                            this.genes[i] = new Gene(parent1.genes[i].getValue());
                    }
                } else {
                    for (int j = 0; j < parent1.noGenes; j++) {
                        if(j > crossPoint[i])
                            this.genes[i] = new Gene(parent2.genes[i].getValue());
                    }
                }
            }
        }
        computeFitness();
    }

    public Gene[] getGenes() {
        return genes;
    }

    public int getFitnessScore() {
        return fitnessScore;
    }

    public int getNoGenes() {
        return noGenes;
    }

    public void initGenes(){
        // Génère aléatoirement les gènes de l'individu
        for(int i = 0; i < this.noGenes; i++)
            this.genes[i] = new Gene();
    }

    public void computeFitness(){
        // Calcule le score de fitness de l'individu à partir de ses gènes
        int cpt = 0; // Will never be a float number
        for (int i = 0; i < this.genes.length; i++) {
            if(this.genes[i].getValue() == 1){
                cpt += Math.pow(2, this.genes.length-1-i);
            }
        }
        this.fitnessScore = cpt;
    }

    @Override
    public String toString() {
        // Renvoie une String contenant les gènes de l'individu et son score de fitness
        // C'est donc à ajouter vu que le score n'est pas calculé par défaut
        // Faudrait pas que je fasse tout le travail non plus

        String res = "[";
        for(int i = 0; i < this.noGenes; i++){
            if(i == this.noGenes-1) {
                res += this.genes[i];
            } else {
                res += this.genes[i] + ";";
            }
        }
        res += "](" + this.fitnessScore + ")";
        return res;
    }

    public boolean isBetterThan(Individual i) {
        // Renvoie true ssi this est meilleur (fitness strictement supérieur) que l'individue passer en parametre
        if(this.fitnessScore > i.fitnessScore)
            return true;
        else
            return false;
    }

    public boolean isEqualTo(Individual i) {
        // return true if this & i do have the same fitness score
        if(this.getFitnessScore() == i.getFitnessScore())
            return true;
        else
            return false;
    }

    public void mutateGenAt(int i) {
        this.genes[i].mutate();
    }
}
