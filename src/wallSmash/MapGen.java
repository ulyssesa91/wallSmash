package wallSmash;


import java.awt.*;

public class MapGen {
    public int map[][];
    public int BWidth;
    public int BHeight;
    public MapGen(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        BWidth = 540/col;
        BHeight = 150/row;
    }

    public void draw(Graphics2D g) {
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j] > 0){
                    g.setColor(Color.white);
                    g.fillRect(j * BWidth + 80, i * BHeight + 50, BWidth, BHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * BWidth + 80, i * BHeight + 50, BWidth, BHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
    }

