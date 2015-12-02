
import processing.core.PApplet;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Tron extends PApplet{

    private Graph graph;
    private int ratio = 25;
    private int margeX = 90;
    private int margeY = 60;
    private Node player;
    private Node oldPlayer = null;
    private Node opponent;
    private boolean turn = true;

    private List<Node> players = new ArrayList<>();
    private List<Node> oppos = new ArrayList<>();



    public static void main(String args[]) {
        PApplet.main(new String[]{"Tron"});
    }

    public void settings() {
        size(900, 600);
        initGraph();
    }

    private void initGraph() {
        graph = new Graph();
        player = new Node((int)random(29),(int)random(19));
        opponent = new Node((int)random(29),(int)random(19));
        System.err.println(player);
        players.clear();
        oppos.clear();
        players.add(player);
        oppos.add(opponent);
        graph.addOpponent(opponent);
    }

    public void setup(){
        smooth();
        frameRate(20);
    }
    @Override
    public void keyPressed() {
        initGraph();
        loop();
    }

    public void draw(){
        background(85, 83, 88);
        pushMatrix();
        translate(margeX, margeY);
            displayGraph();
            displaypath();
        popMatrix();

        if(turn) {
            movePlayer();
        }else{
            moveOpponent();
        }
        turn=(oppos.size() == 0)|| !turn;
    }

    private void movePlayer() {
        oldPlayer = player;
        graph.addPlayer(player);
        player = graph.getDirection();
        players.add(player);
    }

    private void moveOpponent() {
        List<Node> neig = graph.getNeighbours(opponent);
        Collections.shuffle(neig);
        Optional<Node> opponentNeightbours =  neig.stream().findAny();
        if(!opponentNeightbours.isPresent()){
            noLoop();
            return;
        }
        opponent = opponentNeightbours.get();
        graph.addOpponent(opponent);
        oppos.add(opponent);
    }

    private void displaypath() {
        displayPath(players,198, 202 ,183 );
        displayPath(oppos, 134, 203, 203);
        displayPath(new AStar(graph, player, opponent).getPath(), 255, 22, 84);
    }

    private void displayPath(List<Node> nodes, float r, float g, float b) {
         stroke(r,g,b,255);
         strokeWeight(5);
         if(nodes.size()>1){
             Node last = nodes.get(0);
             for (int i = 1; i <nodes.size() ; i++) {
                 line(last.getX()*ratio,last.getY()*ratio,nodes.get(i).getX()*ratio,nodes.get(i).getY()*ratio);
                 last = nodes.get(i);
             }
         }
    }    private void displayGraph() {
       graph.displayGraph().forEach(node -> displayNode(node));
    }

    private void displayNode(final Node node) {
        if (node.isVisited()){
            fill(255,22,84,80);
            stroke(255,22,84,80);
        }else{
            fill(201,220,179,80);
            stroke(201,220,179,80);
        }
        strokeWeight(3);
        ellipse(node.getX() * ratio, node.getY() * ratio, 6, 6);
    }
}