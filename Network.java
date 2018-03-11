package com.company;

public class Network {

    public final int[] NETWORK;
    public final int inputSize;
    public final int outputSize;
    public final int networkSize;

    //1st index: layer we are working in, 2nd index: neuron we are working in.
    public double[][] output;
    //3rd index: Neuron in the previous layer.
    private double[][][] weights;
    private double[][] bias;
    private double[][] error_term;
    private double[][] output_derivative;



    public Network(int... NETWORK){

        this.NETWORK = NETWORK;
        this.inputSize = NETWORK[0];
        this.networkSize = NETWORK.length;
        this.outputSize = NETWORK[2];

        this.output = new double[3][];
        this.weights = new double[3][][];
        this.bias = new double[3][];
        this.error_term = new double[3][];
        this.output_derivative = new double[3][];

        //for each layer
        for(int layer = 0; layer < 3; layer++){
            //Input layer
            this.output[layer] = new double[NETWORK[layer]];
            this.bias[layer] = new double[NETWORK[layer]];
            this.error_term[layer] = new double[NETWORK[layer]];
            this.output_derivative[layer] = new double[NETWORK[layer]];

            //The first layer does not have any weight connected, it does not calculate anything.
            if(layer > 0){
                weights[layer] = new double[NETWORK[layer]][NETWORK[layer-1]];
            }
        }
    }

    public void calculate(double[] input){
        //The first layer is like a buffer to store the inputs
        this.output[0] = input;
        for(int layer = 1; layer < 3; layer++){
            for(int neuron = 0; neuron < NETWORK[layer]; neuron++){
                //first calculate the sum
                double sum = bias[layer][neuron];
                for(int previousNeuron = 0; previousNeuron < NETWORK[layer-1]; previousNeuron++) {
                    sum += output[layer - 1][previousNeuron] * weights[layer][neuron][previousNeuron];
                }
                //second apply activation function
                output[layer][neuron] = sigmoid(sum);
                output_derivative[layer][neuron] = (output[layer][neuron] * (1 - output[layer][neuron]));
            }
        }
    }

    public double[] getOutput(){
        return this.output[networkSize-1];
    }

    public double[][] getErrorTerm() {
        return this.error_term;
    }

    private double sigmoid(double x){
        return 1.0/(1 + Math.exp(-x));
    }

    public void train(double[] input, double[] target, double learningRate){
        calculate(input);
        backPropagationError(target);
        updateWeights(learningRate);
    }

    public void backPropagationError(double[] target){
        //Error in the last layer
        for(int neuron = 0; neuron < NETWORK[networkSize -1]; neuron++){
            error_term[networkSize -1][neuron] = (output[networkSize -1][neuron] - target[neuron])
                    * output_derivative[networkSize -1][neuron];
        }
        //Backpropagate the error_term from the last layer to the previous
        for(int layer = networkSize -2; layer > 0; layer--){
            for(int neuron = 0; neuron < NETWORK[layer]; neuron ++){
                double sum = 0;
                for(int nextNeuron = 0; nextNeuron < NETWORK[layer+1]; nextNeuron++){
                    sum += weights[layer + 1][nextNeuron][neuron] * error_term[layer+1][nextNeuron];
                }
                this.error_term[layer][neuron] = sum * output_derivative[layer][neuron];
            }
        }
    }

    public void updateWeights(double learningRate){
        for(int layer = 1; layer < networkSize; layer++){
            for(int neuron = 0; neuron < NETWORK[layer]; neuron++){
                double errorDifference = - learningRate * error_term[layer][neuron];
                bias[layer][neuron] += errorDifference;
                //Update the weights of each neuron
                for(int previousNeuron = 0; previousNeuron < NETWORK[layer-1]; previousNeuron++){
                    weights[layer][neuron][previousNeuron] += errorDifference * output[layer-1][previousNeuron];
                }

            }
        }
    }

    public void printWeights(){
        for(int layer = 1; layer < networkSize; layer++){
            for(int neuron = 0; neuron < weights[layer].length; neuron++){
                for(int prevNeuron=0; prevNeuron < weights[layer][neuron].length; prevNeuron++){
                    System.out.println("Weight in (Layer: " + layer + " , Neuron: " +neuron+ " , prevNeuron: " + prevNeuron + ") := " +weights[layer][neuron][prevNeuron]);
                }
            }
        }
    }

}
