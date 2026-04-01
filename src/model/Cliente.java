package model;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
    private String nome;
    private String cpf;
    private String telefone;
    private double rendaMensal;

    public Cliente(String cpf, String nome, double rendaMensal, String telefone) {
        this.cpf = cpf;
        this.nome = nome;
        this.rendaMensal = rendaMensal;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getRendaMensal() {
        return rendaMensal;
    }

    public void setRendaMensal(double rendaMensal) {
        this.rendaMensal = rendaMensal;
    }

    public static ArrayList<Cliente> inserirClientes(Scanner scanner){
        ArrayList<Cliente> clientes = new ArrayList<>();
        char opcao = 'S';
        while (Character.toUpperCase(opcao) == 'S') {

            System.out.println("Digite os Dados do Cliente:");


            String nome = lerCampo(scanner, "Nome:");


            String cpf = lerCampo(scanner, "CPF: ");
           
            
            String telefone = lerCampo(scanner, "Telefone: ");

            double renda;
            while (true) {
                System.out.print("Renda Mensal: ");
                try {
                    renda = Double.parseDouble(scanner.nextLine().replace(',', '.'));
                } catch (NumberFormatException ex) {
                    System.out.println("Renda inválida. Digite um número válido.");
                    continue;
                }
                if (renda <= 0) {
                    System.out.println("Renda deve ser maior que zero. Tente novamente.");
                    continue;
                }else{
                    break;
                }
            
            }

            clientes.add(new Cliente(cpf, nome, renda, telefone));

            System.out.print("Deseja inserir outro Cliente? <S/N>: ");
            String resposta = scanner.nextLine().trim();
            opcao = resposta.isEmpty() ? 'N' : resposta.charAt(0);
        }

        return clientes;

    }

    public int parcelas(Scanner scanner){
        int parcelas;
        do{
            System.out.println("Escolha numero de Parcelas (36, 48, 60): ");
            parcelas = scanner.nextInt();
            if (parcelas != 36 && parcelas != 48 && parcelas != 60) {
                System.out.println("Operação invalida!");
                
            }
        }while(parcelas != 36 && parcelas != 48 && parcelas != 60);

        scanner.nextLine();
        return parcelas;
    }


    public boolean aprovadoCompra(Carro carro, int parcelas){
        double i = 0.018;
        double pv = carro.getValor();

        double valorParcela = pv * (i * Math.pow(1 + i, parcelas)) / (Math.pow(1 + i, parcelas) - 1);

        return valorParcela <= this.rendaMensal * 0.3;
    }

    public boolean simularCompra(Carro carro, int parcelas) {
        double i = 0.018;
        double pv = carro.getValor();

        double valorParcela = pv * (i * Math.pow(1 + i, parcelas)) / (Math.pow(1 + i, parcelas) - 1);

        System.out.println("Verificando Aprovação de cliente");
        System.out.println("---------------------------------");
        if (aprovadoCompra(carro, parcelas)){
            System.out.printf("Aprovado em %d x de R$ %.2f%n", parcelas, valorParcela);
            return true;
        } else {
            System.out.printf("Não aprovado. Parcela estimada: R$ %.2f (máx 30%% da renda).%n", valorParcela);
            return false;
        }
    }

    public static boolean isClient(ArrayList<Cliente> lista, String dados) {
        for (Cliente c : lista) {
            if (c.getNome().equalsIgnoreCase(dados.trim()) || c.getCpf().equalsIgnoreCase(dados.trim())) {
                return true;
            }
        }
        return false;
    }

    public static void exibirClientes(ArrayList<Cliente> listaClientes){
        if (listaClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente cliente : listaClientes){
            System.out.printf("Nome: %s | CPF: %s | Telefone: %s | Renda: R$ %.2f%n",
                    cliente.getNome(), cliente.getCpf(), cliente.getTelefone(), cliente.getRendaMensal());
        }
    }

    public static String lerCampo(Scanner scanner, String mensagem) {
        String valor;
        while (true) {
            System.out.print(mensagem);
            valor = scanner.nextLine().trim();

            if (valor.isEmpty()) {
                String campo  = mensagem.replace(':', ' ');
                System.out.println(campo +"Invalido. Tente novamente!");
            } else {
                return valor;
            }
    }
    }
    public void dadosCliente(){
        System.out.println("                  NOME |       CPF     |       TELEFONE     |     RENDA");
        System.out.printf("Cliente encontrado: %s | %s | %s | R$ %.2f%n", nome, cpf, telefone, rendaMensal);
    }
}

