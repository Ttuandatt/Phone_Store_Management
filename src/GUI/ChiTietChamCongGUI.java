package GUI;

import BUS.BangChamCongBUS;
import BUS.ChiTietChamCongBUS;
import BUS.ChucVuBUS;
import BUS.KhoBUS;
import BUS.NhanVienBUS;
import DTO.*;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;


public class ChiTietChamCongGUI extends JPanel{
	
    private JButton btnThem, btnBack;
    
    private Object[][] data;
    private String[] header= {"MaNV", "Nhân viên","Chức vụ", "Chi nhánh"};
    private JTable table;
    private JLabel lb_manv, lb_thang, lb_soNgayLam, lb_soNgayNghi, lb_ot;
    private JTextField tf_soGioOT, tf_ghiChu1, tf_ghiChu2, tGetNgayThangNam;
    private static JPanel jp_tangCa, jp_nghi;
    private String manv, makho;

    private Color color= Color.decode("#FF6A6A");
    private Color colorsunday = new Color(0,0,0,100);
    private JComboBox<String> jc_thang, jc_nam;
    private ArrayList<JLabel> arr_1, arr;
    private ArrayList<JLabel> lb_dayOfWeek_list;
    private DefaultTableModel dftable;
    
    ArrayList<NhanVienDTO> arrNhanVien;
    private static ArrayList<JRadioButton> arr_radio, arr_radio1, arr_radio2;
    private static ArrayList<ChiTietChamCongDTO> arr_temp = new ArrayList<ChiTietChamCongDTO>();
    NhanVienBUS nvBUS = new NhanVienBUS();
    ChucVuBUS cvBUS = new ChucVuBUS();
    KhoBUS khoBUS = new KhoBUS();
    BangChamCongBUS bccBUS = new BangChamCongBUS();
    ChiTietChamCongBUS ctccBUS = new ChiTietChamCongBUS();
    
	//Constructor
    
    public ChiTietChamCongGUI(CardLayout cardLayout, JPanel contentPanel) {

        this.initComponents(cardLayout, contentPanel);
        loadDSNhanVien();
    }
    
