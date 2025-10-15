package com.example.ProjetoLeticia;
import com.example.ProjetoLeticia.core.Transacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TransacaoCredito extends Transacao implements Credito{

    double mdr;
    int nParcelas;

    public TransacaoCredito (String remetente, double limite, String destinatario, double saldo, double mrd, int nParcelas) {
        super(remetente, limite, destinatario, saldo);
        this.mdr = mrd;
        this.nParcelas = nParcelas;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + "\nJuros MDR: %.3f \nNúmero de Parcelas: %d",
                this.mdr,
                this.nParcelas);
    }

    @Override
    public boolean realizarTransacao (double valor) {
        if (verificarTransacao(valor)) {
            this.limiteRemetente -= valor;
            this.saldoDestinatario += valor - valor*mdr;
            return true;
        }
        return false;
    }

    private double calcularTaxa (double juro) {
        double potencia = Math.pow(juro + 1, nParcelas);
        return potencia;
    }

    @Override
    public double parcelarMontante (double juro, double valor) {
        double multiplicacao = valor * calcularTaxa(juro);
        return multiplicacao;
    }

    @Override
    public double parcelarAnuidade (double juro, double valor) {
        double divisao = juro / (calcularTaxa(juro) - 1);
        double multiplicacao = valor * divisao;
        return multiplicacao;
    }
}
