package Codigos;

import java.util.Scanner;

public class CodDessconto {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		float preco,desconto,descontoParcial;
		int porcentagem;
		Scanner tecla = new Scanner(System.in); 
		
		System.out.println("Digite Preço do produto: ");
		preco = tecla.nextFloat();
		System.out.println("digite valor da porcentagem: ");
		porcentagem = tecla.nextInt();
		
		descontoParcial = porcentagem/100;
		desconto = preco*descontoParcial;
		
		System.out.print("valor produto: "+preco+" descoto de: "+porcentagem+" %\n");
		System.out.print("valor do desconto: "+descontoParcial+" Produto com Desconto: %.2f",desconto+" %");
		
		
		
	}

}
