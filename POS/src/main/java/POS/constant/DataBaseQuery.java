package POS.constant;

public class DataBaseQuery {
	
	public static String GET_WAITER = "select * from kelnerzy WHERE PIN = ?";
	
	public static String GET_CURRENT_WAITER_ORDERS = "select * from orders WHERE waiterId = ?";
	
	public static String GET_OTHER_WAITERS_ORDERS = "select * from orders WHERE waiterId <> ?";

	private DataBaseQuery() {
		
	}
}
