package POS.controller;

public class nameKeeper {

	private static String name;
	private static int id;

	private nameKeeper() {
	}

	public static String getName() {
		return name;
	}

	public static void setName(final String name) {
		nameKeeper.name = name;
	}
	public static int getId() {
		return id;
	}
	public static void setId(final int id) {
		nameKeeper.id = id;
	}
}
