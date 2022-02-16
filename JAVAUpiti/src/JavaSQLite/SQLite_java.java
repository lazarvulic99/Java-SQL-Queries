package JavaSQLite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite_java {

	private Connection conn = null;
	
	public void connect() { //konektovanje sa bazom
		disconnect();
		try {
			Class.forName("org.sqlite.JDBC"); //ucita ono sto je potrebno kao driver; klasa za rad //za sta se vezujemo i koju cemo konekciju sa bazom raditi
			conn = DriverManager.getConnection("jdbc:sqlite:Banka.db"); //pravimo konekciju bas sa tom bazom i to naznacili da se radi o sqlite bazi podataka
			System.out.println("Konekcija uspesno uspostavljena");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
	public void disconnect() {
		if(conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} 
	
	
	public void OrderedKomitentByNazivAdresaDescOba() {
		String sql = "SELECT * FROM Komitent ORDER BY Naziv DESC, Adresa DESC";
		
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt("IdKom") + "\t" + rs.getString("Naziv") + "\t" + rs.getString("Adresa"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void AllIdRacCertainStatus(char c) {
		String sql = "SELECT IdRac FROM Racun WHERE Status = ?";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, String.valueOf(c));
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					System.out.println(rs.getInt("IdRac"));
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Pr16(int kamata) {
		String sql = "SELECT Stanje, ?, ?*Stanje*(-1) FROM Racun Where Stanje < 0";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, kamata);
			ps.setDouble(2, kamata*0.01);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					System.out.println(rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getDouble(3));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Pr19() {
		String sql = "SELECT *, Stanje + DozvMinus FROM Racun WHERE Stanje > -DozvMinus";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt("IdRac") + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" +
									rs.getInt(4) + "\t" + rs.getInt(5) + "\t" + rs.getInt(6) + "\t" + rs.getInt(8));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void StanjeNull() {
		String sql = "SELECT IdRac FROM Racun WHERE Stanje IS NULL";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void SumaStanja() {
		String sql = "SELECT SUM(Stanje) FROM Racun";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void MinStanje() {
		String sql = "SELECT Min(Stanje) FROM Racun WHERE Stanje > 0";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void KomitentIRacun() {
		String sql = "SELECT Racun.IdRac, Komitent.Naziv, Racun.Stanje FROM Racun, Komitent WHERE Racun.IdKom = Komitent.IdKom";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getObject(3));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Pr27() {
		String sql = "SELECT Stavka.IdSta, Filijala.Naziv, Stavka.Iznos, Stavka.IdRac FROM Stavka, Filijala"
				+ " WHERE Stavka.IdFil = Filijala.IdFil";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Pr28() {
		String sql = "SELECT Stavka.IdSta, F1.Naziv, Stavka.Iznos, Stavka.IdRac, F2.Naziv FROM Stavka, Filijala F1, Filijala F2"
				+ ", Racun WHERE Stavka.IdFil = F1.IdFil AND Stavka.IdRac = Racun.IdRac AND Racun.IdFil = F2.IdFil";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getString(5));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Pr29() {
		String sql = "SELECT Uplata.IdSta, Filijala.Naziv FROM Uplata, Filijala, Stavka"
				+ " WHERE Stavka.IdSta = Uplata.IdSta AND Stavka.IdFil = Filijala.IdFil AND Osnov = 'Plata'";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void Pr30() {
		String sql = "SELECT IdKom, SUM(Stanje) FROM Racun"
				+ " GROUP BY IdKom";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void Pr31() {
		String sql = "SELECT IdRac, COUNT(Iznos), SUM(Iznos) FROM Stavka, Uplata "
				+ "WHERE Stavka.IdSta = Uplata.IdSta GROUP BY IdRac";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Pr32() {
		String sql = "SELECT Komitent.Naziv, SUM(Stanje) FROM Komitent, Racun "
				+ "WHERE Racun.IdKom = Komitent.IdKom GROUP BY Komitent.IdKom, Komitent.Naziv";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getInt(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void Pr33() {
		String sql = "SELECT Komitent.Naziv, Count(*) FROM Komitent, Racun "
				+ "WHERE Racun.IdKom = Komitent.IdKom AND Racun.Status = 'A' GROUP BY Komitent.IdKom";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getInt(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Pr35() {
		String sql = "SELECT Komitent.Naziv, Sum(Racun.Stanje) FROM Komitent, Racun "
				+ "WHERE Racun.IdKom = Komitent.IdKom GROUP BY Komitent.IdKom, Komitent.Naziv HAVING Sum(Racun.Stanje) > 0";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getInt(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Pr38() {
		String sql = "SELECT IdRac FROM Stavka "
				+ "GROUP BY IdRac HAVING Count(DISTINCT(IdFil)) > 1";
		
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			
			while(rs.next()) {
				System.out.println(rs.getString(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void CreateMesto() {
		String sql = "CREATE TABLE Mesto1("
					+ "IdMes INTEGER,"
					+ "PostBr VARCHAR(6) UNIQUE NOT NULL,"
					+ "Naziv VARCHAR(50) NOT NULL,"
					+ "PRIMARY KEY(IdMes))";
		
		try(Statement stmt = conn.createStatement()){
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertNewMesto(int id, String postbr, String naziv) {
		String sql = "INSERT INTO Mesto1 (IdMes, PostBr, Naziv) VALUES (?,?,?)";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, id);
			ps.setString(2, postbr);
			ps.setString(3, naziv);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean updateBrIme(int id, String postbr, String name) {
		String sql = "UPDATE Mesto1 SET PostBr=?, Naziv=? Where IdMes=?";
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, postbr);
			ps.setString(2, name);
			ps.setInt(3, id);
			
			return ps.executeUpdate() > 0;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public void deletemesto1() {
		String sql = "DELETE FROM Mesto1 WHERE IdMes = 0";
		
		try(Statement stmt = conn.createStatement()){
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean vrsiUplatu(int idRac, int idFil, int iznos) {
		//trazimo idStavke koje cemo uneti kao +1 poslednje stavke
		String idSta_sql = "SELECT MAX(idSta) FROM Stavka";
		int idSta = 0;
		try(Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(idSta_sql)){
			while(rs.next()) {
				idSta = rs.getInt(1) + 1;
			}
			//System.out.println(idSta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//upisujemo stavku
		boolean uspesnost = true;
		String stavka_sql = "INSERT INTO Stavka (IdSta, Iznos, IdFil, IdRac)"
							+ "VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement ps = conn.prepareStatement(stavka_sql)){
			
			conn.setAutoCommit(false);
			
			ps.setInt(1, idSta);
			ps.setInt(2, iznos);
			ps.setInt(3, idFil);
			ps.setInt(4, idRac);
			
			if(ps.executeUpdate() == 0) throw new SQLException();
			
			//upisujemo uplatu
			String uplata_sql = "INSERT INTO Uplata (IdSta, Osnov)"
								+ "VALUES (?,'Uplata')";
			try(PreparedStatement ps1 = conn.prepareStatement(uplata_sql)){
				ps1.setInt(1, idSta);
				if(ps1.executeUpdate() == 0) throw new SQLException();
			}
			
			//nalazenje prethodnog statusa
			String stari_status = "";
			String trenutni_status = "SELECT Status FROM Racun WHERE IdRac = ?";
			
			try(PreparedStatement ps2 = conn.prepareStatement(trenutni_status)){
				ps2.setInt(1, idRac);
				try(ResultSet rs = ps2.executeQuery()){
					while(rs.next()) {
						stari_status = rs.getString(1);
					}
					System.out.println(stari_status);
				}
			}
			
			//uplata na racun
			String uplata = "UPDATE Racun SET Stanje = Stanje + ? WHERE IdRac = ?";
			try(PreparedStatement ps3 = conn.prepareStatement(uplata)){
				ps3.setInt(1, iznos);
				ps3.setInt(2, idRac);
				if(ps3.executeUpdate() == 0) throw new SQLException();
			}
			
			//provera da li treba da se menja status -> proveravas samo ako je pre bilo blokirano
			if(stari_status.compareTo("B") == 0) {
				int proveraId = -1;
				String stanje_ok = "SELECT IdRac FROM Racun WHERE IdRac = ? AND Stanje >= -DozvMinus"; //ako je sad iznad dozvminus, treba u A
				try(PreparedStatement ps4 = conn.prepareStatement(stanje_ok)){
					ps4.setInt(1, idRac);
					try(ResultSet rs = ps4.executeQuery()){
						while(rs.next()) {
							proveraId = rs.getInt(1);
						}
						if(proveraId != -1) { //znaci sad je ok, treba u A
							//updateStatus
							String updateStatus = "UPDATE Racun SET Status = 'A' WHERE IdRac = ?";
							try(PreparedStatement ps5 = conn.prepareStatement(updateStatus)){
								ps5.setInt(1, idRac);
								if(ps5.executeUpdate() == 0) throw new SQLException();
								System.out.println("Doslo je do promene statusa");
							}
							
						}
					}
				}
			}
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
				uspesnost = false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return uspesnost;
	}
		
				
}
