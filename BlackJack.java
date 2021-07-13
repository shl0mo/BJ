package blackjack;

import java.util.*;

class Cartas {
    private int valor;
    private String naipe;
    
    public void define_cartas(int valor, String naipe) {
        this.valor = valor;
        this.naipe = naipe;
    }
    
    public int get_valor() {
        return this.valor;
    }
    
    public String get_naipe() {
        return this.naipe;
    }
}

class Stack {
    private Cartas dados[];
    private int topo;
	
    //construtor
    public Stack(int limMax) {
    	this.dados = new Cartas[limMax];
       	this.topo = 0; 
    }
	
    //inserir
    public void push(Cartas elem) {
    	if(this.dados.length > this.topo) {
            this.dados[this.topo] = elem;
            this.topo++;
	}
    }

    //retirar
    public Cartas pop() {
    	if(this.topo > 0) {
    		this.topo--;
    		return this.dados[this.topo];
    	} else {
    		System.out.println("Pilha vazia!");
    		return null;
    	}
    }
    
    //Size
    public int size() {
    	return this.topo;
    }
		
    //isEmpty
    public boolean isEmpty() {
    	if(this.topo == 0)
    		return true;
    	else
    		return false;
    }
	
    //isFull
    public boolean isFull() {
    	if(this.topo == this.dados.length)
    		return true;
    	else 
    		return false;
    }
    
    public int tamanho_array_dados() {
        return this.dados.length;
    }
    
    public Cartas get_dados(int indice) {
        return this.dados[indice];
    }
    
    public void imprime_deck() {
        for (int i = 0; i < this.topo; i++) {
            System.out.print("\n  Naipe: " + this.dados[i].get_naipe() + "; Valor: " + this.dados[i].get_valor());
        }
    }
}


public class BlackJack {
    
    public static int soma_cartas(List<Cartas> lista_cartas, int soma, int quantidade_as) {
        for (int i = 0; i < lista_cartas.size(); i++) {
            if (lista_cartas.get(i).get_valor() != 1) {
                soma = soma + lista_cartas.get(i).get_valor();
            } else {
                quantidade_as++;
            }
        }
        if (quantidade_as == 1) {
            if (soma + 11 <= 21) {
                soma = soma + 11;
            } else {
                soma = soma + 1;
            }
        } else if (quantidade_as > 1) {
            soma = soma + quantidade_as;
        }
        return soma;
    }

