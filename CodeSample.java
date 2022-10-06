import java.io.*;
import java.util.*;

public class CodeSample {
    static int totalStatesEnqueuedDFS = 0;
    static int totalStatesEnqueuedIDS = 0;

    public static void main(String[] args) {
        String[][] grid = new String[3][3];
        File file = new File(args[1]);
        try {
            Scanner in = new Scanner(file);
            int i = 0, j = 0, row = 0, col = 0;

            for (int r = 0; r < 9; r++) {
                grid[i][j] = in.next();
                if (grid[i][j].equals("*")) {
                    row = i;
                    col = j;
                }
                j++;
                j %= 3;
                if (j == 0)
                    i++;
            }

            if (args[0].equals("dfs")) {
                ArrayList<String> path = new ArrayList<>();
                path.add(gridToString(grid));

                if (!dfs(grid, path, row, col, new HashSet<>(), 10, true))
                    printFailure();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean dfs(String[][] grid, ArrayList<String> path, int row, int col, HashSet<String> visited, int maxDepth, boolean isItDFS) {
        String ord = gridToString(grid);
        if (validate(ord)) {
            printPath(path, isItDFS);
            return true;
        }

        if (path.size() > maxDepth || !visited.add(ord)) {
            if (isItDFS)
                totalStatesEnqueuedDFS--;
            else if (path.size() <= maxDepth)
                totalStatesEnqueuedIDS--;
            return false;
        }

        if (row > 0) {
            String[][] newGrid = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    newGrid[i][j] = grid[i][j];
            }

            String temp = newGrid[row][col];
            newGrid[row][col] = newGrid[row-1][col];
            newGrid[row-1][col] = temp;
            if (isItDFS)
                totalStatesEnqueuedDFS++;
            else if (path.size() == maxDepth)
                totalStatesEnqueuedIDS++;
            path.add(gridToString(newGrid));

            if (dfs(newGrid, path, row-1, col, visited, maxDepth, isItDFS))
                return true;

            path.remove(path.size()-1);
        }

        if (row < 2) {
            String[][] newGrid = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    newGrid[i][j] = grid[i][j];
            }

            String temp = newGrid[row][col];
            newGrid[row][col] = newGrid[row+1][col];
            newGrid[row+1][col] = temp;
            if (isItDFS)
                totalStatesEnqueuedDFS++;
            else if (path.size() == maxDepth)
                totalStatesEnqueuedIDS++;
            path.add(gridToString(newGrid));

            if (dfs(newGrid, path, row+1, col, visited, maxDepth, isItDFS))
                return true;

            path.remove(path.size() - 1);
        }

        if (col > 0) {
            String[][] newGrid = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    newGrid[i][j] = grid[i][j];
            }

            String temp = newGrid[row][col];
            newGrid[row][col] = newGrid[row][col-1];
            newGrid[row][col-1] = temp;
            if (isItDFS)
                totalStatesEnqueuedDFS++;
            else if (path.size() == maxDepth)
                totalStatesEnqueuedIDS++;
            path.add(gridToString(newGrid));

            if (dfs(newGrid, path, row, col-1, visited, maxDepth, isItDFS))
                return true;

            path.remove(path.size()-1);
        }

        if (col < 2) {
            String[][] newGrid = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++)
                    newGrid[i][j] = grid[i][j];
            }

            String temp = newGrid[row][col];
            newGrid[row][col] = newGrid[row][col+1];
            newGrid[row][col+1] = temp;
            if (isItDFS)
                totalStatesEnqueuedDFS++;
            else if (path.size() == maxDepth)
                totalStatesEnqueuedIDS++;
            path.add(gridToString(newGrid));

            if (dfs(newGrid, path, row, col+1, visited, maxDepth, isItDFS))
                return true;

            path.remove(path.size()-1);
        }

        visited.remove(ord);
        return false;
    }

    public static boolean validate(String str) {
        return str.equals("7816*2543");
    }

    public static String gridToString(String[][] grid) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ans.append(grid[i][j]);
            }
        }
        return ans.toString();
    }

    public static void printPath(ArrayList<String> path, boolean isItDFS) {
        StringBuilder out = new StringBuilder();

        int finInd = path.size() - 1;
        for (int i = 0; i <= finInd; i++) {
            String get = path.get(i);
            int ind = 0;
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 2; k++) {
                    out.append(get.charAt(ind++) + " ");
                }
                out.append(get.charAt(ind++) + "\n");
            }
            out.append("\n");
        }

        out.append("Number of moves = " + finInd + "\n");
        out.append("Number of states enqueued = ");
        if (isItDFS)
            out.append(totalStatesEnqueuedDFS);
        else
            out.append(totalStatesEnqueuedIDS);

        System.out.print(out.toString());
    }

    public static void printFailure() {
        System.out.println("Goal state not reached in 10 moves");
    }
}
