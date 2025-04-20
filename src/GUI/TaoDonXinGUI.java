package GUI;

import BUS.DangNhapBUS;
import BUS.DonXinNghiBUS;
import BUS.NhaCungCapBUS;
import Components.ShadowButton;
import DAO.NhaCungCapDAO;
import DTO.*;

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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;



public class TaoDonXinGUI extends JPanel{
	private JTable suppliersTable;
	private NhaCungCapBUS suppliersBUS = new NhaCungCapBUS();
	private DefaultTableModel suppliersModel = new DefaultTableModel();
	private JPanel taoDonXinContent;
	private JTextField txtMaNV, txtNgayTao, txtMaDon;
	private JLabel lblNgayTao, lblNgayBatDau, lblNgayKetThuc, lblLyDo, lblMaNV, lblLoai, lblCacDonXinDaTao, lblSoLanConLai, lblValueSoLanConLai, lblMaDon;
	private JDateChooser dateChooserNgayBD, dateChooserNgayKT;
	private JTextArea txtLyDo;
	private ShadowButton btnCancel, btnSave;
	private DangNhapBUS dnBUS = new DangNhapBUS();
	private String maNV = dnBUS.getMaNV();
	JComboBox<String> cbbLoai;
	
	private JTable donXinNghiTable;
	private DefaultTableModel donXinNghiModel = new DefaultTableModel();
	private ArrayList<DonXinNghiDTO> arrDonXinNghi = new ArrayList<DonXinNghiDTO>();
	private DonXinNghiBUS dxnBUS = new DonXinNghiBUS();
	
	//Constructor
    public TaoDonXinGUI(){
        initComponents();
        log("maNV="+maNV);
        txtMaNV.setText(maNV);
        loadDonXinNghiDaTao();
    }
    
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents() {
        setLayout(new GridBagLayout()); //set Layout
        GridBagConstraints gbc = new GridBagConstraints();
        taoDonXinContent = new JPanel();
        taoDonXinContent.setBackground(Color.white);
        taoDonXinContent.setLayout(new GridBagLayout());
        
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(taoDonXinContent, gbc); // Thêm vào ProductsGUI
        
        JPanel leftPanel, rightPanel;
        leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(Color.white);
        gbc.weightx = 0.85;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        taoDonXinContent.add(leftPanel, gbc);
        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.white);
        gbc.weightx = 0.15;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        taoDonXinContent.add(rightPanel, gbc);
        
        
        //leftPanel
        JPanel topLeftPanel, middleLeftPanel, bottomLeftPanel;
        topLeftPanel = new JPanel(null);
        topLeftPanel.setBackground(Color.white);
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        leftPanel.add(topLeftPanel, gbc);
    
        middleLeftPanel = new JPanel(new GridBagLayout());
        middleLeftPanel.setBackground(Color.white);
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        leftPanel.add(middleLeftPanel, gbc);
        
        bottomLeftPanel = new JPanel(null);
        bottomLeftPanel.setBackground(Color.white);
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        leftPanel.add(bottomLeftPanel, gbc);
        
      //set border cho các panel
        topLeftPanel.setBorder(
        	    BorderFactory.createCompoundBorder(
        	        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
        	        BorderFactory.createEmptyBorder(5, 5, 5, 5)
        	    )
        );
        
