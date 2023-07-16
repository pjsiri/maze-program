/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author pj
 */
public class Maze {
    
    private int numOfLinkers;
    private int numOfCol;
    private int numOfRow;
    private Node firstNode;
    private Node lastNode;
    private ArrayList<Node> nodeList;
    
    public Maze(int numOfLinkers,int numOfCol,int numOfRow,Node firstNode, Node lastNode,ArrayList<Node> nodeList)
    {
        this.numOfLinkers = numOfLinkers;
        this.numOfCol = numOfCol;
        this.numOfRow = numOfRow;
        this.firstNode = firstNode;
        this.lastNode = lastNode;
        this.nodeList = nodeList;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setCurrentDirectory(new File("."));
        String fileName = "Maze1.txt";
        FileManager fileManager = new FileManager(fileName);
        Maze maze = fileManager.readMaze(fileName); // Assign maze object from reading the text file

        JFrame frame = new JFrame("Maze GUI Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel panel = new Panel(maze);
        
        JPanel buttonPanel = new JPanel();
        JButton SelectMaze = new JButton("Select new Maze!"); // Button for selecting a new maze
        JButton RedoPath = new JButton("Redo path animation!"); // Button for redoing the path animation
        
        JLabel fileNameLabel = new JLabel(fileName); // Added JLabel to show file name at the top of GUI
        fileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(fileNameLabel, BorderLayout.NORTH);
        
        SelectMaze.addActionListener((ActionEvent e) ->
        {
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) 
            {
                File selectedFile = fileChooser.getSelectedFile(); // Allow user to select new maze text file
                Maze newMaze = fileManager.readMaze(selectedFile.getAbsolutePath());
                if(newMaze != null) // If the maze has successfully loaded, then show a confirmation message box.
                {
                    panel.setMaze(newMaze);
                    panel.repaint();
                    fileNameLabel.setText(selectedFile.getName()); // Reset file name
                    panel.startPathAnimation(); // Redo path animation
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error: Maze file unsuccessfuly loaded!");
                }
            }
        });
        
        RedoPath.addActionListener((ActionEvent e) ->
        {
            panel.startPathAnimation();
        });
        buttonPanel.add(SelectMaze);
        buttonPanel.add(RedoPath);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.add(panel);
        frame.setResizable(true);
        frame.setSize(800, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.startPathAnimation();
    }

    /**
     * Method: getNumOfLinkers
     * 
     * @return the number of linkers
     */
    public int getNumOfLinkers() {
        return numOfLinkers;
    }

    /**
     * Method: setNumOfLinkers
     * 
     * @param numOfLinkers the number of linkers to set
     */
    public void setNumOfLinkers(int numOfLinkers) {
        this.numOfLinkers = numOfLinkers;
    }

    /**
     * Method: getNumOfCol
     * 
     * @return the number of columns
     */
    public int getNumOfCol() {
        return numOfCol;
    }

    /**
     * Method: setNumOfCol
     * 
     * @param numOfCol the number of columns to set
     */
    public void setNumOfCol(int numOfCol) {
        this.numOfCol = numOfCol;
    }

    /**
     * Method: getNumOfRow
     * 
     * @return the number of rows
     */
    public int getNumOfRow() {
        return numOfRow;
    }

    /**
     * Method: setNumOfRow
     * 
     * @param numOfRow the number of rows to set
     */
    public void setNumOfRow(int numOfRow) {
        this.numOfRow = numOfRow;
    }

    /**
     * Method: getFirstNode
     * 
     * @return the firstNode
     */
    public Node getFirstNode() {
        return firstNode;
    }

    /**
     * Method: setFirstNode
     * 
     * @param firstNode the firstNode to set
     */
    public void setFirstNode(Node firstNode) {
        this.firstNode = firstNode;
    }

    /**
     * Method: getLastNode
     * 
     * @return the lastNode
     */
    public Node getLastNode() {
        return lastNode;
    }

    /**
     * Method: setLastNode
     * 
     * @param lastNode the lastNode to set
     */
    public void setLastNode(Node lastNode) 
    {
        this.lastNode = lastNode;
    }

    /**
     * Method: getNodeList
     * 
     * @return the nodeList
     */
    public ArrayList<Node> getNodeList() 
    {
        return nodeList;
    }

    /**
     * Method: setNodeList
     * 
     * @param nodeList the nodeList to set
     */
    public void setNodeList(ArrayList<Node> nodeList) 
    {
        this.nodeList = nodeList;
    }
}