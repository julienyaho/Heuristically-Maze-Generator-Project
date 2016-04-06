package ie.gmit.sw.ai;

public class Maze {
	private char[][] maze;
	public static int[][] path;
	public static GameRunner runner=new GameRunner();
	int row=0;
	int col=0;
	goal goal=new goal(20,25);
	public Maze(int rows, int cols){
		row=rows;
		col=cols;
		maze = new char[rows][cols];
		path=new int[rows][cols];
		init();
		buildMaze();
		
		int featureNumber = (int)((rows * cols) * 0.01);
		addFeature('W', 'X', featureNumber);
		addFeature('?', 'X', featureNumber);
		addFeature('B', 'X', featureNumber);
		addFeature('H', 'X', featureNumber);
		addFeature('E', 'X', featureNumber);
		
		traverse(1, 0, 1, 1);
		
		for (int i = 0; i < row; i++)
			for (int j = 0; j <  col ; j++)
				if (path[i][j] != 1)
					path[i][j] = -1;
		
	}
	
	public Maze() {
		// TODO Auto-generated constructor stub
	}

	private void init(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = 'X';
			}
		}
	}
	
	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		while (counter < feature){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col] == replace){
				maze[row][col] = feature;
				counter++;
			}
		}
	}
	
	
	private void buildMaze(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				if (num >= 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1] = ' ';
					continue;
				}
				if (row + 1 < maze.length){ //Check south
					maze[row + 1][col] = ' ';
				}				
			}
		}	
	}
	
	public char[][] getMaze(){
		return this.maze;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private boolean traverse(int i1, int j1, int i2, int j2) {
		boolean done = false;

		if (i1 == 1 && j1 == 0)
			path[i1][j1] = 1;

		if (valid(i1, j1, i2, j2)) {
			path[i2][j2] = -1;

			if (i2 == goal.row && j2 == goal.col)
				done = true; 
			else {
				done = traverse(i2, j2, i2 + 1, j2); // down
				if (!done)
					done = traverse(i2, j2, i2, j2 + 1); // right
				if (!done)
					done = traverse(i2, j2, i2 - 1, j2); // up
				if (!done)
					done = traverse(i2, j2, i2, j2 - 1); // left
			}

			if (done) 
				path[i2][j2] = 1;
		} else {
			
		}

		return done;
	}
	
	private boolean valid(int i1, int j1, int i2, int j2) {
		boolean result = false;

		if (i2 >= 0 && i2 < row  && j2 >= 0
				&& j2 < col + 1)
			if (path[i2][j2] == 0) {
				if (i1 == i2 - 1 	&& maze[i2][j2] == ' ')
					result = true;
				else if (i1 == i2 + 1 	&& maze[i2][j2] == ' ')
					result = true;
				else if (j1 == j2 - 1 	&& maze[i2][j2] == ' ')
					result = true;
				else if (j1 == j2 + 1 	&& maze[i2][j2] == ' ')
					result = true;
			}
		return result;
	}
}