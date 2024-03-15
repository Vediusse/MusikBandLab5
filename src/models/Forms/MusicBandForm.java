package models.Forms;

import models.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MusicBandForm {

    private final Validator validator;

    public MusicBandForm() {
        this.validator = new Validator();
    }

    public MusicBand createNew() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("     Имя новой Банды: ");
            String name = getNonEmptyInput(scanner);
            validator.validateName(name, "Name");

            System.out.print("     Координата X: ");
            Long x = Long.parseLong(getNonEmptyInput(scanner));
            validator.validateLongNotNull(x, "X");

            System.out.print("     Координата Y: ");
            Double y = Double.parseDouble(getNonEmptyInput(scanner));
            validator.validateDoubleNotNull(y, "Y");
            validator.validateYValue(y);

            System.out.print("     Количество участников: ");
            Integer numberOfParticipants = Integer.parseInt(getNonEmptyInput(scanner));
            validator.validateNumberOfParticipants(numberOfParticipants, "Number of parcipitants");

            System.out.print("     Количество синглов: ");
            long singlesCount = Long.parseLong(getNonEmptyInput(scanner));
            validator.validateSinglesCount(singlesCount, "Singles Count");

            System.out.print("     Жанр (RAP, PSYCHEDELIC_CLOUD_RAP, BRIT_POP): ");
            String genreString = getNonEmptyInput(scanner).toUpperCase();
            MusicGenre genre = MusicGenre.valueOf(genreString);
            validator.validateMusicGenre(genre, "Genre");


            ZonedDateTime establishmentDate = this.inputEstablishmentDate(scanner);


            System.out.print("     Кто такой фронтмен (Y - если очень хочется , N - если  хочется): ");
            String isFrontman = scanner.nextLine();

            if (isFrontman.equalsIgnoreCase("Y")) {
                // Proceed with frontman creation
                Person frontman = FrontmenForm(scanner);
                if (frontman != null) {
                    return new MusicBand(name, x, y, numberOfParticipants, singlesCount, genre, establishmentDate, frontman);
                }
            } else {
                // Create MusicBand without a frontman
                return new MusicBand(name, x, y, numberOfParticipants, singlesCount, genre, establishmentDate);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("     └──Неверная запись поля");
            this.createNew();
        }
        return null;
    }

    private String getNonEmptyInput(Scanner scanner) {
        String input = "";
        while (input.isEmpty()) {
            try {
                input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Please enter a non-empty value.");
                    System.out.println("     ");
                }
            } catch (NoSuchElementException e) {
                System.out.println("Input terminated unexpectedly. Please try again.");
                System.exit(400);
            }
        }
        return input;
    }

    private Person FrontmenForm(Scanner scanner) {
        try {
            System.out.print("          Имя фронтмена: ");
            String frontManName = scanner.nextLine();

            System.out.print("          Рост фронтмена: ");
            Integer frontManHeight = Integer.parseInt(scanner.nextLine());
            validator.validateIntegerNotNull(frontManHeight, "Рост");

            System.out.print("          Цвет глаз фронтмена (RED, BLACK, BLUE, YELLOW, BROWN): ");
            String eyeColorString = scanner.nextLine();
            Color eyeColor = Color.valueOf(eyeColorString);
            validator.validateColor(eyeColor, "Цвет глаз");

            System.out.print("          Цвет волос фронтмена (GREEN, BLACK, BLUE, ORANGE, BROWN): ");
            String hairColorString = scanner.nextLine();
            Color hairColor = Color.valueOf(hairColorString);
            validator.validateColor(hairColor, "Цвет волос");

            System.out.print("          Национальность фронтмена (GERMANY, SPAIN, ITALY): ");
            String nationalityString = scanner.nextLine();
            Country nationality = Country.valueOf(nationalityString);
            validator.validateCountry(nationality, "Национальность");

            return new Person(frontManName, frontManHeight, eyeColor, hairColor, nationality);

        } catch (IllegalArgumentException e) {
            System.out.println("          └──Неверная запись поля");
            return FrontmenForm(scanner); // Recursive call to handle incorrect input
        } catch (Exception e) {
            scanner.close();
            System.out.println("\nEnd of input. Exiting...");
        }
        return null; // Return null if no frontman is specified
    }

    private ZonedDateTime inputEstablishmentDate(Scanner scanner) {
        while (true) {
            try {

                int currentYear = Year.now().getValue();

                System.out.print("          Год (гггг) (1600-" + currentYear + "): ");
                int year = Integer.parseInt(scanner.nextLine());
                if (year < 1600 || year > currentYear) {
                    throw new DateTimeException("Invalid year. Please enter a valid year (1600-" + currentYear + ").");
                }
                int currentMonth = LocalDate.now().getMonthValue();
                if (year == currentYear) {
                    System.out.print("          Месяц (мм) (1-" + currentMonth + "): ");
                } else {
                    System.out.print("          Месяц (мм) (1-12): ");
                }
                int month = Integer.parseInt(scanner.nextLine());


                if ((year == currentYear && month > currentMonth) || month < 1 || month > 12) {
                    throw new DateTimeException("Invalid month. Please enter a valid month (1-" + currentMonth + ").");
                }
                int currentDay = LocalDate.now().getDayOfMonth();
                int maxDaysInMonth = YearMonth.of(year, month).lengthOfMonth();

                if (year == currentYear && month == currentMonth) {
                    System.out.print("          День (дд) (1-" + currentDay + "): ");
                } else {
                    System.out.print("          День (дд) (1-" + maxDaysInMonth + "): ");
                }
                int day = Integer.parseInt(scanner.nextLine());


                /*
                if (((month == 2) && ((year % 4 != 0) || (year % 100 != 0)) && (day < 1 || day > 28)) ||
                        ((month == 2) && (year % 4 == 0) && (day < 1 || day > 29)) ||
                        ((month % 2 != 0) && (day < 1 || day > 31)) ||
                        ((month % 2 == 0) && (day < 1 || day > 30)) ||
                        (year == currentYear && month == currentMonth && day > LocalDate.now().getDayOfMonth())) {
                    throw new DateTimeException("Invalid day. Please enter a valid day.");
                 */
                if (day < 1 || day > maxDaysInMonth ||
                        (year == currentYear && month == currentMonth && day > LocalDate.now().getDayOfMonth())) {
                    throw new DateTimeException("Invalid day. Please enter a valid day.");
                }

                System.out.print("          Время (чч:мм:сс): ");
                String timeString = scanner.nextLine();

                if (!timeString.matches("\\d{2}:\\d{2}:\\d{2}")) {
                    throw new DateTimeException("Invalid time format. Please enter a valid time in HH:mm:ss format.");
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String fullDateTimeString = String.format("%04d-%02d-%02d %s", year, month, day, timeString);
                LocalDateTime localDateTime = LocalDateTime.parse(fullDateTimeString, formatter);

                ZoneId zoneId = ZoneId.systemDefault();
                ZonedDateTime userDateTime = localDateTime.atZone(zoneId);
                ZonedDateTime currentDateTime = ZonedDateTime.now(zoneId);

                if (userDateTime.isAfter(currentDateTime)) {
                    throw new DateTimeException("Invalid date. Please enter a date not exceeding the current date.");
                }

                return userDateTime;

            } catch (NumberFormatException | DateTimeException e) {
                System.out.println("          └──  Пожалуйста, введите снова.");
            }
        }
    }

    public MusicBand update(MusicBand oldMusikBand) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("     Новое имя для " + oldMusikBand.getName() + " : ");
            String name = scanner.nextLine();
            if (name != null) {
                validator.validateName(name, "Name");
                oldMusikBand.setName(name);
            }


            System.out.print("     Координата X: ");
            String xString = scanner.nextLine();
            if (xString != null) {
                Long x = Long.parseLong(xString);
                oldMusikBand.getCoordinates().setX(x);
            }

            System.out.print("     Координата Y: ");
            String yString = scanner.nextLine();
            if (yString != null) {
                Double y = Double.parseDouble(yString);
                validator.validateYValue(y);
                oldMusikBand.setCoordinates(new Coordinates(oldMusikBand.getCoordinates().getX(), y));
            }

            System.out.print("     Количество участников (" + oldMusikBand.getNumberOfParticipants() + ") : ");
            String numberOfParticipantsString = scanner.nextLine();
            if (numberOfParticipantsString != null) {
                Integer numberOfParticipants = Integer.parseInt(numberOfParticipantsString);
                validator.validateNumberOfParticipants(numberOfParticipants, "Number of parcipitants");
                oldMusikBand.setNumberOfParticipants(Integer.parseInt(numberOfParticipantsString));
            }
            System.out.print("     Количество синглов(" + oldMusikBand.getSinglesCount() + ") : ");

            String singlesCountString = scanner.nextLine();
            if (singlesCountString != null) {
                long singlesCount = Long.parseLong(singlesCountString);
                validator.validateSinglesCount(singlesCount, "Singles Count");
                oldMusikBand.setSinglesCount(singlesCount);
            }

            System.out.print("     Жанр (RAP, PSYCHEDELIC_CLOUD_RAP, BRIT_POP) сейчас (" + oldMusikBand.getGenre() + ") : ");
            String genreString = scanner.nextLine();

            if (genreString != null) {
                MusicGenre genre = MusicGenre.valueOf(genreString);
                validator.validateMusicGenre(genre, "Genre");
                oldMusikBand.setGenre(genre);
            }

            System.out.print("     establishmentDate будем меняеть ? (сейчас " + oldMusikBand.getEstablishmentDate() + " ) (Y - если да)  : ");
            String isEstablishmentDate = scanner.nextLine();

            ZonedDateTime establishmentDate;
            if (isEstablishmentDate.equalsIgnoreCase("Y")) {
                establishmentDate = this.inputEstablishmentDate(scanner);
                oldMusikBand.setEstablishmentDate(establishmentDate);
            }

            Person frontMan = null;
            if (oldMusikBand.getFrontMan() == null) {
                System.out.print("     Создать фронтмена (Y - да, N - нет): ");
                String createFrontman = scanner.nextLine();
                if (createFrontman.equalsIgnoreCase("Y")) {
                    frontMan = FrontmenForm(scanner);
                }
            } else {
                System.out.print("     Изменить фронтмена (Y - да, N - нет): ");
                String updateFrontman = scanner.nextLine();
                if (updateFrontman.equalsIgnoreCase("Y")) {
                    frontMan = FrontmenForm(scanner);
                }
            }

            return oldMusikBand;
        } catch (IllegalArgumentException e) {
            System.out.println("     └──Неверная запись поля");
            this.createNew();
        } catch (Exception e) {
            scanner.close();
            System.out.println("\nEnd of input. Exiting...");
        }
        return oldMusikBand;
    }
}
