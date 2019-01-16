package saveWesteros;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import generic.Operator;
import generic.Problem;
import generic.State;

public class westerosProblem extends Problem {

	public int n;
	public int m;
	public Cell[][] grid;
	String[] types = { "F", "WW", "O" };

	public westerosProblem(int N, int M) throws IOException {

		this.n = N;
		this.m = M;
		BufferedWriter br = new BufferedWriter(new FileWriter("KnowledgeBase.pl"));
		StringBuilder sb = new StringBuilder();

		this.grid = new Cell[this.n][this.m];
		sb.append("jon(location(" + (this.n - 1) + "," + (this.m - 1) + "), 0, s0).");
		sb.append("\n");
		this.grid[this.n - 1][this.m - 1] = new Cell(this.types[(int) (Math.random() * 3)]);
		this.grid[this.n - 1][this.m - 1].type = "JS";
		while (true) {
			int x = (int) (Math.random() * 3);
			int y = (int) (Math.random() * 3);
			if (x != 2 && y != 2) {
				this.grid[x][y] = new Cell(this.types[(int) (Math.random() * 3)]);
				this.grid[x][y].type = "DS";
				sb.append("dS(location(" + x + "," + y + ")).");
				sb.append("\n");
				break;
			}
		}
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				if (this.grid[i][j] == null) {
					this.grid[i][j] = new Cell(this.types[(int) (Math.random() * 3)]);
					if (this.grid[i][j].type == "WW") {
						sb.append("whiteWalkers(location(" + i + "," + j + ") , s0).");
						sb.append("\n");
					} else if (this.grid[i][j].type == "O") {
						sb.append("obst(location(" + i + "," + j + ")).");
						sb.append("\n");
					}

				}
			}
		}
		for (int i = 0; i < this.grid.length; i++) {
			sb.append("rows(" + i + ").");
			sb.append("\n");
		}
		for (int j = 0; j < this.grid[0].length; j++) {
			sb.append("columns(" + j + ").");
			sb.append("\n");
		}

		br.write(sb.toString());
		br.close();
		this.grid[n - 1][m - 1].type = "Jon";
		operators = new ArrayList<>();
		operators.add(new westerosOperators(-1, 0, "North", 1));
		operators.add(new westerosOperators(0, -1, "West", 1));
		operators.add(new westerosOperators(1, 0, "South", 1));
		operators.add(new westerosOperators(0, -1, "East", 1));
		operators.add(new westerosOperators(0, 0, "Kill", 20));
	}

	@Override
	public ArrayList<Operator> getOperators() {
		return operators;
	}

	@Override
	public State getInitialState() {

		return new westerosState(n - 1, m - 1, grid, 0);
	}

	@Override
	public boolean testGoal(State state) {

		westerosState testState = (westerosState) state;
		if (testState.whiteWalkersCount == 0) {
			return true;
		}
		return false;
	}

	public String GridtoString() {
		String count = "";
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				if (this.grid[i][j].type == "Free")
					count += this.grid[i][j].type + "\t\t";
				else
					count += this.grid[i][j].type + "\t";
			}
			count += "\n";
		}

		return count;
	}

}