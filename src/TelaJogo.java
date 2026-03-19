import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TelaJogo extends JFrame {

    private final JPanel contentPane;
    private final JTextField txtLetra;
    private final JLabel lblPalavra, lblDica, lblResultado, lblAcertos, lblPenalidade, lblImagem;
    private final JTextArea txtAreaHistorico;
    private final JButton btnAdivinhar, btnIniciar;
    private final JogoDaForca jogo;

    // Definição da Paleta "Quadro Negro"
    private final Color corFundo = new Color(35, 35, 35); // Cinza muito escuro
    private final Color corGiz = new Color(245, 245, 245);  // Branco suave
    private final Font fontePrincipal = new Font("Comic Sans MS", Font.BOLD, 14); // Lembra escrita à mão

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TelaJogo frame = new TelaJogo();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaJogo() {
        jogo = new JogoDaForca();
        
        setTitle("Jogo da Forca - IFPB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650); // Aumentado para acomodar tudo centralizado
        setLocationRelativeTo(null); // Centraliza a janela na tela
        
        contentPane = new JPanel();
        contentPane.setBackground(corFundo);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        int larguraJanela = 560; // Largura útil interna

        // 1. Botão Iniciar (Estilizado como Giz)
        btnIniciar = new JButton("Iniciar Jogo");
        btnIniciar.setBounds((larguraJanela - 150) / 2, 20, 150, 35);
        estilizarBotao(btnIniciar);
        contentPane.add(btnIniciar);

        // 2. Label Palavra (Destaque Central)
        lblPalavra = new JLabel("AGUARDANDO INÍCIO...", SwingConstants.CENTER);
        lblPalavra.setFont(new Font("Monospaced", Font.BOLD, 28));
        lblPalavra.setForeground(corGiz);
        lblPalavra.setBounds(0, 70, larguraJanela, 40);
        contentPane.add(lblPalavra);

        // 3. Label Dica
        lblDica = new JLabel("Dica: ", SwingConstants.CENTER);
        lblDica.setFont(fontePrincipal);
        lblDica.setForeground(corGiz);
        lblDica.setBounds(0, 115, larguraJanela, 20);
        contentPane.add(lblDica);

        // 9. Label Imagem (Centralizado no meio da tela)
        lblImagem = new JLabel("", SwingConstants.CENTER);
        lblImagem.setBounds((larguraJanela - 250) / 2, 140, 250, 250);
        // Borda branca fina simulando moldura de quadro
        lblImagem.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50))); 
        contentPane.add(lblImagem);

        // 4 e 5. Área de Input (Letra e Botão Lado a Lado mas centralizados)
        txtLetra = new JTextField();
        txtLetra.setHorizontalAlignment(JTextField.CENTER);
        txtLetra.setFont(new Font("SansSerif", Font.BOLD, 18));
        txtLetra.setBackground(corFundo);
        txtLetra.setForeground(corGiz);
        txtLetra.setCaretColor(corGiz);
        txtLetra.setBorder(BorderFactory.createLineBorder(corGiz, 2));
        txtLetra.setBounds((larguraJanela - 160) / 2, 405, 50, 40);
        txtLetra.setEnabled(false);
        contentPane.add(txtLetra);

        btnAdivinhar = new JButton("OK");
        btnAdivinhar.setBounds((larguraJanela - 160) / 2 + 60, 405, 100, 40);
        estilizarBotao(btnAdivinhar);
        btnAdivinhar.setEnabled(false);
        contentPane.add(btnAdivinhar);

        // 6, 7 e 8. Status (Agrupados na parte inferior)
        lblAcertos = new JLabel("Acertos: 0", SwingConstants.CENTER);
        lblAcertos.setForeground(corGiz);
        lblAcertos.setBounds(0, 455, larguraJanela / 2, 20);
        contentPane.add(lblAcertos);

        lblPenalidade = new JLabel("Penalidade: sem penalidades", SwingConstants.CENTER);
        lblPenalidade.setForeground(corGiz);
        lblPenalidade.setBounds(larguraJanela / 2, 455, larguraJanela / 2, 20);
        contentPane.add(lblPenalidade);

        lblResultado = new JLabel("", SwingConstants.CENTER);
        lblResultado.setFont(fontePrincipal.deriveFont(Font.ITALIC));
        lblResultado.setForeground(corGiz);
        lblResultado.setBounds(0, 480, larguraJanela, 30);
        contentPane.add(lblResultado);

        // 10. Histórico (Na base)
        txtAreaHistorico = new JTextArea();
        txtAreaHistorico.setBackground(new Color(50, 50, 50));
        txtAreaHistorico.setForeground(corGiz);
        txtAreaHistorico.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtAreaHistorico);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corGiz), "Histórico", 0, 0, fontePrincipal, corGiz));
        scroll.setBounds(50, 520, larguraJanela - 100, 80);
        contentPane.add(scroll);

        // --- EVENTOS ---
        btnIniciar.addActionListener(e -> {
            jogo.iniciar();
            atualizarTela();
            btnAdivinhar.setEnabled(true);
            txtLetra.setEnabled(true);
            txtLetra.requestFocus();
            lblResultado.setText("EM ANDAMENTO...");
        });

        btnAdivinhar.addActionListener(e -> {
            try {
                String letra = txtLetra.getText();
                jogo.getOcorrencias(letra);
                atualizarTela();
                txtLetra.setText("");
                txtLetra.requestFocus();
                
                if (jogo.terminou()) {
                    finalizarPartida();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }

    private void estilizarBotao(JButton btn) {
        btn.setBackground(corFundo);
        btn.setForeground(corGiz);
        btn.setFont(fontePrincipal);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(corGiz, 1));
    }

    private void atualizarTela() {
        lblPalavra.setText(jogo.getPalavra());
        lblDica.setText("Dica: " + jogo.getDica());
        lblAcertos.setText("Acertos: " + jogo.getAcertos());
        lblPenalidade.setText("Erros: " + jogo.getCodigoPenalidade() + " - " + jogo.getNomePenalidade());
        
        // Procurando os png do joguinho
        String path = "resources/img/" + jogo.getCodigoPenalidade() + ".png";
        System.out.println("Tentando carregar: " + new java.io.File(path).getAbsolutePath());
        ImageIcon iconOriginal = new ImageIcon(path);

        // Agora essa img fica certa
        if (iconOriginal.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            Image imgRedimensionada = iconOriginal.getImage().getScaledInstance(
                    lblImagem.getWidth(), 
                    lblImagem.getHeight(), 
                    Image.SCALE_SMOOTH
            );

            lblImagem.setIcon(new ImageIcon(imgRedimensionada));
        } else {
            System.err.println("Erro ao carregar imagem: " + path);
            lblImagem.setIcon(null);
        }
    }

    private void finalizarPartida() {
        btnAdivinhar.setEnabled(false);
        txtLetra.setEnabled(false);
        
        String caminhoImagem;
        if (jogo.getResultado().equalsIgnoreCase("venceu")) {
            lblResultado.setText("VOCÊ VENCEU!");
            lblResultado.setForeground(new Color(150, 255, 150)); // Verde "giz"
            caminhoImagem = "resources/img/Win.png";
        } else {
            lblResultado.setText("GAME OVER!");
            lblResultado.setForeground(new Color(255, 150, 150)); // Vermelho "giz"
            caminhoImagem = "resources/img/Lose.png";
        }

        try {
            // 1. Carrega a imagem (vitoria.png ou derrota.png)
            ImageIcon iconFinal = new ImageIcon(caminhoImagem);
            
            // 2. Redimensiona para caber no seu label de 250x250 (ou o tamanho que você definiu)
            Image img = iconFinal.getImage().getScaledInstance(
                lblImagem.getWidth(), 
                lblImagem.getHeight(), 
                Image.SCALE_SMOOTH
            );
            
            // 3. Define a imagem final no label
            lblImagem.setIcon(new ImageIcon(img));
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem final: " + e.getMessage());
        }

        // Atualiza o histórico no final
        txtAreaHistorico.setText("");
        for (String p : jogo.getPalavras()) {
            txtAreaHistorico.append(" » " + p + " (" + jogo.getResultado() + ")\n");
        }
    }
}