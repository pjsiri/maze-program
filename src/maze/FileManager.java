/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author pj
 */
public class FileManager 
{
    public String name;
    public String[] lineData;
    public int numberOfLines;
    public String data="";
    private Node lastNode;
    private Node firstNode;
    
    public FileManager()
    {
        lastNode = null;
        firstNode = null;
    }        
    public FileManager(String fileName)
    {
        this.name = fileName;
        numberOfLines = 0;
        File f = new File(name);
        try
        {
            try (Scanner myScanner = new Scanner(f)) {
                while(myScanner.hasNextLine())
                {
                    myScanner.nextLine();
                    numberOfLines++;
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Cannot read the file"+e.getMessage());
        }
        lineData = new String[numberOfLines];
    }
    
    /**
     * Method: readMaze
     * 
     * This method is used to read a maze text file and create a maze object based on the file contents.
     * It uses a array list called node list to hold all the nodes created from the text file.
     * Then, it uses a hash map to store all links between a node. This will later be used to set the node links.
     * Finally, after reading the text file, it will return a maze object with the nodes and the maze attributes. 
     * 
     * @param fileName The name of the maze file to read
     * @return The maze object created
     */
    public Maze readMaze(String fileName) 
    {
        File f;
        if (fileName == null)
            f = new File(this.name);
        else
            f = new File(fileName);

        try 
        {
            int numOfLinkers;
            int numOfCol;
            int numOfRow;
            ArrayList<Node> nodeList; 
            
            try (Scanner myScanner = new Scanner(f)) {
                // Read the text file first line for the rows, columns and linkers
                String headerLine = myScanner.nextLine();
                String[] headerValues = headerLine.split(",");
                numOfLinkers = Integer.parseInt(headerValues[0]);
                numOfCol = Integer.parseInt(headerValues[1]);
                numOfRow = Integer.parseInt(headerValues[2]);
                nodeList = new ArrayList<>(); // This array list will contain all the nodes
                HashMap<Node, String[]> linkerMap = new HashMap<>(); // This HashMap will be used to store linkers.
                // Read the maze nodes
                while (myScanner.hasNextLine())
                {
                    String nodeLine = myScanner.nextLine();
                    String[] nodeValues = nodeLine.split(",");
                    String nodeName = nodeValues[0];
                    int x = Integer.parseInt(nodeValues[1]);
                    int y = Integer.parseInt(nodeValues[2]);
                    String nextNodeName1 = nodeValues[3];
                    String nextNodeName2 = nodeValues[4];
                    
                    Node node = new Node(nodeName, x, y); // Create a node object based on the text file contents
                    node.setVisited(false); // Set the default visited status to false
                    
                    linkerMap.put(node, new String[]{nextNodeName1,nextNodeName2});
                    
                    nodeList.add(node); // Add to the array list
                }   
                
                firstNode = nodeList.get(0);
                lastNode = nodeList.get(nodeList.size() - 1);
                // Connect the nodes to each other with a hash map
                connectLinks(nodeList, linkerMap);
            }
            // Create and return a maze object after reading the text file
            return new Maze(numOfLinkers, numOfCol, numOfRow, firstNode, lastNode, nodeList);

        } catch (IOException e) 
        {
            System.out.println("IOException Error!");
        } catch(NoSuchElementException e)
        {
            System.out.println("Empty file or invalid file Format!");
        } catch(NumberFormatException e)
        {
            System.out.println("Invalid file format!");
        }
    
    return null;
    }
    
    
    /**
     * Method: findNodeByName
     * 
     * This method will first check if the node name is equal to W, and it will return the lastNode if so.
     * Then, it will begin traversing through the node list, comparing all of the nodes to the node name given.
     * If found, it will return the node in the ArrayList.
     */
    
    private Node findNodeByName(ArrayList<Node> nodeList, String nodeName) 
    {
        if(nodeName.equals("W")) // W is the same as the EXIT node, therefore it will return the last node.
        {
            return lastNode;
        }
        for (Node node : nodeList) 
        {
            if (node.getName().equals(nodeName)) {
                return node;
            }
        }
        
        return null; 
    }
    
    /**
     * Method: connectLinks
     * 
     * This method is used to connect the nodes to its links based on the linkerMap. It will check if the first
     * or second linker node is not equal to "A" because "A" refers to null. If not, it will begin to assign the
     * node to its links by using the findNodeByName method. It will search through the node list and return the node.
     * This will be used to assign node1 and node2 to its corresponding node if applicable.
     */
    private void connectLinks(ArrayList<Node> nodeList, HashMap<Node, String[]> linkerMap) 
    {
        for (Node node : linkerMap.keySet()) 
        {
            String[] linkerNodeNames = linkerMap.get(node);
            String nodeName1 = linkerNodeNames[0];
            String nodeName2 = linkerNodeNames[1];

            if (!nodeName1.equals("A")) {
                Node nextNode1 = findNodeByName(nodeList, nodeName1);
                node.setNode1(nextNode1);
            }
            if (!nodeName2.equals("A")) {
                Node nextNode2 = findNodeByName(nodeList, nodeName2);
                node.setNode2(nextNode2);
            }
        }
    }
    
}