    public static void main(String[] args) {
        int score_jogador = 0;
        int score_computador = 0;
        while (true) {
            System.out.print("Escolha o numero de baralhos entre 1 e 7: ");
            Scanner sc = new Scanner(System.in);
            int num_baralhos = sc.nextInt();
            while (num_baralhos < 1 || num_baralhos > 7) {
                    System.out.print("Informe um numero entre 1 e 7");
                    num_baralhos = sc.nextInt();
            }
            int cartas_totais = num_baralhos * 52;
            Stack pilha_baralho = new Stack(cartas_totais);
            List<Cartas> baralho_inicial = new ArrayList<Cartas>();
            int i = 0;
            while (i < num_baralhos) {
                for (int j = 0; j < 4; j++) {
                    String naipe = "";
                    for (int l = 0; l < 13; l++) {
                        if (j == 0) {
                            naipe = "Espadas";
                        } else if (j == 1) {
                            naipe = "Paus";
                        } else if (j == 2) {
                            naipe = "Copas";
                        } else if (j == 3) {
                            naipe = "Ouro";
                        }
                        Cartas carta = new Cartas();
                        int valor = l;
                        if (l >= 10 && l <= 12) {
                            valor = 9;
                        }
                        carta.define_cartas(valor + 1, naipe);
                        baralho_inicial.add(carta);
                    }               
                }
                i++;
            }
            Random aleatorios = new Random();
            Collections.shuffle(baralho_inicial); 
            for (i = 0; i < baralho_inicial.size(); i++) {
                pilha_baralho.push(baralho_inicial.get(i));
            }
            List<Cartas> cartas_jogador = new ArrayList<Cartas>();
            List<Cartas> cartas_computador = new ArrayList<Cartas>();
            cartas_jogador.add(pilha_baralho.pop());
            cartas_jogador.add(pilha_baralho.pop());
            cartas_computador.add(pilha_baralho.pop());
            cartas_computador.add(pilha_baralho.pop());
            int soma_cartas_jogador = 0;
            int soma_cartas_computador = 0;
            int quantidade_as = 0;
            boolean perdeu = false;
            boolean ganhou_empatou = false;
            System.out.println("\nCartas iniciais do Computador:");
            for (i = 0; i < cartas_computador.size(); i++) {
                System.out.print("\n  Naipe: " + cartas_computador.get(i).get_naipe() + "; Valor: " + cartas_computador.get(i).get_valor());
            }
            soma_cartas_computador = soma_cartas(cartas_computador, soma_cartas_computador, quantidade_as);
            soma_cartas_jogador = soma_cartas(cartas_jogador, soma_cartas_jogador, quantidade_as);
            System.out.print("\n\nSoma dos valores das cartas do Computador: " + soma_cartas_computador + "\n");
            if (soma_cartas_computador > 21 && soma_cartas_jogador <= 21) {
                System.out.println("\n\nSuas duas cartas iniciais:");
                for (i = 0; i < cartas_jogador.size(); i++) {
                    System.out.print("\n  Naipe: " + cartas_jogador.get(i).get_naipe() + "; Valor: " + cartas_jogador.get(i).get_valor());
                }
                System.out.print("\n\nSoma dos valores das suas cartas: " + soma_cartas_jogador + "\n");
                System.out.println("\n\nA soma das cartas do Computador ultrapassou 21. Você venceu\n");
                score_jogador++;
                ganhou_empatou = true;
            } else if (soma_cartas_computador > 21 && soma_cartas_jogador > 21) {
                System.out.println("\n\nNinguém venceu");
                ganhou_empatou = true;
            }
            while (!ganhou_empatou) {
                System.out.println("\nSuas cartas:");
                for (i = 0; i < cartas_jogador.size(); i++) {
                    System.out.print("\n  Naipe: " + cartas_jogador.get(i).get_naipe() + "; Valor: " + cartas_jogador.get(i).get_valor());
                }
                soma_cartas_jogador = 0;
                quantidade_as = 0;
                soma_cartas_jogador = soma_cartas(cartas_jogador, soma_cartas_jogador, quantidade_as);
                System.out.print("\n\nSoma dos valores das suas cartas: " + soma_cartas_jogador + "\n");
                if (soma_cartas_jogador > 21) {
                    System.out.println("\nA soma dos valores das suas cartas ultrapassou 21\nVocê perdeu.\n");
                    score_computador++;
                    perdeu = true;
                    break;
                }
                System.out.println("\nEscolha uma das acoes abaixo:");
                System.out.println("1. Hit - pegar uma carta");
                System.out.println("2. Stand - parar de pegar cartas ");
                int escolha = sc.nextInt();
                while (escolha < 1 || escolha > 2) {
                    System.out.println("Escolha uma opcao valida: ");
                    escolha = sc.nextInt();
                }
                if (escolha == 1) {
                    cartas_jogador.add(pilha_baralho.pop());
                } else {
                    break;
                }
            }
            while (!perdeu && !ganhou_empatou) {
                System.out.println("\nCartas do Computador: ");
                for (i = 0; i < cartas_computador.size(); i++) {
                    System.out.print("\n  Naipe: " + cartas_computador.get(i).get_naipe() + "; Valor: " + cartas_computador.get(i).get_valor());
                }
                soma_cartas_computador = 0;
                quantidade_as = 0;
                soma_cartas_computador = soma_cartas(cartas_computador, soma_cartas_computador, quantidade_as);
                System.out.print("\n\nSoma dos valores das cartas do Computador: " + soma_cartas_computador + "\n");
                if (soma_cartas_computador <= 21) {
                    if (soma_cartas_computador == soma_cartas_jogador) {
                        int aleatorio = aleatorios.nextInt(20) + 1;
                        if (aleatorio >= soma_cartas_computador && aleatorio < 21) {
                            System.out.println("\nO jogo está atualmente empatado, mas o Computador optou por pegar mais uma carta\n");
                            cartas_computador.add(pilha_baralho.pop());
                        } else {
                            System.out.println("\nJogo empatado.\n");
                            break;
                        }
                    } else if (soma_cartas_computador > soma_cartas_jogador) {
                        System.out.println("O computador venceu.\n");
                        score_computador++;
                        break;
                    } else {
                        cartas_computador.add(pilha_baralho.pop());
                        System.out.println("O Computador pegou uma carta\n");
                    }
                } else {
                    System.out.println("\n\nParabens, você venceu! A soma dos valores das cartas do Computador ultrapassou 21\n");
                    score_jogador++;
                    break;
                }
            }
            System.out.println("Seu score: " + score_jogador);
            System.out.println("Score do Computador: " + score_computador + "\n");
            System.out.println("Deseja jogar novamente?");
            System.out.println("1. Sim");
            System.out.println("2. Nao");
            int resposta = sc.nextInt();
            while (resposta < 1 || resposta > 2) {
                System.out.print("Entre com um numero que corresponda a uma resposta valida: ");
                resposta = sc.nextInt();
            }
            if (resposta == 2) {
                System.out.println("\nDeck do jogo:");
                pilha_baralho.imprime_deck();
                return;
            } else {
                System.out.println("");
            }
        }
    }
}