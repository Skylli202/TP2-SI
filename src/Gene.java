import java.util.Random;

public class Gene {
    private int value;

    public Gene(){
        // Génère un gène aléatoirement avec la valeur 0 ou 1
        this.value = new Random().nextInt(2);
    }

    public Gene(int value){
        /**
         * Génère un gène avec la valeur passée en paramètre
         * :param value valeur du gène. Elle doit valoir 0 ou 1
         */
        if(value == 0 || value == 1){
            this.value = value;
        } else {
            throw new IllegalArgumentException("Value isn't 0 or 1");
        }
    }

    public void mutate(){
        if(value == 0)
            value = 1;
        else{
            value = 0;
        }
    }

    public int getValue(){
        return this.value;
    }
    public void setValue(int value){
        if(value == 0 || value == 1){
            this.value = value;
        } else {
            throw new IllegalArgumentException("Value isn't 0 or 1");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(this.getValue());
    }
}
