import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {
    private ArrayList<String> todasAsPalavras;
    private ArrayList<String> palavrasUsadas;
    private String palavraSorteada; // JACAREPAGUA
    private String progresso; // ***********
    private String dicaSorteada;
    private int acertos;
    private int penalidades;

    public JogoDaForca() {
        todasAsPalavras = new ArrayList<>();
        palavrasUsadas = new ArrayList<>();
        acertos = 0;
        penalidades = 0;
        // Lendo palavras e dicas do arquivo
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
        System.out.println("Palavras carregadas: " + todasAsPalavras.size());
    }

    public void iniciar() {
        if (todasAsPalavras.isEmpty()) return;

        Random random = new Random();
        int indiceSorteado = random.nextInt(todasAsPalavras.size());
        String linhaSorteada = todasAsPalavras.get(indiceSorteado);

        // Verificação de segurança
        if (linhaSorteada.contains(" ")) {
            String[] partes = linhaSorteada.split(" ");
            this.palavraSorteada = partes[0].toUpperCase();
            this.dicaSorteada = partes[1];
            
            this.acertos = 0;
            this.penalidades = 0;
            this.progresso = "*".repeat(this.palavraSorteada.length());
            palavrasUsadas.add(this.palavraSorteada);
        } else {
            // Se a linha estiver mal formatada, tenta sortear outra
            iniciar(); 
        }
    }

    public String getDica() {
        return dicaSorteada;
    }

    public String getPalavra() {
        return progresso;
    }

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
                this.acertos++;

                // Updatessss
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

    public String getNomePenalidade() {
        switch (penalidades) {
            case 1: return "perdeu primeira perna";
            case 2: return "perdeu segunda perna";
            case 3: return "perdeu primeiro braço";
            case 4: return "perdeu segundo braço";
            case 5: return "perdeu tronco";
            case 6: return "perdeu cabeça";
            default: return "sem penalidades"; // Valor inicial 0 
        }
    }

    public String getResultado() {
        if (acertos == palavraSorteada.length()) return "venceu";
        if (penalidades >= 6) return "perdeu";
        return "em andamento";
    }

    public boolean terminou() {
        return acertos == palavraSorteada.length() || penalidades >= 6;
    }

    public int getAcertos() {
        return acertos;
    }

    public int getCodigoPenalidade() {
        return penalidades;
    }

    public ArrayList<String> getPalavras() {
        return palavrasUsadas;
    }
}