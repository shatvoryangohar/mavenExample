package education.education.storage;


import education.education.exception.UserNotFoundException;
import education.education.model.User;

public class UserStorage {
    private User[] users = new User[10];
    private int size;

    public void add(String type, User user) {
        for (int i = 0; i < size; i++) {
            if (users.length == size) {
                extend();
            }
            users[size++] = user;
        }
    }


    private void extend() {

        User[] array = new User[users.length + 10];
        System.arraycopy(users, 0, array, 0, users.length);
        users = array;

    }

    public void print() {

        for (int i = 0; i < size; i++) {
            System.out.println(users[i]);
        }
    }


//    public User getByEmailAndPassword(String email, String password) {
//        for (int i = 0; i < size; i++) {
//
//            if (users[i].getEmail().equals(email) && users[i].getPassword().equals(password)) {
//                return users[i];
//            }
//        }
//
//        return null;
//    }

    public User getByEmail(String email) throws UserNotFoundException {
        for (int i = 0; i < size; i++) {
            if (users[i].getEmail().contains(email)) {
                return users[i];
            }
            return null;
        }
        throw new UserNotFoundException("User does not exist. Email: " + email);

    }
}




