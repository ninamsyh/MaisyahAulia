package com.uts.ManajemenKaryawan.GUI;

import com.uts.ManajemenKaryawan.Model.Karyawan;
import com.uts.ManajemenKaryawan.Service.ManajemenKaryawan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private final JTextField tfId = new JTextField(10);
    private final JTextField tfNama = new JTextField(10);
    private final JTextField tfPosisi = new JTextField(10);
    private final JTextField tfGaji = new JTextField(10);
    private final JTextField tfTanggal = new JTextField(10);
    private final JComboBox<String> cbDivisiInput = new JComboBox<>();
    private final JTextField tfSearch = new JTextField(15);
    private final JTextField tfMinGaji = new JTextField(6);
    private final JTextField tfMaxGaji = new JTextField(6);
    private final JComboBox<String> cbDivisiFilter = new JComboBox<>();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final ManajemenKaryawan manajer;

    public MainFrame(ManajemenKaryawan manajer) {
        super("Manajemen Data Karyawan");
        this.manajer = manajer;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel Input
        JPanel panelInput = new JPanel(new GridBagLayout());
        GridBagConstraints inputGbc = new GridBagConstraints();
        inputGbc.insets = new Insets(2, 2, 2, 2);
        inputGbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"ID", "Nama", "Posisi", "Gaji", "Tanggal Bergabung", "Divisi"};
        JComponent[] fields = {tfId, tfNama, tfPosisi, tfGaji, tfTanggal, cbDivisiInput};

        for (int i = 0; i < labels.length; i++) {
            inputGbc.gridx = 0;
            inputGbc.gridy = i;
            panelInput.add(new JLabel(labels[i]), inputGbc);

            inputGbc.gridx = 1;
            panelInput.add(fields[i], inputGbc);
        }

        // Tambahkan data divisi ke ComboBox input
        String[] divisiList = {"Marketing", "Finance", "Sales", "Operations", "HRD", "IT", "Purchasing", "General", "Development"};
        for (String divisi : divisiList) {
            cbDivisiInput.addItem(divisi);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(panelInput, gbc);

        // Tombol Tambah, Ubah, Hapus
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        panelButton.add(btnTambah);
        panelButton.add(btnUbah);
        panelButton.add(btnHapus);

        gbc.gridy = 1;
        add(panelButton, gbc);

        // Panel Pencarian dan Filter
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(new JLabel("Cari (ID/Nama):"));
        panelSearch.add(tfSearch);

        panelSearch.add(new JLabel("Divisi:"));
        panelSearch.add(cbDivisiFilter);

        panelSearch.add(new JLabel("Gaji Min:"));
        panelSearch.add(tfMinGaji);
        panelSearch.add(new JLabel("Max:"));
        panelSearch.add(tfMaxGaji);

        JButton btnFilter = new JButton("Filter");
        JButton btnReset = new JButton("Reset");
        panelSearch.add(btnFilter);
        panelSearch.add(btnReset);

        gbc.gridy = 2;
        add(panelSearch, gbc);

        // Tabel
        String[] kolom = {"ID", "Nama", "Posisi", "Gaji", "Tanggal Bergabung", "Divisi"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);

        // Load data awal
        loadDataToTable(manajer.getDaftarKaryawan());
        updateDivisiFilter();

        // Event listeners
        btnTambah.addActionListener(e -> tambahKaryawan());
        btnUbah.addActionListener(e -> ubahKaryawan());
        btnHapus.addActionListener(e -> hapusKaryawan());
        tfSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                cariKaryawan();
            }
        });
        btnFilter.addActionListener(e -> filterKaryawan());
        btnReset.addActionListener(e -> {
            tfSearch.setText("");
            tfMinGaji.setText("");
            tfMaxGaji.setText("");
            cbDivisiFilter.setSelectedIndex(0);
            loadDataToTable(manajer.getDaftarKaryawan());
        });
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                tfId.setText(table.getValueAt(row, 0).toString());
                tfNama.setText(table.getValueAt(row, 1).toString());
                tfPosisi.setText(table.getValueAt(row, 2).toString());
                tfGaji.setText(table.getValueAt(row, 3).toString());
                tfTanggal.setText(table.getValueAt(row, 4).toString());
                cbDivisiInput.setSelectedItem(table.getValueAt(row, 5).toString());
            }
        });

        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void tambahKaryawan() {
        try {
            String id = tfId.getText();
            String nama = tfNama.getText();
            String posisi = tfPosisi.getText();
            double gaji = Double.parseDouble(tfGaji.getText());
            String tanggal = tfTanggal.getText();
            String divisi = cbDivisiInput.getSelectedItem().toString();

            Karyawan k = new Karyawan(id, nama, posisi, gaji, tanggal, divisi);
            manajer.getDaftarKaryawan().add(k);
            manajer.simpanKeFile("data_karyawan.txt");
            loadDataToTable(manajer.getDaftarKaryawan());
            updateDivisiFilter();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Gaji harus berupa angka.");
        }
    }

    private void ubahKaryawan() {
        int index = table.getSelectedRow();
        if (index != -1) {
            try {
                Karyawan k = manajer.getDaftarKaryawan().get(index);
                k.setNama(tfNama.getText());
                k.setPosisi(tfPosisi.getText());
                k.setGaji(Double.parseDouble(tfGaji.getText()));
                k.setTanggalBergabung(tfTanggal.getText());
                k.setDivisi(cbDivisiInput.getSelectedItem().toString());
                manajer.simpanKeFile("data_karyawan.txt");
                loadDataToTable(manajer.getDaftarKaryawan());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Gaji harus berupa angka.");
            }
        }
    }

    private void hapusKaryawan() {
        int index = table.getSelectedRow();
        if (index != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                manajer.getDaftarKaryawan().remove(index);
                manajer.simpanKeFile("data_karyawan.txt");
                loadDataToTable(manajer.getDaftarKaryawan());
                updateDivisiFilter();
            }
        }
    }

    private void cariKaryawan() {
        String keyword = tfSearch.getText().toLowerCase();
        List<Karyawan> hasil = manajer.getDaftarKaryawan().stream()
                .filter(k -> k.getId().toLowerCase().contains(keyword) || k.getNama().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
        loadDataToTable(hasil);
    }

    private void filterKaryawan() {
        String divisi = cbDivisiFilter.getSelectedItem().toString();
        String minStr = tfMinGaji.getText();
        String maxStr = tfMaxGaji.getText();
        double min = minStr.isEmpty() ? Double.MIN_VALUE : Double.parseDouble(minStr);
        double max = maxStr.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxStr);

        List<Karyawan> hasil = manajer.getDaftarKaryawan().stream()
                .filter(k -> (divisi.equals("Semua") || k.getDivisi().equals(divisi)) && k.getGaji() >= min && k.getGaji() <= max)
                .collect(Collectors.toList());
        loadDataToTable(hasil);
    }

    private void updateDivisiFilter() {
        cbDivisiFilter.removeAllItems();
        cbDivisiFilter.addItem("Semua");
        manajer.getDaftarKaryawan().stream()
                .map(Karyawan::getDivisi)
                .distinct()
                .forEach(cbDivisiFilter::addItem);
    }

    private void loadDataToTable(List<Karyawan> list) {
        tableModel.setRowCount(0);
        for (Karyawan k : list) {
            tableModel.addRow(new Object[]{k.getId(), k.getNama(), k.getPosisi(), k.getGaji(), k.getTanggalBergabung(), k.getDivisi()});
        }
    }
}