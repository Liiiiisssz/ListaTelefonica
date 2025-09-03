package com.listatelefonica;

import com.listatelefonica.dao.ContatoDAO;
import com.listatelefonica.model.Contato;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        inicio();
    }

    public static void inicio(){
        boolean sair = false;
        System.out.println(" __________________________________");
        System.out.println("|       CADASTRO DE CONTATOS       |");
        System.out.println("|----------------------------------|");
        System.out.println("| 1. Cadastrar contato             |");
        System.out.println("| 2. Listar todos os contatos      |");
        System.out.println("| 3. Buscar contato por nome       |");
        System.out.println("| 4. Atualizar dados de um contato |");
        System.out.println("| 5. Remover contato               |");
        System.out.println("| 6. Sair do sistema               |");
        System.out.println("|__________________________________|");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao){
            case 1 ->{
                cadastrarContato();
            }
            case 2 ->{
                listarContatos();
            }
            case 3 ->{
                buscar();
            }
            case 4 ->{
                atualizarContato();
            }
            case 5 ->{
                removerContato();
            }
            case 6 ->{
                sair = true;
            }
        }
        if(!sair){
            inicio();
        }
    }

    public static void cadastrarContato(){
        System.out.println("-- CADASTRAR CONTATO --");
        inserirDados(1, 0);
    }

    public static void listarContatos(){
        System.out.println("-- CONTATOS CADASTRADOS --");
        var dao = new ContatoDAO();
        try{
            List<Contato> contatos = dao.listarContatos();

            List<Integer> idContatos = exibirContatos(contatos);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void buscar(){
        System.out.println("-- BUSCAR CONTATO POR NOME --");
        System.out.println("Nome do contato:");
        String nome = sc.nextLine();

        var dao = new ContatoDAO();
        try{
            List<Contato> contatos = dao.buscarContato(nome);

            List<Integer> idContatos = exibirContatos(contatos);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void atualizarContato(){
        System.out.println("-- ATUALIZAR CONTATO --");
        List<Integer> idContatos = new ArrayList<>();
        List<Contato> contatos = new ArrayList<>();
        var dao = new ContatoDAO();
        try{
            contatos = dao.listarContatos();
            idContatos = exibirContatos(contatos);
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("ID do contato para edição:");
        int id = sc.nextInt();
        sc.nextLine();

        if(idContatos.contains(id)){
            inserirDados(2, id);
        } else {
            System.out.println("Opção inválida!");
            atualizarContato();
        }
    }

    public static void removerContato(){
        System.out.println("-- REMOVER CONTATO --");

    }

    public static List<Integer> exibirContatos(List<Contato> contatos){
        List<Integer> idContatos = new ArrayList<>();
        for(Contato contato : contatos){
            System.out.println("\n---------------------------------------");
            System.out.println("ID: " + contato.getId());
            System.out.println("Nome: " + contato.getNome());
            System.out.println("Telefone: " + contato.getTelefone());
            System.out.println("Email: " + contato.getEmail());
            System.out.println("Observação: " + contato.getObservacao());

            idContatos.add(contato.getId());
        }
        return idContatos;
    }

    public static void inserirDados(int opcao, int id){
        var dao = new ContatoDAO();
        System.out.println("Nome do contato:");
        String nome = sc.nextLine();

        System.out.println("Telefone do contato:");
        String telefone = sc.nextLine();

        System.out.println("Email do contato:");
        String email = sc.nextLine();

        System.out.println("Observação:");
        String observacao = sc.nextLine();

        switch (opcao){
            case 1 ->{
                try{
                    var contato = new Contato(nome, telefone, email, observacao);
                    dao.inserirContato(contato);
                    System.out.println("Contato inserido com sucesso!");
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            case 2 ->{
                try{
                    var contato = new Contato(id, nome, telefone, email, observacao);
                    dao.atualizarContato(contato);
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}