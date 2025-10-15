package com.example.ProjetoLeticia;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;
import java.util.Scanner;

@SpringBootApplication
public class ProjetoLeticiaApplication {

	public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        double valor = 0;
        double juros = 0;

        //SpringApplication.run(ProjetoLeticiaApplication.class, args);
        System.out.println("---Transações com cartão de crédito---");
        var cartao = TransacaoCredito.builder()
                .remetente("Letícia")
                .limiteRemetente(3000)
                .destinatario("Kevin")
                .saldoDestinatario(1500)
                .mdr(0.035)
                .nParcelas(5)
                .build();

        var boleto = TransacaoBoleto.builder()
                .remetente("Letícia")
                .limiteRemetente(3000)
                .destinatario("Kevin")
                .saldoDestinatario(1500)
                .linhaDigitavel("00190500954014481606906809350314337370000123599")
                .build();

        System.out.println("\n\n\n---Transação com PIX---");
        var pix = TransacaoPix.builder()
                .remetente("Letícia")
                .limiteRemetente(3000)
                .destinatario("Kevin")
                .saldoDestinatario(1500)
                .chavepix("leticia.camargo@gmail.com.br")
                .build();

        do{
            menu();
            opcao = input.nextInt();
            switch (opcao) {

                case 0:
                    System.out.println("Programa encerrado");
                    break;

                case 1:
                    System.out.println("Dados da transação");
                    System.out.println(cartao);
                    System.out.println("Escreva o valor a ser transferido");
                    valor = input.nextDouble();
                    System.out.println("Escreva o juros ao mês");
                    juros = input.nextDouble();
                    System.out.printf("Valor da parcela R$%.2f\n",cartao.parcelarAnuidade(juros, valor));
                    break;

                case 2:
                    System.out.println("Dados da transação");
                    System.out.println(cartao);
                    System.out.println("Escreva o valor a ser transferido");
                    valor = input.nextDouble();
                    System.out.println("Escreva o juros ao mês");
                    juros = input.nextDouble();
                    System.out.printf("Valor da parcela R$%.2f\n", cartao.parcelarMontante(juros, valor));
                    break;

                case 3:
                    System.out.println("Dados da transação");
                    System.out.println(cartao);
                    System.out.println("Escreva o valor a ser transferido");
                    valor = input.nextDouble();
                    System.out.println(pagarCartao(cartao, valor));
                    break;

                case 4:
                    System.out.println("Dados da transação");
                    System.out.println(boleto);
                    System.out.println(verificarBoleto(boleto, boleto.pagarBoleto()));
                    break;

                case 5:
                    System.out.println("Dados da transação");
                    System.out.println(pix);
                    System.out.println("Escreva o valor a ser transferido");
                    valor = input.nextDouble();
                    System.out.println(fazerPix(pix, valor));
                    break;

                case 6:
                    System.out.println("pix");
                    System.out.println(cartao);
                    System.out.println(pix.definirChavePix());
                    break;

                default:
                    System.out.println("Valor errado! Digite um número de 0 a 6");

            }
        } while(opcao != 0);

	}

    //MENU
    public static void menu(){
        System.out.println("""
                MENU
                OPERAÇÕES
                Escolha uma das opções
                
                Cartão de Crédito
                    1: Parcelar com anuidade
                    2: Parcelar em montante
                    3: Transação normal
                Boleto
                    4:Transação
                Pix
                    5: Transação
                    6: Descobrir chave pix
                0: SAIR
                """);
    }


    //Transferência com cartão de crédito
    public static String pagarCartao(TransacaoCredito cartao, double valor){
        if (cartao.realizarTransacao(valor)) {
            return String.format("Valor transferido \nSaldo remetente: %.2f \nSaldo destinatário: %.2f",cartao.getLimiteRemetente(), cartao.getSaldoDestinatario());
        }
        return "Valor maior que o limite";
    }


    //Transferência com Boleto
    public static String verificarBoleto(TransacaoBoleto boleto, double valor) {
        if (boleto.realizarTransacao(valor)) {
            return String.format("Pagamento realizado no valor de R$%.2f",boleto.pagarBoleto());
        }
        else if (!boleto.validarBoleto()) {
            return "Linha digitável inválida";
        }
        return "Valor maior que o limite";
    }


    //Tranferência com PIX
    public static String fazerPix(TransacaoPix pix, double valor){
        if (pix.realizarTransacao(valor)) {
            return String.format("Valor transferido \nSaldo remetente: %.2f \nSaldo destinatário: %.2f",pix.getLimiteRemetente(), pix.getSaldoDestinatario());
        }
        else if (pix.definirChavePix().equals("não detectada")) {
            return "Chave pix inválida";
        }
        return "Valor maior que o limite";
    }
}
