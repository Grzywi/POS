package POS.util;

public class NameKeeper {

	private static String name;
	private static int id;

	private static int tableNumber;
	private static int password;
	
	private NameKeeper() {
	}

	public static String getName() {
		return name;
	}

	public static void setName(final String name) {
		NameKeeper.name = name;
	}
	public static int getId() {
		return id;
	}
	public static void setId(final int id) {
		NameKeeper.id = id;
	}
	
	public static int getTableNumber() {
		return tableNumber;
	}
	public static void setTableNumber(final int tableNumber) {
		NameKeeper.tableNumber = tableNumber;
	}
	
	public static int getPassword() {
		return password;
	}
	
	public static void setPassword(final int password) {
		NameKeeper.password = password;
	}
}
