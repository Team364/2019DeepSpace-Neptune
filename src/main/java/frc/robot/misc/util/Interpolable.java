package frc.robot.misc.util;

public interface Interpolable<T> {
    T interpolate(T other, double t);
}
