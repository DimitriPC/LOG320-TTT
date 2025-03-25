import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Client {
    public static void main(String[] args) {

        Socket MyClient;
        BufferedInputStream input;
        BufferedOutputStream output;
        CPUPlayer cpuPlayer = null;
        BigBoard bigBoard = null;

        try {
            MyClient = new Socket("localhost", 8888);

            input    = new BufferedInputStream(MyClient.getInputStream());
            output   = new BufferedOutputStream(MyClient.getOutputStream());
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while(1 == 1){
                char cmd = 0;

                cmd = (char)input.read();
                System.out.println(cmd);
                // Debut de la partie en joueur X
                if(cmd == '1'){
                    byte[] aBuffer = new byte[1024];

                    int size = input.available();
                    input.read(aBuffer,0,size);
                    String s = new String(aBuffer).trim();
                    System.out.println(s);

                    System.out.println("Nouvelle partie! Vous jouez X, entrez votre premier coup : ");
                    cpuPlayer = new CPUPlayer(Mark.X);
                    bigBoard = new BigBoard();
                    ArrayList<Move> arr = cpuPlayer.getNextMoveAB(bigBoard);
//                    ArrayList<Move> arr = cpuPlayer.getNextMoveMinMax(bigBoard);
                    bigBoard.play(arr.getFirst(), Mark.X);
                    String move = arr.getFirst().sendMove();
                    output.write(move.getBytes(),0,move.length());
                    output.flush();
                }
                // Debut de la partie en joueur O
                if(cmd == '2'){

                    System.out.println("Nouvelle partie! Vous jouez O, attendez le coup des X");
                    byte[] aBuffer = new byte[1024];

                    int size = input.available();
                    input.read(aBuffer, 0, size);
                    String s = new String(aBuffer).trim();
                    System.out.println(s);

                    cpuPlayer = new CPUPlayer(Mark.O);
                    bigBoard = new BigBoard();

                }


                // Le serveur demande le prochain coup
                // Le message contient aussi le dernier coup joue.
                if(cmd == '3'){
                    byte[] aBuffer = new byte[16];

                    int size = input.available();
                    System.out.println("size :" + size);
                    input.read(aBuffer,0,size);

                    String s = new String(aBuffer);
                    System.out.println("Dernier coup :"+ s);

                    String moveString = s.substring(1, 3);
                    Move m = createMoveWithString(moveString);
                    bigBoard.play(m, cpuPlayer.getMarkCPU().enemy());

                    ArrayList<Move> arr = cpuPlayer.getNextMoveAB(bigBoard);
//                    ArrayList<Move> arr = cpuPlayer.getNextMoveMinMax(bigBoard);
                    bigBoard.play(arr.getFirst(), cpuPlayer.getMarkCPU());
                    String move = arr.getFirst().sendMove();

                    System.out.print(move);
                    output.write(move.getBytes(),0,move.length());
                    output.flush();

                }
                // Le dernier coup est invalide
                if(cmd == '4'){
                    System.out.println("Coup invalide, entrez un nouveau coup : ");
                    String move = console.readLine();
                    output.write(move.getBytes(),0,move.length());
                    output.flush();

                }
                // La partie est terminée
                if(cmd == '5'){
                    byte[] aBuffer = new byte[16];
                    int size = input.available();
                    input.read(aBuffer,0,size);
                    String s = new String(aBuffer);
                    System.out.println("Partie Terminé. Le dernier coup joué est: "+s);
                    String move = console.readLine();
                    output.write(move.getBytes(),0,move.length());
                    output.flush();

                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }

    public static Move createMoveWithString(String s){
        if (s.length() == 2){
            int bigrow = ( 9 - Character.getNumericValue(s.charAt(1)) ) ;
            int bigcol = (s.charAt(0) - 'A') ;
            int index = (bigrow / 3) * 3 + (bigcol / 3);
            int smallrow = bigrow % 3;
            int smallcol = bigcol % 3;

            return new Move(smallrow, smallcol, index);
        }
        return null;
    }
}