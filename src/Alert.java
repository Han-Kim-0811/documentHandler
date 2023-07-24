/**
 * A class responsible for the alert windows
 *
 * @author Donghan Kim (100382712)
 * @version 2022-03-09
 */

public class Alert extends Window{
    private String contents;
    
    private static int untitled = 1;
    
    /**
     * Constructor for Alert
     * 
     * @param contents is the contents of the Alert
     */
    public Alert(String contents){
        super("Alert " + untitled++, new int[]{getNextPos()[0], getNextPos()[1]}, new char[5][35]);
        updateNextPos();
        this.contents = contents;
    }
    
    /**
     * Method that updates the pixels of this Alert with its content
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
                    p[i][j] = '┄';
                }
            }else{
                p[i][0] = '┊';
                p[i][p[i].length - 1] = '┊';
            }
        }
        
        for(int i = 0; i < getName().length(); i++){
            p[0][i + 2] = getName().charAt(i);
        }
        
        p[0][0] = '╭';
        p[0][p[0].length - 1] = '╮';
        p[p.length - 1][0] = '╰';
        p[p.length - 1][p[p.length - 1].length - 1] = '╯';
    }
    
    /**
     * Helper method for updatePixel that update the content of the window
     * 
     * @return void
     */
    private void updateContent(){
        char[][] p = getPixels();
        
        for(int i = 0; i < p[2].length - 2 && i < contents.length(); i++){
            p[2][i + 1] = contents.charAt(i);
        }
    }
}