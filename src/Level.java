import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Level {
	private int[][] grid;
	
	public Level() {
	
	}
	
	
	public Level(String file) {
        ArrayList<ArrayList<Integer>> tempMap = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
            	
                if (currentLine.isEmpty()) {
                    continue;
                }
                ArrayList<Integer> row = new ArrayList<Integer>();
                String[] values = currentLine.trim().split(" ");

                for(String s : values) {
                    if (!s.isEmpty()) {
                        int id = Integer.parseInt(s);
                        row.add(id);
                    }
                }
                tempMap.add(row);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //get 2d array width and height
        int width = tempMap.get(0).size();
        int height = tempMap.size();

        grid = new int[height][width];
        
        //create new Map of specific size and then fill the 2d array from the ArrayList of ArrayLists
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
            	ArrayList<Integer> row = tempMap.get(i);
                grid[i][j] = row.get(j);
            }
        }
	}

	public int[][] getGrid() {
		return grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}
	

	
}
