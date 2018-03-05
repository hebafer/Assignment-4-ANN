package com.company;

public class Network {

    //1st index: layer we are working in.
    //2nd index: neuron we are working in.
    public double[][] output;
    //1st index: current layer.
    //2nd index: current neuron.
    //3rd index: Neuron in the previous layer.
    private double[][][] weights;
    private double[][] bias;

    //How many neurons each layer has.
    public final int[] NETWORK_LAYER_SIZES;
    public final int INPUT_SIZE;
    public final int OUTPUT_SIZE;
    public final int NETWORK_SIZE;


    public Network(int... NETWORK_LAYER_SIZES){
        this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
        this.INPUT_SIZE = NETWORK_LAYER_SIZES[0];
        this.NETWORK_SIZE = NETWORK_LAYER_SIZES.length;
        this.OUTPUT_SIZE = NETWORK_LAYER_SIZES[this.NETWORK_SIZE-1];

        this.output = new double[NETWORK_SIZE][];
        this.weights = new double[NETWORK_SIZE][][];
        this.bias = new double[NETWORK_SIZE][];

        //for each layer
        for(int i = 0; i < NETWORK_SIZE; i++){
            this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
            this.bias[i] = new double[NETWORK_LAYER_SIZES[i]];

            //The first layer does not have any weight connected, it does not calculate anything.
            if(i > 0){
                weights[i] = new double[NETWORK_LAYER_SIZES[i]][NETWORK_LAYER_SIZES[i-1]];
            }
        }
    }
}
