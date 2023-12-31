package ui;

import model.Display;

import java.util.Scanner;

/**
 * A program that handles documents and displays it as windows.
 *
 * @author Han Kim
 * @version 2023-07-24
 */
public class Driver{
    
    /**
     * Main method, which reads user input and executes commands.
     * It will loop until the user inputs the command "quit".
     * 
     * @param args isn't used
     */
    public static void main(String[] args){
        Display display = new Display("Display");
        Scanner scan = new Scanner(System.in);
        boolean quit = false;
        
        while(!quit){
            display.print();
            System.out.print(">>");
            String command = scan.nextLine();
            String[] commands = command.split(" ", 2);
            String[] parameters;
            
            try{
                switch(commands[0].toLowerCase()){
                    case "new":
                        display.commandNew();
                        break;
                    case "open":
                        display.commandOpen(commands[1]);
                        break;
                    case "close":
                        try{
                            display.commandClose(commands[1]);
                        }catch(ArrayIndexOutOfBoundsException e){
                            display.commandClose();
                        }
                        break;
                    case "quit":
                        quit = true;
                        break;
                    case "resize":
                        parameters = commands[1].split(" ", 2);
                        display.commandResize(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]));
                        break;
                    case "move":
                        parameters = commands[1].split(" ", 2);
                        display.commandMove(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]));
                        break;
                    case "select":
                        display.commandSelect(commands[1]);
                        break;
                    case "write":
                        display.commandWrite(commands[1]);
                        break;
                    case "append":
                        display.commandAppend(commands[1]);
                        break;
                    case "save":
                        try{
                            display.commandSave(commands[1]);
                        }catch(ArrayIndexOutOfBoundsException e){
                            display.commandSave();
                        }
                        break;
                    default:
                        display.alert("INVALID COMMAND");
                }
            }catch(Exception e){
                display.alert("INVALID ARGUMENTS");
            } 
        }
        scan.close();
    }
}