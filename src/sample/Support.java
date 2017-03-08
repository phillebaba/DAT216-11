package sample;

import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Support {

    public static boolean isDivisible(Product product){
        String s = product.getUnitSuffix();
        if (s.equals("l") || s.equals("kg")){
            return true;
        }else{
            return false;
        }
    }

    public static double round(double n){
        DecimalFormat df = new DecimalFormat("#.#",
                DecimalFormatSymbols.getInstance(Locale.forLanguageTag("en_US")));
        df.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println("Before format: " + n);
        n = Double.valueOf(df.format(n));
        System.out.println("After format: " + n);
        return n;

    }

    public static double round2(double n){
        DecimalFormat df = new DecimalFormat("0.00",
                DecimalFormatSymbols.getInstance(Locale.forLanguageTag("en_US")));
        df.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println("(2)Before format: " + n);
        n = Double.valueOf(df.format(n));
        System.out.println("(2)After format: " + n);
        return n;

    }

}
