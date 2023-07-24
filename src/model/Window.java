package model;

/**
 * An abstract class for all types of windows, including the display.
 */
public abstract class Window{
    private String name;
    private int[] pos;
    private char[][] pixels;
    
    public static final int DISPLAY_HEIGHT = 32;
    public static final int DISPLAY_WIDTH = 150;
    
    private static int[] nextPos = new int[]{3, 6};
    
    /**
     * Constructor for Window.
     * 
     * @param name is the name of the Window.
     * @param pos is the position of the Window.
     * @param pixels is the pixels of the Window.
     */
    public Window(String name, int[] pos, char[][] pixels){
        this.name = name;
        this.pos = pos;
        this.pixels = pixels;
    }
    
    /**
     * Getter for name.
     * 
     * @return name of this Window.
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Getter for pos.
     * 
     * @return pos of this Window.
     */
    public int[] getPos(){
        return this.pos;
    }
    
    /**
     * Setter for pos.
     * 
     * @param row is the row to set the Pos.
     * @param col is the column to set the Pos.
     */
    public void setPos(int row, int col){
        this.pos[0] = row;
        this.pos[1] = col;
    }
    
    /**
     * Getter for pixels.
     * 
     * @return pixels of this Window.
     */
    public char[][] getPixels(){
        return this.pixels;
    }
    
    /**
     * Setter for pixels.
     * 
     * @param pixels is the pixels to set the pixels.
     */
    public void setPixels(char[][] pixels){
        this.pixels = pixels;
    }
    
    /**
     * Getter for the static variable nextPos.
     * 
     * @return nextPos of Window.
     */
    public static int[] getNextPos(){
        return nextPos;
    }
    
    /**
     * Method, which updates the static variable nextPos.
     * Sets the nextPos to the next nextPos.
     */
    public void updateNextPos(){
        nextPos[0] += 5;
        nextPos[1] += 10;
        
        if(nextPos[0] > DISPLAY_HEIGHT - 2){
            nextPos[0] = 3;
        }
        
        if(nextPos[1] > DISPLAY_WIDTH - 2){
            nextPos[0] = 3;
            nextPos[1] = 6;
        }
    }
    
    /**
     * Method that update the pixels of this Window with its content.
     * To be implemented in each subclass.
     */
    public abstract void updatePixel();
}