/**
 * A class responsible for the document windows
 *
 * @author Donghan Kim (100382712)
 * @version 2022-03-09
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Document extends Window{
    private String contents;
    
    private static int untitled = 1;
    
    /**
     * Constructor for Document
     * 
     * @param contents is the contents of the Display
     */
    public Document(String contents){
        super("Untitled " + untitled++, new int[]{getNextPos()[0], getNextPos()[1]}, new char[25][35]);
        updateNextPos();
        this.contents = contents;
    }
    
    /**
     * Constructor for Document
     * 
     * @param name is the name of the Display
     * @param contents is the contents of the Display
     */
    public Document(String name, String contents){
        super(name, new int[]{getNextPos()[0], getNextPos()[1]}, new char[25][35]);
        updateNextPos();
        this.contents = contents;
    }
    
    /**
     * Constructor for Document
     * 
     * @param file is the file that holds all necessary informations for constructing a Document
     */
    public Document(File file){
        super(file.getName(), new int[]{getNextPos()[0], getNextPos()[1]}, new char[25][35]);
        updateNextPos();
        this.contents = "";
        readFile(file);
    }
    
    /**
     * Getter for contents 
     * 
     * @return contents of this Document
     */
    public String getContents(){
        return this.contents;
    }
    
    /**
     * Helpet method for the constructor with file, that reads the given file
     * 
     * @param file is the file to read
     * @return void
     */
    private void readFile(File file){
        try{
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()){
                this.contents += scan.nextLine();
            }
            scan.close();
        }catch(IOException e){
        }
    }
    
    /**
     * Method that updates the pixels of this Document with its content
     * 
     * @return void
     */
    public void updatePixel(){
        updateBorder();
        updateContent();
    }
    
    /**
     * Helper method for updatePixel that update the border of the window
     * 
     * @return void
     */
    private void updateBorder(){
        char[][] p = getPixels();
        
        for(int i = 0; i < p.length; i++){
            for(int j = 0; j < p[i].length; j++){
                p[i][j] = ' ';
            }
        }
        
        for(int i = 0; i < p.length; i++){
            if(i == 0 || i == p.length - 1){
                for(int j = 0; j < p[i].length; j++){
                    p[i][j] = '─';
                }
            }else{
                p[i][0] = '│';
                p[i][p[i].length - 1] = '│';
            }
        }
        
        for(int i = 0; i < getName().length() && i < p[0]. length - 4; i++){
            p[0][i + 2] = getName().charAt(i);
        }
        
        p[0][0] = '┌';
        p[0][p[0].length - 1] = '┐';
        p[p.length - 1][0] = '└';
        p[p.length - 1][p[p.length - 1].length - 1] = '┘';
    }
    
    /**
     * Helper method for updatePixel that update the content of the window
     * 
     * @return void
     */
    private void updateContent(){
        char[][] p = getPixels();
        int k = 0;
        
        for(int i = 1; i < p.length - 1 && k < contents.length(); i++){
            for(int j = 1; j < p[i].length - 1 && k < contents.length(); j++){
                if(contents.charAt(k) == '|'){
                    j = p[i].length;
                }else{
                    p[i][j] = contents.charAt(k);
                }
                k++;
            }
        }
    }
    
    /**
     * Method for adding contents to the document window
     * 
     * @param content is the content to add
     * @return void
     */
    public void addContent(String content){
        this.contents += content;
    }
}