package GUI;

import DTO.KhoDTO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import BUS.KhoBUS;
import Components.ShadowButton;
import DTO.NhanVienDTO;

public class KhoGUI extends JPanel {

	KhoBUS khoBUS = new KhoBUS();
	JTable nvTable, pbspTable;
	DefaultTableModel nvModel, pbspModel;
	JComboBox cbbKho;
	JPanel khoContent;
	JTextField txtMaKho, txtTenKho, txtDiaChi, txtSdt;
	JRadioButton rbOn, rbOff;
	JButton btnSave, btnRefresh;
	ButtonGroup group;
	// cho việc lấy tên kho đưa vào combobox
	ArrayList<KhoDTO> arrKho = khoBUS.selectAll();
	String[] kho = new String[arrKho.size()];

	// Constructor
	public KhoGUI() {
		initComponents();
		loadDanhSachNhanVien();
		loadPBSP();
	}

	////////////////////////////////////////// METHODS//////////////////////////////////////
	private void initComponents() {
		setLayout(new GridBagLayout()); // set Layout
		GridBagConstraints gbc = new GridBagConstraints();
		khoContent = new JPanel();
		khoContent.setBackground(Color.white);
		khoContent.setLayout(new GridBagLayout());

		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(khoContent, gbc); // Thêm vào ProductsGUI

		JPanel leftPanel, rightPanel;
		// set thông số cho 2 panel
		leftPanel = new JPanel();
		leftPanel.setLayout(new GridBagLayout());
		leftPanel.setBackground(Color.white);
		gbc.weightx = 0.5;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		khoContent.add(leftPanel, gbc);

		rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
		rightPanel.setBackground(Color.white);
		gbc.weightx = 0.5;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 0;
		khoContent.add(rightPanel, gbc);

		// leftPanel
		// topLeftPanel
		JPanel topLeftPanel, bottomLeftPanel;
		topLeftPanel = new JPanel(null);
		topLeftPanel.setPreferredSize(new Dimension(650, 250)); // hoặc kích thước phù hợp
		topLeftPanel.setBackground(Color.white);
		topLeftPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
		gbc.weightx = 1.0;
		gbc.weighty = 0.3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 3, 3);
		leftPanel.add(topLeftPanel, gbc);

