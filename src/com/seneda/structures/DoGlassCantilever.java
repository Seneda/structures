package com.seneda.structures;

import java.util.Scanner;

/**
 * Created by seneda on 16/02/17.
 */
public class DoGlassCantilever {
    public static void main(String[] args)
    {
        Scanner S    = new java.util.Scanner(System.in);
        System.out.println("Enter the Line load (P) : ");
        double P      = S.nextDouble();
        System.out.println("Enter the Height (L) : ");
        double L      = S.nextDouble();
        System.out.println("Enter the Youngs Modulus of the glass (E) : ");
        double E      = S.nextDouble();
        System.out.println("Enter the maximum allowed tip delection (delta) : ");
        double delta  = S.nextDouble();
        System.out.println("Enter the maximum allowed stress : ");
        double stress = S.nextDouble();
        double t_def = GlassCantilever.thicknessMinDeflection(P, L, E, delta);
        double t_str = GlassCantilever.thicknessMinStress(P, L, stress);

        System.out.println("The minimum thickness based on deflection is : " + t_def);
        System.out.println("The minimum thickness based on stress is : " + t_str);
        S.nextLine();
        S.nextLine();
    }
}
