import java.io.*;
import java.util.*;


public class Main {

    static int totalStatesEnqueuedDFS = 0;
    static int totalStatesEnqueuedIDS = 0;
    static int totalStatesEnqueuedAstar1 = 0;
    static int totalStatesEnqueuedAstar2 = 0;
    public static int N = 3;
    public static int[] row = { 1, 0, -1, 0 };
    public static int[] col = { 0, -1, 0, 1 };


    public static class Node
    {
        Node parent;
        int[][] mat = new int[N][N];
        int x, y;
        int cost;
        int level;
    }


    public static class comp implements Comparator<Node>
    {
        @Override
        public int compare(Node lhs, Node rhs){
            return (lhs.cost + lhs.level) > (rhs.cost + rhs.level) ? 1 : -1;
        }
    }

    public static class comp1 implements Comparator<Node>
    {
        @Override
        public int compare(Node lhs, Node rhs){
            if (lhs.level > rhs.level)
            {
                return 1;
            }
            else if (rhs.level > lhs.level)
            {
                return -1;
            }
            else
            {
                return (lhs.cost > rhs.cost) ? 1 : -1;
            }
        }
    }


    public static void main(String[] args)
    {
        String[][] grid = new String[3][3];
        File file = new File(args[1]);
        try {
            Scanner in = new Scanner(file);
            int i = 0;
            int j = 0;
            int row = 0;
            int col = 0;

            for (int r = 0; r < 9; r++)
            {
                grid[i][j] = in.next();
                if (grid[i][j].equals("*"))
                {
                    row = i;
                    col = j;
                }
                j++;
                j %= 3;
                if (j == 0)
                    i++;
            }

            if (args[0].equals("dfs"))
            {
                ArrayList<String> path = new ArrayList<>();
                path.add(gridToString(grid));
                HashSet<String> visited = new HashSet<>();

                if (!dfs(grid, path, row, col, visited, 10, true))
                    printFailure();
            }
            else if (args[0].equals("ids"))
            {
                ArrayList<String> path = new ArrayList<>();
                path.add(gridToString(grid));

                if (!ids(grid, row, col))
                    printFailure();
            }
            else if (args[0].equals("astar1"))
            {
                int finalMat[][] =
                {
                    {7, 8, 1},
                    {6, 0, 2},
                    {5, 4, 3}
                };

                int[][] initialMat = new int[3][3];
                for (int h = 0; h < 3; h++)
                {
                    for (int l = 0; l < 3; l++)
                    {
                        if (grid[h][l].equals("*"))
                        {
                            initialMat[h][l] = 0;
                        }
                        else
                        {
                            initialMat[h][l] = Integer.parseInt(grid[h][l]);
                        }
                    }
                }

                if (!solve(initialMat, row, col, finalMat))
                    printFailure();
            }
            else if (args[0].equals("astar2"))
            {
                int finalMat[][] =
                {
                    {7, 8, 1},
                    {6, 0, 2},
                    {5, 4, 3}
                };

                int[][] initialMat = new int[3][3];
                for (int h = 0; h < 3; h++)
                {
                    for (int l = 0; l < 3; l++)
                    {
                        if (grid[h][l].equals("*"))
                        {
                            initialMat[h][l] = 0;
                        }
                        else
                        {
                            initialMat[h][l] = Integer.parseInt(grid[h][l]);
                        }
                    }
                }

                if (!solve1(initialMat, row, col, finalMat))
                    printFailure();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static boolean dfs(String[][] grid, ArrayList<String> path, int row, int col, HashSet<String> visited, int maxDepth, boolean isItDFS)
    {
        String ord = gridToString(grid);
        if (validate(ord))
        {
            printPath(path, isItDFS);
            return true;
        }

        if (path.size() > maxDepth || !visited.add(ord))
        {
            if (isItDFS)
                totalStatesEnqueuedDFS--;
            else if (path.size() <= maxDepth)
                totalStatesEnqueuedIDS--;
            return false;
        }

        if (row > 0)
        {
            String[][] newGrid = new String[3][3];
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                    newGrid[i][j] = grid[i][j];
            }

            String temp = newGrid[row][col];
            newGrid[row][col] = newGrid[row-1][col];
            newGrid[row-1][col] = temp;
            if (isItDFS)
                totalStatesEnqueuedDFS++;
            else
            {
                if (path.size() == maxDepth)
                    totalStatesEnqueuedIDS++;
            }
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
            else
            {
                if (path.size() == maxDepth)
                    totalStatesEnqueuedIDS++;
            }
            path.add(gridToString(newGrid));

            if (dfs(newGrid, path, row+1, col, visited, maxDepth, isItDFS))
                return true;

            path.remove(path.size() - 1);
        }

        if (col > 0)
        {
            String[][] newGrid = new String[3][3];
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                    newGrid[i][j] = grid[i][j];
            }

            String temp = newGrid[row][col];
            newGrid[row][col] = newGrid[row][col-1];
            newGrid[row][col-1] = temp;
            if (isItDFS)
                totalStatesEnqueuedDFS++;
            else
            {
                if (path.size() == maxDepth)
                    totalStatesEnqueuedIDS++;
            }
            path.add(gridToString(newGrid));

            if (dfs(newGrid, path, row, col-1, visited, maxDepth, isItDFS))
                return true;

            path.remove(path.size()-1);
        }

        if (col < 2)
        {
            String[][] newGrid = new String[3][3];
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                    newGrid[i][j] = grid[i][j];
            }

            String temp = newGrid[row][col];
            newGrid[row][col] = newGrid[row][col+1];
            newGrid[row][col+1] = temp;
            if (isItDFS)
                totalStatesEnqueuedDFS++;
            else
            {
                if (path.size() == maxDepth)
                    totalStatesEnqueuedIDS++;
            }
            path.add(gridToString(newGrid));

            if (dfs(newGrid, path, row, col+1, visited, maxDepth, isItDFS))
                return true;

            path.remove(path.size()-1);
        }

        visited.remove(ord);

        return false;
    }


