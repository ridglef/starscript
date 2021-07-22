package meteordevelopment.starscript;

import meteordevelopment.starscript.value.Value;

import java.util.Random;

/** Standard library with some default functions and variables. */
public class StandardLib {
    private static final Random rand = new Random();

    /** Adds the functions and variables to the provided {@link Starscript} instance. */
    public static void init(Starscript ss) {
        // Variables
        ss.set("PI", Value.number(Math.PI));

        // Numbers
        ss.set("round", Value.function(StandardLib::round));
        ss.set("floor", Value.function(StandardLib::floor));
        ss.set("ceil", Value.function(StandardLib::ceil));
        ss.set("abs", Value.function(StandardLib::abs));
        ss.set("random", Value.function(StandardLib::random));

        // Strings
        ss.set("toUpper", Value.function(StandardLib::toUpper));
        ss.set("toLower", Value.function(StandardLib::toLower));
        ss.set("contains", Value.function(StandardLib::contains));
        ss.set("replace", Value.function(StandardLib::replace));
    }

    // Numbers

    public static Value round(Starscript ss, int argCount) {
        if (argCount == 1) {
            double a = ss.popNumber("Argument to round() needs to be a number.");
            return Value.number(Math.round(a));
        }
        else if (argCount == 2) {
            double b = ss.popNumber("Second argument to round() needs to be a number.");
            double a = ss.popNumber("First argument to round() needs to be a number.");

            double x = Math.pow(10, (int) b);
            return Value.number(Math.round(a * x) / x);
        }
        else {
            ss.error("round() requires 1 or 2 arguments, got %d.", argCount);
            return null;
        }
    }

    public static Value floor(Starscript ss, int argCount) {
        if (argCount != 1) ss.error("floor() requires 1 argument, got %d.", argCount);
        double a = ss.popNumber("Argument to floor() needs to be a number.");
        return Value.number(Math.floor(a));
    }

    public static Value ceil(Starscript ss, int argCount) {
        if (argCount != 1) ss.error("ceil() requires 1 argument, got %d.", argCount);
        double a = ss.popNumber("Argument to ceil() needs to be a number.");
        return Value.number(Math.ceil(a));
    }

    public static Value abs(Starscript ss, int argCount) {
        if (argCount != 1) ss.error("abs() requires 1 argument, got %d.", argCount);
        double a = ss.popNumber("Argument to abs() needs to be a number.");
        return Value.number(Math.abs(a));
    }

    public static Value random(Starscript ss, int argCount) {
        if (argCount == 0) return Value.number(rand.nextDouble());
        else if (argCount == 2) {
            double max = ss.popNumber("Second argument to random() needs to be a number.");
            double min = ss.popNumber("First argument to random() needs to be a number.");

            return Value.number(min + (max - min) * rand.nextDouble());
        }

        ss.error("random() requires 0 or 2 arguments, got %d.", argCount);
        return Value.null_();
    }

    // Strings

    public static Value toUpper(Starscript ss, int argCount) {
        if (argCount != 1) ss.error("toUpper() requires 1 argument, got %d.", argCount);
        String a = ss.popString("Argument to toUpper() needs to be a string.");
        return Value.string(a.toUpperCase());
    }

    public static Value toLower(Starscript ss, int argCount) {
        if (argCount != 1) ss.error("toLower() requires 1 argument, got %d.", argCount);
        String a = ss.popString("Argument to toLower() needs to be a string.");
        return Value.string(a.toLowerCase());
    }

    public static Value contains(Starscript ss, int argCount) {
        if (argCount != 2) ss.error("replace() requires 2 arguments, got %d.", argCount);

        String search = ss.popString("Second argument to contains() needs to be a string.");
        String string = ss.popString("First argument to contains() needs to be a string.");

        return Value.bool(string.contains(search));
    }

    public static Value replace(Starscript ss, int argCount) {
        if (argCount != 3) ss.error("replace() requires 3 arguments, got %d.", argCount);

        String to = ss.popString("Third argument to replace() needs to be a string.");
        String from = ss.popString("Second argument to replace() needs to be a string.");
        String string = ss.popString("First argument to replace() needs to be a string.");

        return Value.string(string.replace(from, to));
    }
}
