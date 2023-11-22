package m13.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Task1Tests {
    private final static String URL_USERS = "https://jsonplaceholder.typicode.com/users";
    private final static String URL_USER_NAME = "https://jsonplaceholder.typicode.com/users?username=";

    public static void main(String[] args) {

        String newUser = createUser();
        System.out.println("newUser = " + newUser);
        System.out.println("*********************************");

        String updatedUser = updateUser(10);
        System.out.println("updatedUser = " + updatedUser);
        System.out.println("*********************************");

        int delRespCode = deleteUser(1);
        System.out.println("delRespCode = " + delRespCode);
        System.out.println("*********************************");

        getUsers();
        System.out.println("*********************************");

        User user = getUserById(1);
        System.out.println("user.toString() = " + user.toString());
        System.out.println("*********************************");

        User user2 = getUserByName("Antonette");
        System.out.println("user.toString() = " + user2.toString());
        System.out.println("*********************************");
    }

    private static String createUser() {
        System.out.println("\nCreate newUser object of User:");

        User newUser = new User(22, "My Name", "myusername", "my@email.eml", "33558800", "epowhost.com");
        newUser.setAddress(new Address("My Street", "My Suit", "My City", "My ZipCode"));
        newUser.getAddress().setGeo(new Geo("23.345", "65.234"));
        newUser.setCompany(new Company("My Company", "Bla Bla", "What is bs?"));
        System.out.println("newUser.toString() = " + newUser.toString());

        System.out.println("\nSerialize newUser to json:");

        String jsonNewUser = JsonHelper.objToJson(newUser);
        System.out.println("JsonHelper.objToJson(newUser) = " + jsonNewUser);

        System.out.println("\nRequest API - Create:");
        ClientAPI clientAPI = new ClientJSoup();
        Map<String,String> createResult = clientAPI.create(URL_USERS, jsonNewUser, null);
        int respCode = Integer.parseInt(createResult.get("respCode"));
        String respBody = createResult.get("respBody");
        System.out.println("respCode = " + respCode);

        return respBody;
    }

    private static String updateUser(int userId) {
        System.out.printf("\nUpdate User with userId = %d:\n", userId);

        User newUser = new User(userId, "My Name", "myusername", "my@email.eml", "33558800", "epowhost.com");
        newUser.setAddress(new Address("My Street", "My Suit", "My City", "My ZipCode"));
        newUser.getAddress().setGeo(new Geo("23.345", "65.234"));
        newUser.setCompany(new Company("My Company", "Bla Bla", "What is bs?"));

        String jsonNewUser = JsonHelper.objToJson(newUser);
        System.out.println("jsonNewUser = " + jsonNewUser);

        System.out.println("\nRequest API - Update:");
        ClientAPI clientAPI = new ClientJSoup();
        Map<String,String> updateResult = clientAPI.update(URL_USERS +"/"+ userId, jsonNewUser, null);
        int respCode = Integer.parseInt(updateResult.get("respCode"));
        String respBody = updateResult.get("respBody");
        System.out.println("respCode = " + respCode);

        return respBody;
    }

    private static int deleteUser(int userId) {
        System.out.printf("\nDelete User with userId = %d:\n", userId);

        System.out.println("\nRequest API - Delete:");

        ClientAPI clientAPI = new ClientJSoup();
        Map<String,String> deleteResult = clientAPI.delete(URL_USERS +"/"+ userId, null);

        int respCode = Integer.parseInt(deleteResult.get("respCode"));
        String respBody = deleteResult.get("respBody");
        System.out.println("respBody = " + respBody);

        return respCode;
    }

    private static void  getUsers() {

        System.out.println("\nGet All Users:");

        ClientAPI clientAPI = new ClientJSoup();
        Map<String,String> getResult = clientAPI.get(URL_USERS, null, null);
        int respCode = Integer.parseInt(getResult.get("respCode"));
        String respBody = getResult.get("respBody");

        List<User> users = JsonHelper.jsonToListObjects(respBody, User.class);
        System.out.printf("users.size() = %d\n\n", users.size());
        for (User user: users) {
            if (user.getId() < 3 || user.getId() > 8) {
                System.out.println("user.toString() = " + user.toString());
            }
        }
    }
    private static User getUserById(int userId) {

        System.out.printf("\nGet User with userId = %d:\n", userId);

        System.out.println("\nRequest API - Get:");
        ClientAPI clientAPI = new ClientJSoup();
        Map<String,String> getResult = clientAPI.get(URL_USERS +"/"+ userId, null, null);

        int respCode = Integer.parseInt(getResult.get("respCode"));
        String respBody = getResult.get("respBody");
        System.out.println("respCode = " + respCode);
        System.out.println("respBody = " + respBody);

        System.out.println("\nDeserialize User from json:");

        return JsonHelper.jsonToObj(respBody, User.class);
    }

    private static User getUserByName(String userName) {

        System.out.printf("\nGet User with username = %s:\n", userName);

        System.out.println("\nRequest API - Get:");
        ClientAPI clientAPI = new ClientJSoup();
        Map<String,String> getResult = clientAPI.get(URL_USER_NAME + userName, null, null);

        int respCode = Integer.parseInt(getResult.get("respCode"));
        String respBody = getResult.get("respBody");
        System.out.println("respCode = " + respCode);
        System.out.println("respBody = " + respBody);


        System.out.println("\nDeserialize User from json:");
        List<User> users = JsonHelper.jsonToListObjects(respBody, User.class);

        return users.get(0);
    }
}
