package application;

import java.util.Locale;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		double[][] tele = new double[3][3];
		
		for(int i=0;i<tele.length;i++) {
			for(int j=0; j<tele.length;j++){
				System.out.println("Digite a umidade do Canteiro " + (i + 1) + ", Sensor " + (j + 1) + ":");
				tele[i][j] = sc.nextDouble();
				
				while(tele[i][j] < 0 || tele[i][j] > 100) {
					System.out.println("Valor inválido, digite novamente os valor da umidade: ");
					tele[i][j] = sc.nextDouble();
				}
			}
		}
		
		System.out.println("=============================================");
		int pontosSecos = 0;
		double media = 0;
		
		for(int i=0;i<tele.length; i++) {
			for(int j=0;j<tele.length;j++) {
				System.out.println("Canteiro " + (i+1) + " - Sensor " + (j+1) + ", valor da umidade: " + tele[i][j]);
				media += tele[i][j];
				
				if(tele[i][j] < 40) {
					System.out.println("⚠️ [ALERTA] Solo seco detectado no Canteiro " + (i+1) + ", Sensor " + (j+1));
					pontosSecos +=	 1;
				}
			}
		}
		
		double mediaGeral = media/9;
		
		System.out.println("===========================================");
		System.out.println("======== Relatório Geral ===============");
		System.out.printf("Média geral de umidade %.2f" ,mediaGeral);
		System.out.println("\nQuantidade de pontos críticos que precisam de irrigação : " + pontosSecos);
		
		
		
		sc.close();
	}

}
