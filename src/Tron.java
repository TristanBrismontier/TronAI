import processing.core.PApplet;

/**
 * Created by Tristan on 23/08/2015.
 */
public class Tron extends PApplet{

    public static void main(String args[]) {
        System.out.println("main");
        PApplet.main(new String[] {"Tron" });
    }

    public void settings() {
        size(900, 800);
    }

    public void setup(){
        background(255);
        smooth();
        rect(50,50,50,50);
    }

    public void draw(){

    }
}
