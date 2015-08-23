
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Tron extends PApplet{

    private TronUnivers tron;
    private int ratio = 25;
    private int margeX = 90;
    private int margeY = 180;
    private Node player;
    private Node oldPlayer = null;
    private Node opponent;
    private Node oldopponent=null;
    private boolean turn = true;

    private List<Node> players = new ArrayList<>();
    private List<Node> oppos = new ArrayList<>();



    public static void main(String args[]) {
        System.out.println("main");
        PApplet.main(new String[]{"Tron"});
    }

    public void settings() {
        size(900, 800);
        initGraph();
    }

    private void initGraph() {
        tron = new TronUnivers();
        player = tron.getGraph().getNodes().get(new Node((int)random(30),(int)random(20)));
        opponent = tron.getGraph().getNodes().get(new Node((int)random(30),(int)random(20)));
        players.clear();
        oppos.clear();
        players.add(player);
        oppos.add(opponent);
    }

    public void setup(){
        smooth();
        frameRate(100);
    }
    @Override
    public void keyPressed() {
        initGraph();
    }

    public void draw(){
        background(0);
        pushMatrix();
        translate(margeX, margeY);
            displayGraph();
            displaypath();
        popMatrix();
        if(turn) {
            try {
                oldPlayer = player;
                player = tron.getGraph().getDirection(player);
                players.add(player);
                if (oldPlayer != null) {
                    tron.getGraph().removeEdge(player, oldPlayer, false);
                }
            } catch (Exception e){

            }
        }else{
            oldopponent = opponent;
            tron.getGraph().addOpponent(opponent);
            try {
                List<Node> neig = tron.getGraph().getNeighbours(opponent);
                Collections.shuffle(neig);
                opponent = neig.stream().filter(node -> !node.isVisited()).findAny().get();
                oppos.add(opponent);
                if(oldopponent!=null){
                    tron.getGraph().removeEdge(opponent, oldopponent, false);
                }
            }catch (Exception e){
               tron.getGraph().restorePath(oppos);
            }
        }
        if(oppos.size() == 0){
            turn = true;
        }else{
            turn = !turn;

        }
    }

    private void displaypath() {
        displayPath(players,true);
        displayPath(oppos,false);
    }

    private void displayPath(List<Node> nodes, boolean p) {
        float b = (p)?0:255;
        float g = (p)?255:0;
        stroke(0,g,b,255);
        strokeWeight(5);
            if(nodes.size()>1){
                Node last = nodes.get(0);
                for (int i = 1; i <nodes.size() ; i++) {
                    line(last.getX()*ratio,last.getY()*ratio,nodes.get(i).getX()*ratio,nodes.get(i).getY()*ratio);
                    last = nodes.get(i);
                }
            }
    }

    private void displayGraph() {
       tron.getGraph().getNodes().forEach((key, value) -> displayNode(value));
    }

    private void displayNode(final Node node) {

        float r = (node.isVisited())?255: node.getR();
        float g = (node.isVisited())?map(node.getG(),0,255,0,50):node.getG();
        float b =(node.isVisited())?map(node.getB(),0,255,0,50):node.getB();
        fill(r, g, b,125);
        stroke(r, g, b,125);
        strokeWeight(3);
        ellipse(node.getX() * ratio, node.getY() * ratio, 6, 6);
        node.getEdges().forEach(e -> displayEdge(node, e));
    }

    private void displayEdge(Node origin, Edge e) {
        Node destination = e.getOther(origin);
        String direction = tron.getGraph().computeDirection(origin, destination);
        if (direction.equals("UP")){
            line(origin.getX()*ratio,origin.getY()*ratio,origin.getX()*ratio,origin.getY()*ratio-(ratio/2));
            return;
        }
        if (direction.equals("DOWN")){
            line(origin.getX()*ratio,origin.getY()*ratio,origin.getX()*ratio,origin.getY()*ratio+(ratio/2));
            return;
        }
        if (direction.equals("LEFT")){
            line(origin.getX()*ratio,origin.getY()*ratio,origin.getX()*ratio-(ratio/2),origin.getY()*ratio);
            return;
        }
        if (direction.equals("RIGHT")){
            line(origin.getX()*ratio,origin.getY()*ratio,origin.getX()*ratio+(ratio/2),origin.getY()*ratio);
            return;
        }
    }

}
