package ru.rkhamatyarov.convivialatmosphere.domain;

import java.math.BigInteger;

public class BeadMultiplication {
    private BigInteger leftBeadFactor;
    private BigInteger rightBeadFactor;

    private BigInteger beadMultiplicationResult;

    public BeadMultiplication(BigInteger leftBeadFactor, BigInteger rightBeadFactor) {
        this.leftBeadFactor = leftBeadFactor;
        this.rightBeadFactor = rightBeadFactor;
        this.beadMultiplicationResult = leftBeadFactor.multiply(rightBeadFactor);
    }

    public BigInteger getLeftBeadFactor() {
        return leftBeadFactor;
    }

    public BigInteger getRightBeadFactor() {
        return rightBeadFactor;
    }

    public BigInteger getBeadMultiplicationResult() {
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
