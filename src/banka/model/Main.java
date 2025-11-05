package banka.model;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    private static List<Klijent> klijenti = new ArrayList<>();
    private static DefaultTableModel tableModel;
    private static JTable tabela;

    private static JLabel lblProsjek;
    private static JLabel lblTop;
    private static JLabel lblUkupno;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::napraviGUI);
    }

    private static void napraviGUI() {
        JFrame frame = new JFrame("💼 Evidencija klijenata banke");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 520);
        frame.setLocationRelativeTo(null);

        // ----- GORNJI PANEL (unos) -----
        JPanel panelUnos = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField txtIme = new JTextField();
        JTextField txtPrezime = new JTextField();
        JTextField txtStanje = new JTextField();
        JButton btnDodaj = new JButton("Dodaj klijenta");
        JButton btnAnaliza = new JButton("Analiza");

        panelUnos.add(new JLabel("Ime:"));
        panelUnos.add(txtIme);
        panelUnos.add(new JLabel("Prezime:"));
        panelUnos.add(txtPrezime);
        panelUnos.add(new JLabel("Stanje (KM):"));
        panelUnos.add(txtStanje);
        panelUnos.add(btnDodaj);
        panelUnos.add(btnAnaliza);

        // ----- TABELA -----
        String[] kolone = {"Ime", "Prezime", "Stanje (KM)"};
        tableModel = new DefaultTableModel(kolone, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(tabela);

        // ----- DONJI PANEL (info) -----
        lblProsjek = new JLabel("Prosječno stanje: 0 KM");
        lblTop = new JLabel("Najbogatiji klijent: -");
        lblUkupno = new JLabel("Ukupno klijenata: 0");

        JPanel panelInfo = new JPanel(new GridLayout(3, 1));
        panelInfo.add(lblProsjek);
        panelInfo.add(lblTop);
        panelInfo.add(lblUkupno);

        // ----- DESNI PANEL (akcije) -----
        JPanel panelAkcije = new JPanel(new GridLayout(6, 1, 5, 5));
        JButton btnObrisi = new JButton("Obriši označenog");
        JButton btnSortAZ = new JButton("Sort A–Ž");
        JButton btnSortSaldoAsc = new JButton("Sort saldo ↑");
        JButton btnSortSaldoDesc = new JButton("Sort saldo ↓");
        JButton btnMin = new JButton("Min saldo");
        JButton btnMax = new JButton("Max saldo");

        panelAkcije.add(btnObrisi);
        panelAkcije.add(btnSortAZ);
        panelAkcije.add(btnSortSaldoAsc);
        panelAkcije.add(btnSortSaldoDesc);
        panelAkcije.add(btnMin);
        panelAkcije.add(btnMax);

        // ----- LAYOUT -----
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(panelUnos, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(panelInfo, BorderLayout.SOUTH);
        frame.add(panelAkcije, BorderLayout.EAST);

        frame.setVisible(true);

        // ====================================================
        // D O G A Đ A J I
        // ====================================================

        // Dodavanje
        btnDodaj.addActionListener(e -> {
            try {
                String ime = txtIme.getText().trim();
                String prezime = txtPrezime.getText().trim();
                String stanjeStr = txtStanje.getText().trim();

                if (ime.isEmpty() || prezime.isEmpty() || stanjeStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Sva polja su obavezna!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double stanje = Double.parseDouble(stanjeStr);

                if (stanje < 0) {
                    JOptionPane.showMessageDialog(frame, "Stanje ne može biti negativno!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Klijent k = new Klijent(ime, prezime, stanje);
                klijenti.add(k);

                osvjeziTabelu();
                azurirajStatuse();

                // očisti polja
                txtIme.setText("");
                txtPrezime.setText("");
                txtStanje.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Unesi ispravan broj za stanje!", "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Analiza (ponovno izračunavanje prosjeka i topa)
        btnAnaliza.addActionListener(e -> azurirajStatuse());

        // Brisanje selektovanog
        btnObrisi.addActionListener(e -> {
            int red = tabela.getSelectedRow();
            if (red == -1) {
                JOptionPane.showMessageDialog(frame, "Označi klijenta u tabeli.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // obriši iz liste i osvježi tabelu
            klijenti.remove(red);
            osvjeziTabelu();
            azurirajStatuse();
        });

        // Sort A–Ž (ime, pa prezime)
        btnSortAZ.addActionListener(e -> {
            klijenti.sort(Comparator
                    .comparing(Klijent::getIme, String.CASE_INSENSITIVE_ORDER)
                    .thenComparing(Klijent::getPrezime, String.CASE_INSENSITIVE_ORDER));
            osvjeziTabelu();
        });

        // Sort saldo rastuće
        btnSortSaldoAsc.addActionListener(e -> {
            klijenti.sort(Comparator.comparingDouble(Klijent::getStanje));
            osvjeziTabelu();
        });

        // Sort saldo opadajuće
        btnSortSaldoDesc.addActionListener(e -> {
            klijenti.sort(Comparator.comparingDouble(Klijent::getStanje).reversed());
            osvjeziTabelu();
        });

        // Min saldo
        btnMin.addActionListener(e -> {
            if (klijenti.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nema klijenata.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Klijent min = klijenti.stream()
                    .min(Comparator.comparingDouble(Klijent::getStanje))
                    .orElse(null);
            JOptionPane.showMessageDialog(frame,
                    "Klijent s najmanjim stanjem:\n" +
                            min.getPunoIme() + " – " + String.format("%.2f KM", min.getStanje()),
                    "Min saldo",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Max saldo
        btnMax.addActionListener(e -> {
            if (klijenti.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nema klijenata.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Klijent max = klijenti.stream()
                    .max(Comparator.comparingDouble(Klijent::getStanje))
                    .orElse(null);
            JOptionPane.showMessageDialog(frame,
                    "Klijent s najvećim stanjem:\n" +
                            max.getPunoIme() + " – " + String.format("%.2f KM", max.getStanje()),
                    "Max saldo",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // inicijalno
        azurirajStatuse();
    }

    // -------- pomoćne metode --------

    private static void osvjeziTabelu() {
        // obriši sve redove
        tableModel.setRowCount(0);
        // dodaj iz liste
        for (Klijent k : klijenti) {
            tableModel.addRow(new Object[]{
                    k.getIme(),
                    k.getPrezime(),
                    String.format("%.2f", k.getStanje())
            });
        }
    }

    private static void azurirajStatuse() {
        // ukupno
        lblUkupno.setText("Ukupno klijenata: " + klijenti.size());

        // prosjek + top
        if (klijenti.isEmpty()) {
            lblProsjek.setText("Prosječno stanje: 0 KM");
            lblTop.setText("Najbogatiji klijent: -");
            return;
        }

        double prosjek = klijenti.stream()
                .mapToDouble(Klijent::getStanje)
                .average()
                .orElse(0.0);

        Klijent top = klijenti.stream()
                .max(Comparator.comparingDouble(Klijent::getStanje))
                .orElse(null);

        lblProsjek.setText(String.format("Prosječno stanje: %.2f KM", prosjek));
        if (top != null) {
            lblTop.setText(String.format("Najbogatiji klijent: %s (%.2f KM)",
                    top.getPunoIme(), top.getStanje()));
        }
    }
}