		bottomLeftPanel = new JPanel(new GridBagLayout());
		bottomLeftPanel.setBackground(Color.white);
		bottomLeftPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2));
		gbc.weightx = 0.5;
		gbc.weighty = 0.7;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		leftPanel.add(bottomLeftPanel, gbc);

		for (int i = 0; i < arrKho.size(); i++) {
			kho[i] = arrKho.get(i).getTenKho();
		}
		cbbKho = new JComboBox<String>(kho);
		cbbKho.setBounds(10, 10, 130, 20);
		topLeftPanel.add(cbbKho);
		cbbKho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tenKho = (String) cbbKho.getSelectedItem();
				loadDanhSachNhanVienTheoKho(tenKho); // Gọi phương thức để load nhân viên theo kho
			}
		});

		JLabel lblMaKho, lblTenKho, lblDiaChi, lblSdt, lblTrangThai, lblQuanLyKho;
		lblMaKho = new JLabel("Mã kho: ");
		lblMaKho.setBounds(10, 60, 100, 20);
		topLeftPanel.add(lblMaKho);
		txtMaKho = new JTextField();
		txtMaKho.setBounds(80, 60, 100, 20);
		topLeftPanel.add(txtMaKho);
		txtMaKho.setEditable(false);

		lblTenKho = new JLabel("Tên kho: ");
		lblTenKho.setBounds(10, 85, 100, 20);
		topLeftPanel.add(lblTenKho);
		txtTenKho = new JTextField();
		txtTenKho.setBounds(80, 85, 100, 20);
		topLeftPanel.add(txtTenKho);

		lblDiaChi = new JLabel("Địa chỉ: ");
		lblDiaChi.setBounds(10, 110, 100, 20);
		topLeftPanel.add(lblDiaChi);
		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(80, 110, 200, 20);
		topLeftPanel.add(txtDiaChi);

		lblSdt = new JLabel("SĐT: ");
		lblSdt.setBounds(10, 135, 100, 20);
		topLeftPanel.add(lblSdt);
		txtSdt = new JTextField();
		txtSdt.setBounds(80, 135, 100, 20);
		topLeftPanel.add(txtSdt);

		lblTrangThai = new JLabel("Trạng thái: ");
		lblTrangThai.setBounds(10, 160, 100, 20);
		topLeftPanel.add(lblTrangThai);

		// Khởi tạo các radio button
		rbOn = new JRadioButton("on");
		rbOff = new JRadioButton("off");

		// Nhóm radio buttons lại
		group = new ButtonGroup();
		group.add(rbOn);
		group.add(rbOff);

		// Add vào topLeftPanel
		rbOn.setBounds(80, 160, 100, 20);
		topLeftPanel.add(rbOn);
		rbOff.setBounds(130, 160, 100, 20);
		topLeftPanel.add(rbOff);

		btnSave = new ShadowButton("Lưu");
		btnSave.setBounds(555, 235, 70, 20);
		topLeftPanel.add(btnSave);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnSave.setBackground(Color.decode("#3A96CF"));
				btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSave.setBackground(Color.white);
			}
		});

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String maKho = txtMaKho.getText().trim();
				String tenKho = txtTenKho.getText().trim();
				String diaChi = txtDiaChi.getText().trim();
				String sdt = txtSdt.getText().trim();
				String trangThai = rbOn.isSelected() ? "1" : "0";

				// Kiểm tra dữ liệu đầu vào nếu cần thiết
				if (maKho.isEmpty() || tenKho.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin bắt buộc!");
					return;
				}

				// Tạo đối tượng KhoDTO
				KhoDTO kho = new KhoDTO();
				kho.setMaKho(maKho);
				kho.setTenKho(tenKho);
				kho.setDiaChi(diaChi);
				kho.setSdt(sdt);
				kho.setTrangThai(trangThai);

				String result = khoBUS.updateKho(kho);

				JOptionPane.showMessageDialog(null, result);
			}
		});

		btnRefresh = new ShadowButton("Làm mới");
		btnRefresh.setBounds(630, 235, 90, 20);
		topLeftPanel.add(btnRefresh);
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRefresh.setBackground(Color.decode("#3A96CF"));
				btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnRefresh.setBackground(Color.white);
			}
		});
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Xóa nội dung các trường nhập liệu

				txtTenKho.setText("");
				txtDiaChi.setText("");
				txtSdt.setText("");
				rbOn.setSelected(false); // Nếu sử dụng radio button
			}
		});

		// bottomLeftPanel
		nvTable = new JTable();
		JScrollPane sp = new JScrollPane(nvTable);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		bottomLeftPanel.add(sp, gbc);

		// rightPanel
		pbspTable = new JTable();
		JScrollPane sp2 = new JScrollPane(pbspTable);
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		rightPanel.add(sp2, gbc);
	}

	private void loadDanhSachNhanVienTheoKho(String tenKho) {
		// Lấy thông tin kho theo tên kho
		KhoDTO kho = khoBUS.getKhoByName(tenKho);

		// Hiển thị thông tin kho vào các ô textField
		if (kho != null) {
			txtMaKho.setText(kho.getMaKho());
			txtTenKho.setText(kho.getTenKho());
			txtDiaChi.setText(kho.getDiaChi());
			txtSdt.setText(kho.getSdt());

			// Cập nhật trạng thái radio button
			if (kho.getTrangThai().equals("on")) { // Kiểm tra nếu trạng thái là "1"
				rbOn.setSelected(true);
			} else {
				rbOff.setSelected(true);
			}
			String maNVQuanLy = khoBUS.getMaNVQuanLyKho(kho.getMaKho());
		}

		// Tiếp tục load danh sách nhân viên theo kho
		ArrayList<NhanVienDTO> danhSachNhanVien = khoBUS.getDanhSachNhanVienTheoKho(tenKho);

		// Clear các dữ liệu cũ trong bảng
		nvModel.setRowCount(0);

		// Duyệt qua danh sách nhân viên và thêm vào bảng
		for (NhanVienDTO nv : danhSachNhanVien) {
			nvModel.addRow(new Object[] { nv.getMaNV(), nv.getHoTen(), nv.getNgaySinh(), nv.getGioiTinh(),
					nv.getDiaChi(), nv.getSoDienThoai(), nv.getEmail() });
			ArrayList<Object[]> danhSachPBSP = khoBUS.getDanhSachPBSPTheoKho(kho.getMaKho());
			pbspModel.setRowCount(0);
			for (Object[] row : danhSachPBSP) {
				pbspModel.addRow(row);
			}
		}
	}

	private void loadDanhSachNhanVien() {
		nvTable.setDefaultEditor(Object.class, null);

		nvModel = new DefaultTableModel();
		nvTable.setModel(nvModel);
		nvModel.addColumn("Mã NV");
		nvModel.addColumn("Họ tên");
		nvModel.addColumn("Ngày sinh");
		nvModel.addColumn("Giới tính");
		nvModel.addColumn("Địa chỉ");
		nvModel.addColumn("Số điện thoại");
		nvModel.addColumn("Email");

	}

	private void loadPBSP() {
		pbspTable.setDefaultEditor(Object.class, null);

		pbspModel = new DefaultTableModel();
		pbspTable.setModel(pbspModel);
		pbspModel.addColumn("Mã PBSP");
		pbspModel.addColumn("Màu sắc");
		pbspModel.addColumn("RAM");
		pbspModel.addColumn("ROM");
		pbspModel.addColumn("Giá bán");
		pbspModel.addColumn("Số lượng");
		pbspModel.addColumn("Trạng thái");
		pbspModel.addColumn("Mã SP");

	}


}
