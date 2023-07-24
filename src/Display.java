/**
 * A class responsible for the display windows
 *
 * @author Donghan Kim (100382712)
 * @version 2022-03-09
 */


import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class Display extends Window{
    private ArrayList<Window> contents;
    
    /**
     * Constructor for Display
     * 
     * @param name is the name of the Display
     */
    public Display(String name){
        super(name, new int[]{0,0}, new char[Window.DISPLAY_HEIGHT][Window.DISPLAY_WIDTH]);
        this.contents = new ArrayList<Window>();
    }
    
    /**
     * Method to prints out the pixels of the Display
     * 
     * @return void
     */
    public void print(){
        updatePixel();
        for(char[] y : getPixels()){
            for(char x : y){
                System.out.print(x);
            }
            System.out.println();
        }
    }
    
    /**
     * Method that updates the pixels of this Display with its content
     * 
     * @return void
     */
    public void updatePixel(){
        updateBorder();
        for(Window window : contents){
            updateWindow(window);
        }
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
                    p[i][j] = '═';
                }
            }else{
                p[i][0] = '║';
                p[i][p[i].length - 1] = '║';
            }
        }
        
        for(int i = 0; i < getName().length(); i++){
            p[0][i + 2] = getName().charAt(i);
        }
        
        p[0][0] = '╔';
        p[0][p[0].length - 1] = '╗';
        p[p.length - 1][0] = '╚';
        p[p.length - 1][p[p.length - 1].length - 1] = '╝';
    }
    
    /**
     * Helper method for updatePixel that update the windows in the display to the pixel
     * 
     * @return void
     */
    private void updateWindow(Window window){
        window.updatePixel();
        
        int i = 0;
        int j = 0;
        
        for(char[] y : window.getPixels()){
            for(char x : y){
                if(window.getPos()[0] + j < getPixels().length - 1 && window.getPos()[1] + i < getPixels()[0].length - 1){
                    getPixels()[window.getPos()[0] + j][window.getPos()[1] + i] = x;
                    i++;
                }
            }
            i = 0;
            j++;
        }
    }
    
    /**
     * Method that makes a new document window
     * 
     * @return void
     */
    public void commandNew(){
        contents.add(new Document(""));
    }
    
    /**
     * Method that opens a document window 
     * 
     * @param fileName is the name of the file to open
     * @return void
     */
    public void commandOpen(String fileName){
        if(fileName.lastIndexOf(".txt") >= 0 && fileName.lastIndexOf(".txt") == fileName.length() - 4){
                    File file = new File(fileName);
            if(file.exists()){
                contents.add(new Document(file));
            }else{
                contents.add(new Document(fileName, ""));
            }
        }else{
            contents.add(new Alert("FILENAMES MUST INCLUDE .txt"));
        }
    }
    
    /**
     * Method that closes the active window 
     * 
     * @return void
     */
    public void commandClose(){
        if(contents.get(contents.size() - 1) instanceof Alert){
            contents.remove(contents.size() - 1);
        }else{
            contents.add(new Alert("CLOSE DOCUMENTS BY NAME ONLY"));
        }
    }
    
    /**
     * Method that closes a window with name
     * 
     * @param fileName is the name of the window
     * @return void
     */
    public void commandClose(String fileName){
        int index = findIndexOf(fileName);
        if(index != -1){
            contents.remove(index);
        }else{
            contents.add(new Alert("DOCUMENT OR ALERT NOT FOUND"));
        }
    }
    
    /**
     * Helper method to find the index of the given window name
     * 
     * @param fileName is the name of the window
     * @return index of the window in contents
     */
    private int findIndexOf(String fileName){
        for(int i = 0; i < contents.size(); i++){
            if(contents.get(i).getName().equals(fileName)){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Method that resizes the active window
     * 
     * @param height is the heigth to resize to 
     * @param width is the width to resize to 
     * @return void
     */
    public void commandResize(int height, int width){
        if(contents.get(contents.size() - 1) instanceof Document){
            Window active = contents.remove(contents.size() - 1);
            active.setPixels(new char[height][width]);
            contents.add(active);
        }else{
            contents.add(new Alert("ALERT CANNOT BE RESIZED"));
        }
    }
    
    /**
     * Method that moves the active window
     * 
     * @param row is the row to move to 
     * @param col is the col to move to 
     * @return void
     */
    public void commandMove(int row, int col){
        if(contents.get(contents.size() - 1) instanceof Document){
            Window temp = contents.remove(contents.size() - 1);
            temp.setPos(row, col);
            contents.add(temp);
        }else{
            contents.add(new Alert("ALERT CANNOT BE MOVED"));
        }
    }
    
    /**
     * Method that moves the active window
     * 
     * @param row is the row to move to 
     * @param col is the column to move to 
     * @return void
     */
    public void commandSelect(String fileName){
        int index = findIndexOf(fileName);
        if(index != -1){
            contents.add(contents.remove(index));
        }else{
            contents.add(new Alert("DOCUMENT OR ALERT NOT FOUND"));
        }
    }
    
    /**
     * Method that writes to the active window
     * 
     * @param content is the content to write to the window 
     * @return void
     */
    public void commandWrite(String content){
        if(contents.get(contents.size() - 1) instanceof Document){
            Window active = contents.remove(contents.size() - 1);
            ((Document)active).addContent(content);
            contents.add(active);
        }else{
            contents.add(new Alert("ALERT CANNOT BE WRITTEN"));
        }
    }
    
    /**
     * Method that appends a content of the selected window to the active window
     * 
     * @param filename is the window with the content 
     * @return void
     */
    public void commandAppend(String fileName){
        int index = findIndexOf(fileName);
        if(index != -1){
            if(contents.get(contents.size() - 1) instanceof Document && contents.get(index) instanceof Document){
                Window temp = contents.get(index);
                Window active = contents.remove(contents.size() - 1);
                ((Document)active).addContent("|" + ((Document)temp).getContents());
                contents.add(active);
            }else{
                contents.add(new Alert("APPEND WITH ALERT NOT ALLOWED"));
            }
        }else{
            contents.add(new Alert("DOCUMENT OR ALERT NOT FOUND"));
        }
    }
    
    /**
     * Method that saves the content of the active window to a txt file
     * 
     * @return void
     */
    public void commandSave(){
        try{
            Window active = contents.get(contents.size() - 1);
            String toPrint = ((Document)active).getContents();
            PrintWriter out = new PrintWriter(active.getName());
            
            for(char ch : toPrint.toCharArray()){
                if(ch == '|'){
                    out.print('\n');
                }else{
                    out.print(ch);
                }
            }
            
            out.close();
            
            contents.add(new Alert("DOCUMENT SAVED"));
        }catch(Exception e){
            contents.add(new Alert("NOTHING TO SAVE"));
        }
    }
    
    /**
     * Method that saves the content of the active window to a txt file
     * 
     * @param filename is the name of the txt file
     * @return void
     */
    public void commandSave(String fileName){
        try{
            Window active = contents.get(contents.size() - 1);
            String toPrint = ((Document)active).getContents();
            PrintWriter out = new PrintWriter(fileName);
            
            for(char ch : toPrint.toCharArray()){
                if(ch == '|'){
                    out.print('\n');
                }else{
                    out.print(ch);
                }
            }
            
            out.close();
            
            contents.add(new Alert("DOCUMENT SAVED"));
        }catch(Exception e){
            contents.add(new Alert("NOTHING TO SAVE"));
        }
    }
    
    /**
     * Method that makes a new alert window with the given error message
     * 
     * @param errMsg is the error message
     * @return void
     */
    public void alert(String errMsg){
        contents.add(new Alert(errMsg));
    }
}