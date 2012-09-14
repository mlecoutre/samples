// GuiceJunitRunner.java, created by Fabio Strozzi on Mar 27, 2011
package org.mat.sample;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * A JUnit class runner for Guice based applications.
 *
 * @author Fabio Strozzi
 */
public class GuiceTestRunner extends BlockJUnit4ClassRunner {
    private Injector injector;

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    public @interface GuiceModules {
        Class<?>[] value();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.junit.runners.BlockJUnit4ClassRunner#createTest()
     */
    @Override
    public Object createTest() throws Exception {
        Object obj = super.createTest();
        injector.injectMembers(obj);
        return obj;
    }

    /**
     * Instances a new JUnit runner.
     *
     * @param klass
     *            The test class
     * @throws InitializationError
     */
    public GuiceTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        Class<?>[] classes = getModulesFor(klass);
        injector = createInjectorFor(classes);
    }

    /**
     * @param classes
     * @return
     * @throws InitializationError
     */
    private Injector createInjectorFor(Class<?>[] classes) throws InitializationError {
        Module[] modules = new Module[classes.length];
        for (int i = 0; i < classes.length; i++) {
            try {
                modules[i] = (Module) (classes[i]).newInstance();
            } catch (InstantiationException e) {
                throw new InitializationError(e);
            } catch (IllegalAccessException e) {
                throw new InitializationError(e);
            }
        }

        List<Module> lModules = Arrays.asList(modules);
        Module m =new JpaPersistModule("default");
        Collections.addAll(lModules, m);
        return Guice.createInjector(lModules);
    }

    /**
     * Gets the Guice modules for the given test class.
     *
     * @param klass
     *            The test class
     * @return The array of Guice {@link Module} modules used to initialize the
     *         injector for the given test.
     * @throws InitializationError
     */
    private Class<?>[] getModulesFor(Class<?> klass) throws InitializationError {
        GuiceModules annotation = klass.getAnnotation(GuiceModules.class);
        if (annotation == null)
            throw new InitializationError(
                    "Missing @GuiceModules annotation for unit test '" + klass.getName()
                            + "'");
        return annotation.value();
    }

}