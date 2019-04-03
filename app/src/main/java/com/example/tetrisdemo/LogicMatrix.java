package com.example.tetrisdemo;

import android.util.Log;

import java.util.Random;

public class LogicMatrix {

    private int matrix[][];
    private int figure[];
    /*
    figure:
        0|0|0
        0|0|0
        x|0|0
        coordinates represent point(0,2)
     */
    private int startingPosition;


    public LogicMatrix(int rows, int columns){
        startingPosition = columns/2 - 1; //mitad del tablero
        matrix = new int[columns][rows];
        figure = new int[2];
        newFigure();




        int y2 = 0;
        int x2 = 0;

        for(int count = 1 ; count != 3 ; ++count) {
            for (int x = 0; x < columns; ++x) {
                matrix[x][y2] = 2;
            }
            for (int y = 0; y < rows; ++y) {
                matrix[x2][y] = 2;
            }
            y2 = rows-1;
            x2 = columns-1;
        }
    }


    public boolean spaceOccupied(int x, int y){
        if(matrix[x][y] != 0){
            return true;
        }
        return false;
    }

    //Enum de los tipos de figuras
    public enum FigureType{
        SQUARE, L, REVERSE_L, Z, REVERSE_Z, I, T;

        private static final FigureType lista [] = FigureType.values();
        private static Random random = new Random();

        public static FigureType getRandom(){
            int size = lista.length;
            return lista[random.nextInt(size)];
        }
    }



    public boolean gameCycle(){
        boolean figurePaused = moveFigure();
        boolean endgame = false;
        if(!figurePaused){
            moveToNext();
        } else{
            endgame = newFigure();
        }

        return endgame;
    }

    //Pinta una figura random en el centro del matrix.

    private boolean newFigure(){
        this.figure[0] = startingPosition;
        this.figure[1] = 3;

        int x = figure[0];
        int y = figure[1];

        boolean endGame = false;
        for(int ya = y-2 ; ya < y+2 ; ++ya){
            for(int xa = x ; xa < x+3 ; ++xa){
                if(matrix[xa][ya] != 0){
                    endGame = true;
                }
            }
        }

        if(!endGame){
            printFigure(FigureType.getRandom());
        }

        return endGame;
    }

    private void printFigure(FigureType figureType){
        int x = figure[0];
        int y = figure[1];

        switch(figureType){
            case SQUARE:
                matrix[x+1][y] = 1;
                matrix[x+2][y] = 1;
                matrix[x+1][y-1] = 1;
                matrix[x+2][y-1] = 1;
                break;

            case I:
                matrix[x+1][y] = 1;
                matrix[x+2][y] = 1;
                matrix[x+1][y-1] = 1;
                matrix[x+2][y-1] = 1;
                break;

            case L:
                matrix[x+1][y] = 1;
                matrix[x+2][y] = 1;
                matrix[x+1][y-1] = 1;
                matrix[x+1][y-2] = 1;

                break;

            case T:
                matrix[x][y] = 1;
                matrix[x+1][y] = 1;
                matrix[x+2][y] = 1;
                matrix[x+1][y-1] = 1;

                break;

            case Z:
                matrix[x+1][y] = 1;
                matrix[x+2][y] = 1;
                matrix[x][y-1] = 1;
                matrix[x+1][y-1] = 1;

                break;

            case REVERSE_L:
                matrix[x+1][y] = 1;
                matrix[x][y] = 1;
                matrix[x+1][y-1] = 1;
                matrix[x+1][y-2] = 1;

                break;

            case REVERSE_Z:
                matrix[x+1][y] = 1;
                matrix[x][y] = 1;
                matrix[x+2][y-1] = 1;
                matrix[x+1][y-1] = 1;

                break;
        }

    }


    //Mueve la figura a la siguiente posicion tomando en cuenta las excepciones.

    private boolean moveFigure(){
        boolean stopFigure = false;
        final int x = figure[0];
        final int y = figure[1];



        //Checks if the figure has arrive to a collision with another figure.
        for(int figurePoint = x ; figurePoint<x+3 ; ++figurePoint){
            if(matrix[figurePoint][y+1]!=0 && matrix[figurePoint][y]==1){
                stopFigure = true;
            }
        }

        return stopFigure;
    }

    private void moveToNext(){
        int lastX = figure[0]+2;
        int firstY = figure[1]-2;

        for(int y = figure[1] ; y >= firstY ; --y){
            for(int x = figure[0] ; x <= lastX ; ++x){
                if(matrix[x][y] == 1){
                    if(matrix[x][y+1] == 0){
                        matrix[x][y+1] = 1;
                    }
                    matrix[x][y] = 0;
                }
            }
        }

        figure[1] = ++figure[1];
    }

/*
    private void checkCompletion(){
        final int rows = matrix[0].length;
        final int maxColums = matrix[1].length;
        boolean completed = false;
        int winRows = 0;

        for(int y = maxColums; y > maxColums-3 ; --y){
            for (int x = 0; x < rows ; ++x){
                if(matrix[x][y]){
                    completed = true;
                }
            }
            if(completed){
                ++winRows;
            }
        }

        for(int y = maxColums-winRows; y > 0 ; --y){
            for (int x = 0; x < rows ; ++x){
                matrix[x][maxColums] = false;
            }
        }
    }
*/


    public void moveRight(){
        int firstY = figure[1]-2;
        boolean border = false;

        for(int y = figure[1] ; y < figure[1]-2 ; --y){
            if(matrix[figure[0]+3][y] == 1){

            }
        }

        for(int y = figure[1] ; y >= firstY ; --y){
            for(int x = figure[0]+2 ; x >= figure[0] ; --x){
                if(matrix[x][y] == 1){
                    if(matrix[x+1][y] == 0){
                        matrix[x+1][y] = 1;
                    }
                    matrix[x][y] = 0;
                }
            }
        }

        figure[0] = ++figure[0];
    }

    public void moveLeft(){
        int lastX = figure[0]+2;
        int firstY = figure[1]-2;

        for(int y = figure[1] ; y >= firstY ; --y){
            for(int x = figure[0] ; x <= lastX ; ++x){
                if(matrix[x][y] == 1){
                    if(matrix[x-1][y] == 0){
                        matrix[x-1][y] = 1;
                    }
                    matrix[x][y] = 0;
                }
            }
        }

        figure[0] = --figure[0];
    }


    public void rotate(int direction){



        int tempX = 0;
        int tempY = 2;

        int tempFigure [][] = new int[3][3];

        if(direction == 1){

            for(int x = figure[0]; x < 3 ; ++x){
                for(int y  = figure[1] ; y < 3 ; ++y){

                }
            }

        } else {

        }


/*
        for(int ya = y ; ya > y-3 ; --ya){
            for(int xa = x ; xa < x+3 ; ++xa){
                Log.i("msj", Integer.toString(tempX));
                Log.i("msj", Integer.toString(tempY));

                matrix[xa][ya] = tempFigure[tempX][tempY];
                ++tempX;
            }
            tempX = 0;
            --tempY;
        }
        */
    }
}
