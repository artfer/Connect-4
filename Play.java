import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.RunnableFuture;

public class Play {
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        System.out.print("Quantas vezes vai jogar? ");
        int n = in.nextInt(); //nº de vezes que se vai jogar
        //System.out.println();
        for(int i=0;i<n;i++){
            System.out.print("Algoritmo a usar?\n1->Minimax  2->Aplha-Beta ");
            int choice = in.nextInt(); //algoritmo que se vai usar
            while(choice!= 1 && choice != 2){
                System.out.print("1->Minimax  2 ->Alpha-Beta ");
                choice=in.nextInt();
            }
            System.out.print("Profundidade a usar? ");
            int limit = in.nextInt(); //limite de profundidade para usar nos algoritmos
            System.out.print("Informação sobre a jogada da IA?(s/n) ");
            char info = in.next().charAt(0);
            System.out.print("Quer ir em primeiro?(s/n) ");
            char order = in.next().charAt(0);
            char player;
            char ai;
            double time,start,end,space; //tempo/espaço que o grafo demora/usa
            if(order=='s'){ //se a pessoa for em primeiro
                player = 'X';
                ai = 'O';
                Matrix m = new Matrix(ai,player);
                while(m.is_Terminal()==-1){ //enquanto a matriz nao for terminal
                    System.out.print("Jogada : ");
                    int play = in.nextInt(); //coluna para adicionar a nova peça
                    while(play<1 || play>7){ //para garantir que nao ha erro "OutOfBounds" enquanto se joga
                        System.out.println("Coluna inválida. Tente outra.");
                        play=in.nextInt();
                    }
                    while(m.is_full(play)){ //para garantir que nao ha erro "OutofBounds" enquanto se joga
                        System.out.println("Coluna cheia. Tente outra.");
                        play = in.nextInt();
                    }
                    m=m.add(play,player); // adiciona a peça na matrix
                    m.print(); //imprime a matriz com a nova jogada
                    if(m.is_Terminal()==-512) { //se a pessoa ganhou
                        System.out.println("Ganhou!");
                        break;
                    }
                    start = new Date().getTime(); //tempo de inicio
                    Graph g = new Graph(m, limit, ai, player); //inicializa o grafo
                    if(choice==1) m = m.add(g.MiniMax_Decision(), ai); //faz a jogada de acordo com o algoritmo escolhido
                    if(choice==2) m = m.add(g.AlphaBeta_Search(),ai);  // ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^
                    end = new Date().getTime(); //tempo do fim
                    m.print(); //imprime a matriz com a nova jogada
                    if(info=='s') {
                         time = (end - start)/1000;
                        space = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(1024); //calcular a memoria em KB
                        System.out.printf("Tempo:%.3fs\nMemória:%.0fKB\n",time,space);
                        System.out.println("Número total de nós percorridos = " + g.total_n);//imprime o numero de nos percorridos no grafo
                        System.out.println();
                    }
                    if(m.is_Terminal()==512){ //se a ia ganhou
                        System.out.println("Perdeu!");
                        break;
                    }
                    if(m.is_Terminal()==0){ //se a matriz estiver cheia e ninguem ganhou
                        System.out.println("Empate!");
                        break;
                    }
                }
            }
            else { //se a pessoa for em segundo
                player = 'O';
                ai = 'X';
                Matrix m = new Matrix(ai,player);
                while(m.is_Terminal()==-1){ //enquanto a matriz nao for terminal
                    start = new Date().getTime(); //tempo de inicio
                    Graph g = new Graph(m, limit, ai, player); //inicializa o grafo
                    if(choice==1) m = m.add(g.MiniMax_Decision(), ai);//faz a jogada de acordo com o algoritmo escolhido
                    if(choice==2) m = m.add(g.AlphaBeta_Search(),ai); // ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^
                    end = new Date().getTime(); //tempo do fim
                    m.print(); //imprime a matriz com  a nova jogada
                    if(info=='s') {
                        time = (end - start)/1000;
                        space = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(1024); //calcular a memoria em KB
                        System.out.printf("Tempo:%.3fs\nMemória:%.0fKB\n",time,space);
                        System.out.println("Número total de nós percorridos = " + g.total_n);//imprime o numero de nos percorridos no grafo
                        System.out.println();
                    }
                    if(m.is_Terminal()==512){ //se a ia ganhou
                        System.out.println("Perdeu!");
                        break;
                    }
                    if(m.is_Terminal()==0){ //se a matriz estiver cheia e ninguem ganhou
                        System.out.println("Empate!");
                        break;
                    }
                    System.out.print("Jogada : ");
                    int play = in.nextInt(); //coluna para adicionar a nova peça
                    while(play<1 || play>7){  //para garantir que nao ha erro "OutOfBounds" enquanto se joga
                        System.out.println("Coluna inválida. Tente outra.");
                        play=in.nextInt();
                    }
                    while(m.is_full(play)){  //para garantir que nao ha erro "OutOfBounds" enquanto se joga
                        System.out.println("Coluna cheia. Tente outra.");
                        play = in.nextInt();
                    }
                    m=m.add(play,player); // adiciona a peça na matrix
                    m.print(); //imprime a matriz com  a nova jogada
                    if(m.is_Terminal()==-512) { //se a pessoa ganhou
                        System.out.println("Ganhou!");
                        break;
                    }
                }
            }

        }
    }
}
