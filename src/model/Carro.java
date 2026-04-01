package model;
import java.util.ArrayList;
import java.util.Scanner;

public class Carro{
    private String modelo;
    private String ano;
    private String placa;
    private double valor;
    private Cliente cliente;

    public Carro(String modelo, String ano, String placa, double  valor) {
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.valor = valor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    Scanner scanner = new Scanner(System.in);
    public boolean venderCarro(Cliente cliente, int parcelas) {
        if (cliente.aprovadoCompra(this, parcelas)) {
            this.cliente = cliente;
            return true;
        } else {
            return false;
        }
    }

    public boolean isModelo(String modelo){
        return this.modelo != null && modelo != null && this.modelo.equalsIgnoreCase(modelo.trim());
    }

    public void dadosCarro(){
        System.out.printf("Carro encontrado: %s | %s | %s | R$ %.2f%n", modelo, ano, placa, valor);
    }

    public boolean confirmador(char opc){
        if (Character.toUpperCase(opc) == 'S'){
            return true;
        }
        return false;
    }
    public static void exibirCarros(ArrayList<Carro> listaCarros){
        if (listaCarros.isEmpty()) {
            System.out.println("Nenhum Carro cadastrado.");
            return;
        }
        for (Carro carro : listaCarros){
            System.out.printf("modelo: %s | Ano: %s | Placa: %s | Valor: R$ %.2f%n",
                    carro.getModelo(), carro.getAno(), carro.getPlaca(), carro.getValor());
        }
    }
    public static ArrayList<Carro> inserirCarros(Scanner scanner){
        ArrayList<Carro> estoque = new ArrayList<>();
        char opcao = 'S';
        while (Character.toUpperCase(opcao) == 'S') {

            System.out.println("Digite os Dados do Carro:");


            String modelo = lerCampo(scanner, "modelo:");


            String ano = lerCampo(scanner, "Ano: ");
           
            
            String placa = lerCampo(scanner, "Placa: ");
            placa = placa.toUpperCase();
            
            
            double valor;
            while (true) {
                System.out.print("Valor: ");
                try {
                    valor = Double.parseDouble(scanner.nextLine().replace(',', '.'));
                } catch (NumberFormatException ex) {
                    System.out.println("Valor inválido. Digite um número válido.");
                    continue;
                }
                if (valor <= 0) {
                    System.out.println("Renda deve ser maior que zero. Tente novamente.");
                    continue;
                }else{
                    break;
                }
            
            }

            estoque.add(new Carro(modelo, ano, placa, valor));

            System.out.print("Deseja inserir outro Carro? <S/N>: ");
            String resposta = scanner.nextLine().trim();
            opcao = resposta.isEmpty() ? 'N' : resposta.charAt(0);
        }

        return estoque;

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
    

    

}
