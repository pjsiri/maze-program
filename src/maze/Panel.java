/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JPanel;

/**
 *
 * @author pj
 */
public class Panel extends JPanel
{
    private static final int NODE_SIZE = 27;
    private static final int SPACING = 60;
    
    private Maze maze;
    private ArrayList<Node> path;
    private int index;
    private Thread animationThread;
    
    public Panel(Maze maze)
    {
        this.maze = maze;
        animationThread = null;
    }

    /**
     * Method: startPathAnimation
     * 
     * This method is used to start the path animation from the starting node to the exit node. It does this
     * by traversing through the path until it reaches the exit node. It checks whether or not the thread is
     * still running, if so it will do nothing and return. This is important because it ensures that only one
     * animation thread will be created at a time. It finds the path and create a animation thread. It keep looping
     * until the path list is less than the index. This ensures the path is fully traversed. The animation thread is
     * set to pause for every 0.5 seconds.
     */
    public void startPathAnimation() 
    {
        if (animationThread != null && animationThread.isAlive()) 
        {
            return; 
        }

        path = findPath(maze.getFirstNode(), maze.getLastNode());
        index = 0;
        
        animationThread = new Thread(() -> 
        {
            while (index < path.size() - 1)  // Break if the path has been fully traversed
            {
                index++; 
                repaint(); // Repaint to update the visualisation

                try 
                {
                    Thread.sleep(500); // Pause for 0.5 seconds
                } 
                catch (InterruptedException e) 
                {
                    System.out.println("Interrupted Exception.");
                }
            }
        });

        animationThread.start();
    }
    
    /**
     * Method: paintComponent
     * 
     * This method is responsible for painting the maze on the graphics.
     */
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        setBackground(Color.BLACK);
            
        int startX = 130;
        int startY = 65;
        
        // Start node and the exit node
        Node startNode = maze.getFirstNode();
        Node exitNode = maze.getLastNode();
        
        // Find a path from the start to exit node
        ArrayList<Node> pathToExit = findPath(startNode, exitNode);
        
        // Draw the edges
        drawEdges(g, startX, startY);
        
        // Draw the nodes
        drawNodes(g, startX, startY);
        
        // Draw the path by using the pathToExit list
        drawPath(g, pathToExit.subList(0, index + 1), startX, startY);
        
        // Draw the key legend
        drawKey(g);
        
