package org.example.generator;

import org.example.model.Courier;

public class CourierGenerate {

    public static Courier getRandom(){
        return new Courier( System.currentTimeMillis() + "apitest","qwerty","Mary");
    }

    public static Courier getWithoutLogin(){
        return new Courier(null,"qwerty","Mary");
    }

    public static Courier getWithoutPassword(){
        return new Courier("Strex",null,"Mary");
    }

    public static Courier getWithEmptyPassword(){
        return new Courier("Test","","Mary");
    }

    public static Courier getWithEmptyLogin(){
        return new Courier("","Test","Mary");
    }

}
