package com.letscode.controller;

import com.letscode.model.*;
import com.letscode.view.CIView;
import jakarta.validation.*;
import lombok.AllArgsConstructor;
import lombok.Cleanup;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
public class CentralIntelligence implements CIView {
    private ArrayList<Rebel> listaRebels;
    private int option = 0, count = 0;
    private static final Logger LOGGER = LogManager.getLogger(CentralIntelligence.class);
    Scanner sc;

    public CentralIntelligence() {
        this.listaRebels = new ArrayList<Rebel>();
        this.sc = new Scanner(System.in);
    }

    private void addRebels(String name, int age, Race race){

        Rebel b = Rebel.builder()
                .name(name)
                .age(age)
                .race(race)
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Rebel>> violations = validator.validate(b);

        try {
            requestAccess(b);
        } catch(Exception e) {
            LOGGER.fatal(e.getMessage());
        }

        violations.forEach(x -> LOGGER.error("Atributo: " + x.getPropertyPath() + " - " + x.getMessage()));
    }

    private void requestAccess(Rebel rebel){
        if(rebel.getAge() > 0 && rebel.getAge() < 40 && rebel.getName().length() < 20 && !rebel.getName().isBlank()){
            listaRebels.add(rebel);
            System.out.println("Rebelde admitido na aliança com sucesso.");
        } else{
            System.err.println("Solicitação para ingresso na causa foi NEGADO.");
            System.err.println("Tente novamente, em outro momento.");
        }

        count++;
    }

    private void printQuantityRebels(){
        int humano = 0, greek = 0, rakata = 0;
        System.out.println("----------------------------");
        System.out.println("Quantidade de Rebeldes");

        for (Rebel rebel : listaRebels) {
            if(!Objects.isNull(rebel)){
                if(rebel.getRace() == Race.HUMANO){
                    humano++;
                }else if(rebel.getRace() == Race.GREE){
                    greek++;
                }else if(rebel.getRace() == Race.RAKATA){
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
                addRebels(name, age, Race.HUMANO);
                break;
            case 2:
                addRebels(name, age, Race.GREE);
                break;
            case 3:
                addRebels(name, age, Race.RAKATA);
                break;
            default:
                System.out.println("Raça inválida");
        }
        System.out.println("----------------------------");
    }

    @Override
    public void generateReport() throws FileNotFoundException, UnsupportedEncodingException {
        System.out.println("----------------------------");
        listSort();
        @Cleanup PrintWriter writer = new PrintWriter("report-rebel.txt", "UTF-8");
        writer.println("LISTA DE REBELDES");
        for (Rebel rebel : listaRebels) {
            writer.println(count + " - "+ rebel.toString());
            count++;
        }
        System.out.println("-----> Relatório gerado com sucesso.");
    }

    private void listSort() {
        System.out.println("Qual parâmetro será usado para ordenar o relátorio?");
        System.out.println("1 - Nome");
        System.out.println("2 - Idade");
        System.out.println("3 - Raça");
        int option = sc.nextInt();

        switch (option) {
            case 1:
                Collections.sort(listaRebels, new SortByName());
                break;
            case 2:
                Collections.sort(listaRebels, new SortByAge());
                break;
            case 3:
                Collections.sort(listaRebels, new SortByRace());
                break;
            default:
                System.out.println("Opção inválida, por favor escolha uma opção válida do menu.");

        }
    }




}
