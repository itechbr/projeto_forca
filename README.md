# Projeto 1: Jogo da Forca - POO 2026.1

## 📌 Descrição do Projeto
[cite_start]Este projeto consiste na implementação de um **Jogo da Forca** em Java, desenvolvido como requisito para a disciplina de Programação Orientada a Objetos[cite: 1]. [cite_start]O sistema utiliza conceitos de POO para gerenciar a lógica de um jogo onde o usuário deve adivinhar uma palavra sorteada de um arquivo externo, respeitando um limite de erros[cite: 3, 4].

## 🎮 Regras do Jogo
* [cite_start]**Tentativas:** O jogador tem no máximo 6 tentativas[cite: 3].
* [cite_start]**Dica:** Cada palavra possui uma dica associada para auxiliar o jogador[cite: 3, 8].
* [cite_start]**Penalidades:** A cada erro, uma penalidade crescente é aplicada[cite: 7]:
    1. Surge Cabeça.
    2. Surge Tronco.    
    3. Surge primeiro braço.
    4. Surge segundo braço.
    5. Surge primeira perna.
    6. Surge segunda perna (jogo termina).
* [cite_start]**Vitória:** Ocorre ao adivinhar todas as letras da palavra[cite: 8].
* [cite_start]**Derrota:** Ocorre ao atingir a penalidade de nível 6[cite: 8].

## 🛠️ Especificações Técnicas

### 1. Classe `JogoDaForca` (Lógica)
[cite_start]Responsável por toda a regra de negócio do sistema[cite: 10]:
* [cite_start]**Leitura de Dados:** Carrega palavras e dicas do arquivo `palavras.csv` no formato "palavra dica"[cite: 14].
* [cite_start]**Gerenciamento de Rodadas:** Sorteia palavras e mantém o histórico de palavras já utilizadas[cite: 15, 18].
* [cite_start]**Processamento de Letras:** O método `getOcorrencias(String letra)` valida a entrada (lançando `Exception` se houver 0 ou mais de 1 caractere) e contabiliza acertos ou penalidades[cite: 19, 21, 22].
* [cite_start]**Status do Jogo:** Métodos para verificar se o jogo terminou e retornar o resultado atual ("venceu", "perdeu" ou "em andamento")[cite: 24, 28].

### 2. Classe `TelaJogo` (Interface Gráfica)
[cite_start]Interface desenvolvida utilizando o plugin **WindowBuilder** (Swing)[cite: 11, 29]:
* [cite_start]Exibição da palavra oculta por asteriscos ("*") e letras reveladas[cite: 17, 31].
* [cite_start]Campos para inserção de letras e botões de ação[cite: 33, 34].
* [cite_start]Exibição visual do progresso (número de acertos, nome da penalidade e imagem correspondente)[cite: 36, 37, 38].
* [cite_start]Área de texto com o histórico das palavras sorteadas e seus resultados[cite: 39].

## 📂 Requisitos de Arquivos
* [cite_start]`palavras.csv`: Arquivo de texto contendo o banco de palavras do jogo[cite: 8].
* [cite_start]Imagens de penalidade: Conjunto de imagens para representar a evolução dos erros do jogador (0 a 6)[cite: 38].

---
[cite_start]**Instituição:** IFPB - CSTSI [cite: 1]  
[cite_start]**Disciplina:** Programação Orientada a Objetos 2026.1 [cite: 1]