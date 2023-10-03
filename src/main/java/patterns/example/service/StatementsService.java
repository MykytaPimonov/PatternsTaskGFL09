package patterns.example.service;

import patterns.example.Main;
import patterns.example.model.Customer;
import patterns.example.model.Rental;
import patterns.example.model.enums.MovieType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

public class StatementsService {

    private final File file;

    public StatementsService() {
        try {
            URL url = Main.class.getClassLoader().getResource("out/stat");
            if (url == null) {
                throw new IOException("Not found file");
            }
            URI uri = url.toURI();
            file = new File(uri);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String statement(Customer customer) {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");
        for (Rental each : customer.getRentals()) {
            double thisAmount = getThisAmount(each);
            frequentRenterPoints++;
            if ((each.getMovie().getType() == MovieType.NEW_RELEASE) && each.getDaysRented() > 1)
                frequentRenterPoints++;
            result.append("\t").append(each.getMovie().getTitle()).append("\t").append(thisAmount).append("\n");
            totalAmount += thisAmount;
        }
        result.append("Amount owed is ").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
        return result.toString();
    }

    public String statementOnHtmlPage(Customer customer) {
        String res = statement(customer);
        String name = "statement-" + customer.getName() + "-" + UUID.randomUUID() + ".html";
        File cur = new File(file.getAbsolutePath() + "/" + name);

        try (FileWriter fw = new FileWriter(cur);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("<html><body>");
            for (String line : res.split("\n")) {
                pw.print("<p>");
                pw.print(line);
                pw.println("</p>");
            }
            pw.println("</body></html>");
            pw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return cur.getAbsolutePath();
    }

    private static double getThisAmount(Rental each) {
        double thisAmount = 0;

        switch (each.getMovie().getType()) {
            case REGULAR -> {
                thisAmount += 2;
                if (each.getDaysRented() > 2)
                    thisAmount += (each.getDaysRented() - 2) * 1.5;
            }
            case NEW_RELEASE -> thisAmount += each.getDaysRented() * 3;
            case CHILDREN -> {
                thisAmount += 1.5;
                if (each.getDaysRented() > 3)
                    thisAmount += (each.getDaysRented() - 3) * 1.5;
            }
            case DRAMA -> {
                thisAmount += 3;
                if (each.getDaysRented() > 2)
                    thisAmount += (each.getDaysRented() - 2) * 1.5;
            }
            case COMEDY -> {
                thisAmount += 1 + each.getDaysRented() * 2;
            }
            case THRILLER -> {
                thisAmount += 2;
                if (each.getDaysRented() > 3)
                    thisAmount += (each.getDaysRented() - 3) * 1.5;
            }
        }
        return thisAmount;
    }

}