    //////////////////////////////////////////METHODS//////////////////////////////////////
    private void initComponents(CardLayout cardLayout, JPanel contentPanel) {
        this.setLayout(null);
//      this.setBackground(Color.white);
        
        JPanel panelTop = new JPanel();
	panelTop.setBounds(5,0,1080,40);
	panelTop.setBackground(Color.white);
	this.add(panelTop);
	panelTop.setLayout(null);

        ArrayList<ChiTietChamCongDTO> arr_AllCTCC = ctccBUS.selectAll();
        /*for (ChiTietChamCongDTO ct: arr_AllCTCC) {
            System.out.println("/////" + ct.getMaCTCC() + " ; " + ct.getNgayTao() + " ; " + ct.getLoaiChamCong());
        }*/
        
	btnBack = new JButton("Quay lại");
	btnBack.setBounds(5,5,120,30);
	btnBack.setBorderPainted(false);
	btnBack.setFocusPainted(false);
	btnBack.setFont(new Font("arial",0,14));
	btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
	panelTop.add(btnBack);
        btnBack.addActionListener(e -> {
            if (!arr_temp.isEmpty()) {
                arr_temp.clear();
            }
            loadDSNhanVien();
            cardLayout.show(contentPanel, "DANH SACH CHAM CONG");
        });
        
        
	JPanel panelFrame = new JPanel();
	panelFrame.setBackground(Color.white);
	panelFrame.setBounds(5,50,1080,520);
	this.add(panelFrame);
	panelFrame.setLayout(null);
		
    	arr_1 = new ArrayList<>();
    	lb_dayOfWeek_list = new ArrayList<>();
        
        ///// table Thông tin nhân viên
        table= new JTable();
    	table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean bln1, int i, int i1) {
                Color selectedColor = Color.decode("#2980b9");
                	
                Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                com.setFont(new Font("Arial",Font.PLAIN,13));
                setBorder(noFocusBorder);
                if(i1==1 || i1==0) {
                    com.setForeground(new Color(0,0,0,150));
                    com.setFont(new Font("Arial",1,13));
                    if (selected) {
	                com.setBackground(new Color(0,0,0,20));
	            } else {
	                com.setBackground(Color.WHITE);
	            }
                    return com;
                }
                if (selected) {
                    com.setBackground(new Color(0,0,0,20));
                    com.setForeground(selectedColor);
                } else {
                    com.setBackground(Color.WHITE);
                    com.setForeground(new Color(102, 102, 102));
                }
                return com;
            }
	});
    	
        dftable= new DefaultTableModel(data, header) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
    	};
        table.setFont(new Font("Arial",1,13));
        table.setModel(dftable);
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  
    	
        JScrollPane scrollPanel= new JScrollPane(table);
    	scrollPanel.setVerticalScrollBar(new JScrollBar());
    	scrollPanel.setBounds(10, 10, 380, 505);
    	scrollPanel.setBorder(new LineBorder(Color.decode("#dfe4ea"),3));
    	panelFrame.add(scrollPanel);
        
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
		int selectedRow = table.getSelectedRow(); // Lấy dòng được chọn
                if (selectedRow != -1) { // Kiểm tra có dòng nào được chọn không
                    manv = (String) table.getValueAt(selectedRow, 0); // Lấy giá trị cột 0 (Mã nhân viên)
                    arr_temp = loadChamCongTheoNhanVien(manv);
                }
                
            }
	});
         
        ///// 
    	String[] thang_title= {"Tháng 01","Tháng 02","Tháng 03","Tháng 04","Tháng 05","Tháng 06","Tháng 07","Tháng 08","Tháng 09","Tháng 10","Tháng 11","Tháng 12"};
    	jc_thang= new JComboBox<String>();
    	jc_thang.setFont(new Font("Arial",1,14));
    	jc_thang.setModel(new DefaultComboBoxModel<>(thang_title));
    	jc_thang.setBounds(450,5,110,30);
    	jc_thang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		//Tháng 2
		int temp_thang= jc_thang.getSelectedIndex()+1;
		int temp_year = Integer.valueOf(jc_nam.getSelectedItem().toString());
				
		LocalDate tempdate = LocalDate.of(temp_year, temp_thang, 1);
		System.out.println(tempdate.getDayOfWeek());
		System.out.println(temp_thang+"/"+temp_year);
		for(JLabel i: arr_1) {
                    i.setBackground(Color.white);
                    i.setText("");
		}
		updateDayOfWeek(temp_thang, temp_year);
                    switch (temp_thang) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
                            arr_1.get(28).setVisible(true);
                            arr_1.get(29).setVisible(true);
                            arr_1.get(30).setVisible(true);
                            break;
			case 4:
			case 6:
			case 9:
			case 11:
                            arr_1.get(28).setVisible(true);
                            arr_1.get(29).setVisible(true);
                            arr_1.get(30).setVisible(false);
                            break;
			case 2:
                            if ((temp_year%4==0 && temp_year%1001!=0) || temp_year%400==0) {
				arr_1.get(28).setVisible(true);
                            }else {
				arr_1.get(28).setVisible(false);
                            }
						
                            arr_1.get(29).setVisible(false);
                            arr_1.get(30).setVisible(false);
                            break;
                            default:
                            break;
                    }
                    if (!arr_temp.isEmpty()) {
                        arr_temp.clear();
                    }
                    table.clearSelection(); 
		}
            });
    	
    	panelTop.add(jc_thang);
    	
        ////// 
    	LocalDate current = LocalDate.now();
    	String[] nam_title= new String[5];
    	for(int i=0;i<nam_title.length;i++) {
    		nam_title[i] = current.getYear()-2+i+"";
    	}
    	jc_nam= new JComboBox<String>();
    	jc_nam.setFont(new Font("Arial",1,14));
    	jc_nam.setModel(new DefaultComboBoxModel<>(nam_title));
    	jc_nam.setBounds(580,5,100,30);
    	// sự kiện cbb năm
    	jc_nam.addActionListener(new ActionListener() {
			
            @Override
            public void actionPerformed(ActionEvent e) {
                //Tháng 2
                int temp_thang= jc_thang.getSelectedIndex()+1;
                int temp_year = Integer.valueOf(jc_nam.getSelectedItem().toString());

                System.out.println(temp_thang+"/"+temp_year);
                for(JLabel i: arr_1) {
                    i.setBackground(Color.white);
                    i.setText("");
                }
                updateDayOfWeek(temp_thang, temp_year);
                switch (temp_thang) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        arr_1.get(28).setVisible(true);
                        arr_1.get(29).setVisible(true);
                        arr_1.get(30).setVisible(true);
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        arr_1.get(28).setVisible(true);
                        arr_1.get(29).setVisible(true);
                        arr_1.get(30).setVisible(false);
                        break;
                    case 2:
                        if ((temp_year%4==0 && temp_year%1001!=0) || temp_year%400==0)
                            arr_1.get(28).setVisible(true);
                        else 
                            arr_1.get(28).setVisible(false);
                        
                        arr_1.get(29).setVisible(false);
                        arr_1.get(30).setVisible(false);
                        break;
                    default:
                        break;
                }
                if (!arr_temp.isEmpty()) {
                    arr_temp.clear();
                }
                table.clearSelection();
            }
	});
    	panelTop.add(jc_nam);
    	
        //////
       	JPanel panel= new JPanel();
    	panel.setBounds(400,10,530,505);
    	panel.setLayout(null);
    	panel.setBorder(new LineBorder(Color.decode("#dfe4ea"),3));
    	panelFrame.add(panel);
    	   	
    	JPanel panel_1= new JPanel();
    	panel_1.setBounds(0,0,530,280);
    	panel_1.setLayout(null);
    	panel_1.setBorder(new LineBorder(Color.decode("#dfe4ea"),3));
    	panel.add(panel_1);
    	GridLayout layout= new GridLayout(4, 8);  
    	layout.setHgap(2);
    	layout.setVgap(2);
    	panel_1.setLayout(layout);
    	for(int i=1; i<32; i++) {
            JLabel a= new JLabel();
    		
            a.setOpaque(true);
            a.setFont(new Font("Arial",1,11));
            a.setBackground(Color.white);
            a.setHorizontalAlignment(JLabel.CENTER);
            a.setLayout(null);
            a.setName(String.valueOf(i));
            
            ///
            JLabel day= new JLabel("");
            day.setHorizontalAlignment(JLabel.CENTER);
            day.setFont(new Font("Arial",0,12));
            day.setBounds(0,0,75,15);
            day.setOpaque(true);
            day.setBackground(new Color(0,0,0,0));
            a.add(day);
    		
            arr_1.add(a);
            lb_dayOfWeek_list.add(day);
            panel_1.add(a);
    	}

    	JPanel thongTinChamCong= new JPanel();
    	thongTinChamCong.setBounds(110,305,450,40);
    	panel.add(thongTinChamCong);
    	thongTinChamCong.setLayout(null);
    	arr= new ArrayList<>();
    	    	
    	int x=0;
    	String[] ab= {"Nghỉ","Tăng Ca","Xóa"};
    	for(int i=0; i<3;i++) {
            JLabel b= new JLabel(ab[i]);
            b.setBackground(Color.white);
            b.setHorizontalAlignment(JLabel.CENTER);
            if(i==0) {
    		b.setBackground(Color.decode("#FF6A6A"));
            }
            b.setBounds(x,0,75,40);
            x+=90;
            b.setOpaque(true);
            arr.add(b);
            thongTinChamCong.add(b);
    	}
        /////////
    	jp_tangCa= new JPanel();
    	jp_tangCa.setLayout(null);
    	jp_tangCa.setBounds(5, 365, 400, 120);
    	panel.add(jp_tangCa);
    	jp_tangCa.setVisible(false);
    	
	JLabel title_tangCa= new JLabel("Loại tăng ca: ");
	title_tangCa.setFont(new Font("Arial",Font.BOLD,14));
	title_tangCa.setBounds(20,5,150,30);
	jp_tangCa.add(title_tangCa);
    	
    	int x_1=130;
    	ButtonGroup g= new ButtonGroup();
    	String[] abc= {"Ngày thường","Ngày lễ"};
    	arr_radio= new ArrayList<>();
    	for(int i=0;i<2;i++) {
            JRadioButton r1= new JRadioButton(abc[i]);
            if(i==0) {
    		r1.setSelected(true);
            }
            r1.setFocusable(false);
            r1.setFont(new Font("Arial",0,14));
            r1.setBounds(x_1,5,120,30);
            g.add(r1);
            arr_radio.add(r1);
            x_1+=120;
            jp_tangCa.add(r1);
    	} 
    	
        JLabel lb_soGio= new JLabel("Số giờ tăng ca: ");
	lb_soGio.setFont(new Font("Arial", Font.BOLD,14));
	lb_soGio.setBounds(20,45,150,30);
	jp_tangCa.add(lb_soGio); 
        
        tf_soGioOT = new JTextField();
        tf_soGioOT.setFont(new Font("Arial",0,14));
	tf_soGioOT.setBounds(130, 45,80,25);
	jp_tangCa.add(tf_soGioOT);
        
        JLabel lb_ghiChu1= new JLabel("Ghi chú:");
	lb_ghiChu1.setFont(new Font("Arial", Font.BOLD,14));
	lb_ghiChu1.setBounds(20,85,100,30);
	jp_tangCa.add(lb_ghiChu1); 
        
        JTextField tf_ghiChu1 = new JTextField();
        tf_ghiChu1.setFont(new Font("Arial",0,14));
	tf_ghiChu1.setBounds(130, 85,250,30);
	jp_tangCa.add(tf_ghiChu1);
        
        ///
        jp_nghi= new JPanel();
    	jp_nghi.setLayout(null);
    	jp_nghi.setBounds(5, 365, 400, 120);
    	panel.add(jp_nghi);
    	jp_nghi.setVisible(true);
    	
	JLabel lb_nghi= new JLabel("Nghỉ có phép: ");
	lb_nghi.setFont(new Font("Arial", Font.BOLD, 14));
	lb_nghi.setBounds(20,5,150,30);
	jp_nghi.add(lb_nghi);
    	
    	int x_2=130;
    	ButtonGroup g1= new ButtonGroup();
    	String[] abc1= {"Có","Không"};
    	arr_radio1= new ArrayList<>();
    	for(int i=0;i<2;i++) {
            JRadioButton r1= new JRadioButton(abc1[i]);
            if(i==0) {
    		r1.setSelected(true);
            }
            r1.setFocusable(false);
            r1.setFont(new Font("Arial",0,14));
            r1.setBounds(x_2,5,120,30);
            g1.add(r1);
            arr_radio1.add(r1);
            x_2+=120;
            jp_nghi.add(r1);
    	} 
    	
        JLabel lb_tinhLuong= new JLabel("Tính lương: ");
	lb_tinhLuong.setFont(new Font("Arial", Font.BOLD,14));
	lb_tinhLuong.setBounds(20,45,150,30);
	jp_nghi.add(lb_tinhLuong); 
        
        int x_3=130;
    	ButtonGroup g2= new ButtonGroup();
    	String[] abc2= {"Có","Không"};
    	arr_radio2= new ArrayList<>();
    	for(int i=0;i<2;i++) {
            JRadioButton r1= new JRadioButton(abc2[i]);
            if(i==0) {
    		r1.setSelected(true);
            }
            r1.setFocusable(false);
            r1.setFont(new Font("Arial",0,14));
            r1.setBounds(x_3,45,120,30);
            g2.add(r1);
            arr_radio2.add(r1);
            x_3+=120;
            jp_nghi.add(r1);
    	} 
        
        // Nghỉ ko phép thì không được tính lương
        arr_radio1.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arr_radio1.get(1).isSelected()) {
                    arr_radio2.get(1).setSelected(true);
                    arr_radio2.get(0).setEnabled(false);
                    arr_radio2.get(1).setEnabled(false);
                }
            }
        });
        
        arr_radio1.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arr_radio1.get(0).isSelected()) {
                    arr_radio2.get(0).setEnabled(true);
                    arr_radio2.get(1).setEnabled(true);
                }
            }
        });
        
        JLabel lb_ghiChu2= new JLabel("Ghi chú: ");
	lb_ghiChu2.setFont(new Font("Arial", Font.BOLD,14));
	lb_ghiChu2.setBounds(20,85,100,30);
	jp_nghi.add(lb_ghiChu2); 
        
        tf_ghiChu2 = new JTextField();
        tf_ghiChu2.setFont(new Font("Arial",0,14));
	tf_ghiChu2.setBounds(130, 85,250,30);
	jp_nghi.add(tf_ghiChu2);
        
        for(JLabel i: arr) {
            i.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for(JLabel i: arr) {
			i.setBackground(Color.white);
                    }
                    // TODO Auto-generated method stub
                    if(i.getText().equals("Nghỉ")) {
			i.setBackground(Color.decode("#FF6A6A"));
			setColor(Color.decode("#FF6A6A"));
			jp_tangCa.setVisible(false);
                        jp_nghi.setVisible(true);
                    }
					
                    if(i.getText().equals("Tăng Ca")) {
			i.setBackground(Color.decode("#4cd137"));
			setColor(Color.decode("#4cd137"));
			jp_tangCa.setVisible(true);
                        jp_nghi.setVisible(false);
                    }
					
                    if(i.getText().equals("Xóa")) {
			i.setBackground(Color.decode("#dfe6e9"));
			setColor(Color.white);
			jp_tangCa.setVisible(false);
                    }
		}
            });
    	}
        
    	for(JLabel i: arr_1) {
            i.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
                    int selectedRow = table.getSelectedRow(); // Lấy dòng được chọn
                    if (selectedRow != -1) { // Kiểm tra có dòng nào được chọn không
                        manv = (String) table.getValueAt(selectedRow, 0); // Lấy giá trị cột 0 (Mã nhân viên)
                    } else {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (i.getBackground().equals(colorsunday) && arr.get(0).getBackground().equals(Color.decode("#FF6A6A"))) {
                        return;
                    }
                    
                    String thang = jc_thang.getSelectedItem().toString();
                    String soThang = thang.replace("Tháng ", "");
                    String nam = jc_nam.getSelectedItem().toString();
                    String ngay = String.format("%02d", Integer.valueOf(i.getName()));
                    
                    String macc = "CC" + soThang + nam+ manv;
                    String mact = "CT" + ngay + soThang + nam + manv;
                    
                    boolean isSun = false;
                    
                    if (arr.get(0).getBackground().equals(Color.decode("#FF6A6A"))) {
                        if(!i.getBackground().equals(colorsunday)) {
                            i.setBackground(getColor());
                            if(arr_radio1.get(0).isSelected()) {
                                    i.setHorizontalAlignment(JLabel.CENTER);
                                    i.setText("NP");
                            } else if(arr_radio1.get(1).isSelected()) {
                                    i.setHorizontalAlignment(JLabel.CENTER);
                                    i.setText("KP");
                            }  
                        }
                        dsChiTietCC(mact, macc, isSun, i);
                        return;
                    }
                    // 
                    if (arr.get(1).getBackground().equals(Color.decode("#4cd137"))) {
                        if (tf_soGioOT.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Vui lòng điền số giờ tăng ca", "Thông báo", JOptionPane.WARNING_MESSAGE);
                            return; 
                        }
                        try {
                            Float soGio = Float.valueOf(tf_soGioOT.getText());
                            if (soGio <= 0) {
                                JOptionPane.showMessageDialog(null, "Số giờ phải lớn hơn 0", "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            if (!i.getBackground().equals(colorsunday)) {
                                i.setBackground(getColor());
                            }
                            if(i.getBackground().equals(Color.decode("#4cd137")) || i.getBackground().equals(colorsunday)) {
                                if(arr_radio.get(1).isSelected()) {
                                    i.setHorizontalAlignment(JLabel.CENTER);
                                    i.setText("Lễ "+ soGio + "h");
                                } else if(arr_radio.get(0).isSelected()) {
                                    i.setHorizontalAlignment(JLabel.CENTER);
                                    i.setText(soGio + "h");
                                }
                                if (i.getBackground().equals(colorsunday))
                                    isSun = true;
                                dsChiTietCC(mact, macc, isSun, i);
                                
                            }
                        } catch (NumberFormatException a) {
                            JOptionPane.showMessageDialog(null, "Số giờ phải là một số hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        return;
                    }
                    
                    if(i.getBackground().equals(Color.decode("#4cd137")) || i.getBackground().equals(Color.decode("#FF6A6A"))) {
                        i.setBackground(getColor());
                        i.setText("");
                    } else if(i.getBackground() == colorsunday)
                        i.setText("");
                    dsChiTietCC(mact, macc, isSun, i);
                   
                }
            });
    	}
    	
        //dateChooser.DateChooser dc = new dateChooser.DateChooser();
        //dc.setTextRefernce(tGetNgayThangNam);
        //System.out.println("//////////// tGetNgayThangNam" + dc);
        
        btnThem = new JButton();
        btnThem.setText("Thêm");
        btnThem.setFont(new Font("Arial", Font.BOLD, 13));
        btnThem.setForeground(Color.white);
        btnThem.setBackground(Color.decode("#37A4F2"));
        btnThem.setBorderPainted(false);
        btnThem.setFocusPainted(false);
        btnThem.setBounds(430, 460, 80, 40);
        btnThem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnThem);
        
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                themChiTietChamCong();
                if (!arr_temp.isEmpty()) {
                    arr_temp.clear();
                }
            }
        });
          
        // set up end
        LocalDate temp_date = LocalDate.now();
    	jc_nam.setSelectedIndex(2);
    	jc_thang.setSelectedIndex(temp_date.getMonthValue()-1);
    	updateDayOfWeek(temp_date.getMonthValue(), temp_date.getYear());
        
    }
    
    
    
    private void loadDSNhanVien() {
        table.setDefaultEditor(Object.class, null); // không cho click vào & edit nội dung các cell trong bảng
        arrNhanVien = nvBUS.selectAll();
	for(int i=0; i<arrNhanVien.size(); i++) {
            NhanVienDTO nvien = arrNhanVien.get(i);
            String cvu = nvien.getChucVu();
            if (!cvu.equals("CV004") ) {
                String manv = nvien.getMaNV();
                String nv = nvien.getHoTen();
                ChucVuDTO cv = cvBUS.selectById(cvu);
                String chucvu = cv.getTenCV();
                String chiNhanh = nvien.getChiNhanh();
                
                Object[] row = {manv, nv, chucvu, chiNhanh};
		dftable.addRow(row);
            }
        }
    }

    private ArrayList<ChiTietChamCongDTO> dsChiTietCC(String mact, String macc, boolean isSun, JLabel i) {
        String loaicc = null;
        String ghiChu = null;
        float gioOT = 0 ;
        
        if (arr.get(0).getBackground().equals(Color.decode("#FF6A6A"))) {
            if (tf_ghiChu2 != null) 
                ghiChu = tf_ghiChu2.getText();
            if(arr_radio1.get(1).isSelected()) {
                loaicc = "Nghỉ không phép";
            } else if(arr_radio1.get(0).isSelected() && arr_radio2.get(0).isSelected()) {
                loaicc = "Nghỉ phép có lương";
            } else if(arr_radio1.get(0).isSelected() && arr_radio2.get(1).isSelected()) {
                loaicc = "Nghỉ phép không lương";
            }
        } else if (arr.get(1).getBackground().equals(Color.decode("#4cd137"))) {  
            if (tf_ghiChu1 != null)
                ghiChu = tf_ghiChu1.getText();
            gioOT = Float.parseFloat(tf_soGioOT.getText());           
            if(arr_radio.get(0).isSelected() && !isSun)
                loaicc = "Tăng ca ngày thường";
            else if(arr_radio.get(0).isSelected() && isSun)
                loaicc = "Tăng ca chủ nhật";
            else if(arr_radio.get(1).isSelected())         // Tăng ca chủ nhật vào ngày lễ thì vẫn được tính lương tăng ca ngày lễ
                loaicc = "Tăng ca ngày lễ";
        }
        System.out.println("Arr_temp is empty? " + arr_temp.isEmpty());
        for (ChiTietChamCongDTO ct : arr_temp) {
            System.out.println(ct.getMaCTCC() + " ; " + ct.getLoaiChamCong());
        }
        
        Iterator<ChiTietChamCongDTO> iter = arr_temp.iterator();
        while (iter.hasNext()) {
            ChiTietChamCongDTO ct = iter.next();
            if (mact.equals(ct.getMaCTCC())) {   // Đã có ctcc thì update hc delete chi tiết
                if (arr.get(2).getBackground().equals(Color.decode("#dfe6e9"))) {
                    // Delete
                    iter.remove();
                    System.out.println("Delete " + ct.getMaCTCC() + " ; "  + ct.getLoaiChamCong() + " ; "  + ct.getSoGioOT() + " ; "  + ct.getChiTiet());
                    return arr_temp;
                } else {
                    // Update
                    ct.setLoaiChamCong(loaicc);
                    ct.setSoGioOT(gioOT);
                    ct.setChiTiet(ghiChu);
                    System.out.println("Update " + ct.getMaCTCC() + " ; "  + ct.getLoaiChamCong() +  " ; "  + ct.getSoGioOT() + " ; "  + ct.getChiTiet());
                    return arr_temp;
                } 
            } 
        } 
        // Chưa có ctcc thì add
        ChiTietChamCongDTO ct_new = new ChiTietChamCongDTO(mact, null, loaicc, ghiChu, macc, gioOT);
        arr_temp.add(ct_new);
        System.out.println("Add " + ct_new.getMaCTCC() + " ; "  + ct_new.getLoaiChamCong() + " ; "  + ct_new.getSoGioOT() + " ; "  + ct_new.getChiTiet());
        return arr_temp;        
    } 
    
    private void themChiTietChamCong() {
        String thang = jc_thang.getSelectedItem().toString();
        String soThang = thang.replace("Tháng ", "");
        String nam = jc_nam.getSelectedItem().toString();
        
        int selectedRow = table.getSelectedRow(); // Lấy dòng được chọn
        if (selectedRow != -1) { // Kiểm tra có dòng nào được chọn không
            manv = (String) table.getValueAt(selectedRow, 0); // Lấy giá trị cột 0 (Mã nhân viên)
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String macc = "CC" + soThang + nam+ manv;
        LocalDate date = LocalDate.now();

        BangChamCongDTO bcc = bccBUS.selectById(macc);
        if (bcc == null) {
            // Nếu bảng chấm công chưa tồn tại => tạo bảng chấm công mới
            bcc = new BangChamCongDTO();
            bcc.setMaBCC(macc);
            bcc.setThangCC(Integer.parseInt(soThang));
            bcc.setNamCC(Integer.parseInt(nam));
            bcc.setSoNgayLam(24.0f);
            bcc.setSoNgayNghiKP(0);
            bcc.setSoNPCoLuong(0);
            bcc.setSoNPKhongLuong(0);
            bcc.setSoGioOTNgayThuong(0);
            bcc.setSoGioOTNgayLe(0);
            bcc.setSoGioOTCN(0);
            bcc.setMaNV(manv);
            String resultAdd = bccBUS.insert(bcc);
            System.out.println(resultAdd);
        } else {
            bcc.setSoNgayLam(24.0f);
            bcc.setSoNgayNghiKP(0);
            bcc.setSoNPCoLuong(0);
            bcc.setSoNPKhongLuong(0);
            bcc.setSoGioOTNgayThuong(0);
            bcc.setSoGioOTNgayLe(0);
            bcc.setSoGioOTCN(0);
        }

        ctccBUS.xoaChiTietChamCongTheoMaCC(macc);
        System.out.println(arr_temp.size());
        int c = 0;
        // Xoá hết ctcc có mabcc -> add ctct 
        for (ChiTietChamCongDTO ct : arr_temp) {
            if (ct.getMaBCC().equals(macc)) {
                ct.setNgayTao(date);
                System.out.println("Thêm CTCC: " + ct.getMaCTCC());
                int resultAdd = ctccBUS.insertChiTietCC(ct);
                if (resultAdd > 0) {
                    c += 1;
                    System.out.println("/// c = " + c);
                    switch (ct.getLoaiChamCong()) {
                        case "Nghỉ không phép":
                            bcc.setSoNgayNghiKP(bcc.getSoNgayNghiKP() + 1.0f);
                            break;
                        case "Nghỉ phép có lương":
                            bcc.setSoNPCoLuong(bcc.getSoNPCoLuong() + 1.0f);
                            break;
                        case "Nghỉ phép không lương":
                            bcc.setSoNPKhongLuong(bcc.getSoNPKhongLuong() + 1.0f);
                            break;
                        case "Tăng ca ngày thường":
                            bcc.setSoGioOTNgayThuong(bcc.getSoGioOTNgayThuong() + ct.getSoGioOT());
                            break;
                        case "Tăng ca ngày lễ":
                            bcc.setSoGioOTNgayLe(bcc.getSoGioOTNgayLe() + ct.getSoGioOT());
                            break;
                        case "Tăng ca chủ nhật":
                            bcc.setSoGioOTCN(bcc.getSoGioOTCN() + ct.getSoGioOT());
                            break;
                        default:
                            break;
                    }
                    bcc.setSoNgayLam(24.0f - bcc.getSoNgayNghiKP() - bcc.getSoNPCoLuong() - bcc.getSoNPKhongLuong());
                    bccBUS.updateById(bcc);
                }
            }
        }
        if (c == arr_temp.size()) {
            JOptionPane.showMessageDialog(null, "Thêm chi tiết chấm công thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            arr_temp.clear();
        } else JOptionPane.showMessageDialog(null, "Thêm chi tiết chấm công thất bại ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        loadChamCongTheoNhanVien(manv);
    }
    
    // Done
    private ArrayList<ChiTietChamCongDTO> loadChamCongTheoNhanVien(String manv) {
        String thang = jc_thang.getSelectedItem().toString();
        String soThang = thang.replace("Tháng ", "");
        String nam = jc_nam.getSelectedItem().toString();

        String macc = "CC" + soThang + nam+ manv;
        ArrayList<ChiTietChamCongDTO> arr_CTCC = new ArrayList<ChiTietChamCongDTO>();
        arr_CTCC = ctccBUS.getChiTietCCTheoMaCC(macc);
        
        int temp_t = Integer.parseInt(soThang);
        int temp_n = Integer.parseInt(nam);
        resetLabel(temp_t, temp_n);
        
        if (!arr_CTCC.isEmpty()) {
            for (ChiTietChamCongDTO ctcc: arr_CTCC) {
                for(JLabel i: arr_1) { 
                    String ngay = String.format("%02d", Integer.valueOf(i.getName()));
                    String date = ngay + soThang + nam; 
                    if (ctcc.getMaCTCC().contains(date)) {
                        switch (ctcc.getLoaiChamCong()) {
                            case "Nghỉ phép có lương":
                            case "Nghỉ phép không lương":
                                i.setBackground(Color.decode("#FF6A6A"));
                                i.setText("NP");
                                break;
                            case "Nghỉ không phép":
                                i.setBackground(Color.decode("#FF6A6A"));
                                i.setText("KP");
                                break;
                            case "Tăng ca ngày thường":
                                i.setBackground(Color.decode("#4cd137"));
                                i.setText("<html>"+ ctcc.getSoGioOT() + "h" + "</html>");
                                break;
                            case "Tăng ca ngày lễ":
                                i.setBackground(Color.decode("#4cd137"));
                                i.setText("<html> Lễ <br> "+ ctcc.getSoGioOT() + "h" +"</html>");
                                break;
                            case "Tăng ca chủ nhật":
                                i.setBackground(colorsunday);
                                i.setText(ctcc.getSoGioOT() + "h");
                                break;
                            default:
                                break;
                        }
                        
                        // Tạo popup menu xem chi tiết
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem item1 = new JMenuItem("Xem chi tiết");
                        // Thêm item vào popup
                        popupMenu.add(item1);
                        // Gắn sự kiện chuột phải cho JLabel
                        i.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                showPopup(e);
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                                showPopup(e);
                            }

                            private void showPopup(MouseEvent e) {
                                if (e.isPopupTrigger()) {
                                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                                }
                            }
                        });
                        item1.addActionListener((ActionEvent e) -> {
                            loadChiTietChamCong(i, ctcc);
                        });
                    }
                }
            }
        } else System.out.println("Nhân viên không có ctcc");
        return arr_CTCC;
    }
    
    private void loadChiTietChamCong(JLabel i, ChiTietChamCongDTO ctcc) {
        if (i.getBackground().equals(Color.WHITE)) {
            jp_tangCa.setVisible(false);
            jp_nghi.setVisible(false);
            return;
        }
        if (ctcc.getLoaiChamCong().contains("Tăng ca")) { 
            jp_tangCa.setVisible(true);
            arr.get(0).setBackground(Color.WHITE);
            arr.get(1).setBackground(Color.decode("#4cd137"));
            tf_soGioOT.setText(String.valueOf(ctcc.getSoGioOT()));
            tf_ghiChu1.setText(ctcc.getChiTiet());
            if (ctcc.getLoaiChamCong().contains("ngày thường") || 
                ctcc.getLoaiChamCong().contains("chủ nhật") && !i.getText().contains("Lễ"))
                arr_radio.get(0).setSelected(true);
            else if (ctcc.getLoaiChamCong().contains("ngày lễ") || 
                ctcc.getLoaiChamCong().contains("chủ nhật") && i.getText().contains("Lễ")) 
                arr_radio.get(0).setSelected(true);

        } else if (ctcc.getLoaiChamCong().contains("Nghỉ")) { 
            jp_nghi.setVisible(true);
            tf_ghiChu2.setText(ctcc.getChiTiet());
            arr.get(0).setBackground(Color.decode("#FF6A6A"));
            arr.get(1).setBackground(Color.WHITE);
            switch (ctcc.getLoaiChamCong()) {
                case "Nghỉ không phép" -> {
                    arr_radio1.get(1).setSelected(true);
                    arr_radio2.get(1).setSelected(true);
                }
                case "Nghỉ phép có lương" -> {
                    arr_radio1.get(0).setSelected(true);
                    arr_radio2.get(0).setSelected(true);
                }
                case "Nghỉ phép không lương" -> {
                    arr_radio1.get(0).setSelected(true);
                    arr_radio2.get(1).setSelected(true);
                }
                default -> {
                }
            }
        }
    }
     
    private void resetLabel(int thang, int nam) {
        for (JLabel i : arr_1) {
            if (i.getBackground() != colorsunday) {
                i.setBackground(Color.WHITE);
            }
            i.setText("");
        }   
    }
    
    public void updateDayOfWeek(int month, int year) {
    	int daysOfMonth = getDayOfMonth(month, year);
    	for (int i=1;i<=daysOfMonth;i++) {
            LocalDate tempdate = LocalDate.of(year, month, i);
            String temp = "";
            switch (tempdate.getDayOfWeek().toString()){
                case "MONDAY":
                    temp = "Mon";
                    break;
    		case "TUESDAY":
                    temp = "Tue";
                    break;
    		case "WEDNESDAY":
                    temp = "Wed";
                    break;
    		case "THURSDAY":
                    temp = "Thu";
                    break;
    		case "FRIDAY":
                    temp = "Fri";
                    break;
    		case "SATURDAY":
                    temp = "Sat";
                    break;
    		case "SUNDAY":
                    temp = "Sun";
                    arr_1.get(i-1).setBackground(colorsunday);
                    break;
			
                default:
                    temp = "Hi";
                    break;
            }
            lb_dayOfWeek_list.get(i-1).setText(i + "  "+temp);
    	}
    }
    
    public int getDayOfMonth(int month, int year) {
    	switch (month) {
            case 1: 
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
		return 31;
            case 4:
            case 6:
            case 9:
            case 11:
		return 30;
            case 2:
                if( (year%4==0 && year%100!=0) || year%400==0) {
                    return 29;
		}
		return 28;
            default:
		return 0;
	}
    }
    
    /* Get, set */
    public JButton getBtnThem() {
        return btnThem;
    }
    
    public ArrayList<JLabel> getArr_1() {
		return arr_1;
    }
    
    public void setArr_1(ArrayList<JLabel> arr_1) {
	this.arr_1 = arr_1;
    }
	
    public void setJc_thang(JComboBox<String> thang) {
	this.jc_thang = thang;
    }
	
    public JComboBox<String> getJc_thang() {
	return jc_thang;
    }
	
    public JComboBox<String> getNam() {
	return jc_nam;
    }
	
    public void setNam(JComboBox<String> nam) {
	this.jc_nam = nam;
    }
	
    public Color getColor() {
	return color;
    }
	
    public void setColor(Color color) {
	this.color = color;
    }
	
    public JButton getBtnBack() {
    	return this.btnBack;
    }   

    public JTextField gettGetNgayThangNam() {
        return tGetNgayThangNam;
    }
}
    /*private void loadBangChamCongList() {
    	
    }*/
