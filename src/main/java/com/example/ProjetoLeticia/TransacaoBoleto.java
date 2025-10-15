package com.example.ProjetoLeticia;
import com.example.ProjetoLeticia.core.Transacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TransacaoBoleto extends Transacao implements Boleto {

    private String linhaDigitavel;


    public TransacaoBoleto (String remetente, double limite, String destinatario, double saldo, double mrd, int nParcelas, String codBarras) {
        super(remetente, limite, destinatario, saldo);
        this.linhaDigitavel = codBarras;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + "\nCódigo Barras: %s",
                this.linhaDigitavel);
    }

    @Override
    public boolean realizarTransacao (double valor) {
        if (validarBoleto() && verificarTransacao(valor)) {
            this.limiteRemetente -= valor;
            this.saldoDestinatario += valor;
            return true;
        }
        return false;
    }

    @Override
    public boolean validarBoleto() {
        String texto = "^0019[0-9]{43}$";
        var pattern = Pattern.compile(texto);
        var matcher = pattern.matcher(linhaDigitavel);
        return matcher.find();
    }

    public double pagarBoleto(){
        double valorInteiro = Double.parseDouble(linhaDigitavel.substring(37, 45));
        double valorDecimal = Double.parseDouble(linhaDigitavel.substring(45, 47));

        return valorInteiro + 1;
    }
}
