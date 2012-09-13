package org.mat.sample;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.util.List;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
 
/**
 * Uses Guice to inject JUnit4 tests.
 *
 * @author Gili Tzabari
 */
public class GuiceTestRunner extends BlockJUnit4ClassRunner
{
  private final Injector injector;
 
  /**
   * Creates a new GuiceTestRunner.
   *
   * @param classToRun the test class to run
   * @param modules the Guice modules
   * @throws InitializationError if the test class is malformed
   */
  public GuiceTestRunner(final Class<?> classToRun, Module... modules) throws InitializationError
  {
    super(classToRun);
    this.injector = Guice.createInjector(modules);
  }
 
  @Override
  public Object createTest()
  {
    return injector.getInstance(getTestClass().getJavaClass());
  }
 
  @Override
  protected void validateZeroArgConstructor(List<Throwable> errors)
  {
    // Guice can inject constructors with parameters so we don't want this method to trigger an error
  }
 
  /**
   * Returns the Guice injector.
   *
   * @return the Guice injector
   */
  protected Injector getInjector()
  {
    return injector;
  }
}
