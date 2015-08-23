
import processing.core.PApplet;

import java.util.Map.Entry;

/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Tron extends PApplet{

    private TronUnivers tron;
    private int ratio = 20;
    private int margeX = 170;
    private int margeY = 200;

    public static void main(String args[]) {
        System.out.println("main");
        PApplet.main(new String[] {"Tron" });
    }

    public void settings() {
        size(900, 800);
        tron = new TronUnivers();
    }

    public void setup(){
        background(0);
        smooth();
        pushMatrix();
            translate(margeX,margeY);
            displayGraph();
        popMatrix();
    }

    public void draw(){

    }

    private void displayGraph() {
       tron.getGraph().getNodes().forEach((key, value) -> displayNode(value));
    }

    private void displayNode(Node node) {
        float r = random(255);
        float g = random(255);
        float b = random(255);
        fill(r,g,b,20);
        stroke(r,g,b,75);
        strokeWeight(5);
        ellipse(node.getX() * ratio, node.getY() * ratio, 8, 8);
        node.getEdges()
                .forEach(e ->
                    line(e.getNodeA().getX()*ratio, e.getNodeA().getY()*ratio
                       , e.getNodeB().getX()*ratio, e.getNodeB().getY()*ratio));
    }

}