        middleLeftPanel.setBorder(
        	    BorderFactory.createCompoundBorder(
        	        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2),
        	        BorderFactory.createEmptyBorder(5, 5, 5, 5)
        	    )
        );
        
        lblMaNV = new JLabel("Mã NV: ");
        lblMaNV.setBounds(10, 10, 50, 20);
        topLeftPanel.add(lblMaNV);
        txtMaNV = new JTextField();
        txtMaNV.setEnabled(false);
        txtMaNV.setBounds(90, 10, 100, 20);
        topLeftPanel.add(txtMaNV);
        
        lblNgayTao = new JLabel("Ngày tạo: ");
        lblNgayTao.setBounds(10, 40, 100, 20);
        topLeftPanel.add(lblNgayTao);
        txtNgayTao = new JTextField();
        txtNgayTao.setBounds(90, 40, 100, 20);
        txtNgayTao.setEnabled(false);
        topLeftPanel.add(txtNgayTao);
        String ngayTao = String.valueOf(new java.sql.Date(System.currentTimeMillis()));
        txtNgayTao.setText(ngayTao);
        
        lblNgayBatDau = new JLabel("Ngày bắt đầu: ");
        lblNgayBatDau.setBounds(10, 70, 100, 20);
        topLeftPanel.add(lblNgayBatDau);
        dateChooserNgayBD = new JDateChooser();
        dateChooserNgayBD.setBounds(90, 70, 100, 20);
        topLeftPanel.add(dateChooserNgayBD);
        
        lblNgayKetThuc = new JLabel("Ngày kết thúc: ");
        lblNgayKetThuc.setBounds(10, 100, 100, 20);
        topLeftPanel.add(lblNgayKetThuc);
        dateChooserNgayKT = new JDateChooser();
        dateChooserNgayKT.setBounds(90, 100, 100, 20);
        topLeftPanel.add(dateChooserNgayKT);
        
        lblLoai = new JLabel("Loại: ");
        lblLoai.setBounds(500, 40, 100, 20);
        topLeftPanel.add(lblLoai);
        String[] loai = {"Nghỉ phép có lương", "Nghỉ phép không lương", "Nghỉ không phép", "Nghỉ việc"};
        cbbLoai = new JComboBox<String>(loai);
        cbbLoai.setBounds(545, 40, 160, 20);
        topLeftPanel.add(cbbLoai);
        
        lblMaDon = new JLabel("Mã đơn: ");
        lblMaDon.setBounds(500, 10, 100, 20);
        topLeftPanel.add(lblMaDon);
        txtMaDon = new JTextField();
        txtMaDon.setBounds(545, 10, 100, 20);
        topLeftPanel.add(txtMaDon);
        
        lblLyDo = new JLabel("Lý do:");
        lblLyDo.setBounds(10, 180, 100, 20);
        topLeftPanel.add(lblLyDo);
        
        txtLyDo = new JTextArea();
        txtLyDo.setLineWrap(true);
        txtLyDo.setWrapStyleWord(true);
        
        JScrollPane sp = new JScrollPane(txtLyDo);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        middleLeftPanel.add(sp, gbc);
        
        
        btnCancel = new ShadowButton("Hủy");
        btnCancel.setBounds(555, 110, 70, 20);
        bottomLeftPanel.add(btnCancel);
        
        btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCancel.setBackground(Color.decode("#3A96CF"));
				btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnCancel.setBackground(Color.white);
			}
		});

        btnSave = new ShadowButton("Lưu");
        btnSave.setBounds(630, 110, 70, 20);
        bottomLeftPanel.add(btnSave);
       
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
				String maDon = txtMaDon.getText().trim();
				String maNV = txtMaNV.getText().trim();
				Date ngayTao = Date.valueOf(txtNgayTao.getText().trim());
				java.util.Date ngayBD = dateChooserNgayBD.getDate();
				java.sql.Date ngayBDSQL=null;
				if (ngayBD != null) {
				    ngayBDSQL = new java.sql.Date(ngayBD.getTime());
				    log("Ngày bắt đầu: " + ngayBDSQL);
				} else {
				    System.out.println("Chưa chọn ngày.");
				}
				java.util.Date ngayKT = dateChooserNgayBD.getDate();
				java.sql.Date ngayKTSQL=null;
				if (ngayKT != null) {
				    ngayKTSQL = new java.sql.Date(ngayKT.getTime());
				    log("Ngày kết thúc: " + ngayKTSQL);
				} else {
				    System.out.println("Chưa chọn ngày.");
				}
				
				
				String lyDo = txtLyDo.getText().trim();
				Date ngayDuyet = null;
				String trangThai = "Chờ duyệt";
				String maNguoiDuyet = null;
				
				DonXinNghiDTO dxn = new DonXinNghiDTO();
				dxn.setMaDon(maDon);
				dxn.setNgayTao(ngayTao);
				dxn.setNgayBD(ngayBDSQL);
				dxn.setNgayKT(ngayKTSQL);
				dxn.setLyDo(lyDo);
				dxn.setNgayDuyet(ngayDuyet);
				dxn.setTrangThai(trangThai);
				dxn.setMaNV(maNV);
				dxn.setMaNguoiDuyet(maNguoiDuyet);
				
				String message = dxnBUS.insert(dxn);
				if(message.equalsIgnoreCase("Tạo đơn xin nghỉ thành công!")) {
					JOptionPane.showMessageDialog(null, message);
				}else if(message.equalsIgnoreCase("Tạo đơn xin nghỉ thất bại!")){
					JOptionPane.showMessageDialog(null, message);

				}
			}
		});
        
        
        
        //rightPanel
        JPanel topRightPanel, bottomRightPanel;
        topRightPanel = new JPanel(null);
        topRightPanel.setBackground(Color.white);
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        rightPanel.add(topRightPanel, gbc);
        
        bottomRightPanel = new JPanel(new GridBagLayout());
        bottomRightPanel.setBackground(Color.white);
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        rightPanel.add(bottomRightPanel, gbc);
    
        donXinNghiTable = new JTable();
        JScrollPane sp2 = new JScrollPane(donXinNghiTable);
        gbc.weightx = 1.0;
        gbc.weighty = 0.7;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        bottomRightPanel.add(sp2, gbc);
        
        lblCacDonXinDaTao = new JLabel("Các đơn xin nghỉ đã tạo");
        lblCacDonXinDaTao.setFont(new Font("Arial", Font.BOLD, 25));
        lblCacDonXinDaTao.setBounds(10, 10, 300, 40);
        topRightPanel.add(lblCacDonXinDaTao);
    
        lblSoLanConLai = new JLabel("Số lần xin nghỉ phép còn lại trong năm: ");
        lblSoLanConLai.setFont(new Font("Arial", Font.BOLD, 15));
        lblSoLanConLai.setBounds(10,50,300,40);
        topRightPanel.add(lblSoLanConLai);
        
        lblValueSoLanConLai = new JLabel("...");
        lblValueSoLanConLai.setBounds(310, 60, 100, 20);
        topRightPanel.add(lblValueSoLanConLai);
        lblValueSoLanConLai.setText(String.valueOf(demSoLanXinNghiConLai()));
        
    }

    
    public void loadDonXinNghiDaTao() {
    	donXinNghiTable.setDefaultEditor(Object.class, null);
    	
    	donXinNghiTable.setModel(donXinNghiModel);
    	donXinNghiModel.addColumn("Mã đơn");
    	donXinNghiModel.addColumn("Ngày tạo");
    	donXinNghiModel.addColumn("Ngày bắt đầu");
    	donXinNghiModel.addColumn("Ngày kết thúc");
    	donXinNghiModel.addColumn("Lý do");
    	donXinNghiModel.addColumn("Mã người duyệt");
    	
    	arrDonXinNghi = dxnBUS.selectDonXinNghiDaTao(dnBUS.getMaNV());
    	for(int i=0; i<arrDonXinNghi.size(); i++) {
    		DonXinNghiDTO dxn = arrDonXinNghi.get(i);
    		String maDon = dxn.getMaDon();
    		Date ngayTao = dxn.getNgayTao();
    		Date ngayBD = dxn.getNgayBD();
    		Date ngayKT = dxn.getNgayKT();
    		String lyDo = dxn.getLyDo();
    		String maNguoiDuyet = dxn.getMaNguoiDuyet();
    		
    		Object[] row = {maDon, ngayTao, ngayBD, ngayKT, lyDo, maNguoiDuyet};
    		donXinNghiModel.addRow(row);
    	}
    	
    	
		// Điều chỉnh kích thước các cột
		TableColumnModel tcm = donXinNghiTable.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(100);
		tcm.getColumn(1).setPreferredWidth(100);
		tcm.getColumn(2).setPreferredWidth(100);
		tcm.getColumn(3).setPreferredWidth(100);
		tcm.getColumn(4).setPreferredWidth(200);
		tcm.getColumn(5).setPreferredWidth(100);
		donXinNghiTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);    //Ngăn các cột tự resize

    }
    
	
    private int demSoLanXinNghiConLai() {
    	int soLanConLai=0;
    	
    	String maNV = dnBUS.getMaNV();
    	log("maNV="+maNV);
    	soLanConLai = 12 - dxnBUS.demSoLanXinNghiConLai(maNV);
    	
    	if(soLanConLai>12) {
    		JOptionPane.showMessageDialog(null, "Đã hết ngày được nghỉ phép!");
    	}
    	
    	return soLanConLai;
    }
       
       
       
	public static void log(String message) {
  	    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
  	    StackTraceElement element = stackTrace[2]; // [0]=getStackTrace, [1]=log(), [2]=caller
  	    System.out.println(element.getClassName() + " | method: " 
  	        + element.getMethodName() + " | line: " + element.getLineNumber() + " | " + message);
  	}
}
