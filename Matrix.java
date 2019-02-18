import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Matrix {
    char m[][]; //matriz
    int[] pos;  //array com as alturas de cada coluna
    char ai,person;  //quem é o X e o O
    Matrix(char ai,char player){ //construtor da primeira matriz
        this.ai=ai;
        person = player;
        m = new char[8][9];         //8linhas 9colunas para ter "paredes"
        pos = new int[8];           //nº de peças em cada coluna
        Arrays.fill(pos,6);         //preencher a pos[] com 6 para as alturas da coluna, ao contrario
        for(int i=0;i<8;i++){
            for(int j=0;j<9;j++) {
                m[i][j] = '-';
                if (i == 0 || i == 7 || j == 0 || j == 8)
                    m[i][j] = '#';  //parede

            }
        }
    }
    Matrix(char [][] m,int[] pos,char ai,char player){ //contrutor das matrizes que nao sao a primeira
        this.ai = ai;
        person = player;
        this.m = m;
        this.pos=pos;
    }

    //verifica se uma certa coluna está cheia
    public boolean is_full(int column){
        if(pos[column]==0)
            return true;
        return false;
    }

    //verifica se todas a colunas estão cheias
    public boolean all_is_full(){
        for(int i=1;i<8;i++)
            if(!(pos[i]==0)) return false;
        return true;
    }

    //copiar a matriz para criar novos nós
    public char[][] copy_m(){
        char[][] tmp = new char[8][9];
        for(int i=0;i<8;i++){
            for(int j=0;j<9;j++){
                tmp[i][j]=m[i][j];
            }
        }
        return tmp;
    }

    //copiar a pos para criar novos nos
    public int[] copy_pos(){
        int[] tmp = new int[8];
        for(int i=0;i<8;i++)
            tmp[i]=pos[i];
        return tmp;
    }

    //adicionar peças à matriz, dada uma coluna e um jogador
    public Matrix add(int col,char c){
        char[][] tmp_m = copy_m();
        int[] tmp_pos = copy_pos();
        if(is_full(col)) {
            System.out.println("Coluna cheia");
        }
        else{
            tmp_m[pos[col ]][col] = c;   //caracter especifico de cada jogador
            tmp_pos[col ]--;           //"altura" da coluna onde vai cair a peça
        }
        Matrix new_m = new Matrix(tmp_m, tmp_pos,ai,person);
        return new_m;
    }

    //deteta se a matriz é terminal
    public int is_Terminal(){
        int tmp = horizontal();
        if(tmp==512 || tmp==-512) return tmp;//alguem ganhou  na horizontal?
        tmp = vertical();
        if(tmp==512 || tmp==-512) return tmp;//alguem ganhou  na vertical?
        tmp = diagonal_p();
        if(tmp==512 || tmp==-512) return tmp;//alguem ganhou  na diagonal principal?
        tmp = diagonal_s();
        if(tmp==512 || tmp==-512) return tmp;//alguem ganhou  na diagonal secundaria?
        if(all_is_full()) return 0; //se ninguem ganhou e a maztriz está cheia entao é um empate
        return -1; // se nenhum dois anteriores , continuar a jogar
    }

    //retorna a pontuaçao da matriz
    public int Utility(){
        return horizontal()+vertical()+diagonal_p()+diagonal_s();
    }

    //retorna a pontuaçao horizontal da matriz
    public int horizontal(){
        int cp,cai;
        int score=0;
        for(int i=1;i<7;i++){
            for(int j=1;j<5;j++){
                cp=0;
                cai=0;
                for(int k=j;k<j+4;k++){
                    if(m[i][k]==person) cp++;
                    if(m[i][k]==ai) cai++;
                }
                if(cp==0 && cai!=0){//se tiver apenas peças da pessoa
                    if(cai==1)score+=1;  //uma peça
                    if(cai==2)score+=10; //duas peças
                    if(cai==3)score+=50; //tres peças
                    if(cai==4)return 512; //a pessoa ganhou ganhou
                }
                if(cai==0 && cp!=0){ //se tiver apenas peças da ia
                    if(cp==1)score-=1;   //uma peça
                    if(cp==2)score-=10;  //duas peças
                    if(cp==3)score-=50;  //tres peças
                    if(cp==4)return -512;//a ia ganhou
                }
            }
        }
        return score;
    }

    //retorna a pontuaçao vertical da matriz
    public int vertical(){
        int cp,cai;
        int score=0;
        for(int i=1;i<8;i++){
            for(int j=1;j<4;j++){
                cp=0;
                cai=0;
                for(int k=j;k<(j+4);k++){
                    if(m[k][i]==person) cp++;
                    if(m[k][i]==ai) cai++;
                }
                if(cp==0 && cai!=0){//se tiver apenas peças da pessoa
                    if(cai==1)score+=1;  //uma peça
                    if(cai==2)score+=10; //duas peças
                    if(cai==3)score+=50; //tres peças
                    if(cai==4)return 512; //a pessoa ganhou ganhou
                }
                if(cai==0 && cp!=0){ //se tiver apenas peças da ia
                    if(cp==1)score-=1;   //uma peça
                    if(cp==2)score-=10;  //duas peças
                    if(cp==3)score-=50;  //tres peças
                    if(cp==4)return -512;//a ia ganhou
                }
            }
        }
        return score;
    }

    //retorna a pontuaçao diagonal (principal) da matriz
    public int diagonal_p(){
        int cp,cai; //contador da pessoa,contador da ia
        int score=0;//pontuaçao total
        for(int i=1;i<4;i++){
            for(int j=1;j<5;j++){
                cp=0;
                cai=0;
                for(int k=0;k<4;k++){
                    if(m[i+k][j+k]==person) cp++;
                    if(m[i+k][j+k]==ai) cai++;
                }
                if(cp==0 && cai!=0){//se tiver apenas peças da pessoa
                    if(cai==1)score+=1;  //uma peça
                    if(cai==2)score+=10; //duas peças
                    if(cai==3)score+=50; //tres peças
                    if(cai==4)return 512; //a pessoa ganhou ganhou
                }
                if(cai==0 && cp!=0){ //se tiver apenas peças da ia
                    if(cp==1)score-=1;   //uma peça
                    if(cp==2)score-=10;  //duas peças
                    if(cp==3)score-=50;  //tres peças
                    if(cp==4)return -512;//a ia ganhou
                }
            }
        }
        return score;
    }

    //retorna a pontuaçao diagonal (secundaria) da matriz
    public int diagonal_s(){
        int cp,cai;
        int score=0;
        for(int i=4;i<7;i++){
            for(int j=1;j<5;j++){
                cp=0;
                cai=0;
                for(int k=0;k<4;k++){
                    if(m[i-k][j+k]==person) cp++;
                    if(m[i-k][j+k]==ai) cai++;
                }
                if(cp==0 && cai!=0){//se tiver apenas peças da pessoa
                    if(cai==1)score+=1;  //uma peça
                    if(cai==2)score+=10; //duas peças
                    if(cai==3)score+=50; //tres peças
                    if(cai==4)return 512; //a pessoa ganhou ganhou
                }
                if(cai==0 && cp!=0){ //se tiver apenas peças da ia
                    if(cp==1)score-=1;   //uma peça
                    if(cp==2)score-=10;  //duas peças
                    if(cp==3)score-=50;  //tres peças
                    if(cp==4)return -512;//a ia ganhou
                }
            }
        }
        return score;
    }

    //imprime a matriz atual
    public void print(){
        System.out.println("1 2 3 4 5 6 7");
        for(int i =1;i<7;i++){
            for(int j=1;j<8;j++) {
                System.out.print(m[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
