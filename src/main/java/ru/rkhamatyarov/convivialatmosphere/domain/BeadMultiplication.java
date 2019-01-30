package ru.rkhamatyarov.convivialatmosphere.domain;

public class BeadMultiplication {
    private Integer leftBeadFactor;
    private Integer rightBeadFactor;

    private Integer beadMultiplicationResult;

    public BeadMultiplication(Integer leftBeadFactor, Integer rightBeadFactor) {
        this.leftBeadFactor = leftBeadFactor;
        this.rightBeadFactor = rightBeadFactor;
        this.beadMultiplicationResult = leftBeadFactor * rightBeadFactor;
    }

    public Integer getLeftBeadFactor() {
        return leftBeadFactor;
    }

    public Integer getRightBeadFactor() {
        return rightBeadFactor;
    }

    public Integer getBeadMultiplicationResult() {
        return beadMultiplicationResult;
    }

    @Override
    public String toString() {
        return "BeadMultiplication{" +
                "leftBeadFactor =" + leftBeadFactor +
                ", rightBeadFactor =" + rightBeadFactor +
                ", leftBeadFactor * rightBeadFactor =" + beadMultiplicationResult +
                '}';
    }
}
