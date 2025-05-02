package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DTO.KhoDTO;
import DTO.NhanVienDTO;
import Database.JDBCConnection;

public class KhoDAO implements DAOInterface<KhoDTO> {
	JDBCConnection jdbc = new JDBCConnection();

	@Override
	public ArrayList<KhoDTO> selectAll() {
		ArrayList<KhoDTO> arrKho = new ArrayList<KhoDTO>();

		try {

			jdbc.openConnection();

			String query = "select * from kho";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				KhoDTO kho = new KhoDTO();
				kho.setMaKho(rs.getString("maKho"));
				kho.setTenKho(rs.getString("tenKho"));
				kho.setDiaChi(rs.getString("diaChi"));
				kho.setSdt(rs.getString("sdt"));
				kho.setTrangThai(rs.getString("trangThai"));

				arrKho.add(kho);
			}

			// Đóng tài nguyên
			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			jdbc.closeConnection();
		}

		return arrKho;
	}

	@Override
	public KhoDTO selectById(String t) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMaNVQuanLyKho(String maKho) {
		String maNV = null;
		String query = "SELECT nv.maNV FROM nhanvien nv " + "WHERE nv.maCV = 'CV001' AND nv.chiNhanh = ?";

		try {
			if (!jdbc.openConnection()) {
				jdbc.openConnection();
			}

			PreparedStatement stmt = jdbc.getConnection().prepareStatement(query);
			stmt.setString(1, maKho);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				maNV = rs.getString("maNV");
			}

			rs.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return maNV;
	}

	public ArrayList<Object[]> getDanhSachPBSPTheoKho(String maKho) {
		ArrayList<Object[]> list = new ArrayList<>();
		try {
			String sql = "SELECT pbsp.maPBSP, pbsp.mauSac, pbsp.RAM, pbsp.ROM, pbsp.giaBan, pbsp.soLuong, pbsp.trangThai, pbsp.maSP "
					+ "FROM pbsp JOIN KHO_PBSP kps ON pbsp.maPBSP = kps.maPBSP " + "WHERE kps.maKho = ?";
			PreparedStatement ps = jdbc.getConnection().prepareStatement(sql);
			ps.setString(1, maKho);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Object[] row = new Object[] { rs.getString("maPBSP"), rs.getString("mauSac"), rs.getString("RAM"),
						rs.getString("ROM"), rs.getDouble("giaBan"), rs.getInt("soLuong"), rs.getString("trangThai"),
						rs.getString("maSP") };
				list.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<NhanVienDTO> getNhanVienByKho(String tenKho) {
		ArrayList<NhanVienDTO> danhSachNhanVien = new ArrayList<>();
		String query = "SELECT * FROM NHANVIEN nv JOIN KHO k ON nv.chiNhanh = k.maKho WHERE k.tenKho = ?";

		try {
			// Kiểm tra xem kết nối đã bị đóng chưa, nếu đóng thì mở lại kết nối
			if (!jdbc.openConnection()) {
				jdbc.openConnection();
			}

			// Thực hiện truy vấn
			try (PreparedStatement stmt = jdbc.getConnection().prepareStatement(query)) {
				stmt.setString(1, tenKho);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					NhanVienDTO nv = new NhanVienDTO();
					nv.setMaNV(rs.getString("maNV"));
					nv.setHoTen(rs.getString("hoTen"));
					nv.setNgaySinh(rs.getDate("ngaySinh"));
					nv.setGioiTinh(rs.getString("gioiTinh"));
					nv.setDiaChi(rs.getString("diaChi"));
					nv.setSoDienThoai(rs.getString("sdt"));
					nv.setEmail(rs.getString("email"));

					// Thêm nhân viên vào danh sách
					danhSachNhanVien.add(nv);
				}
			}
		} catch (Exception e) {
			System.err.println("Error while fetching data: " + e.getMessage());
			e.printStackTrace();
		}

		return danhSachNhanVien;
	}

	public KhoDTO getKhoByName(String tenKho) {
		KhoDTO kho = null;

		if (tenKho == null || tenKho.trim().isEmpty()) {
			return null; // Tránh việc truy vấn với tên kho trống hoặc null
		}

		if (!jdbc.openConnection()) {
			jdbc.openConnection();
		}

		String query = "SELECT * FROM Kho WHERE tenKho = ?";

		try (PreparedStatement stmt = jdbc.getConnection().prepareStatement(query)) {
			stmt.setString(1, tenKho);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				kho = new KhoDTO();
				kho.setMaKho(rs.getString("maKho"));
				kho.setTenKho(rs.getString("tenKho"));
				kho.setDiaChi(rs.getString("diaChi"));
				kho.setSdt(rs.getString("sdt"));
				kho.setTrangThai(rs.getString("trangThai"));
			}
		} catch (Exception e) {
			System.err.println("Error while fetching Kho data: " + e.getMessage());
		}

		return kho; // Nếu không tìm thấy kho, trả về null
	}

	public String getIdByName(String tenKho) {
		String maKho = "";

		try {

			jdbc.openConnection();

			String query = "select maKho from kho where tenKho = ?";

			PreparedStatement ps = jdbc.getConnection().prepareStatement(query);
			ps.setString(1, tenKho);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				maKho = rs.getString("maKho");
			}

		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		} finally {
			// Đóng kết nối CSD
			jdbc.closeConnection();
		}

		return maKho;
	}

	@Override
	public int insert(KhoDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(KhoDTO t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(KhoDTO kho) {
		String query = "UPDATE KHO SET tenKho = ?, diaChi = ?, sdt = ?, trangThai = ? WHERE maKho = ?";
		try {
			if (!jdbc.openConnection()) {
				jdbc.openConnection();
			}

			PreparedStatement stmt = jdbc.getConnection().prepareStatement(query);
			stmt.setString(1, kho.getTenKho());
			stmt.setString(2, kho.getDiaChi());
			stmt.setString(3, kho.getSdt());
			stmt.setString(4, kho.getTrangThai());
			stmt.setString(5, kho.getMaKho());

			int rows = stmt.executeUpdate();
			stmt.close();
			return rows; // số dòng bị ảnh hưởng
		} catch (Exception e) {
			System.err.println("Update lỗi: " + e.getMessage());
			return 0;
		}
	}

}
