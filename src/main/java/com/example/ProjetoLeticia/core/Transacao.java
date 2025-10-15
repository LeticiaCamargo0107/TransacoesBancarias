package com.example.ProjetoLeticia.core;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@NoArgsConstructor
@SuperBuilder
public abstract class Transacao {

    @Setter
    protected String remetente;
    protected double limiteRemetente;
    @Setter
    protected String destinatario;
    protected double saldoDestinatario;

    public Transacao (String remetente, double limite, String destinatario, double saldo) {
        this.remetente = remetente;
        this.limiteRemetente = limiteRemetente;
        this.destinatario = destinatario;
        this.saldoDestinatario = saldoDestinatario;
    }

    @Override
    public String toString() {
        return String.format("Remetente: %s \nLimite: %.2f \nDestinatário: %s \nSaldo: %.2f",
                this.remetente,
                this.limiteRemetente,
                this.destinatario,
                this.saldoDestinatario);
    }

    public abstract boolean realizarTransacao (double valor);

    protected boolean verificarTransacao (double valor) {
        return valor < this.limiteRemetente;
    }
}