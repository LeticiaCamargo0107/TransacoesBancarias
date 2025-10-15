package com.example.ProjetoLeticia;
import com.example.ProjetoLeticia.core.Transacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@SuperBuilder
@NoArgsConstructor
@Setter
public class TransacaoPix extends Transacao implements Pix{

    protected String chavepix;

    public TransacaoPix (String remetente, double limite, String destinatario, double saldo, String chavepix) {
        super(remetente, limite, destinatario, saldo);
        this.chavepix = chavepix;
    }

    public String toString() {
        return String.format(super.toString() + "\nChave Pix: %s",
                this.chavepix);
    }

    @Override
    public boolean realizarTransacao (double valor) {
        if (validarEmail() && verificarTransacao(valor) || validarCPF() && verificarTransacao(valor) || validarTelefone() && verificarTransacao(valor)) {
            limiteRemetente -= valor;
            saldoDestinatario += valor;
            return true;
        }
        return false;
    }

    @Override
    public String definirChavePix() {
        if (validarCPF()) {
            return "CPF";
        }
        else if (validarEmail()) {
            return "email";
        }
        else if (validarTelefone()) {
            return "telefone";
        }
        return "não detectada";
    }

    public boolean validarCPF(){
        String texto = "^[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}-?[0-9]{2}$";
        Pattern pattern = Pattern.compile(texto);
        Matcher matcher = pattern.matcher(chavepix);
        return matcher.find();
    }

    public boolean validarTelefone(){
        String texto = "^55 \\(?[0-9]{2}\\)? [0-9]{5}-?[0-9]{4}$";
        Pattern pattern = Pattern.compile(texto);
        Matcher matcher = pattern.matcher(chavepix);
        return matcher.find();
    }

    public boolean validarEmail(){
        String texto = "^[a-z].*\\.[a-z].*(@[a-z].*\\.com.br|@[a-z].*\\.org.br|@[a-z].*\\.com)$";
        Pattern pattern = Pattern.compile(texto);
        Matcher matcher = pattern.matcher(chavepix);
        return matcher.find();
    }
}
