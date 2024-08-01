package model;

import java.util.concurrent.Semaphore;

public class Semaforos {
  public static Semaphore carro01_carro02_lateral_cima_esquerda_baixo;
  public static Semaphore carro01_carro22_lateral_esquerda;
  public static Semaphore carro01_carro10_lateral_esquerda_baixo;
  public static Semaphore carro01_carro09_lateral_esquerda;
  public static Semaphore carro01_carro17_lateral_esquerda_baixo;
  public static Semaphore carro01_carro19_lateral_esquerda;
  public static Semaphore carro01_carro02_lateral_baixo_direita_cima;
  public static Semaphore carro01_carro10_lateral_baixo_direita;
  public static Semaphore carro01_carro16_lateral_direita_cima;
  public static Semaphore carro01_carro22_lateral_direita;
  public static Semaphore carro01_carro09_lateral_direita;
  public static Semaphore carro01_carro19_lateral_direita;
  public static Semaphore carro01_carro10_ruaQ; 
  public static Semaphore carro01_carro19_ruaQ;
  public static Semaphore carro01_carro16_rua15;
  public static Semaphore carro01_carro09_ruaM;
  public static Semaphore carro01_carro17_ruaM_rua16;
  public static Semaphore carro01_carro02_rua15_ruaM_rua16;
  public static Semaphore carro01_carro16_ruaS;
  public static Semaphore carro01_carro10_ruaS;
  public static Semaphore carro01_carro19_ruaS;
  public static Semaphore carro01_carro16_rua03;
  public static Semaphore carro01_carro22_rua03;
  public static Semaphore carro01_carro19_rua03;
  public static Semaphore carro01_carro22_rua04;
  public static Semaphore carro01_carro19_rua04;
  public static Semaphore carro01_carro09_esquina26;
  public static Semaphore carro01_carro22_esquina26;
  public static Semaphore carro01_carro22_esquina29;
  public static Semaphore carro01_carro09_esquina29;
  public static Semaphore carro01_carro19_esquina15;
  public static Semaphore carro02_carro19_esquina16;
  public static Semaphore carro02_carro22_lateral_esquerda;
  public static Semaphore carro02_carro10_lateral_esquerda_baixo;
  public static Semaphore carro02_carro09_lateral_esquerda;
  public static Semaphore carro02_carro17_lateral_esquerda_baixo;
  public static Semaphore carro02_carro19_lateral_esquerda;
  public static Semaphore carro02_carro10_lateral_baixo_direita;
  public static Semaphore carro02_carro16_lateral_direita_cima;
  public static Semaphore carro02_carro22_lateral_direita;
  public static Semaphore carro02_carro09_lateral_direita;
  public static Semaphore carro02_carro19_lateral_direita;
  public static Semaphore carro02_carro22_lateral_cima;
  public static Semaphore carro02_carro19_lateral_cima;  
  public static Semaphore carro02_carro19_rua21_rua27;
  public static Semaphore carro02_carro16_rua15;
  public static Semaphore carro02_carro22_rua27;
  public static Semaphore carro02_carro09_ruaM;
  public static Semaphore carro02_carro17_ruaM;
  public static Semaphore carro02_carro19_rua22_rua28;
  public static Semaphore carro02_carro22_rua28;
  public static Semaphore carro02_carro19_esquina15;
  public static Semaphore carro01_carro19_esquina16;
  public static Semaphore carro02_carro09_esquina27;
  public static Semaphore carro02_carro10_esquina21;
  public static Semaphore carro02_carro16_esquina22;
  public static Semaphore carro02_carro10_esquina22;
  public static Semaphore carro02_carro09_esquina28;
  public static Semaphore carro02_carro22_esquina28;

  public static Semaphore carro16_carro22_lateral_cima_rua03;
  public static Semaphore carro16_carro19_lateral_cima_rua03_rua09;
  public static Semaphore carro16_carro22_lateral_direita;
  public static Semaphore carro16_carro09_lateral_direita; 
  public static Semaphore carro16_carro19_lateral_direita_ruaS_ruaT;
  public static Semaphore carro16_carro10_ruaR_ruaS_ruaT;
  public static Semaphore carro02_carro19_rua03_rua09;
  public static Semaphore carro02_carro22_rua03;
  public static Semaphore carro16_carro17_esquina22;
  public static Semaphore carro16_carro19_esquina21;
  public static Semaphore carro_16_carro17_esquina22;

  public static Semaphore carro10_carro22_lateral_esquerda;
  public static Semaphore carro10_carro09_lateral_esquerda;
  public static Semaphore carro10_carro17_lateral_esquerda_baixo;
  public static Semaphore carro10_carro22_lateral_baixo;
  public static Semaphore carro10_carro19_lateral_baixo;
  public static Semaphore carro10_carro22_lateral_direita;
  public static Semaphore carro10_carro09_lateral_direita;
  public static Semaphore carro10_carro19_ruaS_ruaT;
  public static Semaphore carro10_carro19_ruaP_ruaQ;
  // public static Semaphore carro10_carro17_esquina19; 
  // public static Semaphore carro10_carro09_esquina19;
  public static Semaphore carro10_carro17_esquina22;
  public static Semaphore carro10_carro09_esquina24;
  
  public static Semaphore carro09_carro17_lateral_esquerda_ruaK_ruaL_ruaM;
  public static Semaphore carro09_carro19_lateral_esquerda_ruaK_ruaL;
  public static Semaphore carro09_carro19_lateral_direita_ruaN_ruaO;
  public static Semaphore carro09_carro22_lateral_esquerda_ruaU_ruaV;
  public static Semaphore carro09_carro22_lateral_direita;
  public static Semaphore carro09_carro22_ruaX_ruaY;
  public static Semaphore carro09_carro16_esquina15;
  public static Semaphore carro09_carro19_esquina27;
  public static Semaphore carro09_carro19_esquina28; 
  public static Semaphore carro09_carro17_esquina28;

  public static Semaphore carro17_carro19_lateral_esquerda_ruaK_ruaL;
  public static Semaphore carro17_carro22_lateral_esquerda;
  public static Semaphore carro17_carro22_lateral_baixo_rua28;
  public static Semaphore carro17_carro19_lateral_baixo_rua22_rua28;
  public static Semaphore carro17_carro16_rua15;
  public static Semaphore carro17_carro19_esquina16;
  public static Semaphore esquina28;
  public static Semaphore esquina15;
  
  public static Semaphore carro19_carro22_lateral_esquerda;
  public static Semaphore carro19_carro22_rua27_ruaBB_rua28;
  public static Semaphore carro19_carro22_lateral_direita;
  public static Semaphore carro19_carro22_rua03_ruaC_rua07;

  public static Semaphore ajuda_pai = new Semaphore(0);
}