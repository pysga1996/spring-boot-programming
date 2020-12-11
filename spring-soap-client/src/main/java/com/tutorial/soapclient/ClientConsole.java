package com.tutorial.soapclient;

import com.tutorial.soapclient.clients.ClassClient;
import com.tutorial.soapclient.clients.CountryClient;
import com.tutorial.soapclient.constants.Command;
import com.tutorial.soapclient.models.Clazz;
import com.tutorial.soapclient.models.GetClassResponse;
import com.tutorial.soapclient.models.GetCountryResponse;
import com.tutorial.soapclient.models.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class ClientConsole implements CommandLineRunner {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(ClientConsole.class);

    public ClientConsole(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        String cmd;
        do {
            System.out.println("Enter command (Type 'exit' to quit):");
            cmd = scanner.nextLine();
            if (args.length > 0) {
                cmd = args[0];
            }
            try {
                doCommand(cmd, scanner);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        } while (cmd != null);
    }

    private void doCommand(String cmd, Scanner scanner) {
        Command command;
        try {
            command = Command.valueOf(cmd);
        } catch (Exception ex) {
            command = Command.EXIT;
        }
        switch (command) {
            case COUNTRIES:
                System.out.println("Enter country:");
                String country = scanner.nextLine();
                GetCountryResponse getCountryResponse = applicationContext.getBean(CountryClient.class).getCountry(country);
                if (getCountryResponse.getCountry() != null) {
                    System.out.println(String.format("Region: %s", getCountryResponse.getCountry().getCurrency()));
                } else {
                    System.err.println("Country not found!");
                }
                break;
            case CLAZZ:
                GetClassResponse getClassResponse = applicationContext.getBean(ClassClient.class).getClazz();
                if (getClassResponse.getClazz() != null) {
                    Clazz clazz = getClassResponse.getClazz();
                    System.out.println(String.format("Class name: %s", clazz.getName()));
                    ZonedDateTime zdt = clazz.getBeginDate().toGregorianCalendar().toZonedDateTime();
                    System.out.println(String.format("Class begin date: %s", formatter.format(zdt)));
                    System.out.println("Class's student List:");
                    for (Student student: clazz.getStudentList().getStudent()) {
                        System.out.println("--");
                        System.out.println(String.format("Class student id: %s", student.getId()));
                        System.out.println(String.format("Class student name: %s", student.getName()));
                        System.out.println(String.format("Class student age: %s", student.getAge()));
                        System.out.println("--");
                    }
                } else {
                    System.err.println("Class not found!");
                }
                break;
            default:
                System.exit(0);
        }
    }
}
