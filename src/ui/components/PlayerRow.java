package ui.components;

import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import model.Player;
import ui.util.UIHelpers;

import java.util.ArrayList;
import java.util.List;

public class PlayerRow {
    private List<Spinner<Integer>> spinners;

    //coordinates
    private Spinner<Integer> row;
    private Spinner<Integer> col;

    //player stats
    private Spinner<Integer> H;
    private Spinner<Integer> A;
    private Spinner<Integer> D;
    private Spinner<Integer> S;

    private Button remove;

    public PlayerRow() {

        spinners = new ArrayList<>();

        row = new Spinner<>();
        col = new Spinner<>();
        H = new Spinner<>();
        A = new Spinner<>();
        D = new Spinner<>();
        S = new Spinner<>();

        UIHelpers.configureSpinner(row, 0, 999, 0);
        UIHelpers.configureSpinner(col, 0, 999, 0);
        UIHelpers.configureSpinner(H, 1, 999, 100);
        UIHelpers.configureSpinner(A, 0, 999, 40);
        UIHelpers.configureSpinner(D, 0, 999, 0);
        UIHelpers.configureSpinner(S, 0, 999, 20);

        spinners.addAll(List.of(row, col, H, A, D, S));
    }

    public List<Spinner<Integer>> getSpinners() {
        return spinners;
    }

    public Player toPlayer() {
        return new Player(
                H.getValue(),
                A.getValue(),
                D.getValue(),
                S.getValue(),
                S.getValue()
        );
    }
}
