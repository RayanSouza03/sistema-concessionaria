package app;

import java.util.ArrayList;
import java.util.Scanner;
import model.Carro;
import model.Cliente;

public class AppConce {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        ArrayList<Carro> estoque = new ArrayList<>();
        Carro confirmador = new Carro(null, null, null, 0);

        estoque.add(new Carro("Sandero", "2020", "CCK4F98", 45000));
        estoque.add(new Carro("Palio", "2012", "CCM3F23", 12000));
        estoque.add(new Carro("Sandero", "2014", "DDK2W33", 34500));

        System.out.println("Programa executado!");
        System.out.println("------------------------");

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Inserir Cliente");
            System.out.println("2 - Inserir Carro");
            System.out.println("3 - Exbir lista de Clientes");
            System.out.println("4 - Exibir lista de Carros");
            System.out.println("5 - Procurar Cliente");
            System.out.println("6 - Procurar Carro");
            System.out.println("7 - Vender Carro");
            System.out.println("8 - Sair");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Opção inválida, digite um número de 1 a 5.");
                continue;
            }

            if (opcao == 1) {
                listaClientes.addAll(Cliente.inserirClientes(scanner));
                System.out.println("Clientes cadastrados. Total de clientes: " + listaClientes.size());
            }else if (opcao == 2) {
                estoque.addAll(Carro.inserirCarros(scanner));
                System.out.println("Carros cadastrados. Total de Carros: " + estoque.size());
            }else if (opcao == 3){
                Cliente.exibirClientes(listaClientes);
            }else if (opcao == 4){
                Carro.exibirCarros(estoque);
            }else if (opcao == 5) {
                System.out.println("Qual cliente deseja procurar? (NOME OU CPF): ");
                String clienteDesejado = scanner.nextLine().trim();

                boolean encontrado = false;
                for (Cliente cliente : listaClientes){
                    if (cliente.isClient(listaClientes,clienteDesejado)){
                        cliente.dadosCliente();
                        encontrado = true;
                    }
                }
                if (!encontrado) {
                    System.out.println("Cliente Não encontrado no sistema.");
                    
                }
            }else if (opcao == 6) {

                

                System.out.print("Qual carro deseja procurar: ");
                String modeloDesejado = scanner.nextLine().trim();

                boolean encontrado = false;
                for (Carro carro : estoque) {
                    if (carro.isModelo(modeloDesejado)) {
                        carro.dadosCarro();
                        encontrado = true;
                    }
                }

                if (!encontrado) {
                    System.out.println("Carro não disponível no estoque.");
                }
            }else if (opcao == 7) {
                if (listaClientes.isEmpty()) {
                    System.out.println("Nenhum cliente cadastrado. Insira um cliente primeiro.");
                    continue;
                }
                if (estoque.isEmpty()) {
                    System.out.println("Estoque vazio. Nenhum carro disponível.");
                    continue;
                }

                System.out.print("Qual cliente deseja comprar um carro?: ");
                String clienteComprador = scanner.nextLine().trim();

                while (!Cliente.isClient(listaClientes, clienteComprador)) {
                    System.out.println("Cliente não encontrado no sistema. Tente outro.");
                    System.out.print("Qual cliente deseja comprar um carro: ");
                    clienteComprador = scanner.nextLine().trim();
                }

                Cliente comprador = null;
                for (Cliente c : listaClientes) {
                    if (c.getNome().equalsIgnoreCase(clienteComprador)) {
                        comprador = c;
                        break;
                    }
                }
                if (comprador == null) {
                    System.out.println("Erro interno: cliente não encontrado após validação. Tente novamente.");
                    continue;
                }

                Carro carroEscolhido = null;
                while (carroEscolhido == null) {
                    System.out.print("Digite a placa do carro escolhido: ");
                    String placaEscolhida = scanner.nextLine().trim();
                    for (Carro carro : estoque) {
                        if (carro.getPlaca().equalsIgnoreCase(placaEscolhida)) {
                            carroEscolhido = carro;
                            break;
                        }
                    }
                    if (carroEscolhido == null) {
                        System.out.println("Placa não encontrada! Tente novamente.");
                    }
                }

                int parcelas;
                char opc;

                System.out.print("Deseja Simular a Compra antes? <S/N>: ");
                String entrada = scanner.nextLine().trim();
                opc = entrada.isEmpty() ? 'N' : Character.toUpperCase(entrada.charAt(0));

                if (opc == 'S') {

                    
                    parcelas = comprador.parcelas(scanner);

                    boolean aprovado = comprador.simularCompra(carroEscolhido, parcelas);

                    if (!aprovado) {
                        System.out.println("Cliente não aprovado.");
                        continue;
                    }

                    
                    System.out.print("Deseja continuar com a compra? <S/N>: ");
                    entrada = scanner.nextLine().trim();
                    opc = entrada.isEmpty() ? 'N' : Character.toUpperCase(entrada.charAt(0));

                    if (opc != 'S') {
                        System.out.println("Compra cancelada.");
                        continue;
                    }

                } else {
                    
                    parcelas = comprador.parcelas(scanner);
                }


                if (comprador.aprovadoCompra(carroEscolhido, parcelas)) {

                    if (carroEscolhido.venderCarro(comprador, parcelas)) {
                        estoque.remove(carroEscolhido);
                        System.out.println("Venda concluída com sucesso.");
                    } else {
                        System.out.println("Erro ao vender o carro.");
                    }

                } else {
                    System.out.println("Financiamento não aprovado.");
                }

            
            } else if (opcao == 8) {
                break;

            } else {
                System.out.println("Opção inválida.");
            }
    }

    scanner.close ();

    System.out.println (

"Programa encerrado!");

    }
}
