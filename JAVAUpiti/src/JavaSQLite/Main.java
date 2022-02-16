package JavaSQLite;

public class Main {

	public static void main(String[] args) {
		SQLite_java db = new SQLite_java();
		db.connect();
		
		db.vrsiUplatu(1, 2, 200000);
		
		db.disconnect();
	}

}
