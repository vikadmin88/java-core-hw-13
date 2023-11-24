package m13.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task1Tests {
    private final static String URL_USERS = "https://jsonplaceholder.typicode.com/users";
    private final static String URL_USER_NAME = "https://jsonplaceholder.typicode.com/users?username=";

    private static ClientAPI getClientAPI() {
        Map<String,String> props = new HashMap<>();
        props.put("Timeout", "60000");
        props.put("Content-Type", "application/json");
        props.put("Accept", "*/*");
        props.put("User-Agent", "Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.4 Safari/537.36");

        ClientAPI clientAPI = new ClientHttpURLConnectionsJava(props);
//        ClientAPI clientAPI = new ClientHttpClientJava(props);
//        ClientAPI clientAPI = new ClientJSoup(props);

        System.out.println("clientAPI === " + clientAPI.getClass().getSimpleName());
        return clientAPI;
    }

    public static void main(String[] args) {

        System.out.println("\n*********************************");
        String newUser = createUser();
        System.out.println("newUser = " + newUser);

        System.out.println("\n*********************************");
        String updatedUser = updateUser(10);
        System.out.println("updatedUser = " + updatedUser);

        System.out.println("\n*********************************");
        int delRespCode = deleteUser(1);
        System.out.println("respCode = " + delRespCode);

        System.out.println("\n*********************************");
        getUsers();

        System.out.println("\n*********************************");
        int userId = 1;
        User user = getUserById(userId);
        if (user != null) {
            System.out.println("getUserByName() = " + user.toString());
        } else {
            System.out.println("User with id " +userId+ " not found!");
        }

        System.out.println("\n*********************************");
        String userName = "Antonette";
        User user2 = getUserByName(userName);
        if (user2 != null) {
            System.out.println("getUserByName() = " + user2.toString());
        } else {
            System.out.println("User with name " +userName+ " not found!");
        }
    }

    private static String createUser() {
        System.out.println("Create newUser object of User:");

        User newUser = new User(22, "My Name", "my_username", "my@email.eml", "33558800", "epowhost.com")
            .setCompany(new User.Company("My Company epowhost.com", "Bla Bla", "What is bs?"))
            .setAddress(new User.Address("My Street", "My Suit", "My City", "My ZipCode"))
            .setAddressGeo(new User.Address.Geo("23.345", "65.234"));

        System.out.println("newUser.toString() = " + newUser);

        System.out.println("\nSerialize newUser to json:");

        String jsonNewUser = JsonHelper.objToJson(newUser);
        System.out.println("JsonHelper.objToJson(newUser) = " + jsonNewUser);

        System.out.println("\nRequest API - Create:");

        ClientAPI clientAPI = getClientAPI();
        Map<String,String> createResult = clientAPI.create(URL_USERS, jsonNewUser, null);
        int respCode = Integer.parseInt(createResult.get("respCode"));

        String respBody = createResult.get("respBody");
        System.out.println("respCode = " + respCode);

        return respBody;
    }

    private static String updateUser(int userId) {
        System.out.printf("Update User with userId = %d:\n", userId);

        User newUser = new User(userId, "My Name", "my_username", "my@email.eml", "33558800", "epowhost.com")
            .setCompany(new User.Company("My Company epowhost.com", "Bla Bla", "What is bs?"))
            .setAddress(new User.Address("My Street", "My Suit", "My City", "My ZipCode"))
            .setAddressGeo(new User.Address.Geo("23.345", "65.234"));

        String jsonNewUser = JsonHelper.objToJson(newUser);
        System.out.println("jsonNewUser = " + jsonNewUser);

        System.out.println("\nRequest API - Update:");

        ClientAPI clientAPI = getClientAPI();
        Map<String,String> updateResult = clientAPI.update(URL_USERS +"/"+ userId, jsonNewUser, null);
        int respCode = Integer.parseInt(updateResult.get("respCode"));

        String respBody = updateResult.get("respBody");
        System.out.println("respCode = " + respCode);

        return respBody;
    }

    private static int deleteUser(int userId) {
        System.out.printf("Delete User with userId = %d:\n", userId);

        System.out.println("\nRequest API - Delete:");

        ClientAPI clientAPI = getClientAPI();
        Map<String,String> deleteResult = clientAPI.delete(URL_USERS +"/"+ userId, null);
        int respCode = Integer.parseInt(deleteResult.get("respCode"));

        String respBody = deleteResult.get("respBody");

        return respCode;
    }

    private static void  getUsers() {

        System.out.println("Get All Users:");

        ClientAPI clientAPI = getClientAPI();
        Map<String,String> getResult = clientAPI.get(URL_USERS, null, null);
        int respCode = Integer.parseInt(getResult.get("respCode"));
        String respBody = getResult.get("respBody");

        List<User> users = JsonHelper.jsonToListObjects(respBody, User.class);
        if (users != null && !users.isEmpty()) {
            System.out.println("respCode = " + respCode);
            System.out.printf("users.size() = %d\n\n", users.size());
            for (User user : users) {
                if (user.getId() < 3 || user.getId() > 8) {
                    System.out.println("user.toString() = " + user.toString());
                }
            }
        }
    }

    private static User getUserById(int userId) {

        System.out.printf("Get User with userId = %d:\n", userId);

        System.out.println("\nRequest API - Get:");
        ClientAPI clientAPI = getClientAPI();
        Map<String,String> getResult = clientAPI.get(URL_USERS +"/"+ userId, null, null);

        int respCode = Integer.parseInt(getResult.get("respCode"));

        String respBody = getResult.get("respBody");
        System.out.println("respCode = " + respCode);
        System.out.println("respBody = " + respBody);

        System.out.println("\nDeserialize User from json:");

        return JsonHelper.jsonToObj(respBody, User.class);
    }

    private static User getUserByName(String userName) {

        System.out.printf("Get User with username = %s:\n", userName);

        System.out.println("\nRequest API - Get:");

        ClientAPI clientAPI = getClientAPI();
        Map<String,String> getResult = clientAPI.get(URL_USER_NAME + userName, null, null);
        int respCode = Integer.parseInt(getResult.get("respCode"));

        String respBody = getResult.get("respBody");
        System.out.println("respCode = " + respCode);
        System.out.println("respBody = " + respBody);

        System.out.println("\nDeserialize User from json:");
        List<User> listUsers = JsonHelper.jsonToListObjects(respBody, User.class);
        if (listUsers != null && !listUsers.isEmpty()) {
            return listUsers.get(0);
        }
        return null;
    }
}
