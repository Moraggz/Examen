package examen3;
import java.util.ArrayList;

public class Raton {
	public static void main(String[] args) {
		
        char[][] laberinto={
        	{'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'},
        	{'1','S','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','1'},
        	{'1','1','1','1','1','1','1','0','1','1','1','1','1','1','0','1','1','1','0','1'},
        	{'1','1','1','1','1','1','1','0','1','1','1','1','1','1','0','1','1','1','0','1'},
        	{'1','1','1','0','0','0','0','0','0','0','0','1','1','1','0','1','1','1','0','1'},
        	{'1','1','1','0','1','1','1','1','1','1','0','0','1','1','1','1','1','1','0','1'},
        	{'1','1','1','0','1','1','1','1','1','1','1','0','0','0','0','0','0','0','0','1'},
        	{'1','1','1','0','0','0','0','0','1','1','1','0','1','1','1','1','1','1','1','1'},
        	{'1','1','1','1','1','1','1','0','1','1','1','0','1','1','1','1','1','1','1','1'},
        	{'1','1','1','1','1','0','0','0','1','1','1','0','0','0','1','1','1','1','1','1'},
        	{'1','1','1','1','1','0','1','1','1','1','1','1','0','1','1','1','1','1','1','1'},
        	{'1','1','1','1','0','0','1','1','1','1','1','1','0','0','0','0','0','1','1','1'},
        	{'1','1','1','1','0','1','1','1','1','1','1','1','1','1','0','1','1','1','1','1'},
        	{'1','0','1','1','0','1','1','1','1','1','F','F','1','1','0','1','1','1','1','1'},
        	{'1','0','0','1','0','1','1','1','1','1','F','F','1','1','0','1','1','1','1','1'},
        	{'1','0','0','0','0','1','1','1','1','1','1','0','1','1','0','1','1','1','1','1'},
        	{'1','1','1','1','0','1','1','1','1','1','1','0','1','1','0','1','1','1','1','1'},
        	{'1','1','1','1','0','1','1','1','1','1','1','0','1','1','1','1','1','1','1','1'},
        	{'1','1','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','1','1'},
        	{'1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1','1'}
        };

        Raton raton= new Raton(laberinto);
        ArrayList<String> ruta= raton.encontrarRuta();
        System.out.println("Movimientos: "+ruta);
    }
	
    private char[][] laberinto;
    private int filas;
    private int columnas;
    private ArrayList<String> movimientos;

    public Raton(char[][] laberinto) {
        this.laberinto = laberinto;
        this.filas = laberinto.length;
        this.columnas = laberinto[0].length;
        this.movimientos = new ArrayList<>();
    }
    
    public ArrayList<String> encontrarRuta() {
        int filaInicio=-1;
        int columnaInicio=-1;

        for (int i=0;i<filas;i++) {
            for (int j=0;j<columnas;j++) {
                if (laberinto[i][j]=='S') {
                    filaInicio=i;
                    columnaInicio=j;
                    break;
                }
            }
        }

        if (filaInicio == -1 || columnaInicio == -1) {
            System.out.println("No se encontro la posicion inicial del raton.");
            return null;
        }
        
        encontrarRutaLaberinto(filaInicio, columnaInicio);
        return movimientos;
    }

    private boolean encontrarRutaLaberinto(int fila, int columna) {
        if (fila<0 || fila>=filas || columna<0 || columna>=columnas || laberinto[fila][columna]=='1') {
            return false;
        }

        if (laberinto[fila][columna]=='F') {
            return true;
        }

        laberinto[fila][columna]='1';

        movimientos.add("arriba");
        if (encontrarRutaLaberinto(fila-1,columna)) {
            return true;
        }
        movimientos.remove(movimientos.size()-1);

        movimientos.add("abajo");
        if (encontrarRutaLaberinto(fila+1,columna)) {
            return true;
        }
        movimientos.remove(movimientos.size()-1);

        movimientos.add("derecha");
        if (encontrarRutaLaberinto(fila,columna+1)) {
            return true;
        }
        movimientos.remove(movimientos.size()-1);

        movimientos.add("izquierda");
        if (encontrarRutaLaberinto(fila,columna-1)) {
            return true;
        }
        movimientos.remove(movimientos.size()-1);

        laberinto[fila][columna]='0'; 
        return false;
    }
}
