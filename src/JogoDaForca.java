import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {
    // Atributos que controlam o funcionamento do jogo
    private ArrayList<String> todasAsPalavras;
    private ArrayList<String> palavrasUsadas;
    private String palavraSorteada; 
    private String progresso; 
    private String dicaSorteada;
    private int acertos;
    private int penalidades;
    private ArrayList<String> letrasTentadas;

    // Inicializa o jogo e carrega o arquivo CSV das palavras
    public JogoDaForca() {
        todasAsPalavras = new ArrayList<>();
        palavrasUsadas = new ArrayList<>();
        this.letrasTentadas = new ArrayList<>();
        acertos = 0;
        penalidades = 0;

        try (Scanner scanner = new Scanner(new File("resources/palavras.csv"))) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (!linha.isEmpty()) {
                    todasAsPalavras.add(linha);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo de palavras não encontrado.");
        }
    }

    // Sorteia uma nova palavra, define a dica e reseta os contadores da rodada
    public void iniciar() {
        if (todasAsPalavras.isEmpty()) return;

        Random random = new Random();
        int indiceSorteado = random.nextInt(todasAsPalavras.size());
        String linhaSorteada = todasAsPalavras.get(indiceSorteado);

        if (linhaSorteada.contains(" ")) {
            String[] partes = linhaSorteada.split(" ");
            this.palavraSorteada = partes[0].toUpperCase();
            this.dicaSorteada = partes[1];
            
            this.acertos = 0;
            this.penalidades = 0;
            this.progresso = "*".repeat(this.palavraSorteada.length());
            palavrasUsadas.add(this.palavraSorteada);
            
            this.letrasTentadas.clear();
        } else {
            iniciar(); 
        }
    }

    // Gerencia as letras que já foram
    public boolean jaTentou(String letra) {
        String l = letra.toUpperCase().trim();
        if (letrasTentadas.contains(l)) {
            return true;
        }
        letrasTentadas.add(l);
        return false;
    }

    public ArrayList<String> getLetrasTentadas() {
        return letrasTentadas;
    }

    
    // Verifica as ocorrências da letra na palavra sorteada e atualiza o progresso e penalidades
    public ArrayList<Integer> getOcorrencias(String letra) throws Exception {
        if (letra == null || letra.length() != 1) {
            throw new Exception("Entrada inválida. Por favor, insira uma única letra.");
        }
        
        ArrayList<Integer> ocorrencias = new ArrayList<>();
        String letraUpper = letra.toUpperCase();
        boolean encontrou = false;
        
        for (int i = 0; i < palavraSorteada.length(); i++) {
            if (palavraSorteada.charAt(i) == letraUpper.charAt(0)) {
                ocorrencias.add(i + 1);
                
                if (progresso.charAt(i) == '*') {
                    this.acertos++;
                }

                char[] progressoArray = progresso.toCharArray();
                progressoArray[i] = letraUpper.charAt(0);
                this.progresso = String.valueOf(progressoArray);
                encontrou = true;
            }
        }
        
        if (!encontrou) {
            this.penalidades++;
        }
        return ocorrencias;
    }
    
    // Retorna o nome da penalidade com base no número de penalidades acumuladas
    public String getNomePenalidade() {
        return switch (penalidades) {
            case 1 -> "Cabeça";
            case 2 -> "Tronco";
            case 3 -> "Braço Direito";
            case 4 -> "Braço Esquerdo";
            case 5 -> "Perna Direita";
            case 6 -> "Perna Esquerda";
            default -> "sem penalidades";
        };
    }
    
    // Verifica se ganhou ou perdeu
    public String getResultado() {
        if (palavraSorteada != null && progresso.equals(palavraSorteada)) return "venceu";
        if (penalidades >= 6) return "perdeu";
        return "em andamento";
    }
    
    // Determina se o jogo terminou, seja por vitória ou derrota
    public boolean terminou() {
        return (palavraSorteada != null && progresso.equals(palavraSorteada)) || penalidades >= 6;
    }
    
    public int getAcertos() { return acertos; }
    public int getCodigoPenalidade() { return penalidades; }
    public ArrayList<String> getPalavras() { return palavrasUsadas; }
    public String getDica() { return dicaSorteada; }
    public String getPalavra() { return progresso; }
}