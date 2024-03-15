package views;


import controllers.CommandHandlers;
import controllers.Commands.AbstractCommand;
import models.Collection;

import java.util.*;

public class ScriptMode {

    private final CommandHandlers commandView;
    private final String beginCommandLine;
    private final Map<Integer, LinkedHashMap<String, String>> commandHistory;  // Track occurrences of each command
    String beginLine;
    private Collection collectionView;
    private Integer uniqueId = 0;


    public ScriptMode(CommandHandlers commandView, Collection collectionView) {
        this.beginCommandLine = "> ";
        this.beginLine = "(lab5) rublev@MacBook-Pro-Valerij src % ";
        this.commandView = commandView;
        this.commandHistory = new HashMap<>();
        this.collectionView = collectionView;
    }

    void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    void newLine() {
        int maxLength = 606;
        System.out.println("rublev@MacBook-Pro-Valerij src % bash script.sh");
    }

    String centerText(String input) {
        String[] lines = input.split("\n");

        int maxLength = 606;
        for (String line : lines) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }

        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            int padding = (maxLength - line.length()) / 2;
            String paddedLine = " ".repeat(padding) + line;
            result.append(paddedLine).append("\n");
        }

        return result.toString();
    }

    public void updateHistory(String command, String result) {
        LinkedHashMap<String, String> commandEntry = new LinkedHashMap<>();
        commandEntry.put(command, result);
        commandHistory.put(uniqueId, commandEntry);
        this.uniqueId++;
    }

    public void view() {
        clearConsole();
        this.newLine();
        int order = 1;

        for (Map.Entry<Integer, LinkedHashMap<String, String>> historyEntry : commandHistory.entrySet()) {
            int key = historyEntry.getKey();
            LinkedHashMap<String, String> commandEntry = historyEntry.getValue();

            for (Map.Entry<String, String> entry : commandEntry.entrySet()) {
                System.out.println(this.beginLine + entry.getKey());
                System.out.println(entry.getValue());
            }

        }

        Scanner scanner = new Scanner(System.in);
        System.out.print(beginLine);
        while (true) {
            if (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                if (command.isEmpty()) {
                    this.view();
                } else {
                    Request request = new Request(command, this.collectionView);
                    AbstractCommand.Response<String> response = this.commandView.executeCommand(request);
                    if (response != null) {
                        this.collectionView = response.musicBands();
                        if (response.success()) {
                            this.updateHistory(request.command(), response.result());
                        } else {
                            this.updateHistory(request.command(), response.errorMessage());
                        }
                    } else {
                        this.updateHistory(request.command(), "     Command not found or encountered an issue.");
                    }
                    this.view();
                }
            } else {
                System.exit(400);
            }
        }
    }

    public record Request(String command, Collection oldCollection) {
        public int length() {
            return command.length();
        }
    }


}

