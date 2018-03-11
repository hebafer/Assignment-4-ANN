package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static ArrayList<Example> getTrainingExamples(){
        ArrayList<Example> examples = new ArrayList<>();
        try {
            Scanner input = new Scanner("assignment 4 titanic.dat");
            File file = new File(input.nextLine());
            input = new Scanner(file);

            for (int i = 0; i < 8; i++) {
                input.nextLine();
            }

            String[] parts;
            int i = 0;
            while (input.hasNextLine() && !input.hasNext("EOF") && i < 1509) {
                parts = input.nextLine().split(",");
                //System.out.println(parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3]);
                examples.add(new Example(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
                       Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
                i++;
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return examples;
    }

    public static ArrayList<Example> getValidationInputs(){
        ArrayList<Example> examples = new ArrayList<>();
        try {
            Scanner input = new Scanner("assignment 4 titanic.dat");
            File file = new File(input.nextLine());
            input = new Scanner(file);

            for (int i = 0; i < 1509; i++) {
                input.nextLine();
            }

            String[] parts;
            while (input.hasNextLine() && !input.hasNext("EOF")) {
                parts = input.nextLine().split(",");
                //System.out.println(parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3]);
                examples.add(new Example(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return examples;
    }

    public static void main(String[] args){
        Network network = new Network(3, 2, 1);
        ArrayList<Example> examples = getTrainingExamples();
        ArrayList<Example> validation = getValidationInputs();
        for(int epoch=0; epoch < 100; epoch++){
            for(int i=0; i < examples.size(); i++){
                network.train(examples.get(i).getInputs(), examples.get(i).getTarget(), 0.2);
            }
        }
        double[] output;
        String result;
        String expected;
        int aciertos = 0, error = 0, expectedSurvived = 0;
        int aliveExpectedSurvived = 0, alive=0, dead = 0;
        for(int i=0; i < validation.size(); i++){
            network.calculate(validation.get(i).getInputs());
            output = network.getOutput();
            result = (output[0] >= 0.5)?"Survived":"Died";
            expected = (validation.get(i).isSurvived())?"Survived":"Died";
            if(result.equals(expected)){ aciertos++; } else error++;
            if(result.equals("Survived")){ alive++;} else dead++;
            if(expected=="Survived"){
                expectedSurvived++;
                if(result=="Survived") aliveExpectedSurvived++;
            }
            System.out.println("Result:= " +result + ", Expected: " + expected);
        }
        System.out.println("Numero de aciertos: " + aciertos+ " Numero de errores: " + error);
        System.out.println("Porcentaje de aciertos: "+ (double)aciertos/validation.size());
        System.out.println("Alive: "+ alive + " , Dead: " + dead);
        System.out.println("Survive According the ANN("+ aliveExpectedSurvived +") according to the expected value("+expectedSurvived+"): "
                            + (double)aliveExpectedSurvived/expectedSurvived);
        //network.printWeights();
    }

}