    public static boolean ids(String[][] grid, int row, int col)
    {
        for (int i = 1; i < 11; i++)
        {
            ArrayList<String> newIterate = new ArrayList<>();
            newIterate.add(gridToString(grid));
            if (dfs(grid, newIterate, row, col, new HashSet<String>(), i, false))
                return true;
        }
        return false;
    }


    public static Node newNode(int mat[][], int x, int y, int newX, int newY, int level, Node parent)
    {
        Node node = new Node();
        node.parent = parent;

        node.mat = new int[N][N];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                node.mat[i][j] = mat[i][j];
            }
        }

        int temp = node.mat[x][y];
        node.mat[x][y] = node.mat[newX][newY];
        node.mat[newX][newY]=temp;

        node.cost = Integer.MAX_VALUE;
        node.level = level;

        node.x = newX;
        node.y = newY;

        return node;
    }


    public static int calculateCost(int initialMat[][], int finalMat[][])
    {
        int count = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (initialMat[i][j]!=0 && initialMat[i][j] != finalMat[i][j])
                    count++;
        return count;
    }


    public static int[] manhattan(int val, int[][] finalMat)
    {
        int[] ans = new int[2];
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (finalMat[i][j] == val)
                {
                    ans[0] = i;
                    ans[1] = j;
                    return ans;
                }
            }
        }
        return ans;
    }


    public static int calculateCost1(int[][] initialMat, int[][] finalMat)
    {
        int cost = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (initialMat[i][j] != 0)
                {
                    int[] get = manhattan(initialMat[i][j], finalMat);
                    cost += Math.abs(get[0] - i);
                    cost += Math.abs(get[1] - j);
                }
            }
        return cost;
    }


    public static int isSafe(int x, int y)
    {
        return (x >= 0 && x < N && y >= 0 && y < N) ? 1 : 0;
    }


    public static boolean solve(int initialMat[][], int x, int y, int finalMat[][])
    {
        PriorityQueue<Node> pq = new PriorityQueue<>(new comp());

        Node root = newNode(initialMat, x, y, x, y, 0, null);
        root.cost = calculateCost(initialMat,finalMat);

        pq.add(root);

        while(!pq.isEmpty())
        {
            Node min = pq.peek();
            pq.poll();

            if(min.cost == 0){
                printPath(min, true);
                return true;
            }

            for (int i = 0; i < 4; i++)
            {
                if (isSafe(min.x + row[i], min.y + col[i])>0)
                {
                    if (min.level + 1 > 10)
                        continue;
                    Node child = newNode(min.mat, min.x, min.y, min.x + row[i],min.y + col[i], min.level + 1, min);
                    child.cost = calculateCost(child.mat, finalMat);
                    totalStatesEnqueuedAstar1++;

                    pq.add(child);
                }
            }
        }
        return false;
    }


    public static boolean solve1(int initialMat[][], int x, int y, int finalMat[][])
    {
        PriorityQueue<Node> pq = new PriorityQueue<>(new comp());

        Node root = newNode(initialMat, x, y, x, y, 0, null);
        root.cost = calculateCost1(initialMat,finalMat);

        pq.add(root);

        while(!pq.isEmpty())
        {
            Node min = pq.peek();
            pq.poll();

            if(min.cost == 0){
                printPath1(min, true);
                return true;
            }

            for (int i = 0; i < 4; i++)
            {
                if (isSafe(min.x + row[i], min.y + col[i])>0)
                {
                    if (min.level + 1 > 10)
                        continue;
                    Node child = newNode(min.mat, min.x, min.y, min.x + row[i],min.y + col[i], min.level + 1, min);
                    child.cost = calculateCost1(child.mat, finalMat);
                    totalStatesEnqueuedAstar2++;

                    pq.add(child);
                }
            }
        }
        return false;
    }


    public static boolean validate(String str)
    {
        return str.equals("7816*2543");
    }


    public static String gridToString(String[][] grid)
    {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                ans.append(grid[i][j]);
            }
        }
        return ans.toString();
    }


    public static void printPath(ArrayList<String> path, boolean isItDFS)
    {
        StringBuilder out = new StringBuilder();

        String init = path.get(0);
        for (int i = 0; i < 3; i++)
            out.append(init.charAt(i) + " ");
        out.append("(Initial input state)\n");

        int ind = 3;

        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                out.append(init.charAt(ind++) + " ");
            }
            out.append(init.charAt(ind++) + "\n");
        }
        out.append("\n");

        int finInd = path.size() - 1;
        for (int i = 1; i < finInd; i++)
        {
            String get = path.get(i);
            ind = 0;
            for (int j = 0; j < 3; j++)
            {
                for (int k = 0; k < 2; k++)
                {
                    out.append(get.charAt(ind++) + " ");
                }
                out.append(get.charAt(ind++) + "\n");
            }
            out.append("\n");
        }

        String goal = path.get(finInd);
        for (int i = 0; i < 3; i++)
            out.append(goal.charAt(i) + " ");
        out.append("(Goal state)\n");

        ind = 3;

        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                out.append(goal.charAt(ind++) + " ");
            }
            out.append(goal.charAt(ind++) + "\n");
        }
        out.append("\n");

        out.append("Number of moves = " + finInd + "\n");
        out.append("Number of states enqueued = ");
        if (isItDFS)
        {
            out.append(totalStatesEnqueuedDFS);
        }
        else
        {
            out.append(totalStatesEnqueuedIDS);
        }

        System.out.print(out.toString());
    }


    public static void printPath(Node root, boolean last){
        if(root.parent == null){
            printFirstMatrix(root.mat);
            return;
        }
        printPath(root.parent, false);
        if (last)
        {
            printLastMatrix(root.mat);
        }
        else
        {
            printMatrix(root.mat);
            System.out.println("");
            return;
        }
        System.out.println("");
        System.out.println("Number of moves = " + root.level);
        System.out.println("Number of states enqueued = " + totalStatesEnqueuedAstar1);
    }


    public static void printPath1(Node root, boolean last){
        if(root.parent == null){
            printFirstMatrix(root.mat);
            return;
        }
        printPath(root.parent, false);
        if (last)
        {
            printLastMatrix(root.mat);
        }
        else
        {
            printMatrix(root.mat);
            System.out.println("");
            return;
        }
        System.out.println("");
        System.out.println("Number of moves = " + root.level);
        System.out.println("Number of states enqueued = " + totalStatesEnqueuedAstar2);
    }


    public static void printFirstMatrix(int mat[][])
    {
        StringBuilder out = new StringBuilder();
        for (int i = 0 ; i < 3; i++)
        {
            if (mat[0][i] != 0)
                out.append(mat[0][i] + " ");
            else
                out.append("* ");
        }
        out.append("(Initial input state)\n");

        for (int i = 1; i < N; i++){
            for(int j = 0; j < N; j++){
                if (mat[i][j] != 0)
                    out.append(mat[i][j] + " ");
                else
                    out.append("* ");
            }
            out.append("\n");
        }
        System.out.println(out.toString());
    }


    public static void printLastMatrix(int mat[][])
    {
        StringBuilder out = new StringBuilder();
        for (int i = 0 ; i < 3; i++)
        {
            if (mat[0][i] != 0)
                out.append(mat[0][i] + " ");
            else
                out.append("* ");
        }
        out.append("(Goal state)\n");

        for (int i = 1; i < N; i++){
            for(int j = 0; j < N; j++){
                if (mat[i][j] != 0)
                    out.append(mat[i][j] + " ");
                else
                    out.append("* ");
            }
            out.append("\n");
        }
        System.out.print(out.toString());
    }


    public static void printMatrix(int mat[][])
    {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if (mat[i][j] != 0)
                    out.append(mat[i][j] + " ");
                else
                    out.append("* ");
            }
            out.append("\n");
        }
        System.out.print(out.toString());
    }


    public static void printFailure()
    {
        System.out.println("Goal state not reached in 10 moves");
    }

}
