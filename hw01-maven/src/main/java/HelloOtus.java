import com.google.common.base.Splitter;

public class HelloOtus {
    public static void main(String[] args) {
        Splitter.on(' ')
                .omitEmptyStrings()
                .trimResults()
                .split(" Hello , Otus!    It is nice to join     this course!")
                .forEach(System.out::println);

    }
}