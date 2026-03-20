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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class TelaJogo extends JFrame {

    private final JPanel contentPane;
    private final JTextField txtLetra;
    private final JLabel lblPalavra, lblDica, lblResultado, lblAcertos, lblPenalidade, lblImagem, lblLetrasUsadas;
    private final JTextArea txtAreaHistorico;
    private final JButton btnAdivinhar, btnIniciar;
    private final JogoDaForca jogo;

    // Paleta de cores preto branco e cinza, com fonte estilo giz
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
        setSize(600, 650);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBackground(corFundo);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        int larguraJanela = 560;

        // Botão comeeeeça o jogo no Maracanã
        btnIniciar = new JButton("Iniciar Jogo");
        btnIniciar.setBounds((larguraJanela - 150) / 2, 20, 150, 35);
        estilizarBotao(btnIniciar);
        contentPane.add(btnIniciar);

        lblPalavra = new JLabel("AGUARDANDO INÍCIO...", SwingConstants.CENTER);
        lblPalavra.setFont(new Font("Monospaced", Font.BOLD, 28));
        lblPalavra.setForeground(corGiz);
        lblPalavra.setBounds(0, 70, larguraJanela, 40);
        contentPane.add(lblPalavra);

        lblDica = new JLabel("Dica: ", SwingConstants.CENTER);
        lblDica.setFont(fontePrincipal);
        lblDica.setForeground(corGiz);
        lblDica.setBounds(0, 115, larguraJanela, 20);
        contentPane.add(lblDica);

        lblLetrasUsadas = new JLabel("Letras tentadas: []", SwingConstants.CENTER);
        lblLetrasUsadas.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        lblLetrasUsadas.setForeground(new Color(200, 200, 200));
        lblLetrasUsadas.setBounds(0, 135, larguraJanela, 20);
        contentPane.add(lblLetrasUsadas);

        lblImagem = new JLabel("", SwingConstants.CENTER);
        lblImagem.setBounds((larguraJanela - 250) / 2, 160, 250, 250);
        lblImagem.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50))); 
        contentPane.add(lblImagem);

        txtLetra = new JTextField();
        txtLetra.setHorizontalAlignment(JTextField.CENTER);
        txtLetra.setFont(new Font("SansSerif", Font.BOLD, 18));
        txtLetra.setBackground(corFundo);
        txtLetra.setForeground(corGiz);
        txtLetra.setCaretColor(corGiz);
        txtLetra.setBorder(BorderFactory.createLineBorder(corGiz, 2));
        txtLetra.setBounds((larguraJanela - 160) / 2, 420, 50, 40);
        txtLetra.setEnabled(false);
        contentPane.add(txtLetra);

        btnAdivinhar = new JButton("OK");
        btnAdivinhar.setBounds((larguraJanela - 160) / 2 + 60, 420, 100, 40);
        estilizarBotao(btnAdivinhar);
        btnAdivinhar.setEnabled(false);
        contentPane.add(btnAdivinhar);

        lblAcertos = new JLabel("Acertos: 0", SwingConstants.CENTER);
        lblAcertos.setForeground(corGiz);
        lblAcertos.setBounds(0, 470, larguraJanela / 2, 20);
        contentPane.add(lblAcertos);

        lblPenalidade = new JLabel("Penalidade: sem penalidades", SwingConstants.CENTER);
        lblPenalidade.setForeground(corGiz);
        lblPenalidade.setBounds(larguraJanela / 2, 470, larguraJanela / 2, 20);
        contentPane.add(lblPenalidade);

        lblResultado = new JLabel("", SwingConstants.CENTER);
        lblResultado.setFont(fontePrincipal.deriveFont(Font.ITALIC));
        lblResultado.setForeground(corGiz);
        lblResultado.setBounds(0, 495, larguraJanela, 30);
        contentPane.add(lblResultado);

        txtAreaHistorico = new JTextArea();
        txtAreaHistorico.setBackground(new Color(50, 50, 50));
        txtAreaHistorico.setForeground(corGiz);
        txtAreaHistorico.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtAreaHistorico);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(corGiz), "Histórico", 0, 0, fontePrincipal, corGiz));
        scroll.setBounds(50, 530, larguraJanela - 100, 70);
        contentPane.add(scroll);

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
                String letra = txtLetra.getText().toUpperCase().trim();
                
                if (letra.isEmpty()) return;

                if (jogo.jaTentou(letra)) {
                    // Cria um label estilizado para a mensagem
                    JLabel msgCorpo = new JLabel("<html><div style='text-align: center;'>A letra <b style='color: #FF6666;'>" + letra + "</b> já foi usada!</div></html>");
                    msgCorpo.setFont(fontePrincipal);
                    msgCorpo.setForeground(corGiz);

                    // Personaliza as cores do JOptionPane para esta chamada
                    UIManager.put("OptionPane.background", corFundo);
                    UIManager.put("Panel.background", corFundo);

                    JOptionPane.showMessageDialog(this, msgCorpo, "Letra Repetida", JOptionPane.PLAIN_MESSAGE);
                    
                    txtLetra.setText("");
                    txtLetra.requestFocus();
                    return;
                }

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
        lblLetrasUsadas.setText("Letras tentadas: " + jogo.getLetrasTentadas().toString());
        
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
        
        String caminhoImagem; // Png mortal kombat
        if (jogo.getResultado().equalsIgnoreCase("venceu")) {
            lblResultado.setText("VOCÊ VENCEU!");
            lblResultado.setForeground(new Color(150, 255, 150));
            caminhoImagem = "resources/img/Win.png";
        } else {
            lblResultado.setText("GAME OVER!");
            lblResultado.setForeground(new Color(255, 150, 150));
            caminhoImagem = "resources/img/Lose.png";
        }

        try {
            ImageIcon iconFinal = new ImageIcon(caminhoImagem);
            
            Image img = iconFinal.getImage().getScaledInstance(
                lblImagem.getWidth(), 
                lblImagem.getHeight(), 
                Image.SCALE_SMOOTH
            );
            
            lblImagem.setIcon(new ImageIcon(img));
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem final: " + e.getMessage());
        }

        txtAreaHistorico.setText("");
        for (String p : jogo.getPalavras()) {
            txtAreaHistorico.append(" » " + p + " (" + jogo.getResultado() + ")\n");
        }
    }
}