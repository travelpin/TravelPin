package db.mysql;

class Dog {
    void bark(Dog d) {
        System.out.println("bark");
    }
}
class Poodle extends Dog {
//    void bark(Dog d) {
//        System.out.println("woof");
//   }
    void bark(Poodle p) {
        System.out.println("yap");
    }
    void play(Dog d) {
        System.out.println("no");
    }
    void play(Poodle p) {
        System.out.println("bowwow");
    }
    public static void main(String[] args) {
        Dog dan = new Poodle();
        Poodle pym = new Poodle();
        dan.bark(dan);
    }







}
