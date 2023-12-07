package main;

import funcionario.Funcionario;
import pessoa.Pessoa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Cole o caminho absoluto do arquivo Funcionarios.csv aqui: ");

        String path = scanner.next();

        DecimalFormat formatoEmReal = new DecimalFormat("###,###.##");

        List<Funcionario> listaDePessoas = new ArrayList<>();

        adicionarFuncionariosALista(path, listaDePessoas);

        System.out.println("LISTA DE FUNCIONÁRIOS COM O JOÃO");
        for (Funcionario funcionario: listaDePessoas) {
            System.out.println(funcionario);
        }

        System.out.println();

        System.out.println("João foi removido da lista = " + listaDePessoas.removeIf(funcionario ->
                funcionario.getNome().equalsIgnoreCase("João")));

        System.out.println();

        System.out.println("LISTA DE TODOS OS FUNCIONÁRIOS SEM O JOÃO");
        for (Funcionario funcionario: listaDePessoas) {
            System.out.println(funcionario);
        }

        System.out.println();

        listaDePessoas.forEach(funcionario -> funcionario.setSalario(funcionario.aumentoDeSalario()));

        System.out.println("AUMENTO DE SALÁRIO EM 10%");
        for (Funcionario funcionario: listaDePessoas) {
            System.out.println(funcionario);
        }

        System.out.println();

        Map<String, List<Funcionario>> funcionariosPorFuncao = listaDePessoas.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("LISTA AGRUPADA POR FUNÇÃO");

        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(funcionario -> System.out.println(funcionario.getNome()));
        });

        System.out.println();

        List<Funcionario> mesDeNascimentoOutubroEDezembro = listaDePessoas.stream().filter(funcionario ->
                funcionario.getDataNascimento().getMonthValue() == 10 ||
                        funcionario.getDataNascimento().getMonthValue() == 12).toList();

        System.out.println("FUNCIONÁRIOS QUE FAZEM ANIVERSÁRIO EM OUTUBRO E DEZEMBRO");
        for (Funcionario funcionario: mesDeNascimentoOutubroEDezembro) {
            System.out.println(funcionario.getNome());
        }

        System.out.println();

        int maiorIdade = 0;
        String nome = "";

        for (Funcionario funcionario: listaDePessoas) {
            int idade = LocalDate.now().getYear() - funcionario.getDataNascimento().getYear();
            if (idade > maiorIdade) {
                nome = funcionario.getNome();
                maiorIdade = idade;
            }
        }

        System.out.printf("A pessoa com a maior idade é %s que tem %d anos. \n", nome, maiorIdade);


        List<Funcionario> listaDeFuncionariosOrdenada = listaDePessoas.stream()
                .sorted(Comparator.comparing(Pessoa::getNome)).toList();

        System.out.println();

        System.out.println("LISTA  DE FUNCIONÁRIOS ORDENADA POR ORDEM ALFABÉTICA");

        for (Funcionario funcionario: listaDeFuncionariosOrdenada) {
            System.out.println(funcionario);
        }

        System.out.println();

        BigDecimal totalDeSalario = listaDeFuncionariosOrdenada.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        System.out.println("Salário total de todos os funcionários = R$" + formatoEmReal.format(totalDeSalario.setScale(2, BigDecimal.ROUND_HALF_UP)));

        System.out.println();

        System.out.println("QUANTIDADE DE SALÁRIOS MÍNIMOS PARA CADA FUNCIONÁRIO");
        for (Funcionario funcionario: listaDeFuncionariosOrdenada) {
            System.out.println(funcionario.getNome() + " recebe " + funcionario.quantidadeSalarioMinimo() + " salário(s) mínimo(s)");
        }

    }

    private static void adicionarFuncionariosALista(String path, List<Funcionario> listaDePessoas) {

        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] campos = line.split(",");
                String nome = campos[0];
                LocalDate dataNascimento = LocalDate.parse(campos[1], formatoData);
                BigDecimal salario = BigDecimal.valueOf(Double.parseDouble(campos[2]));
                String funcao = campos[3];
                listaDePessoas.add(new Funcionario(nome, dataNascimento, salario, funcao));
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}