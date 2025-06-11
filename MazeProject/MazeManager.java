import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
//Halil burdaki kodları dikkatle incele anlamadığını beraber çalışam hoca sorabilir ben rotatingrows için donsatlist diye bi class oluşturmuşum
//kusura bakma ya adları çok şey yapmamıştım meğer belgede isimler de varmış düzenlemeye çalışcam
public class MazeManager {
    public  final MazeTile[][] mazeGrid;
    public  final int width;
    public  final int height;
    public  final donsatlist rotatingRows;
    public  int goalX = -1;
    public  int goalY = -1;
    

    public MazeManager(int width, int height) {
        this.width = width;
        this.height = height;
        this.mazeGrid = new MazeTile[height][width];
        this.rotatingRows = new donsatlist();
        initializeMaze();
    }
    
    private void initializeMaze() {
        Random rand = new Random();
        char[] tileTypes = {'E', 'W', 'T', 'P'};
        
        //eastgele labirent oluşturmak için matris dizinin satır ve sütunlarını oluşturur 
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mazeGrid[y][x] = new MazeTile(tileTypes[rand.nextInt(tileTypes.length)], x, y);
            }
        }

        // rastgele bir noktaya tek bir hedef koymak için
        placeGoal(rand);
        
        //dönen zımbırtıyı initialez etmek içn bunu kullancaz
        initializeRotatingRows(rand);
    }

    private void placeGoal(Random rand) {
        goalX = rand.nextInt(width);
        goalY = rand.nextInt(height);
        mazeGrid[goalY][goalX] = new MazeTile('G', goalX, goalY);
    }

    private void initializeRotatingRows(Random rand) {
        while (rotatingRows.getRotatingRowCount() < 2) {
            int row = rand.nextInt(height);
            if (!rotatingRows.contains(row)) {
                CircularLinkedList<MazeTile> rowList = new CircularLinkedList<>();
                for (int x = 0; x < width; x++) {
                    rowList.append(mazeGrid[row][x]);
                }
                rotatingRows.addRow(row, rowList);
            }
        }
    }

    public void rotateRandomCorridor() {
        int[] availableRows = rotatingRows.getAllIndexes();
        if (availableRows.length > 0) {
            int rowToRotate = availableRows[new Random().nextInt(availableRows.length)];
            rotateCorridor(rowToRotate);
        }
    }

    public void rotateCorridor(int rowId) {
        if (!rotatingRows.contains(rowId)) return;
        
        CircularLinkedList<MazeTile> row = rotatingRows.getRow(rowId);
        row.rotate();
        
        // döndür satırı labirente geri ekle 
        MazeTile[] rotatedRow = new MazeTile[width];
        row.toArray(rotatedRow);
        System.arraycopy(rotatedRow, 0, mazeGrid[rowId], 0, width);
    }

    public boolean isValidMove(Agent agen, int x, int y, String direction) {
        int newX = x, newY = y;
        
        switch (direction.toUpperCase()) {
            case "UP" -> newY--;
            case "DOWN" -> newY++;
            case "LEFT" -> newX--;
            case "RIGHT" -> newX++;
            default -> { return false; }
        }
        
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return false;
        }
        
        MazeTile targetTile = mazeGrid[newY][newX];
        if (targetTile.getType() == 'T') {
            agen.triggerTrap();
            return false;
        }
        return targetTile.isTraversable() && !targetTile.hasAgent();
    }

    public void updateAgentPosition(Agent agent, int oldX, int oldY) {
        if (isValidPosition(oldX, oldY)) {
            mazeGrid[oldY][oldX].removeAgent();
        }
        if (isValidPosition(agent.getCurrentX(), agent.getCurrentY())) {
            mazeGrid[agent.getCurrentY()][agent.getCurrentX()].placeAgent();
        }
    }
    //labirentin durumunu yazdırıyoz burda
    public void printMaze() {
        System.out.println("\n=== CURRENT MAZE ===");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(mazeGrid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println("Legend: A=Agent, G=Goal, P=Power-up, T=Trap, E=Empty, W=Wall");
    }
    //halil bu mazesnapshotı senin kodlarında kullanman gerekiyo sanırım ona göre eklersin
    public void printMazeSnapshot(FileWriter logFile) throws IOException {           //burda dosyaya yazdırabiliyomuşuz log çıktısı vermek için txtli bi zımbırtı oluşturcaz
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                MazeTile tile = mazeGrid[y][x];
                char c = tile.hasAgent() ? 'A' : tile.getType();
                logFile.write(c + " ");
            }
            logFile.write("\n");
        }
    }

    public MazeTile getTile(int x, int y) {
        return isValidPosition(x, y) ? mazeGrid[y][x] : null;
    }

    public int[] getRotatingRowIndexes() {
        return rotatingRows.getAllIndexes();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getGoalX() { return goalX; }
    public int getGoalY() { return goalY; }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}

