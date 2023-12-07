package funcionario;

import pessoa.Pessoa;
import regras.Salario;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Funcionario extends Pessoa implements Salario {

    private BigDecimal salario;
    private String funcao;

    DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DecimalFormat decFormat = new DecimalFormat("###,###.##");

    public Funcionario() {
    }

    public Funcionario(BigDecimal salario, String funcao) {
        this.salario = salario;
        this.funcao = funcao;
    }

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(salario, that.salario) && Objects.equals(funcao, that.funcao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salario, funcao);
    }

    @Override
    public String toString() {
        return "Nome= " + getNome() +
                ", data de Nascimento= " + getDataNascimento().format(formatoData) +
                ", salario= " + decFormat.format(this.salario) +
                ", funcao= " + this.funcao ;
    }

    @Override
    public BigDecimal aumentoDeSalario() {
        return this.salario.multiply(new BigDecimal("1.10")).setScale(2, BigDecimal.ROUND_UP);
    }

    @Override
    public String quantidadeSalarioMinimo() {
        BigDecimal salarios = this.salario.divide(new BigDecimal("1212.00"),1, BigDecimal.ROUND_DOWN);
        return decFormat.format(salarios);
    }
}
