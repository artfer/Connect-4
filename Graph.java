import java.util.LinkedList;

public class Graph {
    Matrix root;
    int limit;
    char ai,player;
    int total_n;
    Graph(Matrix root,int limit,char ai,char player){
        this.limit = limit; //limite de profundidade
        this.root=root; //matriz
        this.ai = ai; //char da ia
        this.player = player; //char da pessoa
        total_n=1;
    }

    //cria o descendente
    private Matrix Make_Descendant(int i,char player,Matrix m){
        Matrix tmp=null;
        if(!m.is_full(i)) tmp = m.add(i,player); //se   a matriz nao estiver cheia na coluna i, adiciona a peça
        return tmp;
    }

    //funçao MiniMax, retorna a melhor jogada
    public int MiniMax_Decision(){
        int depth =1;//profundidade da raiz
        int value=Integer.MIN_VALUE,best=0;
        for(int i=1;i<8;i++) {
            int tmp = value;
            Matrix next = Make_Descendant(i,ai,root); //cria descendente para a coluna i
            if(next!=null) {
                total_n++; //incremento do numero total de nos
                int min = Min_Value(next, depth);
                value = Math.max(value, min); //o valor e igual ao maximo do min
                if (tmp < value) //se existir um novo valor, atualiza a coluna a retornar
                    best = i;
            }
        }
        return best;
    }

    private int Max_Value(Matrix state,int depth){
        depth++; //incremento da profundidade atual
        int v = Integer.MIN_VALUE;
        if(state.is_Terminal()!=-1 || depth >= limit) { //se a matriz for final, retorna o valor da matriz
            v=state.Utility();
            return v;
        }
        else {
            for(int i=1;i<8;i++){
                Matrix next = Make_Descendant(i,ai,state);//cria descendente para a coluna i
                if(next!=null) {
                    total_n++; //incremento do numero total de nos
                    v = Math.max(v, Min_Value(next, depth)); //o valor e igual ao maximo do min
                }
            }
        }
        return v;
    }

    private int Min_Value(Matrix state,int depth){
        depth++; //incremento da profundidade atual
        int v = Integer.MAX_VALUE;
        if(state.is_Terminal()!=-1 || depth >= limit) {//se a matriz for final, retorna o valor da matriz
            v=state.Utility();
            return v;
        }
        else {
            for(int i=1;i<8;i++){
                Matrix next = Make_Descendant(i,player,state); //cria o descendente para a coluna i
                if(next!=null) {
                    total_n++; //incremento do numero total de nos
                    v = Math.min(v, Max_Value(next, depth)); //o valor e igual ao minimo do max
                }
            }
        }
        return v;
    }

    //funcao Alpha-Beta, retorna a melhor jogada
    public int AlphaBeta_Search(){
        int depth =1;//profundidade da raiz
        int value=Integer.MIN_VALUE,best=0;
        int alfa = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        for(int i=1;i<8;i++) {
            int tmp = value;
            Matrix next = Make_Descendant(i,ai,root);
            if(next!=null) {
                total_n++;
                int min = AB_Min_Value(next,alfa,beta,depth);
                value = Math.max(value, min); //o valor e igual ao maximo do min
                if(value >= beta) return value; //se o valor for maior que o beta, retorna o valor
                alfa = Math.max(alfa,value); //atualiza o alfa
                if (tmp < value) //se existir um novo valor, atualiza a coluna a retornar
                    best = i;
            }
        }
        return best;
    }

    private int AB_Max_Value(Matrix state,int alfa,int beta,int depth){
        depth++;//incremento da profundidade atual
        int v = Integer.MIN_VALUE;
        if(state.is_Terminal()!=-1 || depth >= limit) {//se a matriz for final, retorna o valor da matriz
            v=state.Utility();
            return v;
        }
        else {
            for(int i=1;i<8;i++){
                Matrix next = Make_Descendant(i,ai,state); //cria o descendente para a coluna i
                if(next!=null) {
                    total_n++; //incremento do numero total de nos
                    v = Math.max(v, AB_Min_Value(next, alfa, beta, depth)); //o valor e igual ao maximo do min
                    if(v>= beta) return v; //se o valor for maior que o beta, retorna o valor
                    alfa = Math.max(alfa,v); //atualiza o alfa
                }
            }
        }
        return v;
    }

    private int AB_Min_Value(Matrix state,int alfa,int beta,int depth){
        depth++;//incremento da profundidade atual
        int v = Integer.MAX_VALUE;
        if(state.is_Terminal()!=-1 || depth >= limit) {//se a matriz for final , retorna o valor da matriz
            v=state.Utility();
            return v;
        }
        else {
            for(int i=1;i<8;i++){
                Matrix next = Make_Descendant(i,player,state); //cria o descendente para a coluna i
                if(next!=null) {
                    total_n++;//incremento do nuemro total de nos
                    v = Math.min(v, AB_Max_Value(next, alfa, beta, depth)); //o valor e igual ao mimino do max
                    if(v<=alfa) return v; //se o valor for menor que o alfa retorna o valor
                    beta = Math.min(beta,v); //atualiza o beta
                }
            }
        }
        return v;
    }

}