        // Draw the visited nodes of the path
        drawVisitedNodes(g, pathToExit);
    }
    
    /**
     * Method: drawKey
     * 
     * This method is used to draw the key on the top left of the graphics. This is used to give information
     * about the edge colors and their meaning
     */
    private void drawKey(Graphics g) 
    {
        int keyStartX = 10;
        int keyStartY = 10;
        int keySpacing = 20;

        g.setColor(Color.BLUE); // Normal Edge between nodes
        g.drawLine(keyStartX, keyStartY, keyStartX + 20, keyStartY);
        g.setColor(Color.WHITE);
        g.drawString("Path between nodes", keyStartX + 30, keyStartY + 5);

        g.setColor(Color.ORANGE); // Path from starting node to exit node
        g.drawLine(keyStartX, keyStartY + keySpacing, keyStartX + 20, keyStartY + keySpacing);
        g.setColor(Color.WHITE); 
        g.drawString("Path from start to exit", keyStartX + 30, keyStartY + keySpacing + 5);
    }
    
    /**
     * Method: drawVisitedNodes
     * 
     * This method is used to draw all visited nodes from the start to end.
     * 
     */
    private void drawVisitedNodes(Graphics g, List<Node> nodeList) 
    {
        int xPos = 730;
        int yPos = 10;
        int spacing = 20;

        g.setColor(Color.WHITE);
        g.drawString("Path:", xPos, yPos);

        for (int i = 0; i < nodeList.size(); i++) 
        {
            String nodeName = nodeList.get(i).getName();
            g.drawString(nodeName, xPos, yPos + (i + 1) * spacing);
        }
    }

    /**
     * Method: drawEdges
     * 
     * This method is used to draw the edges between each nodes on  graphics object.
     * 
     */
    private void drawEdges(Graphics g, int startX, int startY) 
    {
        g.setColor(Color.BLUE);

        for (Node node : maze.getNodeList()) // Go through the maze node list to find the location of each nodes
        {
            int x1 = startX + node.getX() * (NODE_SIZE + SPACING) + NODE_SIZE / 2; 
            int y1 = startY + node.getY() * (NODE_SIZE + SPACING) + NODE_SIZE / 2; 
            
            Node node1 = node.getNode1(); 
            Node node2 = node.getNode2(); 

            if (node1 != null) 
            {
                int x2 = startX + node1.getX() * (NODE_SIZE + SPACING) + NODE_SIZE / 2;
                int y2 = startY + node1.getY() * (NODE_SIZE + SPACING) + NODE_SIZE / 2;

                g.drawLine(x1, y1, x2, y2);
            }

            if (node2 != null) 
            {
                int x3 = startX + node2.getX() * (NODE_SIZE + SPACING) + NODE_SIZE / 2;
                int y3 = startY + node2.getY() * (NODE_SIZE + SPACING) + NODE_SIZE / 2;

                g.drawLine(x1, y1, x3, y3);
            }
        }
    }
    
    /**
     * Method: drawNodes
     * 
     * This method is used to draw all the nodes shape and node names on the graphics object
     */
    private void drawNodes(Graphics g, int startX, int startY) {
        for (Node node : maze.getNodeList()) {
            int x = startX + node.getX() * (NODE_SIZE + SPACING);
            int y = startY + node.getY() * (NODE_SIZE + SPACING);

            // Draw the node shape
            g.setColor(Color.ORANGE); // Node colour is set to orange
            g.fillOval(x, y, NODE_SIZE, NODE_SIZE);

            // Draw the node name
            g.setColor(Color.BLUE);
            if (node.getName().equals("EXIT")) // If it is the exit node
            {
                g.setColor(Color.RED);
            }
            else if(node.getName().equalsIgnoreCase("start"))
            {
                g.setColor(Color.GREEN);
            }
            
            Font fontName = new Font("Arial", Font.BOLD, 12);
            g.setFont(fontName);
            FontMetrics fm = g.getFontMetrics();
            int stringHeight = fm.getHeight();
            int textX = x - fm.stringWidth(node.getName()) - 3; 
            int textY = y + NODE_SIZE / 2 + stringHeight / 4;
            textY -= 9; // Center the Y coordinate
            g.drawString(node.getName(), textX, textY);
        }
    }

    /**
     * Method: drawPath
     * 
     * This method is used to draw the path from the starting node to the exit node.
     * 
     */
    private void drawPath(Graphics g, List<Node> path, int startX, int startY) 
    {
        g.setColor(Color.ORANGE); // Set the path colour to orange
        
        // Go through the nodes in the path list
        for (int i = 0; i < path.size() - 1; i++) 
        {
            Node currentNode = path.get(i);
            Node nextNode = path.get(i + 1);

            int x1 = startX + currentNode.getX() * (NODE_SIZE + SPACING) + NODE_SIZE / 2;
            int y1 = startY + currentNode.getY() * (NODE_SIZE + SPACING) + NODE_SIZE / 2 ;
            int x2 = startX + nextNode.getX() * (NODE_SIZE + SPACING) + NODE_SIZE / 2;
            int y2 = startY + nextNode.getY() * (NODE_SIZE + SPACING) + NODE_SIZE / 2 ;
            
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * Method: findPath
     * 
     * This method is used to perform breadth-first search by using a queue approach. This method main purpose
     * is to find the path from the start node to the exit node in the maze. Firstly, it creates a ArrayList to
     * store the path from start to the exit node. The map is used to store node links relationship. 
     * The queue is used to perform the breadth-first search. It will begin by adding the start node to the queue.
     * Then, it enter a while loop, where it gets the next node from the queue and assigns it to current node.
     * If the current node name is equal to the exit node name, then it will construct the pathToExit. 
     * Collections.reverse is called to get the path from start to exit.
     * 
     */
    private ArrayList<Node> findPath(Node startNode, Node exitNode) 
    {
        ArrayList<Node> pathToExit = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        HashMap<Node, Node> nodeLinkMap = new HashMap<>();

        queue.offer(startNode);
        startNode.setVisited(true); // Set the start node as visited

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll(); // Get the next node from the queue

            if (currentNode.getName().equalsIgnoreCase(exitNode.getName())) {
                // Reached the exit node, construct the path
                while (currentNode != null) 
                {
                    pathToExit.add(currentNode);
                    currentNode = nodeLinkMap.get(currentNode);
                }
                Collections.reverse(pathToExit); // Used to reverse to return it from start to exit
                break;
            }
            
            // explore the links between the current node
            for (Node nodeLinkerParts : currentNode.getNodeLinkers()) 
            {
                if (!nodeLinkerParts.isVisited()) 
                {
                    nodeLinkerParts.setVisited(true);
                    queue.offer(nodeLinkerParts);
                    nodeLinkMap.put(nodeLinkerParts, currentNode);
                }
            }
        }

        // Reset all the visited flag to false in the node list.
        for (Node node : maze.getNodeList()) 
        {
            node.setVisited(false);
        }
        
        return pathToExit;
    }

    /**
     * Method: getMaze
     * 
     * @return the maze
     */
    public Maze getMaze() 
    {
        return maze;
    }

    /**
     * Method: setMaze
     * 
     * @param maze the maze to set
     */
    public void setMaze(Maze maze) 
    {
        this.maze = maze;
    }
}