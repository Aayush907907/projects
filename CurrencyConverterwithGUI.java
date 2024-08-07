import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverterGUI {
    
    // Method to simulate fetching exchange rates from an API
    private static Map<String, Double> getExchangeRates() {
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD_TO_INR", 74.85);
        rates.put("EUR_TO_INR", 88.50);
        rates.put("INR_TO_USD", 0.013);
        rates.put("INR_TO_EUR", 0.011);
        rates.put("USD_TO_EUR", 0.85);
        rates.put("EUR_TO_USD", 1.18);
        // Add more exchange rates as needed
        return rates;
    }

    // Method to convert currency
    private static double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        Map<String, Double> rates = getExchangeRates();
        String key = fromCurrency + "_TO_" + toCurrency;
        if (rates.containsKey(key)) {
            return amount * rates.get(key);
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Currency Converter");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create and set up components
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(30, 30, 100, 25);
        frame.add(amountLabel);

        JTextField amountField = new JTextField();
        amountField.setBounds(150, 30, 200, 25);
        frame.add(amountField);

        JLabel fromCurrencyLabel = new JLabel("From Currency:");
        fromCurrencyLabel.setBounds(30, 70, 100, 25);
        frame.add(fromCurrencyLabel);

        String[] currencies = {"USD", "EUR", "INR"};
        JComboBox<String> fromCurrencyBox = new JComboBox<>(currencies);
        fromCurrencyBox.setBounds(150, 70, 200, 25);
        frame.add(fromCurrencyBox);

        JLabel toCurrencyLabel = new JLabel("To Currency:");
        toCurrencyLabel.setBounds(30, 110, 100, 25);
        frame.add(toCurrencyLabel);

        JComboBox<String> toCurrencyBox = new JComboBox<>(currencies);
        toCurrencyBox.setBounds(150, 110, 200, 25);
        frame.add(toCurrencyBox);

        JButton convertButton = new JButton("Convert");
        convertButton.setBounds(150, 150, 100, 30);
        frame.add(convertButton);

        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(30, 200, 320, 25);
        frame.add(resultLabel);

        // Add action listener to the button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    String fromCurrency = (String) fromCurrencyBox.getSelectedItem();
                    String toCurrency = (String) toCurrencyBox.getSelectedItem();
                    double convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);

                    if (convertedAmount != -1) {
                        resultLabel.setText(String.format("Converted Amount: %.2f %s", convertedAmount, toCurrency));
                    } else {
                        resultLabel.setText("Conversion rate not available.");
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid amount entered.");
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
