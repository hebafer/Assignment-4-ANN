package com.company;

public class Example {
    private double input1;
    private double input2;
    private double input3;
    private double target;
    private boolean survived;

    public Example(double input1, double input2, double input3, double survived) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        if(survived == 1.0){
            this.target = 0.75;
            this.survived = true;
        }else{
            this.target = 0.25;
            this.survived = false;
        }
    }

    public double getInput1() {
        return input1;
    }

    public void setInput1(double input1) {
        this.input1 = input1;
    }

    public double getInput2() {
        return input2;
    }

    public void setInput2(double input2) {
        this.input2 = input2;
    }

    public double getInput3() {
        return input3;
    }

    public void setInput3(double input3) {
        this.input3 = input3;
    }

    public double[] getTarget() {
        double[] array = {this.target};
        return array;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public boolean isSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
    }

    public double[] getInputs(){
        double[] array = {this.getInput1(), this.getInput2(), this.getInput3()};
        return array;
    }

    @Override
    public String toString() {
        return "Example{" +
                "input1=" + input1 +
                ", input2=" + input2 +
                ", input3=" + input3 +
                ", target=" + target +
                ", survived=" + survived +
                '}';
    }
}
