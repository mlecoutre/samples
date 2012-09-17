package org.mat.samples.closure;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Person[] persons = new Person[] {new Person("Me",10), new Person("You", 12)};

        Arrays.sort(persons, {Person a, Person b -> (a.age < b.age) ? 1 : (a.age > b.age ? -1 : 0)});

    });

    }
}
