package instance;

//for now enum, if transfer through JSON will be available then make abstract class subject/factory pattern
public enum Subject implements Transferable {
    MATHEMATICS,
    COMPUTER_SCIENCE,
    MUSIC,
    ART,
    PE,
    FOREIGN_LANGUAGE,
    ENGLISH,
    CHEMISTRY,
    BIOLOGY,
    HISTORY,
    PHYSICS,
    GEOGRAPHY,
    ALL
}
