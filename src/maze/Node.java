/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maze;

import java.util.ArrayList;

/**
 *
 * @author pj
 */
public class Node 
{
    private String name;
    private int x;
    private int y;
    private Node node1;
    private Node node2;
    private boolean visited;
    
    public Node(String name, int x, int y)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        visited = false;
    }
    
    /**
     * Method: getNodeLinkers
     * 
     * @return the node links
     */
    public ArrayList<Node> getNodeLinkers() 
    {
        ArrayList<Node> connectNodes = new ArrayList<>();

        if (node1 != null) 
        {
            connectNodes.add(node1);
        }
        if (node2 != null) 
        {
            connectNodes.add(node2);
        }

        return connectNodes;
    }

    /**
     * Method: getName
     * 
     * @return the name of the node
     */
    public String getName() {
        return name;
    }

    /**
     * Method: setName
     * 
     * @param name the name of the node to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method: getX
     * 
     * @return the x position of the node
     */
    public int getX() {
        return x;
    }

    /**
     * Method: setX
     * 
     * @param x the x position of the node to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Method: getY
     * 
     * @return the y position of the node
     */
    public int getY() {
        return y;
    }

    /**
     * Method: setY
     * 
     * @param y the y position of the node to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Method: getNode1
     * 
     * @return the node1
     */
    public Node getNode1() {
        return node1;
    }

    /**
     * Method: setNode1
     * 
     * @param node1 the node1 to set
     */
    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    /**
     * Method: getNode2
     * 
     * @return the node2
     */
    public Node getNode2() {
        return node2;
    }

    /**
     * Method: setNode2
     * 
     * @param node2 the node2 to set
     */
    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    /**
     * Method: isVisited
     * 
     * @return the visited flag
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Method: setVisited
     * 
     * @param visited the highlighted to set
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
