public class Echo {
    private String inputString;

    Echo(String input) {
        inputString = input;
    }

    public void toEcho() {
        System.out.println(inputString);
    }
}
