# Programação Concorrente 
A programação concorrente é um paradigma de programação utilizada para a construção de programas que fazem a utilização da execução
simultânea de várias tarefas computacionais interativas. Essas tarefas podem ser executadas por um único processador, 
vários processadores em um único equipamento ou processadores distribuídos em uma rede.

## Trabalhos 💼

A área da programação concorrente tem consigo alguns problemas clássicos que felizmente foram sido solucionados ao longo dos anos. Durante a disciplina alguns desses problemas
foram abordados de forma teórica, através de exemplos no quadro branco e também de forma prática, através de trabalhos práticos que podem ser visualizados a seguir:

### Trens sem colisão 🚂
Neste Trabalho, existem dois processos e dois recursos compartilhados, é permitido apenas um processo por vez acessar a esse determinado recurso.
Para solucionar esse problema foram vistas três possíveis soluções:
- Variável de Travamento
- Estrita Alternância
- Solução de Peterson
<div align="center">
  
  ![trens_sem_colisao](https://github.com/tiago-sb/programacao-concorrente/assets/111618371/6ce607b4-5e53-4b9b-84f4-b60451655c72)  
  
</div>

### Barbeiro Dorminhoco 💈😴

Neste Trabalho, um barbeiro precisa efetuar o corte de cabelo de alguns clientes. O objetivo é lidar com eficiência com algumas condições, são elas:
- Apenas um cliente por vez pode ser atendido pelo barbeiro  
- Ao chegar na barbearia, o cliente deve entrar em um fila de espera, a qual possui tamanho limitado de até cinco pessoas
- Caso o cliente chegue na barbearia e a fila esteja cheia(cinco clientes esperando), ele deve se retirar da barbearia
- Quando não há clientes esperando para serem atendidos, o barbeiro dorme

A solução clássica para esta situação já foi implementada e utiliza os semáforos, uma breve amostra desta situação pode ser vista abaixo:

## Instalação

Instale o repositório utilizando o comando 

```bash
git clone https://github.com/tiago-sb/programacao-concorrente.git
```

## Requisitos
Neste projeto foi utilizado o Java 8. Sugiro utilizar a Java SE Development Kit 8u351 
- Link: <a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html"/>Download</a>

Ao baixar e instalar o kit, teste se o java está configurado em sua máquina corretamente dando o comando:

```bash
java -version
```

## Autor ✍🏽
- [@tiago-sb](https://github.com/tiago-sb)