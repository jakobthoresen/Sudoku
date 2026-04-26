package no.uib.inf101.sample.model;

import no.uib.inf101.sample.datastructure.CellPosition;

public record SudokuCell(CellPosition pos, int value, boolean isFixed) {

}
