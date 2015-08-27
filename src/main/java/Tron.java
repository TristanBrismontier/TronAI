
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Tron
 * Created by Tristan Brismontier on 23/08/2015.
 */
public class Tron extends PApplet{

    private TronUniverse tron;
    private int ratio = 25;
    private int margeX = 90;
    private int margeY = 60;
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
        size(900, 600);
        initGraph();
    }

    private void initGraph() {
        tron = new TronUniverse();
        player = tron.getGraph().getNodes().get(new Node((int)random(30),(int)random(20)));
        opponent = tron.getGraph().getNodes().get(new Node((int)random(30),(int)random(20)));
        System.err.println(player);
        players.clear();
        oppos.clear();
        players.add(player);
        oppos.add(opponent);
        tron.getGraph().addOpponent(opponent);
    }

    public void setup(){
        smooth();
        frameRate(50);
    }
    @Override
    public void keyPressed() {
        initGraph();
    }

    public void draw(){
        background(85, 83, 88);
        pushMatrix();
        translate(margeX, margeY);
            displayGraph();
            displaypath();
        popMatrix();
        if(turn) {
            try {
                oldPlayer = player;
                player = tron.getGraph().getDirection(player,opponent );
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
                opponent =  tron.getGraph().getDirection(opponent, opponent );//neig.stream().filter(node -> !node.isVisited()).findAny().get();
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
        displayPath(players,198, 202 ,183 );
        displayPath(oppos,134 , 203,203 );
        displayPath(new AStar(tron.getGraph(), player, opponent).getPath(),255 ,22,84 );
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
       tron.getGraph().getNodes().forEach((key, value) -> displayNode(value, false));
    }

    private void displayNode(final Node node, boolean path) {
        fill(201, 220,179,80);
        stroke(201, 220,179,80);
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
