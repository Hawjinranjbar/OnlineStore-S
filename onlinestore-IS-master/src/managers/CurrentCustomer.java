
package managers;

public class CurrentCustomer {
    private static int loggedInCustomerId = -1;

    public static void login(int customerId) {
        loggedInCustomerId = customerId;
    }

    public static int getLoggedInCustomerId() {
        return loggedInCustomerId;
    }

    public static void logout() {
        loggedInCustomerId = -1;
    }

    public static boolean isLoggedIn() {
        return loggedInCustomerId != -1;
    }
}
