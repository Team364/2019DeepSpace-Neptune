package frc.robot;

public class States{

    //State for whether robot is in hatch mode or cargo mode
    public static enum ObjectStates {
        CARGO_OBJ,
        HATCH_OBJ
    }

    //Object State is declared and set to cargo
    public static ObjectStates objState = ObjectStates.CARGO_OBJ;
}


