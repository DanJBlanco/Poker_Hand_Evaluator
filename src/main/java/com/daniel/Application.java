package com.daniel;

import com.daniel.pokerhand.model.Hand;
import com.daniel.pokerhand.util.HandValue;

import javax.print.attribute.standard.Finishings;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Application {



    public static void main(String[] args) {

        boolean init =initProject();
        while (!init){
            init = initProject();
        }


        int countHand1Win = 0;
        int countHand2Win = 0;
        int countNeither = 0;
        double[] valuePorce = new double[]{0.00,0.00};
        ArrayList<String> allPorce = new ArrayList<String>();
        Hand[] hands = new Hand[2];
        HandValue handEvaluator = new HandValue();


        ArrayList<String> handsString = readFile("pokerdata.txt");

        for(String each : handsString){

            hands = Hand.getHandByString(each);

            int[] values = new int[]{
                    handEvaluator.evalHand(hands[0].getCards()),
                    handEvaluator.evalHand(hands[1].getCards())
            };

            valuePorce[0] = handEvaluator.getTypeValue(hands[0].getCards());
            valuePorce[1] = handEvaluator.getTypeValue(hands[1].getCards());

            allPorce.add(String.format("%16.6f%s %7s %15.6f%s%n", valuePorce[0],"%","|",valuePorce[1],"%"));

            if (values[0]==values[1]){
                countNeither++;
            }else if(values[0]>values[1]) {
                countHand1Win++;
            }else{
                countHand2Win++;
            }

        }

        printResult(countHand1Win, countHand2Win, countNeither, allPorce);

    }

    private static boolean initProject() {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("************** MENU **************");
        System.out.println("*                                *");
        System.out.println("* 1.- Read Hands                 *");
        System.out.println("*                                *");
        System.out.println("* 0.- Exit                       *");
        System.out.println("**********************************");
        System.out.print("Enter option(1): ");

        String option  = myObj.nextLine();

        if (option.matches("\\d+") && Integer.parseInt(option) == 0){
            System.exit(0);
        }
        return option.matches("\\d+") && Integer.parseInt(option) == 1;

    }

    private static void printResult(int countHand1Win, int countHand2Win, int countNeither, ArrayList<String> allPorce) {


        try {

            String ruta = "result.txt";
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }


            PrintWriter writer = new PrintWriter("result.txt", "UTF-8");
            writer.println("1: " + countHand1Win +
                    "\n2: " + countHand2Win +
                    "\n3: " + countNeither +
                    "\n4:"
            );

            for (String porc : allPorce){
                writer.println(porc);
            }
            writer.close();

            System.out.println("Proccess Finish, open file 'result.txt'");
            System.out.println("Press any key to close");
            System.in.read();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private static ArrayList<String> readFile(String file) {

        ArrayList<String> handsString = new ArrayList<String>();
        try{
            String[] data = null;
            BufferedReader buf = new BufferedReader(new FileReader(file));
            String lineJustFetched = null;
            String[] wordsArray;

            while(true){
                lineJustFetched = buf.readLine();
                if(lineJustFetched == null){
                    break;
                }else{
                    wordsArray = lineJustFetched.split("\t");
                    for(String each : wordsArray){
                        if(!"".equals(each)){
                            handsString.add(each);
                        }
                    }
                }
            }


            buf.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        return handsString;
    }





}
