package com.letscode;

import lombok.AllArgsConstructor;
import lombok.Cleanup;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;

@AllArgsConstructor
public class CentralIntelligence implements CIView {
    private Rebel[] listaRebels;
    private int option = 0, count = 0;
    Scanner sc;
    Random random;

    public CentralIntelligence() {
        this.listaRebels = new Rebel[20];
        this.random = new Random();
        this.sc = new Scanner(System.in);
    }

    private void addRebels(String name, int age, Race race){
        int index = random.nextInt(20);

        Rebel b = Rebel.builder()
                .name(name)
                .age(age)
                .race(race)
                .build();
        if(this.listaRebels[index] == null){
            listaRebels[index] = b;
            System.out.println("Rebelde admitido na aliança com sucesso.");
        }else{
            System.err.println("Solicitação para ingresso na causa foi NEGADO.");
            System.err.println("Tente novamente, em outro momento.");
        }
        count++;

    }

    private void printQuantityRebels(){
        int humano = 0, greek = 0, rakata = 0;
        System.out.println("----------------------------");
        System.out.println("Quantidade de Rebeldes");
        for(int i = 0; i < listaRebels.length; i++) {
            if(listaRebels[i] != null){
                if(listaRebels[i].getRace() == Race.Humano){
                    humano++;
                }else if(listaRebels[i].getRace() == Race.Greek){
                    greek++;
                }else if(listaRebels[i].getRace() == Race.Rakata){
                    rakata++;
                }
            }
        }
        System.out.println(humano + " - Humanos");
        System.out.println(greek + " - Greek");
        System.out.println(rakata + " - Rakata");
        System.out.println(count - (humano + greek + rakata) + " - Rebeldes não admitodos na aliança.");
        System.out.println("----------------------------");
    }

    @Override
    public void startAPP() {
        System.out.println("--- Bem-vindo a Inteligência Cental ---");

        do{
            System.out.println("1 - Solicitar ingresso de Rebelde");
            System.out.println("2 - Quantidade Rebeldes");
            System.out.println("3 - Imprimir relátorio");
            System.out.println("4- Sair");
            System.out.print("Informe sua opção: ");
            option = sc.nextInt();

            switch (option){
                case 1:
                    joinAlliance();
                    break;
                case 2:
                    printQuantityRebels();
                    break;
                case 3:
                    try {
                        generateReport();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    sc.close();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }while (option != 4);
    }

    @Override
    public void joinAlliance() {
        this.sc = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("Informe os dados pessoais do ingressante");
        System.out.print("Nome: ");
        String name = sc.nextLine();

        System.out.print("Idade: ");
        int age = sc.nextInt();

        System.out.println("Tipos de Raça: ");
        System.out.println("1 - Humano");
        System.out.println("2 - Greek");
        System.out.println("3 - Rakata");
        System.out.print("Informe sua opção: ");
        int race = sc.nextInt();

        switch (race){
            case 1:
                addRebels(name, age, Race.Humano);
                break;
            case 2:
                addRebels(name, age, Race.Greek);
                break;
            case 3:
                addRebels(name, age, Race.Rakata);
                break;
            default:
                System.out.println("Raça inválida");
        }
        System.out.println("----------------------------");
    }

    @Override
    public void generateReport() throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("----------------------------");
        @Cleanup PrintWriter writer = new PrintWriter("report-rebel.txt", "UTF-8");
        writer.println("LISTA DE REBELDES");
        for(int i = 0; i < listaRebels.length; i++) {
            if(listaRebels[i] != null){
                writer.println("Nome: " + listaRebels[i].getName() +
                               " | Idade: " + listaRebels[i].getAge() +
                               " | Raça: " + listaRebels[i].getRace());
            }
        }
        System.out.println("-----> Relatório gerado com sucesso.");
    }
}
